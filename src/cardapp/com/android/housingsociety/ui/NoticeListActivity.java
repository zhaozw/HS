package cardapp.com.android.housingsociety.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;







import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.adapter.NoticeListAdapter;
import cardapp.com.android.housingsociety.bean.Notice;
import cardapp.com.android.housingsociety.bean.NoticeClass;
import cardapp.com.android.housingsociety.bean.NoticeItem;
import cardapp.com.android.housingsociety.bean.NoticeItemFetchedEvent;
import cardapp.com.android.housingsociety.control.DataControl;
import cardapp.com.android.housingsociety.debug.AppLogger;
import cardapp.com.android.housingsociety.ui.NoticeDetailActivity.MyRequestCallBack;
import cardapp.com.android.housingsociety.util.AppUtil;
import cardapp.com.android.housingsociety.util.MyNetUtil;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 通知列表UI
 *
 * @author CardApp@ZuoQing
 *
 */
@EActivity(R.layout.activity_notice_list)
public class NoticeListActivity extends BaseActivity {
	public static final String ESTATESERVICEACTIVITYNOTICECLASS = "cardapp.com.android.housingsociety.ui.EstateServiceActivity.noticeclass";
	@ViewById(R.id.title)
	TextView title;
	@ViewById(R.id.list)
	ListView list;

	long noticeId; // 首次为0 第一次获取列表得到的第一条数据中NoticeId的值
	long page; // 第几页，首次为1
	long noticeClassId; // 通告分类ID，由父类传入
	NoticeClass noticeClass;

	List<NoticeItem> mDatas = new ArrayList<NoticeItem>();
	BaseAdapter mAdapter;

	@Bean
	DataControl dataControl;
	private Notice notice;
	private String pdfPath;
	private HttpHandler<File> httpHandler;

	@AfterViews
	void afterViews() {
		// get extra
		noticeClass = (NoticeClass) getIntent().getSerializableExtra(
				ESTATESERVICEACTIVITYNOTICECLASS);
		// init parameter
		noticeId = 0;
		page = 1;
		noticeClassId = noticeClass.getNoticeClassId();
		// init view
		title.setText(AppUtil.getRightStringByLanguage(
				myApplication.getAppLan(), noticeClass.getNoticeClassChiName(),
				noticeClass.getNoticeClassChiName(),
				noticeClass.getNoticeClassEngName()));

		// 后台获取数据
		dataControl.getNoticeItems(noticeId, page, noticeClassId);
	}

	/**
	 * 接收数据集合
	 * 
	 * @param shopList
	 */
	@UiThread
	public void onEvent(NoticeItemFetchedEvent e) {
		if (e != null && e.getList() != null && e.getList().size() != 0) {
			mDatas.clear();
			mDatas.addAll(e.getList());
			updateList();
		}
	}

	/**
	 * 查考pdf数据接收
	 * 
	 */
	@UiThread
	public void onEvent(Notice e) {
		if (e != null) {
			dataControl.doVisit(2, e.getNoticeId(), e.getIntegral());	// visit Server
			notice = e;
			String address = AppUtil.getRightStringByLanguage(
					myApplication.getAppLan(), notice.getMobileChiContent(),
					notice.getMobileChiContent(), notice.getMobileEngContent());
			pdfPath = Environment.getExternalStorageDirectory().getPath()
					+ "/HousingSociety/download/pdf-" + notice.getChiTitle()
					+ "-" + notice.getEngTitle() + ".pdf";
			AppLogger.i("pdfName:" + pdfPath);
			File file = new File(pdfPath);
			if (MyNetUtil.serverEnable(this)){
				HttpUtils http = new HttpUtils();
				httpHandler = http.download(address, pdfPath,
						false, true, new MyRequestCallBack());
			} else if (file.exists()) { // pdf文件已经存在
				dismissProgressDialog();
				// 打开文件
				openPDFFile(pdfPath);
			} 
		}
	}

	/**
	 * 下载文件事件监听
	 *
	 * @author CardApp@ZuoQing
	 *
	 */
	class MyRequestCallBack extends RequestCallBack<File> {
		@Override
		public void onStart() {
		}

		@Override
		public void onLoading(long total, long current, boolean isUploading) {
		}

		@Override
		public void onSuccess(ResponseInfo<File> responseInfo) {
			dismissProgressDialog();
			// 打开文件
			openPDFFile(pdfPath);
		}

		@Override
		public void onFailure(HttpException error, String msg) {
			AppLogger.i(msg);
			dismissProgressDialog();
			if (msg.contains("maybe the file has downloaded completely")) {
				// 打开文件
				openPDFFile(pdfPath);
			} else {
				toast(getString(R.string.visitServerError));
			}
		}
	}

	/**
	 * 打开pdf文件
	 * 
	 * @param pdfName
	 */
	private void openPDFFile(final String pdfName) {
		Intent intent = new Intent("android.intent.action.VIEW");
		Uri uri = Uri.fromFile(new File(pdfName));
		intent.setDataAndType(uri, "application/pdf");
		mActivity.startActivity(intent);
	}
	
	/**
	 * 接收Bus提示
	 * 
	 * @param shopList
	 */
	@UiThread
	public void onEvent(String e) {
		if (mDatas.size() == 0) {
			toast(e);
		}
	}

	/**
	 * 更新列表
	 */
	private void updateList() {
		if (mAdapter == null) {
			mAdapter = new NoticeListAdapter(this, mDatas);
			list.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}
	}

	@ItemClick(R.id.list)
	void itemClick(int position) {
		/*Intent intent = new Intent(this, NoticeDetailActivity_.class);
		// save extra
		NoticeItem item = mDatas.get(position);
		if (item != null && MyNetUtil.serverEnable(this)) {
			intent.putExtra(
					NoticeDetailActivity.ESTATESERVICEACTIVITYNOTICEITEM, item);
			startActivity(intent);
		} else {
			toast(getString(R.string.visitServerError));
		}*/
		// 获取数据打开pdf
		NoticeItem item = mDatas.get(position);
		showLoadingProgressDialog();
		if (MyNetUtil.serverEnable(this)){
			dataControl.getNoticeAsync(item.getNoticeId());
		} else {
			dataControl.getNotice(item.getNoticeId());
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		myApplication.BUS.register(this);
	}

	@Override
	public void onStop() {
		myApplication.BUS.unregister(this);
		super.onStop();
	}

}

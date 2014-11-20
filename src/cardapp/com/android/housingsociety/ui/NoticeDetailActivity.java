package cardapp.com.android.housingsociety.ui;

import java.io.File;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.bean.Notice;
import cardapp.com.android.housingsociety.bean.NoticeItem;
import cardapp.com.android.housingsociety.control.DataControl;
import cardapp.com.android.housingsociety.debug.AppLogger;
import cardapp.com.android.housingsociety.util.AppUtil;
import cardapp.com.android.housingsociety.util.MyNetUtil;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 通知详细UI
 *
 * @author CardApp@ZuoQing
 *
 */
@EActivity(R.layout.activity_notice_detail)
public class NoticeDetailActivity extends BaseActivity {
	public static final String ESTATESERVICEACTIVITYNOTICEITEM = "cardapp.com.android.housingsociety.ui.NoticeListActivity.noticeItem";

	@ViewById(R.id.title)
	TextView title;
	@ViewById(R.id.content)
	TextView content;
	@ViewById(R.id.seeFile)
	Button seeFile;

	long noticeId; // 通告ID
	NoticeItem noticeItem;
	Notice notice;

	String pdfPath; // pdf文件路径
	@Bean
	DataControl dataControl;
	HttpHandler<File> httpHandler;

	@AfterViews
	void afterViews() {
		// get extra
		noticeItem = (NoticeItem) getIntent().getSerializableExtra(
				ESTATESERVICEACTIVITYNOTICEITEM);
		// init parameter
		noticeId = noticeItem.getNoticeId();
		// init view
		title.setText(AppUtil.getRightStringByLanguage(
				myApplication.getAppLan(), noticeItem.getChiTitle(),
				noticeItem.getChiTitle(), noticeItem.getEngTitle()));

		// 后台获取数据
		if (MyNetUtil.serverEnable(this)){
			dataControl.getNoticeAsync(noticeId);
			content.setText(getString(R.string.getAddress));
		} else {
			dataControl.getNotice(noticeId);
		}
	}

	/**
	 * 接收Bus数据后更新界面
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
			content.setText(address);
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
				content.setText(getString(R.string.everDownloaded) + pdfPath);
				// 打开文件
				openPDFFile(pdfPath);
				showLoadingProgressDialog();
			} else {
				content.setText(getString(R.string.visitServerError));
				showLoadingProgressDialog();
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
			content.setText(getString(R.string.connectServer));
		}

		@Override
		public void onLoading(long total, long current, boolean isUploading) {
			content.setText(getString(R.string.downloadFile));
		}

		@Override
		public void onSuccess(ResponseInfo<File> responseInfo) {
			content.setText(getString(R.string.downloadSuccess));
			dismissProgressDialog();
			// 打开文件
			openPDFFile(pdfPath);
		}

		@Override
		public void onFailure(HttpException error, String msg) {
			AppLogger.i(msg);
			if (msg.contains("maybe the file has downloaded completely")) {
				content.setText(getString(R.string.everDownloaded));
				// 打开文件
				openPDFFile(pdfPath);
			} else {
				content.setText(getString(R.string.downloadFail));
			}
			dismissProgressDialog();
		}
	}
	
	@Click({R.id.seeFile})
	void click(View v) {
		switch (v.getId()) {
		case R.id.seeFile:
			if (!TextUtils.isEmpty(pdfPath)) {
				openPDFFile(pdfPath);
			}
			break;
		}
	}

	/**
	 * 打开pdf文件
	 * 
	 * @param pdfName
	 */
	private void openPDFFile(final String pdfName) {
		seeFile.setEnabled(true);
		Intent intent = new Intent("android.intent.action.VIEW");
		Uri uri = Uri.fromFile(new File(pdfName));
		intent.setDataAndType(uri, "application/pdf");
		mActivity.startActivity(intent);
		finish();
	}

	/**
	 * 接收Bus提示
	 * 
	 * @param shopList
	 */
	@UiThread
	public void onEvent(String e) {
		if (notice == null) {
			toast(e);
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
		if (httpHandler != null) {
			httpHandler.cancel();
		}
		super.onStop();
	}

}

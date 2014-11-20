package cardapp.com.android.housingsociety.ui;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.adapter.LifeMustAClassAdapter;
import cardapp.com.android.housingsociety.bean.Necessity;
import cardapp.com.android.housingsociety.bean.NecessityClass;
import cardapp.com.android.housingsociety.bean.NecessityListFetchedEvent;
import cardapp.com.android.housingsociety.control.DataControl;
import cardapp.com.android.housingsociety.util.AppUtil;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 确定了一个类型的生活必备(第二级)
 *
 * @author CardApp@ZuoQing
 *
 */
@EActivity(R.layout.activity_life_must_a_class)
public class LifeMustAClassActivity extends BaseActivity {
	public static final String LIFEMUSTACLASSACTIVITYNECCESSITYID = "cardapp.com.android.housingsociety.ui.LifeMustAClassActivity.necessityId";

	@ViewById(R.id.list)
	ListView list;
	@ViewById(R.id.title)
	TextView title;

	BaseAdapter mAdapter = null;
	List<Necessity> mDatas = new ArrayList<Necessity>();
	long necessityClassId = 2L; // 从父界面传入
	NecessityClass necessityClass;

	@Bean
	DataControl dataControl;

	@AfterViews
	void afterViews() {
		// get NeccessityClass
		Intent i = getIntent();
		if (i != null) {
			necessityClass = (NecessityClass) i
					.getSerializableExtra(LifeMustActivity.LIFEMUSTACLASSACTIVITYNECCESSITYCLASSID);
		}
		// get NeccessityClassId
		if (necessityClass != null) {
			// set title
			title.setText(AppUtil.getRightStringByLanguage(myApplication
					.getAppLan(), necessityClass.getNecessityClassChiName()
					.trim(), necessityClass.getNecessityClassChiName().trim(),
					necessityClass.getNecessityClassEngName().trim()));
			necessityClassId = necessityClass.getNecessityClassId();
			// backgroud get data
			dataControl.getNecessityList(necessityClassId);
		}
	}

	/**
	 * 接收Bus数据后更新界面
	 * 
	 * @param shopList
	 */
	@UiThread
	public void onEvent(NecessityListFetchedEvent e) {
		if (e != null && e.getList() != null && e.getList().size() != 0) {
			mDatas.clear();
			mDatas.addAll(e.getList());
			updateList();
		}
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
	 * 更新ListView
	 */
	void updateList() {
		if (mAdapter == null) {
			mAdapter = new LifeMustAClassAdapter(this, mDatas);
			list.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * ListView click event
	 * 
	 * @param position
	 */
	@ItemClick(R.id.list)
	void itemClick(int position) {
		Necessity necessity = mDatas.get(position);

		if (necessity.getType() == 3) {	// 巴士类型，提示拨打电话
			String phone = necessity.getNecessityPhone();
			callPhone(phone);
		} else {
			Intent intent = new Intent(this, LifeMustDetailActivity_.class);
			intent.putExtra(LIFEMUSTACLASSACTIVITYNECCESSITYID, necessity);
			startActivity(intent);
		}
	}

	/**
	 * 拨打手机
	 */
	private void callPhone(final String phone) {
		if (TextUtils.isEmpty(phone)) {
			
			return ;
		}
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(getString(R.string.call) + ":" + phone);
		builder.setPositiveButton(getString(R.string.call),
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent2 = new Intent(
								"android.intent.action.DIAL", Uri.parse("tel:"
										+ phone));
						mActivity.startActivity(intent2);
					}
				});
		builder.setNegativeButton(getString(android.R.string.cancel),
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.show();
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

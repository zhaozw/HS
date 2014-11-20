package cardapp.com.android.housingsociety.ui;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;


import cardapp.com.android.housingsociety.MyApplication;
import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.adapter.LifeMustAdapter;
import cardapp.com.android.housingsociety.bean.NecessityClass;
import cardapp.com.android.housingsociety.bean.NecessityClassListFetchedEvent;
import cardapp.com.android.housingsociety.control.DataControl;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 生活必需界面(第一级)
 *
 * @author CardApp@ZuoQing
 *
 */
@EActivity(R.layout.activity_life_must)
public class LifeMustActivity extends BaseActivity {
	public static final String LIFEMUSTACLASSACTIVITYNECCESSITYCLASSID = "cardapp.com.android.housingsociety.ui.LifeMustAClassActivity.necessityClassId";

	@ViewById(R.id.list)
	ListView list;
	@ViewById(R.id.title)
	TextView title;
	@App
	MyApplication app;

	BaseAdapter mAdapter = null;
	List<NecessityClass> mDatas = new ArrayList<NecessityClass>();
	String estate = "";

	@Bean
	DataControl dataControl;

	@AfterViews
	void afterViews() {
		title.setText(getString(R.string.lifeMust));
		// 检查能否连接服务器

		estate = app.getString(R.string.estate);
		if (!TextUtils.isEmpty(estate)) {
			// backgroundGetData();
			dataControl.getNecessityClassList(estate);
		}
	}

	/**
	 * 接收Bus数据后更新界面
	 * 
	 * @param shopList
	 */
	@UiThread
	public void onEvent(NecessityClassListFetchedEvent e) {
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
	 * 更新数据
	 */
	void updateList() {
		if (mAdapter == null) {
			mAdapter = new LifeMustAdapter(this, mDatas);
			list.setAdapter(mAdapter);
			View v = View.inflate(this, R.layout.image, null);
			list.addHeaderView(v);
		} else {
			mAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * list 点击事件
	 * 
	 * @param position
	 */
	@ItemClick(R.id.list)
	void itemClick(int position) {
		if (position == 0) {
			return;
		}
		NecessityClass nc = mDatas.get(position - 1);

		Intent intent = new Intent(this, LifeMustAClassActivity_.class);
		intent.putExtra(LIFEMUSTACLASSACTIVITYNECCESSITYCLASSID, nc);
		startActivity(intent);
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

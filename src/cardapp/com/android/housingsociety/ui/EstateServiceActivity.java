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
import cardapp.com.android.housingsociety.adapter.EstateServiceAdapter;
import cardapp.com.android.housingsociety.bean.NoticeClass;
import cardapp.com.android.housingsociety.bean.NoticeClassFetchedEvent;
import cardapp.com.android.housingsociety.control.DataControl;
import android.content.Intent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 物业服务UI
 *
 * @author CardApp@ZuoQing
 *
 */
@EActivity(R.layout.activity_estate_service)
public class EstateServiceActivity extends BaseActivity {
	
	@ViewById(R.id.title)
	TextView title;
	@ViewById(R.id.logo)
	ImageView logo;
	@ViewById(R.id.list)
	ListView list;

	BaseAdapter mAdapter;
	List<NoticeClass> mDatas = new ArrayList<NoticeClass>();
	String estate;

	@Bean
	DataControl dataControl;

	@AfterViews
	void afterViews() {
		title.setText(getString(R.string.estateNotice));
		logo.setImageResource(R.drawable.home_icon);
		logo.setVisibility(View.VISIBLE);
		
		estate = myApplication.getString(R.string.estate);
		// get data in backgroud
		dataControl.getNoticeClass(estate);
	}

	/**
	 * 接收Bus数据后更新界面
	 * 
	 */
	@UiThread
	public void onEvent(NoticeClassFetchedEvent e) {
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
	 * 更新列表
	 */
	private void updateList() {
		if (mAdapter == null) {
			mAdapter = new EstateServiceAdapter(this, mDatas);
			list.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * ListView click event
	 * @param position
	 */
	@ItemClick(R.id.list)
	void itemClick(int position) {
		Intent intent = new Intent(this, NoticeListActivity_.class);
		// save extra
		NoticeClass noticeClass = mDatas.get(position);
		if (noticeClass != null && noticeClass.getNoticeCounts() > 0) {	// 判断是否有数据
			intent.putExtra(NoticeListActivity.ESTATESERVICEACTIVITYNOTICECLASS, noticeClass);
			startActivity(intent);
		} else {
			toast(getString(R.string.dataEmpty));
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

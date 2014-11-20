package cardapp.com.android.housingsociety.ui;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.viewpagerindicator.CirclePageIndicator;

import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.adapter.ImagesFragmentAdapter;
import cardapp.com.android.housingsociety.adapter.LifeMustDetailAdapter;
import cardapp.com.android.housingsociety.bean.Necessity;
import cardapp.com.android.housingsociety.bean.NecessityListItem;
import cardapp.com.android.housingsociety.bean.NecessityListItemFetchedEvent;
import cardapp.com.android.housingsociety.control.DataControl;
import cardapp.com.android.housingsociety.debug.AppLogger;
import cardapp.com.android.housingsociety.ui.common.GoogleMapActivity;
import cardapp.com.android.housingsociety.util.AppUtil;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 生活必备（第三级列表）
 *
 * @author CardApp@ZuoQing
 *
 */
@EActivity(R.layout.activity_life_must_detail)
public class LifeMustDetailActivity extends BaseActivity {

	@ViewById(R.id.list)
	ListView list;
	@ViewById(R.id.title)
	TextView title;
	@ViewById(R.id.call)
	Button call;
	@ViewById(R.id.add)
	Button add;
	@ViewById(R.id.map)
	Button map;
	//@ViewById(R.id.pager)
	ViewPager mPager; // 图片展示
	//@ViewById(R.id.indicator)
	CirclePageIndicator mIndicator; // 图片展示指示位置

	BaseAdapter mAdapter = null;
	List<NecessityListItem> mDatas = new ArrayList<NecessityListItem>();
	long necessityId = 36L;
	Necessity necessity;	 // 从父界面传入

	String[] imageUrls; // 图片url集合
	FragmentPagerAdapter imagesAdapter;

	@Bean
	DataControl dataControl;

	@AfterViews
	void afterViews() {
		// get NeccessityClassId
		Intent i = getIntent();
		if (i != null) {
			necessity = (Necessity) i
					.getSerializableExtra(LifeMustAClassActivity.LIFEMUSTACLASSACTIVITYNECCESSITYID);
		}
		if (necessity != null) {
			title.setText(AppUtil.getRightStringByLanguage(myApplication
					.getAppLan(), necessity.getNecessityChiName().trim(),
					necessity.getNecessityChiName().trim(), necessity
							.getNecessityEngName().trim()));
			necessityId = necessity.getNecessityId(); 
			// 图片墙
			initImagesShow();
			// backgroud get data
			dataControl.getNecessityListItems(necessityId);
		}
		
		if (getString(R.string.switchLanguage_comChi_value).equals(myApplication.getAppLan())) {	// 中文环境
			map.setBackgroundResource(R.drawable.seletc_button_map_zh);
			call.setBackgroundResource(R.drawable.seletc_button_call_zh);
			add.setBackgroundResource(R.drawable.seletc_button_add_zh);
		}
	}

	/**
	 * 初始化图片预览
	 */
	private void initImagesShow() {
		View v = View.inflate(this, R.layout.merge_images, null);
		mPager = (ViewPager) v.findViewById(R.id.pager);
		mIndicator = (CirclePageIndicator) v.findViewById(R.id.indicator);
		 String tStr = necessity.getImageList();
//		String tStr = "http://img0.bdstatic.com/img/image/shouye/sygjdl-9631142484.jpg,http://img0.bdstatic.com/img/image/shouye/bzmlmh-13545088266.jpg,http://img0.bdstatic.com/img/image/shouye/mxlbb-14130495854.jpg";
		if (!TextUtils.isEmpty(tStr)) {
			imageUrls = tStr.split(",");
			// 设置图片
			imagesAdapter = new ImagesFragmentAdapter(
					getSupportFragmentManager(), imageUrls);
			mPager.setAdapter(imagesAdapter);
			mIndicator.setViewPager(mPager);
			if (imageUrls.length > 1) {
				final float density = getResources().getDisplayMetrics().density;
				mIndicator.setBackgroundColor(0x00CCCCCC);
				mIndicator.setRadius(3 * density);
				mIndicator.setPageColor(0xFF000000);
				mIndicator.setFillColor(0xFFFFFFFF);
				mIndicator.setStrokeColor(0xFF000000);
				mIndicator.setStrokeWidth(0);
			} else {
				mIndicator.setVisibility(View.INVISIBLE);
			}
			list.addHeaderView(v);
		} else { // 无法加载图片
			toast(getString(R.string.loadImageFail));
		}
	}

	/**
	 * 接收Bus数据后更新界面
	 * 
	 * @param shopList
	 */
	@UiThread
	public void onEvent(NecessityListItemFetchedEvent e) {
		AppLogger.d("onEvent");
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
		if(mAdapter == null) {
			mAdapter = new LifeMustDetailAdapter(this, mDatas);
			list.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}
	}
	
	@Click({R.id.map, R.id.add, R.id.call})
	void click(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.map:
			String location = necessity.getLocation().trim();
			if (!TextUtils.isEmpty(location)) {
				intent = new Intent(this, GoogleMapActivity.class);
				intent.putExtra(GoogleMapActivity.GOOGLEMMAPLOCATION, location);
				intent.putExtra(
						GoogleMapActivity.GOOGLEMMAPLOCATIONTITLE,
						AppUtil.getRightStringByLanguage(
								myApplication.getAppLan(),
								necessity.getNecessityChiName(), necessity.getNecessityChiName(), necessity.getNecessityEngName()));
			} else {
				toast(getString(R.string.dataEmpty));
			}
			break;
		case R.id.add:
			if (getString(R.string.switchLanguage_comChi_value).equals(myApplication.getAppLan())) {
				add.setBackgroundResource(R.drawable.seletc_button_added_zh);
			} else {
				add.setBackgroundResource(R.drawable.seletc_button_added);
			}
			break;
		case R.id.call:
			String phone = necessity.getNecessityPhone();
			if (!TextUtils.isEmpty(phone)) {
				callPhone(phone);
			} else {
				toast(getString(R.string.dataEmpty));
			}
			break;
		}
		if (intent != null) {
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

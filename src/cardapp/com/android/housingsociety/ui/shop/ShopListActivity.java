package cardapp.com.android.housingsociety.ui.shop;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;


import android.content.Intent;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.adapter.AllShopUIAdapter;
import cardapp.com.android.housingsociety.bean.ShopItem;
import cardapp.com.android.housingsociety.bean.ShopItemListFetchedEvent;
import cardapp.com.android.housingsociety.cache.Cache;
import cardapp.com.android.housingsociety.control.DataControl;
import cardapp.com.android.housingsociety.debug.AppLogger;
import cardapp.com.android.housingsociety.ui.BaseActivity;

@EActivity(R.layout.activity_all_shop)
public class ShopListActivity extends BaseActivity {
	public static final String SHOPLISTACTIVITYCLASS = "cardapp.com.android.housingsociety.fragment.SHOPLISTACTIVITYCLASS";
	public static final int OTHER = 1;
	public static final int CATERING = 2;
	public static final int SHOPPING = 3;
	public static final int EDUCATION = 4;

	@ViewById(R.id.title)
	TextView title;
	@ViewById(R.id.list)
	ListView list;
	// param for visit server
	private long shopId = 0L;
	private long page = 1L;
	private long classId = 0L;
	private String estate = "";
	private String classEngName = "";
	private int type = 0; // 0.无classId 1.已有classId

	List<ShopItem> mDatas = new ArrayList<ShopItem>();
	BaseAdapter mAdapter;
	
	@Bean
	DataControl dataControl;
	String key = "";

	@AfterViews
	void afterViews() {
		Intent i = getIntent();
		// init title and classEngName
		if (i != null) {
			int type = i.getIntExtra(SHOPLISTACTIVITYCLASS,
					SHOPPING);
			switch (type) {
			case CATERING:
				title.setText(getString(R.string.catering));
				classEngName = "Catering";
				key = Cache.CATERING;
				break;
			case EDUCATION:
				title.setText(getString(R.string.educationInformation));
				classEngName = "Education Information";
				key = Cache.EDUINFO;
				break;
			case OTHER:
				title.setText(getString(R.string.other));
				classEngName = "Other";
				key = Cache.OTHER;
				break;
			case SHOPPING:
				title.setText(getString(R.string.shopping));
				classEngName = "Shopping";
				key = Cache.SHOPPING;
				break;
			}
		}
		AppLogger.v(classEngName);

		estate = myApplication.getString(R.string.estate);

		mAdapter = new AllShopUIAdapter(this, mDatas);
		list.setAdapter(mAdapter);
		dataControl.getShops(key, shopId, page, classId, estate, classEngName, type);
	}
	
	/**
	 * 接收Bus数据后更新界面
	 * @param shopList
	 */
	@UiThread
    public void onEvent(ShopItemListFetchedEvent shopList){
		AppLogger.i(""+shopList.getShopList().size());
		mDatas.clear();
		mDatas.addAll(shopList.getShopList());
		mAdapter.notifyDataSetChanged();
    }

	/**
	 * ListView Item Click Event
	 * 
	 * @param position
	 */
	@ItemClick(R.id.list)
	void itemClick(int position) {
		Intent intent = new Intent(this, ShopDetailActivity_.class);
		intent.putExtra(ShopDetailActivity.SHOPLISTACTIVITYITEMCLICKSHOPITEM, mDatas.get(position));
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

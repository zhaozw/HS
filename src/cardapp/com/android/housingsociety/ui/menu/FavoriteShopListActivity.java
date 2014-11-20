package cardapp.com.android.housingsociety.ui.menu;

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
import cardapp.com.android.housingsociety.ui.shop.ShopDetailActivity;
import cardapp.com.android.housingsociety.ui.shop.ShopDetailActivity_;
import cardapp.com.android.housingsociety.util.FavoriteManageUtil;

@EActivity(R.layout.activity_all_shop)
public class FavoriteShopListActivity extends BaseActivity {
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

	List<ShopItem> mDatas = null;
	BaseAdapter mAdapter;
	
	String key = "";
	@Bean
	FavoriteManageUtil favorityManage;

	@AfterViews
	void afterViews() {
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Intent i = getIntent();
		// init title and classEngName
		if (i != null) {
			int type = i.getIntExtra(SHOPLISTACTIVITYCLASS,
					SHOPPING);
			switch (type) {
			case CATERING:
				title.setText(getString(R.string.catering));
				key = Cache.FAV_CATERING;
				mDatas = favorityManage.getFavoEduInfo(key);
				break;
			case EDUCATION:
				title.setText(getString(R.string.educationInformation));
				key = Cache.FAV_EDUINFO;
				mDatas = favorityManage.getFavoEduInfo(key);
				break;
			case OTHER:
				title.setText(getString(R.string.other));
				key = Cache.FAV_OTHER;
				mDatas = favorityManage.getFavoEduInfo(key);
				break;
			case SHOPPING:
				title.setText(getString(R.string.shopping));
				key = Cache.FAV_SHOPPING;
				mDatas = favorityManage.getFavoEduInfo(key);
				break;
			}
		}

		if (mDatas != null) {
			mAdapter = new AllShopUIAdapter(this, mDatas);
			list.setAdapter(mAdapter);
		}
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
}

package cardapp.com.android.housingsociety.ui.menu;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.adapter.FavoriteAdapter;
import cardapp.com.android.housingsociety.adapter.ViewHolder;
import cardapp.com.android.housingsociety.ui.BaseActivity;

/**
 * 我的最爱UI
 *
 * @author CardApp@ZuoQing
 *
 */
@EActivity(R.layout.activity_favorite)
public class FavoriteActivity extends BaseActivity {

	@ViewById(R.id.title)
	TextView title;
	@ViewById(R.id.list)
	ListView list;
	Activity mActivity;

	List<String> items = new ArrayList<String>();

	@AfterViews
	void afterViews() {
		mActivity = this;

		// init data
		title.setText(getString(R.string.favourites));
		items.add(getString(R.string.estateManagement));
		items.add(getString(R.string.lifeMust));
		items.add(getString(R.string.other));
		items.add(getString(R.string.catering));
		items.add(getString(R.string.shopping));
		items.add(getString(R.string.educationInformation));

		list.setAdapter(new FavoriteAdapter(this, items));
	}

	@ItemClick(R.id.list)
	void itemClick(int position) {
		Intent intent = null;
		if (getString(R.string.other).equals(items.get(position))) { // other
			intent = new Intent(this, FavoriteShopListActivity_.class);
			intent.putExtra(FavoriteShopListActivity_.SHOPLISTACTIVITYCLASS,
					FavoriteShopListActivity_.OTHER);
		} else if (getString(R.string.catering).equals(items.get(position))) { // catering
			intent = new Intent(this, FavoriteShopListActivity_.class);
			intent.putExtra(FavoriteShopListActivity_.SHOPLISTACTIVITYCLASS,
					FavoriteShopListActivity_.CATERING);
		} else if (getString(R.string.shopping).equals(items.get(position))) { // shopping
			intent = new Intent(this, FavoriteShopListActivity_.class);
			intent.putExtra(FavoriteShopListActivity_.SHOPLISTACTIVITYCLASS,
					FavoriteShopListActivity_.SHOPPING);
		} else if (getString(R.string.educationInformation).equals(
				items.get(position))) { // educationInformation
			intent = new Intent(this, FavoriteShopListActivity_.class);
			intent.putExtra(FavoriteShopListActivity_.SHOPLISTACTIVITYCLASS,
					FavoriteShopListActivity_.EDUCATION);
		}
		if (intent != null) {
			startActivity(intent);
		}
	}
}

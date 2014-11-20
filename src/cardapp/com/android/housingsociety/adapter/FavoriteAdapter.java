package cardapp.com.android.housingsociety.adapter;

import java.util.List;

import cardapp.com.android.housingsociety.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 收藏界面的adapter
 *
 * @author CardApp@ZuoQing
 *
 */
public class FavoriteAdapter extends BaseAdapter {

	List<String> mItems = null;
	Context mContext; 
	
	public FavoriteAdapter(Context context, List<String> items) {
		mItems = items;
		mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_estate_service_list, null);
		}
		TextView title = ViewHolder.get(convertView, R.id.title);
		title.setText(mItems.get(position));
		
		return convertView;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}

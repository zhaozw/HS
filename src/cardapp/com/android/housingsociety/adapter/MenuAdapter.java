package cardapp.com.android.housingsociety.adapter;

import java.util.List;

import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.bean.MyMenuItem;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Menu Adapter
 *
 * @author CardApp@ZuoQing
 *
 */
public class MenuAdapter extends BaseAdapter {
	List<MyMenuItem> menus = null;		// save menu information
	Context mContext;
	
	public MenuAdapter(Context context, List<MyMenuItem> menus) {
		this.menus = menus;
		mContext = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (menus != null) {
			return menus.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_menu_adapter, null);
		}
		
		TextView title = ViewHolder.get(convertView, R.id.title);
		ImageView image = ViewHolder.get(convertView, R.id.image);
		
		MyMenuItem item = menus.get(position);
		title.setText(item.getTitle());
		int t = item.getImage();
		if (t == R.drawable.ic_launcher) {	// no image
			//image.setVisibility(View.INVISIBLE);
		} else {
			image.setImageResource(t);
		}
		
		return convertView;
	}

}

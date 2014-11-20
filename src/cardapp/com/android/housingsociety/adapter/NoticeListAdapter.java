package cardapp.com.android.housingsociety.adapter;

import java.util.List;

import cardapp.com.android.housingsociety.MyApplication;
import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.bean.NoticeItem;
import cardapp.com.android.housingsociety.util.AppUtil;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 生活必备 确定类型 适配器(二级列表)
 *
 * @author CardApp@ZuoQing
 *
 */
public class NoticeListAdapter extends BaseAdapter {
	List<NoticeItem> mDatas;
	Context mContext;
	MyApplication app;
	
	public NoticeListAdapter(Context context, List<NoticeItem> datas) {
		mContext = context;
		mDatas = datas;
		app = (MyApplication) context.getApplicationContext();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_estate_service_list, null);
		}
		NoticeItem nc = mDatas.get(position);
		TextView tv = ViewHolder.get(convertView, R.id.title);
		// 判断语言环境
		tv.setText(AppUtil.getRightStringByLanguage(app.getAppLan(), nc.getChiTitle(), nc.getChiTitle(), nc.getEngTitle()));
		
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mDatas != null) {
			return mDatas.size();
		} else {
			return 0;
		}
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

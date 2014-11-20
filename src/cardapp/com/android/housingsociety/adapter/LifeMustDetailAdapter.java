package cardapp.com.android.housingsociety.adapter;

import java.util.List;

import cardapp.com.android.housingsociety.MyApplication;
import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.bean.NecessityListItem;
import cardapp.com.android.housingsociety.util.AppUtil;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 生活必备 List 适配器(三级列表)
 *
 * @author CardApp@ZuoQing
 *
 */
public class LifeMustDetailAdapter extends BaseAdapter {
	List<NecessityListItem> mDatas;
	Context mContext;
	MyApplication app;

	public LifeMustDetailAdapter(Context context, List<NecessityListItem> datas) {
		mContext = context;
		mDatas = datas;
		app = (MyApplication) context.getApplicationContext();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NecessityListItem nc = mDatas.get(position);
		if (convertView == null) {
			convertView = View.inflate(mContext,
					R.layout.item_life_must_detail_adapter, null);
		}
		TextView left = ViewHolder.get(convertView, R.id.left);
		TextView right = ViewHolder.get(convertView, R.id.right);
		TextView price = ViewHolder.get(convertView, R.id.price);
		TextView oneLeft = ViewHolder.get(convertView, R.id.oneLeft);
		TextView oneRight = ViewHolder.get(convertView, R.id.oneRight);
		TextView twoLeft = ViewHolder.get(convertView, R.id.twoLeft);
		TextView twoRight = ViewHolder.get(convertView, R.id.twoRight);
		TextView threeLeft = ViewHolder.get(convertView, R.id.threeLeft);
		TextView threeRight = ViewHolder.get(convertView, R.id.threeRight);

		String lan = app.getAppLan();
		if (nc.getIsBigBus()) {
			left.setText(AppUtil.getRightStringByLanguage(lan,
					nc.getFirstLayerChiLeft(), nc.getFirstLayerChiLeft(),
					nc.getFirstLayerEngLeft()));
			right.setText(AppUtil.getRightStringByLanguage(lan,
					nc.getFirstLayerChiRight(), nc.getFirstLayerChiRight(),
					nc.getFirstLayerEngRight()));
			price.setText(nc.getPrice());
			oneLeft.setText(AppUtil.getRightStringByLanguage(lan,
					nc.getStepOneChiUp(), nc.getStepOneChiUp(),
					nc.getStepOneEngUp()));
			oneRight.setText(AppUtil.getRightStringByLanguage(lan,
					nc.getStepOneChiDown(), nc.getStepOneChiDown(),
					nc.getStepOneEngDown()));
			twoLeft.setText(AppUtil.getRightStringByLanguage(lan,
					nc.getStepTwoChiUp(), nc.getStepTwoChiUp(),
					nc.getStepTwoEngUp()));
			twoRight.setText(AppUtil.getRightStringByLanguage(lan,
					nc.getStepTwoChiDown(), nc.getStepTwoChiDown(),
					nc.getStepTwoEngDown()));
			threeLeft.setText(AppUtil.getRightStringByLanguage(lan,
					nc.getStepThreeChiUp(), nc.getStepThreeChiUp(),
					nc.getStepThreeEngUp()));
			threeRight.setText(AppUtil.getRightStringByLanguage(lan,
					nc.getStepThreeChiDown(), nc.getStepThreeChiDown(),
					nc.getStepThreeEngDown()));
		} else {
			left.setText(AppUtil.getRightStringByLanguage(lan,
					nc.getFirstLayerChiLeft(), nc.getFirstLayerChiLeft(),
					nc.getFirstLayerEngLeft()));
			right.setText(AppUtil.getRightStringByLanguage(lan,
					nc.getStepOneChiUp(), nc.getStepOneChiUp(),
					nc.getStepOneEngUp()));
			oneLeft.setText(AppUtil.getRightStringByLanguage(lan,
					nc.getFirstLayerChiRight(), nc.getFirstLayerChiRight(),
					nc.getFirstLayerEngRight()));
			oneLeft.setSingleLine(false);

			price.setText(nc.getPrice());
			oneRight.setText(AppUtil.getRightStringByLanguage(lan,
					nc.getStepOneChiDown(), nc.getStepOneChiDown(),
					nc.getStepOneEngDown()));
			twoLeft.setText(AppUtil.getRightStringByLanguage(lan,
					nc.getStepTwoChiUp(), nc.getStepTwoChiUp(),
					nc.getStepTwoEngUp()));
			twoRight.setText(AppUtil.getRightStringByLanguage(lan,
					nc.getStepTwoChiDown(), nc.getStepTwoChiDown(),
					nc.getStepTwoEngDown()));
			threeLeft.setText(AppUtil.getRightStringByLanguage(lan,
					nc.getStepThreeChiUp(), nc.getStepThreeChiUp(),
					nc.getStepThreeEngUp()));
			threeRight.setText(AppUtil.getRightStringByLanguage(lan,
					nc.getStepThreeChiDown(), nc.getStepThreeChiDown(),
					nc.getStepThreeEngDown()));
		}

		return convertView;
	}

	@Override
	public int getCount() {
		if (mDatas != null) {
			return mDatas.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}

package cardapp.com.android.housingsociety.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import cardapp.com.android.housingsociety.MyApplication;
import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.bean.NecessityClass;
import cardapp.com.android.housingsociety.util.AppUtil;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 生活必备适配器 (一级列表)
 *
 * @author CardApp@ZuoQing
 *
 */
public class LifeMustAdapter extends BaseAdapter {
	List<NecessityClass> mDatas;
	Context mContext;
	MyApplication app;

	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public LifeMustAdapter(Context context, List<NecessityClass> datas) {
		mContext = context;
		mDatas = datas;
		app = (MyApplication) context.getApplicationContext();

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.mtr)
				.showImageForEmptyUri(R.drawable.mtr)
				.showImageOnFail(R.drawable.mtr).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext,
					R.layout.item_life_must_adapter, null);
		}
		NecessityClass nc = mDatas.get(position);
		TextView tv = ViewHolder.get(convertView, R.id.title);
		ImageView image = ViewHolder.get(convertView, R.id.image);
		// 判断语言环境
		tv.setText(AppUtil.getRightStringByLanguage(app.getAppLan(),
				nc.getNecessityClassChiName(), nc.getNecessityClassChiName(),
				nc.getNecessityClassEngName()));
		// 图片处理
		ImageLoader.getInstance().displayImage(nc.getNecessityClassImage(),
				image, options, animateFirstListener);

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

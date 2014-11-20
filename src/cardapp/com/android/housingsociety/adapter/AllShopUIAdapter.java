package cardapp.com.android.housingsociety.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import cardapp.com.android.housingsociety.MyApplication;
import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.bean.ShopItem;
import cardapp.com.android.housingsociety.util.AppUtil;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 与商家相关的四个界面Adapter: 其他，餐饮，购物，教育资讯
 *
 * @author CardApp@ZuoQing
 *
 */
public class AllShopUIAdapter extends BaseAdapter {
	List<ShopItem> mDatas;
	Context mContext;
	MyApplication app;

	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public AllShopUIAdapter(Context context, List<ShopItem> datas) {
		mContext = context;
		mDatas = datas;
		app = (MyApplication) context.getApplicationContext();

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.mtr)
				.showImageForEmptyUri(R.drawable.mtr)
				.showImageOnFail(R.drawable.mtr).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(10)).build();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext,
					R.layout.item_all_shop_adapter, null);
		}
		ShopItem si = mDatas.get(position);
		TextView tv = ViewHolder.get(convertView, R.id.title);
		ImageView image = ViewHolder.get(convertView, R.id.image);
		// 判断语言环境
		tv.setText(AppUtil.getRightStringByLanguage(app.getAppLan(),
				si.getSimChiName(), si.getChiName(), si.getEngName()));
		// 图片处理
		ImageLoader.getInstance().displayImage(si.getShopIconPath(), image,
				options, animateFirstListener);

		return convertView;
	}

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
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

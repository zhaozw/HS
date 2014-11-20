package cardapp.com.android.housingsociety.adapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import cardapp.com.android.housingsociety.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * unuse
 * 展示图片Adapter
 *
 * @author CardApp@ZuoQing
 *
 */
public class ImagesAdapter extends BaseAdapter {
		String[] mList = null;
		Context mContext;
		
		DisplayImageOptions options;
		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
		
		public ImagesAdapter(Context context, String[] list) {
			this.mContext = context;
			this.mList = list;
			
			options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.mtr)
			.showImageForEmptyUri(R.drawable.mtr)
			.showImageOnFail(R.drawable.mtr).cacheInMemory(true)
			.cacheOnDisk(true).considerExifParams(true)
			.displayer(new RoundedBitmapDisplayer(10))
			.build();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(mContext, R.layout.item_images_adapter, null);
			}
			ImageView image = (ImageView) convertView;
			// display imange
			ImageLoader.getInstance().displayImage(mList[position], image,
					options, animateFirstListener);
			
			return convertView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (mList == null) {
				return 0;
			} else {
				return mList.length;
			}
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		
}

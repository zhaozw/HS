package cardapp.com.android.housingsociety.fragment;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.debug.AppLogger;
import cardapp.com.android.housingsociety.ui.common.ShowImagesActivity;
import cardapp.com.android.housingsociety.ui.common.ShowImagesActivity_;
import cardapp.com.android.housingsociety.util.MyNetUtil;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 显示一张图片的Fragment
 *
 * @author CardApp@ZuoQing
 *
 */
public final class ImageFragment extends Fragment implements OnClickListener {
    private static final String KEY_CONTENT = "TestFragment:Content";
    private String url = "???";
    private String[] urls = null;
	private FragmentActivity mActivity;
	
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public static ImageFragment newInstance(String url, String[] urls) {
        ImageFragment fragment = new ImageFragment();

        fragment.url = url;
        fragment.urls = urls;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();
        
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            url = savedInstanceState.getString(KEY_CONTENT);
        }
        
        options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.mtr)
		.showImageForEmptyUri(R.drawable.mtr)
		.showImageOnFail(R.drawable.mtr).cacheInMemory(true)
		.cacheOnDisk(true).considerExifParams(true)
		.imageScaleType(ImageScaleType.NONE_SAFE)
		.build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	ImageView image = (ImageView) inflater.inflate(R.layout.item_images_adapter, null);

    	ImageLoader.getInstance().displayImage(url, image, options, animateFirstListener);
    	image.setOnClickListener(this);
    	
        return image;
    }

    /**
     * 图片点击事件，查看大图
     */
	@Override
	public void onClick(View v) {
		AppLogger.d("image onclick");
		if (MyNetUtil.serverEnable(mActivity)) {
			Intent intent = new Intent(mActivity, ShowImagesActivity_.class);
			// put Extra
			intent.putExtra(ShowImagesActivity.IMAGELIST, urls);
			mActivity.startActivity(intent);
		} else {
			Toast.makeText(mActivity, getString(R.string.visitServerError), Toast.LENGTH_SHORT).show();
		}
	}

	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, url);
    }
}

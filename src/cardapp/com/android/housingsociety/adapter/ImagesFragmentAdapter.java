package cardapp.com.android.housingsociety.adapter;

import cardapp.com.android.housingsociety.fragment.ImageFragment;

import com.viewpagerindicator.IconPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ImagesFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter{
	String[] urls = null;

	public ImagesFragmentAdapter(FragmentManager fm, String[] urls) {
		super(fm);
		this.urls = urls;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return ImageFragment.newInstance(urls[arg0], urls);
	}

	@Override
	public int getCount() {
		if (urls == null) {
			return 0;
		} else {
			return urls.length;
		}
	}

	@Override
	public int getIconResId(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

}

package cardapp.com.android.housingsociety.ui.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import ru.truba.touchgallery.GalleryWidget.GalleryViewPager;
import ru.truba.touchgallery.GalleryWidget.UrlPagerAdapter;
import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.ui.BaseActivity;

/**
 * 展示图片集合通过UIR
 *
 * @author CardApp@ZuoQing
 *
 */
@EActivity(R.layout.activity_show_images)
public class ShowImagesActivity extends BaseActivity {
	public static final String IMAGELIST = "IMAGELIST";

	@ViewById(R.id.viewer)
	GalleryViewPager mViewPager;
	@ViewById(R.id.title)
	TextView title;

	List<String> items = new ArrayList<String>();

	@AfterViews
	void afterViews() {
		// get Extra
		Intent intent = getIntent();
		String[] urls = null;
		if (intent != null) {
			urls = intent.getStringArrayExtra(IMAGELIST);
		}
		if (urls != null && urls.length != 0) {
			// add data
			Collections.addAll(items, urls);
		}

		if (items.size() > 0) {
			title.setText("1/" + items.size());
			UrlPagerAdapter pagerAdapter = new UrlPagerAdapter(this, items);
			mViewPager.setOffscreenPageLimit(3);
			mViewPager.setAdapter(pagerAdapter);
		} else {
			Toast.makeText(this, getString(R.string.dataEmpty),
					Toast.LENGTH_SHORT).show();
		}

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				title.setText((arg0 + 1) + "/" + items.size());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

}

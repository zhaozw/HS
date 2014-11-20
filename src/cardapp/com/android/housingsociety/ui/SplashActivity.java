package cardapp.com.android.housingsociety.ui;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.control.DataControl;
import cardapp.com.android.housingsociety.debug.AppLogger;
import cardapp.com.android.housingsociety.net.AppService;
import cardapp.com.android.housingsociety.net.impl.AppServiceImpl;
import cardapp.com.android.housingsociety.util.UserManageUtil;
import android.content.Intent;
import android.widget.ImageView;

/**
 * App welcome UI
 *
 * @author CardApp@ZuoQing
 *
 */
@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {
	private static final int FADEINTIMESHOWLOGO = 1000;
	private static final long DELAYTIMEGOHOME = 1000;

	@ViewById(R.id.image)
	ImageView image;
	@Bean
	UserManageUtil userManage;
	@Bean
	DataControl dataControl;

	@AfterViews
	void afterViews() {
		// init something
		backgroudAddTagToJPushServer();
		// Set Animate to image
		FadeInBitmapDisplayer.animate(image, FADEINTIMESHOWLOGO);
		
		Intent intent = null;
		if (userManage.whetherLogin()) {	// Have User login
			intent = new Intent(this, MainActivity2_.class);
			go2OtherUI(intent);
		} else {	// go to Login UI
			intent = new Intent(this, MainActivity2_.class);
//			intent = new Intent(this, LoginActivity_.class);
			go2OtherUI(intent);
		}
		dataControl.doVisit(1, 0, 0);	//  visit Server
	}
	
	/**
	 * 添加标签到JPush
	 */
	@Background
	void backgroudAddTagToJPushServer() {
		AppServiceImpl appService = new AppServiceImpl();
		String estateId = appService.getEstateId(myApplication.getString(R.string.estate));
//		AppService service = new AppServiceImpl();
//		String estateId = service.getEstateId("BoShekMansion");
		if (estateId == null || "0".equals(estateId) || "-1".equals(estateId)) { 	// 不设置标签
			AppLogger.i("获取Estateid失败");
		} else {	// estateid 作为标签
			// set tag to JPush
			Set<String> tags = new LinkedHashSet<String>();
			tags.add(estateId);
			tags = JPushInterface.filterValidTags(tags);
			JPushInterface.setTags(this, tags, new TagAliasCallback() {
				
				@Override
				public void gotResult(int arg0, String arg1, Set<String> arg2) {
					if (arg0 == 0) {	// 调用成功
						Iterator<String> i = arg2.iterator();
						while (i.hasNext()){
							AppLogger.i("设置标签成功"+i.next());
						}
					} else {
						AppLogger.i("设置标签失败");
					}
				}
			});
		}
	}
	
	@UiThread(delay=DELAYTIMEGOHOME)
	void go2OtherUI(Intent intent) {
		startActivity(intent);
		finish();
	}
}

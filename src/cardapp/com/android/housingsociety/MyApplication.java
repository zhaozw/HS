package cardapp.com.android.housingsociety;

import java.util.Locale;

import org.androidannotations.annotations.EApplication;

import cn.jpush.android.api.JPushInterface;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import cardapp.com.android.housingsociety.debug.AppLogger;
import cardapp.com.android.housingsociety.ui.menu.SettingsActivity2;
import cardapp.com.android.housingsociety.util.AppUtil;
import de.greenrobot.event.EventBus;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * Application for save same variable and init app
 *
 * @author CardApp@ZuoQing
 *
 */
@EApplication
public class MyApplication extends Application {
	public static final EventBus BUS = new EventBus(); // 应用总线

	String appLan = ""; // app language , "auto" Auto-determinate Language,
							// "zh" Simple Chiness, "zh-HK" Tradiional Chinese,
							// "en" English
	String sysLanguage = ""; // Android OS language
	String sysCountry = ""; // Android OS Country
	
	@Override
	public void onCreate() {
		super.onCreate();
		AppLogger.i("MyApplication oncreate");

		// init params 
		sysLanguage = Locale.getDefault().getLanguage();
		sysCountry = Locale.getDefault().getCountry();
		// init language
		initAppLanguage();
		
		// init universal-image-loader configer
		initImageLoader(getApplicationContext());
		
		// 推送初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
	}
	
    /**初始化图片加载类配置信息**/
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
            .threadPriority(Thread.NORM_PRIORITY - 2)//加载图片的线程数
            .denyCacheImageMultipleSizesInMemory() //解码图像的大尺寸将在内存中缓存先前解码图像的小尺寸。
            .discCacheFileNameGenerator(new Md5FileNameGenerator())//设置磁盘缓存文件名称
            .tasksProcessingOrder(QueueProcessingType.LIFO)//设置加载显示图片队列进程
            .writeDebugLogs() // Remove for release app
//            .memoryCacheSizePercentage(availableMemoryPercent) 	// 限制缓存文件数量
//            .memoryCacheSize(memoryCacheSize)	// 限制缓存文件最大尺寸
            .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

	/**
	 * init App Language
	 */
	private void initAppLanguage() {
		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(this);
		String v = sharedPref.getString(
				SettingsActivity2.KEY_PREF_SWITCH_LANGUAGE, "");
		if (!TextUtils.isEmpty(v)) { // User set the language once
			appLan = v;
			// update app language
		} else {	// 跟随系统语言
			if (sysLanguage.equals("zh")) {
				appLan = "zh_HK";
			} else {
				appLan = "en";
			}
		}
		AppUtil.updateLanguage(getResources(), appLan);
	}

	/**
	 * Set App language
	 * 
	 * @param lan
	 */
	public void setAppLan(String lan) {
		appLan = lan;
	}

	/**
	 * get App language
	 * 
	 * @return
	 */
	public String getAppLan() {
		return appLan;
	}

	public String getSysLanguage() {
		return sysLanguage;
	}

	public void setSysLanguage(String sysLanguage) {
		this.sysLanguage = sysLanguage;
	}

	public String getSysCountry() {
		return sysCountry;
	}

	public void setSysCountry(String sysCountry) {
		this.sysCountry = sysCountry;
	}

}

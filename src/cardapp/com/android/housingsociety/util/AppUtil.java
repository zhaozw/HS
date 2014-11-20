package cardapp.com.android.housingsociety.util;

import java.util.Locale;

import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.debug.AppLogger;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * App Util function: 1. 更新App语言 void updateLanguage(Resources , String) 2.
 * 判断是否是第一次启动 public static boolean isFirstUseApp(Context context)
 *
 * @author CardApp@ZuoQing
 *
 */
public class AppUtil {

	/**
	 * 获取当前语言环境对应的文字
	 * 
	 * @param lan
	 *            语言环境，zh_HK, en
	 * @param simChi
	 *            简体中文， null则不会被赋值给res
	 * @param traChi
	 *            繁体中文
	 * @param en
	 *            英语
	 * @return 
	 */
	public static String getRightStringByLanguage(String lan, String simChi,
			String traChi, String en) {
		//AppLogger.d(lan + ":" + simChi + ":" + traChi + ":" + en);
		String res = null;

		// 根据系统英语，默认是繁体，如果系统是简体中文或者是英语(什么英语都可以)则相应更改，其他情况默认繁体
		if ("".equals(lan)) {
			String sysLanguage = Locale.getDefault().getLanguage();
			if ("en".equals(sysLanguage)) { // English
				lan = "en";
			} else { // simple
				lan = "zh_HK";
			}
		}
		// 判断语言并赋值res，res仍然有可能是空
		if ("en".equals(lan)) {
			res = en;
		} else if ("zh_HK".equals(lan)) {
			res = traChi;
		} else if ("zh".equals(lan)) {
			res = simChi;
		}
		// 如果res仍然是空， 按照traChi/en/simChi顺序赋值
		if (TextUtils.isEmpty(res)) { 
			res = "";
			if (!TextUtils.isEmpty(en)) {
				res = en;
				return res;
			} else if (!TextUtils.isEmpty(traChi)) {
				res = traChi;
				return res;
			} else if (!TextUtils.isEmpty(simChi)) {
				res = simChi;
				return res;
			} 
		}

		return res;
	}

	/**
	 * 更新App语言
	 * 
	 * @param connectionPref
	 * @param v
	 */
	public static void updateLanguage(Resources resources, String v) {
		AppLogger.i("Language:" + v);
		if (TextUtils.isEmpty(v) || resources == null) {
			return;
		}
		Configuration config = resources.getConfiguration();// 获得设置对象
		if (resources.getString(R.string.switchLanguage_comChi_value).equals(v)) {
			config.locale = Locale.TRADITIONAL_CHINESE; // traditional chinese
		} else if (resources.getString(R.string.switchLanguage_eng_value).equals(v)) {
			config.locale = Locale.ENGLISH; // English
		} 
		resources.updateConfiguration(config, resources.getDisplayMetrics());
	}

	/**
	 * Whether first use app
	 */
	public static boolean isFirstUseApp(Context context) {
		boolean res = true;

		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(context);
		res = sharedPref.getBoolean("FIRSTOPENTHEAPP", true);
		sharedPref.edit().putBoolean("FIRSTOPENTHEAPP", false).commit(); // remember
																			// commit

		return res;
	}
}

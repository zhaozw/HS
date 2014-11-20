package cardapp.com.android.housingsociety.ui.menu;

import java.util.Locale;

import cn.jpush.android.api.JPushInterface;
import cardapp.com.android.housingsociety.MyApplication;
import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.util.AppUtil;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Setting UI(Use System Setting Style)
 *
 * @author CardApp@ZuoQing
 *
 */
public class SettingsActivity2 extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {

	public static final String KEY_PREF_FONT_SIZE = "pref_fontSize";
	public static final String KEY_PREF_SWITCH_LANGUAGE = "pref_switchLanguage";
	public static final String KEY_PREF_CLEAN_CACHE = "pref_cleanCache";
	public static final String KEY_PREF_VERSION_NUM = "pref_versionNum";
	public static final String KEY_PREF_DISCLAIMER = "pref_disclaimer";
	public static final String KEY_PREF_ABOUT_CIC = "pref_aboutCIC";
	public static final String KEY_PREF_FEEDBACK = "pref_feedback";
	public static final String KEY_PREF_GRADECOMMENT = "pref_gradeComment";
	public static final String KEY_PREF_MESSAGEHINT = "pref_messageHint";	// 推送消息
	
	Bundle s; 
	MyApplication myApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.s = savedInstanceState;  
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		addPreferencesFromResource(R.xml.preferences);
		// init myApplication
		myApplication = (MyApplication) getApplication();

		// init acitivty view
		initView();
	}
	
	@Override
	protected void onStart() {
		preventRefreshUIByOSLanguageChange();
		super.onStart();
	}

	/**
	 * init Setting param 
	 */
	void initView() {
		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(this);
		String v = null;
		Preference connectionPref = null;
		// 字体
		v = sharedPref.getString(KEY_PREF_FONT_SIZE, "");
		connectionPref = findPreference(KEY_PREF_FONT_SIZE);
		updateFontSize(v, connectionPref);
		// 语言
		v = sharedPref.getString(KEY_PREF_SWITCH_LANGUAGE, "");
		connectionPref = findPreference(KEY_PREF_SWITCH_LANGUAGE);
		updateLanguageSummary(v, connectionPref);

		// Clean Cache

		// Set Version code
		try {
			String versionName = getPackageManager().getPackageInfo(
					"cardapp.com.android.housingsociety", 0).versionName;
			connectionPref = findPreference(KEY_PREF_VERSION_NUM);
			connectionPref.setSummary(versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Calculate Cache
		
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		String key = preference.getKey();
		String message = null;
		String title = null;
		if (key.equals(KEY_PREF_DISCLAIMER)) {		// Disclaimer
			title = getString(R.string.disclaimer);
			message = getString(R.string.disclaimerDetail);
		} else if (key.equals(KEY_PREF_ABOUT_CIC)) {		// about cic
			title = getString(R.string.aboutCIC);
			message = getString(R.string.aboutCICDetail);
		} else if (key.equals(KEY_PREF_FEEDBACK)) {		// feedback
			// sedn Email
			Intent data=new Intent(Intent.ACTION_SENDTO);    
			data.setData(Uri.parse("mailto:info@cardapp.com.hk"));    
			data.putExtra(Intent.EXTRA_SUBJECT, "Feedback To HousingSociety");    
			data.putExtra(Intent.EXTRA_TEXT, "You want suggest to us: \n");    
			startActivity(data);
		} else if (key.equals(KEY_PREF_GRADECOMMENT)) {		// grade ande comment
			Toast.makeText(this, "Will Go to Google Play", 0).show();
			// go to Google Play 
		} 
		// Show Message By Dialog
		if (!TextUtils.isEmpty(message) && !TextUtils.isEmpty(title)) {
			AlertDialog.Builder dialog = new Builder(this);
			dialog.setMessage(message);
			dialog.setTitle(title);
			dialog.create().show();
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		Preference connectionPref = null;
		String v = null;
		if (key.equals(KEY_PREF_FONT_SIZE)) {		// 字体大小
			connectionPref = findPreference(key);
			// Set summary to be the user-description for the selected value
			v = sharedPreferences.getString(key, "");
			updateFontSize(v, connectionPref);
		} else if (key.equals(KEY_PREF_SWITCH_LANGUAGE)) {	// 切换语言
			v = sharedPreferences.getString(key, "");
			connectionPref = findPreference(key);
			// update app language
			AppUtil.updateLanguage(getResources(), v);
			// change app language
			myApplication.setAppLan(v);
			// refresh current ui language
			onCreate(s); 
		} else if (key.equals(KEY_PREF_MESSAGEHINT)) {	// 推送消息
			CheckBoxPreference cbp = (CheckBoxPreference) findPreference(key);
			if (cbp.isChecked()) {	// 确认接送推送
				if (JPushInterface.isPushStopped(this)){
					JPushInterface.resumePush(this);
				}
			} else {	// 停止推送消息
				if (!JPushInterface.isPushStopped(this)){
					JPushInterface.stopPush(this);
				}
			}
		}
	}

	/**
	 * 防止系统语言改变导致界面语言改变
	 * @param lan
	 */
	private void preventRefreshUIByOSLanguageChange() {
		// judeg whether Android OS has change Language and App language Don't
		// auto-determinate by Android OS
		// if Android OS Language change then update app self Language
		String language = Locale.getDefault().getLanguage();
		String country = Locale.getDefault().getCountry();
		if (!myApplication.getSysLanguage().equals(language)
				|| !myApplication.getSysCountry().equals(country)) {
			String lan = myApplication.getAppLan();
			// update params
			myApplication.setSysLanguage(language);
			myApplication.setSysCountry(country);
			// update app language
			AppUtil.updateLanguage(getResources(), lan);
			// refresh UI
			this.finish();
			this.startActivity(new Intent(this, this.getClass()));
		}
	}

	/**
	 * update Language Summary
	 * @param v
	 * @param connectionPref
	 */
	private void updateLanguageSummary(String v, Preference connectionPref) {
		if (getString(R.string.switchLanguage_comChi_value).equals(v)) {
			connectionPref.setSummary(getString(R.string.switchLanguage_comChi));
		} else if (getString(R.string.switchLanguage_eng_value).equals(v)) {
			connectionPref.setSummary(getString(R.string.switchLanguage_eng));
		}
	}

	/**
	 * 更新字体大小
	 * 
	 * @param connectionPref
	 * @param v
	 */
	private void updateFontSize(String v, Preference connectionPref) {
		if ("bigFontSize".equals(v)) {
			connectionPref.setSummary(getString(R.string.fontSize_bigFont));
		} else if ("middleFontSize".equals(v)) {
			connectionPref.setSummary(getString(R.string.fontSize_middleFont));
		} else if ("smallFontSize".equals(v)) {
			connectionPref.setSummary(getString(R.string.fontSize_smallFont));
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:		// finish this activity
			this.finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
        JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
        JPushInterface.onPause(this);
	}
}

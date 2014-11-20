package cardapp.com.android.housingsociety.util;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import android.R.bool;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import cardapp.com.android.housingsociety.MyApplication;
import cardapp.com.android.housingsociety.bean.User;
import cardapp.com.android.housingsociety.cache.Cache;

/**
 * manage user util
 * 	function:
 * 		1. 判断是否已经登录了用户；
 * 		2. 设置用户登录状态（已登录，或者未登录状态）；
 * 		3. 保存/获取用户信息;
 * 		4. 保存/获取用户名；
 * 		5. 保存/获取用户密码；
 *
 * @author CardApp@ZuoQing
 *
 */
@EBean(scope = EBean.Scope.Singleton)
public class UserManageUtil {
	private static String KEYISLOGIN = "USERMANAGEUTIL_KEYISLOGIN";
	private static String KEYUSERNAME = "USERMANAGEUTIL_KEYUSERNAME";
	private static String KEYUSERPWD = "USERMANAGEUTIL_KEYUSERPWD";
	private static String KEYUSERBEAN = "USERMANAGEUTIL_KEYUSERBEAN";
	private static String AUTOLOGIN = "USERMANAGEUTIL_AUTOLOGIN";
	private static String REMENBERPWD = "USERMANAGEUTIL_REMENBERPWD";

	SharedPreferences sp;
	@App
	MyApplication app;
	@Bean
	Cache cache;

	@AfterInject
	void afterInject() {
		sp = PreferenceManager.getDefaultSharedPreferences(app);
	}

	/**
	 * whether user is login
	 * 
	 * @return true, user has login
	 */
	public boolean whetherLogin() {
		return sp.getBoolean(KEYISLOGIN, false);
	}

	/**
	 * Setting Login State
	 * 
	 * @param flag
	 *            true:login success; false, logout
	 */
	public void setLoginState(boolean flag) {
		sp.edit().putBoolean(KEYISLOGIN, flag).commit();
	}

	/**
	 * save user bean and set user login state is true and save user name
	 * 
	 * @param user
	 */
	public void saveUser(User user) {
		cache.put(KEYUSERBEAN, user);
		setLoginState(true);
		saveUserName(user.getUserName());
	}

	/**
	 * get user bean, if login state is false return null
	 * 
	 * @return
	 */
	public User getUser() {
		if (!whetherLogin()) { // User don't login
			return null;
		}
		return cache.get(KEYUSERBEAN, User.class);
	}

	// Auto Login
	public boolean isAutoLogin() {
		return sp.getBoolean(AUTOLOGIN, false);
	}

	public void setAutoLoginState(boolean flag) {
		sp.edit().putBoolean(AUTOLOGIN, flag).commit();
	}

	// Remember PWd
	public boolean isRememberPwd() {
		return sp.getBoolean(REMENBERPWD, false);
	}

	public void setRememberPwdState(boolean flag) {
		sp.edit().putBoolean(REMENBERPWD, flag).commit();
	}

	// UserName
	public void saveUserName(String userName) {
		sp.edit().putString(KEYUSERNAME, userName).commit();
	}

	public String getUserName() {
		return sp.getString(KEYUSERNAME, "UserName");
	}

	// password
	public void saveUserPwd(String pwd) {
		sp.edit().putString(KEYUSERPWD, pwd).commit();
	}

	public String getUserPwd() {
		return sp.getString(KEYUSERPWD, "");
	}
}

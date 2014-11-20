package cardapp.com.android.housingsociety.ui;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.google.gson.Gson;

import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.bean.User;
import cardapp.com.android.housingsociety.debug.AppLogger;
import cardapp.com.android.housingsociety.net.AppService;
import cardapp.com.android.housingsociety.net.impl.AppServiceImpl;
import cardapp.com.android.housingsociety.util.MyNetUtil;
import cardapp.com.android.housingsociety.util.MyStringUtil;
import cardapp.com.android.housingsociety.util.UserManageUtil;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

/**
 * Login interface
 * 
 * @author CardApp@ZuoQing
 * 
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

	@ViewById(R.id.name)
	EditText name;
	@ViewById(R.id.pwd)
	EditText pwd;
	@ViewById(R.id.login)
	Button login;
	@ViewById(R.id.rememberPwd)
	CheckBox rememberPwd;
	@ViewById(R.id.autoLogin)
	CheckBox autoLogin;
	@Bean
	UserManageUtil userManage;

	@AfterViews
	void afterViews() {
		ActionBar bar = getSupportActionBar();
		bar.hide();
		
		// 初始化是否记住用户名和自动登录
		if (userManage.isRememberPwd()) {
			
		}
		if (userManage.isAutoLogin()) {
			
		}
		autoLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					rememberPwd.setChecked(true);
				}
			}
		});
	}

	@Click(R.id.login)
	public void login(View v) {
		if (v.getId() == R.id.login) { // login button click event
			// Check network whether can connect to the server
			if (!MyNetUtil.serverEnable(this)) {
				toast(getString(R.string.visitServerError));
				return;
			}
			// get data
			String username = name.getText().toString().trim();
			String password = pwd.getText().toString().trim();
			AppLogger.d("username:" + username);
			AppLogger.d("password:" + password);
			// check data
			if (TextUtils.isEmpty(username)) {
				toast(getString(R.string.cannotEmptyUserName));
				return;
			}
			if (TextUtils.isEmpty(password)) {
				toast(getString(R.string.cannotEmptyPwd));
				return;
			}
			// invaild login button
			login.setEnabled(false);
			showLoadingProgressDialog();
			// backgourd thread to login
			backgroudLogin(username, password);
		}
	}

	/**
	 * login on backgroud thread
	 * 
	 * @param username
	 * @param pwd
	 */
	@Background
	void backgroudLogin(String username, String password) {
		AppLogger.d("Backgroud Login:");

		AppService appService = new AppServiceImpl();
		String estate = myApplication.getString(R.string.estate);
//		String estate = "BoShekMansion";

		// get salt
		String salt = appService.getUserLoginInfo(username);
		// get esatateid
		String estateId = appService.getEstateId(estate);
		// construction hashedValue by MD5 encryptings
		String hashedValue = MyStringUtil.encryptingByMD5For32BitUpper(salt
				+ ":" + username + ":" + password);
		// visit Server to get result
		String result = appService.userLogin(username, hashedValue, estateId);
AppLogger.i("result:" + result);
		// deal result
		if (TextUtils.isEmpty(result)) {
			loginFail(getString(R.string.visitServerError));
			return;
		}
		Gson gson = new Gson();
		User user = null;
		try {
			user = gson.fromJson(result, User.class);
		} catch (Exception e) {
			e.printStackTrace();
			AppLogger.v("User Json cannot parser.");
			loginFail(getString(R.string.userNameOrPwdError)+"");
			return;
		}
		// save user and go Home
		if (user != null && user.getLoginSuccess()) {
			// save data
			if (rememberPwd.isChecked()) {
				userManage.saveUserName(username);
			}
			if (autoLogin.isChecked()) {
				userManage.saveUserPwd(password);
			}
			// user estateid = estate + estateId
			user.setEstateId(estate + user.getEstateId());
			userManage.saveUser(user);
			loginSuccess();
		} else {
			loginFail(getString(R.string.userNameOrPwdError)+"..");
		}
	
	}

	/**
	 * login fail to print Toast
	 */
	@UiThread
	void loginFail(String str) {
		dismissProgressDialog();
		toast(str);
		login.setEnabled(true);
	}

	/**
	 * login success to go home
	 */
	@UiThread
	void loginSuccess() {
		dismissProgressDialog();
		AppLogger.d("Login Success, Go Home:");
		// finish this activity
		this.finish();
		// start Home actiivty
		Intent intent = new Intent(this, MainActivity2_.class);
		startActivity(intent);
	}
}

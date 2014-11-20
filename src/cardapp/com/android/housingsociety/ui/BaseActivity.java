package cardapp.com.android.housingsociety.ui;

import java.util.Locale;

import cn.jpush.android.api.JPushInterface;
import cardapp.com.android.housingsociety.MyApplication;
import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.debug.AppLogger;
import cardapp.com.android.housingsociety.util.AppUtil;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

/**
 * the parent activity of other activity function: 
 *	function:
 *		1. hide action bar;
 *		2. prevent Android System Language Changed
 *		3. Change UI Language follow App Setting
 *
 *	Some Constant
 *		1. MyApplication myApplication;
 *	
 * @author CardApp@ZuoQing
 *
 */
public class BaseActivity extends ActionBarActivity {
	Bundle s;
	String currentLan = ""; // current Activity language
	public MyApplication myApplication;
	public Activity mActivity;
	public ActionBar actionBar;
	
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// hide actionBar
		actionBar = getSupportActionBar();
		actionBar.hide();
		
		this.s = savedInstanceState;

		// init myApplication
		myApplication = (MyApplication) this.getApplication();
		mActivity = this;
		// init currentLan
		currentLan = myApplication.getAppLan();
	}

	@Override
	protected void onStart() {
		super.onStart();
        
		// judge whether change current Activity language by finish self when
		// setting language change
		String lan = myApplication.getAppLan();
		AppLogger.i("#########" + currentLan + ":" + lan);
		if (!currentLan.equals(lan)) {
			this.finish();
			this.startActivity(new Intent(this, this.getClass()));
		}
		// 防止系统语言改变导致界面语言改变
		preventRefreshUIByOSLanguageChange();
	}

	/**
	 * 防止系统语言改变导致界面语言改变
	 * @param lan
	 */
	private void preventRefreshUIByOSLanguageChange() {
		String lan = myApplication.getAppLan();
		// judeg whether Android OS has change Language and App language Don't
		// auto-determinate by Android OS
		// if Android OS Language change then update app self Language
		String language = Locale.getDefault().getLanguage();
		String country = Locale.getDefault().getCountry();
		if (!myApplication.getSysLanguage().equals(language)
				|| !myApplication.getSysCountry().equals(country)) {
			// update params
			myApplication.setSysLanguage(language);
			myApplication.setSysCountry(country);
			// update app language
			AppUtil.updateLanguage(getResources(), lan);
			// refresh UI ；危险，会导致界面重新加载，如果有用户做了一些操作会被清除
			this.finish();
			this.startActivity(new Intent(this, this.getClass()));
		}
	}
	
	/**
	 * 提示消息
	 * @param str
	 */
	public void toast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 返回按钮被点击
	 * @param v
	 */
	public void finishActivity(View v) {
		this.finish();
	}

	/**
	 * 显示等待窗口
	 */
	public void showLoadingProgressDialog() {
		this.showLoadingProgressDialog(getString(R.string.pleaseWait));
	}

	/**
	 * 显示等待窗口
	 * @param message	自定义提示信息
	 */
	public void showLoadingProgressDialog(String message) {
		AppLogger.i("showLoadingProgressDialog");
		if (this.progressDialog == null) {
			this.progressDialog = new ProgressDialog(this);
			this.progressDialog.setIndeterminate(true);
		}

//		this.progressDialog.setMessage(message);
		//this.progressDialog.setCancelable(false);
		this.progressDialog.setMessage(message);
		this.progressDialog.show();
	}

	/**
	 * 关闭等待窗口
	 */
	public void dismissProgressDialog() {
		if (this.progressDialog != null && this.progressDialog.isShowing()) {
			this.progressDialog.setCancelable(true);
			this.progressDialog.cancel();
			AppLogger.i("######dismissed the Dialog");
		}
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}

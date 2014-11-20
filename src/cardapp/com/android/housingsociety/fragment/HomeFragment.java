package cardapp.com.android.housingsociety.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.ui.EstateServiceActivity_;
import cardapp.com.android.housingsociety.ui.LifeMustActivity_;
import cardapp.com.android.housingsociety.ui.shop.ShopListActivity;
import cardapp.com.android.housingsociety.ui.shop.ShopListActivity_;
import cardapp.com.android.housingsociety.util.UserManageUtil;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

/**
 * Home Fragment
 * 		主界面的Fragment
 *
 * @author CardApp@ZuoQing
 *
 */
@EFragment(R.layout.fragment_home)
public class HomeFragment extends Fragment {
	
	Activity mActivity;
	
	@Bean
	UserManageUtil userManage;

	@AfterViews
	void afterViews() {
		mActivity = getActivity();
	}

	@Click({ R.id.logo, R.id.estateManagement, R.id.lifeMust, R.id.other, R.id.catering,
			R.id.shopping, R.id.educationInformation })
	void click(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.logo: // 点击logo
			if (mActivity != null) {
				IMenuManagement i = (IMenuManagement) mActivity;
				i.openMenu();
			}
			break;
		case R.id.estateManagement: // 物业服务
			// 检查用户登录
			if (userManage.whetherLogin()) {	// Have User Login 
				intent = new Intent(mActivity, EstateServiceActivity_.class);
			} else {
				Toast.makeText(mActivity, getString(R.string.please_login), Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.lifeMust: // 生活必备
			intent = new Intent(mActivity, LifeMustActivity_.class);
			break;
		case R.id.other: // 其他
			intent = new Intent(mActivity, ShopListActivity_.class);
			intent.putExtra(ShopListActivity.SHOPLISTACTIVITYCLASS, ShopListActivity.OTHER);
			break;
		case R.id.catering: // 餐饮
			intent = new Intent(mActivity, ShopListActivity_.class);
			intent.putExtra(ShopListActivity.SHOPLISTACTIVITYCLASS, ShopListActivity.CATERING);
			break;
		case R.id.shopping: // 购物
			intent = new Intent(mActivity, ShopListActivity_.class);
			intent.putExtra(ShopListActivity.SHOPLISTACTIVITYCLASS, ShopListActivity.SHOPPING);
			break;
		case R.id.educationInformation: // 教育资讯
			intent = new Intent(mActivity, ShopListActivity_.class);
			intent.putExtra(ShopListActivity.SHOPLISTACTIVITYCLASS, ShopListActivity.EDUCATION);
			break;
		}
		if (intent != null) {
			mActivity.startActivity(intent);
		}
	}
	
	/**
	 * 接口调用界面菜单
	 * 
	 */
	public interface IMenuManagement {
		void openMenu();
	}
}

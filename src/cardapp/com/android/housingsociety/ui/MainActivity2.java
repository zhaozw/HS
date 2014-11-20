package cardapp.com.android.housingsociety.ui;

import java.util.ArrayList;
import java.util.List;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.adapter.MenuAdapter;
import cardapp.com.android.housingsociety.bean.MyMenuItem;
import cardapp.com.android.housingsociety.fragment.HomeFragment.IMenuManagement;
import cardapp.com.android.housingsociety.fragment.HomeFragment_;
import cardapp.com.android.housingsociety.ui.menu.ContactUsActivity_;
import cardapp.com.android.housingsociety.ui.menu.FavoriteActivity_;
import cardapp.com.android.housingsociety.ui.menu.InboxActivity_;
import cardapp.com.android.housingsociety.ui.menu.ReportProblemActivity_;
import cardapp.com.android.housingsociety.ui.menu.SettingsActivity2;
import cardapp.com.android.housingsociety.util.AppUtil;
import cardapp.com.android.housingsociety.util.UserManageUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * Main Interface
 *
 * @author CardApp@ZuoQing
 *
 */
@EActivity 
public class MainActivity2 extends BaseActivity implements OnItemClickListener, IMenuManagement {

	@Bean
	UserManageUtil userManage;

	ListView mMenuList; // menu listview
    MenuDrawer mMenuDrawer;
	BaseAdapter mAdapter; // menu adapter
	List<MyMenuItem> menuData; // menu adapter data

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.Type.OVERLAY, Position.START);
        mMenuDrawer.setContentView(R.layout.activity_main_2);

		// init menu data
		getMenuData();
		// init Menu List
		mMenuList = new ListView(this);
//		mMenuList.setBackgroundColor(getResources().getColor(R.color.menu_listview_color));
		mMenuList.setBackgroundResource(R.drawable.menu_bg);
		mAdapter = new MenuAdapter(this, menuData);
		mMenuList.setAdapter(mAdapter);
		mMenuList.setOnItemClickListener(this);
		mMenuList.setDividerHeight(-1);
		// add Menu List to MenuDrawer
        mMenuDrawer.setMenuView(mMenuList);
        // coustom MenuDrawer
        mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
        mMenuDrawer.setHardwareLayerEnabled(false);
        mMenuDrawer.setMaxAnimationDuration(3000);
        mMenuDrawer.setMenuSize((int)getResources().getDimension(R.dimen.menu_item_width));
//        mMenuDrawer.setDropShadow(getResources().getColor(android.R.color.transparent));
        mMenuDrawer.setDropShadow(R.drawable.menu_shadow_style);
        mMenuDrawer.setDropShadowSize(1000);

        // init home content
		Fragment fragment = new HomeFragment_();
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
	}

	@Override
	protected void onStart() {
		if (AppUtil.isFirstUseApp(this)) { // First Open The App
			mMenuDrawer.openMenu();
		}
		super.onStart();
	}

	/**
	 * 获取侧边栏菜单数据
	 */
	void getMenuData() {
		if (userManage == null) {
			return;
		}
		if (menuData == null) {
			menuData = new ArrayList<MyMenuItem>();
		}
		MyMenuItem item = null;
		if (userManage.whetherLogin()) { // 已经登录
			item = new MyMenuItem();
			item.setTitle(userManage.getUserName()); // User Name
			item.setImage(R.drawable.menu_user);
			item.setAction("USERNAME");
			menuData.add(item);
		} else { // 未登录
			item = new MyMenuItem();
			item.setTitle(getString(R.string.login)); // Login
			item.setImage(R.drawable.menu_user);
			item.setAction("LOGIN");
			menuData.add(item);
		}

		String[] titles = { getString(R.string.inbox),
				getString(R.string.contactUs), getString(R.string.favourites),
				getString(R.string.setting), getString(R.string.reportProblems) };
		int[] images = { R.drawable.menu_box, R.drawable.menu_contact,
				R.drawable.menu_fa, R.drawable.menu_set, R.drawable.menu_report };
		String[] actions = { "INBOX", "CONTACT", "FAVOURITES", "SETTINGS",
				"REPORT" };
		for (int i = 0; i < titles.length; i++) {
			item = new MyMenuItem();
			item.setTitle(titles[i]); // Inbox
			item.setImage(images[i]);
			item.setAction(actions[i]);
			menuData.add(item);
		}

		if (userManage.whetherLogin()) {
			item = new MyMenuItem();
			item.setTitle(getString(R.string.logout)); // Logout
			item.setImage(R.drawable.menu_logout);
			item.setAction("LOGOUT");
			menuData.add(item);
		}
	}

	/**
	 * 菜单点击事件
	 * 
	 * @param position
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// update selected item and title, then close the drawer
		mMenuList.setItemChecked(position, true);
		mMenuDrawer.closeMenu();

		MyMenuItem item = menuData.get(position);
		Intent intent = null;
		switch (item.getAction()) {
		case "LOGIN":
			finish();
			startActivity(new Intent(this, LoginActivity_.class));
			break;
		case "USERNAME":
//					intent = new Intent(this, UserInfoActivity_.class);
			break;
		case "INBOX":
			intent = new Intent(this, InboxActivity_.class);
			break;
		case "CONTACT":
			intent = new Intent(this, ContactUsActivity_.class);
			break;
		case "FAVOURITES":
			intent = new Intent(this, FavoriteActivity_.class);
			break;
		case "SETTINGS":
			intent = new Intent(this, SettingsActivity2.class);
			break;
		case "REPORT":
			intent = new Intent(this, ReportProblemActivity_.class);
			break;
		case "LOGOUT":
			userManage.setLoginState(false);
			this.finish();
			startActivity(new Intent(this, this.getClass()));
			break;
		}
		if (intent != null) {
			startActivity(intent);
		}
	}

	/**
	 * HomeFragment调用的接口，用来打开菜单
	 */
	@Override
	public void openMenu() {
		if (mMenuDrawer != null) {
			mMenuDrawer.openMenu();
		}
	}

}

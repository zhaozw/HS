package cardapp.com.android.housingsociety.ui.shop;

import java.io.File;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.google.android.gms.internal.fl;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewpagerindicator.CirclePageIndicator;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.adapter.ImagesFragmentAdapter;
import cardapp.com.android.housingsociety.bean.ShopDetail;
import cardapp.com.android.housingsociety.bean.ShopItem;
import cardapp.com.android.housingsociety.cache.Cache;
import cardapp.com.android.housingsociety.control.DataControl;
import cardapp.com.android.housingsociety.debug.AppLogger;
import cardapp.com.android.housingsociety.ui.BaseActivity;
import cardapp.com.android.housingsociety.ui.common.GoogleMapActivity;
import cardapp.com.android.housingsociety.ui.common.ShowImagesActivity;
import cardapp.com.android.housingsociety.ui.common.ShowImagesActivity_;
import cardapp.com.android.housingsociety.util.AppUtil;
import cardapp.com.android.housingsociety.util.FavoriteManageUtil;
import cardapp.com.android.housingsociety.util.MyNetUtil;

/**
 * 展示商品的详细信息 存在问题： 1、如果图片地址无效，图片展示区域会显示一片空白； 2、
 *
 * @author CardApp@ZuoQing
 *
 */
@EActivity(R.layout.activity_shop_detail)
public class ShopDetailActivity extends BaseActivity {
	public static final String SHOPLISTACTIVITYITEMCLICKSHOPITEM = "cardapp.com.android.housingsociety.ui.shop.ShopListActivity.SHOPLISTACTIVITYITEMCLICKSHOPITEM";

	@ViewById(R.id.title)
	TextView title;
	@ViewById(R.id.logo)
	ImageView logo; // 头部logo
	//
	@ViewById(R.id.share)
	Button share;
	@ViewById(R.id.stamp)
	Button stamp;
	@ViewById(R.id.order)
	Button order;
	//
	@ViewById(R.id.map)
	Button map;
	@ViewById(R.id.coupon)
	Button coupon;
	@ViewById(R.id.add)
	Button add;
	//
	@ViewById(R.id.call)
	Button call;
	//
	@ViewById(R.id.describe)
	TextView describe;
	@ViewById(R.id.pager)
	ViewPager mPager; // 图片展示
	@ViewById(R.id.indicator)
	CirclePageIndicator mIndicator; // 图片展示指示位置

	ShopItem shopItem; // 父页面传入的ListView的一项
	ShopDetail mShopDetail; // 当前页面的详细内容
	FragmentPagerAdapter mAdapter;
	String[] imageUrls; // 图片url集合
	String pdfPath;

	@Bean
	DataControl dataControl; // 获取数据

	private boolean addedFlag = false; // 判断是否已经添加过了
	@Bean
	FavoriteManageUtil favoriteManage;

	@AfterViews
	void afterViews() {
		// get shopItem
		Intent intent = getIntent();
		if (intent != null) {
			shopItem = (ShopItem) intent
					.getSerializableExtra(SHOPLISTACTIVITYITEMCLICKSHOPITEM);
		}
		// init title and logo
		if (shopItem != null) {
			title.setText(AppUtil.getRightStringByLanguage(
					myApplication.getAppLan(), shopItem.getSimChiName().trim(),
					shopItem.getChiName().trim(), shopItem.getEngName().trim()));
			String iconUri = shopItem.getShopIconPath();
			ImageLoader.getInstance().displayImage(iconUri, logo);
			// get data
			dataControl.getShopDetail(shopItem.getShopId());
		} else {
			title.setText("Shop Detail");
		}

	}

	/**
	 * 接收Bus数据后更新界面
	 * 
	 * @param shopList
	 */
	@UiThread
	public void onEvent(ShopDetail shopDetail) {
		// AppLogger.i(shopDetail.getSimChiName());
		if (shopDetail != null) {
			mShopDetail = shopDetail;
			dataControl.doVisit(4, shopDetail.getShopId(),
					shopDetail.getIntegral()); // visit Server
			update();
		}
	}

	/**
	 * 接收Bus发送的提示信息
	 * 
	 * @param shopList
	 */
	@UiThread
	public void onEvent(String str) {
		// AppLogger.i(str);
		if (mShopDetail == null) { // 没有缓存数据才提示信息
			toast(str);
		}
	}

	/**
	 * update description, image, button
	 */
	@UiThread
	void update() {
		describe.setText(Html.fromHtml(AppUtil.getRightStringByLanguage(
				myApplication.getAppLan(), mShopDetail.getSimChiDesc(),
				mShopDetail.getChiDesc(), mShopDetail.getEngDesc())));
		String tStr = mShopDetail.getImageList();
		// String tStr =
		// "http://img0.bdstatic.com/img/image/shouye/shishang1111.jpg,http://img0.bdstatic.com/img/image/qiuyi1111.jpg,http://img0.bdstatic.com/img/image/shouye/meinv1111.jpg";
		if (!TextUtils.isEmpty(tStr)) {
			imageUrls = tStr.split(",");
			// 设置图片
			mAdapter = new ImagesFragmentAdapter(getSupportFragmentManager(),
					imageUrls);
			mPager.setAdapter(mAdapter);
			mIndicator.setViewPager(mPager);
			if (imageUrls.length > 1) {
				final float density = getResources().getDisplayMetrics().density;
				mIndicator.setBackgroundColor(0x00CCCCCC);
				mIndicator.setRadius(3 * density);
				mIndicator.setPageColor(0xFF000000);
				mIndicator.setFillColor(0xFFFFFFFF);
				mIndicator.setStrokeColor(0xFF000000);
				mIndicator.setStrokeWidth(0);
			} else {
				mIndicator.setVisibility(View.INVISIBLE);
			}
		} else { // 无法加载图片
			toast(getString(R.string.loadImageFail));
		}

		updateButton();
	}

	/**
	 * 更新按钮
	 */
	private void updateButton() {
		// 中文环境
		if (getString(R.string.switchLanguage_comChi_value).equals(
				myApplication.getAppLan())) {
			share.setBackgroundResource(R.drawable.seletc_button_share_zh);
			map.setBackgroundResource(R.drawable.seletc_button_map_zh);
			coupon.setBackgroundResource(R.drawable.seletc_button_coupon_zh);
			add.setBackgroundResource(R.drawable.seletc_button_add_zh);
			stamp.setBackgroundResource(R.drawable.seletc_button_stamp_zh);
		}
		// init stamp button
		if (!TextUtils.isEmpty(mShopDetail.getSimChiStamp())
				|| !TextUtils.isEmpty(mShopDetail.getChiStamp())
				|| !TextUtils.isEmpty(mShopDetail.getEngStamp())) {
			stamp.setEnabled(true);
		}

		// init order button
		if (!TextUtils.isEmpty(mShopDetail.getWebLink())) {
			// order.setEnabled(true);
			call.setEnabled(true);
			// call.setText(getString(R.string.order));
			if (getString(R.string.switchLanguage_comChi_value).equals(
					myApplication.getAppLan())) {
				call.setBackgroundResource(R.drawable.seletc_button_order_zh);
			}
		}

		// init map button
		if (!TextUtils.isEmpty(mShopDetail.getLocationAxis())) {
			map.setEnabled(true);
		}

		// init coupon button
		if (!TextUtils.isEmpty(mShopDetail.getEngCouponPdf())
				|| !TextUtils.isEmpty(mShopDetail.getChiCouponPdf())
				|| !TextUtils.isEmpty(mShopDetail.getSimChiCouponPdf())) {
			coupon.setEnabled(true);
		}

		// init call button
		if (!TextUtils.isEmpty(mShopDetail.getPhone())) {
			call.setEnabled(true);
			// call.setText(getString(R.string.call));
			if (getString(R.string.switchLanguage_comChi_value).equals(
					myApplication.getAppLan())) {
				call.setBackgroundResource(R.drawable.seletc_button_call_zh);
			}
		}

		if (whetherAddedFavorite(mShopDetail.getShopId())) { // 是否已经添加过到最爱
		// add.setText(getString(R.string.added));
			addedFlag = true;
			if (getString(R.string.switchLanguage_comChi_value).equals(
					myApplication.getAppLan())) {
				add.setBackgroundResource(R.drawable.seletc_button_added_zh);
			} else {
				add.setBackgroundResource(R.drawable.seletc_button_added);
			}
		}
	}

	@Click({ R.id.share, R.id.stamp, R.id.order, R.id.map, R.id.coupon,
			R.id.add, R.id.call })
	void click(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.share: //
			intent = new Intent();
			intent.setAction(Intent.ACTION_SEND);
			intent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
			intent.setType("text/plain");
			break;
		case R.id.stamp: //
			/*
			 * "StampIcon": "", "EngStamp": "", "ChiStamp": "", "SimChiStamp":
			 * "",
			 */
			// 查看Stamp图片
			stampMethod();
			break;
		case R.id.order: // 打开订购网页
			orderMethod();
			break;
		case R.id.map: //
			intent = new Intent(this, GoogleMapActivity.class);
			intent.putExtra(GoogleMapActivity.GOOGLEMMAPLOCATION,
					mShopDetail.getLocationAxis());
			intent.putExtra(GoogleMapActivity.GOOGLEMMAPLOCATIONTITLE, AppUtil
					.getRightStringByLanguage(myApplication.getAppLan(),
							mShopDetail.getSimChiName(),
							mShopDetail.getChiName(), mShopDetail.getEngName()));
			break;
		case R.id.coupon: //
			couponMethod();
			break;
		case R.id.add: //
			if (addedFlag) { // 已经添加过
				deleteFavorite();
			} else { // 未添加过
				// 添加到最爱
				addFavorite();
				addedFlag = true;
				if (getString(R.string.switchLanguage_comChi_value).equals(
						myApplication.getAppLan())) {
					add.setBackgroundResource(R.drawable.seletc_button_added_zh);
				} else {
					add.setBackgroundResource(R.drawable.seletc_button_added);
				}
			}
			break;
		case R.id.call: // 提示框是否拨打电话
			if (!TextUtils.isEmpty(mShopDetail.getPhone())) {
				callPhone();
			} else {
				orderMethod();
			}

			break;
		}

		if (intent != null) {
			startActivity(intent);
		}
	}

	/**
	 * 查看Stamp图片
	 */
	private void stampMethod() {
		String address = mShopDetail.getStampIcon();
		AppLogger.v(address);
		if (TextUtils.isEmpty(address)) {
			return;
		}
		if (address.endsWith(".pdf")) { // 查看pdf
			pdfPath = Environment.getExternalStorageDirectory().getPath()
					+ "/HousingSociety/download/pdf-"
					+ mShopDetail.getChiName() + "-" + mShopDetail.getEngName()
					+ ".pdf";
			if (MyNetUtil.serverEnable(this)) {
				HttpUtils http = new HttpUtils();
				// HttpHandler<File> httpHandler =
				http.download(address, pdfPath, false, true,
						new MyRequestCallBack());
			} else {
				toast(getString(R.string.visitServerError));
			}
		} else { // 查看图片
			if (MyNetUtil.serverEnable(mActivity)) {
				Intent stampIntent = new Intent(mActivity,
						ShowImagesActivity_.class);
				// put Extra
				stampIntent.putExtra(ShowImagesActivity.IMAGELIST,
						new String[] { address });
				mActivity.startActivity(stampIntent);
			} else {
				Toast.makeText(mActivity, getString(R.string.visitServerError),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * 查看Coupon pdf
	 */
	private void couponMethod() {
		// 查看Coupon pdf
		String address = AppUtil.getRightStringByLanguage(
				myApplication.getAppLan(), mShopDetail.getSimChiCouponPdf(),
				mShopDetail.getChiCouponPdf(), mShopDetail.getEngCouponPdf());
		pdfPath = Environment.getExternalStorageDirectory().getPath()
				+ "/HousingSociety/download/pdf-" + mShopDetail.getChiName()
				+ "-" + mShopDetail.getEngName() + ".pdf";
		AppLogger.d(pdfPath);
		if (MyNetUtil.serverEnable(this)) {
			HttpUtils http = new HttpUtils();
			// HttpHandler<File> httpHandler =
			http.download(address, pdfPath, false, true,
					new MyRequestCallBack());
		} else {
			toast(getString(R.string.visitServerError));
		}
	}

	/**
	 * 订购，打开一个网页
	 */
	private void orderMethod() {
		Intent intent;
		String url = mShopDetail.getWebLink();
		// String url =
		// "http://www.meituan.com/deal/26567641.html?mtt=1.index%2Ffloor.nr.3.i2fh959g";
		intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(intent);
	}

	/**
	 * 下载文件事件监听
	 *
	 * @author CardApp@ZuoQing
	 *
	 */
	class MyRequestCallBack extends RequestCallBack<File> {
		@Override
		public void onStart() {
			showLoadingProgressDialog();
		}

		@Override
		public void onLoading(long total, long current, boolean isUploading) {
		}

		@Override
		public void onSuccess(ResponseInfo<File> responseInfo) {
			dismissProgressDialog();
			// 打开文件
			openPDFFile(pdfPath);
		}

		@Override
		public void onFailure(HttpException error, String msg) {
			dismissProgressDialog();
			AppLogger.i(msg);
			if (msg.contains("maybe the file has downloaded completely")) {
				// 打开文件
				openPDFFile(pdfPath);
			} else {
				toast(getString(R.string.visitServerError));
			}
		}
	}

	/**
	 * 打开pdf文件
	 * 
	 * @param pdfName
	 */
	private void openPDFFile(final String pdfName) {
		Intent intent = new Intent("android.intent.action.VIEW");
		Uri uri = Uri.fromFile(new File(pdfName));
		intent.setDataAndType(uri, "application/pdf");
		mActivity.startActivity(intent);
	}

	/**
	 * 拨打手机
	 */
	private void callPhone() {
		final String phone = mShopDetail.getPhone();
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(getString(R.string.call) + ":" + phone);
		builder.setPositiveButton(getString(R.string.call),
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent2 = new Intent(
								"android.intent.action.DIAL", Uri.parse("tel:"
										+ phone));
						mActivity.startActivity(intent2);
					}
				});
		builder.setNegativeButton(getString(android.R.string.cancel),
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.show();
	}
	
	/**
	 * 获取相应的key
	 * @return
	 */
	String getKey() {
		if (shopItem == null) {
			return "";
		}
		if (shopItem.getShopClassEngName().contains("Education")) { // 添加到教育资讯收藏
			return Cache.FAV_EDUINFO;
		} else if (shopItem.getShopClassEngName().contains("Shopping")) { // 添加到教育资讯收藏
			return Cache.FAV_SHOPPING;
		} else if (shopItem.getShopClassEngName().contains("Catering")) { // 添加到教育资讯收藏
			return Cache.FAV_CATERING;
		} else if (shopItem.getShopClassEngName().contains("Other")) { // 添加到教育资讯收藏
			return Cache.FAV_OTHER;
		}
		return "";
	}

	/**
	 * 检查是否已经添加过到收藏
	 * 
	 * @return
	 */
	private boolean whetherAddedFavorite(long id) {
		String key = getKey();
		if (TextUtils.isEmpty(key))  {
			AppLogger.d("没有相应的选项");
			return false;
		}
		return favoriteManage.isAddedEduInfo(key, id);
	}

	/**
	 * 添加到最爱
	 */
	private void addFavorite() {
		String key = getKey();
		if (TextUtils.isEmpty(key))  {
			AppLogger.d("没有相应的选项");
			return;
		}
		favoriteManage.addShopItem(key, shopItem);
		// 提示
		toast(getString(R.string.addSuccess));
	}

	/**
	 * 删除收藏
	 */
	private void deleteFavorite() {
		String key = getKey();
		if (TextUtils.isEmpty(key))  {
			AppLogger.d("没有相应的选项");
			return;
		}
		long id = shopItem.getShopId();
		// 删除成功提示
		if (favoriteManage.deleteEduInfo(key, id)) {
			toast(getString(R.string.deleteSuccess));
			addedFlag = false;
			if (getString(R.string.switchLanguage_comChi_value).equals(
					myApplication.getAppLan())) {
				add.setBackgroundResource(R.drawable.seletc_button_add_zh);
			} else {
				add.setBackgroundResource(R.drawable.seletc_button_add);
			}
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		myApplication.BUS.register(this);
	}

	@Override
	public void onStop() {
		myApplication.BUS.unregister(this);
		super.onStop();
	}
}

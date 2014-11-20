package cardapp.com.android.housingsociety.control;

import java.util.List;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cardapp.com.android.housingsociety.MyApplication;
import cardapp.com.android.housingsociety.R;
import cardapp.com.android.housingsociety.bean.EstateInfo;
import cardapp.com.android.housingsociety.bean.EstateInfoListFetchedEvent;
import cardapp.com.android.housingsociety.bean.Necessity;
import cardapp.com.android.housingsociety.bean.NecessityClass;
import cardapp.com.android.housingsociety.bean.NecessityClassListFetchedEvent;
import cardapp.com.android.housingsociety.bean.NecessityListFetchedEvent;
import cardapp.com.android.housingsociety.bean.NecessityListItem;
import cardapp.com.android.housingsociety.bean.NecessityListItemFetchedEvent;
import cardapp.com.android.housingsociety.bean.Notice;
import cardapp.com.android.housingsociety.bean.NoticeClass;
import cardapp.com.android.housingsociety.bean.NoticeClassFetchedEvent;
import cardapp.com.android.housingsociety.bean.NoticeItem;
import cardapp.com.android.housingsociety.bean.NoticeItemFetchedEvent;
import cardapp.com.android.housingsociety.bean.ShopDetail;
import cardapp.com.android.housingsociety.bean.ShopItem;
import cardapp.com.android.housingsociety.bean.ShopItemListFetchedEvent;
import cardapp.com.android.housingsociety.cache.Cache;
import cardapp.com.android.housingsociety.debug.AppLogger;
import cardapp.com.android.housingsociety.net.impl.AppServiceImpl;
import cardapp.com.android.housingsociety.net.impl.ShopServiceImpl;
import cardapp.com.android.housingsociety.util.AppUtil;
import cardapp.com.android.housingsociety.util.Installation;
import cardapp.com.android.housingsociety.util.MyNetUtil;

/**
 * 管理数据，获取缓存数据，获取服务器数据
 *
 * @author CardApp@ZuoQing
 *
 */
@EBean(scope = EBean.Scope.Singleton)
public class DataControl {
	// (Explained later)
	public static final String NETWORK = "NETWORK";
	public static final String CACHE = "CACHE";
	public static final String UPLOAD = "UPLOAD";

	@Bean
	ShopServiceImpl shopService;
	@Bean
	AppServiceImpl appService;
	@App
	MyApplication app;
	@Bean
	Cache cache;

	// /////////////////////// Estate Service api
	// ///////////////////////////////////////////

	/**
	 * 获取不同通知类型
	 * 
	 * @param estate
	 */
	@Background(serial = CACHE)
	public void getNoticeClass(String estate) {
		String key = Cache.NOTICECLASSBYESTATE + estate;
		NoticeClassFetchedEvent cachedResult = cache.get(key,
				NoticeClassFetchedEvent.class);
		if (cachedResult != null) {
			app.BUS.post(cachedResult);
		}
		getNoticeClassAsync(estate);
	}

	@Background(serial = NETWORK)
	public void getNoticeClassAsync(String estate) {
		String key = Cache.NOTICECLASSBYESTATE + estate;
		String estateId = appService.getEstateId(estate);
		String type = "PS";
		String res = appService.getNoticeClasses(estateId, type);

		if (TextUtils.isEmpty(res)) {
			app.BUS.post(app.getString(R.string.visitServerError));
			return;
		}
		Gson gson = new Gson();
		List<NoticeClass> mDatas = null;
		try {
			mDatas = gson.fromJson(res, new TypeToken<List<NoticeClass>>() {
			}.getType());
			// update ui
			if (mDatas != null && mDatas.size() != 0) {
				NoticeClassFetchedEvent s = new NoticeClassFetchedEvent(mDatas);
				cache.put(key, s);
				app.BUS.post(s);
			} else {
				app.BUS.post(app.getString(R.string.dataEmpty));
			}
		} catch (Exception e) {
			e.printStackTrace();
			app.BUS.post(app.getString(R.string.visitServerError));
		}
	}

	/**
	 * 获取确定类型的所有通知
	 * 
	 * @param noticeId
	 * @param page
	 * @param noticeClassId
	 */
	@Background(serial = CACHE)
	public void getNoticeItems(long noticeId, long page, long noticeClassId) {
		String key = Cache.NOTICEITEMBYNOTICECLASSID + noticeClassId;
		NoticeItemFetchedEvent cachedResult = cache.get(key,
				NoticeItemFetchedEvent.class);
		if (cachedResult != null) {
			app.BUS.post(cachedResult);
		}
		getNoticeItemsAsync(noticeId, page, noticeClassId);
	}

	@Background(serial = NETWORK)
	public void getNoticeItemsAsync(long noticeId, long page, long noticeClassId) {
		String key = Cache.NOTICEITEMBYNOTICECLASSID + noticeClassId;
		String res = appService.getNoticeList(noticeId, page, noticeClassId);
		if (TextUtils.isEmpty(res)) {
			app.BUS.post(app.getString(R.string.visitServerError));
			return;
		}
		// parser result
		Gson gson = new Gson();
		List<NoticeItem> mDatas = null;
		try {
			mDatas = gson.fromJson(res, new TypeToken<List<NoticeItem>>() {
			}.getType());
			if (mDatas != null && mDatas.size() != 0) {
				NoticeItemFetchedEvent s = new NoticeItemFetchedEvent(mDatas);
				cache.put(key, s);
				app.BUS.post(s);
			} else {
				app.BUS.post(app.getString(R.string.dataEmpty));
			}
		} catch (Exception e) {
			e.printStackTrace();
			app.BUS.post(app.getString(R.string.visitServerError));
		}
	}

	@Background(serial = CACHE)
	public void getNotice(long noticeId) {
		String key = Cache.NOTICEBYNOTICEID + noticeId;
		Notice cachedResult = cache.get(key, Notice.class);
		if (cachedResult != null) {
			app.BUS.post(cachedResult);
		} /*else {
			getNoticeAsync(noticeId);
		}*/
	}

	@Background(serial = NETWORK)
	public void getNoticeAsync(long noticeId) {
		String key = Cache.NOTICEBYNOTICEID + noticeId;
		String res = appService.getNoticeContent(noticeId);
		if (TextUtils.isEmpty(res)) {
			app.BUS.post(app.getString(R.string.visitServerError));
			return;
		}
		Gson gson = new Gson();
		List<Notice> mDatas = null;
		try {
			mDatas = gson.fromJson(res, new TypeToken<List<Notice>>() {
			}.getType());
			if (mDatas != null && mDatas.size() != 0) {
				Notice n = mDatas.get(0);
				cache.put(key, n);
				app.BUS.post(n);
			} else {
				app.BUS.post(app.getString(R.string.visitServerError));
			}
		} catch (Exception e) {
			app.BUS.post(app.getString(R.string.visitServerError));
		}
	}

	// /////////////////////// Life Must api
	// ///////////////////////////////////////////

	/**
	 * 生活必备第一层级
	 * 
	 * @param estate
	 */
	@Background(serial = CACHE)
	public void getNecessityClassList(String estate) {
		String key = Cache.LIFEMUSTALLCLASSBYESTATE + estate;
		NecessityClassListFetchedEvent cachedResult = cache.get(key,
				NecessityClassListFetchedEvent.class);
		if (cachedResult != null) {
			app.BUS.post(cachedResult);
		}
		getNecessityClassListAsync(estate);
	}

	@Background(serial = NETWORK)
	public void getNecessityClassListAsync(String estate) {
		String key = Cache.LIFEMUSTALLCLASSBYESTATE + estate;
		String estateId = appService.getEstateId(estate);
		String res = appService.getNecessityClassList(estateId);

		if (TextUtils.isEmpty(estateId) || TextUtils.isEmpty(res)) { // 无法连接服务器
			app.BUS.post(app.getString(R.string.visitServerError));
			return;
		}
		// parser res
		Gson gson = new Gson();
		List<NecessityClass> mDatas = null;
		try {
			mDatas = gson.fromJson(res, new TypeToken<List<NecessityClass>>() {
			}.getType());

			if (mDatas != null && mDatas.size() != 0) {
				NecessityClassListFetchedEvent s = new NecessityClassListFetchedEvent(
						mDatas);
				cache.put(key, s);
				app.BUS.post(s);
			} else {
				app.BUS.post(app.getString(R.string.dataEmpty));
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			app.BUS.post(app.getString(R.string.visitServerError));
			return;
		}
	}

	/**
	 * 生活必备第二层级
	 * 
	 * @param necessityClassId
	 */
	@Background(serial = CACHE)
	public void getNecessityList(long necessityClassId) {
		String key = Cache.LIFEMUSTNECESSITYBYCLASSID + necessityClassId;
		NecessityListFetchedEvent cachedResult = cache.get(key,
				NecessityListFetchedEvent.class);
		if (cachedResult != null) {
			app.BUS.post(cachedResult);
		}
		getNecessityListAsync(necessityClassId);
	}

	@Background(serial = NETWORK)
	public void getNecessityListAsync(long necessityClassId) {
		String key = Cache.LIFEMUSTNECESSITYBYCLASSID + necessityClassId;
		String res = appService.getNecessity(necessityClassId);
		if (TextUtils.isEmpty(res)) { // 没有结果
			app.BUS.post(app.getString(R.string.visitServerError));
			return;
		}
		Gson gson = new Gson();
		List<Necessity> mDatas = null;
		try {
			mDatas = gson.fromJson(res, new TypeToken<List<Necessity>>() {
			}.getType());
			if (mDatas != null && mDatas.size() != 0) {
				NecessityListFetchedEvent s = new NecessityListFetchedEvent(
						mDatas);
				cache.put(key, s);
				app.BUS.post(s);
			} else {
				app.BUS.post(app.getString(R.string.visitServerError));
			}
		} catch (Exception e) {
			e.printStackTrace();
			app.BUS.post(app.getString(R.string.visitServerError));
		}
	}

	/**
	 * 生活必备第三层级
	 * 
	 * @param necessityId
	 */
	@Background(serial = CACHE)
	public void getNecessityListItems(long necessityId) {
		String key = Cache.LIFEMUSTNECESSITYLISTITEMBYNECESSITYID + necessityId;
		NecessityListItemFetchedEvent cachedResult = cache.get(key,
				NecessityListItemFetchedEvent.class);
		if (cachedResult != null) {
			app.BUS.post(cachedResult);
		}
		getNecessityListItemsAsync(necessityId);
	}

	@Background(serial = NETWORK)
	public void getNecessityListItemsAsync(long necessityId) {
		String key = Cache.LIFEMUSTNECESSITYLISTITEMBYNECESSITYID + necessityId;
		String res = appService.getNecessityList(necessityId);
		if (TextUtils.isEmpty(res)) { // 没有结果
			app.BUS.post(app.getString(R.string.dataEmpty));
			return;
		}
		Gson gson = new Gson();
		List<NecessityListItem> mDatas = null;
		try {
			mDatas = gson.fromJson(res,
					new TypeToken<List<NecessityListItem>>() {
					}.getType());
			if (mDatas != null && mDatas.size() != 0) {
				NecessityListItemFetchedEvent s = new NecessityListItemFetchedEvent(
						mDatas);
				cache.put(key, s);
				app.BUS.post(s);
			} else {
				app.BUS.post(app.getString(R.string.dataEmpty));
			}
		} catch (Exception e) {
			e.printStackTrace();
			app.BUS.post(app.getString(R.string.dataEmpty));
		}
	}

	// /////////////////////// Shop api
	// /////////////////////////////////////////

	// This is what the activity calls, it's public
	@Background(serial = CACHE)
	public void getShops(String key, long shopId, long page, long classId,
			String estate, String classEngName, int type) {
		AppLogger.d("getShops");
		// Try to load the existing cache
		ShopItemListFetchedEvent cachedResult = cache.get(key,
				ShopItemListFetchedEvent.class);

		// If there's something in cache, send the event
		if (cachedResult != null)
			app.BUS.post(cachedResult);

		// Then load from server, asynchronously
		getShopsAsync(key, shopId, page, classId, estate, classEngName, type);
	}

	@Background(serial = NETWORK)
	public void getShopsAsync(String key, long shopId, long page, long classId,
			String estate, String classEngName, int type) {
		AppLogger.d("getShopsAsync");
		String res = shopService.getShopsListByClass(shopId, page, classId,
				estate, classEngName, type);
		if (TextUtils.isEmpty(res)) {
			return;
		}
		Gson gson = new Gson();
		List<ShopItem> mDatas = null;
		try {
			mDatas = gson.fromJson(res, new TypeToken<List<ShopItem>>() {
			}.getType());
			if (mDatas != null && mDatas.size() != 0) {
				ShopItemListFetchedEvent sif = new ShopItemListFetchedEvent(
						mDatas);
				cache.put(key, sif);
				app.BUS.post(sif);
			}
		} catch (Exception e) {
			return;
		}
	}

	@Background(serial = CACHE)
	public void getShopDetail(long shopId) {
		AppLogger.d("getShopDetail");
		String key = Cache.SHOPDETAILBYID + shopId;
		ShopDetail cachedResult = cache.get(key, ShopDetail.class);
		if (cachedResult != null) {
			app.BUS.post(cachedResult);
		}
		getShopDetailAsync(shopId);
	}

	@Background(serial = NETWORK)
	public void getShopDetailAsync(long shopId) {
		AppLogger.d("getShopDetailAsync");
		String key = Cache.SHOPDETAILBYID + shopId;
		// get result from server
		String res = shopService.getShopDetails(shopId);
		if (TextUtils.isEmpty(res)) {
			app.BUS.post(app.getString(R.string.visitServerError));
			return;
		}
		// parser result
		Gson gson = new Gson();
		List<ShopDetail> list = null;
		try {
			list = gson.fromJson(res,
					new TypeToken<List<ShopDetail>>() {
					}.getType());
		} catch (Exception e) {
			e.printStackTrace();
			AppLogger.v("######### json parser bad #########");
		}
		if (list == null || list.size() == 0) {
			app.BUS.post(app.getString(R.string.dataEmpty));
			return;
		}
		// deal ShopDetail
		ShopDetail sd = list.get(0);
		if (sd != null) {
			cache.put(key, sd);
			app.BUS.post(sd);
		} else {
			app.BUS.post(app.getString(R.string.dataEmpty));
			return;
		}
	}

	// ///// left Menu ////////////
	/**
	 * Report Problem
	 * 
	 * @param shopId
	 */
	@Background(serial = CACHE)
	public void getEstateInfoList(String estate, long classId) {
		String key = Cache.SHOPDETAILBYID + estate + classId;
		EstateInfoListFetchedEvent cachedResult = cache.get(key,
				EstateInfoListFetchedEvent.class);
		if (cachedResult != null) {
			app.BUS.post(cachedResult);
		}
		//getEstateInfoListAsync(estate, classId);
	}

	@Background(serial = NETWORK)
	public void getEstateInfoListAsync(String estate, long classId) {
		String key = Cache.SHOPDETAILBYID + estate + classId;
		String estateId = appService.getEstateId(estate);
		String res = appService.getEstateInfo(estateId, classId);
		if (TextUtils.isEmpty(res)) {
			app.BUS.post(app.getString(R.string.visitServerError));
			return ;
		}
		try {
			Gson gson = new Gson();
			List<EstateInfo> list = gson.fromJson(res,
					new TypeToken<List<EstateInfo>>() {
					}.getType());
			EstateInfoListFetchedEvent s = new EstateInfoListFetchedEvent(list);
			cache.put(key, s);
			app.BUS.post(s);
		} catch (Exception e) {
			e.printStackTrace();
			// post somethin
			app.BUS.post(app.getString(R.string.visitServerError));
		}
	}
	
	
	//============================ 向服务器发送通知 =============================

	/**
	 * 通知服务器app状态
	 * @param type	1.打开App 2.查看通告详细 3.打开会所预订 4.商户详细 5.订购页
	 * @param pointId	当type=2/4/5时由接口对应接口提供此值，非则填0（2：NoticeId 4:ShopId）
	 * @param integral	当type=2/4时由接口对应接口提供此值，非则填0
	 */
	@Background(serial = UPLOAD)
	public void doVisit(long type, long pointId, long integral) {
		if (!MyNetUtil.serverEnable(app)) {	// 网络不可用
			return;
		}
		String appCode = app.getString(R.string.appCode);
		String deviceIdentification = Installation.id(app);	// 标识设备
		long clientType = 2;	// 1.IOS 2.Android 3.web
		shopService.doVisit(appCode, deviceIdentification, type, pointId, integral, clientType);
	}
	
	/**
	 * 上传错误日志（例如：崩溃）
	 * @param errorType	类型 自行定义
	 * @param location	错误位置 自行定义
	 * @param errorMsg	错误报告
	 */
	@Background(serial = UPLOAD)
	public void uploadAppErrorInfo(String errorType, String location, String errorMsg) {
		String appCode = app.getString(R.string.appCode);
		String deviceIdentification = Installation.id(app);	// 标识设备
		long clientType = 2;	// 1.IOS 2.Android 3.web
		shopService.uploadAppErrorInfo(appCode, deviceIdentification, errorType, location, errorMsg, clientType);
	}
}

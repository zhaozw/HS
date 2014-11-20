package cardapp.com.android.housingsociety.cache;

import java.io.Serializable;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

import cardapp.com.android.housingsociety.MyApplication;
import cardapp.com.android.housingsociety.debug.AppLogger;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

/**
 * 持久化的缓存系统
 *
 * @author CardApp@ZuoQing
 *
 */
@EBean
public class Cache {
	public static String USER = "Cache.KEY.USER";
	public static String CATERING = "Cache.KEY.CATERING";
	public static String SHOPPING = "Cache.KEY.SHOPPING";
	public static String EDUINFO = "Cache.KEY.EDUINFO";
	public static String OTHER = "Cache.KEY.OTHER";
	public static String SHOPDETAILBYID = "Cache.KEY.SHOPDETAILBYID";	// 和shopid拼接
	public static String LIFEMUSTALLCLASSBYESTATE = "Cache.KEY.LIFEMUSTALLCLASSBYESTATE";
	public static String LIFEMUSTNECESSITYBYCLASSID = "Cache.KEY.LIFEMUSTNECESSITYBYCLASSID";
	public static String LIFEMUSTNECESSITYLISTITEMBYNECESSITYID = "Cache.KEY.LIFEMUSTNECESSITYLISTITEMBYNECESSITYID";
	public static String NOTICECLASSBYESTATE = "Cache.KEY.NOTICECLASSBYESTATE";
	public static String NOTICEITEMBYNOTICECLASSID = "Cache.KEY.NOTICEITEMBYNOTICECLASSID";
	public static String NOTICEBYNOTICEID = "Cache.KEY.NOTICEBYNOTICEID";
	public static String ESTATEINFO = "Cache.KEY.ESTATEINFO";
	
	// 收藏key
	public static String FAV_EDUINFO = "Cache.KEY.FAV_EDUINFO";
	public static String FAV_CATERING = "Cache.KEY.FAV_CATERING";
	public static String FAV_OTHER = "Cache.KEY.FAV_OTHER";
	public static String FAV_SHOPPING = "Cache.KEY.FAV_SHOPPING";
	
	@App
	MyApplication application;
	
	/**
	 *  获取一个对象
	 * @param key			关键字
	 * @param returnType	返回类型
	 * @return				返回对象
	 */
	public <T extends Serializable> T get(String key, Class<T> returnType) {
		T res = null;
		try {
			DB db = DBFactory.open(application);
			// 
			res = db.get(key, returnType);

			db.close();
		} catch (SnappydbException e) {
			AppLogger.v("Get Cache Exception");
			e.printStackTrace();
		}
		
		return res;
	}

	
	/**
	 * 保存一个对象
	 * @param key		关键字
	 * @param value		对象
	 */
    public void put(String key, Serializable value) {
    	try {
			DB db = DBFactory.open(application);
			
			db.put(key, value);
			
			db.close();
		} catch (SnappydbException e) {
			AppLogger.v("Save Cache Exception");
			e.printStackTrace();
		}
    }
}

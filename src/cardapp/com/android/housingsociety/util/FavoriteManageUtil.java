package cardapp.com.android.housingsociety.util;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import android.text.TextUtils;
import cardapp.com.android.housingsociety.bean.ShopDetail;
import cardapp.com.android.housingsociety.bean.ShopItem;
import cardapp.com.android.housingsociety.bean.ShopItemListFetchedEvent;
import cardapp.com.android.housingsociety.cache.Cache;

/**
 * 收藏管理
 *
 * @author CardApp@ZuoQing
 *
 */
@EBean
public class FavoriteManageUtil {

	@Bean
	Cache cache;
	
	/**
	 * 添加一个ShopDetail到收藏
	 * @param sd
	 */
	public void addShopItem(String key, ShopItem s) {
		if (TextUtils.isEmpty(key)){
			return ;
		}
		// 获取之前收藏数据
		ShopItemListFetchedEvent cachedResult = cache.get(key,
				ShopItemListFetchedEvent.class);
		List<ShopItem> list = null;
		if (cachedResult != null) {		// 之前有缓存
			cachedResult.getShopList().add(s);
		} else {	// 之前没有缓存
			list = new ArrayList<ShopItem>();
			list.add(s);
			cachedResult = new ShopItemListFetchedEvent(list);
		}
		// 保存数据
		cache.put(key, cachedResult);
	}
	
	/**
	 * 判断是否添加特定id的ShopDetail
	 * @param key
	 * @param id
	 * @return
	 */
	public boolean isAddedEduInfo(String key, long id) {
		if (TextUtils.isEmpty(key)){
			return false;
		}
		ShopItemListFetchedEvent cachedResult = cache.get(key,
				ShopItemListFetchedEvent.class);
		if (cachedResult != null) {
			List<ShopItem> list = cachedResult.getShopList();
			if (list == null) {
				return false;
			}
			for (ShopItem s : list) {
				if (s.getShopId() == id) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}
	
	/**
	 * 删除一个收藏
	 * @param key
	 * @param id
	 * @return
	 */
	public boolean deleteEduInfo(String key, long id) {
		if (TextUtils.isEmpty(key)){
			return false;
		}
		ShopItemListFetchedEvent cachedResult = cache.get(key,
				ShopItemListFetchedEvent.class);
		if (cachedResult != null) {
			List<ShopItem> list = cachedResult.getShopList();
			if (list == null) {
				return false;
			}
			for (ShopItem s : list) {
				if (s.getShopId() == id) {
					boolean flag = list.remove(s);
					cache.put(key, cachedResult);	// 保存更改
					return flag;
				}
			}
			return false;
		} else {
			return false;
		}
	}
	
	/**
	 * 获取收藏的教育咨询数据
	 * @return
	 */
	public List<ShopItem> getFavoEduInfo(String key) {
		if (TextUtils.isEmpty(key)){
			return null;
		}
		ShopItemListFetchedEvent cachedResult = cache.get(key,
				ShopItemListFetchedEvent.class);
		if (cachedResult != null) {
			return cachedResult.getShopList();
		} else {
			return null;
		}
	}
}

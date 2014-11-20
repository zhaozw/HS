package cardapp.com.android.housingsociety.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 保存ShopItem集合数据
 *
 * @author CardApp@ZuoQing
 *
 */
public class ShopItemListFetchedEvent implements Serializable{
	private static final long serialVersionUID = 1L;
	List<ShopItem> shopList;
	
	public ShopItemListFetchedEvent() {
	}
	
	public ShopItemListFetchedEvent(List<ShopItem> shopList) {
		this.shopList = shopList;
	}

	public List<ShopItem> getShopList() {
		return shopList;
	}

	public void setShopList(List<ShopItem> shopList) {
		this.shopList = shopList;
	}
	public String toString() {
		return shopList.toString();
	}
}

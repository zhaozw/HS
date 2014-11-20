package cardapp.com.android.housingsociety.bean;

import java.io.Serializable;

/**
 * 一个商品条目，不是商品详细信息，用于表示商品列表的一项信息
 *
 * @author CardApp@ZuoQing
 *
 */
public class ShopItem implements Serializable {
	private static final long serialVersionUID = 1L;
	String ChiName;		// 繁体中文名称
	String SimChiName;		// 简体中文名称
	String EngName;		// 英文名称
	Long ShopId;		// 对应ID
	String UserId;		// 对应商户拥有者ID
	String ShopIconPath;		// 
	Boolean IsOnlineCall;		// 是否可在线预订
	Boolean IsTop;		// false, 
	Boolean IsIndexshow;		// false, 
	Boolean OrderIndex;		// false, 
    String ShopClassChiName;		// 購物", 
    String ShopClassSimChiName;		// 购物", 
    String ShopClassEngName;		// Shopping", 
	public String getChiName() {
		return ChiName;
	}
	public void setChiName(String chiName) {
		ChiName = chiName;
	}
	public String getSimChiName() {
		return SimChiName;
	}
	public void setSimChiName(String simChiName) {
		SimChiName = simChiName;
	}
	public String getEngName() {
		return EngName;
	}
	public void setEngName(String engName) {
		EngName = engName;
	}
	public Long getShopId() {
		return ShopId;
	}
	public void setShopId(Long shopId) {
		ShopId = shopId;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getShopIconPath() {
		return ShopIconPath;
	}
	public void setShopIconPath(String shopIconPath) {
		ShopIconPath = shopIconPath;
	}
	public Boolean getIsOnlineCall() {
		return IsOnlineCall;
	}
	public void setIsOnlineCall(Boolean isOnlineCall) {
		IsOnlineCall = isOnlineCall;
	}
	public Boolean getIsTop() {
		return IsTop;
	}
	public void setIsTop(Boolean isTop) {
		IsTop = isTop;
	}
	public Boolean getIsIndexshow() {
		return IsIndexshow;
	}
	public void setIsIndexshow(Boolean isIndexshow) {
		IsIndexshow = isIndexshow;
	}
	public Boolean getOrderIndex() {
		return OrderIndex;
	}
	public void setOrderIndex(Boolean orderIndex) {
		OrderIndex = orderIndex;
	}
	public String getShopClassChiName() {
		return ShopClassChiName;
	}
	public void setShopClassChiName(String shopClassChiName) {
		ShopClassChiName = shopClassChiName;
	}
	public String getShopClassSimChiName() {
		return ShopClassSimChiName;
	}
	public void setShopClassSimChiName(String shopClassSimChiName) {
		ShopClassSimChiName = shopClassSimChiName;
	}
	public String getShopClassEngName() {
		return ShopClassEngName;
	}
	public void setShopClassEngName(String shopClassEngName) {
		ShopClassEngName = shopClassEngName;
	}
    
}

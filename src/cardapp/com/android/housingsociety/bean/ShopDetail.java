package cardapp.com.android.housingsociety.bean;

import java.io.Serializable;

/**
 * 商品详细
 *
 * @author CardApp@ZuoQing
 *
 */
public class ShopDetail implements Serializable{
	private static final long serialVersionUID = 1L;
	Long ShopId;		// ;		//  213, 
    String UserId;		// ;		//  "61630D7ACA915ED8", 
    String ChiName;		// ;		//  "EC Foods", 
    String SimChiName;		// ;		//  "即将推出       ", 
    String EngName;		// ;		//  "EC Foods", 
    String ShopClassChiName;		// ;		//  "購物", 
    String ShopClassSimChiName;		// ;		//  "购物", 
    String ShopClassEngName;		// ;		//  "Shopping", 
    String Phone;		// ;		//  "23078388", 
    String Email;		// ;		//  "enquiry@bubblyenglish.com", 
    String LocationAxis;		// ;		//  "22.323023,114.160674", 
    Long ShopClassId;		// ;		//  0, 
    String ShopUploadFolder;		// ;		//  "20141028181215", 
    String ShopIconPath;		// ;		//  "http://merchantadmin.cardapp.com.hk/uploads/20141028181215/edfaa600-e268-488b-89e5-d77cf1b11445.jpg", 
    String ChiDesc;		// ;		//  "<p>欢迎使用ueditor!</p>", 
    String SimChiDesc;		// ;		//  "<p>欢迎使用ueditor!</p>", 
    String EngDesc;		// ;		//  "<p>欢迎使用ueditor!</p>", 
    String LastUpdateTime;		// ;		//  "2014-10-28T18:12:15", 
    Boolean IsTop;		// ;		//  false, 
    Boolean IsIndexshow;		// ;		//  false, 
    Boolean OrderIndex;		// ;		//  false, 
    String StampIcon;		// ;		//  "", 
    String EngStamp;		//  "", 
    String ChiStamp;		//  "", 
    String SimChiStamp;		//  "", 
    String EngCouponPdf;		//  "", 
    String ChiCouponPdf;		//  "", 
    String SimChiCouponPdf;		//  "", 
    String EngLaudName;		//  "", 
    String ChiLaudName;		//  "", 
    String SimChiLaudName;		//  "", 
    String EngLaudCaption;		//  "", 
    String ChiLaudCaption;		//  "", 
    String SimChiLaudCaption;		//  "", 
    String EngLaudDescription;		//  "", 
    String ChiLaudDescription;		//  "", 
    String SimChiLaudDescription;		//  "", 
    String EngLaudMessage;		//  "", 
    String ChiLaudMessage;		//  "", 
    String SimChiLaudMessage;		//  "", 
    String WebLink;		//  "", 
    String ImageList;		//  "http://merchantadmin.cardapp.com.hk/uploads/20141028181215/25355f4f-62fe-491d-a3c4-f5989d74b07f.jpg", 
    Boolean IsOnlineCall;		//  false
    Long Integral;		// 浏览可获取积分
	public Long getIntegral() {
		return Integral;
	}
	public void setIntegral(Long integral) {
		Integral = integral;
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
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getLocationAxis() {
		return LocationAxis;
	}
	public void setLocationAxis(String locationAxis) {
		LocationAxis = locationAxis;
	}
	public Long getShopClassId() {
		return ShopClassId;
	}
	public void setShopClassId(Long shopClassId) {
		ShopClassId = shopClassId;
	}
	public String getShopUploadFolder() {
		return ShopUploadFolder;
	}
	public void setShopUploadFolder(String shopUploadFolder) {
		ShopUploadFolder = shopUploadFolder;
	}
	public String getShopIconPath() {
		return ShopIconPath;
	}
	public void setShopIconPath(String shopIconPath) {
		ShopIconPath = shopIconPath;
	}
	public String getChiDesc() {
		return ChiDesc;
	}
	public void setChiDesc(String chiDesc) {
		ChiDesc = chiDesc;
	}
	public String getSimChiDesc() {
		return SimChiDesc;
	}
	public void setSimChiDesc(String simChiDesc) {
		SimChiDesc = simChiDesc;
	}
	public String getEngDesc() {
		return EngDesc;
	}
	public void setEngDesc(String engDesc) {
		EngDesc = engDesc;
	}
	public String getLastUpdateTime() {
		return LastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		LastUpdateTime = lastUpdateTime;
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
	public String getStampIcon() {
		return StampIcon;
	}
	public void setStampIcon(String stampIcon) {
		StampIcon = stampIcon;
	}
	public String getEngStamp() {
		return EngStamp;
	}
	public void setEngStamp(String engStamp) {
		EngStamp = engStamp;
	}
	public String getChiStamp() {
		return ChiStamp;
	}
	public void setChiStamp(String chiStamp) {
		ChiStamp = chiStamp;
	}
	public String getSimChiStamp() {
		return SimChiStamp;
	}
	public void setSimChiStamp(String simChiStamp) {
		SimChiStamp = simChiStamp;
	}
	public String getEngCouponPdf() {
		return EngCouponPdf;
	}
	public void setEngCouponPdf(String engCouponPdf) {
		EngCouponPdf = engCouponPdf;
	}
	public String getChiCouponPdf() {
		return ChiCouponPdf;
	}
	public void setChiCouponPdf(String chiCouponPdf) {
		ChiCouponPdf = chiCouponPdf;
	}
	public String getSimChiCouponPdf() {
		return SimChiCouponPdf;
	}
	public void setSimChiCouponPdf(String simChiCouponPdf) {
		SimChiCouponPdf = simChiCouponPdf;
	}
	public String getEngLaudName() {
		return EngLaudName;
	}
	public void setEngLaudName(String engLaudName) {
		EngLaudName = engLaudName;
	}
	public String getChiLaudName() {
		return ChiLaudName;
	}
	public void setChiLaudName(String chiLaudName) {
		ChiLaudName = chiLaudName;
	}
	public String getSimChiLaudName() {
		return SimChiLaudName;
	}
	public void setSimChiLaudName(String simChiLaudName) {
		SimChiLaudName = simChiLaudName;
	}
	public String getEngLaudCaption() {
		return EngLaudCaption;
	}
	public void setEngLaudCaption(String engLaudCaption) {
		EngLaudCaption = engLaudCaption;
	}
	public String getChiLaudCaption() {
		return ChiLaudCaption;
	}
	public void setChiLaudCaption(String chiLaudCaption) {
		ChiLaudCaption = chiLaudCaption;
	}
	public String getSimChiLaudCaption() {
		return SimChiLaudCaption;
	}
	public void setSimChiLaudCaption(String simChiLaudCaption) {
		SimChiLaudCaption = simChiLaudCaption;
	}
	public String getEngLaudDescription() {
		return EngLaudDescription;
	}
	public void setEngLaudDescription(String engLaudDescription) {
		EngLaudDescription = engLaudDescription;
	}
	public String getChiLaudDescription() {
		return ChiLaudDescription;
	}
	public void setChiLaudDescription(String chiLaudDescription) {
		ChiLaudDescription = chiLaudDescription;
	}
	public String getSimChiLaudDescription() {
		return SimChiLaudDescription;
	}
	public void setSimChiLaudDescription(String simChiLaudDescription) {
		SimChiLaudDescription = simChiLaudDescription;
	}
	public String getEngLaudMessage() {
		return EngLaudMessage;
	}
	public void setEngLaudMessage(String engLaudMessage) {
		EngLaudMessage = engLaudMessage;
	}
	public String getChiLaudMessage() {
		return ChiLaudMessage;
	}
	public void setChiLaudMessage(String chiLaudMessage) {
		ChiLaudMessage = chiLaudMessage;
	}
	public String getSimChiLaudMessage() {
		return SimChiLaudMessage;
	}
	public void setSimChiLaudMessage(String simChiLaudMessage) {
		SimChiLaudMessage = simChiLaudMessage;
	}
	public String getWebLink() {
		return WebLink;
	}
	public void setWebLink(String webLink) {
		WebLink = webLink;
	}
	public String getImageList() {
		return ImageList;
	}
	public void setImageList(String imageList) {
		ImageList = imageList;
	}
	public Boolean getIsOnlineCall() {
		return IsOnlineCall;
	}
	public void setIsOnlineCall(Boolean isOnlineCall) {
		IsOnlineCall = isOnlineCall;
	}

}

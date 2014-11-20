package cardapp.com.android.housingsociety.net;

import java.util.Date;

/**
 * 商户访问服务器方法
 * 
 * 測試接口地址： http://merchantmobile.cardapp.com.hk/ShopOrderSystemService 注意：
 * IShopOrderSystemService _RemoteInterface.方法
 * 
 * Estate：屋苑代码 嘉亨湾---GrandPromenade UserId：商户接口返回值 Customer：若住户即住户UserId
 * 非住户即mac地址或者設備唯一標識即可 ShopId：商户接口返回值 Ip:ip地址 ClientType：1.IOS 2.Android
 * 3.网站-----此處爲數字1/2/3
 * 
 * @author CardApp@ZuoQing
 * 
 */
public interface ShopService {

	/**
	 * 得到同类型商户分类
	 * 
	 * ShopClassId：long 对应ID ChiName：string 中文名称 EngName：string 英文名称
	 * ChiDesc：string 中文描述 EngDesc：string 英文描述
	 * 
	 * @param Estate
	 *            屋苑代码(GrandPromenade、LeyburnVillas)
	 * @param type
	 *            同類型標誌【LeyburnVillas（FB、EO）】
	 * @return
	 */
	//String getShopClassList(String estate, String type);

	/**
	 * 根据商户类别ID得到商户列表
	 * 
	 * ChiName：string 繁体中文名称 SimChiName：string 简体中文名称 EngName：string 英文名称
	 * ShopId：long 对应ID UserId：string 对应商户拥有者ID ShopIconPath：string
	 * IsOnlineCall：bool 是否可在线预订
	 * 
	 * @param shopId
	 *            首次为0 第一次获取列表得到的第一条数据中Id的值
	 * @param page
	 *            第几页，首次为1
	 * @param classId
	 *            商户分类ID（当type=0时可随便）
	 * @param Estate
	 *            屋苑代码（当type=1时可随便）(GrandPromenade、LeyburnVillas)
	 * @param ClassEngName
	 *            分类英文名称（当type=1时可随便）
	 * @param type
	 *            0.无classId 1.已有classId
	 * @return
	 */
	String getShopsListByClass(long shopId, long page, long classId,
			String estate, String classEngName, int type);

	/**
	 * 根据商户ID得到商户详细信息
	 * 
	 * ShopId：long 对应ID UserId：string 对应商户拥有者ID ChiName：string 繁体中文名称
	 * SimChiName：string 简体中文名称 EngName：string 英文名称 Email：string 电邮 Phone：string
	 * 电话 LocationAxis：string 坐标 ChiDesc：string 繁体中文描述 SimChiDesc：string 简体中文描述
	 * EngDesc：string 英文描述 ShopIconPath StampIcon EngStamp ChiStamp SimChiStamp
	 * EngCouponPdf ChiCouponPdf SimChiCouponPdf EngLaudName ChiLaudName
	 * SimChiLaudName EngLaudCaption ChiLaudCaption SimChiLaudCaption
	 * EngLaudDescription ChiLaudDescription SimChiLaudDescription
	 * EngLaudMessage ChiLaudMessage SimChiLaudMessage WebLink LastUpdateTime
	 * ShopUploadFolder ImageList：string 图片数组 IsOnlineCall：bool 是否可在线预订
	 * 
	 * @param shopId
	 *            商户ID
	 * @return
	 */
	String getShopDetails(long shopId);

	/**
	 * 收件夹
	 * 
	 * ResultOne：bool
	 * 
	 * @param Estate
	 *            屋苑代码
	 * @param Type
	 *            0.单一类别1.多类别
	 * @param ClassEngName
	 *            类别英文（当type=1 可随便）
	 * @param Classify
	 *            同類型標誌（当type=0 可随便）
	 * @param LastUpdateTime
	 *            最后一次查询时间
	 * @return
	 */
	String checkUpdateReturnBoolForShop(String estate, long type,
			String classEngName, String classify, Date lastUpdateTime);

	/**
	 * 得到列表
	 * 
	 * ShopId：long 对应ID ChiName：string 对应繁体中文名称 SimChiName：string 对应简体繁体中文名称
	 * EngName：string 英文
	 * 
	 * @param Estate	屋苑代码
	 * @param ClassEngName	类别英文（当type=1 可随便）
	 * @param LastUpdateTime	最后一次查询时间
	 * @return
	 */
	String inboxupdateForShop(String estate, String classEngName,
			Date lastUpdateTime);
	
	String doVisit(String appCode, String deviceIdentification, long type, long pointId, long integral, long clientType);

	String uploadAppErrorInfo(String appCode, String deviceIdentification, String errorType, String location, String errorMsg, long clientType);
}

/*
 * 測試接口地址：
http://merchantmobile.cardapp.com.hk/ShopOrderSystemService
注意： IShopOrderSystemService _RemoteInterface.方法

前台：
http://merchant.cardapp.com.hk/Chi/?Estate=XXX&UserId=XXX&Customer=XXX&ShopId=XXX&Ip=XXX&ClientType=XXX
http://merchant.cardapp.com.hk/Eng/?Estate=XXX&UserId=XXX&Customer=XXX&ShopId=XXX&Ip=XXX&ClientType=XXX


Estate：屋苑代码 嘉亨湾---GrandPromenade
UserId：商户接口返回值
Customer：若住户即住户UserId 非住户即mac地址或者設備唯一標識即可
ShopId：商户接口返回值
Ip:ip地址
ClientType：1.IOS 2.Android 3.网站-----此處爲數字1/2/3



一、String GetShopClassList(string Estate, string type)

1、作用：
得到同类型商户分类
2、参数：
Estate：屋苑代码(GrandPromenade、LeyburnVillas)
Type：同類型標誌【LeyburnVillas（FB、EO）】

3、返回值：
ShopClassId：long 对应ID
ChiName：string 中文名称
EngName：string 英文名称
ChiDesc：string 中文描述
EngDesc：string 英文描述

二、string GetShopsListByClass(long shopId, long page, long classId, string Estate, string ClassEngName, int type)

1、作用：
根据商户类别ID得到商户列表
2、参数：
shopId：首次为0 第一次获取列表得到的第一条数据中Id的值
page：第几页，首次为1
classId：商户分类ID（当type=0时可随便）
Estate：屋苑代码（当type=1时可随便）(GrandPromenade、LeyburnVillas)
ClassEngName：分类英文名称（当type=1时可随便）
Type：0.无classId 1.已有classId

3、返回值：
ChiName：string 繁体中文名称
SimChiName：string 简体中文名称
EngName：string 英文名称
ShopId：long 对应ID
UserId：string 对应商户拥有者ID
ShopIconPath：string
IsOnlineCall：bool 是否可在线预订

三、string GetShopDetails(long shopId)

1、作用：
根据商户ID得到商户详细信息
2、参数：
shopId：商户ID
3、返回值：
ShopId：long 对应ID
UserId：string 对应商户拥有者ID
ChiName：string 繁体中文名称
SimChiName：string 简体中文名称
EngName：string 英文名称
Email：string 电邮
Phone：string 电话
LocationAxis：string 坐标
ChiDesc：string 繁体中文描述
SimChiDesc：string 简体中文描述
EngDesc：string 英文描述
ShopIconPath
StampIcon
EngStamp
ChiStamp
SimChiStamp
EngCouponPdf
ChiCouponPdf
SimChiCouponPdf
EngLaudName
ChiLaudName
SimChiLaudName
EngLaudCaption
ChiLaudCaption
SimChiLaudCaption
EngLaudDescription
ChiLaudDescription
SimChiLaudDescription
EngLaudMessage
ChiLaudMessage
SimChiLaudMessage
WebLink
LastUpdateTime
ShopUploadFolder
ImageList：string 图片数组
IsOnlineCall：bool 是否可在线预订


四、string CheckUpdateReturnBoolForShop(string Estate, long Type, string ClassEngName, string Classify, DateTime LastUpdateTime)

1、作用：
收件夹

2、参数：
Estate：屋苑代码
Type：0.单一类别1.多类别
ClassEngName：类别英文（当type=1 可随便）
Classify：同類型標誌（当type=0 可随便）
LastUpdateTime：最后一次查询时间

3、返回值：
ResultOne：bool 

五、string InboxupdateForShop(string Estate, string ClassEngName, DateTime LastUpdateTime)

1、作用：
得到列表
2、参数：
Estate：屋苑代码
ClassEngName：类别英文（当type=1 可随便）
LastUpdateTime：最后一次查询时间

3、返回值：
ShopId：long 对应ID
ChiName：string 对应繁体中文名称
SimChiName：string 对应简体繁体中文名称
EngName：string 英文

 * */
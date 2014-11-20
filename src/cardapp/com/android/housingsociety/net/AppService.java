package cardapp.com.android.housingsociety.net;

import java.util.Date;

/**
 * 访问服务器方法
 * 	登入相关-通告-收件夹
 *
 * @author CardApp@ZuoQing
 *
 */
public interface AppService {

	/**
	 * 获取EstateId
	 * 
	 * @param Estate
	 *            屋苑代码
	 * @return 当0是不存在，-1异常 	
	 */
	String getEstateId(String estate);

	/**
	 * 取密匙
	 * 
	 * @param username
	 *            用户名
	 * @return UserId: 用户名 Salt：String 密匙---加密
	 */
	String getUserLoginInfo(String username);

	/**
	 * 登入驗證。
	 * 
	 * LoginSuccess：bool UserChiDisplayName：String 中文 UserEngDisplayName：String
	 * 英文 UserId：long 用户ID EstateId：String 屋苑ID---标签"Estate"+ EstateId
	 * UserName：String 用户名 UserToken：String 交互密匙 定义：Shopping（购物）Education
	 * Information（教育资讯）Catering（餐饮）Other（其他）
	 * 
	 * @param username
	 *            用户名
	 * @param hashedValue
	 *            MD5加密值
	 * @param estateId
	 *            屋苑id
	 * @return
	 */
	String userLogin(String username, String hashedValue, String estateId);

	/**
	 * 根据屋苑ID得到通告分类
	 * 
	 * EstateId：String 屋苑ID NoticeClassId：long 对应ID NoticeClassChiName：String
	 * 中文名称 NoticeClassEngName：String 英文名称 NoticeCounts：long 对应通告数目
	 * 
	 * @param EstateId
	 *            屋苑ID
	 * @param type
	 * @return
	 */
	String getNoticeClasses(String estateId, String type);

	/**
	 * 根据通告分类ID得到对应通告列表
	 * 
	 * 返回值： ChiTitle：string 中文名称 EngTitle：string 英文名称
	 * EmergencyNoticeDeadline：date 紧急通告到期日 IsEmergencyNotice：bool 是否紧急通告
	 * IsImportantNotice：bool 是否重要通告 IsNewNotice：bool 是否新通告
	 * NewNoticeDeadline：date 新通告到期日 NoticeClassid：long 对应分类ID NoticeId：long
	 * 对应ID
	 * 
	 * 
	 * @param noticeId
	 *            首次为0 第一次获取列表得到的第一条数据中NoticeId的值
	 * @param page
	 *            第几页，首次为1
	 * @param noticeClassId
	 *            通告分类ID
	 * @return
	 */
	String getNoticeList(long noticeId, long page, long noticeClassId);

	/**
	 * 根据通告ID得到通告内容
	 * 
	 * ChiTitle：string 中文名称 EngTitle：string 英文名称 NoticeClassid：long 对应分类ID
	 * NoticeId：long 对应ID ContentFormat MobileChiContent MobileEngContent
	 * 
	 * @param noticeId
	 *            通告ID
	 * @return
	 */
	String getNoticeContent(long noticeId);

	/**
	 * 得到屋苑联系信息
	 * 
	 * 返回值： ChiTitle：string 中文标题 EngTitle：string 英文标题 ChiPhone：string 电话
	 * EngPhone：string 电话 Type：0:电话 1:传真 2: 电邮 3:网址 4:无内容
	 * 
	 * @param EstateId
	 *            屋苑ID
	 * @param classId
	 *            1:联络我们 2:设置中联络我们
	 * @return
	 */
	String getEstateInfo(String estateId, long classId);

	/**
	 * 收件夹
	 * 
	 * ResultOne：bool ResultTwo：bool
	 * 
	 * @param EstateId
	 *            屋苑ID
	 * @param ClassOneUpdateTime
	 *            物业服务处
	 * @param ClassTwoUpdateTime
	 *            立案法团
	 * @return
	 */
	String checkUpdateReturnBool(String estateId, Date classOneUpdateTime,
			Date classTwoUpdateTime);

	/**
	 * 通告收件夹
	 * 
	 * 返回值： ChiTitle：string 中文名称 EngTitle：string 英文名称 NoticeId：long 对应ID
	 * 
	 * @param EstateId
	 *            屋苑ID
	 * @param ClassId
	 *            1：物业服务处 2：立案法团
	 * @param LastUpdateTime
	 *            最后更新时间
	 * @return
	 */
	String inboxUpdateForNotice(String estateId, long classId,
			Date lastUpdateTime);

	/**
	 * 得到一级列表
	 * @param estateId
	 * @return
	 */
	String getNecessityClassList(String estateId);

	/**
	 * 根据一级ID得到二级列表
	 * @param necessityClassId
	 * @return
	 */
	String getNecessity(long necessityClassId);

	/**
	 * 根据二级ID得到三级列表
	 * @param necessityId
	 * @return
	 */
	String getNecessityList(long necessityId);

	/**
	 * 根据id得到消息
	 * @param messageId
	 * @return
	 */
	String getPushMsgById(long messageId);

	/**
	 * 发送email
	 * @param estateId
	 * @param address
	 * @param username
	 * @param email
	 * @param telephone
	 * @param type
	 * @param remark
	 * @return
	 */
	String sendEmail(String estateId, String address, String username,
			String email, String telephone, String type, String remark);
}

/*
 * 接口地址：http://hsmobile.cardapp.com.hk/HousingSocietyService

注意： IHousingSociety_RemoteInterface.方法

一、string GetEstateId(string Estate)

1、作用：
获取EstateId

2、参数：
Estate：屋苑代码

3、返回值：
当0是不存在，-1异常

二、string GetUserLoginInfo(string username)

1、作用：
取密匙

2、参数：
username：用户名

3、返回值：
UserId: 用户名
Salt：string 密匙---加密

三、string UserLogin(string username, string hashedValue)

1、作用：
登入驗證。

2、参数：
username：用户名
hashedValue：MD5加密值

3、返回值：
LoginSuccess：bool
UserChiDisplayName：string 中文
UserEngDisplayName：string 英文
UserId：long 用户ID
EstateId：string 屋苑ID---标签"Estate"+ EstateId
UserName：string 用户名
UserToken：string 交互密匙

定义：Shopping（购物）Education Information（教育资讯）Catering（餐饮）Other（其他）


四、string GetNoticeClasses(string EstateId, ,string type)

1、作用：
根据屋苑ID得到通告分类
Type=PS
2、参数：
EstateId：屋苑ID
3、返回值：
EstateId：string 屋苑ID
NoticeClassId：long 对应ID
NoticeClassChiName：string 中文名称
NoticeClassEngName：string 英文名称
NoticeCounts：long 对应通告数目

五、string GetNoticeList(long noticeId, long page, long noticeClassId)

1、作用：
根据通告分类ID得到对应通告列表
2、参数：
noticeId：首次为0 第一次获取列表得到的第一条数据中NoticeId的值
page：第几页，首次为1
noticeClassId：通告分类ID
3、返回值：
ChiTitle：string 中文名称
EngTitle：string 英文名称
EmergencyNoticeDeadline：date 紧急通告到期日
IsEmergencyNotice：bool 是否紧急通告
IsImportantNotice：bool 是否重要通告
IsNewNotice：bool 是否新通告
NewNoticeDeadline：date 新通告到期日
NoticeClassid：long 对应分类ID
NoticeId：long 对应ID

六、string GetNoticeContent(long noticeId)

1、作用：
根据通告ID得到通告内容
2、参数：
noticeId：通告ID
3、返回值：
ChiTitle：string 中文名称
EngTitle：string 英文名称
NoticeClassid：long 对应分类ID
NoticeId：long 对应ID
ContentFormat
MobileChiContent
MobileEngContent


七、string GetEstateInfo(string EstateId, long classId)

1、作用：
得到屋苑联系信息
2、参数：
EstateId:屋苑ID
classId：1:联络我们 2:设置中联络我们
3、返回值：
ChiTitle：string 中文标题
EngTitle：string 英文标题
ChiPhone：string 电话
EngPhone：string 电话
Type：0:电话 1:传真 2: 电邮 3:网址 4:无内容

八、string CheckUpdateReturnBool(string EstateId, DateTime ClassOneUpdateTime, DateTime ClassTwoUpdateTime);

1、作用：
收件夹
2、参数：
EstateId：屋苑ID
ClassOneUpdateTime：物业服务处
ClassTwoUpdateTime：立案法团

3、返回值：
ResultOne：bool
ResultTwo：bool

九、string InboxupdateForNotice(string EstateId, long ClassId, DateTime LastUpdateTime);


1、作用：
通告收件夹
2、参数：
EstateId：屋苑ID
ClassId：1：物业服务处 2：立案法团
LastUpdateTime：最后更新时间
3、返回值：
ChiTitle：string 中文名称
EngTitle：string 英文名称
NoticeId：long 对应ID

 */
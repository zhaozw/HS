package cardapp.com.android.housingsociety.bean;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author CardApp@ZuoQing
 *
 */
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	String UserName;				// 用户名
	Long UserId;					// 用户ID			
	Boolean LoginSuccess;			// 是否登陆成功		
	String UserToken;				// 交互密匙
	String UserChiDisplayName;		// 中文名
	String UserEngDisplayName;		// 英文名
	String EstateId;				// 屋苑ID
	String BuildingId;				// 
	
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public Long getUserId() {
		return UserId;
	}
	public void setUserId(Long userId) {
		UserId = userId;
	}
	public Boolean getLoginSuccess() {
		return LoginSuccess;
	}
	public void setLoginSuccess(Boolean loginSuccess) {
		LoginSuccess = loginSuccess;
	}
	public String getUserToken() {
		return UserToken;
	}
	public void setUserToken(String userToken) {
		UserToken = userToken;
	}
	public String getUserChiDisplayName() {
		return UserChiDisplayName;
	}
	public void setUserChiDisplayName(String userChiDisplayName) {
		UserChiDisplayName = userChiDisplayName;
	}
	public String getUserEngDisplayName() {
		return UserEngDisplayName;
	}
	public void setUserEngDisplayName(String userEngDisplayName) {
		UserEngDisplayName = userEngDisplayName;
	}
	public String getEstateId() {
		return EstateId;
	}
	public void setEstateId(String estateId) {
		EstateId = estateId;
	}
	public String getBuildingId() {
		return BuildingId;
	}
	public void setBuildingId(String buildingId) {
		BuildingId = buildingId;
	}
	
}

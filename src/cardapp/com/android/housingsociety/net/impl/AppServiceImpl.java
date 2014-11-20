package cardapp.com.android.housingsociety.net.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.androidannotations.annotations.EBean;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import android.text.TextUtils;
import cardapp.com.android.housingsociety.net.AppService;
import cardapp.com.android.housingsociety.util.MyNetUtil;

/**
 * AppService实现
 *
 * @author CardApp@ZuoQing
 *
 */
@EBean
public class AppServiceImpl implements AppService {
	Map<String, String> paraPaths = null; // save some constant

	/**
	 * 初始化常量
	 */
	public AppServiceImpl() {
		paraPaths = new HashMap<String, String>();
		paraPaths.put("WEBSERVICE_URL",
				"http://hsmobile.cardapp.com.hk/HousingSocietyService");
		paraPaths.put("INTERFACEPATH", "IHousingSociety_RemoteInterface/");
		paraPaths.put("NAMESPACE", "http://tempuri.org/");
		paraPaths.put("RESULTPROPERTY", "Result");
	}

	@Override
	public String getEstateId(String estate) {
		if (TextUtils.isEmpty(estate)) {
			return null;
		}
		paraPaths.put("METHODNAME", "GetEstateId");

		List<String> parameKeys = new ArrayList<String>();
		parameKeys.add("Estate");
		Map<String, Object> parameValues = new HashMap<String, Object>();
		parameValues.put("Estate", estate);

		String t = MyNetUtil.accessSoapServer(paraPaths, parameKeys,
				parameValues);
		if (!TextUtils.isEmpty(t) && t.startsWith("\"") && t.endsWith("\"")) {
			String estateId = t.trim().substring(1, t.length() - 1);
			return estateId;
		}
		return null;
	}

	@Override
	public String getUserLoginInfo(String username) {
		if (TextUtils.isEmpty(username)) {
			return null;
		}

		paraPaths.put("METHODNAME", "GetUserLoginInfo");
		List<String> parameKeys = new ArrayList<String>();
		Map<String, Object> parameValues = new HashMap<String, Object>();
		parameKeys.add("username");
		parameValues.put("username", username);

		String result = MyNetUtil.accessSoapServer(paraPaths, parameKeys,
				parameValues);

		// deal result
		try {
			if (!TextUtils.isEmpty(result)) {
				return new JSONObject(result).getString("Salt");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String userLogin(String username, String hashedValue, String estateId) {
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(hashedValue)
				|| TextUtils.isEmpty(estateId)) {
			return null;
		}

		// init param
		paraPaths.put("METHODNAME", "UserLogin");
		List<String> parameKeys = new ArrayList<String>();
		parameKeys.add("username");
		parameKeys.add("hashedValue");
		parameKeys.add("EstateId");
		Map<String, Object> parameValues = new HashMap<String, Object>();
		parameValues.put("username", username);
		parameValues.put("hashedValue", hashedValue);
		parameValues.put("EstateId", estateId);

		// access server to get result
		return MyNetUtil.accessSoapServer(paraPaths, parameKeys,
				parameValues);
	}

	@Override
	public String getNoticeClasses(String estateId, String type) {
		if (TextUtils.isEmpty(estateId) || TextUtils.isEmpty(type)
				|| TextUtils.isEmpty(estateId)) {
			return null;
		}

		// init param
		paraPaths.put("METHODNAME", "GetNoticeClasses");
		List<String> parameKeys = new ArrayList<String>();
		parameKeys.add("EstateId");
		parameKeys.add("type");
		Map<String, Object> parameValues = new HashMap<String, Object>();
		parameValues.put("EstateId", estateId);
		parameValues.put("type", type);

		// access server to get result
		return MyNetUtil.accessSoapServer(paraPaths, parameKeys,
				parameValues);
	}

	@Override
	public String getNoticeList(long noticeId, long page, long noticeclassId) {

		// init param
		paraPaths.put("METHODNAME", "GetNoticeList");
		List<String> parameKeys = new ArrayList<String>();
		parameKeys.add("noticeId");
		parameKeys.add("page");
		parameKeys.add("noticeClassId");
		Map<String, Object> parameValues = new HashMap<String, Object>();
		parameValues.put("noticeId", noticeId);
		parameValues.put("page", page);
		parameValues.put("noticeClassId", noticeclassId);

		// access server to get result
		return MyNetUtil.accessSoapServer(paraPaths, parameKeys,
				parameValues);
	}

	@Override
	public String getNoticeContent(long noticeId) {
		// init param
		paraPaths.put("METHODNAME", "GetNoticeContent");
		List<String> parameKeys = new ArrayList<String>();
		parameKeys.add("noticeId");
		Map<String, Object> parameValues = new HashMap<String, Object>();
		parameValues.put("noticeId", noticeId);

		// access server to get result
		return MyNetUtil.accessSoapServer(paraPaths, parameKeys,
				parameValues);
	}

	@Override
	public String getEstateInfo(String estateId, long classId) {
		// init param
		paraPaths.put("METHODNAME", "GetEstateInfo");
		List<String> parameKeys = new ArrayList<String>();
		parameKeys.add("EstateId");
		parameKeys.add("classId");
		Map<String, Object> parameValues = new HashMap<String, Object>();
		parameValues.put("EstateId", estateId);
		parameValues.put("classId", classId);

		// access server to get result
		return MyNetUtil.accessSoapServer(paraPaths, parameKeys,
				parameValues);
	}

	@Override
	public String checkUpdateReturnBool(String estateId,
			Date classOneUpdateTime, Date classTwoUpdateTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String inboxUpdateForNotice(String estateId, long classId,
			Date LastUpdateTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNecessityClassList(String estateId) {
		if (TextUtils.isEmpty(estateId)) {
			return null;
		}
		paraPaths.put("METHODNAME", "GetNecessityClassList");

		List<String> parameKeys = new ArrayList<String>();
		Map<String, Object> parameValues = new HashMap<String, Object>();
		parameKeys.add("EstateId");
		parameValues.put("EstateId", estateId);

		String t = MyNetUtil.accessSoapServer(paraPaths, parameKeys,
				parameValues);
		if (!TextUtils.isEmpty(t)) {
			return t;
		}
		return null;
	}

	@Override
	public String getNecessity(long necessityClassId) {
		
		paraPaths.put("METHODNAME", "GetNecessity");

		List<String> parameKeys = new ArrayList<String>();
		Map<String, Object> parameValues = new HashMap<String, Object>();
		parameKeys.add("necessityClassId");
		parameValues.put("necessityClassId", necessityClassId);

		String t = MyNetUtil.accessSoapServer(paraPaths, parameKeys,
				parameValues);
		//AppLogger.d(t);
		if (!TextUtils.isEmpty(t)) {
			return t;
		}
		return null;
	}

	@Override
	public String getNecessityList(long necessityId) {
		paraPaths.put("METHODNAME", "GetNecessityList");

		List<String> parameKeys = new ArrayList<String>();
		Map<String, Object> parameValues = new HashMap<String, Object>();
		parameKeys.add("necessityId");
		parameValues.put("necessityId", necessityId);

		String t = MyNetUtil.accessSoapServer(paraPaths, parameKeys,
				parameValues);
		//AppLogger.d(t);
		if (!TextUtils.isEmpty(t)) {
			return t;
		}
		return null;
	}

	@Override
	public String getPushMsgById(long messageId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sendEmail(String estateId, String address, String username,
			String email, String telephone, String type, String remark) {
		// TODO Auto-generated method stub
		return null;
	}

}

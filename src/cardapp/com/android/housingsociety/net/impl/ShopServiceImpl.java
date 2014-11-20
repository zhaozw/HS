package cardapp.com.android.housingsociety.net.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.EBean;

import android.text.TextUtils;
import cardapp.com.android.housingsociety.net.ShopService;
import cardapp.com.android.housingsociety.util.MyNetUtil;

/**
 * ShopService实现
 *
 * @author CardApp@ZuoQing
 *
 */
@EBean
public class ShopServiceImpl implements ShopService {
	Map<String, String> paraPaths = null;	// save some constant

	/**
	 * 初始化常量
	 */
	public ShopServiceImpl() {
		String cardapp = "http://merchantmobile.cardapp.com.hk";	// for test 
		//String hkcic = "http://merchantmobile.hk-cic.com";		// for business
		paraPaths = new HashMap<String, String>();
		paraPaths.put("WEBSERVICE_URL", cardapp + "/ShopOrderSystemService");
		paraPaths.put("INTERFACEPATH", "IShopOrderSystem_RemoteInterface/");
		paraPaths.put("NAMESPACE", "http://tempuri.org/");
		paraPaths.put("RESULTPROPERTY", "Result");
	}
	/*
	@Override
	public String getShopClassList(String estate, String type) {
		// 不用
		return null;
	}*/

	@Override
	public String getShopsListByClass(long shopId, long page, long classId,
			String estate, String classEngName, int type) {
		if (TextUtils.isEmpty(estate) || TextUtils.isEmpty(classEngName)) {
			return null;
		}

		// init param
		paraPaths.put("METHODNAME", "GetShopsListByClass");
		List<String> parameKeys = new ArrayList<String>();
		parameKeys.add("shopId");
		parameKeys.add("page");
		parameKeys.add("classId");
		parameKeys.add("Estate");
		parameKeys.add("ClassEngName");
		parameKeys.add("type");
		Map<String, Object> parameValues = new HashMap<String, Object>();
		parameValues.put("shopId", shopId);
		parameValues.put("page", page);
		parameValues.put("classId", classId);
		parameValues.put("Estate", estate);
		parameValues.put("ClassEngName", classEngName);
		parameValues.put("type", type);

		// access server to get result
		return MyNetUtil.accessSoapServer(paraPaths, parameKeys,
				parameValues);
	}

	@Override
	public String getShopDetails(long shopId) {
		// init param
		paraPaths.put("METHODNAME", "GetShopDetails");
		List<String> parameKeys = new ArrayList<String>();
		parameKeys.add("shopId");
		Map<String, Object> parameValues = new HashMap<String, Object>();
		parameValues.put("shopId", shopId);

		// access server to get result
		return MyNetUtil.accessSoapServer(paraPaths, parameKeys,
				parameValues);
	}

	@Override
	public String checkUpdateReturnBoolForShop(String estate, long type,
			String classEngName, String classify, Date lastUpdateTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String inboxupdateForShop(String estate, String classEngName,
			Date lastUpdateTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String doVisit(String appCode, String deviceIdentification,
			long type, long pointId, long integral, long clientType) {
		// init param
		paraPaths.put("METHODNAME", "DoVisit");
		List<String> parameKeys = new ArrayList<String>();
		parameKeys.add("AppCode");
		parameKeys.add("DeviceIdentification");
		parameKeys.add("Type");
		parameKeys.add("PointId");
		parameKeys.add("Integral");
		parameKeys.add("ClientType");
		Map<String, Object> parameValues = new HashMap<String, Object>();
		parameValues.put("AppCode", appCode);
		parameValues.put("DeviceIdentification", deviceIdentification);
		parameValues.put("Type", type);
		parameValues.put("PointId", pointId);
		parameValues.put("Integral", integral);
		parameValues.put("ClientType", clientType);

		// access server to get result
		return MyNetUtil.accessSoapServer(paraPaths, parameKeys,
				parameValues);
	}

	@Override
	public String uploadAppErrorInfo(String appCode,
			String deviceIdentification, String errorType, String location,
			String errorMsg, long clientType) {
		// init param
		paraPaths.put("METHODNAME", "UploadAppErrorInfo");
		List<String> parameKeys = new ArrayList<String>();
		parameKeys.add("AppCode");
		parameKeys.add("DeviceIdentification");
		parameKeys.add("ErrorType");
		parameKeys.add("Location");
		parameKeys.add("ErrorMsg");
		parameKeys.add("ClientType");
		Map<String, Object> parameValues = new HashMap<String, Object>();
		parameValues.put("AppCode", appCode);
		parameValues.put("DeviceIdentification", deviceIdentification);
		parameValues.put("ErrorType", errorType);
		parameValues.put("Location", location);
		parameValues.put("ErrorMsg", errorMsg);
		parameValues.put("ClientType", clientType);

		// access server to get result
		return MyNetUtil.accessSoapServer(paraPaths, parameKeys,
				parameValues);
	}

	

}

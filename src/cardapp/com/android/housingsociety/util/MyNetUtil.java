package cardapp.com.android.housingsociety.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import cardapp.com.android.housingsociety.debug.AppLogger;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 访问网络的工具 FUNCTION: 1. 判断是否能连接服务器 2. 获取服务器数据
 * 
 *
 * @author CardApp@ZuoQing
 *
 */
public class MyNetUtil {

	/**
	 * check connect server whether enable
	 * 
	 * @return true, can connnect server
	 */
	public static boolean serverEnable(Context context) {
		// 检查网络
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 访问服务器
	 * 
	 * @param paraPaths
	 *            包含地址，接口，方法，命名空间，获取的结果等参数
	 * @param parameKeys
	 *            参数集合
	 * @param parameValues
	 *            参数值集合
	 * @return 获取指定结果字符串
	 */
	public static String accessSoapServer(Map<String, String> paraPaths,
			List<String> parameKeys, Map<String, Object> parameValues) {
		
		if (parameKeys == null || parameValues == null || paraPaths == null) {
			return null;
		}
		// 1、根据webservice的命名空间和调用的方法名，创建soapObj请求对象;
		SoapObject soapObj_request = new SoapObject(paraPaths.get("NAMESPACE"),
				paraPaths.get("METHODNAME"));

		// 2、设置调用方法的参数值;
		StringBuilder sb = new StringBuilder();
		for (String key : parameKeys) {
			sb.append("key:"+key + "###" +"value:"+parameValues.get(key) + "\n");
			soapObj_request.addProperty(key, parameValues.get(key));
		}

		// 3、创建envelope对象，关联soapObj请求
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// envelope.encodingStyle = "UTF-8";
		// envelope.bodyOut = soapObj_request;
		envelope.setOutputSoapObject(soapObj_request);
		envelope.encodingStyle = SoapSerializationEnvelope.ENC;

		// 4、注册信封对象
		(new MarshalBase64()).register(envelope);

		String SOAP_ACTION = paraPaths.get("NAMESPACE")
				+ paraPaths.get("INTERFACEPATH") + paraPaths.get("METHODNAME");
//AppLogger.v("SOAP_ACTION:" + SOAP_ACTION);

		// 5、创建HttpTransportSE对象，发出请求
		HttpTransportSE ht = new HttpTransportSE(
				paraPaths.get("WEBSERVICE_URL"));
		ht.debug = true;
		try {
			ht.call(SOAP_ACTION, envelope);
		} catch (HttpResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//AppLogger.v(envelope.bodyIn.toString());
		// 6、获得结果
		SoapObject soapObj_result = null;
		try {
			soapObj_result = (SoapObject) envelope.bodyIn;
		} catch (Exception e) {
			e.printStackTrace();
			AppLogger.v("############## java.lang.ClassCastException: org.ksoap2.SoapFault cannot be cast to org.ksoap2.serialization.SoapObject #####################");
		}

		if (soapObj_result == null) {
			return null;
		}

		if (soapObj_result.getPropertyCount() == 0) {
			return null;
		}

		// LoginResponse{LoginResult={"Tag":"estate3","LoginSuccess":0}; }
		try {
			String result = soapObj_result.getPropertyAsString(paraPaths
					.get("METHODNAME") + paraPaths.get("RESULTPROPERTY"));
			AppLogger.v("########### Method:"+paraPaths.get("METHODNAME")+" ##############");
			AppLogger.v(sb.toString());
			AppLogger.v("########### Result:"+result+" ##############");
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}

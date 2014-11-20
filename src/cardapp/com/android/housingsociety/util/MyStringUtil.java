package cardapp.com.android.housingsociety.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import android.text.TextUtils;

/**
 * MD5 加密
 * 	function:
 * 		1. MD5加密，并转成大小写
 * 
 * @author CardApp@ZuoQing
 * 
 */
public class MyStringUtil {

	/**
	 * 小写密文
	 * @param plainText 明文
	 * @return	32位小写密文
	 */
	public static String encryptingByMD5For32BitLower(String plainText) {
		if (TextUtils.isEmpty(plainText)) {
			return "";
		}
		return encryptingByMD5For32Bit(plainText).toLowerCase(Locale.US);
	}

	/**
	 * 大写密文
	 * @param plainText 明文
	 * @return	32位大写密文
	 */
	public static String encryptingByMD5For32BitUpper(String plainText) {
		if (TextUtils.isEmpty(plainText)) {
			return "";
		}
		return encryptingByMD5For32Bit(plainText).toUpperCase(Locale.US);
	}

	/**
	 * MD5 加密
	 * 
	 * @param plainText
	 *            明文
	 * @return 32位密文
	 */
	private static String encryptingByMD5For32Bit(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return re_md5;

	}
}

package com.lxq.platform.util;

import java.security.MessageDigest;

/**
 * MD5工具类
 * @author xueqingli@foxmail.com
 */
public class MD5 {
	
	/**ʮ���������s十六进制数字*/
	private final static String[] hexDigits = { 
		"0", "1", "2", "3",
		"4", "5","6", "7",
		"8", "9", "a", "b",
		"c", "d", "e", "f" 
	};
	
	/**
	 * 字节数组转化为十六进制字符串
	 * @param b 字节数组
	 * @return 十六进制字符串
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 一个字节转化十六进制字符串
	 * @param b 字节
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 将数组的字符串转化为md5编码后的字符串
	 * @param origin 要编码的字符串
	 * @return md5编码后的字符串
	 */
	public static String encode(String origin) {
		String resultString = null;

		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes("UTF-8")));
		} catch (Exception ex) {

		}
		return resultString;
	}

}
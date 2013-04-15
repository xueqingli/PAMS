package com.lxq.platform.util;

import java.io.UnsupportedEncodingException;

public class DataConvert {

	public static String toString(Object data) {
		if (data == null) {
			return "";
		} else {
			return data.toString();
		}
	}

	public static String complementSpace(int size) {
		String spaceString = "";
		for (int i = 0; i < size; i++) {
			spaceString = spaceString + " ";
		}
		return spaceString;
	}

	public static String complementSpace(String str, int length , String encoding) {

		int size = getBytes(str , encoding).length;
		for (int i = size; i < length; i++) {
			str = str + " ";
		}
		return str;
	}

	public static String leftComplementZero(String str , int length){
		int size = str.length();
		for (int i = size; i < length; i++) {
			str = "0"+str;
		}
		return str;
	}
	
	public static byte[] getBytes(String str , String encoding) {
		try {
			return str.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			return str.getBytes();
		}
	}

}

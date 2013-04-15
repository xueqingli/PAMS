package com.lxq.platform.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射机制工具类
 * @author xueqingli@foxmail.com
 */
public class ReflectUtil {
	
	/**
	 * 根据类，和属性名称，获取属性的类型
	 * @param c 类
	 * @param propertyName 属性名称
	 * @return 属性类型
	 * @throws NoSuchMethodException 
	 */
	public static Class<?> getPropertyType(Class<?> c ,String propertyName) throws SecurityException, NoSuchMethodException{
		Method method = null;
		try{//boolean之外的类型getXXX
			String methodName = "get"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
			method = c.getMethod(methodName);
		}catch(NoSuchMethodException e){//boolean类型isXXX
			String methodName = "is"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
			method = c.getMethod(methodName);
		}
		return method.getReturnType();
	}

	/**
	 * 根据属性名称获取属性值
	 * @param c 类
	 * @param o 对象
	 * @param propertyName 属性名称
	 * @return 属性值
	 * @throws NoSuchMethodException 
	 */
	public static Object getPropertyValue(Class<?> c,Object o ,String propertyName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
		if(o == null){
			return "";
		}
		
		Method method = null;
		try{//boolean之外的类型getXXX
			String methodName = "get"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
			method = c.getMethod(methodName);
		}catch(NoSuchMethodException e){//boolean类型isXXX
			String methodName = "is"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
			method = c.getMethod(methodName);
		}
		return method.invoke(o);
	}

}

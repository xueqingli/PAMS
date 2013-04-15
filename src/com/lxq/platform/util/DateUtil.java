package com.lxq.platform.util;

import java.util.*;
import java.text.SimpleDateFormat;

/**
 * 日期处理工具类
 * @author lizi
 *
 */
public class DateUtil {
	
	public static final String FULL_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String NOMAL_DATE_FORMAT = "yyyy-MM-dd";
	public static final String SHORT_DATE_FORMAT = "yyyy-MM";
	  
    /**
     * 获取当前时间，精确到毫秒
     *
     * @return 
     */
    public static String getNowTime() {
        return getNowTime(DateUtil.FULL_DATETIME_FORMAT);
    }
    
    /**
     * 根据指定日期格式获取当前时间，精确到毫秒
     * @param sFormat 日期格式
     * @return
     */
    public static String getNowTime(String sFormat) {
    	SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
    	Calendar c = Calendar.getInstance();
    	c.setTime(new Date());
    	return sdf.format(c.getTime());
    }
    
    /**
     * 根据指定日期格式获取当前日期
     * @param sFormat 日期格式
     * @return
     */
    public static String getToday(String sFormat) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        String s1 = sdf.format(gc.getTime());
        return s1;
    }

    /**
     * 获取当前日期
     * @return
     */
    public static String getToday() {
        return getToday(DateUtil.NOMAL_DATE_FORMAT);
    }

    /**
     * 针对日期对象的日期推移计算，如昨天、上月等
     *
     * @param date
     * @param iYear
     * @param iMonth
     * @param iDate
     * @param sFormat
     * @return
     */
    public static String getRelativeDate(java.util.Date date, int iYear, int iMonth, int iDate, String sFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(sFormat);            //定义格式
        GregorianCalendar gc = new GregorianCalendar();            //

        gc.setTime(date);           //设置时间

        gc.add(Calendar.YEAR, iYear);               //算术求和
        gc.add(Calendar.MONTH, iMonth);
        gc.add(Calendar.DATE, iDate);

        return sdf.format(gc.getTime());
    }

    public static String getRelativeDate(String sDate, int iYear, int iMonth, int iDate, String sFormat)
    {
    	//System.out.println("getRelativeDate:"+sDate);
    	if(sDate==null) return null;
        Date date = parseString2Date(sDate, DateUtil.NOMAL_DATE_FORMAT );
        return getRelativeDate(date, iYear, iMonth, iDate, sFormat);
    }

    public static String getRelativeDate(java.util.Date date, int iYear, int iMonth, int iDate) {
        return getRelativeDate(date, iYear, iMonth, iDate, DateUtil.NOMAL_DATE_FORMAT);
    }

    public static String getRelativeDate(String sDate, int iYear, int iMonth, int iDate)
    {
        return getRelativeDate(sDate, iYear, iMonth, iDate, DateUtil.NOMAL_DATE_FORMAT);
    }

    public static String getRelativeMonth(java.util.Date date, int iYear, int iMonth, String s) {
        return getRelativeDate(date, iYear, iMonth, 0, s);
    }

    public static String getRelativeMonth(String sDate, int iYear, int iMonth, String s)
    {
        return getRelativeDate(sDate, iYear, iMonth, 0, s);
    }

    public static String getRelativeMonth(java.util.Date date, int iYear, int iMonth) {
        return getRelativeDate(date, iYear, iMonth, 0, DateUtil.SHORT_DATE_FORMAT);
    }

    public static String getRelativeMonth(String sDate, int iYear, int iMonth)
    {
        return getRelativeDate(sDate, iYear, iMonth, 0, DateUtil.SHORT_DATE_FORMAT);
    }

    //判断是否为月末
    public static boolean monthEnd(String sEndDate)
    {
    	String sTommorow = getRelativeDate(sEndDate, 0, 0, 1);
    	if(sTommorow == null){ return false;}
        if (sTommorow.substring(8, 10).equals("01"))
            return true;
        else
            return false;

    }

    /**
     * 转化日期字符串的格式
     * @param sDate
     * @param format1
     * @return
     */
    public static String formatDate(String sDate, String format) {
    	
    	return formatDate(sDate,NOMAL_DATE_FORMAT,format);
    }
    
    /**
     * 转化日期字符串的格式
     * @param sDate
     * @param format1
     * @param format2
     * @return
     */
    public static String formatDate(String sDate,String format1 , String format2) {
        
    	Date date = parseString2Date(sDate,format1);
    	
    	return new SimpleDateFormat(format2).format(date);
    }

    /**
     * 将日期字符转化为Date对象
     * @param datestring
     * @return
     */
    public static Date parseString2Date(String datestring)
    {
        return parseString2Date(datestring, DateUtil.NOMAL_DATE_FORMAT);
    }

    /**
     * 将日期字符按指定格式转换为Date对象
     *
     * @param datestring 日期字符串 
     * @param format 日期格式
     * @return
     */
    public static Date parseString2Date(String datestring, String format)
    {
    	if (datestring==null) return null;
        try {
            Date date = new SimpleDateFormat(format).parse(datestring);
            return date;
        } catch (Exception e) {
        	System.out.println("日期转化'" + datestring + "'转换异常" + e);
        	return null;
        }
    }
}

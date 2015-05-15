package com.sq.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jfinal.log.Logger;

public class MyTimeUtils {

	public static final long ONEDAY = 1000*60*60*24;
	public static SimpleDateFormat BOTH = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String zuheTime(String startTime2) {
		String type1 = "yyyy-MM-dd HH:mm:ss";
		String type2 = "yyyy-MM-dd HH:mm";
		String type3 = "yyyy-MM-dd HH";
		String type4="yyyy-MM-dd";
		if(type1.length() == startTime2.length()){
			return startTime2;
		}else if(type2.length() == startTime2.length()){
			return startTime2+":00";
		}else if(type3.length() == startTime2.length()){
			return startTime2+":00:00";
		}else if(type4.length() == startTime2.length()){
			return startTime2+" 00:00:00";
		}else {
			return null;
		}
	}

	public static String getBothTime(String startTime, int hours) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(startTime);
			Date retDate = new Date(date.getTime()+hours*3600000l);
			return sdf.format(retDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date toDateStyleBoth(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getYYYYMMDD(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}
	
	public static String toDateStyleBoth(Date dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(dateStr);
	}
	/**
	 * 获取上个月
	 * @return 年+月（如201408）
	 */
	public static String getLastMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);//取得系统当前时间所在月第一天时间对象
		cal.add(Calendar.DAY_OF_MONTH, -1);//日期减一,取得上月最后一天时间对象
		int year=cal.get(Calendar.YEAR);//获取年
	    int month=cal.get(Calendar.MONTH)+1;//获取上个月
	    if (month<10) {
	    	return year+"0"+month;
		}else {
			return year+""+month;
		}
	}
	/**
	 * 查询该月份最后一天的时间
	 * @param yearMonth 如201408、20140816
	 * @return Date 如20140831的Date
	 */
	public static Date getMonthLastDay(String yearMonth) {
		int year;
		int month;
		if (yearMonth.length()==6) {
			year = Integer.valueOf(yearMonth.substring(0, 4));
			month = Integer.valueOf(yearMonth.substring(4, 6));
		}else if (yearMonth.length()==8) {
			year = Integer.valueOf(yearMonth.substring(0, 4));
			month = Integer.valueOf(yearMonth.substring(4, 6));
		}else {
			return null;
		}
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);//某一年
		calendar.set(Calendar.MONTH,month-1);//某一月(1月份是0)
		int end=calendar.getActualMaximum(calendar.DAY_OF_MONTH);
		SimpleDateFormat datef=new SimpleDateFormat("yyyy-MM-dd");
		try {
			return datef.parse(year+"-"+month+"-"+end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
//	public static String createTime(long now , int hours){
//		long ti = hours * 3600000l;
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		try {
//			Date date = sdf.parse(now);
//			long newTime = date.getTime()+ti;
//			return sdf.format(new Date(newTime));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
}

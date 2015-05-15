package com.sq.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateFormatUtils {

	public static SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat sdf_hour = new SimpleDateFormat("yyyy-MM-dd HH");
	public static SimpleDateFormat sdf_all  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static boolean isToday(String date){
		if(StringUtils.isBlank(date)){
			return false;
		}
		if(date.length() != 10){
			try {
				date = sdf_date.format(sdf_date.parse(date));
			} catch (ParseException e) {
				System.out.println(date+"解析失败");
				return false;
			}
		}
		if(date.equals(sdf_date.format(new Date()))){
			return true;
		}
		return false;
	}

	public static String getHourFormat(String string) {
		try {
			return sdf_hour.format(sdf_hour.parse(string));
		} catch (ParseException e) {
			return null;
		}
	}
}

package com.sq.common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;

import com.jfinal.kit.PathKit;
import com.sq.utils.PropertiesUtils;

public class Constant {

	public static String LOG_QUERY_FILE_TYPE_SHELL  = "shell";
	public static String LOG_QUERY_FILE_TYPE_WEB = "web";
	public static String SQ_SEPARATOR = "SQ_SE_SQ";
	public static String HAIWAI_FLAG = "HAIWAIFLAG";
	public static String INTERNATIONAL_SHOW_BOTH = "both";
	public static String INTERNATIONAL_SHOW_FOREIGN = "foreign";
	public static String INTERNATIONAL_SHOW_LOCALE = "locale";
	public static int SHELL_STATUS_READY = 0;
	public static int SHELL_STATUS_CHECK_BASIC = 1;
	public static int SHELL_STATUS_DONE = 2;
	public static int SHELL_STATUS_ERROR = 3;
	private static String interMessageShowType;
	public static boolean isHaiwai = false;
	private static String needInternationalFlag ;
	private static boolean isNeedInternational;
	public static int SHELL_STATUS_QUERYING = 4;
	public static int SHELL_STATUS_CHECK_FILE = 1;
	private static Map<String,String> tableMap = new HashMap<String,String>();
	private static Map<String,String[]> showTablesMap = new HashMap<String,String[]>();
	public static String okPath = PathKit.getRootClassPath()+"/index/ok/";
	public static String getInterMessageShowType(){
		if(interMessageShowType == null){
			try {
				interMessageShowType =PropertiesUtils.getPropertiesValue("jdbc.properties", "INTERNATIONAL_SHOW");
			} catch (Exception e) {
				e.printStackTrace();
				return INTERNATIONAL_SHOW_LOCALE;
			}
			if(interMessageShowType == null)
				return INTERNATIONAL_SHOW_LOCALE;
		}
		
		return interMessageShowType;
	}
	
	public static boolean needInternational(){
		if(needInternationalFlag == null){
			isNeedInternational = Boolean.valueOf(PropertiesUtils.getPropertiesValue("jdbc.properties", "INTERNATIONAL_NEED"));
		}
		return isNeedInternational;
	}

	public static String getTableName(String type) {
		if(tableMap.get(type) == null){
			String tableName =PropertiesUtils.getPropertiesValue("typeToTable.properties", type);
			if(StringUtils.isBlank(tableName)){
				tableName = type;
			}
			tableMap.put(type, tableName);
		}
		return tableMap.get(type);
	}
	private static Map<String,Boolean> dailyMap ;
	public static boolean checkIsDaily(String mainTableName) {
		if(dailyMap == null){
			loadDailyMap();
		}
		if(dailyMap.get(mainTableName) == null){
			return Boolean.FALSE;
		}
		return dailyMap.get(mainTableName);
	}

	private static void loadDailyMap(){
			ReentrantLock lock = new ReentrantLock();
			lock.lock();
			try {
				dailyMap =  new HashMap<String,Boolean>();
				String tableName =PropertiesUtils.getPropertiesValue("typeToTable.properties", "daily_tables");
				if(StringUtils.isBlank(tableName)){
					return ;
				}
				String[] tables = tableName.replaceAll("，", ",").split(",");
				for(String table : tables){
					dailyMap.put(table, Boolean.TRUE);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				lock.unlock();
			}
	}
//	public static String[] getShowTables(String type) {
//		if(showTablesMap.get(type) == null){
//			String tableName =PropertiesUtils.getPropertiesValue("typeToTable.properties", type);
//			String[] tbs = tableName.replaceAll("，", ",").split(",");
//			showTablesMap.put(type, tbs);
//		}
//		return showTablesMap.get(type);
//	}
}

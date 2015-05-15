package com.sq.lunce.index;

import org.apache.commons.lang.StringUtils;

import com.sq.utils.PropertiesUtils;

public class Constant {

	public static String LUC_SERVER_KEY = "serverName";
	public static String LUC_LOG_TIME = "logTime";
	public static long TIMER_HOURS_TIMES = 1000 * 60 * 60L;
	public static String LUT_LOG_TYPE_NAME = "LUC_LOG_TYPE";
	private static String LUC_INDEX_ROOT  ;
	private static int maxIndexSize = 0 ;
	public static String getIndexRoot(){
		if(LUC_INDEX_ROOT == null){
			LUC_INDEX_ROOT = PropertiesUtils.getPropertiesValue("jdbc.properties", "indexRootPath");
		}
		return LUC_INDEX_ROOT;
	}
	
	public static int getMaxIndexSize(){
		if(maxIndexSize != 0){
			return maxIndexSize;
		}
		String maxSizeSet = PropertiesUtils.getPropertiesValue("jdbc.properties", "indexShowMaxSize");
		if(StringUtils.isNotBlank(maxSizeSet)){
			maxIndexSize = Integer.parseInt(maxSizeSet);
		}else {
			maxIndexSize = 100;
		}
		
		return maxIndexSize;
	}
	
}

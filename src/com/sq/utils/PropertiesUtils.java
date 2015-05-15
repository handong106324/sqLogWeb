package com.sq.utils;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtils {
	private static final Logger log = LoggerFactory.getLogger(PropertiesUtils.class);
	private static String pingtainame ;
	
	public static String getPingtainame() {
		if (pingtainame==null||pingtainame.trim().equals("")) {
			pingtainame = PropertiesUtils.getPropertiesValue("config.properties", "pingtainame");
		}
		return pingtainame;
	}

	/**
	 * properties文件 帮助工具
	 */
	private static Properties config = null;
	public static String getPropertiesValue(String propertiesName,String key){
		try {
			InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream(propertiesName);
			config = new Properties();
			config.load(in);
			in.close();
			String value = config.getProperty(key);
			return value;
		} catch (Exception e) {
			log.error("", e);
		}
		
		return null;
	}
	
	public static void main(String[] args) {			   
		String value = PropertiesUtils.getPropertiesValue("config.properties", "appStore");
		System.out.println(value);
	}
}

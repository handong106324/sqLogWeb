package com.sq.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class UniqueCheckFactory {

	private static Map<String,String[]> INSTANCE;
	public static  Map<String,String[]>  getInstance(Properties pro){
		if(INSTANCE == null){
			init(pro);
		}
		return INSTANCE;
	}
	private static void init(Properties pro) {
		INSTANCE = new HashMap<String,String[]>();
		if(pro == null)return;
		Iterator it = pro.keySet().iterator();
		while(it.hasNext()){
			String key = it.next().toString();
			String value = pro.getProperty(key);
			INSTANCE.put(key, value.split(","));
		}
	}
}

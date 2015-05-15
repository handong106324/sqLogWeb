package com.sq.cache;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class QueryMap {

	public static void removeCountMap(String type,HttpSession session) {
		Map<String,Integer> countMap = (Map<String, Integer>) session.getAttribute(type);
		if(countMap == null){
			return;
		}
		countMap.put(type, 0);
	}

	public static int putNullQuery(String type,HttpSession session) {
		Map<String,Integer> countMap = (Map<String, Integer>) session.getAttribute(type);
		if(countMap == null){
			countMap = new HashMap<String,Integer>();
			countMap.put(type, 1);
			session.setAttribute(type, countMap);
		}
		int count = countMap.get(type);
		count ++;
		countMap.put(type, count);
		return count;
	}
}

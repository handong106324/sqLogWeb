package com.sq.lunce.index.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


import com.sq.lunce.index.Constant;
import com.sq.lunce.index.query.LucIndexSearch;
import com.sq.model.DateParam;
import com.sq.utils.PropertiesUtils;

public class LucExecutor {

	private static Logger logger = Logger.getLogger(LucExecutor.class);
	private static int  maxSize = 100;
	private static LucExecutor instance;
	public static LucExecutor getInstance(){
		
		if(instance == null){
			ReentrantLock lock = new ReentrantLock();
			try {
				lock.lock();
				instance = new LucExecutor();
				lock.unlock();
			} catch (Exception e) {
				logger.error("[初始化LucExecutor][报错]["+e+"]");
				e.printStackTrace();
			}finally{
				lock.unlock();
			}
			
		}
		return instance;
	}

	public List<Map<String,String>> getDatas(DateParam dp,String serverName,String[] paras,String[] vals, String type){
		List<Map<String,String>> datas = new ArrayList<Map<String,String>>();
		List<String> dates = dp.getDates();
		for(String queryDate : dates){
			Integer[] date_hours = dp.getHoursDuring(queryDate);
			LucIndexSearch search = new LucIndexSearch(Constant.getIndexRoot()+"/"+queryDate, serverName,type, date_hours[0], date_hours[1],paras,vals);
			List<Map<String,String>> das = search.getDatas();
			datas.addAll(das);
			logger.error("[LucExecutor][getDatas][日期："+queryDate+"][时间："+date_hours[0]+"-"+date_hours[1]+"][结果："+das.size()+"]");
			if((maxSize - datas.size()) <= 0){
				break;
			}
		}
		return datas;
	}
}

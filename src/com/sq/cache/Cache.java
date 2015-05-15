package com.sq.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.sq.common.Constant;
import com.sq.entity.GAME;
import com.sq.entity.InternetMessage;
import com.sq.entity.LogFileInfo;
import com.sq.entity.LogPageInfo;
import com.sq.entity.PageCondition;
import com.sq.interfaces.GameServerOperation;
import com.sq.utils.MyListUtils;
import com.sq.utils.PropertiesUtils;

public class Cache {
	
	public static Map<String,LogFileInfo> logFileInfoMap = new HashMap<String, LogFileInfo>();
	public static Map<String,List<LogPageInfo>> logPageInfoListMap = new HashMap<String, List<LogPageInfo>>();
	public static Map<String,List<PageCondition>> conditions = new HashMap<String,List<PageCondition>>();
	public static List<LogFileInfo> logFileList;
	public static Map<String,GAME> games = new HashMap<String,GAME>();
	public static Map<String,InternetMessage> messes = null;
	public static Map<String,Map<String,String>> conditionShowMap = new HashMap<String,Map<String,String>>();
	public static Map<String,Map<String,String>> colShowMap = new HashMap<String,Map<String,String>>();
	public static Map<String,Map<Long,Record>> foreignDataMap  = new HashMap<String,Map<Long,Record>>();

	public static LogFileInfo getLogFileInfo(String serverName,String type, String gameName){
		if(logFileInfoMap.get(gameName+"_"+serverName+"-"+type) == null){
			logFileInfoMap.put(gameName+"_"+serverName+"-"+type, LogFileInfo. dao.findFirstByCondition("serverName ='"+serverName+"' and `type`='"+type+"' and gameName='"+gameName+"'" ));
		}
		return logFileInfoMap.get(gameName+"_"+serverName+"-"+type);
	}
	
	@SuppressWarnings("unchecked")
	public static List<LogPageInfo> getLogPageInfos(String type,String gameName){
		if(logPageInfoListMap.get(gameName+"_"+type) == null || logPageInfoListMap.get(gameName+"_"+type).size() == 0){
			List<LogPageInfo> ls = LogPageInfo. dao.findPart("`key`='"+type+"' and game='"+gameName+"' and (attachTo ='' or attachTo is null)");
			logPageInfoListMap.put(gameName+"_"+type, ls);
			Map<String,String> vM = new HashMap<String,String>();
			for(LogPageInfo pc : ls){
				vM.put(pc.getStr("colName"), Cache.getMessage(pc.getStr("colName")));
			}
			colShowMap.put(gameName+"-"+type, vM);
		}
		return logPageInfoListMap.get(gameName+"_"+type);
	}

	@SuppressWarnings("unchecked")
	public static List<LogPageInfo> getAllLogPageInfos(String type,String gameName){
		if(logPageInfoListMap.get(gameName+"_"+type+"_all") == null || logPageInfoListMap.get(gameName+"_"+type+"_all").size() == 0){
			logPageInfoListMap.put(gameName+"_"+type+"_all", LogPageInfo. dao.findPart("`key`='"+type+"' and game='"+gameName+"'"));
		}
		return logPageInfoListMap.get(gameName+"_"+type+"_all");
	}
	
	@SuppressWarnings("unchecked")
	public static List<LogFileInfo> getAllLogFileInfo() {
		if(logFileList == null){
			logFileList = LogFileInfo.dao.findAll();
		}
		return logFileList;
	}

//	public static LogFileInfo getLogFileInfo(String serverName, String intro) {
//		return getLogFileInfo(serverName, intro, GAME.GAME_WHCL);
//	}

	public static void update() {
		logFileInfoMap.clear();
		logPageInfoListMap.clear();
		logFileList = null;
		conditions.clear();
		games.clear();
		colShowMap.clear();
		messes.clear();
		GameServerOperation.clear();
	}

	@SuppressWarnings("unchecked")
	public static List<PageCondition> getPageConditon(String gameWx, String type) {
		List<PageCondition> res = conditions.get(gameWx+"-"+type);
		if(res == null || res.size() == 0){
			res = PageCondition.dao.findPart("type='"+type+"' and game='"+gameWx+"' order by sortIndex asc");			
			conditions.put(gameWx+"-"+type, res);
			Map<String,String> vM = new HashMap<String,String>();
			for(PageCondition pc : res){
				vM.put(pc.getStr("showName"), Cache.getMessage(pc.getStr("showName")));
			}
			conditionShowMap.put(gameWx+"-"+type, vM);
		}
		return res;
	}

	public static void deleteCondition(Integer paraToInt,String game,String type) {
		List<PageCondition> res = conditions.get(game+"-"+type);
		if(res != null){
			for(PageCondition page : res){
				if(page.getInt("id").intValue() == paraToInt){
					conditions.remove(page);
					break;
				}
			}
		}
		
	}

	public static void addCondition(PageCondition role) {
		List<PageCondition> cs = conditions.get(role.get("game")+"-"+role.getStr("type"));
		cs.add(role);
	}

	@SuppressWarnings("unchecked")
	public static String getMessage(String key){
		if(!Constant.needInternational()){
			return key;
		}
		if(messes == null || messes.size() == 0){
			messes = (Map<String, InternetMessage>) MyListUtils.toStringMap(InternetMessage.dao.findAll(), "key");
		}
		InternetMessage imess = messes.get(key);
		if(imess == null){
			return key;
		}
		String showType = Constant.getInterMessageShowType();
		if(showType == null){
			return key;
		}
		if(showType.equals(Constant.INTERNATIONAL_SHOW_BOTH)){
			return imess.getStr("value")+"("+key+")";
		}
		if(showType.equals(Constant.INTERNATIONAL_SHOW_LOCALE)){
			return key;
		}
		if(showType.equals(Constant.INTERNATIONAL_SHOW_FOREIGN)){
			return imess.getStr("value");
		}
		return key;
	}
	
	public static GAME getGameInfo(String game) {
		GAME ga = games.get(game);
		if(ga == null){
			ga = GAME.dao.findFirstByCondition("name='"+game+"'");
			games.put(game, ga);
			
		}
		return ga;
	}

	public static Object getConditionShowMap(String game, String type) {
		return conditionShowMap.get(game+"-"+type);
	}
	private static Boolean showDetail  = null;
	public static boolean showDetailInPage(){
		if(showDetail != null){
			return showDetail;
		}
		String show = PropertiesUtils.getPropertiesValue("jdbc.properties", "HANGUO_WH");
		if(show==null){
			showDetail = Boolean.TRUE;
		}else {
			showDetail = Boolean.valueOf(show);
		}
		return showDetail;
	}

	public static Object getColMap(String game, String type) {
		// TODO Auto-generated method stub
		return colShowMap.get(game+"-"+type);
	}

	static ReentrantLock lock = new ReentrantLock();
	public static Map<Long, Record> getForeignDataMap(String foreignKey) {
		if(foreignDataMap.get(foreignKey) != null){
			return foreignDataMap.get(foreignKey);
		}
		try {
			lock.lock();
			if(foreignDataMap.get(foreignKey) == null){
				reloadForeignDataMap(foreignKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
		return foreignDataMap.get(foreignKey);
	}
	
	public static void reloadForeignDataMap(String foreignKey) {
			foreignDataMap.put(foreignKey, loadForeignData(foreignKey));
	}
	
	private static Map<Long, Record> loadForeignData(String foreignKey) {
		List<Record> list = Db.find("select * from "+ foreignKey);
		return toMap(list);
	}
	
	public static List<Record> loadForeignDataList(String foreignKey) {
		List<Record> list = Db.find("select * from "+ foreignKey);
		return list;
	}

	private static Map<Long, Record> toMap(List<Record> list) {
		Map<Long,Record> map = new HashMap<Long,Record>();
		for(Record re : list){
			map.put(re.getLong("id"), re);
		}
		return map;
	}
}

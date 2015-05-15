package com.sq.interfaces;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jfinal.kit.PathKit;
import com.sq.common.Constant;
import com.sq.entity.GameServer;
import com.sq.utils.JacksonManager;
import com.sq.utils.MD5;
import com.sq.utils.PropertiesUtils;
import com.sq.utils.URLHandler;

public class GameServerOperation {
	private static Logger logger = Logger.getLogger(GameServerOperation.class);
	private static Map<String,String> serverMap = new HashMap<String, String>();
	private static String f_url = "/admin/gameManager/";
	private static int timeout = 20;
	public static Map jinyan(String serverName,String playerId,String bantime){
		Map<String,String> param = new HashMap<String,String>();
		param.put("playerId", playerId);
		param.put("bantime", bantime);
		String serverUrl = getServerUrl(serverName);
		if(StringUtils.isBlank(serverUrl)){
			Map map = new HashMap<String,String>();
			map.put("result", "服务器地址为空");
			return map;
		}
		try {
			String res = URLHandler.sendPost(serverUrl+f_url+"jinyan.jsp", param,timeout);
			System.out.println(serverUrl+f_url+"jinyan.jsp"+" : "+res);;
			return (Map) JacksonManager.getInstance().jsonDecodeObject(res);
		} catch (Exception e) {
			logger.error(e);
		}
		Map map = new HashMap<String,String>();
		map.put("result", "操作失败");
		return map;
	}
	
	public static Map jieJinyan(String serverName,String playerId){
		Map<String,String> param = new HashMap<String,String>();
		param.put("playerId", playerId);
		String serverUrl = getServerUrl(serverName);
		if(StringUtils.isBlank(serverUrl)){
			Map map = new HashMap<String,String>();
			map.put("result", "服务器地址为空");
			return map;
		}
		try {
			String res = URLHandler.sendPost(serverUrl+f_url+"jieyan.jsp", param,timeout);
			return (Map) JacksonManager.getInstance().jsonDecodeObject(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map map = new HashMap<String,String>();
		map.put("result", "操作失败");
		return map;
	}
	
	
	public static Map kickPlayer(String serverName,String playerId){
		Map<String,String> param = new HashMap<String,String>();
		param.put("playerId", playerId);
		String serverUrl = getServerUrl(serverName);
		if(StringUtils.isBlank(serverUrl)){
			Map map = new HashMap<String,String>();
			map.put("result", "服务器地址为空");
			return map;
		}
		try {
			String res = URLHandler.sendPost(serverUrl+f_url+"kickPlayer.jsp", param,timeout);
			return (Map) JacksonManager.getInstance().jsonDecodeObject(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map map = new HashMap<String,String>();
		map.put("result", "操作失败");
		return map;
	}
	
	public static Map sealPassport(String serverName,String userName,String innerReason,String reson,String hour,String playerId,String playerName){
		Map<String,String> param = new HashMap<String,String>();
		param.put("userName", userName);
		param.put("innerReason", innerReason);
		param.put("reason", reson);
		param.put("hours", hour);
		param.put("playerId", playerId);
		param.put("playerName", playerName);
		param.put("from", "forbiduser");
		String serverUrl = getServerUrl(serverName);
		param.put("serverUrl", serverUrl);
		logger.debug("[GameServerOperation][sealPassport][userName:"+userName+"][innerReason:"+innerReason+"][desc:"+reson+"][playerId:"+playerId+"][longTime="+hour+"][url="+serverUrl+"]");
		if(StringUtils.isBlank(serverUrl)){
			Map map = new HashMap<String,String>();
			map.put("result", "服务器地址为空");
			return map;
		}
		try {
//			param.put("username", "admin");
//			param.put("password", "sq%TGB*UHB");
			long timeL = new Date().getTime();
			param.put("auth.time", timeL+"");
			
			param.put("auth.sign", MD5.getSign(timeL));
			param.put("sysName", URLEncoder.encode("日志系统使用","UTF-8"));

			//serverUrl+f_url+"userNameSeal.jsp"
			String url = "http://116.213.205.24:28088/gm";
			if(Constant.isHaiwai){
				url = "http://210.242.234.87:28088/gm";
			}
			String res = URLHandler.sendPost(url+"/player_openInterface_forbidUserToLogSys.do", param,timeout);
			logger.debug("[GameServerOperation][res="+res+"][url="+url+"]");
			return (Map) JacksonManager.getInstance().jsonDecodeObject(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map map = new HashMap<String,String>();
		map.put("result", "操作失败");
		return map;
	}
	
	public static Map liftSealPassport(String serverName,String userName){
		Map<String,String> param = new HashMap<String,String>();
		param.put("userName", userName);
		String serverUrl = getServerUrl(serverName);
		if(StringUtils.isBlank(serverUrl)){
			Map map = new HashMap<String,String>();
			map.put("result", "服务器地址为空");
			return map;
		}
		try {
			String res = URLHandler.sendPost("http://117.121.17.6:29901/admin/gameManager/userNameOpen.jsp", param,timeout);
			logger.debug("[GameServerOperation][listSealPassport][username:"+userName+"][res:"+res+"]");
			return (Map) JacksonManager.getInstance().jsonDecodeObject(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map map = new HashMap<String,String>();
		map.put("result", "操作失败");
		return map;
	}
	private static String getServerUrl(String serverName) {
		String url = serverMap.get(serverName);
		if(StringUtils.isNotBlank(url)){
			System.out.println(url);
			return url;
		}
		reloadServer();
		url = serverMap.get(serverName);
		System.out.println(url);
		return url;
	}
	
	public static List<GameServer> getServers(){
		if(serverMap == null || serverMap.size() ==0){
			reloadServer();
		}
		List<GameServer> list = new ArrayList<GameServer>();
		Iterator<String> it = serverMap.keySet().iterator();
		while(it.hasNext()){
			String serName = it.next();
			GameServer gs = new GameServer();
			gs.put("serverName", serName);
			list.add(gs);
		}
		return list;
	}
	
	public static void reloadServer(){
		try {
			String haiwai = PropertiesUtils.getPropertiesValue("jdbc.properties", "HAIWAIFLAG");
			String res = "";
			if(haiwai != null&&haiwai.equals("true")){
				res = URLHandler.sendPost("http://210.242.234.75:29901/admin/gameManager/getServerList.jsp", null, 20);
			}else {
				res = URLHandler.sendPost("http://117.121.17.6:29901/admin/gameManager/getServerList.jsp", null, 20);
			}
			List<Map> mpList = (List<Map>) JacksonManager.getInstance().jsonDecodeObject(res);
			InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream("chaturl.properties");
			Properties pro = new Properties();
			pro.load(in);
			in.close();
			for(Map map : mpList){
				System.out.println(map+":"+map.get("showName"));
				if(!map.get("status").equals("内部测试用")){
					if(	pro.containsKey(map.get("showName").toString())){
						serverMap.put(map.get("showName").toString(), pro.get(map.get("showName").toString()).toString());
					}else {
						serverMap.put(map.get("showName").toString(), map.get("url").toString());

					}
				}
					
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			logger.debug(e);
		}
	}

	public static void clear() {
		if(serverMap != null)serverMap.clear();
	}
}

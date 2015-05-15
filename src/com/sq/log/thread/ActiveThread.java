package com.sq.log.thread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sq.cache.Cache;
import com.sq.entity.LogFileInfo;
import com.sq.shell.ShellFactory;

public class ActiveThread extends Thread {

	private List<LogFileInfo> infos = Cache.getAllLogFileInfo();
	public void activeServer(){
		Map<String,String[]> map = new HashMap<String,String[]>();
		for(LogFileInfo fileInfo : infos){
			boolean isShell;
			String ip,port;
			String queryType = fileInfo.getStr("queryType");
			if(StringUtils.isNotBlank(queryType) && queryType.equals("shell")){
				isShell = true;
				ip = fileInfo.getStr("ip");
				port = fileInfo.getStr("port");
			}else{
				isShell = false;
				ip = fileInfo.getStr("webIp");
				port = fileInfo.getStr("webPort");
			}
			if(!map.containsKey(ip)){
				map.put(ip, new String[]{ip,port,isShell?"yes":"no"});
				ShellFactory.getInstance().activeServer(ip, port, isShell);
			}
		}
	}
	
	@Override
	public void run() {
		activeServer();
	}
}

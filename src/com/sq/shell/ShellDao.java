package com.sq.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.sq.log.result.LogResultInfo;
import com.sq.shell.connection.ConnectionFactory;
import com.sq.utils.JacksonManager;
import com.sq.utils.URLHandler;

public class ShellDao {

	public static Logger logger = ShellFactory.logger;
	private ConnectionFactory factory;
	private int timeOut = 20000;
	
	private int uriTimeout = 20;
	/**
	 * @return the timeOut
	 */
	public int getTimeOut() {
		return timeOut;
	}
	/**
	 * @param timeOut the timeOut to set
	 */
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	public ShellDao(ConnectionFactory factory){
		this.factory = factory;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> getResultByWeb(String cmdCat, int count,String ip,String port) {
			Map param = new HashMap();
			param.put("cmd", cmdCat);
			param.put("count", count+"");
			String res;
			try {
				logger.debug("执行cmd："+cmdCat);
				System.out.println("http://"+ip+":"+port+"/whclLog/shell/runSh.do"+cmdCat);
				res = URLHandler.sendPost("http://"+ip+":"+port+"/whclLog/shell/runSh.do", param,uriTimeout * 10);
				Map<String,Object> resu = (Map)JacksonManager.getInstance().jsonDecodeObject(res);
				return resu;
			} catch (Exception e) {
				param.put("msg", "网络不好或者文件过大，在规定时间内无法完成查询");
			}
			return param;
	}

	public void sendMsg(String ip,String port,boolean isShell){
		if(isShell){
			Session sess = null;
			try {
				sess = factory.getConn(ip, port).openSession();
				sess.execCommand("ls");
			} catch (IOException e) {
				logger.error(e);
			} finally {
				if (sess != null)
					sess.close();
			}
		}else{
			Map<String,String> param = new HashMap<String,String>();
			param.put("cmd", "ls");
			param.put("count", 0+"");
			try {
				URLHandler.sendPost("http://"+ip+":"+port+"/whclLog/shell/runSh.do", param,uriTimeout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Map<String, Object> getResultByShell(String cmdCat, int count,String ip,String port) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> list = new ArrayList<String>();
		long start = System.currentTimeMillis();
		Session sess = null;
		try {
			boolean netBusy = false;
			sess = factory.getConn(ip, port).openSession();
			sess.execCommand(cmdCat);
			logger.debug("[ShellDao][getResultByShell][准备执行][cmd:"+cmdCat+"][ip:"+ip+"][port:"+port+"]");
			InputStream stdout = new StreamGobbler(sess.getStdout());
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout, "UTF-8"));
			int index = 0;
			while (true) {
				if(netBusy && System.currentTimeMillis() - start > 60000){
					map.put("msg", "网络比较卡，请稍后操作");
					break;
				}
				String line = br.readLine();
				if (line == null || index++ >= count)
					break;
				list.add(line);
				if(netBusy)netBusy = false; 
				if(System.currentTimeMillis() - start > 300000){
					map.put("msg", "获取数据过长，请稍后操作");
					break;
				}
			}
			logger.debug("[ShellDao][getResultByShell][size:" + list.size()	+ "][cmd:" + cmdCat + "][最大数据量:" + count + "][耗时："+ (System.currentTimeMillis() - start) + "]");
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if (sess != null)
				sess.close();
		}
		map.put("datas", list);
		return map;
	}
	
	public void getResultByShellSych(String cmdCat,String fileName, String param,String ip,String port, LogResultInfo info) {
		Session sess = null;
		long start = System.currentTimeMillis();
		int index = 0 ;
		try {
			
			cmdCat += " "+fileName +" " + param;
			sess = factory.getConn(ip, port).openSession();
			sess.execCommand(cmdCat);
			boolean netBusy = true;
			logger.debug("[ShellDao][getResultByShell][准备执行][cmd:"+cmdCat+"][ip:"+ip+"][port:"+port+"]");
			info.setScheduleMsg("执行");
			InputStream stdout = new StreamGobbler(sess.getStdout());
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout, "UTF-8"));
			while (true) {
				if(netBusy && System.currentTimeMillis() - start > 60000){
					info.setScheduleMsg(getFileShowName(fileName)+"网络超时");
					info.setOK(getFileShowName(fileName));
					break;
				}
				if(info.isCancel()){
					info.setScheduleMsg(getFileShowName(fileName), "强制取消成功");
					info.setOK(getFileShowName(fileName));
					break;
				}
				String line = br.readLine();
				if (line == null)
					break;
				if((System.currentTimeMillis() - start ) >= timeOut*10){
					info.setScheduleMsg(getFileShowName(fileName)+"超时");
					info.setOK(getFileShowName(fileName));
					break;
				}
				
				try {
					info.getDatas().put(line);
					info.setSize(fileName, 1);
					if(++index  >= 100){
						break;
					}
				} catch (InterruptedException e) {
					logger.error("[ShellDao][getResult]["+e+"]");
				}
			}
		} catch (IOException e) {
			logger.error(e);
			e.getStackTrace();
			info.setMsg("发生错误"+e.getLocalizedMessage());
		} finally {
			info.setSize(index);
			logger.debug("[ShellDao][getResultByShell][size:" + info.getSize()	+ "][info:"+info+"][cmd:" + cmdCat + "][耗时："+ (System.currentTimeMillis() - start) + "]");
			if (sess != null)
				sess.close();
		}
	}
	
	public static String getFileShowName(String fileName){
		String date = getDateFrom(fileName);
		if(fileName.contains("3d_log_game1")){
			fileName =  date + "场景服1";
		}else 		if(fileName.contains("3d_log_game2")){
			fileName =  date +  "场景服2";
		}else 		if(fileName.contains("3d_log_game3")){
			fileName =  date + "场景服3";
		}else 		if(fileName.contains("3d_log_logic")){
			fileName =  date + "逻辑服";
		}
	return fileName;
}

	private static String getDateFrom(String fileName) {
		if( fileName != null && fileName.contains(".log")){
			String nn = StringUtils.replaceChars(fileName, '.', ',');
			String[] nns = StringUtils.split(nn,",log,");
			for(String n:nns){
				if((n.length() == 10||n.length() == 13) && n.contains("-") && n.indexOf("-") == 4 && (n.lastIndexOf('-')==7||n.lastIndexOf('-')==10)){
					return n;
				}
			}
		}
		
		
	return "";
}
	
	public void queryResultByWeb(String cmdCat,String fileName,String params,String ip,String port,LogResultInfo info) {
		cmdCat += " "+fileName +" " + params;
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("cmd", cmdCat);
		param.put("sessionId", info.getSessionId());
			try {
				logger.debug("执行cmd："+cmdCat);
				String url = "http://"+ip+":"+port+"/whclLog/shell/runSh.do";
				System.out.println(url+"?sessionId="+info.getSessionId()+"&cmd="+cmdCat);
				URLHandler.getConnectionByPost(url, param, 1000000,info);
				logger.debug("[ShellDao][queryResultByWeb][已执行完毕]");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
}

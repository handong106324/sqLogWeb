package com.sq.shell;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jfinal.kit.PathKit;
import com.sq.common.Constant;
import com.sq.log.result.LogFileCheckInfo;
import com.sq.log.result.LogResultInfo;
import com.sq.model.FileInfo;
import com.sq.model.ShellInfo;
import com.sq.shell.connection.ConnectionFactory;
import com.sq.shell.result.LogResult;
import com.sq.utils.DateFormatUtils;
import com.sq.utils.PropertiesUtils;

public class ShellFactory {

	private static ShellFactory instance;
	public static Logger logger = Logger.getLogger(ShellFactory.class);

	private ShellDao dao ;
	private ShellFactory() {
		init();
	}

	private static String jdbcFile = "jdbc.properties";
	private void init() {
		String key = PropertiesUtils.getPropertiesValue(jdbcFile, "shell_key");
		String userName = PropertiesUtils.getPropertiesValue(jdbcFile,
				"shell_username");
		String pwd = PropertiesUtils.getPropertiesValue(jdbcFile,
				"shell_password");
		String isByKey =  PropertiesUtils.getPropertiesValue(jdbcFile,
				"isByKey");
		if(isByKey == null){
			isByKey = "true";
		}
		File keyfile = new File(key);
		if(!keyfile.exists()){
			keyfile = new File(PathKit.getWebRootPath() + File.separator + "WEB-INF" + File.separator+"classes"+File.separator+"pingtai");
		}
		ConnectionFactory.getInstance().init(key, userName, pwd,Boolean.valueOf(isByKey));
		dao = new ShellDao(ConnectionFactory.getInstance());
		String timeout = PropertiesUtils.getPropertiesValue(jdbcFile, "timeout");
		if(StringUtils.isNotBlank(timeout))dao.setTimeOut(Integer.parseInt(timeout));
	}

	public static ShellFactory getInstance() {
		if (instance == null) {
			instance = new ShellFactory();
		}
		return instance;
	}

	/**
	 * @param param 
	 * @return the conn
	 */

	@SuppressWarnings("unchecked")
	public LogResult getResult(String fileAbsPath, String param, int count,ShellInfo shellInfo) {
		LogResult result = new LogResult();
		String cmdShell = ShellFactory.getInstance().getCatCmd(fileAbsPath);
		cmdShell += " "+fileAbsPath +" " + param;
		logger.debug("[ShellFactory][getResult][cmd:"+cmdShell+"]");
		Map<String,Object> map = exec(cmdShell, count, shellInfo.getIp(), shellInfo.getPort(),shellInfo.isShell());
		if(map != null){
			result.setDatas((List<String>) map.get("datas"));
			result.setMsg((String) map.get("msg"));
		}else {
			result.setDatas(new ArrayList<String>());
			result.setMsg("网络不畅通或者文件过大");
		}
		return result;
	}
//	
	public void activeServer(String ip,String port,boolean isShell){
		dao.sendMsg(ip, port, isShell);
	}

	private Map<String, Object> exec(String cmdShell,int count,String ip,String port,boolean isShell){
		if(isShell){
			return dao.getResultByShell(cmdShell,count,ip,port);
		}else {
			return dao.getResultByWeb(cmdShell, count,ip,port);
		}
	}

/**
 * 获取命令名称 cat or zcat
 * @param filePath
 * @return
 */
	private String getCatCmd(String filePath){
		String shellCmd = "ls";
		if(filePath.endsWith(".log")){
			shellCmd = "cat ";
		}else if(filePath.endsWith(".gz")){
			shellCmd = "zcat ";
		}
		return shellCmd;
	}
	
	
	@SuppressWarnings("unchecked")
	public LogFileCheckInfo checkFileExsit(String path,ShellInfo shellInfo){
		LogFileCheckInfo info = new LogFileCheckInfo();
		Date date = new Date();
		String cmdZcat = "ls -ltr "+path;
		long start = System.currentTimeMillis();
		List<String> result = (List<String>) exec(cmdZcat, 10,shellInfo.getIp(),shellInfo.getPort(),shellInfo.isShell()).get("datas");
		if(result != null && result.size() > 0){
			long tempSize = 0 ;
			List<FileInfo> infs = new ArrayList<FileInfo>();
			for(String in:result){
				FileInfo fi = new FileInfo();
				String[] ins = in.split(" ");
				int index = 0;
				for(String i:ins){
					if(StringUtils.isNotBlank(i)){
						if(index == 4){
							fi.setSize(Long.parseLong(i));
						}else if(index  == 5){
							fi.setLastModifyTime(i);
						}else if(index  == 6){
							
							if(Long.parseLong(i) == date.getDate()){
								fi.setLastModifyTime(fi.getLastModifyTime()+" "+i);
							}else{
								fi.setLastModifyTime(fi.getLastModifyTime()+" <span style='color: blue'>"+i+"</span>");
							}
						}else if(index  == 7){
							fi.setLastModifyTime(fi.getLastModifyTime()+" "+i);
						}else if(index  == 8){
							fi.setFileName(i);
						}
						index ++;
					}
				}
				infs.add(fi);
				
			}
			info.setFiles(infs);
			logger.debug("[ShellUtils][CheckFileExsit][cmd:"+cmdZcat+"][存在][path:"+path+"][耗时："+(System.currentTimeMillis() - start)+"]");
			info.setSuccess(true);
			info.setSize(tempSize);
			return info;
		}
		info.setSuccess(false);
		logger.debug("[ShellUtils][CheckFileExsit][cmd:"+cmdZcat+"][不存在][path:"+path+"][耗时："+(System.currentTimeMillis() - start)+"]");
		System.out.println("校验文件是否存在耗时："+(System.currentTimeMillis() - start));
		return info;
	}


	@SuppressWarnings("unchecked")
	public LogFileCheckInfo checkFileInfo( String queryDate,ShellInfo shellInfo) {
		LogFileCheckInfo info = new LogFileCheckInfo();
		String cmdZcat = "ls -ltr "+(shellInfo.getPath() +"."+queryDate+"*");
		long start = System.currentTimeMillis();
		List<String> result = (List<String>) exec(cmdZcat, 24*10,shellInfo.getIp(),shellInfo.getPort(),shellInfo.isShell()).get("datas");
		
		if(result != null && result.size() > 0){
			long tempSize = 0 ;
			List<FileInfo> infs = new ArrayList<FileInfo>();
			for(String in:result){
				FileInfo fi = new FileInfo();
				String[] ins = in.split(" ");
				int index = 0;
				for(String i:ins){
					if(StringUtils.isNotBlank(i)){
						if(index == 4){
							fi.setSize(Long.parseLong(i));
						}else if(index  == 5){
						}else if(index  == 6){
						}else if(index  == 7){
						}else if(index  == 8){
//							if(!isToday && i.endsWith(".gz")){
//								fi.setFileName(i);
//								if(i.contains(queryDate+"-")){
//									info.setByDate(false);
//								}
//							}
//							if(isToday && i.endsWith(".log")){
								fi.setFileName(i);
								if(i.contains(queryDate+"-")){
									info.setByDate(false);
								}
//							}
							break;
						}
						index ++;
					}
				}
				if(StringUtils.isNotBlank(fi.getFileName())){
					addFileInfoToList(infs,fi);
				}
				
			}
			info.setFiles(infs);
			logger.debug("[ShellUtils][CheckFileExsit][cmd:"+cmdZcat+"][存在][path:"+shellInfo.getPath()+"][耗时："+(System.currentTimeMillis() - start)+"]");
			info.setSuccess(true);
			info.setSize(tempSize);
			return info;
		}
			return checkFileExsit(shellInfo.getPath(), shellInfo);
//		info.setSuccess(false);
//		logger.debug("[ShellUtils][CheckFileExsit][cmd:"+cmdZcat+"][不存在][path:"+shellInfo.getPath()+"][耗时："+(System.currentTimeMillis() - start)+"]");
//		System.out.println("校验文件是否存在耗时："+(System.currentTimeMillis() - start));
//		return info;
		
	}
	

	private void addFileInfoToList(List<FileInfo> infs, FileInfo fi) {
		boolean canAdd = true;
		for(FileInfo info : infs){
			if(fi.getFileName().endsWith(".log")){
				if(info.getFileName().contains(fi.getFileName())){
					canAdd = false;
				}else {
					continue;
				}
			}else  {
				if(fi.getFileName().contains(info.getFileName())){
					canAdd = false;
				}else {
					continue;
				}
			}
		}
		if(canAdd){
			infs.add(fi);
		}
	}


	@SuppressWarnings("unchecked")
	public String lastLogTime(String path,ShellInfo shellInfo){
		String cmdZcat = "tail "+path;
		List<String> result = (List<String>) exec(cmdZcat, 20,shellInfo.getIp(),shellInfo.getPort(),shellInfo.isShell()).get("datas");
		if(result.size() == 0){
			return "获取日志最后记录时间失败";
		}
		for(String logSt : result){
			List<String> infos = new ArrayList<String>();

			String logStr = logSt.replaceAll("\\[", Constant.SQ_SEPARATOR).replaceAll("\\]",  Constant.SQ_SEPARATOR);
			String[] logStrs = logStr.split(Constant.SQ_SEPARATOR);
			for(String log :logStrs){
				if(StringUtils.isNotBlank(log)){
					infos.add(log.trim());
				}
			}
			if(infos.size() > 3){
				String time = parseTime(infos.get(1));
				return "最后记录时间："+time;
			}
		}
		
		return "获取日志最后记录时间失败";
	}

	private String parseTime(String string) {
		if(StringUtils.isBlank(string)){
			return "<font color='red'>获取失败</font>";
		}
		String time = DateFormatUtils.getHourFormat(string);
		if(time == null){
			return "<font color='red'>获取失败</font>";
		}
		if(!DateFormatUtils.isToday(time)){
			time ="<font color='red'>"+time+"</font>";
		}
		return time;
	}

	public void queryResultByShell(String fileName, String param,ShellInfo shellInfo, LogResultInfo info) {
		String cmdShell = ShellFactory.getInstance().getCatCmd(fileName);
		dao.getResultByShellSych(cmdShell,fileName,param, shellInfo.getIp(), shellInfo.getPort(), info);
		info.setOK(fileName);
		info.setScheduleMsg(ShellDao.getFileShowName(fileName)+":查询完毕");
	}

	public void queryResultByWeb(String fileName, String param,ShellInfo shellInfo,LogResultInfo info) {
		String cmdShell = ShellFactory.getInstance().getCatCmd(fileName);
		dao.queryResultByWeb(cmdShell,fileName,param, shellInfo.getIp(), shellInfo.getPort(),info);
		info.setOK(fileName);
		info.setScheduleMsg(ShellDao.getFileShowName(fileName)+":查询完毕");
	}

}

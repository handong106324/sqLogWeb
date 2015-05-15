package com.sq.log.factory;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.sq.cache.Cache;
import com.sq.common.Constant;
import com.sq.entity.LogFileInfo;
import com.sq.log.result.LogExecutor;
import com.sq.log.result.LogQueryExecutor;
import com.sq.log.result.LogResultInfo;
import com.sq.lunce.index.result.LucExecutor;
import com.sq.model.DateParam;
import com.sq.model.ShellInfo;

public class LogFactory {

	private static LogFactory instance;
	private Logger logger = Logger.getLogger(getClass());
	private LogFactory(){
		
	}
	
	public static LogFactory getInstance(){
		if(instance == null){
			instance = new LogFactory();
		}
		return instance;
	}
	
//	public void query(String serverName,String type, DateParam dp,String[] condition,HttpSession session){
//		query(serverName, type, dp, condition, session,GAME.GAME_WHCL);
//	}
	
	public void query(String serverName,String type, DateParam dp,String[] condition,HttpSession session,String game){
		logger.debug("[LogFactory][query][开始检查配置信息]");
		
		LogFileInfo pro = Cache.getLogFileInfo(serverName,type,game);
		LogResultInfo info = new LogResultInfo(type,game);
		info.setSessionId(session.getId());
		session.setAttribute(game+"-"+type, info);
		info.setScheduleMsg("开始检查Shell配置");
		if(pro == null){
			logger.debug("[LogFactory][query][检查配置信息][未找到Shell配置信息]");
			info.setMsg("未找到Shell配置信息");
			info.setScheduleMsg("未找到Shell配置信息");
			info.setStatus(Constant.SHELL_STATUS_ERROR);
			return ;
		}
		info.setResultHandleType(pro.getStr("resultHandleType"));
		logger.debug("[LogFactory][query][检查配置信息完成]");
		ShellInfo shellInfo = new ShellInfo(pro);
		info.setScheduleMsg("检查Shell配置完成");
		new LogExecutor( dp, condition, shellInfo, info);
	}

	/**
	 * 
	 * @param serverName
	 * @param type
	 * @param dp
	 * @param paras
	 * @param vals
	 * @param session
	 * @param game  省略，当前使用不到。以后可能会用到
	 * @return
	 */
	public List<Map<String, String>> queryByLuc(String serverName, String type, DateParam dp,String[] paras,String[] vals, HttpSession session, String game) {
		//处理type，一天所有的文件都放在一个
		return LucExecutor.getInstance().getDatas(dp,serverName, paras, vals,type);
	}
}

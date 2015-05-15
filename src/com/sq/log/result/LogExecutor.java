package com.sq.log.result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sq.log.chain.CheckFileChain;
import com.sq.model.BaseFlow;
import com.sq.model.DateParam;
import com.sq.model.ShellInfo;

public class LogExecutor{

	private static Logger logger = Logger.getLogger(LogExecutor.class);
	private DateParam dp;
	private String[] condition;
	List<BaseFlow> datas = new ArrayList<BaseFlow>();
	private ShellInfo shellInfo;
	private List<String> list;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private LogResultInfo session;
	
	public LogExecutor(DateParam dp,String[] condition,ShellInfo shellInfo,LogResultInfo session){
		this.dp = dp;
		this.condition = condition;
		this.session = session;
		this.shellInfo = shellInfo;
		
		execute();
	}
	/**
	 * 1  获取所有需要要查询的日子
	 * 2  判断每一天的日志适合哪种类型
	 */
	private void execute() {
		logger.debug("[LogExecutor][execute][......]");
		CheckFileChain checkFileChain = new CheckFileChain();
		checkFileChain.setData(session);
		checkFileChain.setDp(getDp());
		checkFileChain.setShellInfo(shellInfo);
		checkFileChain.setCondition(condition);
		
		checkFileChain.execute();
//		checkFileChain.setNextChain(nextChain);
		
	}

	/**
	 * @return the datas
	 */
	public List<BaseFlow> getDatas() {
		return datas;
	}

	/**
	 * @param datas the datas to set
	 */
	public void setDatas(List<BaseFlow> datas) {
		this.datas = datas;
	}


	/**
	 * @param logger the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}


	/**
	 * @return the dp
	 */
	public DateParam getDp() {
		return dp;
	}

	/**
	 * @param dp the dp to set
	 */
	public void setDp(DateParam dp) {
		this.dp = dp;
	}


	/**
	 * @return the condition
	 */
	public String[] getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String[] condition) {
		this.condition = condition;
	}

	/**
	 * @return the count
	 */

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public static Logger getLogger(){
		return logger;
	}
	
}

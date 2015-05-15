package com.sq.log.chain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sq.common.Constant;
import com.sq.log.result.LogExecutor;
import com.sq.log.result.LogFileCheckInfo;
import com.sq.model.DateParam;
import com.sq.model.FileInfo;
import com.sq.model.ShellInfo;
import com.sq.shell.ShellDao;
import com.sq.shell.ShellFactory;
import com.sq.utils.DateFormatUtils;

public class CheckFileChain extends LogBaseChain {

	private DateParam dp;
	private ShellInfo shellInfo;
	private String[] condition;
	private List<ExeInfo> infs = new ArrayList<ExeInfo>(); 

	private Logger logger = LogExecutor.getLogger();
	@Override
	public void execute() {
		getData().setStatus(Constant.SHELL_STATUS_CHECK_FILE);
		init();
		doNext();
	}
	
	private void init() {
		List<String> dates = dp.getDates();
		for(String queryDate : dates){
			boolean isToday = DateFormatUtils.isToday(queryDate);
			getData().setScheduleMsg("检查文件配置......");
			LogFileCheckInfo info = ShellFactory.getInstance().checkFileInfo(queryDate, shellInfo);
			logger.debug("[LogExecutor][execute][isToday:"+isToday+"][文件校验："+info+"]");
			List<FileInfo> fis = info.getFiles();
			
			if(info.isByDate()){
				logger.debug("[LogExecutor][execute][bydate]");
				if(! info.isSuccess()){
					continue;
				}
				
				String sql = combineCondition(queryDate,true);
				infs.add(new ExeInfo(fis, sql, false));
			}else {
				logger.debug("[LogExecutor][execute][byHour]");
				List<String> d_hours = dp.getDateHours(queryDate);
				List<FileInfo> newFis = new ArrayList<FileInfo>();
				for(String d_hour :d_hours){
					String newFileName = "."+d_hour+".log";
					for(FileInfo fi :fis){
						if(fi.getFileName().contains(newFileName)){
							newFis.add(fi);
							break;
						}
					}
					if(!checkFileExsit(fis,newFileName)){
						getData().setMsg(d_hour+"的文件不存在");
						continue;
					}
				}
				
					String sql = combineCondition(queryDate,false);
					if(newFis.size() >0){
						infs.add(new ExeInfo(newFis, sql, false));
					}else {
						getData().setMsg("没有有效文件");
						getData().finish();
					}
				
			}
		}
		
	}

	public void doNext(){
		for(ExeInfo ei : infs){
			initStatusOfData(ei.getFiles());
		}
		
		for(ExeInfo ei : infs){
			doNextChain(ei.getFiles(), ei.getSql(), ei.isQueryDetail());
		}
	}
	
	public void initStatusOfData(List<FileInfo> fis){
		for(FileInfo fi :fis){
			getData().add(ShellDao.getFileShowName(fi.getFileName()));
		}
	}
	
	private void doNextChain(List<FileInfo> fis, String sql, boolean isToday) {
		if(getNextChain() == null) {
			ExecuteShellChain nextChain = new ExecuteShellChain();
			nextChain.setData(getData());
			nextChain.setShellInfo(shellInfo);
			setNextChain(nextChain);
		}
		((ExecuteShellChain)getNextChain()).setFis(fis);
		((ExecuteShellChain)getNextChain()).setQueryDetail(isToday);
		((ExecuteShellChain)getNextChain()).setSql(sql);
		((ExecuteShellChain)getNextChain()).execute();
		
	}



	private boolean checkFileExsit(List<FileInfo> fis, String newFileName) {
		for(FileInfo fi : fis){
			if(fi.getFileName().contains(newFileName)){
				return true;
			}
		}
		return false;
	}
	


	public String combineCondition(String queryDate,boolean byDate) {
		if(dp.getAll_Dates().contains(queryDate)){
			return getGrepOrCmdByHour();
		}
		if(!byDate){
			return getGrepOrCmdByHour();
		}
		List<String> hosr = dp.getDateMap().get(queryDate);
		return getGrepOrCmd(hosr);
	}
	
	
	public String getGrepOrCmdByHour() {
		StringBuilder cmd   = new StringBuilder(" | grep -E '");
		if(StringUtils.isNotBlank(shellInfo.getFilter())){
			cmd.append("(");
			if(shellInfo.getFilter().endsWith("'")){
				cmd .append(shellInfo.getFilter().substring(0, shellInfo.getFilter().length() -1));
			}else {
				cmd.append(shellInfo.getFilter());
			}
			cmd.append(")");
		}
		
	
		for(String con:condition){
			if(StringUtils.isNotBlank(con)){
				cmd.append(".*").append(con);
			}
		}
		cmd.append("'");
		return cmd.toString();
	}

	//拼写条件
	public String getGrepOrCmd(List<String> args) {
		if (args == null || args.size() == 0)
			return "";
		StringBuilder cmd   = new StringBuilder(" | grep -E '(");
		for (String c : args) {
			if (StringUtils.isNotBlank(c)) {
				cmd.append(c + "|");
			}
		}
		if (cmd.toString().endsWith("|")) {
			cmd.deleteCharAt(cmd.length() - 1);
		}
		cmd.append(")");
		if(StringUtils.isNotBlank(shellInfo.getFilter())){
			cmd.append(".*(");
			if(shellInfo.getFilter().endsWith("'")){
				cmd .append(shellInfo.getFilter().substring(0, shellInfo.getFilter().length() -1));
			}else {
				cmd.append(shellInfo.getFilter());
			}
			cmd.append(")");
		}
		
	
		for(String con:condition){
			if(StringUtils.isNotBlank(con)){
				cmd.append(".*").append(con);
			}
		}
		cmd.append("'");
		return cmd.toString();
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
	 * @return the shellInfo
	 */
	public ShellInfo getShellInfo() {
		return shellInfo;
	}
	/**
	 * @param shellInfo the shellInfo to set
	 */
	public void setShellInfo(ShellInfo shellInfo) {
		this.shellInfo = shellInfo;
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
	
}
class ExeInfo{
	private List<FileInfo> files;
	private boolean queryDetail;
	private String sql;
	public ExeInfo(List<FileInfo> newFis, String sql2, boolean b) {
		this.files = newFis;
		this.sql = sql2;
		this.queryDetail = b;
	}
	/**
	 * @return the files
	 */
	public List<FileInfo> getFiles() {
		return files;
	}
	/**
	 * @param files the files to set
	 */
	public void setFiles(List<FileInfo> files) {
		this.files = files;
	}
	/**
	 * @return the queryDetail
	 */
	public boolean isQueryDetail() {
		return queryDetail;
	}
	/**
	 * @param queryDetail the queryDetail to set
	 */
	public void setQueryDetail(boolean queryDetail) {
		this.queryDetail = queryDetail;
	}
	/**
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}
	/**
	 * @param sql the sql to set
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	
}
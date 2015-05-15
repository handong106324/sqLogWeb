package com.sq.log.chain;

import java.util.List;

import org.apache.log4j.Logger;

import com.sq.common.Constant;
import com.sq.log.result.LogExecutor;
import com.sq.model.FileInfo;
import com.sq.model.ShellInfo;
import com.sq.shell.ShellDao;
import com.sq.shell.ShellFactory;
import com.sq.shell.currency.ThreadPool;
import com.sq.utils.MyEmailUtils;

public class ExecuteShellChain extends LogBaseChain{

	private Logger logger = LogExecutor.getLogger();
	private ShellInfo shellInfo ;
	private List<FileInfo> fis;
	private String sql;
	private boolean queryDetail;
	@Override
	public void execute() {
		currencyExc(fis, sql, queryDetail);
	}

	private void currencyExc(List<FileInfo> fis,final String sql, final boolean queryDetail) {
		getData().setScheduleMsg("执行查询......");
		getData().setStatus(Constant.SHELL_STATUS_QUERYING);
		try{
		for(final FileInfo fi :fis){
			ThreadPool.getInstance().execute(new Runnable() {
				
				public void run() {
					long start = System.currentTimeMillis();
					getData().setScheduleMsg(ShellDao.getFileShowName(fi.getFileName()),ShellDao.getFileShowName(fi.getFileName())+"：文件大小="+(fi.getSize()/1024/1024)+"M");
					if(shellInfo.isShell()){
						ShellFactory.getInstance().queryResultByShell(fi.getFileName(), sql==null?"":sql,  shellInfo, getData());
					}	else {
						ShellFactory.getInstance().queryResultByWeb(fi.getFileName(),sql==null?"":sql, shellInfo,getData());
					}
					getData().setScheduleMsg(ShellDao.getFileShowName(fi.getFileName()),"结果数:"+getData().getFileResultCount(fi.getFileName())+","+(queryDetail?ShellFactory.getInstance().lastLogTime(fi.getFileName(), shellInfo):""));
					long end = System.currentTimeMillis();
					getData().setOK(ShellDao.getFileShowName(fi.getFileName()));
					getData().setScheduleMsg(ShellDao.getFileShowName(fi.getFileName())+":查询完毕");
					logger.debug("[LogExecutor][currencyExc][file:"+fi.getFileName()+"][执行时间:"+(end - start)/1000+"s][结果数:"+getData().getFileResultCount(fi.getFileName())+"][文件大小："+fi.getSize()/1024/1024+"M][查询："+sql+"][是否查询详细:"+queryDetail+"]");
//					if((end  - start) > 60*1000){
////						MyEmailUtils.sendEmail("handong@sqage.com", "扫描文件过长提醒", null, "扫描["+fi.getFileName()+"]["+ShellDao.getFileShowName(fi.getFileName())+"][用时："+(end - start)/1000+"s]");
//					}
				}
			});
		}
		
		} catch (Exception e) {
			logger.error(e);
		}
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
	 * @return the fis
	 */
	public List<FileInfo> getFis() {
		return fis;
	}

	/**
	 * @param fis the fis to set
	 */
	public void setFis(List<FileInfo> fis) {
		this.fis = fis;
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

}

package com.sq.model;

import java.util.List;

import com.sq.entity.LogPageInfo;

public class PageResult {

	private List<LogPageInfo> userfulPages;
	private int count;
	private String msg;
	private List datas;
	/**
	 * @return the userfulPages
	 */
	public List<LogPageInfo> getUserfulPages() {
		return userfulPages;
	}
	/**
	 * @param userfulPages the userfulPages to set
	 */
	public void setUserfulPages(List<LogPageInfo> userfulPages) {
		this.userfulPages = userfulPages;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the datas
	 */
	public List getDatas() {
		return datas;
	}
	/**
	 * @param datas the datas to set
	 */
	public void setDatas(List datas) {
		this.datas = datas;
	}
	
	
}

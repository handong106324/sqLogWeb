package com.sq.shell.result;

import java.util.List;

public class LogResult {

	private long count;
	private List<String> datas;
	private String msg ;
	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(long count) {
		this.count = count;
	}
	/**
	 * @return the datas
	 */
	public List<String> getDatas() {
		return datas;
	}
	/**
	 * @param datas the datas to set
	 */
	public void setDatas(List<String> datas) {
		this.datas = datas;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}

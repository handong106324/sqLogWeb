package com.sq.log.result;

import java.util.ArrayList;
import java.util.List;

import com.sq.model.FileInfo;

public class LogFileCheckInfo {

	private boolean isSuccess;
	private long size;
	private String msg;
	private String lastModifyTime;
	private List<FileInfo> files = new ArrayList<FileInfo>();
	
	private boolean isByDate = true;
	/**
	 * @return the isByDate
	 */
	public boolean isByDate() {
		return isByDate;
	}
	/**
	 * @param isByDate the isByDate to set
	 */
	public void setByDate(boolean isByDate) {
		this.isByDate = isByDate;
	}
	/**
	 * @return the isSuccess
	 */
	public boolean isSuccess() {
		return isSuccess;
	}
	/**
	 * @param isSuccess the isSuccess to set
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	/**
	 * @return the size
	 */
	public long getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(long size) {
		this.size = size;
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
	public String getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public List<FileInfo> getFiles() {
		return files;
	}
	public void setFiles(List<FileInfo> files) {
		this.files = files;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LogFileCheckInfo [isSuccess=" + isSuccess + ", size=" + size
				+ ", msg=" + msg + ", lastModifyTime=" + lastModifyTime
				+ ", files.size=" + files .size()+ ", isByDate=" + isByDate + "]";
	}
	
	
}


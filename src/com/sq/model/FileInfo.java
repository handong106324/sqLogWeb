package com.sq.model;

public class FileInfo{
	private String fileName;
	private String lastModifyTime;
	private long size;
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the lastModifyTime
	 */
	public String getLastModifyTime() {
		return lastModifyTime;
	}
	/**
	 * @param lastModifyTime the lastModifyTime to set
	 */
	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
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
	
}


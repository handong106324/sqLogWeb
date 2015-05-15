package com.sq.model;

import org.apache.commons.lang.StringUtils;

import com.sq.entity.LogFileInfo;

public class ShellInfo {
		private boolean isShell;
		private String ip;
		private String port;
		private boolean isMany;
		private String filter;
		private int count;
		private String path;
		public ShellInfo(){
			
		}
		public ShellInfo(LogFileInfo fileInfo) {
			String queryType = fileInfo.getStr("queryType");
			if(StringUtils.isNotBlank(queryType) && queryType.equals("shell")){
				isShell = true;
				this.ip = fileInfo.getStr("ip");
				this.port = fileInfo.getStr("port");
			}else{
				isShell = false;
				this.ip = fileInfo.getStr("webIp");
				this.port = fileInfo.getStr("webPort");
			}
			this.filter = fileInfo.getStr("queryWhere");
			String many = fileInfo.getStr("recordType");
			if(StringUtils.isNotBlank(many) && many.equals("MANY")){
				isMany = true;
			}else {
				isMany = false;
			}
			String fileName = fileInfo.getStr("fileName");
			if(!fileName.endsWith(".log")){
				fileName += ".log";
			}
			this.setPath(fileInfo.getStr("dir")+fileName) ;
		}

		public ShellInfo(LogFileInfo fileInfo,int count) {
			String queryType = fileInfo.getStr("queryType");
			if(StringUtils.isNotBlank(queryType) && queryType.equals("shell")){
				isShell = true;
				this.ip = fileInfo.getStr("ip");
				this.port = fileInfo.getStr("port");
			}else{
				isShell = false;
				this.ip = fileInfo.getStr("webIp");
				this.port = fileInfo.getStr("webPort");
			}
			this.filter = fileInfo.getStr("queryWhere");
			String many = fileInfo.getStr("recordType");
			if(StringUtils.isNotBlank(many) && many.equals("MANY")){
				isMany = true;
				count *=3;
			}else {
				isMany = false;
			}
			String fileName = fileInfo.getStr("fileName");
			if(!fileName.endsWith(".log")){
				fileName += ".log";
			}
			this.setPath(fileInfo.getStr("dir")+fileName) ;
		}

//		public String getPathByServerName(String serverName,String type,String queryDate) {
//			String fileName = info.getStr("fileName");
//			if(!fileName.endsWith(".log")){
//				fileName += ".log";
//			}
//			return info.getStr("dir")+fileName + dp.getSufix(queryDate);
//		}
		/**
		 * @return the isShell
		 */
		public boolean isShell() {
			return isShell;
		}


		/**
		 * @param isShell the isShell to set
		 */
		public void setShell(boolean isShell) {
			this.isShell = isShell;
		}


		/**
		 * @return the ip
		 */
		public String getIp() {
			return ip;
		}


		/**
		 * @param ip the ip to set
		 */
		public void setIp(String ip) {
			this.ip = ip;
		}


		/**
		 * @return the port
		 */
		public String getPort() {
			return port;
		}


		/**
		 * @param port the port to set
		 */
		public void setPort(String port) {
			this.port = port;
		}


		/**
		 * @return the isMany
		 */
		public boolean isMany() {
			return isMany;
		}


		/**
		 * @param isMany the isMany to set
		 */
		public void setMany(boolean isMany) {
			this.isMany = isMany;
		}


		/**
		 * @return the filter
		 */
		public String getFilter() {
			return filter;
		}


		/**
		 * @param filter the filter to set
		 */
		public void setFilter(String filter) {
			this.filter = filter;
		}


		/**
		 * @return the count
		 */
		public long getCount() {
			return count;
		}


		/**
		 * @param count the count to set
		 */
		public void setCount(int count) {
			this.count = count;
		}


		public String getPath() {
			return path;
		}


		public void setPath(String path) {
			this.path = path;
		}
		
}

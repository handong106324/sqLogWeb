package test.data;

import java.util.List;

public class QueryCondition{
	private String filePath;
	private String[] titles;
	private String ip;
	private String port;
	private List<String> userNames;
	private String filter;
	private boolean byHour = false;
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * @return the titles
	 */
	public String[] getTitles() {
		return titles;
	}
	/**
	 * @param titles the titles to set
	 */
	public void setTitles(String[] titles) {
		this.titles = titles;
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
	 * @return the userNames
	 */
	public List<String> getUserNames() {
		return userNames;
	}
	/**
	 * @param userNames the userNames to set
	 */
	public void setUserNames(List<String> userNames) {
		this.userNames = userNames;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public boolean isByHour() {
		return byHour;
	}
	public void setByHour(boolean byHour) {
		this.byHour = byHour;
	}
	
	
}
package com.sq.model;

import com.sq.common.Constant;

public class TaskFlow {

	private String logStr ;
	private String[] keys = new String[]{"获得","增加","奖励"};
	private String playerName = "";
	private String taskName = "";
	private String reward = "";
	public TaskFlow(String str){
		this.logStr = str;
		init();
	}
	private void init() {
		logStr = logStr.replaceAll("\\[", Constant.SQ_SEPARATOR).replaceAll("\\]",  Constant.SQ_SEPARATOR);
		String[] logStrs = logStr.split(Constant.SQ_SEPARATOR);
		for(String log : logStrs){
			if(log.contains("name:") && !log.contains("username:") ){
				String[] ns = log.split(":");
				if(ns.length > 1)this.playerName = ns[1];
			}else if(log.contains("完成任务:")){
				taskName = log.split(":")[1];
			}
			if(!contains(log)){
				reward += "["+log+"]";
			}
		}
	}
	private boolean contains(String log) {
		for(String key :keys){
			if(log.contains(key)){
				return false;
			}
		}
		return true;
	}
	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}
	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}
	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * @return the reward
	 */
	public String getReward() {
		return reward;
	}
	/**
	 * @param reward the reward to set
	 */
	public void setReward(String reward) {
		this.reward = reward;
	}
	
	
}

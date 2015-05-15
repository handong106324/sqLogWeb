package com.sq.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sq.common.Constant;

public class BehaviorFlow {
	private String time;
	List<String> infos = new ArrayList<String>();
	BehaviorModel data = new BehaviorModel();
	/**
	 * @return the data
	 */
	public BehaviorModel getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(BehaviorModel data) {
		this.data = data;
	}

	public BehaviorFlow(String logSt){
		String logStr = logSt.replaceAll("\\[", Constant.SQ_SEPARATOR).replaceAll("\\]",  Constant.SQ_SEPARATOR);
		String[] logStrs = logStr.split(Constant.SQ_SEPARATOR);
		for(String log :logStrs){
			if(StringUtils.isNotBlank(log)){
				infos.add(log.trim());
			}
		}
		if(infos.size() > 3){
			this.time = infos.get(1);
			data.setTime(time);
		}
		parse();
	}
	
	private void parse() {
		boolean loopFlag = true;
		for(String log : infos){
			if(loopFlag){
				if(log.equals("-")){
					loopFlag = false;
				}
				continue;
			}
			if(log.contains("playerId:")){
				String[] vals = log.split(":");
				data.setPlayerId(vals[vals.length-1]);
			}else if(log.contains("username:")){
				String[] vals = log.split(":");
				data.setUserName(vals[vals.length - 1]);
			}else if(log.contains("name:")){
				String[] vals = log.split(":");
				data.setPlayerName(vals[vals.length - 1]);
			}else {
				data.setMsg(data.getMsg()+" "+log);
			}
		}
	}





	/**
	 * @param proVal
	 */

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}

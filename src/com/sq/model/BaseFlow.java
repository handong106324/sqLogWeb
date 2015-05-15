package com.sq.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sq.common.Constant;

public class BaseFlow {
	private String time;
	private Map<String,String> mapInfos = new HashMap<String,String>();
	List<String> infos = new ArrayList<String>();
	Map<String,String> toColNameMap = new HashMap<String,String>();
	int ind_ =0;
	private String seqType = "";
	private PageParam para;
	private boolean canUse = true;
	public BaseFlow(String logSt, PageParam param){
		this.setPara(param);
		initData(logSt);
		loadAllData(logSt);
		init();
	}
	
	private void initData(String logSt) {
		Iterator<String> k =para.getSeqMap().keySet().iterator();
		while(k.hasNext()){
			String key = k.next();
			if(logSt.contains(key)){
				this.seqType = key;
				break;
			}
		}
	}

	protected void loadAllData(String logSt) {
		String logStr = logSt.replaceAll("\\[", Constant.SQ_SEPARATOR).replaceAll("\\]",  Constant.SQ_SEPARATOR);
		logStr = logStr.replaceAll("\\{", Constant.SQ_SEPARATOR).replaceAll("\\}",  Constant.SQ_SEPARATOR);
		String[] logStrs = logStr.split(Constant.SQ_SEPARATOR);
		for(String log :logStrs){
			if(StringUtils.isNotBlank(log)){
				infos.add(log.trim());
			}
		}
	}

	private void init() {
		if(infos.size() > 3){
			this.time = infos.get(1);
		}
		combine();
	}


	private void combine() {
		boolean loopFlag = true;
		if(!canUse){
			return;
		}
		for(String proVal : infos){
			if(loopFlag){
				if(proVal.equals("-") || (proVal.endsWith("-")) ||(proVal.startsWith("-"))){
					loopFlag = false;
				}
				continue;
			}
			proVal = proVal.replaceAll("：", ":");
			handleVal(proVal);
			ind_++;
		}
	}

	protected void handleVal(String proVal) {
		String type = this.para.getType(proVal,ind_,seqType);
		if(type.equals(PageParam.KEY) || type.equals(PageParam.KEYS)){
			parseKey(proVal,getPrefix(proVal));
		}else if(type.equals(PageParam.CONTAIN)){
			parseContain(proVal);
		}else if(type.equals(PageParam.VALUE)){
			parseValue(proVal);
		}else if(type.equals(PageParam.SEQ)){
			if(StringUtils.isNotBlank(proVal))parseSeqValue(proVal,ind_);
		}else {
			if(para.isOtherFlag()){
				String old = mapInfos.get(para.getOtherShowName());
				if(StringUtils.isBlank(old)){
					old = "";
				}
				handleOtherInfo(old,proVal);
			}
		}
	}

	private void parseSeqValue(String proVal, int ind_2) {
		String vl = proVal;
		if(proVal.contains(":") ){
			vl = proVal.split(":")[1];
		}
		if(proVal.contains("=")){
			vl = proVal.split("=")[1];
		}
		Map<Integer,String> m = para.getSeqMap().get(seqType);
		if(m != null && m.get(ind_2) != null)put_(m.get(ind_2), vl);
	}

	public void handleOtherInfo(String old, String proVal) {
		mapInfos.put(para.getOtherShowName(), old+"  "+proVal);
	}

	private String getPrefix(String proVal) {
		if(proVal.contains(":") && !proVal.contains("=")){
			return ":";
		}
		if(!proVal.contains(":") && proVal.contains("="))
		{
			return "=";
		}
		if(proVal.contains("=") && proVal.contains(":") ){
			return ":";
		}
		return ":";
	}


	private void parseValue(String proVal) {
		Iterator<String> it = para.getValueMap().keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			if(proVal.contains(key)){
				put_(para.getValueMap().get(key),para.getValueShowMap().get(proVal));
				break;
			}
		}
	}

	private void parseContain(String proVal) {
		Iterator<String> it = para.getContainMap().keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			if(proVal.contains(key)){
				put_(para.getContainMap().get(key),proVal);
				break;
			}
		}
	}

	private void parseKey(String proVal,String keyPrix) {
			String[] nv = proVal.split(keyPrix);
			if(nv.length ==2){
				if(para.getKeysMap()  != null && para.getKeysMap() .containsKey(nv[0])){
					String listName =para.getKeysMap() .get(nv[0]);
					put_(listName, nv[1]);
				}else {
					put_(para.getKeyMap().get(nv[0]), nv[1]);
				}
			}else if(nv.length == 3){
				if(para.getKeysMap().containsKey(nv[0]+keyPrix+nv[1])){
					put_(para.getKeysMap().get(nv[0]+keyPrix+nv[1]), nv[2]);
				}else if(para.getKeyMap().containsKey(nv[0]+keyPrix+nv[1])){
					put_(para.getKeyMap().get(nv[0]+keyPrix+nv[1]), nv[2]);
				}
			}
	}

	protected void put_(String key ,String val){
		String oldVal = mapInfos.get(key);
		if(StringUtils.isBlank(oldVal)){
			mapInfos.put(key, val);
		}else {
			mapInfos.put(key, oldVal+",<br>"+val);
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
	public Map<String, String> getMapInfos() {
		return mapInfos;
	}

	
	public void setMapInfos(Map<String, String> mapInfos) {
		this.mapInfos = mapInfos;
	}

	public PageParam getPara() {
		return para;
	}

	public void setPara(PageParam para) {
		this.para = para;
	}

	public boolean canUse() {
		return canUse;
	}
	
	public void setCanUse(Boolean b){
		this.canUse = b;
	}

}

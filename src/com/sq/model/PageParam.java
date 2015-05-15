package com.sq.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sq.cache.Cache;
import com.sq.entity.GAME;
import com.sq.entity.LOGTYPE;
import com.sq.entity.LogPageInfo;

public class PageParam {

	public static String CONTAIN = "contain";
	public static String KEY = "key";
	public static String KEYS = "keys";
	public static String VALUE = "value";
//	public static String ORDER_INDEX = "orderIndex";
	
	public static String SEQ = "sequence";

	private Map<String, String> keyMap = new HashMap<String, String>();
	private Map<String, String> keysMap = new HashMap<String, String>();
	private Map<String, String> containMap = new HashMap<String, String>();
	private Map<String,String>  valueMap = new HashMap<String,String>();
	private Map<String,String>  valueShowMap = new HashMap<String,String>();
	private Map<String,String>  typeCacheMap = new HashMap<String,String>();
	
	private Map<String,Map<Integer,String>>  seqMap = new HashMap<String,Map<Integer,String>>();
	/**
	 * @return the seqMap
	 */
	public Map<String, Map<Integer, String>> getSeqMap() {
		return seqMap;
	}

	/**
	 * @param seqMap the seqMap to set
	 */
	public void setSeqMap(Map<String, Map<Integer, String>> seqMap) {
		this.seqMap = seqMap;
	}


	private String resultHandleType;
	/**
	 * @return the valueShowMap
	 */
	public Map<String, String> getValueShowMap() {
		return valueShowMap;
	}

	/**
	 * @param valueShowMap the valueShowMap to set
	 */
	public void setValueShowMap(Map<String, String> valueShowMap) {
		this.valueShowMap = valueShowMap;
	}

	private boolean otherFlag = false;
	private String otherShowName;
	private String type;
	private List<LogPageInfo> usefualPages = new ArrayList<LogPageInfo>();

	/**
	 * @return the usefualPages
	 */
	public List<LogPageInfo> getUsefualPages() {
		return usefualPages;
	}

	public void setUsefualPages(List<LogPageInfo> usefualPages) {
		this.usefualPages = usefualPages;
	}

//	public PageParam(LOGTYPE type) {
//
//		this.type = type;
//		init(GAME.GAME_WHCL);
//	}

	public PageParam(String type2, String game) {
		this.type = type2;
		init(game);
	}

	private void init(String game) {
		//获取需要显示的信息
		this.usefualPages = Cache.getLogPageInfos(type, game);
		List<LogPageInfo> all = Cache.getAllLogPageInfos(type,game);//LogPageInfo.dao.findPart("`key`='"+type.getIntro()+"' order by sortIndex");
		for(LogPageInfo info : all){
			List<String> vals = new ArrayList<String>();
			String showName = info.getStr("colName");
			String showKey  = info.getStr("colVals");
			String type     = info.getStr("type");
			boolean isAttach = StringUtils.isNotBlank(info.getStr("attachTo"));
			if(isAttach){
				showName = info.getStr("attachTo");
			}
			if(type.equals(CONTAIN)){
				vals.add(info.getStr("colVals"));
				String[] vas = showKey.split(",");
				for(String va :vas){
					containMap.put(va, showName);
				}
				
			}else if(type.equals(KEYS)){
				String[] vs = showKey.split(",");
				for(String va :vs){
					keysMap.put(va, showName);
				}
				vals.add(info.getStr("colName"));
			}else if(type.equals(KEY)){
				keyMap.put(showKey, showName);
				vals.add(showKey);
			}else if(type.equals(VALUE)){
				String[] values = showKey.split(",");
				for(String value :values){
					if(StringUtils.isNotBlank(value)){
						String[] kvs = value.split(":");
						valueMap.put(kvs[0], showName);
						valueShowMap.put(kvs[0], kvs[1]);
					}
				}
			}else if(info.getStr("type").equals("other")){
				this.setOtherFlag(true);
				this.otherShowName = info.getStr("colName");
			}else if(type.equals(SEQ)){
				if(!showKey.contains(":")) continue;
				String[] tps = showKey.split(",");
				for(String tp :tps){
					String type3 = tp.split(":")[0];
					if(seqMap.get(type3) == null){
						seqMap.put(type3, new HashMap<Integer,String>());
					}
					Map<Integer,String> mp = seqMap.get(type3);
					String v = tp.split(":")[1];
					if(v.contains("+")){
						String[] vvs = v.replace("+", ",").split(",");
						for(String vs :vvs){
							mp.put(Integer.parseInt(vs), showName);
							seqMap.put(type3, mp);
						}
					}else {
						mp.put(Integer.parseInt(v), showName);
						seqMap.put(type3, mp);
					}
					
				}
			}
				
		}
	}

	public String getType(String proVal,int index,String seq_type) {
		if(proVal.contains("：")){
			proVal = proVal.replaceAll("：", ":");
		}
		if(proVal.contains("=")){
			proVal = proVal.replaceAll("=", ":");
		}
		String cacheType = typeCacheMap.get(proVal);
		if(cacheType != null)return cacheType;
		String keyPrix = ":";
		if (proVal.contains(keyPrix) && notInContain(proVal)) {
			String[] nv = proVal.split(keyPrix);
			if (nv.length == 2) {
				if (keysMap != null && keysMap.containsKey(nv[0])) {
					typeCacheMap.put(proVal, KEYS);
					return KEYS;
				}
				if (keyMap.containsKey(nv[0])) {
					typeCacheMap.put(proVal, KEY);
					return KEY;
				}
			} else if (nv.length == 3) {
				if (keyMap.containsKey(nv[0] + keyPrix + nv[1])) {
					typeCacheMap.put(proVal, KEY);
					return KEY;
				}
				if (keysMap.containsKey(nv[0] + keyPrix + nv[1])) {
					typeCacheMap.put(proVal, KEYS);
					return KEYS;
				}
			}
		}
		if(isValueType(proVal)){
			typeCacheMap.put(proVal, VALUE);
			return VALUE;
		}
		if (!notInContain(proVal)) {
			typeCacheMap.put(proVal, CONTAIN);
			return CONTAIN;
		}
		if(seqMap.get(seq_type)!=null && seqMap.get(seq_type).containsKey(index)){
			typeCacheMap.put(proVal, SEQ);
			return SEQ;
		}
//		if(orderMap.get(index+"") != null){
//			typeCacheMap.put(proVal, ORDER_INDEX);
//			return ORDER_INDEX;
//		}
//		typeCacheMap.put(proVal, "");
		return "";
	}



	//固定值较多，可以用缓存
	private boolean isValueType(String proVal) {
		Iterator<String> keys = valueMap.keySet().iterator();
		while(keys.hasNext()){
			String key = keys.next();
			if(key.equals(proVal)){
				return true;
			}
		}
		return false;
	}
	//固定值较多，可以用缓存
	private boolean notInContain(String proVal) {
		Iterator<String> keys = containMap.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			if(proVal.contains(key)){
				return false;
			}
		}
		return true;
	}


	/**
	 * @return the keysMap
	 */
	public Map<String, String> getKeysMap() {
		return keysMap;
	}

	/**
	 * @param keysMap
	 *            the keysMap to set
	 */
	public void setKeysMap(Map<String, String> keysMap) {
		this.keysMap = keysMap;
	}

	/**
	 * @return the containMap
	 */
	public Map<String, String> getContainMap() {
		return containMap;
	}

	/**
	 * @param containMap
	 *            the containMap to set
	 */
	public void setContainMap(Map<String, String> containMap) {
		this.containMap = containMap;
	}

	public Map<String, String> getKeyMap() {
		return keyMap;
	}

	public void setKeyMap(Map<String, String> keyMap) {
		this.keyMap = keyMap;
	}

	public boolean isOtherFlag() {
		return otherFlag;
	}

	public void setOtherFlag(boolean otherFlag) {
		this.otherFlag = otherFlag;
	}

	public String getOtherShowName() {
		return otherShowName;
	}

	public void setOtherShowName(String otherShowName) {
		this.otherShowName = otherShowName;
	}

	public Map<String,String> getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map<String,String> valueMap) {
		this.valueMap = valueMap;
	}


	public String getResultHandleType() {
		return resultHandleType;
	}

	public void setResultHandleType(String resultHandleType) {
		this.resultHandleType = resultHandleType;
	}

}

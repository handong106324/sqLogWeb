package com.sq.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class MyListUtils {

	
	public static boolean contains(String[] array,String arr){
		for(String ar:array){
			if(arr.equals(ar)){
				return true;
			}
		}
		return false;
	}

	public static boolean contains(List<String> array,String arr){
		for(String ar:array){
			if(arr.equals(ar)){
				return true;
			}
		}
		return false;
	}

	public static String[] deleteFromArray(String str,String[] ids) {
		List<String> list = new ArrayList<String>();
		for(String as :ids){
			if(!as.equals(str)){
				list.add(as);
			}
		}
		String[] rets = new String[list.size()];
		int size = list.size();
		for(int i = 0 ; i < size;i++){
			rets[i] = list.get(i);
		}
		return rets;
	}

	public static Object sortDateString(List<String> timeList) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Long> ll = new ArrayList<Long>();
		Map<Long,String> map = new HashMap<Long, String>();
		List<String> ret = new ArrayList<String>();
		for(String s :timeList){
			try {
				Date d = sdf.parse(s);
				ll.add(d.getTime());
				map.put(d.getTime(), s);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Collections.sort(ll);
		for(Long l :ll){
			ret.add(map.get(l));
		}
		return ret;
	}

	@SuppressWarnings("rawtypes")
	public static Map<Integer,? extends Model> toMap(List<? extends Model> needModels) {
		// TODO Auto-generated method stub
		Map<Integer,Model> map = new HashMap<Integer, Model>();
		for(Model model : needModels){
			if(model != null){
				map.put(model.getInt("id"), model);
			}
		}
		return map;
	}
	
	public static Map<Long,? extends Model> toMapLong(List<? extends Model> needModels) {
		// TODO Auto-generated method stub
		Map<Long,Model> map = new HashMap<Long, Model>();
		for(Model model : needModels){
			if(model != null){
				map.put(model.getLong("id"), model);
			}
		}
		return map;
	}
	@SuppressWarnings("rawtypes")
	public static Map<Integer,? extends Model> toMap(List<? extends Model> needModels,String proName) {
		// TODO Auto-generated method stub
		Map<Integer,Model> map = new HashMap<Integer, Model>();
		for(Model model : needModels){
			if(model != null)map.put(model.getInt(proName), model);
		}
		return map;
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<String,? extends Model> toStringMap(List<? extends Model> needModels,String proName) {
		// TODO Auto-generated method stub
		Map<String,Model> map = new HashMap<String, Model>();
		for(Model model : needModels){
			if(model != null)map.put(model.getStr(proName), model);
		}
		return map;
	}
	
	public static Map<String,String> toStringMap(List<? extends Model> needModels,String keyProName,String valueProName) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String, String>();
		for(Model model : needModels){
			if(model != null)map.put(model.getStr(keyProName),model.getStr(valueProName));
		}
		return map;
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<Integer,List<Model>> toMapV2(List<? extends Model> needModels,String proName) {
		Map<Integer,List<Model>> map = new HashMap<Integer, List<Model>>();
		for(Model<Model> model : needModels){
			if(model != null){
				int id = model.getInt(proName);
				List<Model> lst = map.get(id);
				if(lst == null)
				{	
					lst = new ArrayList<Model>();
					map.put(id, lst);
				}
				if(!lst.contains(model))lst.add(model);
					map.put(model.getInt(proName), lst);
			}
		}
		return map;
	}

	
	public static String getIdsStr(List<? extends Model> modles){
		StringBuffer sbg = new StringBuffer("");
		for(Model m :modles){
			sbg.append(m.getInt("id"));
			sbg.append(",");
		}
		if(sbg.toString().endsWith(",")){
			sbg.deleteCharAt(sbg.length() - 1);
		}
		if(sbg.length() == 0){
			return "-1";
		}
		return sbg.toString();
	}
	@SuppressWarnings("rawtypes")
	public static String getIdsStr(List<? extends Model> models, String proName) {
		StringBuffer sbg = new StringBuffer("");
		List<String> hasList = new ArrayList<String>();
		for(Model m :models){
			String val = m.getStr(proName);
			if(val != null && val.length() >0){
				if(!hasList.contains(val)){
					sbg.append(val);
					sbg.append(",");
					hasList.add(val);
				}
			}
		}
		if(sbg.toString().endsWith(",")){
			sbg.deleteCharAt(sbg.length() - 1);
		}
		if(sbg.length() == 0){
			return "-1";
		}
		return sbg.toString();
	}

	public static String getIdsStrForString(List<Model> models,String proName) {
		StringBuffer sbg = new StringBuffer("");
		List<String> hasList = new ArrayList<String>();
		for(Model m :models){
			String val = m.getStr(proName);
			if(val != null && val.length() >0){
				if(!hasList.contains(val)){
					sbg.append("'"+val+"'");
					sbg.append(",");
					hasList.add(val);
				}
			}
		}
		if(sbg.toString().endsWith(",")){
			sbg.deleteCharAt(sbg.length() - 1);
		}
		if(sbg.length() == 0){
			return "-1";
		}
		return sbg.toString();
	}
	
}

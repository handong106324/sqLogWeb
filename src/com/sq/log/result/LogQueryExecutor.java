package com.sq.log.result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.sq.cache.Cache;
import com.sq.common.Constant;
import com.sq.entity.LogPageInfo;
import com.sq.utils.MyTimeUtils;

public class LogQueryExecutor{

	private static Logger logger = Logger.getLogger(LogQueryExecutor.class);

	private List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
//	private String mainTableName;
	private String startTime;
	private String endTime;
	
	private String[] mainTableCols;
	private String[] mainTableVals;
	
	private List<LogPageInfo> lpfs ;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf_hour = new SimpleDateFormat("yyyy-MM-dd HH");
	SimpleDateFormat sdf_ALL = new SimpleDateFormat("yyyyMMdd");
	public LogQueryExecutor(String startTime, String endTime, String type,	String[] paras, String[] vals,String game) {
		this.mainTableCols = paras;
		this.mainTableVals = vals;
		this.startTime = startTime;
		this.endTime = endTime;
//		this.mainTableName = type;//Constant.getTableName(type);
		this.lpfs = Cache.getLogPageInfos(type, game);
		execute(type);
	}
	/**
	 * 
	 */
	private void execute(String basicTableName) {
		boolean isDaily = Constant.checkIsDaily(basicTableName);
		//加载主表新数据，辅助信息放入公共信息
		try {
			long st = sdf_hour.parse(startTime).getTime();
			long r_et = sdf_hour.parse(endTime).getTime();
			long et = sdf.parse(endTime).getTime()+MyTimeUtils.ONEDAY ;
			if(!isDaily){
				loadMainTableData(basicTableName,st,r_et);
			}else {
				for(long zeroClock = st ; zeroClock <et;zeroClock +=  MyTimeUtils.ONEDAY ){
					String dateformat = sdf_ALL.format(new Date(zeroClock+1000));
					loadMainTableData(basicTableName+dateformat, zeroClock, zeroClock+MyTimeUtils.ONEDAY);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void loadMainTableData(String table,long st,long et) {
		long start = System.currentTimeMillis();
		String sql = "select * from "+table+" where 1=1 ";
		try {
			
			int index = 0;
			for(String para :mainTableCols){
				if(StringUtils.isNotBlank(para) && StringUtils.isNotBlank(mainTableVals[index])){
					sql +=" and "+ para+"= '"+mainTableVals[index]+"'";
				}
				index ++;
			}
			

			sql += " and `now` between "+ st+" and "+et;
			List<Record> ds = Db.find(sql);
			if(ds==null) return;
		
			logger.debug("[LogQueryExecutor][loadMainTableData][查询数据："+sql+"][结果数："+ds.size()+"]");
			for(Record md : ds){
				dataList.add(changeToMap(md));
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		logger.debug("[LogQueryExecutor][loadMainTableData][查询数据："+sql+"][结果数："+dataList.size()+"][耗时："+(System.currentTimeMillis() - start)+"]");
	}
	
	private Map<String, String> changeToMap(Record md) {
		Map<String,String> res = new HashMap<String, String>();
		for(LogPageInfo lpf : lpfs){
			//foreignKey -- 定位到Table 以及Model，并且该key为获取所有Map的公共key
			if(StringUtils.isNotBlank(lpf.getStr("foreignKey"))){
				res.put(lpf.getStr("colName"), getForeignVal(lpf.getStr("foreignKey"),lpf.getStr("foreignCol"),md.getLong(lpf.getStr("targetId")),lpf.getStr("key"),getShowVal(lpf,md.getStr(lpf.getStr("colVals")))));
			}else {
				String colVals = lpf.getStr("colVals");
				if(colVals.equals("now")){
					res.put(lpf.getStr("colName"), MyTimeUtils.BOTH.format(new Date(md.getLong(colVals))));
				}else 
					res.put(lpf.getStr("colName"), getShowVal(lpf,md.getStr(colVals)));
			}
		}
		return res;
	}
	
	private String getShowVal(LogPageInfo lpf, String str) {
		String showVals = lpf.getStr("showValue");
		if(StringUtils.isBlank(showVals))return str;
		String[] showValues = showVals.replaceAll("，", ",").split(",");
		for(String showVal :showValues){
			String[] vals = showVal.replaceAll("：", ":").split(":");
			if(vals[0].equals(str)){
				return vals[1];
			}
		}
		return str;
	}
	private String getForeignVal(String foreignKey,String foreignCol ,Long targetId,String dataKey, String colVals) {
		Map<Long, Record> foreignMap = Cache.getForeignDataMap(foreignKey);
		if(foreignMap.get(targetId) ==null){
			Cache.reloadForeignDataMap(foreignKey);
			foreignMap = Cache.getForeignDataMap(foreignKey);
			if(foreignMap.get(targetId) ==null){
				return "-ERROR-";
			}
		}
		String val = foreignMap.get(targetId).getStr(foreignCol);
		if(dataKey.equals("value") && colVals.contains(":")){
			String[] vls = colVals.split(",");
			for(String v : vls){
				String [] vs = v.split(":");
				if(vs[0].equals(val)){
					return vs[1];
				}
			}
		}
		if(colVals.equals("now")){
			val = MyTimeUtils.BOTH.format(new Date(foreignMap.get(targetId).getLong(foreignCol)));
		}
		return val;
	}
	public List<Map<String,String>> getDataList() {
		return dataList;
	}
	public void setDataList(List<Map<String,String>> dataList) {
		this.dataList = dataList;
	}

}

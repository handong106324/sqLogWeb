package com.sq.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DateParam {

	private Date st;
	private Date et;
	private Date dateSt;
	private Date dateEt;
	private int stHour;
	private int etHour;
	private List<String> dates = new ArrayList<String>();
	private List<String> half_Dates = new ArrayList<String>();
	private List<String> all_Dates = new ArrayList<String>();
	private Map<String,List<String>> dateMap = new HashMap<String,List<String>>();
	SimpleDateFormat sdt_date = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdt_hour = new SimpleDateFormat("yyyy-MM-dd HH");
	SimpleDateFormat sdt_only_hour = new SimpleDateFormat("HH");
	private Map<String,List<String>> date_hourMap = new HashMap<String,List<String>>();
	private Map<String,Integer[]>  dateTimeDuring = new HashMap<String,Integer[]>();
	private long ONEDAY = 24 * 60 * 60 * 1000L;
	public DateParam(String startTime,String endTime){
		try {
			this.st = sdt_hour.parse(startTime);
			this.et = sdt_hour.parse(endTime);
			this.dateEt = sdt_date.parse(endTime);
			this.dateSt = sdt_date.parse(startTime);
			this.stHour = Integer.parseInt(sdt_only_hour.format(st));
			this.etHour = Integer.parseInt(sdt_only_hour.format(et));
			init();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	private void init() {
		if(check()){
			parseDate();
		}
	}
	private void parseDate() {
		
		if(dateSt.getTime() == dateEt.getTime()){
			List<String> hours = new ArrayList<String>();
			String date = sdt_date.format(dateSt);
				for(int i = stHour; i <= etHour;i++){
					String dh = date+" "+(i<10?"0"+i:i);
					hours.add(dh);
					putDateHours(date,date+"-"+(i<10?"0"+i:i));
				}
			dateTimeDuring.put(date, new Integer[]{stHour,etHour});
			dateMap.put(date,hours);
			dates.add(date);
			half_Dates.add(date);
		}else {
			long len = (dateEt.getTime() - dateSt.getTime())/ONEDAY ;
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateSt);
			for(int i = 0 ; i < len +1;i++){
				String date = sdt_date.format(cal.getTime());
				if(i == 0){
					dateMap.put(date, parseHours(stHour, 24,date));
					half_Dates.add(date);
					dateTimeDuring.put(date, new Integer[]{stHour,24});
				}else if(i == len){
					dateMap.put(date, parseHours(0,etHour,date));
					half_Dates.add(date);
					dateTimeDuring.put(date, new Integer[]{0,etHour});
				}else {
					dateMap.put(date, parseHours(0, 24,date));
					all_Dates.add(date);
					dateTimeDuring.put(date, new Integer[]{0,24});
				}
				
				dates.add(sdt_date.format(cal.getTime()));
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		
	}

	public Integer[] getHoursDuring(String date){
		return dateTimeDuring.get(date);
	}
	
	private void putDateHours(String date, String dh) {
		List<String> list = date_hourMap.get(date);
		if(list == null){
			list = new ArrayList<String>();
			date_hourMap.put(date, list);
		}
		list.add(dh);
	}
	
	public List<String> getDateHours(String date){
		return date_hourMap.get(date);
	}
	
	private List<String> parseHours(int st,int end, String date) {
		List<String> hours = new ArrayList<String>();
			for(int i = st; i <= end;i++){
				hours.add(date+" "+(i<10?"0"+i:i));
				putDateHours(date,date+"-"+(i<10?"0"+i:i));
			}
		return hours;
	}
	private boolean check() {
		if(et.getTime() < st.getTime()){
			return false;
		}
		return true;
	}
	
	public String getSufix(String endTime){
		
		String today = sdt_date.format(new Date());
		String sufix = "";
		String t;
		try {
			t = sdt_date.format(sdt_date.parse(endTime));
			if(!today.equals(t)){
				sufix ="."+ t +".log";
			}
			return sufix;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * @return the dates
	 */
	public List<String> getDates() {
		return dates;
	}
	/**
	 * @param dates the dates to set
	 */
	public void setDates(List<String> dates) {
		this.dates = dates;
	}
	/**
	 * @return the dateMap
	 */
	public Map<String, List<String>> getDateMap() {
		return dateMap;
	}
	/**
	 * @param dateMap the dateMap to set
	 */
	public void setDateMap(Map<String, List<String>> dateMap) {
		this.dateMap = dateMap;
	}
	/**
	 * @return the half_Dates
	 */
	public List<String> getHalf_Dates() {
		return half_Dates;
	}
	/**
	 * @param half_Dates the half_Dates to set
	 */
	public void setHalf_Dates(List<String> half_Dates) {
		this.half_Dates = half_Dates;
	}
	/**
	 * @return the all_Dates
	 */
	public List<String> getAll_Dates() {
		return all_Dates;
	}
	/**
	 * @param all_Dates the all_Dates to set
	 */
	public void setAll_Dates(List<String> all_Dates) {
		this.all_Dates = all_Dates;
	}

}

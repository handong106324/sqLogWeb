package com.sq.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import test.QueryExecutor;
import test.data.QueryCondition;

import com.jfinal.core.Controller;
import com.sq.base.SqageActionInterface;
import com.sq.entity.MaTengTestData;
import com.sq.excel.ExcelExportPOI2;
@SqageActionInterface(path="test")
public class TestAction extends Controller {
	Logger logger = Logger.getLogger(TestAction.class);

//	public void createPeizhi(){
//		String[] games = "交易,摆摊,器灵,拍卖,翅膀,宝石,经验,宠物".split(","); 
//		String type = getPara("type");
//		List<LogFileInfo> infos = LogFileInfo.dao.findPart("gameName='wx' and type='物品'");
//		for(String gme : games){
//			for(LogFileInfo lfi : infos){
//				LogFileInfo fi = new LogFileInfo();
//				fi.set("fileName", lfi.getStr("fileName"));
//				fi.set("type", gme);
//				fi.set("gameName", "wx");
//				fi.set("queryWhere", lfi.getStr("queryWhere"));
//				fi.set("queryType", lfi.get("queryType"));
//				fi.set("ip", lfi.get("ip"));
//				fi.set("port", lfi.get("port"));
//				fi.set("serverName", lfi.get("serverName"));
//				fi.set("dir", lfi.get("dir"));
//				fi.save();
//			}
//		}
//		renderJson("data","success");
//	}
	
//	public void queryCountByDate(){
//		String date = getPara("queryDate");
//		QueryDataForLog query = new QueryDataForLog();
//		query.execute(date);
//		System.out.println(date+"的数据总量"+query.getCount());
//		Map<String,Long> couNmap = query.getCountMap();
//		Iterator<String> it = couNmap.keySet().iterator();
//		while(it.hasNext()){
//			String key = it.next();
//			System.out.println(key+"的总数据量="+couNmap.get(key));
//		}
//		couNmap.put("all", query.getCount());
//		renderJson("result",couNmap);
//	}
	private long oneday = 1000*60*60*24;
	public void testParseDate() throws ParseException {
		String startTime = getPara("queryDate");
		String endTime = getPara("endDate");
		String ip = "117.121.17.11";
		String port = "22201";
		String[] dates= parseDates(startTime,endTime);
//		String[] dates = new String[]{startTime,endTime};
		Long start = System.currentTimeMillis();
		List<String> allUsers = loadParas();
		List<String> temuP = new ArrayList<String>();
		for(String user : allUsers){
			temuP.add(user);
			if(temuP.size() >= 500){
				executeQuery(temuP, ip, port, dates);
				temuP.clear();
				System.out.println("清空"+temuP.size());
			}
		}
		if(temuP.size() >0){
			executeQuery(temuP, ip, port, dates);
		}
		
		System.out.println("攻击耗时："+(System.currentTimeMillis() - start));
	}
	
	public String[] parseDates(String string, String string2) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long start = sdf.parse(string).getTime();
		long end =   sdf.parse(string2).getTime();
		Long du = ((end - start)/oneday);
		String[] datas = new String[ du.intValue()+1];
		int index= 0 ;
		for(long i = start ; i <= end ;){
			datas[index] = sdf.format(new Date(i));
			i += oneday;
			index ++;
		}
		return datas;
	}
	
	private void executeQuery(List<String> us,String ip,String port,String[] dates){
		QueryExecutor exe = new QueryExecutor(us);
			for(String date :dates){
				List<QueryCondition> cs =exe. loadAllCondition(ip,port,date,"/data2/3dgame_log/zhuluchongyuan");
				if(!exe.printM(cs, date)){
					System.out.println("执行失败，跳出，执行下一个"+date);
					continue;
				}
			}
	}
	
	@SuppressWarnings("unchecked")
	public void showAllMatengData(){
		List<MaTengTestData> allDatas = MaTengTestData.dao.findAll();
		List<String> titles = new ArrayList<String>();
		titles.add("账号");
		Map<String,Map<String,Integer>> datas  = new HashMap<String,Map<String,Integer>>();
		for(MaTengTestData data :allDatas){
			if(datas.get(data.getStr("userName")) == null){
				datas.put(data.getStr("userName"), new HashMap<String,Integer>());
			}
			Map<String,Integer> map = datas.get(data.getStr("userName"));
			map.put(data.getStr("type"), data.getInt("val"));
			if(!titles.contains(data.getStr("type"))){
				titles.add(data.getStr("type"));
			}
		}
		for(String title : titles){
			System.out.print(title+"  ");
		}
		System.out.println();
		
		List<String[]> dataset = new ArrayList<String[]>();
		List<String> users = loadParas();

		for(String user : users){
			Map<String,Integer> actMap = datas.get(user);
			String[] datas_t = new String[titles.size()];
			
			datas_t[0] = user;
			
			for(int i = 1 ; i < titles.size() ; i ++){
				if(actMap == null){
					datas_t[i] = "0";
				}else {
					Integer time = actMap.get(titles.get(i));
					if(time == null) time = 0;
					datas_t[i] = time+"";
				}
			}
			dataset.add(datas_t);
		}
		
//		Iterator<String> userId = datas.keySet().iterator();
//		while(userId.hasNext()){
//			String user = userId.next();
//			Map<String,Integer> actMap = datas.get(user);
//			System.out.print(user+"  ");
//			String[] datas_t = new String[titles.size()];
//			datas_t[0] = user;
//			for(int i = 1 ; i < titles.size() ; i ++){
//				Integer time = actMap.get(titles.get(i));
//				if(time == null) time = 0;
//				datas_t[i] = time+"";
//				System.out.print(time+"  ");
//			}
//			dataset.add(datas_t);
//			System.out.println();
//		}
		
		
		
		
		ExcelExportPOI2 poi = new ExcelExportPOI2();
		String path = "e://mate.xls";
		OutputStream out;
		try {
			out = new FileOutputStream(path);
			poi.exportExcel("流失数据", new ArrayList(), titles, dataset, out, "yyyy-MM-dd");
			out.close();
			logger.info("应收Excel导出成功！");
			renderJson("result","success");
		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
		} 
	}
	public List<String> loadParas() {
		List<String> userNames = new ArrayList();
		String fi = "E://11-29.txt";
		try {
			FileReader reader = new FileReader(new File(fi));
			BufferedReader rd = new BufferedReader(reader);
			String temp = null;
			while((temp = rd.readLine()) != null){
				if(!userNames.contains(temp)){
					userNames.add(temp);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return userNames;
	}
}

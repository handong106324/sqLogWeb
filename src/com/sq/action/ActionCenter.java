package com.sq.action;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sq.GameBiz;
import com.sq.base.BaseAction;
import com.sq.base.SqageActionInterface;
import com.sq.cache.Cache;
import com.sq.cache.QueryMap;
import com.sq.common.Constant;
import com.sq.entity.PageCondition;
import com.sq.log.EnumNotFoundException;
import com.sq.log.factory.LogFactory;
import com.sq.log.result.LogQueryExecutor;
import com.sq.log.result.LogResultInfo;
import com.sq.model.DateParam;
import com.sq.model.PageParam;
import com.sq.utils.MyListUtils;

@SqageActionInterface(path="center")
public class ActionCenter extends BaseAction {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public void list() throws EnumNotFoundException{
		
		String game = getPara("game");
		setAttr("game", game);
		String serverName = getPara("serverName");
		String endTime = getPara("endTime");
		String type = getPara("type");
		//服务器获取，方式不同，展示方式
		setAttr("projects", new GameBiz().getServers(game));//);
		setAttr("endTime", endTime);
		setAttr("type", type);
		setAttr("showtype", Cache.getMessage(type)+Cache.getMessage("日志"));
		setAttr("weizhi", Cache.getMessage("您的位置")+Cache.getMessage("首页")+" »" +Cache.getMessage("日志")+Cache.getMessage("查询"));
		setAttr("servers", Cache.getMessage("服务器"));
		setAttr("start_time", Cache.getMessage("开始时间"));
		setAttr("end_time", Cache.getMessage("结束时间"));
		setAttr("query", Cache.getMessage("查询"));
		setAttr("action", "center");
		setAttr("showDetail", Cache.showDetailInPage());
		List<PageCondition> conds = Cache.getPageConditon(game,type);
		setAttr("conditions", conds);
		setAttr("querying", Cache.getMessage("开始查询"));
		PageParam param = new PageParam(type,game);
		setAttr("pros", param.getUsefualPages());
		setAttr("conds", MyListUtils.getIdsStr(conds, "name"));
		setAttr("conditonShowMap", Cache.getConditionShowMap(game,type));
		setAttr("colMessageMap", Cache.getColMap(game,type));
		if(StringUtils.isBlank(serverName) ){
			return;
		}
		
	}
	
	public void list_kor() throws EnumNotFoundException{
		String game = "whcl";
		setAttr("game", game);
		String endTime = getPara("endTime");
		String startTime = getPara("startTime");
		String wh_type = getPara("wh_type");
		//服务器获取，方式不同，展示方式
		setAttr("projects", Cache.loadForeignDataList("serverinfo"));//);
		setAttr("endTime", endTime);
		setAttr("startTime", startTime);
		setAttr("wh_type", wh_type);
		setAttr("showtype", Cache.getMessage(wh_type)+Cache.getMessage("日志"));
		setAttr("weizhi", Cache.getMessage("您的位置")+Cache.getMessage("首页")+" »" +Cache.getMessage("日志")+Cache.getMessage("查询"));
		setAttr("servers", Cache.getMessage("服务器"));
		setAttr("start_time", Cache.getMessage("开始时间"));
		setAttr("end_time", Cache.getMessage("结束时间"));
		setAttr("query", Cache.getMessage("查询"));
		setAttr("action", "center");
		setAttr("showDetail", Cache.showDetailInPage());
		List<PageCondition> conds = Cache.getPageConditon(game,wh_type);
		setAttr("conditions", conds);
		setAttr("querying", Cache.getMessage("开始查询"));
		PageParam param = new PageParam(wh_type,game);
		setAttr("pros", param.getUsefualPages());
		setAttr("conds", MyListUtils.getIdsStr(conds, "name"));
		setAttr("conditonShowMap", Cache.getConditionShowMap(game,wh_type));
		setAttr("colMessageMap", Cache.getColMap(game,wh_type));
//		if(StringUtils.isBlank(serverName)  ){
//			return;
//		}
		if(StringUtils.isBlank(endTime) && StringUtils.isBlank(startTime)  ){
			return;
		}
		if(StringUtils.isBlank(endTime) || StringUtils.isBlank(startTime)  ){
			setAttr("errMsg", "条件不足");
			return;
		}
		String [] vals ,paras;
		List<PageCondition> conditions = Cache.getPageConditon(game,wh_type);
		if(conditions != null && conditions.size() >0){
			vals = new String[conditions.size() ];
			paras = new String[conditions.size() ];
			int ind = 0;
			boolean allNull = true;
			for(PageCondition pc : conditions){
				String showName = pc.getStr("name");
				String showVal = getPara(showName);
				if(StringUtils.isNotBlank(showVal)){
					pc.put("lastVal", showVal);
				}else {
					pc.put("lastVal", "");
				}
				vals[ind] = showVal;
				paras[ind]= showName;
				ind ++;
				if(StringUtils.isNotBlank(showVal)){
					allNull = false;
				}
			}
			if(allNull){
				setAttr("errMsg","过滤信息不全");
				return;
			}
		}else {
			setAttr("errMsg","配置不完全，用户名、角色名请至少有一个");
			return;
		}
		
		LogQueryExecutor lqe = new LogQueryExecutor(startTime, endTime, wh_type,paras,vals,game);
		setAttr("datas", lqe.getDataList());
		logger.debug("[][][数据量"+lqe.getDataList().size()+"]");
	}
	
	public void listByLuc() {
		
		String game = getPara("game");
		setAttr("game", game);
		String serverName = getPara("serverName");
		String endTime = getPara("endTime");
		String type = getPara("type");
		String startTime = getPara("startTime");
		//服务器获取，方式不同，展示方式
		setAttr("projects", new GameBiz().getServers(game));//);
		setAttr("endTime", endTime);
		setAttr("type", type);
		setAttr("showtype", Cache.getMessage(type)+Cache.getMessage("日志"));
		setAttr("weizhi", Cache.getMessage("您的位置")+Cache.getMessage("首页")+" »" +Cache.getMessage("日志")+Cache.getMessage("查询"));
		setAttr("servers", Cache.getMessage("服务器"));
		setAttr("start_time", Cache.getMessage("开始时间"));
		setAttr("end_time", Cache.getMessage("结束时间"));
		setAttr("query", Cache.getMessage("查询"));
		setAttr("action", "center");
		setAttr("showDetail", Cache.showDetailInPage());
		List<PageCondition> conds = Cache.getPageConditon(game,type);
		setAttr("conditions", conds);
		setAttr("querying", Cache.getMessage("开始查询"));
		PageParam param = new PageParam(type,game);
		setAttr("pros", param.getUsefualPages());
		setAttr("conds", MyListUtils.getIdsStr(conds, "name"));
		setAttr("conditonShowMap", Cache.getConditionShowMap(game,type));
		setAttr("colMessageMap", Cache.getColMap(game,type));
		if(StringUtils.isBlank(serverName) ){
			return;
		}
		String [] vals ,paras;
		List<PageCondition> conditions = Cache.getPageConditon(game,type);
		if(conditions != null && conditions.size() >0){
			vals = new String[conditions.size()];
			paras = new String[conditions.size()];
			int ind = 0;
			boolean allNull = true;
			for(PageCondition pc : conditions){
				String showName = pc.getStr("name");
				String showVal = getPara(showName);
				setAttr(showName, showVal);
				vals[ind++] = showVal;
				paras[ind++]= showName;
				if(StringUtils.isNotBlank(showVal)){
					allNull = false;
				}
			}
			if(allNull){
				setAttr("errMsg","过滤信息不全");
				return;
			}
		}else {
			setAttr("errMsg","配置不完全，用户名、角色名请至少有一个");
			return;
		}
		DateParam dp = new DateParam(startTime, endTime);
		List datas = LogFactory.getInstance().queryByLuc(serverName.trim(), type, dp,paras, vals, getSession(),game);
		setAttr("list", datas);
	}
	
//	private Object getGameServers(GAME ga) {
//		if(ga.getStr("serverGet").equals("")){
//			return GameServerOperation.getServers();
//		}else  {
//			return GameServer.dao.findPart("name='"+ga.getFlag()+"' and isUse = 1");
//		}
//	}


	/**
	 * 1: 打开页面，falg ： 时间服务器为空
	 * 2：获取类别type
	 * 3：根据类别获取页面筛选条件
	 * @throws EnumNotFoundException 
	 */
	public  void listAjax() throws EnumNotFoundException{
		//基本的四个必备标识：类型，服务器，开始时间，结束时间
		String type = getPara("type");
		String serverName = getPara("serverName");
		String startTime = getPara("startTime");
		String endTime = getPara("endTime");
		String game = getPara("game");
		if(StringUtils.isBlank(serverName) ){
			renderJson("data","条件不足");
			return;
		}
		String [] vals ,paras;
		List<PageCondition> conditions = Cache.getPageConditon(game,type);
		if(conditions != null && conditions.size() >0){
			vals = new String[conditions.size()];
			paras = new String[conditions.size()];
			int ind = 0;
			boolean allNull = true;
			for(PageCondition pc : conditions){
				String showName = pc.getStr("name");
				String showVal = getPara(showName);
				setAttr(showName, showVal);
				vals[ind] = showVal;
				paras[ind]= showName;
				ind++;
				if(StringUtils.isNotBlank(showVal)){
					allNull = false;
				}
			}
			if(allNull){
				renderJson("data","过滤信息不全");
				return;
			}
		}else {
			renderJson("data","配置不完全，用户名、角色名请至少有一个");
			return;
		}
		
		DateParam dp = new DateParam(startTime, endTime);
//		if(!isLucFlag){
			LogFactory.getInstance().query(serverName.trim(), type, dp, vals, getSession(),game);
//		}else {
//			LogFactory.getInstance().queryByLuc(serverName.trim(), type, dp,paras, vals, getSession(),game);
//		}
		
	}
	
	public void getSessionVal() throws EnumNotFoundException{
		String type = getPara("type");
		String game = getPara("game");
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isBlank(game)){
			map.put("result", "success");
			map.put("msg", "游戏名称错误");
			map.put("count", "0");
			renderJson("data",map);
			return;
		}
		type = game+"-"+ type;
		LogResultInfo result  = getSessionAttr(type);
		if(result == null){
			map.put("result", "doing");
			renderJson("data",map);
			int count = QueryMap.putNullQuery(type+"query",getSession());
			if(count >= 20){
				map.put("result", "success");
				map.put("msg", "本次查询失效");
				map.put("count", "0");
				QueryMap.removeCountMap(type+"query",getSession());
				renderJson("data",map);
			}
			return;
		}
		if(result.getStatus() == Constant.SHELL_STATUS_ERROR){
			map.put("result", "success");
			map.put("msg", result.getMsg());
			map.put("count", "0");System.out.println(result);
			QueryMap.removeCountMap(type+"query",getSession());
			renderJson("data",map);
			return;
		}
		if(result.isFinish() && result.getDatas().size() ==0){
			map.put("result", "success");
			System.out.println(result);
			getSession().removeAttribute(type);
			map.put("resMsg", result.getSize()+Cache.getMessage("条结果")+","+Cache.getMessage("查询结束"));
		}else {
			map.put("result", "doing");
		}
		map.put("datas", result.getTableInfo());
		map.put("msg", result.getMsg());
		map.put("count", result.getSize()+"");
		renderJson("data",map);
	}
}

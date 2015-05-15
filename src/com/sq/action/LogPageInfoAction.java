package com.sq.action;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.Page;
import com.sq.base.BaseAction;
import com.sq.base.SqageActionInterface;
import com.sq.entity.LOGTYPE;
import com.sq.entity.LogPageInfo;

@SqageActionInterface(path="logpage")
public class LogPageInfoAction extends BaseAction {
	
	Logger logger = Logger.getLogger(getClass());
	int pageSize = 20;
	public void list() {
		setInfos();
		Integer pageNumber = 1;
		if(getPara("pageNumber")!=null){
			pageNumber = getParaToInt("pageNumber");
		}
		String key = getPara("keyword");
		
		String where = " where 1=1 ";
		setAttr("keyword", key);
		if(StringUtils.isNotBlank(key)){
			where += " and `key` ='"+key+"' ";
		}
		String select = "select * ";
		
		Page<LogPageInfo> page = LogPageInfo.dao.paginate(pageNumber, pageSize, select, "from log_page_info "+where);
		setAttr("page", page);
		renderJsp("/logpage/list.jsp");
	}
	public void showAddOrUpdate(){
		setInfos();
		String id = this.getPara("id");
		if (id==null||id.equals("")) {
			renderJsp("/logpage/addOrUpdate.jsp");
			return;
		}
		LogPageInfo logpage = LogPageInfo.dao.findById(id);
		setAttr("bean", logpage);
		renderJsp("/logpage/addOrUpdate.jsp");
	}
	public void addOrUpdate(){
		Integer id = getParaToInt("id");
		String game = getPara("game");
		LogPageInfo logpage = null;
		if(id == null){
			logpage = new LogPageInfo();
		//	colName=账号  colVals=userane  index=0  type=key  id=72  key=人物属性
			String colName = getPara("colName");
			String key  = getPara("key");
			if(LogPageInfo.dao.findFirstByCondition(" colName='"+colName+"' and `key`='"+key+"' and game='"+game+"'") != null){
				showAddOrUpdate();
				setAttr("msg", key+" 已存在设置 "+ colName);
				return;
			}
			this.saveOrUpdateToDb(logpage);
		}else{
			logpage = LogPageInfo.dao.findById(id);
			if (logpage!=null) {
				saveOrUpdateToDb(logpage);
			}
		}
		list();
	}
	public void delete (){
		int id = this.getParaToInt("id");
		boolean bo = LogPageInfo.dao.deleteById(id);
		if (bo) {
			logger.info("删除Project成功");
			renderJson("result", "success");
		}else {
			logger.info("删除Project失败");
			renderJson("result", "删除失败");
		}
		list();
	}
	
	public void active(){
		int id = getParaToInt("id");
		LogPageInfo key = LogPageInfo.dao.findById(id);
		if(key.getBoolean("isUse")){
			key.set("isUse", 0);
		}else{
			key.set("isUse", 1);
		}
		key.update();
		list();
	}
	
	public void setInfos(){
		setAttr("types", LOGTYPE.dao.findAll());
	}
}

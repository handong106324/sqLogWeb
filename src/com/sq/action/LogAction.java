package com.sq.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.Page;
import com.sq.base.BaseAction;
import com.sq.base.SqageActionInterface;
import com.sq.cache.Cache;
import com.sq.entity.LOGTYPE;
import com.sq.entity.LogFileInfo;
import com.sq.interfaces.GameServerOperation;
import com.sq.utils.PropertiesUtils;

@SqageActionInterface(path="file")
public class LogAction extends BaseAction {
	
	Logger logger = Logger.getLogger(getClass());
	int pageSize = 20;
	public void list() {
		setAttr("log_query_type", PropertiesUtils.getPropertiesValue("jdbc.properties", "log_query_file_type"));

		setAttr("servers", GameServerOperation.getServers());
		String serverName = getPara("serverName");
		setAttr("serverId", serverName);
		Integer pageNumber = 1;
		if(getPara("pageNumber")!=null){
			pageNumber = getParaToInt("pageNumber");
		}
		String key = getPara("type");
		
		String where = " where 1=1 ";
		setAttr("type", key);
		if(StringUtils.isNotBlank(key)){
			where += " and type ='"+key+"' ";
		}
		if(StringUtils.isNotBlank(serverName)){
			where += " and serverName ='"+serverName+"'";
		}
		String select = "select * ";
		
		Page<LogFileInfo> page = LogFileInfo.dao.paginate(pageNumber, pageSize, select, "from "+LogFileInfo.dao.getTableName()+" "+where);
		setAttr("page", page);
		renderJsp("/file/list.jsp");
	}
	public void showAddOrUpdate(){
		setAttr("log_query_type", PropertiesUtils.getPropertiesValue("jdbc.properties", "log_query_file_type"));
		setAttr("servers", GameServerOperation.getServers());
		List<LOGTYPE> typess = LOGTYPE.dao.findAll();
		List<String> types = new ArrayList<String>();
		for(LOGTYPE type:typess){
			types.add(type.getStr("name"));
		}
		setAttr("types", types);
		String id = this.getPara("id");
		LogFileInfo keyword = LogFileInfo.dao.findById(id);
		setAttr("bean", keyword);
		renderJsp("/file/addOrUpdate.jsp");
	}
	public void addOrUpdate(){
		Integer id = this.getParaToInt("id");
		String servername = getPara("serverName");
		String type = getPara("type");
		LogFileInfo keyword = null;
		if(id == null){
			LogFileInfo b = LogFileInfo.dao.findFirstByCondition("serverName='"+servername+"' and type ='"+type+"'");
			if(b != null){
				setAttr("msg", servername+","+type+" 组合已存在");
				keepPara();
				showAddOrUpdate();
				return;
			}
			keyword = new LogFileInfo();
			this.saveOrUpdateToDb(keyword);
			Cache.getAllLogFileInfo().add(keyword);
		}else{
			keyword = LogFileInfo.dao.findById(id);
			if (keyword!=null) {
				saveOrUpdateToDb(keyword);
				List<LogFileInfo> list = Cache.getAllLogFileInfo();
				for(int i = 0 ; i < list.size() ; i ++){
					LogFileInfo log = list.get(i);
					if((log.getInt("id")+"") .equals(keyword.getInt("id")+"")){
						
						Cache.getAllLogFileInfo().set(i, keyword);
						System.out.println("更换["+log+"-->"+keyword);
					}
				}
			}
		}
		list();
	}
	public void delete (){
		int id = this.getParaToInt("id");
		boolean bo = LogFileInfo.dao.deleteById(id);
		if (bo) {
			logger.info("删除Project成功");
			renderJson("result", "success");
		}else {
			logger.info("删除Project失败");
			renderJson("result", "删除失败");
		}
		list();
	}
	
}

package com.sq.action;

import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Page;
import com.sq.base.BaseAction;
import com.sq.base.SqageActionInterface;
import com.sq.cache.Cache;
import com.sq.entity.LogFileInfo;
import com.sq.entity.Project;

@SqageActionInterface(path = "/project")
public class ProjectAction extends BaseAction {
	private Integer pageSize = 20;

	public void index() {

	}
	/**
	 * 项目列表
	 */
	public void list() {
		Integer pageNumber = 1;
		if(getPara("pageNumber")!=null){
			pageNumber = getParaToInt("pageNumber");
		}
		String select = "select * ";
		StringBuilder sqlExceptSelect = new StringBuilder("from project ");
		Page<Project> page = Project.dao.paginate(pageNumber, pageSize, select, sqlExceptSelect.toString());
		setAttr("page", page);
		renderJsp("/project/list.jsp");
	}
	public void showAddOrUpdate(){
		String id = this.getPara(0);
		if (id==null||id.equals("")) {
			renderJsp("/project/addOrUpdate.jsp");
			return;
		}
		Project project = Project.dao.findById(id);
		setAttr("bean", project);
		renderJsp("/project/addOrUpdate.jsp");
	}
	public void addOrUpdate(){
		Integer id = this.getParaToInt("id");
		Project project = null;
		if(StringUtils.isBlank(getPara("name"))){
			renderJson("result","fail");
			return;
		}
		if(id == null){
			project = new Project();
			if(this.saveOrUpdateToDb(project)){
				renderJson("result","success");
			}else {
				renderJson("result","项目已存在");
			}
		}else{
			project = Project.dao.findById(id);
			if (project!=null) {
				boolean bo = saveOrUpdateToDb(project);
					if (bo) {
						renderJson("result","success");
					}else {
						renderJson("result","失败");
					}
			}
		}
		renderJson("result","success");
	}
	public void delete (){
		int id = this.getParaToInt("id");
		boolean bo = Project.dao.deleteById(id);
		if (bo) {
			logger.info("删除Project成功");
			renderJson("result", "success");
		}else {
			logger.info("删除Project失败");
			renderJson("result", "删除失败");
		}
	}
	
	public void updateCache(){
		Cache.update();
		renderJson("result","success");
	}
	
	public void addPeizhi(){
		String gameName = getPara("gameName");
		String shellIp = "117.121.22.23";
		String shellPort = getPara("port");
		String queryWhere = getPara("queryWhere");
		String serverNames = getPara("serverNames");
		String serverUrls  = getPara("urls");
		String fileName = getPara("fileName");
		String type = getPara("type");
		String queryType = getPara("queryType");
		if(StringUtils.isBlank(serverUrls) || StringUtils.isBlank(serverNames)){
			setAttr("error", "请填写完整");
			return;
		}
		String[] urls = StringUtils.split(serverUrls,",");
		String[] servers = StringUtils.split(serverNames,",");
		if(urls.length != servers.length){
			setAttr("error", "数量不一致");
			return;
		}
		int i = 0 ;
		for(String url:urls){
			LogFileInfo info = new LogFileInfo();
			info.set("dir", url);
			info.set("fileName", fileName);
			info.set("type", type);
			info.set("serverName", servers[i++]);
			info.set("ip", shellIp);
			info.set("port", shellPort);
			info.set("gameName", gameName);
			info.set("queryWhere", queryWhere);
			info.set("queryType", queryType);
			info.save();
		}
	}
	

}

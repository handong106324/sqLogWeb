package com.sq.action;

import java.util.Date;

import com.jfinal.plugin.activerecord.Page;
import com.sq.base.BaseAction;
import com.sq.base.SqageActionInterface;
import com.sq.entity.Resource;

@SqageActionInterface(path = "/resource")
public class ResourceAction extends BaseAction{

	private Integer pageSize = 20;
	public void index() {
		
	}
	public void list(){
		Integer pageNumber = 1;
		if(getPara("pageNumber")!=null){
			pageNumber = getParaToInt("pageNumber");
		}
		String select = "select a.*,b.resourceName as parentName ";
		StringBuilder sqlExceptSelect = new StringBuilder("from t_resource a left join t_resource b on a.parentId=b.id ");
		Page<Resource> page = Resource.dao.paginate(pageNumber, pageSize, select, sqlExceptSelect.toString());
		setAttr("page", page);
		renderJsp("/resource/list.jsp");
	}
	public void showAddOrUpdate(){
		String id = this.getPara(0);
		Resource resource = Resource.dao.findFirst("select * from t_resource where id="+id);
		setAttr("bean", resource);
		setAttr("list", Resource.dao.find("select * from t_resource order by parentId "));
		renderJsp("/resource/addOrUpdate.jsp");
	}
	public void addOrUpdate(){
		Integer id  = null;
		if(getPara("id") != null && getPara("id").toString().equals("")){
			id = null;
		} else {
			id = this.getParaToInt("id");
		}
		Resource resource = null;
		Date time = new Date();
		if(id == null){
			resource = new Resource();
			resource.set("createTime", time);
			resource.set("updateTime", time);
			resource.set("resourceName", this.getPara("resourceName"));
			resource.set("resourceUrl", this.getPara("resourceUrl"));
			resource.set("parentId", this.getParaToInt("parentId"));
		}else{
			resource = Resource.dao.findById(id);
			resource.set("updateTime", time);
			resource.set("resourceName", this.getPara("resourceName"));
			resource.set("resourceUrl", this.getPara("resourceUrl"));
			resource.set("parentId", this.getParaToInt("parentId"));
		}
		if(this.saveOrUpdateToDb(resource)){
			renderJson("result","success");
		}else {
			renderJson("result","["+getPara("resourceName")+"]已存在");
		}
	}
	
	public void delete (){
		Resource.dao.deleteById(this.getParaToInt("id"));
		renderJson("result", "success");
	}

	
	
	
}

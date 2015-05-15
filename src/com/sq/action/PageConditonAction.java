package com.sq.action;

import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Page;
import com.sq.base.BaseAction;
import com.sq.base.SqageActionInterface;
import com.sq.cache.Cache;
import com.sq.entity.PageCondition;
@SqageActionInterface(path = "pagecondition")
public class PageConditonAction extends BaseAction{

	private Integer pageSize = 20;

	@SuppressWarnings("unchecked")
	public void list(){
		Integer pageNumber = 1;
		if(getPara("pageNumber")!=null){
			pageNumber = getParaToInt("pageNumber");
		}
		String game = getPara("game");
		String type = getPara("type");
		String sql = " where 1=1 ";
		if(StringUtils.isNotBlank(game)){
			sql += " and game='"+game+"'";// and type='"+type+"'"
		}
		if(StringUtils.isNotBlank(type)){
			sql += " and type ='"+type+"'";
		}
		
		setAttr("game", game);
		setAttr("type", type);
		if(StringUtils.isBlank(game) && StringUtils.isBlank(type)){
			return;
		}
		String select = "select * ";
		StringBuilder sqlExceptSelect = new StringBuilder("from  "+PageCondition.dao.getTableName()+sql );
		Page<PageCondition> page = PageCondition.dao.paginate(pageNumber, pageSize, select, sqlExceptSelect.toString());
		setAttr("page", page);
		renderJsp("list.jsp");
	}
	public void showAddOrUpdate(){
		String id = this.getPara("id");
		PageCondition role = (PageCondition) PageCondition.dao.findFirst("select * from "+PageCondition.dao.getTableName()+" where id="+id);
		setAttr("bean", role);
		renderJsp("addOrUpdate.jsp");
	}
	public void addOrUpdate(){
		Integer id = this.getParaToInt("id");
		PageCondition role = null;
		String game = getPara("game");
		String type = getPara("type");
		String showName = getPara("showName");
		if(PageCondition.dao.findFirstByCondition("game='"+game+"' and  type='"+type+"' and showName ='"+showName+"'") != null){
			setAttr("error", "已存在");
			return;
		}
		if(id == null){
			role = new PageCondition();
		}else{
			role = (PageCondition) PageCondition.dao.findById(id);
		}
		if(this.saveOrUpdateToDb(role)){
			Cache.addCondition(role);
		}
		list();
	}
	
	public void delete (){
		PageCondition pc = (PageCondition) PageCondition.dao.findById(this.getParaToInt("id"));
		if(pc != null){
			String type = pc.getStr("type");
			String game = pc.getStr("game");
			if(pc.delete()){
				Cache.deleteCondition(this.getParaToInt("id"),game,type);
				renderJson("result", "success");
				return;
			}else {
				renderJson("result", "failed");
			}
		}
	}

}

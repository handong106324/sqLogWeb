package com.sq.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.Page;
import com.sq.base.BaseAction;
import com.sq.base.SqageActionInterface;
import com.sq.entity.KeyWord;

@SqageActionInterface(path="keyword")
public class KeyWordAction extends BaseAction {
	private static List<KeyWord> keys ;
	Logger logger = Logger.getLogger(getClass());
	int pageSize = 20;
	public void list() {
		Integer pageNumber = 1;
		if(getPara("pageNumber")!=null){
			pageNumber = getParaToInt("pageNumber");
		}
		String key = getPara("keyword");
		
		String where = " where 1=1 ";
		setAttr("keyword", key);
		if(StringUtils.isNotBlank(key)){
			where += " and keyword ='"+key+"' ";
		}
		String select = "select * ";
		
		Page<KeyWord> page = KeyWord.dao.paginate(pageNumber, pageSize, select, "from keyword "+where);
		setAttr("page", page);
		renderJsp("/keyword/list.jsp");
	}
	public void showAddOrUpdate(){
		String id = this.getPara(0);
		if (id==null||id.equals("")) {
			renderJsp("/keyword/addOrUpdate.jsp");
			return;
		}
		KeyWord keyword = KeyWord.dao.findById(id);
		setAttr("bean", keyword);
		renderJsp("/keyword/addOrUpdate.jsp");
	}
	public void addOrUpdate(){
		Integer id = this.getParaToInt("id");
		KeyWord keyword = null;
		if(StringUtils.isBlank(getPara("keyword"))){
			renderJson("result","fail");
			return;
		}
		if(id == null){
			keyword = new KeyWord();
			if(this.saveOrUpdateToDb(keyword)){
				if(!keys.contains(keyword)){
					keys.add(keyword);
					logger.debug("[KeyWordAction][往缓存增加新的关键字][key="+keyword+"]");
				}
				renderJson("result","success");
			}else {
				renderJson("result","已存在");
			}
		}else{
			keyword = KeyWord.dao.findById(id);
			if (keyword!=null) {
				boolean bo = saveOrUpdateToDb(keyword);
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
		boolean bo = KeyWord.dao.deleteById(id);
		if (bo) {
			keys = KeyWord.dao.findPart(" isUse = 1");
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
		KeyWord key = KeyWord.dao.findById(id);
		if(key.getBoolean("isUse")){
			key.set("isUse", 0);
		}else{
			key.set("isUse", 1);
		}
		key.update();
		list();
	}
	
	public static List<KeyWord> getKeyCache(){
		if(keys == null){
			System.out.println("重新获取keys");
			try {
				keys = KeyWord.dao.findPart(" isUse = 1");
			} catch (Exception e) {
				keys =new ArrayList();
				//连接异常等待，暂停连接数据库
			}
		}
		return keys;
	}
}

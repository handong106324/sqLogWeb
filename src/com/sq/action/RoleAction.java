package com.sq.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.sq.base.BaseAction;
import com.sq.base.SqageActionInterface;
import com.sq.entity.Resource;
import com.sq.entity.Role;
@SqageActionInterface(path = "/role")
public class RoleAction extends BaseAction{

	private Integer pageSize = 20;
	public void index() {
		
	}
	public void list(){
		Integer pageNumber = 1;
		if(getPara("pageNumber")!=null){
			pageNumber = getParaToInt("pageNumber");
		}
		String select = "select * ";
		StringBuilder sqlExceptSelect = new StringBuilder("from t_role ");
		Page<Role> page = Role.dao.paginate(pageNumber, pageSize, select, sqlExceptSelect.toString());
		setAttr("page", page);
		renderJsp("/role/list.jsp");
	}
	public void showAddOrUpdate(){
		String id = this.getPara(0);
		Role role = Role.dao.findFirst("select * from t_role where id="+id);
		setAttr("bean", role);
		renderJsp("/role/addOrUpdate.jsp");
	}
	public void addOrUpdate(){
		Integer id = this.getParaToInt("id");
		Role role = null;
		Date time = new Date();
		if(id == null){
			role = new Role();
			role.set("createTime", time);
			role.set("updateTime", time);
			role.set("roleName", this.getPara("roleName"));
		}else{
			role = Role.dao.findById(id);
			role.set("updateTime", time);
			role.set("roleName", this.getPara("roleName"));
		}
		if(this.saveOrUpdateToDb(role)){
			renderJson("result","success");
		}else {
			renderJson("result","已存在");
		}
	}
	
	public void delete (){
		Role.dao.deleteById(this.getParaToInt("id"));
		renderJson("result", "success");
	}

	public void showRoleResource(){
		String roleName = getPara("roleName");
		String roleId = getPara("roleId");
		setAttr("roleName", roleName);
		setAttr("roleId", roleId);
		//获取所有菜单
		List<Resource> allList = Resource.dao.find("select * from t_resource");
		//User user = (User)getSessionAttr("userInfo");
		List<Role> rols = Role.dao.find("select * from t_role");
		setSessionAttr("roles", rols);
		//获取已选中的
		List<Resource> listResource = Resource.dao.find("select r.* from t_resource r inner join t_role_resource rr " +
				"on r.id=rr.resourceId where rr.roleId = "+roleId);
		List<Resource> list = new ArrayList<Resource>(); 
		List<Resource> list_temp = new ArrayList<Resource>();//存放一级菜单
		
		//标记是否已选择
		for(Resource map : allList){
			for(Resource map2 : listResource){
				if(((Integer)map2.get("id")).intValue() == ((Integer)map.get("id")).intValue()){
					map.put("state", 1);
				}
			}
		}
		
		for(Resource map : allList){
			if(map.get("parentId")!=null && ((Integer)map.get("parentId")).intValue() == 0){
				list_temp.add(map);
			}
		}
		for(Resource map : list_temp){
			List<Resource> tempList = new ArrayList<Resource>();
			for(Resource map2 : allList){
				if(((Integer)map2.get("parentId")).intValue() == ((Integer)map.get("id")).intValue()){
					tempList.add(map2);
					List<Resource> list3 = new ArrayList<Resource>();
					for(Resource map3 : allList){
						if(((Integer)map3.get("parentId")).intValue() == ((Integer)map2.get("id")).intValue()){
							list3.add(map3);
						}
						map2.put("list", list3);
					}
				}
			}
			map.put("list", tempList);
			list.add(map);
		}
		
		
		
		setAttr("list", list);
		renderJsp("/resource/setRoleResource.jsp");
	}
	public void setResource(){
		String resourceId[] = this.getParaValues("resourceId");
		setAttr("roleName", this.getPara("roleName"));
		Integer roleId = getParaToInt("roleId");
		if(roleId != null && roleId > 0 && resourceId != null && resourceId.length > 0){
			Db.update("delete from t_role_resource where roleId="+roleId);
			List<String> sql = new ArrayList<String>();
			for(int i = 0; i<resourceId.length; i++){
				sql.add("insert into t_role_resource (roleId,resourceId) values("+roleId+","+resourceId[i]+")");
			}
			Db.batch(sql, 100);
		}
		
		this.list();
	}
	
	
}

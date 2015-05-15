package com.sq.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sq.base.BaseAction;
import com.sq.base.SqageActionInterface;
import com.sq.entity.Group;
import com.sq.entity.GroupUser;
import com.sq.entity.User;
import com.sq.utils.MyListUtils;

@SqageActionInterface(path = "/group")
public class GroupAction extends BaseAction{

	private Integer pageSize = 20;
	public void index() {
		
	}
	public void list(){
		Integer pageNumber = 1;
		if(getPara("pageNumber")!=null){
			pageNumber = getParaToInt("pageNumber");
		}
		String select = "select * ";
		StringBuilder sqlExceptSelect = new StringBuilder("from t_group ");
		Page<Group> page = Group.dao.paginate(pageNumber, pageSize, select, sqlExceptSelect.toString());
		List<Group> groupList = page.getList();
		for(Group g : groupList){
			g.put("managerList", g.getManagerList());
		}
		setAttr("page", page);
		renderJsp("/group/list.jsp");
	}
	public void showAddOrUpdate(){
		String id = this.getPara(0);
		Group group = Group.dao.findFirst("select * from t_group where id="+id);
		List<User> userList = User.dao.find("select * from t_user order by loginName");
		if(StringUtils.isNotBlank(id)){
			List<GroupUser> groupUserList = GroupUser.dao.find("select * from t_group_user where groupId = " + id);
			for(User u : userList){
				for(GroupUser gu : groupUserList){
					if(gu.getInt("userId").intValue() == u.getInt("id").intValue()){
						if(gu.getInt("isManager") == 1){
							u.put("flag", 2);
						}else{
							u.put("flag", 1);
						}
					}
				}
			}
		}
		setAttr("userList", userList);
		setAttr("bean", group);
		renderJsp("/group/addOrUpdate.jsp");
	}
	public void addOrUpdate(){
		Map<String, String> result = new HashMap<String, String>();
		final Integer id = this.getParaToInt("id");
		String userIdStr = getPara("userId");
		final String isManager = getPara("isManager");
		if(userIdStr.indexOf(isManager)==-1){
			userIdStr +=","+isManager;
		}
		final String userIds[] = userIdStr.split(",");
		final String groupName = getPara("groupName");
		if(id == null){
			Group temp = Group.dao.findFirst("select id from t_group where groupName = ?", groupName);
			if(temp != null){
				result.put("msg", "组名已存在");
				result.put("result", "fail");
				renderJson(result);
				return;
			}
			boolean succeed = Db.tx(new IAtom() {
				public boolean run() throws SQLException {
					Record bean = new Record().set("groupName", groupName);
					Db.save("t_group", bean);
					List<String> sqlList = new ArrayList<String>();
					for(String s : userIds){
						String isM = "0";
						if(isManager.equals(s)){
							isM = "1";
						}
						sqlList.add("insert into t_group_user (groupId, userId,isManager) values("+bean.get("id")+","+s+","+isM+")");
					}
					Db.batch(sqlList, 10);
					return true;
				}
			});
			if(succeed == false){
				renderJson("result","fail");
			}
			
		}else{
			Group temp = Group.dao.findFirst("select id from t_group where groupName = ?", groupName);
			if(temp != null && id.intValue() != temp.getInt("id").intValue()){
				result.put("msg", "组名已存在");
				result.put("result", "fail");
				renderJson(result);
				return;
			}
			boolean succeed = Db.tx(new IAtom() {
				public boolean run() throws SQLException {
					Record bean = Db.findById("t_group", id)
					.set("groupName", getPara("groupName"));
					Db.update("t_group", bean);
					List<String> sqlList = new ArrayList<String>();
					sqlList.add("delete from t_group_user where groupId = "+bean.get("id"));
					for(String s : userIds){
						String isM = "0";
						if(isManager.equals(s)){
							isM = "1";
						}
						sqlList.add("insert into t_group_user (groupId, userId,isManager) values("+bean.get("id")+","+s+","+isM+")");
					}
					Db.batch(sqlList, 10);
					return true;
				}
			});
			if(succeed == false){
				renderJson("result","fail");
			}
		}
		renderJson("result","success");
	}
	
	public void delete (){
		Group.dao.deleteById(this.getParaToInt("id"));
		renderJson("result", "success");
	}

	public void setCheck(){
		String id = getPara("id");
		if(id != null ){
			Group group = Group.dao.findById(id);
			group.set("isTemplateCheck", 1);
			List<Group> groups = Group.dao.find("select * from t_group where isTemplateCheck = 1");
			for(Group g:groups){
				g.set("isTemplateCheck", 0);
				g.update();
			}
			if(group.update()){
				renderJson("result","success");
			}else {
				renderJson("result","failed");
			}
		}
	}
	
	public void getUserListByGroupIdJson(){
		Map<String,Object> map = new HashMap<String, Object>();
		String groupId = getPara("groupId");
		List<GroupUser> gus = GroupUser.dao.findPart(" groupId="+groupId);
		List<User> us = new ArrayList<User>();
		if(gus != null && gus.size() > 0){
			us = User.dao.findPart(" id in("+MyListUtils.getIdsStr(gus,"userId")+")");
		}
		Map<Integer,Object> duijiMap = new HashMap<Integer, Object>();
		Map<Integer,User> userMap = (Map<Integer, User>) MyListUtils.toMap(us);
		map.put("userMap", userMap);
		map.put("duijiMap", duijiMap);
		map.put("result",us);
		renderJson(map);
	}
	
	public void checkIsManager(){
		String groupId = getPara("groupId");
		if(groupId == null || groupId.length() == 0 || groupId.equals("undefined")){
			renderJson("result","failed");
			return;
		}
		GroupUser gu = GroupUser.dao.findFirstByCondition("isManager=1 and groupId="+groupId+" and userId="+((User)getSessionAttr("userInfo")).getInt("id"));
		if(gu == null)renderJson("result","failed");
		else renderJson("result","success");
	}
	
}

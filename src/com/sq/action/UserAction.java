package com.sq.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jiangge.utils.StringUtils;
import com.jiangge.utils.algorithm.MD5;
import com.sq.base.BaseAction;
import com.sq.base.SqageActionInterface;
import com.sq.entity.Group;
import com.sq.entity.GroupUser;
import com.sq.entity.Role;
import com.sq.entity.User;

@SqageActionInterface(path="/user")
public class UserAction extends BaseAction{

	private Integer pageSize = 20;
	public void index() {
		
	}
	public void list(){
		Role role = Role.dao.findById(getUser().getInt("roleId"));
		List<User> manUser = null;
		boolean superFlag = false;
		if(role.get("isSuper") != null && role.getBoolean("isSuper")) {
			setAttr("super", "1");
			superFlag = true;
		}else {
			String managerGroupIds = getUser().getManagerGroupIds();
			if(managerGroupIds != null && managerGroupIds.length() > 0){
				manUser = User.dao.findPart("id in(select userId from "+GroupUser.dao.getTableName()+" where groupId in("+managerGroupIds+") ) ");
			}
		}
		Map<String, String> param = new HashMap<String, String>();
		Integer pageNumber = 1;
		if(getPara("pageNumber")!=null){
			pageNumber = getParaToInt("pageNumber");
		}
		String realName = getPara("realName");
		Integer groupId  = getParaToInt("groupId");
		param.put("realName", realName);
		param.put("groupId", String.valueOf(groupId));
		String select = "select u.*,r.roleName ";
		StringBuilder sqlExceptSelect = new StringBuilder("from t_user u left join t_role r on r.id=u.roleId where 1=1 ");
		if(StringUtils.isNotEmpty(realName)){
			sqlExceptSelect.append("and u.realName like '%"+realName+"%'");
		}
		if(groupId!=null && groupId.intValue()>0){
			sqlExceptSelect.append("and u.id in( select userId from t_group_user where groupId= "+groupId.intValue()+")");
		}
		Page<User> page = User.dao.paginate(pageNumber, pageSize, select, sqlExceptSelect.toString());
		List<User> userList = page.getList();
		for(User u : userList){
			u.put("groupUserList", u.getGroups());
			if(superFlag){
				u.put("canChangeOption", true);
			}else {
				if(manUser != null){
					if(manUser.contains(u)){
						u.put("canChangeOption", true);
					}
				}
			}
		}
		setAttr("page", page);
		setAttr("groupList",Group.dao.find("select * from t_group"));
		renderJsp("/user/list.jsp");
	}
	public void showAddOrUpdate(){
		String id = this.getPara(0);
		List<Group> groupList = Group.dao.find("select * from t_group");
		if(id != null){
			User user = User.dao.findFirst("select u.*,r.roleName from t_user u left join t_role r on r.id= u.roleId where u.id="+id);
			setAttr("bean", user);
			List<GroupUser> groupUserList = GroupUser.dao.find("select * from t_group_user where userId = ?", Integer.valueOf(id));
			for(Group g : groupList){
				for(GroupUser gu : groupUserList){
					if(gu.getInt("groupId").intValue() == g.getInt("id").intValue()){
						if(gu.getInt("isManager").intValue() == 1){
							g.put("flag", 2);
						}else{
							g.put("flag", 1);
						}
					}
				}
			}
		}
		
		setAttr("groupList",groupList);
		setAttr("roleList", Role.dao.find("select * from t_role"));
		renderJsp("/user/addOrUpdate.jsp");
	}
	public void addOrUpdate(){
		final Integer id = this.getParaToInt("id");
		String loginName = this.getPara("loginName");
		Map<String, String> result = new HashMap<String, String>();
		final Date time = new Date();
		String groupIds = getPara("groupId");
		final String[] groupId = groupIds.split(",");
		String isManagers = getPara("isManager");
		String[] isManager = isManagers.split(",");
		final int isValid = getParaToInt("isValid");
		final String  phone = getPara("phone");
		final Map<String, String> isManagerMap = new HashMap<String, String>();
		for(String s : isManager){
			isManagerMap.put(s, s);
		}
		if(id == null){
			User temp = User.dao.findFirst("select id from t_user where loginName = '"+loginName+"'");
			if(temp != null){
				result.put("msg", "登录名已存在");
				result.put("result", "fail");
				renderJson(result);
				return;
			}
			boolean succeed = Db.tx(new IAtom() {
				public boolean run() throws SQLException {
					Record bean = new Record().set("createTime", time)
					.set("updateTime", time).set("phone", phone).set("isValid", isValid)
					.set("password", MD5.GetMD5Code(getPara("password"),true))
					.set("realName", getPara("realName"))
					.set("loginName", getPara("loginName"))
					.set("roleId", getParaToInt("roleId"))
					.set("email", getPara("email"))
					.set("state", 0);
					Db.save("t_user", bean);
					List<String> sqlList = new ArrayList<String>();
					for(String s : groupId){
						String isM = "0";
						if(isManagerMap.get(s)!=null){
							isM = "1";
						}
						sqlList.add("insert into t_group_user (groupId, userId,isManager) values("+s+","+bean.get("id")+","+isM+")");
					}
					Db.batch(sqlList, 10);
					return true;
				}
			});
			if(succeed == false){
				renderJson("result","fail");
			}
			
		}else{
			boolean succeed = Db.tx(new IAtom() {
				public boolean run() throws SQLException {
					try{
						Record bean = Db.findById("t_user", id)
						.set("updateTime", time).set("phone", phone).set("isValid", isValid)
						.set("realName", getPara("realName"))
						.set("roleId", getParaToInt("roleId"))
						.set("email", getPara("email"))
						.set("state", 0);
						Db.update("t_user", bean);
						List<String> sqlList = new ArrayList<String>();
						sqlList.add("delete from t_group_user where userId = "+bean.get("id"));
						for(String s : groupId){
							String isM = "0";
							if(isManagerMap.get(s)!=null){
								isM = "1";
							}
							sqlList.add("insert into t_group_user (groupId, userId,isManager) values("+s+","+bean.get("id")+","+isM+")");
						}
						Db.batch(sqlList, 10);
					}catch(Exception e){
						return false;
					}
					return true;
				}
			});
			if(succeed == false){
				renderJson("result","fail");
			}
		}
		renderJson("result","success");
	}
	
	public void toChangePass(){
		render("changePass.jsp");
	}
	
	public void delete (){
		User user = User.dao.findById(getParaToInt("id"));
		boolean flag = false;
		if(user != null){
			flag = user.delete();
		}
			Map<String, Object> result = new HashMap<String, Object>();
			if(flag){
				result.put("result", "success");
			}else {
				result.put("result", "failed");
			}
			renderJson(result);
	}
	/**
	 * 1. 工作流
	 * @param user
	 * @return
	 */

	public void changePass(){
		User user = (User)this.getSessionAttr("userInfo");
		String password = MD5.GetMD5Code(this.getPara("password"),true);
		String rePass = this.getPara("rePass");
		String confirmPass = this.getPara("confirmPass");
		if(user != null){
			if(!password.equals(user.getStr("password"))){
				renderJson("result","旧密码错误");
				return;
			}
			if(!rePass.equals(confirmPass)){
				renderJson("result","新密码和重复密码不一致");
				return;
			}
			
			user.set("password", MD5.GetMD5Code(this.getPara("rePass"),true));
			user.set("updateTime", new Date());
			user.update();
			renderJson("result", "success");
			return;
		}
		renderJson("result","操作失败");
		
	}
	
	public void resetPass(){
		User user = User.dao.findById(this.getParaToInt("id"));
		if(user != null){
			user.set("password", MD5.GetMD5Code("123456",true));
			user.set("updateTime", new Date());
			user.update();
			renderJson("result", "success");
			return;
		}
		renderJson("result","fail");
		
	}
	
}

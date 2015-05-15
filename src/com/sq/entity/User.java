package com.sq.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Model;
import com.sq.base.SqageBaseEntity;
import com.sq.utils.MyListUtils;
@SqageBaseEntity(tableName="t_user")
public class User extends Model<User> {

	private static final long serialVersionUID = 1375903937173530760L;
	public static User dao = new User();
	private String groupIds;
	private String managerGroupIds;
	private Map<Integer,String> listName;
	private Map<Integer,Group> managerGroup;
	public List<GroupUser> getGroups(){
		return GroupUser.dao.find("select gu.*,g.groupName from t_group_user gu inner join t_group g " +
				"on gu.groupId = g.id where gu.userId = ? ", get("id"));
	}
	public String getGroupIds() {
		List<GroupUser> gus = getGroups();
		StringBuffer sbf = new StringBuffer("");
		for(int i = 0 ; i < gus.size() ; i ++){
			sbf.append(gus.get(i).get("groupId")).append(",");
		}
		if(sbf.toString().endsWith(",")){
			sbf.deleteCharAt(sbf.length()-1);
		}
		return sbf.toString();
	}
	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}
	public String getManagerGroupIds() {
		List<GroupUser> gus = getGroups();
		StringBuffer sbf = new StringBuffer("");
		for(int i = 0 ; i < gus.size() ; i ++){
			int groupID = gus.get(i).get("groupId");
			int userId = getInt("id");
			List<GroupUser> gs = GroupUser.dao.find("select * from t_group_user as g where g.groupId=" +groupID +" AND g.userId ="+userId +" AND g.isManager = 1");
			if(gs.size() > 0){
				sbf.append(groupID).append(",");
			}
		}
		if(sbf.toString().endsWith(",")){
			sbf.deleteCharAt(sbf.length() -1);
		}
		return managerGroupIds = sbf.toString();
	}
	
	public Map getListName(){
		if(listName != null){//暂时不考虑有修改的情况
			return listName;
		}
		List<User> users  = User.dao.find("select * from t_user");
		listName = new HashMap<Integer,String>();
		for(User user :users){
			listName.put(user.getInt("id"), (String)user.get("realName"));
		}
		return listName;
	}
	
	@Override
	public Map<String, Object> getAttrs() {
		return super.getAttrs();
	}
	@SuppressWarnings("unchecked")
	public Map<Integer, Group> getManagerGroup() {
		if(managerGroup != null){
			return managerGroup;
		}
		managerGroup = (Map<Integer, Group>) MyListUtils.toMap(Group.dao.findPart(" id in ( select groupId from "+GroupUser.dao.getTableName()+" where isManager = 1 and userId="+getInt("id")+")"));
		return managerGroup;
	}
}

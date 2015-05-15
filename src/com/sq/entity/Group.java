package com.sq.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Model;
import com.sq.base.SqageBaseEntity;
@SqageBaseEntity(tableName="t_group")
public class Group extends Model<Group> {
	public static final Group dao = new Group();
	private Map<Integer,String> listName;
	public List<GroupUser> getManagerList (){
		return GroupUser.dao.find("select u.realName,gu.userId from t_group_user gu inner join t_user u on gu.userId = u.id where gu.isManager = 1 and gu.groupId = ? ", get("id") );
	}
	
	@Override
	public Map<String, Object> getAttrs() {
		// TODO Auto-generated method stub
		return super.getAttrs();
	}
	
	public Map getListName(){
		if(listName != null){//暂时不考虑有修改的情况
			return listName;
		}
		List<Group> users  = Group.dao.find("select * from t_group");
		listName = new HashMap<Integer,String>();
		for(Group user :users){
			listName.put(user.getInt("id"), (String)user.get("groupName"));
		}
		return listName;
	}
	
}

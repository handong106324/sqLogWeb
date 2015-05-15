package com.sq.entity;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;
import com.sq.base.SqageBaseEntity;
@SqageBaseEntity(tableName="t_group_user")
public class GroupUser extends Model<GroupUser> {
	public static final GroupUser dao = new GroupUser();
	
	@Override
	public Map<String, Object> getAttrs() {
		// TODO Auto-generated method stub
		return super.getAttrs();
	}
	
}

package com.sq.entity;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;
import com.sq.base.SqageBaseEntity;
@SqageBaseEntity(tableName = "t_role")
public class Role extends Model<Role> {
	public static final Role dao = new Role();
	@Override
	public Map<String, Object> getAttrs() {
		// TODO Auto-generated method stub
		return super.getAttrs();
	}
	
}

package com.sq.entity;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;
import com.sq.base.SqageBaseEntity;
@SqageBaseEntity(tableName = "t_role_resource")
public class RoleResource extends Model<RoleResource> {
	
	public static RoleResource dao = new RoleResource(); 
	@Override
	public Map<String, Object> getAttrs() {
		// TODO Auto-generated method stub
		return super.getAttrs();
	}
}

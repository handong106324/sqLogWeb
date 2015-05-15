package com.sq.entity;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;
import com.sq.base.SqageBaseEntity;
@SqageBaseEntity(tableName ="t_resource")
public class Resource extends Model<Resource> {
	public static Resource dao = new Resource();
	@Override
	public Map<String, Object> getAttrs() {
		// TODO Auto-generated method stub
		return super.getAttrs();
	}
}

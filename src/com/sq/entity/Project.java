package com.sq.entity;

import com.jfinal.plugin.activerecord.Model;
import com.sq.base.SqageBaseEntity;
@SqageBaseEntity(tableName="project")
public class Project extends Model<Project> {
	public static Project dao = new Project();
}

package com.sq.entity;

import com.jfinal.plugin.activerecord.Model;
import com.sq.base.SqageBaseEntity;
@SqageBaseEntity(tableName="file_info")
public class LogFileInfo extends Model<LogFileInfo> {

	public static LogFileInfo dao = new LogFileInfo();

	
}

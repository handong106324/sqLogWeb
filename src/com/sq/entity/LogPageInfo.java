package com.sq.entity;

import com.jfinal.plugin.activerecord.Model;
import com.sq.base.SqageBaseEntity;
@SqageBaseEntity(tableName="log_page_info")
public class LogPageInfo extends Model<LogPageInfo> {
	public static LogPageInfo dao = new LogPageInfo();
}

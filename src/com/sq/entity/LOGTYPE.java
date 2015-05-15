package com.sq.entity;

import com.jfinal.plugin.activerecord.Model;
import com.sq.base.SqageBaseEntity;
@SqageBaseEntity(tableName="logtype")
public class LOGTYPE extends Model<LOGTYPE> {
	public static LOGTYPE dao = new LOGTYPE();
}

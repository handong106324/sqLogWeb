package com.sq.entity;

import com.jfinal.plugin.activerecord.Model;
import com.sq.base.SqageBaseEntity;
@SqageBaseEntity(tableName="message_guojihua")
public class InternetMessage extends Model<InternetMessage> {
	public static InternetMessage dao = new InternetMessage();
	
	public static String get_(String key){
		return "";
	}
}

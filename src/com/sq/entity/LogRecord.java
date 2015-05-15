package com.sq.entity;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;
import com.sq.base.SqageBaseEntity;

@SqageBaseEntity(tableName="log_record")
public class LogRecord extends Model{
	public static LogRecord dao = new LogRecord();
	
	public boolean log(String time,String userName,String playerId,String playerName,String operator,
			String content,String serverName,String operateType,String desc){
		LogRecord model = new LogRecord();
		model.set("time", time);
		model.set("userName", userName);
		model.set("playerId", playerId);
		model.set("operator", operator);
		model.set("content", content);
		model.set("serverName", serverName);
		model.set("playerName", playerName);
		model.set("operateType", operateType);
		model.set("desc", desc);
		model.set("createTime", new Date());
		return model.save();
	}
}

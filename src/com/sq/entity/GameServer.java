package com.sq.entity;

import com.jfinal.plugin.activerecord.Model;
import com.sq.base.SqageBaseEntity;

@SqageBaseEntity(tableName="games")
public class GameServer extends Model{
	public static GameServer dao = new GameServer();
}

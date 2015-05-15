package com.sq.entity;

import com.jfinal.plugin.activerecord.Model;
import com.sq.base.SqageBaseEntity;
@SqageBaseEntity(tableName="game")
public class GAME extends Model<GAME> {
	public static GAME dao = new GAME();
	
//	public static String getEnumByFlag(String gameName){
//		dao.findFirstByCondition(" game")
//	}
}

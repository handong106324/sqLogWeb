package com.sq.entity;

import com.jfinal.plugin.activerecord.Model;
import com.sq.base.SqageBaseEntity;
@SqageBaseEntity(tableName="keyword")
public class KeyWord extends Model<KeyWord> {
	public static KeyWord dao = new KeyWord();
}

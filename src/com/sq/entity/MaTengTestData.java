package com.sq.entity;

import com.jfinal.plugin.activerecord.Model;
import com.sq.base.SqageBaseEntity;
@SqageBaseEntity(tableName="t_temp")
public class MaTengTestData extends Model<MaTengTestData> {

	public static MaTengTestData dao = new MaTengTestData();
}

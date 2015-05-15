package com.sq.entity;

import com.jfinal.plugin.activerecord.Model;
import com.sq.base.SqageBaseEntity;

@SqageBaseEntity(tableName="t_page_condition")
public class PageCondition extends Model{
	public static PageCondition dao = new PageCondition();
}

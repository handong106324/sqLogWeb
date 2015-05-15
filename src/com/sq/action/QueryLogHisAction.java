package com.sq.action;

import com.sq.base.BaseAction;
import com.sq.base.SqageActionInterface;
@SqageActionInterface(path="his")
public class QueryLogHisAction extends BaseAction{

	public void list(){
		String start = getPara("date");
		String server = getPara("serverName");
		String fileName = getPara("fileName");
		String changjing = getPara("changjing");
		
	}
}

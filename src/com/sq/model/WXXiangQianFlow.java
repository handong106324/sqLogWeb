package com.sq.model;

import com.sq.base.ResultHandlerType;

@ResultHandlerType(type="wx_xq")
public class WXXiangQianFlow  extends BaseFlow{

	private int type = 0 ;
	public WXXiangQianFlow(String logSt, PageParam param) {
		super(logSt, param);
	}
	private int index = 0 ;
	
	@Override
	protected void loadAllData(String logSt) {
		super.loadAllData(logSt);
		if(logSt.contains("操作类别:镶嵌确认")){
			type = 1;
		}else if(logSt.contains("操作类别:确认拆除宝石")){
			type = 2;
		}
	}
	
	@Override
	protected void handleVal(String proVal) {
	
		if(type == 1){
			handleType1(proVal);
		}else if(type == 2){
			handleType2(proVal);
		}
		index ++;
	}

	private void handleType2(String proVal) {
		// TODO Auto-generated method stub
		//[INFO] 2015-02-04 12:10:45,217 [BeatHeart-Thread-92] - [操作类别:确认拆除宝石] [状态:成功] [username:91USER_350551303] [id:1386000000000021179] [name:KYS天羽绒] [ae:摇光影舞长靴] [articleId:1386000000088050692] [道具名字:宝石炎焚(4级)] [道具id:1386000000085877707] [1] [0ms]
		if(proVal.contains("操作类别")){
			put_("操作类型",proVal.split(":")[1]);
		}else if(proVal.contains("username")){
			put_("账号",proVal.split(":")[1]);
		}else if(proVal.contains("id")&&!proVal.contains("道具id")){
			put_("角色ID",proVal.split(":")[1]);
		}else if(proVal.contains("name") && !proVal.contains("username")){
			put_("角色名",proVal.split(":")[1]);
		}else if(proVal.contains("ae")){
			put_("物品名称",proVal.split(":")[1]);
		}else if(proVal.contains("articleId")){
			put_("物品ID",proVal.split(":")[1]);
		}else if(proVal.contains("道具名字")){
			put_("宝石名字",proVal.split(":")[1]);
		}else if(proVal.contains("道具id")){
			put_("宝石ID",proVal.split(":")[1]);
		}else if(proVal.contains("结果")){
			put_("结果",proVal.split(":")[1]);
		}else if(proVal.contains("状态")){
			put_("状态",proVal.split(":")[1]);
		}
	}

	private void handleType1(String proVal) {
		if(proVal.contains("操作类别")){
			put_("操作类型",proVal.split(":")[1]);
		}else if(proVal.contains("username")){
			put_("账号",proVal.split(":")[1]);
		}else if(proVal.contains("id")&&!proVal.contains("道具id")){
			put_("角色ID",proVal.split(":")[1]);
		}else if(proVal.contains("name") && !proVal.contains("username")){
			put_("角色名",proVal.split(":")[1]);
		}else if(proVal.contains("ae")){
			put_("物品名称",proVal.split(":")[1]);
		}else if(proVal.contains("articleId")){
			put_("物品ID",proVal.split(":")[1]);
		}else if(proVal.contains("道具名字")){
			put_("宝石名字",proVal.split(":")[1]);
		}else if(proVal.contains("道具id")){
			put_("宝石ID",proVal.split(":")[1]);
		}else if(proVal.contains("结果")){
			put_("结果",proVal.split(":")[1]);
		}else if(proVal.contains("状态")){
			put_("状态",proVal.split(":")[1]);
		}
		
	}



	

}

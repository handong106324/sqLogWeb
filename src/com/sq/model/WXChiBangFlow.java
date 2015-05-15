package com.sq.model;

import com.sq.base.ResultHandlerType;

@ResultHandlerType(type="wx_cb")
public class WXChiBangFlow  extends BaseFlow{

	public WXChiBangFlow(String logSt, PageParam param) {
		super(logSt, param);
	}
	private int index = 0 ;
	private int type = 0 ;
	@Override
	protected void loadAllData(String logSt) {
		super.loadAllData(logSt);
		if(logSt.contains("炼星成功。。。。")){
			setCanUse(false);
			return;
		}
		if(logSt.contains("确认镶嵌光效宝石") || logSt.contains("确认拆除光效宝石")){
			type = 1; 
		}else if(logSt.contains("镶嵌的光效宝石到期删除")){
			type = 2; 
		}else if(logSt.contains("镶嵌")){
			type = 3; 
		}else if(logSt.contains("翅膀面板强化确认")){
			type = 4; 
		}
	}
	
	@Override
	protected void handleVal(String proVal) {
	
		if(type == 1){
			handleType1(proVal);
		}else if(type == 2){
			handleType2(proVal);
		}else if(type == 3){
			handleType3(proVal);
		}else if(type == 4){
			handleType4(proVal);
		}
		index ++;
	}

	public void handleType1(String proVal){
		//[确认镶嵌光效宝石] [成功] [{username:562641943}{id:1386000000000003305}{name:醉酒惜花颜}{level:220}{channel:SQAGE_MIESHI}] [初级亮星宝石(3天)]
		if(index == 0){
			put_("操作类型",proVal);
		}else if(index == 1){
			put_("状态",proVal);
		}else if(index == 2){
			put_("账号",proVal.split(":")[1]);
		}else if(index == 3){
			put_("角色ID",proVal.split(":")[1]);
		}else if(index == 4){
			put_("角色名",proVal.split(":")[1]);
		}else if(index == 7){
			put_("物品",proVal);
		}
	}

	
	public void handleType(String proVal){
		if(proVal.contains("确认镶嵌光效宝石") || proVal.contains("确认拆除光效宝石")){
			handleType1(proVal);
		}else if(proVal.contains("镶嵌的光效宝石到期删除")){
			handleType2(proVal);
		}else if(proVal.contains("镶嵌")){
			handleType3(proVal);
		}else if(proVal.contains("翅膀面板强化确认")){
			handleType4(proVal);
		}
	}

	private void handleType4(String proVal) {
		//[翅膀面板强化确认] [炼星成功。。。。。。。。。。。。。。。。。。] [8]
		//[WARN] 2015-01-14 20:30:23,632 [仙境线程-9] - [翅膀面板强化确认] [成功] [{username:sunxiaoyao}{id:1386000000000002718}{name:❀ゞ若梦丶}{level:220}{channel:SQAGE_MIESHI}] [7] [8] [totalLuckValue:1000000] [resultValue:539669]
			if(index ==0){
				put_("操作类型",proVal);
			}else if(index ==1){
				put_("状态",proVal);
			}else if(index ==2){
				put_("账号",proVal.split(":")[1]);
			}else if(index ==3){
				put_("角色ID",proVal.split(":")[1]);
			}else if(index ==4){
				put_("角色名",proVal.split(":")[1]);
			}else if(index ==7){
				put_("强化前等级",proVal);
			}else if(index ==8){
				put_("强化后等级",proVal);
			}else if(index ==9){
				put_("成功率",proVal);
			}else if(index ==1){
				put_("成功率",proVal);
			}
	}

	private void handleType3(String proVal) {
		// [镶嵌] [成功] [{username:wsnidaye}{id:1386000000000286781}{name:吻我瑶瑶冰}{level:178}{channel:lenovosdk_MIESHI}] [宝石:宝石湛天(3级)] [宝石id:1386000000075109223] [镶嵌完成！把宝石湛天(3级)镶嵌到了装备的第1个插槽。] [孔:0]
		if(index == 0){
			put_("操作类型",proVal);
		}else if(index == 1){
			put_("状态",proVal);
		}else if(index == 2){
			put_("账号",proVal.split(":")[1]);
		}else if(index == 3){
			put_("角色ID",proVal.split(":")[1]);
		}else if(index == 4){
			put_("角色名",proVal.split(":")[1]);
		}else if(index == 7){
			put_("物品",proVal.split(":")[1]);
		}else if(index == 8){
			put_("物品ID",proVal.split(":")[1]);
		}else if(index == 9){
			put_("结果",proVal);
		}
	}

	private void handleType2(String proVal) {
		// [翅膀] [镶嵌的光效宝石到期删除] [{username:fxj1974}{id:1386000000000003345}{name:暗夜↘冷月}{level:220}{channel:BAKA001_MIESHI}] [中级翼光宝石(3天)] [1386000000087170507]
		if(index == 1){
			put_("操作类型",proVal);
		}else if(index == 2){
			put_("账号",proVal.split(":")[1]);
		}else if(index == 3){
			put_("角色ID",proVal.split(":")[1]);
		}else if(index == 4){
			put_("角色名",proVal.split(":")[1]);
		}else if(index == 7){
			put_("物品名称",proVal);
		}else if(index == 8){
			put_("物品ID",proVal);
		}
	}

}

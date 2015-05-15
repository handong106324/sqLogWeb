package com.sq.model;

import com.sq.base.ResultHandlerType;

@ResultHandlerType(type="wx_qh")
public class WXQiangHuaFlow  extends BaseFlow{

	String type ;
	public WXQiangHuaFlow(String logSt, PageParam param) {
		super(logSt, param);
	}
	private int index = 0 ;
	
	@Override
	protected void loadAllData(String logSt) {
		super.loadAllData(logSt);
		if(logSt.contains("操作类别:装备强化确认")){
			this.type = "操作类别:装备强化确认";
			
		}else if(logSt.contains( "reason:强化删除")){
			this.type =  "reason:强化删除";
		}else if(logSt.contains("确认装备精炼")){
			this.type = "确认装备精炼";
			if(logSt.contains("操作类别:确认装备精炼升级")){
				this.type = "操作类别:确认装备精炼升级";
			}
		}
		this.cols = new String[]{"操作类型","状态","账号","角色ID","角色名","物品名称","物品ID","强化前星级","强化后星级","成功率","是否绑定","结果"};
	}
	
	@Override
	protected void handleVal(String proVal) {
//		super.handleVal(proVal);
	
		if(this.type .equals("操作类别:装备强化确认")){
			handleType1(proVal);
		}else if(this.type .equals("reason:强化删除")){
			handleType2(proVal);
		}else if(this.type .equals("确认装备精炼")){
			handleType3(proVal);
		}else if(type.equals("操作类别:确认装备精炼升级")){
			handleType4(proVal);
		}
		index ++;
	}
	private String[] cols ;

	private void handleType3(String proVal) {
		// [确认装备精炼] [成功] [增加经验:16/100] [360SDKUSER_747162377] [1386000000000061759] [昊天混混] [曜日九幽天蚕] [1386000000087204459] [精炼成功，您的曜日九幽天蚕的精炼经验增长12] [1ms]
		if(index == 0){
			put_(this.cols[0], proVal);
		}else if(index == 1){
			put_(cols[1], proVal);
		}else if(index == 2){
			
		}else if(index == 3){
			put_(cols[2], proVal);
		}else if(index == 4){
			put_(cols[3], proVal);
		}else if(index == 5){
			put_(cols[4], proVal);
		}else if(index == 6){
			put_(cols[5], proVal);
		}else if(index == 7){
			put_(cols[6], proVal);
		}else if(index == 8){
			put_(cols[cols.length - 1], proVal);
		}else if(index == 9){
			
		}else if(index == 10){
			
		}else if(index == 11){
		}
	}

	private void handleType2(String proVal) {
		// [remove] [ae:炼星符] [articleId:1386000000088071921] [reason:强化删除] [从游戏中删除:true] [name:戦vs宠er]
		String[] vs = proVal.split(":");
		
		if(vs.length == 2){
			String pname = vs[0];
			if(vs[0].equals("ae")){
				pname = "物品名称";
			}else if(vs[0].equals("articleId")){
				pname = "物品ID";
			}else if(vs[0].equals("reason")){
				pname = "操作类型";
			}else if(vs[0].equals("name")){
				pname = "角色名";
			}
			put_(pname,vs[1]);
		}
	}

	private void handleType1(String proVal) {
		// [操作类别:装备强化确认] [状态:成功] [username:91USER_447733726] [id:1386000000000015639] [name:戦vs宠er] [ae:忘仙九幽蟒衣] [articleId:1386000000084138225] [强化前星级:2] [强化后星级:3] [totalLuckValue:444400] [resultValue:92582] [8ms]
		String[] vs = proVal.split(":");
		if(vs.length == 2){
			String pname = vs[0];
			String val = vs[1];
			if(vs[0].equals("ae")){
				pname = "物品名称";
			}else if(vs[0].equals("articleId")){
				pname = "物品ID";
			}else if(vs[0].equals("操作类别")){
				pname = "操作类型";
			}else if(vs[0].equals("name")){
				pname = "角色名";
			}else if(vs[0].equals("username")){
				pname = "账号";
			}else if(vs[0].equals("totalLuckValue")){
				pname = "成功率";
				val = proVal;
			}else if(vs[0].equals("resultValue")){
				val = proVal;
				pname = "成功率";
			}else if(vs[0].equals("id")){
				pname = "角色ID";
			}
			put_(pname,val);
		}
	}
	
	public void handleType4(String proVal){
		//[操作类别:确认装备精炼升级] [状态:成功] [562641943] [1386000000000003305] [醉酒惜花颜] [蚩尤血狂禅杖] [1386000000080709790] [装备升级成功！] [193ms]
		String[] vs = proVal.split(":");
		if(vs.length == 2){
			String pname = vs[0];
			String val = vs[1];
			if(vs[0].equals("操作类别")){
				pname = "操作类型";
			}
			put_(pname,val);
			return;
		}
		if(index== 5){
			put_("物品名称",proVal);
		}else if(index == 6){
			put_( "物品ID",proVal);
		}else if(index == 2){
			put_( "账号",proVal);
		} else if(index == 3){
			put_( "角色ID",proVal);
		}else if(index == 4){
			put_( "角色名",proVal);
		}else if(index == 7){
			put_( "结果",proVal);
		}
	}

}

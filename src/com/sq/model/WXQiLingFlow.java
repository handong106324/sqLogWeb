package com.sq.model;

import com.sq.base.ResultHandlerType;

@ResultHandlerType(type="wx_ql")
public class WXQiLingFlow  extends BaseFlow{

	public WXQiLingFlow(String logSt, PageParam param) {
		super(logSt, param);
	}
	private int index = 0 ;
	
	@Override
	protected void loadAllData(String logSt) {
		super.loadAllData(logSt);
	}
	
	@Override
	protected void handleVal(String proVal) {
	
		handleType(proVal);
		index ++;
	}



	
	public void handleType(String proVal){
		// [全部吞噬] [成功] [username:91USER_465696103] [id:1386000000000016326] [name:九幽～魔尊]
		//[level:220] [channel:91ZHUSHOU_MIESHI] [main:强化琼浆之灵(头)] [1386000000088113468] [color:0] [binded:false] [material:强化金汤之灵(头)] [1386000000088075239] [color:0] [binded:false] [吞噬成功增加100点吞噬经验]
		if(index== 0){
			put_("操作类型",proVal);
		}else if(index == 1){
			put_( "状态",proVal);
		}else if(index == 2){
			if(proVal.contains("username")){
				put_( "账号",proVal.split(":")[1]);
			}
		} else if(index == 3){
			if(proVal.contains("id")){
				put_( "角色ID",proVal.split(":")[1]);
			}
		}else if(index == 4){
			if(proVal.contains("name") && !proVal.contains("username")){
				put_( "角色名",proVal.split(":")[1]);
			}
		}else if(index == 7){
			if(proVal.contains("main")){
				put_( "物品名称",proVal.split(":")[1]);
			}
		}else if(index == 8){
				put_( "物品ID",proVal);
		}else if(index == 9){
			if(proVal.contains("color")){
				put_( "品质",proVal.split(":")[1]);
			}
		}else if(index == 10){
			if(proVal.contains("binded")){
				put_( "物品是否绑定",proVal.split(":")[1]);
			}
		}else if(index == 13){
			if(proVal.contains("color")){
				put_( "被吞噬品质",proVal.split(":")[1]);
			}
		}else if(index == 11){
			if(proVal.contains("material")){
				put_( "被吞噬装备",proVal.split(":")[1]);
			}
		}else if(index == 12){
			put_( "被吞噬装备ID",proVal);
		}else if(index == 14){
			if(proVal.contains("binded")){
				put_( "被吞噬是否绑定",proVal.split(":")[1]);
			}
		}else if(index == 15){
			put_("结果",proVal);
		}
		
	}

}

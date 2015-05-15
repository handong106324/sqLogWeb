package com.sq.model;

import com.sq.base.ResultHandlerType;

@ResultHandlerType(type="ORDER_INDEX")
public class OrderIndexFlow  extends BaseFlow{

	public OrderIndexFlow(String logSt, PageParam param) {
		super(logSt, param);
	}
	private boolean handlerOrderIndexFlag = false;
	private int index = 0 ;
	
	@Override
	protected void loadAllData(String logSt) {
		super.loadAllData(logSt);
		if(logSt.contains("减少经验")){
			this.handlerOrderIndexFlag = true;
		}
	}
	
	@Override
	protected void handleVal(String proVal) {
		super.handleVal(proVal);
	
		if(handlerOrderIndexFlag){
			if(index == 1){
				put_("账号", proVal);
			}else if(index == 2){
				put_("角色名", proVal);
			}else if(index == 3){
				put_("角色ID", proVal.split(":")[1]);
			}else if(index == 4){
				put_("获取经验", "-"+proVal);
			}else if(index == 6){
				if(proVal.contains("-->")){
					put_("原经验", proVal.split("-->")[0].split(":")[1]);
					put_("变化后经验", proVal.split("-->")[1]);

				}
			}
		}
		index ++;
	}
	

}

package com.sq.model;

import com.sq.base.ResultHandlerType;

@ResultHandlerType(type="BAIDU_ACCOUNT_WRONG")
public class BaiduAccountProblenFlow  extends BaseFlow{

	private boolean isStart  = false;
	public BaiduAccountProblenFlow(String logSt, PageParam param) {
		super(logSt, param);
	}
	private int index = 0 ;
	
	@Override
	public void handleOtherInfo(String old, String proVal) {
		if(proVal.equals("百度用户混乱问题")){
			isStart = true;
			return;
		}
		if(isStart){
			if(index == 0){
				super.handleOtherInfo(old, "使用账号："+ proVal+"<br>");
			}else if(index == 1){
				super.handleOtherInfo(old, "混乱账号："+ proVal+"<br>");
			}
			index ++;
		}
	}


}

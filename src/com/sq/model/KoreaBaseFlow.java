package com.sq.model;

import org.apache.commons.lang.StringUtils;

import com.sq.base.ResultHandlerType;
import com.sq.common.Constant;

@ResultHandlerType(type="kor")
public class KoreaBaseFlow  extends BaseFlow{

	public KoreaBaseFlow(String logSt, PageParam param) {
		super(logSt, param);
	}
	
	protected void loadAllData(String logSt) {
		String logStr = logSt.replaceAll("\\[", Constant.SQ_SEPARATOR).replaceAll("\\]",  Constant.SQ_SEPARATOR);
		logStr = logStr.replaceAll("\\{", Constant.SQ_SEPARATOR).replaceAll("\\}",  Constant.SQ_SEPARATOR);
		logStr = logStr.replaceAll(",", Constant.SQ_SEPARATOR);

		String[] logStrs = logStr.split(Constant.SQ_SEPARATOR);
		
		for(String log :logStrs){
			if(StringUtils.isNotBlank(log)){
				infos.add(log.trim());
			}
		}
	}
	

}

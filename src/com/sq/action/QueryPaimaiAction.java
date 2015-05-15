package com.sq.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sq.base.BaseAction;
import com.sq.base.SqageActionInterface;
import com.sq.utils.DateFormatUtils;
import com.sq.utils.JacksonManager;
import com.sq.utils.URLHandler;
@SqageActionInterface(path="paimai")
public class QueryPaimaiAction extends BaseAction{
	
	public void list(){
		String userName = getPara("userName");
		String playerName = getPara("playerName");
		String startTime  = getPara("startTime");
		String endTime  = getPara("endTime");
		String type = getPara("type");
		
		setAttr("startTime", startTime);
		setAttr("endTime", endTime);
		setAttr("userName", userName);
		setAttr("playerName", playerName);
		setAttr("type", type);
		Map<String,String> param = new HashMap<String,String>();
		if(StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime) || (StringUtils.isBlank(userName) && StringUtils.isBlank(playerName))){
			return;
		}
//		SimpleDateFormat sdf_all  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			param.put("startTime", DateFormatUtils.sdf_hour.parse(startTime).getTime()+"");
			param.put("endTime", DateFormatUtils.sdf_hour.parse(endTime).getTime()+"");
			param.put("userName", userName);
			param.put("playerName", playerName);
			param.put("type", type);
			String rsult = URLHandler.sendPost("http://210.242.234.78:28080/bas/interfaceLog_index.do", param, 60000);
			Map map = (Map)JacksonManager.getInstance().jsonDecodeObject(rsult);
			if(map.get("success").equals("true") || (Boolean)map.get("success")){
				List<Map> obj = (List<Map>) map.get("result");
				for(Map m : obj){
					String crt = m.get("CREATETIME")+"";
					if(StringUtils.isNotBlank(crt) && !"null".equals(crt)){
						m.put("CREATETIME", new Date(Long.parseLong(crt)));
					}
				}
				setAttr("list", obj);
			}else {
				setAttr("msg", map.get("msg"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

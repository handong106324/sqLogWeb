package com.sq.action;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sq.base.BaseAction;
import com.sq.base.SqageActionInterface;
import com.sq.cache.Cache;
import com.sq.entity.LOGTYPE;
import com.sq.entity.PageCondition;
import com.sq.log.EnumNotFoundException;
import com.sq.model.PageParam;
import com.sq.utils.MyListUtils;

@SqageActionInterface(path="gateway")
public class GatewayLogCenter extends BaseAction {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public void list() throws EnumNotFoundException{
		
		String game = getPara("game");
		setAttr("game", game);
		String serverName = getPara("serverName");
		if(StringUtils.isBlank(serverName)){
			setAttr("msg", "请先配置网关信息");
			return;
		}
		String endTime = getPara("endTime");
		String type = getPara("type");
		//服务器获取，方式不同，展示方式
//		setAttr("projects", getGameServers(ga));//);
		setAttr("endTime", endTime);
		setAttr("type", type);
		setAttr("action", "center");
		List<PageCondition> conds = Cache.getPageConditon(game,type);
		setAttr("conditions", conds);
		setAttr("conds", MyListUtils.getIdsStr(conds, "name"));
		PageParam param = new PageParam(type,game);
		setAttr("pros", param.getUsefualPages());
		if(StringUtils.isBlank(serverName) ){
			return;
		}
	}

}

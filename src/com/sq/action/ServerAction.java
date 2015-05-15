package com.sq.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sq.base.BaseAction;
import com.sq.base.SqageActionInterface;
import com.sq.entity.GAME;
import com.sq.entity.GameServer;
import com.sq.whcl.transport.message.QUERY_LOG_RES;
import com.sq.whcl.transport.webclient.WebClient;

@SqageActionInterface(path="server")
public class ServerAction extends BaseAction {

	public void querySize(){
		QUERY_LOG_RES res = WebClient.getInstance().query();
		Map map = new HashMap();
		map.put("articleQueueSize", res.getArticleQueueSize());
		map.put("currencyQueueSize", res.getCurrencyQueueSize());
		map.put("memory", res.getMemory());
		renderJson("result",map);
	}
	
	public void operateServer(){
		String operateType=getPara("operateType");
		String proName    = getPara("proName");
		WebClient.getInstance().operate(Byte.parseByte(operateType), Byte.parseByte(proName));
		renderJson("result","success");
	}
	
	public void queryServers(){
		String serverName = getPara("serverName");
		List<GameServer> sers = GameServer.dao.findPart("isUse =1 and name='wx' and serverName like '%"+serverName+"%'");
//		map.put("data", sers);
//		Map<String,Object> map = new HashMap<String,Object>();
		for(GameServer gs :sers){
			String sn = gs.getStr("serverName");
			gs.put("listName", sn.replace(serverName, "<font color=orange>"+serverName+"</font>"));
		}
//		map.put("result", "sucess");
		renderJson("data",sers);
	}
	
}

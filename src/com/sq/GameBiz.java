package com.sq;

import java.util.List;

import com.sq.cache.Cache;
import com.sq.entity.GAME;
import com.sq.entity.GameServer;
import com.sq.interfaces.GameServerOperation;

public class GameBiz {

	
	public List<GameServer> getServers(String game){
		GAME ga = Cache.getGameInfo(game);
		if("url".equals(ga.getStr("serverGet"))){
			return GameServerOperation.getServers();
		}else if("query".equals(ga.getStr("serverGet"))){
			return GameServer.dao.findPart("name='"+game+"' and isUse = 1");
		}
		return null;
	}
}

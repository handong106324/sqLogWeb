package com.sq.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.deploy.graph.Edge;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.sq.base.SqageActionInterface;
import com.sq.common.Constant;
import com.sq.entity.KeyWord;
import com.sq.entity.LogRecord;
import com.sq.entity.User;
import com.sq.interfaces.GameServerOperation;
import com.sq.stat.StatInfo;
import com.sq.whcl.transport.message.QUERY_CHAT_LOG_RES;
import com.sq.whcl.transport.model.ChatLogFlow;
import com.sq.whcl.transport.webclient.WebClient;

@SqageActionInterface(path="chat")
public class ChatAction extends Controller {
	
	Logger logger = Logger.getLogger(ChatAction.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
	SimpleDateFormat sdf_all = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@SuppressWarnings({ "rawtypes" })
	public void list() throws ParseException{
		String channel = getPara("channel");
		String serverName = getPara("serverName");
		if(StringUtils.isBlank(serverName)|| StringUtils.isBlank(channel)){
			return;
		}
		String[] servers = serverName.split(",");
		List<Map> flows = new ArrayList<Map>();
		for(String s:servers){
			QUERY_CHAT_LOG_RES res = WebClient.getInstance().queryChat(s,channel,50);
			if(res == null){
				continue;
			}
			ChatLogFlow[] flowt = res.getChatLogFlows();
			for(ChatLogFlow fl : flowt){
				if(hasKeyword(fl)){
					Map<String,Object> resu = new HashMap<String,Object>();
					resu.put("channel", fl.getChannel());
					resu.put("content", fl.getContent().replace("\n", " ").trim());
					resu.put("playerName", fl.getPlayerName());
					resu.put("playerId", fl.getPlayerId()+"");
					resu.put("userName", fl.getUsername());
					resu.put("channel", fl.getTime());
					resu.put("vipLevel", fl.getVipLevel());
					resu.put("serverName", fl.getServerName());
					resu.put("time", fl.getTime());
					flows.add(resu);
				}
			}
//			if(flows.size()>0)logger.debug("[ChatAction][server:"+s+"][channel:"+channel+"][数量:"+flows.size()+"]");
		}
		if(flows.size() ==0){
			renderJson("data","false");
			return;
		}
		User user = getSessionAttr("userInfo");
//		String result = JacksonManager.getInstance().toJson(flows);
		StatInfo.addChatUser(user.getStr("realName"));
		Map<String,Object> rs = new HashMap<String, Object>();
		rs.put("chatUsers", StatInfo.getChatUsers());
		rs.put("data", flows);
		renderJson(rs);
	}
	
	private boolean hasKeyword(ChatLogFlow fl) {
		if(Constant.isHaiwai)return true;
		List<KeyWord> kes = KeyWordAction.getKeyCache();
		String content = fl.getContent();
		for(KeyWord key : kes){
			if(content.contains(key.getStr("keyword"))){
				return true;
			}
		}
		return false;
	}

	public void close(){
		WebClient.getInstance().closeQueryChat();
		User user = getSessionAttr("userInfo");
//		String result = JacksonManager.getInstance().toJson(flows);
		StatInfo.removeChatUser(user.getStr("realName"));
		renderJson("data","success");
		return;
	}
	
	public void toList(){
		
		setAttr("servers", GameServerOperation.getServers());
		List<String> channels = new ArrayList<String>();
		if(Constant.isHaiwai){
			channels.add("世界");
			channels.add("私聊");
			channels.add("門派");
			channels.add("幫會");
			channels.add("交易");
			channels.add("侠义");
			channels.add("組隊");
			channels.add("彩世");
		}else{
			channels.add("世界");
			channels.add("私聊");
			channels.add("门派");
			channels.add("帮会");
			channels.add("交易");
			channels.add("侠义");
			channels.add("组队");
			channels.add("彩世");
		}
		setAttr("channels", channels);
		render("list.jsp");
	}
	
	public void jinyan(){
		String serverName = getPara("serverName");
		String playerId = getPara("playerId");
		String bantime = getPara("bantime");
		
		String playerName = getPara("playerName");
		String userName = getPara("userName");
		String time = getPara("time");
		String content = getPara("content");
		if(StringUtils.isBlank(serverName)){
			renderJson("result","服务器名称不能为空");
			return;
		}
		if(StringUtils.isBlank(playerId)){
			renderJson("result","角色Id不能为空");
			return;
		}
		if(StringUtils.isBlank(bantime)){
			bantime = "1";//默认禁言1小时
		}
		Map map = GameServerOperation.jinyan(serverName, playerId,bantime);
		if(map != null && "success".equals(map.get("result").toString())){
			logger.debug("[chatAction][jinyan][serverName="+serverName+"][playerId="+playerId+"][time="+bantime+"][userName="+userName+"][content="+content+"]");
			new LogRecord().log(time, userName, playerId, playerName,  ((User)getSessionAttr("userInfo")).getStr("realName"), content, serverName,"禁言", (!bantime.equals("0"))?("禁言"+bantime+"小时"):"永久禁言");
		}
		renderJson("result",map);
	}
	public void jieyan(){
		String serverName = getPara("serverName");
		String playerId = getPara("playerId");
		if(StringUtils.isBlank(serverName)){
			renderJson("result","服务器名称不能为空");
			return;
		}
		if(StringUtils.isBlank(playerId)){
			renderJson("result","角色Id不能为空");
			return;
		}
		Map map = GameServerOperation.jieJinyan(serverName, playerId);
		renderJson("result",map);
	}
	
	public void kick(){
		String serverName = getPara("serverName");
		String playerId = getPara("playerId");
		
		String playerName = getPara("playerName");
		String userName = getPara("userName");
		String time = getPara("time");
		String content = getPara("content");
		if(StringUtils.isBlank(serverName)){
			renderJson("result","服务器名称不能为空");
			return;
		}
		if(StringUtils.isBlank(playerId)){
			renderJson("result","角色Id不能为空");
			return;
		}
		Map map = GameServerOperation.kickPlayer(serverName, playerId);
		if(map != null && "success".equals(map.get("result").toString())){
			logger.debug("[chatAction][kick][serverName="+serverName+"][playerId="+playerId+"][userName="+userName+"][content="+content+"]");

			new LogRecord().log(time, userName, playerId, playerName,  ((User)getSessionAttr("userInfo")).getStr("realName"), content, serverName,"踢下线", "将玩家踢下线");
		}
		renderJson("result",map);
	}
	
	public void sealPassport(){
		String serverName = getPara("serverName");
		String userName = getPara("userName");
		String innerReason = getPara("innerReason");
		String reason = getPara("reason");
		String hour = getPara("hours");
		String time = getPara("time");
		String playerId = getPara("playerId");
		String playerName = getPara("playerName");
		logger.debug("[chatAction][sealPassport][serverName="+serverName+"][userName="+userName+"][reason="+reason+"][hour="+hour+"]");
		if(StringUtils.isBlank(serverName)){
			renderJson("result","服务器名称不能为空");
			return;
		}
		if(StringUtils.isBlank(userName)){
			renderJson("result","登录名不能为空");
			return;
		}
		if(StringUtils.isBlank(innerReason)){
			renderJson("result","对内原因不能为空");
			return;
		}
		if(StringUtils.isBlank(reason)){
			renderJson("result","对外原因不能为空");
			return;
		}

		if(StringUtils.isBlank(hour)){
			hour = "0";//默认封停1小时
		}
		Map map = GameServerOperation.sealPassport(serverName, userName, innerReason, reason, hour, playerId,playerName);
		if(map != null && "success".equals(map.get("result").toString())){
			new LogRecord().log(time, userName,playerId,playerName == null ?"":playerName,  ((User)getSessionAttr("userInfo")).getStr("realName"), reason, serverName,"封停", "账号封停");
		}
		renderJson("result",map);
	}
	
	public void liftSealPassport(){
		String serverName = getPara("serverName");
		String userName = getPara("userName");
		if(StringUtils.isBlank(serverName)){
			renderJson("result","服务器名称不能为空");
			return;
		}
		if(StringUtils.isBlank(userName)){
			renderJson("result","登录名不能为空");
			return;
		}
		Map map = GameServerOperation.liftSealPassport(serverName, userName);
		renderJson("result",map);
	}
	
	public void listOperate(){
		String userName = getPara("userName");
		String playerId = getPara("playerId");
		String operator = getPara("operator");
		String serverName = getPara("serverName");
		String playerName = getPara("playerName");
		String operateType = getPara("operateType");
		String startTime = getPara("startTime");
		String endTime   = getPara("endTime");
		setAttr("projects", GameServerOperation.getServers());
		setAttr("userName", userName);
		setAttr("playerName", playerId);
		setAttr("operator", operator);
		setAttr("serverName", serverName);
		setAttr("playerName", playerName);
		setAttr("operateType", operateType);
		setAttr("startTime", startTime);
		setAttr("endTime", endTime);
		int pageNumber = 1;
		if(getPara("pageNumber") != null){
			pageNumber = getParaToInt("pageNumber");
		}
		String where = " where 1=1 ";
		if(StringUtils.isNotBlank(userName)){
			where += " and userName ='"+userName+"'";
		}
		if(StringUtils.isNotBlank(playerId)){
			where += " and playerId ='"+playerId+"'";
		}
		if(StringUtils.isNotBlank(operator)){
			where += " and operator ='"+operator+"'";
		}
		if(StringUtils.isNotBlank(serverName)){
			where += " and serverName ='"+serverName+"'";
		}
		if(StringUtils.isNotBlank(playerName)){
			where += " and playerName ='"+playerName+"'";
		}
		if(StringUtils.isNotBlank(operateType)){
			where += " and operateType ='"+operateType+"'";
		}
		if(StringUtils.isNotBlank(startTime)){
			where += " and createTime >='"+startTime+"'";
		}
		if(StringUtils.isNotBlank(endTime)){
			where += " and createTime <='"+endTime+"'";
		}
		Page<LogRecord> page = LogRecord.dao.paginate(pageNumber, 20, " select * "," from log_record "+ where);
		setAttr("page", page);
	}
}

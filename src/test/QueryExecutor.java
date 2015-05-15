package test;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import test.data.QueryCondition;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.sq.shell.connection.ConnectionFactory;
import com.sq.utils.PropertiesUtils;

public class QueryExecutor {
	List<String> allTitles = new ArrayList<String>();
	public  List<QueryCondition> loadAllCondition(String ip, String port, String date,String path) {
		List<QueryCondition> qcs = new ArrayList<QueryCondition>();
		String filePath = path +"/3d_log_*/3dgame/playerManager.log";
		String[] titles = new String[]{"角色死亡"};//,"镶嵌宝石","装备强化""领取帮会BUFF",,"完成任务"
		add(qcs, titles, filePath, ip, port);
//		开光装备     	
//		强化装备     equipmentDataManager	
//		熔炼装备     equipmentDataManager	
//		镶嵌宝石     equipmentDataManager	
//		血炼装备     equipmentDataManager	
		String filePath_kaiguang =path+ "/3d_log_*/3dgame/equipmentDataManager.log";
		String[] titles_kaiguang = new String[]{"装备开光成功","装备熔炼成功","装备血炼成功","装备强化完成","镶嵌宝石完成"};//,"
		add(qcs, titles_kaiguang, filePath_kaiguang, ip, port);
		
//		铁匠         billingCenter		修理装备
		add(qcs, new String[]{"修理装备"}, path+ "/3d_log_*/3dgame/billingCenter.log", ip, port);
//		聊天         chatMessageManager		聊天
		add(qcs, new String[]{"聊天"}, path+ "/3d_log_*/3dgame/chatMessageManager.log", ip, port);
//		历练跑环     taskManager		历练跑环
//		文职跑环     taskManager		文职跑环
//		每日参拜     taskManager		参拜
//		帮会行侠     taskManager		帮会行侠
		add(qcs, new String[]{"历练跑环","文职跑环","参拜","帮会行侠","完成任务"}, path+ "/3d_log_*/3dgame/taskManager.log", ip, port);
//		传功         shiTuManager		对方同意传功开始
//		拜师完成处理任务完成  收徒
		add(qcs, new String[]{"对方同意传功开始","0收徒成功1拜师成功"}, path+ "/3d_log_*/3dgame/shiTuManager.log", ip, port);
//		据点战       shanzhai			增加帮会积分
		add(qcs, new String[]{"增加帮会积分"}, path+ "/3d_log_*/3dgame/shanzhai.log", ip, port);
//		门派运镖     treasureManager		运镖完成
		add(qcs, new String[]{"运镖完成"}, path+ "/3d_log_*/3dgame/treasureManager.log", ip, port);
//		演武         trainningManager		玩家开始演武
		add(qcs, new String[]{"玩家开始演武"}, path+ "/3d_log_*/3dgame/trainningManager.log", ip, port);
		
//		武林情缘     qingYuanManager		情缘活动开始运行完成  |  国内情缘
//		煮酒论剑     qingYuanManager		情缘活动开始运行完成  |  国内煮酒
//		江湖情缘     qingYuanManager		情缘活动开始运行完成  |  国外情缘
//		江湖煮酒     qingYuanManager		情缘活动开始运行完成  |  国外煮酒
		add(qcs, new String[]{"国内情缘","国内煮酒","国外情缘","国外煮酒"},path+ "/3d_log_*/3dgame/qingYuanManager.log", ip, port,"情缘活动开始运行完成");
//		江湖令       knapsack			remove |  江湖令
		add(qcs, new String[]{"江湖令"}, path+ "/3d_log_*/3dgame/knapsack.log", ip, port,"remove");
//		帮会钓鱼     fishManager		帮会钓鱼开始
		add(qcs, new String[]{"帮会钓鱼开始"}, path+ "/3d_log_*/3dgame/fishManager.log", ip, port);
//		帮会跑商     tradingManager		开启跑商
		add(qcs, new String[]{"开启跑商"}, path+ "/3d_log_*/3dgame/tradingManager.log", ip, port);
		//gameInstanceManager
		add(qcs, new String[]{"玩家进入副本"}, path+ "/3d_log_*/3dgame/gameInstanceManager.log", ip, port);
		//玩家进入战场 
		add(qcs, new String[]{"玩家进入战场"}, path+ "/3dgame/battleSceneManager.log", ip, port);
		//武学技能
		//申请好友
		add(qcs, new String[]{"社交操作完成"},path+ "/3d_log_*/3dgame/socialityManager.log", ip, port,"-E '增加.*[0]'");
		
		add(qcs, new String[]{"申请取消入会","领取BUFF"}, path+ "/3d_log_*/3dgame/faction.log", ip, port,"-E '(type:0|\\[领取BUFF\\])'");

		add(qcs, new String[]{"学习技能","技能升级"}, path+ "/3d_log_*/3dgame/game.log", ip, port,true);
		return qcs;
	}
	
	public void add(List<QueryCondition> qcs ,String[] titles , String filePath,String ip,String port){
		QueryCondition qd = new QueryCondition();
		qd.setFilePath(filePath);
		qd.setTitles(titles);
		qd.setIp(ip);
		qd.setPort(port);
		qd.setUserNames(userNames);
		qcs.add(qd);
		addAllStringArray(titles);
	}
	
	public void addAllStringArray(String[] tits){
		for(String ti:tits){
			allTitles.add(ti);
		}
	}
	
	
	public void add(List<QueryCondition> qcs ,String[] titles , String filePath,String ip,String port,boolean byHour){
		QueryCondition qd = new QueryCondition();
		qd.setFilePath(filePath);
		qd.setTitles(titles);
		qd.setIp(ip);
		qd.setPort(port);
		qd.setUserNames(userNames);
		qd.setByHour(byHour);
		qcs.add(qd);
		addAllStringArray(titles);
	}
	
	
//	public void add(List<QueryCondition> qcs ,String[] titles , String filePath,String ip,String port,boolean byHour,String filter){
//		QueryCondition qd = new QueryCondition();
//		qd.setFilePath(filePath);
//		qd.setTitles(titles);
//		qd.setIp(ip);
//		qd.setFilter(filter);
//		qd.setPort(port);
//		qd.setUserNames(userNames);
//		qd.setByHour(byHour);
//		qcs.add(qd);
//		addAllStringArray(titles);
//	}
//	
	public void add(List<QueryCondition> qcs ,String[] titles , String filePath,String ip,String port, String string){
		QueryCondition qd = new QueryCondition();
		qd.setFilePath(filePath);
		qd.setTitles(titles);
		qd.setIp(ip);
		qd.setPort(port);
		qd.setFilter(string);
		qd.setUserNames(userNames);
		qcs.add(qd);
		addAllStringArray(titles);
	}

//	@Test
//	public void testParseDate() throws ParseException{
//		String start = "2014-12-01",end ="2014-12-30";
//		String[] ds = parseDates(start, end);
//		for(String d : ds){
//			System.out.println(d);
//		}
//	}
	private  String jdbcFile = "jdbc.properties";
	public List<String> userNames;
	public QueryExecutor(List<String> userNamess){
		this.userNames = userNamess;
		init();
	}
	private  void init() {
		String key = PropertiesUtils.getPropertiesValue(jdbcFile, "shell_key");
		String userName = PropertiesUtils.getPropertiesValue(jdbcFile,"shell_username");
		String pwd = PropertiesUtils.getPropertiesValue(jdbcFile,"shell_password");
		String isByKey =  PropertiesUtils.getPropertiesValue(jdbcFile,"isByKey");
		if(isByKey == null){
			isByKey = "true";
		}
		File keyfile = new File(key);
		
		if(!keyfile.exists()){
			keyfile = new File(PathKit.getWebRootPath() + File.separator + "WEB-INF" + File.separator+"classes"+File.separator+"pingtai");
		}
		ConnectionFactory.getInstance().init(key, userName, pwd,Boolean.valueOf(isByKey));
	}
	
	
	public Boolean printM(List<QueryCondition> condis,String date){
		System.out.println(date+" 开始查询--");
		for(QueryCondition qd : condis){
			if(qd.isByHour())date = date+"*";
			QueryInfo qi = new QueryInfo(qd.getTitles(),qd.getIp(),qd.getPort(),qd.getFilePath()+"."+date+".log.gz",qd.getUserNames(),qd.getFilter(),date);
			if(!qi.isSuccess()){
				System.out.println("数据查询失败，跳出："+date);
				return false;
			}
			List<String> thisList = qi.getSqlList();
			if(thisList .size() > 0)Db.batch(thisList, 100);
		}
		return true;
	}
	
}


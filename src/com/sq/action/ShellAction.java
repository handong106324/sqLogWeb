package com.sq.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sq.base.BaseAction;
import com.sq.base.SqageActionInterface;
import com.sq.common.Constant;
import com.sq.utils.JacksonManager;
import com.sq.utils.URLHandler;

@SqageActionInterface(path="shell")
public class ShellAction extends BaseAction{
	
	public void runSh(){
//		String cmd = getPara("cmd");
//		int count = getParaToInt("count");
		Map<String,String> param = new HashMap<String,String>();
		param.put("cmd", "cat /data2/3dgame_log/wohucanglong/3d_log_game3/3dgame/billingCenter.log | grep 用户充值");
		param.put("count", 1000+"");
		try {
			String url = "http://117.121.17.11:28089/whclLog/shell/runSh.do";
			String urlTest = "http://localhost:8080/whclLog/shell/runSh.do";
			String res = URLHandler.sendPost(url, param,20);
//			String res = HttpUtils.sendPost(urlTest, (HashMap<String, String>) param);
			List<String> list = (List)JacksonManager.getInstance().jsonDecodeObject(res);
			for(String s:list){
				System.out.println(s);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static void main(String[] args){
		ShellAction s = new ShellAction();
		s.runSh();
//		s.parseToLog();
		try {
			System.out.println(sdf.parse("2014-10-28 06:12:02,322"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void parseToLog(){
		String log = "[INFO] 2014-10-28 06:12:02,322 [pool-5-thread-321] - [用户消费] [成功] [私银] [货币类型:私银] [用户:UCUSER_375115147] [角色:红雪、战神] [服务器:wohucanglong] [场景服:gameSceneThree] [消费金额:1600] [消费渠道:角色神武宝甲] [地图名:nj_lingyunku] [角色等级:58] [账户变化:3846759 -> 3845159] [角色神武宝甲] [0]";
		log = log.replaceAll("\\[", Constant.SQ_SEPARATOR).replaceAll("\\]",  Constant.SQ_SEPARATOR);
		String[] basicLogInfo = log.split( Constant.SQ_SEPARATOR);
		for(String s:basicLogInfo){
			System.out.println(s);
		}
	}
	
	public void testDoShell(){
		String cmd = getPara("filePath");
//		long count = ShellFactory.getInstance().getCount(cmd, "","");
//		renderJson("result",cmd+" 的总记录数为:"+count);
	}
}

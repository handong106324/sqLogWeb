package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.jfinal.kit.PathKit;
import com.sq.common.Constant;
import com.sq.log.result.LogFileCheckInfo;
import com.sq.model.FileInfo;
import com.sq.model.ShellInfo;
import com.sq.shell.ShellDao;
import com.sq.shell.ShellFactory;
import com.sq.shell.connection.ConnectionFactory;
import com.sq.shell.currency.ThreadPool;
import com.sq.shell.result.LogResult;
import com.sq.utils.PropertiesUtils;

public class TstShell {

	private static String behaviors = "玩家离开游戏|玩家跨图|接取任务|完成任务|玩家死亡|玩家复活|收取邮件|玩家进入游戏|合成物品|领取帮会鱼饵|玩家升级|装备摘取宝石|商城购买物品|经脉突破|穿装备|装备强化|脱装备|装备镶嵌宝石" ;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		init();
		long start = System.currentTimeMillis();
		final String param = loadParas("V");
		final ShellInfo info = new ShellInfo();
		info.setCount(1000);
		info.setIp("117.121.17.11");
		info.setPort("22201");
		info.setShell(false);
		System.out.println(param);
//		//.2014-11-16.log.gz
//		String  para = " | grep -v '(玩家离开游戏|玩家跨图|接取任务|完成任务|玩家死亡|	玩家复活|收取邮件|玩家进入游戏|合成物品|领取帮会鱼饵|玩家升级"+
//				"|装备摘取宝石|商城购买物品|经脉突破|穿装备|装备强化|脱装备|装备镶嵌宝石)'" ;
		getResult("/data2/3dgame_log/fengqiyunyong/3d_log_game1/3dgame/behaviorManager.log", param, 10000000, info);
//		LogFileCheckInfo ci =ShellFactory.getInstance().checkFileExsit("/data2/3dgame_log/wohucanglong/3d_log_*/3dgame/knapsack.log.2014-11-16.log.gz", info);
//		List<FileInfo> fis = ci.getFiles();
////		if(fis != null){
////			for(FileInfo fi :fis){
////				long start1 = System.currentTimeMillis();
////				LogResult res=ShellFactory.getInstance().getResult(fi.getFileName(), param, 1000, info);
////				System.out.println(fi.getFileName()+"文件大小："+(fi.getSize()/1024/1024)+"扫描后结果: "+(res.getDatas()!=null?res.getDatas().size():0)+" 耗时："+(System.currentTimeMillis() - start1));
////			}
////		}
//		
//			// TODO Auto-generated method stub
//			final BlockingQueue<Future<LogResult>> fus = new LinkedBlockingDeque<Future<LogResult>>();
//			CompletionService<LogResult> ser = new ExecutorCompletionService<LogResult>(ThreadPool.getInstance());
//			for(final FileInfo fi :fis){
//				fus.add(ser.submit(new Callable<LogResult>() {
//					public LogResult call() throws Exception {
//						LogResult res=ShellFactory.getInstance().getResult(fi.getFileName(), param, 1000 , info);
//						System.out.println(fi.getFileName()+"文件大小："+(fi.getSize()/1024/1024));
//						return res;
//					}
//				}));
//			}
//			
//			int size = fus.size();
//			for(int i = 0 ; i < size;i++){
//				try {
//					LogResult res = (LogResult) ser.take().get(30, TimeUnit.SECONDS);
//					System.out.println("扫描后结果: "+(res.getDatas()!=null?res.getDatas().size():0));
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
////		System.out.println(ci.getMsg());
//		System.out.println(System.currentTimeMillis() - start);
	}
	
	private static String jdbcFile = "jdbc.properties";
	private static void init() {
		String key = PropertiesUtils.getPropertiesValue(jdbcFile, "shell_key");
		String userName = PropertiesUtils.getPropertiesValue(jdbcFile,
				"shell_username");
		String pwd = PropertiesUtils.getPropertiesValue(jdbcFile,
				"shell_password");
		String isByKey =  PropertiesUtils.getPropertiesValue(jdbcFile,
				"isByKey");
		if(isByKey == null){
			isByKey = "true";
		}
		File keyfile = new File(key);
		if(!keyfile.exists()){
			keyfile = new File(PathKit.getWebRootPath() + File.separator + "WEB-INF" + File.separator+"classes"+File.separator+"pingtai");
		}
		ConnectionFactory.getInstance().init(key, userName, pwd,Boolean.valueOf(isByKey));
	}

	private static void getResult(String string, String param, int i,ShellInfo info) {
		Session sess = null;
		try {
			long start = System.currentTimeMillis();
			sess = ConnectionFactory.getInstance().getConn(info.getIp(), info.getPort()).openSession();
			sess.execCommand("cat "+string+"  "+param);
			InputStream stdout = new StreamGobbler(sess.getStdout());
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout, "UTF-8"));
			long index = 0;
			String line = null;
			while ((line = br.readLine()) != null) {
				if(notContain(line))filterData(line);
				index ++;
				if(index % 10000 == 0){
					System.out.println(index);
				}
			}
			for(String type:types){
				System.out.println(type);
			}
			System.out.println("耗时="+(System.currentTimeMillis() - start)+"  count=");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sess != null)
				sess.close();
		}
		
	}

	private static boolean notContain(String line) {
		for(String t : types){
			if(line.contains(t)){
				return false;
			}
		}
		return true;
	}

	private static List<String> types = new ArrayList<String>();
	private static void filterData(String line) {
		String logStr = line.replaceAll("\\[", Constant.SQ_SEPARATOR).replaceAll("\\]",  Constant.SQ_SEPARATOR);
		logStr = logStr.replaceAll("\\{", Constant.SQ_SEPARATOR).replaceAll("\\}",  Constant.SQ_SEPARATOR);
		String[] logStrs = logStr.split(Constant.SQ_SEPARATOR);
		boolean can = false;
		for(String st : logStrs){
			if(st.trim().equals("-")){
				can = true;
				continue;
			}
			if(can){
				if(!types.contains(st)){
					System.out.println(st);
					types.add(st);
				}
				break;
			}
		}
	}

	private static String loadParas(String t){
		String[] bes = behaviors.replace("|", ",").split(",");
		StringBuilder sb = new StringBuilder();
		for(String be: bes){
			sb.append(" | grep -v " ).append(be);
		}
		return sb.toString();
	}
	
	private static String loadParas() {
		String fi = "E://tttt.txt";
		try {
			FileReader reader = new FileReader(new File(fi));
			BufferedReader rd = new BufferedReader(reader);
			String temp = null;
			StringBuilder sb = new StringBuilder("| grep -E '(");
			long count = 0 ;
			while((temp = rd.readLine()) != null){
				if(count ++ ==0)sb.append(temp);
				else sb.append("|").append(temp);
			}
			sb.append(")'");
			return sb.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

//	public String getFileName(String key){
//		String[] equipmentDataManager = new String[]{"强化装备","镶嵌宝石","开光","熔炼","血炼"};
//		String[] chatMessageManager = new String[]{"chatMessageManager"};
//		String[] taskManager = new String[]{"历练跑环","文职跑环","每日参拜","帮会行侠","完成任务"};
//		String[] treasureManager = new String[]{"门派运镖"};
//		String[] shiTuManager = new String[]{"传功"};
//		String[] shanzhai = new String[]{"据点战"};
//		String[] trainningManager = new String[]{"演武"};
//		String[] faction = new String[]{"帮会buff"};
//		String[] qingYuanManager = new String[]{"武林情缘","煮酒论剑","江湖情缘","江湖煮酒"};
//		String[] jianghuling = new String[]{"江湖令"};
//		String[] fishManager = new String[]{"钓鱼"};
//		String[] tradingManager = new String[]{"跑商"};
//		String[] game = new String[]{"修炼武学"};
//	}
}

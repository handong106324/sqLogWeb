package test.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sq.entity.LogFileInfo;
import com.sq.model.ShellInfo;
import com.sq.shell.ShellFactory;
import com.sq.shell.result.LogResult;

public class QueryDataForLog {

	private long count = 0;
	private Map<String,Long> countMap = new HashMap<String,Long>();
	private Map<String,Long> alsoMap = new HashMap<String,Long>();
	/**
	 * @return the countMap
	 */
	public Map<String, Long> getCountMap() {
		return countMap;
	}
	/**
	 * @param countMap the countMap to set
	 */
	public void setCountMap(Map<String, Long> countMap) {
		this.countMap = countMap;
	}
	public void execute(String date) {
//		loadHasGotenData();
		List<LogFileInfo> allFileInfos = LogFileInfo.dao.findPart("gameName = 'whcl' and serverName='天下无双' order by serverName");
		for(LogFileInfo lfi :allFileInfos){
//			if(hasGotData(lfi)){
//				
//				continue;
//			}
			long queryCount = query(lfi,date);
			if(queryCount == 0){
				continue;
			}
			setCount(getCount() + queryCount);
			Long serverCount = countMap.get(lfi.getStr("serverName"));
			if(serverCount == null){
				serverCount = 0l;
				countMap.put(lfi.getStr("serverName"), serverCount);
			}
			serverCount += queryCount;
		}
	}
	private void loadHasGotenData() {
		this.alsoMap.put("卧虎藏龙_物品", 14369083l);
		this.alsoMap.put("卧虎藏龙_邮件", 558706l);
		this.alsoMap.put("卧虎藏龙_货币", 896675l);
		this.alsoMap.put("风起云涌_物品", 11250746l);
		this.alsoMap.put("风起云涌_邮件", 408558l);
		
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		
//		天下无双_物品------------------------------
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		22649371
//		天下无双_邮件------------------------------
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		894339
//		天下无双_货币------------------------------
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		1392842
//
//		武林至尊_物品------------------------------
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		10456070
//		武林至尊_邮件------------------------------
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		348473
//		武林至尊_货币------------------------------
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		612214
//
//		笑傲武林_物品------------------------------
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		13522034
//		笑傲武林_邮件------------------------------
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		521508
//		笑傲武林_货币------------------------------
//		916384
//		this.alsoMap.put("风起云涌_货币", 609143l);
//
//		武林至尊_任务------------------------------
//		747474
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		武林至尊_宝石------------------------------
//		74049
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		武林至尊_强化------------------------------
//		8927
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		笑傲武林_宝石------------------------------
//		108725
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		笑傲武林_强化------------------------------
//		13240
//		this.alsoMap.put("风起云涌_货币", 609143l);
//
//
//		笑傲武林_任务------------------------------
//		1397630
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		天下无双_宝石------------------------------
//		168445
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		天下无双_强化------------------------------
//		19123
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		天下无双_任务------------------------------
//		1868044
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		风起云涌_宝石------------------------------
//		67728
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		风起云涌_强化------------------------------
//		7710
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		风起云涌_任务------------------------------
//		593216
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		卧虎藏龙_宝石------------------------------
//		101351
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		卧虎藏龙_强化------------------------------
//		12160
//		this.alsoMap.put("风起云涌_货币", 609143l);
//
//
//
//
//		卧虎藏龙_任务------------------------------
//		1034660
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		卧虎藏龙_武学------------------------------
//		50685
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		卧虎藏龙_副本------------------------------
//		630344
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		天下无双_副本------------------------------
//		914441
//		this.alsoMap.put("风起云涌_货币", 609143l);
//
//
//		天下无双_武学------------------------------
//		82088
//		this.alsoMap.put("风起云涌_货币", 609143l);
//
//
//		风起云涌_武学------------------------------
//		34093
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		风起云涌_副本------------------------------
//		466470
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		武林至尊_武学------------------------------
//		32661
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		武林至尊_副本------------------------------
//		415655
//		this.alsoMap.put("风起云涌_货币", 609143l);
//
//		笑傲武林_武学------------------------------
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		51145
//
//		笑傲武林_副本------------------------------
//		550022
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		武林至尊_神武------------------------------
//		123618
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		笑傲武林_神武------------------------------
//		0
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		天下无双_神武------------------------------
//		276191
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		风起云涌_神武------------------------------
//		124185
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		卧虎藏龙_神武------------------------------
//		170994
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		武林至尊_功绩声望------------------------------
//		41819
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		天下无双_功绩声望------------------------------
//		87813
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		风起云涌_功绩声望------------------------------
//		45931
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		卧虎藏龙_功绩声望------------------------------
//		58656
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		笑傲武林_功绩声望------------------------------
//		54835
//		this.alsoMap.put("风起云涌_货币", 609143l);
//		武林至尊_修为------------------------------
//		911150
//		this.alsoMap.put("风起云涌_货币", 609143l);
//
//		this.alsoMap.put("天下无双_修为", 2144669l);
//		
		
	}
	private boolean hasGotData(LogFileInfo lfi) {
		
		
		return false;
	}
	private long query(LogFileInfo lfi, String date) {
		String dir = lfi.getStr("dir");
		String fileName = lfi.getStr("fileName");
		String querytype = lfi.getStr("queryType");
		boolean isByShell = false;
		String queryWhere = lfi.getStr("queryWhere");
		String ip = lfi.getStr("ip");
		String port = lfi.getStr("port");
		String webIip = lfi.getStr("webIp");
		String webport = lfi.getStr("webPort");
		ShellInfo shInfo = new ShellInfo();
		shInfo.setFilter(queryWhere);
		if(StringUtils.isNotBlank(querytype) && querytype.equals("shell")){
			isByShell = true;
			shInfo.setIp(ip);
			shInfo.setPort(port);
		}else {
			shInfo.setIp(webIip);
			shInfo.setPort(webport);
		}
		shInfo.setMany(false);
		shInfo.setPath(dir);
		shInfo.setShell(isByShell);
		LogResult logResult = ShellFactory.getInstance().getResult(getFileName(dir,fileName,date), getPara(queryWhere), 100, shInfo);
		List<String> ls = logResult.getDatas();
		if(ls == null) return 0;
		System.out.println(lfi.getStr("serverName")+"_"+lfi.getStr("type")+"------------------------------");
		for(String ll : ls){
			System.out.println(ll);
		}
		Long l = Long.parseLong(ls.get(ls.size() - 1));
//		ShellDao shelLDao = new ShellDao(ConnectionFactory.getInstance());
		
		return l;
	}
	private String getPara(String queryWhere) {
		if(StringUtils.isBlank(queryWhere)){
			return "| wc -l" ;
		}
		if(!queryWhere.startsWith("'")){
			queryWhere = "'"+queryWhere;
		}
		if(!queryWhere.endsWith("'")){
			queryWhere += "'";
		}
		return " | grep -E "+queryWhere+" | wc -l";
	}
	private String getFileName(String dir, String fileName, String date) {
		if(!fileName.endsWith(".log")){
			fileName +=".log";
		}
		return dir +fileName+"."+date+"*.log.gz";
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}

}

package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.sq.model.ShellInfo;
import com.sq.shell.connection.ConnectionFactory;

public class QueryInfo {

	private boolean isSuccess = true;
	private String[] titles;
	private ShellInfo shellInfo;
	private String date;
	private Map<String,Map<String,Integer>> userMap = new HashMap<String,Map<String,Integer>>();
	/**
	 * @return the userMap
	 */
	public Map<String, Map<String, Integer>> getUserMap() {
		return userMap;
	}


	/**
	 * @param userMap the userMap to set
	 */
	public void setUserMap(Map<String, Map<String, Integer>> userMap) {
		this.userMap = userMap;
	}

	private String filter;
	
	private List<String> userNames;
	public QueryInfo(String[] titles,String ip ,String port,String filePath,List<String> userNames,String filter,String date){
		this.titles =  titles;
		this.filePath = filePath;
		shellInfo = new ShellInfo();
		shellInfo.setIp(ip);
		this.date = date;
		shellInfo.setPort(port);
		this.filter = filter;
		this.userNames = userNames;
		param = loadParas();
		exe();
	}

	
	private String filePath;
	private String param;
	
	
	private void exe() {
	
		getResult(filePath, param);
	}
	
	
	public List<String> getSqlList() {
		Iterator<String> users = userMap.keySet().iterator();
		List<String> sqlList = new ArrayList<String>();
		while(users.hasNext()){
			String user = users.next();
			Map<String,Integer> uM = userMap.get(user);
			Iterator<String> ti = uM.keySet().iterator();
			while(ti.hasNext()){
				String title = ti.next();
				String sql = " insert into t_temp (userName, type,val,queryDate) values ('"+user+"','"+title+"','"+uM.get(title)+"','"+date+"')";
				sqlList.add(sql);
			}
		}

		return sqlList;
	}


	private  void getResult(String string, String param) {
		Session sess = null;
		try {
			long start = System.currentTimeMillis();
			sess = ConnectionFactory.getInstance().getConn(shellInfo.getIp(), shellInfo.getPort()).openSession();
			System.out.println("获取Session时间"+(System.currentTimeMillis() - start));
			String cat = "cat";
			if(string.endsWith(".log.gz")){
				cat = "zcat";
			}
			String f="";
			if(StringUtils.isNotBlank(filter)){
				f = "| grep "+filter;
			}
			sess.execCommand(cat+" "+string+" "+f+"  "+param);
			System.out.println("执行命令获取数据的时间"+(System.currentTimeMillis() - start));
			InputStream stdout = new StreamGobbler(sess.getStdout());
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout, "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				filterData(line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			this.isSuccess = false;
		
		} finally {
			if (sess != null)
				sess.close();
		}
//		System.out.println("---------------------Result----------------------");
//		for(String tit : titles){
//			System.out.print("\t");
//			System.out.print(tit);
//		}
//		System.out.println();
//		for(String u : userNames){
//			if(userMap.get(u)==null){
//				continue;
//			}
////			System.out.print(u);
////			System.out.print("\t");
////			for(String tit :titles){
////				if(userMap.get(u).get(tit) ==null){
////					userMap.get(u).put(tit,0);
////				}
//////				System.out.printf("    "+userMap.get(u).get(tit)+"    ");
//////				System.out.print("\t");
////			}
////			System.out.println();
//			
//		}
		
	}
	
	private  void filterData(String line) {
		for(String title : titles){
			if(line.contains(title)){
				for(String u  : userNames){
					if(line.contains(u)){
						Map<String,Integer> m = userMap.get(u);
						if(m == null){
							m = new HashMap<String,Integer>();
							userMap.put(u, m);
						}
						if(m.get(title) == null){
							m.put(title, 0);
							System.out.println(u+":"+title);
						}
						m.put(title, m.get(title)+1);
						return;
					}
				}
			}
		}
	}
	
	private String loadParas() {
		StringBuilder sb = new StringBuilder("| grep -E '(");
		int count = 0;
		for(String u:userNames){
			if(count ++ ==0)sb.append(u);
			else sb.append("|").append(u);
		}
		sb.append(")'");
		count =0;
		sb.append("| grep -E '(");
		for(String u:titles){
			if(count ++ ==0)sb.append(u);
			else sb.append("|").append(u);
		}
		sb.append(")'");
		return sb.toString();
	}


	public boolean isSuccess() {
		return isSuccess;
	}


	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}

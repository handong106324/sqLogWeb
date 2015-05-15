package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.sq.shell.connection.ConnectionFactory;
import com.sq.utils.PropertiesUtils;

public class DownBillingCenterData {

	public static void main(String[] args){
		init();
	}
	
	private static String jdbcFile = "jdbc.properties";
	private static void init() {
		String key = PropertiesUtils.getPropertiesValue(jdbcFile, "shell_key");
		String userName = PropertiesUtils.getPropertiesValue(jdbcFile,
				"shell_username");
		String pwd = PropertiesUtils.getPropertiesValue(jdbcFile,
				"shell_password");
		ConnectionFactory.getInstance().init(key, userName, pwd,true);
		toServer("117.121.17.32", "22201", "tianxiawushuang");
		toServer("117.121.17.32", "22201", "wulinzhizun");
		toServer("117.121.17.11", "22201", "xiaoaowulin");
		toServer("117.121.17.11", "22201", "fengqiyunyong");
		toServer("117.121.17.11", "22201", "wohucanglong");
	}
	
	public static void toServer(String ip ,String port,String servername){
		getResultByShellSych("zcat /data2/3dgame_log/"+servername+"/3d_log_logic/3dgame/billingCenter.log.2014-10-1[89].log.gz | grep 私银和银子 | grep -E '用户充值|用户消费' |grep 成功  > to_tao_log/"+servername+"_2014_10_si_yin.log",ip, port);
		getResultByShellSych("zcat /data2/3dgame_log/"+servername+"/3d_log_logic/3dgame/billingCenter.log.2014-10-2*.log.gz    | grep 私银和银子 | grep -E '用户充值|用户消费' |grep 成功 >  to_tao_log/"+servername+"_2014_2a_si_yin.log",ip, port);
		getResultByShellSych("zcat /data2/3dgame_log/"+servername+"/3d_log_logic/3dgame/billingCenter.log.2014-10-3*.log.gz    | grep 私银和银子 | grep -E '用户充值|用户消费' |grep 成功 >  to_tao_log/"+servername+"_2014_3a_si_yin.log",ip, port);
		getResultByShellSych("zcat /data2/3dgame_log/"+servername+"/3d_log_logic/3dgame/billingCenter.log.2014-11-0[1234].log.gz | grep 私银和银子 | grep -E '用户充值|用户消费' |grep 成功  > to_tao_log/"+servername+"_2014_11_si_yin.log",ip, port);

	}
//	public static void toServer(String ip ,String port,String servername){
//		getResultByShellSych("zcat /data2/3dgame_log/"+servername+"/3d_log_logic/3dgame/billingCenter.log.2014-10-1[89].log.gz | grep 官银| grep -E '用户充值|用户消费' |grep 成功  > to_tao_log/"+servername+"_2014_10.log",ip, port);
//		getResultByShellSych("zcat /data2/3dgame_log/"+servername+"/3d_log_logic/3dgame/billingCenter.log.2014-10-2*.log.gz    | grep 官银| grep -E '用户充值|用户消费' |grep 成功 >  to_tao_log/"+servername+"_2014_2a.log",ip, port);
//		getResultByShellSych("zcat /data2/3dgame_log/"+servername+"/3d_log_logic/3dgame/billingCenter.log.2014-10-3*.log.gz    | grep 官银| grep -E '用户充值|用户消费' |grep 成功 >  to_tao_log/"+servername+"_2014_3a.log",ip, port);
//		getResultByShellSych("zcat /data2/3dgame_log/"+servername+"/3d_log_logic/3dgame/billingCenter.log.2014-11-0[1234].log.gz | grep 官银| grep -E '用户充值|用户消费' |grep 成功  > to_tao_log/"+servername+"_2014_11.log",ip, port);
//
//	}
	
	public static void getResultByShellSych(String cmdCat, String ip,String port) {
		Session sess = null;
		try {
			long start = System.currentTimeMillis();
			sess = ConnectionFactory.getInstance().getConn(ip, port).openSession();
			sess.execCommand(cmdCat);
			InputStream stdout = new StreamGobbler(sess.getStdout());
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout, "UTF-8"));
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				System.out.println(line);
				
			}
			System.out.println(System.currentTimeMillis() - start);
		} catch (IOException e) {
			e.getStackTrace();
		} finally {
			if (sess != null)
				sess.close();
		}
	}
}

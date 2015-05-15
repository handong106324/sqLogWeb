package com.sq.shell.connection;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ch.ethz.ssh2.Connection;

import com.jfinal.kit.PathKit;

public class ConnectionFactory {

	private static ConnectionFactory instance;
	private String key;
	private String userName;
	private String pwd;
	private long lastGetConnTime;
	private boolean isByKey ;
	private Map<String,Connection> connMap = new HashMap<String,Connection>();
	
	public void init(String key , String userName,String pwd,boolean isByKey){
		this.key = key;
		this.userName = userName;
		this.pwd = pwd;
		this.isByKey = isByKey;
	}
	
	public Connection getConn(String ip,String port) {
		long start = System.currentTimeMillis();
		//TEST  TAIWAN
//		ip = ip.replace("10.20.41", "210.242.234");
//		port  = 36000+"";
		Connection c = connMap.get(ip+"-"+port);
		if(c != null){
			lastGetConnTime = System.currentTimeMillis();
			return c;
		}
		File keyfile = new File(key);
		if(!keyfile.exists()){
			keyfile = new File(PathKit.getWebRootPath() + File.separator + "WEB-INF" + File.separator+"classes"+File.separator+"pingtai");
		}
		Connection conn = new Connection(ip, Integer.parseInt(port));
		try {
			System.out.println("连接SSH2-----");
			conn.connect();
			boolean isAuthenticated = false;
				if(!isByKey){
					isAuthenticated = conn.authenticateWithPassword(userName, pwd);
				}else {
					isAuthenticated = conn.authenticateWithPublicKey(userName,keyfile, pwd);
				}
			if (isAuthenticated == false)
				throw new IOException("Authentication failed.");
			connMap.put(ip+"-"+port, conn);
			System.out.println("获取连接时间="+(System.currentTimeMillis() - start)+" 上次获取连接时间："+new Date(lastGetConnTime));
			return conn;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ConnectionFactory getInstance() {
		if (instance == null) {
			instance = new ConnectionFactory();
		}
		return instance;
	}

}

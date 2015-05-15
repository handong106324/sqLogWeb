package com.sq.plugin;

import java.io.File;
import java.io.IOException;

import ch.ethz.ssh2.Connection;

import com.jfinal.plugin.IPlugin;
import com.sq.utils.PropertiesUtils;

public class ShellPlugin implements IPlugin {
	String jdbcFile = "jdbc.properties";
	private Connection conn;
	public boolean start(){
		String ip = PropertiesUtils.getPropertiesValue(jdbcFile, "shell_ip");
		String key = PropertiesUtils.getPropertiesValue(jdbcFile, "shell_key");
		String userName = PropertiesUtils.getPropertiesValue(jdbcFile, "shell_username");
		String pwd = PropertiesUtils.getPropertiesValue(jdbcFile, "shell_password");
		int port = Integer.parseInt(PropertiesUtils.getPropertiesValue(jdbcFile, "shell_port"));
		File keyfile = new File(key);
		conn = new Connection(ip,port);
		try {
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPublicKey(userName, keyfile, pwd);
			if (isAuthenticated == false)
				throw new IOException("Authentication failed.");
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		return true;
	}
	
	/**
	 * @return the conn
	 */
	public Connection getConn() {
		return conn;
	}

	public boolean stop() {
		return true;
	}

}

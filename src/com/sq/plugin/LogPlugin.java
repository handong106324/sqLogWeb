package com.sq.plugin;

import com.jfinal.plugin.IPlugin;
import com.sq.utils.PropertiesUtils;
import com.sq.whcl.transport.webclient.WebClient;
import com.xuanzhi.tools.transport2.DefaultConnectionSelector2;

public class LogPlugin implements IPlugin {
	
	public boolean start(){
		DefaultConnectionSelector2 selector = new DefaultConnectionSelector2();
		String ip = PropertiesUtils.getPropertiesValue("jdbc.properties", "MSG_SERVER_IP");
		selector.setHost(ip);
		selector.setPort(20014);
		selector.setClientModel(true);
		selector.setName("LogSystemClient");
		WebClient wc = new WebClient();
		try {
			wc.initialize();
			selector.init();
			wc.setConnectionSelector(selector);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

	public boolean stop() {
		return true;
	}

}

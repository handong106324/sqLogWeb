package com.sq.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.sq.base.BaseAction;
import com.sq.base.SqageActionInterface;
import com.sq.common.Constant;
import com.sq.entity.LogFileInfo;
import com.sq.model.FileInfo;
import com.sq.model.ShellInfo;
import com.sq.shell.ShellFactory;
@SqageActionInterface(path="peizhi")
public class SetAction extends BaseAction{

	/**
	 * 为新增服务器 添加配置
	 */
	@Before(Tx.class)
	public void add(){
		String game = getPara("gameName");
		String servername = getPara("serverName");
		String sourceServerPort = getPara("srcServer");
		String dir = getPara("dir");
		LogFileInfo fiTemp = LogFileInfo.dao.findFirst("select * from file_info where serverName='"+servername+"'");
		if(fiTemp != null){
			renderJson("result","不能重复配置");
			return;
		}
		LogFileInfo fileInfo = LogFileInfo.dao.findFirst("select * from file_info where port = " + sourceServerPort +" order by id");
//		
		if(fileInfo == null){
			fileInfo = LogFileInfo.dao.findFirst("select * from file_info where port = 30011 order by id");
		}
		List<LogFileInfo> infos = LogFileInfo.dao.findPart("gameName='"+game +"' and serverName='"+fileInfo.getStr("serverName")+"'");
		for(LogFileInfo lfi : infos){
			LogFileInfo fi = new LogFileInfo();
			fi.set("fileName", lfi.getStr("fileName"));
			fi.set("type", lfi.getStr("type"));
			fi.set("gameName", game);
			fi.set("queryWhere", lfi.getStr("queryWhere"));
			fi.set("queryType", lfi.get("queryType"));
			fi.set("ip", lfi.getStr("ip"));
			fi.set("port", sourceServerPort);//lfi.getStr("port"));
			fi.set("webIp", lfi.get("webIp"));
			fi.set("webPort", lfi.get("webPort"));
			fi.set("recordType", lfi.get("recordType"));
			fi.set("resultHandleType", lfi.get("resultHandleType"));
			fi.set("serverName", servername);
			fi.set("dir", dir+"/3d_log_*/3dgame/");//
			fi.save();
		}
		renderJson("result","success");
	}
	
	/**
	 * 添加一个类型
	 */
	public void addType(){
		String game = getPara("gameName");
		String type = getPara("type");
		String fileName = getPara("fileName");
		String queryWhere = getPara("queryWhere");
		List<LogFileInfo> infos = LogFileInfo.dao.findPart("gameName='"+game+"' and type='物品'");
		for(LogFileInfo lfi : infos){
			LogFileInfo fi = new LogFileInfo();
			fi.set("fileName", fileName);
			fi.set("type", type);
			fi.set("gameName", game);
			fi.set("queryWhere", queryWhere);
			fi.set("queryType", lfi.get("queryType"));
			fi.set("ip", lfi.get("ip"));
			fi.set("port", lfi.get("port"));
			fi.set("serverName", lfi.get("serverName"));
			fi.set("dir", lfi.get("dir"));
			fi.save();
		}
		renderJson("data","success");
	}
	
	public  void showAdd(){
		render("addPeizhi.jsp");
	}
	
	public void list(){
		String server = getPara("serverName");
		String game = getPara("game");
		String func = getPara("type");
		if(StringUtils.isBlank(server) && StringUtils.isBlank(game) && StringUtils.isBlank(func)){
			return;
		}
	}
	
	public void copyPeizhi(){
		String[] logServers = new String[]{""};
	}
	
	public void getNewDir(){
		String port = getPara("port");
		String basePath = getPara("basePath");
		ShellInfo shellInfo = new ShellInfo();
		if(Constant.isHaiwai){
			shellInfo.setIp("210.242.234.87");
		}else {
			shellInfo.setIp("117.121.22.23");
		}
		shellInfo.setPort(port);
		shellInfo.setShell(true);
		List<FileInfo> fileInfos = ShellFactory.getInstance().checkFileExsit(basePath, shellInfo).getFiles();
		Map map = new HashMap();
		List<String> names = new ArrayList<String>();
		for(FileInfo fi : fileInfos){
			if(fi.getFileName() != null)names.add(fi.getFileName());
		}
		map.put("result", "success");
		map.put("datas", names);
		renderJson("result",map);
	}
}

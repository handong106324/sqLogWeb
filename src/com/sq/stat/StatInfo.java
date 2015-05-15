package com.sq.stat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatInfo {

	private static List<String> useChatList =Collections.synchronizedList(new ArrayList<String>());
	
//	private static Map<String,String> useChatMap = new HashMap<String,String>();
//	private static Map<String,ShellExecuteInfo> executeShellInfoMap = new HashMap<String,ShellExecuteInfo>();
	
	public static void addChatUser(String userName){
		if(!useChatList.contains(userName)){
			useChatList.add(userName);
		}
	}
	
	public static void removeChatUser(String userName){
		useChatList.remove(userName);
	}
	
	public static String getChatUsers(){
		String res = "正在使用聊天监控的人员：";
		for(String us:useChatList){
			res +="["+us+"]";
		}
		return res;
	}
	
}

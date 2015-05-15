package com.sq.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.sq.entity.User;
import com.sq.shell.currency.EmailThread;

public class MyEmailUtils {

	private static Logger logger = Logger.getLogger(MyEmailUtils.class);
	
	public static final int NOTICE_TYPE_FLOW_TO_CHECK = 0 ;
	public static final int NOTICE_TYPE_FLOW_CHECK_OK = 1;
	public static final int NOTICE_TYPE_FLOW_CHECK_REJECT = 2;
	
	public static final int NOTICE_TYPE_GUAQI = 3 ;
	public static final int NOTICE_FLOW_START = 4;
	public static final int NOTICE_OPTION_CHANGE = 5;

	public static final int NOTICE_FLOW_TASK_TO_DO = 6;

	public static final int FLOW_PRESS_TO_DO = 7;
	
	/**
	 * User:目标人
	 * FlowInstance
	 */
	
	public static void sendEmail(String email, String title,String ccAddresss, String content) {
		if (checkEmail(email)) {
			logInfo("准备向"+email+","+ccAddresss+" 发送信息："+content);
			new EmailThread(email, title,ccAddresss, content).start();
		}
	}

	private static boolean checkEmail(String email) {
		if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
			return false;
		}
		return true;
	}


	private static void  logInfo(String log){
		if(logger.isInfoEnabled()){
			logger.info(log);
		}
	}
	public static void main(String[] args){
		MyEmailUtils.sendEmail("handong@sqage.com", "dddddddd", "", "dddddddd");
	}
}

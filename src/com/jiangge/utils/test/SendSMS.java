package com.jiangge.utils.test;

import java.util.Map;

public class SendSMS {
	/**
	 * ��ָ���ֻ��ŷ��Ͷ���
	 * @param phone
	 * @param content
	 */
	public void send(Map<String, String> taskParam){
		/**��ȡ�ֻ��źͷ��͵�����**/
		String phone = taskParam.get("phone");
		String content = taskParam.get("content");
		System.out.println("���ֻ���:" + phone + "���Ͷ���,������:" + content);
	}
}

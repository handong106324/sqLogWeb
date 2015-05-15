package com.jiangge.utils;

import java.util.List;

import com.jiangge.utils.mail.EmailHandle;

/**
 * �ʼ����͹�����
 * @author jiang.li
 * @date 2013-12-19 14:27
 */
public class EmailUtils {
	

	/**
	 * �����ʼ�
	 * @param smtp        �ʼ�Э��
	 * @param fromAddress �����˵�ַ
	 * @param fromPass    ����������
	 * @param toaddress   �ռ��˵�ַ
	 * @param subject     ��������
	 * @param content     ��������
	 * @throws Exception
	 */
	public static void send(String smtp,String fromAddress,String fromPass,String toAddress, String subject, String content){
		try{
			System.out.println("��ʼ��" + toAddress + "�����ʼ�");
			EmailHandle emailHandle = new EmailHandle(smtp);
			emailHandle.setFrom(fromAddress);
			emailHandle.setNeedAuth(true);
			emailHandle.setSubject(subject);
			emailHandle.setBody(content);
			emailHandle.setTo(toAddress);
			emailHandle.setFrom(fromAddress);
			emailHandle.setNamePass(fromAddress, fromPass);
			emailHandle.sendEmail();
			System.out.println("�ʼ����ͽ���!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * �����ʼ�
	 * @param smtp        �ʼ�Э��
	 * @param fromAddress �����˵�ַ
	 * @param fromPass    ����������
	 * @param toAddress   �ռ��˵�ַ
	 * @param ccAdress    �����˵�ַ
	 * @param subject     ��������
	 * @param content     ��������
	 * @throws Exception
	 */
	public static void send(String smtp,String fromAddress,String fromPass,String toAddress,String ccAdress, String subject, String content){
		try{
			System.out.println("��ʼ��" + toAddress + "�����ʼ�");
			EmailHandle emailHandle = new EmailHandle(smtp);
			emailHandle.setFrom(fromAddress);
			emailHandle.setNeedAuth(true);
			emailHandle.setSubject(subject);
			emailHandle.setBody(content);
			emailHandle.setTo(toAddress);
			/**��ӳ���**/
			if(StringUtils.isNotEmpty(ccAdress)){
				emailHandle.setCopyTo(ccAdress);
			}
			emailHandle.setFrom(fromAddress);
			emailHandle.setNamePass(fromAddress, fromPass);
			emailHandle.sendEmail();
			System.out.println("�ʼ����ͽ���!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * �����ʼ�
	 * @param smtp        �ʼ�Э��
	 * @param fromAddress �����˵�ַ
	 * @param fromPass    ����������
	 * @param toaddress   �ռ��˵�ַ
	 * @param subject     ��������
	 * @param content     ��������
	 * @throws Exception
	 */
	public static void send(String smtp,String fromAddress,String fromPass,String toAddress,String subject, String content,List<String> fileList){
		try{
			System.out.println("��ʼ��" + toAddress + "�����ʼ�");
			EmailHandle emailHandle = new EmailHandle(smtp);
			emailHandle.setFrom(fromAddress);
			emailHandle.setNeedAuth(true);
			emailHandle.setSubject(subject);
			emailHandle.setBody(content);
			emailHandle.setTo(toAddress);
			emailHandle.setFrom(fromAddress);
			emailHandle.setNamePass(fromAddress, fromPass);
			/** �����ļ�·�� **/
			for(String file : fileList){
				emailHandle.addFileAffix(file);
			}
			emailHandle.sendEmail();
			System.out.println("�ʼ����ͽ���!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * �����ʼ�
	 * @param smtp        �ʼ�Э��
	 * @param fromAddress �����˵�ַ
	 * @param fromPass    ����������
	 * @param toAddress   �ռ��˵�ַ
	 * @param ccAdress    �����˵�ַ
	 * @param subject     ��������
	 * @param content     ��������
	 * @throws Exception
	 */
	public static void send(String smtp,String fromAddress,String fromPass,String toAddress,String ccAdress,String subject, String content,List<String> fileList){
		try{
			System.out.println("��ʼ��" + toAddress + "�����ʼ�");
			EmailHandle emailHandle = new EmailHandle(smtp);
			emailHandle.setFrom(fromAddress);
			emailHandle.setNeedAuth(true);
			emailHandle.setSubject(subject);
			emailHandle.setBody(content);
			emailHandle.setTo(toAddress);
			/**��ӳ���**/
			if(StringUtils.isNotEmpty(ccAdress)){
				emailHandle.setCopyTo(ccAdress);
			}
			emailHandle.setFrom(fromAddress);
			emailHandle.setNamePass(fromAddress, fromPass);
			/** �����ļ�·�� **/
			for(String file : fileList){
				emailHandle.addFileAffix(file);
			}
			emailHandle.sendEmail();
			System.out.println("�ʼ����ͽ���!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
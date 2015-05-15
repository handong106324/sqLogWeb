package com.sq.plugin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jfinal.plugin.IPlugin;
import com.sq.mail.MailSenderInfo;
import com.sq.mail.MyAuthenticator;

public class MailPlugin implements IPlugin {
	
	Logger logger = Logger.getLogger(MailPlugin.class);

	private static final int SLEEP_TIME=30000;
	private BlockingQueue<MailSenderInfo> queue = new ArrayBlockingQueue<MailSenderInfo>(10000);
	public static MailPlugin instance;
	public boolean start() {
		instance = this;
		ResourceBundle mailConfig = ResourceBundle.getBundle("mail_config",Locale.SIMPLIFIED_CHINESE);
		// 读取配置文件，sendmail是否发送邮件
		sendmail = "true".equals(mailConfig.getString("issendmail")) ? true : false;
		mailServerHost = mailConfig.getString("mailServerHost");
		mailServerPort = mailConfig.getString("mailServerPort");
		validate	=	mailConfig.getString("validate");
		fromAddress	=	mailConfig.getString("fromAddress");
		toAddress	=	mailConfig.getString("toAddress");
//		ccAddress	=	mailConfig.getString("ccAddress");
		userName	=	mailConfig.getString("userName");
		password	=	mailConfig.getString("password");		
		properties = new Properties();
		properties.put("mail.smtp.host", this.mailServerHost);    
		properties.put("mail.smtp.port", this.mailServerPort);    
		properties.put("mail.smtp.auth", validate);
		logger.info("====================邮件配置信息 开始=====================");
		logger.info("mailServerHost="+mailServerHost);
		logger.info("mailServerPort="+mailServerPort);
		logger.info("validate="+validate);
		logger.info("fromAddress="+fromAddress);
		logger.info("toAddress="+toAddress);
		logger.info("userName="+userName);
		logger.info("====================邮件配置信息 结束=====================");
		if(sendmail){
			Thread thread = new Thread(new HandlerThread());
			thread.start();
		}
		return true;
	}

	public boolean stop() {
		return false;
	}
	class HandlerThread implements Runnable{
		List<MailSenderInfo> list = new ArrayList<MailSenderInfo>();
		
		public void run() {
			while(true){
				try{
					if(list != null && list.size() > 0){
						logger.info("====================发送邮件数量："+list.size());
						sendMailHandler(list);
						list.clear();
					}else{
						Thread.sleep(SLEEP_TIME);
					}					
					while(!queue.isEmpty()){
						list.add(queue.poll(1,TimeUnit.SECONDS));
					}					
				}catch(Exception e){
					logger.error("", e);
//					if(list!=null &&list.size()>0)
//						list.remove(list.size()-1);
					try {
						Thread.sleep(SLEEP_TIME);
					} catch (InterruptedException e1) {
						
					}
				}
			}
		}

	}
	
	/**
	 * 将要发送的邮件放入队列
	 * @param mailInfo
	 * @return void
	 */
	public void sendHtmlMail(MailSenderInfo mailInfo){
		queue.add(mailInfo);		
	}
	
	/**
	 * 发送邮件
	 * @param list
	 * @return
	 */
	public void sendMailHandler(List<MailSenderInfo> list){
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				if(!sendMail(list.get(i))){
					queue.add(list.get(i));
				}
			}
		}		
	}
	
	/**   
	  * 以文本格式发送邮件   
	  * @param mailInfo 待发送的邮件的信息   
	  */    
	private boolean sendTextMail(MailSenderInfo mailInfo) {
		if (!sendmail||mailInfo==null)
			return false;
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		
		if(StringUtils.isNotBlank(mailInfo.getToAddress()))
				toAddress=mailInfo.getToAddress();
		if(StringUtils.isNotBlank(mailInfo.getCcAddress()))
			ccAddress=mailInfo.getCcAddress();
		if ("true".equals(validate)) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(userName, password);
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(properties,
				authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(fromAddress);
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			InternetAddress[] toList = new InternetAddress().parse(toAddress);
			InternetAddress[] ccList =null;
			if(StringUtils.isNotBlank(ccAddress))
				 new InternetAddress().parse(ccAddress);
			// Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipients(Message.RecipientType.TO, toList);
			if(ccList!=null)
				mailMessage.setRecipients(Message.RecipientType.CC, ccList);
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// 设置邮件消息的主要内容
			String mailContent = mailInfo.getContent();
			mailMessage.setText(mailContent);
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			logger.error("以文本格式发送邮件", ex);
			ex.printStackTrace();
		}
		return false;
	}
  
  /**   
    * 以HTML格式发送邮件   
    * @param mailInfo 待发送的邮件信息   
    */    
	public boolean sendMail(MailSenderInfo mailInfo) {
		if (!sendmail ||mailInfo==null)
			return false;
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		if(StringUtils.isNotBlank(mailInfo.getToAddress()))
			toAddress=mailInfo.getToAddress();
		if(StringUtils.isNotBlank(mailInfo.getCcAddress()))
		ccAddress=mailInfo.getCcAddress();
		// 如果需要身份认证，则创建一个密码验证器
		if ("true".equals(validate)) {
			authenticator = new MyAuthenticator(userName, password);
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(properties,
				authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(fromAddress);
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			// Message.RecipientType.TO属性表示接收者的类型为TO
			InternetAddress[] toList = new InternetAddress().parse(toAddress);
			InternetAddress[] ccList =null;
			if(StringUtils.isNotBlank(ccAddress))
				 new InternetAddress().parse(ccAddress);
			// Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipients(Message.RecipientType.TO, toList);
			if(ccList!=null)
				mailMessage.setRecipients(Message.RecipientType.CC, ccList);
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			logger.error("以HTML格式发送邮件", ex);
			ex.printStackTrace();
		}
		return false;
	}   
	
	private Properties properties;
	private String mailServerHost;    
	private String mailServerPort;    
	// 邮件发送者的地址    
	private String fromAddress ;    
	// 邮件接收者的地址    
	private String toAddress;    
	// 抄送的地址    
	private String ccAddress;    
	// 登陆邮件发送服务器的用户名和密码    
	private String userName ;    
	private String password ;    
	// 是否需要身份验证    
	private String validate ;    
	// 是
	private boolean sendmail ;
	
	/**
	 * 是否允许发送邮件
	 * @return
	 */
	public boolean isSendmail() {
		return sendmail;
	}
	private void setSendmail(boolean sendmail) {
		this.sendmail = sendmail;
	}

}

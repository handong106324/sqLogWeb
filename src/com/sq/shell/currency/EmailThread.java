package com.sq.shell.currency;

import com.sq.mail.MailSenderInfo;
import com.sq.plugin.MailPlugin;

public class EmailThread extends Thread {
	private String email;
	private String content;
	private String title;
	private String ccAddress;
	public EmailThread(String email,String title,String ccAddress,String content){
		this.email = email;
		this.content = content;
		this.title = title;
		this.ccAddress = ccAddress;
	}
	@Override
	public void run() {
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setToAddress(email);
		mailInfo.setCcAddress(ccAddress);
		mailInfo.setSubject(title);
		mailInfo.setContent(content);		
		MailPlugin.instance.sendHtmlMail(mailInfo);
	}
}

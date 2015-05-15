package com.sq.mail;
public class MailSenderInfo {    
    
    // 邮件主题    
    private String subject ;    
    // 邮件的文本内容    
    private String content;    
    // 邮件附件的文件名    
    private String[] attachFileNames;      
    
    /**
     * 接收邮件人地址
     */
    private String toAddress;
    /**
     * 抄送邮件人地址
     */
    private String ccAddress;
    public String[] getAttachFileNames() {    
      return attachFileNames;    
    }   
   
    public void setAttachFileNames(String[] fileNames) {    
      this.attachFileNames = fileNames;    
    }   
    /**
     * 邮件标题
     * @return
     */
    public String getSubject() {    
      return subject;    
    }   
    /**
     * 邮件标题
     * @param subject
     */
    public void setSubject(String subject) {    
      this.subject = subject;    
    }   
    /**
     * 邮件正文
     * @return
     */
    public String getContent() {    
      return content;    
    }   
    /**
     * 邮件正文
     * @param textContent
     */
    public void setContent(String textContent) {    
      this.content = textContent;    
    }
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getCcAddress() {
		return ccAddress;
	}
	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}    

    
    /**
	 * 获取发送邮件样式
	 * @return
	 */
	public static String getMailStyle(){
		StringBuffer tabStyle = new StringBuffer();
		tabStyle.append("<style>");
		tabStyle.append("table {border:1px solid #fff;}");
		tabStyle.append("th {background:#328aa4;}");
		tabStyle.append("td {background:#e5f1f4;}");
		tabStyle.append("</style>");
		return tabStyle.toString();
	}
}   

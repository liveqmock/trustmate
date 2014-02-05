/* 
 * Project Name : 
 * Project      : 
 * File Name    : com.pgmate.sms.conf.SMSConfigBean.java
 * Date	        : Jun 24, 2008
 * Version      : 2.0
 * Author       : ginaida@ginaida.net
 * Comment      :  
 */

package com.pgmate.sms.conf;

public class SMSConfigBean {
	
	private String serverIp		= "";
	private String serverPort	= "";
	private String timeout		= "";
	private String source		= "";
	private String sendTelNo	= "";

	public SMSConfigBean() {
		// TODO Auto-generated constructor stub
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSendTelNo() {
		return sendTelNo;
	}

	public void setSendTelNo(String sendTelNo) {
		this.sendTelNo = sendTelNo;
	}
	
	

}

/* 
 * Project Name : 
 * Project      : 
 * File Name    : net.kttrust.model.sms.SMSBean.java
 * Date	        : Jun 24, 2008
 * Version      : 2.0
 * Author       : ginaida@ginaida.net
 * Comment      :  
 */

package com.pgmate.sms.model;

public class SMSBean implements java.io.Serializable{

	private String recvTelNo	= "";
	private String sendTelNo	= "";
	private String message		= "";
	
	
	public SMSBean(){
		
	}


	public String getRecvTelNo() {
		return recvTelNo;
	}


	public void setRecvTelNo(String recvTelNo) {
		this.recvTelNo = recvTelNo;
	}


	public String getSendTelNo() {
		return sendTelNo;
	}


	public void setSendTelNo(String sendTelNo) {
		this.sendTelNo = sendTelNo;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	

}

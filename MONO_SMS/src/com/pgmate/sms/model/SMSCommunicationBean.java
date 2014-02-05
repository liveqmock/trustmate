/* 
 * Project Name : KTT-PGVersion II
 * Project      : PGv2_DDL
 * File Name    : net.kttrust.model.sms.SMSCommunicationBean.java
 * Date	        : Jun 23, 2008
 * Version      : 2.0
 * Author       : ginaida@ginaida.net
 * Comment      :  
 */

package com.pgmate.sms.model;

import biz.trustnet.common.util.CommonUtil;

public class SMSCommunicationBean implements java.io.Serializable{

	private String source 		= "";
	private String state		= "";	//요청 : 11,실패 : 99 , 성공 :00
	private String sendTelNo	= "";
	private String recvTelNo	= "";
	private String message		= "";
	
	public SMSCommunicationBean(){
		
	}
	
	public SMSCommunicationBean(String transaction) {
		this(transaction.getBytes());
	}
	
	public SMSCommunicationBean(byte[] transaction) {
		source 		= CommonUtil.toString(transaction,0,10).trim();
		state		= CommonUtil.toString(transaction,10,2).trim();
		sendTelNo	= CommonUtil.toString(transaction,12,15).trim();
		recvTelNo	= CommonUtil.toString(transaction,27,123).trim();
		message 	= CommonUtil.toString(transaction,150,80).trim();
	}
	
	public String getTransaction(){
		StringBuffer sb = new StringBuffer();
		sb.append(CommonUtil.byteFiller(source, 10));
		sb.append(CommonUtil.byteFiller(state, 2));
		sb.append(CommonUtil.byteFiller(sendTelNo, 15));
		sb.append(CommonUtil.byteFiller(recvTelNo, 123));
		sb.append(CommonUtil.byteFiller(message, 80));
		
		return sb.toString();
	}

	public String getSource() {
		return source;
	}



	public void setSource(String source) {
		this.source = source;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getSendTelNo() {
		return sendTelNo;
	}



	public void setSendTelNo(String sendTelNo) {
		this.sendTelNo = sendTelNo;
	}
	
	public void setRecvTelNo(String recvTelNo){
		this.recvTelNo = recvTelNo;
	}

	public void setRecvTelNo(String[] recvTelNo){
		for(int i=0;i<recvTelNo.length;i++){
			if(recvTelNo.length != i+1){
				this.recvTelNo += recvTelNo[i]+":";
			}else{
				this.recvTelNo += recvTelNo[i];
			}
		}
	}
	
	public String[] getRecvTelNo(){
		if(this.recvTelNo.indexOf(":") > -1){
			return CommonUtil.split(recvTelNo, ":", true);
		}else{
			String[] telNo = new String[1];
			telNo[0] = recvTelNo;
			return telNo;
		}
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
}

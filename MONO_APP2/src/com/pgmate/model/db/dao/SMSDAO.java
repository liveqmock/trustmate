/*
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.SMSDAO.java
 * Date	        : Jan 27, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :
 */

package com.pgmate.model.db.dao;

import biz.trustnet.common.log.Log;

import com.pgmate.sms.client.SMSClient;

public class SMSDAO {


	public SMSDAO(){
	}
	
	public void adminSMS(String message,String sendTelNo){
		String[] recvTelNo = new MemberDAO().getBySmsNotice("ADMIN");
		send(recvTelNo,sendTelNo,message);
	}
	
	public void systemSMS(String message,String sendTelNo){
		String[] recvTelNo = new MemberDAO().getBySmsNotice("SYSTEM");
		send(recvTelNo,sendTelNo,message);
	}
	

	public void adminSMS(String message){
		String[] recvTelNo = new MemberDAO().getBySmsNotice("ADMIN");
		send(recvTelNo,"",message);
	}
	

	public void systemSMS(String message){
		String[] recvTelNo = new MemberDAO().getBySmsNotice("SYSTEM");
		send(recvTelNo,"",message);
	}
	
	public void send(String[] recvTelNo,String sendTelNo,String message){
		try{
			SMSClient client = new SMSClient();
			client.sendSMS(recvTelNo,sendTelNo, message);
		}catch(Exception e){
			Log.debug("log.day","SMS ERROR ="+e.getMessage(),this);
		}
	}

	



}

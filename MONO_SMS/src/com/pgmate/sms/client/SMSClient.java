/* 
 * Project Name : 
 * Project      : 
 * File Name    : com.pgmate.sms.client.SMSClient.java
 * Date	        : Jun 24, 2008
 * Version      : 2.0
 * Author       : ginaida@ginaida.net
 * Comment      :  
 */

package com.pgmate.sms.client;

import biz.trustnet.common.comm.client.SocketManager;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;
import biz.trustnet.common.xml.XMLFactory;

import com.pgmate.sms.conf.SMSConfigBean;
import com.pgmate.sms.model.SMSBean;
import com.pgmate.sms.model.SMSCommunicationBean;

public class SMSClient {

	private SMSConfigBean configBean = null;
	private static int SPEC_LEN = 230;
	
	public SMSClient(){
		this.configBean = (SMSConfigBean)XMLFactory.getEntity("SMS");
	}
	
	public SMSClient(SMSConfigBean configBean){
		this.configBean = configBean;
	}
	
	public SMSClient(String serverIp,String serverPort){
		configBean = new SMSConfigBean();	
		configBean.setServerIp(serverIp);
		configBean.setServerPort(serverPort);
		configBean.setTimeout("30000");
	} 
	
	public boolean sendSMS(String recvTelNo,String sendTelNo,String message){
		SMSBean smsBean = new SMSBean();
		smsBean.setMessage(message);
		smsBean.setRecvTelNo(recvTelNo);
		smsBean.setSendTelNo(sendTelNo);
		
		return sendSMS(smsBean);
	}
	
	
	public boolean sendSMS(String[] recvTelNo,String sendTelNo,String message){
		SMSCommunicationBean smsCommBean = new SMSCommunicationBean();
		if(smsCommBean.getSource().equals("")){
			smsCommBean.setSource(configBean.getSource());
		}
		smsCommBean.setRecvTelNo(recvTelNo);
		if(sendTelNo.equals("")){
			smsCommBean.setSendTelNo(configBean.getSendTelNo());
		}else{
			smsCommBean.setSendTelNo(sendTelNo);
		}
		smsCommBean.setMessage(message);
		smsCommBean.setState("11");
		return sendSMS(smsCommBean);
	}
	
	public boolean sendSMS(SMSBean smsBean){
		SMSCommunicationBean smsCommBean = new SMSCommunicationBean();
		if(smsCommBean.getSource().equals("")){
			smsCommBean.setSource(configBean.getSource());
		}
		smsCommBean.setRecvTelNo(smsBean.getRecvTelNo());
		if(smsCommBean.getSendTelNo().equals("")){
			smsCommBean.setSendTelNo(configBean.getSendTelNo());
		}else{
			smsCommBean.setSendTelNo(smsBean.getSendTelNo());
		}
		
		smsCommBean.setMessage(smsBean.getMessage());
		smsCommBean.setState("11");
		return sendSMS(smsCommBean);
	}
	
	public boolean sendSMS(SMSCommunicationBean smsCommBean){
		SocketManager socket = new SocketManager();
		byte[] request 	= (smsCommBean.getTransaction()).getBytes();
		byte[] response = null;
		try{
			socket.setSocketInfo(configBean.getServerIp(),configBean.getServerPort());
			socket.setSocketTimeout(configBean.getTimeout(),configBean.getTimeout());
			response =  socket.connect(request, SPEC_LEN);
			Log.debug("log.day","HOST>>SMS =["+CommonUtil.toString(request)+"]",this);
		}catch(Exception e){
			System.out.println("SMSClient Error="+e.getMessage());
			smsCommBean.setState("99");
			response = (smsCommBean.getTransaction()).getBytes();
		}finally{
			Log.debug("log.day","HOST<<SMS =["+CommonUtil.toString(response)+"]",this);
		}
		
		SMSCommunicationBean smsCommResBean = new SMSCommunicationBean(response);
		if(smsCommResBean.getState().equals("00")){
			return true;
		}else{
			return false;
		}
		
	}
	
	
	
	
	public static void main(String[] args) {
		com.pgmate.sms.client.SMSClient sms = new com.pgmate.sms.client.SMSClient();
		sms.sendSMS(args[0],"0234463730","SMS TEST");

	}

}

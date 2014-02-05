/*
 * Project Name : 
 * Project      : 
 * File Name    : com.pgmate.sms.server.ServiceListener.java
 * Date	        : Jun 24, 2008
 * Version      : 2.0
 * Author       : ginaida@ginaida.net
 * Comment      :
 */

package com.pgmate.sms.server;


import java.net.Socket;

import biz.trustnet.common.comm.client.SocketManager;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.sms.conf.SMSConfigBean;
import com.pgmate.sms.dao.SMSDAO;
import com.pgmate.sms.model.SMSCommunicationBean;

public class ServiceListener {

	private byte[] request      		= null;
	private byte[] response				= null;
	private SocketManager	socket		= null;
	boolean receiveSuccess				= false;
	private static final int SPEC_LEN	= 230;

	public ServiceListener(){
	}


	public ServiceListener(Socket receiveSocket)throws Exception{
		socket = new SocketManager();
		socket.setSocket(receiveSocket);

	}

	public void getRequest(){

		try{

			request = socket.recv(SPEC_LEN);
			if(request.length == SPEC_LEN){
				receiveSuccess 	= true;
			}
		}catch(Exception e){
			Log.debug("log.day","SMS DATA Receive Error= = "+e.getMessage(),this);
		}finally{
			Log.debug("log.day","HOST>>SMS ["+CommonUtil.toString(request)+"]",this);
		}
	}

	public void process(SMSConfigBean configBean){

		getRequest();
		SMSCommunicationBean smsBean = null;
		try{
			if(!receiveSuccess){
				throw new Exception("SMS packet Error : request= "+CommonUtil.toString(request));
			}else{
				smsBean = new SMSCommunicationBean(request);
				if(smsBean.getSendTelNo().trim().equals("")){
					smsBean.setSendTelNo(configBean.getSendTelNo());
				}
				smsBean = new SMSDAO().insertSMS(smsBean);
				response = (smsBean.getTransaction()).getBytes();
				socket.send(response);
			}
		}catch(Exception e){
			Log.debug("log.day","SMS Send Error="+CommonUtil.getExceptionMessage(e),this);
			smsBean.setState("99");
			smsBean.setMessage(e.getMessage());
			try{
				response = smsBean.getTransaction().getBytes();
				socket.send(response);
			}catch(Exception ea){
				Log.debug("log.day","SMS SYSTEM ERROR = "+e.getMessage(),this);
			}
		}finally{
			Log.debug("log.day","HOST<<SMS ["+CommonUtil.toString(response)+"]",this);
			socket.socketClose();
		}

	}


}

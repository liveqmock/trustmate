/* 
 * Project Name : GSI_PROJECT
 * Project      : GSI_PAYMENT
 * File Name    : com.pgmate.payment.server.ServiceListener.java
 * Date	        : Dec 23, 2008
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.server;

import java.net.Socket;

import biz.trustnet.common.comm.client.SocketManager;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.server.conf.ServerConfigBean;

public class ServiceListener {

	private byte[] request      			= null;
	private byte[] response					= null;
	private SocketManager	socket			= null;
	private boolean receiveSuccess			= false;
	private boolean trnsctnSuccess			= false;
	private ServerConfigBean configBean 	= null;
	private int SPEC_LENGTH					= 4;
	
	public ServiceListener(Socket receiveSocket,ServerConfigBean configBean)throws Exception {
		this.configBean = configBean;
		socket = new SocketManager();
		socket.setSocket(receiveSocket);
		
	}
	
	public void getRequest(){
		try{
			byte[] specLength 	= socket.recv(SPEC_LENGTH);
			request	= CommonUtil.byteAppend(specLength,socket.recv(CommonUtil.parseInt(specLength)));
			//Log.debug("log.day","HOST << GSI encrypt["+CommonUtil.toString(encryptedTxt)+"]",this);
			receiveSuccess = true;
		}catch(Exception e){
			Log.debug("log.day","DATA Receive Error = "+e.getMessage(),this);
		}finally{
			Log.debug("log.day","HOST >> PAY ["+CommonUtil.toString(request)+"]",this);
		}
	}
	
	public void process(){
		
		try{
			getRequest();
			if(!receiveSuccess){
				response = request;
			}	
			
			socket.send(response);
			trnsctnSuccess	= true;
		}catch(Exception e){
			Log.debug("log.day","HOST << PAY [RESONOSE ERROR]"+CommonUtil.getExceptionMessage(e),this);
		}finally{
			Log.debug("log.day","HOST << PAY ["+CommonUtil.toString(response)+"]",this);
			
			socket.socketClose();
		}
	}
	
	
	
}

/* 
 * Project      : MONO_GATEWAY
 * File Name    : com.pgmate.gateway.server.ServiceListener.java
 * Date	        : Sep 16, 2008
 * Version      : 2.0
 * Author       : ginaida@trustmate.net
 * Comment      : 
 */

package com.pgmate.gateway.server;

import java.net.Socket;

import biz.trustnet.common.comm.client.SocketManager;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.BeanUtil;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.gateway.cipher.GSICipher;
import com.pgmate.gateway.server.conf.ServerConfigBean;
import com.pgmate.model.comm.HeaderBean;

public class ServiceListener {

	private byte[] request      			= null;
	private byte[] response					= null;
	private SocketManager	socket			= null;
	boolean receiveSuccess					= false;
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
			request				= CommonUtil.byteAppend(specLength,socket.recv(CommonUtil.parseInt(specLength)));
			receiveSuccess = true;
		}catch(Exception e){
			Log.debug("log.day","DATA Receive Error = "+e.getMessage(),this);
		}finally{
			Log.debug("log.day","HOST >> GATE ["+CommonUtil.toString(request)+"]",this);
		}
	}
	
	public void process(){
		try{
			getRequest();
			if(!receiveSuccess){
				response = request;
			}else{
				response = comm(request);
			}
			socket.send(response);
		}catch(Exception e){
			Log.debug("log.day","HOST << GATE [RESONOSE ERROR]"+CommonUtil.getExceptionMessage(e),this);
		}finally{
			Log.debug("log.day","HOST << GATE ["+CommonUtil.toString(response)+"]",this);
			socket.socketClose();
		}
	}
	
	public byte[] comm(byte[] request)throws Exception{
		
		GSICipher cipher	= new GSICipher();
		byte[] encrypt 		= cipher.encrypt(request);
		byte[] decrypt 		= null;
		SocketManager socket = new SocketManager();
		socket.setSocketInfo(configBean.getPwnServerIp(),configBean.getPwnServerPort());
		socket.setSocketTimeout(configBean.getPwnTimeout(),configBean.getPwnTimeout());
		Log.debug("log.day","GATE >> PWN  ["+CommonUtil.toString(encrypt)+"]",this);
		try{
			decrypt = socket.connect3(4, encrypt);
			Log.debug("log.day","GATE << PWN  ["+CommonUtil.toString(decrypt)+"]",this);
			decrypt = cipher.decrypt(decrypt);
		}catch(Exception e){
			Log.debug("log.day","COMMUNICATION ERROR =["+BeanUtil.beanToString(configBean)+"]"+e.getMessage(),this);
			HeaderBean headerBean = new HeaderBean(request);
			headerBean.setResultCd("1");
			headerBean.setResultMsg("X103");
			headerBean.setExtra(e.getMessage());
			decrypt = headerBean.getTransaction(CommonUtil.toString(request,200,request.length-200)).getBytes();
		}
		return decrypt;
	}
}

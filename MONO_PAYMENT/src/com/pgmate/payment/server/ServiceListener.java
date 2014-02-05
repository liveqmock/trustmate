/* 
 * Project Name : GSI_PROJECT
 * Project      : GSI_PAYMENT
 * File Name    : com.pgmate.payment.server.ServiceListener.java
 * Date	        : Dec 23, 2008
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.payment.server;

import java.net.Socket;

import biz.trustnet.common.comm.client.SocketManager;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.comm.HeaderBean;
import com.pgmate.model.db.TrnsctnLogBean;
import com.pgmate.model.db.dao.GSISEQDAO;
import com.pgmate.model.db.dao.TrnsctnLogDAO;
import com.pgmate.payment.conf.ServerConfigBean;
import com.pgmate.payment.main.Payment;
import com.pgmate.payment.util.GSICipher;

public class ServiceListener {

	private byte[] request      			= null;
	private byte[] response					= null;
	private SocketManager	socket			= null;
	private boolean receiveSuccess			= false;
	private boolean trnsctnSuccess			= false;
	private ServerConfigBean configBean 	= null;
	private int SPEC_LENGTH					= 4;
	private GSICipher cipher				= null;
	private TrnsctnLogBean trnsctnLogBean	= null;
	private TrnsctnLogDAO trnsctnLogDAO		= null;
	
	public ServiceListener(Socket receiveSocket,ServerConfigBean configBean)throws Exception {
		this.configBean = configBean;
		socket = new SocketManager();
		socket.setSocket(receiveSocket);
		cipher = new GSICipher();
		trnsctnLogBean = new TrnsctnLogBean();
		trnsctnLogDAO = new TrnsctnLogDAO();
		trnsctnLogBean.setIpAddress(receiveSocket.getInetAddress().toString());
	}
	
	public void getRequest(){
		try{
			byte[] specLength 	= socket.recv(SPEC_LENGTH);
			byte[] encryptedTxt	= CommonUtil.byteAppend(specLength,socket.recv(CommonUtil.parseInt(specLength)));
			//Log.debug("log.day","HOST << GSI encrypt["+CommonUtil.toString(encryptedTxt)+"]",this);
			request				= cipher.decrypt(encryptedTxt);
			receiveSuccess = true;
		}catch(Exception e){
			Log.debug("log.day","DATA Receive Error = "+e.getMessage(),this);
		}finally{
			Log.debug("log.day","HOST >> PAY ["+CommonUtil.toString(request)+"]",this);
		}
	}
	
	public void process(){
		HeaderBean headerBean 	= null;
		GSISEQDAO sequence 		= new GSISEQDAO();
		TrnsctnLogBean oldLogBean = trnsctnLogDAO.getTrnsctnLogByReqData(CommonUtil.toString(request));
		try{
			getRequest();
			if(!receiveSuccess){
				response = request;
			}else if(oldLogBean.getIdx() != 0){
				response = oldLogBean.getResData().getBytes();
			}else{
				headerBean = new HeaderBean(request);
				headerBean.setTrnResDate(CommonUtil.getCurrentDate("yyyyMMddHHmmss"));
				headerBean.setResultCd("0");
				headerBean.setResultMsg("0000");
				headerBean.setTransactionId(sequence.getTRNNOSEQ());
				insertLog(headerBean);
				
				Payment payment = new Payment(configBean,request);
				response = payment.execute(headerBean);
			}
			
			socket.send(cipher.encrypt(response));
			trnsctnSuccess	= true;
		}catch(Exception e){
			Log.debug("log.day","HOST << PAY [RESONOSE ERROR]"+CommonUtil.getExceptionMessage(e),this);
		}finally{
			Log.debug("log.day","HOST << PAY ["+CommonUtil.toString(response)+"]",this);
			if(trnsctnSuccess){
				updateLog(headerBean,response,"00");
			}else{
				updateLog(headerBean,response,"99");
			}
			socket.socketClose();
		}
	}
	
	
	public void insertLog(HeaderBean headerBean){
		trnsctnLogBean.setMerchantId(headerBean.getMerchantId());
		trnsctnLogBean.setReqData(CommonUtil.toString(request));
		trnsctnLogBean.setServiceType(headerBean.getServiceType());
		trnsctnLogBean.setStatus("11");
		trnsctnLogDAO.insertTrnsctnLog(trnsctnLogBean);
	}
	
	public void updateLog(HeaderBean headerBean,byte[] response,String status){
		trnsctnLogBean.setResData(CommonUtil.toString(response));
		trnsctnLogBean.setStatus(status);
		trnsctnLogBean.setTransactionId(headerBean.getTransactionId());
		trnsctnLogDAO.updateTrnsctnLog(trnsctnLogBean);
	}
	
}

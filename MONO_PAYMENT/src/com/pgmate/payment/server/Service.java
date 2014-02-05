/* 
 * Project Name : GSI_PROJECT
 * Project      : GSI_PAYMENT
 * File Name    : com.pgmate.payment.server.Service.java
 * Date	        : Dec 23, 2008
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.payment.server;

import biz.trustnet.common.comm.server.SocketService;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;
import biz.trustnet.common.xml.XMLFactory;

import com.pgmate.payment.conf.ServerConfigBean;



public class Service {
	private SocketService service;
	private int defaultPort = 20000;
	
	
	public Service() throws Exception {
		
	}
	
	public boolean start() throws Exception {
		ServerConfigBean configBean = (ServerConfigBean)XMLFactory.getEntity("MONO_PAYMENT");
		if(configBean.getServerPort().equals("")){
			service = new SocketService(defaultPort, new Server ());
		}else{
			service = new SocketService(CommonUtil.parseInt(configBean.getServerPort()), new Server ());
		}		
		Log.debug("log.day","MONO_PAYMENT SERVER RUN = TRUE PORT =["+configBean.getServerPort()+"]" ,this);
		return true;
	}
	
	
	public void close() throws Exception{
		service.close();
	}
	
	
	public static void main(String [] args){
		try{
			com.pgmate.payment.server.Service trustpay = new com.pgmate.payment.server.Service();
			if(trustpay.start()){
				System.out.println("MONO_PAYMENT Started ... ^^ ");
			}else{
				System.out.println("MONO_PAYMENT Start fail");
			}
		}catch(Exception e){System.out.println(e.getMessage());}
	}

}

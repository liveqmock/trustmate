/* 
 * Project Name : GSI_PROJECT
 * Project      : GSI_PAYMENT
 * File Name    : com.pgmate.payment.server.Service.java
 * Date	        : Dec 23, 2008
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.server;

import biz.trustnet.common.comm.sslserver.SSLSocketService;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;
import biz.trustnet.common.util.Property;
import biz.trustnet.common.xml.XMLFactory;

import com.pgmate.server.conf.ServerConfigBean;



public class Service {
	private SSLSocketService service;
	private int defaultPort = 20000;
	
	
	public Service() throws Exception {
		
	}
	
	public boolean start() throws Exception {
		ServerConfigBean configBean = (ServerConfigBean)XMLFactory.getEntity("MONO_SSL");
		
		System.setProperty("javax.net.ssl.keyStore","/home/trustmate/MONO_SSL/conf/pgmatekeystore");
		System.setProperty("javax.net.ssl.keyStorePassword",configBean.getKeyStorePasswd());
		System.setProperty("javax.net.ssl.trustStore","/home/trustmate/MONO_SSL/conf/pgmatekeystore");
		System.setProperty("javax.net.ssl.trustStorePassword",configBean.getKeyStorePasswd());
		if(configBean.getServerPort().equals("")){
			service = new SSLSocketService(defaultPort, new Server ());
		}else{
			service = new SSLSocketService(CommonUtil.parseInt(configBean.getServerPort()), new Server ());
		}		
		Log.debug("log.day","MONO_SSL SERVER RUN = TRUE PORT =["+configBean.getServerPort()+"]" ,this);
		return true;
	}
	
	
	public void close() throws Exception{
		service.close();
	}
	
	
	public static void main(String [] args){
		try{
			com.pgmate.server.Service trustpay = new com.pgmate.server.Service();
			if(trustpay.start()){
				System.out.println("MONO_SSL Started ... ^^ ");
			}else{
				System.out.println("MONO_SSL Start fail");
			}
		}catch(Exception e){System.out.println(e.getMessage());}
	}

}

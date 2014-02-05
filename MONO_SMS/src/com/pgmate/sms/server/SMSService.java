/* 
 * Project Name : 
 * Project      : 
 * File Name    : com.pgmate.sms.server.SMSService.java
 * Date	        : Jun 24, 2008
 * Version      : 2.0
 * Author       : ginaida@ginaida.net
 * Comment      :  
 */

package com.pgmate.sms.server;


import biz.trustnet.common.comm.server.SocketService;
import biz.trustnet.common.daemon.NTWrapper;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;
import biz.trustnet.common.xml.XMLFactory;

import com.pgmate.sms.conf.SMSConfigBean;

public class SMSService extends NTWrapper{

	private SocketService service;
	private int defaultPort = 19998;
	
	
	public SMSService() throws Exception {
		
	}
	
	public boolean start() throws Exception {
		SMSConfigBean configBean = (SMSConfigBean)XMLFactory.getEntity("SMS");
		if(configBean.getServerPort().equals("")){
			service = new SocketService(defaultPort, new SMSServer ());
		}else{
			service = new SocketService(CommonUtil.parseInt(configBean.getServerPort()), new SMSServer ());
		}
		
		Log.debug("log.day"," RUN = TRUE",this);
		return true;
	}
	
	
	public void close() throws Exception{
		service.close();
	}
	
	
	public static void main(String [] args){
		try{
			com.pgmate.sms.server.SMSService sms = new com.pgmate.sms.server.SMSService();
			sms.start();
		}catch(Exception e){System.out.println(e.getMessage());}
	}
}

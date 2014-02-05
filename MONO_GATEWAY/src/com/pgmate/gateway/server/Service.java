/* 
 * Project      : MONO_GATEWAY
 * File Name    : com.pgmate.gateway.server.ServiceListener.java
 * Date	        : Sep 16, 2008
 * Version      : 2.0
 * Author       : ginaida@trustmate.net
 * Comment      : 
 */

package com.pgmate.gateway.server;

import biz.trustnet.common.comm.server.SocketService;
import biz.trustnet.common.daemon.NTWrapper;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;
import biz.trustnet.common.xml.XMLFactory;

import com.pgmate.gateway.server.conf.ServerConfigBean;

public class Service extends NTWrapper{

	private SocketService service;
	private int defaultPort = 20000;
	
	
	public Service() throws Exception {
		
	}
	
	public boolean start() throws Exception {
		ServerConfigBean configBean = (ServerConfigBean)XMLFactory.getEntity("MONO_GATEWAY");
		if(configBean.getServerPort().equals("")){
			service = new SocketService(defaultPort, new Server ());
		}else{
			service = new SocketService(CommonUtil.parseInt(configBean.getServerPort()), new Server ());
		}		
		Log.debug("log.day","MONO_GATEWAY SERVER RUN = TRUE",this);
		return true;
	}
	
	
	public void close() throws Exception{
		service.close();
	}
	
	
	public static void main(String [] args){
		try{
			com.pgmate.gateway.server.Service trustpay = new com.pgmate.gateway.server.Service();
			if(trustpay.start()){
				System.out.println("MONO_GATEWAY Started ... ^^ ");
			}else{
				System.out.println("MONO_GATEWAY Start fail");
			}
		}catch(Exception e){System.out.println(e.getMessage());}
	}

}

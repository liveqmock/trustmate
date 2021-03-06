/* 
 * Project Name : GSI_PROJECT
 * Project      : GSI_PAYMENT
 * File Name    : com.pgmate.payment.server.Server.java
 * Date	        : Dec 23, 2008
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.payment.server;

import java.io.IOException;
import java.net.Socket;

import biz.trustnet.common.comm.server.SocketServer;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;
import biz.trustnet.common.xml.XMLFactory;

import com.pgmate.payment.conf.ServerConfigBean;

public class Server  implements SocketServer{
	
	private static ServerConfigBean configBean = null;
	
	public void serve(Socket socket) {	
		try{
			loadConfig();
			socket.setSoTimeout(CommonUtil.parseInt(configBean.getServerTimeout()));	
			
			if(socket.getInetAddress().toString().indexOf(configBean.getAckServer()) > -1){
			}else{
				Log.debug("log.root","Client IP=["+socket.getInetAddress().toString()+"]",this);
				ServiceListener listener = new ServiceListener(socket,configBean);		
				listener.process();
			}
		
		}catch(Exception e){
			Log.debug("log.root","SERVER SOCKET ERROR = "+CommonUtil.getExceptionMessage(e),this);
		}finally{
			try{
				socket.close();
			}catch(IOException io){
				Log.debug("log.root","SERVER SOCKET CLOSE ERROR = "+io.getMessage(),this);
			}
		}
	}
	
	public void loadConfig(){
		if(configBean == null || configBean.getServerPort().equals("")){
			configBean = (ServerConfigBean)XMLFactory.getEntity("MONO_PAYMENT");
		}
	}

}

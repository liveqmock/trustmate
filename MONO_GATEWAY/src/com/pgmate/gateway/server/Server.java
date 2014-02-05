/* 
 * Project      : MONO_GATEWAY
 * File Name    : com.pgmate.gateway.server.ServiceListener.java
 * Date	        : Sep 16, 2008
 * Version      : 2.0
 * Author       : ginaida@trustmate.net
 * Comment      : 
 */

package com.pgmate.gateway.server;

import java.io.IOException;
import java.net.Socket;

import biz.trustnet.common.comm.server.SocketServer;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;
import biz.trustnet.common.xml.XMLFactory;

import com.pgmate.gateway.server.conf.ServerConfigBean;


public class Server implements SocketServer{
	
	private static ServerConfigBean configBean = null;
	
	public void serve(Socket socket) {	
		try{
			loadConfig();
			socket.setSoTimeout(CommonUtil.parseInt(configBean.getServerTimeout()));	
			Log.debug("log.root","Client IP=["+socket.getInetAddress().toString()+"]",this);
			ServiceListener listener = new ServiceListener(socket,configBean);		
			listener.process();
		
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
			configBean = (ServerConfigBean)XMLFactory.getEntity("MONO_GATEWAY");
		}
	}

}

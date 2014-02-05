/*
 * Project Name : 
 * Project      : 
 * File Name    : com.pgmate.sms.server.SMSServer.java
 * Date	        : Jun 24, 2008
 * Version      : 2.0
 * Author       : ginaida@ginaida.net
 * Comment      :
 */

package com.pgmate.sms.server;

import java.io.IOException;
import java.net.Socket;

import com.pgmate.sms.conf.SMSConfigBean;

import biz.trustnet.common.comm.server.SocketServer;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.xml.XMLFactory;

public class SMSServer implements SocketServer{

	private static SMSConfigBean configBean = null;
	
	public void serve(Socket socket) {

		try{
			getConfigBean();
			socket.setSoTimeout(70000);
			ServiceListener listener = new ServiceListener(socket);
			if(socket.getInetAddress().toString().indexOf("112.175.48.40") > -1){
			}else{
				listener.process(configBean);
			}


		}catch(Exception e){
			Log.debug("log.root","SERVER SOCKET ERR = SERVE "+e.getMessage(),this);
		}finally{
			try{
				socket.close();

			}catch(IOException io){
				Log.debug("log.root","SERVER SOCKET ERR = SOCKET CLOSE"+io.getMessage(),this);
			}
		}
	}
	
	public void getConfigBean(){
		if(configBean != null){
			configBean = (SMSConfigBean)XMLFactory.getEntity("SMS");
		}
	}

}

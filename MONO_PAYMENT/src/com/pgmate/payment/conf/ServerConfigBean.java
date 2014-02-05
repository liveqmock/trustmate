/* 
 * Project Name : GSI_PROJECT
 * Project      : GSI_PAYMENT
 * File Name    : com.pgmate.payment.conf.ServerConfigBean.java
 * Date	        : Dec 23, 2008
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.payment.conf;

public class ServerConfigBean {

	private String serverPort		= "";
	private String serverMode		= "";
	private String serverTimeout	= "";
	private String kttTestCard		= "";
	private String ackServer		= "";
	
	public ServerConfigBean(){	
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getServerTimeout() {
		return serverTimeout;
	}

	public void setServerTimeout(String serverTimeout) {
		this.serverTimeout = serverTimeout;
	}


	public String getKttTestCard() {
		return kttTestCard;
	}

	public void setKttTestCard(String kttTestCard) {
		this.kttTestCard = kttTestCard;
	}

	public String getServerMode() {
		return serverMode;
	}

	public void setServerMode(String serverMode) {
		this.serverMode = serverMode;
	}

	public String getAckServer() {
		return ackServer;
	}

	public void setAckServer(String ackServer) {
		this.ackServer = ackServer;
	}
	
	
	
	
}

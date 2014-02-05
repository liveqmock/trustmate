/* 
 * Project      : MONO_GATEWAY
 * File Name    : com.pgmate.gateway.server.conf.ServerConfigBean.java
 * Date	        : Dec 22, 2008
 * Version      : 1.0
 * Author       : ginaida@trustmate.net
 * Comment      :  
 */

package com.pgmate.gateway.server.conf;

public class ServerConfigBean {

	private String serverPort		= "";
	private String serverTimeout	= "";
	private String pwnServerIp		= "";
	private String pwnServerPort	= "";
	private String pwnTimeout		= "";
	
	
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


	public String getPwnServerIp() {
		return pwnServerIp;
	}


	public void setPwnServerIp(String pwnServerIp) {
		this.pwnServerIp = pwnServerIp;
	}


	public String getPwnServerPort() {
		return pwnServerPort;
	}


	public void setPwnServerPort(String pwnServerPort) {
		this.pwnServerPort = pwnServerPort;
	}


	public String getPwnTimeout() {
		return pwnTimeout;
	}


	public void setPwnTimeout(String pwnTimeout) {
		this.pwnTimeout = pwnTimeout;
	}


	}

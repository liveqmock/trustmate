package com.pgmate.payment.ksnet;

/**
 * @author Administrator
 *
 */
public class KSNETConfigBean {

	
	private String ip	= "";
	private String port = "";
	private String timeout	= "";
	
	public KSNETConfigBean(){
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	
	
}

package com.pgmate.payment.smartro;

public class SMARTROConfigBean {
	
	private String ip	= "";
	private String port = "";
	private String timeout	= "";
	private String logDir	= "";
	
	public SMARTROConfigBean(){
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

	public String getLogDir() {
		return logDir;
	}

	public void setLogDir(String logDir) {
		this.logDir = logDir;
	}
	
}

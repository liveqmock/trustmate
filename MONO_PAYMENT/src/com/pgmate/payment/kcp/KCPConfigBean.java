package com.pgmate.payment.kcp;

/**
 * @author Administrator
 *
 */
public class KCPConfigBean {
	
	private String homeDirectory = "";
	private String logLevel			= "";
	private String url				= "";
	private String port				= "";
	private String txMode			= "";
	private String keyDirectory		= "";
	
	
	public KCPConfigBean(){
	}


	public String getHomeDirectory() {
		return homeDirectory;
	}


	public void setHomeDirectory(String homeDirectory) {
		this.homeDirectory = homeDirectory;
	}


	public String getLogLevel() {
		return logLevel;
	}


	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port;
	}


	public String getTxMode() {
		return txMode;
	}


	public void setTxMode(String txMode) {
		this.txMode = txMode;
	}


	public String getKeyDirectory() {
		return keyDirectory;
	}


	public void setKeyDirectory(String keyDirectory) {
		this.keyDirectory = keyDirectory;
	}
	
	
	
	
}

package com.pgmate.server.conf;

/**
 * @author Administrator
 *
 */
public class ServerConfigBean {
	private String serverPort	= "";
	private String serverTimeout	= "";
	private String ackServer	= "";
	private String keyStore	= "";
	private String keyStorePasswd	= "";
	
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

	public String getAckServer() {
		return ackServer;
	}

	public void setAckServer(String ackServer) {
		this.ackServer = ackServer;
	}

	public String getKeyStore() {
		return keyStore;
	}

	public void setKeyStore(String keyStore) {
		this.keyStore = keyStore;
	}

	public String getKeyStorePasswd() {
		return keyStorePasswd;
	}

	public void setKeyStorePasswd(String keyStorePasswd) {
		this.keyStorePasswd = keyStorePasswd;
	}
	
	
}

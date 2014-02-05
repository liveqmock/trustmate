package com.pgmate.model.db;

import java.sql.Timestamp;

public class AgentMerchantBean implements java.io.Serializable{
	
	private String agentId			= "";
	private String merchantId		= "";
	private String regId			= "";
	private Timestamp regDate		= null;
	
	public AgentMerchantBean(){
		
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	
}

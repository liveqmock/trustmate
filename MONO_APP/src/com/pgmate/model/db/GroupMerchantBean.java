package com.pgmate.model.db;

import java.sql.Timestamp;

public class GroupMerchantBean implements java.io.Serializable{
	
	private String groupId			= "";
	private String merchantId		= "";
	private String regId			= "";
	private Timestamp regDate		= null;
	
	public GroupMerchantBean(){
		
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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

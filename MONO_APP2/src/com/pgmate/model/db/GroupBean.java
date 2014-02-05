/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.MerchantBean.java
 * Date	        : Jan 15, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.db;

import java.sql.Timestamp;

public class GroupBean implements java.io.Serializable{
	
	
	private long idx				= 0;
	private String groupId			= "";
	private String name				= "";
	private String password			= "";
	private String pwUpdate			= "";
	private String bizNature		= "";
	private String product			= "";
	private String homepage			= "";
	private String addr1			= "";
	private String addr2			= "";
	private String zipCode			= "";
	private String telNo			= "";
	private String fax				= "";
	private String email			= "";
	private String identiNo			= "";
	private String ceoName			= "";
	private String ceoEngName		= "";
	private String ceoAddr1			= "";
	private String ceoAddr2			= "";
	private String ceoZipCode		= "";
	private String ceoTelNo			= "";
	private String ceoPhoneNo		= "";
	private String ceoEMail			= "";
	private String ceoIdentiNo		= "";
	private String settlePartName	= "";
	private String settlePartEMail 	= "";
	private String settlePartTelNo	= "";
	private String settleBank		= "";
	private String settleBankCode	= "";
	private String settleAccount	= "";
	private String active			= "";
	private long commentIdx			= 0;
	private Timestamp regDate		= null;
	
	public GroupBean(){
		
	}

	public long getIdx() {
		return idx;
	}

	public void setIdx(long idx) {
		this.idx = idx;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPwUpdate() {
		return pwUpdate;
	}

	public void setPwUpdate(String pwUpdate) {
		this.pwUpdate = pwUpdate;
	}

	public String getBizNature() {
		return bizNature;
	}

	public void setBizNature(String bizNature) {
		this.bizNature = bizNature;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdentiNo() {
		return identiNo;
	}

	public void setIdentiNo(String identiNo) {
		this.identiNo = identiNo;
	}

	public String getCeoName() {
		return ceoName;
	}

	public void setCeoName(String ceoName) {
		this.ceoName = ceoName;
	}

	public String getCeoEngName() {
		return ceoEngName;
	}

	public void setCeoEngName(String ceoEngName) {
		this.ceoEngName = ceoEngName;
	}

	public String getCeoAddr1() {
		return ceoAddr1;
	}

	public void setCeoAddr1(String ceoAddr1) {
		this.ceoAddr1 = ceoAddr1;
	}

	public String getCeoAddr2() {
		return ceoAddr2;
	}

	public void setCeoAddr2(String ceoAddr2) {
		this.ceoAddr2 = ceoAddr2;
	}

	public String getCeoZipCode() {
		return ceoZipCode;
	}

	public void setCeoZipCode(String ceoZipCode) {
		this.ceoZipCode = ceoZipCode;
	}

	public String getCeoTelNo() {
		return ceoTelNo;
	}

	public void setCeoTelNo(String ceoTelNo) {
		this.ceoTelNo = ceoTelNo;
	}

	public String getCeoPhoneNo() {
		return ceoPhoneNo;
	}

	public void setCeoPhoneNo(String ceoPhoneNo) {
		this.ceoPhoneNo = ceoPhoneNo;
	}

	public String getCeoEMail() {
		return ceoEMail;
	}

	public void setCeoEMail(String ceoEMail) {
		this.ceoEMail = ceoEMail;
	}

	public String getCeoIdentiNo() {
		return ceoIdentiNo;
	}

	public void setCeoIdentiNo(String ceoIdentiNo) {
		this.ceoIdentiNo = ceoIdentiNo;
	}

	public String getSettlePartName() {
		return settlePartName;
	}

	public void setSettlePartName(String settlePartName) {
		this.settlePartName = settlePartName;
	}

	public String getSettlePartEMail() {
		return settlePartEMail;
	}

	public void setSettlePartEMail(String settlePartEMail) {
		this.settlePartEMail = settlePartEMail;
	}

	public String getSettlePartTelNo() {
		return settlePartTelNo;
	}

	public void setSettlePartTelNo(String settlePartTelNo) {
		this.settlePartTelNo = settlePartTelNo;
	}

	public String getSettleBank() {
		return settleBank;
	}

	public void setSettleBank(String settleBank) {
		this.settleBank = settleBank;
	}

	public String getSettleBankCode() {
		return settleBankCode;
	}

	public void setSettleBankCode(String settleBankCode) {
		this.settleBankCode = settleBankCode;
	}

	public String getSettleAccount() {
		return settleAccount;
	}

	public void setSettleAccount(String settleAccount) {
		this.settleAccount = settleAccount;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public long getCommentIdx() {
		return commentIdx;
	}

	public void setCommentIdx(long commentIdx) {
		this.commentIdx = commentIdx;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	
	
}

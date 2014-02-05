/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.TrnsctnBean.java
 * Date	        : Jan 15, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.db;

import java.sql.Timestamp;


public class TrnsctnBean extends GSIBean implements java.io.Serializable{
	
	private long idx				= 0;
	private String transactionId	= "";
	private String merchantId		= "";
	private String mallId			= "";
	private String serviceType		= "";
	private Timestamp trnReqDate	= null;
	private Timestamp trnResDate	= null;
	private String trnStatus		= "";
	private String resultCd			= "";
	private String resultMsg		= "";
	private String vanTransactionId	= "";
	private String approvalNo		= "";
	private String payNo			= "";
	private String payUserId		= "";
	private String payEmail			= "";
	private String payName			= "";
	private String payTelNo			= "";
	private double amount			= 0;
	private String productType		= "";
	private String productName		= "";
	private String curType			= "";
	private String ipAddress		= "";
	private String van				= "";
	private String temp1			= "";
	private String temp2			= "";
	private String auth				= "";
	private String trnDate			= "";
	
	public TrnsctnBean(){
		
	}

	public long getIdx() {
		return idx;
	}

	public void setIdx(long idx) {
		this.idx = idx;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMallId() {
		return mallId;
	}

	public void setMallId(String mallId) {
		this.mallId = mallId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Timestamp getTrnReqDate() {
		return trnReqDate;
	}

	public void setTrnReqDate(Timestamp trnReqDate) {
		this.trnReqDate = trnReqDate;
	}

	public Timestamp getTrnResDate() {
		return trnResDate;
	}

	public void setTrnResDate(Timestamp trnResDate) {
		this.trnResDate = trnResDate;
	}

	public String getTrnStatus() {
		return trnStatus;
	}

	public void setTrnStatus(String trnStatus) {
		this.trnStatus = trnStatus;
	}

	public String getResultCd() {
		return resultCd;
	}

	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getVanTransactionId() {
		return vanTransactionId;
	}

	public void setVanTransactionId(String vanTransactionId) {
		this.vanTransactionId = vanTransactionId;
	}

	public String getApprovalNo() {
		return approvalNo;
	}

	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getPayUserId() {
		return payUserId;
	}

	public void setPayUserId(String payUserId) {
		this.payUserId = payUserId;
	}

	public String getPayEmail() {
		return payEmail;
	}

	public void setPayEmail(String payEmail) {
		this.payEmail = payEmail;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getPayTelNo() {
		return payTelNo;
	}

	public void setPayTelNo(String payTelNo) {
		this.payTelNo = payTelNo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCurType() {
		return curType;
	}

	public void setCurType(String curType) {
		this.curType = curType;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getVan() {
		return van;
	}

	public void setVan(String van) {
		this.van = van;
	}

	public String getTemp1() {
		return temp1;
	}

	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}

	public String getTemp2() {
		return temp2;
	}

	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getTrnDate() {
		return trnDate;
	}

	public void setTrnDate(String trnDate) {
		this.trnDate = trnDate;
	}
	
	
}

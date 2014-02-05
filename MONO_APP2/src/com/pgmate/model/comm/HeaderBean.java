/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.comm.HeaderBean.java
 * Date	        : Dec 23, 2008
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.comm;

import biz.trustnet.common.util.CommonUtil;

public class HeaderBean {
	private String specLength	= "";
	private String specType		= "";
	private String trnType		= "";
	private String merchantId	= "";
	private String mallId		= "";
	private String serviceType	= "";
	private String ipAddress	= "";
	private String trnDate		= "";
	private String trnResDate	= "";
	private String payNo		= "";
	private String transactionId= "";
	private String resultCd		= "";
	private String resultMsg	= "";
	private String extra		= "";
	
	
	public HeaderBean(){	
	}
	
	public HeaderBean(String transaction){
		this(transaction.getBytes());
	}
	
	public HeaderBean(byte[] transaction){
		specLength		= CommonUtil.toString(transaction,0,4).trim();
		specType		= CommonUtil.toString(transaction,4,4).trim();
		trnType			= CommonUtil.toString(transaction,8,4).trim();
		merchantId		= CommonUtil.toString(transaction,12,20).trim();
		mallId			= CommonUtil.toString(transaction,32,20).trim();
		serviceType		= CommonUtil.toString(transaction,52,8).trim();
		ipAddress		= CommonUtil.toString(transaction,60,20).trim();
		trnDate			= CommonUtil.toString(transaction,80,14).trim();
		trnResDate		= CommonUtil.toString(transaction,94,14).trim();
		payNo			= CommonUtil.toString(transaction,108,50).trim();
		transactionId	= CommonUtil.toString(transaction,158,12).trim();
		resultCd		= CommonUtil.toString(transaction,170,1).trim();
		resultMsg		= CommonUtil.toString(transaction,171,4).trim();
		extra			= CommonUtil.toString(transaction,175,25).trim();
	}
	
	public String getTransaction(byte[] transaction){
		return getTransaction(CommonUtil.toString(transaction));
	}
	
	public String getTransaction(String transaction){
		StringBuffer sb = new StringBuffer();
		specLength = CommonUtil.toString(200-4+transaction.getBytes().length);
		sb.append(CommonUtil.zerofill(specLength,4));
		sb.append(CommonUtil.byteFiller(specType,4));
		sb.append(CommonUtil.byteFiller(trnType,4));
		sb.append(CommonUtil.byteFiller(merchantId,20));
		sb.append(CommonUtil.byteFiller(mallId,20));
		sb.append(CommonUtil.byteFiller(serviceType,8));
		sb.append(CommonUtil.byteFiller(ipAddress,20));
		sb.append(CommonUtil.byteFiller(trnDate,14));
		sb.append(CommonUtil.byteFiller(trnResDate,14));
		sb.append(CommonUtil.byteFiller(payNo,50));
		sb.append(CommonUtil.byteFiller(transactionId,12));
		sb.append(CommonUtil.byteFiller(resultCd,1));
		sb.append(CommonUtil.byteFiller(resultMsg,4));
		sb.append(CommonUtil.byteFiller(extra,25));
		sb.append(transaction);
		return sb.toString();
	}

	public String getSpecLength() {
		return specLength;
	}


	public void setSpecLength(String specLength) {
		this.specLength = specLength;
	}


	public String getSpecType() {
		return specType;
	}


	public void setSpecType(String specType) {
		this.specType = specType;
	}


	public String getTrnType() {
		return trnType;
	}


	public void setTrnType(String trnType) {
		this.trnType = trnType;
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


	public String getIpAddress() {
		return ipAddress;
	}


	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	public String getTrnDate() {
		return trnDate;
	}


	public void setTrnDate(String trnDate) {
		this.trnDate = trnDate;
	}


	public String getTrnResDate() {
		return trnResDate;
	}


	public void setTrnResDate(String trnResDate) {
		this.trnResDate = trnResDate;
	}


	public String getPayNo() {
		return payNo;
	}


	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}


	public String getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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


	public String getExtra() {
		return extra;
	}


	public void setExtra(String extra) {
		this.extra = extra;
	}
}

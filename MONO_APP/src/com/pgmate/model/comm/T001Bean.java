/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.comm.T001Bean.java
 * Date	        : Dec 23, 2008
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.comm;

import biz.trustnet.common.util.CommonUtil;

public class T001Bean {
	private String cardNumber	= "";
	private String cardExpire	= "";
	private String cardCVV		= "";
	private String cardPassword	= "";
	private String cardExtra	= "";
	private String payUserId	= "";
	private String payName		= "";
	private String payTelNo		= "";
	private String payEmail		= "";
	private String productType	= "";
	private String productName	= "";
	private String amount		= "";
	private String curType		= "";
	private String approvalNo	= "";
	private String domain		= "";
	private String cavv			= "";
	private String extra		= "";
	
	private String foreName		= "";
	private String surName		= "";
	private String addr1		= "";
	private String addr2		= "";
	private String city			= "";
	private String state		= "";
	private String country		= "";
	private String zip			= "";
	
	
	public T001Bean(){
	}
	
	public T001Bean(String transaction){
		this(transaction.getBytes());
	}
	
	public T001Bean(byte[] transaction){
		cardNumber		= CommonUtil.toString(transaction,0,20).trim();
		cardExpire		= CommonUtil.toString(transaction,20,4).trim();
		cardCVV			= CommonUtil.toString(transaction,24,3).trim();
		cardPassword	= CommonUtil.toString(transaction,27,4).trim();
		cardExtra		= CommonUtil.toString(transaction,31,10).trim();
		payUserId		= CommonUtil.toString(transaction,41,50).trim();
		payName			= CommonUtil.toString(transaction,91,50).trim();
		payTelNo		= CommonUtil.toString(transaction,141,20).trim();
		payEmail		= CommonUtil.toString(transaction,161,100).trim();
		productType		= CommonUtil.toString(transaction,261,2).trim();
		productName		= CommonUtil.toString(transaction,263,50).trim();
		amount			= CommonUtil.toString(transaction,313,10).trim();
		curType			= CommonUtil.toString(transaction,323,3).trim();;
		approvalNo		= CommonUtil.toString(transaction,326,8).trim();
		domain			= CommonUtil.toString(transaction,334,50).trim();
		cavv			= CommonUtil.toString(transaction,384,100);
		extra			= CommonUtil.toString(transaction,484,66).trim();
		if(transaction.length == 731){
			foreName		= CommonUtil.toString(transaction,550,20).trim();
			surName			= CommonUtil.toString(transaction,570,20).trim();
			addr1			= CommonUtil.toString(transaction,590,50).trim();
			addr2			= CommonUtil.toString(transaction,640,50).trim();
			city			= CommonUtil.toString(transaction,690,20).trim();
			state			= CommonUtil.toString(transaction,710,10).trim();
			country			= CommonUtil.toString(transaction,720,2).trim();
			zip				= CommonUtil.toString(transaction,722,9).trim();
		}
		
	}
	
	public String getTransaction(){
		StringBuffer sb = new StringBuffer();
		sb.append(CommonUtil.byteFiller(cardNumber,20));
		sb.append(CommonUtil.byteFiller(cardExpire,4));
		sb.append(CommonUtil.byteFiller(cardCVV,3));
		sb.append(CommonUtil.byteFiller(cardPassword,4));
		sb.append(CommonUtil.byteFiller(cardExtra,10));
		sb.append(CommonUtil.byteFiller(payUserId,50));
		sb.append(CommonUtil.byteFiller(payName,50));
		sb.append(CommonUtil.byteFiller(payTelNo,20));
		sb.append(CommonUtil.byteFiller(payEmail,100));
		sb.append(CommonUtil.byteFiller(productType,2));
		sb.append(CommonUtil.byteFiller(productName,50));
		sb.append(CommonUtil.zerofill(amount,10));
		sb.append(CommonUtil.byteFiller(curType,3));
		sb.append(CommonUtil.byteFiller(approvalNo,8));
		sb.append(CommonUtil.byteFiller(domain,50));
		sb.append(CommonUtil.byteFiller(cavv,100));
		sb.append(CommonUtil.byteFiller(extra,66));
		sb.append(CommonUtil.byteFiller(foreName,20));
		sb.append(CommonUtil.byteFiller(surName,20));
		sb.append(CommonUtil.byteFiller(addr1,50));
		sb.append(CommonUtil.byteFiller(addr2,50));
		sb.append(CommonUtil.byteFiller(city,20));
		sb.append(CommonUtil.byteFiller(state,10));
		sb.append(CommonUtil.byteFiller(country,2));
		sb.append(CommonUtil.byteFiller(zip,9));
		
		return sb.toString();
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardExpire() {
		return cardExpire;
	}

	public void setCardExpire(String cardExpire) {
		this.cardExpire = cardExpire;
	}

	public String getCardCVV() {
		return cardCVV;
	}

	public void setCardCVV(String cardCVV) {
		this.cardCVV = cardCVV;
	}

	public String getCardPassword() {
		return cardPassword;
	}

	public void setCardPassword(String cardPassword) {
		this.cardPassword = cardPassword;
	}

	public String getCardExtra() {
		return cardExtra;
	}

	public void setCardExtra(String cardExtra) {
		this.cardExtra = cardExtra;
	}

	public String getPayUserId() {
		return payUserId;
	}

	public void setPayUserId(String payUserId) {
		this.payUserId = payUserId;
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

	public String getPayEmail() {
		return payEmail;
	}

	public void setPayEmail(String payEmail) {
		this.payEmail = payEmail;
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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurType() {
		return curType;
	}

	public void setCurType(String curType) {
		this.curType = curType;
	}

	public String getApprovalNo() {
		return approvalNo;
	}

	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getCavv() {
		return cavv;
	}

	public void setCavv(String cavv) {
		this.cavv = cavv;
	}

	public String getForeName() {
		return foreName;
	}

	public void setForeName(String foreName) {
		this.foreName = foreName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
}

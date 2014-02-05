/* 
 * Project      : MONO_GATEWAY
 * File Name    : com.pgmate.model.comm.T001Bean.java
 * Date	        : Dec 22, 2008
 * Version      : 1.0
 * Author       : ginaida@trustmate.net
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
	private String extra		= "";
	
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
		extra			= CommonUtil.toString(transaction,334,66).trim();
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
		sb.append(CommonUtil.byteFiller(extra,66));
		
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
	
	
}

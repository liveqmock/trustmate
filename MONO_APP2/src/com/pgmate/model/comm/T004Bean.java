package com.pgmate.model.comm;

import biz.trustnet.common.util.CommonUtil;

public class T004Bean {

	private String authType		= "";		//인증타입 1: 카드번호 +유효기간 , 2:카드번호+유효기간+CVV 
	private String cardNumber	= "";
	private String cardExpire	= "";
	private String cardCVV		= "";
	private String cardPassword	= "";
	private String cardFiller	= "";
	private String payName		= "";
	private String payTelNo		= "";
	private String message		= "";
	
	public T004Bean(){
	}
	
	public T004Bean(String transaction){
		this(transaction.getBytes());
	}
	
	public T004Bean(byte[] transaction){
		authType		= CommonUtil.toString(transaction,0,1).trim();
		cardNumber		= CommonUtil.toString(transaction,1,20).trim();
		cardExpire		= CommonUtil.toString(transaction,21,4).trim();
		cardCVV			= CommonUtil.toString(transaction,25,4).trim();
		cardPassword	= CommonUtil.toString(transaction,29,4).trim();
		cardFiller  	= CommonUtil.toString(transaction,33,50).trim();
		payName			= CommonUtil.toString(transaction,83,50).trim();
		payTelNo		= CommonUtil.toString(transaction,133,15).trim();
		message			= CommonUtil.toString(transaction,148,152).trim();
	}
	
	public String getTransaction(){
		StringBuffer sb = new StringBuffer();
		sb.append(CommonUtil.byteFiller(authType,1));
		sb.append(CommonUtil.byteFiller(cardNumber,20));
		sb.append(CommonUtil.byteFiller(cardExpire,4));
		sb.append(CommonUtil.byteFiller(cardCVV,4));
		sb.append(CommonUtil.byteFiller(cardPassword,4));
		sb.append(CommonUtil.byteFiller(cardFiller,50));
		sb.append(CommonUtil.byteFiller(payName,50));
		sb.append(CommonUtil.byteFiller(payTelNo,15));
		sb.append(CommonUtil.byteFiller(message,152));
		
		return sb.toString();
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
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

	public String getCardFiller() {
		return cardFiller;
	}

	public void setCardFiller(String cardFiller) {
		this.cardFiller = cardFiller;
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


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
	
}

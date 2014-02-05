/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.comm.T002Bean.java
 * Date	        : Dec 23, 2008
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.comm;

import biz.trustnet.common.util.CommonUtil;

public class T002Bean {
	private String voidType		= "";
	private String approvalDay	= "";
	private String rApprovalNo	= "";
	private String rPayNo		= "";
	private String rTransactionId= "";
	private String cardNumber	= "";
	private String cardExpire	= "";
	private String amount		= "";
	private String curType		= "";
	private String approvalNo	= "";
	private String extra		= "";
	
	public T002Bean(){
		
	}
	
	public T002Bean(String transaction){
		this(transaction.getBytes());
	}
	
	public T002Bean(byte[] transaction){
		voidType		= CommonUtil.toString(transaction,0,1).trim(); 
		approvalDay		= CommonUtil.toString(transaction,1,8).trim();
		rApprovalNo		= CommonUtil.toString(transaction,9,8).trim();
		rPayNo			= CommonUtil.toString(transaction,17,50).trim();
		rTransactionId	= CommonUtil.toString(transaction,67,12).trim();
		cardNumber		= CommonUtil.toString(transaction,79,20).trim();
		cardExpire		= CommonUtil.toString(transaction,99,4).trim();
		amount			= CommonUtil.toString(transaction,103,10).trim();
		curType			= CommonUtil.toString(transaction,113,3).trim();
		approvalNo		= CommonUtil.toString(transaction,116,8).trim();
		extra			= CommonUtil.toString(transaction,124,76).trim();
	}
	
	public String getTransaction(){
		StringBuffer sb = new StringBuffer();
		sb.append(CommonUtil.byteFiller(voidType,1));
		sb.append(CommonUtil.byteFiller(approvalDay,8 ));
		sb.append(CommonUtil.byteFiller(rApprovalNo,8 ));
		sb.append(CommonUtil.byteFiller(rPayNo,50 ));
		sb.append(CommonUtil.byteFiller(rTransactionId,12 ));
		sb.append(CommonUtil.byteFiller(cardNumber,20 ));
		sb.append(CommonUtil.byteFiller(cardExpire,4 ));
		sb.append(CommonUtil.zerofill(amount,10 ));
		sb.append(CommonUtil.byteFiller(curType,3 ));
		sb.append(CommonUtil.byteFiller(approvalNo,8 ));
		sb.append(CommonUtil.byteFiller(extra,76 ));
		
		return sb.toString();
	}

	public String getVoidType() {
		return voidType;
	}

	public void setVoidType(String voidType) {
		this.voidType = voidType;
	}

	public String getApprovalDay() {
		return approvalDay;
	}

	public void setApprovalDay(String approvalDay) {
		this.approvalDay = approvalDay;
	}

	public String getRApprovalNo() {
		return rApprovalNo;
	}

	public void setRApprovalNo(String approvalNo) {
		rApprovalNo = approvalNo;
	}

	public String getRPayNo() {
		return rPayNo;
	}

	public void setRPayNo(String payNo) {
		rPayNo = payNo;
	}

	public String getRTransactionId() {
		return rTransactionId;
	}

	public void setRTransactionId(String transactionId) {
		rTransactionId = transactionId;
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

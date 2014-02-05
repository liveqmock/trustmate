/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.comm.T003Bean.java
 * Date	        : Dec 23, 2008
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.comm;

import biz.trustnet.common.util.CommonUtil;

public class T003Bean {
	private String rPayNo	= "";
	private String trnType	= "";
	private String resultCd	= "";
	private String resultMsg= "";
	private String transactionId	= "";
	private String approvalNo		= "";
	private String status		= "";
	private String extra	= "";
	
	public T003Bean(){
	}
	
	public T003Bean(String transaction){
		this(transaction.getBytes());
	}
	
	public T003Bean(byte[] transaction){
		rPayNo			= CommonUtil.toString(transaction,0,50).trim();
		trnType			= CommonUtil.toString(transaction,50,4).trim();
		resultCd		= CommonUtil.toString(transaction,54,1).trim();
		resultMsg		= CommonUtil.toString(transaction,55,4).trim();
		transactionId	= CommonUtil.toString(transaction,59,12).trim();
		approvalNo		= CommonUtil.toString(transaction,71,8).trim();
		status 			= CommonUtil.toString(transaction,79,2).trim();
		extra			= CommonUtil.toString(transaction,81,119).trim();
	}
	
	public String getTransaction(){
		StringBuffer sb = new StringBuffer();

		sb.append(CommonUtil.byteFiller(rPayNo,50 ));
		sb.append(CommonUtil.byteFiller(trnType,4 ));
		sb.append(CommonUtil.byteFiller(resultCd,1 ));
		sb.append(CommonUtil.byteFiller(resultMsg,4 ));
		sb.append(CommonUtil.zerofill(transactionId,12 ));
		sb.append(CommonUtil.byteFiller(approvalNo,8 ));
		sb.append(CommonUtil.byteFiller(status,2 ));
		sb.append(CommonUtil.byteFiller(extra,119 ));
		
		return sb.toString();
	}

	public String getRPayNo() {
		return rPayNo;
	}

	public void setRPayNo(String payNo) {
		rPayNo = payNo;
	}

	public String getTrnType() {
		return trnType;
	}

	public void setTrnType(String trnType) {
		this.trnType = trnType;
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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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

	public String getrPayNo() {
		return rPayNo;
	}

	public void setrPayNo(String rPayNo) {
		this.rPayNo = rPayNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}

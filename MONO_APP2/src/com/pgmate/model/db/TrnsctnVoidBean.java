/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.TrnsctnVoidBean.java
 * Date	        : Jan 15, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.db;

import java.sql.Timestamp;

public class TrnsctnVoidBean extends GSIBean implements java.io.Serializable{
	
	private long idx					= 0;
	private String transactionId		= "";
	private String voidTransactionId	= "";
	private String voidVanTransactionId	= "";
	private double voidAmount			= 0;
	private String voidApprovalNo		= "";
	private String rootTrnStatus		= "";
	private String status				= "";
	private String voidResultCd			= "";
	private long commentIdx			= 0;
	private Timestamp voidReqDate		= null;
	private Timestamp voidResDate		= null;
	private Timestamp regDate			= null;
	
	public TrnsctnVoidBean(){
		
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

	public String getVoidTransactionId() {
		return voidTransactionId;
	}

	public void setVoidTransactionId(String voidTransactionId) {
		this.voidTransactionId = voidTransactionId;
	}

	public String getVoidVanTransactionId() {
		return voidVanTransactionId;
	}

	public void setVoidVanTransactionId(String voidVanTransactionId) {
		this.voidVanTransactionId = voidVanTransactionId;
	}

	public double getVoidAmount() {
		return voidAmount;
	}

	public void setVoidAmount(double voidAmount) {
		this.voidAmount = voidAmount;
	}

	public String getVoidApprovalNo() {
		return voidApprovalNo;
	}

	public void setVoidApprovalNo(String voidApprovalNo) {
		this.voidApprovalNo = voidApprovalNo;
	}

	public String getRootTrnStatus() {
		return rootTrnStatus;
	}

	public void setRootTrnStatus(String rootTrnStatus) {
		this.rootTrnStatus = rootTrnStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVoidResultCd() {
		return voidResultCd;
	}

	public void setVoidResultCd(String voidResultCd) {
		this.voidResultCd = voidResultCd;
	}

	public long getCommentIdx() {
		return commentIdx;
	}

	public void setCommentIdx(long commentIdx) {
		this.commentIdx = commentIdx;
	}

	public Timestamp getVoidReqDate() {
		return voidReqDate;
	}

	public void setVoidReqDate(Timestamp voidReqDate) {
		this.voidReqDate = voidReqDate;
	}

	public Timestamp getVoidResDate() {
		return voidResDate;
	}

	public void setVoidResDate(Timestamp voidResDate) {
		this.voidResDate = voidResDate;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	
}

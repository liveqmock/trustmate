/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.TrnsctnCBBean.java
 * Date	        : Jan 15, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.db;

import java.sql.Timestamp;

public class TrnsctnCBBean extends GSIBean implements java.io.Serializable{
	
	private long idx				= 0;
	private String transactionId	= "";
	private String cbState			= "";
	private String rootTrnStatus	= "";
	private String flag				= "";
	private long commentIdx			= 0;
	private Timestamp regDate		= null;
	
	public TrnsctnCBBean(){
		
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

	public String getCbState() {
		return cbState;
	}

	public void setCbState(String cbState) {
		this.cbState = cbState;
	}

	public String getRootTrnStatus() {
		return rootTrnStatus;
	}

	public void setRootTrnStatus(String rootTrnStatus) {
		this.rootTrnStatus = rootTrnStatus;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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

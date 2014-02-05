/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.TrnsctnAcquireBean.java
 * Date	        : Jan 15, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.db;

import java.sql.Timestamp;

public class TrnsctnAcquireBean extends GSIBean implements java.io.Serializable{
	
	private long idx				= 0;
	private String transactionId	= "";
	private String status			= "";
	private String acquireDate		= "";
	private double amount			= 0;
	private String cardErrCd		= "";
	private String vanErrCd			= "";
	private String temp				= "";
	private String van				= "";
	private Timestamp regDate		= null;
	
	public TrnsctnAcquireBean(){
		
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAcquireDate() {
		return acquireDate;
	}

	public void setAcquireDate(String acquireDate) {
		this.acquireDate = acquireDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCardErrCd() {
		return cardErrCd;
	}

	public void setCardErrCd(String cardErrCd) {
		this.cardErrCd = cardErrCd;
	}

	public String getVanErrCd() {
		return vanErrCd;
	}

	public void setVanErrCd(String vanErrCd) {
		this.vanErrCd = vanErrCd;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getVan() {
		return van;
	}

	public void setVan(String van) {
		this.van = van;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	
	

}

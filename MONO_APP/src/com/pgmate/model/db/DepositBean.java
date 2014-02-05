/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.DepositBean.java
 * Date	        : Feb 11, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.db;

import java.sql.Timestamp;

public class DepositBean implements java.io.Serializable{
	
	private long idx			= 0;
	private String merchantId	= "";
	private double currentRate	= 0;
	private double lastAmount	= 0;
	private double currAmount	= 0;
	private double payAmount	= 0; 
	private double totalAmount	= 0;
	private String payDate		= "";
	private long payIdx			= 0;
	private String payYn		= "";
	private Timestamp regDate	= null;
	
	public DepositBean(){
		
	}

	public long getIdx() {
		return idx;
	}

	public void setIdx(long idx) {
		this.idx = idx;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public double getCurrentRate() {
		return currentRate;
	}

	public void setCurrentRate(double currentRate) {
		this.currentRate = currentRate;
	}

	public double getLastAmount() {
		return lastAmount;
	}

	public void setLastAmount(double lastAmount) {
		this.lastAmount = lastAmount;
	}

	public double getCurrAmount() {
		return currAmount;
	}

	public void setCurrAmount(double currAmount) {
		this.currAmount = currAmount;
	}

	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public long getPayIdx() {
		return payIdx;
	}

	public void setPayIdx(long payIdx) {
		this.payIdx = payIdx;
	}

	public String getPayYn() {
		return payYn;
	}

	public void setPayYn(String payYn) {
		this.payYn = payYn;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	
}

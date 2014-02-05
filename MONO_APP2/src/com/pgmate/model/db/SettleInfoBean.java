/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.SettleInfoBean.java
 * Date	        : Feb 11, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.db;

import java.sql.Timestamp;

public class SettleInfoBean implements java.io.Serializable{
	
	private long idx			= 0;
	private String merchantId 	= "";
	private String invoiceIdx	= "";
	private String settleIdx	= "";
	private String settleType	= "";
	private String startDate	= "";
	private String endDate		= "";
	private double totalCount	= 0;
	private double amount		= 0;
	private String settleYn		= "";
	private Timestamp regDate	= null;
	
	public SettleInfoBean(){
		
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

	public String getSettleIdx() {
		return settleIdx;
	}

	public void setSettleIdx(String settleIdx) {
		this.settleIdx = settleIdx;
	}

	public String getSettleType() {
		return settleType;
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public double getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(double totalCount) {
		this.totalCount = totalCount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getSettleYn() {
		return settleYn;
	}

	public void setSettleYn(String settleYn) {
		this.settleYn = settleYn;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public String getInvoiceIdx() {
		return invoiceIdx;
	}

	public void setInvoiceIdx(String invoiceIdx) {
		this.invoiceIdx = invoiceIdx;
	}
	
}

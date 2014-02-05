/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.SettleBean.java
 * Date	        : Feb 11, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.db;

import java.sql.Timestamp;

public class SettleBean extends GSIBean implements java.io.Serializable{
	
	private long idx			= 0;
	private String merchantId	= "";
	private String invoiceIdx	= "";
	private String trnsctnIdx	= "";
	private String cbIdx		= "";
	private String refundIdx	= "";
	private String feeIdx		= "";
	private String fileName		= "";
	private String settleYn		= "";
	private String depositIdx	= "";
	private String vanFeeIdx	= "";
	private Timestamp regDate	= null;
	
	public SettleBean(){
		
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

	public String getInvoiceIdx() {
		return invoiceIdx;
	}

	public void setInvoiceIdx(String invoiceIdx) {
		this.invoiceIdx = invoiceIdx;
	}

	public String getTrnsctnIdx() {
		return trnsctnIdx;
	}

	public void setTrnsctnIdx(String trnsctnIdx) {
		this.trnsctnIdx = trnsctnIdx;
	}

	public String getCbIdx() {
		return cbIdx;
	}

	public void setCbIdx(String cbIdx) {
		this.cbIdx = cbIdx;
	}

	public String getRefundIdx() {
		return refundIdx;
	}

	public void setRefundIdx(String refundIdx) {
		this.refundIdx = refundIdx;
	}

	public String getFeeIdx() {
		return feeIdx;
	}

	public void setFeeIdx(String feeIdx) {
		this.feeIdx = feeIdx;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSettleYn() {
		return settleYn;
	}

	public void setSettleYn(String settleYn) {
		this.settleYn = settleYn;
	}

	public String getDepositIdx() {
		return depositIdx;
	}

	public void setDepositIdx(String depositIdx) {
		this.depositIdx = depositIdx;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public String getVanFeeIdx() {
		return vanFeeIdx;
	}

	public void setVanFeeIdx(String vanFeeIdx) {
		this.vanFeeIdx = vanFeeIdx;
	}
	
	
}

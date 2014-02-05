/* 
 * Project      : MONO_DAEMON
 * File Name    : com.pgmate.daemon.bean.PanworldSettleBean.java
 * Date         : Jun 4, 2009
 * Version      : 1.0
 * Author       : ginaida@trustmate.net
 * Comment      :  
 */

package com.pgmate.daemon.bean;
public class SettleBean {

	private String merchantId	= "";
	private String startDay		= "";
	private String endDay		= "";
	private double totalCnt		= 0;
	private double totalAmt		= 0;
	private double validCnt		= 0;
	private double validAmt		= 0;
	private double refundCnt	= 0;
	private double refundAmt	= 0;
	private double cbCnt		= 0;
	private double cbAmt		= 0;
	
	public SettleBean(){	
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public double getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(double totalCnt) {
		this.totalCnt = totalCnt;
	}

	public double getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public double getValidCnt() {
		return validCnt;
	}

	public void setValidCnt(double validCnt) {
		this.validCnt = validCnt;
	}

	public double getValidAmt() {
		return validAmt;
	}

	public void setValidAmt(double validAmt) {
		this.validAmt = validAmt;
	}

	public double getRefundCnt() {
		return refundCnt;
	}

	public void setRefundCnt(double refundCnt) {
		this.refundCnt = refundCnt;
	}

	public double getRefundAmt() {
		return refundAmt;
	}

	public void setRefundAmt(double refundAmt) {
		this.refundAmt = refundAmt;
	}

	public double getCbCnt() {
		return cbCnt;
	}

	public void setCbCnt(double cbCnt) {
		this.cbCnt = cbCnt;
	}

	public double getCbAmt() {
		return cbAmt;
	}

	public void setCbAmt(double cbAmt) {
		this.cbAmt = cbAmt;
	}
	
	
	
	
}

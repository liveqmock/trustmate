package com.pgmate.model.db;

import java.sql.Timestamp;

/**
 * @author Administrator
 *
 */
public class ReportMerchantPeriodBean extends GSIBean {

	
	private String merchantId	= "";
	private String name			= "";
	private String active		= "";
	private Timestamp regDate	= null;
	private String mon			= "";
	private double totCnt			= 0;
	private double totAmt			= 0;
	private double sucCnt			= 0;
	private double sucAmt			= 0;
	private double refundCnt		= 0;
	private double refundAmt		= 0;
	private double cbCnt			= 0;
	private double cbAmt			= 0;
	private double refundRateByCnt	= 0;
	private double refundRateByAmt	= 0;
	private double cbRateByCnt	= 0;
	private double cbRateByAmt	= 0;
	
	
	
	public ReportMerchantPeriodBean(){
	}



	public String getMerchantId() {
		return merchantId;
	}



	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getActive() {
		return active;
	}



	public void setActive(String active) {
		this.active = active;
	}



	public Timestamp getRegDate() {
		return regDate;
	}



	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}



	public String getMon() {
		return mon;
	}



	public void setMon(String mon) {
		this.mon = mon;
	}



	public double getTotCnt() {
		return totCnt;
	}



	public void setTotCnt(double totCnt) {
		this.totCnt = totCnt;
	}



	public double getTotAmt() {
		return totAmt;
	}



	public void setTotAmt(double totAmt) {
		this.totAmt = totAmt;
	}



	public double getSucCnt() {
		return sucCnt;
	}



	public void setSucCnt(double sucCnt) {
		this.sucCnt = sucCnt;
	}



	public double getSucAmt() {
		return sucAmt;
	}



	public void setSucAmt(double sucAmt) {
		this.sucAmt = sucAmt;
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



	public double getRefundRateByCnt() {
		return refundRateByCnt;
	}



	public void setRefundRateByCnt(double refundRateByCnt) {
		this.refundRateByCnt = refundRateByCnt;
	}



	public double getRefundRateByAmt() {
		return refundRateByAmt;
	}



	public void setRefundRateByAmt(double refundRateByAmt) {
		this.refundRateByAmt = refundRateByAmt;
	}



	public double getCbRateByCnt() {
		return cbRateByCnt;
	}



	public void setCbRateByCnt(double cbRateByCnt) {
		this.cbRateByCnt = cbRateByCnt;
	}



	public double getCbRateByAmt() {
		return cbRateByAmt;
	}



	public void setCbRateByAmt(double cbRateByAmt) {
		this.cbRateByAmt = cbRateByAmt;
	}
	
}


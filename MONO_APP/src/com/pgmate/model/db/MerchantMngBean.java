/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.MerchantMngBean.java
 * Date	        : Jan 15, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.db;

import java.sql.Timestamp;

public class MerchantMngBean implements java.io.Serializable{
	
	
	private long idx				= 0;
	private String merchantId		= "";
	private String curType			= "";
	private double onceLimit		= 0;
	private double dayLimit			= 0;
	private double monthLimit		= 0;
	private double cardMonthLimit	= 0;
	private double tempLimit		= 0;
	private int duplicationCount 	= 0;
	private String reportEMail		= "";
	private String demo				= "";
	private String van				= "";
	private String vanId			= "";
	private double vmFee			= 0;
	private double jcbFee			= 0;
	private double deposit			= 0;
	private String temp				= "";
	private long commentIdx			= 0;
	private double vanFee			= 0;
	private double cbFee			= 0;
	private Timestamp regDate		= null;
	private String auth				= "";
	private String dm				= "";
	
	public MerchantMngBean(){
		
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

	public String getCurType() {
		return curType;
	}

	public void setCurType(String curType) {
		this.curType = curType;
	}

	public double getOnceLimit() {
		return onceLimit;
	}

	public void setOnceLimit(double onceLimit) {
		this.onceLimit = onceLimit;
	}

	public double getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(double dayLimit) {
		this.dayLimit = dayLimit;
	}

	public double getMonthLimit() {
		return monthLimit;
	}

	public void setMonthLimit(double monthLimit) {
		this.monthLimit = monthLimit;
	}

	public double getCardMonthLimit() {
		return cardMonthLimit;
	}

	public void setCardMonthLimit(double cardMonthLimit) {
		this.cardMonthLimit = cardMonthLimit;
	}

	public double getTempLimit() {
		return tempLimit;
	}

	public void setTempLimit(double tempLimit) {
		this.tempLimit = tempLimit;
	}

	public int getDuplicationCount() {
		return duplicationCount;
	}

	public void setDuplicationCount(int duplicationCount) {
		this.duplicationCount = duplicationCount;
	}

	public String getReportEMail() {
		return reportEMail;
	}

	public void setReportEMail(String reportEMail) {
		this.reportEMail = reportEMail;
	}

	public String getDemo() {
		return demo;
	}

	public void setDemo(String demo) {
		this.demo = demo;
	}

	public String getVan() {
		return van;
	}

	public void setVan(String van) {
		this.van = van;
	}

	public String getVanId() {
		return vanId;
	}

	public void setVanId(String vanId) {
		this.vanId = vanId;
	}

	public double getVmFee() {
		return vmFee;
	}

	public void setVmFee(double vmFee) {
		this.vmFee = vmFee;
	}

	public double getJcbFee() {
		return jcbFee;
	}

	public void setJcbFee(double jcbFee) {
		this.jcbFee = jcbFee;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
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

	public double getVanFee() {
		return vanFee;
	}

	public void setVanFee(double vanFee) {
		this.vanFee = vanFee;
	}

	public double getCbFee() {
		return cbFee;
	}

	public void setCbFee(double cbFee) {
		this.cbFee = cbFee;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getDm() {
		return dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}
	
	
}

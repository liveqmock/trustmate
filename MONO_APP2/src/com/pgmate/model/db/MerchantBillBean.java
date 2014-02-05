/* 
 * Project Name : KTT-PGVersion II
 * Project      : PGv2_APP
 * File Name    : net.kttrust.model.db.MerchantBillBean.java
 * Date	        : Feb 21, 2010
 * Version      : 2.0
 * Author       : phing@trustmate.net
 * Comment      :  
 */

package com.pgmate.model.db;

import java.sql.Timestamp;

public class MerchantBillBean extends GSIBean implements java.io.Serializable{

	private	String merchantId			= "";	// 가맹점 아이디
	private	String period				= "";	// 정산주기(00:월초,10:10일,15:15일,30:30일,1:1일,7:주,0:즉시)
	private	double visamaster			= 0;	// 비자마스터 과금	%
	private	double jcbamex				= 0;	// JCB AMEX	과금 %
	private	double transaction			= 0;	// 거래건당 부과금액 고정
	private	double refund				= 0;	// 정산취소시 건당	고정 (Y,N)
	private	double chargeback			= 0;	// 부도시 건당	고정
	private	double management			= 0;	// 관리비 매년	고정
	private double setupFee				= 0;	// 초기 설치비
	private	double bankTransfer			= 0;	// 송금수수료 매회	고정
	private	double deposit				= 0;	// 보증금 IDX
	private	double vat					= 0;	// VAT	건당 수수료	%
	private	String active				= "";	// 상태 (Y:사용,N:중지)
	private	String bankCode				= "";	// 은행코드
	private	String branch				= "";	// 은행지점
	private	String account				= "";	// 계좌번호
	private	String accountHolder		= "";	// 계좌주명
	private	Timestamp regDate			= null;	// 등록일
	
	public MerchantBillBean(){
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public double getVisamaster() {
		return visamaster;
	}

	public void setVisamaster(double visamaster) {
		this.visamaster = visamaster;
	}

	public double getJcbamex() {
		return jcbamex;
	}

	public void setJcbamex(double jcbamex) {
		this.jcbamex = jcbamex;
	}

	public double getTransaction() {
		return transaction;
	}

	public void setTransaction(double transaction) {
		this.transaction = transaction;
	}

	public double getRefund() {
		return refund;
	}

	public void setRefund(double refund) {
		this.refund = refund;
	}

	public double getChargeback() {
		return chargeback;
	}

	public void setChargeback(double chargeback) {
		this.chargeback = chargeback;
	}

	public double getManagement() {
		return management;
	}

	public void setManagement(double management) {
		this.management = management;
	}

	public double getBankTransfer() {
		return bankTransfer;
	}

	public void setBankTransfer(double bankTransfer) {
		this.bankTransfer = bankTransfer;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public double getSetupFee() {
		return setupFee;
	}

	public void setSetupFee(double setupFee) {
		this.setupFee = setupFee;
	}

	
	
}

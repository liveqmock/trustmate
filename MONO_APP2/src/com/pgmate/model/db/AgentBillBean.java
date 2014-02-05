package com.pgmate.model.db;

import java.sql.Timestamp;

public class AgentBillBean extends GSIBean implements java.io.Serializable{
	
	private String agentId				= "";
	private String merchantId			= "";
	private	double visamaster			= 0;	// 비자마스터 과금	%
	private	double jcbamex				= 0;	// JCB AMEX	과금 %
	private	double transaction			= 0;	// 거래건당 부과금액 고정
	private	double refund				= 0;	// 정산취소시 건당	고정 (Y,N)
	private	double chargeback			= 0;	// 부도시 건당	고정
	private	double management			= 0;	// 관리비 매년	고정
	private double setupFee				= 0;	// 초기 설치비
	private	double bankTransfer			= 0;	// 송금수수료 매회	고정
	private	double vat					= 0;	// VAT	건당 수수료	%
	private	String active				= "";	// 상태 (Y:사용,N:중지)
	private	Timestamp regDate			= null;	// 등록일
	
	public AgentBillBean(){
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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

	public double getSetupFee() {
		return setupFee;
	}

	public void setSetupFee(double setupFee) {
		this.setupFee = setupFee;
	}

	public double getBankTransfer() {
		return bankTransfer;
	}

	public void setBankTransfer(double bankTransfer) {
		this.bankTransfer = bankTransfer;
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

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	
}

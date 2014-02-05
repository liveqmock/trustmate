package com.pgmate.model.db;

import java.sql.Timestamp;

public class AgentSettleBean extends GSIBean implements java.io.Serializable{
	
	private String settleId				= "";		//정산 인덱스
	private String agentId				= "";		//agent 아이디
	private String merchantId			= "";		//가맹점아이디
	private String startDay				= "";		//과금시작일
	private String endDay				= "";		//과금종료일
	private Timestamp settleDate		= null;		//정산 예정일
	private String bankInfo				= "";		//은행 이름 및 지점명
	private String account				= "";		//계좌번호
	private double totAmt				= 0;		//총 거래금액
	private double totFee				= 0;		//총 수수료
	private double totSettle			= 0;		//총 정산금액        
	private double trnVmCnt				= 0;		//비자마스터 총 건수      
	private double trnVmAmt				= 0;		//비자마스터 총 금액
	private double trnVmFee				= 0;		//비자마스터 수수료 
	private double trnVmRate			= 0;		//비자마스터 수수료 기준율
	private double trnJaCnt				= 0;		//JCB/AMEX 총 건수        
	private double trnJaAmt				= 0;		//JCB/AMEX 총 금액         
	private double trnJaFee				= 0;		//JCB/AMEX 수수료          
	private double trnJaRate			= 0;		//JCB/AMEX 수수료 기준율
	private double cbCnt				= 0;		//총 C*B 건수
	private double cbAmt				= 0;		//총 C*B 금액
	private double cbFee				= 0;		//C*B 수수료
	private double cbRate				= 0;		//C*B 수수료 기준율
	private double refundVmCnt			= 0;		//취소 V/M 건수
	private double refundVmAmt			= 0;		//취소 V/M 금액               
	private double refundVmFee			= 0;		//취소 V/M 수수료             
	private double refundVmRate			= 0;		//취소 V/M 수수료 기준율      
	private double refundJaCnt			= 0;		//취소 J/A 건수               
	private double refundJaAmt			= 0;		//취소 J/A 금액               
	private double refundJaFee			= 0;		//취소 J/A 수수료             
	private double refundJaRate			= 0;		//취소 J/A 수수료 기준율    
	private double manageFee			= 0;		//월 관리비                   
	private double vanCnt				= 0;		//밴피 건수                   
	private double vanFee				= 0;		//밴피 수수료                 
	private double vanRate				= 0;		//밴피 수수료 기준율          
	private double transfeFee			= 0;		//은행이체수수료                
	private double vatAmt				= 0;		//소비세 금액          
	private String etcName				= "";		//추가부가이름                
	private double etcAmt				= 0;		//추가부가금액
	private String settleIn				= "";		//관련 정산번호
	private Timestamp regDate			= null;		//생성일자
	
	public AgentSettleBean(){
	}

	public String getSettleId() {
		return settleId;
	}

	public void setSettleId(String settleId) {
		this.settleId = settleId;
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

	public Timestamp getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Timestamp settleDate) {
		this.settleDate = settleDate;
	}

	public String getBankInfo() {
		return bankInfo;
	}

	public void setBankInfo(String bankInfo) {
		this.bankInfo = bankInfo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public double getTotAmt() {
		return totAmt;
	}

	public void setTotAmt(double totAmt) {
		this.totAmt = totAmt;
	}

	public double getTotFee() {
		return totFee;
	}

	public void setTotFee(double totFee) {
		this.totFee = totFee;
	}

	public double getTotSettle() {
		return totSettle;
	}

	public void setTotSettle(double totSettle) {
		this.totSettle = totSettle;
	}

	public double getTrnVmCnt() {
		return trnVmCnt;
	}

	public void setTrnVmCnt(double trnVmCnt) {
		this.trnVmCnt = trnVmCnt;
	}

	public double getTrnVmAmt() {
		return trnVmAmt;
	}

	public void setTrnVmAmt(double trnVmAmt) {
		this.trnVmAmt = trnVmAmt;
	}

	public double getTrnVmFee() {
		return trnVmFee;
	}

	public void setTrnVmFee(double trnVmFee) {
		this.trnVmFee = trnVmFee;
	}

	public double getTrnVmRate() {
		return trnVmRate;
	}

	public void setTrnVmRate(double trnVmRate) {
		this.trnVmRate = trnVmRate;
	}

	public double getTrnJaCnt() {
		return trnJaCnt;
	}

	public void setTrnJaCnt(double trnJaCnt) {
		this.trnJaCnt = trnJaCnt;
	}

	public double getTrnJaAmt() {
		return trnJaAmt;
	}

	public void setTrnJaAmt(double trnJaAmt) {
		this.trnJaAmt = trnJaAmt;
	}

	public double getTrnJaFee() {
		return trnJaFee;
	}

	public void setTrnJaFee(double trnJaFee) {
		this.trnJaFee = trnJaFee;
	}

	public double getTrnJaRate() {
		return trnJaRate;
	}

	public void setTrnJaRate(double trnJaRate) {
		this.trnJaRate = trnJaRate;
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

	public double getCbFee() {
		return cbFee;
	}

	public void setCbFee(double cbFee) {
		this.cbFee = cbFee;
	}

	public double getCbRate() {
		return cbRate;
	}

	public void setCbRate(double cbRate) {
		this.cbRate = cbRate;
	}

	public double getRefundVmCnt() {
		return refundVmCnt;
	}

	public void setRefundVmCnt(double refundVmCnt) {
		this.refundVmCnt = refundVmCnt;
	}

	public double getRefundVmAmt() {
		return refundVmAmt;
	}

	public void setRefundVmAmt(double refundVmAmt) {
		this.refundVmAmt = refundVmAmt;
	}

	public double getRefundVmFee() {
		return refundVmFee;
	}

	public void setRefundVmFee(double refundVmFee) {
		this.refundVmFee = refundVmFee;
	}

	public double getRefundVmRate() {
		return refundVmRate;
	}

	public void setRefundVmRate(double refundVmRate) {
		this.refundVmRate = refundVmRate;
	}

	public double getRefundJaCnt() {
		return refundJaCnt;
	}

	public void setRefundJaCnt(double refundJaCnt) {
		this.refundJaCnt = refundJaCnt;
	}

	public double getRefundJaAmt() {
		return refundJaAmt;
	}

	public void setRefundJaAmt(double refundJaAmt) {
		this.refundJaAmt = refundJaAmt;
	}

	public double getRefundJaFee() {
		return refundJaFee;
	}

	public void setRefundJaFee(double refundJaFee) {
		this.refundJaFee = refundJaFee;
	}

	public double getRefundJaRate() {
		return refundJaRate;
	}

	public void setRefundJaRate(double refundJaRate) {
		this.refundJaRate = refundJaRate;
	}

	public double getManageFee() {
		return manageFee;
	}

	public void setManageFee(double manageFee) {
		this.manageFee = manageFee;
	}

	public double getVanCnt() {
		return vanCnt;
	}

	public void setVanCnt(double vanCnt) {
		this.vanCnt = vanCnt;
	}

	public double getVanFee() {
		return vanFee;
	}

	public void setVanFee(double vanFee) {
		this.vanFee = vanFee;
	}

	public double getVanRate() {
		return vanRate;
	}

	public void setVanRate(double vanRate) {
		this.vanRate = vanRate;
	}

	public double getTransfeFee() {
		return transfeFee;
	}

	public void setTransfeFee(double transfeFee) {
		this.transfeFee = transfeFee;
	}

	public double getVatAmt() {
		return vatAmt;
	}

	public void setVatAmt(double vatAmt) {
		this.vatAmt = vatAmt;
	}

	public String getEtcName() {
		return etcName;
	}

	public void setEtcName(String etcName) {
		this.etcName = etcName;
	}

	public double getEtcAmt() {
		return etcAmt;
	}

	public void setEtcAmt(double etcAmt) {
		this.etcAmt = etcAmt;
	}

	public String getSettleIn() {
		return settleIn;
	}

	public void setSettleIn(String settleIn) {
		this.settleIn = settleIn;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	
}

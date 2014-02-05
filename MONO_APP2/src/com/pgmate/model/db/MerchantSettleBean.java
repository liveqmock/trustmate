/* 
 * Project Name : KTT-PGVersion II
 * Project      : PGv2_APP
 * File Name    : net.kttrust.model.db.MerchantSettleBean.java
 * Date	        : Feb 21, 2010
 * Version      : 2.0
 * Author       : phing@trustmate.net
 * Comment      :  
 */

package com.pgmate.model.db;

import java.sql.Timestamp;

public class MerchantSettleBean extends GSIBean implements java.io.Serializable{
	
	private String settleId				= "";		//정산 인덱스
	private String merchantId			= "";		//가맹점아이디
	private String startDay				= "";		//과금시작일
	private String endDay				= "";		//과금종료일
	private String period				= "";		//정산주기(10,15,0:월초,30:월말)
	private String settleCnt			= "";		//정산횟차 기본:1, 최대:3
	private String status				= "";		//R:예정,C:확정,Y:지불완료
	private Timestamp payDate			= null;		//가맹점 지급일자
	private Timestamp settleDate		= null;		//정산 예정일
	private String bankInfo				= "";		//은행 이름 및 지점명
	private String account				= "";		//계좌번호
	private double totAmt				= 0;		//총 거래금액
	private double totFee					= 0;		//총 수수료
	private double totDeposit				= 0;		//보증금 차감금액
	private double totSettle				= 0;		//총 정산금액
	private String trnBillId			= "";		//거래번호 BILL_ID        
	private double trnVmCnt				= 0;		//비자마스터 총 건수      
	private double trnVmAmt				= 0;		//비자마스터 총 금액
	private double trnVmFee				= 0;		//비자마스터 수수료 
	private double trnVmRate			= 0;		//비자마스터 수수료 기준율
	private double trnJaCnt				= 0;		//JCB/AMEX 총 건수        
	private double trnJaAmt				= 0;		//JCB/AMEX 총 금액         
	private double trnJaFee				= 0;		//JCB/AMEX 수수료          
	private double trnJaRate			= 0;		//JCB/AMEX 수수료 기준율
	private String preTrnBillId			= "";		//이전달 거래번호 BILL_ID        
	private double preTrnVmCnt			= 0;		//이전달 비자마스터 총 건수      
	private double preTrnVmAmt			= 0;		//이전달 비자마스터 총 금액      
	private double preTrnVmFee			= 0;		//이전달 비자마스터 수수료       
	private double preTrnVmRate			= 0;		//이전달 비자마스터 수수료 기준율
	private double preTrnJaCnt			= 0;		//이전달 JCB/AMEX 총 건수        
	private double preTrnJaAmt			= 0;		//이전달 JCB/AMEX 총 금액        
	private double preTrnJaFee			= 0;		//이전달 JCB/AMEX 수수료         
	private double preTrnJaRate			= 0;		//이전달 JCB/AMEX 수수료 기준율  
	private String cbBillId				= "";		//C*B 거래 BILL_ID
	private double cbCnt					= 0;		//총 C*B 건수
	private double cbAmt					= 0;		//총 C*B 금액
	private double cbFee					= 0;		//C*B 수수료
	private double cbRate				= 0;		//C*B 수수료 기준율
	private String refundBillId			= "";		//취소 거래 BILL_ID
	private double refundVmCnt			= 0;		//취소 V/M 건수
	private double refundVmAmt			= 0;		//취소 V/M 금액               
	private double refundVmFee			= 0;		//취소 V/M 수수료             
	private double refundVmRate			= 0;		//취소 V/M 수수료 기준율      
	private double refundJaCnt			= 0;		//취소 J/A 건수               
	private double refundJaAmt			= 0;		//취소 J/A 금액               
	private double refundJaFee			= 0;		//취소 J/A 수수료             
	private double refundJaRate			= 0;		//취소 J/A 수수료 기준율      
	private String preRefundBillId		= "";		//이전달취소 거래 BILL_ID     
	private double preRefundVmCnt			= 0;		//이전달취소 V/M 건수         
	private double preRefundVmAmt			= 0;		//이전달취소 V/M 금액         
	private double preRefundVmFee			= 0;		//이전달취소 V/M 수수료       
	private double preRefundVmRate		= 0;		//이전달취소 V/M 수수료 기준율
	private double preRefundJaCnt			= 0;		//이전달취소 J/A 건수         
	private double preRefundJaAmt			= 0;		//이전달취소 J/A 금액         
	private double preRefundJaFee			= 0;		//이전달취소 J/A 수수료       
	private double preRefundJaRate		= 0;		//이전달취소 J/A 수수료 기준율    
	private double manageFee				= 0;		//월 관리비                   
	private double vanCnt					= 0;		//밴피 건수                   
	private double vanFee					= 0;		//밴피 수수료                 
	private double vanRate				= 0;		//밴피 수수료 기준율          
	private double transfeFee				= 0;		//은행이체수수료                
	private double vatAmt					= 0;		//소비세 금액                 
	private long depositIdx				= 0;		//보증금 관리 인덱스          
	private String etcName				= "";		//추가부가이름                
	private double etcAmt					= 0;		//추가부가금액         
	private Timestamp regDate			= null;		//생성일자 
	
	public MerchantSettleBean(){
	}

	public String getSettleId() {
		return settleId;
	}

	public void setSettleId(String settleId) {
		this.settleId = settleId;
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

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getSettleCnt() {
		return settleCnt;
	}

	public void setSettleCnt(String settleCnt) {
		this.settleCnt = settleCnt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getPayDate() {
		return payDate;
	}

	public void setPayDate(Timestamp payDate) {
		this.payDate = payDate;
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

	public double getTotDeposit() {
		return totDeposit;
	}

	public void setTotDeposit(double totDeposit) {
		this.totDeposit = totDeposit;
	}

	public double getTotSettle() {
		return totSettle;
	}

	public void setTotSettle(double totSettle) {
		this.totSettle = totSettle;
	}

	public String getTrnBillId() {
		return trnBillId;
	}

	public void setTrnBillId(String trnBillId) {
		this.trnBillId = trnBillId;
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

	public String getPreTrnBillId() {
		return preTrnBillId;
	}

	public void setPreTrnBillId(String preTrnBillId) {
		this.preTrnBillId = preTrnBillId;
	}

	public double getPreTrnVmCnt() {
		return preTrnVmCnt;
	}

	public void setPreTrnVmCnt(double preTrnVmCnt) {
		this.preTrnVmCnt = preTrnVmCnt;
	}

	public double getPreTrnVmAmt() {
		return preTrnVmAmt;
	}

	public void setPreTrnVmAmt(double preTrnVmAmt) {
		this.preTrnVmAmt = preTrnVmAmt;
	}

	public double getPreTrnVmFee() {
		return preTrnVmFee;
	}

	public void setPreTrnVmFee(double preTrnVmFee) {
		this.preTrnVmFee = preTrnVmFee;
	}

	public double getPreTrnVmRate() {
		return preTrnVmRate;
	}

	public void setPreTrnVmRate(double preTrnVmRate) {
		this.preTrnVmRate = preTrnVmRate;
	}

	public double getPreTrnJaCnt() {
		return preTrnJaCnt;
	}

	public void setPreTrnJaCnt(double preTrnJaCnt) {
		this.preTrnJaCnt = preTrnJaCnt;
	}

	public double getPreTrnJaAmt() {
		return preTrnJaAmt;
	}

	public void setPreTrnJaAmt(double preTrnJaAmt) {
		this.preTrnJaAmt = preTrnJaAmt;
	}

	public double getPreTrnJaFee() {
		return preTrnJaFee;
	}

	public void setPreTrnJaFee(double preTrnJaFee) {
		this.preTrnJaFee = preTrnJaFee;
	}

	public double getPreTrnJaRate() {
		return preTrnJaRate;
	}

	public void setPreTrnJaRate(double preTrnJaRate) {
		this.preTrnJaRate = preTrnJaRate;
	}

	public String getCbBillId() {
		return cbBillId;
	}

	public void setCbBillId(String cbBillId) {
		this.cbBillId = cbBillId;
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

	public String getRefundBillId() {
		return refundBillId;
	}

	public void setRefundBillId(String refundBillId) {
		this.refundBillId = refundBillId;
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

	public String getPreRefundBillId() {
		return preRefundBillId;
	}

	public void setPreRefundBillId(String preRefundBillId) {
		this.preRefundBillId = preRefundBillId;
	}

	public double getPreRefundVmCnt() {
		return preRefundVmCnt;
	}

	public void setPreRefundVmCnt(double preRefundVmCnt) {
		this.preRefundVmCnt = preRefundVmCnt;
	}

	public double getPreRefundVmAmt() {
		return preRefundVmAmt;
	}

	public void setPreRefundVmAmt(double preRefundVmAmt) {
		this.preRefundVmAmt = preRefundVmAmt;
	}

	public double getPreRefundVmFee() {
		return preRefundVmFee;
	}

	public void setPreRefundVmFee(double preRefundVmFee) {
		this.preRefundVmFee = preRefundVmFee;
	}

	public double getPreRefundVmRate() {
		return preRefundVmRate;
	}

	public void setPreRefundVmRate(double preRefundVmRate) {
		this.preRefundVmRate = preRefundVmRate;
	}

	public double getPreRefundJaCnt() {
		return preRefundJaCnt;
	}

	public void setPreRefundJaCnt(double preRefundJaCnt) {
		this.preRefundJaCnt = preRefundJaCnt;
	}

	public double getPreRefundJaAmt() {
		return preRefundJaAmt;
	}

	public void setPreRefundJaAmt(double preRefundJaAmt) {
		this.preRefundJaAmt = preRefundJaAmt;
	}

	public double getPreRefundJaFee() {
		return preRefundJaFee;
	}

	public void setPreRefundJaFee(double preRefundJaFee) {
		this.preRefundJaFee = preRefundJaFee;
	}

	public double getPreRefundJaRate() {
		return preRefundJaRate;
	}

	public void setPreRefundJaRate(double preRefundJaRate) {
		this.preRefundJaRate = preRefundJaRate;
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

	public long getDepositIdx() {
		return depositIdx;
	}

	public void setDepositIdx(long depositIdx) {
		this.depositIdx = depositIdx;
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

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	
	

	
}

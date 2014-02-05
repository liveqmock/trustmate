package com.pgmate.model.db;

import java.sql.Timestamp;

/**
 * @author Administrator
 *
 */
public class MerchantDepositBean extends GSIBean {

	
	private long idx			= 0;		//인덱스
	private String merchantId	= "";		//가맹점 아이디
	private double rate			= 0;		//요율
	private double lastAmt		= 0;		//지난달 최종금액	
	private double curAmt			= 0;		//현재 금액
	private double payAmt			= 0;		//지불된 금액
	private double totAmt			= 0;		//전체 금액
	private String payMonth		= "";		//지급 월
	private String settleId		= "";		//정산 번호
	private String paySettleId	= "";		//지급 정산 번호 
	private String comments		= "";		//사유 코멘트 		
	private Timestamp regDate	= null;		//등록일자
	public MerchantDepositBean(){
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
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getLastAmt() {
		return lastAmt;
	}
	public void setLastAmt(double lastAmt) {
		this.lastAmt = lastAmt;
	}
	public double getCurAmt() {
		return curAmt;
	}
	public void setCurAmt(double curAmt) {
		this.curAmt = curAmt;
	}
	public double getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(double payAmt) {
		this.payAmt = payAmt;
	}
	public double getTotAmt() {
		return totAmt;
	}
	public void setTotAmt(double totAmt) {
		this.totAmt = totAmt;
	}
	public String getPayMonth() {
		return payMonth;
	}
	public void setPayMonth(String payMonth) {
		this.payMonth = payMonth;
	}
	public String getSettleId() {
		return settleId;
	}
	public void setSettleId(String settleId) {
		this.settleId = settleId;
	}
	public String getPaySettleId() {
		return paySettleId;
	}
	public void setPaySettleId(String paySettleId) {
		this.paySettleId = paySettleId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	
	
}

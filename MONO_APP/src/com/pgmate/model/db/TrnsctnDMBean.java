package com.pgmate.model.db;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Administrator
 *
 */
public class TrnsctnDMBean extends GSIBean implements Serializable {

	
	private String transactionId	= "";
	private String ekey				= "";
	private String ehash			= "";
	private String addr1			= "";
	private String addr2			= "";
	private String city				= "";
	private String state			= "";
	private String country			= "";
	private String zip				= "";
	private String resultCd			= "";
	private String resultMsg		= "";
	private String hostCd			= "";
	private String decision			= "";
	private String dmNo				= "";
	private double fraudScore		= 0;
	private Timestamp reqDate		= null;
	private Timestamp regDate		= null;
	
	
	
	public TrnsctnDMBean(){
		
	}



	public String getTransactionId() {
		return transactionId;
	}



	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	public String getEkey() {
		return ekey;
	}



	public void setEkey(String ekey) {
		this.ekey = ekey;
	}



	public String getEhash() {
		return ehash;
	}



	public void setEhash(String ehash) {
		this.ehash = ehash;
	}



	public String getAddr1() {
		return addr1;
	}



	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}



	public String getAddr2() {
		return addr2;
	}



	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getZip() {
		return zip;
	}



	public void setZip(String zip) {
		this.zip = zip;
	}



	public String getResultCd() {
		return resultCd;
	}



	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}



	public String getResultMsg() {
		return resultMsg;
	}



	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}



	public String getHostCd() {
		return hostCd;
	}



	public void setHostCd(String hostCd) {
		this.hostCd = hostCd;
	}



	public String getDecision() {
		return decision;
	}



	public void setDecision(String decision) {
		this.decision = decision;
	}



	public String getDmNo() {
		return dmNo;
	}



	public void setDmNo(String dmNo) {
		this.dmNo = dmNo;
	}



	public double getFraudScore() {
		return fraudScore;
	}



	public void setFraudScore(double fraudScore) {
		this.fraudScore = fraudScore;
	}



	public Timestamp getReqDate() {
		return reqDate;
	}



	public void setReqDate(Timestamp reqDate) {
		this.reqDate = reqDate;
	}



	public Timestamp getRegDate() {
		return regDate;
	}



	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}


	
	
}

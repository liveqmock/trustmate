/* 
 * Project Name : KTT-PGVersion II
 * Project      : PGv2_DDL
 * File Name    : net.kttrust.model.sms.SMSDBBean.java
 * Date	        : Jun 24, 2008
 * Version      : 2.0
 * Author       : ginaida@ginaida.net
 * Comment      :  
 */

package com.pgmate.sms.model;

import java.sql.Timestamp;

public class SMSDBBean implements java.io.Serializable {
	
	private String cmid		= "";
	private String status	= "";
	private Timestamp regDate		= null;
	private Timestamp reportDate	= null;
	private String recvTelNo= "";
	private String sendTelNo= "";
	private String message	= "";
	private String carrier	= "";
 
	public SMSDBBean() {
		// TODO Auto-generated constructor stub
	}

	public String getCmid() {
		return cmid;
	}

	public void setCmid(String cmid) {
		this.cmid = cmid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public Timestamp getReportDate() {
		return reportDate;
	}

	public void setReportDate(Timestamp reportDate) {
		this.reportDate = reportDate;
	}

	public String getRecvTelNo() {
		return recvTelNo;
	}

	public void setRecvTelNo(String recvTelNo) {
		this.recvTelNo = recvTelNo;
	}

	public String getSendTelNo() {
		return sendTelNo;
	}

	public void setSendTelNo(String sendTelNo) {
		this.sendTelNo = sendTelNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	
	

}

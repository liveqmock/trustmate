package com.pgmate.model.db;

import java.sql.Timestamp;

public class AlertBean implements java.io.Serializable{
	
	private String alertType		= "";	// type
	private String resultMsg		= "";	// 오류코드유형
	private String target			= "";	// 수신대상
	private String regId			= "";	// 등록자
	private Timestamp regDate		= null;	// 등록일
	private String msg				= "";	// 오류코드메세지
	private String message			= "";	// 메세지
	
	public AlertBean(){
	}

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}

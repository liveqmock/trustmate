/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.ctl.sso.SSOBean.java
 * Date	        : Jan 19, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.ctl.sso;

import java.sql.Timestamp;

public class SSOBean implements java.io.Serializable{
	
	private String memberId		= "";
	private String memberPassword= "";
	private String memberName 	= "";
	private String pwUpdate		= "";
	private String memberRole	= "";
	private int memberGrade		= 5;
	private String curType		= "USD";
	private Timestamp regDate	= null;
	private String active		= "";
	private String targetURL	= "";
	private String errorURL		= "";
	private String serviceName	= "";
	
	public SSOBean(){
		
	}

	public String getMemberId() {
		return memberId;
	}

	public String getMemberPassword() {
		return memberPassword;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getPwUpdate() {
		return pwUpdate;
	}

	public String getMemberRole() {
		return memberRole;
	}

	public int getMemberGrade() {
		return memberGrade;
	}

	public String getCurType() {
		return curType;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public String getActive() {
		return active;
	}

	public String getTargetURL() {
		return targetURL;
	}

	public String getErrorURL() {
		return errorURL;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public void setPwUpdate(String pwUpdate) {
		this.pwUpdate = pwUpdate;
	}

	public void setMemberRole(String memberRole) {
		this.memberRole = memberRole;
	}

	public void setMemberGrade(int memberGrade) {
		this.memberGrade = memberGrade;
	}

	public void setCurType(String curType) {
		this.curType = curType;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}

	public void setErrorURL(String errorURL) {
		this.errorURL = errorURL;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	
	
	

}

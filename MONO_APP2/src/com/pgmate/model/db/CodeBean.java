/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.CodeBean.java
 * Date	        : Jan 15, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.db;

import java.sql.Timestamp;

public class CodeBean implements java.io.Serializable{
	
	private long idx			= 0;
	private String alias		= "";
	private String acode		= "";
	private String code			= "";
	private String krValue		= "";
	private String jpValue		= "";
	private String enValue		= "";
	private String memberId		= "";
	private String active		= "";
	private Timestamp regDate	= null;
	
	public CodeBean(){
		
	}

	public long getIdx() {
		return idx;
	}

	public void setIdx(long idx) {
		this.idx = idx;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAcode() {
		return acode;
	}

	public void setAcode(String acode) {
		this.acode = acode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getKrValue() {
		return krValue;
	}

	public void setKrValue(String krValue) {
		this.krValue = krValue;
	}

	public String getJpValue() {
		return jpValue;
	}

	public void setJpValue(String jpValue) {
		this.jpValue = jpValue;
	}

	public String getEnValue() {
		return enValue;
	}

	public void setEnValue(String enValue) {
		this.enValue = enValue;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
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

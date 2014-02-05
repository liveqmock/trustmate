/*
 * Project Name : PG_APP
 * Project      : PW_APP
 * File Name    : com.pgmate.model.db.TrnsctnRiskBean.java
 * Date	        : 2010. 6. 1.
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :
 */

package com.pgmate.model.db;

import java.sql.Timestamp;

public class TrnsctnRiskBean implements java.io.Serializable{

	private	long idx					= 0;	// 인덱스
	private	String unit					= "";	// 단위(카드번호,이메일,전화번호)
	private	String active				= "";	// 차단여부(Y:차단,N:해제)
	private	String comments				= "";	// 등록사유
	private	Timestamp regDate			= null;	// 등록일

	public TrnsctnRiskBean(){
	}

	public long getIdx() {
		return idx;
	}

	public String getUnit() {
		return unit;
	}

	public String getActive() {
		return active;
	}

	public String getComments() {
		return comments;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setIdx(long idx) {
		this.idx = idx;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}



}

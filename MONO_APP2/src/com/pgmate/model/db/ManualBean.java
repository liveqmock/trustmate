package com.pgmate.model.db;

import java.sql.Timestamp;

/**
 * @author Administrator
 *
 */
public class ManualBean {

	private long idx 	= 0;
	private String docType	= "";
	private String title	= "";
	private String location	= "";
	private String active = "";
	private Timestamp regDate	= null;
	
	public ManualBean(){
	}

	public long getIdx() {
		return idx;
	}

	public void setIdx(long idx) {
		this.idx = idx;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

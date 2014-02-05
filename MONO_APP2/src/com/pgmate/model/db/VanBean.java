package com.pgmate.model.db;

import java.sql.Timestamp;

/**
 * @author Administrator
 *
 */
public class VanBean extends GSIBean {

	private long idx 		= 0;
	private String van		= "";
	private String vanId	= "";
	private String descriptor= "";
	private String status	= "";
	private String url		= "";
	private String adminId	= "";
	private String adminPw	= "";
	private Timestamp regDate= null;
	
	
	
	public VanBean(){
	}



	public long getIdx() {
		return idx;
	}



	public void setIdx(long idx) {
		this.idx = idx;
	}



	public String getVan() {
		return van;
	}



	public void setVan(String van) {
		this.van = van;
	}



	public String getVanId() {
		return vanId;
	}



	public void setVanId(String vanId) {
		this.vanId = vanId;
	}



	public String getDescriptor() {
		return descriptor;
	}



	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public String getAdminId() {
		return adminId;
	}



	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}



	public String getAdminPw() {
		return adminPw;
	}



	public void setAdminPw(String adminPw) {
		this.adminPw = adminPw;
	}



	public Timestamp getRegDate() {
		return regDate;
	}



	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	
	
}

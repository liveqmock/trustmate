/* 
 * Project      : PG_APP
 * File Name    : net.pgmate.model.db.TrnsctnLogBean.java
 * Date	        : Dec 23, 2008
 * Version      : 2.0
 * Author       : ginaida@ginaida.net
 * Comment      :  
 */

package com.pgmate.model.db;

import java.sql.Timestamp;

public class TrnsctnLogBean extends GSIBean implements java.io.Serializable{

	private long idx			= 0;
	private String merchantId	= "";
	private String serviceType	= "";
	private String transactionId= "";
	private String ipAddress	= "";
	private String reqData		= "";
	private String resData		= "";
	private String status		= "";
	private Timestamp regDate	= null;
	
	public TrnsctnLogBean() {
		// TODO Auto-generated constructor stub
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

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getReqData() {
		return reqData;
	}

	public void setReqData(String reqData) {
		this.reqData = reqData;
	}

	public String getResData() {
		return resData;
	}

	public void setResData(String resData) {
		this.resData = resData;
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
	
	
	
	

}

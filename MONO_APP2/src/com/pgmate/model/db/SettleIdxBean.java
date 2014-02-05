/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.SettleIdxBean.java
 * Date	        : Feb 11, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.db;

public class SettleIdxBean implements java.io.Serializable{
	
	private String merchantId		= "";
	private String settleIdx		= "";
	private String transactionId	= "";
	private String settleType		= "";
	
	public SettleIdxBean(){
		
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getSettleIdx() {
		return settleIdx;
	}

	public void setSettleIdx(String settleIdx) {
		this.settleIdx = settleIdx;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getSettleType() {
		return settleType;
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}
	
	

}

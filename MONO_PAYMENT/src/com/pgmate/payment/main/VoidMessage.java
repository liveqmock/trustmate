/* 
 * Project Name : GSI_PROJECT
 * Project      : GSI_PAYMENT
 * File Name    : com.pgmate.payment.main.VoidMessage.java
 * Date	        : Feb 5, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.payment.main;

import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.TrnsctnBean;

public class VoidMessage {

	public static final String VOID_POSSIBLE_STATUS = "02,07,08,09,10,17,20";
	public static final String VOID_SUCCESS_STATUS  = "06,18,14";
	public static final String VOID_SETTLE_STATUS 	= "11,12,13,24";
	
	public VoidMessage(){
		
	}
	
	public boolean isAleadyVoided(String trnStatus){
		if(VOID_SUCCESS_STATUS.indexOf(trnStatus) > -1 ){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isPossible(String trnStatus){
		if(VOID_POSSIBLE_STATUS.indexOf(trnStatus) > -1  || VOID_SETTLE_STATUS.indexOf(trnStatus) > -1){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * VOID_SETTLE_STATUS 은 환불로 처리 한다.
	 * @param trnStatus
	 * @return
	 */
	public boolean isSettleVoid(String trnStatus){
		if(VOID_SETTLE_STATUS.indexOf(trnStatus) > -1 ){
			return true;
		}else{
			return false;
		}
	}
	
	public String getTrnStatus(TrnsctnBean trnsctnBean){
		if(trnsctnBean.getTrnStatus().equals("02")||trnsctnBean.getTrnStatus().equals("17")){
			if(trnsctnBean.getTransactionId().substring(0,6).equals(CommonUtil.getCurrentDate("yyMMdd"))){
				return "06";
			}else{
				return "18";
			}
		}else if(trnsctnBean.getTrnStatus().equals("07")||trnsctnBean.getTrnStatus().equals("08")||trnsctnBean.getTrnStatus().equals("09")||trnsctnBean.getTrnStatus().equals("10")||trnsctnBean.getTrnStatus().equals("20")){
			return "18";
		}else if(trnsctnBean.getTrnStatus().equals("11")||trnsctnBean.getTrnStatus().equals("12")||trnsctnBean.getTrnStatus().equals("13")||trnsctnBean.getTrnStatus().equals("24")){
			return "14";
		}else{
			return "18";
		}
	}
	
}

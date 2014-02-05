/* 
 * Project Name : GSI_PROJECT
 * Project      : GSI_PAYMENT
 * File Name    : com.pgmate.payment.util.CardUtil.java
 * Date	        : Jan 27, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.payment.util;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

public class CardUtil {
	public CardUtil(){	
	}
	
	/**
	 * 카드 발급자 명칭 확인
	 * @param cardTrack
	 * @return
	 */

	public static String getCardBrand(String cardTrack){
		
		if(CommonUtil.isNullOrSpace(cardTrack)){
			return "UNKNOWN";
		}
		
		if(cardTrack.indexOf("=") > 0){
			cardTrack = cardTrack.split("=")[0];
		}
		
		if(cardTrack.startsWith("3")){
			if(cardTrack.length() == 16){
				return "JCB";
			}else if(cardTrack.length() == 15){
				return "AMEX";
			}else if(cardTrack.length() == 14){
				return "DINERS";
			}else{
				Log.debug("log.day","UNKNOWN CARD =["+cardTrack+"]",null);
				return "UNKNOWN";
			}
			
		}else if(cardTrack.startsWith("4")){
			return "VISA";
		}else if(cardTrack.startsWith("5") || cardTrack.startsWith("6")){
			return "MASTER";
		}else{
			Log.debug("log.day","UNKNOWN CARD =["+cardTrack+"]",null);
			return "UNKNOWN";
		}
	}
	
	public static String getCardIssueCode(String cardTrack){
		if(CommonUtil.isNullOrSpace(cardTrack)){
			return "06";
		}
		
		if(cardTrack.indexOf("=") > 0){
			cardTrack = cardTrack.split("=")[0];
		}
		
		if(cardTrack.startsWith("3")){
			if(cardTrack.length() == 16){
				return "03";
			}else if(cardTrack.length() == 15){
				return "04";
			}else if(cardTrack.length() == 14){
				return "05";
			}else{
				Log.debug("log.day","UNKNOWN CARD =["+cardTrack+"]",null);
				return "06";
			}
			
		}else if(cardTrack.startsWith("4")){
			return "01";
		}else if(cardTrack.startsWith("5") || cardTrack.startsWith("6")){
			return "02";
		}else{
			Log.debug("log.day","UNKNOWN CARD =["+cardTrack+"]",null);
			return "06";
		}
	}
	
	public static String getCardBin(String cardTrack){
		if(CommonUtil.isNullOrSpace(cardTrack) || cardTrack.length() < 6){
			return "";
		}else{
			return cardTrack.substring(0,6);
		}
	}
	
	
	public static String convertCardNumber(String cardNumber){
		if(cardNumber.startsWith("B4")){
			if(cardNumber.length() > 17){
				cardNumber = cardNumber.substring(1,17);
			}
		}
		return cardNumber;
	}
}

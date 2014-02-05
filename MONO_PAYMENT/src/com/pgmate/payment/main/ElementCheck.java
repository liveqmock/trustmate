/* 
 * Project Name : GSI_PROJECT
 * Project      : GSI_PAYMENT
 * File Name    : com.pgmate.payment.main.ElementCheck.java
 * Date	        : Jan 27, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.payment.main;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.comm.HeaderBean;
import com.pgmate.model.comm.T001Bean;
import com.pgmate.model.comm.T002Bean;
import com.pgmate.model.comm.T003Bean;
import com.pgmate.model.comm.T004Bean;
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.dao.TrnsctnDAO;
import com.pgmate.payment.util.CardUtil;

public class ElementCheck {

	public String resultMsg	= "";
	
	
	public ElementCheck(){	
	}
	
	public boolean checkElement(HeaderBean headerBean,MerchantBean merchantBean){
		if(!headerBean.getMerchantId().equals(merchantBean.getMerchantId())){
			resultMsg = "4"; 
			Log.debug("log.day","[등록되지 않은 가맹점 아이디]",this);
			return false;
		}else if(headerBean.getMallId().equals("")){
			Log.debug("log.day","[MALLID 가 입력되지 않았습니다.]",this);
			resultMsg = "5";
			return false;
		}else if(!headerBean.getSpecType().equals("CFIX")){
			Log.debug("log.day","[SPECTYPE 오류]",this);
			resultMsg = "5";
			return false;
		}else if(headerBean.getServiceType().equals("")){
			Log.debug("log.day","[SERVICE TYPE 오류]",this);
			resultMsg = "5";
			return false;
		}else if(headerBean.getIpAddress().equals("")){
			Log.debug("log.day","[IPADDRESS 입력 오류]",this);
			resultMsg = "7";
			return false;
		}else if(headerBean.getPayNo().equals("")){
			Log.debug("log.day","[PAYNO 입력 오류]",this);
			resultMsg = "P10C";
			return false;
		}else if(!headerBean.getTrnType().startsWith("T00")){	//T001,T002,T003 만 지원한다.
			Log.debug("log.day","[TRNTYPE 오류 T00 이 아님]",this);
			resultMsg = "2";
			return false;
		}else if(!merchantBean.getActive().equals("1") && headerBean.getTrnType().equals("T001")){
			Log.debug("log.day","[가맹점이 활성화되지 않았음.]",this);
			resultMsg = "7979";
			return false;
		}else if((new TrnsctnDAO().getDuplicatedPayNo(headerBean.getMerchantId(), headerBean.getPayNo())).size() !=0 && headerBean.getTrnType().equals("T001")){
			Log.debug("log.day","[PAY_NO 중복 오류  ]",this);
			resultMsg = "P10C";
			return false;
		}else{
			return true;
		}
	}
	
	public boolean checkT001(T001Bean tBean){
		
		
		if(CardUtil.getCardBrand(tBean.getCardNumber()).equals("DINERS") || CardUtil.getCardBrand(tBean.getCardNumber()).equals("AMEX")){
			resultMsg = "8418";
			return false;
		}else if(tBean.getCardNumber().equals("") || !CommonUtil.isStringIsNumeric(tBean.getCardNumber())){
			Log.debug("log.day","[카드번호가 없거나 잘못되었습니다..]",this);
			resultMsg = "11";
			return false;
		}else if(tBean.getCardExpire().equals("")){
			Log.debug("log.day","[유효기간 입력 오류]",this);
			resultMsg = "12";
			return false;
		}else if(tBean.getProductType().equals("")){
			Log.debug("log.day","[PRODUCT TYPE 입력 오류]",this);
			resultMsg = "5";
			return false;
		}else if(tBean.getProductName().equals("")){
			Log.debug("log.day","[PRODUCT NAME 입력 오류]",this);
			resultMsg = "5";
			return false;
		}else if(CommonUtil.parseLong(tBean.getAmount()) < 100){
			Log.debug("log.day","[최소 거래금액 1$ 보다 금액 적음]",this);
			resultMsg = "8006";
			return false;
		}else if(tBean.getCurType().equals("")){
			Log.debug("log.day","[CURTYPE 이 입력되지 않았습니다. ]",this);
			resultMsg = "P109";
			return false;
		}else{
			int expiry = CommonUtil.parseInt(tBean.getCardExpire());
			int nowExpiry = CommonUtil.parseInt(CommonUtil.getCurrentDate("yyMM"));
			if(expiry < nowExpiry){
				Log.debug("log.day","[유효기간이 현재보다 과거입니다. ]",this);
				resultMsg = "8314";
				return false;
			}
		}
		return true;
	}
	
	public boolean checkT002(T002Bean tBean){
		if(tBean.getVoidType().equals("1")){
			if(tBean.getRTransactionId().equals("")){
				Log.debug("log.day","[취소요청시 RTRANSACTIONID 가 입력되지 않았음]",this);
				resultMsg = "20";
				return false;
			}else{
				return true;
			}
		}else if(tBean.getVoidType().equals("2")){
			if(tBean.getApprovalDay().equals("")){
				Log.debug("log.day","[승인일자가 입력되지 않았음]",this);
				resultMsg = "5";
				return false;
			}else if(tBean.getRApprovalNo().equals("")){
				Log.debug("log.day","[원거래 승인 번호가 입력되지 않았음]",this);
				resultMsg = "5";
				return false;
			}else if(tBean.getRPayNo().equals("")){
				Log.debug("log.day","[원거래 주문번호가 입력되지 않았음]",this);
				resultMsg = "5";
				return false;
			}else if(tBean.getAmount().equals("")){
				Log.debug("log.day","[원거래 금액이 입력되지 않았음]",this);
				resultMsg = "5";
				return false;
			}else{
				return true;
			}
		}else{
			Log.debug("log.day","[T002 검색조건이 1,2가 아닙니다.]",this);
			resultMsg = "5";
			return false;
		}
	}
	
	public boolean checkT003(T003Bean tBean){
		if(tBean.getRPayNo().equals("")){
			Log.debug("log.day","[가맹점 주문번호가 입력되지 않았음]",this);
			resultMsg = "5";
			return false;
		}else{
			return true;
		}
	}
	
	public boolean checkT004(T004Bean tBean){
		
		
		if(CardUtil.getCardBrand(tBean.getCardNumber()).equals("DINERS") || CardUtil.getCardBrand(tBean.getCardNumber()).equals("AMEX")){
			resultMsg = "8418";
			return false;
		}else if(tBean.getCardNumber().equals("") || !CommonUtil.isStringIsNumeric(tBean.getCardNumber())){
			Log.debug("log.day","[카드번호가 없거나 잘못되었습니다..]",this);
			resultMsg = "11";
			return false;
		}else if(tBean.getCardExpire().equals("")){
			Log.debug("log.day","[유효기간 입력 오류]",this);
			resultMsg = "12";
			return false;
		}else if(CommonUtil.isNullOrSpace(tBean.getAuthType())){
			Log.debug("log.day","[AUTH TYPE 미입력 오류]",this);
			resultMsg = "5";
			return false;
		}else if(tBean.getAuthType().equals("B")){
			if(CommonUtil.isNullOrSpace(tBean.getCardCVV()) || !CommonUtil.isStringIsNumeric(tBean.getCardNumber())){
				Log.debug("log.day","TYPE B CVV 인증거래 [CVV 입력 오류]",this);
				resultMsg = "5";
				return false;
			}
		}else if(tBean.getAuthType().equals("C")){
			if(CommonUtil.isNullOrSpace(tBean.getCardCVV()) || !CommonUtil.isStringIsNumeric(tBean.getCardNumber())){
				Log.debug("log.day","TYPE C CVV 인증거래 [CVV 입력 오류]",this);
				resultMsg = "5";
				return false;
			}
			if(CommonUtil.isNullOrSpace(tBean.getCardPassword()) || !CommonUtil.isStringIsNumeric(tBean.getCardPassword())){
				Log.debug("log.day","TYPE C CVV+패스워드 인증거래 [패스워드 입력 오류]",this);
				resultMsg = "5";
				return false;
			}
		}else{
			
		}
		return true;
	}
	
	
	
	
	
	
	
}

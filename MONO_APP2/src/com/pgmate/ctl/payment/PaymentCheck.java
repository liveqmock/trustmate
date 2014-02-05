package com.pgmate.ctl.payment;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.comm.HeaderBean;
import com.pgmate.model.comm.T001Bean;
import com.pgmate.model.comm.T002Bean;
import com.pgmate.model.comm.T003Bean;
import com.pgmate.model.comm.T004Bean;
import com.pgmate.web.util.ParamUtil;

/**
 * @author Administrator
 *
 */
public class PaymentCheck {
	
	private String message = "";

	public PaymentCheck(){
	}
	
	public boolean webPayCheck(ParamUtil param){
		
		if(param.isNullOrSpace("TRN_TYPE")){
			message =  "Required parameter[TRN_TYPE] was not received.";
			return false;
		}
		
		if(param.isNullOrSpace("MERCHANT_ID")){
			message =  "Required parameter[MERCHANT_ID] was not received.";
			return false;
		}
		
		if(param.isNullOrSpace("MALL_ID")){
			message =  "Required parameter[MALL_ID] was not received.";
			return false;
		}
		
		if(param.isNullOrSpace("SERVICE_TYPE")){
			message =  "Required parameter[SERVICE_TYPE] was not received.";
			return false;
		}
		
		if(param.isNullOrSpace("IP_ADDRESS")){
			message =  "Required parameter[IPADDRESS] was not received.";
			return false;
		}
		
		if(param.isNullOrSpace("PAY_NO")){
			message =  "Required parameter[PAY_NO] was not received.";
			return false;
		}
		
		if(param.isNullOrSpace("RES_TYPE")){
			message =  "Required parameter[RES_TYPE] was not received.";
			return false;
		}else{
			if(param.isEqualUpperCase("RES_TYPE","TEXT") || param.isEqualUpperCase("RES_TYPE","REDIRECT")){
				if(param.isEqualUpperCase("RES_TYPE","REDIRECT")){
					if(param.isNullOrSpace("REDIRECT_URL")){
						message =  "Required parameter[REDIRECT_URL] was not received.";
						return false;
					}
				}
			}else{
				message =  "RES_TYPE[TEXT,REDIRECT] the value is invalid.";
				return false;
			}
		}
		
		if(param.isEqual("TRN_TYPE", "T001")){
			return checkT001(param);
		}else if(param.isEqual("TRN_TYPE", "T002")){
			return checkT002(param);
		}else if(param.isEqual("TRN_TYPE", "T003")){
			return checkT003(param);
		}else if(param.isEqual("TRN_TYPE", "T004")){
			return checkT004(param);
		}else{
			message =  "TRN_TYPE[T001,T002,T003,T004] the value is invalid.";
			return false;
		}
		
	}
	
	
	public boolean checkT001(ParamUtil param){
		if(param.isNullOrSpace("CARD_NUMBER")){
			message =  "Required parameter[CARD_NUMBER] was not received.";
			return false;
		}
		if(param.isNullOrSpace("CARD_EXPIRE")){
			message =  "Required parameter[CARD_EXPIRE] was not received.";
			return false;
		}
		
		if(param.isNullOrSpace("PAY_NAME")){
			message =  "Required parameter[PAY_NAME] was not received.";
			return false;
		}
		if(param.isNullOrSpace("PAY_TELNO")){
			message =  "Required parameter[PAY_TELNO] was not received.";
			return false;
		}
		if(param.isNullOrSpace("PAY_EMAIL")){
			message =  "Required parameter[PAY_EMAIL] was not received.";
			return false;
		}
		if(param.isNullOrSpace("PRODUCT_TYPE")){
			message =  "Required parameter[PRODUCT_TYPE] was not received.";
			return false;
		}
		if(param.isNullOrSpace("PRODUCT_NAME")){
			message =  "Required parameter[PRODUCT_NAME] was not received.";
			return false;
		}
		if(param.isNullOrSpace("AMOUNT")){
			message =  "Required parameter[AMOUNT] was not received.";
			return false;
		}
		if(param.isNullOrSpace("CUR_TYPE")){
			message =  "Required parameter[CUR_TYPE] was not received.";
			return false;
		}
		return true;
	}
	public boolean checkT002(ParamUtil param){
		if(param.isNullOrSpace("VOID_TYPE")){
			message =  "Required parameter[VOID_TYPE] was not received.";
			return false;
		}else{
			if(param.isEqual("VOID_TYPE","1")){
				if(param.isNullOrSpace("RTRANSACTION_ID")){
					message =  "Required parameter[RTRANSACTION_ID] was not received.";
					return false;
				}
			}else if(param.isEqual("VOID_TYPE","2")){
				if(param.isNullOrSpace("APPROVAL_DAY")){
					message =  "Required parameter[APPROVAL_DAY] was not received.";
					return false;
				}
				if(param.isNullOrSpace("RAPPROVAL_NO")){
					message =  "Required parameter[RAPPROVAL_NO] was not received.";
					return false;
				}
				if(param.isNullOrSpace("RPAY_NO")){
					message =  "Required parameter[RPAY_NO] was not received.";
					return false;
				}
				if(param.isNullOrSpace("AMOUNT")){
					message =  "Required parameter[AMOUNT] was not received.";
					return false;
				}
			}else{
				message =  "VOID_TYPE[1,2] the value is invalid.";
				return false;
			}
		}
		
		return true;
	}
	public boolean checkT003(ParamUtil param){
		if(param.isNullOrSpace("RPAY_NO")){
			message =  "Required parameter[RPAY_NO] was not received.";
			return false;
		}
		return true;
	}
	public boolean checkT004(ParamUtil param){
		if(param.isNullOrSpace("CARD_NUMBER")){
			message =  "Required parameter[CARD_NUMBER] was not received.";
			return false;
		}
		if(param.isNullOrSpace("CARD_EXPIRE")){
			message =  "Required parameter[CARD_EXPIRE] was not received.";
			return false;
		}
		if(param.isNullOrSpace("AUTH_TYPE")){
			message =  "Required parameter[AUTH_TYPE] was not received.";
			return false;
		}else{
			if(param.isEqual("AUTH_TYPE", "A")){
				
			}else if(param.isEqual("AUTH_TYPE", "B")){
				if(param.isNullOrSpace("CARD_CVV")){
					message =  "Required parameter[CARD_CVV] was not received.";
					return false;
				}
			}else if(param.isEqual("AUTH_TYPE", "C")){
				if(param.isNullOrSpace("CARD_CVV")){
					message =  "Required parameter[CARD_CVV] was not received.";
					return false;
				}
				if(param.isNullOrSpace("CARD_PASSWORD")){
					message =  "Required parameter[CARD_PASSWORD] was not received.";
					return false;
				}
			}else{
				message =  "AUTH_TYPE[A,B,C] the value is invalid.";
			}
		}
		if(param.isNullOrSpace("PAY_NAME")){
			message =  "Required parameter[PAY_NAME] was not received.";
			return false;
		}
		if(param.isNullOrSpace("PAY_TELNO")){
			message =  "Required parameter[PAY_TELNO] was not received.";
			return false;
		}
		
		return true;
	}
	
	
	public String getMessage(){
		return message;
	}
	
	
	public HeaderBean getHeaderBean(ParamUtil param){
		HeaderBean headerBean = new HeaderBean();
		headerBean.setSpecType("CFIX");
		headerBean.setTrnType(param.getString("TRN_TYPE"));
		headerBean.setMerchantId(param.getString("MERCHANT_ID"));
		headerBean.setMallId(param.getString("MALL_ID"));
		headerBean.setServiceType(param.getString("SERVICE_TYPE"));
		headerBean.setIpAddress(param.getString("IP_ADDRESS"));
		headerBean.setTrnDate(CommonUtil.getCurrentDate("yyyyMMddHHmmss"));
		headerBean.setPayNo(param.getString("PAY_NO"));
		headerBean.setResultCd(param.getString("RESULT_CD"));
		return headerBean;
	}
	
	public T001Bean getT001Bean(ParamUtil param){
		T001Bean tBean = new T001Bean();
		tBean.setCardNumber(param.getString("CARD_NUMBER"));
		tBean.setCardExpire(param.getString("CARD_EXPIRE"));
		tBean.setCardCVV(param.getString("CARD_CVV"));
		tBean.setCardPassword(param.getString("CARD_PASSWORD"));
		tBean.setCardExtra(param.getString("CARD_EXTRA"));
		tBean.setPayUserId(param.getString("PAY_USERID"));
		tBean.setPayName(param.getString("PAY_NAME"));
		tBean.setPayTelNo(param.getString("PAY_TELNO"));
		tBean.setPayEmail(param.getString("PAY_EMAIL"));
		tBean.setProductType(param.getString("PRODUCT_TYPE"));
		tBean.setProductName(param.getString("PRODUCT_NAME"));
		tBean.setAmount(param.getString("AMOUNT"));
		tBean.setCurType(param.getString("CUR_TYPE"));
		tBean.setDomain(param.getString("DOMAIN"));
		if(param.isNullOrSpace("eci")){
			//param.put("eci", "06");
			Log.debug("log.pay", "ECI IS NULL TO 06", this);
		}
		if(param.isNullOrSpace("CAVV")){
			tBean.setCavv(CommonUtil.byteFiller(param.getString("cavv"),40)+CommonUtil.byteFiller(param.getString("xid"),40)+CommonUtil.byteFiller(param.getString("eci"),2));
		}else{
			tBean.setCavv(param.getString("CAVV"));
		}
		tBean.setExtra(param.getString("EXTRA"));
		tBean.setForeName(param.getString("FORENAME"));
		tBean.setSurName(param.getString("SURNAME"));
		tBean.setAddr1(param.getString("ADDR1"));
		tBean.setAddr2(param.getString("ADDR2"));
		tBean.setCity(param.getString("CITY"));
		tBean.setState(param.getString("STATE"));
		tBean.setCountry(param.getString("COUNTRY"));
		tBean.setZip(param.getString("ZIP"));
		
		
		return tBean;
	}
	
	public T002Bean getT002Bean(ParamUtil param){
		T002Bean tBean = new T002Bean();
		tBean.setCardNumber(param.getString("CARD_NUMBER"));
		tBean.setCardExpire(param.getString("CARD_EXPIRE"));
		tBean.setVoidType(param.getString("VOID_TYPE"));
		tBean.setApprovalDay(param.getString("APPROVAL_DAY"));
		tBean.setRApprovalNo(param.getString("RAPPROVAL_NO"));
		tBean.setRPayNo(param.getString("RPAY_NO"));
		tBean.setRTransactionId(param.getString("RTRANSACTION_ID"));
		tBean.setAmount(param.getString("AMOUNT"));
		tBean.setCurType(param.getString("CUR_TYPE"));
		tBean.setExtra(param.getString("EXTRA"));
		return tBean;
	}
	
	public T003Bean getT003Bean(ParamUtil param){
		T003Bean tBean = new T003Bean();
		tBean.setTrnType("T001");
		tBean.setRPayNo(param.getString("RPAY_NO"));
		tBean.setExtra(param.getString("EXTRA"));
		return tBean;
	}
	
	public T004Bean getT004Bean(ParamUtil param){
		T004Bean tBean = new T004Bean();
		tBean.setAuthType(param.getString("AUTH_TYPE"));
		tBean.setCardNumber(param.getString("CARD_NUMBER"));
		tBean.setCardExpire(param.getString("CARD_EXPIRE"));
		tBean.setCardCVV(param.getString("CARD_CVV"));
		tBean.setCardPassword(param.getString("CARD_PASSWORD"));
		tBean.setCardFiller(param.getString("CARD_FILLER"));
		tBean.setPayName(param.getString("PAY_NAME"));
		tBean.setPayTelNo(param.getString("PAY_TELNO"));
		return tBean;
	}
	
	
}

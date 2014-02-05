package com.pgmate.payment.smartro;

import kr.co.smartro.adapter.global.client.SmilePayClient;
import biz.trustnet.common.util.CommonUtil;
import biz.trustnet.common.xml.XMLFactory;

import com.pgmate.model.comm.HeaderBean;
import com.pgmate.model.comm.T001Bean;
import com.pgmate.model.db.MerchantMngBean;
import com.pgmate.model.db.TrnsctnBean;

public class SMARTRO {
	
	private static SMARTROConfigBean configBean = null;
	
	public SMARTRO(){
		if(configBean == null){
			configBean = (SMARTROConfigBean)XMLFactory.getEntity("SMARTRO");
		}
	}

	public SmilePayClient executeCredit(HeaderBean headerBean,T001Bean tBean,MerchantMngBean merchantMngBean){
		
		
		SmilePayClient payClient = new SmilePayClient();
		payClient.setParam("SMILEPAY_DOMAIN_NAME", configBean.getIp());
		payClient.setParam("SMILEPAY_ADAPTOR_LISTEN_PORT", configBean.getPort());
		payClient.setParam("SOCKET_SO_TIMEOUT",configBean.getTimeout());
		payClient.setParam("SMILEPAY_LOG_HOME",configBean.getLogDir());
		payClient.setParam("APP_LOG","1");
		payClient.setParam("ENC_CODE","1");
		payClient.setParam("TELEGRAM_CODE","520052105201");
		payClient.setParam("T_VERSION","1000");
		payClient.setParam("MID",merchantMngBean.getVanId());
		payClient.setParam("M_REF_NO",headerBean.getTransactionId());
		payClient.setParam("CHANNEL_TYPE","WEB");
		
		payClient.setParam("GOODS_NM",tBean.getProductName());
		if(merchantMngBean.getCurType().equals("KRW")){
			payClient.setParam("CUR_CODE","410");
		}else if(merchantMngBean.getCurType().equals("USD")){
			payClient.setParam("CUR_CODE","840");
		}else if(merchantMngBean.getCurType().equals("JPY")){
			payClient.setParam("CUR_CODE","392");
		}else{
			payClient.setParam("CUR_CODE","978");
		}
		payClient.setParam("GOODS_AMT",CommonUtil.convertAmount(CommonUtil.parseDouble(tBean.getAmount())));
		payClient.setParam("SERVICE_AMT","0");
		payClient.setParam("TAX_AMT","0");
		payClient.setParam("CARD_QUOTA","00");
		payClient.setParam("BUYER_TEL",tBean.getPayTelNo());
		payClient.setParam("BUYER_EMAIL",tBean.getPayEmail());
		payClient.setParam("BUYER_NM",tBean.getPayName());
		payClient.setParam("CARD_NO",tBean.getCardNumber());
		payClient.setParam("EXPIRE_DT",tBean.getCardExpire());
		payClient.setParam("CARD_CVC",tBean.getCardCVV());
		payClient.setParam("AUTH_TYPE","0");
		payClient.setParam("CARD_CAVV","");
		payClient.setParam("CARD_XID","");
		payClient.setParam("CARD_ECI","");
		payClient.setParam("BUYER_IP",headerBean.getIpAddress());
		payClient.setParam("ACQU_TYPE","1");
		
		return payClient;
		
		
		
	}
	
	public SmilePayClient executeVoid(TrnsctnBean trnsctnBean,MerchantMngBean merchantMngBean){
		
		
		SmilePayClient payClient = new SmilePayClient();
		payClient.setParam("SMILEPAY_DOMAIN_NAME", configBean.getIp());
		payClient.setParam("SMILEPAY_ADAPTOR_LISTEN_PORT", configBean.getPort());
		payClient.setParam("SOCKET_SO_TIMEOUT",configBean.getTimeout());
		payClient.setParam("SMILEPAY_LOG_HOME",configBean.getLogDir());
		payClient.setParam("APP_LOG","1");
		payClient.setParam("ENC_CODE","1");
		payClient.setParam("TELEGRAM_CODE","520152110000");
		payClient.setParam("T_VERSION","1000");
		payClient.setParam("MID",merchantMngBean.getVanId());
		payClient.setParam("M_REF_NO","1");
		payClient.setParam("CHANNEL_TYPE","WEB");
		
		payClient.setParam("CC_TYPE","1");
		payClient.setParam("PART_CC_TYPE","0");
		payClient.setParam("TID",trnsctnBean.getVanTransactionId());
		if(merchantMngBean.getCurType().equals("KRW")){
			payClient.setParam("CUR_CODE","410");
		}else if(merchantMngBean.getCurType().equals("USD")){
			payClient.setParam("CUR_CODE","840");
		}else if(merchantMngBean.getCurType().equals("JPY")){
			payClient.setParam("CUR_CODE","392");
		}else{
			payClient.setParam("CUR_CODE","978");
		}
		payClient.setParam("CC_AMT",CommonUtil.convertAmount(trnsctnBean.getAmount()));
		payClient.setParam("ORG_APP_DT",CommonUtil.convertTimestampToString(trnsctnBean.getTrnReqDate(), "yyyyMMdd"));
		payClient.setParam("ORG_APP_NO",trnsctnBean.getApprovalNo());
		
		return payClient;
		
		
		
	}

}

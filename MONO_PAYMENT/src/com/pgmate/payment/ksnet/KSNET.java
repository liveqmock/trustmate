package com.pgmate.payment.ksnet;

import biz.trustnet.common.comm.client.SocketManager;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;
import biz.trustnet.common.xml.XMLFactory;

import com.pgmate.model.comm.HeaderBean;
import com.pgmate.model.comm.T001Bean;
import com.pgmate.model.comm.T002Bean;
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.MerchantMngBean;
import com.pgmate.payment.conf.ServerConfigBean;

/**
 * @author Administrator
 *
 */
public class KSNET {

	public static KSNETConfigBean configBean = null;
	
	public KSNET(){
		if(configBean == null){
			configBean = (KSNETConfigBean)XMLFactory.getEntity("KSNET");
		}
	}
	
	public KSNETHeader getHeader(HeaderBean headerBean,T001Bean tBean,MerchantMngBean merchantMngBean){
		KSNETHeader ksHeader = new KSNETHeader();
		
		ksHeader.setCrypto("0");	
		ksHeader.setSpecVersion("0603");
		ksHeader.setSpecType("0");
		ksHeader.setRetry("0");
		ksHeader.setTrnDate(headerBean.getTrnDate());
		ksHeader.setMerchantId(merchantMngBean.getVanId());
		ksHeader.setTrnsNo(headerBean.getTransactionId());
		ksHeader.setPayName(tBean.getPayName());
		ksHeader.setPayEmail(tBean.getPayEmail());
		ksHeader.setPdtType(tBean.getProductType());
		ksHeader.setPdtName(tBean.getProductName());
		ksHeader.setTrxType("K");
		ksHeader.setTrnAccess("1");
		ksHeader.setPayTel(tBean.getPayTelNo());
		ksHeader.setPayCount("0");
		return ksHeader;
	}
	
	public KSNETHeader getHeader(HeaderBean headerBean,MerchantMngBean merchantMngBean){
		KSNETHeader ksHeader = new KSNETHeader();
		
		ksHeader.setCrypto("0");	
		ksHeader.setSpecVersion("0603");
		ksHeader.setSpecType("0");
		ksHeader.setRetry("0");
		ksHeader.setTrnDate(headerBean.getTrnDate());
		ksHeader.setMerchantId(merchantMngBean.getVanId());
		ksHeader.setTrnsNo(headerBean.getTransactionId());
		//ksHeader.setPayName(tBean.getPayName());
		//ksHeader.setPayEmail(tBean.getPayEmail());
		//ksHeader.setPdtType(tBean.getProductType());
		//ksHeader.setPdtName(tBean.getProductName());
		ksHeader.setTrxType("K");
		ksHeader.setTrnAccess("1");
		//ksHeader.setPayTel(tBean.getPayTelNo());
		ksHeader.setPayCount("0");
		
		return ksHeader;
	}
	
	public KSNETCredit getCredit(HeaderBean headerBean,T001Bean tBean,MerchantMngBean merchantMngBean,MerchantBean merchantBean){
		KSNETCredit kCredit = new KSNETCredit();
		
		kCredit.setReqType("1000");
		kCredit.setPayCondition("1");
		kCredit.setCardTrack(tBean.getCardNumber()+"="+tBean.getCardExpire());
		kCredit.setPeriod("00");
		if(merchantMngBean.getCurType().equals("KRW")){
			kCredit.setCurrency("0");
		}else if(merchantMngBean.getCurType().equals("USD")){
			kCredit.setCurrency("1");
		}else if(merchantMngBean.getCurType().equals("JPY")){
			kCredit.setCurrency("2");
		}
		
		kCredit.setAmount(CommonUtil.convertAmount(CommonUtil.parseDouble(tBean.getAmount()) *10));
		Log.debug("log.day","AMOUNT="+kCredit.getAmount(),this);
		kCredit.setIsBatch("0");
		kCredit.setCardType("1");
		if(CommonUtil.isNullOrSpace(tBean.getDomain())){
			kCredit.setDomain(merchantBean.getHomepage());
		}else{
			kCredit.setDomain(tBean.getDomain());
		}
		
		kCredit.setIpAddress(headerBean.getIpAddress());
		kCredit.setCompanyCode("");
		if(tBean.getCavv().trim().equals("")){
			kCredit.setVisa3d("7");
			kCredit.setCertType("M");
			kCredit.setMpiSource("K");
			kCredit.setMpiCAVV("N");
			String cerValue = "00082"+CommonUtil.setFiller(80)+"06"; 
			kCredit.setCertValue(cerValue);
			Log.debug("log.day","CERT_VALUE = ["+kCredit.getCertValue()+"]",this);
		}else{
			kCredit.setVisa3d("7");
			kCredit.setCertType("M");
			kCredit.setMpiSource("K");
			kCredit.setMpiCAVV("N");
			String cerValue = "00082"+tBean.getCavv().substring(0,82);
			kCredit.setCertValue(cerValue);
			Log.debug("log.day","CERT_VALUE = ["+kCredit.getCertValue()+"]",this);
		}
		 
		return kCredit;
		
	}
	
	public KSNETVoid getVoid(String vanTransactionId){
		KSNETVoid kVoid = new KSNETVoid();
		kVoid.setVoidType("0");						//주문번호 취소
		kVoid.setReqType("1010"); 
		kVoid.setKsnetTrnId(vanTransactionId);
		return kVoid;
	}
	
	
	public byte[] commKsnet(String request)throws Exception{
		
		byte[] response = null;
		SocketManager socket = new SocketManager();
		
		Log.debug("log.day","GSI>>KSNET ["+request+"]",this);
		
		socket.setSocketInfo(configBean.getIp(),configBean.getPort());
		socket.setSocketTimeout(configBean.getTimeout(),configBean.getTimeout());
		
		try{
			response = socket.connect2(4,request.getBytes());
		}catch(Exception e){
			throw new Exception("KSNET Communication Error="+e.getMessage());
		}finally{
			Log.debug("log.day","GSI<<KSNET ["+CommonUtil.toString(response)+"]",this);
		}
		
		return response;
	}
}

/* 
 * Project Name : GSI_PROJECT
 * Project      : GSI_PAYMENT
 * File Name    : com.pgmate.payment.main.Payment.java
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
import com.pgmate.model.db.MerchantMngBean;
import com.pgmate.model.db.dao.MerchantDAO;
import com.pgmate.model.db.dao.MerchantMngDAO;
import com.pgmate.payment.conf.ServerConfigBean;

public class Payment {

	private HeaderBean headerBean 	= null;
	private byte[] request			= null;
	private ServerConfigBean configBean = null;
	
	public Payment(ServerConfigBean configBean,byte[] request){
		this.configBean = configBean;
		this.request	= request;
	}
	
	public byte[] execute(HeaderBean headerBean){
		this.headerBean = headerBean;
		MerchantBean merchantBean = new MerchantDAO().getById(headerBean.getMerchantId());
		ElementCheck elementCheck = new ElementCheck();
		if(!elementCheck.checkElement(headerBean,merchantBean)){
			return elementErrorMessage(elementCheck.resultMsg,"HEADERBEAN");
		}
		
		MerchantMngBean merchantMngBean = new MerchantMngDAO().getById(headerBean.getMerchantId());
		if(CommonUtil.isNullOrSpace(merchantMngBean.getVan()) || CommonUtil.isNullOrSpace(merchantMngBean.getVanId())){
			Log.debug("log.day","[Element Error] = [VAN OR VANID IS NULL]",this);
			return elementErrorMessage("5","T001");
		}
		
		
		String data = "";
		if(headerBean.getTrnType().equals("T001")){
			/*
			if(request.length != 750){
				return elementErrorMessage("0119","T001");
			}*/
			T001Bean tBean = new T001Bean(CommonUtil.toString(request,200));
			if(!elementCheck.checkT001(tBean)){
				return elementErrorMessage(elementCheck.resultMsg,"T001");
			}else{
				T001 t001 = new T001(configBean,merchantBean,merchantMngBean);
				return t001.execute(headerBean, tBean).getBytes();
			}
		}else if(headerBean.getTrnType().equals("T002")){
			T002Bean tBean = new T002Bean(CommonUtil.toString(request,200));
			if(!elementCheck.checkT002(tBean)){
				return elementErrorMessage(elementCheck.resultMsg,"T002");
			}else{
				T002 t002 = new T002(configBean,merchantBean,merchantMngBean);
				return t002.execute(headerBean, tBean).getBytes();
			}
		}else if(headerBean.getTrnType().equals("T003")){
			T003Bean tBean = new T003Bean(CommonUtil.toString(request,200));
			if(!elementCheck.checkT003(tBean)){
				return elementErrorMessage(elementCheck.resultMsg,"T003");
			}else{
				T003 t003 = new T003(configBean,merchantBean,merchantMngBean);
				return t003.execute(headerBean, tBean).getBytes();
			}
		}else if(headerBean.getTrnType().equals("T004")){
			T004Bean tBean = new T004Bean(CommonUtil.toString(request,200));
			if(!elementCheck.checkT004(tBean)){
				return elementErrorMessage(elementCheck.resultMsg,"T003");
			}else{
				T004 t004 = new T004(configBean,merchantBean,merchantMngBean);
				return t004.execute(headerBean, tBean).getBytes();
			}
		}else{
			headerBean.setResultCd("1");
			headerBean.setResultMsg("2");
			data = CommonUtil.toString(request,200);
		}
		
		return headerBean.getTransaction(data).getBytes();
	}
	
	public byte[] elementErrorMessage(String resultMsg,String spec){
		headerBean.setResultCd("2");
		headerBean.setResultMsg(resultMsg);
		Log.debug("log.day","[Element Error] = ["+resultMsg+"]"+spec,this);
		return headerBean.getTransaction(CommonUtil.toString(request,200)).getBytes();
	}
}

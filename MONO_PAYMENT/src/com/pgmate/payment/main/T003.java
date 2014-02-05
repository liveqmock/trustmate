/* 
 * Project Name : GSI_PROJECT
 * Project      : GSI_PAYMENT
 * File Name    : com.pgmate.payment.main.T003.java
 * Date	        : Jan 27, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.payment.main;

import java.util.List;

import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.comm.HeaderBean;
import com.pgmate.model.comm.T003Bean;
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.MerchantMngBean;
import com.pgmate.model.db.TrnsctnBean;
import com.pgmate.model.db.dao.TrnsctnDAO;
import com.pgmate.payment.conf.ServerConfigBean;

public class T003 {

	private ServerConfigBean configBean = null;
	private MerchantBean merchantBean 	= null;
	private MerchantMngBean merchantMngBean = null;
	
	public T003(ServerConfigBean configBean,MerchantBean merchantBean,MerchantMngBean merchantMngBean){
		this.configBean = configBean;
		this.merchantBean = merchantBean;
		this.merchantMngBean = merchantMngBean;
	}
	
	public String execute(HeaderBean headerBean,T003Bean tBean){
		TrnsctnBean trnsctnBean = new TrnsctnBean();
		trnsctnBean.setMerchantId(headerBean.getMerchantId());
		trnsctnBean.setMallId(headerBean.getMallId());
		trnsctnBean.setPayNo(tBean.getRPayNo());
		//trnsctnBean.setTrnReqDate(CommonUtil.stringToTimestamp(CommonUtil.getCurrentDate("yyyyMMdd")+"000000"));
		try{
			List<TrnsctnBean> trnsctnList = new TrnsctnDAO().get(trnsctnBean);
			if(trnsctnList.size() == 0){
				headerBean.setResultCd("1");
				headerBean.setResultMsg("P10O");
			}else{
				trnsctnBean = (TrnsctnBean)trnsctnList.get(0);
				headerBean.setResultCd("0");
				headerBean.setResultMsg("0000");
				tBean.setTrnType("TRN1");
				tBean.setResultCd(trnsctnBean.getResultCd());
				tBean.setResultMsg(trnsctnBean.getResultMsg());
				tBean.setTransactionId(trnsctnBean.getTransactionId());
				tBean.setApprovalNo(trnsctnBean.getApprovalNo());
				tBean.setStatus(trnsctnBean.getTrnStatus());
			}
		}catch(Exception e){
			headerBean.setResultCd("2");
			headerBean.setResultMsg("VAN4");
		}
		headerBean.setTrnResDate(CommonUtil.getCurrentDate("yyyyMMddHHmmss"));
		return headerBean.getTransaction(tBean.getTransaction());
	}
}

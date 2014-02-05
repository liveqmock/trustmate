package com.pgmate.payment.main;

import java.util.HashMap;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.kcp.C_PP_CLI;
import com.pgmate.model.comm.HeaderBean;
import com.pgmate.model.comm.T001Bean;
import com.pgmate.model.db.MerchantMngBean;
import com.pgmate.model.db.TrnsctnDMBean;
import com.pgmate.model.db.dao.TrnsctnDMDAO;
import com.pgmate.payment.kcp.KCPCredit;
import com.pgmate.payment.util.KcpDMUtil;

/**
 * @author Administrator
 *
 */
public class DMThread extends Thread {

	private MerchantMngBean merchantMngBean = null;
	private HeaderBean headerBean = null;
	private T001Bean tBean = null;
	public DMThread(MerchantMngBean merchantMngBean,HeaderBean headerBean, T001Bean tBean){
		this.merchantMngBean = merchantMngBean;
		this.headerBean = headerBean;
		this.tBean = tBean;
	}
	
	public void run(){
		KcpDMUtil kcpDm = new KcpDMUtil();
		TrnsctnDMDAO trnsctnDMDAO = new TrnsctnDMDAO();
		Log.debug("log.day","KCP DM TRANSACTION_ID= "+headerBean.getTransactionId(),this);
		if(kcpDm.getDM(merchantMngBean.getVanId(),headerBean.getTransactionId())){
			HashMap<String,String> dmHashMap = kcpDm.getResult();
			String ekey = (String)dmHashMap.get("ekey");
			if(!ekey.equals("")){
				TrnsctnDMBean trnsctnDMBean = new TrnsctnDMBean();
				trnsctnDMBean.setTransactionId(headerBean.getTransactionId());
				trnsctnDMBean.setEkey(ekey);
				trnsctnDMBean.setEhash((String)dmHashMap.get("ehash"));
				trnsctnDMBean.setAddr1(tBean.getAddr1());
				trnsctnDMBean.setAddr2(tBean.getAddr2());
				trnsctnDMBean.setCity(tBean.getCity());
				trnsctnDMBean.setState(tBean.getState());
				trnsctnDMBean.setCountry(tBean.getCountry());
				trnsctnDMBean.setZip(tBean.getZip());
				
				trnsctnDMDAO.insert(trnsctnDMBean);
				Log.debug("log.day","KCP DM EXECUTE",this);
				C_PP_CLI  dm = new KCPCredit().executeDM(headerBean, tBean, merchantMngBean,dmHashMap);
				
				trnsctnDMBean.setDmNo(dm.mf_get_res("cert_dm_no"));
				trnsctnDMBean.setResultCd(dm.m_res_cd);
				trnsctnDMBean.setDecision(dm.mf_get_res("decision"));
				trnsctnDMBean.setHostCd(dm.mf_get_res("res_host_cd"));
				if(CommonUtil.isNullOrSpace(dm.mf_get_res("res_host_en_msg"))){
					trnsctnDMBean.setResultMsg(dm.m_res_msg);
				}else{
					trnsctnDMBean.setResultMsg(dm.mf_get_res("res_host_en_msg"));
				}
				trnsctnDMBean.setFraudScore(CommonUtil.parseDouble(dm.mf_get_res("fraudScore")));
				
				trnsctnDMDAO.update(trnsctnDMBean);
				
			}else{
				Log.debug("log.day","KCP DM FALSE EKEY IS NULL EXECUTE PAYMENT",this);
			}
		}else{
			Log.debug("log.day","KCP DM ERROR EXE TRUE",this);
		}
	}
}

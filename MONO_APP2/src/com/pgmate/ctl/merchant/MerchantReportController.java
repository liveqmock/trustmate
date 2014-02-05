/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.ctl.merchant.MerchantReportController.java
 * Date	        : Jan 23, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.ctl.merchant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;
import biz.trustnet.common.xml.XMLFactory;

import com.pgmate.ctl.sso.SSOBean;
import com.pgmate.model.db.MerchantMngBean;
import com.pgmate.model.db.MerchantSettleBean;
import com.pgmate.model.db.TrnsctnAcquireBean;
import com.pgmate.model.db.TrnsctnCBBean;
import com.pgmate.model.db.dao.MerchantBillDAO;
import com.pgmate.model.db.dao.MerchantDAO;
import com.pgmate.model.db.dao.MerchantDepositDAO;
import com.pgmate.model.db.dao.MerchantMngDAO;
import com.pgmate.model.db.dao.MerchantSettleDAO;
import com.pgmate.model.db.dao.TrnsctnAcquireDAO;
import com.pgmate.model.db.dao.TrnsctnCBDAO;
import com.pgmate.resource.SettleConfigBean;
import com.pgmate.web.util.AccessUtil;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class MerchantReportController implements Controller {
	
	private SSOBean ssoBean = null;
	
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {

		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		
		try{
			if(param.getSession("sso") == null){
				mav = new ModelAndView("/common/textResponse","RESPONSE","MSG||Your session is about to expire.");
			}else{
				ssoBean = new AccessUtil().get(param);
				if(param.getString("request").equals("clist")){
					mav = cbList(param);
				}else if(param.getString("request").equals("alist")){
					mav = acquire(param);
				}else if(param.getString("request").equals("ilist")){
					mav = invoice(param);
				}else if(param.getString("request").equals("paidView")){
					mav = paidView(param);
				}else{
					request.setAttribute("redirectURL","/main.jsp");
					mav = new ModelAndView("/common/redirect","message","Session Expired. ["+param.getString("request")+"]");
				}
			}	
		}catch(Exception e){
			mav = new ModelAndView("/main");
			Log.debug("log.day","MerchantTrnsctn Controller"+CommonUtil.getExceptionMessage(e),this);
		}
		return mav;
	}
	
	public ModelAndView paidView(ParamUtil param){
		SettleConfigBean configBean = (SettleConfigBean)XMLFactory.getEntity("SETTLE");
		MerchantSettleBean msBean = new MerchantSettleDAO().getBySettleId(param.getString("settleId"));
		setUnit(param, msBean.getMerchantId());
		param.setAttribute("mBean", new MerchantDAO().getById(msBean.getMerchantId()));
		param.setAttribute("merchantBillBean", new MerchantBillDAO().getByMerchantId(msBean.getMerchantId()));
		param.setAttribute("merchantDepositBean", new MerchantDepositDAO().getByIdx(msBean.getDepositIdx()));
		param.setAttribute("configBean", configBean);
		return new ModelAndView("/bill/view", "merchantSettleBean", msBean);
		
	}
	
	public ModelAndView invoice(ParamUtil param){
		
		MerchantSettleBean merchantSettleBean = new MerchantSettleBean();
		MerchantSettleDAO merchantSettleDAO = new MerchantSettleDAO();
		param.toBean(merchantSettleBean);
		param.toBean(merchantSettleDAO);
		param.setDate(merchantSettleDAO);
		
		if(param.getString("format").equals("excel")){
			merchantSettleDAO.pageSize =1000000;
			param.setAttribute("format", param.getString("format"));
			if(!param.isNullOrSpace("startDate") && !param.isNullOrSpace("endDate")){
				long checkDay = CommonUtil.getDifferDays(param.getString("startDate"), param.getString("endDate"));
				if(checkDay > 90){
					return new ModelAndView("/common/textResponse","RESPONSE","MSG||Period of reference excess(Max : 90 Days)");
				}
			}else{
				return new ModelAndView("/common/textResponse","RESPONSE","MSG||Please input period.(Max : 90 Days)");
			}
		}
		
		List<MerchantSettleBean> settleList = merchantSettleDAO.get(" AND MERCHANT_ID ='"+ssoBean.getMemberId()+"' AND STATUS IN ('C','Y')");
		param.setAttribute("settleList",settleList);
		param.setAttribute("dao", merchantSettleDAO);
		new PagingUtil(param, "/merchant/report.do").create();
		return new ModelAndView("/merchant/report/invoiceList");
	}
	
	public ModelAndView acquire(ParamUtil param){
		TrnsctnAcquireBean tAcquireBean = new TrnsctnAcquireBean();
		TrnsctnAcquireDAO tAcquireDAO = new TrnsctnAcquireDAO();
		
		param.toBean(tAcquireBean);
		param.toBean(tAcquireDAO);
		param.setDate(tAcquireDAO);
		
		tAcquireDAO.orderBy = " ORDER BY REG_DATE DESC ";
		tAcquireBean.setTemp1String(ssoBean.getMemberId());
		
	    List<TrnsctnAcquireBean> acquireList = null;
	    if(param.getString("format").equals("excel")){
	    	tAcquireDAO.pageSize =1000000;
			param.setAttribute("format", param.getString("format"));
			if(!param.isNullOrSpace("startDate") && !param.isNullOrSpace("endDate")){
				long checkDay = CommonUtil.getDifferDays(param.getString("startDate"), param.getString("endDate"));
				if(checkDay > 90){
					return new ModelAndView("/common/textResponse","RESPONSE","MSG||Period of reference excess(Max : 90 Days)");
				}
			}else{
				return new ModelAndView("/common/textResponse","RESPONSE","MSG||Please input period.(Max : 90 Days)");
			}
		}
	    acquireList = tAcquireDAO.getByMerchant(tAcquireBean);
		param.setAttribute("trnsctnAcquireBean", tAcquireBean);
	    param.setAttribute("dao", tAcquireDAO);
	   
    	new PagingUtil(param,"/merchant/trnsctn.do").create();
	    return new ModelAndView("/merchant/trnsctn/acquireList","acquireList",acquireList);
	   
	}
	
	public ModelAndView cbList(ParamUtil param){
		TrnsctnCBBean cbBean = new TrnsctnCBBean();
		TrnsctnCBDAO cbDAO = new TrnsctnCBDAO();
		
		param.toBean(cbBean);
		param.toBean(cbDAO);
		param.setDate(cbDAO);
		
		cbDAO.orderBy = " ORDER BY A.REG_DATE DESC ";
		List<TrnsctnCBBean> cbList = null;
		if(param.getString("format").equals("excel")){
			cbDAO.pageSize =1000000;
			param.setAttribute("format", param.getString("format"));
			if(!param.isNullOrSpace("startDate") && !param.isNullOrSpace("endDate")){
				long checkDay = CommonUtil.getDifferDays(param.getString("startDate"), param.getString("endDate"));
				if(checkDay > 90){
					return new ModelAndView("/common/textResponse","RESPONSE","MSG||Period of reference excess(Max : 90 Days)");
				}
			}else{
				return new ModelAndView("/common/textResponse","RESPONSE","MSG||Please input period.(Max : 90 Days)");
			}
		}
		
		
		cbBean.setTemp3String(param.getString("merchantId"));
		cbList = cbDAO.getView(cbBean);
		
		param.setAttribute("dao", cbDAO);
		param.setAttribute("cbBean", cbBean);
		
		new PagingUtil(param,"/merchant/trnsctn.do").create();
		return new ModelAndView("/merchant/trnsctn/cbList","cbList",cbList);
	
	}
	
	
	public void setUnit(ParamUtil param,String merchantId){
		MerchantMngBean mngBean = new MerchantMngDAO().getById(merchantId);
		String payCurType = "";
		if(mngBean.getCurType().equals("USD")){
			payCurType = "$";
		}else if(mngBean.getCurType().equals("JPY")){
			payCurType = "￥";
		}else if(mngBean.getCurType().equals("EUR")){
			payCurType = "€";
		}else{
			
		}
		param.setAttribute("payCurType", payCurType);
		
	}

}

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

import biz.trustnet.common.excel.ExcelUtil;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;
import biz.trustnet.common.xml.XMLFactory;

import com.pgmate.ctl.sso.SSOBean;
import com.pgmate.model.db.DepositBean;
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.MerchantMngBean;
import com.pgmate.model.db.MerchantSettleBean;
import com.pgmate.model.db.SettleBean;
import com.pgmate.model.db.SettleInfoBean;
import com.pgmate.model.db.TrnsctnAcquireBean;
import com.pgmate.model.db.TrnsctnBean;
import com.pgmate.model.db.TrnsctnCBBean;
import com.pgmate.model.db.dao.DepositDAO;
import com.pgmate.model.db.dao.MerchantBillDAO;
import com.pgmate.model.db.dao.MerchantDAO;
import com.pgmate.model.db.dao.MerchantDepositDAO;
import com.pgmate.model.db.dao.MerchantMngDAO;
import com.pgmate.model.db.dao.MerchantSettleDAO;
import com.pgmate.model.db.dao.SettleDAO;
import com.pgmate.model.db.dao.SettleInfoDAO;
import com.pgmate.model.db.dao.TrnsctnAcquireDAO;
import com.pgmate.model.db.dao.TrnsctnCBDAO;
import com.pgmate.model.db.dao.TrnsctnMerchantDAO;
import com.pgmate.payment.util.GSICrypt;
import com.pgmate.resource.SettleConfigBean;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class MerchantReportController implements Controller {
	
	private SSOBean ssoBean = null;
	
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {

		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		
		try{
			if(param.getSession("sso") == null){
				mav = new ModelAndView("/sso/websso");
			}else{
				ssoBean = (SSOBean)param.getSession("sso");
				if(param.getString("request").equals("cblist")){
					mav = cbList(param);
				}else if(param.getString("request").equals("cblistForm")){
					return new ModelAndView("/merchant/report/cbList","endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
				}else if(param.getString("request").equals("acquire")){
					mav = acquire(param);
				}else if(param.getString("request").equals("acquireForm")){
					return new ModelAndView("/merchant/report/acquire","endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
				}else if(param.getString("request").equals("settlement")){
					mav = settlement(param);
				}else if(param.getString("request").equals("settlementForm")){
					return new ModelAndView("/merchant/report/settlement","endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
				}else if(param.getString("request").equals("invoice")){
					mav = invoice(param);
				}else if(param.getString("request").equals("invoiceForm")){
					mav = invoiceForm(param);
					//return new ModelAndView("/merchant/report/invoice");
				}else if(param.getString("request").equals("paidView")){
					mav = paidView(param);
				}else{
					request.setAttribute("redirectURL","/merchant/main.jsp");
					mav = new ModelAndView("/common/redirect","message","Session Expired. ["+param.getString("request")+"]");
				}
			}	
		}catch(Exception e){
			mav = new ModelAndView("/merchant/main");
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
	
	public ModelAndView invoiceForm(ParamUtil param){
		/*
		SettleBean sBean = new SettleBean();
		SettleDAO sDAO = new SettleDAO();
		param.toBean(sBean);
		param.toBean(sDAO);
		
		sDAO.orderBy = " ORDER BY A.IDX DESC ";
		sDAO.pageSize=10000;
		
		//페이지 조건절 처리 
		if(param.isNullOrSpace("idx")){
			param.setAttribute("sList", sDAO.getByViewId(ssoBean.getMemberId()));
		}
		
		param.setAttribute("sBean",sBean);
		return new ModelAndView("/merchant/report/invoiceForm");
		*/
		
		MerchantSettleDAO merchantSettleDAO = new MerchantSettleDAO();
		merchantSettleDAO.pageSize = 10000;
		List<MerchantSettleBean> settleList = merchantSettleDAO.get(" AND MERCHANT_ID ='"+ssoBean.getMemberId()+"' AND STATUS IN ('C','Y')");
		param.setAttribute("settleList",settleList);
		param.setAttribute("dao", merchantSettleDAO);
		new PagingUtil(param, "/merchant/report.do").create();
		return new ModelAndView("/merchant/report/invoiceList");
		
	}
	
	public ModelAndView invoice(ParamUtil param){
		SettleBean sBean = new SettleDAO().getByInvoiceAndYn(param.getString("invoiceIdx"),"Y");
		DepositBean dBean = new DepositDAO().getByIdx(CommonUtil.parseLong(sBean.getDepositIdx()));
		MerchantBean mBean = new MerchantDAO().getById(sBean.getMerchantId());
		param.setAttribute("mBean", mBean);
		param.setAttribute("dBean", dBean);
		return new ModelAndView("/merchant/report/invoice","sBean",sBean);
	}
	
	public ModelAndView settlement(ParamUtil param){
		SettleInfoBean siBean = new SettleInfoBean();
		SettleInfoDAO siDAO	= new SettleInfoDAO();
		
		param.toBean(siBean);
		param.toBean(siDAO);
		param.setDate(siDAO);
		
		siDAO.orderBy = " ORDER BY REG_DATE DESC ";
		siBean.setMerchantId(ssoBean.getMemberId());
		
		List<SettleInfoBean> settlementList = siDAO.get(siBean);
		
		param.setAttribute("siBean", siBean);
		param.setAttribute("dao", siDAO);
		new PagingUtil(param,"/merchant/report.do").create();
	    return new ModelAndView("/merchant/report/settlement","settlementList",settlementList);
		
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
			if(!param.isNullOrSpace("startDate") && !param.isNullOrSpace("endDate")){
				long checkDay = CommonUtil.getDifferDays(param.getString("startDate"), param.getString("endDate"));
				if(checkDay > 90){
					Log.debug("log.day","Period of reference excess(Max : 90 Days)",this);
					return new ModelAndView("/common/redirect","message","[Period of reference excess(Max : 90 Days)]");
				}
			}else{
				Log.debug("log.day","Please input period.(Max : 90 Days)",this);
				return new ModelAndView("/common/redirect","message","[Please input period.(Max : 90 Days)]");
			}
		}
	    acquireList = tAcquireDAO.getByMerchant(tAcquireBean);
		param.setAttribute("trnsctnAcquireBean", tAcquireBean);
	    param.setAttribute("dao", tAcquireDAO);
	    if(param.getString("format").equals("excel")){
	    	return new ModelAndView("/merchant/report/acquireListExl","acquireList",acquireList);
	    }else{
	    	new PagingUtil(param,"/merchant/report.do").create();
		    return new ModelAndView("/merchant/report/acquire","acquireList",acquireList);
	    }
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
			if(!param.isNullOrSpace("startDate") && !param.isNullOrSpace("endDate")){
				long checkDay = CommonUtil.getDifferDays(param.getString("startDate"), param.getString("endDate"));
				if(checkDay > 90){
					Log.debug("log.day","Period of reference excess(Max : 90 Days)",this);
					return new ModelAndView("/common/redirect","message","[Period of reference excess(Max : 90 Days)]");
				}
			}else{
				Log.debug("log.day","Please input period.(Max : 90 Days)",this);
				return new ModelAndView("/common/redirect","message","[Please input period.(Max : 90 Days)]");
			}
		}
		cbList = cbDAO.getMerchantTrnsctncbView(cbBean, ssoBean.getMemberId());
		param.setAttribute("dao", cbDAO);
		param.setAttribute("cbBean", cbBean);
		if(param.getString("format").equals("excel")){
			return new ModelAndView("/merchant/report/cbListExl","cbList",cbList);
		}else{
			new PagingUtil(param,"/merchant/report.do").create();
			return new ModelAndView("/merchant/report/cbList","cbList",cbList);
		}
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

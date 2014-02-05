/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.ctl.merchant.MerchantReportController.java
 * Date	        : Jan 23, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.ctl.group;

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
import com.pgmate.model.db.dao.GroupDAO;
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

public class GroupReportController implements Controller {
	
	private SSOBean ssoBean = null;
	
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {

		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		
		try{
			if(param.getSession("sso") == null){
				mav = new ModelAndView("/sso/websso");
			}else{
				ssoBean = (SSOBean)param.getSession("sso");
				if(param.getString("request").equals("invoice")){
					mav = invoice(param);
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
	
	public ModelAndView invoice(ParamUtil param){
		param.setAttribute("merchantList", new GroupDAO().getByMerchant(ssoBean.getMemberId()));
		List<MerchantSettleBean> settleList = null;
		MerchantSettleDAO merchantSettleDAO = new MerchantSettleDAO();
		param.toBean(merchantSettleDAO);
		
		if(!CommonUtil.isNullOrSpace(param.getString("merchantId"))){
			settleList = merchantSettleDAO.get(" AND MERCHANT_ID ='"+ssoBean.getMemberId()+"' AND STATUS IN ('C','Y')");
			param.setAttribute("merchantId", param.getString("merchantId"));
			
		}
		
		param.setAttribute("settleList",settleList);
		param.setAttribute("dao", merchantSettleDAO);
		new PagingUtil(param, "/group/report.do").create();
		return new ModelAndView("/group/report/invoiceList");
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

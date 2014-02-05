package com.pgmate.ctl.member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;
import biz.trustnet.common.xml.XMLFactory;

import com.pgmate.ctl.sso.SSOBean;
import com.pgmate.model.db.AgentBillBean;
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.MerchantBillBean;
import com.pgmate.model.db.MerchantMngBean;
import com.pgmate.model.db.MerchantSettleBean;
import com.pgmate.model.db.dao.AgentBillDAO;
import com.pgmate.model.db.dao.MerchantBillDAO;
import com.pgmate.model.db.dao.MerchantDAO;
import com.pgmate.model.db.dao.MerchantDepositDAO;
import com.pgmate.model.db.dao.MerchantMngDAO;
import com.pgmate.model.db.dao.MerchantSettleDAO;
import com.pgmate.model.db.dao.TrnsctnDAO;
import com.pgmate.resource.SettleConfigBean;
import com.pgmate.web.util.AccessUtil;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class BillController implements Controller  {
	
	private SSOBean ssoBean = null;
	
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {

		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		
		try{
			if(param.getSession("sso") == null){
				
				mav = new ModelAndView("/common/textResponse","RESPONSE","MSG||Your session is about to expire.");
			}else{
				ssoBean = new AccessUtil().get(param);
				
				if(param.getString("request").equals("add")){
					mav = billAdd(param);
				}else if(param.getString("request").equals("editForm")){
					return new ModelAndView("/bill/billEditForm", "mbBean", new MerchantBillDAO().getByMerchantId(param.getString("merchantId")));
				}else if(param.getString("request").equals("edit")){
					mav = billEdit(param);
				}else if(param.getString("request").equals("paidForm")){
					return new ModelAndView("/bill/paidList");
				}else if(param.getString("request").equals("paidList")){
					mav = paidList(param);
				}else if(param.getString("request").equals("paidView")){
					mav = paidView(param);
				}else if(param.getString("request").equals("invoice")){
					mav = getInvoice(param);
				}else if(param.getString("request").equals("invoiceExcel")){
					mav = invoiceExcel(param);
				}else if(param.getString("request").equals("paidCheck")){
					mav = paidCheck(param);
				}else if(param.getString("request").equals("agentAdd")){
					mav = agentBillAdd(param);
				}else if(param.getString("request").equals("agentEditForm")){
					return new ModelAndView("/bill/agentBillEditForm", "agentBillBean", new AgentBillDAO().getByAgentIdMerchantId(param.getString("agentId"),param.getString("merchantId")));
				}else if(param.getString("request").equals("agentEdit")){
					mav = agentBillEdit(param);
				}else{
					request.setAttribute("redirectURL","/main.jsp");
					mav = new ModelAndView("/common/redirect","message","Session Expired. ["+param.getString("request")+"]");
				}
			}
		}catch(Exception e){
			mav = new ModelAndView("/main");
			Log.debug("log.day","Bill Controller"+CommonUtil.getExceptionMessage(e),this);
		}
		return mav;
	}
	
	public ModelAndView agentBillEdit(ParamUtil param){
		
		AgentBillBean agentBillBean = new AgentBillBean();
		
		param.toBean(agentBillBean);
		
		if(new AgentBillDAO().update(agentBillBean)){
			param.setMessage("OK||Agent ID:"+agentBillBean.getAgentId()+" Merchant ID:"+agentBillBean.getMerchantId()+" Bill Information has been updated.");
		}else{
			param.setMessage("NOK||Agent ID:"+agentBillBean.getAgentId()+" Merchant ID:"+agentBillBean.getMerchantId()+" Bill Information failed to register.");
		}
		return new ModelAndView("/common/ajaxResponse");
	}
	
	public ModelAndView agentBillAdd(ParamUtil param){
		
		AgentBillBean agentBillBean = new AgentBillBean();
		
		param.toBean(agentBillBean);
		
		if(new AgentBillDAO().insert(agentBillBean)){
			param.setMessage("OK||Agent ID:"+agentBillBean.getAgentId()+" Merchant ID:"+agentBillBean.getMerchantId()+" Bill Information has been registered.");
		}else{
			param.setMessage("NOK||Agent ID:"+agentBillBean.getAgentId()+" Merchant ID:"+agentBillBean.getMerchantId()+" Bill Information failed to register.");
		}
		
		return new ModelAndView("/common/ajaxResponse");
	}
	
	public ModelAndView invoiceExcel(ParamUtil param){
		
		MerchantSettleDAO msDAO = new MerchantSettleDAO();
		MerchantSettleBean msBean = msDAO.getBySettleId(param.getString("settleId"));
		
		param.setAttribute("trnsctnList", msDAO.getTrnsctn(msBean.getTrnBillId()));
		param.setAttribute("refundList", msDAO.getTrnsctn(msBean.getRefundBillId()));
		param.setAttribute("cbList", msDAO.getTrnsctn(msBean.getCbBillId()));
		return new ModelAndView("/bill/invoiceExl");
	}
	
	public ModelAndView paidCheck(ParamUtil param){
		
		MerchantSettleDAO msDAO = new MerchantSettleDAO();
		
		if(param.getString("status").equals("C")){
			if(msDAO.updateBySettle(param.getString("settleId"), param.getString("status"))){
				param.setAttribute("RESPONSE", "OK||invoice status has been updated");
			}else{
				param.setAttribute("RESPONSE", "NOK||invoice status failed to update");
			}
		}else if(param.getString("status").equals("Y")){
			if(msDAO.updateBySettleComplete(param.getString("settleId"), param.getString("status"))){
				new TrnsctnDAO().updateSettleStatus(param.getString("settleId"));
				param.setAttribute("RESPONSE", "OK||invoice status has been updated");
			}else{
				param.setAttribute("RESPONSE", "NOK||invoice status failed to update");
			}
		}else{
			param.setAttribute("RESPONSE", "NOK||invoice status failed to update [status is not requested]");
		}
		
		return new ModelAndView("/common/textResponse");
	}
	
	public ModelAndView getInvoice(ParamUtil param){
		
		MerchantDAO merchantDAO = new MerchantDAO();
		merchantDAO.regStartDate = CommonUtil.stringToTimestamp("20130101");
		merchantDAO.pageSize = 10000;
		merchantDAO.orderBy = " ORDER BY MERCHANT_ID ASC";
		List<MerchantBean> merchantList = merchantDAO.get("");
		param.setAttribute("merchantList",merchantList);
		if(!param.isNullOrSpace("merchantId")){
			MerchantSettleDAO merchantSettleDAO = new MerchantSettleDAO();
			merchantSettleDAO.pageSize = 10000;
			List<MerchantSettleBean> settleList = merchantSettleDAO.get(" AND MERCHANT_ID ='"+param.getString("merchantId")+"' AND STATUS IN ('C','Y')");
			param.setAttribute("merchantId", param.getString("merchantId"));
			param.setAttribute("settleList",settleList);
		}
		
		return new ModelAndView("/bill/invoice");
	}
	
	public ModelAndView paidView(ParamUtil param){
		SettleConfigBean configBean = (SettleConfigBean)XMLFactory.getEntity("SETTLE");
		MerchantSettleBean msBean = new MerchantSettleDAO().getBySettleId(param.getString("settleId"));
		setUnit(param, msBean.getMerchantId());
		param.setAttribute("mBean", new MerchantDAO().getById(msBean.getMerchantId()));
		param.setAttribute("merchantBillBean", new MerchantBillDAO().getByMerchantId(msBean.getMerchantId()));
		param.setAttribute("merchantDepositBean", new MerchantDepositDAO().getByIdx(msBean.getDepositIdx()));
		param.setAttribute("configBean", configBean);
		return new ModelAndView("/bill/invoice", "merchantSettleBean", msBean);
		
	}
	
	public ModelAndView paidList(ParamUtil param){
		
		MerchantSettleBean msBean = new MerchantSettleBean();
		MerchantSettleDAO msDAO = new MerchantSettleDAO();
		
		param.toBean(msBean);
		param.toBean(msDAO);
		param.setDate(msDAO);
		
		List<MerchantSettleBean> paidList = null;
		
		if(param.getString("format").equals("excel")){
			msDAO.orderBy = " ORDER BY SETTLE_ID DESC";
			msDAO.pageSize =1000000;
			if(!param.isNullOrSpace("startDate") && !param.isNullOrSpace("endDate")){
				long checkDay = CommonUtil.getDifferDays(param.getString("startDate"), param.getString("endDate"));
				if(checkDay > 90){
					Log.debug("log.day","조회기간 초과입니다.(90일)",this);
					return new ModelAndView("/common/redirect","message","[조회기간 초과입니다.(90일이내)]");
				}
			}else{
				Log.debug("log.day","조회기간을 입력하여 주시기 바랍니다.(90일이내)",this);
				return new ModelAndView("/common/redirect","message","[조회기간을 입력하여 주시기 바랍니다.(90일이내)]");
			}
		}
		
		paidList = msDAO.get(msBean);
		param.setAttribute("msBean", msBean);
		param.setAttribute("paidList", paidList);
		param.setAttribute("dao", msDAO);
		
		new PagingUtil(param,"/bill.do").create();
		if(param.getString("format").equals("excel")){
			return new ModelAndView("/bill/paidListExl");
		}else{
			return new ModelAndView("/bill/paidList");
		}
	}
	
	public ModelAndView billEdit(ParamUtil param){
		
		MerchantBillBean mbBean = new MerchantBillBean();
		
		param.toBean(mbBean);
		
		if(new MerchantBillDAO().update(mbBean)){
			param.setMessage("OK||"+mbBean.getMerchantId()+" Bill Information has been updated.");
		}else{
			param.setMessage("NOK||"+mbBean.getMerchantId()+" Bill Information failed to register.");
		}
		return new ModelAndView("/common/ajaxResponse");
	}
	
	public ModelAndView billAdd(ParamUtil param){
		
		MerchantBillBean mbBean = new MerchantBillBean();
		
		param.toBean(mbBean);
		
		if(new MerchantBillDAO().insert(mbBean)){
			param.setMessage("OK||"+mbBean.getMerchantId()+" Bill Information has been registered.");
		}else{
			param.setMessage("NOK||"+mbBean.getMerchantId()+" Bill Information failed to register.");
		}
		
		return new ModelAndView("/common/ajaxResponse");
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

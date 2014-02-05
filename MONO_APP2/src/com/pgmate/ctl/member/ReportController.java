/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.ctl.member.ReportController.java
 * Date	        : Feb 12, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.ctl.member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pgmate.ctl.sso.SSOBean;
import com.pgmate.model.db.DepositBean;
import com.pgmate.model.db.GSIBean;
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.MerchantDepositBean;
import com.pgmate.model.db.MerchantSettleBean;
import com.pgmate.model.db.SettleBean;
import com.pgmate.model.db.TrnsctnAcquireBean;
import com.pgmate.model.db.TrnsctnBean;
import com.pgmate.model.db.TrnsctnCBBean;
import com.pgmate.model.db.dao.ChartDAO;
import com.pgmate.model.db.dao.DepositDAO;
import com.pgmate.model.db.dao.MerchantDAO;
import com.pgmate.model.db.dao.MerchantDepositDAO;
import com.pgmate.model.db.dao.MerchantSettleDAO;
import com.pgmate.model.db.dao.SettleDAO;
import com.pgmate.model.db.dao.SettleInfoDAO;
import com.pgmate.model.db.dao.TotalReportDAO;
import com.pgmate.model.db.dao.TrnsctnAcquireDAO;
import com.pgmate.model.db.dao.TrnsctnCBDAO;
import com.pgmate.model.db.dao.TrnsctnDAO;
import com.pgmate.model.db.dao.TrnsctnMerchantDAO;
import com.pgmate.payment.util.GSICrypt;
import com.pgmate.resource.GSIResource;
import com.pgmate.web.util.AccessUtil;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class ReportController implements Controller {
	
private SSOBean ssoBean = null;
	 
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
   
		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		
		try{
			if(param.getSession("sso") == null){
				mav = new ModelAndView("/common/textResponse","RESPONSE","MSG||Your session is about to expire.");
			}else{
				ssoBean = new AccessUtil().get(param);
				if(param.getString("request").equals("plist")){
					mav = paidList(param);
				}else if(param.getString("request").equals("update")){
					mav = settleUpdate(param);
				}else if(param.getString("request").equals("alist")){
					mav = acquire(param);
				}else if(param.getString("request").equals("invoice")){
					mav = invoice(param);
				}else if(param.getString("request").equals("fileUpload")){
					param.setAttribute("merchantId", param.getString("merchantId"));
					param.setAttribute("invoiceIdx", param.getString("invoiceIdx"));
					return new ModelAndView("/report/fileUpload","settleIdx", param.getString("settleIdx"));
				}else if(param.getString("request").equals("deposit")){
					param.setAttribute("merchantId", param.getString("merchantId"));
					return new ModelAndView("/report/deposit","settleIdx", param.getString("settleIdx"));
				}else if(param.getString("request").equals("depositUpdate")){
					mav = depositUpdate(param);
				}else if(param.getString("request").equals("reportMonthly")){
					mav = reportMonthly(param);
				}else if(param.getString("request").equals("reportMerchantForm")){
					mav = reportMerchantForm(param);
				}else if(param.getString("request").equals("reportMerchant")){
					mav = reportMerchant(param);
				}else if(param.getString("request").equals("depositList")){
					mav = depositList(param);
				}else if(param.getString("request").equals("depositEditForm")){
					mav = depositEditForm(param);
				}else if(param.getString("request").equals("depositEdit")){
					mav = depositEdit(param);
				}else if(param.getString("request").equals("exportList")){
					mav = exportList(param);
				}else if(param.getString("request").equals("rateList")){
					mav = rateList(param);
				}else{
					request.setAttribute("redirectURL","/main.jsp");
					mav = new ModelAndView("/common/redirect","message","Session Expired. ["+param.getString("request")+"]");
				}
			}	
		}catch(Exception e){
			mav = new ModelAndView("/main");
			Log.debug("log.day","Report Controller"+CommonUtil.getExceptionMessage(e),this);
		}
		return mav;
	}
	
	public ModelAndView rateList(ParamUtil param){
		
		GSIBean gsiBean = new GSIBean();
		TotalReportDAO trDAO = new TotalReportDAO();
		param.toBean(gsiBean);
		param.toBean(trDAO);
		param.setDate(trDAO);
		
		List<GSIBean> rateList = null;
		
		if(param.getString("format").equals("excel")){
			trDAO.pageSize =1000000;
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
		
		rateList = trDAO.getByApprovalRate(gsiBean);
		param.setAttribute("gsiBean", gsiBean);
		param.setAttribute("rateList", rateList);
		param.setAttribute("dao", trDAO);
		
		new PagingUtil(param,"/report.do").create();
		return new ModelAndView("/report/approvalRate");
	}
	
	public ModelAndView exportList(ParamUtil param){
		GSICrypt crypt = new GSICrypt();
		TrnsctnBean trnsctnBean = new TrnsctnBean();
		TrnsctnMerchantDAO trnsctnMerchantDAO = new TrnsctnMerchantDAO();
		
		param.toBean(trnsctnBean);
		param.toBean(trnsctnMerchantDAO);
		param.setDate(trnsctnMerchantDAO);
		
		if(!param.getString("temp1String").equals("")){
			trnsctnBean.setTemp1String(crypt.encrypt(param.getString("temp1String").replaceAll("-","")));
		}
		
		List<TrnsctnBean> trnsctnList = null;
		
		
		if(!param.isNullOrSpace("startDate") && !param.isNullOrSpace("endDate")){
			long checkDay = CommonUtil.getDifferDays(param.getString("startDate"), param.getString("endDate"));
			if(checkDay > 730){
				return new ModelAndView("/common/textResponse","RESPONSE","MSG||Period of reference excess(Max : 2 Year)");
			}
		}else{
			return new ModelAndView("/common/textResponse","RESPONSE","MSG||Please input period.(Max : 2 Year)");
		}
		
		trnsctnList = trnsctnMerchantDAO.getExport(trnsctnBean);
		if(param.isEqual("format", "excel")){
			return new ModelAndView("/report/exportExcel","trnsctnList",trnsctnList);
		}else{
			Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy/MM/dd HH:mm:ss").create();
			return new ModelAndView("/common/jsonResponse","message",gson.toJson(trnsctnList));
		}
	}
	
	public ModelAndView paidList(ParamUtil param){
		
		MerchantSettleBean msBean = new MerchantSettleBean();
		MerchantSettleDAO msDAO = new MerchantSettleDAO();
		
		param.toBean(msBean);
		param.toBean(msDAO);
		param.setDate(msDAO);
		
		/*
		 * 그룹일 경우 그룹 아이디 설정 처리
		 */
		if(ssoBean.getMemberRole().equals(GSIResource.MEMBER_ROLE_GROUP)){
			msBean.setPublicGroupId(ssoBean.getMemberId());
		}
		
		List<MerchantSettleBean> paidList = null;
		
		if(param.getString("format").equals("excel")){
			msDAO.orderBy = " ORDER BY SETTLE_ID DESC";
			msDAO.pageSize =1000000;
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
		
		paidList = msDAO.get(msBean);
		param.setAttribute("msBean", msBean);
		param.setAttribute("paidList", paidList);
		param.setAttribute("dao", msDAO);
		
		new PagingUtil(param,"/report.do").create();
		return new ModelAndView("/report/paidList");
	}
	
	public ModelAndView depositEditForm(ParamUtil param){
		
		MerchantDepositBean mdBean = new MerchantDepositDAO().getByIdx(CommonUtil.parseLong(param.getString("idx")));
		return new ModelAndView("/report/depositEditForm", "mdBean", mdBean);
		
	}
	
	public ModelAndView depositEdit(ParamUtil param){
		if(new MerchantDepositDAO().updateByPayMonth(CommonUtil.parseLong(param.getString("idx")), param.getString("payMonth"), param.getString("comments"))){
			param.setMessage("OK|| Release Month has been updated");
		}else{
			param.setMessage("NOK|| Release Month failed to update");
		}
		return new ModelAndView("/common/ajaxResponse");
	}
	
	public ModelAndView depositList(ParamUtil param){
		
		MerchantDepositBean mdBean = new MerchantDepositBean();
		MerchantDepositDAO mdDAO = new MerchantDepositDAO();
		param.toBean(mdBean);
		param.toBean(mdDAO);
		param.setDate(mdDAO);
		
		/*
		 * 그룹일 경우 그룹 아이디 설정 처리
		 */
		if(ssoBean.getMemberRole().equals(GSIResource.MEMBER_ROLE_GROUP)){
			mdBean.setPublicGroupId(ssoBean.getMemberId());
		}
		
		
		List<MerchantDepositBean> dlist =null;
		
		if(param.getString("format").equals("excel")){
	    	mdDAO.pageSize =1000000;
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
		dlist = mdDAO.get(mdBean);
		param.setAttribute("mdBean", mdBean);
		param.setAttribute("dao",mdDAO);
		
		
		new PagingUtil(param,"/report.do").create();
		return new ModelAndView("/report/depositList","dlist",dlist);
		
		
	}
	
	public ModelAndView reportMonthly(ParamUtil param){
		param.setAttribute("startDate",param.getString("startDate"));
		param.setAttribute("endDate",param.getString("endDate"));
		return new ModelAndView("/report/reportMonthly","reportList",new TotalReportDAO().getByTrnsctnMonthly(param.getString("startDate"),param.getString("endDate")));
	}
	
	public ModelAndView reportMerchantForm(ParamUtil param){
		
		/*
		 * 그룹일 경우 그룹 아이디 설정 처리
		 */
		if(ssoBean.getMemberRole().equals(GSIResource.MEMBER_ROLE_GROUP)){
			param.setAttribute("chartList",new ChartDAO().getByMerchant(ssoBean.getMemberId()));
		}else{
			param.setAttribute("chartList",new ChartDAO().getByMerchant(""));
		}
		
		
		
		return new ModelAndView("/report/reportMerchantForm");
	}
	
	public ModelAndView reportMerchant(ParamUtil param){
		/*
		 * 그룹일 경우 그룹 아이디 설정 처리
		 */
		if(ssoBean.getMemberRole().equals(GSIResource.MEMBER_ROLE_GROUP)){
			param.setAttribute("chartList",new ChartDAO().getByMerchant(ssoBean.getMemberId()));
			param.setAttribute("reportList",new TotalReportDAO().getByGroupPeriod(param.getString("merchantId"), ssoBean.getMemberId()));
		}else{
			param.setAttribute("chartList",new ChartDAO().getByMerchant(""));
			param.setAttribute("reportList",new TotalReportDAO().getByMerchantPeriod(param.getString("merchantId")));
		}
		
		return new ModelAndView("/report/reportMerchant");
	}
	
	public ModelAndView cbEdit(ParamUtil param){
		
		TrnsctnCBDAO tcDAO = new TrnsctnCBDAO();
		TrnsctnCBBean tcBean = tcDAO.getByTransactionId(param.getString("transactionId"));
		
		if(tcDAO.updateState("4", tcBean.getTransactionId())){
			if(new TrnsctnDAO().updateTrnStatus(tcBean.getTransactionId(), tcBean.getRootTrnStatus())){
				param.setAttribute("success","success");
				return new ModelAndView("/common/popupredirect","message","[ChargeBack status update completion] TRANSACTIONID =["+tcBean.getTransactionId() +"]");
			}else{
				Log.debug("log.day","[CB Update] TB_TRNSCTN TRN_STATUS Update failure TRANSACTION_ID :"+tcBean.getTransactionId(),this);
				return new ModelAndView("/common/popupredirect","message","[TRANSACTION TRN_STATUS Update failure] ");
			}
		}else{
			Log.debug("log.day","CB 반려 처리 실패 TRANSACTION_ID :"+tcBean.getTransactionId(),this);
			return new ModelAndView("/common/popupredirect","message","[ChargeBack State Update failure] ");
		}
	}
	
	public ModelAndView cbInfo(ParamUtil param){
		TrnsctnCBBean tcBean = new TrnsctnCBDAO().getByIdx(param.getString("idx"));
		TrnsctnBean trnsctnBean = new TrnsctnDAO().getByTransactionId(tcBean.getTransactionId());
		MerchantBean merchantBean = new MerchantDAO().getById(trnsctnBean.getMerchantId());
		
		param.setAttribute("tcBean",tcBean);
		param.setAttribute("trnsctnBean",trnsctnBean);
		param.setAttribute("merchantBean",merchantBean);
		return new ModelAndView("/report/cbinfo");
	}
	
	public ModelAndView cbAddForm(ParamUtil param){
		String transactionId = "";
		String vanTransactionId = "";
		TrnsctnBean tBean = new TrnsctnBean();
		
		
		if(!CommonUtil.isNullOrSpace(param.getString("transactionId")) || !CommonUtil.isNullOrSpace(param.getString("vanTransactionId"))){
			if(!CommonUtil.isNullOrSpace(param.getString("transactionId"))){
				transactionId = param.getString("transactionId").trim();
				tBean.setTransactionId(transactionId);
			}
			if(!CommonUtil.isNullOrSpace(param.getString("vanTransactionId"))){
				vanTransactionId = param.getString("vanTransactionId").trim();
				tBean.setVanTransactionId(vanTransactionId);
			}
			tBean = new TrnsctnDAO().getByTransactionIdOrVanTransactionId(tBean);
		}
		param.setAttribute("trnsctnBean", tBean);
		return new ModelAndView("/report/cbAddForm");
	}
	
	public ModelAndView cbInsert(ParamUtil param){
		TrnsctnCBBean tcBean = new TrnsctnCBBean();
		param.toBean(tcBean);
		
		if(new TrnsctnCBDAO().insert(tcBean)){
			if(new TrnsctnDAO().updateTrnStatus(tcBean.getTransactionId(), "23")){
				param.setAttribute("redirectURL","/report.do?request=cblist");
				return new ModelAndView("/common/redirect","message","[INSERT SUCCESS]");
			}else{
				param.setAttribute("redirectURL","/report.do?request=cblist");
				return new ModelAndView("/common/redirect","message","[INSERT SUCCESS] [TRANSACTION TRN_STATUS UPDATE FAILURE! TRANSACTION_ID : "+tcBean.getTransactionId()+" ]");
			}
		}else{
			param.setAttribute("redirectURL","/report.do?request=cblist");
			return new ModelAndView("/common/redirect","message","[INSERT FAILURE]");
		}
		
	}
	
	public ModelAndView depositUpdate(ParamUtil param){
		DepositBean dBean = new DepositBean();
		SettleBean sBean = new SettleBean();
		param.toBean(dBean);
		
		if(new DepositDAO().insert(dBean)){
			sBean.setIdx(CommonUtil.parseLong(param.getString("settleIdx")));
			sBean.setDepositIdx(CommonUtil.toString(dBean.getIdx()));
			if(new SettleDAO().updateByDeposit(sBean)){
				param.setAttribute("redirectURL", "/report.do?request=settlementView&merchantId="+sBean.getMerchantId()+"&settleIdx="+sBean.getIdx());
				return new ModelAndView("/common/popupredirect","message","[UPDATE & REGIST SUCCESS]");
			}else{
				return new ModelAndView("/common/popupredirect","message","[UPDATE & REGIST FAILURE]");
			}
		}else{
			return new ModelAndView("/common/popupredirect","message","[UPDATE & REGIST FAILURE]");
		}
	}
	
	public ModelAndView invoice(ParamUtil param){
		MerchantBean mBean = new MerchantBean();
		MerchantDAO mDAO = new MerchantDAO();
		SettleBean sBean = new SettleBean();
		SettleDAO sDAO = new SettleDAO();
		param.toBean(mBean);
		param.toBean(sBean);
		param.toBean(mDAO);
		param.toBean(sDAO);
		
		mDAO.orderBy = " ORDER BY MERCHANT_ID ASC ";
		sDAO.orderBy = " ORDER BY A.IDX DESC ";
		mDAO.pageSize=10000;
		sDAO.pageSize=10000;
		
		//페이지 조건절 처리 
		if(param.isNullOrSpace("merchantId")){
			param.setAttribute("merchantList", mDAO.get(""));
		}else{
			param.setAttribute("merchantList", mDAO.get(""));
			param.setAttribute("sList", sDAO.getByViewId(sBean.getMerchantId()));
		}
		
		param.setAttribute("mBean",mBean);
		param.setAttribute("sBean",sBean);
		return new ModelAndView("/report/invoiceForm");
	}
	
	public ModelAndView settleUpdate(ParamUtil param){
		SettleBean sBean = new SettleBean();
		sBean = new SettleDAO().getByInvoiceIdx(param.getString("invoiceIdx"));
		
		if(!sBean.getSettleYn().equals("Y")){ //이미 정산확정일 경우 처리 필요
			if(new SettleInfoDAO().settleYnUpdate(param.getString("invoiceIdx"), "Y")){
				if(new SettleDAO().updateBySettleYn(param.getString("invoiceIdx"))){
					param.setAttribute("redirectURL", "/report.do?request=settlement");
					return new ModelAndView("/common/redirect","message","[정산확정 처리되었습니다.]");
				}else{
					param.setAttribute("redirectURL","/report.do?request=settlementForm");
					return new ModelAndView("/common/redirect","message","[정산 확정 처리가 실패되었습니다.]");
				}
			}else{
				param.setAttribute("redirectURL","/report.do?request=settlementForm");
				return new ModelAndView("/common/redirect","message","[정산내역 확정 처리가 실패되었습니다.]");
			}
		}else{
			param.setAttribute("redirectURL","/report.do?request=settlementForm");
			return new ModelAndView("/common/redirect","message","[이미 정산확정 처리되었습니다.]");
		}
	}
	
	public ModelAndView acquire(ParamUtil param){
		TrnsctnAcquireBean tAcquireBean = new TrnsctnAcquireBean();
		TrnsctnAcquireDAO tAcquireDAO = new TrnsctnAcquireDAO();
		
		param.toBean(tAcquireBean);
		param.toBean(tAcquireDAO);
		param.setDate(tAcquireDAO);
		
		tAcquireDAO.orderBy = " ORDER BY REG_DATE DESC ";
		
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
	    
	    acquireList = tAcquireDAO.get(tAcquireBean);
		param.setAttribute("trnsctnAcquireBean", tAcquireBean);
	    param.setAttribute("dao", tAcquireDAO);
	    new PagingUtil(param,"/report.do").create();
	    return new ModelAndView("/report/acquireList","acquireList",acquireList);
	}
	
	
	
	
}

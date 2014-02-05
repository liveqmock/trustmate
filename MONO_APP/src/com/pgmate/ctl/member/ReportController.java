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

import com.pgmate.ctl.sso.SSOBean;
import com.pgmate.model.db.DepositBean;
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.MerchantDepositBean;
import com.pgmate.model.db.SettleBean;
import com.pgmate.model.db.SettleInfoBean;
import com.pgmate.model.db.TrnsctnAcquireBean;
import com.pgmate.model.db.TrnsctnBean;
import com.pgmate.model.db.TrnsctnCBBean;
import com.pgmate.model.db.dao.DepositDAO;
import com.pgmate.model.db.dao.MerchantDAO;
import com.pgmate.model.db.dao.MerchantDepositDAO;
import com.pgmate.model.db.dao.SettleDAO;
import com.pgmate.model.db.dao.SettleInfoDAO;
import com.pgmate.model.db.dao.TotalReportDAO;
import com.pgmate.model.db.dao.TrnsctnAcquireDAO;
import com.pgmate.model.db.dao.TrnsctnCBDAO;
import com.pgmate.model.db.dao.TrnsctnDAO;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class ReportController implements Controller {
	
private SSOBean ssoBean = null;
	 
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
   
		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		
		try{
			if(param.getSession("sso") == null){
				mav = new ModelAndView("/sso/websso");
			}else{
				ssoBean = (SSOBean)param.getSession("sso");
				if(param.getString("request").equals("settlement")){
					mav = settlement(param);
				}else if(param.getString("request").equals("settlementForm")){
					return new ModelAndView("/report/settlement","endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
				}else if(param.getString("request").equals("settlementView")){
					mav = settlementView(param);
				}else if(param.getString("request").equals("update")){
					mav = settleUpdate(param);
				}else if(param.getString("request").equals("cblist")){
					mav = cbList(param);
				}else if(param.getString("request").equals("cblistForm")){
					return new ModelAndView("/report/cbList","endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
				}else if(param.getString("request").equals("cbAdd")){
					mav = cbInsert(param);
				}else if(param.getString("request").equals("cbAddForm")){
					mav = cbAddForm(param);
				}else if(param.getString("request").equals("cbInfo")){
					mav = cbInfo(param);
				}else if(param.getString("request").equals("cbEdit")){
					mav = cbEdit(param);
				}else if(param.getString("request").equals("acquire")){
					mav = acquire(param);
				}else if(param.getString("request").equals("acquireForm")){
					return new ModelAndView("/report/acquire","endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
				}else if(param.getString("request").equals("invoiceForm")){
					mav = invoiceForm(param);
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
				}else if(param.getString("request").equals("reportMerchant")){
					mav = reportMerchant(param);
				}else if(param.getString("request").equals("depositList")){
					mav = depositList(param);
				}else if(param.getString("request").equals("depositEditForm")){
					mav = depositEditForm(param);
				}else if(param.getString("request").equals("depositEdit")){
					mav = depositEdit(param);
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
	
public ModelAndView depositEditForm(ParamUtil param){
		
		MerchantDepositBean mdBean = new MerchantDepositDAO().getByIdx(CommonUtil.parseLong(param.getString("idx")));
		return new ModelAndView("/report/depositEdit", "mdBean", mdBean);
		
	}
	
	public ModelAndView depositEdit(ParamUtil param){
		
		if(new MerchantDepositDAO().updateByPayMonth(CommonUtil.parseLong(param.getString("idx")), param.getString("payMonth"), param.getString("comments"))){
			param.setAttribute("redirectURL","/report.do?request=depositList");
			return new ModelAndView("/common/redirect","message","[UPDATE SUCCESS]");
		}else{
			param.setAttribute("redirectURL","/report.do?request=depositList");
			return new ModelAndView("/common/redirect","message","[UPDATE FAILURE]");
		}
	}
	
	public ModelAndView depositList(ParamUtil param){
		
		MerchantDepositBean mdBean = new MerchantDepositBean();
		MerchantDepositDAO mdDAO = new MerchantDepositDAO();
		param.toBean(mdBean);
		param.toBean(mdDAO);
		param.setDate(mdDAO);
		
		List<MerchantDepositBean> dlist =null;
		
		if(param.getString("format").equals("excel")){
			mdDAO.pageSize =1000000;
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
		dlist = mdDAO.get(mdBean);
		param.setAttribute("mdBean", mdBean);
		param.setAttribute("dao",mdDAO);
		
		if(param.getString("format").equals("excel")){
			return new ModelAndView("/report/depositListExl","dlist",dlist);
		}else{
			new PagingUtil(param,"/report.do").create();
			return new ModelAndView("/report/depositList","dlist",dlist);
		}
		
	}
	
	public ModelAndView reportMonthly(ParamUtil param){
		param.setAttribute("startMon",param.getString("startMon"));
		param.setAttribute("endMon",param.getString("endMon"));
		return new ModelAndView("/report/reportMonthly","reportList",new TotalReportDAO().getByTrnsctnMonthly(param.getString("startMon"),param.getString("endMon")));
	}
	
	public ModelAndView reportMerchant(ParamUtil param){
		param.setAttribute("merchantId",param.getString("merchantId"));
		MerchantDAO merchantDAO = new MerchantDAO();
		merchantDAO.pageSize = 1000;
		merchantDAO.orderBy = " ORDER BY MERCHANT_ID ASC ";
		param.setAttribute("merchantList",merchantDAO.get(""));
		return new ModelAndView("/report/reportMerchant","reportList",new TotalReportDAO().getByMerchantPeriod(param.getString("merchantId")));
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
	
	public ModelAndView invoiceForm(ParamUtil param){
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
	
	public ModelAndView invoice(ParamUtil param){
		SettleBean sBean = new SettleDAO().getByInvoiceAndYn(param.getString("invoiceIdx"),"Y");
		DepositBean dBean = new DepositDAO().getByIdx(CommonUtil.parseLong(sBean.getDepositIdx()));
		MerchantBean mBean = new MerchantDAO().getById(sBean.getMerchantId());
		param.setAttribute("mBean", mBean);
		param.setAttribute("dBean", dBean);
		return new ModelAndView("/report/invoiceView","sBean",sBean);
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
	
	public ModelAndView settlementView(ParamUtil param){
		DepositBean dBean = new DepositBean();
		dBean.setMerchantId(param.getString("merchantId"));
		List<SettleInfoBean> siList = new SettleInfoDAO().getByInvoiceIdx(param.getString("invoiceIdx"));
		List<DepositBean> dList = new DepositDAO().get(dBean);
		SettleBean sBean = new SettleDAO().getByInvoiceIdx(param.getString("invoiceIdx"));
		param.setAttribute("sBean",sBean);
		param.setAttribute("invoiceIdx", param.getString("invoiceIdx"));
		param.setAttribute("merchantId", dBean.getMerchantId());
		param.setAttribute("dList",dList);
		param.setAttribute("fileCheck", param.getString("fileCheck"));
		param.setAttribute("fileName", param.getString("fileName"));
		return new ModelAndView("/report/settlementView","siList",siList);
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
	    
	    acquireList = tAcquireDAO.get(tAcquireBean);
		param.setAttribute("trnsctnAcquireBean", tAcquireBean);
	    param.setAttribute("dao", tAcquireDAO);
	    if(param.getString("format").equals("excel")){
	    	return new ModelAndView("/report/acquireListExl","acquireList",acquireList);
	    }else{
	    	new PagingUtil(param,"/report.do").create();
		    return new ModelAndView("/report/acquire","acquireList",acquireList);
	    }
	}
	
	public ModelAndView cbList(ParamUtil param){
		TrnsctnCBBean cbBean = new TrnsctnCBBean();
		TrnsctnCBDAO cbDAO = new TrnsctnCBDAO();
		
		param.toBean(cbBean);
		param.toBean(cbDAO);
		param.setDate(cbDAO);
		
		cbDAO.orderBy = " ORDER BY REG_DATE DESC ";
		
		List<TrnsctnCBBean> cbList = null;
		if(param.getString("format").equals("excel")){
			cbDAO.pageSize =1000000;
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
		cbList = cbDAO.getView(cbBean);
		param.setAttribute("dao", cbDAO);
		param.setAttribute("cbBean", cbBean);
		if(param.getString("format").equals("excel")){
			return new ModelAndView("/report/cbListExl","cbList",cbList);
		}else{
			new PagingUtil(param,"/report.do").create();
			return new ModelAndView("/report/cbList","cbList",cbList);
		}
	}
	
	public ModelAndView settlement(ParamUtil param){
		SettleBean sBean = new SettleBean();
		SettleDAO sDAO = new SettleDAO();
		param.toBean(sBean);
		param.toBean(sDAO);
		param.setDate(sDAO);
		
		List<SettleBean> settlementList = null;
		if(param.getString("format").equals("excel")){
			sDAO.pageSize =1000000;
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
		
		settlementList = sDAO.getView(sBean);
		param.setAttribute("sBean", sBean);
		param.setAttribute("dao", sDAO);
		if(param.getString("format").equals("excel")){
			return new ModelAndView("/report/settleListExl","settlementList",settlementList);
		}else{
			new PagingUtil(param,"/report.do").create();
			return new ModelAndView("/report/settlement","settlementList",settlementList);
		}
	}

}

/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.ctl.member.TrnsctnController.java
 * Date	        : Feb 2, 2009
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
import com.pgmate.model.db.TrnsctnBean;
import com.pgmate.model.db.TrnsctnLogBean;
import com.pgmate.model.db.TrnsctnVoidBean;
import com.pgmate.model.db.dao.CodeDAO;
import com.pgmate.model.db.dao.CommentTrnsctnDAO;
import com.pgmate.model.db.dao.GSISEQDAO;
import com.pgmate.model.db.dao.MerchantDAO;
import com.pgmate.model.db.dao.TrnsctnAuthDAO;
import com.pgmate.model.db.dao.TrnsctnDAO;
import com.pgmate.model.db.dao.TrnsctnLogDAO;
import com.pgmate.model.db.dao.TrnsctnMerchantDAO;
import com.pgmate.model.db.dao.TrnsctnSCRDAO;
import com.pgmate.model.db.dao.TrnsctnVoidDAO;
import com.pgmate.payment.util.Comment;
import com.pgmate.payment.util.GSICrypt;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class TrnsctnController implements Controller {
	
	private SSOBean ssoBean = null;
	
	
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		
		try{
			if(param.getSession("sso") == null){
				mav = new ModelAndView("/sso/websso");
			}else{
				ssoBean = (SSOBean)param.getSession("sso");
				if(param.getString("request").equals("list")){
					mav = trnsctnList(param);
				}else if(param.getString("request").equals("listKrw")){
					mav = trnsctnListKrw(param);
				}else if(param.getString("request").equals("listUsd")){
					mav = trnsctnListUsd(param);
				}else if(param.getString("request").equals("listEur")){
					mav = trnsctnListEur(param);
				}else if(param.getString("request").equals("listForm")){
					return new ModelAndView("/trnsctn/trnsctnList","endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
				}else if(param.getString("request").equals("listFormKrw")){
					return new ModelAndView("/trnsctn/trnsctnListKrw","endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
				}else if(param.getString("request").equals("listFormUsd")){
					return new ModelAndView("/trnsctn/trnsctnListUsd","endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
				}else if(param.getString("request").equals("listFormEur")){
					return new ModelAndView("/trnsctn/trnsctnListEur","endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
				}else if(param.getString("request").equals("view")){
					mav = trnsctnInfo(param);
				}else if(param.getString("request").equals("void")){
					mav = trnsctnVoidInsert(param);
				}else if(param.getString("request").equals("log")){
					mav = logList(param);
				}else if(param.getString("request").equals("logForm")){
					return new ModelAndView("/trnsctn/trnsctnLog","endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
				}else if(param.getString("request").equals("authForm")){
					return new ModelAndView("/trnsctn/authList","endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
				}else if(param.getString("request").equals("listAuth")){
					mav = authList(param);
				}else{
					request.setAttribute("redirectURL","/main.jsp");
					mav = new ModelAndView("/common/redirect","message","Session Expired. ["+param.getString("request")+"]");
				}
			}	
		}catch(Exception e){
			mav = new ModelAndView("/main");
			Log.debug("log.day","Trnsctn Controller"+CommonUtil.getExceptionMessage(e),this);
		}
		return mav;
	}
	
	public ModelAndView logList(ParamUtil param){
		TrnsctnLogBean tLogBean = new TrnsctnLogBean();
		TrnsctnLogDAO tLogDAO = new TrnsctnLogDAO();
		
		param.toBean(tLogBean);
		param.toBean(tLogDAO);
		param.setDate(tLogDAO); 
		
		List<TrnsctnLogBean> tLogList = tLogDAO.getTrnsctnLog(tLogBean);
		
		param.setAttribute("dao", tLogDAO);
		param.setAttribute("tLogBean",tLogBean);
		new PagingUtil(param,"/trnsctn.do").create();
		return new ModelAndView("/trnsctn/trnsctnLog", "tLogList", tLogList);
	}
	
	public ModelAndView trnsctnVoidInsert(ParamUtil param){
		TrnsctnVoidBean trnsctnvoidBean = new TrnsctnVoidBean();
		param.toBean(trnsctnvoidBean);
		trnsctnvoidBean.setVoidVanTransactionId(param.getString("vanTransactionId"));
		trnsctnvoidBean.setVoidAmount(CommonUtil.parseDouble(param.getString("amount")));
		trnsctnvoidBean.setRootTrnStatus(param.getString("status"));
		trnsctnvoidBean.setRegDate(CommonUtil.getCurrentTimestamp());
		
		trnsctnvoidBean.setVoidTransactionId(new GSISEQDAO().getTRNNOSEQ());					//void_transactionid 생성
		
		if(new TrnsctnVoidDAO().insert(trnsctnvoidBean)){							            //취소접수입력
			if(!new TrnsctnDAO().updateTrnStatus(trnsctnvoidBean.getTransactionId(),"17")){		//취소대기상태변경
				Log.debug("log.day","취소대기상태 변경 실패",this);
			}
			new Comment().setTrnsctnComment(trnsctnvoidBean.getCommentIdx(),ssoBean.getMemberId(),param.getString("comment"));	//취소접수메모.
			Log.debug("log.day","신용카드 거래 취소  접수 완료",this);
			param.setAttribute("success","success");
			return new ModelAndView("/common/popupredirect","message","[Credit card transaction cancellation acceptance completion] TRANSACTIONID =["+trnsctnvoidBean.getTransactionId() +"]");
		}else{
			Log.debug("log.day","신용카드 거래취소 접수 실패",this);
			return new ModelAndView("/common/popupredirect","message","[Credit card transaction cancellation acceptance failure] ");
		}
		
	}
	
	public ModelAndView trnsctnInfo(ParamUtil param){
		param.setAttribute("trnsctnSCRBean", new TrnsctnSCRDAO().getByTransactionId(param.getString("transactionId")));
		TrnsctnBean trnsctnBean = new TrnsctnDAO().getByTransactionId(param.getString("transactionId"));
		param.setAttribute("resultMsg",new CodeDAO().getByAliasCode("KAPPV_CODE",trnsctnBean.getResultMsg()).getKrValue());
		param.setAttribute("trnsctnBean",new TrnsctnDAO().getByTransactionId(param.getString("transactionId")));
		param.setAttribute("merchantBean",new MerchantDAO().getById(trnsctnBean.getMerchantId()));
		TrnsctnVoidBean trnsctnVoidBean = new TrnsctnVoidDAO().getByTransactionId(trnsctnBean.getTransactionId());
		param.setAttribute("trnsctnVoidBean",trnsctnVoidBean);
		param.setAttribute("voidComment", new CommentTrnsctnDAO().getByIdx(trnsctnVoidBean.getCommentIdx()).getComments());
		return new ModelAndView("/trnsctn/trnsctninfo");
	}
	
	public ModelAndView trnsctnList(ParamUtil param){
		GSICrypt crypt = new GSICrypt();
		TrnsctnBean trnsctnBean = new TrnsctnBean();
		TrnsctnMerchantDAO trnsctnMerchantDAO = new TrnsctnMerchantDAO();
		
		param.toBean(trnsctnBean);
		param.toBean(trnsctnMerchantDAO);
		param.setDate(trnsctnMerchantDAO);
		
		if(!param.getString("temp1String").equals("")){
			trnsctnBean.setTemp1String(crypt.encrypt(param.getString("temp1String")));
		}
		
		//기본값 설정 
		trnsctnBean.setCurType("JPY");
		List<TrnsctnBean> trnsctnList = null;
		if(param.getString("format").equals("excel")){
			trnsctnMerchantDAO.pageSize =1000000;
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
		
		trnsctnList = trnsctnMerchantDAO.get(trnsctnBean);
		param.setAttribute("trnsctnBean", trnsctnBean);
		
		param.setAttribute("trnsctnList", trnsctnList);
		param.setAttribute("dao", trnsctnMerchantDAO);
		param.setAttribute("searchAmount", trnsctnMerchantDAO.getSearchAmount());
		param.setAttribute("currentTrnsctnBean", trnsctnMerchantDAO.getMonthlyTransaction(trnsctnBean.getCurType()));
		new PagingUtil(param,"/trnsctn.do").create();
		if(param.getString("format").equals("excel")){
			return new ModelAndView("/trnsctn/trnsctnListExl");
		}else{
			return new ModelAndView("/trnsctn/trnsctnList");
		}
		
		
		
	}
	
	public ModelAndView trnsctnListKrw(ParamUtil param){
		GSICrypt crypt = new GSICrypt();
		TrnsctnBean trnsctnBean = new TrnsctnBean();
		TrnsctnMerchantDAO trnsctnMerchantDAO = new TrnsctnMerchantDAO();
		
		param.toBean(trnsctnBean);
		param.toBean(trnsctnMerchantDAO);
		param.setDate(trnsctnMerchantDAO);
		
		if(!param.getString("temp1String").equals("")){
			trnsctnBean.setTemp1String(crypt.encrypt(param.getString("temp1String")));
		}
		//기본값 설정 
		trnsctnBean.setCurType("KRW");
		
		List<TrnsctnBean> trnsctnList = null;
		if(param.getString("format").equals("excel")){
			trnsctnMerchantDAO.pageSize =1000000;
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
		
		trnsctnList = trnsctnMerchantDAO.get(trnsctnBean);
		param.setAttribute("trnsctnBean", trnsctnBean);
		
		param.setAttribute("trnsctnList", trnsctnList);
		param.setAttribute("dao", trnsctnMerchantDAO);
		param.setAttribute("searchAmount", trnsctnMerchantDAO.getSearchAmount());
		param.setAttribute("currentTrnsctnBean", trnsctnMerchantDAO.getMonthlyTransaction(trnsctnBean.getCurType()));
		new PagingUtil(param,"/trnsctn.do").create();
		if(param.getString("format").equals("excel")){
			return new ModelAndView("/trnsctn/trnsctnListExlKrw");
		}else{
			return new ModelAndView("/trnsctn/trnsctnListKrw");
		}
		
		
		
	}
	
	public ModelAndView trnsctnListUsd(ParamUtil param){
		GSICrypt crypt = new GSICrypt();
		TrnsctnBean trnsctnBean = new TrnsctnBean();
		TrnsctnMerchantDAO trnsctnMerchantDAO = new TrnsctnMerchantDAO();
		
		param.toBean(trnsctnBean);
		param.toBean(trnsctnMerchantDAO);
		param.setDate(trnsctnMerchantDAO);
		
		if(!param.getString("temp1String").equals("")){
			trnsctnBean.setTemp1String(crypt.encrypt(param.getString("temp1String")));
		}
		
		//기본값 설정 
		trnsctnBean.setCurType("USD");
		
		List<TrnsctnBean> trnsctnList = null;
		if(param.getString("format").equals("excel")){
			trnsctnMerchantDAO.pageSize =1000000;
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
		
		trnsctnList = trnsctnMerchantDAO.get(trnsctnBean);
		param.setAttribute("trnsctnBean", trnsctnBean);
		
		param.setAttribute("trnsctnList", trnsctnList);
		param.setAttribute("dao", trnsctnMerchantDAO);
		param.setAttribute("searchAmount", trnsctnMerchantDAO.getSearchAmount());
		param.setAttribute("currentTrnsctnBean", trnsctnMerchantDAO.getMonthlyTransaction(trnsctnBean.getCurType()));
		new PagingUtil(param,"/trnsctn.do").create();
		if(param.getString("format").equals("excel")){
			return new ModelAndView("/trnsctn/trnsctnListExlUsd");
		}else{
			return new ModelAndView("/trnsctn/trnsctnListUsd");
		}
		
		
		
	}
	
	public ModelAndView trnsctnListEur(ParamUtil param){
		GSICrypt crypt = new GSICrypt();
		TrnsctnBean trnsctnBean = new TrnsctnBean();
		TrnsctnMerchantDAO trnsctnMerchantDAO = new TrnsctnMerchantDAO();
		
		param.toBean(trnsctnBean);
		param.toBean(trnsctnMerchantDAO);
		param.setDate(trnsctnMerchantDAO);
		
		if(!param.getString("temp1String").equals("")){
			trnsctnBean.setTemp1String(crypt.encrypt(param.getString("temp1String")));
		}
		
		//기본값 설정 
		trnsctnBean.setCurType("EUR");
		
		List<TrnsctnBean> trnsctnList = null;
		if(param.getString("format").equals("excel")){
			trnsctnMerchantDAO.pageSize =1000000;
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
		
		trnsctnList = trnsctnMerchantDAO.get(trnsctnBean);
		param.setAttribute("trnsctnBean", trnsctnBean);
		
		param.setAttribute("trnsctnList", trnsctnList);
		param.setAttribute("dao", trnsctnMerchantDAO);
		param.setAttribute("searchAmount", trnsctnMerchantDAO.getSearchAmount());
		param.setAttribute("currentTrnsctnBean", trnsctnMerchantDAO.getMonthlyTransaction(trnsctnBean.getCurType()));
		new PagingUtil(param,"/trnsctn.do").create();
		if(param.getString("format").equals("excel")){
			return new ModelAndView("/trnsctn/trnsctnListExlEur");
		}else{
			return new ModelAndView("/trnsctn/trnsctnListEur");
		}
		
		
		
	}
	
	public ModelAndView authList(ParamUtil param){
		GSICrypt crypt = new GSICrypt();
		TrnsctnBean trnsctnBean = new TrnsctnBean();
		TrnsctnAuthDAO trnsctnAuthDAO = new TrnsctnAuthDAO();
		

		trnsctnBean.setAuth("Y");
		
		param.toBean(trnsctnBean);
		param.toBean(trnsctnAuthDAO);
		param.setDate(trnsctnAuthDAO);
		
		if(!param.getString("temp1String").equals("")){
			trnsctnBean.setTemp1String(crypt.encrypt(param.getString("temp1String")));
		}
		
		
		List<TrnsctnBean> trnsctnList = null;
		if(param.getString("format").equals("excel")){
			trnsctnAuthDAO.pageSize =1000000;
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
		
		trnsctnList = trnsctnAuthDAO.get(trnsctnBean);
		param.setAttribute("trnsctnBean", trnsctnBean);
		
		param.setAttribute("trnsctnList", trnsctnList);
		param.setAttribute("dao", trnsctnAuthDAO);
		param.setAttribute("searchAmount", trnsctnAuthDAO.getSearchAmount());
		param.setAttribute("currentTrnsctnBean", trnsctnAuthDAO.getMonthlyTransaction());
		new PagingUtil(param,"/trnsctn.do").create();
		if(param.getString("format").equals("excel")){
			return new ModelAndView("/trnsctn/authListExl");
		}else{
			return new ModelAndView("/trnsctn/authList");
		}
		
		
		
	}
	


}

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
import com.pgmate.model.db.AgentBean;
import com.pgmate.model.db.GroupBean;
import com.pgmate.model.db.TrnsctnBean;
import com.pgmate.model.db.TrnsctnCBBean;
import com.pgmate.model.db.TrnsctnLogBean;
import com.pgmate.model.db.TrnsctnVoidBean;
import com.pgmate.model.db.dao.AgentDAO;
import com.pgmate.model.db.dao.CodeDAO;
import com.pgmate.model.db.dao.CommentTrnsctnDAO;
import com.pgmate.model.db.dao.GSISEQDAO;
import com.pgmate.model.db.dao.GroupDAO;
import com.pgmate.model.db.dao.MerchantDAO;
import com.pgmate.model.db.dao.TrnsctnAuthDAO;
import com.pgmate.model.db.dao.TrnsctnCBDAO;
import com.pgmate.model.db.dao.TrnsctnDAO;
import com.pgmate.model.db.dao.TrnsctnLogDAO;
import com.pgmate.model.db.dao.TrnsctnMerchantDAO;
import com.pgmate.model.db.dao.TrnsctnSCRDAO;
import com.pgmate.model.db.dao.TrnsctnVoidDAO;
import com.pgmate.payment.util.Comment;
import com.pgmate.payment.util.GSICrypt;
import com.pgmate.resource.GSIResource;
import com.pgmate.web.util.AccessUtil;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class TrnsctnController implements Controller {
	
	private SSOBean ssoBean = null;
	
	
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		  
		try{  
			if(param.getSession("sso") == null){
				mav = new ModelAndView("/common/textResponse","RESPONSE","MSG||Your session is about to expire.");
			}else{ 
				ssoBean = new AccessUtil().get(param);
				if(param.getString("request").equals("list")){
					mav = trnsctnList(param);
				}else if(param.getString("request").equals("view")){
					mav = trnsctnInfo(param); 
				}else if(param.getString("request").equals("void")){
					mav = trnsctnVoidInsert(param); 
				}else if(param.getString("request").equals("log")){
					mav = logList(param); 
				}else if(param.getString("request").equals("logForm")){
					return new ModelAndView("/trnsctn/trnsctnLog","endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
				}else if(param.getString("request").equals("authList")){
					mav = listAuth(param);
				}else if(param.getString("request").equals("cbList")){
					mav = cbList(param);
				}else if(param.getString("request").equals("rlist")){
					mav = rList(param);
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
	
	public ModelAndView rList(ParamUtil param){
		
		TrnsctnVoidBean trnsctnVoidBean = new TrnsctnVoidBean();
		TrnsctnVoidDAO trnsctnVoidDAO = new TrnsctnVoidDAO();
		
		param.toBean(trnsctnVoidBean);
		param.toBean(trnsctnVoidDAO);
		param.setDate(trnsctnVoidDAO);
		
		/*
		 * 그룹일 경우 그룹 아이디 설정 처리
		 */
		if(ssoBean.getMemberRole().equals(GSIResource.MEMBER_ROLE_GROUP)){
			trnsctnVoidBean.setPublicGroupId(ssoBean.getMemberId());
		}
		
		List<TrnsctnVoidBean> voidList = null;
		if(!param.getString("format").equals("list")){
			trnsctnVoidDAO.pageSize =1000000;
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
		
		voidList = trnsctnVoidDAO.getView(trnsctnVoidBean);
		
		param.setAttribute("voidList", voidList);
		param.setAttribute("searchAmount", trnsctnVoidDAO.getSumAmount());
		param.setAttribute("dao", trnsctnVoidDAO);
		new PagingUtil(param,"/trnsctn.do").create();
		return new ModelAndView("/trnsctn/refundDetail");
	}
	 
	public ModelAndView trnsctnList(ParamUtil param){
		GSICrypt crypt = new GSICrypt();
		TrnsctnBean trnsctnBean = new TrnsctnBean();
		TrnsctnMerchantDAO trnsctnMerchantDAO = new TrnsctnMerchantDAO();
		
		param.toBean(trnsctnBean);
		param.toBean(trnsctnMerchantDAO);
		param.setDate(trnsctnMerchantDAO);
		
		if(!param.getString("temp1String").equals("")){
			trnsctnBean.setTemp1String(crypt.encrypt(param.getString("temp1String").replaceAll("-","")));
		}
		/*
		 * 그룹일 경우 그룹 아이디 설정 처리
		 */
		if(ssoBean.getMemberRole().equals(GSIResource.MEMBER_ROLE_GROUP)){
			trnsctnBean.setPublicGroupId(ssoBean.getMemberId());
		}
		
		List<TrnsctnBean> trnsctnList = null;
		if(!param.getString("format").equals("list")){
			trnsctnMerchantDAO.pageSize =1000000;
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
		trnsctnList = trnsctnMerchantDAO.get(trnsctnBean);
		param.setAttribute("trnsctnBean", trnsctnBean);
		
		param.setAttribute("trnsctnList", trnsctnList);
		param.setAttribute("dao", trnsctnMerchantDAO);
		param.setAttribute("searchAmount", trnsctnMerchantDAO.getSearchAmount());

		param.setAttribute("currentTrnsctnBean", trnsctnMerchantDAO.getMonthlyTransaction(trnsctnBean.getCurType()));
		new PagingUtil(param,"/trnsctn.do").create();
		
		return new ModelAndView("/trnsctn/trnsctnDetail");
		
	}
	
	public ModelAndView logList(ParamUtil param){
		TrnsctnLogBean tLogBean = new TrnsctnLogBean();
		TrnsctnLogDAO tLogDAO = new TrnsctnLogDAO();
		
		param.toBean(tLogBean);
		param.toBean(tLogDAO);
		param.setDate(tLogDAO); 
		
		/*
		 * 그룹일 경우 그룹 아이디 설정 처리
		 */
		if(ssoBean.getMemberRole().equals(GSIResource.MEMBER_ROLE_GROUP)){
			tLogBean.setPublicGroupId(ssoBean.getMemberId());
		}
		
		List<TrnsctnLogBean> tLogList = tLogDAO.getTrnsctnLog(tLogBean);
		
		param.setAttribute("dao", tLogDAO);
		param.setAttribute("tLogBean",tLogBean);
		new PagingUtil(param,"/trnsctn.do").create();
		return new ModelAndView("/trnsctn/logDetail", "tLogList", tLogList);
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
		param.setAttribute("resultMsg",new CodeDAO().getByAliasCode("KAPPV_CODE",trnsctnBean.getResultMsg()).getEnValue());
		param.setAttribute("trnStatus",new CodeDAO().getByAliasCode("TRN_STATUS",trnsctnBean.getTrnStatus()).getEnValue());
		param.setAttribute("trnsctnBean",trnsctnBean);
		param.setAttribute("merchantBean",new MerchantDAO().getById(trnsctnBean.getMerchantId()));
		TrnsctnVoidBean trnsctnVoidBean = new TrnsctnVoidDAO().getByTransactionId(trnsctnBean.getTransactionId());
		
		param.setAttribute("trnsctnVoidBean",trnsctnVoidBean);
		param.setAttribute("voidCommentList", new CommentTrnsctnDAO().getByIdx(CommonUtil.toString(trnsctnVoidBean.getCommentIdx())));
		param.setAttribute("voidTrnStatus",new CodeDAO().getByAliasCode("TRN_STATUS",trnsctnVoidBean.getRootTrnStatus()).getEnValue());
		
		TrnsctnCBBean trnsctnCbBean = new TrnsctnCBDAO().getByTransactionId(trnsctnBean.getTransactionId());
		param.setAttribute("trnsctnCbBean",trnsctnCbBean);
		param.setAttribute("cbCommentList", new CommentTrnsctnDAO().getByIdx(CommonUtil.toString(trnsctnCbBean.getCommentIdx())));
		param.setAttribute("cbTrnStatus",new CodeDAO().getByAliasCode("TRN_STATUS",trnsctnCbBean.getRootTrnStatus()).getEnValue());
		
		
		if(trnsctnBean.getTransactionId().equals("")){
			return new ModelAndView("/trnsctn/trnsctnIsNotValid");
		}else{
			if(ssoBean.getMemberRole().equals(GSIResource.MEMBER_ROLE_MEMBER)){
				return new ModelAndView("/trnsctn/trnsctnInfo");
			}else if(ssoBean.getMemberRole().equals(GSIResource.MEMBER_ROLE_MERCHANT)){	
				if(ssoBean.getMemberId().equals(trnsctnBean.getMerchantId())){
					return new ModelAndView("/trnsctn/trnsctnInfo");
				}else{
					return new ModelAndView("/trnsctn/trnsctnIsNotValid");
				}
			}else if(ssoBean.getMemberRole().equals(GSIResource.MEMBER_ROLE_GROUP)){
				GroupBean groupBean = new GroupDAO().getByMerchantId(trnsctnBean.getMerchantId());
				
				if(ssoBean.getMemberId().equals(groupBean.getGroupId())){
					return new ModelAndView("/trnsctn/trnsctnInfo");
				}else{
					return new ModelAndView("/trnsctn/trnsctnIsNotValid");
				}
			}else if(ssoBean.getMemberRole().equals(GSIResource.MEMBER_ROLE_AGENT)){
				AgentBean agentBean = new AgentDAO().getByMerchantId(trnsctnBean.getMerchantId());
				
				if(ssoBean.getMemberId().equals(agentBean.getAgentId())){
					return new ModelAndView("/trnsctn/trnsctnInfo");
				}else{
					return new ModelAndView("/trnsctn/trnsctnIsNotValid");
				}
			}else{
				return new ModelAndView("/trnsctn/trnsctnIsNotValid");
			}
		}
	}
	
	
	
	
	public ModelAndView listAuth(ParamUtil param){
		GSICrypt crypt = new GSICrypt();
		TrnsctnBean trnsctnBean = new TrnsctnBean();
		TrnsctnAuthDAO trnsctnAuthDAO = new TrnsctnAuthDAO();
		

		trnsctnBean.setAuth("Y");
		
		param.toBean(trnsctnBean);
		param.toBean(trnsctnAuthDAO);
		param.setDate(trnsctnAuthDAO);
		
		if(!param.getString("temp1String").equals("")){
			trnsctnBean.setTemp1String(crypt.encrypt(param.getString("temp1String").replaceAll("-","")));
		}
		
		/*
		 * 그룹일 경우 그룹 아이디 설정 처리
		 */
		if(ssoBean.getMemberRole().equals(GSIResource.MEMBER_ROLE_GROUP)){
			trnsctnBean.setPublicGroupId(ssoBean.getMemberId());
		}
		
		List<TrnsctnBean> trnsctnList = null;
		if(!param.getString("format").equals("list")){
			trnsctnAuthDAO.pageSize =1000000;
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
		
		trnsctnList = trnsctnAuthDAO.get(trnsctnBean);
		param.setAttribute("trnsctnBean", trnsctnBean);
		
		param.setAttribute("trnsctnList", trnsctnList);
		param.setAttribute("dao", trnsctnAuthDAO);
		param.setAttribute("searchAmount", trnsctnAuthDAO.getSearchAmount());
		param.setAttribute("currentTrnsctnBean", trnsctnAuthDAO.getMonthlyTransaction());
		new PagingUtil(param,"/trnsctn.do").create();
		return new ModelAndView("/trnsctn/trnsctnDetail");
		
	}
	
	public ModelAndView cbList(ParamUtil param){
		TrnsctnCBBean cbBean = new TrnsctnCBBean();
		TrnsctnCBDAO cbDAO = new TrnsctnCBDAO();
		
		param.toBean(cbBean);
		param.toBean(cbDAO);
		param.setDate(cbDAO);
		
		/*
		 * 그룹일 경우 그룹 아이디 설정 처리
		 */
		if(ssoBean.getMemberRole().equals(GSIResource.MEMBER_ROLE_GROUP)){
			cbBean.setPublicGroupId(ssoBean.getMemberId());
		}
		
		cbDAO.orderBy = " ORDER BY REG_DATE DESC ";
		
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
		cbList = cbDAO.getView(cbBean);
		param.setAttribute("dao", cbDAO);
		param.setAttribute("cbBean", cbBean);
		new PagingUtil(param,"/trnsctn.do").create();
		return new ModelAndView("/trnsctn/cbList","cbList",cbList);
	}
	
	
	


}

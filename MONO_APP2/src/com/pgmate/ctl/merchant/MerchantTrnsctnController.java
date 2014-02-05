/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.ctl.merchant.MerchantTrnsctnController.java
 * Date	        : Jan 21, 2009
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
import com.pgmate.web.util.AccessUtil;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class MerchantTrnsctnController implements Controller {
	
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
				}else if(param.getString("request").equals("authList")){
					mav = authList(param);
				}else if(param.getString("request").equals("log")){
					mav = logList(param); 
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
	
	public ModelAndView logList(ParamUtil param){
		TrnsctnLogBean tLogBean = new TrnsctnLogBean();
		TrnsctnLogDAO tLogDAO = new TrnsctnLogDAO();
		
		param.toBean(tLogBean);
		param.toBean(tLogDAO);
		param.setDate(tLogDAO); 
		
		tLogBean.setMerchantId(ssoBean.getMemberId());
		
		List<TrnsctnLogBean> tLogList = tLogDAO.getTrnsctnLog(tLogBean);
		
		param.setAttribute("dao", tLogDAO);
		param.setAttribute("tLogBean",tLogBean);
		new PagingUtil(param,"/merchant/trnsctn.do").create();
		return new ModelAndView("/merchant/trnsctn/logDetail", "tLogList", tLogList);
	}
	
	public ModelAndView trnsctnVoidInsert(ParamUtil param){
		TrnsctnVoidBean trnsctnvoidBean = new TrnsctnVoidBean();
		param.toBean(trnsctnvoidBean);
		trnsctnvoidBean.setVoidVanTransactionId(param.getString("vanTransactionId"));
		trnsctnvoidBean.setVoidAmount(CommonUtil.parseDouble(param.getString("amount")));
		trnsctnvoidBean.setRootTrnStatus(param.getString("status"));
		trnsctnvoidBean.setRegDate(CommonUtil.getCurrentTimestamp());
		
		trnsctnvoidBean.setVoidTransactionId(new GSISEQDAO().getTRNNOSEQ());					//void_transactionid 생성
		
		if(new TrnsctnVoidDAO().insert(trnsctnvoidBean)){							//취소 insert
			if(!new TrnsctnDAO().updateTrnStatus(trnsctnvoidBean.getTransactionId(),"17")){		//취소상태변경
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
		param.setAttribute("trnsctnBean",new TrnsctnDAO().getByTransactionId(param.getString("transactionId")));
		param.setAttribute("merchantBean",new MerchantDAO().getById(trnsctnBean.getMerchantId()));
		TrnsctnVoidBean trnsctnVoidBean = new TrnsctnVoidDAO().getByTransactionId(trnsctnBean.getTransactionId());
		param.setAttribute("trnsctnVoidBean",trnsctnVoidBean);
		param.setAttribute("voidComment", new CommentTrnsctnDAO().getByIdx(trnsctnVoidBean.getCommentIdx()).getComments());
		return new ModelAndView("/merchant/trnsctn/trnsctnInfo");
	}
	
	public ModelAndView trnsctnList(ParamUtil param){
		GSICrypt crypt = new GSICrypt();
		TrnsctnBean trnsctnBean = new TrnsctnBean();
		TrnsctnMerchantDAO trnsctnMerchantDAO = new TrnsctnMerchantDAO();
		
		param.toBean(trnsctnBean);
		param.toBean(trnsctnMerchantDAO);
		param.setDate(trnsctnMerchantDAO);
		
		
		trnsctnBean.setMerchantId(ssoBean.getMemberId());
		//환율 정보 처리 
		trnsctnBean.setCurType(ssoBean.getCurType());
		//인증이 아닌 데이터
		trnsctnBean.setAuth("N");
		if(!param.getString("temp1String").equals("")){
			trnsctnBean.setTemp1String(crypt.encrypt(param.getString("temp1String").replaceAll("-","")));
		}
		
		List<TrnsctnBean> trnsctnList = null;
		if(param.getString("format").equals("excel")){
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
		param.setAttribute("dao", trnsctnMerchantDAO);
		param.setAttribute("searchAmount", trnsctnMerchantDAO.getSearchAmount());
		
		new PagingUtil(param,"/merchant/trnsctn.do").create();
		return new ModelAndView("/merchant/trnsctn/trnsctnDetail","trnsctnList",trnsctnList);
	
	
	}
	
	public ModelAndView authList(ParamUtil param){
		GSICrypt crypt = new GSICrypt();
		TrnsctnBean trnsctnBean = new TrnsctnBean();
		TrnsctnAuthDAO trnsctnAuthDAO = new TrnsctnAuthDAO();
		
		param.toBean(trnsctnBean);
		param.toBean(trnsctnAuthDAO);
		param.setDate(trnsctnAuthDAO);
		
		
		trnsctnBean.setMerchantId(ssoBean.getMemberId());
		//환율 정보 처리 
		trnsctnBean.setCurType(ssoBean.getCurType());
		//인증이 아닌 데이터
		trnsctnBean.setAuth("Y");
		if(!param.getString("temp1String").equals("")){
			trnsctnBean.setTemp1String(crypt.encrypt(param.getString("temp1String").replaceAll("-","")));
		}
		
		List<TrnsctnBean> trnsctnList = null;
		if(param.getString("format").equals("excel")){
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
		param.setAttribute("dao", trnsctnAuthDAO);
		param.setAttribute("searchAmount", trnsctnAuthDAO.getSearchAmount());
		
		new PagingUtil(param,"/merchant/trnsctn.do").create();
		return new ModelAndView("/merchant/trnsctn/trnsctnDetail","trnsctnList",trnsctnList);
		
	
	}

}

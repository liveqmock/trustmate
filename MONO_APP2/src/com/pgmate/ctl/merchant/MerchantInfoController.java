/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.ctl.merchant.MerchantInfoController.java
 * Date	        : Jan 28, 2009
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

import biz.trustnet.common.cipher.MDEncoder;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.mail.EMail;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.ctl.sso.SSOBean;
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.NoticeBean;
import com.pgmate.model.db.QuestionBean;
import com.pgmate.model.db.dao.DepositDAO;
import com.pgmate.model.db.dao.MerchantBillDAO;
import com.pgmate.model.db.dao.MerchantDAO;
import com.pgmate.model.db.dao.MerchantMngDAO;
import com.pgmate.model.db.dao.NoticeDAO;
import com.pgmate.model.db.dao.QuestionDAO;
import com.pgmate.web.util.AccessUtil;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class MerchantInfoController implements Controller {
	
private SSOBean ssoBean = null;
	
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {

		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		
		try{
			if(param.getSession("sso") == null){
				mav = new ModelAndView("/common/textResponse","RESPONSE","MSG||Your session is about to expire.");
			}else{
				ssoBean = new AccessUtil().get(param);
				if(param.getString("request").equals("info")){
					mav = merchantInfo(param);
				}else if(param.getString("request").equals("notice")){
					mav = notice(param);
				}else if(param.getString("request").equals("question")){
					return new ModelAndView("/merchant/info/question");
					//mav = question(param);
				}else if(param.getString("request").equals("mailSend")){
					mav = merchantMailSend(param);
				}else if(param.getString("request").equals("passwordModi")){
					mav = merchantPasswordModi(param);
				}else if(param.getString("request").equals("modify")){
					mav = merchantModify(param);
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
	
	public ModelAndView merchantModify(ParamUtil param){
		
		MerchantBean merchantBean = new MerchantBean();
		param.toBean(merchantBean);
		
		merchantBean= new MerchantDAO().getById(ssoBean.getMemberId());
		
		if(merchantBean.getPassword().equals(new MDEncoder().encodeSHA1(param.getString("password"))) && !param.getString("uppassword").equals("")){
			merchantBean.setPassword(new MDEncoder().encodeSHA1(param.getString("uppassword")));
			merchantBean.setPwUpdate("Y");
			Log.debug("log.day","change password  : "+param.getString("uppassword") + "  & MerchantId : "+ssoBean.getMemberId(),this);
			if(new MerchantDAO().pwupdate(merchantBean)){	
				param.setAttribute("merchantBean",merchantBean);
				param.setAttribute("redirectURL","/merchant/info.do?request=info");
				return new ModelAndView("/common/redirect","message","[UPDATE SUCCESS]");
			}else{
				param.setAttribute("redirectURL","/merchant/info.do?request=info");
				return new ModelAndView("/common/redirect","message","[UPDATE FAILURE]");
			}
		}else{
			param.setAttribute("redirectURL","/merchant/info.do?request=info");
			return new ModelAndView("/common/redirect","message","[The Original passwd is incorrect. To input again..]");
		}
	}
	
	public ModelAndView merchantPasswordModi(ParamUtil param){
		param.setAttribute("merchantBean", new MerchantDAO().getById(ssoBean.getMemberId()));
		return new ModelAndView("/merchant/info/passwordUpdate");
	}
	
	public ModelAndView merchantMailSend(ParamUtil param){
		QuestionBean qBean = new QuestionBean();
		QuestionDAO qDAO = new QuestionDAO();
		
		param.toBean(qBean);
		param.toBean(qDAO);
		param.setDate(qDAO);
		
		qBean.setMerchantId(ssoBean.getMemberId());
		qBean.setActive("Y");
		if(qDAO.insert(qBean)){
			EMail m = new EMail();
			
			try{
				Log.debug("log.day","MAIL SEND ="+param.getString("memberName"),this);
				m.setFrom(qBean.getEmail(),param.getString("memberName"));
				m.setToSubject("redsky@panworld-net.com","[PANWORLD SERVICE PAGE] :"+param.getString("subject"));
				m.setText("EMAIL=["+qBean.getEmail()+"]\n"+param.getString("contents"));
				m.sendEMail();
				param.setAttribute("redirectURL","/merchant/info.do?request=info");
				return new ModelAndView("/common/redirect","message","[An E-Mail has been sent.]");
			}catch(Exception e){
				Log.debug("log.day","MAIL ERROR="+CommonUtil.getExceptionMessage(e),this);
				param.setAttribute("redirectURL","/merchant/info.do?request=question");
				return new ModelAndView("/common/redirect","message","[E-Mail has not been sent. Please send to the address above.] : ["+e.getMessage()+"]");
			}
		}else{
			param.setAttribute("redirectURL","/merchant/info.do?request=question");
			return new ModelAndView("/common/redirect","message","[Question has not been sent to PANWORLD] : ["+qBean.getSubject()+"]");
		}
		
	}
	
	public ModelAndView question(ParamUtil param){
		QuestionBean qBean = new QuestionBean();
		QuestionDAO qDAO = new QuestionDAO();
		
		param.toBean(qBean);
		param.toBean(qDAO);
		param.setDate(qDAO);
		
		List<QuestionBean> qList = qDAO.get(qBean);
		
		param.setAttribute("dao", qDAO);
		new PagingUtil(param,"/merchant/info.do").create();
		return new ModelAndView("/merchant/info/question","qList",qList);
	}
	
	public ModelAndView notice(ParamUtil param){
		NoticeBean nBean = new NoticeBean();
		NoticeDAO nDAO = new NoticeDAO();
		
		param.toBean(nBean);
		param.toBean(nDAO);
		param.setDate(nDAO);
		nBean.setActive("Y");
		List<NoticeBean> nList = nDAO.get(nBean);
		
		param.setAttribute("dao", nDAO);
		new PagingUtil(param,"/merchant/info.do").create();
		return new ModelAndView("/merchant/info/notice","nList",nList);
	}
	
	public ModelAndView merchantInfo(ParamUtil param){
		MerchantBean  mBean = new MerchantBean();
		MerchantDAO mDAO = new MerchantDAO();
		
		param.toBean(mBean);
		param.toBean(mDAO);
		param.setDate(mDAO);
		
		mBean.setMerchantId(ssoBean.getMemberId());
		mBean = mDAO.getById(mBean.getMerchantId());
		
		param.setAttribute("dBean", new DepositDAO().getByMerchant(mBean.getMerchantId()));
		param.setAttribute("mngBean", new MerchantMngDAO().getById(mBean.getMerchantId()));
		//param.setAttribute("mbBean", new MerchantBillDAO().getByMerchantId(mBean.getMerchantId()));
		return new ModelAndView("/merchant/info/merchantView","mBean",mBean);
	}
	

}

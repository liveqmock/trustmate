/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.ctl.merchant.MerchantTrnsctnController.java
 * Date	        : Jan 21, 2009
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

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.ctl.sso.SSOBean;
import com.pgmate.model.db.TrnsctnBean;
import com.pgmate.model.db.dao.TrnsctnAuthDAO;
import com.pgmate.model.db.dao.TrnsctnMerchantDAO;
import com.pgmate.payment.util.GSICrypt;
import com.pgmate.web.util.AccessUtil;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class GroupTrnsctnController implements Controller {
	
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
				}else if(param.getString("request").equals("authList")){
					mav = authList(param);
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
	

	
	public ModelAndView authList(ParamUtil param){
		GSICrypt crypt = new GSICrypt();
		TrnsctnBean trnsctnBean = new TrnsctnBean();
		TrnsctnAuthDAO trnsctnAuthDAO = new TrnsctnAuthDAO();
		
		param.toBean(trnsctnBean);
		param.toBean(trnsctnAuthDAO);
		param.setDate(trnsctnAuthDAO);
		
		
		trnsctnBean.setTemp3String(ssoBean.getMemberId());
		//환율 정보 처리 
		
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
		
		new PagingUtil(param,"/group/trnsctn.do").create();
		return new ModelAndView("/group/trnsctn/trnsctnDetail","trnsctnList",trnsctnList);
		
	
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
		
		trnsctnBean.setPublicGroupId(ssoBean.getMemberId());
		
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
		param.setAttribute("dao", trnsctnMerchantDAO);
		param.setAttribute("searchAmount", trnsctnMerchantDAO.getSearchAmount());

		new PagingUtil(param,"/group/trnsctn.do").create();
		
		return new ModelAndView("/group/trnsctn/trnsctnDetail","trnsctnList",trnsctnList);
		
	}
	
	

}

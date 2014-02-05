/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.ctl.merchant.MerchantInfoController.java
 * Date	        : Jan 28, 2009
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
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.MerchantMngBean;
import com.pgmate.model.db.dao.GroupDAO;
import com.pgmate.model.db.dao.MerchantDAO;
import com.pgmate.model.db.dao.MerchantMngDAO;
import com.pgmate.web.util.AccessUtil;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class GroupInfoController implements Controller {
	
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
					mav = merchantList(param);
				}else if(param.getString("request").equals("merchantView")){
					mav = merchantView(param);
				}else if(param.getString("request").equals("info")){
					mav = info(param);
				}else{
					request.setAttribute("redirectURL","/main.jsp");
					mav = new ModelAndView("/common/redirect","message","Session Expired. ["+param.getString("request")+"]");
				}
			}	
		}catch(Exception e){
			mav = new ModelAndView("/main");
			Log.debug("log.day","GroupMerchantTrnsctn Controller"+CommonUtil.getExceptionMessage(e),this);
		}
		return mav;
	}
	
	public ModelAndView info(ParamUtil param){
		
		return new ModelAndView("/group/info/GroupView","gBean",new GroupDAO().getById(ssoBean.getMemberId()));
	}
	
	public ModelAndView merchantList(ParamUtil param){
		param.setAttribute("merchantList", new GroupDAO().getByMerchant(ssoBean.getMemberId()));
		
		MerchantDAO merchantDAO = new MerchantDAO();
		MerchantBean merchantBean = new MerchantBean();
		param.toBean(merchantBean);
		param.toBean(merchantDAO);
		//GROUP 정보 적용
		merchantBean.setPublicGroupId(ssoBean.getMemberId());
		merchantDAO.orderBy = " ORDER BY MERCHANT_ID ASC ";
		List<MerchantBean> merchantList = merchantDAO.get(merchantBean);
		
		param.setAttribute("merchantBean", merchantBean);
		param.setAttribute("dao", merchantDAO);
		if(param.getString("format").equals("excel")){
			param.setAttribute("format", param.getString("format"));
		}
		new PagingUtil(param,"/group/info.do").create();
		return new ModelAndView("/group/info/merchantList","mList",merchantList);
	
	
	}
	
	public ModelAndView merchantView(ParamUtil param){
		MerchantBean merchantBean = new MerchantDAO().getById(param.getString("merchantId"));
		MerchantMngBean merchantMngBean = new MerchantMngDAO().getById(param.getString("merchantId"));
		param.setAttribute("mBean", merchantBean);
		param.setAttribute("mngBean", merchantMngBean);
		return new ModelAndView("/group/info/merchantView");
	
	
	}


}

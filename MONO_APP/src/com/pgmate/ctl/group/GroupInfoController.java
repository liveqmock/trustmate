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
import com.pgmate.model.db.NoticeBean;
import com.pgmate.model.db.QuestionBean;
import com.pgmate.model.db.dao.DepositDAO;
import com.pgmate.model.db.dao.GroupDAO;
import com.pgmate.model.db.dao.MerchantDAO;
import com.pgmate.model.db.dao.MerchantMngDAO;
import com.pgmate.model.db.dao.NoticeDAO;
import com.pgmate.model.db.dao.QuestionDAO;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class GroupInfoController implements Controller {
	
private SSOBean ssoBean = null;
	
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {

		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		
		try{
			if(param.getSession("sso") == null){
				mav = new ModelAndView("/sso/websso");
			}else{
				ssoBean = (SSOBean)param.getSession("sso");
				if(param.getString("request").equals("merchantList")){
					mav = merchantList(param);
				}else if(param.getString("request").equals("merchantView")){
					mav = merchantView(param);
				}else{
					request.setAttribute("redirectURL","/group/main.jsp");
					mav = new ModelAndView("/common/redirect","message","Session Expired. ["+param.getString("request")+"]");
				}
			}	
		}catch(Exception e){
			mav = new ModelAndView("/group/main");
			Log.debug("log.day","GroupMerchantTrnsctn Controller"+CommonUtil.getExceptionMessage(e),this);
		}
		return mav;
	}
	
	public ModelAndView merchantList(ParamUtil param){
		param.setAttribute("merchantList", new GroupDAO().getByMerchant(ssoBean.getMemberId()));
		
		MerchantDAO merchantDAO = new MerchantDAO();
		MerchantBean merchantBean = new MerchantBean();
		param.toBean(merchantBean);
		param.toBean(merchantDAO);
		//GROUP 정보 적용
		merchantBean.setTemp3String(ssoBean.getMemberId());
		merchantDAO.orderBy = " ORDER BY MERCHANT_ID ASC ";
		List<MerchantBean> merchantList = merchantDAO.get(merchantBean);
		
		param.setAttribute("merchantBean", merchantBean);
		param.setAttribute("dao", merchantDAO);
		
		new PagingUtil(param,"/group/info.do").create();
		return new ModelAndView("/group/merchant/list","merchantList",merchantList);
	
	
	}
	
	public ModelAndView merchantView(ParamUtil param){
		MerchantBean merchantBean = new MerchantDAO().getById(param.getString("merchantId"));
		MerchantMngBean merchantMngBean = new MerchantMngDAO().getById(param.getString("merchantId"));
		param.setAttribute("merchantBean", merchantBean);
		param.setAttribute("merchantMngBean", merchantMngBean);
		return new ModelAndView("/group/merchant/view");
	
	
	}


}

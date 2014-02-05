/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.ctl.sso.AuthenticationController.java
 * Date	        : Jan 19, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.ctl.sso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import biz.trustnet.common.cipher.MDEncoder;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;
import biz.trustnet.common.xml.XMLFactory;

import com.pgmate.model.db.GroupBean;
import com.pgmate.model.db.MemberBean;
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.MerchantMngBean;
import com.pgmate.model.db.dao.GroupDAO;
import com.pgmate.model.db.dao.MemberDAO;
import com.pgmate.model.db.dao.MerchantDAO;
import com.pgmate.model.db.dao.MerchantMngDAO;
import com.pgmate.resource.CustomizingBean;
import com.pgmate.resource.GSIResource;
import com.pgmate.web.util.ParamUtil;

public class AuthenticationController implements Controller{
	
	private String message = "Welcome to pgmate";
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		ParamUtil param = new ParamUtil(request);
		SSOBean ssoBean = new SSOBean();
		param.toBean(ssoBean);
		
		ModelAndView mav = null;
		try{
			if(setLogin(request,ssoBean)){
				message = "Welcome to pgmate";
				mav = new ModelAndView(ssoBean.getTargetURL(),"message",message);
			}else{
				//request.setAttribute("redirectURL",ssoBean.getErrorURL());
				//mav = new ModelAndView("/common/redirect","message",message);
				mav = new ModelAndView("/sso/websso","message",message);
			}
		}catch(Exception e){
			Log.debug("log.day","Login Error"+CommonUtil.getExceptionMessage(e),this);
		}
		return mav;
	}
	
	public boolean setLogin(HttpServletRequest request,SSOBean ssoBean){
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(10*60);
		CustomizingBean customizingBean = (CustomizingBean)XMLFactory.getEntity("CUSTOMER");
		//패스워드 MD5 SHA1 으로 인코드 한다.
		ssoBean.setMemberPassword(new MDEncoder().encodeSHA1(ssoBean.getMemberPassword()));
		
		MemberBean memberBean = new MemberDAO().getById(ssoBean.getMemberId());
		if(!memberBean.getMemberId().equals("")){
			if(memberBean.getPassword().equals(ssoBean.getMemberPassword())){
				if(memberBean.getActive().equals("1")){
					ssoBean.setActive(memberBean.getActive());
					ssoBean.setMemberName(memberBean.getName());
					ssoBean.setMemberGrade(memberBean.getMemberGrade());
					ssoBean.setMemberRole(GSIResource.MEMBER_ROLE_MEMBER);
					ssoBean.setPwUpdate("Y");
					ssoBean.setRegDate(memberBean.getRegDate());
					ssoBean.setTargetURL("/trnsctn/trnsctnListUsd");
					session.setAttribute("sso",ssoBean);
					session.setAttribute("customizingBean", customizingBean);
					return true;
				}else{
					message = "The username has expired";
					return false;
				}	
			}else{
				message = "The password you entered is incorrect";
				return false;
			}
		}else{
			GroupBean groupBean = new GroupDAO().getById(ssoBean.getMemberId());
			MerchantBean merchantBean = new MerchantDAO().getById(ssoBean.getMemberId());
			if(!groupBean.getGroupId().equals("")){
				if(groupBean.getPassword().equals(ssoBean.getMemberPassword())){
					if(groupBean.getActive().equals("1")){
						ssoBean.setActive(groupBean.getActive());
						ssoBean.setMemberName(groupBean.getName());
						ssoBean.setMemberRole(GSIResource.MEMBER_ROLE_GROUP);
						ssoBean.setPwUpdate("Y");
						ssoBean.setRegDate(groupBean.getRegDate());
						ssoBean.setTargetURL("/group/main");
						request.setAttribute("endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
						session.setAttribute("sso", ssoBean);
						session.setAttribute("customizingBean", customizingBean);
						return true;
					}else{
						message = "The username has expired";
						return false;
					}
					
				}else{
					message = "The password you entered is incorrect";
					return false;
				}
			}else if(!merchantBean.getMerchantId().equals("")){
				if(merchantBean.getPassword().equals(ssoBean.getMemberPassword())){
					ssoBean.setActive(merchantBean.getActive());
					ssoBean.setMemberName(merchantBean.getName());
					ssoBean.setMemberRole(GSIResource.MEMBER_ROLE_MERCHANT);
					ssoBean.setPwUpdate(merchantBean.getPwUpdate());
					ssoBean.setRegDate(merchantBean.getRegDate());
					ssoBean.setTargetURL("/merchant/main");
					request.setAttribute("endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
					MerchantMngBean merchantMngBean = new MerchantMngDAO().getById(merchantBean.getMerchantId());
					ssoBean.setCurType(merchantMngBean.getCurType());
					session.setAttribute("customizingBean", customizingBean);
					session.setAttribute("sso", ssoBean);
					
					return true;
				}else{
					message = "The password you entered is incorrect";
					return false;
				}
			}else{
				message = "The username or password you entered is incorrect";
				return false;
			}
			
		} 
	}

}

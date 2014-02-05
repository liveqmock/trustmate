/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.ctl.sso.SSOController.java
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

import biz.trustnet.common.log.Log;

import com.pgmate.resource.GSIResource;

public class SSOController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(false);  
		if(session == null ){
			return new ModelAndView("/sso/websso");
		}else if(session.getAttribute("sso") == null){
			return new ModelAndView("/sso/websso");
		}else if(session.isNew()){
			session.invalidate();
			return new ModelAndView("/sso/websso");
		}else{
			SSOBean ssoBean = (SSOBean)session.getAttribute("sso");
			if(ssoBean.getMemberRole().equals(GSIResource.MEMBER_ROLE_MEMBER)){
				return new ModelAndView("/main");
			}else if(ssoBean.getMemberRole().equals(GSIResource.MEMBER_ROLE_MERCHANT)){
				return new ModelAndView("/merchant/main");
			}else{
				session.removeAttribute("sso");
				return new ModelAndView("/sso/websso");
			}
		}
	}
	
	
}

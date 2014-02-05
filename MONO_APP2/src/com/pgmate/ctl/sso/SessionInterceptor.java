/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.ctl.sso.SessionInterceptor.java
 * Date	        : Jan 19, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.ctl.sso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import biz.trustnet.common.log.Log;

public class SessionInterceptor extends HandlerInterceptorAdapter{

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		SSOBean ssoBean = null;
		try{
			ssoBean = (SSOBean)request.getSession().getAttribute("sso");
		}catch(Exception e){}
		
		if (ssoBean == null) {
			String url = request.getServletPath();
			String query = request.getQueryString();
			
			ModelAndView mav = new ModelAndView("/sso/websso");
			
			if (query != null) {
				mav.addObject("logonForwardAction", url + "?" + query);
			} else {
				mav.addObject("logonForwardAction", url);
			}
			
			return false;
		} else {
			return true;
		}
	}
}

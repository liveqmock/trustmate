/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.ctl.sso.SSOOutController.java
 * Date	        : Jan 19, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.ctl.sso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

public class SSOOutController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		ModelAndView mav = null;
		SSOBean ssoBean = (SSOBean)request.getSession().getAttribute("sso");
		if(ssoBean != null){
			Log.debug("log.access","Log out ="+ssoBean.getMemberId(),this);
		}
		mav =  new ModelAndView("/sso/websso");
		
		try{
			request.getSession().removeAttribute("sso");
			request.getSession().invalidate();
		}catch(Exception e){
			Log.debug("log.access","Log out Exception="+CommonUtil.getExceptionMessage(e),this);
		}
		return mav;
	}
}

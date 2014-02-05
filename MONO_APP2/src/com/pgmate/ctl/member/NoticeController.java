/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.ctl.member.NoticeController.java
 * Date	        : Feb 9, 2009
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
import com.pgmate.model.db.ManualBean;
import com.pgmate.model.db.NoticeBean;
import com.pgmate.model.db.dao.ManualDAO;
import com.pgmate.model.db.dao.NoticeDAO;
import com.pgmate.web.util.AccessUtil;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class NoticeController implements Controller {
	
	private SSOBean ssoBean = null;
	
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {

		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		
		try{
			if(param.getSession("sso") == null){
				mav = new ModelAndView("/common/textResponse","RESPONSE","MSG||Your session is about to expire.");
			}else{
				ssoBean = new AccessUtil().get(param);
				if(param.getString("request").equals("notice")){
					mav = notice(param);
				}else if(param.getString("request").equals("add")){
					mav = add(param);
				}else{
					request.setAttribute("redirectURL","/main.jsp");
					mav = new ModelAndView("/common/redirect","message","Session Expired. ["+param.getString("request")+"]");
				}
			}	
		}catch(Exception e){
			mav = new ModelAndView("/main");
			Log.debug("log.day","Notice Controller"+CommonUtil.getExceptionMessage(e),this);
		}
		return mav;
	}
	
	public ModelAndView add(ParamUtil param){
		NoticeBean nBean = new NoticeBean();
		param.toBean(nBean);
		
		nBean.setMemberName("SITE ADMIN");
		nBean.setActive("Y");
		
		if(new NoticeDAO().insert(nBean)){
			param.setMessage("OK|| Notice has been registered.");
		}else{
			param.setMessage("NOK|| Notice has failed to register.");
		}
		return new ModelAndView("/common/ajaxResponse");
	}
	
	public ModelAndView notice(ParamUtil param){
		NoticeBean nBean = new NoticeBean();
		NoticeDAO nDAO = new NoticeDAO();
		
		param.toBean(nBean);
		param.toBean(nDAO);
		param.setDate(nDAO);
		nDAO.pageSize = 10000;
		List<NoticeBean> nList = nDAO.get(nBean);
		
		param.setAttribute("noticeList", nList);
		
		ManualBean manualBean = new ManualBean();
		ManualDAO manualDAO = new ManualDAO();
		
		param.toBean(manualBean);
		param.toBean(manualDAO);
		param.setDate(manualDAO);
		
		manualDAO.pageSize =10000;
		List<ManualBean> list = manualDAO.get(manualBean);
		
		param.setAttribute("manualList", list);
	
		return new ModelAndView("/notice/noticeList");
	}

}

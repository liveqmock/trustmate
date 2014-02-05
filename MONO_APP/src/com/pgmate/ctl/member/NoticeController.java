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
import com.pgmate.model.db.NoticeBean;
import com.pgmate.model.db.dao.NoticeDAO;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class NoticeController implements Controller {
	
	private SSOBean ssoBean = null;
	
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {

		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		
		try{
			if(param.getSession("sso") == null){
				mav = new ModelAndView("/sso/websso");
			}else{
				ssoBean = (SSOBean)param.getSession("sso");
				if(param.getString("request").equals("notice")){
					mav = notice(param);
				}else if(param.getString("request").equals("noticeForm")){
					return new ModelAndView("/notice/noticeWrite");
				}else if(param.getString("request").equals("regist")){
					mav = regist(param);
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
	
	public ModelAndView regist(ParamUtil param){
		NoticeBean nBean = new NoticeBean();
		param.toBean(nBean);
		
		nBean.setMemberName("SITE ADMIN");
		nBean.setActive("Y");
		
		if(new NoticeDAO().insert(nBean)){
			param.setAttribute("redirectURL","/notice.do?request=notice");
			return new ModelAndView("/common/redirect","message","[REGISTER SUCCESS]");
		}else{
			param.setAttribute("redirectURL","/notice.do?request=noticeForm");
			return new ModelAndView("/common/redirect","message","[REGISTER FAILURE]");
		}
	}
	
	public ModelAndView notice(ParamUtil param){
		NoticeBean nBean = new NoticeBean();
		NoticeDAO nDAO = new NoticeDAO();
		
		param.toBean(nBean);
		param.toBean(nDAO);
		param.setDate(nDAO);
		
		List<NoticeBean> nList = nDAO.get(nBean);
		
		param.setAttribute("dao", nDAO);
		new PagingUtil(param,"/notice.do").create();
		return new ModelAndView("/notice/notice","nList",nList);
	}

}

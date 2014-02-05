/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.ctl.merchant.MerchantDocmentController.java
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

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.ctl.sso.SSOBean;
import com.pgmate.model.db.ManualBean;
import com.pgmate.model.db.TrnsctnBean;
import com.pgmate.model.db.dao.ManualDAO;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class MerchantDocumentController implements Controller {
	
private SSOBean ssoBean = null;
	
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {

		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		
		try{
			if(param.getSession("sso") == null){
				mav = new ModelAndView("/sso/websso");
			}else{
				ssoBean = (SSOBean)param.getSession("sso");
				if(param.getString("request").equals("manual")){
					return getManual(param);
				}else{
					request.setAttribute("redirectURL","/merchant/main.jsp");
					mav = new ModelAndView("/common/redirect","message","Session Expired. ["+param.getString("request")+"]");
				}
			}	
		}catch(Exception e){
			mav = new ModelAndView("/merchant/main");
			Log.debug("log.day","MerchantTrnsctn Controller"+CommonUtil.getExceptionMessage(e),this);
		}
		return mav;
	}
	
	public ModelAndView getManual(ParamUtil param){
		ManualBean manualBean = new ManualBean();
		ManualDAO manualDAO = new ManualDAO();
		
		param.toBean(manualBean);
		param.toBean(manualDAO);
		param.setDate(manualDAO);
		
		manualDAO.pageSize =50;
		List<ManualBean> list = manualDAO.get(manualBean);
		
		param.setAttribute("manualBean", manualBean);
		
		param.setAttribute("list", list);
		param.setAttribute("dao", manualDAO);
		new PagingUtil(param,"/trnsctn.do").create();
		return new ModelAndView("/merchant/document/manual");
		
	}
	
}

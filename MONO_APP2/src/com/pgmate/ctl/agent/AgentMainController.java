package com.pgmate.ctl.agent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.pgmate.ctl.sso.SSOBean;
import com.pgmate.model.db.dao.TrnsctnDAO;
import com.pgmate.web.util.AccessUtil;
import com.pgmate.web.util.ParamUtil;

public class AgentMainController implements Controller {
	
private SSOBean ssoBean = null;
	
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {

		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		
		try{
			if(param.getSession("sso") == null){
				mav = new ModelAndView("/common/textResponse","RESPONSE","MSG||Your session is about to expire.");
			}else{
				ssoBean = new AccessUtil().get(param);
				param.setAttribute("recentErrorTrnsctn",new TrnsctnDAO().getRecentError(" AND MERCHANT_ID IN (SELECT MERCHANT_ID FROM TB_AGENT_MERCHANT WHERE AGENT_ID ='"+ssoBean.getMemberId()+"') "));
				param.setAttribute("recentTrnsctn",new TrnsctnDAO().getRecentTransaction(" AND MERCHANT_ID IN (SELECT MERCHANT_ID FROM TB_AGENT_MERCHANT WHERE AGENT_ID ='"+ssoBean.getMemberId()+"') "));
				
				return new ModelAndView("/agent/board");
			}	  
		}catch(Exception e){
			request.setAttribute("redirectURL","/main.jsp");
			mav = new ModelAndView("/common/redirect","message","Session Expired. ["+param.getString("request")+"]");
		}
		return mav;
	}

}

package com.pgmate.web.util;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.ctl.sso.SSOBean;
import com.pgmate.model.db.AccessLogBean;
import com.pgmate.model.db.dao.AccessLogDAO;
import com.pgmate.resource.GSIResource;

/**
 * @author Administrator
 *
 */
public class AccessUtil {

	public AccessUtil(){
	}
	
	public SSOBean get(ParamUtil param){
		SSOBean ssoBean = null;
		try{
			AccessLogBean alBean = new AccessLogBean();
			ssoBean = (SSOBean)param.getSession("sso");
			if(ssoBean != null){
				if(write(param, ssoBean)){
					alBean.setMemberId(ssoBean.getMemberId());
					alBean.setIpAddress(param.getRemoteAddr());
					alBean.setUserAgent(param.getHeader("User-Agent"));
					alBean.setAccessPage(param.getServletRequest().getRequestURI().trim()+"?request="+param.getString("request"));
					new AccessLogDAO().insert(alBean);
				}
			}
		}catch(Exception e){
			Log.debug("log.day",CommonUtil.getExceptionMessage(e),this);
		}
		return ssoBean;
	}
	
	
	public boolean write(ParamUtil param,SSOBean ssoBean){
		if(param.getRemoteAddr().equals("127.0.0.1")){
			return false;
		}else if(param.getRemoteAddr().equals("192.168.0.1")){
			return false;
		}else if(param.getRemoteAddr().equals("14.32.186.19")){
			return false;
		}else if(param.getServletRequest().getRequestURI().trim().equals("/ajax.do")){
			return false;
		}else{
		
			return true;
		}
		
	}
	
	
}

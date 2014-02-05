package com.pgmate.ctl.member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.ctl.sso.SSOBean;
import com.pgmate.model.db.AlertBean;
import com.pgmate.model.db.TrnsctnRiskBean;
import com.pgmate.model.db.dao.AlertDAO;
import com.pgmate.model.db.dao.TrnsctnRiskDAO;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class ManageController implements Controller {
	
	private SSOBean ssoBean = null;
	
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		
		try{
			ssoBean = (SSOBean)param.getSession("sso");
			if(param.getString("request").equals("riskList")){
				mav = riskList(param);
			}else if(param.getString("request").equals("riskAddForm")){
				return new ModelAndView("/control/riskAdd");
			}else if(param.getString("request").equals("riskAdd")){
				mav = riskAdd(param);
			}else if(param.getString("request").equals("riskEditForm")){
				return new ModelAndView("/control/riskEdit","trBean", new TrnsctnRiskDAO().getByIdx(CommonUtil.parseLong(param.getString("idx"))));
			}else if(param.getString("request").equals("riskEdit")){
				mav = riskEdit(param);
			}else if(param.getString("request").equals("alertList")){
				mav = alertList(param);
			}else if(param.getString("request").equals("alertAddForm")){
				return new ModelAndView("/control/alertAdd");
			}else if(param.getString("request").equals("alertAdd")){
				mav = alertAdd(param);
			}else if(param.getString("request").equals("alertDelete")){
				mav = alertDelete(param);
			}else{
				request.setAttribute("redirectURL","/main.jsp");
				mav = new ModelAndView("/common/redirect","message","Session Expired. ["+param.getString("request")+"]");
			}
		}catch(Exception e){
			mav = new ModelAndView("/main");
			Log.debug("log.day","Manage Controller"+CommonUtil.getExceptionMessage(e),this);
		}
		return mav;
		
	}
	
	public ModelAndView alertDelete(ParamUtil param){
		
		AlertBean aBean = new AlertBean();
		param.toBean(aBean);
		
		if(new AlertDAO().delete(aBean)){
			param.setAttribute("redirectURL","/manage.do?request=alertList");
			return new ModelAndView("/common/redirect","message","[DELETE SUCCESS]");
		}else{
			param.setAttribute("redirectURL","/manage.do?request=alertList");
			return new ModelAndView("/common/redirect","message","[DELETE FAILURE]");
		}
	}
	
	public ModelAndView alertAdd(ParamUtil param){
		
		AlertBean aBean = new AlertBean();
		param.toBean(aBean);
		aBean.setRegId(ssoBean.getMemberId());
		
		if(new AlertDAO().insert(aBean)){
			param.setAttribute("redirectURL","/manage.do?request=alertList");
			return new ModelAndView("/common/redirect","message","[INSERT SUCCESS]");
		}else{
			param.setAttribute("redirectURL","/manage.do?request=alertList");
			return new ModelAndView("/common/redirect","message","[INSERT FAILURE]");
		}
	}
	
	public ModelAndView alertList(ParamUtil param){
		
		AlertBean aBean = new AlertBean();
		AlertDAO aDAO = new AlertDAO();
		param.toBean(aBean);
		param.toBean(aDAO);
		param.setDate(aDAO);
		
		aDAO.orderBy = " ORDER BY REG_DATE DESC ";
		
		List<AlertBean> alertList = null;
		if(param.getString("format").equals("excel")){
			aDAO.pageSize =1000000;
			if(!param.isNullOrSpace("startDate") && !param.isNullOrSpace("endDate")){
				long checkDay = CommonUtil.getDifferDays(param.getString("startDate"), param.getString("endDate"));
				if(checkDay > 90){
					Log.debug("log.day","조회기간 초과입니다.(90일)",this);
					return new ModelAndView("/common/redirect","message","[조회기간 초과입니다.(90일이내)]");
				}
			}else{
				Log.debug("log.day","조회기간을 입력하여 주시기 바랍니다.(90일이내)",this);
				return new ModelAndView("/common/redirect","message","[조회기간을 입력하여 주시기 바랍니다.(90일이내)]");
			}
		}
		alertList = aDAO.get(aBean);
		param.setAttribute("dao", aDAO);
		param.setAttribute("aBean", aBean);
		if(param.getString("format").equals("excel")){
			return new ModelAndView("/control/alertListExl","alertList",alertList);
		}else{
			new PagingUtil(param,"/report.do").create();
			return new ModelAndView("/control/alertList","alertList",alertList);
		}
	}
	
	public ModelAndView riskEdit(ParamUtil param){
		
		TrnsctnRiskBean trBean = new TrnsctnRiskBean();
		param.toBean(trBean);
		
		if(new TrnsctnRiskDAO().update(trBean)){
			param.setAttribute("redirectURL","/manage.do?request=riskList");
			return new ModelAndView("/common/redirect","message","[UPDATE SUCCESS]");
		}else{
			param.setAttribute("redirectURL","/manage.do?request=riskList");
			return new ModelAndView("/common/redirect","message","[UPDATE FAILURE]");
		}
		
	}
	
	public ModelAndView riskAdd(ParamUtil param){
		
		TrnsctnRiskBean trBean = new TrnsctnRiskBean();
		param.toBean(trBean);
		
		if(new TrnsctnRiskDAO().insert(trBean)){
			param.setAttribute("redirectURL","/manage.do?request=riskList");
			return new ModelAndView("/common/redirect","message","[INSERT SUCCESS]");
		}else{
			param.setAttribute("redirectURL","/manage.do?request=riskList");
			return new ModelAndView("/common/redirect","message","[INSERT FAILURE]");
		}
	}
	
	public ModelAndView riskList(ParamUtil param){
		
		TrnsctnRiskBean trBean = new TrnsctnRiskBean();
		TrnsctnRiskDAO trDAO = new TrnsctnRiskDAO();
		param.toBean(trBean);
		param.toBean(trDAO);
		param.setDate(trDAO);
		
		trDAO.orderBy = " ORDER BY REG_DATE DESC ";
		
		List<TrnsctnRiskBean> trList = null;
		if(param.getString("format").equals("excel")){
			trDAO.pageSize =1000000;
			if(!param.isNullOrSpace("startDate") && !param.isNullOrSpace("endDate")){
				long checkDay = CommonUtil.getDifferDays(param.getString("startDate"), param.getString("endDate"));
				if(checkDay > 90){
					Log.debug("log.day","조회기간 초과입니다.(90일)",this);
					return new ModelAndView("/common/redirect","message","[조회기간 초과입니다.(90일이내)]");
				}
			}else{
				Log.debug("log.day","조회기간을 입력하여 주시기 바랍니다.(90일이내)",this);
				return new ModelAndView("/common/redirect","message","[조회기간을 입력하여 주시기 바랍니다.(90일이내)]");
			}
		}
		trList = trDAO.get(trBean);
		param.setAttribute("dao", trDAO);
		param.setAttribute("trBean", trBean);
		if(param.getString("format").equals("excel")){
			return new ModelAndView("/control/riskListExl","trList",trList);
		}else{
			new PagingUtil(param,"/report.do").create();
			return new ModelAndView("/control/riskList","trList",trList);
		}
		
	}

}

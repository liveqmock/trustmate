package com.pgmate.ctl.member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.ctl.sso.SSOBean;
import com.pgmate.model.db.AccessLogBean;
import com.pgmate.model.db.AlertBean;
import com.pgmate.model.db.TrnsctnRiskBean;
import com.pgmate.model.db.VanBean;
import com.pgmate.model.db.dao.AccessLogDAO;
import com.pgmate.model.db.dao.AlertDAO;
import com.pgmate.model.db.dao.TrnsctnRiskDAO;
import com.pgmate.model.db.dao.VanDAO;
import com.pgmate.web.util.AccessUtil;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class ManageController implements Controller {
	
	private SSOBean ssoBean = null;
	
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		
		try{
			if(param.getSession("sso") == null){
				mav = new ModelAndView("/common/textResponse","RESPONSE","MSG||Your session is about to expire.");
			}else{
				ssoBean = new AccessUtil().get(param);
				if(param.getString("request").equals("rlist")){
					mav = riskList(param);
				}else if(param.getString("request").equals("riskAdd")){
					mav = riskAdd(param);
				}else if(param.getString("request").equals("riskEdit")){
					mav = riskEdit(param);
				}else if(param.getString("request").equals("slist")){
					mav = alertList(param);
				}else if(param.getString("request").equals("alertAdd")){
					mav = alertAdd(param);
				}else if(param.getString("request").equals("alertDelete")){
					mav = alertDelete(param);
				}else if(param.getString("request").equals("vanlist")){
					mav = vanList(param);
				}else if(param.getString("request").equals("vanAdd")){
					mav = vanAdd(param);
				}else if(param.getString("request").equals("vanEdit")){
					mav = vanEdit(param);
				}else if(param.getString("request").equals("vanEditForm")){
					mav = vanEditForm(param);
				}else if(param.getString("request").equals("accesslist")){
					mav = accessList(param);
				}else{
					request.setAttribute("redirectURL","/main.jsp");
					mav = new ModelAndView("/common/redirect","message","Session Expired. ["+param.getString("request")+"]");
				}
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
			param.setMessage("OK|| Alert has been updated");
		}else{
			param.setMessage("NOK|| Alert failed to update");
		}
		return new ModelAndView("/common/ajaxResponse");
	}
	
	public ModelAndView alertAdd(ParamUtil param){
		
		AlertBean aBean = new AlertBean();
		param.toBean(aBean);
		aBean.setRegId(ssoBean.getMemberId());
		
		
		if(new AlertDAO().insert(aBean)){
			param.setMessage("OK|| Alert has been registed");
		}else{
			param.setMessage("NOK|| Alert failed to regist");
		}
		return new ModelAndView("/common/ajaxResponse");
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
			param.setAttribute("format", param.getString("format"));
			if(!param.isNullOrSpace("startDate") && !param.isNullOrSpace("endDate")){
				long checkDay = CommonUtil.getDifferDays(param.getString("startDate"), param.getString("endDate"));
				if(checkDay > 90){
					return new ModelAndView("/common/textResponse","RESPONSE","MSG||Period of reference excess(Max : 90 Days)");
				}
			}else{
				return new ModelAndView("/common/textResponse","RESPONSE","MSG||Please input period.(Max : 90 Days)");
			}
		}
		alertList = aDAO.get(aBean);
		param.setAttribute("dao", aDAO);
		param.setAttribute("aBean", aBean);
		new PagingUtil(param,"/manage.do").create();
		return new ModelAndView("/control/alertList","alertList",alertList);
		
	}
	
	public ModelAndView riskEdit(ParamUtil param){
		TrnsctnRiskDAO trnsctnRiskDAO = new TrnsctnRiskDAO();
		
		TrnsctnRiskBean trBean = trnsctnRiskDAO.getByIdx(param.getLong("idx"));
		trBean.setActive(param.getString("active"));
		trBean.setComments(trBean.getComments()+"<br>"+CommonUtil.getCurrentDate()+":"+param.getString("comments"));
		
		
		if(trnsctnRiskDAO.update(trBean)){
			param.setMessage("OK|| Unit has been updated");
		}else{
			param.setMessage("NOK|| Unit failed to update");
		}
		return new ModelAndView("/common/ajaxResponse");
	}
	
	public ModelAndView riskAdd(ParamUtil param){
		
		TrnsctnRiskBean trBean = new TrnsctnRiskBean();
		param.toBean(trBean);
		 

		if(new TrnsctnRiskDAO().insert(trBean)){
			param.setMessage("OK|| Unit has been registed");
		}else{
			param.setMessage("NOK|| Unit failed to registed");
		}
		return new ModelAndView("/common/ajaxResponse");
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
			param.setAttribute("format", param.getString("format"));
			if(!param.isNullOrSpace("startDate") && !param.isNullOrSpace("endDate")){
				long checkDay = CommonUtil.getDifferDays(param.getString("startDate"), param.getString("endDate"));
				if(checkDay > 90){
					return new ModelAndView("/common/textResponse","RESPONSE","MSG||Period of reference excess(Max : 90 Days)");
				}
			}else{
				return new ModelAndView("/common/textResponse","RESPONSE","MSG||Please input period.(Max : 90 Days)");
			}
		}
		trList = trDAO.get(trBean);
		param.setAttribute("dao", trDAO);
		param.setAttribute("trBean", trBean);
		new PagingUtil(param,"/manage.do").create();
		return new ModelAndView("/control/riskList","trList",trList);
		
		
	}
	
	
	
	
	public ModelAndView vanList(ParamUtil param){
		
		VanBean vanBean = new VanBean();
		VanDAO vanDAO = new VanDAO();
		
		param.toBean(vanBean);
		param.toBean(vanDAO);
		param.setDate(vanDAO);
		
		vanDAO.orderBy = " ORDER BY REG_DATE DESC ";
		
		List<VanBean> vanList = null;
		if(param.getString("format").equals("excel")){
			vanDAO.pageSize =1000000;
			param.setAttribute("format", param.getString("format"));
			if(!param.isNullOrSpace("startDate") && !param.isNullOrSpace("endDate")){
				long checkDay = CommonUtil.getDifferDays(param.getString("startDate"), param.getString("endDate"));
				if(checkDay > 90){
					return new ModelAndView("/common/textResponse","RESPONSE","MSG||Period of reference excess(Max : 90 Days)");
				}
			}else{
				return new ModelAndView("/common/textResponse","RESPONSE","MSG||Please input period.(Max : 90 Days)");
			}
		}
		vanList = vanDAO.get(vanBean);
		param.setAttribute("dao", vanDAO);
		new PagingUtil(param,"/manage.do").create();
		return new ModelAndView("/control/vanList","vanList",vanList);
		
		
	}
	
	public ModelAndView vanEditForm(ParamUtil param){
		VanDAO vanDAO = new VanDAO();
		
		VanBean vanBean = vanDAO.getByIdx(param.getLong("idx"));
	
		return new ModelAndView("/control/vanEditForm","vanBean",vanBean);
	}
	
	public ModelAndView vanEdit(ParamUtil param){
		VanDAO vanDAO = new VanDAO();
		
		VanBean vanBean = vanDAO.getByIdx(param.getLong("idx"));
		param.toBean(vanBean);
		
		
		if(vanDAO.update(vanBean)){
			param.setMessage("OK|| VAN has been updated");
		}else{
			param.setMessage("NOK|| VAN failed to update");
		}
		return new ModelAndView("/common/ajaxResponse");
	}
	
	public ModelAndView vanAdd(ParamUtil param){
		
		VanBean vanBean = new VanBean();
		param.toBean(vanBean);
		 

		if(new VanDAO().insert(vanBean)){
			param.setMessage("OK|| VAN has been registed");
		}else{
			param.setMessage("NOK|| VAN failed to registed");
		}
		return new ModelAndView("/common/ajaxResponse");
	}
	
	
	public ModelAndView accessList(ParamUtil param){
		
		AccessLogBean accessLogBean = new AccessLogBean();
		AccessLogDAO accessLogDAO = new AccessLogDAO();
		
		param.toBean(accessLogBean);
		param.toBean(accessLogDAO);
		param.setDate(accessLogDAO);
		
		accessLogDAO.orderBy = " ORDER BY IDX DESC ";
		
		List<AccessLogBean> accessList = null;
		if(param.getString("format").equals("excel")){
			accessLogDAO.pageSize =1000000;
			param.setAttribute("format", param.getString("format"));
			if(!param.isNullOrSpace("startDate") && !param.isNullOrSpace("endDate")){
				long checkDay = CommonUtil.getDifferDays(param.getString("startDate"), param.getString("endDate"));
				if(checkDay > 90){
					return new ModelAndView("/common/textResponse","RESPONSE","MSG||Period of reference excess(Max : 90 Days)");
				}
			}else{
				return new ModelAndView("/common/textResponse","RESPONSE","MSG||Please input period.(Max : 90 Days)");
			}
		}
		accessList = accessLogDAO.get(accessLogBean);
		param.setAttribute("dao", accessLogDAO);
		new PagingUtil(param,"/manage.do").create();
		return new ModelAndView("/control/accessList","accessList",accessList);
		
		
	}

}

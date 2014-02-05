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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pgmate.ctl.sso.SSOBean;
import com.pgmate.model.chart.BarChartBean;
import com.pgmate.model.chart.CalendarBean;
import com.pgmate.model.chart.PieChartBean;
import com.pgmate.model.chart.StatisticChartBean;
import com.pgmate.model.db.AgentBean;
import com.pgmate.model.db.CodeBean;
import com.pgmate.model.db.GroupBean;
import com.pgmate.model.db.MemberBean;
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.dao.AgentDAO;
import com.pgmate.model.db.dao.ChartDAO;
import com.pgmate.model.db.dao.CodeDAO;
import com.pgmate.model.db.dao.GroupDAO;
import com.pgmate.model.db.dao.MemberDAO;
import com.pgmate.model.db.dao.MerchantDAO;
import com.pgmate.resource.GSIResource;
import com.pgmate.web.util.AccessUtil;
import com.pgmate.web.util.ParamUtil;

public class AjaxController implements Controller {
	
	private SSOBean ssoBean = null;
	
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {

		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		
		try{
			if(param.getSession("sso") == null){
				mav = new ModelAndView("/common/textResponse","RESPONSE","MSG||Your session is about to expire.");
			}else{
				ssoBean = new AccessUtil().get(param);
				if(param.getString("request").equals("merchantId")){
					mav = getMerchantId(param);
				}else if(param.getString("request").equals("checkSession")){
					mav = getCheckSession(param);
				}else if(param.getString("request").equals("resultMsg")){
					mav = getResultMsg(param);
				}else if(param.getString("request").equals("checkMemberId")){
					mav = getCheckMemberId(param);
				}else if(param.getString("request").equals("checkMerchantId")){
					mav = getCheckMerchantId(param);
				}else if(param.getString("request").equals("checkGroupId")){
					mav = getCheckGroupId(param);
				}else if(param.getString("request").equals("checkAgentId")){
					mav = getCheckAgentId(param);
				}else if(param.getString("request").equals("monthlyChart")){
					mav = getMonthlyChart(param);
				}else if(param.getString("request").equals("merchantMonthlyChart")){
					mav = getMerchantMonthlyChart(param);
				}else if(param.getString("request").equals("monthlyTodayChart")){
					mav = getMonthlyTodayChart(param);
				}else if(param.getString("request").equals("todayChart")){
					mav = getTodayChart();
				}else if(param.getString("request").equals("toExcel")){
					mav = toExcel(param);
				}else if(param.getString("request").equals("toPdf")){
					mav = toPdf(param);
				}else if(param.getString("request").equals("settleCalendar")){
					mav = settleCalendar(param);
				}else{
					request.setAttribute("redirectURL","/main.jsp");
					mav = new ModelAndView("/common/redirect","message","Session Expired. ["+param.getString("request")+"]");
				}
			}	  
		}catch(Exception e){
			request.setAttribute("redirectURL","/main.jsp");
			mav = new ModelAndView("/common/redirect","message","Session Expired. ["+param.getString("request")+"]");
		}
		return mav;
	}
	
	public ModelAndView getCheckSession(ParamUtil param){
		if(param.getSession("sso") ==  null){
			return new ModelAndView("/common/textResponse","RESPONSE","MSG||Your session is about to expire.");
		}else{
			return new ModelAndView("/common/textResponse","RESPONSE","MSG||ALIVE");
		}
	}
	
	public ModelAndView getMerchantId(ParamUtil param){
		List<String> list = null;
		if(ssoBean.getMemberRole().equals("0001")){
			list = new MerchantDAO().getMerchantList(param.getString("merchantId"));
			
		}else if(ssoBean.getMemberRole().equals("0002")){
			list = new ArrayList<String>();
			list.add(ssoBean.getMemberId());	
		}else if(ssoBean.getMemberRole().equals("0003")){
			list = new GroupDAO().getMerchantList(ssoBean.getMemberId(),param.getString("merchantId"));
		}else if(ssoBean.getMemberRole().equals("0004")){
			list = new AgentDAO().getMerchantList(ssoBean.getMemberId(),param.getString("merchantId"));
		}else{
			list = new ArrayList<String>();
		}
		
		return new ModelAndView("/common/jsonResponse","message",getResponse(list));		
	}
	
	public ModelAndView getResultMsg(ParamUtil param){
		CodeBean codeBean = new CodeDAO().getByAliasCode("KAPPV_CODE",param.getString("resultMsg"));
		StringBuffer sb = new StringBuffer();
		sb.append(codeBean.getEnValue());
		sb.append("<br>"+codeBean.getKrValue());
		sb.append("<br>"+codeBean.getJpValue());
		return new ModelAndView("/common/ajaxResponse","message",sb.toString());		
	}
	
	
	public ModelAndView getCheckMemberId(ParamUtil param){
		MemberBean memberBean = new MemberDAO().getById(param.getString("memberId"));
		if(param.getString("memberId").indexOf(" ") > -1){
			return new ModelAndView("/common/jsonResponse","message",getResponse("Member ID can not contain any spaces."));
		}
		
		if(memberBean.getMemberId().equals("")){
			return new ModelAndView("/common/jsonResponse","message",getResponse("true"));
		}else{
			return new ModelAndView("/common/jsonResponse","message",getResponse("Member ID is aleady exists"));
		}
	}
	
	public ModelAndView getCheckMerchantId(ParamUtil param){
		MerchantBean merchantBean = new MerchantDAO().getById(param.getString("merchantId"));
		if(param.getString("merchantId").indexOf(" ") > -1){
			return new ModelAndView("/common/jsonResponse","message",getResponse("Merchant ID can not contain any spaces."));
		}
		if(merchantBean.getMerchantId().equals("")){
			return new ModelAndView("/common/jsonResponse","message",getResponse("true"));
		}else{
			return new ModelAndView("/common/jsonResponse","message",getResponse("Merchant ID is aleady exists"));
		}
	}
	
	public ModelAndView getCheckGroupId(ParamUtil param){
		GroupBean groupBean = new GroupDAO().getById(param.getString("groupId"));
		if(param.getString("groupId").indexOf(" ") > -1){
			return new ModelAndView("/common/jsonResponse","message",getResponse("Group ID can not contain any spaces."));
		}
		if(groupBean.getGroupId().equals("")){
			return new ModelAndView("/common/jsonResponse","message",getResponse("true"));
		}else{
			return new ModelAndView("/common/jsonResponse","message",getResponse("Group ID is aleady exists"));
		}
	}
	
	public ModelAndView getCheckAgentId(ParamUtil param){
		AgentBean agentBean = new AgentDAO().getById(param.getString("agentId"));
		if(param.getString("agentId").indexOf(" ") > -1){
			return new ModelAndView("/common/jsonResponse","message",getResponse("Agent ID can not contain any spaces."));
		}
		if(agentBean.getAgentId().equals("")){
			return new ModelAndView("/common/jsonResponse","message",getResponse("true"));
		}else{
			return new ModelAndView("/common/jsonResponse","message",getResponse("Agent ID is aleady exists"));
		}
	}
	
	public ModelAndView getMonthlyChart(ParamUtil param){
		ChartDAO chartDAO = new ChartDAO();
		
		StatisticChartBean chartBean = new StatisticChartBean();
		
		List<BarChartBean> list = null;
		if(ssoBean.getMemberRole().equals("0002")){
			list = chartDAO.getByTrnsctnMonthlyByMerchant(param.getString("type")," AND MERCHANT_ID ='"+ssoBean.getMemberId()+"'");
			
		}else if(ssoBean.getMemberRole().equals("0003")){
			list = chartDAO.getByTrnsctnMonthlyByGroup(param.getString("type"),ssoBean.getMemberId());
		}else if(ssoBean.getMemberRole().equals("0004")){
			list = chartDAO.getByTrnsctnMonthlyByAgent(param.getString("type"),ssoBean.getMemberId());
		}else{
			list = chartDAO.getByTrnsctnMonthly(param.getString("type"));
		}
		List<String> categories = chartDAO.getCategoris();
		chartBean.setCategories(categories);
		chartBean.setSeries(list);
		return new ModelAndView("/common/jsonResponse","message",getResponse(chartBean));
	}
	

	
	
	public ModelAndView getMerchantMonthlyChart(ParamUtil param){
		ChartDAO chartDAO = new ChartDAO();
		
		if(ssoBean.getMemberRole().equals(GSIResource.MEMBER_ROLE_GROUP)){
			if(!new GroupDAO().checkMerchant(ssoBean.getMemberId(), param.getString("merchantId"))){
				param.put("merchantId", "");
			}
		}
		
		StatisticChartBean chartBean = new StatisticChartBean();
		List<BarChartBean> list = chartDAO.getByMonthlyByMerchantId(param.getString("merchantId"),param.getString("type"));
		List<String> categories = chartDAO.getCategoris();
		chartBean.setCategories(categories);
		chartBean.setSeries(list);
		return new ModelAndView("/common/jsonResponse","message",getResponse(chartBean));
	}
	
	public ModelAndView getMonthlyTodayChart(ParamUtil param){
		ChartDAO chartDAO = new ChartDAO();
		
		StatisticChartBean chartBean = new StatisticChartBean();
		List<BarChartBean> list = chartDAO.getByTrnsctnMonthlyToday(param.getString("type"));
		List<String> categories = chartDAO.getCategoris();
		chartBean.setCategories(categories);
		chartBean.setSeries(list);
		return new ModelAndView("/common/jsonResponse","message",getResponse(chartBean));
	}
	
	
	public ModelAndView getTodayChart(){
		ChartDAO chartDAO = new ChartDAO();
		
		PieChartBean pieChartBean = new PieChartBean();
		List<Object[]> list = chartDAO.getTodayChart();
		pieChartBean.setData(list);
		pieChartBean.setName("TODAY TRANSACTION");
		Object[] obj = new Object[1];
		obj[0] = pieChartBean;
		return new ModelAndView("/common/jsonResponse","message",getResponse(obj));
	}
	
	public ModelAndView settleCalendar(ParamUtil param){
		List<CalendarBean> list = new ChartDAO().getBySettleCalendar(param.getString("month"));
		
		return new ModelAndView("/common/jsonResponse","message",getResponse(list));
	}
	
	
	public ModelAndView toExcel(ParamUtil param){
		
		return new ModelAndView("/common/excelResponse","message",param.getString("message"));
	}
	
	public ModelAndView toPdf(ParamUtil param){


		return new ModelAndView("/common/textResponse","message","OK");
	}
	


	public String getResponse(Object obj){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(obj);
		return json;
	
	}
	
	

}

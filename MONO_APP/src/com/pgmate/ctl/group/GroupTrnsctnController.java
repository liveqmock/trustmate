/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.ctl.merchant.MerchantTrnsctnController.java
 * Date	        : Jan 21, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.ctl.group;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.ctl.sso.SSOBean;
import com.pgmate.model.db.TrnsctnBean;
import com.pgmate.model.db.TrnsctnVoidBean;
import com.pgmate.model.db.dao.CodeDAO;
import com.pgmate.model.db.dao.CommentTrnsctnDAO;
import com.pgmate.model.db.dao.GroupDAO;
import com.pgmate.model.db.dao.MerchantDAO;
import com.pgmate.model.db.dao.TrnsctnDAO;
import com.pgmate.model.db.dao.TrnsctnGroupDAO;
import com.pgmate.model.db.dao.TrnsctnSCRDAO;
import com.pgmate.model.db.dao.TrnsctnVoidDAO;
import com.pgmate.payment.util.GSICrypt;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class GroupTrnsctnController implements Controller {
	
	private SSOBean ssoBean = null;
	
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {

		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		
		try{
			if(param.getSession("sso") == null){
				mav = new ModelAndView("/sso/websso");
			}else{
				ssoBean = (SSOBean)param.getSession("sso");
				
				if(param.getString("request").equals("listForm")){
					param.setAttribute("merchantList", new GroupDAO().getByMerchant(ssoBean.getMemberId()));
					return new ModelAndView("/group/trnsctn/trnsctnList","endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
				}else if(param.getString("request").equals("list")){
					mav = trnsctnList(param);
				}else if(param.getString("request").equals("view")){
					mav = trnsctnInfo(param);
				}else{
					request.setAttribute("redirectURL","/group/main.jsp");
					mav = new ModelAndView("/common/redirect","message","Session Expired. ["+param.getString("request")+"]");
				}
			}	
		}catch(Exception e){
			mav = new ModelAndView("/group/main");
			Log.debug("log.day","MerchantTrnsctn Controller"+CommonUtil.getExceptionMessage(e),this);
		}
		return mav;
	}
	
	
	
	public ModelAndView trnsctnInfo(ParamUtil param){
		param.setAttribute("trnsctnSCRBean", new TrnsctnSCRDAO().getByTransactionId(param.getString("transactionId")));
		TrnsctnBean trnsctnBean = new TrnsctnDAO().getByTransactionId(param.getString("transactionId"));
		param.setAttribute("resultMsg",new CodeDAO().getByAliasCode("KAPPV_CODE",trnsctnBean.getResultMsg()).getEnValue());
		param.setAttribute("trnsctnBean",new TrnsctnDAO().getByTransactionId(param.getString("transactionId")));
		param.setAttribute("merchantBean",new MerchantDAO().getById(trnsctnBean.getMerchantId()));
		TrnsctnVoidBean trnsctnVoidBean = new TrnsctnVoidDAO().getByTransactionId(trnsctnBean.getTransactionId());
		param.setAttribute("trnsctnVoidBean",trnsctnVoidBean);
		param.setAttribute("voidComment", new CommentTrnsctnDAO().getByIdx(trnsctnVoidBean.getCommentIdx()).getComments());
		return new ModelAndView("/group/trnsctn/trnsctninfo");
	}
	
	public ModelAndView trnsctnList(ParamUtil param){
		GSICrypt crypt = new GSICrypt();
		TrnsctnBean trnsctnBean = new TrnsctnBean();
		TrnsctnGroupDAO trnsctnGroupDAO = new TrnsctnGroupDAO();
		
		param.toBean(trnsctnBean);
		param.toBean(trnsctnGroupDAO);
		param.setDate(trnsctnGroupDAO);

		trnsctnBean.setTemp3String(ssoBean.getMemberId());
		
		if(!param.getString("temp1String").equals("")){
			trnsctnBean.setTemp1String(crypt.encrypt(param.getString("temp1String")));
		}
		param.setAttribute("merchantList", new GroupDAO().getByMerchant(ssoBean.getMemberId()));
		List<TrnsctnBean> trnsctnList = null;
		if(param.getString("format").equals("excel")){
			trnsctnGroupDAO.pageSize =1000000;
			if(!param.isNullOrSpace("startDate") && !param.isNullOrSpace("endDate")){
				long checkDay = CommonUtil.getDifferDays(param.getString("startDate"), param.getString("endDate"));
				if(checkDay > 90){
					Log.debug("log.day","Period of reference excess(Max : 90 Days)",this);
					return new ModelAndView("/common/redirect","message","[Period of reference excess(Max : 90 Days)]");
				}
			}else{
				Log.debug("log.day","Please input period.(Max : 90 Days)",this);
				return new ModelAndView("/common/redirect","message","[Please input period.(Max : 90 Days)]");
			}
		}
		trnsctnList = trnsctnGroupDAO.get(trnsctnBean);
		
		param.setAttribute("trnsctnBean", trnsctnBean);
		param.setAttribute("dao", trnsctnGroupDAO);
		param.setAttribute("searchAmount", trnsctnGroupDAO.getSearchAmount());
		if(param.getString("format").equals("excel")){
			return new ModelAndView("/group/trnsctn/trnsctnListExl","trnsctnList",trnsctnList);
		}else{
			new PagingUtil(param,"/group/trnsctn.do").create();
			return new ModelAndView("/group/trnsctn/trnsctnList","trnsctnList",trnsctnList);
		}
	
	}
	
	

}

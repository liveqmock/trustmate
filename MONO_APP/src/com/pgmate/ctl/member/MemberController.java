/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.ctl.member.MemberController.java
 * Date	        : Feb 6, 2009
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

import biz.trustnet.common.cipher.MDEncoder;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.ctl.sso.SSOBean;
import com.pgmate.model.db.GroupMerchantBean;
import com.pgmate.model.db.MemberBean;
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.MerchantBillBean;
import com.pgmate.model.db.MerchantMallBean;
import com.pgmate.model.db.MerchantMngBean;
import com.pgmate.model.db.dao.GroupDAO;
import com.pgmate.model.db.dao.MemberDAO;
import com.pgmate.model.db.dao.MerchantBillDAO;
import com.pgmate.model.db.dao.MerchantDAO;
import com.pgmate.model.db.dao.MerchantMallDAO;
import com.pgmate.model.db.dao.MerchantMngDAO;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class MemberController implements Controller {
	
	private SSOBean ssoBean = null;
	
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {

		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		
		try{
			if(param.getSession("sso") == null){
				mav = new ModelAndView("/sso/websso");
			}else{
				ssoBean = (SSOBean)param.getSession("sso");
				if(param.getString("request").equals("list")){
					mav = merchantList(param);
				}else if(param.getString("request").equals("listForm")){
					return new ModelAndView("/member/merchantList","endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
				}else if(param.getString("request").equals("view")){
					mav = merchantView(param);
				}else if(param.getString("request").equals("passwordModi")){
					mav = passwordModi(param);
				}else if(param.getString("request").equals("passwordModi2")){
					mav = passwordModi2(param);
				}else if(param.getString("request").equals("modify")){
					mav = pwModify(param);
				}else if(param.getString("request").equals("mlistForm")){
					return new ModelAndView("/member/memberList","endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
				}else if(param.getString("request").equals("mlist")){
					mav = memberList(param);
				}else if(param.getString("request").equals("mview")){
					mav = memberView(param);
				}else if(param.getString("request").equals("modify2")){
					mav = pwModify2(param);
				}else if(param.getString("request").equals("mEditForm")){
					return new ModelAndView("/member/memberEdit","mBean", new MemberDAO().getById(param.getString("memberId")));
				}else if(param.getString("request").equals("mEdit")){
					mav = memberEdit(param);
				}else if(param.getString("request").equals("mAddForm")){
					return new ModelAndView("/member/memberAdd");
				}else if(param.getString("request").equals("mAdd")){
					mav = memberAdd(param);
				}else if(param.getString("request").equals("addForm")){
					return new ModelAndView("/member/merchantAdd");
				}else if(param.getString("request").equals("add")){
					mav = merchantAdd(param);
				}else if(param.getString("request").equals("editForm")){
					return new ModelAndView("/member/merchantEdit","mBean", new MerchantDAO().getByIdx(CommonUtil.parseLong(param.getString("idx"))));
				}else if(param.getString("request").equals("edit")){
					mav = merchantEdit(param);
				}else if(param.getString("request").equals("serviceAddForm")){
					return new ModelAndView("/member/merchantServiceAdd","merchantId", param.getString("merchantId"));
				}else if(param.getString("request").equals("serviceAdd")){
					mav = merchantServiceAdd(param);
				}else if(param.getString("request").equals("serviceEditForm")){
					return new ModelAndView("/member/merchantServiceEdit","mngBean", new MerchantMngDAO().getByIdx(CommonUtil.parseLong(param.getString("idx"))));
				}else if(param.getString("request").equals("serviceEdit")){
					mav = merchantServiceEdit(param);
				}else if(param.getString("request").equals("groupSelectForm")){
					mav = groupSelectForm(param);
				}else if(param.getString("request").equals("groupSelect")){
					mav = groupSelect(param);
				}else{
					request.setAttribute("redirectURL","/main.jsp");
					mav = new ModelAndView("/common/redirect","message","Session Expired. ["+param.getString("request")+"]");
				}
			}	
		}catch(Exception e){
			mav = new ModelAndView("/main");
			Log.debug("log.day","Member Controller"+CommonUtil.getExceptionMessage(e),this);
		}
		return mav;
	}
	
	public ModelAndView groupSelect(ParamUtil param){
		
		GroupMerchantBean gmBean = new GroupMerchantBean();
		GroupDAO gDAO = new GroupDAO();
		param.toBean(gmBean);
		gmBean.setRegId(ssoBean.getMemberId());
		String message = "";
		
		
		if(gDAO.checkMerchant(gmBean)){	// update 처리
			if(CommonUtil.isNullOrSpace(gmBean.getGroupId())){
				if(gDAO.deleteMerchantId(gmBean.getMerchantId())){
					message = "가맹점 그룹 해제 성공 MerchantId["+gmBean.getMerchantId()+"]";
				}else{
					message = "가맹점 그룹 해제 실패 MerchantId["+gmBean.getMerchantId()+"]";
				}
			}else{
				if(gDAO.updateGroupMerchant(gmBean)){
					message = "가맹점 그룹 변경 성공 MerchantId["+gmBean.getMerchantId()+"] groupId["+gmBean.getGroupId()+"]";
				}else{
					message = "가맹점 그룹 변경 실패 MerchantId["+gmBean.getMerchantId()+"] groupId["+gmBean.getGroupId()+"]";
				}
			}
		}else{							// insert 처리
			if(CommonUtil.isNullOrSpace(gmBean.getGroupId())){
				message = "가맹점 그룹 등록  실패 [그룹 아이디가 선택되지 않았습니다.]";
			}else{
				if(gDAO.insertGroupMerchant(gmBean)){
					message = "가맹점 그룹 등록 성공 MerchantId["+gmBean.getMerchantId()+"] groupId["+gmBean.getGroupId()+"]";
				}else{
					message = "가맹점 그룹 등록  실패 MerchantId["+gmBean.getMerchantId()+"] groupId["+gmBean.getGroupId()+"]";
				}
			}
		}
		
		Log.debug("log.day",message,this);
		param.setAttribute("redirectURL","/member.do?request=view&merchantId="+param.getString("merchantId"));
		return new ModelAndView("/common/redirect","message",message);
	}
	
	public ModelAndView groupSelectForm(ParamUtil param){
		
		param.setAttribute("merchantId", param.getString("merchantId"));
		param.setAttribute("glist", new GroupDAO().getByActive("1"));
		if(!CommonUtil.isNullOrSpace(param.getString("groupId"))){
			param.setAttribute("groupId", param.getString("groupId"));
		}
		return new ModelAndView("/member/groupSelect");
		
		
	}
	
	public ModelAndView merchantServiceEdit(ParamUtil param){
		
		MerchantMngBean mngBean = new MerchantMngBean();
		param.toBean(mngBean);
		
		if(new MerchantMngDAO().update(mngBean)){
			param.setAttribute("redirectURL","/member.do?request=list");
			return new ModelAndView("/common/redirect","message","[UPDATE SUCCESS]");
		}else{
			param.setAttribute("redirectURL","/member.do?request=list");
			return new ModelAndView("/common/redirect","message","[UPDATE FAILURE]");
		}
		
	}
	
	public ModelAndView merchantServiceAdd(ParamUtil param){
		
		MerchantMngBean mngBean = new MerchantMngBean();
		param.toBean(mngBean);
		
		MerchantBillBean mbBean = new MerchantBillDAO().getByMerchantId(mngBean.getMerchantId());
		
		if(new MerchantMngDAO().insert(mngBean)){
			
			if(CommonUtil.isNullOrSpace(mbBean.getMerchantId())){
				param.setAttribute("redirectURL","/bill.do?request=billAddForm&merchantId="+mngBean.getMerchantId());
				return new ModelAndView("/common/redirect","message","[INSERT SUCCESS! PLEASE INPUT FOR NEXT STEP BILL INFOMATION. ]");
			}else{
				param.setAttribute("redirectURL","/member.do?request=list");
				return new ModelAndView("/common/redirect","message","[INSERT SUCCESS]");
			}
		}else{
			param.setAttribute("redirectURL","/member.do?request=list");
			return new ModelAndView("/common/redirect","message","[INSERT FAILURE]");
		}
		
	}
	
	public ModelAndView merchantEdit(ParamUtil param){
		
		MerchantBean mBean = new MerchantBean();
		param.toBean(mBean);
		
		mBean.setServiceDate(CommonUtil.stringToTimestamp(param.getReplaceString("serviceDate",CommonUtil.getCurrentDate("yyyyMMdd") )));
		
		if(new MerchantDAO().update(mBean)){
			param.setAttribute("mBean",mBean);
			param.setAttribute("redirectURL","/member.do?request=list");
			return new ModelAndView("/common/redirect","message","[UPDATE SUCCESS]");
		}else{
			param.setAttribute("redirectURL","/member.do?request=list");
			return new ModelAndView("/common/redirect","message","[UPDATE FAILURE]");
		}
		
	}
	
	public ModelAndView merchantAdd(ParamUtil param){
		
		MerchantBean mBean = new MerchantBean();
		param.toBean(mBean);
		mBean.setServiceDate(CommonUtil.stringToTimestamp(param.getReplaceString("serviceDate",CommonUtil.getCurrentDate("yyyyMMdd"))));
		mBean.setPassword(new MDEncoder().encodeSHA1(mBean.getPassword()));
		if(new MerchantDAO().insert(mBean)){
			param.setAttribute("redirectURL","/member.do?request=serviceAddForm&merchantId="+mBean.getMerchantId());
			return new ModelAndView("/common/redirect","message","[INSERT SUCCESS! PLEASE INPUT FOR NEXT STEP SERVICE INFOMATION. ]");
		}else{
			param.setAttribute("redirectURL","/member.do?request=list");
			return new ModelAndView("/common/redirect","message","[INSERT FAILURE]");
		}
		
	}
	
	public ModelAndView memberEdit(ParamUtil param){
		
		MemberBean mBean = new MemberBean();
		param.toBean(mBean);
		
		if(new MemberDAO().update2(mBean)){
			param.setAttribute("mBean",mBean);
			param.setAttribute("redirectURL","/member.do?request=mlist");
			return new ModelAndView("/common/redirect","message","[UPDATE SUCCESS]");
		}else{
			param.setAttribute("redirectURL","/member.do?request=mlist");
			return new ModelAndView("/common/redirect","message","[UPDATE FAILURE]");
		}
		
	}
	
	public ModelAndView memberAdd(ParamUtil param){
		
		MemberBean mBean = new MemberBean();
		param.toBean(mBean);
		
		mBean.setPassword(new MDEncoder().encodeSHA1(mBean.getPassword()));
		if(new MemberDAO().insert(mBean)){
			param.setAttribute("mBean",mBean);
			param.setAttribute("redirectURL","/member.do?request=mlist");
			return new ModelAndView("/common/redirect","message","[INSERT SUCCESS]");
		}else{
			param.setAttribute("redirectURL","/member.do?request=mlist");
			return new ModelAndView("/common/redirect","message","[INSERT FAILURE]");
		}
		
	}
	
	public ModelAndView pwModify(ParamUtil param){
		
		MerchantBean mBean = new MerchantBean();
		param.toBean(mBean);
		
		mBean= new MerchantDAO().getById(param.getString("merchantId"));
		/*
		if(mBean.getPassword().equals(new MDEncoder().encodeSHA1(param.getString("password"))) && !param.getString("uppassword").equals("")){
			mBean.setPassword(new MDEncoder().encodeSHA1(param.getString("uppassword")));
			mBean.setPwUpdate("Y");
			Log.debug("log.day","change password  : "+param.getString("uppassword") + "  & MerchantId : "+mBean.getMerchantId(),this);
			if(new MerchantDAO().pwupdate(mBean)){	
				param.setAttribute("mBean",mBean);
				param.setAttribute("redirectURL","/member.do?request=list");
				return new ModelAndView("/common/redirect","message","[UPDATE SUCCESS]");
			}else{
				param.setAttribute("redirectURL","/member.do?request=list");
				return new ModelAndView("/common/redirect","message","[UPDATE FAILURE]");
			}
		}else{
			param.setAttribute("redirectURL","/member.do?request=list");
			return new ModelAndView("/common/redirect","message","[기존 패스워드가 일치 하지 않습니다. 다시 입력하여 주시기 바랍니다.]");
		}
		*/
		
		mBean.setPassword(new MDEncoder().encodeSHA1(param.getString("uppassword")));
		mBean.setPwUpdate("Y");
		Log.debug("log.day","change password  : "+param.getString("uppassword") + "  & MerchantId : "+mBean.getMerchantId(),this);
		if(new MerchantDAO().pwupdate(mBean)){	
			param.setAttribute("mBean",mBean);
			param.setAttribute("redirectURL","/member.do?request=list");
			return new ModelAndView("/common/redirect","message","[UPDATE SUCCESS]");
		}else{
			param.setAttribute("redirectURL","/member.do?request=list");
			return new ModelAndView("/common/redirect","message","[UPDATE FAILURE]");
		}
	}
	
	public ModelAndView pwModify2(ParamUtil param){
		
		MemberBean mBean = new MemberBean();
		
		mBean= new MemberDAO().getById(param.getString("memberId"));
		
		if(mBean.getPassword().equals(new MDEncoder().encodeSHA1(param.getString("password"))) && !param.getString("uppassword").equals("")){
			mBean.setPassword(new MDEncoder().encodeSHA1(param.getString("uppassword")));
			Log.debug("log.day","change password  : "+param.getString("uppassword") + "  & MemberId : "+mBean.getMemberId(),this);
			if(new MemberDAO().pwupdate(mBean)){	
				param.setAttribute("mBean",mBean);
				param.setAttribute("redirectURL","/member.do?request=mlist");
				return new ModelAndView("/common/redirect","message","[UPDATE SUCCESS]");
			}else{
				param.setAttribute("redirectURL","/member.do?request=mlist");
				return new ModelAndView("/common/redirect","message","[UPDATE FAILURE]");
			}
		}else{
			param.setAttribute("redirectURL","/member.do?request=mlist");
			return new ModelAndView("/common/redirect","message","[기존 패스워드가 일치 하지 않습니다. 다시 입력하여 주시기 바랍니다.]");
		}
	}
	
	public ModelAndView passwordModi(ParamUtil param){
		param.setAttribute("merchantBean", new MerchantDAO().getById(param.getString("merchantId")));
		return new ModelAndView("/member/passwordUpdate");
	}
	public ModelAndView passwordModi2(ParamUtil param){
		param.setAttribute("mBean", new MemberDAO().getById(param.getString("memberId")));
		return new ModelAndView("/member/passwordUpdate2");
	}
	
	public ModelAndView memberView(ParamUtil param){
		MemberBean mBean = new MemberDAO().getById(param.getString("memberId"));
		return new ModelAndView("/member/memberView","mBean", mBean);
	}
	
	public ModelAndView merchantView(ParamUtil param){
		MerchantBean mBean = new MerchantDAO().getById(param.getString("merchantId"));
		MerchantMallDAO mallDAO = new MerchantMallDAO();
		List<MerchantMallBean> mallList = mallDAO.getByMerchantId(mBean.getMerchantId());
		
		param.setAttribute("dao", mallDAO);
		param.setAttribute("mallList",mallList);
		param.setAttribute("mngBean", new MerchantMngDAO().getById(mBean.getMerchantId()));
		param.setAttribute("mbBean", new MerchantBillDAO().getByMerchantId(mBean.getMerchantId()));
		param.setAttribute("gBean", new GroupDAO().getByMerchantId(mBean.getMerchantId()));
		return new ModelAndView("/member/merchantView","mBean",mBean);
		
	}
	
	public ModelAndView memberList(ParamUtil param){
		MemberBean mBean = new MemberBean();
		MemberDAO mDAO = new MemberDAO();
		
		param.toBean(mBean);
		param.toBean(mDAO);
		param.setDate(mDAO);
		
		List<MemberBean> mList = null;
		if(param.getString("format").equals("excel")){
			mDAO.pageSize =1000000;
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
		mList = mDAO.get(mBean);
		param.setAttribute("dao", mDAO);
		param.setAttribute("mBean", mBean);
		if(param.getString("format").equals("excel")){
			return new ModelAndView("/member/memberListExl","mList",mList);
		}else{
			new PagingUtil(param,"/member.do");
			return new ModelAndView("/member/memberList","mList",mList);
		}
	}
	
	public ModelAndView merchantList(ParamUtil param){
		MerchantBean mBean = new MerchantBean();
		MerchantDAO mDAO = new MerchantDAO();
		
		param.toBean(mBean);
		param.toBean(mDAO);
		param.setDate(mDAO);
		
		List<MerchantBean> mList = null;
		if(param.getString("format").equals("excel")){
			mDAO.pageSize =1000000;
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
		mList = mDAO.get(mBean);
		param.setAttribute("dao", mDAO);
		param.setAttribute("mBean", mBean);
		if(param.getString("format").equals("excel")){
			return new ModelAndView("/member/merchantListExl","mList",mList);
		}else{
			new PagingUtil(param,"/member.do");
			return new ModelAndView("/member/merchantList","mList",mList);
		}
	}
	
}

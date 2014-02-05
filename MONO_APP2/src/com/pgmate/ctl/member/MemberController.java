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
import com.pgmate.model.db.AgentBean;
import com.pgmate.model.db.GroupBean;
import com.pgmate.model.db.GroupMerchantBean;
import com.pgmate.model.db.MemberBean;
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.MerchantMallBean;
import com.pgmate.model.db.MerchantMngBean;
import com.pgmate.model.db.dao.AgentBillDAO;
import com.pgmate.model.db.dao.AgentDAO;
import com.pgmate.model.db.dao.GroupDAO;
import com.pgmate.model.db.dao.MemberDAO;
import com.pgmate.model.db.dao.MerchantBillDAO;
import com.pgmate.model.db.dao.MerchantDAO;
import com.pgmate.model.db.dao.MerchantMallDAO;
import com.pgmate.model.db.dao.MerchantMngDAO;
import com.pgmate.model.db.dao.VanDAO;
import com.pgmate.resource.GSIResource;
import com.pgmate.web.util.AccessUtil;
import com.pgmate.web.util.PagingUtil;
import com.pgmate.web.util.ParamUtil;

public class MemberController implements Controller {
	
	private SSOBean ssoBean = null;
	
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {

		ParamUtil param = new ParamUtil(request);	
		ModelAndView mav = null;
		
		try{ 
			if(param.getSession("sso") == null){
				mav = new ModelAndView("/common/textResponse","RESPONSE","MSG||Your session is about to expire.");
			}else{
				ssoBean = new AccessUtil().get(param);
				if(param.getString("request").equals("list")){
					mav = merchantList(param);
				}else if(param.getString("request").equals("view")){
					mav = merchantView(param);
				}else if(param.getString("request").equals("psForm")){
					mav = passwordForm(param);
				}else if(param.getString("request").equals("psUpdate")){
					mav = passwordEdit(param);
				}else if(param.getString("request").equals("add")){
					mav = merchantAdd(param);
				}else if(param.getString("request").equals("editForm")){
					return new ModelAndView("/member/merchantEditForm","mBean", new MerchantDAO().getByIdx(CommonUtil.parseLong(param.getString("idx"))));
				}else if(param.getString("request").equals("edit")){
					mav = merchantEdit(param);
				}else if(param.getString("request").equals("serviceAdd")){
					mav = merchantServiceAdd(param);
				}else if(param.getString("request").equals("serviceEditForm")){
					return new ModelAndView("/member/merchantServiceEditForm","mngBean", new MerchantMngDAO().getByIdx(CommonUtil.parseLong(param.getString("idx"))));
				}else if(param.getString("request").equals("serviceEdit")){
					mav = merchantServiceEdit(param);
				}else if(param.getString("request").equals("mlist")){
					mav = memberList(param);
				}else if(param.getString("request").equals("mview")){
					mav = memberView(param);
				}else if(param.getString("request").equals("mEditForm")){
					return new ModelAndView("/member/memberEdit","mBean", new MemberDAO().getById(param.getString("memberId")));
				}else if(param.getString("request").equals("mEdit")){
					mav = memberEdit(param);
				}else if(param.getString("request").equals("mAdd")){
					mav = memberAdd(param);
				}else if(param.getString("request").equals("groupSelectForm")){
					mav = groupSelectForm(param);
				}else if(param.getString("request").equals("groupSelect")){
					mav = groupSelect(param);
				}else if(param.getString("request").equals("glist")){
					mav = groupList(param);
				}else if(param.getString("request").equals("gview")){
					mav = groupView(param);
				}else if(param.getString("request").equals("gedit")){
					mav = groupEdit(param);
				}else if(param.getString("request").equals("geditForm")){
					return new ModelAndView("/member/groupEditForm","gBean", new GroupDAO().getByIdx(CommonUtil.parseLong(param.getString("idx"))));
				}else if(param.getString("request").equals("gadd")){
					mav = groupAdd(param);
				}else if(param.getString("request").equals("alist")){
					mav = agentList(param);
				}else if(param.getString("request").equals("aadd")){
					mav = agentAdd(param);
				}else if(param.getString("request").equals("aview")){
					mav = agentView(param);
				}else if(param.getString("request").equals("agentEditForm")){
					return new ModelAndView("/member/agentEdit","agentBean", new AgentDAO().getById(param.getString("agentId")));
				}else if(param.getString("request").equals("agentEdit")){
					mav = agentEdit(param);
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
	
	public ModelAndView agentEdit(ParamUtil param){
		
		AgentBean aBean = new AgentBean();
		param.toBean(aBean);
		
		if(new AgentDAO().update(aBean)){
			param.setMessage("OK||"+aBean.getName()+" Information has been updated.");
		}else{
			param.setMessage("NOK||"+aBean.getName()+" Information failed to update.");
		}
		return new ModelAndView("/common/ajaxResponse");
	}
	
	
	public ModelAndView agentView(ParamUtil param){
		
		AgentBean agentBean = new AgentDAO().getById(param.getString("agentId"));
		param.setAttribute("agentBean", agentBean);
		
		
		param.setAttribute("list", new AgentBillDAO().getView(" AND A.AGENT_ID ='"+param.getString("agentId")+"' "));
		return new ModelAndView("/member/agentView");
	}
	
	public ModelAndView agentAdd(ParamUtil param){
		AgentBean aBean = new AgentBean();
		param.toBean(aBean);
		aBean.setPassword(new MDEncoder().encodeSHA1(aBean.getPassword()));
		if(new AgentDAO().insert(aBean)){
			param.setMessage("OK||"+aBean.getName()+" has been registered.");
		}else{
			param.setMessage("NOK||"+aBean.getName()+" failed to register.");
		}
		return new ModelAndView("/common/ajaxResponse");
		
	}
	
	public ModelAndView agentList(ParamUtil param){
		AgentBean aBean = new AgentBean();
		AgentDAO aDAO = new AgentDAO();
		
		param.toBean(aBean);
		param.toBean(aDAO);
		param.setDate(aDAO);
		
		List<AgentBean> aList = null;
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
		aList = aDAO.get(aBean);
		param.setAttribute("dao", aDAO);
		param.setAttribute("aBean", aBean);
		new PagingUtil(param,"/member.do");
		return new ModelAndView("/member/agentList","aList",aList);
	}
	
	public ModelAndView groupAdd(ParamUtil param){
		GroupBean gBean = new GroupBean();
		param.toBean(gBean);
		gBean.setPassword(new MDEncoder().encodeSHA1(gBean.getPassword()));
		if(new GroupDAO().insert(gBean)){
			param.setMessage("OK||"+gBean.getName()+" has been registered.");
		}else{
			param.setMessage("NOK||"+gBean.getName()+" failed to register.");
		}
		return new ModelAndView("/common/ajaxResponse");
		
	}
	
	public ModelAndView groupEdit(ParamUtil param){
		
		GroupBean gBean = new GroupBean();
		param.toBean(gBean);
		
		if(new GroupDAO().update(gBean)){
			param.setMessage("OK||"+gBean.getName()+" Information has been updated.");
		}else{
			param.setMessage("NOK||"+gBean.getName()+" Information failed to update.");
		}
		return new ModelAndView("/common/ajaxResponse");
	}
	
	public ModelAndView groupView(ParamUtil param){
		GroupBean gBean = new GroupBean();
		GroupDAO gDAO = new GroupDAO();
		
		if(ssoBean.getMemberRole().equals(GSIResource.MEMBER_ROLE_GROUP)){
			gBean = gDAO.getById(ssoBean.getMemberId());
		}else{
			gBean = gDAO.getById(param.getString("groupId"));
		}
		
		param.setAttribute("gBean", gBean);
		return new ModelAndView("/member/groupView");
		
	}

	
	public ModelAndView groupList(ParamUtil param){
		GroupBean gBean = new GroupBean();
		GroupDAO gDAO = new GroupDAO();
		
		param.toBean(gBean);
		param.toBean(gDAO);
		param.setDate(gDAO);
		
		List<GroupBean> gList = null;
		if(param.getString("format").equals("excel")){
			gDAO.pageSize =1000000;
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
		gList = gDAO.get(gBean);
		param.setAttribute("dao", gDAO);
		param.setAttribute("gBean", gBean);
		new PagingUtil(param,"/member.do");
		return new ModelAndView("/member/groupList","gList",gList);
	}
	
	public ModelAndView groupSelectForm(ParamUtil param){
		param.setAttribute("merchantId", param.getString("merchantId"));
		param.setAttribute("glist", new GroupDAO().getByActive("1"));
		if(!CommonUtil.isNullOrSpace(param.getString("groupId"))){
			param.setAttribute("groupId", param.getString("groupId"));
		}
		return new ModelAndView("/member/groupSelectForm");
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
					param.setMessage("OK||"+gmBean.getMerchantId()+" Group Information has been terminated");
				}else{
					param.setMessage("NOK||"+gmBean.getMerchantId()+" Group Information failed to terminate.");
				}
			}else{
				if(gDAO.updateGroupMerchant(gmBean)){
					param.setMessage("OK||"+gmBean.getMerchantId()+" Group has been updated. (MerchantId["+gmBean.getMerchantId()+"] groupId["+gmBean.getGroupId()+"])");
					message = "가맹점 그룹 변경 성공 MerchantId["+gmBean.getMerchantId()+"] groupId["+gmBean.getGroupId()+"]";
				}else{
					param.setMessage("NOK||"+gmBean.getMerchantId()+" Group failed to update. (MerchantId["+gmBean.getMerchantId()+"] groupId["+gmBean.getGroupId()+"])");
					message = "가맹점 그룹 변경 실패 MerchantId["+gmBean.getMerchantId()+"] groupId["+gmBean.getGroupId()+"]";
				}
			}
		}else{							// insert 처리
			if(CommonUtil.isNullOrSpace(gmBean.getGroupId())){
				param.setMessage("NOK||"+gmBean.getMerchantId()+" Group ID is not selected");
			}else{
				if(gDAO.insertGroupMerchant(gmBean)){
					param.setMessage("OK||"+gmBean.getMerchantId()+" Group Information has been updated.  (MerchantId["+gmBean.getMerchantId()+"] groupId["+gmBean.getGroupId()+"])");
					message = "가맹점 그룹 등록 성공 MerchantId["+gmBean.getMerchantId()+"] groupId["+gmBean.getGroupId()+"]";
				}else{
					param.setMessage("OK||"+gmBean.getMerchantId()+" Group Information failed to update.  (MerchantId["+gmBean.getMerchantId()+"] groupId["+gmBean.getGroupId()+"])");
					message = "가맹점 그룹 등록  실패 MerchantId["+gmBean.getMerchantId()+"] groupId["+gmBean.getGroupId()+"]";
				}
			}
		}
		
		Log.debug("log.day",(String)param.getAttribute("message"),this);
		return new ModelAndView("/common/ajaxResponse");
	}
	
	
	
	public ModelAndView merchantServiceEdit(ParamUtil param){
		
		MerchantMngBean mngBean = new MerchantMngBean();
		param.toBean(mngBean);
		
		if(new MerchantMngDAO().update(mngBean)){
			param.setMessage("OK||"+mngBean.getMerchantId()+" Service Information has been updated.");
		}else{
			param.setMessage("NOK||"+mngBean.getMerchantId()+" Service Information failed to update.");
		}
		return new ModelAndView("/common/ajaxResponse");
	}
	
	public ModelAndView merchantServiceAdd(ParamUtil param){
		
		MerchantMngBean mngBean = new MerchantMngBean();
		param.toBean(mngBean);
		

		if(new MerchantMngDAO().insert(mngBean)){
			param.setMessage("OK||"+mngBean.getMerchantId()+" Service Information has been registered.");
		}else{
			param.setMessage("NOK||"+mngBean.getMerchantId()+" Service Information failed to register.");
		}
		return new ModelAndView("/common/ajaxResponse");
	}
	
	public ModelAndView merchantEdit(ParamUtil param){
		
		MerchantBean mBean = new MerchantBean();
		param.toBean(mBean);
		mBean.setServiceDate(CommonUtil.stringToTimestamp(param.getReplaceString("serviceDates",CommonUtil.getCurrentDate("yyyyMMdd"))));
		
		if(new MerchantDAO().update(mBean)){
			param.setMessage("OK||"+mBean.getName()+" Information has been updated.");
		}else{
			param.setMessage("NOK||"+mBean.getName()+" Information failed to update.");
		}
		return new ModelAndView("/common/ajaxResponse");
	}
	
	public ModelAndView merchantAdd(ParamUtil param){
		MerchantBean mBean = new MerchantBean();
		param.toBean(mBean);
		mBean.setServiceDate(CommonUtil.stringToTimestamp(param.getReplaceString("serviceDates",CommonUtil.getCurrentDate("yyyyMMdd"))));
		mBean.setPassword(new MDEncoder().encodeSHA1(mBean.getPassword()));
		if(new MerchantDAO().insert(mBean)){
			param.setMessage("OK||"+mBean.getName()+" has been registered.");
		}else{
			param.setMessage("NOK||"+mBean.getName()+" failed to register.");
		}
		return new ModelAndView("/common/ajaxResponse");
		
	}
	
	public ModelAndView memberEdit(ParamUtil param){
		
		MemberBean mBean = new MemberBean();
		param.toBean(mBean);
		
		if(new MemberDAO().update2(mBean)){
			param.setMessage("OK||Administrator has been updated.");
		}else{
			param.setMessage("NOK||Administrator failed to update.");
		}
		return new ModelAndView("/common/ajaxResponse");
	}
	
	public ModelAndView memberAdd(ParamUtil param){
		
		MemberBean mBean = new MemberBean();
		param.toBean(mBean);
		
		mBean.setPassword(new MDEncoder().encodeSHA1(mBean.getPassword()));
		if(new MemberDAO().insert(mBean)){
			param.setMessage("OK||Administrator has been registered.");
			
		}else{
			param.setMessage("OK||Administrator failed to register.");
		}
		return new ModelAndView("/common/ajaxResponse");
		
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
	
	public ModelAndView passwordForm(ParamUtil param){
		param.setAttribute("role", param.getString("role"));
		param.setAttribute("id", param.getString("id"));
		return new ModelAndView("/member/password");
	}
	
	
	public ModelAndView passwordEdit(ParamUtil param){
		if(param.isEqual("role","MEMBER")){
			MemberBean memberBean = new MemberDAO().getById(param.getString("id"));
			memberBean.setPassword(new MDEncoder().encodeSHA1(param.getString("password")));
			if(new MemberDAO().pwupdate(memberBean)){
				param.setMessage("OK||"+memberBean.getName()+" password was updated");
			}else{
				param.setMessage("NOK||"+memberBean.getName()+" password was not updated");
			}
		}else if(param.isEqual("role","MERCHANT")){
			MerchantBean merchantBean = new MerchantDAO().getById(param.getString("id"));
			merchantBean.setPassword(new MDEncoder().encodeSHA1(param.getString("password")));
			if(new MerchantDAO().pwupdate(merchantBean)){
				param.setMessage("OK||"+merchantBean.getName()+" password was updated");
			}else{
				param.setMessage("NOK||"+merchantBean.getName()+" password was not updated");
			}
		}else if(param.isEqual("role","GROUP")){
			GroupBean groupBean = new GroupDAO().getById(param.getString("id"));
			groupBean.setPassword(new MDEncoder().encodeSHA1(param.getString("password")));
			if(new GroupDAO().pwupdate(groupBean)){
				param.setMessage("OK||"+groupBean.getName()+" password was updated");
			}else{
				param.setMessage("NOK||"+groupBean.getName()+" password was not updated");
			}
		}else if(param.isEqual("role","AGENT")){
			AgentBean agentBean = new AgentDAO().getById(param.getString("id"));
			agentBean.setPassword(new MDEncoder().encodeSHA1(param.getString("password")));
			if(new AgentDAO().pwupdate(agentBean)){
				param.setMessage("OK||"+agentBean.getName()+" password was updated");
			}else{
				param.setMessage("NOK||"+agentBean.getName()+" password was not updated");
			}
		}else{
			param.setMessage("NOK||Parameter role was not included");
		}
		
		return new ModelAndView("/common/ajaxResponse");
	}
	
	public ModelAndView passwordModi2(ParamUtil param){
		param.setAttribute("mBean", new MemberDAO().getById(param.getString("memberId")));
		return new ModelAndView("/member/password");
	}
	
	public ModelAndView memberView(ParamUtil param){
		MemberBean mBean = new MemberDAO().getById(param.getString("memberId"));
		return new ModelAndView("/member/memberView","mBean", mBean);
	}
	
	public ModelAndView merchantView(ParamUtil param){
		MerchantBean mBean = new MerchantDAO().getById(param.getString("merchantId"));
		MerchantMallDAO mallDAO = new MerchantMallDAO();
		List<MerchantMallBean> mallList = mallDAO.getByMerchantId(mBean.getMerchantId());
		MerchantMngBean merchantMngBean = new MerchantMngDAO().getById(mBean.getMerchantId());
		param.setAttribute("dao", mallDAO);
		param.setAttribute("mallList",mallList);
		param.setAttribute("mngBean",merchantMngBean);
		param.setAttribute("vanBean", new VanDAO().getByVanAndVanId(merchantMngBean.getVan(),merchantMngBean.getVanId()));
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
		new PagingUtil(param,"/member.do");
		return new ModelAndView("/member/memberList","mList",mList);
	}
	
	public ModelAndView merchantList(ParamUtil param){
		MerchantBean mBean = new MerchantBean();
		MerchantDAO mDAO = new MerchantDAO();
		
		param.toBean(mBean);
		param.toBean(mDAO);
		param.setDate(mDAO);
		
		/*
		 * 그룹일 경우 그룹 아이디 설정 처리
		 */
		if(ssoBean.getMemberRole().equals(GSIResource.MEMBER_ROLE_GROUP)){
			mBean.setPublicGroupId(ssoBean.getMemberId());
		}
		
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
		new PagingUtil(param,"/member.do");
		return new ModelAndView("/member/merchantList","mList",mList);
	}
	
}

/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.ctl.payment.PaymentController.java
 * Date	        : Jan 28, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.ctl.payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import biz.trustnet.common.comm.client.SocketManager;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.comm.HeaderBean;
import com.pgmate.model.comm.T001Bean;
import com.pgmate.model.comm.T002Bean;
import com.pgmate.model.comm.T003Bean;
import com.pgmate.model.comm.T004Bean;
import com.pgmate.model.db.dao.CodeDAO;
import com.pgmate.payment.util.GSICipher;
import com.pgmate.web.util.ParamUtil;

public class PaymentController implements Controller {
	
	private ParamUtil param = null;
	private PaymentCheck check = null;
	
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		param = new ParamUtil(request);
		check = new PaymentCheck();
		 
		try{
			Log.debug("log.pay","REQUEST =["+param.getQueryString()+"]",this);
			Log.debug("log.pay","DOMAIN =["+param.getString("DOMAIN")+"]",this);
			if(param.isNullOrSpace("IP_ADDRESS")){
				param.put("IP_ADDRESS", request.getRemoteAddr());
			}
			if(check.webPayCheck(param)){
				if(param.getString("TRN_TYPE").equals("T001")){
					mav = t001Payment(param);
				}else if(param.getString("TRN_TYPE").equals("T002")){
					mav = t002Payment(param);
				}else if(param.getString("TRN_TYPE").equals("T003")){
					mav = t003Payment(param);
				}else if(param.getString("TRN_TYPE").equals("T004")){
					mav = t004Payment(param);
				}else{
					mav = null;
				}
			}else{
				param.put("RESULT_CD", "2");
				param.put("RESULT_MSG", "5");
				param.put("MESSAGE",check.getMessage());
				Log.debug("log.pay", "CHECK ERROR =["+check.getMessage()+"]", this);
				mav = getMessage();
			}
		
		}catch(Exception e){
			Log.debug("log.day","Payment Controller"+CommonUtil.getExceptionMessage(e),this);
		}
		return mav;
	}
	
	
	public ModelAndView t001Payment(ParamUtil param){
		HeaderBean headerBean = check.getHeaderBean(param);
		headerBean.setSpecLength("0927");
		T001Bean tBean = check.getT001Bean(param);
		Log.debug("log.pay", "CAVV =["+tBean.getCavv()+"]", this);
		byte[] response = connect((headerBean.getTransaction(tBean.getTransaction())).getBytes());
		headerBean = new HeaderBean(response);
		tBean = new T001Bean(CommonUtil.toString(response,200,response.length-200));
		param.put("TRN_DATE", headerBean.getTrnDate());
		param.put("TRN_RES_DATE", headerBean.getTrnResDate());
		param.put("TRANSACTION_ID", headerBean.getTransactionId());
		param.put("RESULT_CD", headerBean.getResultCd());
		param.put("RESULT_MSG", headerBean.getResultMsg());
		
		
		param.put("APPROVAL_NO", tBean.getApprovalNo());
		param.put("MESSAGE", new CodeDAO().getByAliasCode("KAPPV_CODE", headerBean.getResultMsg()).getEnValue());
		Log.debug("log.pay", "MESSAGE =["+param.getString("MESSAGE")+"]", this);
		return getMessage();
		
	}
	
	public ModelAndView t002Payment(ParamUtil param){
		HeaderBean headerBean = check.getHeaderBean(param);
		headerBean.setSpecLength("0396");
		T002Bean tBean = check.getT002Bean(param);
		
		byte[] response = connect((headerBean.getTransaction(tBean.getTransaction())).getBytes());
		headerBean = new HeaderBean(response);
		tBean = new T002Bean(CommonUtil.toString(response,200,response.length-200));
		
		param.put("TRN_DATE", headerBean.getTrnDate());
		param.put("TRN_RES_DATE", headerBean.getTrnResDate());
		param.put("TRANSACTION_ID", headerBean.getTransactionId());
		param.put("RESULT_CD", headerBean.getResultCd());
		param.put("RESULT_MSG", headerBean.getResultMsg());
		
		
		param.put("APPROVAL_NO", tBean.getApprovalNo());
		param.put("MESSAGE", new CodeDAO().getByAliasCode("KAPPV_CODE", headerBean.getResultMsg()).getEnValue());
		Log.debug("log.pay", "MESSAGE =["+param.getString("MESSAGE")+"]", this);
		return getMessage();
		
	}
	
	public ModelAndView t003Payment(ParamUtil param){
		HeaderBean headerBean = check.getHeaderBean(param);
		headerBean.setSpecLength("0396");
		T003Bean tBean = check.getT003Bean(param);
		
		byte[] response = connect((headerBean.getTransaction(tBean.getTransaction())).getBytes());
		headerBean = new HeaderBean(response);
		tBean = new T003Bean(CommonUtil.toString(response,200,response.length-200));
		
		param.put("TRN_DATE", headerBean.getTrnDate());
		param.put("TRN_RES_DATE", headerBean.getTrnResDate());
		param.put("TRANSACTION_ID", headerBean.getTransactionId());
		param.put("RESULT_CD", headerBean.getResultCd());
		param.put("RESULT_MSG", headerBean.getResultMsg());
		
		 
		param.put("RRESULT_CD", tBean.getResultCd());
		param.put("RRESULT_MSG", tBean.getResultMsg());
		param.put("RTRANSACTION_ID", tBean.getTransactionId());
		param.put("RAPPROVAL_NO", tBean.getApprovalNo());
		param.put("RSTATUS", tBean.getStatus());
		param.put("MESSAGE", new CodeDAO().getByAliasCode("KAPPV_CODE", headerBean.getResultMsg()).getEnValue());
		Log.debug("log.pay", "MESSAGE =["+param.getString("MESSAGE")+"]", this);
		return getMessage();
		
	} 
	
	
	public ModelAndView t004Payment(ParamUtil param){
		HeaderBean headerBean = check.getHeaderBean(param);
		headerBean.setSpecLength("0496");
		T004Bean tBean = check.getT004Bean(param);
		
		byte[] response = connect((headerBean.getTransaction(tBean.getTransaction())).getBytes());
		headerBean = new HeaderBean(response);
		tBean = new T004Bean(CommonUtil.toString(response,200,response.length-200));
		
		param.put("TRN_DATE", headerBean.getTrnDate());
		param.put("TRN_RES_DATE", headerBean.getTrnResDate());
		param.put("TRANSACTION_ID", headerBean.getTransactionId());
		param.put("RESULT_CD", headerBean.getResultCd());
		param.put("RESULT_MSG", headerBean.getResultMsg());
		
		param.put("MESSAGE", new CodeDAO().getByAliasCode("KAPPV_CODE", headerBean.getResultMsg()).getEnValue());
		Log.debug("log.pay", "MESSAGE =["+param.getString("MESSAGE")+"]", this);
		return getMessage();
		
	}
	
	
	public byte[] connect(byte[] request){
		GSICipher cipher	= new GSICipher();
		byte[] encrypt 		= cipher.encrypt(request);
		byte[] decrypt 		= null;
		SocketManager socket = new SocketManager();
		socket.setSocketInfo("localhost","20000");
		socket.setSocketTimeout(60000, 60000);
		//Log.debug("log.pay","WEB >> PWN  ["+CommonUtil.toString(encrypt)+"]",this);
		try{
			decrypt = socket.connect3(4, encrypt);
			//Log.debug("log.pay","WEB << PWN  ["+CommonUtil.toString(decrypt)+"]",this);
			decrypt = cipher.decrypt(decrypt);
		}catch(Exception e){
			Log.debug("log.pay","COMMUNICATION ERROR ="+e.getMessage(),this);
			HeaderBean headerBean = new HeaderBean(request);
			headerBean.setResultCd("1");
			headerBean.setResultMsg("X103");
			headerBean.setExtra(e.getMessage());
			decrypt = headerBean.getTransaction(CommonUtil.toString(request,200,request.length-200)).getBytes();
		}
		return decrypt;
	}
	
	
	
	public ModelAndView getMessage(){
		if(param.getString("RES_TYPE").equals("REDIRECT")){
			param.setAttribute("RESULT_CD", param.getString("RESULT_CD"));
			param.setAttribute("MESSAGE", param.getString("MESSAGE"));
			param.setAttribute("REDIRECT_URL", param.getString("REDIRECT_URL"));
			Log.debug("log.pay","RESPONSE =["+param.getQueryString("utf-8")+"]",this);
			param.setAttribute("PAYRESPONSE", param.getHIDDENTag());
			return new ModelAndView("/payment/response/webResponse");
		}else{
			param.setAttribute("PAYRESPONSE", param.getQueryString("utf-8"));
			Log.debug("log.pay","RESPONSE =["+(String)param.getAttribute("PAYRESPONSE")+"]",this);
			return new ModelAndView("/payment/response/textResponse");
		}
	}
}
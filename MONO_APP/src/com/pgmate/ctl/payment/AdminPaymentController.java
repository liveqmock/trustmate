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

import com.pgmate.ctl.sso.SSOBean;
import com.pgmate.model.comm.HeaderBean;
import com.pgmate.model.comm.T001Bean;
import com.pgmate.model.comm.T002Bean;
import com.pgmate.model.db.TrnsctnBean;
import com.pgmate.model.db.TrnsctnVoidBean;
import com.pgmate.model.db.dao.CodeDAO;
import com.pgmate.model.db.dao.TrnsctnDAO;
import com.pgmate.model.db.dao.TrnsctnVoidDAO;
import com.pgmate.payment.util.Comment;
import com.pgmate.payment.util.GSICipher;
import com.pgmate.web.util.ParamUtil;

public class AdminPaymentController implements Controller {
	
	private SSOBean ssoBean = null;
		
		public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {

			ParamUtil param = new ParamUtil(request);	
			ModelAndView mav = null;
			ssoBean = (SSOBean)param.getSession("sso");
			try{
				if(param.getString("TRN_TYPE").equals("T001")){
					mav = creditPayment(param);
				}else if(param.getString("TRN_TYPE").equals("T002")){
					mav = voidPayment(param);
				}else if(param.getString("REQ_TYPE").equals("mechantT002")){
					mav = voidPaymentMerchant(param);
				}else{
					mav = null;
				}
				
			}catch(Exception e){
				Log.debug("log.day","Payment Controller"+CommonUtil.getExceptionMessage(e),this);
			}
			return mav;
		}
		
		public ModelAndView creditPayment(ParamUtil param){
			HeaderBean headerBean = new HeaderBean();
			T001Bean tBean = new T001Bean();
			param.toBean(headerBean);
			param.toBean(tBean);
			byte[] response = connect((headerBean.getTransaction(tBean.getTransaction())).getBytes());
			headerBean = new HeaderBean(response);
			tBean = new T001Bean(CommonUtil.toString(response,200,response.length-200));
			param.setAttribute("headerBean", headerBean);
			param.setAttribute("tBean", tBean);
			return new ModelAndView("/payment/PaymentComplete");
		}
		
		public ModelAndView voidPayment(ParamUtil param){
			HeaderBean headerBean = new HeaderBean();
			T002Bean tBean = new T002Bean();
			param.toBean(headerBean);
			param.toBean(tBean);
			headerBean.setTrnDate(CommonUtil.getCurrentDate("yyyyMMddHHmmss"));
			tBean.setRTransactionId(param.getString("rTransactionId"));
			byte[] response = connect((headerBean.getTransaction(tBean.getTransaction())).getBytes());
			headerBean = new HeaderBean(response);
			tBean = new T002Bean(CommonUtil.toString(response,200,response.length-200));
			param.setAttribute("headerBean", headerBean);
			param.setAttribute("tBean", tBean);
			Log.debug("log.day","거래 취소 요청 거래번호 =["+tBean.getRTransactionId()+"] 사용자=["+ssoBean.getMemberId()+"]",this);
			if(headerBean.getResultMsg().equals("0000")){
				TrnsctnVoidBean trnsctnvoidBean = new TrnsctnVoidDAO().getByTransactionId(tBean.getRTransactionId());
				new Comment().updateTrnsctnComment(trnsctnvoidBean.getCommentIdx(),ssoBean.getMemberId(),"["+ssoBean.getMemberId()+"] "+param.getString("comment"));
				Log.debug("log.day","신용카드 거래 취소 완료",this);
				param.setAttribute("success","success");
				return new ModelAndView("/common/popupredirect2","message","[Credit card transaction cancellation acceptance completion] TRANSACTIONID =["+trnsctnvoidBean.getTransactionId() +"]");
			}else{
				Log.debug("log.day","신용카드 거래취소 실패",this);
				param.setAttribute("success","failed");
				return new ModelAndView("/common/popupredirect2","message","[Credit card transaction cancellation acceptance failure] ");
			}
			
		}
		
		public ModelAndView voidPaymentMerchant(ParamUtil param){
			HeaderBean headerBean = new HeaderBean();
			T002Bean tBean = new T002Bean();
			TrnsctnBean trnsctnBean = new TrnsctnDAO().getByIdx(param.getString("idx"));
			headerBean.setTransactionId(trnsctnBean.getTransactionId());
			headerBean.setSpecType("CFIX");
			headerBean.setTrnType("T002");
			headerBean.setMerchantId(trnsctnBean.getMerchantId());
			headerBean.setMallId(trnsctnBean.getMallId());
			headerBean.setServiceType(trnsctnBean.getServiceType());
			headerBean.setIpAddress(trnsctnBean.getIpAddress());
			headerBean.setPayNo(trnsctnBean.getPayNo());
			headerBean.setTrnDate(CommonUtil.getCurrentDate("yyyyMMddHHmmss"));
			
			tBean.setVoidType("1");
			tBean.setRTransactionId(trnsctnBean.getTransactionId());
			
			byte[] response = connect((headerBean.getTransaction(tBean.getTransaction())).getBytes());
			headerBean = new HeaderBean(response);
			tBean = new T002Bean(CommonUtil.toString(response,200,response.length-200));
			Log.debug("log.day","거래 취소 요청 거래번호 =["+tBean.getRTransactionId()+"] 사용자=["+ssoBean.getMemberId()+"]",this);
			if(headerBean.getResultMsg().equals("0000")){
				TrnsctnVoidBean trnsctnvoidBean = new TrnsctnVoidDAO().getByTransactionId(trnsctnBean.getTransactionId());
				new Comment().updateTrnsctnComment(trnsctnvoidBean.getCommentIdx(),ssoBean.getMemberId(),"["+ssoBean.getMemberId()+"] "+param.getString("comment"));
				param.setAttribute("RESPONSE", "OK");
			}else{
				String msg = new CodeDAO().getByAliasCode("KAPPV_CODE", headerBean.getResultMsg()).getEnValue();
				param.setAttribute("RESPONSE", "Credit card transaction cancellation acceptance failure ["+msg+"]");
			}
			return new ModelAndView("/common/textResponse");
			
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
}
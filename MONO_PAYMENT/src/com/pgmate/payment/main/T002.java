/* 
 * Project Name : GSI_PROJECT
 * Project      : GSI_PAYMENT
 * File Name    : com.pgmate.payment.main.Void.java
 * Date	        : Jan 27, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.payment.main;

import java.util.Calendar;
import java.util.List;

import kr.co.smartro.adapter.global.client.SmilePayClient;
import kr.co.smartro.adapter.global.client.dto.WebMessageDTO;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.inicis.inipay4.util.INIdata;
import com.kcp.C_PP_CLI;
import com.pgmate.model.comm.HeaderBean;
import com.pgmate.model.comm.T002Bean;
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.MerchantMngBean;
import com.pgmate.model.db.TrnsctnBean;
import com.pgmate.model.db.TrnsctnVoidBean;
import com.pgmate.model.db.dao.SMSDAO;
import com.pgmate.model.db.dao.TrnsctnDAO;
import com.pgmate.model.db.dao.TrnsctnVoidDAO;
import com.pgmate.payment.conf.ServerConfigBean;
import com.pgmate.payment.inicis.INICISCredit;
import com.pgmate.payment.kcp.KCPCredit;
import com.pgmate.payment.ksnet.KSNET;
import com.pgmate.payment.ksnet.KSNETHeader;
import com.pgmate.payment.ksnet.KSNETResponse;
import com.pgmate.payment.ksnet.KSNETVoid;
import com.pgmate.payment.smartro.SMARTRO;
import com.pgmate.payment.util.InicisUtil;
import com.pgmate.payment.util.KcpUtil;

public class T002 {

	private ServerConfigBean configBean = null;
	private MerchantBean merchantBean 	= null;
	private MerchantMngBean merchantMngBean = null;
	private HeaderBean headerBean		= null;
	private T002Bean tBean				= null;
	private TrnsctnDAO trnsctnDAO		= null;
	
	public T002(ServerConfigBean configBean,MerchantBean merchantBean,MerchantMngBean merchantMngBean){
		this.configBean = configBean;
		this.merchantBean = merchantBean;
		this.merchantMngBean = merchantMngBean;
	}
	
	public String execute(HeaderBean headerBean,T002Bean tBean){
		this.headerBean = headerBean;
		this.tBean		= tBean;
		
		String response = "";
		TrnsctnBean trnsctnBean = null;
		trnsctnDAO = new TrnsctnDAO();
		
		if(tBean.getVoidType().equals("1")){
			trnsctnBean = trnsctnDAO.getMerchantTrnsctnById(tBean.getRTransactionId(),headerBean.getMerchantId());
		}else{
			trnsctnBean = new TrnsctnBean();
			if(merchantMngBean.getCurType().equals("USD") || merchantMngBean.getCurType().equals("EUR")){
				trnsctnBean.setAmount(CommonUtil.parseDouble(tBean.getAmount())/100);
			}else{
				trnsctnBean.setAmount(CommonUtil.parseDouble(tBean.getAmount()));
			}
			trnsctnBean.setApprovalNo(tBean.getRApprovalNo());
			trnsctnBean.setPayNo(tBean.getRPayNo());
			trnsctnBean.setTrnReqDate(CommonUtil.stringToTimestamp(tBean.getApprovalDay()));
			List<TrnsctnBean> trnsctnList = trnsctnDAO.get(trnsctnBean);
			if(trnsctnList.size() == 0){
				trnsctnBean = new TrnsctnBean();
			}else{
				trnsctnBean = (TrnsctnBean)trnsctnList.get(0);
			}
		}
		

		VoidMessage message = new VoidMessage();
		if(trnsctnBean.getTransactionId().equals("")){
			Log.debug("log.day","원거래를 찾을 수 없습니다.",this);
			response = getErrorMessage("P10G");
		}else if(message.isAleadyVoided(trnsctnBean.getTrnStatus())){
			Log.debug("log.day","기취소 거래입니다.["+trnsctnBean.getTrnStatus()+"]",this);
			response = getErrorMessage("P10Q");
		}else if(!message.isPossible(trnsctnBean.getTrnStatus())){
			Log.debug("log.day","취소 가능한 거래가 아닙니다.["+trnsctnBean.getTrnStatus()+"]",this);
			response = getErrorMessage("P10L");
		}else if(CommonUtil.parseLong(CommonUtil.getOpDate(Calendar.MONTH, -6, CommonUtil.getCurrentDate("yyyyMMdd"))) > CommonUtil.parseLong(CommonUtil.convertTimestampToString(trnsctnBean.getTrnReqDate(),"yyyyMMdd"))){
			Log.debug("log.day","6개월 기관경과 취소가능한거래 아닙니다.["+CommonUtil.convertTimestampToString(trnsctnBean.getTrnReqDate(),"yyyyMMdd")+"]",this);
			response = getErrorMessage("P118");
		}else{
			
			TrnsctnVoidBean voidBean = getTrnsctnVoidBean(trnsctnBean);
			
			if(trnsctnBean.getTemp2().equals("DEMO")){
				voidBean.setVoidVanTransactionId("DEMO_"+CommonUtil.getCurrentDate("yyyyMMddHHmmss"));
				voidBean.setVoidResultCd("0000");
				headerBean.setResultCd("0");
				headerBean.setResultMsg("0000");
				Log.debug("log.day","DEMO TRANSACTION VOID",this);
			}else{
				Log.debug("log.day",trnsctnBean.getVan()+" VOID TRANSACTION",this);
				if(trnsctnBean.getVan().equals("KSNET")){
					
					KSNET ksnet = new KSNET();
					KSNETHeader ksHeader = ksnet.getHeader(headerBean, merchantMngBean);
					if(!trnsctnBean.getTemp2().equals(merchantMngBean.getVanId())){
						ksHeader.setMerchantId(trnsctnBean.getTemp2());
					}
					
					KSNETVoid ksVoid = ksnet.getVoid(trnsctnBean.getVanTransactionId());
					String request = ksHeader.getHeader(ksVoid.getKSNETVoid());
					byte[] res= null;
					
					try{
						res = ksnet.commKsnet(request);
						KSNETHeader ksRHeader = new KSNETHeader(CommonUtil.toString(res,0,300));
						KSNETResponse ksResponse = new KSNETResponse(CommonUtil.toString(res,300,res.length - 300));
						voidBean.setVoidVanTransactionId(ksResponse.getKsnetTrnId());
						Log.debug("log.day","MESSAGE = ["+ksResponse.getMessage1()+ksResponse.getMessage2()+"]",this);
						if(ksResponse.getResponseCode().equals("O")){
							headerBean.setResultCd("0");
							headerBean.setResultMsg("0000");
							tBean.setApprovalNo(trnsctnBean.getApprovalNo());
							Log.debug("log.day","["+headerBean.getTransactionId()+"] VOID SUCCESS",this);
						}else{
							headerBean.setResultCd("1");
							headerBean.setResultMsg(ksResponse.getApprovalNo());
							Log.debug("log.day","["+headerBean.getTransactionId()+"] VOID FAILURE",this);
						}
					}catch(Exception e){
						Log.debug("log.day","["+headerBean.getTransactionId()+"] KSNET Communication Error="+CommonUtil.getExceptionMessage(e),this);
						headerBean.setResultCd("1");
						headerBean.setResultMsg("X103");
						new SMSDAO().systemSMS("KSNET CANCEL COMMUNICATION ERROR [X103]"+e.getMessage());
					}
				}else if(trnsctnBean.getVan().equals("INICIS")){
					INICISCredit ini = new INICISCredit();
					
					INIdata data = ini.execute(merchantMngBean, trnsctnBean.getVanTransactionId(),trnsctnBean.getTemp2());
					
					voidBean.setVoidVanTransactionId(data.getData("tid"));
					Log.debug("log.day","MESSAGE = ["+data.getData("ResultMsg")+"]",this);
					if(data.getData("ResultCode").equals("00")){
						headerBean.setResultCd("0");
						headerBean.setResultMsg("0000");
						tBean.setApprovalNo(trnsctnBean.getApprovalNo());
						Log.debug("log.day","["+headerBean.getTransactionId()+"] VOID SUCCESS",this);
					}else if(data.getData("ResultCode").equals("XX")){
						headerBean.setResultCd("1");
						headerBean.setResultMsg("X103");
						new SMSDAO().systemSMS("INICIS CANCEL COMMUNICATION ERROR [X103]");
						Log.debug("log.day","["+headerBean.getTransactionId()+"] INICIS Communication Error",this);
					}else{
						headerBean.setResultCd("1");
						InicisUtil iUtil = new InicisUtil();
						//MSG[0] = 코드 , MSG[1] = 문구 , MSG[2] = 메세지 
						String[] msg = iUtil.getResponseMessage(data.getData("ResultMsg"));
						trnsctnBean.setResultMsg(iUtil.getCode(msg[0])); //코드 변환 해야 함.
						Log.debug("log.day","MESSAGE=["+msg[0]+","+msg[1]+","+msg[2]+"]",this);
						
						Log.debug("log.day","["+headerBean.getTransactionId()+"] VOID FAILURE",this);
					}	
				}else if(trnsctnBean.getVan().equals("SMARTRO")){
					SmilePayClient payClient = new SMARTRO().executeVoid(trnsctnBean, merchantMngBean);
					
					WebMessageDTO responseDTO = payClient.doService();
					
					voidBean.setVoidVanTransactionId(responseDTO.getParameter("TID"));
					Log.debug("log.day","MESSAGE = ["+responseDTO.getParameter("RESP_MSG")+"]",this);
					if(responseDTO.getParameter("RESP_CODE").equals("0000")){
						headerBean.setResultCd("0");
						headerBean.setResultMsg("0000");
						tBean.setApprovalNo(trnsctnBean.getApprovalNo());
						Log.debug("log.day","["+headerBean.getTransactionId()+"] VOID SUCCESS",this);
					}else{
						headerBean.setResultCd("1");
						headerBean.setResultMsg(responseDTO.getParameter("RESP_CODE"));
						Log.debug("log.day","["+headerBean.getTransactionId()+"] VOID FAILURE",this);
					}
				}else if(trnsctnBean.getVan().equals("KCP")){
					C_PP_CLI  kcp = new KCPCredit().execute(merchantMngBean,trnsctnBean.getVanTransactionId(),trnsctnBean.getTemp2(),headerBean.getTransactionId());
					
					
					voidBean.setVoidVanTransactionId(kcp.mf_get_res("tno"));
					Log.debug("log.day","MESSAGE = ["+kcp.m_res_msg+"]",this);
					if(kcp.m_res_cd.equals("0000")){
						headerBean.setResultCd("0");
						headerBean.setResultMsg("0000");
						tBean.setApprovalNo(trnsctnBean.getApprovalNo());
						Log.debug("log.day","["+headerBean.getTransactionId()+"] VOID SUCCESS",this);
					}else if(kcp.m_res_cd.equals("9502") || kcp.m_res_cd.equals("S102") || kcp.m_res_cd.equals("9562") ){
						headerBean.setResultCd("1");
						headerBean.setResultMsg("X103");
						new SMSDAO().systemSMS("KCP CANCEL COMMUNICATION ERROR [X103]");
						Log.debug("log.day","["+headerBean.getTransactionId()+"] KCP Communication Error",this);
					}else{
						headerBean.setResultCd("1");
						headerBean.setResultMsg(new KcpUtil().getCode(kcp.m_res_cd));
						
						Log.debug("log.day","["+headerBean.getTransactionId()+"] VOID FAILURE",this);
					}	
				}
			}
			
			voidBean.setVoidResDate(CommonUtil.getCurrentTimestamp());
			if(headerBean.getResultCd().equals("0")){
				updateTrnsctnVoid(trnsctnBean,voidBean);
			}
			
			
			headerBean.setTrnResDate(CommonUtil.getCurrentDate("yyyyMMddHHmmss"));
			response = headerBean.getTransaction(tBean.getTransaction());
		}
		return response;
		
	}
	
	/**
	 * 취소거래에 대한 TRNSCTN_VOID 내역 INSERT 또는 UPDATE 
	 * 관리자 시스템에 의해서 접수된 내역은 UPDATE , 그외 단말기 또는 시스템 취소는 INSERT
	 * @param voidBean
	 * @param trnsctnBean
	 */
	public void updateTrnsctnVoid(TrnsctnBean trnsctnBean,TrnsctnVoidBean voidBean){
		voidBean.setStatus("00");
		
		if(new TrnsctnVoidDAO().insertVoid(voidBean)){
			Log.debug("log.day","TRNSCTNVOIDBEAN INSERTED",this);
		}
		if(trnsctnDAO.updateTrnStatus(trnsctnBean.getTransactionId(),new VoidMessage().getTrnStatus(trnsctnBean))){		//취소대기상태변경
			Log.debug("log.day","TRNSCTNBEAN STATUS UPDATE ",this);
		}else{
			new SMSDAO().systemSMS("DCP TRNSCTN STATUS UPDATE ERROR"+trnsctnBean.getTransactionId());
		}
		
	}
	
	
	public TrnsctnVoidBean getTrnsctnVoidBean(TrnsctnBean trnsctnBean){
		tBean.setApprovalDay(CommonUtil.convertTimestampToString(trnsctnBean.getTrnReqDate(),"yyyyMMdd"));
		tBean.setRApprovalNo(trnsctnBean.getApprovalNo());
		tBean.setRPayNo(trnsctnBean.getPayNo());
		tBean.setRTransactionId(trnsctnBean.getTransactionId());
		tBean.setAmount(CommonUtil.convertAmount(trnsctnBean.getAmount()*100));
		tBean.setCurType(trnsctnBean.getCurType());
		
		TrnsctnVoidBean voidBean = new TrnsctnVoidBean();
		voidBean.setTransactionId(trnsctnBean.getTransactionId());
		voidBean.setVoidTransactionId(headerBean.getTransactionId());
		voidBean.setVoidAmount(trnsctnBean.getAmount());
		voidBean.setRootTrnStatus(trnsctnBean.getTrnStatus());
		voidBean.setVoidApprovalNo(trnsctnBean.getApprovalNo());
		voidBean.setStatus("11");
		voidBean.setVoidReqDate(CommonUtil.getCurrentTimestamp());
		return voidBean;
	}
	

	public String getErrorMessage(String resultMsg){
		headerBean.setResultCd("1");
		headerBean.setResultMsg(resultMsg);
		Log.debug("log.day","[Error] = ["+resultMsg+"] TransactionId=["+tBean.getRTransactionId()+"]",this);
		return headerBean.getTransaction(tBean.getTransaction());
	}
}

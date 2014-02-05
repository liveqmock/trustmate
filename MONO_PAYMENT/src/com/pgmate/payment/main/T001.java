/*
 * Project Name : GSI_PROJECT
 * Project      : GSI_PAYMENT
 * File Name    : com.pgmate.payment.main.Credit.java
 * Date	        : Jan 27, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :
 */

package com.pgmate.payment.main;

import java.util.HashMap;
import java.util.List;

import kr.co.smartro.adapter.global.client.SmilePayClient;
import kr.co.smartro.adapter.global.client.dto.WebMessageDTO;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.inicis.inipay4.util.INIdata;
import com.kcp.C_PP_CLI;
import com.pgmate.model.comm.HeaderBean;
import com.pgmate.model.comm.T001Bean;
import com.pgmate.model.db.CurrentTrnsctnBean;
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.MerchantMngBean;
import com.pgmate.model.db.TrnsctnBean;
import com.pgmate.model.db.TrnsctnDMBean;
import com.pgmate.model.db.TrnsctnRiskBean;
import com.pgmate.model.db.TrnsctnSCRBean;
import com.pgmate.model.db.dao.SMSDAO;
import com.pgmate.model.db.dao.TrnsctnDAO;
import com.pgmate.model.db.dao.TrnsctnDMDAO;
import com.pgmate.model.db.dao.TrnsctnRiskDAO;
import com.pgmate.model.db.dao.TrnsctnSCRDAO;
import com.pgmate.payment.conf.GSIResource;
import com.pgmate.payment.conf.ServerConfigBean;
import com.pgmate.payment.inicis.INICISCredit;
import com.pgmate.payment.kcp.KCPCredit;
import com.pgmate.payment.ksnet.KSNET;
import com.pgmate.payment.ksnet.KSNETCredit;
import com.pgmate.payment.ksnet.KSNETHeader;
import com.pgmate.payment.ksnet.KSNETResponse;
import com.pgmate.payment.smartro.SMARTRO;
import com.pgmate.payment.util.CardUtil;
import com.pgmate.payment.util.GSICrypt;
import com.pgmate.payment.util.InicisUtil;
import com.pgmate.payment.util.KcpDMUtil;
import com.pgmate.payment.util.KcpUtil;
import com.pgmate.payment.util.SmartroUtil;

public class T001 {

	private ServerConfigBean configBean = null;
	private MerchantBean merchantBean 	= null;
	private MerchantMngBean merchantMngBean = null;
	private TrnsctnDAO trnsctnDAO		= null;
	private TrnsctnBean trnsctnBean		= null;
	private HeaderBean headerBean 		= null;
	private T001Bean tBean 				= null;
	private String alertMessage			= "";
	private TrnsctnDMBean trnsctnDMBean = null;
	

	public T001(ServerConfigBean configBean,MerchantBean merchantBean,MerchantMngBean merchantMngBean){
		this.configBean = configBean;
		this.merchantBean = merchantBean;
		this.merchantMngBean = merchantMngBean;
	}

	public String execute(HeaderBean headerBean,T001Bean tBean){
		this.headerBean = headerBean;
		this.tBean = tBean;
		if(!insertTrnsctn(headerBean,tBean)){
			alertMessage = "[TRNSCTN INSERT ERROR] "+CommonUtil.nToB(headerBean.getTransactionId()); 
			setErrorMessage("00ZH");	//내부오류
		}else{
			insertTrnsctnSCR();
			if(!checkLimit()){
				setErrorMessage(trnsctnBean.getResultMsg());	//내부오류
			}else if((configBean.getKttTestCard().indexOf(tBean.getCardNumber()) > -1) || merchantMngBean.getDemo().equals("Y") || configBean.getServerMode().equals("1")){
				Log.debug("log.day","TEST TRANSACTION DEMO = ["+merchantMngBean.getDemo()+"]",this);
				trnsctnBean.setResultCd("0");
				trnsctnBean.setResultMsg("0000");
				trnsctnBean.setTrnStatus("02");
				trnsctnBean.setApprovalNo(CommonUtil.getCurrentDate("HHmmss"));
				trnsctnBean.setTemp2("DEMO");
			}else{
				Log.debug("log.day",merchantMngBean.getVan()+" TRANSACTION",this);
				
				if(merchantMngBean.getVan().equals("KSNET")){
					KSNET ksnet = new KSNET();
					KSNETHeader ksHeader = ksnet.getHeader(headerBean, tBean, merchantMngBean);
					KSNETCredit ksCredit = ksnet.getCredit(headerBean, tBean, merchantMngBean,merchantBean);
					
					String request = ksHeader.getHeader(ksCredit.getKSNETCredit());
					byte[] response= null;
					
					try{
						response = ksnet.commKsnet(request);
						
						
						KSNETHeader ksRHeader = new KSNETHeader(CommonUtil.toString(response,0,300));
						KSNETResponse ksResponse = new KSNETResponse(CommonUtil.toString(response,300,response.length - 300));
						
						trnsctnBean.setVanTransactionId(ksResponse.getKsnetTrnId());
						if(ksResponse.getResponseCode().equals("O")){
							trnsctnBean.setResultCd("0");
							trnsctnBean.setResultMsg("0000");
							trnsctnBean.setTrnStatus("02");
							trnsctnBean.setApprovalNo(ksResponse.getApprovalNo());
						}else{
							trnsctnBean.setResultCd("1");
							trnsctnBean.setTrnStatus("03");
							trnsctnBean.setResultMsg(ksResponse.getApprovalNo());
							Log.debug("log.day","MESSAGE=["+ksResponse.getApprovalNo()+"]",this);
							addToRisk();
						}
						
						Log.debug("log.day","KSNET_TRANSACTION_ID=["+ksResponse.getKsnetTrnId()+"]",this);
					}catch(Exception e){
						Log.debug("log.day","["+headerBean.getTransactionId()+"] KSNET Communication Error="+CommonUtil.getExceptionMessage(e),this);
						trnsctnBean.setResultCd("1");
						trnsctnBean.setTrnStatus("03");
						trnsctnBean.setResultMsg("X103");
						alertMessage = "KSNET COMMUNICATION ERROR [X103] "+CommonUtil.nToB(headerBean.getTransactionId()); 
					}
				
				
				}else if(merchantMngBean.getVan().equals("INICIS")){
					INICISCredit ini = new INICISCredit();
					
					INIdata data = ini.execute(headerBean, tBean, merchantMngBean);
					trnsctnBean.setVanTransactionId(data.getData("tid"));
					
					
					if(data.getData("ResultCode").equals("00")){
						trnsctnBean.setResultCd("0");
						trnsctnBean.setResultMsg("0000");
						trnsctnBean.setTrnStatus("02");
						trnsctnBean.setApprovalNo(data.getData("CardAuthCode"));
					}else if(data.getData("ResultCode").equals("XX")){
						trnsctnBean.setResultCd("1");
						trnsctnBean.setResultMsg("X103");
						trnsctnBean.setTrnStatus("03");
						Log.debug("log.day","["+headerBean.getTransactionId()+"] INICIS Communication Error",this);
						alertMessage = "INICIS COMMUNICATION ERROR [X103] "+CommonUtil.nToB(headerBean.getTransactionId()); 
					}else{
						trnsctnBean.setResultCd("1");
						trnsctnBean.setTrnStatus("03");
						InicisUtil iUtil = new InicisUtil();
						//MSG[0] = 코드 , MSG[1] = 문구 , MSG[2] = 메세지 
						String[] msg = iUtil.getResponseMessage(data.getData("ResultMsg"));
						trnsctnBean.setResultMsg(iUtil.getCode(msg[0])); //코드 변환 해야 함.
						Log.debug("log.day","MESSAGE=["+msg[0]+","+msg[1]+","+msg[2]+"]",this);
						if(msg[2].indexOf("필수항목") > -1){ 
							new SMSDAO().systemSMS("INICIS ["+trnsctnBean.getTransactionId()+"]["+msg[2]);
						}
						addToRisk();
					}
				}else if(merchantMngBean.getVan().equals("SMARTRO")){
					SmilePayClient payClient = new SMARTRO().executeCredit(headerBean, tBean, merchantMngBean);
					
					WebMessageDTO responseDTO = payClient.doService();
					
					trnsctnBean.setVanTransactionId(responseDTO.getParameter("TID"));
					if(responseDTO.getParameter("RESP_CODE").equals("0000")){
						trnsctnBean.setResultCd("0");
						trnsctnBean.setResultMsg("0000");
						trnsctnBean.setTrnStatus("02");
						trnsctnBean.setApprovalNo(responseDTO.getParameter("APP_NO"));
					}else{
						trnsctnBean.setResultCd("1");
						trnsctnBean.setTrnStatus("03");
						SmartroUtil sUtil = new SmartroUtil();
						trnsctnBean.setResultMsg(sUtil.getCode(responseDTO.getParameter("RESP_CODE")));
						Log.debug("log.day","SMARTRO CODE=["+responseDTO.getParameter("RESP_CODE")+"] MESSAGE=["+responseDTO.getParameter("RESP_MSG")+"] KSNET CODE=["+trnsctnBean.getResultMsg()+"]",this);
						addToRisk();
					}
					
					Log.debug("log.day","SMARTRO_TRANSACTION_ID=["+responseDTO.getParameter("TID")+"]",this);
					
				}else if(merchantMngBean.getVan().equals("KCP")){
					KcpDMUtil kcpDm = new KcpDMUtil();
					
					boolean process = true;
					
					if(merchantMngBean.getDm().equals("Y")){
						process = checkDM(kcpDm); //변경이전
						//DMThread dmThread = new DMThread(merchantMngBean, headerBean, tBean);
						//dmThread.start();
					}
					
					if(process){
						C_PP_CLI  kcp = new KCPCredit().execute(headerBean, tBean, merchantMngBean, trnsctnDMBean);
						
						trnsctnBean.setVanTransactionId(kcp.mf_get_res("tno"));
						
						
						if(kcp.m_res_cd.equals("0000")){
							trnsctnBean.setResultCd("0");
							trnsctnBean.setResultMsg("0000");
							trnsctnBean.setTrnStatus("02");
							trnsctnBean.setApprovalNo(kcp.mf_get_res("app_no"));
						}else if(kcp.m_res_cd.equals("9502") || kcp.m_res_cd.equals("S102") || kcp.m_res_cd.equals("9562")){
							trnsctnBean.setResultCd("1");
							trnsctnBean.setResultMsg("X103");
							trnsctnBean.setTrnStatus("03");
							Log.debug("log.day","["+headerBean.getTransactionId()+"] KCP Communication Error",this);
							alertMessage = "KCP COMMUNICATION ERROR [X103] "+CommonUtil.nToB(headerBean.getTransactionId()); 
						}else{
							trnsctnBean.setResultCd("1");
							trnsctnBean.setTrnStatus("03");
							
							trnsctnBean.setResultMsg(new KcpUtil().getCode(kcp.m_res_cd)); //코드 변환 해야 함.
							//new SMSDAO().systemSMS("KCP ERROR ["+trnsctnBean.getTransactionId()+"]["+msg[2]);
							
							addToRisk();
						}
					}else{
						trnsctnBean.setResultCd("1");
						trnsctnBean.setResultMsg("0810");
						trnsctnBean.setTrnStatus("03");
						Log.debug("log.day","["+headerBean.getTransactionId()+"] DM REJECT",this);
					}
				}
				Log.debug("log.day","TRANSACTION_ID=["+trnsctnBean.getTransactionId()+"] RESULT MSG = ["+trnsctnBean.getResultMsg()+"]",this);
				
			}
		}
		headerBean.setResultCd(trnsctnBean.getResultCd());
		headerBean.setResultMsg(trnsctnBean.getResultMsg());
		headerBean.setTrnResDate(CommonUtil.getCurrentDate("yyyyMMddHHmmss"));
		tBean.setApprovalNo(trnsctnBean.getApprovalNo());
		Log.debug("log.day","UPDATE ="+Boolean.toString(trnsctnDAO.update(trnsctnBean)),this);
		alert();
		return headerBean.getTransaction(tBean.getTransaction());
	}


	/**
	 * 가맹점 한도 측정
	 * @return
	 */
	public boolean checkLimit(){
		//테스트카드 및 DEMO 시 거래 차단 및 한도 시스템 예외 적용
		if(configBean.getKttTestCard().indexOf(tBean.getCardNumber()) > -1 || merchantMngBean.getDemo().equals("Y")){
			return true;
		}

		if(merchantMngBean.getOnceLimit() < trnsctnBean.getAmount() || merchantMngBean.getDemo().equals("Y")){
			Log.debug("log.day","ONCE LIMIT =["+CommonUtil.convertAmount(merchantMngBean.getOnceLimit())+"] REQ AMOUNT=["+trnsctnBean.getAmount()+"]",this);
			trnsctnBean.setResultMsg("P102");
			alertMessage = "가맹점 1회 한도 초과[8329] DAY LIMIT =["+CommonUtil.convertAmount(merchantMngBean.getOnceLimit())+"] REQ AMOUNT=["+trnsctnBean.getAmount()+"]"; 
			return false;
		}else{
			CurrentTrnsctnBean currentTrnsctnBean = trnsctnDAO.getTrnsctnCount("'"+trnsctnBean.getMerchantId()+"'");
			if((currentTrnsctnBean.getDailySum()+trnsctnBean.getAmount()) > merchantMngBean.getDayLimit()){
				Log.debug("log.day","DAY LIMIT =["+CommonUtil.convertAmount(merchantMngBean.getDayLimit())+"] REQ AMOUNT=["+currentTrnsctnBean.getDailySum()+"||"+trnsctnBean.getAmount()+"]",this);
				alertMessage = "가맹점 1일 사용한도 초과[8329] DAY LIMIT =["+CommonUtil.convertAmount(merchantMngBean.getDayLimit())+"] REQ AMOUNT=["+currentTrnsctnBean.getDailySum()+"||"+trnsctnBean.getAmount()+"]";
				trnsctnBean.setResultMsg("8329");
				return false;
			}else if((currentTrnsctnBean.getMonthSum()+trnsctnBean.getAmount()) > merchantMngBean.getMonthLimit()){
				Log.debug("log.day","MONTH LIMIT =["+CommonUtil.convertAmount(merchantMngBean.getMonthLimit())+"] REQ AMOUNT=["+currentTrnsctnBean.getMonthSum()+"||"+trnsctnBean.getAmount()+"]",this);
				alertMessage = "가맹점 1월 사용한도 초과 [P103] MONTH LIMIT =["+CommonUtil.convertAmount(merchantMngBean.getMonthLimit())+"] REQ AMOUNT=["+currentTrnsctnBean.getMonthSum()+"||"+trnsctnBean.getAmount()+"]";
				trnsctnBean.setResultMsg("P103");
				return false;
			}else{
				return checkTrnsctn();
			}
		}
	}


	/**
	 * 1일 사용 횟수 초과 처리 또는 USD 에 대한 지정 시간 중복 거래 체크
	 * 1카드 1개월 사용 한도 관리
	 * @return
	 */
	public boolean checkTrnsctn(){
		
		
		if(!merchantMngBean.getCurType().equals(tBean.getCurType())){
			Log.debug("log.day","[가맹점에 설정된 CUR_TYPE 과 요청한 CUR_TYPE 이 다릅니다. ]",this);
			trnsctnBean.setResultMsg("P109");
			return false;
		}
		
		TrnsctnSCRDAO scrDAO = new TrnsctnSCRDAO();
		//1카드 1일 거래 차단
		if(merchantMngBean.getDuplicationCount() > 0){
			if(scrDAO.getTodayTrnsctnCount(tBean.getCardNumber(),GSIResource.TRN_STATUS_SUCCESS) > merchantMngBean.getDuplicationCount()){
				Log.debug("log.day","TODAYTRNSCTNCOUNT =["+tBean.getCardNumber()+"] + DUPLICATIONCOUNT =["+merchantMngBean.getDuplicationCount()+"]",this);
				trnsctnBean.setResultMsg("8328");
				return false;
			}
		}
		//1카드 1개월 거래 한도 차단
		if(merchantMngBean.getCardMonthLimit() > 0){
			double amount = scrDAO.getAmountByMerchantIdAndCardNumber(tBean.getCardNumber(), trnsctnBean.getMerchantId(),GSIResource.TRN_STATUS_SUCCESS) + trnsctnBean.getAmount();
			if(amount > merchantMngBean.getCardMonthLimit() ){
				Log.debug("log.day","카드소지자 1달 한도 초과 총요청금액= ["+CommonUtil.toString(amount)+"]설정금액 =["+CommonUtil.toString(merchantMngBean.getCardMonthLimit()),this);
				trnsctnBean.setResultMsg("8326");
				return false;
			}
		}

		//30일이내 거래횟수 제한
		if(merchantMngBean.getTempLimit() > 0){
			int monthlyTrnsctnCount = scrDAO.getMonthlyTrnsctnCount(tBean.getCardNumber(),GSIResource.TRN_STATUS_SUCCESS);
			if(monthlyTrnsctnCount > merchantMngBean.getTempLimit()){
				Log.debug("log.day","1달 거래횟수 초과 =["+tBean.getCardNumber()+"] 8328 설정="+CommonUtil.toString(merchantMngBean.getTempLimit()) +" 현재="+CommonUtil.toString(monthlyTrnsctnCount),this);
				trnsctnBean.setResultMsg("8328");
				return false;
			}
		}
		//지정된 시간 이내의 중복 거래 차단 현 2분
		if(scrDAO.trnsctnDuplicateOfMinutes(tBean.getCardNumber(),GSIResource.DUPLICATE_CHECK_MINUTES)){
			Log.debug("log.day","DUPLICATION CHECK =["+tBean.getCardNumber()+"] P105 ",this);
			trnsctnBean.setResultMsg("P105");
			return false;
		}
		//B/L 시스템에 의한 차단
		List<TrnsctnRiskBean> trnsctnRiskList = new TrnsctnRiskDAO().getByUnit(tBean.getCardNumber(),tBean.getPayEmail(),tBean.getPayTelNo());
		if(trnsctnRiskList.size() > 0){
			Log.debug("log.day","B/L SUSPENDED CARD =["+tBean.getCardNumber()+"] ["+tBean.getPayEmail()+"] ["+tBean.getPayTelNo()+"]",this);
			trnsctnBean.setResultMsg("8324");
			return false;
		}
		return true;
	}



	public boolean insertTrnsctn(HeaderBean headerBean,T001Bean tBean){
		trnsctnDAO 	= new TrnsctnDAO();
		trnsctnBean = new TrnsctnBean();

		trnsctnBean.setTransactionId(headerBean.getTransactionId());
		trnsctnBean.setMerchantId(headerBean.getMerchantId());
		trnsctnBean.setMallId(headerBean.getMallId());
		trnsctnBean.setServiceType(headerBean.getServiceType());
		trnsctnBean.setTrnReqDate(CommonUtil.stringToTimestamp(headerBean.getTrnDate()));
		trnsctnBean.setTrnStatus("01");
		trnsctnBean.setPayNo(headerBean.getPayNo());
		trnsctnBean.setPayUserId(tBean.getPayUserId());
		trnsctnBean.setPayEmail(tBean.getPayEmail());
		trnsctnBean.setPayName(tBean.getPayName());
		trnsctnBean.setPayTelNo(tBean.getPayTelNo());
		trnsctnBean.setCurType(tBean.getCurType());
		trnsctnBean.setResultCd("1");
		if(tBean.getCurType().equals("USD")){
			trnsctnBean.setAmount(CommonUtil.parseDouble(tBean.getAmount())/100);
		}else{
			trnsctnBean.setAmount(CommonUtil.parseDouble(tBean.getAmount()));
		}
		trnsctnBean.setProductType(tBean.getProductType());
		trnsctnBean.setProductName(tBean.getProductName());
		trnsctnBean.setIpAddress(headerBean.getIpAddress());
		trnsctnBean.setTemp1(tBean.getDomain());
		trnsctnBean.setVan(merchantMngBean.getVan());
		trnsctnBean.setTemp2(merchantMngBean.getVanId());
		if(tBean.getCavv().length() > 82){
			trnsctnBean.setAuth(tBean.getCavv().substring(80,82));
		}else{
			trnsctnBean.setAuth("N");
		}
		
		return trnsctnDAO.insert(trnsctnBean);
	}

	public void insertTrnsctnSCR(){
		GSICrypt crypt = new GSICrypt();
		TrnsctnSCRBean trnsctnSCRBean = new TrnsctnSCRBean();
		trnsctnSCRBean.setCardNum(crypt.encrypt(CardUtil.convertCardNumber(tBean.getCardNumber())));
		trnsctnSCRBean.setCardExpire(crypt.encrypt(tBean.getCardExpire()));
		trnsctnSCRBean.setCardBin(CardUtil.getCardBin(tBean.getCardNumber()));
		trnsctnSCRBean.setCardCvv(tBean.getCardCVV());
		trnsctnSCRBean.setCardIdenti(tBean.getCardExtra());
		trnsctnSCRBean.setCardPw(tBean.getCardPassword());
		trnsctnSCRBean.setCardType(CardUtil.getCardBrand(tBean.getCardNumber()));
		trnsctnSCRBean.setTransactionId(trnsctnBean.getTransactionId());
		trnsctnSCRBean.setTrxType("K");
		trnsctnSCRBean.setTransactionId(trnsctnBean.getTransactionId());
		new TrnsctnSCRDAO().insert(trnsctnSCRBean);
	}



	public void setErrorMessage(String resultMsg){
		trnsctnBean.setTrnResDate(CommonUtil.getCurrentTimestamp());
		trnsctnBean.setResultCd("1");
		trnsctnBean.setResultMsg(resultMsg);
		trnsctnBean.setTrnStatus("03");
		Log.debug("log.day","[Error] = ["+resultMsg+"] TransactionId=["+trnsctnBean.getTransactionId()+"]",this);
	}

	

	public void addToRisk(){

		if(trnsctnBean.getResultMsg().equals("8324") || trnsctnBean.getResultMsg().equals("8350")){ // PenaltyCode 인 경우 는 B/L 에 등록한다.
			TrnsctnRiskBean trnsctnRiskBean = new TrnsctnRiskBean();
			TrnsctnRiskDAO trnscntRiskDAO = new TrnsctnRiskDAO();
			trnsctnRiskBean.setActive("Y");
			trnsctnRiskBean.setUnit(tBean.getCardNumber());
			trnsctnRiskBean.setComments("BY SYSTEM TRN_ID=["+trnsctnBean.getTransactionId()+"] RESULT_MSG=["+trnsctnBean.getResultMsg()+"]");
			trnscntRiskDAO.insert(trnsctnRiskBean);
			Log.debug("log.day","BLACK LIST(B/L) REGISTRATION UNIT = CARD",this);
			
			if(!trnsctnBean.getPayEmail().equals("")){
				trnsctnRiskBean.setUnit(trnsctnBean.getPayEmail().trim());
				trnscntRiskDAO.insert(trnsctnRiskBean);
				Log.debug("log.day","BLACK LIST(B/L) REGISTRATION UNIT = EMAIL",this);
			}
		}
	}
	
	
	public void alert(){
		try{
			AlertMessage msg = new AlertMessage(trnsctnBean.getTransactionId(),headerBean.getResultMsg(),alertMessage);
			msg.start();
		}catch(Exception e){
			Log.debug("log.day","ALERT ERROR = "+CommonUtil.getExceptionMessage(e),this);
		}
	}
	
	
	
	public boolean checkDM(KcpDMUtil kcpDm){
		boolean process = false;
		
		TrnsctnDMDAO trnsctnDMDAO = new TrnsctnDMDAO();
		
		if(kcpDm.getDM(merchantMngBean.getVanId(),headerBean.getTransactionId())){
			HashMap<String,String> dmHashMap = kcpDm.getResult();
			String ekey = (String)dmHashMap.get("ekey");
			if(!ekey.equals("")){
				trnsctnDMBean = new TrnsctnDMBean();
				trnsctnDMBean.setTransactionId(headerBean.getTransactionId());
				trnsctnDMBean.setEkey(ekey);
				trnsctnDMBean.setEhash((String)dmHashMap.get("ehash"));
				trnsctnDMBean.setAddr1(tBean.getAddr1());
				trnsctnDMBean.setAddr2(tBean.getAddr2());
				trnsctnDMBean.setCity(tBean.getCity());
				trnsctnDMBean.setState(tBean.getState());
				trnsctnDMBean.setCountry(tBean.getCountry());
				trnsctnDMBean.setZip(tBean.getZip());
				
				trnsctnDMDAO.insert(trnsctnDMBean);
				Log.debug("log.day","KCP DM EXECUTE",this);
				C_PP_CLI  dm = new KCPCredit().executeDM(headerBean, tBean, merchantMngBean,dmHashMap);
				if(dm.mf_get_res("decision").equalsIgnoreCase("REJECT") || dm.mf_get_res("decision").equalsIgnoreCase("ERROR")){
					process = false;
				}else{
					process = true;
				}
				trnsctnDMBean.setDmNo(dm.mf_get_res("cert_dm_no"));
				trnsctnDMBean.setResultCd(dm.m_res_cd);
				trnsctnDMBean.setDecision(dm.mf_get_res("decision"));
				trnsctnDMBean.setHostCd(dm.mf_get_res("res_host_cd"));
				if(CommonUtil.isNullOrSpace(dm.mf_get_res("res_host_en_msg"))){
					trnsctnDMBean.setResultMsg(dm.m_res_msg);
				}else{
					trnsctnDMBean.setResultMsg(dm.mf_get_res("res_host_en_msg"));
				}
				trnsctnDMBean.setFraudScore(CommonUtil.parseDouble(dm.mf_get_res("fraudScore")));
				
				trnsctnDMDAO.update(trnsctnDMBean);
				
			}else{
				Log.debug("log.day","KCP DM FALSE EKEY IS NULL EXECUTE PAYMENT",this);
				process = false;
			}
		}else{
			Log.debug("log.day","KCP DM ERROR EXE TRUE",this);
			process = false;
		}
		
		return process;
	}




}

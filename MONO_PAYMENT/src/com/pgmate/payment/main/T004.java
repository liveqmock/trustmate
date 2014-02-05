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

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.inicis.inipay4.util.INIdata;
import com.pgmate.model.comm.HeaderBean;
import com.pgmate.model.comm.T004Bean;
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.MerchantMngBean;
import com.pgmate.model.db.TrnsctnBean;
import com.pgmate.model.db.TrnsctnSCRBean;
import com.pgmate.model.db.dao.CodeDAO;
import com.pgmate.model.db.dao.SMSDAO;
import com.pgmate.model.db.dao.TrnsctnDAO;
import com.pgmate.model.db.dao.TrnsctnSCRDAO;
import com.pgmate.payment.conf.GSIResource;
import com.pgmate.payment.conf.ServerConfigBean;
import com.pgmate.payment.inicis.INICISCredit;
import com.pgmate.payment.util.CardUtil;
import com.pgmate.payment.util.GSICrypt;
import com.pgmate.payment.util.InicisUtil;

public class T004 {

	private ServerConfigBean configBean = null;
	private MerchantBean merchantBean 	= null;
	private MerchantMngBean merchantMngBean = null;
	private TrnsctnDAO trnsctnDAO		= null;
	private TrnsctnBean trnsctnBean		= null;
	private HeaderBean headerBean 		= null;
	private T004Bean tBean 				= null;
	private long amount 				= 100;

	public T004(ServerConfigBean configBean,MerchantBean merchantBean,MerchantMngBean merchantMngBean){
		this.configBean = configBean;
		this.merchantBean = merchantBean;
		this.merchantMngBean = merchantMngBean;
	}

	public String execute(HeaderBean headerBean,T004Bean tBean){
		this.headerBean = headerBean;
		this.tBean = tBean;
		Log.debug("log.day","T004 AUTHENTICATION TRANSACTION",this);
		if(!insertTrnsctn(headerBean,tBean,amount)){
			new SMSDAO().systemSMS("PANWORLD [TRNSCTN INSERT ERROR] CODE=[00ZH] ");
			setErrorMessage("00ZH");	//내부오류
		}else{
			insertTrnsctnSCR();
			if(!checkLimit()){
				setErrorMessage(trnsctnBean.getResultMsg());	//내부오류
			}else{
				Log.debug("log.day",merchantMngBean.getVan()+" TRANSACTION",this);
				INICISCredit ini = new INICISCredit();
				
				INIdata data = ini.execute(headerBean, tBean, merchantMngBean,amount);
				trnsctnBean.setVanTransactionId(data.getData("tid"));
				
				
				if(data.getData("ResultCode").equals("00")){
					trnsctnBean.setResultCd("0");
					trnsctnBean.setResultMsg("0000");
					trnsctnBean.setTrnStatus("02");
					trnsctnBean.setApprovalNo(data.getData("CardAuthCode"));
					tBean.setMessage("authentication succeed");
				}else if(data.getData("ResultCode").equals("XX")){	//인증에서는 시스템 오류 : 통신장애로 표기 
					trnsctnBean.setResultCd("2");					//시스템 오류
					trnsctnBean.setResultMsg("X103");
					trnsctnBean.setTrnStatus("01");					//승인장애
					Log.debug("log.day","["+headerBean.getTransactionId()+"] INICIS Communication Error",this);
					new SMSDAO().systemSMS("INICIS COMMUNICATION ERROR [X103]");
					tBean.setMessage("Timeout after Data transmission");
				}else{
					trnsctnBean.setResultCd("1");
					trnsctnBean.setTrnStatus("03");
					InicisUtil iUtil = new InicisUtil();
					//MSG[0] = 코드 , MSG[1] = 문구 , MSG[2] = 메세지 
					String[] msg = iUtil.getResponseMessage(data.getData("ResultMsg"));
					trnsctnBean.setResultMsg(iUtil.getCode(msg[0])); //원코드 코드 분리해야함.
					Log.debug("log.day","MESSAGE=["+msg[0]+","+msg[1]+","+msg[2]+"]",this);
					tBean.setMessage(new CodeDAO().getByAliasCode("KAPPV_CODE", trnsctnBean.getResultMsg()).getEnValue());
				}
		
				Log.debug("log.day","TRANSACTION_ID=["+trnsctnBean.getTransactionId()+"] RESULT MSG = ["+trnsctnBean.getResultMsg()+"]",this);
				
			}
		}
		headerBean.setResultCd(trnsctnBean.getResultCd());
		headerBean.setResultMsg(trnsctnBean.getResultMsg());
		headerBean.setTrnResDate(CommonUtil.getCurrentDate("yyyyMMddHHmmss"));
		
		Log.debug("log.day","UPDATE ="+Boolean.toString(trnsctnDAO.update(trnsctnBean)),this);
		return headerBean.getTransaction(tBean.getTransaction());
	}


	/**
	 * 가맹점 한도 측정
	 * @return
	 */
	public boolean checkLimit(){
		if(!merchantMngBean.getAuth().equals("Y")){
			Log.debug("log.day","NO PERMISSION FROM AUTHENTICATION",this);
			trnsctnBean.setResultMsg("0805");
			return false;
		}
		
		
		return true;
		/*
		//테스트카드 및 DEMO 시 거래 차단 및 한도 시스템 예외 적용
		if(configBean.getKttTestCard().indexOf(tBean.getCardNumber()) > -1 || merchantMngBean.getDemo().equals("Y")){
			return true;
		}

		if(merchantMngBean.getOnceLimit() < trnsctnBean.getAmount() || merchantMngBean.getDemo().equals("Y")){
			Log.debug("log.day","ONCE LIMIT =["+CommonUtil.convertAmount(merchantMngBean.getOnceLimit())+"] REQ AMOUNT=["+trnsctnBean.getAmount()+"]",this);
			trnsctnBean.setResultMsg("P102");
			new SMSDAO().adminSMS("PANWORLD 1회 한도 초과[8329] DAY LIMIT =["+CommonUtil.convertAmount(merchantMngBean.getOnceLimit())+"] REQ AMOUNT=["+trnsctnBean.getAmount()+"]");
			return false;
		}else{
			CurrentTrnsctnBean currentTrnsctnBean = trnsctnDAO.getTrnsctnCount("'"+trnsctnBean.getMerchantId()+"'");
			if((currentTrnsctnBean.getDailySum()+trnsctnBean.getAmount()) > merchantMngBean.getDayLimit()){
				Log.debug("log.day","DAY LIMIT =["+CommonUtil.convertAmount(merchantMngBean.getDayLimit())+"] REQ AMOUNT=["+currentTrnsctnBean.getDailySum()+"||"+trnsctnBean.getAmount()+"]",this);
				new SMSDAO().adminSMS("PANWORLD 가맹점 1일 사용한도 초과[8329] DAY LIMIT =["+CommonUtil.convertAmount(merchantMngBean.getDayLimit())+"] REQ AMOUNT=["+currentTrnsctnBean.getDailySum()+"||"+trnsctnBean.getAmount()+"]");
				trnsctnBean.setResultMsg("8329");
				return false;
			}else if((currentTrnsctnBean.getMonthSum()+trnsctnBean.getAmount()) > merchantMngBean.getMonthLimit()){
				Log.debug("log.day","MONTH LIMIT =["+CommonUtil.convertAmount(merchantMngBean.getMonthLimit())+"] REQ AMOUNT=["+currentTrnsctnBean.getMonthSum()+"||"+trnsctnBean.getAmount()+"]",this);
				new SMSDAO().adminSMS("PANWORLD 가맹점 1월 사용한도 초과 [P103] MONTH LIMIT =["+CommonUtil.convertAmount(merchantMngBean.getMonthLimit())+"] REQ AMOUNT=["+currentTrnsctnBean.getMonthSum()+"||"+trnsctnBean.getAmount()+"]");
				trnsctnBean.setResultMsg("P103");
				return false;
			}else{
				return checkTrnsctn();
			}
		}
		*/
	}


	/**
	 * 1일 사용 횟수 초과 처리 또는 USD 에 대한 지정 시간 중복 거래 체크
	 * 1카드 1개월 사용 한도 관리
	 * @return
	 */
	public boolean checkTrnsctn(){

		TrnsctnSCRDAO scrDAO = new TrnsctnSCRDAO();
		//1카드 1일 거래 차단
		if(scrDAO.getTodayTrnsctnCount(tBean.getCardNumber(),GSIResource.TRN_STATUS_SUCCESS) > merchantMngBean.getDuplicationCount()){
			Log.debug("log.day","TODAYTRNSCTNCOUNT =["+tBean.getCardNumber()+"] + DUPLICATIONCOUNT =["+merchantMngBean.getDuplicationCount()+"]",this);
			trnsctnBean.setResultMsg("8328");
			return false;
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
		return true;
	}



	public boolean insertTrnsctn(HeaderBean headerBean,T004Bean tBean,long amount){
		trnsctnDAO 	= new TrnsctnDAO();
		trnsctnBean = new TrnsctnBean();

		trnsctnBean.setTransactionId(headerBean.getTransactionId());
		trnsctnBean.setMerchantId(headerBean.getMerchantId());
		trnsctnBean.setMallId(headerBean.getMallId());
		trnsctnBean.setServiceType(headerBean.getServiceType());
		trnsctnBean.setTrnReqDate(CommonUtil.stringToTimestamp(headerBean.getTrnDate()));
		trnsctnBean.setTrnStatus("01");
		trnsctnBean.setPayNo(headerBean.getPayNo());
		trnsctnBean.setPayUserId("");
		trnsctnBean.setPayEmail("");
		trnsctnBean.setPayName(tBean.getPayName());
		trnsctnBean.setPayTelNo(tBean.getPayTelNo());
		trnsctnBean.setCurType("USD");
		trnsctnBean.setResultCd("1");
		trnsctnBean.setAmount(amount/100);
		trnsctnBean.setAuth("Y");
		
		trnsctnBean.setProductType("1");
		trnsctnBean.setProductName("AUTH");
		trnsctnBean.setIpAddress(headerBean.getIpAddress());
		trnsctnBean.setTemp1("AUTH");
		trnsctnBean.setVan(merchantMngBean.getVan());
		trnsctnBean.setTemp2(merchantMngBean.getVanId());
		return trnsctnDAO.insert(trnsctnBean);
	}

	public void insertTrnsctnSCR(){
		GSICrypt crypt = new GSICrypt();
		TrnsctnSCRBean trnsctnSCRBean = new TrnsctnSCRBean();
		trnsctnSCRBean.setCardNum(crypt.encrypt(CardUtil.convertCardNumber(tBean.getCardNumber())));
		trnsctnSCRBean.setCardExpire(crypt.encrypt(tBean.getCardExpire()));
		trnsctnSCRBean.setCardBin(CardUtil.getCardBin(tBean.getCardNumber()));
		trnsctnSCRBean.setCardCvv(tBean.getCardCVV());
		trnsctnSCRBean.setCardIdenti("");
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






}

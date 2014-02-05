/*
 * Project      : PGv2_SETTLE
 * File Name    : net.kttrust.settle2.Settlement.java
 * Date         : 2011-12-14
 * Version      : 1.0
 * Author       : ginaida@trustmate.net
 * Comment      :
 */

package com.pgmate.daemon.settle;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.BeanUtil;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.AgentBean;
import com.pgmate.model.db.AgentBillBean;
import com.pgmate.model.db.AgentSettleBean;
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.MerchantBillBean;
import com.pgmate.model.db.MerchantDepositBean;
import com.pgmate.model.db.MerchantSettleBean;
import com.pgmate.model.db.dao.AgentBillDAO;
import com.pgmate.model.db.dao.AgentDAO;
import com.pgmate.model.db.dao.GSISEQDAO;
import com.pgmate.model.db.dao.MerchantBillDAO;
import com.pgmate.model.db.dao.MerchantDAO;
import com.pgmate.model.db.dao.MerchantDepositDAO;
import com.pgmate.model.db.dao.MerchantSettleDAO;
import com.pgmate.model.db.dao.SequenceDAO;

public class Settlement {

	//24 정산 취소 요청 및 15 환불 요청 에 대한 처리 방안?
	
	private static String DAY_03 	= "03";			//3일	: TYPE101 말,TYPE151 말, TYPE152 말, TYPE301, TYPE0302 초,TYPE303 ,TYPE304 
	private static String DAY_04 	= "04";			//3일	: TYPE101 말,TYPE151 말, TYPE152 말, TYPE301, TYPE0302 초,TYPE303 ,TYPE304 
	private static String DAY_13	= "13";			//13일	: TYPE101 초 
	private static String DAY_18	= "18";			//18일	: TYPE151 초,TYPE152 초,TYPE302 초
	private static String DAY_23	= "23";			//23일	: TYPE101 중
	
	private static String TYPE_070	= "TYPE070";	//7일  정산 -28~-22
	private static String TYPE_071	= "TYPE071";	//7일  정산 -21~-15
	private static String TYPE_100	= "TYPE100";	//10일  정산 
	private static String TYPE_150	= "TYPE150";	//150일 정산 
	private static String TYPE_300	= "TYPE300";	//300일 정산 마지막일 지급
	private static String TYPE_301	= "TYPE301";	//300일 정산 10일 지급
	
	private String currentDate 		= "";			//오늘
	private String currentDay		= "";			//오늘 날짜
	private String currentMonth 	= "";			//이번 달 YYYYMM
	private String lastMonth		= "";			//지난 달 YYYYMM
	private String nextMonth		= "";			//다음 달 YYYYMM
	private String merchantId 		= "";
	private String currentMonthLastDay="";			//이번달 마지막 날짜 YYYMMDD
	private String lastMonthLastDay = "";			//지난달 마지막 날째 YYYMMDD
	
	private String[] week 			= {"일","월","화","수","목","금","토"};
	private String dayOfWeek		= "";			//요일
	private int weekOfMonth			= 0;			//이번주 주차 
	
	private SDAO settleDAO			= new SDAO();
	private GSISEQDAO seqDAO 		= new GSISEQDAO();
	private MerchantSettleDAO merchantSettleDAO = new MerchantSettleDAO();
	


	public Settlement() {
		currentDate = CommonUtil.getCurrentDate("yyyyMMdd");										//오늘 
		currentDay 	= currentDate.substring(6,8);													//오늘 날짜
		dayOfWeek 	= week[CommonUtil.getDayOfWeek()-1];											//오늘의 요일
		Calendar oCalendar = Calendar.getInstance( );
		weekOfMonth = oCalendar.get(Calendar.WEEK_OF_MONTH);										//이번달의 주차  
		execute();
	
	}


	public boolean execute(){
		currentMonth= currentDate.substring(0,6);													//이번 달 YYYYMM
		lastMonth =  CommonUtil.getOpDate(GregorianCalendar.MONTH,-1,currentDate).substring(0,6);	//지난 달 YYYYMM
		nextMonth =  CommonUtil.getOpDate(GregorianCalendar.MONTH,1,currentDate).substring(0,6);	//다음 달 YYYYMM
		currentMonthLastDay= currentMonth+CommonUtil.getLastDayOfMonth(currentMonth);				//이번 달 YYYYMMDD
		lastMonthLastDay = lastMonth+CommonUtil.getLastDayOfMonth(lastMonth);						//지난 달 YYYYMMDD
		
		
		if(dayOfWeek.equals("금")){ 
			settleDAO.updateRefund();													//취소 데이터 보정
			type070();
			type071();
		}
		
		if(currentDay.equals(DAY_03)){						//TYPE100,TYPE150,TYPE300, TYPE0301 
			settleDAO.backup();															//테이블 백업 
			settleDAO.updateRefund();													//취소 데이터 보정
			type100();
			type150();
			type300();
			type301();
		}else if(currentDay.equals(DAY_13)){				//TYPE100 초  
			settleDAO.updateRefund();													//취소 데이터 보정
			type100();
		}else if(currentDay.equals(DAY_18)){				//TYPE150 
			settleDAO.updateRefund();													//취소 데이터 보정
			type150();
		}else if(currentDay.equals(DAY_23)){				//TYPE100 중 
			settleDAO.updateRefund();													//취소 데이터 보정
			type100();
		}else{
			if(currentDay.equals("01")){
				settleDAO.backup();
			}
			Log.debug("log.day",currentDay+" 정산지정일이 아닙니다.",this);
		}
		return true;
	}
	
	//7일 정산 -28~-22
	public boolean type070(){
		String startDate 	= "";
		String endDate		= "";
		String settleDate	= "";
		int settleCnt		= 1;
		
		startDate 	= CommonUtil.getOpDate(GregorianCalendar.DATE,-25,currentDate);		//-28일
		endDate	 	= CommonUtil.getOpDate(GregorianCalendar.DATE,-19,currentDate);		//-22일 일주일간
		settleDate 	= CommonUtil.getOpDate(GregorianCalendar.DATE,+3,currentDate)+"000000";	//금주 목요일 
		settleCnt   = getDayOfWeek(startDate);
		
		
		Log.debug("log.day","070 일 정산 STARTDAY =["+startDate+"] ENDDAY=["+endDate+"] SETTLE_DAY =["+settleDate.substring(0,8)+"] SETTLE_CNT =["+settleCnt+"]",this);

		MerchantBillDAO merchantBillDAO = new MerchantBillDAO();
		
		merchantBillDAO.setPageSize(50000);
		merchantBillDAO.orderBy = "  ORDER BY MERCHANT_ID ASC";
		List<MerchantBillBean> merchantBillList = merchantBillDAO.getByPeriodAndActive(TYPE_070);
		for(int i=0 ; i<merchantBillList.size();i++){
			try{
				MerchantBillBean merchantBillBean = (MerchantBillBean)merchantBillList.get(i);
				MerchantBean merchantBean = new MerchantDAO().getById(merchantBillBean.getMerchantId());
				merchantId = merchantBillBean.getMerchantId();
				Log.debug("log.day","",this);
				Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] CUR/TOT =["+(i+1)+"/"+merchantBillList.size()+"]",this);
				
				if(settleDAO.isCreateSettle(merchantId, endDate)){
					MerchantSettleBean merchantSettleBean = getSettleBean(merchantBillBean,merchantBean);
					merchantSettleBean.setStartDay(startDate);
					merchantSettleBean.setEndDay(endDate);
					merchantSettleBean.setPeriod(TYPE_070);
					merchantSettleBean.setSettleCnt(CommonUtil.toString(settleCnt));
					merchantSettleBean.setSettleDate(CommonUtil.stringToTimestamp(settleDate));
					merchantSettleBean = calcTotTransaction(merchantSettleBean);
					
					if(merchantSettleDAO.insert(merchantSettleBean)){
						if(settleDAO.updateTrnId(merchantSettleBean)){
							Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] UPLOADED",this);
						}
					}else{
						Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] FAIL",this);
					}
				}else{
					Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] 거래 정보 없음.",this);
				}
			}catch(Exception e){
				Log.debug("log.day",CommonUtil.getExceptionMessage(e),this);
			}
		}
		return true;
	}
	
	//7일 정산 -21~-15
	public boolean type071(){
		String startDate 	= "";
		String endDate		= "";
		String settleDate	= "";
		int settleCnt		= 1;
		
		startDate 	= CommonUtil.getOpDate(GregorianCalendar.DATE,-18,currentDate);		//-21일
		endDate	 	= CommonUtil.getOpDate(GregorianCalendar.DATE,-12,currentDate);		//-15일 일주일간
		settleDate 	= CommonUtil.getOpDate(GregorianCalendar.DATE,+3,currentDate)+"000000";	//금주 목요일

		settleCnt   = getDayOfWeek(startDate);
		
		
		Log.debug("log.day","071 일 정산 STARTDAY =["+startDate+"] ENDDAY=["+endDate+"] SETTLE_DAY =["+settleDate.substring(0,8)+"] SETTLE_CNT =["+settleCnt+"]",this);

		MerchantBillDAO merchantBillDAO = new MerchantBillDAO();
		
		merchantBillDAO.setPageSize(50000);
		merchantBillDAO.orderBy = "  ORDER BY MERCHANT_ID ASC";
	
		List<MerchantBillBean> merchantBillList = merchantBillDAO.getByPeriodAndActive(TYPE_071);
		for(int i=0 ; i<merchantBillList.size();i++){
			try{
				MerchantBillBean merchantBillBean = (MerchantBillBean)merchantBillList.get(i);
				
				MerchantBean merchantBean = new MerchantDAO().getById(merchantBillBean.getMerchantId());
				merchantId = merchantBillBean.getMerchantId();
				Log.debug("log.day","",this);
				Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] CUR/TOT =["+(i+1)+"/"+merchantBillList.size()+"]",this);
				if(settleDAO.isCreateSettle(merchantId, endDate)){
					MerchantSettleBean merchantSettleBean = getSettleBean(merchantBillBean,merchantBean);
					merchantSettleBean.setStartDay(startDate);
					merchantSettleBean.setEndDay(endDate);
					merchantSettleBean.setPeriod(TYPE_071);
					merchantSettleBean.setSettleCnt(CommonUtil.toString(settleCnt));
					merchantSettleBean.setSettleDate(CommonUtil.stringToTimestamp(settleDate));
					merchantSettleBean = calcTotTransaction(merchantSettleBean);
					
					if(merchantSettleDAO.insert(merchantSettleBean)){
						if(settleDAO.updateTrnId(merchantSettleBean)){
							Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] UPLOADED",this);
						}
					}else{
						Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] FAIL",this);
					}
				}else{
					Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] 거래 정보 없음.",this);
				}
					
			}catch(Exception e){
				Log.debug("log.day",CommonUtil.getExceptionMessage(e),this);
			}
		}
		return true;
	}
	
	
	//10일 정산
	public boolean type100(){
		String startDate 	= "";
		String endDate		= "";
		String settleDate	= "";
		int settleCnt		= 1;

		if(currentDay.equals(DAY_03)){
			startDate 	= lastMonth+"21";											//지난달 21일부터
			endDate 	= lastMonthLastDay;											//지난달 마지막 날까지
			settleDate	= currentMonth+"10000000";									//이번달 10일 지급
			settleCnt	= 3;
		}else if(currentDay.equals(DAY_13)){
			startDate 	= currentMonth+"01";										//이번달 1일 부터
			endDate 	= currentMonth+"10";										//이번달 10일까지
			settleDate	= currentMonth+"20000000";									//이번달 20일 지급
			settleCnt	= 1;
		}else if(currentDay.equals(DAY_23)){
			startDate 	= currentMonth+"11";										//이번달 11일 부터
			endDate 	= currentMonth+"20";										//이번달 20일 까지
			settleDate	= currentMonthLastDay+"000000";								//이번달  말 지급
			settleCnt	= 2;
		}else{
		}
		
		Log.debug("log.day","10 일 정산 STARTDAY =["+startDate+"] ENDDAY=["+endDate+"] SETTLE_DAY =["+settleDate.substring(0,8)+"] SETTLE_CNT =["+settleCnt+"]",this);

		MerchantBillDAO merchantBillDAO = new MerchantBillDAO();
		
		merchantBillDAO.setPageSize(50000);
		merchantBillDAO.orderBy = "  ORDER BY MERCHANT_ID ASC";
		List<MerchantBillBean> merchantBillList = merchantBillDAO.getByPeriodAndActive(TYPE_100);
		for(int i=0 ; i<merchantBillList.size();i++){
			try{
				MerchantBillBean merchantBillBean = (MerchantBillBean)merchantBillList.get(i);
				MerchantBean merchantBean = new MerchantDAO().getById(merchantBillBean.getMerchantId());
				merchantId = merchantBillBean.getMerchantId();
				Log.debug("log.day","",this);
				Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] CUR/TOT =["+(i+1)+"/"+merchantBillList.size()+"]",this);
				
				if(settleDAO.isCreateSettle(merchantId, endDate)){
					MerchantSettleBean merchantSettleBean = getSettleBean(merchantBillBean,merchantBean);
					merchantSettleBean.setStartDay(startDate);
					merchantSettleBean.setEndDay(endDate);
					merchantSettleBean.setPeriod(TYPE_100);
					merchantSettleBean.setSettleCnt(CommonUtil.toString(settleCnt));
					merchantSettleBean.setSettleDate(CommonUtil.stringToTimestamp(settleDate));
					merchantSettleBean = calcTotTransaction(merchantSettleBean);
					
					if(merchantSettleDAO.insert(merchantSettleBean)){
						if(settleDAO.updateTrnId(merchantSettleBean)){
							Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] UPLOADED",this);
						}
					}else{
						Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] FAIL",this);
					}
				}else{
					Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] 거래 정보 없음.",this);
				}
			}catch(Exception e){
				Log.debug("log.day",CommonUtil.getExceptionMessage(e),this);
			}
		}
		return true;
	}

	public boolean type150(){
		String startDate 	= "";
		String endDate		= "";
		String settleDate	= "";
		int settleCnt		= 1;
		if(currentDay.equals(DAY_03)){
			startDate 	= lastMonth+"16";											//지난달 16일 부터
			endDate 	= lastMonthLastDay;											//지난달 마지막일 까지
			settleDate	= currentMonth+"15000000";									//이번달 15일 지급한다.
			settleCnt		= 2;													//정산 2회차 보증금 차감한다.
		}else if(currentDay.equals(DAY_18)){
			startDate 	= currentMonth+"01";										//이번달 1일 부터
			endDate 	= currentMonth+"15";										//이번달 15일 까지
			settleDate	= currentMonthLastDay+"000000";								//이번달 마지막에 지급한다.
			settleCnt		= 1;													//정산 1회차는 보증금을 차감 및 전 회차 보증금을 지급한다.
		}else{
		}
		Log.debug("log.day","TYPE150 정산 STARTDAY =["+startDate+"] ENDDAY=["+endDate+"] SETTLE_DAY =["+settleDate.substring(0,8)+"] SETTLE_CNT =["+settleCnt+"]",this);

		MerchantBillDAO merchantBillDAO = new MerchantBillDAO();
		merchantBillDAO.setPageSize(10000);
		merchantBillDAO.orderBy = "  ORDER BY MERCHANT_ID ASC";
		List<MerchantBillBean> merchantBillList = merchantBillDAO.getByPeriodAndActive(TYPE_150);
		for(int i=0 ; i<merchantBillList.size();i++){
			try{
				MerchantBillBean merchantBillBean = (MerchantBillBean)merchantBillList.get(i);
				MerchantBean merchantBean = new MerchantDAO().getById(merchantBillBean.getMerchantId());
				merchantId = merchantBillBean.getMerchantId();
				Log.debug("log.day","",this);
				Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] CUR/TOT =["+(i+1)+"/"+merchantBillList.size()+"]",this);
				if(settleDAO.isCreateSettle(merchantId, endDate)){
					MerchantSettleBean merchantSettleBean = getSettleBean(merchantBillBean,merchantBean);
					merchantSettleBean.setStartDay(startDate);
					merchantSettleBean.setEndDay(endDate);
					merchantSettleBean.setPeriod(TYPE_150);
					merchantSettleBean.setSettleCnt(CommonUtil.toString(settleCnt));
					merchantSettleBean.setSettleDate(CommonUtil.stringToTimestamp(settleDate));
					merchantSettleBean = calcTotTransaction(merchantSettleBean);
					if(merchantSettleDAO.insert(merchantSettleBean)){
						if(settleDAO.updateTrnId(merchantSettleBean)){
							Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] UPLOADED",this);
						}
					}else{
						Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] FAIL",this);
					}
				}else{
					Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] 거래 정보 없음.",this);
				}
			}catch(Exception e){
				Log.debug("log.day",CommonUtil.getExceptionMessage(e),this);
			}
		}
		return true;
	}

	

	public boolean type300(){

		String startDate 	= lastMonth+"01";										//지난달 1일부터
		String endDate		= lastMonthLastDay;										//지난달 마지막 날까지
		String settleDate	= currentMonthLastDay+"000000";							//이번달 마지막날 지급
		Log.debug("log.day","TYPE_300 월말 정산 STARTDAY =["+startDate+"] ENDDAY=["+endDate+"] SETTLE_DAY =["+settleDate.substring(0,8)+"]",this);
		MerchantBillDAO merchantBillDAO = new MerchantBillDAO();
		merchantBillDAO.orderBy = "  ORDER BY MERCHANT_ID ASC";
		merchantBillDAO.setPageSize(10000);
		List<MerchantBillBean> merchantBillList = merchantBillDAO.getByPeriodAndActive(TYPE_300);
		for(int i=0 ; i<merchantBillList.size();i++){
			try{
				MerchantBillBean merchantBillBean = (MerchantBillBean)merchantBillList.get(i);
				MerchantBean merchantBean = new MerchantDAO().getById(merchantBillBean.getMerchantId());
				merchantId = merchantBillBean.getMerchantId();
				Log.debug("log.day","",this);
				Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] CUR/TOT =["+(i+1)+"/"+merchantBillList.size()+"]",this);
				if(settleDAO.isCreateSettle(merchantId, endDate)){
					MerchantSettleBean merchantSettleBean = getSettleBean(merchantBillBean,merchantBean);
					merchantSettleBean.setStartDay(startDate);
					merchantSettleBean.setEndDay(endDate);
					merchantSettleBean.setPeriod(TYPE_300);
					merchantSettleBean.setSettleCnt("1");
					merchantSettleBean.setSettleDate(CommonUtil.stringToTimestamp(settleDate));
					merchantSettleBean = calcTotTransaction(merchantSettleBean);
					if(merchantSettleDAO.insert(merchantSettleBean)){
						if(settleDAO.updateTrnId(merchantSettleBean)){
							Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] UPLOADED",this);
						}
					}else{
						Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] FAIL",this);
					}
				}else{
					Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] 거래 정보 없음.",this);
				}
			}catch(Exception e){
				Log.debug("log.day",CommonUtil.getExceptionMessage(e),this);
			}

		}
		return true;
	}
	
	public boolean type301(){

		String startDate 	= lastMonth+"01";										//지난달 1일부터
		String endDate		= lastMonthLastDay;										//지난달 마지막 날까지
		String settleDate	= currentMonth+"10000000";								//이번달 10일 지급
		Log.debug("log.day","TYPE_301 월말 정산 STARTDAY =["+startDate+"] ENDDAY=["+endDate+"] SETTLE_DAY =["+settleDate.substring(0,8)+"]" ,this);
		MerchantBillDAO merchantBillDAO = new MerchantBillDAO();
		merchantBillDAO.orderBy = "  ORDER BY MERCHANT_ID ASC";
		merchantBillDAO.setPageSize(10000);
		List<MerchantBillBean> merchantBillList = merchantBillDAO.getByPeriodAndActive(TYPE_301);
		for(int i=0 ; i<merchantBillList.size();i++){
			try{
				MerchantBillBean merchantBillBean = (MerchantBillBean)merchantBillList.get(i);
				MerchantBean merchantBean = new MerchantDAO().getById(merchantBillBean.getMerchantId());
				merchantId = merchantBillBean.getMerchantId();
				Log.debug("log.day","",this);
				Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] CUR/TOT =["+(i+1)+"/"+merchantBillList.size()+"]",this);
				if(settleDAO.isCreateSettle(merchantId, endDate)){
					MerchantSettleBean merchantSettleBean = getSettleBean(merchantBillBean,merchantBean);
					merchantSettleBean.setStartDay(startDate);
					merchantSettleBean.setEndDay(endDate);
					merchantSettleBean.setPeriod(TYPE_301);
					merchantSettleBean.setSettleCnt("1");
					merchantSettleBean.setSettleDate(CommonUtil.stringToTimestamp(settleDate));
					merchantSettleBean = calcTotTransaction(merchantSettleBean);
					if(merchantSettleDAO.insert(merchantSettleBean)){
						if(settleDAO.updateTrnId(merchantSettleBean)){
							Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] UPLOADED",this);
						}
					}else{
						Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] FAIL",this);
					}
				}else{
					Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] 거래 정보 없음.",this);
				}
			}catch(Exception e){
				Log.debug("log.day",CommonUtil.getExceptionMessage(e),this);
			}

		}
		return true;
	}
	
	
	
	
	/**
	 * 전월(이번달 이전 전체를 뜻함) 거래 및 당월 거래를 합산하여 전체 금액을 수수료로 적용한다.
	 * 소숫점에 대해서는 반올림 처리한다.
	 */
	public MerchantSettleBean calcTotTransaction(MerchantSettleBean merchantSettleBean){
		merchantSettleBean.setPreTrnBillId("T"+seqDAO.getSEQ("BILL"));
		settleDAO.insertBillTrnPre(merchantSettleBean);
		merchantSettleBean.setPreTrnVmFee(adjustAmount(merchantSettleBean.getPreTrnVmRate()*merchantSettleBean.getPreTrnVmAmt()));			//합산된 금액으로 수수료를 적용한다.
		merchantSettleBean.setPreTrnJaFee(adjustAmount(merchantSettleBean.getPreTrnJaRate()*merchantSettleBean.getPreTrnJaAmt()));			//합산된 금액으로 수수료를 적용한다.
		if(merchantSettleBean.getPreTrnVmCnt() + merchantSettleBean.getPreTrnJaCnt() > 0){
			Log.debug("log.day","이전 거래 V/M=["+merchantSettleBean.getPreTrnVmAmt()+"] J/A =["+merchantSettleBean.getPreTrnJaAmt()+"]",this);
		}
		
		//PG_BILL_TRN_ID 에 각 이번달 거래분을 INSERT 하고 각 총계를 계산한다.
		merchantSettleBean.setTrnBillId("T"+seqDAO.getSEQ("BILL"));
		settleDAO.insertBillTrn(merchantSettleBean);
		merchantSettleBean.setTrnVmFee(adjustAmount(merchantSettleBean.getTrnVmRate()*merchantSettleBean.getTrnVmAmt()));					//합산된 금액으로 수수료를 적용한다.
		merchantSettleBean.setTrnJaFee(adjustAmount(merchantSettleBean.getTrnJaRate()*merchantSettleBean.getTrnJaAmt()));					//합산된 금액으로 수수료를 적용한다.
		if(merchantSettleBean.getTrnVmCnt() + merchantSettleBean.getTrnJaCnt() > 0){
			Log.debug("log.day","당월 거래 V/M=["+merchantSettleBean.getTrnVmAmt()+"] J/A =["+merchantSettleBean.getTrnJaAmt()+"]",this);
		}
		//PG_BILL_TRN_ID 에 전달 이번달 이전분을 INSERT 하고 각 총계를 계산한다.

		//계산된 금액을 토대로 전체 금액을 산정한다.
		merchantSettleBean.setTotAmt(merchantSettleBean.getTrnVmAmt()+merchantSettleBean.getTrnJaAmt()+merchantSettleBean.getPreTrnVmAmt()+merchantSettleBean.getPreTrnJaAmt());	//총거래금액(이번달. 지난달)

		return calcTrnRefund(merchantSettleBean);
	}

    /**
     * 이전 주기에 대한 취소 수수료는 이전 금액 + ( 건당 취소 수수료 * 건수 )
     * 이전 월에 대한 취소 원가 계산 ( 원 금액 - ( (수수료 + FACTORING) * 1.05 ))
     * 
     * 당월 취소(승인 취소( 전일 ~ 다음날 12시 제외) 제외)의 경우는 건당 취소 수수료 * 건수  
     * 원금에 대한 차감은 후에 종합 수수료 계산시 적용한다.
     * @param merchantSettleBean
     * @return
     */
	public MerchantSettleBean calcTrnRefund(MerchantSettleBean merchantSettleBean){
		merchantSettleBean.setPreRefundBillId("R"+seqDAO.getSEQ("BILL"));
		settleDAO.insertBillRefundPre(merchantSettleBean);
		
		
		//VISA,MASTER 에 대해서 정산 후 취소의 경우 원금 차감 및 건당 수수료 부과 
		// V/M 지급 금액 계산 
		/** 수수료 차감 공식 제외 
		double transactionFee = merchantSettleBean.getTrnVmRate() * merchantSettleBean.getPreRefundVmAmt();
		double vatFee = transactionFee * (merchantSettleBean.getVanRate());
		merchantSettleBean.setPreRefundVmAmt(merchantSettleBean.getPreRefundVmAmt() - transactionFee - vatFee);
		
		transactionFee = merchantSettleBean.getTrnJaRate() * merchantSettleBean.getPreRefundJaAmt();
		vatFee = transactionFee * (merchantSettleBean.getVanRate());
		merchantSettleBean.setPreRefundJaAmt(merchantSettleBean.getPreRefundJaAmt() - transactionFee - vatFee);
		**/
		
		if(merchantSettleBean.getPreRefundVmRate() == 0){																						//원거래 금액만 차감
			merchantSettleBean.setRefundVmFee(0);																							
			merchantSettleBean.setRefundJaFee(0);		
		}else{																																	//원거래 금액 + 건당 추가 차감
			merchantSettleBean.setPreRefundVmFee(merchantSettleBean.getPreRefundVmRate()*merchantSettleBean.getPreRefundVmCnt());										
			merchantSettleBean.setPreRefundJaFee(merchantSettleBean.getPreRefundJaRate()*merchantSettleBean.getPreRefundJaCnt());
			if(merchantSettleBean.getPreRefundVmCnt() + merchantSettleBean.getPreRefundJaCnt() > 0){
				Log.debug("log.day","원거래 1:1 차감 + 건당 ["+merchantSettleBean.getPreRefundJaRate()+"] 적용함.  차감함.",this);
				Log.debug("log.day","이전 취소 V/M=["+merchantSettleBean.getPreRefundVmFee()+"] J/A =["+merchantSettleBean.getPreRefundJaFee()+"]",this);
			}
		}
		
		merchantSettleBean.setRefundBillId("R"+seqDAO.getSEQ("BILL"));
		settleDAO.insertBillRefund(merchantSettleBean);
		
		if(merchantSettleBean.getRefundVmRate() == 0){																							//차감하지 않는다.
			merchantSettleBean.setRefundVmFee(0);																							
			merchantSettleBean.setRefundJaFee(0);		
			if(merchantSettleBean.getRefundVmCnt() + merchantSettleBean.getRefundJaCnt() > 0){
				Log.debug("log.day","차감 없음 ",this);
				Log.debug("log.day","이전 취소 V/M=["+merchantSettleBean.getPreRefundVmFee()+"] J/A =["+merchantSettleBean.getPreRefundJaFee()+"]",this);
			}
		}else{																																	//취소 건당 FEE를 차감한다.
			merchantSettleBean.setRefundVmFee(merchantSettleBean.getRefundVmRate()*merchantSettleBean.getRefundVmCnt());			
			merchantSettleBean.setRefundJaFee(merchantSettleBean.getRefundJaRate()*merchantSettleBean.getRefundJaCnt());			
			if(merchantSettleBean.getPreRefundVmCnt() + merchantSettleBean.getPreRefundJaCnt() > 0){
				Log.debug("log.day","별도 취소 수수료 적용",this);
				Log.debug("log.day","당월 취소 V/M=["+merchantSettleBean.getRefundVmFee()+"] J/A =["+merchantSettleBean.getRefundJaFee()+"]",this);
			}
		}
		
		
		return calcCB(merchantSettleBean);
	}


	/**
	 * 차지백은 전체 금액을 차감한 후 건당 차지백 FEE * 건수를 적용하여 추가 수수료를 적용한다.
	 */
	public MerchantSettleBean calcCB(MerchantSettleBean merchantSettleBean){
		merchantSettleBean.setCbBillId("C"+seqDAO.getSEQ("BILL"));
		settleDAO.insertBillCb(merchantSettleBean);
		merchantSettleBean.setCbFee(merchantSettleBean.getCbCnt()*merchantSettleBean.getCbRate());
		if(merchantSettleBean.getCbFee() > 0){
			Log.debug("log.day","당월 C*B CNT =["+merchantSettleBean.getCbCnt()+"] AMT =["+merchantSettleBean.getCbAmt()+"]",this);
		}
		return calcManagement(merchantSettleBean);
	}



	/**
	 * 년 관리비 청구 및 SETUP_FEE 청구 
	 */
	public MerchantSettleBean calcManagement(MerchantSettleBean merchantSettleBean){
		List<MerchantSettleBean> msList = merchantSettleDAO.get(" AND MERCHANT_ID ='"+merchantSettleBean.getMerchantId()+"'");
		
		
		
		if(msList.size() == 0){
			merchantSettleBean.setEtcName("Setup Fee");
			merchantSettleBean.setEtcAmt(merchantSettleBean.getTemp2Double());
			Log.debug("log.day","최초1회 SETUP_FEE 청구  =["+merchantSettleBean.getEtcAmt()+"]",this);
			merchantSettleBean.setManageFee(merchantSettleBean.getManageFee());
			Log.debug("log.day","최초1회 년 관리비 청구  =["+merchantSettleBean.getManageFee()+"]",this);
		}else{
			//1년 후 동일 1회차이면 다시 년 관리비 청구 
			String oneYearLater = CommonUtil.getOpDate(GregorianCalendar.MONTH,13,currentDate).substring(0,6);
			if(oneYearLater.startsWith(currentMonth) &&  merchantSettleBean.getSettleCnt().equals("1")){
				merchantSettleBean.setManageFee(merchantSettleBean.getManageFee());
				Log.debug("log.day","2회 년 관리비 청구  =["+merchantSettleBean.getManageFee()+"]",this);
			}else{
				merchantSettleBean.setManageFee(0);
			}
		}
		Log.debug("log.day",merchantSettleBean.getEtcName()+"=["+merchantSettleBean.getEtcAmt()+"]",this);
		Log.debug("log.day","년 관리비 =["+merchantSettleBean.getManageFee()+"]",this);
		

		return calcEtc(merchantSettleBean);
	}

	public MerchantSettleBean calcEtc(MerchantSettleBean merchantSettleBean){
		
		
		//VAN FEE 산정 : 이달 전달 총 거래 건수 * 약정된 건당 Rate
		merchantSettleBean.setVanCnt(merchantSettleBean.getTrnVmCnt()+merchantSettleBean.getTrnJaCnt()+merchantSettleBean.getPreTrnVmCnt()+merchantSettleBean.getPreTrnJaCnt());
		merchantSettleBean.setVanFee(merchantSettleBean.getVanCnt()*merchantSettleBean.getVanRate());
		if(merchantSettleBean.getVanCnt() > 0){
			Log.debug("log.day","VAN CNT =["+merchantSettleBean.getVanCnt()+"] FEE =["+merchantSettleBean.getVanFee()+"]",this);
		}
		
		//정산 대금이 없는경우
		if(merchantSettleBean.getTotAmt()==0){
			merchantSettleBean.setTransfeFee(0);
		}
		//V/B*J/A 수수료 합계
		// V/M 거래수수료 + J/A 거래수수료 + 이전달 V/M 거래수수료 + 이전달 J/A 거래수수료
		double trnFee = merchantSettleBean.getTrnVmFee()+merchantSettleBean.getTrnJaFee()+merchantSettleBean.getPreTrnVmFee()+merchantSettleBean.getPreTrnJaFee();
		// 기타 수수료 + 년관리비 + VAN 수수료 + C*B 수수료
		trnFee = trnFee +merchantSettleBean.getEtcAmt()+ merchantSettleBean.getManageFee()+merchantSettleBean.getVanFee()+merchantSettleBean.getCbFee();
		// 당월 취소 수수료 V/M 당월 수수료 + J/A 당월 수수료 + V/M 전월 수수료 + J/A 전월 수수료
		trnFee = trnFee + merchantSettleBean.getRefundVmFee()+merchantSettleBean.getRefundJaFee()+ merchantSettleBean.getPreRefundVmFee()+merchantSettleBean.getPreRefundJaFee();
		
		
		//부가세 계산
		merchantSettleBean.setVatAmt(adjustAmount(trnFee * merchantSettleBean.getTemp3Double()));
		Log.debug("log.day","부가세 기준율 = "+merchantSettleBean.getTemp3Double(),this);
		//전달 차지백 금액 및 취소 금액 합계 + 부가세
		merchantSettleBean.setTotFee(trnFee+merchantSettleBean.getCbAmt()+merchantSettleBean.getPreRefundVmAmt()+merchantSettleBean.getPreRefundJaAmt()+merchantSettleBean.getVatAmt());
		
		//송금수수료 계산 ( 기본 은행정보를 이용하며 예상 송금 금액으로 산정한다. )
		double transferFee = getTransferFee(merchantSettleBean,merchantSettleBean.getTotAmt()-merchantSettleBean.getTotFee());
		merchantSettleBean.setTransfeFee(transferFee);
		if(transferFee > 0){
			Log.debug("log.day","TRANSFER FEE =["+CommonUtil.toString(transferFee)+"]",this);
		}

		//총 비용에 송금수수료 추가
		merchantSettleBean.setTotFee(merchantSettleBean.getTotFee()+transferFee);

		//총 정산해야할 금액 계산
		merchantSettleBean.setTotSettle(merchantSettleBean.getTotAmt()-merchantSettleBean.getTotFee());

		return calcDeposit(merchantSettleBean);
	}

	public MerchantSettleBean calcDeposit(MerchantSettleBean merchantSettleBean){

		MerchantDepositDAO merchantDepositDAO = new MerchantDepositDAO();
		List<MerchantDepositBean> merchantDepositList = merchantDepositDAO.getByMerchant(merchantSettleBean.getMerchantId());
		List<MerchantDepositBean> payList = settleDAO.getCurrentPay(merchantSettleBean.getMerchantId());
		
		//지불해야하는 금액 
		long paySumAmount = 0;
		for(int i=0;i<payList.size();i++){
			MerchantDepositBean payDepositBean = (MerchantDepositBean)payList.get(i);
			paySumAmount += payDepositBean.getCurAmt();
		}
		//최종 DEPOSIT 정보 
		MerchantDepositBean merchantDepositBean = null;
		if(merchantDepositList.size() ==0){
			merchantDepositBean = new MerchantDepositBean();
		}else{	
			merchantDepositBean = (MerchantDepositBean)merchantDepositList.get(0);
		}
		
		merchantSettleBean.setDepositIdx(merchantDepositBean.getIdx());

		if(merchantSettleBean.getTemp1Double() != 0){
			if(merchantDepositBean.getIdx() ==0){
				merchantDepositBean.setMerchantId(merchantSettleBean.getMerchantId());
				merchantDepositBean.setLastAmt(0);
			}else{
				//지난달 총액을 마지막 금액으로 넣어준다.
				merchantDepositBean.setLastAmt(merchantDepositBean.getTotAmt());
			}
			

			//Deposit %
			merchantDepositBean.setRate(merchantSettleBean.getTemp1Double());
			//이번 주기 Deposit 금액
			double totAmt = merchantSettleBean.getTotAmt();
			merchantDepositBean.setCurAmt(adjustAmount(totAmt*merchantSettleBean.getTemp1Double()));
			Log.debug("log.day","DEPOSIT TOT_AMT =["+totAmt+"] RATE =["+merchantSettleBean.getTemp1Double()+"]",this);
			if(merchantSettleBean.getTotAmt() <= 0){
				merchantDepositBean.setCurAmt(0);
			}
			//기본 6개월 후 데파짓 지불
			merchantDepositBean.setPayMonth(CommonUtil.getOpDate(2, 6, currentDate).substring(0,6));
			Log.debug("log.day","CUR_AMT =["+merchantDepositBean.getCurAmt()+"]",this);
			Log.debug("log.day","PAY_MONTH =["+merchantDepositBean.getPayMonth()+"]",this);
			
			
			
			
			//예정된 지급을 하고 그외에는 차감한다.
			//6개월전 Deposit 총금액을 이번달에 보내준다.
			merchantDepositBean.setPayAmt(paySumAmount);
			if(paySumAmount > 0){
				Log.debug("log.day","RELEASE DEPOSIT =["+CommonUtil.toString(paySumAmount)+"]",this);
				for(int i=0;i<payList.size();i++){
					MerchantDepositBean payDepositBean = (MerchantDepositBean)payList.get(i);
					payDepositBean.setPaySettleId(merchantSettleBean.getSettleId());
					payDepositBean.setComments("RELEASE BY SYSTEM = "+CommonUtil.toString(payDepositBean.getCurAmt()));
					Log.debug("log.day","RELEASE IDX =["+CommonUtil.toString(payDepositBean.getIdx())+"]",this);
					settleDAO.releaseDeposit(payDepositBean);
				}
			}
			
		
			//총 Deposit 잔고 = 지난달 총액 + 현재 달 금액 - 이번 줄 금액
			merchantDepositBean.setTotAmt(merchantDepositBean.getLastAmt()+merchantDepositBean.getCurAmt()-merchantDepositBean.getPayAmt());
			//이번달 반환 Deposit
			//현재의 Deposit -지불 금액
			merchantSettleBean.setTotDeposit(merchantDepositBean.getCurAmt()-merchantDepositBean.getPayAmt());
			//총 지급액 = 총거래금액 - 수수료 + 이번달 반환 Deposit
			merchantSettleBean.setTotSettle(merchantSettleBean.getTotSettle()-merchantSettleBean.getTotDeposit());
				
			
			merchantSettleBean.setDepositIdx(SequenceDAO.getSequenceLong("SEQ_MERCHANT_DEPOSIT"));
			merchantDepositBean.setIdx(merchantSettleBean.getDepositIdx());
			merchantDepositBean.setSettleId(merchantSettleBean.getSettleId());
			if(merchantDepositDAO.insert(merchantDepositBean)){
				Log.debug("log.day","DEPOSIT INFO INSERTED ["+merchantDepositBean.getIdx()+"]",this);
			}
		}
		return merchantSettleBean;

	}

	
	/**
	 * 송금 수수료 계산
	 * @param merchantSettleBean
	 * @param amount
	 * @return
	 */
	public double getTransferFee(MerchantSettleBean merchantSettleBean,double amount){
		
		return 0;
		
	}
	
	
	public MerchantSettleBean getSettleBean(MerchantBillBean merchantBillBean,MerchantBean merchantBean){
		MerchantSettleBean merchantSettleBean = new MerchantSettleBean();
		merchantSettleBean.setSettleId("S"+seqDAO.getSETTLESEQ());
		merchantSettleBean.setMerchantId(merchantBillBean.getMerchantId());
		merchantSettleBean.setStatus("R");
		
		String bankInfo = "";
		if(!CommonUtil.isNullOrSpace(merchantBillBean.getBankCode())){
			bankInfo = "["+merchantBillBean.getBankCode()+"]";
		}
		if(!CommonUtil.isNullOrSpace(merchantBillBean.getBranch())){
			bankInfo += "["+merchantBillBean.getBranch()+"]";
		}
		if(!CommonUtil.isNullOrSpace(merchantBillBean.getAccountHolder())){
			bankInfo += "["+merchantBillBean.getAccountHolder()+"]";
		}
		merchantSettleBean.setBankInfo(bankInfo);
		
		merchantSettleBean.setAccount(merchantBillBean.getAccount());
		merchantSettleBean.setTrnVmRate(merchantBillBean.getVisamaster());
		merchantSettleBean.setTrnJaRate(merchantBillBean.getJcbamex());
		merchantSettleBean.setPreTrnVmRate(merchantBillBean.getVisamaster());
		merchantSettleBean.setPreTrnJaRate(merchantBillBean.getJcbamex());
		merchantSettleBean.setCbRate(merchantBillBean.getChargeback());


		if(merchantBillBean.getRefund() == 0){ 													// 원 수수료를 취소에 처리한다.
			merchantSettleBean.setRefundVmRate(merchantBillBean.getVisamaster());				
			merchantSettleBean.setRefundJaRate(merchantBillBean.getJcbamex());					
			merchantSettleBean.setPreRefundVmRate(merchantBillBean.getVisamaster());			
			merchantSettleBean.setPreRefundJaRate(merchantBillBean.getJcbamex());				
		}else{																					// 적용되어 있는 값이 있으면 적용된 취소 수수료 적용
			merchantSettleBean.setRefundVmRate(merchantBillBean.getRefund());					
			merchantSettleBean.setRefundJaRate(merchantBillBean.getRefund());					
			merchantSettleBean.setPreRefundVmRate(merchantBillBean.getRefund());				
			merchantSettleBean.setPreRefundJaRate(merchantBillBean.getRefund());				
		}
		

		
		merchantSettleBean.setManageFee(merchantBillBean.getManagement());
		merchantSettleBean.setTransfeFee(merchantBillBean.getBankTransfer());
		merchantSettleBean.setVanRate(merchantBillBean.getTransaction());
		
		merchantSettleBean.setTemp1Double(merchantBillBean.getDeposit());						//Deposit %
		merchantSettleBean.setTemp1String(CommonUtil.convertTimestampToString(merchantBean.getServiceDate(),"yyyyMMdd"));					//OPEN 일자
		if(merchantSettleBean.getTemp1String() ==null){
			merchantSettleBean.setTemp1String(CommonUtil.convertTimestampToString(merchantBillBean.getRegDate(),"yyyyMMdd"));
		}
		merchantSettleBean.setTemp2Double(merchantBillBean.getSetupFee());
		merchantSettleBean.setTemp3Double(merchantBillBean.getVat());
		


		return merchantSettleBean;
	}
	public long adjustLong(double amount){
		return CommonUtil.parseLong(CommonUtil.adjustDouble(0,amount,BigDecimal.ROUND_HALF_UP));
	}

	/**
	 * 특정 가맹점만 정산 처리할때 . merchantId,startDate,endDate,settleDate,settleCnt를 지정해야 한다.
	 * @return
	 */
	public boolean executeMerchant(){
		String merchantId 	= "";//ID 지정
		String startDate 	= "";//YYYYMMDD
		String endDate		= "";//YYYYMMDD
		String settleDate	= "";//YYYYMMDDHHMMSS
		String settleCnt	= "1";	//정산 횟차 지정
		Log.debug("log.day", merchantId + " 정산 STARTDAY =["+startDate+"] ENDDAY=["+endDate+"]",this);
		MerchantBillDAO merchantBillDAO = new MerchantBillDAO();
		MerchantBillBean merchantBillBean = merchantBillDAO.getByMerchantId(merchantId);
		MerchantBean merchantBean = new MerchantDAO().getById(merchantBillBean.getMerchantId());
		Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"]",this);
		MerchantSettleBean merchantSettleBean = getSettleBean(merchantBillBean,merchantBean);
		merchantSettleBean.setStartDay(startDate);
		merchantSettleBean.setEndDay(endDate);
		merchantSettleBean.setPeriod(merchantBillBean.getPeriod());
		merchantSettleBean.setSettleCnt(settleCnt);
		merchantSettleBean.setSettleDate(CommonUtil.stringToTimestamp(settleDate));
		merchantSettleBean = calcTotTransaction(merchantSettleBean);
		if(merchantSettleDAO.insert(merchantSettleBean)){
			Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] INSERT",this);
			if(settleDAO.updateTrnId(merchantSettleBean)){
				Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] SUCCESS",this);
				System.out.println("MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] SUCCESS");
			}
		}else{
			Log.debug("log.day","MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] FAIL",this);
			System.out.println("MERCHANT_ID =["+merchantBillBean.getMerchantId()+"] FAIL");
		}
		System.out.println(BeanUtil.beanToString(merchantSettleDAO));

		return true;
	}
	
	public int getDayOfWeek(String day){
		Calendar c = Calendar.getInstance(Locale.KOREA);
		c.clear();
		c.set(CommonUtil.parseInt(day.substring(0,4)), CommonUtil.parseInt(day.substring(4,6))-1,CommonUtil.parseInt(day.substring(6,8)),0,0,0);
		return c.get(Calendar.WEEK_OF_MONTH);
	}
	
	public double adjustAmount(double amount){
		if(amount == 0){
			return 0;
		}else if (amount < 0){
			return CommonUtil.adjustDoubleHalfDown(2,amount+0.005);
		}else{
			return CommonUtil.adjustDoubleHalfDown(2,amount-0.005);
		}
	}
	
	
	public void agentSettle(String yyyyMM){
		AgentDAO agentDAO = new AgentDAO();
		AgentBillDAO agentBillDAO = new AgentBillDAO();
		List<AgentBean> agentList = new AgentDAO().getByActive("1");
		for(int i=0;i<agentList.size();i++){
			AgentBean agentBean = (AgentBean)agentList.get(i);
			
			List<MerchantBean> agentMerchantList  = agentDAO.getByAgentId(agentBean.getAgentId());
			for(int j=0;j<agentMerchantList.size();j++){
				MerchantBean merchantBean = (MerchantBean)agentMerchantList.get(j);
				AgentBillBean agentBillBean = agentBillDAO.getByAgentIdMerchantId(agentBean.getAgentId(), merchantBean.getMerchantId());
				agentSettleCalc(yyyyMM,agentBean,agentBillBean, merchantBean);
			}
			
		}
	}
	
	public void agentSettleCalc(String yyyyMM,AgentBean agentBean,AgentBillBean agentBillBean, MerchantBean merchantBean){
		Log.debug("log.day",yyyyMM+" AGENT_ID ="+agentBean.getAgentId()+" MERCHANT_ID ="+merchantBean.getMerchantId(),this);
		List<MerchantSettleBean> settleList = new MerchantSettleDAO().get(" AND  MERCHANT_ID ='"+merchantBean.getMerchantId()+"' AND TO_CHAR(SETTLE_DATE,'YYYYMM') ='"+yyyyMM+"'  ");
		AgentSettleBean agentSettleBean = new AgentSettleBean();
		
		agentSettleBean.setSettleId("");
		agentSettleBean.setAgentId(agentBean.getAgentId());
		agentSettleBean.setMerchantId(merchantBean.getMerchantId());
		agentSettleBean.setStartDay(lastMonth);
		agentSettleBean.setEndDay(lastMonthLastDay);
		agentSettleBean.setSettleDate(CommonUtil.stringToTimestamp(currentDate));
		String bankInfo = "";
		if(!CommonUtil.isNullOrSpace(agentBean.getBankCode())){
			bankInfo = "["+agentBean.getBankCode()+"]";
		}
		if(!CommonUtil.isNullOrSpace(agentBean.getBranch())){
			bankInfo += "["+agentBean.getBranch()+"]";
		}
		if(!CommonUtil.isNullOrSpace(agentBean.getAccountHolder())){
			bankInfo += "["+agentBean.getAccountHolder()+"]";
		}
		agentSettleBean.setBankInfo(bankInfo);
		agentSettleBean.setAccount(agentBean.getAccount());
		
		
		//
		
		
		
		
		
		
		
	}




	public static void main(String[] args){
		
		com.pgmate.daemon.settle.Settlement s = new com.pgmate.daemon.settle.Settlement();
		
	}

}

/*
 * Project Name : MONO_DAEMON
 * Project      : MONO_DAEMON
 * File Name    : com.pgmate.daemon.settle.Settle.java
 * Date	        : Apr 6, 2009
 * Version      : 2.0
 * Author       : ginaida@ginaida.net
 * Comment      :
 */

package com.pgmate.daemon.settle;

import java.util.GregorianCalendar;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.mail.EMail;
import biz.trustnet.common.util.BeanUtil;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.daemon.dao.BillingDAO;
import com.pgmate.model.db.DepositBean;
import com.pgmate.model.db.MerchantMngBean;
import com.pgmate.model.db.SettleBean;
import com.pgmate.model.db.SettleInfoBean;
import com.pgmate.model.db.dao.DepositDAO;
import com.pgmate.model.db.dao.GSISEQDAO;
import com.pgmate.model.db.dao.MerchantMngDAO;
import com.pgmate.model.db.dao.SettleDAO;
import com.pgmate.model.db.dao.SettleInfoDAO;

public class Settle {

	private String startDay			= "";
	private String endDay			= "";
	private String currentDay		= "";
	private BillingDAO billingDAO 	= null;
	private SettleBean settleBean  	= null;
	private String transactionSettleIdx = "";
	private String[] cardType = {"'VISA','MASTER'","'JCB'"};

	public Settle(){
	}

	public boolean execute(){
		currentDay = CommonUtil.getCurrentDate("yyyyMMdd");
			
		if(currentDay.endsWith("03")){
			setPeriod("15");
			execute("unaico");
			execute("unaico2");
			setPeriod("10");
			execute("unaicoeu");
				
		}else if(currentDay.endsWith("13")){
			setPeriod("10");
			execute("unaicoeu");
		}else if(currentDay.endsWith("18")){
			setPeriod("15");
			execute("unaico");
			execute("unaico2");
		}else if(currentDay.endsWith("23")){
			setPeriod("10");
			execute("unaicoeu");
		}else{
			Log.debug("log.day",currentDay+" 오늘은 정산이 없습니다.",this);
		}
		
		return true;
	}
	
	public void setPeriod(String type){
		String month = CommonUtil.getCurrentDate("yyyyMM");
		int date = CommonUtil.parseInt(CommonUtil.getCurrentDate("dd"));
		
		if(type.equals("7")){			//주정산 
			startDay = CommonUtil.getOpDate(GregorianCalendar.DATE,-28,currentDay);
			endDay	 = CommonUtil.getOpDate(GregorianCalendar.DATE,-22,currentDay);		// 일주일간
		}else if(type.equals("10")){	//10일 정산
			if(date == 3){
				endDay	 = CommonUtil.getOpDate(GregorianCalendar.DATE,-3,currentDay);		// 마지막날.
				startDay = endDay.substring(0,6)+"21";
			}else if(date == 13){
				startDay = month+"01";
				endDay	 = month+"10";
			}else if(date == 23){
				startDay = month+"11";
				endDay	 = month+"20";
			}
		}else if(type.equals("15")){	//15일 정산
			if(date == 3){
				endDay	 = CommonUtil.getOpDate(GregorianCalendar.DATE,-3,currentDay);		// 마지막날.
				startDay= endDay.substring(0,6)+"16";
			}else{
				startDay = month+"01";
				endDay	 = month+"15";
			}
		}else{
			
		}
	}


	/**
	 * 가맹점 아이디에 따른 수정산을 위하여 별도 설계.
	 * @param merchantId
	 * @return
	 */
	public boolean executeMerchant(String merchantId){
		startDay = CommonUtil.getOpDate(GregorianCalendar.DATE,-35,currentDay);
		endDay	 = CommonUtil.getOpDate(GregorianCalendar.DATE,-22,currentDay);		// 일주일간
		
		execute(merchantId);
		return true;
	}
	
	public void temp(String merchantId){
		Log.debug("log.stl","----- MERCHANT_ID =["+merchantId+"] START -----",this);
		Log.debug("log.stl","-START DAY =["+startDay+"] ",this);
		Log.debug("log.stl","-E N D DAY =["+endDay+"] ",this);
	}

	public void execute(String merchantId){
		settleBean = new SettleBean();
		billingDAO = new BillingDAO();
		
		Log.debug("log.stl","----- MERCHANT_ID =["+merchantId+"] START -----",this);
		Log.debug("log.stl","-START DAY =["+startDay+"] ",this);
		Log.debug("log.stl","-E N D DAY =["+endDay+"] ",this);
		
		MerchantMngBean merchantMngBean = new MerchantMngDAO().getById(merchantId);
		//1. 정산정보의 기본 값 설정
		settleBean.setMerchantId(merchantId);
		settleBean.setSettleYn("N");
		settleBean.setFileName("/upload/"+merchantId.toUpperCase()+"_"+CommonUtil.getCurrentDate("yyyyMMdd")+".pdf");

		//2. 정산상세정보에 대한 기본 값 설정
		SettleInfoBean settleInfoBean = new SettleInfoBean();
		settleInfoBean.setMerchantId(merchantId);
		settleInfoBean.setStartDate(startDay.substring(0,4)+"-"+startDay.substring(4,6)+"-"+startDay.substring(6,8));
		settleInfoBean.setEndDate(endDay.substring(0,4)+"-"+endDay.substring(4,6)+"-"+endDay.substring(6,8));
		settleInfoBean.setSettleYn("N");

		settleBean.setInvoiceIdx("INVOICE_"+new GSISEQDAO().getSEQ("INVOICE"));
		settleInfoBean.setInvoiceIdx(settleBean.getInvoiceIdx());
		//3. 총거래비용 산정
		if(setTransaction(settleInfoBean,merchantMngBean)){	//거래건수가 없다면 VAN_FEE 및 수수료는 의미가 없다.0
			settleBean.setTrnsctnIdx(settleInfoBean.getSettleIdx());

			//4. 총거래비용 건수 기준으로 VAN FEE 를 적용한다.
			setTransactionVanFee(settleInfoBean,merchantMngBean);
			settleBean.setVanFeeIdx(settleInfoBean.getSettleIdx());

			//5. 각 거래금액에 대한 수수료를 차감한다.
			setTransactionFee(settleInfoBean,merchantMngBean);
			settleBean.setFeeIdx(settleInfoBean.getSettleIdx());

		}

		//6. 정산 후 취소된 데이터에 대해서는 (14)
		setTransactionVoid(settleInfoBean);
		settleBean.setRefundIdx(settleInfoBean.getSettleIdx());

		//7. 차지백에 대한 차감 (차지백은 등록되면 무조건 차감한다.)
		setTransactionChargeBack(settleInfoBean,merchantMngBean);
		settleBean.setCbIdx(settleInfoBean.getSettleIdx());


		//최종정산정보 등록
		if(new SettleDAO().insert(settleBean)){
			sendMail();
			Log.debug("log.stl","SETTLE INSERT SUCCESS "+BeanUtil.beanToString(settleBean),this);
		}else{
			Log.debug("log.stl","SETTLE INSERT FAILURE "+BeanUtil.beanToString(settleBean),this);
		}
		Log.debug("log.stl","----- MERCHANT_ID =["+merchantId+"] END -----\n",this);

	}

	public boolean setTransaction(SettleInfoBean settleInfoBean,MerchantMngBean merchantMngBean){
		settleInfoBean.setSettleType("T");
		settleInfoBean.setSettleIdx("T_"+new GSISEQDAO().getSETTLESEQ());
		transactionSettleIdx = settleInfoBean.getSettleIdx();
		if(billingDAO.insertTrnsctnData(settleInfoBean)){			//거래데이터를 TB_SETTLE_IDX 로 저장
			Log.debug("log.stl","TB_TRNSCTN --> TB_SETTLE_IDX SUCCESS",this);
			if(billingDAO.insertSettleInfo(settleInfoBean)){	//TB_SETTLE_IDX 를 TB_SETTLE_INFO 로 저장
				Log.debug("log.stl","TB_SETTLE_IDX --> TB_SETTLE_INFO SUCCESS",this);
				if(billingDAO.updateTrnsctnData(settleInfoBean)){	//TB_SETTLE_IDX 의 TRANSACTION_ID 를 이용하여 TB_TRNSCTN 정산예정('11') 으로 업데이트
					Log.debug("log.stl","TB_SETTLE_IDX --> TB_TRNSCTN STATUS UPDATE SUCCESS",this);

					//Deposit 정보 Insert
					DepositDAO depositDAO =  new DepositDAO();
					DepositBean depositBean = depositDAO.getById(settleInfoBean.getMerchantId());
					DepositBean payDepositBean = depositDAO.getByMerchantPayDate(settleInfoBean.getMerchantId(), currentDay, "N");
					if(depositBean.getIdx() ==0){
						depositBean = new DepositBean();
						depositBean.setMerchantId(settleInfoBean.getMerchantId());
						depositBean.setLastAmount(0);
					}else {
						depositBean.setLastAmount(depositBean.getTotalAmount());
					}
					
					depositBean.setCurrAmount(CommonUtil.adjustDoubleHalfDown(2,settleInfoBean.getAmount()*merchantMngBean.getDeposit()));
					depositBean.setCurrentRate(merchantMngBean.getDeposit()*100);
					depositBean.setTotalAmount(depositBean.getLastAmount()+depositBean.getCurrAmount());
					depositBean.setPayDate(CommonUtil.getOpDate(2, 6, currentDay));
					
					if(payDepositBean.getIdx() == 0){
						depositBean.setPayAmount(0);
						depositBean.setPayIdx(0);
						depositBean.setPayYn("N");
					}else{
						Log.debug("log.stl","DEPOSIT PAYBACK ="+payDepositBean.getCurrAmount(),this);
						Log.debug("log.stl","DEPOSIT IDX     ="+payDepositBean.getIdx(),this);
						depositBean.setPayAmount(payDepositBean.getCurrAmount());
						depositBean.setTotalAmount(depositBean.getTotalAmount()-depositBean.getPayAmount());
						depositBean.setPayIdx(payDepositBean.getIdx());
						depositBean.setPayYn("Y");
						
						//원 데파짓 정보 업데이트 
						payDepositBean.setPayYn("Y");
						depositDAO.update(payDepositBean);
					}
				
					if(depositDAO.insert(depositBean)){
						Log.debug("log.stl","DEPOSIT INFO INSERTED ",this);
						settleBean.setDepositIdx(CommonUtil.toString(depositBean.getIdx()));
					}
					return true;
				}else{
					Log.debug("log.stl","TB_SETTLE_IDX --> TB_TRNSCTN STATUS UPDATE FAILURE",this);
					return false;
				}
			}else{
				Log.debug("log.stl","TB_SETTLE_IDX --> TB_SETTLE_INFO FAILURE",this);
				return false;
			}
		}else{
			Log.debug("log.stl","TB_TRNSCTN --> TB_SETTLE_IDX FAILURE",this);
			return false;
		}
	}


	public boolean setTransactionVanFee(SettleInfoBean settleInfoBean,MerchantMngBean merchantMngBean){
		settleInfoBean.setSettleType("V");
		settleInfoBean.setSettleIdx("V_"+new GSISEQDAO().getSETTLESEQ());


		settleInfoBean.setAmount(CommonUtil.adjustDoubleHalfDown(2,merchantMngBean.getVanFee() * settleInfoBean.getTotalCount()));
		if(new SettleInfoDAO().insert(settleInfoBean)){
			Log.debug("log.stl","TRANSACTION COUNT =["+settleInfoBean.getTotalCount(),this);
			Log.debug("log.stl","TRANSACTION AMOUNT =["+settleInfoBean.getAmount(),this);
			Log.debug("log.stl","TRANSACTION VANFEE INSERT SUCCESS ",this);
			return true;
		}else{
			Log.debug("log.stl","TRANSACTION VANFEE INSERT FAILURE ",this);
			return false;
		}
	}


	public boolean setTransactionFee(SettleInfoBean settleInfoBean,MerchantMngBean merchantMngBean){

		settleInfoBean.setSettleType("F");

		settleInfoBean.setSettleIdx("F_"+new GSISEQDAO().getSETTLESEQ());
		double[] trnsctn = {0,0};
		for(int i=0 ; i< cardType.length ; i++){
			double[] trnsctnData = billingDAO.getCountAndAMount(transactionSettleIdx,cardType[i]);
			trnsctn[0]+=trnsctnData[0];
			if(cardType[i].equals("'VISA','MASTER'")){
				trnsctn[1]+=trnsctnData[1]*merchantMngBean.getVmFee()/100;
			}else if(cardType[i].equals("'JCB'")){
				trnsctn[1]+=trnsctnData[1]*merchantMngBean.getJcbFee()/100;
			}
		}

		settleInfoBean.setTotalCount(trnsctn[0]);
		settleInfoBean.setAmount(CommonUtil.adjustDoubleHalfDown(2, trnsctn[1]));

		if(new SettleInfoDAO().insert(settleInfoBean)){
			Log.debug("log.stl","TRANSACTION COUNT =["+settleInfoBean.getTotalCount(),this);
			Log.debug("log.stl","TRANSACTION AMOUNT =["+settleInfoBean.getAmount(),this);
			Log.debug("log.stl","TRANSACTION FEE INSERT SUCCESS ",this);
			return true;
		}else{
			Log.debug("log.stl","TRANSACTION FEE INSERT FAILURE ",this);
			return false;
		}
	}


	public boolean setTransactionVoid(SettleInfoBean settleInfoBean){
		settleInfoBean.setSettleType("R");
		settleInfoBean.setSettleIdx("R_"+new GSISEQDAO().getSETTLESEQ());

		if(billingDAO.insertTrnsctnVoidData(settleInfoBean)){
			double[] trnsctn = billingDAO.getCountAndAMount(settleInfoBean.getSettleIdx());
			settleInfoBean.setTotalCount(trnsctn[0]);
			settleInfoBean.setAmount(CommonUtil.adjustDoubleHalfDown(2,trnsctn[1]));
			if(new SettleInfoDAO().insert(settleInfoBean)){
				Log.debug("log.stl","TRANSACTION VOID COUNT =["+settleInfoBean.getTotalCount(),this);
				Log.debug("log.stl","TRANSACTION VOID AMOUNT =["+settleInfoBean.getAmount(),this);
				Log.debug("log.stl","TRANSACTION VOID INSERT SUCCESS ",this);
				return true;
			}else{
				Log.debug("log.stl","TRANSACTION VOID INSERT FAILURE ",this);
				return false;
			}
		}else{
			Log.debug("log.stl","TRANSACTION VOID IS NOT EXIST ",this);
			return false;
		}
	}


	public boolean setTransactionChargeBack(SettleInfoBean settleInfoBean,MerchantMngBean merchantMngBean){
		settleInfoBean.setSettleType("C");
		settleInfoBean.setSettleIdx("C_"+new GSISEQDAO().getSETTLESEQ());

		if(billingDAO.insertTrnsctnChargeBackData(settleInfoBean)){
			double[] trnsctn = billingDAO.getCountAndAMount(settleInfoBean.getSettleIdx());
			settleInfoBean.setTotalCount(trnsctn[0]);
			//CB 계산 = 총금액 + 건수*책정피(20$)-기 지급 수수료(cbFee)
			double cbAmount = trnsctn[1]+(trnsctn[0]*merchantMngBean.getCbFee());
			double cbFee    = billingDAO.getCountAndAMountFee(settleInfoBean.getSettleIdx(),merchantMngBean.getVmFee(),merchantMngBean.getJcbFee());
			settleInfoBean.setAmount(CommonUtil.adjustDoubleHalfDown(2,cbAmount-cbFee));

			if(new SettleInfoDAO().insert(settleInfoBean)){
				Log.debug("log.stl","TRANSACTION CHARGEBACK COUNT =["+settleInfoBean.getTotalCount(),this);
				Log.debug("log.stl","TRANSACTION CHARGEBACK AMOUNT1 =["+settleInfoBean.getAmount(),this);
				Log.debug("log.stl","TRANSACTION CHARGEBACK FEE =["+merchantMngBean.getCbFee(),this);
				Log.debug("log.stl","TRANSACTION CHARGEBACK AMOUNT2 =["+trnsctn[0]*merchantMngBean.getCbFee(),this);
				Log.debug("log.stl","TRANSACTION CHARGEBACK INSERT SUCCESS ",this);
				return true;
			}else{
				Log.debug("log.stl","TRANSACTION CHARGEBACK INSERT FAILURE ",this);
				return false;
			}
		}else{
			Log.debug("log.stl","TRANSACTION CHARGEBACK IS NOT EXIST ",this);
			return false;
		}
	}

	public void sendMail(){

		String[] emailList = new String[3];
		emailList[0] = "ginaida@trustmate.net";
		emailList[1] = "phing@trustmate.net";
		emailList[2] = "redsky@monohitech.com";


		if(emailList != null){
			try{
				StringBuffer sb = new StringBuffer();
				sb.append("-- 아래의 내역은 정산 시스템에서 자동 산출된 VISA/MASTER,JCB 별 거래 건수 및 금액입니다.\n");
				sb.append("-- app.pgmate.com 에서 시스템에서 산출된 INVOICE 를 근거로 PG 거래현황을 작성시 이용하여 주시기 바랍니다.\n");
				sb.append("-- 시스템에서는 INVOICE 번호로 확인가능하며 C*B,Refund 를 확인하실 수 있습니다.\n\n");
				sb.append("\n\r 가맹점 ID ="+settleBean.getMerchantId());
				sb.append("\n\r INVOICE NO ="+settleBean.getInvoiceIdx());
				sb.append("\n\r 내역 ="+billingDAO.getSettleInfoGroupByCard(settleBean));

					for(int i=0 ; i<emailList.length ; i++){
						EMail m = new EMail();
						m.setToSubject(emailList[i],settleBean.getMerchantId().toUpperCase()+" 카드별 사용 내역 "+CommonUtil.getCurrentDate("yyyy-MM-dd"));
						m.setText(sb.toString());
						m.sendEMail();
						Log.debug("log.day","MAIL SEND ="+emailList[i],this);
					}

			}catch(Exception e){
				Log.debug("log.day","MAIL ERROR="+CommonUtil.getExceptionMessage(e),this);
			}
		}
	}

	public static void main(String[] args){
		com.pgmate.daemon.settle.Settle s = new com.pgmate.daemon.settle.Settle();
		s.execute();
	}






}

/*
 * Project Name : MONO_DAEMON
 * Project      : MONO_DAEMON
 * File Name    : com.pgmate.daemon.Daemon.java
 * Date	        : Apr 6, 2009
 * Version      : 2.0
 * Author       : ginaida@ginaida.net
 * Comment      :
 */

package com.pgmate.daemon;



import java.util.List;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.mail.EMail;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.daemon.auth.AuthCancel;
import com.pgmate.daemon.bean.SettleBean;
import com.pgmate.daemon.dao.SettleDAO;
import com.pgmate.daemon.settle.Settlement;
import com.pgmate.model.db.dao.TrnsctnDAO;

public class Daemon {

	public Daemon(){
	}

	public boolean execute(String request){
		boolean isExecute = false;


		if(request.equals("SETTLE")){
			Log.debug("log.day","START SETTLEMENT! ",this);
			new Settlement();
		}else if(request.equals("DEMOTRNSCTN")){
			updateDemoTrnsctnStatus();
		}else if(request.equals("TRNSCTNACQUIRE")){
			updateTrnsctnStatus();
		}else if(request.equals("PWSETTLE")){
			pwSettle();
		}else if(request.equals("AUTHCANCLE")){
			new AuthCancel().execute();
		}else{
			Log.debug("log.day","START NOTHING!",this);
		}

		return isExecute;

	}

	/**
	 * DEMO 및 TESTCARD 거래에 대해서 매일 오전
	 */
	public void updateDemoTrnsctnStatus(){
		Log.debug("log.day","UPDATE DEMOTRNSCTNSTATUS RESULT ="+new TrnsctnDAO().updateDemoToFail(),this);
	}

	/**
	 * 거래상태를 매입요청, 매입요청 데이터를 매입완료로 변경 처리 한다.
	 * 매일 12시 거래상태를 아래와 같이 변경한다.
	 */
	public void updateTrnsctnStatus(){
		Log.debug("log.day","UPDATE TRNSCTNSTATUS RESULT ="+new TrnsctnDAO().updateTrnStatusToAcquire(),this);
	}

	/**
	 * 매달 1일에 정산 이후 시스템 처리
	 */
	public void pwSettle(){
		String[] emailList = {"redsky@monohitech.com","ginaida@trustmate.net","phing@trustmate.net"};
		//String[] emailList = {"ginaida@panworld-net.com","phing@panworld-net.com"};
		List<SettleBean> list = new SettleDAO().getPanworldSettle();
		StringBuffer sb = new StringBuffer();
		sb.append("MONOHITECH 지급 내역서\n");
		sb.append(CommonUtil.getCurrentDate("yyyy-MM-dd")+"일까지의 REFUND 및 C*B 가 반영되었습니다. 정산 시에는 미처리된 C*B,REFUND 에 대해서는  수동반영해야 합니다.\n\n");
		for(int i=0 ; i < list.size(); i++){
			SettleBean psBean = (SettleBean)list.get(i);
			sb.append("\r*가맹점			= ["+psBean.getMerchantId()+"]\n");
			sb.append("\r*정산기간		= ["+psBean.getStartDay()+"~"+psBean.getEndDay()+"]\n");
			sb.append("\r*총거래건수		= ["+CommonUtil.adjustDoubleHalfUp(2, psBean.getTotalCnt())+"]\n");
			sb.append("\r*총거래금액		= ["+CommonUtil.adjustDoubleHalfUp(2, psBean.getTotalAmt())+"]\n");
			sb.append("\r*총유효건수		= ["+CommonUtil.adjustDoubleHalfUp(2, psBean.getValidCnt())+"]\n");
			sb.append("\r*총유효금액		= ["+CommonUtil.adjustDoubleHalfUp(2, psBean.getValidAmt())+"]\n");
			sb.append("\r*총정산후취소건수	= ["+CommonUtil.adjustDoubleHalfUp(2, psBean.getRefundCnt())+"]\n");
			sb.append("\r*총정산후취소금액	= ["+CommonUtil.adjustDoubleHalfUp(2, psBean.getRefundAmt())+"]\n");
			sb.append("\r*총차지백건수		= ["+CommonUtil.adjustDoubleHalfUp(2, psBean.getCbCnt())+"]\n");
			sb.append("\r*총차지백금액		= ["+CommonUtil.adjustDoubleHalfUp(2, psBean.getCbAmt())+"]\n\n\n\n");
		}



		try{
			for(int i=0 ; i<emailList.length ; i++){
			EMail m = new EMail();
			m.setToSubject(emailList[i],"[MONOHITECH 지급내역서] "+CommonUtil.getCurrentDate("yyyy-MM-dd"));
			m.setText(sb.toString());
			m.sendEMail();
			Log.debug("log.day","MAIL SEND ="+emailList[i],this);
			}

		}catch(Exception e){
			Log.debug("log.day","MAIL ERROR="+CommonUtil.getExceptionMessage(e),this);
		}
	}

	public static void main(String[] args){
		if(args[0].equals("")){
			System.out.println("ARGUMENTS IS NULL!");
		}else{
			com.pgmate.daemon.Daemon  daemon = new com.pgmate.daemon.Daemon();
			System.out.println("EXECUTE = "+daemon.execute(args[0]));
		}
	}
}

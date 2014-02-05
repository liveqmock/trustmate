
/* 
 * Project Name : KTT-PGVersion II
 * Project      : PGv2_DDL
 * File Name    : net.kttrust.model.db.dao.SMSDAO.java
 * Date	        : Jun 24, 2008
 * Version      : 2.0
 * Author       : ginaida@ginaida.net
 * Comment      :  
 */

package com.pgmate.sms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.sms.model.SMSBean;
import com.pgmate.sms.model.SMSCommunicationBean;

public class SMSDAO {

	public SMSDAO(){
	}
	
	public boolean insertSMS(String phone,String msg){
		return insertSMS(phone,"0234463730",msg);
	}
	
	public boolean insertSMS(String phone,String callBack,String msg){
		SMSBean smsBean = new SMSBean();
		smsBean.setRecvTelNo(phone);
		smsBean.setSendTelNo(callBack);
		smsBean.setMessage(msg);
		return insertSMS(smsBean);
	}
	 
	
	public boolean insertSMS(SMSBean smsBean){
		smsBean.setMessage(CommonUtil.strCutKor(smsBean.getMessage(), 80));
		
		boolean sent = false;
		String query = "INSERT INTO TRUSTMATE.TB_SMTNT_DATA (MSG_SEQ, CUR_STATE, CALL_TO, CALL_FROM, SMS_TXT, MSG_TYPE, SMS_TYPE) VALUES(LPAD(TRUSTMATE.SEQ_SMTNT_DATA.NEXTVAL,11,0),'0',"
			+"'"+smsBean.getRecvTelNo()+"', '"+smsBean.getSendTelNo()+"','"+smsBean.getMessage()+"',4,'TEMP' )";
		try{
			 if(DBFactory.getInstance().statementExecuteUpdate(query) > 0){
				 sent = true;
				 Log.debug("log.day","SMS SEND TO="+smsBean.getRecvTelNo(),this);
			 }
		}catch(Exception e){
			Log.debug("log.day","SMS SEND ERROR="+e.getMessage(),this);
		}
		return sent;
	}
	
	public SMSCommunicationBean insertSMS(SMSCommunicationBean smsBean){
		
		String query = "INSERT INTO TRUSTMATE.TB_SMTNT_DATA (MSG_SEQ, CUR_STATE, CALL_TO, CALL_FROM, SMS_TXT, MSG_TYPE, SMS_TYPE) VALUES(LPAD(TRUSTMATE.SEQ_SMTNT_DATA.NEXTVAL,11,0),'0',?,?,?,4,?) ";
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			for(int i=0; i<smsBean.getRecvTelNo().length;i++){
				pstmt	= conn.prepareStatement(query);
				pstmt.setString(1,smsBean.getRecvTelNo()[i]);
				pstmt.setString(2,smsBean.getSendTelNo());
				pstmt.setString(3,smsBean.getMessage());
				pstmt.setString(4,smsBean.getSource());
				if(pstmt.executeUpdate() > 0){
					 Log.debug("log.day","SMS SENT TO="+smsBean.getRecvTelNo()[i],this);
					smsBean.setState("00");
				}else{
					smsBean.setState("99");
				}
				conn.commit();
			}
		}catch(Exception e){
			Log.debug("log.day","SMS SEND ERROR="+e.getMessage(),this);
			smsBean.setState("88");
		}finally{
			db.close(pstmt);
			db.close(conn);
		}
		return smsBean;
	}
	
	
}


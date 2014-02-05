package com.pgmate.payment.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.dao.MemberDAO;
import com.pgmate.model.db.dao.SMSDAO;
import com.pgmate.model.db.factory.DBFactory;

/**
 * @author Administrator
 *
 */
public class AlertMessage extends Thread {

	private String resultMsg 	= "";
	private String message      = "";
	private String transactionId = "";
	
	private String alertType 	= "";
	private String target		= "";
	private String alertMessage	= "";
	
	
	public AlertMessage(String transactionId,String resultMsg,String message){
		this.transactionId = transactionId;
		this.resultMsg = resultMsg;
		this.message = message;
	}
	public void run() {
		if(!resultMsg.equals("0000")){
			if(getAlert()){
				String[] telNo = null;
				if(target.equals("ALL")){
					telNo = new MemberDAO().getBySmsNotice("");
				}else{
					telNo = new MemberDAO().getBySmsNotice(target);
				}
				if(!alertMessage.equals("")){
					new SMSDAO().send(telNo,"", alertMessage);
				}else{
					new SMSDAO().send(telNo,"", "["+transactionId+"]"+message);
				}
			}
		}
		
	}
	
	public boolean getAlert(){
		String query = "SELECT ALERT_TYPE,  TARGET,MESSAGE FROM TB_ALERT WHERE RESULT_MSG =?";
		boolean isExist = false;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;

		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			pstmt.setString(1,resultMsg );
			rset 	= pstmt.executeQuery();

			while(rset.next()){
				alertType 	= CommonUtil.nToB(rset.getString("ALERT_TYPE"));
				target 		= CommonUtil.nToB(rset.getString("TARGET"));
				message 	= CommonUtil.nToB(rset.getString("MESSAGE"));
				isExist 	= true;
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		return isExist;
	}
}

/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : net.pgmate.model.db.dao.GSISEQDAO.java
 * Date	        : Dec 23, 2008
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.db.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import com.pgmate.model.db.factory.DBFactory;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

public class GSISEQDAO {

	public static final String SEQ_TRANSACTION 	= "TRNNO";
	public static final String SEQ_TRAN_VOID	= "TRNVOID";
	public static final String SEQ_SETTLE		= "SETTLE";
	
	public GSISEQDAO(){	
	}
	
	public synchronized String getTRNNOSEQ(){
		String sequence = "";
		String query = "SELECT TO_CHAR(SYSDATE,'YYMMDD')||LPAD(SEQ_TRANSACTION.NEXTVAL,6,0) AS SEQ FROM DUAL";
		
		DBFactory db 	= null;
		Connection conn			= null;
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		
		try{
			db   = DBFactory.getInstance();
			conn = db.getConnection();
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			while(rset.next()){
				sequence = rset.getString("SEQ");
			}
		}catch(Exception e){
			Log.debug("log.sql","SEQ QUERY="+query,null);
			Log.debug("log.sql",e.getMessage(),null);
			
		}finally{
			db.close(conn,pstmt,rset);
		}
		return sequence;
	}

	
	public synchronized String getTRNVOIDSEQ(){
		return getSEQ(SEQ_TRAN_VOID);
	}
	
	public synchronized String getSETTLESEQ(){
		return getSEQ(SEQ_SETTLE);
	}
	
	
	public String getSEQ(String name){
		
		String sequence = getSequence(name);
		int seq = 1;
		while(sequence.equals("")){
			sequence = getSequence(name);
			
			if(seq == 3){
				sequence = CommonUtil.getCurrentDate("yyMMdd")+CommonUtil.zerofill(CommonUtil.generateRandomKey(),6);
			}
			seq++;
		}
		
		return sequence;
	}
	
	
	public synchronized String getSequence(String name){
		
		String sequence 		= "";
		DBFactory db 	= null;
		Connection conn			= null;
		CallableStatement stmt	= null;
	
		
		try{
			db 	= DBFactory.getInstance();
			conn= db.getConnection();
			stmt= conn.prepareCall("{call GET_SEQ(?,?)}");    
			stmt.setString(1, name);
			stmt.registerOutParameter(2,java.sql.Types.VARCHAR);
			stmt.execute();
			sequence = CommonUtil.nToB(stmt.getString(2));	
		}catch(Exception e){
			Log.debug("log.day","Create Sequence Error ="+name+e.getMessage(),this);
		}finally{
			db.close(stmt);
			db.close(conn);
		}
		return sequence;
	}
}

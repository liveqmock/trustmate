package com.pgmate.payment.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.factory.DBFactory;

public class SmartroUtil {
	
	public SmartroUtil(){
	}
	
	public String getCode(String code){
		String resCode = "8373";
		
		String query = "SELECT KSNET FROM TB_SMARTRO WHERE SMARTRO =?";
		
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			pstmt.setString(1,code);
			rset 	= pstmt.executeQuery();
			
			while(rset.next()){
				resCode =rset.getString("KSNET"); 
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
	
		return resCode;
	}

}

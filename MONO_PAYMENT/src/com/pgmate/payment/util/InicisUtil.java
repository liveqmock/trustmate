package com.pgmate.payment.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.pgmate.model.db.factory.DBFactory;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

/**
 * @author Administrator
 *
 */
public class InicisUtil {

	public InicisUtil(){
	}
	
	public String[] getResponseMessage(String str){
		String[] newStr = {"","",""};
		try{
			byte[] msg = str.getBytes();
			String text = CommonUtil.toString(msg,1,msg.length-2);
			newStr[0] = text.substring(0,text.indexOf("]"));
			text = CommonUtil.replace(text,"][", "");
			text = CommonUtil.replace(text, newStr[0], "");
			String[] message = CommonUtil.split(text,"|", true);
			newStr[1] = message[0];
			newStr[2] = message[1];
		}catch(Exception e){
			Log.debug("log.day","PARSE ERROR"+e.getMessage(),this);
		}
		return newStr;
	}
	
	
	public String getCode(String code){
		String resCode = "8373";
		
		String query = "SELECT KSNET FROM TB_INICIS WHERE INICIS =?";
		
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
	
	public static void main(String[] args){
		InicisUtil i = new InicisUtil();
		i.getResponseMessage("[500416][Card실패|해당 가맹점에서 사용불가한 카드입니다]");
	}
}

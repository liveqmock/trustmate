/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : net.pgmate.model.db.dao.SequenceDAO.java
 * Date	        : Dec 23, 2008
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import com.pgmate.model.db.factory.DBFactory;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

public class SequenceDAO {


	public SequenceDAO(){
		
	}
	
	/**
	 * GET ORACLE SEQUENCE Modify length 
	 * @param sequenceName
	 * @param len
	 * @return
	 */
	public static long getSequence(String sequenceName,int len){
		return CommonUtil.parseLong(getSequence(sequenceName,CommonUtil.toString(len)));
	}
	
	
	/**
	 * GET ORACLE SEQUENCE Modify length
	 * @param sequenceName
	 * @param len
	 * @return
	 */
	public static String getSequence(String sequenceName,String len){
		
		String sequence = "";
		String query = "SELECT LPAD("+sequenceName+".NEXTVAL,"+len+",0) AS SEQ FROM DUAL";
		
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

	
	
	/***
	 * GET ORACLE SEQUENCE 
	 * @param sequenceName
	 * @return
	 */
	public static long getSequenceLong(String sequenceName){
		return CommonUtil.parseLong(getSequence(sequenceName));
	}
	
	
	/**
	 * GET ORACLE SEQUENCE
	 * @param sequenceName
	 * @return
	 */
	public static String getSequence(String sequenceName){
		
		String sequence = "";
		String query = "SELECT NVL("+sequenceName+".NEXTVAL,0) AS SEQ FROM DUAL";
		
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
	
	
	public static long getSequence(Connection conn,String sequenceName,int len){
		return CommonUtil.parseLong(getSequence(conn,sequenceName,CommonUtil.toString(len)));
	}
	
	/**
	 * GET SEQUENCE USING Connection 
	 * important! getSequenceLong() 
	 * Using Connection and not close this Connection;
	 * */
	public static String getSequence(Connection conn,String sequenceName,String len){
		String sequence = "";
		String query = "SELECT LPAD("+sequenceName+".NEXTVAL,"+len+",0) AS SEQ FROM DUAL";
		
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		
		try{
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			while(rset.next()){
				sequence = rset.getString("SEQ");
			}
		}catch(Exception e){
			Log.debug("log.sql","SEQ QUERY="+query,null);
			Log.debug("log.sql",e.getMessage(),null);
			
		}finally{
			try{
				rset.close();
				pstmt.close();
			}catch(Exception e){}
		}
		
		return sequence;
	}
	
	
	/**
	 * GET SEQUENCE USING Connection 
	 * important! getSequenceLong() 
	 * Using Connection and not close this Connection;
	 * 
	 * @param conn
	 * @param sequenceName
	 * @return
	 */
	public static long getSequenceLong(Connection conn,String sequenceName){
		return CommonUtil.parseLong(getSequence(sequenceName));
	}
	
	
	/**
	 * GET ORACLE SEQUENCE
	 * @param sequenceName
	 * @return
	 */
	public static String getSequence(Connection conn,String sequenceName){
		
		String sequence = "";
		String query = "SELECT NVL("+sequenceName+".NEXTVAL,0) AS SEQ FROM DUAL";
		
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		
		try{
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			while(rset.next()){
				sequence = rset.getString("SEQ");
			}
		}catch(Exception e){
			Log.debug("log.sql","SEQ QUERY="+query,null);
			Log.debug("log.sql",e.getMessage(),null);
			
		}finally{
			try{
				rset.close();
				pstmt.close();
			}catch(Exception e){}
		}
		
		return sequence;
	}
	
}


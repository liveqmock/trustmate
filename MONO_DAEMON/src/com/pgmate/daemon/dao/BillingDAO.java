/* 
 * Project Name : MONO_DAEMON
 * Project      : MONO_DAEMON
 * File Name    : com.pgmate.daemon.dao.SettleDAO.java
 * Date	        : Apr 6, 2009
 * Version      : 2.0
 * Author       : ginaida@ginaida.net
 * Comment      :  
 */

package com.pgmate.daemon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import biz.trustnet.common.db.DBConnectionManager;
import biz.trustnet.common.db.DBFactory;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.SettleBean;
import com.pgmate.model.db.SettleInfoBean;
import com.pgmate.model.db.dao.SettleInfoDAO;

public class BillingDAO {

	public BillingDAO(){
	}
	
	/**
	 * 총 거래건에 대한 TB_SETTLE_IDX 로 업데이트 
	 */
	public boolean insertTrnsctnData(SettleInfoBean settleInfoBean){
		String query = "INSERT INTO GSI.TB_SETTLE_IDX ("
					+"	SELECT T.MERCHANT_ID,'"+settleInfoBean.getSettleIdx()+"','T',T.TRANSACTION_ID,S.CARD_TYPE FROM VW_TRNSCTN T,VW_TRNSCTN_SCR S WHERE S.TRANSACTION_ID = T.TRANSACTION_ID AND TRN_STATUS IN ('02','07','08','09')" 
					+"	AND TO_CHAR(TRN_REQ_DATE,'YYYYMMDD') BETWEEN '"+settleInfoBean.getStartDate().replaceAll("-","")+"' AND '"+settleInfoBean.getEndDate().replaceAll("-","")+"' "
					+"	AND T.MERCHANT_ID ='"+settleInfoBean.getMerchantId()+"' )"; 
					 
		DBConnectionManager db 	= null;
		Statement stmt			= null;
		Connection conn			= null;
		int result = 0;
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			stmt	= conn.createStatement();
			result 	= stmt.executeUpdate(query);
			conn.commit();
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(stmt);
			db.close(conn);
		}
		if(result >0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 업데이트된 거래를 정산예정으로 상태를 처리한다. 
	 * @param settleInfoBean
	 * @return
	 */
	public boolean updateTrnsctnData(SettleInfoBean settleInfoBean){
		String query = "UPDATE GSI.TB_TRNSCTN SET TRN_STATUS ='13' WHERE TRANSACTION_ID IN (SELECT TRANSACTION_ID FROM VW_SETTLE_IDX WHERE SETTLE_IDX=?)"; 
					 
		DBConnectionManager db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		int result = 0;
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			int idx = 1;
			pstmt.setString(idx++,settleInfoBean.getSettleIdx());
			result = pstmt.executeUpdate();
			conn.commit();
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(pstmt);
			db.close(conn);
		}
		if(result >0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 총 건수 및 총 거래비용을 계산하여 추가한다.
	 * @param settleInfoBean
	 * @return
	 */
	public boolean insertSettleInfo(SettleInfoBean settleInfoBean){
		String query = "SELECT COUNT(1) AS COUNT,SUM(AMOUNT) AMOUNT FROM VW_TRNSCTN T,VW_SETTLE_IDX S WHERE T.TRANSACTION_ID=S.TRANSACTION_ID AND S.SETTLE_IDX =?";
		DBConnectionManager db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
		
			int idx = 1;
			pstmt.setString(idx++,settleInfoBean.getSettleIdx());
			rset = pstmt.executeQuery();
			while(rset.next()){
				settleInfoBean.setTotalCount(rset.getDouble("COUNT"));
				settleInfoBean.setAmount(rset.getDouble("AMOUNT"));
				
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(pstmt);
			db.close(conn);
		}
		
		if(settleInfoBean.getTotalCount() > 0){
			return new SettleInfoDAO().insert(settleInfoBean);
		}else{
			return false;
		}
	}
	
	public String getSettleInfoGroupByCard(SettleBean settleBean){
		String query = "SELECT S.CARD_TYPE CARD_TYPE, COUNT(1) AS CNT,SUM(AMOUNT) AMT FROM VW_TRNSCTN T,VW_SETTLE_IDX S WHERE T.TRANSACTION_ID=S.TRANSACTION_ID AND S.SETTLE_IDX =? GROUP BY S.CARD_TYPE";
		DBConnectionManager db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		String value = "";
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
		
			pstmt.setString(1,settleBean.getTrnsctnIdx());
			rset = pstmt.executeQuery();
			while(rset.next()){
				value += "CARD =["+rset.getString("CARD_TYPE")+"] : COUNT =["
				+CommonUtil.toString(rset.getDouble("CNT"))+"] : AMOUNT =["+CommonUtil.toString(rset.getDouble("AMT"))+"]";
				
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(pstmt);
			db.close(conn);
		}
		
		return value;
	}
	
	/**
	 * 정산 후 취소한 것만 취소에 대한 원금 차감한다.
	 * @param settleInfoBean
	 * @return
	 */
	public boolean insertTrnsctnVoidData(SettleInfoBean settleInfoBean){
		String query = "INSERT INTO GSI.TB_SETTLE_IDX ("
					+"	SELECT T.MERCHANT_ID,'"+settleInfoBean.getSettleIdx()+"','V',T.TRANSACTION_ID,S.CARD_TYPE FROM VW_TRNSCTN_VOID V,VW_TRNSCTN T,VW_TRNSCTN_SCR S WHERE T.TRANSACTION_ID=V.TRANSACTION_ID AND T.TRANSACTION_ID = S.TRANSACTION_ID AND T.TRN_STATUS ='14'" 
					+"	AND TO_CHAR(V.VOID_REQ_DATE,'YYYYMMDD') BETWEEN '"+settleInfoBean.getStartDate().replaceAll("-","")+"' AND '"+settleInfoBean.getEndDate().replaceAll("-","")+"' AND T.MERCHANT_ID ='"+settleInfoBean.getMerchantId()+"' AND V.STATUS='00' )"; 
					 
		DBConnectionManager db 	= null;
		Statement 	stmt		= null;
		Connection conn			= null;
		int result = 0;
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			stmt	= conn.createStatement();
			
			result = stmt.executeUpdate(query);
			conn.commit();
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(stmt);
			db.close(conn);
		}
		if(result >0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean insertTrnsctnChargeBackData(SettleInfoBean settleInfoBean){
		String query = "INSERT INTO GSI.TB_SETTLE_IDX ("
					+"	SELECT T.MERCHANT_ID,'"+settleInfoBean.getSettleIdx()+"','C',T.TRANSACTION_ID,S.CARD_TYPE FROM VW_TRNSCTN_CB C,VW_TRNSCTN T,VW_TRNSCTN_SCR S WHERE T.TRANSACTION_ID=C.TRANSACTION_ID  AND T.TRANSACTION_ID = S.TRANSACTION_ID" 
					+"	AND TO_CHAR(C.REG_DATE,'YYYYMMDD') BETWEEN '"+settleInfoBean.getStartDate().replaceAll("-","")+"' AND '"+settleInfoBean.getEndDate().replaceAll("-","")+"' AND T.MERCHANT_ID ='"+settleInfoBean.getMerchantId()+"' AND C.CB_STATE IN ('1','2','3','4') )"; 
					 
		DBConnectionManager db 	= null;
		Statement stmt			= null;
		Connection conn			= null;
		int result = 0;
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			stmt	= conn.createStatement();
			result = stmt.executeUpdate(query);
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(stmt);
			db.close(conn);
		}
		if(result >0){
			return true;
		}else{
			return false;
		}
	}
	
	
	public double[] getCountAndAMount(String settleIdx){
		String query = "SELECT COUNT(1) AS COUNT,SUM(AMOUNT) AMOUNT FROM VW_TRNSCTN T,VW_SETTLE_IDX S WHERE T.TRANSACTION_ID=S.TRANSACTION_ID AND S.SETTLE_IDX =?";
		DBConnectionManager db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		double[] trnsctn = new double[2];
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
		
			int idx = 1;
			pstmt.setString(idx++,settleIdx);
			rset = pstmt.executeQuery();
			while(rset.next()){
				trnsctn[0] = rset.getDouble("COUNT");
				trnsctn[1] = rset.getDouble("AMOUNT");
				
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn, pstmt, rset);
		}
		
		return trnsctn;
	}
	
	public double getCountAndAMountFee(String settleIdx,double vmFee,double jcbFee){
		String query = "SELECT CARD_TYPE ,AMOUNT AMOUNT FROM VW_TRNSCTN T,VW_SETTLE_IDX S WHERE T.TRANSACTION_ID=S.TRANSACTION_ID AND S.SETTLE_IDX =?";
		DBConnectionManager db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		double fee = 0;
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
		
			int idx = 1;
			pstmt.setString(idx++,settleIdx);
			rset = pstmt.executeQuery();
			while(rset.next()){
				String cardType = rset.getString("CARD_TYPE");
				if(cardType.equals("VISA") || cardType.equals("MASTER")){
					fee += rset.getDouble("AMOUNT") * vmFee/100;
				}
				if(cardType.equals("JCB")){
					fee += rset.getDouble("AMOUNT") * jcbFee/100;
				}
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn, pstmt, rset);
		}
		
		return fee;
	}
	
	public double[] getCountAndAMount(String settleIdx,String cardType){
		String query = "SELECT COUNT(1) AS COUNT,SUM(AMOUNT) AMOUNT FROM VW_TRNSCTN T,VW_SETTLE_IDX S WHERE T.TRANSACTION_ID=S.TRANSACTION_ID AND S.SETTLE_IDX =? AND S.CARD_TYPE IN ("+cardType+")";
		DBConnectionManager db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		double[] trnsctn = new double[2];
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
		
			int idx = 1;
			pstmt.setString(idx++,settleIdx);
			rset = pstmt.executeQuery();
			while(rset.next()){
				trnsctn[0] = rset.getDouble("COUNT");
				trnsctn[1] = rset.getDouble("AMOUNT");
				
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn, pstmt, rset);
		}Log.debug("log.sql","QUERY="+query,this);
		
		return trnsctn;
	}
}

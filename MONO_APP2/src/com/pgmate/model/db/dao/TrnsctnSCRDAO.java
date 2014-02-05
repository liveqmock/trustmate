/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.TrnsctnSCRDAO.java
 * Date	        : Dec 23, 2008
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



import com.pgmate.model.db.factory.DBFactory;
import com.pgmate.model.db.factory.DBUtil;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.BeanUtil;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.TrnsctnSCRBean;
import com.pgmate.payment.util.GSICrypt;

public class TrnsctnSCRDAO extends DAO {

	public TrnsctnSCRDAO() {
		// TODO Auto-generated constructor stub
	}

	public TrnsctnSCRBean getByTransactionId(String transactionId){
		List<TrnsctnSCRBean> list = get(" AND TRANSACTION_ID = '"+transactionId +"'");
		if(list.size() == 0){
			return new TrnsctnSCRBean();
		}else{
			return (TrnsctnSCRBean)list.get(0);
		}
	}
	
	public TrnsctnSCRBean getByCardNum(String cardNum){
		GSICrypt crypt = new GSICrypt();
		List<TrnsctnSCRBean> list = get(" AND CARD_NUM = '"+crypt.encrypt(cardNum) +"'");
		if(list.size() == 0){
			return new TrnsctnSCRBean();
		}else{
			return (TrnsctnSCRBean)list.get(0);
		}
	}
	
	public List<TrnsctnSCRBean> get(TrnsctnSCRBean trnsctnscrBean){
		GSICrypt crypt = new GSICrypt();
		StringBuffer sb = new StringBuffer();
		
		if(!trnsctnscrBean.getTransactionId().equals("")){
			return get("AND TRANSACTION_ID = '" + trnsctnscrBean.getTransactionId() +"'");
		}
		if(!trnsctnscrBean.getTrxType().equals("")){
			sb.append("AND TRX_TYPE = '"+ trnsctnscrBean.getTrxType() +"'");
		}
		if(!trnsctnscrBean.getCardType().equals("")){
			sb.append("AND CARD_TYPE = '"+ trnsctnscrBean.getCardType() +"'");
		}
		if(!trnsctnscrBean.getCardBin().equals("")){
			sb.append("AND CARD_BIN = '"+ trnsctnscrBean.getCardBin() +"'");
		}
		if(!trnsctnscrBean.getCardNum().equals("")){
			sb.append("AND CARD_NUM = '"+ crypt.encrypt(trnsctnscrBean.getCardNum()) +"'");
		}
		if(!trnsctnscrBean.getCardExpire().equals("")){
			sb.append("AND CARD_EXPIRE = '"+ crypt.encrypt(trnsctnscrBean.getCardExpire()) +"'");
		}
		if(!trnsctnscrBean.getCardPw().equals("")){
			sb.append("AND CARD_PW = '"+ trnsctnscrBean.getCardPw() +"'");
		}
		if(!trnsctnscrBean.getCardCvv().equals("")){
			sb.append("AND CARD_CVV = '"+ trnsctnscrBean.getCardCvv() +"'");
		}
		if(!trnsctnscrBean.getCardIdenti().equals("")){
			sb.append("AND CARD_IDENTI = '"+ trnsctnscrBean.getCardIdenti() +"'");
		}
		
		return get(sb.toString());
	
	}
	
	
	
	public List<TrnsctnSCRBean> get(String subQuery){
		GSICrypt crypt = new GSICrypt();
		String query = "SELECT TRANSACTION_ID, TRX_TYPE, CARD_TYPE, CARD_BIN, CARD_NUM, CARD_EXPIRE, "
			         + "CARD_PW, CARD_CVV, CARD_IDENTI, REG_DATE "
			         + "FROM VW_TRNSCTN_SCR WHERE TRANSACTION_ID IS NOT NULL " + subQuery;
		
		if(super.regStartDate != null){
			query +=" AND REG_DATE >="+new DBUtil().getToDate(regStartDate);
		}
		if(super.regEndDate != null){
			query +=" AND REG_DATE <"+new DBUtil().getToDate(regEndDate);
		}		
		if(!super.orderBy.equals("")){
			query += " "+super.orderBy;
		}
		
		query = super.toPaging(query);
		
		
		List<TrnsctnSCRBean> list = new ArrayList<TrnsctnSCRBean>();
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;

		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			rset 	= pstmt.executeQuery();
			
			while(rset.next()){
				TrnsctnSCRBean trnsctnscrBean = new TrnsctnSCRBean();
				trnsctnscrBean.setTransactionId(rset.getString("TRANSACTION_ID"));
				trnsctnscrBean.setTrxType(rset.getString("TRX_TYPE"));
				trnsctnscrBean.setCardType(rset.getString("CARD_TYPE"));
				trnsctnscrBean.setCardBin(rset.getString("CARD_BIN"));
				trnsctnscrBean.setCardNum(crypt.hideCardNumberDecrypt(rset.getString("CARD_NUM")));
				trnsctnscrBean.setCardExpire(crypt.decrypt(rset.getString("CARD_EXPIRE")));
				trnsctnscrBean.setCardPw(rset.getString("CARD_PW"));
				trnsctnscrBean.setCardCvv(rset.getString("CARD_CVV"));
				trnsctnscrBean.setCardIdenti(rset.getString("CARD_IDENTI"));
				trnsctnscrBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(trnsctnscrBean);
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;

	}
	
	public boolean insert(TrnsctnSCRBean trnsctnscrBean){
		
		String query = "INSERT INTO TB_TRNSCTN_SCR (TRANSACTION_ID, TRX_TYPE, CARD_TYPE, CARD_BIN, CARD_NUM, CARD_EXPIRE, "
			         + "CARD_PW, CARD_CVV, CARD_IDENTI, REG_DATE)"
			         + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, trnsctnscrBean.getTransactionId());
			pstmt.setString(idx++, trnsctnscrBean.getTrxType());
			pstmt.setString(idx++, trnsctnscrBean.getCardType());
			pstmt.setString(idx++, trnsctnscrBean.getCardBin());
			pstmt.setString(idx++, trnsctnscrBean.getCardNum());
			pstmt.setString(idx++, trnsctnscrBean.getCardExpire());
			pstmt.setString(idx++, trnsctnscrBean.getCardPw());
			pstmt.setString(idx++, trnsctnscrBean.getCardCvv());
			pstmt.setString(idx++, trnsctnscrBean.getCardIdenti());

			result = pstmt.executeUpdate();
			conn.commit();			
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(trnsctnscrBean),this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);		
		}finally{
			db.close(pstmt);
			db.close(conn);			
		}
		
		if(result > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean update(TrnsctnSCRBean trnsctnscrBean){
		
		String query = "UPDATE TB_TRNSCTN_SCR SET TRX_TYPE=?, CARD_TYPE=?, CARD_BIN=?, CARD_NUM=?, "
			         + "CARD_EXPIRE=?, CARD_PW=?, CARD_CVV=?, CARD_IDENTI=? "
			         + "WHERE TRANSACTION_ID=?";
		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;		
		
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setString(idx++, trnsctnscrBean.getTrxType());
			pstmt.setString(idx++, trnsctnscrBean.getCardType());
			pstmt.setString(idx++, trnsctnscrBean.getCardBin());
			pstmt.setString(idx++, trnsctnscrBean.getCardNum());
			pstmt.setString(idx++, trnsctnscrBean.getCardExpire());
			pstmt.setString(idx++, trnsctnscrBean.getCardPw());
			pstmt.setString(idx++, trnsctnscrBean.getCardCvv());
			pstmt.setString(idx++, trnsctnscrBean.getCardIdenti());
			pstmt.setString(idx++, trnsctnscrBean.getTransactionId());
			result = pstmt.executeUpdate();
			conn.commit();			

		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(trnsctnscrBean),this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);			
		}finally{
			db.close(pstmt);
			db.close(conn);			
		}

		if(result > 0){
			return true;
		}else{
			return false;
		}
		
	}	
	
	public int getTodayTrnsctnCount(String cardNumber,String trnStatus){

		String query = "SELECT NVL(COUNT(T.IDX),0) AS CNT FROM VW_TRNSCTN_SCR S,VW_TRNSCTN T "
					+" WHERE S.TRANSACTION_ID = T.TRANSACTION_ID AND S.CARD_NUM=? AND TO_CHAR(T.TRN_REQ_DATE,'YYYYMMDD') = TO_CHAR(SYSDATE,'YYYYMMDD') AND T.TRN_STATUS IN ("+trnStatus+")"; 
		
		int count = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;

		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			pstmt.setString(1,new GSICrypt().encrypt(cardNumber));
			rset 	= pstmt.executeQuery();
						
			while(rset.next()){
				count = rset.getInt("CNT");
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return count;
	}
	
	public double getAmountByMerchantIdAndCardNumber(String cardNumber,String merchantId,String trnStatus){

		String query = "SELECT NVL(SUM(AMOUNT),0) AMT FROM VW_TRNSCTN T,VW_TRNSCTN_SCR S WHERE "
					+" T.TRANSACTION_ID = S.TRANSACTION_ID AND S.CARD_NUM=? AND T.MERCHANT_ID=? AND S.REG_DATE > TO_DATE(TO_CHAR(SYSDATE,'YYYY-MM'),'YYYY-MM') AND T.TRN_STATUS IN ("+trnStatus+")"; 
		
		double amount = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;

		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			pstmt.setString(1,new GSICrypt().encrypt(cardNumber));
			pstmt.setString(2,merchantId);
			rset 	= pstmt.executeQuery();
						
			while(rset.next()){
				amount = rset.getDouble("AMT");
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return amount;
	}
	
	public boolean trnsctnDuplicateOfMinutes(String cardNumber,String minutes){

		String query = "SELECT NVL(COUNT(T.IDX),0) AS CNT FROM VW_TRNSCTN_SCR S,VW_TRNSCTN T "
					+" WHERE S.TRANSACTION_ID = T.TRANSACTION_ID AND S.CARD_NUM=? AND TRN_REQ_DATE > SYSDATE - "+minutes+"/10/24/6 AND T.TRN_STATUS ='02' AND T.RESULT_CD='00'"; 
		
		int count = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;

		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			pstmt.setString(1,new GSICrypt().encrypt(cardNumber));
			rset 	= pstmt.executeQuery();
						
			while(rset.next()){
				count = rset.getInt("CNT");
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		if(count > 0){
			return true;
		}else{
			return false;
		}
	
	}
	
	public int getMonthlyTrnsctnCount(String cardNumber,String trnStatus){

		String query = "SELECT NVL(COUNT(T.IDX),0) AS CNT FROM VW_TRNSCTN_SCR S,VW_TRNSCTN T "
					+" WHERE S.TRANSACTION_ID = T.TRANSACTION_ID AND S.CARD_NUM=? AND T.TRN_REQ_DATE > SYSDATE -30 AND T.TRN_STATUS IN ("+trnStatus+")"; 
		
		int count = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;

		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			pstmt.setString(1,new GSICrypt().encrypt(cardNumber));
			rset 	= pstmt.executeQuery();
						
			while(rset.next()){
				count = rset.getInt("CNT");
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return count;
	}
	
	
	
	
}

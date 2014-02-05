/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.TrnsctnAcquireDAO.java
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

import com.pgmate.model.db.TrnsctnAcquireBean;

public class TrnsctnAcquireDAO extends DAO {

	public TrnsctnAcquireDAO() {
		// TODO Auto-generated constructor stub
	}

	public TrnsctnAcquireBean getByIdx(String idx){
		List<TrnsctnAcquireBean> list = get(" AND IDX ="+idx);
		if(list.size() == 0){
			return new TrnsctnAcquireBean();
		}else{
			return (TrnsctnAcquireBean)list.get(0);
		}
	}
	
	
	public List<TrnsctnAcquireBean> get(TrnsctnAcquireBean trnsctnacquireBean){
		StringBuffer sb = new StringBuffer();
		
		if(trnsctnacquireBean.getIdx() != 0){
			return get(" AND IDX =" + CommonUtil.toString(trnsctnacquireBean.getIdx()));
		}
		if(!trnsctnacquireBean.getTransactionId().equals("")){
			sb.append(" AND TRANSACTION_ID = '"+ trnsctnacquireBean.getTransactionId() +"'");
		}
		if(!trnsctnacquireBean.getStatus().equals("")){
			sb.append(" AND STATUS = '"+ trnsctnacquireBean.getStatus() +"' ");
		}
		if(!trnsctnacquireBean.getAcquireDate().equals("")){
			sb.append(" AND ACQUIRE_DATE = '"+ trnsctnacquireBean.getAcquireDate()+"' ");
		}
		if(trnsctnacquireBean.getAmount() != 0){
			sb.append(" AND AMOUNT = '"+ CommonUtil.toString(trnsctnacquireBean.getAmount()) +"' ");
		}
		if(!trnsctnacquireBean.getCardErrCd().equals("")){
			sb.append(" AND CARD_ERROR_CD = '"+ trnsctnacquireBean.getCardErrCd() +"'");
		}
		if(!trnsctnacquireBean.getVanErrCd().equals("")){
			sb.append(" AND VAN_ERROR_CD = '"+ trnsctnacquireBean.getVanErrCd() +"'");
		}
		
		return get(sb.toString());
	
	}
	
	public List<TrnsctnAcquireBean> getByMerchant(TrnsctnAcquireBean trnsctnacquireBean){
		StringBuffer sb = new StringBuffer();
		
		if(trnsctnacquireBean.getIdx() != 0){
			return get(" AND A.IDX =" + CommonUtil.toString(trnsctnacquireBean.getIdx()));
		}
		if(!trnsctnacquireBean.getTemp1String().equals("")){
			sb.append(" AND B.MERCHANT_ID = '"+ trnsctnacquireBean.getTemp1String() +"'");
		}
		if(!trnsctnacquireBean.getTransactionId().equals("")){
			sb.append(" AND A.TRANSACTION_ID = '"+ trnsctnacquireBean.getTransactionId() +"'");
		}
		if(!trnsctnacquireBean.getStatus().equals("")){
			sb.append(" AND A.STATUS = '"+ trnsctnacquireBean.getStatus() +"' ");
		}
		if(!trnsctnacquireBean.getAcquireDate().equals("")){
			sb.append(" AND A.ACQUIRE_DATE = '"+ trnsctnacquireBean.getAcquireDate()+"' ");
		}
		if(trnsctnacquireBean.getAmount() != 0){
			sb.append(" AND A.AMOUNT = '"+ CommonUtil.toString(trnsctnacquireBean.getAmount()) +"' ");
		}
		if(!trnsctnacquireBean.getCardErrCd().equals("")){
			sb.append(" AND A.CARD_ERROR_CD = '"+ trnsctnacquireBean.getCardErrCd() +"'");
		}
		if(!trnsctnacquireBean.getVanErrCd().equals("")){
			sb.append(" AND A.VAN_ERROR_CD = '"+ trnsctnacquireBean.getVanErrCd() +"'");
		}
		if(!trnsctnacquireBean.getPublicAgentId().equals("")){
			sb.append(" AND B.MERCHANT_ID IN (SELECT MERCHANT_ID FROM TB_AGENT_MERCHANT WHERE AGENT_ID = '"+ trnsctnacquireBean.getPublicAgentId() +"') ");
		}
		
		if(!trnsctnacquireBean.getPublicGroupId().equals("")){
			sb.append(" AND B.MERCHANT_ID IN (SELECT MERCHANT_ID FROM TB_GROUP_MERCHANT WHERE GROUP_ID = '"+ trnsctnacquireBean.getPublicGroupId() +"') ");
		}
		
		return getByMerchant(sb.toString());
	
	}	
	
	public List<TrnsctnAcquireBean> get(String subQuery){
		String query = "SELECT IDX, TRANSACTION_ID, STATUS, ACQUIRE_DATE, AMOUNT, CARD_ERROR_CD, VAN_ERROR_CD, TEMP, VAN, REG_DATE "
			         + "FROM VW_TRNSCTN_ACQUIRE WHERE IDX IS NOT NULL " + subQuery;
		
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
		
		
		List<TrnsctnAcquireBean> list = new ArrayList<TrnsctnAcquireBean>();
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
				TrnsctnAcquireBean trnsctnacquireBean = new TrnsctnAcquireBean();
				trnsctnacquireBean.setIdx(rset.getLong("IDX"));
				trnsctnacquireBean.setTransactionId(rset.getString("TRANSACTION_ID"));
				trnsctnacquireBean.setStatus(rset.getString("STATUS"));
				trnsctnacquireBean.setAcquireDate(rset.getString("ACQUIRE_DATE"));
				trnsctnacquireBean.setAmount(rset.getDouble("AMOUNT"));
				trnsctnacquireBean.setCardErrCd(rset.getString("CARD_ERROR_CD"));
				trnsctnacquireBean.setVanErrCd(rset.getString("VAN_ERROR_CD"));
				trnsctnacquireBean.setTemp(rset.getString("TEMP"));
				trnsctnacquireBean.setVan(rset.getString("VAN"));
				trnsctnacquireBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(trnsctnacquireBean);
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;

	}
	
	public List<TrnsctnAcquireBean> getByMerchant(String subQuery){
		String query = "SELECT A.IDX, A.TRANSACTION_ID,  A.STATUS,  A.ACQUIRE_DATE,  A.AMOUNT,  A.CARD_ERROR_CD,  A.VAN_ERROR_CD,  A.TEMP,  A.REG_DATE, B.MERCHANT_ID "
			         + "FROM VW_TRNSCTN_ACQUIRE A, VW_TRNSCTN B WHERE A.IDX IS NOT NULL AND A.TRANSACTION_ID = B.TRANSACTION_ID " + subQuery;
		
		if(super.regStartDate != null){
			query +=" AND A.REG_DATE >="+new DBUtil().getToDate(regStartDate);
		}
		if(super.regEndDate != null){
			query +=" AND A.REG_DATE <"+new DBUtil().getToDate(regEndDate);
		}		
		if(!super.orderBy.equals("")){
			query += " "+super.orderBy;
		}
		
		query = super.toPaging(query);
		
		
		List<TrnsctnAcquireBean> list = new ArrayList<TrnsctnAcquireBean>();
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
				TrnsctnAcquireBean trnsctnacquireBean = new TrnsctnAcquireBean();
				trnsctnacquireBean.setIdx(rset.getLong("IDX"));
				trnsctnacquireBean.setTransactionId(rset.getString("TRANSACTION_ID"));
				trnsctnacquireBean.setStatus(rset.getString("STATUS"));
				trnsctnacquireBean.setAcquireDate(rset.getString("ACQUIRE_DATE"));
				trnsctnacquireBean.setAmount(rset.getDouble("AMOUNT"));
				trnsctnacquireBean.setCardErrCd(rset.getString("CARD_ERROR_CD"));
				trnsctnacquireBean.setVanErrCd(rset.getString("VAN_ERROR_CD"));
				trnsctnacquireBean.setTemp(rset.getString("TEMP"));
				trnsctnacquireBean.setRegDate(rset.getTimestamp("REG_DATE"));
				trnsctnacquireBean.setTemp1String(rset.getString("MERCHANT_ID"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(trnsctnacquireBean);
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;

	}
	
	public boolean insert(TrnsctnAcquireBean trnsctnacquireBean){
		
		String query = "INSERT INTO TB_TRNSCTN_ACQUIRE (IDX, TRANSACTION_ID, STATUS, ACQUIRE_DATE, AMOUNT, "
			         + "CARD_ERROR_CD, VAN_ERROR_CD, TEMP, VAN, REG_DATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		
		try{
			trnsctnacquireBean.setIdx(SequenceDAO.getSequenceLong("SEQ_TRNSCTN_ACQUIRE"));
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setLong(idx++, trnsctnacquireBean.getIdx());
			pstmt.setString(idx++, trnsctnacquireBean.getTransactionId());
			pstmt.setString(idx++, trnsctnacquireBean.getStatus());
			pstmt.setString(idx++, trnsctnacquireBean.getAcquireDate());
			pstmt.setDouble(idx++, trnsctnacquireBean.getAmount());
			pstmt.setString(idx++, trnsctnacquireBean.getCardErrCd());
			pstmt.setString(idx++, trnsctnacquireBean.getVanErrCd());
			pstmt.setString(idx++, trnsctnacquireBean.getTemp());
			pstmt.setString(idx++, trnsctnacquireBean.getVan());

			result = pstmt.executeUpdate();
			conn.commit();			
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(trnsctnacquireBean),this);
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
	
	public boolean update(TrnsctnAcquireBean trnsctnacquireBean){
		
		String query = "UPDATE TB_TRNSCTN_ACQUIRE SET TRANSACTION_ID=?, STATUS=?, ACQUIRE_DATE=?, AMOUNT=?, "
			         + "CARD_ERROR_CD=?, VAN_ERROR_CD=?, TEMP=?, VAN=? WHERE IDX=?";
		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;		
		
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setString(idx++, trnsctnacquireBean.getTransactionId());
			pstmt.setString(idx++, trnsctnacquireBean.getStatus());
			pstmt.setString(idx++, trnsctnacquireBean.getAcquireDate());
			pstmt.setDouble(idx++, trnsctnacquireBean.getAmount());
			pstmt.setString(idx++, trnsctnacquireBean.getCardErrCd());
			pstmt.setString(idx++, trnsctnacquireBean.getVanErrCd());
			pstmt.setString(idx++, trnsctnacquireBean.getTemp());
			pstmt.setString(idx++, trnsctnacquireBean.getVan());			
			pstmt.setLong(idx++, trnsctnacquireBean.getIdx());
			result = pstmt.executeUpdate();
			conn.commit();			

		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(trnsctnacquireBean),this);
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
	
	
}

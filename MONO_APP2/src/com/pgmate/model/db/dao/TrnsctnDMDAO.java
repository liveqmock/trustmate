/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.TrnsctnCBDAO.java
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

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.BeanUtil;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.TrnsctnDMBean;
import com.pgmate.model.db.factory.DBFactory;
import com.pgmate.model.db.factory.DBUtil;

public class TrnsctnDMDAO extends DAO {
	
	

	public TrnsctnDMDAO() {
		// TODO Auto-generated constructor stub
	}

	public TrnsctnDMBean getByTransactionId(String transactionId){
		List<TrnsctnDMBean> list = get(" AND TRANSACTION_ID = '"+ transactionId +"'");
		if(list.size() == 0){
			return new TrnsctnDMBean();
		}else{
			return (TrnsctnDMBean)list.get(0);
		}
	}
	
	
	
	public List<TrnsctnDMBean> get(TrnsctnDMBean trnsctnDmBean){
		StringBuffer sb = new StringBuffer();
		
		if(!trnsctnDmBean.getTransactionId().equals("")){
			sb.append(" AND TRANSACTION_ID = '"+ trnsctnDmBean.getTransactionId() +"'");
		}
		if(!trnsctnDmBean.getDecision().equals("")){
			sb.append(" AND DECISION = '"+ trnsctnDmBean.getDecision() +"'");
		}
		if(!trnsctnDmBean.getResultCd().equals("")){
			sb.append(" AND RESULT_CD = '"+ trnsctnDmBean.getResultCd() +"'");
		}
		if(!trnsctnDmBean.getHostCd().equals("")){
			sb.append(" AND HOST_CD = '"+ trnsctnDmBean.getHostCd() +"'");
		}
		if(!trnsctnDmBean.getDmNo().equals("")){
			sb.append(" AND DM_NO = '"+ trnsctnDmBean.getDmNo() +"'");
		}
		if(trnsctnDmBean.getFraudScore() > 0){
			sb.append(" AND FRAUD_SCORE > "+ trnsctnDmBean.getFraudScore());
		}
		
		return get(sb.toString());
	
	}
	
	
	public List<TrnsctnDMBean> get(String subQuery){
		String query = "SELECT TRANSACTION_ID,  EKEY,  EHASH,  ADDR1,  ADDR2,  CITY,  STATE,  COUNTRY,  ZIP,  RESULT_CD,  RESULT_MSG,  HOST_CD,"
						+" DECISION,  DM_NO,  FRAUD_SCORE,  REQ_DATE,  REG_DATE FROM TB_TRNSCTN_DM WHERE 1=1 " + subQuery;
		
		if(super.regStartDate != null){
			query +=" AND REG_DATE >= "+new DBUtil().getToDate(regStartDate);
		}
		if(super.regEndDate != null){
			query +=" AND REG_DATE < "+new DBUtil().getToDate(regEndDate);
		}		
		if(!super.orderBy.equals("")){
			query += " "+super.orderBy;
		}
		
		query = super.toPaging(query);
		
		
		List<TrnsctnDMBean> list = new ArrayList<TrnsctnDMBean>();
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
				TrnsctnDMBean trnsctnDmBean = new TrnsctnDMBean();
				
				trnsctnDmBean.setTransactionId(rset.getString("TRANSACTION_ID"));
				trnsctnDmBean.setEkey(rset.getString("EKEY"));
				trnsctnDmBean.setEhash(rset.getString("EHASH"));
				trnsctnDmBean.setAddr1(rset.getString("ADDR1"));
				trnsctnDmBean.setAddr2(rset.getString("ADDR2"));
				trnsctnDmBean.setCity(rset.getString("CITY"));
				trnsctnDmBean.setState(rset.getString("STATE"));
				trnsctnDmBean.setCountry(rset.getString("COUNTRY"));
				trnsctnDmBean.setZip(rset.getString("ZIP"));
				trnsctnDmBean.setResultCd(rset.getString("RESULT_CD"));
				trnsctnDmBean.setResultMsg(rset.getString("RESULT_MSG"));
				trnsctnDmBean.setHostCd(rset.getString("HOST_CD"));
				trnsctnDmBean.setDecision(rset.getString("DECISION"));
				trnsctnDmBean.setDmNo(rset.getString("DM_NO"));
				trnsctnDmBean.setFraudScore(rset.getDouble("FRAUD_SCORE"));
				trnsctnDmBean.setReqDate(rset.getTimestamp("REQ_DATE"));
				trnsctnDmBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(trnsctnDmBean);
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;

	}
	
	
	
	
	public boolean insert(TrnsctnDMBean trnsctnDmBean){
		
		String query = "INSERT INTO TB_TRNSCTN_DM (TRANSACTION_ID, EKEY,  EHASH,  ADDR1,  ADDR2,  CITY,  STATE,  COUNTRY,  ZIP,  DM_NO ,REQ_DATE,REG_DATE) "
			         + "VALUES (?, ?, ?, ?, ?, ?, ?, ? ,? ,? ,SYSDATE, SYSDATE)";
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, trnsctnDmBean.getTransactionId());
			pstmt.setString(idx++, trnsctnDmBean.getEkey());
			pstmt.setString(idx++, trnsctnDmBean.getEhash());
			pstmt.setString(idx++, trnsctnDmBean.getAddr1());
			pstmt.setString(idx++, trnsctnDmBean.getAddr2());
			pstmt.setString(idx++, trnsctnDmBean.getCity());
			pstmt.setString(idx++, trnsctnDmBean.getState());
			pstmt.setString(idx++, trnsctnDmBean.getCountry());
			pstmt.setString(idx++, trnsctnDmBean.getZip());
			pstmt.setString(idx++, trnsctnDmBean.getDmNo());

			result = pstmt.executeUpdate();
			conn.commit();			
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(trnsctnDmBean),this);
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
	
	public boolean update(TrnsctnDMBean trnsctnDmBean){
		
		String query = "UPDATE TB_TRNSCTN_DM SET RESULT_CD =?, RESULT_MSG=?,HOST_CD=?,DECISION=?,DM_NO=?, FRAUD_SCORE=?,REG_DATE =SYSDATE "
			         + " WHERE TRANSACTION_ID=?";
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;		
		
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setString(idx++, trnsctnDmBean.getResultCd());
			pstmt.setString(idx++, trnsctnDmBean.getResultMsg());
			pstmt.setString(idx++, trnsctnDmBean.getHostCd());
			pstmt.setString(idx++, trnsctnDmBean.getDecision());
			pstmt.setString(idx++, trnsctnDmBean.getDmNo());
			pstmt.setDouble(idx++, trnsctnDmBean.getFraudScore());
			pstmt.setString(idx++, trnsctnDmBean.getTransactionId());
			result = pstmt.executeUpdate();
			conn.commit();			

		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(trnsctnDmBean),this);
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

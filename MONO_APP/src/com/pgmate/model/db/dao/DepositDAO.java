/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.DepositDAO.java
 * Date	        : Feb 11, 2009
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

import com.pgmate.model.db.DepositBean;

public class DepositDAO extends DAO {
	
	public DepositDAO(){
		
	}
	
	public DepositBean getById(String merchantId){
		List<DepositBean> list = get(" AND MERCHANT_ID='"+merchantId+"'");
		if(list.size() == 0){
			return new DepositBean();
		}else{
			return (DepositBean)list.get(0);
		}	
	}
	
	public DepositBean getByMerchant(String merchantId){
		List<DepositBean> list = getMerchant(" AND MERCHANT_ID='"+merchantId+"'");
		if(list.size() == 0){
			return new DepositBean();
		}else{
			return (DepositBean)list.get(0);
		}	
	}
	
	public DepositBean getByMerchantPayDate(String merchantId,String payDate,String payYn){
		List<DepositBean> list = getMerchant(" AND MERCHANT_ID='"+merchantId+"' AND PAY_DATE='"+payDate+"' AND PAY_YN='"+payYn+"'");
		if(list.size() == 0){
			return new DepositBean();
		}else{
			return (DepositBean)list.get(0);
		}	
	}
	
	public DepositBean getByIdx(long idx){
		List<DepositBean> list = get(" AND IDX="+CommonUtil.toString(idx));
		if(list.size() == 0){
			return new DepositBean();
		}else{
			return (DepositBean)list.get(0);
		}	
	}
	
	public List<DepositBean> get(DepositBean dBean){
		StringBuffer sb = new StringBuffer();
		if(!dBean.getMerchantId().equals("")){
			return get(" AND MERCHANT_ID='"+dBean.getMerchantId()+"'");
		}
		
		return get(sb.toString());
	}
	
	public List<DepositBean> get(String subQuery){
		
		String query = "SELECT IDX, MERCHANT_ID, CURRENT_RATE, LAST_AMOUNT, CURR_AMOUNT,PAY_AMOUNT, TOTAL_AMOUNT,PAY_DATE,PAY_IDX,PAY_YN, REG_DATE "
			+" FROM VW_DEPOSIT WHERE IDX IS NOT NULL " + subQuery;
		
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
		
		List<DepositBean> list = new ArrayList<DepositBean>();
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
				DepositBean dBean = new DepositBean();
				dBean.setIdx(rset.getLong("IDX"));
				dBean.setMerchantId(rset.getString("MERCHANT_ID"));
				dBean.setCurrentRate(rset.getDouble("CURRENT_RATE"));
				dBean.setLastAmount(rset.getDouble("LAST_AMOUNT"));
				dBean.setCurrAmount(rset.getDouble("CURR_AMOUNT"));
				dBean.setPayAmount(rset.getDouble("PAY_AMOUNT"));
				dBean.setTotalAmount(rset.getDouble("TOTAL_AMOUNT"));
				dBean.setPayDate(rset.getString("PAY_DATE"));
				dBean.setPayIdx(rset.getLong("PAY_IDX"));
				dBean.setPayYn(rset.getString("PAY_YN"));
				dBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(dBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
public List<DepositBean> getMerchant(String subQuery){
		
		String query = "SELECT IDX, MERCHANT_ID, CURRENT_RATE, LAST_AMOUNT, CURR_AMOUNT,PAY_AMOUNT, TOTAL_AMOUNT,PAY_DATE,PAY_IDX,PAY_YN, REG_DATE FROM "
			+" (SELECT IDX, MERCHANT_ID, CURRENT_RATE, LAST_AMOUNT, CURR_AMOUNT, PAY_AMOUNT, TOTAL_AMOUNT, PAY_DATE,PAY_IDX,PAY_YN, REG_DATE FROM VW_DEPOSIT WHERE IDX IS NOT NULL " 
			+ subQuery + "ORDER BY REG_DATE DESC  ) WHERE ROWNUM = 1 ";
		
		query = super.toPaging(query);
		
		List<DepositBean> list = new ArrayList<DepositBean>();
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
				DepositBean dBean = new DepositBean();
				dBean.setIdx(rset.getLong("IDX"));
				dBean.setMerchantId(rset.getString("MERCHANT_ID"));
				dBean.setCurrentRate(rset.getDouble("CURRENT_RATE"));
				dBean.setLastAmount(rset.getDouble("LAST_AMOUNT"));
				dBean.setCurrAmount(rset.getDouble("CURR_AMOUNT"));
				dBean.setPayAmount(rset.getDouble("PAY_AMOUNT"));
				dBean.setTotalAmount(rset.getDouble("TOTAL_AMOUNT"));
				dBean.setPayDate(rset.getString("PAY_DATE"));
				dBean.setPayIdx(rset.getLong("PAY_IDX"));
				dBean.setPayYn(rset.getString("PAY_YN"));
				dBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(dBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public boolean update(DepositBean dBean){
		String query = "UPDATE TB_DEPOSIT SET CURRENT_RATE = ?, LAST_AMOUNT = ?, CURR_AMOUNT = ?, PAY_AMOUNT=?,TOTAL_AMOUNT = ?,PAY_DATE=?,PAY_IDX=?,PAY_YN=? WHERE IDX = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setDouble(idx++, dBean.getCurrentRate());
			pstmt.setDouble(idx++, dBean.getLastAmount());
			pstmt.setDouble(idx++, dBean.getCurrAmount());
			pstmt.setDouble(idx++, dBean.getPayAmount());
			pstmt.setDouble(idx++, dBean.getTotalAmount());
			pstmt.setString(idx++, dBean.getPayDate());
			pstmt.setLong(idx++, dBean.getPayIdx());
			pstmt.setString(idx++, dBean.getPayYn());
			pstmt.setLong(idx++, dBean.getIdx());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(dBean),this);
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
	
	public boolean insert(DepositBean dBean){
		String query = "INSERT INTO TB_DEPOSIT ( IDX, MERCHANT_ID, CURRENT_RATE, LAST_AMOUNT, CURR_AMOUNT, PAY_AMOUNT,TOTAL_AMOUNT,PAY_DATE,PAY_IDX,PAY_YN, REG_DATE ) "
			         + " VALUES ( ?, ?, ?, ?, ?, ?,?,?,?,?, SYSDATE )";
 
				
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			dBean.setIdx(SequenceDAO.getSequenceLong("SEQ_DEPOSIT"));
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setLong(idx++, dBean.getIdx());
			pstmt.setString(idx++, dBean.getMerchantId());
			pstmt.setDouble(idx++, dBean.getCurrentRate());
			pstmt.setDouble(idx++, dBean.getLastAmount());
			pstmt.setDouble(idx++, dBean.getCurrAmount());
			pstmt.setDouble(idx++, dBean.getPayAmount());
			pstmt.setDouble(idx++, dBean.getTotalAmount());
			pstmt.setString(idx++, dBean.getPayDate());
			pstmt.setLong(idx++, dBean.getPayIdx());
			pstmt.setString(idx++, dBean.getPayYn());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(dBean),this);
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

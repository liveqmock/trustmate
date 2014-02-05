/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.SettleInfoDAO.java
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

import com.pgmate.model.db.SettleInfoBean;

public class SettleInfoDAO extends DAO {
	
	public SettleInfoDAO(){
		
	}
	
	public SettleInfoBean getById(String merchantId){
		List<SettleInfoBean> list = get(" AND MERCHANT_ID='"+merchantId+"'");
		if(list.size() == 0){
			return new SettleInfoBean();
		}else{
			return (SettleInfoBean)list.get(0);
		}	
	}
	
	public SettleInfoBean getByIdx(long idx){
		List<SettleInfoBean> list = get(" AND IDX="+CommonUtil.toString(idx));
		if(list.size() == 0){
			return new SettleInfoBean();
		}else{
			return (SettleInfoBean)list.get(0);
		}	
	}
	
	public List<SettleInfoBean> getBySettleIdx(String settleIdx){
		String subQuery = " AND SETTLE_IDX = '"+settleIdx+"' ";
		return get(subQuery);
	}
	
	public List<SettleInfoBean> getByInvoiceIdx(String invoiceIdx){
		String subQuery = " AND INVOICE_IDX = '"+invoiceIdx+"' ";
		return get(subQuery);
	}
	
	public List<SettleInfoBean> get(SettleInfoBean siBean){
		StringBuffer sb = new StringBuffer();
		if(!siBean.getMerchantId().equals("")){
			sb.append(" AND MERCHANT_ID='"+siBean.getMerchantId()+"'");
		}
		if(!siBean.getSettleType().equals("")){
			sb.append(" AND SETTLE_TYPE='"+siBean.getSettleType()+"' ");
		}
		if(!siBean.getSettleYn().equals("")){
			sb.append(" AND SETTLE_YN='"+siBean.getSettleYn()+"' ");
		}
		
		return get(sb.toString());
	}
	
	public List<SettleInfoBean> get(String subQuery){
		
		String query = "SELECT IDX, MERCHANT_ID, INVOICE_IDX,SETTLE_IDX, SETTLE_TYPE, START_DATE, END_DATE, TOT_COUNT, AMOUNT, SETTLE_YN, REG_DATE "
			+" FROM VW_SETTLE_INFO WHERE IDX IS NOT NULL " + subQuery;
		
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
		
		List<SettleInfoBean> list = new ArrayList<SettleInfoBean>();
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
				SettleInfoBean siBean = new SettleInfoBean();
				siBean.setIdx(rset.getLong("IDX"));
				siBean.setMerchantId(rset.getString("MERCHANT_ID"));
				siBean.setInvoiceIdx(rset.getString("INVOICE_IDX"));
				siBean.setSettleIdx(rset.getString("SETTLE_IDX"));
				siBean.setSettleType(rset.getString("SETTLE_TYPE"));
				siBean.setStartDate(rset.getString("START_DATE"));
				siBean.setEndDate(rset.getString("END_DATE"));
				siBean.setTotalCount(rset.getDouble("TOT_COUNT"));
				siBean.setAmount(rset.getDouble("AMOUNT"));
				siBean.setSettleYn(rset.getString("SETTLE_YN"));
				siBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(siBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public boolean settleYnUpdate(String invoiceIdx, String settleYn){
		String query = "UPDATE TB_SETTLE_INFO SET SETTLE_YN = '"+settleYn+"' WHERE INVOICE_IDX = '"+invoiceIdx+"'";

		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql","SettleIdx["+invoiceIdx+"] SettleYn["+settleYn+"]",this);
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
	
	
	public boolean update(SettleInfoBean siBean){
		String query = "UPDATE TB_SETTLE_INFO SET SETTLE_IDX = ?, SETTLE_TYPE = ?, START_DATE = ?, END_DATE = ?, "
			+" TOT_COUNT = ?, AMOUNT=?, SETTLE_YN = ? WHERE IDX = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, siBean.getSettleIdx());
			pstmt.setString(idx++, siBean.getSettleType());
			pstmt.setString(idx++, siBean.getStartDate());
			pstmt.setString(idx++, siBean.getEndDate());
			pstmt.setDouble(idx++, siBean.getTotalCount());
			pstmt.setDouble(idx++, siBean.getAmount());
			pstmt.setString(idx++, siBean.getSettleYn());
			pstmt.setLong(idx++, siBean.getIdx());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(siBean),this);
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
	
	public boolean insert(SettleInfoBean siBean){
		String query = "INSERT INTO TB_SETTLE_INFO ( IDX, MERCHANT_ID, INVOICE_IDX,SETTLE_IDX, SETTLE_TYPE, START_DATE, END_DATE, TOT_COUNT, AMOUNT, SETTLE_YN, REG_DATE ) "
			+" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE )";
 
		 
				
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			siBean.setIdx(SequenceDAO.getSequenceLong("SEQ_SETTLE_INFO"));
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setLong(idx++, siBean.getIdx());
			pstmt.setString(idx++, siBean.getMerchantId());
			pstmt.setString(idx++, siBean.getInvoiceIdx());
			pstmt.setString(idx++, siBean.getSettleIdx());
			pstmt.setString(idx++, siBean.getSettleType());
			pstmt.setString(idx++, siBean.getStartDate());
			pstmt.setString(idx++, siBean.getEndDate());
			pstmt.setDouble(idx++, siBean.getTotalCount());
			pstmt.setDouble(idx++, siBean.getAmount());
			pstmt.setString(idx++, siBean.getSettleYn());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(siBean),this);
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

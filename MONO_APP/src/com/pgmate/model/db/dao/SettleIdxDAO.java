/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.SettleIdxDAO.java
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
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.BeanUtil;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.SettleIdxBean;

public class SettleIdxDAO extends DAO {
	
	public SettleIdxDAO(){
		
	}
	
	public SettleIdxBean getById(String merchantId){
		List<SettleIdxBean> list = get(" AND MERCHANT_ID='"+merchantId+"'");
		if(list.size() == 0){
			return new SettleIdxBean();
		}else{
			return (SettleIdxBean)list.get(0);
		}	
	}
	
	public SettleIdxBean getBySettleIdx(String settleIdx){
		List<SettleIdxBean> list = get(" AND SETTLE_IDX='"+settleIdx+"'");
		if(list.size() == 0){
			return new SettleIdxBean();
		}else{
			return (SettleIdxBean)list.get(0);
		}	
	}
	
	public SettleIdxBean getByTransactionId(String transactionId){
		List<SettleIdxBean> list = get(" AND TRANSACTION_ID='"+transactionId+"'");
		if(list.size() == 0){
			return new SettleIdxBean();
		}else{
			return (SettleIdxBean)list.get(0);
		}	
	}
	
	public List<SettleIdxBean> get(SettleIdxBean sBean){
		StringBuffer sb = new StringBuffer();
		if(!sBean.getMerchantId().equals("")){
			return get(" AND MERCHANT_ID='"+sBean.getMerchantId()+"'");
		}
		if(!sBean.getSettleIdx().equals("")){
			sb.append(" AND SETTLE_IDX='"+sBean.getSettleIdx()+"' ");
		}
		if(!sBean.getTransactionId().equals("")){
			sb.append(" AND TRANSACTION_ID='"+sBean.getTransactionId()+"' ");
		}
		
		return get(sb.toString());
	}
	
	public List<SettleIdxBean> get(String subQuery){
		
		String query = "SELECT MERCHANT_ID, SETTLE_IDX,SETTLE_TYPE TRANSACTION_ID FROM VW_SETTLE_IDX WHERE IDX IS NOT NULL " + subQuery;
		
		query = super.toPaging(query);
		
		List<SettleIdxBean> list = new ArrayList<SettleIdxBean>();
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
				SettleIdxBean sBean = new SettleIdxBean();
				sBean.setMerchantId(rset.getString("MERCHANT_ID"));
				sBean.setSettleIdx(rset.getString("SETTLE_IDX"));
				sBean.setSettleType(rset.getString("SETTLE_TYPE"));
				sBean.setTransactionId(rset.getString("TRANSACTION_ID"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(sBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	
	public boolean update(SettleIdxBean sBean){
		String query = "UPDATE TB_SETTLE_IDX SET SETTLE_IDX = ?, TRANSACTION_ID = ? WHERE MERCHANT_ID = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, sBean.getSettleIdx());
			pstmt.setString(idx++, sBean.getTransactionId());
			pstmt.setString(idx++, sBean.getMerchantId());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(sBean),this);
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
	
	public boolean insert(SettleIdxBean sBean){
		String query = "INSERT INTO TB_SETTLE_IDX ( MERCHANT_ID, SETTLE_IDX, SETTLE_TYPE,TRANSACTION_ID ) VALUES ( ?,?,?,? )";
 
				
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setString(idx++, sBean.getMerchantId());
			pstmt.setString(idx++, sBean.getSettleIdx());
			pstmt.setString(idx++, sBean.getSettleType());
			pstmt.setString(idx++, sBean.getTransactionId());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(sBean),this);
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

/* 
 * Project      : PG_APP
 * File Name    : net.pgmate.model.db.dao.TrnsctnLogDAO.java
 * Date	        : Dec 23, 2008
 * Version      : 2.0
 * Author       : ginaida@ginaida.net
 * Comment      :  
 */

package com.pgmate.model.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.pgmate.model.db.TrnsctnLogBean;


import com.pgmate.model.db.factory.DBFactory;
import com.pgmate.model.db.factory.DBUtil;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.BeanUtil;
import biz.trustnet.common.util.CommonUtil;

public class TrnsctnLogDAO extends DAO {

	public TrnsctnLogDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public List<String> getServiceType(){
		String query = "SELECT SERVICE_TYPE FROM VW_TRNSCTN_LOG WHERE IDX IS NOT NULL GROUP BY SERVICE_TYPE";
		
		List<String> list = new ArrayList<String>();
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
				list.add(rset.getString("SERVICE_TYPE"));
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	
	public List<TrnsctnLogBean> getTrnsctnLogByMerchantId(String merchantId){
		return getTrnsctnLog(" AND MERCHANT_ID ='"+merchantId+"'");
	}
	
	public TrnsctnLogBean getTrnsctnLogByIdx(String idx){
		List<TrnsctnLogBean> list = getTrnsctnLog(" AND IDX ="+idx);
		if(list.size() == 0){
			return new TrnsctnLogBean();
		}else{
			return (TrnsctnLogBean)list.get(0);
		}	
	}
	
	public TrnsctnLogBean getTrnsctnLogByReqData(String reqData){
		List<TrnsctnLogBean> list = getTrnsctnLog(" AND  REQ_DATA = '"+reqData+"'");
		if(list.size() == 0){
			return new TrnsctnLogBean();
		}else{
			return (TrnsctnLogBean)list.get(0);
		}	
	}
	
	public List<TrnsctnLogBean> getTrnsctnLog(TrnsctnLogBean trnsctnLogBean){
		
		if(trnsctnLogBean.getIdx() !=0){
			return getTrnsctnLog("AND IDX ="+CommonUtil.toString(trnsctnLogBean.getIdx()));
		}else{
			StringBuffer sb = new StringBuffer();
			
			if(!trnsctnLogBean.getMerchantId().equals("")){
				sb.append(" AND MERCHANT_ID ='"+trnsctnLogBean.getMerchantId()+"' ");
			}
			
			if(!trnsctnLogBean.getServiceType().equals("")){
				sb.append(" AND SERVICE_TYPE ='"+trnsctnLogBean.getServiceType()+"'");
			}
			
			if(!trnsctnLogBean.getTransactionId().equals("")){
				sb.append(" AND TRANSACTION_ID ='"+trnsctnLogBean.getTransactionId()+"'");
			}
			
			if(!trnsctnLogBean.getIpAddress().equals("")){
				sb.append(" AND IPADDRESS ='"+trnsctnLogBean.getIpAddress()+"'");
			}
			
			if(!trnsctnLogBean.getStatus().equals("")){
				sb.append(" AND STATUS ='"+trnsctnLogBean.getStatus()+"'");
			}
			
			if(!trnsctnLogBean.getReqData().equals("")){
				sb.append(" AND REQ_DATA like '%"+trnsctnLogBean.getReqData()+"%'");
			}
			
			return getTrnsctnLog(sb.toString());
			
		}
	}
	
	public List<TrnsctnLogBean> getTrnsctnLog(String subQuery){
		String query = "SELECT IDX,MERCHANT_ID,SERVICE_TYPE,TRANSACTION_ID,IPADDRESS,REQ_DATA,RES_DATA,STATUS, REG_DATE FROM VW_TRNSCTN_LOG WHERE IDX IS NOT NULL " + subQuery;
		
		if(super.regStartDate != null){
			query +=" AND REG_DATE >="+new DBUtil().getToDate(regStartDate);
		}
		if(super.regEndDate != null){
			query +=" AND REG_DATE <="+new DBUtil().getToDate(regEndDate);
		}		
		if(!super.orderBy.equals("")){
			query += " "+super.orderBy;
		}
		
		query = super.toPaging(query);
		
		List<TrnsctnLogBean> list = new ArrayList<TrnsctnLogBean>();
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
				TrnsctnLogBean trnsctnLogBean = new TrnsctnLogBean();
				trnsctnLogBean.setIdx(rset.getLong("IDX"));
				trnsctnLogBean.setMerchantId(rset.getString("MERCHANT_ID"));
				trnsctnLogBean.setServiceType(rset.getString("SERVICE_TYPE"));
				trnsctnLogBean.setTransactionId(rset.getString("TRANSACTION_ID"));
				trnsctnLogBean.setIpAddress(rset.getString("IPADDRESS"));
				trnsctnLogBean.setReqData(rset.getString("REQ_DATA"));
				trnsctnLogBean.setResData(rset.getString("RES_DATA"));
				trnsctnLogBean.setStatus(rset.getString("STATUS"));
				trnsctnLogBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(trnsctnLogBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public boolean updateTrnsctnLog(TrnsctnLogBean trnsctnLogBean){
		String query = "UPDATE TB_TRNSCTN_LOG SET TRANSACTION_ID = ?,IPADDRESS = ?, RES_DATA = ?,STATUS =? WHERE IDX =?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, trnsctnLogBean.getTransactionId());
			pstmt.setString(idx++, trnsctnLogBean.getIpAddress());
			pstmt.setString(idx++, trnsctnLogBean.getResData());
			pstmt.setString(idx++, trnsctnLogBean.getStatus());
			pstmt.setLong(idx++, trnsctnLogBean.getIdx());
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(trnsctnLogBean),this);
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
	
	public boolean insertTrnsctnLog(TrnsctnLogBean trnsctnLogBean){
		String query = "INSERT INTO TB_TRNSCTN_LOG ( IDX, MERCHANT_ID, SERVICE_TYPE, TRANSACTION_ID, IPADDRESS, "
			+" REQ_DATA, RES_DATA, STATUS,REG_DATE) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,SYSDATE )";
	
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			trnsctnLogBean.setIdx(SequenceDAO.getSequenceLong("SEQ_TRNSCTN_LOG"));
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setLong(idx++, trnsctnLogBean.getIdx());
			pstmt.setString(idx++, trnsctnLogBean.getMerchantId());
			pstmt.setString(idx++, trnsctnLogBean.getServiceType());
			pstmt.setString(idx++, trnsctnLogBean.getTransactionId());
			pstmt.setString(idx++, CommonUtil.cut(trnsctnLogBean.getIpAddress(),30));
			pstmt.setString(idx++, CommonUtil.cut(trnsctnLogBean.getReqData(),600));
			pstmt.setString(idx++, CommonUtil.cut(trnsctnLogBean.getResData(),600));
			pstmt.setString(idx++, trnsctnLogBean.getStatus());

			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(trnsctnLogBean),this);
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

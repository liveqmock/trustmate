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


import com.pgmate.model.db.factory.DBFactory;
import com.pgmate.model.db.factory.DBUtil;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.BeanUtil;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.TrnsctnCBBean;

public class TrnsctnCBDAO extends DAO {
	
	public double sumAmount = 0;

	public TrnsctnCBDAO() {
		// TODO Auto-generated constructor stub
	}

	public double getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(double sumAmount) {
		this.sumAmount = sumAmount;
	}

	public TrnsctnCBBean getByIdx(String idx){
		List<TrnsctnCBBean> list = get(" AND IDX ="+idx);
		if(list.size() == 0){
			return new TrnsctnCBBean();
		}else{
			return (TrnsctnCBBean)list.get(0);
		}
	}

	public TrnsctnCBBean getByTransactionId(String transactionId){
		List<TrnsctnCBBean> list = get(" AND TRANSACTION_ID = '"+ transactionId +"'");
		if(list.size() == 0){
			return new TrnsctnCBBean();
		}else{
			return (TrnsctnCBBean)list.get(0);
		}
	}
	
	public TrnsctnCBBean getViewByTransactionId(String transactionId){
		List<TrnsctnCBBean> list = getView(" AND A.TRANSACTION_ID = '"+ transactionId +"'");
		if(list.size() == 0){
			return new TrnsctnCBBean();
		}else{
			return (TrnsctnCBBean)list.get(0);
		}
	}
	
	public List<TrnsctnCBBean> get(TrnsctnCBBean trnsctncbBean){
		StringBuffer sb = new StringBuffer();
		
		if(trnsctncbBean.getIdx() != 0){
			return get(" AND IDX =" + CommonUtil.toString(trnsctncbBean.getIdx()));
		}
		if(!trnsctncbBean.getTransactionId().equals("")){
			sb.append(" AND TRANSACTION_ID = '"+ trnsctncbBean.getTransactionId() +"'");
		}
		if(!trnsctncbBean.getCbState().equals("")){
			sb.append(" AND CB_STATE = '"+ trnsctncbBean.getCbState() +"'");
		}
		if(!trnsctncbBean.getRootTrnStatus().equals("")){
			sb.append(" AND ROOT_TRN_STATE = '"+ trnsctncbBean.getRootTrnStatus() +"'");
		}
		if(!trnsctncbBean.getFlag().equals("")){
			sb.append(" AND FLAG = '"+ trnsctncbBean.getFlag() +"'");
		}
		if(trnsctncbBean.getCommentIdx() != 0){
			sb.append(" AND COMMENT_IDX = '"+ CommonUtil.toString(trnsctncbBean.getCommentIdx()) +"'");
		}
		
		return get(sb.toString());
	
	}
	
	public List<TrnsctnCBBean> getView(TrnsctnCBBean trnsctncbBean){
		StringBuffer sb = new StringBuffer();
		
		if(trnsctncbBean.getIdx() != 0){
			return getView(" AND A.IDX = '" + CommonUtil.toString(trnsctncbBean.getIdx()) +"'");
		}
		if(!trnsctncbBean.getTransactionId().equals("")){
			sb.append(" AND A.TRANSACTION_ID = '"+ trnsctncbBean.getTransactionId() +"'");
		}
		if(!trnsctncbBean.getTemp1String().equals("")){
			sb.append(" AND B.MERCHANT_ID = '"+ trnsctncbBean.getTemp1String() +"'");
		}
		if(!trnsctncbBean.getCbState().equals("")){
			sb.append(" AND A.CB_STATE = '"+ trnsctncbBean.getCbState() +"'");
		}
		if(!trnsctncbBean.getRootTrnStatus().equals("")){
			sb.append(" AND A.ROOT_TRN_STATE = '"+ trnsctncbBean.getRootTrnStatus() +"'");
		}
		if(!trnsctncbBean.getFlag().equals("")){
			sb.append(" AND A.FLAG = '"+ trnsctncbBean.getFlag() +"'");
		}
		if(trnsctncbBean.getCommentIdx() != 0){
			sb.append(" AND A.COMMENT_IDX = '"+ CommonUtil.toString(trnsctncbBean.getCommentIdx()) +"'");
		}
		
		return getView(sb.toString());
	
	}
	
	public List<TrnsctnCBBean> getMerchantTrnsctncbView(TrnsctnCBBean trnsctncbBean, String merchantId){  //merchant CB View
		StringBuffer sb = new StringBuffer();
		
		if(trnsctncbBean.getIdx() != 0){
			return getView(" AND A.IDX = '" + CommonUtil.toString(trnsctncbBean.getIdx()) +"'");
		}
		if(!trnsctncbBean.getTransactionId().equals("")){
			sb.append(" AND A.TRANSACTION_ID = '"+ trnsctncbBean.getTransactionId() +"'");
		}
		if(!trnsctncbBean.getCbState().equals("")){
			sb.append(" AND A.CB_STATE = '"+ trnsctncbBean.getCbState() +"'");
		}
		if(!trnsctncbBean.getRootTrnStatus().equals("")){
			sb.append(" AND A.ROOT_TRN_STATE = '"+ trnsctncbBean.getRootTrnStatus() +"'");
		}
		if(!trnsctncbBean.getFlag().equals("")){
			sb.append(" AND A.FLAG = '"+ trnsctncbBean.getFlag() +"'");
		}
		if(trnsctncbBean.getCommentIdx() != 0){
			sb.append(" AND A.COMMENT_IDX = '"+ CommonUtil.toString(trnsctncbBean.getCommentIdx()) +"'");
		}
		if(!merchantId.equals("")){
			sb.append(" AND B.MERCHANT_ID = '"+merchantId+"' ");
		}
		
		
		return getView(sb.toString());
	
	}
	
	public List<TrnsctnCBBean> get(String subQuery){
		String query = "SELECT IDX, TRANSACTION_ID, CB_STATE, ROOT_TRN_STATE, FLAG, COMMENT_IDX, REG_DATE "
			         + "FROM VW_TRNSCTN_CB WHERE IDX IS NOT NULL " + subQuery;
		
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
		
		
		List<TrnsctnCBBean> list = new ArrayList<TrnsctnCBBean>();
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
				TrnsctnCBBean trnsctncbBean = new TrnsctnCBBean();
				trnsctncbBean.setIdx(rset.getLong("IDX"));
				trnsctncbBean.setTransactionId(rset.getString("TRANSACTION_ID"));
				trnsctncbBean.setCbState(rset.getString("CB_STATE"));
				trnsctncbBean.setRootTrnStatus(rset.getString("ROOT_TRN_STATE"));
				trnsctncbBean.setFlag(rset.getString("FLAG"));
				trnsctncbBean.setCommentIdx(rset.getLong("COMMENT_IDX"));
				trnsctncbBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(trnsctncbBean);
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;

	}
	
	public List<TrnsctnCBBean> getView(String subQuery){
		String query = "SELECT A.IDX, A.TRANSACTION_ID, A.CB_STATE, A.ROOT_TRN_STATE, A.FLAG, B.AMOUNT, A.REG_DATE,B.MERCHANT_ID,B.CUR_TYPE "
			         + " FROM VW_TRNSCTN_CB A, VW_TRNSCTN B "
					 + " WHERE A.IDX IS NOT NULL AND A.TRANSACTION_ID = B.TRANSACTION_ID " + subQuery;
		
		if(super.regStartDate != null){
			query +=" AND A.REG_DATE >="+new DBUtil().getToDate(regStartDate);
		}
		if(super.regEndDate != null){
			query +=" AND A.REG_DATE < "+new DBUtil().getToDate(regEndDate);
		}		
		if(!super.orderBy.equals("")){
			query += " "+super.orderBy;
		}
		
		query = super.toPaging(query);
		
		
		List<TrnsctnCBBean> list = new ArrayList<TrnsctnCBBean>();
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
				TrnsctnCBBean trnsctncbBean = new TrnsctnCBBean();
				trnsctncbBean.setIdx(rset.getLong("IDX"));
				trnsctncbBean.setTransactionId(rset.getString("TRANSACTION_ID"));
				trnsctncbBean.setCbState(rset.getString("CB_STATE"));
				trnsctncbBean.setRootTrnStatus(rset.getString("ROOT_TRN_STATE"));
				trnsctncbBean.setFlag(rset.getString("FLAG"));
				trnsctncbBean.setTemp1Double(rset.getDouble("AMOUNT"));
				trnsctncbBean.setRegDate(rset.getTimestamp("REG_DATE"));
				trnsctncbBean.setTemp1String(rset.getString("MERCHANT_ID"));
				trnsctncbBean.setTemp2String(rset.getString("CUR_TYPE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				sumAmount = sumAmount + trnsctncbBean.getTemp1Double();
				list.add(trnsctncbBean);
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;

	}
	
	
	public boolean insert(TrnsctnCBBean trnsctncbBean){
		
		String query = "INSERT INTO TB_TRNSCTN_CB (IDX, TRANSACTION_ID, CB_STATE, ROOT_TRN_STATE, FLAG, COMMENT_IDX, REG_DATE) "
			         + "VALUES (?, ?, ?, ?, ?, ?, SYSDATE)";
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		
		try{
			trnsctncbBean.setIdx(SequenceDAO.getSequenceLong("SEQ_TRNSCTN_CB"));
			trnsctncbBean.setCommentIdx(SequenceDAO.getSequenceLong("SEQ_COMMENT_TRNSCTN"));
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setLong(idx++, trnsctncbBean.getIdx());
			pstmt.setString(idx++, trnsctncbBean.getTransactionId());
			pstmt.setString(idx++, trnsctncbBean.getCbState());
			pstmt.setString(idx++, trnsctncbBean.getRootTrnStatus());
			pstmt.setString(idx++, trnsctncbBean.getFlag());
			pstmt.setLong(idx++, trnsctncbBean.getCommentIdx());

			result = pstmt.executeUpdate();
			conn.commit();			
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(trnsctncbBean),this);
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
	
	public boolean update(TrnsctnCBBean trnsctncbBean){
		
		String query = "UPDATE TB_TRNSCTN_CB SET CB_STATE=? , FLAG=? "
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
			
			pstmt.setString(idx++, trnsctncbBean.getCbState());
			pstmt.setString(idx++, trnsctncbBean.getFlag());
			pstmt.setString(idx++, trnsctncbBean.getTransactionId());
			result = pstmt.executeUpdate();
			conn.commit();			

		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(trnsctncbBean),this);
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
	
	public boolean updateState(String cbState, String transactionId){
		
		String query = "UPDATE TB_TRNSCTN_CB SET CB_STATE=?, WHERE TRANSACTION_ID=?";
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;		
		
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setString(idx++, cbState);
			pstmt.setString(idx++, transactionId);
			result = pstmt.executeUpdate();
			conn.commit();			

		}catch(Exception e){
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

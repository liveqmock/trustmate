/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.TrnsctnVoidDAO.java
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

import com.pgmate.model.db.TrnsctnVoidBean;
import com.pgmate.payment.util.Comment;
import com.pgmate.payment.util.GSICrypt;

public class TrnsctnVoidDAO extends DAO {
	
	public double sumAmount = 0;

	public TrnsctnVoidDAO() {
		// TODO Auto-generated constructor stub
	}
	
	

	public double getSumAmount() {
		return sumAmount;
	}



	public void setSumAmount(double sumAmount) {
		this.sumAmount = sumAmount;
	}



	public TrnsctnVoidBean getByIdx(String idx){
		List<TrnsctnVoidBean> list = get(" AND IDX = '"+ idx +"'");
		if(list.size() == 0){
			return new TrnsctnVoidBean();
		}else{
			return (TrnsctnVoidBean)list.get(0);
		}
	}
	
	public TrnsctnVoidBean getByTransactionId(String transactionId){
		List<TrnsctnVoidBean> list = get(" AND TRANSACTION_ID = '"+ transactionId +"'");
		if(list.size() == 0){
			return new TrnsctnVoidBean();
		}else{
			return (TrnsctnVoidBean)list.get(0);
		}
	}
	
	public TrnsctnVoidBean getViewByTransactionId(String transactionId){
		List<TrnsctnVoidBean> list = getView(" AND A.TRANSACTION_ID = '"+ transactionId +"'");
		if(list.size() == 0){
			return new TrnsctnVoidBean();
		}else{
			return (TrnsctnVoidBean)list.get(0);
		}
	}
	
	
	public List<TrnsctnVoidBean> get(TrnsctnVoidBean trnsctnvoidBean){
		StringBuffer sb = new StringBuffer();
		
		if(trnsctnvoidBean.getIdx() != 0){
			return get("AND IDX = '" + CommonUtil.toString(trnsctnvoidBean.getIdx()) +"'");
		}
		if(!trnsctnvoidBean.getTransactionId().equals("")){
			sb.append("AND TRANSACTION_ID = '" + trnsctnvoidBean.getTransactionId() +"'");
		}
		if(!trnsctnvoidBean.getVoidTransactionId().equals("")){
			sb.append("AND VOID_TRANSACTION_ID = '"+ trnsctnvoidBean.getVoidTransactionId() +"'");
		}
		if(!trnsctnvoidBean.getVoidVanTransactionId().equals("")){
			sb.append("AND VOID_VAN_TRANSACTION_ID = '"+ trnsctnvoidBean.getVoidVanTransactionId() +"'");
		}
		if(trnsctnvoidBean.getVoidAmount() != 0){
			sb.append("AND VOID_AMOUNT = '"+ CommonUtil.toString(trnsctnvoidBean.getVoidAmount()) +"'");
		}
		if(!trnsctnvoidBean.getVoidApprovalNo().equals("")){
			sb.append("AND VOID_APPROVAL_NO = '"+ trnsctnvoidBean.getVoidApprovalNo() +"'");
		}
		if(!trnsctnvoidBean.getRootTrnStatus().equals("")){
			sb.append("AND ROOT_TRN_STATUS = '"+ trnsctnvoidBean.getRootTrnStatus() +"'");
		}
		if(!trnsctnvoidBean.getStatus().equals("")){
			sb.append("AND STATUS = '"+ trnsctnvoidBean.getStatus() +"'");
		}
		if(!trnsctnvoidBean.getVoidResultCd().equals("")){
			sb.append("AND VOID_RESULT_CD = '"+ trnsctnvoidBean.getVoidResultCd() +"'");
		}
		if(trnsctnvoidBean.getCommentIdx() != 0){
			sb.append("AND COMMENT_IDX = '"+ CommonUtil.toString(trnsctnvoidBean.getCommentIdx()) +"'");
		}
		
		return get(sb.toString());
	
	}
	
	
	
	public List<TrnsctnVoidBean> get(String subQuery){
		String query = "SELECT IDX, TRANSACTION_ID, VOID_TRANSACTION_ID, VOID_VAN_TRANSACTION_ID, VOID_AMOUNT, VOID_APPROVAL_NO, "
			         + "ROOT_TRN_STATUS, STATUS, VOID_RESULT_CD, COMMENT_IDX, VOID_REQ_DATE, VOID_RES_DATE, REG_DATE "
			         + "FROM VW_TRNSCTN_VOID WHERE IDX IS NOT NULL " + subQuery;
		
		if(super.regStartDate != null){
			query +=" AND VOID_REQ_DATE >="+new DBUtil().getToDate(regStartDate);
		}
		if(super.regEndDate != null){
			query +=" AND VOID_REQ_DATE <"+new DBUtil().getToDate(regEndDate);
		}		
		if(!super.orderBy.equals("")){
			query += " "+ "ORDER BY VOID_REQ_DATE DESC";
		}
		
		query = super.toPaging(query);
		
		
		List<TrnsctnVoidBean> list = new ArrayList<TrnsctnVoidBean>();
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
				TrnsctnVoidBean trnsctnvoidBean = new TrnsctnVoidBean();
				trnsctnvoidBean.setIdx(rset.getLong("IDX"));
				trnsctnvoidBean.setTransactionId(rset.getString("TRANSACTION_ID"));
				trnsctnvoidBean.setVoidTransactionId(rset.getString("VOID_TRANSACTION_ID"));
				trnsctnvoidBean.setVoidVanTransactionId(rset.getString("VOID_VAN_TRANSACTION_ID"));
				trnsctnvoidBean.setVoidAmount(rset.getDouble("VOID_AMOUNT"));
				trnsctnvoidBean.setVoidApprovalNo(rset.getString("VOID_APPROVAL_NO"));
				trnsctnvoidBean.setRootTrnStatus(rset.getString("ROOT_TRN_STATUS"));
				trnsctnvoidBean.setStatus(rset.getString("STATUS"));
				trnsctnvoidBean.setVoidResultCd(rset.getString("VOID_RESULT_CD"));
				trnsctnvoidBean.setCommentIdx(rset.getLong("COMMENT_IDX"));
				trnsctnvoidBean.setVoidReqDate(rset.getTimestamp("VOID_REQ_DATE"));
				trnsctnvoidBean.setVoidResDate(rset.getTimestamp("VOID_RES_DATE"));
				trnsctnvoidBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(trnsctnvoidBean);
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;

	}
	
	public List<TrnsctnVoidBean> getView(TrnsctnVoidBean trnsctnvoidBean){
		StringBuffer sb = new StringBuffer();
		
		if(trnsctnvoidBean.getIdx() != 0){
			return get(" AND A.IDX = '" + CommonUtil.toString(trnsctnvoidBean.getIdx()) +"'");
		}
		if(!trnsctnvoidBean.getTransactionId().equals("")){
			sb.append(" AND A.TRANSACTION_ID = '" + trnsctnvoidBean.getTransactionId() +"'");
		}
		if(!trnsctnvoidBean.getVoidTransactionId().equals("")){
			sb.append(" AND A.VOID_TRANSACTION_ID = '"+ trnsctnvoidBean.getVoidTransactionId() +"'");
		}
		if(!trnsctnvoidBean.getVoidVanTransactionId().equals("")){
			sb.append(" AND A.VOID_VAN_TRANSACTION_ID = '"+ trnsctnvoidBean.getVoidVanTransactionId() +"'");
		}
		if(trnsctnvoidBean.getVoidAmount() != 0){
			sb.append(" AND A.VOID_AMOUNT = '"+ CommonUtil.toString(trnsctnvoidBean.getVoidAmount()) +"'");
		}
		if(!trnsctnvoidBean.getVoidApprovalNo().equals("")){
			sb.append(" AND A.VOID_APPROVAL_NO = '"+ trnsctnvoidBean.getVoidApprovalNo() +"'");
		}
		if(!trnsctnvoidBean.getRootTrnStatus().equals("")){
			sb.append(" AND A.ROOT_TRN_STATUS = '"+ trnsctnvoidBean.getRootTrnStatus() +"'");
		}
		if(!trnsctnvoidBean.getStatus().equals("")){
			sb.append(" AND A.STATUS = '"+ trnsctnvoidBean.getStatus() +"'");
		}
		if(!trnsctnvoidBean.getVoidResultCd().equals("")){
			sb.append(" AND A.VOID_RESULT_CD = '"+ trnsctnvoidBean.getVoidResultCd() +"'");
		}
		if(trnsctnvoidBean.getCommentIdx() != 0){
			sb.append(" AND A.COMMENT_IDX = '"+ CommonUtil.toString(trnsctnvoidBean.getCommentIdx()) +"'");
		}
		if(!trnsctnvoidBean.getTemp1String().equals("")){
			sb.append(" AND D.MERCHANT_ID ='"+trnsctnvoidBean.getTemp1String()+"' ");
		}
		if(!trnsctnvoidBean.getTemp5String().equals("")){
			sb.append(" AND D.PAY_NO = '" + trnsctnvoidBean.getTemp5String() + "' ");
		}
		
		if(!trnsctnvoidBean.getPublicAgentId().equals("")){
			sb.append(" AND D.MERCHANT_ID IN (SELECT MERCHANT_ID FROM TB_AGENT_MERCHANT WHERE AGENT_ID='"+trnsctnvoidBean.getPublicAgentId()+"')");
		}
		if(!trnsctnvoidBean.getPublicGroupId().equals("")){
			sb.append(" AND D.MERCHANT_ID IN (SELECT MERCHANT_ID FROM TB_GROUP_MERCHANT WHERE GROUP_ID='"+trnsctnvoidBean.getPublicGroupId()+"')");
		}
		
		return getView(sb.toString());
	
	}
	
	public List<TrnsctnVoidBean> getView(String subQuery){
		GSICrypt crypt = new GSICrypt();
		String query = "SELECT A.IDX, A.VOID_TRANSACTION_ID, A.VOID_REQ_DATE, A.VOID_RES_DATE, A.STATUS, D.MERCHANT_ID , A.VOID_AMOUNT, A.TRANSACTION_ID, C.CARD_NUM, D.VAN, "
					 + "B.COMMENTS, A.COMMENT_IDX, D.PAY_NO , A.ROOT_TRN_STATUS, A.VOID_APPROVAL_NO "
			         + "FROM VW_TRNSCTN_VOID A, VW_COMMENT_TRNSCTN B, VW_TRNSCTN_SCR C, VW_TRNSCTN D "
			         + "WHERE  A.IDX IS NOT NULL AND A.COMMENT_IDX = B.IDX AND A.TRANSACTION_ID = C.TRANSACTION_ID AND A.TRANSACTION_ID = D.TRANSACTION_ID " + subQuery;
		
		if(super.regStartDate != null){
			query +=" AND A.VOID_REQ_DATE >="+new DBUtil().getToDate(regStartDate);
		}
		if(super.regEndDate != null){
			query +=" AND A.VOID_REQ_DATE <"+new DBUtil().getToDate(regEndDate);
		}		
		if(!super.orderBy.equals("")){
			query += " ORDER BY A.VOID_REQ_DATE DESC";
		}
		
		query = super.toPaging(query);
		
		
		List<TrnsctnVoidBean> list = new ArrayList<TrnsctnVoidBean>();
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
				TrnsctnVoidBean trnsctnvoidBean = new TrnsctnVoidBean();
				trnsctnvoidBean.setIdx(rset.getLong("IDX"));
				trnsctnvoidBean.setVoidTransactionId(rset.getString("VOID_TRANSACTION_ID"));
				trnsctnvoidBean.setVoidReqDate(rset.getTimestamp("VOID_REQ_DATE"));
				trnsctnvoidBean.setVoidResDate(rset.getTimestamp("VOID_RES_DATE"));
				trnsctnvoidBean.setStatus(rset.getString("STATUS"));
				trnsctnvoidBean.setTemp1String(rset.getString("MERCHANT_ID"));
				trnsctnvoidBean.setVoidAmount(rset.getDouble("VOID_AMOUNT"));
				trnsctnvoidBean.setTransactionId(rset.getString("TRANSACTION_ID"));
				trnsctnvoidBean.setTemp3String(crypt.hideCardNumberDecrypt(rset.getString("CARD_NUM")));
				trnsctnvoidBean.setTemp4String(rset.getString("VAN"));
				trnsctnvoidBean.setTemp2String(rset.getString("COMMENTS"));
				trnsctnvoidBean.setCommentIdx(rset.getLong("COMMENT_IDX"));
				trnsctnvoidBean.setTemp5String(rset.getString("PAY_NO"));
				trnsctnvoidBean.setRootTrnStatus(rset.getString("ROOT_TRN_STATUS"));
				trnsctnvoidBean.setVoidApprovalNo(rset.getString("VOID_APPROVAL_NO"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				sumAmount = sumAmount + trnsctnvoidBean.getVoidAmount();
				list.add(trnsctnvoidBean);
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;

	}
	
	public boolean insert(TrnsctnVoidBean trnsctnvoidBean){
		
		String query = "INSERT INTO TB_TRNSCTN_VOID (IDX, TRANSACTION_ID, VOID_TRANSACTION_ID, VOID_VAN_TRANSACTION_ID, VOID_AMOUNT, "
			         + "VOID_APPROVAL_NO, ROOT_TRN_STATUS, STATUS, VOID_RESULT_CD, COMMENT_IDX, VOID_REQ_DATE, VOID_RES_DATE, REG_DATE) "
			         + "VALUES (?, ?, ?, ?, ?, ?, ?, '11', ?, ?, SYSDATE, ?, ?)";
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		
		try{
			trnsctnvoidBean.setIdx(SequenceDAO.getSequenceLong("SEQ_TRNSCTN_VOID"));
			trnsctnvoidBean.setCommentIdx(SequenceDAO.getSequenceLong("SEQ_COMMENT_TRNSCTN"));
					
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setLong(idx++, trnsctnvoidBean.getIdx());
			pstmt.setString(idx++, trnsctnvoidBean.getTransactionId());
			pstmt.setString(idx++, trnsctnvoidBean.getVoidTransactionId());
			pstmt.setString(idx++, trnsctnvoidBean.getVoidVanTransactionId());
			pstmt.setDouble(idx++, trnsctnvoidBean.getVoidAmount());
			pstmt.setString(idx++, trnsctnvoidBean.getVoidApprovalNo());
			pstmt.setString(idx++, trnsctnvoidBean.getRootTrnStatus());
			pstmt.setString(idx++, trnsctnvoidBean.getVoidResultCd());
			pstmt.setLong(idx++, trnsctnvoidBean.getCommentIdx());
			pstmt.setTimestamp(idx++, trnsctnvoidBean.getVoidResDate());
			pstmt.setTimestamp(idx++, trnsctnvoidBean.getRegDate());

			result = pstmt.executeUpdate();
			conn.commit();			
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(trnsctnvoidBean),this);
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
	
	
	public boolean insertVoid(TrnsctnVoidBean trnsctnvoidBean){
		
		String query = "INSERT INTO TB_TRNSCTN_VOID (IDX, TRANSACTION_ID, VOID_TRANSACTION_ID,VOID_VAN_TRANSACTION_ID, VOID_AMOUNT, VOID_APPROVAL_NO,"
					 + "ROOT_TRN_STATUS, STATUS, VOID_RESULT_CD,COMMENT_IDX, VOID_REQ_DATE, VOID_RES_DATE,REG_DATE) VALUES (?,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,SYSDATE )";	
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		
		try{
			trnsctnvoidBean.setIdx(SequenceDAO.getSequenceLong("SEQ_TRNSCTN_VOID"));
			trnsctnvoidBean.setCommentIdx(SequenceDAO.getSequenceLong("SEQ_COMMENT_TRNSCTN"));
			new Comment().setTrnsctnComment(trnsctnvoidBean.getCommentIdx(),"SYSTEM","SYSTEM CANCEL");
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setLong(idx++, trnsctnvoidBean.getIdx());
			pstmt.setString(idx++, trnsctnvoidBean.getTransactionId());
			pstmt.setString(idx++, trnsctnvoidBean.getVoidTransactionId());
			pstmt.setString(idx++, trnsctnvoidBean.getVoidVanTransactionId());
			pstmt.setDouble(idx++, trnsctnvoidBean.getVoidAmount());
			pstmt.setString(idx++, trnsctnvoidBean.getVoidApprovalNo());
			pstmt.setString(idx++, trnsctnvoidBean.getRootTrnStatus());
			pstmt.setString(idx++, trnsctnvoidBean.getStatus());
			pstmt.setString(idx++, trnsctnvoidBean.getVoidResultCd());
			pstmt.setLong(idx++, trnsctnvoidBean.getCommentIdx());
			pstmt.setTimestamp(idx++, trnsctnvoidBean.getVoidReqDate());
			pstmt.setTimestamp(idx++, trnsctnvoidBean.getVoidResDate());

			result = pstmt.executeUpdate();
			conn.commit();			
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(trnsctnvoidBean),this);
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
	
	
	
	
	public boolean update(TrnsctnVoidBean trnsctnvoidBean){
		
		String query = "UPDATE TB_TRNSCTN_VOID SET TRANSACTION_ID=?, VOID_TRANSACTION_ID=?, VOID_VAN_TRANSACTION_ID=?, VOID_AMOUNT=?, "
			         + "VOID_APPROVAL_NO=?, ROOT_TRN_STATUS=?, STATUS=?, VOID_RESULT_CD=?, COMMENT_IDX=?, VOID_RES_DATE=? "
			         + "WHERE IDX=?";
		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;		
		
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setString(idx++, trnsctnvoidBean.getTransactionId());
			pstmt.setString(idx++, trnsctnvoidBean.getVoidTransactionId());
			pstmt.setString(idx++, trnsctnvoidBean.getVoidVanTransactionId());
			pstmt.setDouble(idx++, trnsctnvoidBean.getVoidAmount());
			pstmt.setString(idx++, trnsctnvoidBean.getVoidApprovalNo());
			pstmt.setString(idx++, trnsctnvoidBean.getRootTrnStatus());
			pstmt.setString(idx++, trnsctnvoidBean.getStatus());
			pstmt.setString(idx++, trnsctnvoidBean.getVoidResultCd());
			pstmt.setLong(idx++, trnsctnvoidBean.getCommentIdx());
			pstmt.setTimestamp(idx++, trnsctnvoidBean.getVoidResDate());
			pstmt.setLong(idx++, trnsctnvoidBean.getIdx());
			result = pstmt.executeUpdate();
			conn.commit();			

		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(trnsctnvoidBean),this);
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
	
	
    public boolean updateStatus(String transactionId, String status){
		
		String query = "UPDATE TB_TRNSCTN_VOID SET STATUS=? , VOID_RES_DATE=SYSDATE WHERE TRANSACTION_ID=?";
			         
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;		
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, status);
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

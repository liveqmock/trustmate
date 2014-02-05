/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.QuestionDAO.java
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

import com.pgmate.model.db.QuestionBean;

public class QuestionDAO extends DAO {

	public QuestionDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public QuestionBean getByIdx(String idx){
		List<QuestionBean> list = get(" AND IDX ="+idx);
		if(list.size() == 0){
			return new QuestionBean();
		}else{
			return (QuestionBean)list.get(0);
		}
	}
	
	public QuestionBean getById(String merchantId){
		List<QuestionBean> list = get(" AND MERCHANT_ID ='"+merchantId+"' ");
		if(list.size() == 0){
			return new QuestionBean();
		}else{
			return (QuestionBean)list.get(0);
		}
	}
	
	public List<QuestionBean> get(QuestionBean qBean){
		StringBuffer sb = new StringBuffer();
		
		if(qBean.getIdx() != 0){
			return get(" AND IDX ="+CommonUtil.toString(qBean.getIdx()));
		}
		
		if(!qBean.getMerchantId().equals("")){
			sb.append(" AND MERCHANT_ID = '"+qBean.getMerchantId()+"'");
		}
		
		if(!qBean.getMemberName().equals("")){
			sb.append(" AND MEMBER_NAME ='"+qBean.getMemberName()+"'");
		}
		
		if(!qBean.getSubject().equals("")){
			sb.append(" AND SUBJECT ='"+qBean.getSubject()+"'");
		}
		
		if(!qBean.getEmail().equals("")){
			sb.append(" AND EMAIL ='"+qBean.getEmail()+"'");
		}
		
		if(!qBean.getActive().equals("")){
			sb.append(" AND ACTIVE ='"+qBean.getActive()+"'");
		}
		
		return get(sb.toString());

	}
	
	
	public List<QuestionBean> get(String subQuery){
		String query = "SELECT IDX, MERCHANT_ID, MEMBER_NAME, SUBJECT, EMAIL, CONTENTS, ACTIVE, REG_DATE FROM VW_QUESTION "
			+" WHERE IDX IS NOT NULL " + subQuery;
		
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
		
		List<QuestionBean> list = new ArrayList<QuestionBean>();
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
				QuestionBean qBean = new QuestionBean();
				qBean.setIdx(rset.getLong("IDX"));
				qBean.setMerchantId(rset.getString("MERCHANT_ID"));
				qBean.setMemberName(rset.getString("MEMBER_NAME"));
				qBean.setSubject(rset.getString("SUBJECT"));
				qBean.setEmail(rset.getString("EMAIL"));
				qBean.setContents(rset.getString("CONTENTS"));
				qBean.setActive(rset.getString("ACTIVE"));
				qBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(qBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public boolean update(QuestionBean qBean){
		String query = "UPDATE TB_QUESTION SET MERCHANT_ID = ?, MEMBER_NAME = ?, SUBJECT = ?, EMAIL = ?, CONTENTS =?, ACTIVE=? WHERE IDX =?";
		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, qBean.getMerchantId());
			pstmt.setString(idx++, qBean.getMemberName());
			pstmt.setString(idx++, qBean.getSubject());
			pstmt.setString(idx++, qBean.getEmail());
			pstmt.setString(idx++, qBean.getContents());
			pstmt.setString(idx++, qBean.getActive());
			pstmt.setLong(idx++, qBean.getIdx());
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(qBean),this);
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
	
	public boolean insert(QuestionBean qBean){
		String query = "INSERT INTO TB_QUESTION ( IDX, MERCHANT_ID, MEMBER_NAME, SUBJECT, EMAIL, CONTENTS, ACTIVE, REG_DATE) "
			+" VALUES ( ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
				
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			qBean.setIdx(SequenceDAO.getSequenceLong("SEQ_QUESTION"));
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setLong(idx++, qBean.getIdx());
			pstmt.setString(idx++, qBean.getMerchantId());
			pstmt.setString(idx++, qBean.getMemberName());
			pstmt.setString(idx++, qBean.getSubject());
			pstmt.setString(idx++, qBean.getEmail());
			pstmt.setString(idx++, qBean.getContents());
			pstmt.setString(idx++, qBean.getActive());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(qBean),this);
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

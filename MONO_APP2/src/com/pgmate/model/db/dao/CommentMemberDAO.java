/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.CommentMemberDAO.java
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

import com.pgmate.model.db.CommentMemberBean;

public class CommentMemberDAO extends DAO {

	public CommentMemberDAO() {
	}
	
	
	public List<CommentMemberBean> getByMemberId(String memberId){
		return get(" AND MEMBER_ID = '"+memberId+"'");
	}
	
	public List<CommentMemberBean> getByIdx(String idx){
		return get(" AND IDX ="+idx);	
	}
	
	public List<CommentMemberBean> get(String subQuery){
		String query = "SELECT IDX, MEMBER_ID, COMMENTS, REG_DATE FROM VW_COMMENT_MEMBER WHERE IDX IS NOT NULL " + subQuery;
		
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
		
		List<CommentMemberBean> list = new ArrayList<CommentMemberBean>();
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
				CommentMemberBean cmBean = new CommentMemberBean();
				cmBean.setIdx(rset.getLong("IDX"));
				cmBean.setMemberId(rset.getString("MEMBER_ID"));
				cmBean.setComments(CommonUtil.nToB(rset.getString("COMMENTS")));				
				cmBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(cmBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public boolean update(CommentMemberBean cmBean){
		String query = "UPDATE TB_COMMENT_MEMBER SET MEMBER_ID = ?,COMMENTS  =?, WHERE IDX=?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, cmBean.getMemberId());
			pstmt.setString(idx++, cmBean.getComments());
			pstmt.setLong(idx++, cmBean.getIdx());
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(cmBean),this);
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
	
	public boolean insert(CommentMemberBean cmBean){
		String query = "INSERT INTO TB_COMMENT_MEMBER (IDX, MEMBER_ID, COMMENTS,REG_DATE) VALUES (? ,? ,? ,SYSDATE )";
	
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setLong(idx++, cmBean.getIdx());
			pstmt.setString(idx++, cmBean.getMemberId());
			if(cmBean.getComments().equals("")){
				pstmt.setString(idx++, cmBean.getMemberId());
			}else{
				pstmt.setString(idx++, cmBean.getComments());
			}

			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(cmBean),this);
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

/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.CommentSettleDAO.java
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

import com.pgmate.model.db.CommentSettleBean;

public class CommentSettleDAO extends DAO {

	public CommentSettleDAO() {
	}
	
	
	public List<CommentSettleBean> getByMemberId(String memberId){
		return get(" AND MEMBER_ID = '"+memberId+"'");
	}
	
	public List<CommentSettleBean> getByIdx(String idx){
		return get(" AND IDX ="+idx);	
	}
	
	public List<CommentSettleBean> get(String subQuery){
		String query = "SELECT IDX, MEMBER_ID, COMMENTS, REG_DATE FROM VW_COMMENT_SETTLE WHERE IDX IS NOT NULL " + subQuery;
		
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
		
		List<CommentSettleBean> list = new ArrayList<CommentSettleBean>();
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
				CommentSettleBean csBean = new CommentSettleBean();
				csBean.setIdx(rset.getLong("IDX"));
				csBean.setMemberId(rset.getString("MEMBER_ID"));
				csBean.setComments(CommonUtil.nToB(rset.getString("COMMENTS")));				
				csBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(csBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public boolean update(CommentSettleBean csBean){
		String query = "UPDATE TB_COMMENT_SETTLE SET MEMBER_ID = ?,COMMENTS  =?, WHERE IDX=?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, csBean.getMemberId());
			pstmt.setString(idx++, csBean.getComments());
			pstmt.setLong(idx++, csBean.getIdx());
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(csBean),this);
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
	
	public boolean insert(CommentSettleBean csBean){
		String query = "INSERT INTO TB_COMMENT_SETTLE (IDX, MEMBER_ID, COMMENTS,REG_DATE) VALUES (? ,? ,? ,SYSDATE )";
	
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setLong(idx++, csBean.getIdx());
			pstmt.setString(idx++, csBean.getMemberId());
			if(csBean.getComments().equals("")){
				pstmt.setString(idx++, csBean.getMemberId());
			}else{
				pstmt.setString(idx++, csBean.getComments());
			}

			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(csBean),this);
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

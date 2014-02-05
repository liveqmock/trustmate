/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.CommentTrnsctnDAO.java
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

import com.pgmate.model.db.CommentTrnsctnBean;

public class CommentTrnsctnDAO extends DAO {

	public CommentTrnsctnDAO() {
	}
	
	
	public List<CommentTrnsctnBean> getByMemberId(String memberId){
		return get(" AND MEMBER_ID = '"+memberId+"'");
	}
	
	public List<CommentTrnsctnBean> getByIdx(String idx){
		return get(" AND IDX ="+idx);	
	}
	
	public CommentTrnsctnBean getByIdx(long idx){
		List<CommentTrnsctnBean> ctList = get(" AND IDX ="+idx);
		if(ctList.size() == 0){
			return new CommentTrnsctnBean();
		}else{
			return (CommentTrnsctnBean)ctList.get(0);
		}	
	}
	
	public List<CommentTrnsctnBean> get(String subQuery){
		String query = "SELECT IDX, MEMBER_ID, COMMENTS, REG_DATE FROM VW_COMMENT_TRNSCTN WHERE IDX IS NOT NULL " + subQuery;
		
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
		
		List<CommentTrnsctnBean> list = new ArrayList<CommentTrnsctnBean>();
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
				CommentTrnsctnBean ctBean = new CommentTrnsctnBean();
				ctBean.setIdx(rset.getLong("IDX"));
				ctBean.setMemberId(rset.getString("MEMBER_ID"));
				ctBean.setComments(CommonUtil.nToB(rset.getString("COMMENTS")));				
				ctBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(ctBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public boolean update(CommentTrnsctnBean ctBean){
		String query = "UPDATE TB_COMMENT_TRNSCTN SET MEMBER_ID = ?,COMMENTS  =? WHERE IDX=?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, ctBean.getMemberId());
			pstmt.setString(idx++, ctBean.getComments());
			pstmt.setLong(idx++, ctBean.getIdx());
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(ctBean),this);
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
	
	public boolean insert(CommentTrnsctnBean ctBean){
		String query = "INSERT INTO TB_COMMENT_TRNSCTN (IDX, MEMBER_ID, COMMENTS,REG_DATE) VALUES (? ,? ,? ,SYSDATE )";
	
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setLong(idx++, ctBean.getIdx());
			pstmt.setString(idx++, ctBean.getMemberId());
			if(ctBean.getComments().equals("")){
				pstmt.setString(idx++, ctBean.getMemberId());
			}else{
				pstmt.setString(idx++, ctBean.getComments());
			}

			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(ctBean),this);
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

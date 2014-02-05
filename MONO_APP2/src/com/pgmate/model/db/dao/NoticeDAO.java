/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.NoticeDAO.java
 * Date	        : Dec 23, 2008
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.db.dao;

import java.io.BufferedWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.driver.OracleResultSet;
import oracle.sql.CLOB;

import com.pgmate.model.db.factory.DBFactory;
import com.pgmate.model.db.factory.DBUtil;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.BeanUtil;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.NoticeBean;

public class NoticeDAO extends DAO {

	public NoticeDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public NoticeBean getByIdx(String idx){
		List<NoticeBean> list = get(" AND IDX ="+idx);
		if(list.size() == 0){
			return new NoticeBean();
		}else{
			return (NoticeBean)list.get(0);
		}
	}
	
	
	public List<NoticeBean> get(NoticeBean nBean){
		StringBuffer sb = new StringBuffer();
		
		if(nBean.getIdx() != 0){
			return get(" AND IDX ="+CommonUtil.toString(nBean.getIdx()));
		}
		if(!nBean.getMemberName().equals("")){
			sb.append(" AND MEMBER_NAME ='"+nBean.getMemberName()+"'");
		}
		
		if(!nBean.getSubject().equals("")){
			sb.append(" AND SUBJECT ='"+nBean.getSubject()+"'");
		}
		
		if(!nBean.getActive().equals("")){
			sb.append(" AND ACTIVE ='"+nBean.getActive()+"'");
		}
		
		return get(sb.toString());

	}
	
	
	public List<NoticeBean> get(String subQuery){
		String query = "SELECT IDX, MEMBER_NAME, SUBJECT, CONTENTS, ACTIVE, REG_DATE FROM VW_NOTICE "
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
		
		List<NoticeBean> list = new ArrayList<NoticeBean>();
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
				NoticeBean nBean = new NoticeBean();
				nBean.setIdx(rset.getLong("IDX"));
				nBean.setMemberName(rset.getString("MEMBER_NAME"));
				nBean.setSubject(rset.getString("SUBJECT"));
				StringBuffer sb = new StringBuffer();
				Reader reader = rset.getCharacterStream("CONTENTS");
				char[] buffer = new char[1024];
				int byteRead;
				while((byteRead = reader.read(buffer,0,1024)) != -1){
					sb.append(buffer,0,byteRead);
				}
				reader.close();
				nBean.setContents(sb.toString());
				nBean.setActive(rset.getString("ACTIVE"));
				nBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(nBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public boolean update(NoticeBean nBean){
		String query = "UPDATE TB_NOTICE SET MEMBER_NAME=?, SUBJECT = ?, CONTENTS =?, ACTIVE=? WHERE IDX =?";
		String selectQuery = "SELECT CONTENTS FROM TB_NOTICE WHERE IDX ="+CommonUtil.toString(nBean.getIdx());
		
		boolean excute	= false;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rst			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			conn.setAutoCommit(false); //최종 CLOB 처리가 끝날때까지 자동 Commit 을 잠시 멈춘다.
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, nBean.getMemberName());
			pstmt.setString(idx++, nBean.getSubject());
			pstmt.setString(idx++, nBean.getContents());
			pstmt.setString(idx++, nBean.getActive());
			pstmt.setLong(idx++, nBean.getIdx());
			
			if(pstmt.executeUpdate() > 0){
				pstmt.close();
				pstmt 	= conn.prepareStatement(selectQuery);
				rst 	= pstmt.executeQuery();
				if(rst.next()){
					CLOB clob = ((OracleResultSet)rst).getCLOB("CONTENTS");
					BufferedWriter bw = new BufferedWriter(clob.getCharacterOutputStream());
					bw.write(nBean.getContents());
					bw.close();
				}
			}
			conn.commit();
			excute = true;
			
			
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(nBean),this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(pstmt);
			db.close(conn);
		}
		
		return excute;
	}
	
	public boolean insert(NoticeBean nBean){
		String query = "INSERT INTO TB_NOTICE ( IDX, MEMBER_NAME, SUBJECT, CONTENTS, ACTIVE, REG_DATE) "
			+" VALUES ( ?, ?, ?, ?, ?, SYSDATE)";
				
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			nBean.setIdx(SequenceDAO.getSequenceLong("SEQ_NOTICE"));
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setLong(idx++, nBean.getIdx());
			pstmt.setString(idx++, nBean.getMemberName());
			pstmt.setString(idx++, nBean.getSubject());
			pstmt.setString(idx++, nBean.getContents());
			pstmt.setString(idx++, nBean.getActive());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(nBean),this);
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

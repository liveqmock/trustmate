/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.AccessLogDAO.java
 * Date	        : Jan 15, 2009
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

import com.pgmate.model.db.AccessLogBean;

public class AccessLogDAO extends DAO {
	
	public AccessLogDAO(){
		
	}
	
	public AccessLogBean getByIdx(String idx){
		List<AccessLogBean> list = get(" AND IDX = '"+idx+"' ");
		if(list.size() == 0){
			return new AccessLogBean();
		}else{
			return (AccessLogBean)list.get(0);
		}	
	}
	
	public List<AccessLogBean> getByMemberId(String memberId){
		return get(" AND MEMBER_ID ='"+memberId+"'");
	}
	
	public List<AccessLogBean> get(AccessLogBean aBean){
		
		if(aBean.getIdx() !=0){
			return get(" AND IDX ='"+CommonUtil.toString(aBean.getIdx())+"' ");
		}else{
			StringBuffer sb = new StringBuffer();
			
			if(!aBean.getMemberId().equals("")){
				sb.append(" AND MEMBER_ID ='"+aBean.getMemberId()+"'");
			}
			
			if(!aBean.getIpAddress().equals("")){
				sb.append(" AND IPADDRESS ='"+aBean.getIpAddress()+"'");
			}
			
			if(!aBean.getUserAgent().equals("")){
				sb.append(" AND USER_AGENT ='"+aBean.getUserAgent()+"'");
			}
			
			if(!aBean.getAccessPage().equals("")){
				sb.append(" AND ACCESS_PAGE ='"+aBean.getAccessPage()+"'");
			}
			
			if(aBean.getRegDate() != null){
				sb.append(" AND TO_CHAR(REG_DATE, 'yyyy-MM-dd') = '"+ CommonUtil.convertTimestampToString(aBean.getRegDate(), "yyyy-MM-dd") + "'");
			}
			
			return get(sb.toString());
			
		}
	}
	
	public List<AccessLogBean> get(String subQuery){
		String query = "SELECT IDX, MEMBER_ID, IPADDRESS, USER_AGENT, ACCESS_PAGE, REG_DATE FROM VW_ACCESS_LOG WHERE IDX IS NOT NULL " + subQuery;
		
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
		
		List<AccessLogBean> list = new ArrayList<AccessLogBean>();
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
				AccessLogBean aBean = new AccessLogBean();
				aBean.setIdx(rset.getLong("IDX"));
				aBean.setMemberId(rset.getString("MEMBER_ID"));
				aBean.setIpAddress(rset.getString("IPADDRESS"));
				aBean.setUserAgent(rset.getString("USER_AGENT"));
				aBean.setAccessPage(rset.getString("ACCESS_PAGE"));
				aBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(aBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public boolean update(AccessLogBean aBean){
		String query = "UPDATE TB_ACCESS_LOG SET MEMBER_ID = ?, IPADDRESS = ?, USER_AGENT = ?, ACCESS_PAGE = ? WHERE IDX =?";

		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, aBean.getMemberId());
			pstmt.setString(idx++, aBean.getIpAddress());
			pstmt.setString(idx++, aBean.getUserAgent());
			pstmt.setString(idx++, aBean.getAccessPage());
			pstmt.setLong(idx++, aBean.getIdx());
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(aBean),this);
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
	
	public boolean insert(AccessLogBean aBean){
		String query = "INSERT INTO TB_ACCESS_LOG ( IDX, MEMBER_ID, IPADDRESS, USER_AGENT, ACCESS_PAGE, REG_DATE )"
			+" VALUES (? ,? ,? ,? ,? ,SYSDATE )";
	
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			aBean.setIdx(SequenceDAO.getSequenceLong("SEQ_ACCESS_LOG"));
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setLong(idx++, aBean.getIdx());
			pstmt.setString(idx++, aBean.getMemberId());
			pstmt.setString(idx++, aBean.getIpAddress());
			pstmt.setString(idx++, aBean.getUserAgent());
			pstmt.setString(idx++, aBean.getAccessPage());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(aBean),this);
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

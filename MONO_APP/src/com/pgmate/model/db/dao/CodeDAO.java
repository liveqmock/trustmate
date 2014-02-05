/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.CodeDAO.java
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

import com.pgmate.model.db.CodeBean;

public class CodeDAO extends DAO {

	public CodeDAO(){
		
	}
	
	public List<String> getAliasList(){
		String query = "SELECT DISTINCT(ALIAS) FROM VW_CODE WHERE IDX IS NOT NULL GROUP BY ALIAS";
		
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
				list.add(rset.getString("ALIAS"));
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",e.getMessage(),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public CodeBean getByIdx(String idx){
		List<CodeBean> list = get(" AND IDX ="+idx);
		if(list.size() == 0){
			return new CodeBean();
		}else{
			return (CodeBean)list.get(0);
		}
	}
	
	public List<CodeBean> getByCode(String code){
		CodeBean codeBean = new CodeBean();
		codeBean.setCode(code);
		return get(codeBean);
	}
	
	public List<CodeBean> getByAlias(String alias){
		CodeBean codeBean = new CodeBean();
		codeBean.setAlias(alias);
		return get(codeBean);
	}
	
	public CodeBean getByAliasCode(String alias,String code){
		CodeBean codeBean = new CodeBean();
		codeBean.setAlias(alias);
		codeBean.setCode(code);
		List<CodeBean> list = get(codeBean);
		if(list.size() == 0){
			return new CodeBean();
		}else{
			return (CodeBean)list.get(0);
		}
	}
	
	
	public List<CodeBean> get(CodeBean codeBean){
		StringBuffer sb = new StringBuffer();
		
		if(codeBean.getIdx() != 0){
			return get(" AND IDX ="+CommonUtil.toString(codeBean.getIdx()));
		}
		if(!codeBean.getAlias().equals("")){
			sb.append(" AND ALIAS ='"+codeBean.getAlias()+"'");
		}
		
		if(!codeBean.getActive().equals("")){
			sb.append(" AND ACTIVE ='"+codeBean.getActive()+"'");
		}
		
		if(!codeBean.getCode().equals("")){
			sb.append(" AND CODE ='"+codeBean.getCode()+"'");
		}
		
		if(!codeBean.getAcode().equals("")){
			sb.append(" AND ACODE ='"+codeBean.getAcode()+"'");
		}
		
		
		return get(sb.toString());

	}
	
	
	public List<CodeBean> get(String subQuery){
		String query = "SELECT IDX, ALIAS, ACODE, CODE,KR_VALUE, JP_VALUE, EN_VALUE, MEMBER_ID, ACTIVE, REG_DATE FROM VW_CODE "
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
		
		List<CodeBean> list = new ArrayList<CodeBean>();
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
				CodeBean codeBean = new CodeBean();
				codeBean.setIdx(rset.getLong("IDX"));
				codeBean.setAlias(rset.getString("ALIAS"));
				codeBean.setAcode(rset.getString("ACODE"));
				codeBean.setCode(rset.getString("CODE"));
				codeBean.setKrValue(rset.getString("KR_VALUE"));
				codeBean.setJpValue(rset.getString("JP_VALUE"));
				codeBean.setEnValue(rset.getString("EN_VALUE"));
				codeBean.setMemberId(rset.getString("MEMBER_ID"));
				codeBean.setActive(rset.getString("ACTIVE"));
				codeBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(codeBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",e.getMessage(),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public boolean update(CodeBean codeBean){
		String query = "UPDATE TB_CODE SET ALIAS =?,ACODE=?,CODE=?,KR_VALUE =?,JP_VALUE =?,EN_VALUE =?,MEMBER_ID =?,ACTIVE =? WHERE IDX =?";
		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, codeBean.getAlias());
			pstmt.setString(idx++, codeBean.getAcode());
			pstmt.setString(idx++, codeBean.getCode());
			pstmt.setString(idx++, codeBean.getKrValue());
			pstmt.setString(idx++, codeBean.getJpValue());
			pstmt.setString(idx++, codeBean.getEnValue());
			pstmt.setString(idx++, codeBean.getMemberId());
			pstmt.setString(idx++, codeBean.getActive());
			pstmt.setLong(idx++, codeBean.getIdx());
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(codeBean),this);
			Log.debug("log.sql",e.getMessage(),this);
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
	
	public boolean insert(CodeBean codeBean){
		String query = "INSERT INTO TB_CODE ( IDX, ALIAS, ACODE,CODE,KR_VALUE, JP_VALUE, EN_VALUE, MEMBER_ID, ACTIVE, REG_DATE) "
			+" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
				
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			codeBean.setIdx(SequenceDAO.getSequenceLong("SEQ_CODE"));
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setLong(idx++, codeBean.getIdx());
			pstmt.setString(idx++, codeBean.getAlias());
			pstmt.setString(idx++, codeBean.getAcode());
			pstmt.setString(idx++, codeBean.getCode());
			pstmt.setString(idx++, codeBean.getKrValue());
			pstmt.setString(idx++, codeBean.getJpValue());
			pstmt.setString(idx++, codeBean.getEnValue());
			pstmt.setString(idx++, codeBean.getMemberId());
			pstmt.setString(idx++, codeBean.getActive());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(codeBean),this);
			Log.debug("log.sql",e.getMessage(),this);
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

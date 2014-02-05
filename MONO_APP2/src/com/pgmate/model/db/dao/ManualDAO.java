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

import com.pgmate.model.db.ManualBean;

public class ManualDAO extends DAO {

	public ManualDAO() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public List<ManualBean> get(ManualBean nBean){
		StringBuffer sb = new StringBuffer();
		
		if(nBean.getIdx() != 0){
			return get(" AND IDX ="+CommonUtil.toString(nBean.getIdx()));
		}
		if(!nBean.getTitle().equals("")){
			sb.append(" AND TITLE LIKE  '%"+nBean.getTitle()+"%'");
		}
		
		if(!nBean.getDocType().equals("")){
			sb.append(" AND DOC_TYPE ='"+nBean.getDocType()+"'");
		}
		
		if(!nBean.getActive().equals("")){
			sb.append(" AND ACTIVE ='"+nBean.getActive()+"'");
		}
		
		return get(sb.toString());

	}
	
	
	public List<ManualBean> get(String subQuery){
		String query = "SELECT IDX, DOC_TYPE, TITLE, LOCATION, ACTIVE, REG_DATE FROM TB_MANUAL "
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
		
		List<ManualBean> list = new ArrayList<ManualBean>();
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
				ManualBean nBean = new ManualBean();
				nBean.setIdx(rset.getLong("IDX"));
				nBean.setDocType(rset.getString("DOC_TYPE"));
				nBean.setTitle(rset.getString("TITLE"));
				nBean.setLocation(rset.getString("LOCATION"));
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
	
	

}

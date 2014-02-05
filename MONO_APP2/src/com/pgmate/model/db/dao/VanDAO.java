package com.pgmate.model.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.BeanUtil;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.TrnsctnRiskBean;
import com.pgmate.model.db.VanBean;
import com.pgmate.model.db.factory.DBFactory;
import com.pgmate.model.db.factory.DBUtil;

/**
 * @author Administrator
 *
 */
public class VanDAO extends DAO {

	public VanDAO(){
	}
	
	public VanBean getByIdx(long idx){
		List<VanBean> list = get(" AND IDX ="+CommonUtil.toString(idx));
		if(list.size() == 0){
			return new VanBean();
		}else{
			return (VanBean)list.get(0);
		}
	}
	
	
	public VanBean getByVanAndVanId(String van,String vanId){
		List<VanBean> list = get(" AND VAN ='"+van+"' AND VAN_ID='"+vanId+"'");
		if(list.size() == 0){
			return new VanBean();
		}else{
			return (VanBean)list.get(0);
		}
	}
	
	public List<VanBean> get(VanBean vanBean){
		
		StringBuffer sb = new StringBuffer();
		if(!vanBean.getVan().equals("")){
			sb.append(" AND VAN ='"+vanBean.getVan()+"'");
		}
		if(!vanBean.getVanId().equals("")){
			sb.append(" AND VAN_ID ='"+vanBean.getVanId()+"'");
		}
		if(!vanBean.getStatus().equals("")){
			sb.append(" AND STATUS ='"+vanBean.getStatus()+"'");
		}
		if(!vanBean.getDescriptor().equals("")){
			sb.append(" AND DESCRIPTOR LIKE '%"+vanBean.getDescriptor()+"%'");
		}
		return get(sb.toString());
	}
	
	public List<VanBean> get(String subQuery){
		String query = "SELECT IDX, VAN, VAN_ID, DESCRIPTOR, STATUS, URL, ADMIN_ID, ADMIN_PW, REG_DATE FROM TB_VAN  "
				+" WHERE 1=1 " + subQuery;
			
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
		
		List<VanBean> list = new ArrayList<VanBean>();
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
				
				VanBean vanBean = new VanBean();
				vanBean.setIdx(rset.getLong("IDX"));
				vanBean.setVan(rset.getString("VAN"));
				vanBean.setVanId(rset.getString("VAN_ID"));
				vanBean.setDescriptor(rset.getString("DESCRIPTOR"));
				vanBean.setStatus(rset.getString("STATUS"));
				vanBean.setUrl(rset.getString("URL"));
				vanBean.setAdminId(rset.getString("ADMIN_ID"));
				vanBean.setAdminPw(rset.getString("ADMIN_PW"));
				vanBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(vanBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	
	public List<String> getVanList(){
		String query = "SELECT VAN FROM TB_VAN WHERE STATUS ='ACTIVE' GROUP BY VAN ";
				
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
				
				list.add(rset.getString("VAN"));
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public List<String> getVanIdList(String van){
		String query = "SELECT VAN_ID FROM TB_VAN WHERE VAN =? AND STATUS ='ACTIVE' ";
				
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
				
				list.add(rset.getString("VAN_ID"));
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	
	public boolean update(VanBean vanBean){

		String query =  "UPDATE TB_VAN SET VAN=?, VAN_ID=?,DESCRIPTOR=?, STATUS=?,URL=?,ADMIN_ID=?,ADMIN_PW=? WHERE IDX = ? ";
		
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;


		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);

			int idx = 1;
			pstmt.setString(idx++, vanBean.getVan());
			pstmt.setString(idx++, vanBean.getVanId());
			pstmt.setString(idx++, vanBean.getDescriptor());
			pstmt.setString(idx++, vanBean.getStatus());
			pstmt.setString(idx++, vanBean.getUrl());
			pstmt.setString(idx++, vanBean.getAdminId());
			pstmt.setString(idx++, vanBean.getAdminPw());
			pstmt.setLong(idx++, vanBean.getIdx());

			result = pstmt.executeUpdate();
			conn.commit();

		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(vanBean),this);
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
	
	
	
	public boolean insert(VanBean vanBean){

		String query =  "INSERT INTO TB_VAN (IDX, VAN, VAN_ID, DESCRIPTOR, STATUS, URL, ADMIN_ID, ADMIN_PW, REG_DATE ) VALUES (SEQ_VAN.NEXTVAL,?,?,?,?,?,?,?,SYSDATE)";
		
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;


		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);

			int idx = 1;
			pstmt.setString(idx++, vanBean.getVan());
			pstmt.setString(idx++, vanBean.getVanId());
			pstmt.setString(idx++, vanBean.getDescriptor());
			pstmt.setString(idx++, vanBean.getStatus());
			pstmt.setString(idx++, vanBean.getUrl());
			pstmt.setString(idx++, vanBean.getAdminId());
			pstmt.setString(idx++, vanBean.getAdminPw());

			result = pstmt.executeUpdate();
			conn.commit();

		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(vanBean),this);
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

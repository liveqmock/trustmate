/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.MerchantDAO.java
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

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.BeanUtil;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.GroupBean;
import com.pgmate.model.db.GroupMerchantBean;
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.factory.DBFactory;
import com.pgmate.model.db.factory.DBUtil;

public class GroupDAO extends DAO {

	public GroupDAO() {
	}
	
	public List<GroupBean> geByName(String groupName){
		return get(" AND NAME='"+groupName+"'");
	}
	
	
	public List<GroupBean> getByActive(String active){
		return get(" AND ACTIVE='"+active+"'");
	}
	
	public List<MerchantBean> getByMerchant(String groupId){
		MerchantDAO merchantDAO = new MerchantDAO();
		merchantDAO.pageSize = 10000;
		merchantDAO.orderBy = "ORDER BY MERCHANT_ID ASC";
		return merchantDAO.get(" AND MERCHANT_ID IN (SELECT MERCHANT_ID FROM TB_GROUP_MERCHANT WHERE GROUP_ID='"+groupId+"')");
	}
	
	public GroupBean getById(String groupId){
		List<GroupBean> list = get(" AND GROUP_ID ='"+groupId+"'");
		if(list.size() == 0){
			return new GroupBean();
		}else{
			return (GroupBean)list.get(0);
		}	
	}
	
	public List<GroupBean> get(GroupBean groupBean){
		StringBuffer sb = new StringBuffer();
		
		if(!groupBean.getGroupId().equals("")){
			return get(" AND MERCHANT_ID ='"+groupBean.getGroupId()+"' ");
		}else{
			if(!groupBean.getName().equals("")){
				sb.append( " AND NAME LIKE '%"+groupBean.getName()+"%' ");
			}
			if(!groupBean.getActive().equals("")){
				sb.append( " AND ACTIVE='"+groupBean.getActive()+"' ");
			}
			
			
			return get(sb.toString());
		}
		
	}
	
	public List<GroupBean> get(String subQuery){
		
		String query = "SELECT  GROUP_ID, NAME, PASSWORD, ACTIVE, REG_DATE FROM TB_GROUP "
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
		
		List<GroupBean> list = new ArrayList<GroupBean>();
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
				GroupBean groupBean = new GroupBean();
				
				groupBean.setGroupId(rset.getString("GROUP_ID"));
				groupBean.setName(CommonUtil.nToB(rset.getString("NAME")));
				groupBean.setPassword(rset.getString("PASSWORD"));
				groupBean.setActive(CommonUtil.nToB(rset.getString("ACTIVE")));
				groupBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(groupBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public boolean update(GroupBean groupBean){
		String query = "UPDATE TB_GROUP SET NAME =? , ACTIVE= ?  WHERE GROUP_ID = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, groupBean.getName());
			pstmt.setString(idx++, groupBean.getActive());
			pstmt.setString(idx++, groupBean.getGroupId());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(groupBean),this);
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
	
	public boolean pwupdate(GroupBean groupBean){
		String query = "UPDATE TB_GROUP SET PASSWORD =?   WHERE GROUP_ID = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, groupBean.getPassword());
			pstmt.setString(idx++, groupBean.getGroupId());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(groupBean),this);
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
	
	public boolean insert(GroupBean groupBean){
		String query = "INSERT INTO TB_GROUP (GROUP_ID, NAME, PASSWORD,ACTIVE, REG_DATE) VALUES (? ,? ,? ,? ,SYSDATE )";
		 
				
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			
			pstmt.setString(idx++, groupBean.getGroupId());
			pstmt.setString(idx++, groupBean.getName());
			pstmt.setString(idx++, groupBean.getPassword());
			pstmt.setString(idx++, groupBean.getActive());
			
		result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(groupBean),this);
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
	
	public boolean checkMerchant(GroupMerchantBean gmBean){
		
		String query = "SELECT MERCHANT_ID FROM TB_GROUP_MERCHANT WHERE MERCHANT_ID='"+gmBean.getMerchantId()+"' ";
		String check = "";
		
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
				check = rset.getString("MERCHANT_ID");
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		if(!CommonUtil.isNullOrSpace(check)){
			return true;
		}else{
			return false;
		}
		
	}
	
	public boolean insertGroupMerchant(GroupMerchantBean gmBean){
		String query = "INSERT INTO TB_GROUP_MERCHANT (GROUP_ID, MERCHANT_ID, REG_ID, REG_DATE) VALUES (? ,? ,? ,SYSDATE )";
		 
				
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			
			pstmt.setString(idx++, gmBean.getGroupId());
			pstmt.setString(idx++, gmBean.getMerchantId());
			pstmt.setString(idx++, gmBean.getRegId());
			
		result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(gmBean),this);
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
	
	public boolean updateGroupMerchant(GroupMerchantBean gmBean){
		String query = "UPDATE TB_GROUP_MERCHANT SET GROUP_ID =? , REG_ID =?  WHERE MERCHANT_ID = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, gmBean.getGroupId());
			pstmt.setString(idx++, gmBean.getRegId());
			pstmt.setString(idx++, gmBean.getMerchantId());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(gmBean),this);
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
	
	public GroupBean getByMerchantId(String merchantId){
		String query = "SELECT GROUP_ID, NAME FROM TB_GROUP WHERE GROUP_ID=(SELECT GROUP_ID FROM TB_GROUP_MERCHANT WHERE MERCHANT_ID='"+merchantId+"')";
		
		GroupBean gBean = new GroupBean();
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
				gBean.setGroupId(rset.getString("GROUP_ID"));
				gBean.setName(rset.getString("NAME"));
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return gBean;
	}
	
	public boolean deleteMerchantId(String merchantId){
		String query = "DELETE TB_GROUP_MERCHANT WHERE MERCHANT_ID = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, merchantId);
			
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

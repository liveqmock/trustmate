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

import com.pgmate.model.db.AgentBean;
import com.pgmate.model.db.AgentMerchantBean;
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.factory.DBFactory;
import com.pgmate.model.db.factory.DBUtil;

public class AgentDAO extends DAO {

	public AgentDAO() {
	}
	
	public List<AgentBean> geByName(String agentName){
		return get(" AND NAME='"+agentName+"'");
	}
	
	
	public List<AgentBean> getByActive(String active){
		return get(" AND ACTIVE='"+active+"'");
	}
	
	public List<MerchantBean> getByAgentId(String agentId){
		MerchantDAO merchantDAO = new MerchantDAO();
		merchantDAO.pageSize = 10000;
		merchantDAO.orderBy = "ORDER BY MERCHANT_ID ASC";
		return merchantDAO.get(" AND MERCHANT_ID IN (SELECT MERCHANT_ID FROM TB_AGENT_MERCHANT WHERE AGENT_ID='"+agentId+"')");
	}
	
	public AgentBean getById(String agentId){
		List<AgentBean> list = get(" AND AGENT_ID ='"+agentId+"'");
		if(list.size() == 0){
			return new AgentBean();
		}else{
			return (AgentBean)list.get(0);
		}	
	}
	
	public List<AgentBean> get(AgentBean agentBean){
		StringBuffer sb = new StringBuffer();
		
		if(!agentBean.getAgentId().equals("")){
			return get(" AND AGENT_ID ='"+agentBean.getAgentId()+"' ");
		}else{
			if(!agentBean.getName().equals("")){
				sb.append( " AND NAME LIKE '%"+agentBean.getName()+"%' ");
			}
			if(!agentBean.getActive().equals("")){
				sb.append( " AND ACTIVE='"+agentBean.getActive()+"' ");
			}
			
			
			return get(sb.toString());
		}
		
	}
	
	public List<AgentBean> get(String subQuery){
		
		String query = "SELECT  AGENT_ID, NAME, PASSWORD, ACTIVE, BANK_CODE, BRANCH, ACCOUNT, ACCOUNT_HOLDER, REG_DATE FROM TB_AGENT "
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
		
		List<AgentBean> list = new ArrayList<AgentBean>();
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
				AgentBean agentBean = new AgentBean();
				
				agentBean.setAgentId(rset.getString("AGENT_ID"));
				agentBean.setName(CommonUtil.nToB(rset.getString("NAME")));
				agentBean.setPassword(rset.getString("PASSWORD"));
				agentBean.setActive(CommonUtil.nToB(rset.getString("ACTIVE")));
				agentBean.setBankCode(CommonUtil.nToB(rset.getString("BANK_CODE")));
				agentBean.setBranch(CommonUtil.nToB(rset.getString("BRANCH")));
				agentBean.setAccount(CommonUtil.nToB(rset.getString("ACCOUNT")));
				agentBean.setAccountHolder(CommonUtil.nToB(rset.getString("ACCOUNT_HOLDER")));
				agentBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(agentBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public boolean update(AgentBean agentBean){
		String query = "UPDATE TB_AGENT SET NAME =? , ACTIVE= ? , BANK_CODE= ?, BRANCH= ?, ACCOUNT= ?, ACCOUNT_HOLDER= ? WHERE AGENT_ID = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, agentBean.getName());
			pstmt.setString(idx++, agentBean.getActive());
			pstmt.setString(idx++, agentBean.getBankCode());
			pstmt.setString(idx++, agentBean.getBranch());
			pstmt.setString(idx++, agentBean.getAccount());
			pstmt.setString(idx++, agentBean.getAccountHolder());
			pstmt.setString(idx++, agentBean.getAgentId());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(agentBean),this);
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
	
	public boolean pwupdate(AgentBean agentBean){
		String query = "UPDATE TB_AGENT SET PASSWORD =?   WHERE AGENT_ID = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, agentBean.getPassword());
			pstmt.setString(idx++, agentBean.getAgentId());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(agentBean),this);
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
	
	public boolean insert(AgentBean agentBean){
		String query = "INSERT INTO TB_AGENT (AGENT_ID, NAME, PASSWORD,ACTIVE, BANK_CODE, BRANCH, ACCOUNT, ACCOUNT_HOLDER, REG_DATE) VALUES (? ,? ,? ,? ,? ,? ,? ,?,SYSDATE )";
		 
				
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			
			pstmt.setString(idx++, agentBean.getAgentId());
			pstmt.setString(idx++, agentBean.getName());
			pstmt.setString(idx++, agentBean.getPassword());
			pstmt.setString(idx++, agentBean.getActive());
			pstmt.setString(idx++, agentBean.getBankCode());
			pstmt.setString(idx++, agentBean.getBranch());
			pstmt.setString(idx++, agentBean.getAccount());
			pstmt.setString(idx++, agentBean.getAccountHolder());
			
		result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(agentBean),this);
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
	
	public boolean checkMerchant(AgentMerchantBean gmBean){
		
		String query = "SELECT MERCHANT_ID FROM TB_AGENT_MERCHANT WHERE MERCHANT_ID='"+gmBean.getMerchantId()+"' ";
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
	
	public boolean insertAgentMerchant(AgentMerchantBean amBean){
		String query = "INSERT INTO TB_AGENT_MERCHANT (AGENT_ID, MERCHANT_ID, REG_ID, REG_DATE) VALUES (? ,? ,? ,SYSDATE )";
		 
				
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			
			pstmt.setString(idx++, amBean.getAgentId());
			pstmt.setString(idx++, amBean.getMerchantId());
			pstmt.setString(idx++, amBean.getRegId());
			
		result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(amBean),this);
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
	
	public boolean updateGroupMerchant(AgentMerchantBean gmBean){
		String query = "UPDATE TB_AGENT_MERCHANT SET AGENT_ID =? , REG_ID =?  WHERE MERCHANT_ID = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, gmBean.getAgentId());
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
	
	public AgentBean getByMerchantId(String merchantId){
		String query = "SELECT AGENT_ID, NAME FROM TB_AGENT WHERE AGENT_ID=(SELECT AGENT_ID FROM TB_AGENT_MERCHANT WHERE MERCHANT_ID='"+merchantId+"')";
		
		AgentBean gBean = new AgentBean();
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
				gBean.setAgentId(rset.getString("AGENT_ID"));
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
		String query = "DELETE TB_AGENT_MERCHANT WHERE MERCHANT_ID = ?";


		
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
	
	public List<String> getMerchantList(String agentId,String merchantId){
		
		String query = "SELECT MERCHANT_ID FROM TB_AGENT_MERCHANT WHERE AGENT_ID='"+agentId+"' AND MERCHANT_ID LIKE '%"+merchantId+"%' ORDER BY MERCHANT_ID DESC";
			
		
		
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
				list.add(rset.getString("MERCHANT_ID"));
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

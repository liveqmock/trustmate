package com.pgmate.model.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import biz.trustnet.common.db.DBUtil;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.BeanUtil;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.AgentBillBean;
import com.pgmate.model.db.factory.DBFactory;

/**
 * @author Administrator
 *
 */
public class AgentBillDAO extends DAO{

	public AgentBillDAO(){
	}
	
	public AgentBillBean getByMerchantId(String merchantId){
		List<AgentBillBean> list = get(" AND MERCHANT_ID = '"+merchantId+"' ");
		if(list.size() == 0){
			return new AgentBillBean();
		}else{
			return (AgentBillBean)list.get(0);
		}
	}
	
	public List<AgentBillBean> getByAgentId(String agentId){
		List<AgentBillBean> list = get(" AND AGENT_ID = '"+agentId+"' ");
		return list;
	}
	
	public AgentBillBean getByAgentIdMerchantId(String agentId,String merchantId){
		List<AgentBillBean> list = get(" AND AGENT_ID = '"+agentId+"' AND MERCHANT_ID = '"+merchantId+"' ");
		if(list.size() == 0){
			return new AgentBillBean();
		}else{
			return (AgentBillBean)list.get(0);
		}
	}
	
	public List<AgentBillBean> get(AgentBillBean agentBillBean){
		StringBuffer sb = new StringBuffer();
		

		if(!agentBillBean.getAgentId().equals("")){
			sb.append(" AND AGENT_ID = '"+agentBillBean.getAgentId()+"' ");
		}
		
		if(!agentBillBean.getMerchantId().equals("")){
			sb.append(" AND MERCHANT_ID = '"+agentBillBean.getMerchantId()+"' ");
		}
		
		if(!agentBillBean.getActive().equals("")){
			sb.append(" AND ACTIVE = '"+agentBillBean.getActive()+"' ");
		}
		
		return get(sb.toString());
	}
	
	public List<AgentBillBean> get(String subQuery){
		String query = "SELECT AGENT_ID,MERCHANT_ID,VISAMASTER,JCBAMEX,TRANSACTION,REFUND,CHARGEBACK,MANAGEMENT,SETUP_FEE,BANK_TRANSFER,VAT,ACTIVE,REG_DATE "
			+ " FROM TB_AGENT_BILL WHERE AGENT_ID IS NOT NULL " + subQuery;
		
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
		
		List<AgentBillBean> list = new ArrayList<AgentBillBean>();
		
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
				AgentBillBean agentBillBean = new AgentBillBean();
				agentBillBean.setAgentId(rset.getString("AGENT_ID"));
				agentBillBean.setMerchantId(rset.getString("MERCHANT_ID"));
				agentBillBean.setVisamaster(rset.getDouble("VISAMASTER"));
				agentBillBean.setJcbamex(rset.getDouble("JCBAMEX"));
				agentBillBean.setTransaction(rset.getDouble("TRANSACTION"));
				agentBillBean.setRefund(rset.getDouble("REFUND"));
				agentBillBean.setChargeback(rset.getDouble("CHARGEBACK"));
				agentBillBean.setManagement(rset.getDouble("MANAGEMENT"));
				agentBillBean.setSetupFee(rset.getDouble("SETUP_FEE"));
				agentBillBean.setBankTransfer(rset.getDouble("BANK_TRANSFER"));
				agentBillBean.setVat(rset.getDouble("VAT"));
				agentBillBean.setActive(rset.getString("ACTIVE"));
				agentBillBean.setRegDate(rset.getTimestamp("REG_DATE"));
				
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(agentBillBean);
			}
		}catch(SQLException e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn, pstmt, rset);
		}
		return list;
	}
	
	public List<AgentBillBean> getView(String subQuery){
		String query = "SELECT A.AGENT_ID AS AGENT_ID,A.MERCHANT_ID AS MERCHANT_ID, C.NAME AS NAME,VISAMASTER,JCBAMEX,TRANSACTION,REFUND,"
					 + "CHARGEBACK,MANAGEMENT,SETUP_FEE,BANK_TRANSFER,VAT,B.ACTIVE,B.REG_DATE "
					 + "FROM TB_AGENT_MERCHANT A LEFT OUTER JOIN TB_AGENT_BILL B ON A.MERCHANT_ID=B.MERCHANT_ID "
					 + "LEFT OUTER JOIN TB_MERCHANT C ON A.MERCHANT_ID=C.MERCHANT_ID "
					 + "WHERE A.AGENT_ID IS NOT NULL "
					 + subQuery;
		
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
		
		List<AgentBillBean> list = new ArrayList<AgentBillBean>();
		
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
				AgentBillBean agentBillBean = new AgentBillBean();
				agentBillBean.setAgentId(rset.getString("AGENT_ID"));
				agentBillBean.setMerchantId(rset.getString("MERCHANT_ID"));
				agentBillBean.setTemp1String(rset.getString("NAME"));
				agentBillBean.setVisamaster(rset.getDouble("VISAMASTER"));
				agentBillBean.setJcbamex(rset.getDouble("JCBAMEX"));
				agentBillBean.setTransaction(rset.getDouble("TRANSACTION"));
				agentBillBean.setRefund(rset.getDouble("REFUND"));
				agentBillBean.setChargeback(rset.getDouble("CHARGEBACK"));
				agentBillBean.setManagement(rset.getDouble("MANAGEMENT"));
				agentBillBean.setSetupFee(rset.getDouble("SETUP_FEE"));
				agentBillBean.setBankTransfer(rset.getDouble("BANK_TRANSFER"));
				agentBillBean.setVat(rset.getDouble("VAT"));
				agentBillBean.setActive(rset.getString("ACTIVE"));
				agentBillBean.setRegDate(rset.getTimestamp("REG_DATE"));
				
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(agentBillBean);
			}
		}catch(SQLException e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn, pstmt, rset);
		}
		return list;
	}
	
	public boolean update(AgentBillBean agentBillBean){
		String query = "UPDATE TB_AGENT_BILL SET  VISAMASTER = ?, JCBAMEX = ?, TRANSACTION = ?, REFUND = ?, CHARGEBACK = ?,  MANAGEMENT = ?,SETUP_FEE=?, BANK_TRANSFER = ?, VAT = ?, ACTIVE = ? "
					 + " WHERE AGENT_ID = ? ";
		backup(agentBillBean);
		int result 				= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setDouble(idx++, agentBillBean.getVisamaster());
			pstmt.setDouble(idx++, agentBillBean.getJcbamex());
			pstmt.setDouble(idx++, agentBillBean.getTransaction());
			pstmt.setDouble(idx++, agentBillBean.getRefund());
			pstmt.setDouble(idx++, agentBillBean.getChargeback());
			pstmt.setDouble(idx++, agentBillBean.getManagement());
			pstmt.setDouble(idx++, agentBillBean.getSetupFee());
			pstmt.setDouble(idx++, agentBillBean.getBankTransfer());
			pstmt.setDouble(idx++, agentBillBean.getVat());
			pstmt.setString(idx++, agentBillBean.getActive());
			pstmt.setString(idx++, agentBillBean.getAgentId());
			result = pstmt.executeUpdate();
			conn.commit();
		}catch(SQLException e){
			Log.debug("log.sql",BeanUtil.beanToString(agentBillBean),this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(pstmt);
			db.close(conn);
		}
		
		if(result > -1){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean insert(AgentBillBean agentBillBean){
		String query = "INSERT INTO TB_AGENT_BILL(AGENT_ID,MERCHANT_ID,VISAMASTER,JCBAMEX,TRANSACTION,REFUND,CHARGEBACK,MANAGEMENT,SETUP_FEE,BANK_TRANSFER,VAT,ACTIVE,REG_DATE) "
					 + "VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE) ";

		int result 				= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			int idx = 1;
			
			pstmt.setString(idx++, agentBillBean.getAgentId());
			pstmt.setString(idx++, agentBillBean.getMerchantId());
			pstmt.setDouble(idx++, agentBillBean.getVisamaster());
			pstmt.setDouble(idx++, agentBillBean.getJcbamex());
			pstmt.setDouble(idx++, agentBillBean.getTransaction());
			pstmt.setDouble(idx++, agentBillBean.getRefund());
			pstmt.setDouble(idx++, agentBillBean.getChargeback());
			pstmt.setDouble(idx++, agentBillBean.getManagement());
			pstmt.setDouble(idx++, agentBillBean.getSetupFee());
			pstmt.setDouble(idx++, agentBillBean.getBankTransfer());
			pstmt.setDouble(idx++, agentBillBean.getVat());
			pstmt.setString(idx++, agentBillBean.getActive());
			result = pstmt.executeUpdate();
			conn.commit();
		}catch(SQLException e){
			Log.debug("log.sql",BeanUtil.beanToString(agentBillBean),this);
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
	public void backup(AgentBillBean agentBillBean){
		String query = "INSERT INTO TB_AGENT_BILL_HIS SELECT * FROM TB_AGENT_BILL WHERE AGENT_ID ='"+agentBillBean.getAgentId()+"'";
		try{
			DBFactory.getInstance().statementExecuteUpdate(query);
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query, this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}
	}
}

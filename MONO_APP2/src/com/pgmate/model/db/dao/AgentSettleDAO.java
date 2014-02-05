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

import com.pgmate.model.db.AgentSettleBean;
import com.pgmate.model.db.GSIBean;
import com.pgmate.model.db.factory.DBFactory;

/**
 * @author Administrator
 *
 */
public class AgentSettleDAO extends DAO {

	private GSIBean gsiBean = new GSIBean();
	
	public AgentSettleDAO(){
	}
	
	
	
	public AgentSettleBean getBySettleId(String settleId){
		List<AgentSettleBean> list = get(" AND SETTLE_ID='"+settleId+"' ");
		if(list.size() == 0){
			return new AgentSettleBean();
		}else{
			return (AgentSettleBean)list.get(0);
		}
	}
	
	public GSIBean getSumAmount(){
		return gsiBean;
	}
	
	
	
	public List<AgentSettleBean> get(AgentSettleBean agentSettleBean){
		StringBuffer sb = new StringBuffer();
		if(!agentSettleBean.getSettleId().equals("")){
			sb.append(" AND SETTLE_ID = '"+agentSettleBean.getSettleId()+"' ");
		}
		if(!agentSettleBean.getMerchantId().equals("")){
			sb.append(" AND AGENT_ID = '"+agentSettleBean.getAgentId()+"' ");
		}
		if(!agentSettleBean.getMerchantId().equals("")){
			sb.append(" AND MERCHANT_ID = '"+agentSettleBean.getMerchantId()+"' ");
		}

		if(agentSettleBean.getSettleDate() != null){
			sb.append(" AND TO_CHAR(SETTLE_DATE,'yyyyMMdd') = '"+CommonUtil.convertTimestampToString(agentSettleBean.getSettleDate(), "yyyyMMdd")+"' ");
		}
		if(!agentSettleBean.getTemp2String().equals("")){
			sb.append(" AND STATUS != '"+agentSettleBean.getTemp2String()+"' ");
		}
		if(!agentSettleBean.getTemp3String().equals("")){
			sb.append(" AND SUBSTR(MERCHANT_ID,0,4) = '"+agentSettleBean.getTemp3String().substring(0,4)+"' ");
		}
		
		if(!agentSettleBean.getPublicGroupId().equals("")){
			sb.append(" AND MERCHANT_ID IN (SELECT MERCHANT_ID FROM TB_GROUP_MERCHANT WHERE GROUP_ID='"+agentSettleBean.getPublicGroupId()+"')");
		}
	
		
		return get(sb.toString());
	}
	
	public List<AgentSettleBean> get(String subQuery){
		setAmount(subQuery);
		String query = "SELECT SETTLE_ID,AGENT_ID,MERCHANT_ID,START_DAY,END_DAY, "
			+ "SETTLE_DATE,BANK_INFO,ACCOUNT,TOT_AMT,TOT_FEE,TOT_SETTLE, "
			+ "TRN_VM_CNT,TRN_VM_AMT,TRN_VM_FEE,TRN_VM_RATE,TRN_JA_CNT,TRN_JA_AMT,TRN_JA_FEE,TRN_JA_RATE,CB_CNT,CB_AMT,CB_FEE,CB_RATE, " 
			+ "REFUND_VM_CNT,REFUND_VM_AMT,REFUND_VM_FEE,REFUND_VM_RATE,REFUND_JA_CNT,REFUND_JA_AMT,REFUND_JA_FEE,REFUND_JA_RATE, "
			+ "MANAGE_FEE,VAN_CNT,VAN_FEE,VAN_RATE,TRANSFE_FEE,VAT_AMT,ETC_NAME,ETC_AMT,SETTLE_IN,REG_DATE " 
			+ "FROM TB_AGENT_SETTLE WHERE SETTLE_ID IS NOT NULL " + subQuery;
		
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
		List<AgentSettleBean> list = new ArrayList<AgentSettleBean>();
		
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
				AgentSettleBean agentSettleBean = new AgentSettleBean();
				agentSettleBean.setSettleId(rset.getString("SETTLE_ID"));
				agentSettleBean.setAgentId(rset.getString("AGENT_ID"));
				agentSettleBean.setMerchantId(rset.getString("MERCHANT_ID"));
				agentSettleBean.setStartDay(rset.getString("START_DAY"));
				agentSettleBean.setEndDay(rset.getString("END_DAY"));
				agentSettleBean.setSettleDate(rset.getTimestamp("SETTLE_DATE"));
				agentSettleBean.setBankInfo(rset.getString("BANK_INFO"));
				agentSettleBean.setAccount(rset.getString("ACCOUNT"));
				agentSettleBean.setTotAmt(rset.getDouble("TOT_AMT"));
				agentSettleBean.setTotFee(rset.getDouble("TOT_FEE"));
				agentSettleBean.setTotSettle(rset.getDouble("TOT_SETTLE"));
				agentSettleBean.setTrnVmCnt(rset.getDouble("TRN_VM_CNT"));
				agentSettleBean.setTrnVmAmt(rset.getDouble("TRN_VM_AMT"));
				agentSettleBean.setTrnVmFee(rset.getDouble("TRN_VM_FEE"));
				agentSettleBean.setTrnVmRate(rset.getDouble("TRN_VM_RATE"));
				agentSettleBean.setTrnJaCnt(rset.getDouble("TRN_JA_CNT"));
				agentSettleBean.setTrnJaAmt(rset.getDouble("TRN_JA_AMT"));
				agentSettleBean.setTrnJaFee(rset.getDouble("TRN_JA_FEE"));
				agentSettleBean.setTrnJaRate(rset.getDouble("TRN_JA_RATE"));
				agentSettleBean.setCbCnt(rset.getDouble("CB_CNT"));
				agentSettleBean.setCbAmt(rset.getDouble("CB_AMT"));
				agentSettleBean.setCbFee(rset.getDouble("CB_FEE"));
				agentSettleBean.setCbRate(rset.getDouble("CB_RATE"));
				agentSettleBean.setRefundVmCnt(rset.getDouble("REFUND_VM_CNT"));
				agentSettleBean.setRefundVmAmt(rset.getDouble("REFUND_VM_AMT"));
				agentSettleBean.setRefundVmFee(rset.getDouble("REFUND_VM_FEE"));
				agentSettleBean.setRefundVmRate(rset.getDouble("REFUND_VM_RATE"));
				agentSettleBean.setRefundJaCnt(rset.getDouble("REFUND_JA_CNT"));
				agentSettleBean.setRefundJaAmt(rset.getDouble("REFUND_JA_AMT"));
				agentSettleBean.setRefundJaFee(rset.getDouble("REFUND_JA_FEE"));
				agentSettleBean.setRefundJaRate(rset.getDouble("REFUND_JA_RATE"));
				agentSettleBean.setManageFee(rset.getDouble("MANAGE_FEE"));
				agentSettleBean.setVanCnt(rset.getDouble("VAN_CNT"));
				agentSettleBean.setVanFee(rset.getDouble("VAN_FEE"));
				agentSettleBean.setVanRate(rset.getDouble("VAN_RATE"));
				agentSettleBean.setTransfeFee(rset.getDouble("TRANSFE_FEE"));
				agentSettleBean.setVatAmt(rset.getDouble("VAT_AMT"));
				agentSettleBean.setEtcName(rset.getString("ETC_NAME"));
				agentSettleBean.setEtcAmt(rset.getDouble("ETC_AMT"));
				agentSettleBean.setSettleIn(rset.getString("SETTLE_IN"));
				agentSettleBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(agentSettleBean);
			}
		}catch(SQLException e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn, pstmt, rset);
		}
		return list;
	}
	
	
	public void setAmount(String subQuery){
		
		String query = "SELECT SUM(TOT_AMT) AS AMT ,SUM(TOT_FEE) AS FEE,SUM(TOT_SETTLE) AS SETTLE " 
			+ "FROM TB_AGENT_SETTLE WHERE SETTLE_ID IS NOT NULL " + subQuery;
		
		if(super.regStartDate != null){
			query +=" AND REG_DATE >="+new DBUtil().getToDate(regStartDate);
		}
		if(super.regEndDate != null){
			query +=" AND REG_DATE <"+new DBUtil().getToDate(regEndDate);
		}		
		
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
				gsiBean.setTemp1Double(rset.getLong("AMT"));
				gsiBean.setTemp2Double(rset.getLong("FEE"));
				gsiBean.setTemp3Double(rset.getLong("SETTLE"));
			}
		}catch(SQLException e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn, pstmt, rset);
		}
		
	}
	
	public boolean insert(AgentSettleBean agentSettleBean){
		String query = "INSERT INTO TB_AGENT_SETTLE (SETTLE_ID,AGENT_ID,MERCHANT_ID,START_DAY,END_DAY, "
			+ "SETTLE_DATE,BANK_INFO,ACCOUNT,TOT_AMT,TOT_FEE,TOT_SETTLE, "
			+ "TRN_VM_CNT,TRN_VM_AMT,TRN_VM_FEE,TRN_VM_RATE,TRN_JA_CNT,TRN_JA_AMT,TRN_JA_FEE,TRN_JA_RATE,CB_CNT,CB_AMT,CB_FEE,CB_RATE, "
			+ "REFUND_VM_CNT,REFUND_VM_AMT,REFUND_VM_FEE,REFUND_VM_RATE,REFUND_JA_CNT,REFUND_JA_AMT,REFUND_JA_FEE,REFUND_JA_RATE, "
			+ "MANAGE_FEE,VAN_CNT,VAN_FEE,VAN_RATE,TRANSFE_FEE,VAT_AMT,ETC_NAME,ETC_AMT,SETTLE_IN,REG_DATE ) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE)";
		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setString(idx++, agentSettleBean.getSettleId());
			pstmt.setString(idx++, agentSettleBean.getAgentId());
			pstmt.setString(idx++, agentSettleBean.getMerchantId());
			pstmt.setString(idx++, agentSettleBean.getStartDay());
			pstmt.setString(idx++, agentSettleBean.getEndDay());
			pstmt.setTimestamp(idx++, agentSettleBean.getSettleDate());
			pstmt.setString(idx++, agentSettleBean.getBankInfo());
			pstmt.setString(idx++, agentSettleBean.getAccount());
			pstmt.setDouble(idx++, agentSettleBean.getTotAmt());
			pstmt.setDouble(idx++, agentSettleBean.getTotFee());
			pstmt.setDouble(idx++, agentSettleBean.getTotSettle());
			pstmt.setDouble(idx++, agentSettleBean.getTrnVmCnt());
			pstmt.setDouble(idx++, agentSettleBean.getTrnVmAmt());
			pstmt.setDouble(idx++, agentSettleBean.getTrnVmFee());
			pstmt.setDouble(idx++, agentSettleBean.getTrnVmRate());
			pstmt.setDouble(idx++, agentSettleBean.getTrnJaCnt());
			pstmt.setDouble(idx++, agentSettleBean.getTrnJaAmt());
			pstmt.setDouble(idx++, agentSettleBean.getTrnJaFee());
			pstmt.setDouble(idx++, agentSettleBean.getTrnJaRate());
			pstmt.setDouble(idx++, agentSettleBean.getCbCnt());
			pstmt.setDouble(idx++, agentSettleBean.getCbAmt());
			pstmt.setDouble(idx++, agentSettleBean.getCbFee());
			pstmt.setDouble(idx++, agentSettleBean.getCbRate());
			pstmt.setDouble(idx++, agentSettleBean.getRefundVmCnt());
			pstmt.setDouble(idx++, agentSettleBean.getRefundVmAmt());
			pstmt.setDouble(idx++, agentSettleBean.getRefundVmFee());
			pstmt.setDouble(idx++, agentSettleBean.getRefundVmRate());
			pstmt.setDouble(idx++, agentSettleBean.getRefundJaCnt());
			pstmt.setDouble(idx++, agentSettleBean.getRefundJaAmt());
			pstmt.setDouble(idx++, agentSettleBean.getRefundJaFee());
			pstmt.setDouble(idx++, agentSettleBean.getRefundJaRate());
			pstmt.setDouble(idx++, agentSettleBean.getManageFee());
			pstmt.setDouble(idx++, agentSettleBean.getVanCnt());
			pstmt.setDouble(idx++, agentSettleBean.getVanFee());
			pstmt.setDouble(idx++, agentSettleBean.getVanRate());
			pstmt.setDouble(idx++, agentSettleBean.getTransfeFee());
			pstmt.setDouble(idx++, agentSettleBean.getVatAmt());
			pstmt.setString(idx++, agentSettleBean.getEtcName());
			pstmt.setDouble(idx++, agentSettleBean.getEtcAmt());
			pstmt.setString(idx++, agentSettleBean.getSettleIn());
			result = pstmt.executeUpdate();
			conn.commit();
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(agentSettleBean),this);
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
	
	public boolean update(AgentSettleBean agentSettleBean){
		String query = "UPDATE TB_AGENT_SETTLE SET AGENT_ID=?,MERCHANT_ID=?,START_DAY=?,END_DAY=?, "
			+ "SETTLE_DATE=?,BANK_INFO=?,ACCOUNT=?,TOT_AMT=?,TOT_FEE=?,TOT_SETTLE=?, "
			+ "TRN_VM_CNT=?,TRN_VM_AMT=?,TRN_VM_FEE=?,TRN_VM_RATE=?,TRN_JA_CNT=?,TRN_JA_AMT=?,TRN_JA_FEE=?,TRN_JA_RATE=?, "
			+ "CB_BILL_ID=?,CB_CNT=?,CB_AMT=?,CB_FEE=?,CB_RATE=?,REFUND_VM_CNT=?, "
			+ "REFUND_VM_AMT=?,REFUND_VM_FEE=?,REFUND_VM_RATE=?,REFUND_JA_CNT=?,REFUND_JA_AMT=?,REFUND_JA_FEE=?,REFUND_JA_RATE=? "
			+ "MANAGE_FEE=?,VAN_CNT=?,VAN_FEE=?,VAN_RATE=?,TRANSFE_FEE=?,VAT_AMT=?,ETC_NAME=?,ETC_AMT=?,SETTLE_IN=? "
			+ "WHERE SETTLE_ID=? ";
		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setString(idx++, agentSettleBean.getAgentId());
			pstmt.setString(idx++, agentSettleBean.getMerchantId());
			pstmt.setString(idx++, agentSettleBean.getStartDay());
			pstmt.setString(idx++, agentSettleBean.getEndDay());
			pstmt.setTimestamp(idx++, agentSettleBean.getSettleDate());
			pstmt.setString(idx++, agentSettleBean.getBankInfo());
			pstmt.setString(idx++, agentSettleBean.getAccount());
			pstmt.setDouble(idx++, agentSettleBean.getTotAmt());
			pstmt.setDouble(idx++, agentSettleBean.getTotFee());
			pstmt.setDouble(idx++, agentSettleBean.getTotSettle());
			pstmt.setDouble(idx++, agentSettleBean.getTrnVmCnt());
			pstmt.setDouble(idx++, agentSettleBean.getTrnVmAmt());
			pstmt.setDouble(idx++, agentSettleBean.getTrnVmFee());
			pstmt.setDouble(idx++, agentSettleBean.getTrnVmRate());
			pstmt.setDouble(idx++, agentSettleBean.getTrnJaCnt());
			pstmt.setDouble(idx++, agentSettleBean.getTrnJaAmt());
			pstmt.setDouble(idx++, agentSettleBean.getTrnJaFee());
			pstmt.setDouble(idx++, agentSettleBean.getTrnJaRate());
			pstmt.setDouble(idx++, agentSettleBean.getCbCnt());
			pstmt.setDouble(idx++, agentSettleBean.getCbAmt());
			pstmt.setDouble(idx++, agentSettleBean.getCbFee());
			pstmt.setDouble(idx++, agentSettleBean.getCbRate());
			pstmt.setDouble(idx++, agentSettleBean.getRefundVmCnt());
			pstmt.setDouble(idx++, agentSettleBean.getRefundVmAmt());
			pstmt.setDouble(idx++, agentSettleBean.getRefundVmFee());
			pstmt.setDouble(idx++, agentSettleBean.getRefundVmRate());
			pstmt.setDouble(idx++, agentSettleBean.getRefundJaCnt());
			pstmt.setDouble(idx++, agentSettleBean.getRefundJaAmt());
			pstmt.setDouble(idx++, agentSettleBean.getRefundJaFee());
			pstmt.setDouble(idx++, agentSettleBean.getRefundJaRate());
			pstmt.setDouble(idx++, agentSettleBean.getManageFee());
			pstmt.setDouble(idx++, agentSettleBean.getVanCnt());
			pstmt.setDouble(idx++, agentSettleBean.getVanFee());
			pstmt.setDouble(idx++, agentSettleBean.getVanRate());
			pstmt.setDouble(idx++, agentSettleBean.getTransfeFee());
			pstmt.setDouble(idx++, agentSettleBean.getVatAmt());
			pstmt.setString(idx++, agentSettleBean.getEtcName());
			pstmt.setDouble(idx++, agentSettleBean.getEtcAmt());
			pstmt.setString(idx++, agentSettleBean.getSettleIn());
			pstmt.setString(idx++, agentSettleBean.getSettleId());
			
			result = pstmt.executeUpdate();
			conn.commit();
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(agentSettleBean),this);
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
	
	
	public boolean updateByEtc(AgentSettleBean agentSettleBean){
		String query = "UPDATE TB_AGENT_SETTLE SET TOT_FEE = ?, TOT_SETTLE = ?, ETC_NAME = ?, ETC_AMT = ? WHERE SETTLE_ID = ? ";
		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setDouble(idx++, agentSettleBean.getTotFee());
			pstmt.setDouble(idx++, agentSettleBean.getTotSettle());
			pstmt.setString(idx++, agentSettleBean.getEtcName());
			pstmt.setDouble(idx++, agentSettleBean.getEtcAmt());
			pstmt.setString(idx++, agentSettleBean.getSettleId());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(agentSettleBean),this);
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
	
	public List<String> getAbleSettleDate(){
		
		String query = "SELECT TO_CHAR(SETTLE_DATE,'YYYYMMDD') AS SETTLE_LIST FROM TB_AGENT_SETTLE GROUP BY TO_CHAR(SETTLE_DATE,'YYYYMMDD') ORDER BY TO_CHAR(SETTLE_DATE,'YYYYMMDD') DESC";
		
		
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
				list.add(rset.getString("SETTLE_LIST"));
			}
		}catch(SQLException e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn, pstmt, rset);
		}
		return list;
	}

}

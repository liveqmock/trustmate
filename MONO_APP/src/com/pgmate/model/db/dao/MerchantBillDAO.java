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

import com.pgmate.model.db.MerchantBillBean;
import com.pgmate.model.db.factory.DBFactory;

/**
 * @author Administrator
 *
 */
public class MerchantBillDAO extends DAO{

	public MerchantBillDAO(){
	}
	
	public MerchantBillBean getByMerchantId(String merchantId){
		List<MerchantBillBean> list = get(" AND MERCHANT_ID = '"+merchantId+"' ");
		if(list.size() == 0){
			return new MerchantBillBean();
		}else{
			return (MerchantBillBean)list.get(0);
		}
	}
	
	public List<MerchantBillBean> getByPeriod(String period){
		return get(" AND PERIOD = '"+period+"' ");
	}
	
	public List<MerchantBillBean> getByPeriodAndActive(String period){
		return get(" AND PERIOD = '"+period+"' AND ACTIVE='Y'");
	}
	
	public List<MerchantBillBean> get(MerchantBillBean mbillBean){
		StringBuffer sb = new StringBuffer();
		if(!mbillBean.getMerchantId().equals("")){
			sb.append(" AND MERCHANT_ID = '"+mbillBean.getMerchantId()+"' ");
		}
		
		if(!mbillBean.getPeriod().equals("")){
			sb.append(" AND PERIOD = '"+mbillBean.getPeriod()+"' ");
		}
		if(!mbillBean.getActive().equals("")){
			sb.append(" AND ACTIVE = '"+mbillBean.getActive()+"' ");
		}
		if(!mbillBean.getBankCode().equals("")){
			sb.append(" AND BANK_CODE = '"+mbillBean.getBankCode()+"' ");
		}
		if(!mbillBean.getBranch().equals("")){
			sb.append(" AND BRANCH = '"+mbillBean.getBranch()+"' ");
		}
		if(!mbillBean.getAccount().equals("")){
			sb.append(" AND ACCOUNT = '"+mbillBean.getAccount()+"' ");
		}
		if(!mbillBean.getAccountHolder().equals("")){
			sb.append(" AND ACCOUNT_HOLDER = '"+mbillBean.getAccountHolder()+"' ");
		}
	
		return get(sb.toString());
	}
	
	public List<MerchantBillBean> get(String subQuery){
		String query = "SELECT MERCHANT_ID, PERIOD, VISAMASTER, JCBAMEX, TRANSACTION, REFUND, CHARGEBACK, MANAGEMENT,SETUP_FEE, BANK_TRANSFER, DEPOSIT, VAT, ACTIVE, BANK_CODE, BRANCH, ACCOUNT, ACCOUNT_HOLDER,  "
			+ " REG_DATE FROM TB_MERCHANT_BILL WHERE MERCHANT_ID IS NOT NULL " + subQuery;
		
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
		
		List<MerchantBillBean> list = new ArrayList<MerchantBillBean>();
		
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
				MerchantBillBean mbillBean = new MerchantBillBean();
				mbillBean.setMerchantId(rset.getString("MERCHANT_ID"));
				
				mbillBean.setPeriod(rset.getString("PERIOD"));
				mbillBean.setVisamaster(rset.getDouble("VISAMASTER"));
				mbillBean.setJcbamex(rset.getDouble("JCBAMEX"));
				mbillBean.setTransaction(rset.getDouble("TRANSACTION"));
				mbillBean.setRefund(rset.getDouble("REFUND"));
				mbillBean.setChargeback(rset.getDouble("CHARGEBACK"));
				
				mbillBean.setManagement(rset.getDouble("MANAGEMENT"));
				mbillBean.setSetupFee(rset.getDouble("SETUP_FEE"));
				mbillBean.setBankTransfer(rset.getDouble("BANK_TRANSFER"));
				
				mbillBean.setDeposit(rset.getDouble("DEPOSIT"));
				mbillBean.setVat(rset.getDouble("VAT"));
				mbillBean.setActive(rset.getString("ACTIVE"));
				mbillBean.setBankCode(rset.getString("BANK_CODE"));
				mbillBean.setBranch(rset.getString("BRANCH"));
				mbillBean.setAccount(rset.getString("ACCOUNT"));
				mbillBean.setAccountHolder(rset.getString("ACCOUNT_HOLDER"));
				
				mbillBean.setRegDate(rset.getTimestamp("REG_DATE"));
				
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(mbillBean);
			}
		}catch(SQLException e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn, pstmt, rset);
		}
		return list;
	}
	
	public boolean update(MerchantBillBean mbillBean){
		String query = "UPDATE TB_MERCHANT_BILL SET  PERIOD = ?, VISAMASTER = ?, JCBAMEX = ?, TRANSACTION = ?, REFUND = ?, CHARGEBACK = ?,  MANAGEMENT = ?,SETUP_FEE=?, BANK_TRANSFER = ?, DEPOSIT = ?, VAT = ?, ACTIVE = ?, BANK_CODE = ?, BRANCH = ?, ACCOUNT = ?, ACCOUNT_HOLDER = ? "
					 + " WHERE MERCHANT_ID = ? ";
		backup(mbillBean);
		int result 				= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			
			pstmt.setString(idx++, mbillBean.getPeriod());
			pstmt.setDouble(idx++, mbillBean.getVisamaster());
			pstmt.setDouble(idx++, mbillBean.getJcbamex());
			pstmt.setDouble(idx++, mbillBean.getTransaction());
			pstmt.setDouble(idx++, mbillBean.getRefund());
			pstmt.setDouble(idx++, mbillBean.getChargeback());
			pstmt.setDouble(idx++, mbillBean.getManagement());
			pstmt.setDouble(idx++, mbillBean.getSetupFee());
			
			pstmt.setDouble(idx++, mbillBean.getBankTransfer());
			pstmt.setDouble(idx++, mbillBean.getDeposit());
			pstmt.setDouble(idx++, mbillBean.getVat());
			pstmt.setString(idx++, mbillBean.getActive());
			pstmt.setString(idx++, mbillBean.getBankCode());
			pstmt.setString(idx++, mbillBean.getBranch());
			pstmt.setString(idx++, mbillBean.getAccount());
			pstmt.setString(idx++, mbillBean.getAccountHolder());
			pstmt.setString(idx++, mbillBean.getMerchantId());
			result = pstmt.executeUpdate();
			conn.commit();
		}catch(SQLException e){
			Log.debug("log.sql",BeanUtil.beanToString(mbillBean),this);
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
	
	public boolean insert(MerchantBillBean mbillBean){
		String query = "INSERT INTO TB_MERCHANT_BILL(MERCHANT_ID, PERIOD, VISAMASTER, JCBAMEX, TRANSACTION, REFUND, CHARGEBACK, MANAGEMENT,SETUP_FEE, BANK_TRANSFER, DEPOSIT, VAT, ACTIVE, BANK_CODE, BRANCH, ACCOUNT, ACCOUNT_HOLDER, REG_DATE ) "
					 + "VALUES ( ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?, SYSDATE) ";

		int result 				= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			int idx = 1;
			
			pstmt.setString(idx++, mbillBean.getMerchantId());
			pstmt.setString(idx++, mbillBean.getPeriod());
			pstmt.setDouble(idx++, mbillBean.getVisamaster());
			pstmt.setDouble(idx++, mbillBean.getJcbamex());
			pstmt.setDouble(idx++, mbillBean.getTransaction());
			pstmt.setDouble(idx++, mbillBean.getRefund());
			pstmt.setDouble(idx++, mbillBean.getChargeback());
			pstmt.setDouble(idx++, mbillBean.getManagement());
			pstmt.setDouble(idx++, mbillBean.getSetupFee());
			
			pstmt.setDouble(idx++, mbillBean.getBankTransfer());
			pstmt.setDouble(idx++, mbillBean.getDeposit());
			pstmt.setDouble(idx++, mbillBean.getVat());
			pstmt.setString(idx++, mbillBean.getActive());
			pstmt.setString(idx++, mbillBean.getBankCode());
			pstmt.setString(idx++, mbillBean.getBranch());
			pstmt.setString(idx++, mbillBean.getAccount());
			pstmt.setString(idx++, mbillBean.getAccountHolder());
			result = pstmt.executeUpdate();
			conn.commit();
		}catch(SQLException e){
			Log.debug("log.sql",BeanUtil.beanToString(mbillBean),this);
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
	public void backup(MerchantBillBean mbBean){
		String query = "INSERT INTO TB_MERCHANT_BILL_HIS SELECT * FROM TB_MERCHANT_BILL WHERE MERCHANT_ID ='"+mbBean.getMerchantId()+"'";
		try{
			DBFactory.getInstance().statementExecuteUpdate(query);
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query, this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}
	}
}

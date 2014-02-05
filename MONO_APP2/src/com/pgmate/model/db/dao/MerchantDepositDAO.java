package com.pgmate.model.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import biz.trustnet.common.db.DBUtil;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.BeanUtil;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.MerchantDepositBean;
import com.pgmate.model.db.factory.DBFactory;

/**
 * @author Administrator
 *
 */
public class MerchantDepositDAO extends DAO {

	public MerchantDepositDAO(){
	}
	
	public MerchantDepositBean getByIdx(long idx){
		List<MerchantDepositBean> list = get(" AND IDX="+CommonUtil.toString(idx));
		if(list.size() == 0){
			return new MerchantDepositBean();
		}else{
			return (MerchantDepositBean)list.get(0);
		}	
	}
	
	public List<MerchantDepositBean> getByMerchant(String merchantId){
		return get(" AND A.MERCHANT_ID='"+merchantId+"'");
	}
	
	public List<MerchantDepositBean> getByMerchantNotSettle(String merchantId,String payMonth){
		return get(" AND A.MERCHANT_ID='"+merchantId+"' AND PAY_MONTH ='"+payMonth+"' AND PAY_SETTLE_ID IS NULL ");
	}
	
	public List<MerchantDepositBean> get(MerchantDepositBean merchantDepositBean){
		StringBuffer sb = new StringBuffer();
		if(!merchantDepositBean.getMerchantId().equals("")){
			sb.append(" AND A.MERCHANT_ID='"+merchantDepositBean.getMerchantId()+"'");
		}
		if(!merchantDepositBean.getPayMonth().equals("")){
			sb.append(" AND PAY_MONTH='"+merchantDepositBean.getPayMonth()+"'");
		}
		if(!merchantDepositBean.getSettleId().equals("")){
			sb.append(" AND SETTLE_ID='"+merchantDepositBean.getSettleId()+"'");
		}
		
		if(!merchantDepositBean.getPublicAgentId().equals("")){
			sb.append(" AND A.MERCHANT_ID IN (SELECT MERCHANT_ID FROM TB_AGENT_MERCHANT WHERE AGENT_ID='"+merchantDepositBean.getPublicAgentId()+"')");
		}
		if(!merchantDepositBean.getPublicGroupId().equals("")){
			sb.append(" AND A.MERCHANT_ID IN (SELECT MERCHANT_ID FROM TB_GROUP_MERCHANT WHERE GROUP_ID='"+merchantDepositBean.getPublicGroupId()+"')");
		}
		
		return get(sb.toString());
	}
	
	public List<MerchantDepositBean> get(String subQuery){
		
		String query = "SELECT IDX, A.MERCHANT_ID AS MERCHANT_ID, RATE, LAST_AMT, CUR_AMT, PAY_AMT, TOT_AMT,PAY_MONTH,SETTLE_ID,PAY_SETTLE_ID , COMMENTS, REG_DATE, NAME, CUR_TYPE   "
			+" FROM TB_MERCHANT_DEPOSIT A LEFT OUTER JOIN VIEW_MERCHANT B ON A.MERCHANT_ID = B.MERCHANT_ID WHERE IDX IS NOT NULL " + subQuery;
		
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
		
		List<MerchantDepositBean> list = new ArrayList<MerchantDepositBean>();
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
				MerchantDepositBean merchantDepositBean = new MerchantDepositBean();
				merchantDepositBean.setIdx(rset.getLong("IDX"));
				merchantDepositBean.setMerchantId(rset.getString("MERCHANT_ID"));
				merchantDepositBean.setRate(rset.getDouble("RATE"));
				merchantDepositBean.setLastAmt(rset.getDouble("LAST_AMT"));
				merchantDepositBean.setCurAmt(rset.getDouble("CUR_AMT"));
				merchantDepositBean.setPayAmt(rset.getDouble("PAY_AMT"));
				merchantDepositBean.setTotAmt(rset.getDouble("TOT_AMT"));
				merchantDepositBean.setPayMonth(rset.getString("PAY_MONTH"));
				merchantDepositBean.setSettleId(rset.getString("SETTLE_ID"));
				merchantDepositBean.setPaySettleId(rset.getString("PAY_SETTLE_ID"));
				merchantDepositBean.setComments(rset.getString("COMMENTS"));
				merchantDepositBean.setRegDate(rset.getTimestamp("REG_DATE"));
				merchantDepositBean.setTemp1String(rset.getString("NAME"));
				merchantDepositBean.setTemp2String(rset.getString("CUR_TYPE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(merchantDepositBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public boolean update(MerchantDepositBean merchantDepositBean){
		String query = "UPDATE TB_MERCHANT_DEPOSIT SET MERCHANT_ID = ?, RATE = ?, LAST_AMT = ?, CUR_AMT = ?, PAY_AMT = ?, TOT_AMT = ?,PAY_MONTH=?,  SETTLE_ID=?,PAY_SETTLE_ID=?,COMMENTS =? WHERE IDX = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, merchantDepositBean.getMerchantId());
			pstmt.setDouble(idx++, merchantDepositBean.getRate());
			pstmt.setDouble(idx++, merchantDepositBean.getLastAmt());
			pstmt.setDouble(idx++, merchantDepositBean.getCurAmt());
			pstmt.setDouble(idx++, merchantDepositBean.getPayAmt());
			pstmt.setDouble(idx++, merchantDepositBean.getTotAmt());
			pstmt.setString(idx++, merchantDepositBean.getPayMonth());
			pstmt.setString(idx++, merchantDepositBean.getSettleId());
			pstmt.setString(idx++, merchantDepositBean.getPaySettleId());
			pstmt.setString(idx++,merchantDepositBean.getComments());
			pstmt.setLong(idx++, merchantDepositBean.getIdx());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(merchantDepositBean),this);
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
	
	public boolean updateByPayMonth(long idx, String payMonth, String comments){
		String query = "UPDATE TB_MERCHANT_DEPOSIT SET PAY_MONTH=?, COMMENTS=? WHERE IDX = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			pstmt.setString(1, payMonth);
			pstmt.setString(2, comments);
			pstmt.setLong(3,idx);
			
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
	
	public boolean insert(MerchantDepositBean merchantDepositBean){
		String query = "INSERT INTO TB_MERCHANT_DEPOSIT ( IDX, MERCHANT_ID, RATE, LAST_AMT, CUR_AMT, PAY_AMT, TOT_AMT,PAY_MONTH,SETTLE_ID,PAY_SETTLE_ID,COMMENTS, REG_DATE ) "
			         + " VALUES ( ?, ?, ?, ?, ?, ?, ?,?,?,?,?, SYSDATE )";
 
				
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setLong(idx++, merchantDepositBean.getIdx());
			pstmt.setString(idx++, merchantDepositBean.getMerchantId());
			pstmt.setDouble(idx++, merchantDepositBean.getRate());
			pstmt.setDouble(idx++, merchantDepositBean.getLastAmt());
			pstmt.setDouble(idx++, merchantDepositBean.getCurAmt());
			pstmt.setDouble(idx++, merchantDepositBean.getPayAmt());
			pstmt.setDouble(idx++, merchantDepositBean.getTotAmt());
			pstmt.setString(idx++, merchantDepositBean.getPayMonth());
			pstmt.setString(idx++, merchantDepositBean.getSettleId());
			pstmt.setString(idx++, merchantDepositBean.getPaySettleId());
			pstmt.setString(idx++,merchantDepositBean.getComments());
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(merchantDepositBean),this);
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
	
	public boolean delete(String settleId){
		String query = "DELETE FROM TB_MERCHANT_DEPOSIT WHERE COMMENTS LIKE '"+settleId+"%'";
 
				
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
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

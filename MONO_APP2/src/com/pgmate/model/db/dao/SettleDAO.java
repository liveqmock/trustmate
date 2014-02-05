/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.SettleDAO.java
 * Date	        : Feb 11, 2009
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

import com.pgmate.model.db.SettleBean;

public class SettleDAO extends DAO {
	
	public SettleDAO(){
		
	}
	
	public SettleBean getById(String merchantId){
		List<SettleBean> list = get(" AND MERCHANT_ID='"+merchantId+"'");
		if(list.size() == 0){
			return new SettleBean();
		}else{
			return (SettleBean)list.get(0);
		}	
	}
	
	public List<SettleBean> getByViewId(String merchantId){
		return getView(" AND A.MERCHANT_ID ='"+merchantId+"' AND A.SETTLE_YN='Y' ");
	}
	
	public List<SettleBean> getByViewMerchantId(String merchantId){
		return getView(" AND A.MERCHANT_ID ='"+merchantId+"' ");
	}
	
	public SettleBean getByIdx(long idx){
		List<SettleBean> list = get(" AND IDX="+CommonUtil.toString(idx));
		if(list.size() == 0){
			return new SettleBean();
		}else{
			return (SettleBean)list.get(0);
		}	
	}
	
	public SettleBean getByInvoiceIdx(String invoiceIdx){
		List<SettleBean> list = get(" AND INVOICE_IDX ='"+invoiceIdx+"'");
		if(list.size() == 0){
			return new SettleBean();
		}else{
			return (SettleBean)list.get(0);
		}	
	}
	
	public SettleBean getByViewIdx(String idx){
		List<SettleBean> list = getView(" AND A.IDX='"+idx+"'");
		if(list.size() == 0){
			return new SettleBean();
		}else{
			return (SettleBean)list.get(0);
		}
	}
	
	public SettleBean getByViewInvoiceIdx(String invoiceIdx){
		List<SettleBean> list = getView(" AND A.INVOICE_IDX='"+invoiceIdx+"'");
		if(list.size() == 0){
			return new SettleBean();
		}else{
			return (SettleBean)list.get(0);
		}
	}
	
	public SettleBean getByInvoiceAndYn(String invoiceIdx,String settleYn){
		List<SettleBean> list = getView(" AND A.INVOICE_IDX='"+invoiceIdx+"' AND A.SETTLE_YN='"+settleYn+"'");
		if(list.size() == 0){
			return new SettleBean();
		}else{
			return (SettleBean)list.get(0);
		}
	}
	
	public List<SettleBean> getView(SettleBean sBean){
		StringBuffer sb = new StringBuffer();
		if(sBean.getIdx() !=0){
			return getView(" AND A.IDX ='"+sBean.getIdx()+"'");
		}
		if(!sBean.getMerchantId().equals("")){
			sb.append(" AND A.MERCHANT_ID ='"+sBean.getMerchantId()+"'");
		}
		
		if(!sBean.getInvoiceIdx().equals("")){
			sb.append(" AND A.INVOICE_IDX ='"+sBean.getInvoiceIdx()+"'");
		}
		
		return getView(sb.toString());
	}
	
	public List<SettleBean> get(String subQuery){
		
		String query = "SELECT IDX, MERCHANT_ID, INVOICE_IDX, TRNSCTN_IDX, CHARGEBACK_IDX, REFUND_IDX, FEE_IDX, DEPOSIT_IDX, VANFEE_IDX,FILE_NAME, SETTLE_YN, REG_DATE "
			+" FROM VW_SETTLE WHERE IDX IS NOT NULL " + subQuery;
		
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
		
		List<SettleBean> list = new ArrayList<SettleBean>();
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
				SettleBean sBean = new SettleBean();
				sBean.setIdx(rset.getLong("IDX"));
				sBean.setMerchantId(rset.getString("MERCHANT_ID"));
				sBean.setInvoiceIdx(rset.getString("INVOICE_IDX"));
				sBean.setTrnsctnIdx(rset.getString("TRNSCTN_IDX"));
				sBean.setCbIdx(rset.getString("CHARGEBACK_IDX"));
				sBean.setRefundIdx(rset.getString("REFUND_IDX"));
				sBean.setFeeIdx(rset.getString("FEE_IDX"));
				sBean.setDepositIdx(rset.getString("DEPOSIT_IDX"));
				sBean.setVanFeeIdx(rset.getString("VANFEE_IDX"));
				sBean.setFileName(rset.getString("FILE_NAME"));
				sBean.setSettleYn(rset.getString("SETTLE_YN"));
				sBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(sBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public List<SettleBean> getView(String subQuery){
		
		String query = "SELECT A.IDX, A.INVOICE_IDX,A.MERCHANT_ID, B.TOT_COUNT AS T_COUNT, B.AMOUNT AS T_AMOUNT, C.TOT_COUNT AS C_COUNT, C.AMOUNT AS C_AMOUNT, "
					 + " D.TOT_COUNT AS R_COUNT, D.AMOUNT AS R_AMOUNT, E.TOT_COUNT AS F_COUNT, E.AMOUNT AS F_AMOUNT, F.TOT_COUNT AS G_COUNT, F.AMOUNT AS G_AMOUNT, B.START_DATE AS START_DATE, "
					 + " B.END_DATE AS END_DATE, A.REG_DATE AS REG_DATE , A.DEPOSIT_IDX AS DEPOSIT_IDX, "
					 + " A.FILE_NAME AS FILE_NAME, A.SETTLE_YN AS SETTLE_YN "
					 + " FROM VW_SETTLE A LEFT OUTER JOIN VW_SETTLE_INFO B ON A.TRNSCTN_IDX = B.SETTLE_IDX "
					 + " LEFT OUTER JOIN VW_SETTLE_INFO C ON A.CHARGEBACK_IDX = C.SETTLE_IDX "
					 + " LEFT OUTER JOIN VW_SETTLE_INFO D ON A.REFUND_IDX = D.SETTLE_IDX "
					 + " LEFT OUTER JOIN VW_SETTLE_INFO E ON A.FEE_IDX = E.SETTLE_IDX "
					 + " LEFT OUTER JOIN VW_SETTLE_INFO F ON A.VANFEE_IDX = F.SETTLE_IDX "
					 + " WHERE A.IDX IS NOT NULL " + subQuery;
		
		if(super.regStartDate != null){
			query +=" AND A.REG_DATE >="+new DBUtil().getToDate(regStartDate);
		}
		if(super.regEndDate != null){
			query +=" AND A.REG_DATE <"+new DBUtil().getToDate(regEndDate);
		}		
		if(!super.orderBy.equals("")){
			query += " "+super.orderBy.replaceAll("REG_DATE","A.REG_DATE");
		}
		
		query = super.toPaging(query);
		
		List<SettleBean> list = new ArrayList<SettleBean>();
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
				SettleBean sBean = new SettleBean();
				sBean.setIdx(rset.getLong("IDX"));
				sBean.setInvoiceIdx(rset.getString("INVOICE_IDX"));
				sBean.setMerchantId(rset.getString("MERCHANT_ID"));
				sBean.setTemp1Double(rset.getDouble("T_COUNT"));
				sBean.setTemp2Double(rset.getDouble("T_AMOUNT"));
				sBean.setTemp3Double(rset.getDouble("C_COUNT"));
				sBean.setTemp4Double(rset.getDouble("C_AMOUNT"));
				sBean.setTemp5Double(rset.getDouble("R_COUNT"));
				sBean.setTemp6Double(rset.getDouble("R_AMOUNT"));
				sBean.setTemp7Double(rset.getDouble("F_COUNT"));
				sBean.setTemp8Double(rset.getDouble("F_AMOUNT"));
				sBean.setTemp9Double(rset.getDouble("G_COUNT"));
				sBean.setTemp10Double(rset.getDouble("G_AMOUNT"));
				sBean.setTemp1String(rset.getString("START_DATE"));
				sBean.setTemp2String(rset.getString("END_DATE"));
				sBean.setRegDate(rset.getTimestamp("REG_DATE"));
				sBean.setDepositIdx(rset.getString("DEPOSIT_IDX"));
				sBean.setFileName(rset.getString("FILE_NAME"));
				sBean.setSettleYn(rset.getString("SETTLE_YN"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(sBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public boolean update(SettleBean sBean){
		String query = "UPDATE TB_SETTLE SET INVOICE_IDX = ?, TRNSCTN_IDX = ?, CHARGEBACK_IDX = ?, REFUND_IDX = ?, "
			+" FEE_IDX = ?,VANFEE_IDX =?, DEPOSIT_IDX=?, FILE_NAME=?, SETTLE_YN=? WHERE IDX = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, sBean.getInvoiceIdx());
			pstmt.setString(idx++, sBean.getTrnsctnIdx());
			pstmt.setString(idx++, sBean.getCbIdx());
			pstmt.setString(idx++, sBean.getRefundIdx());
			pstmt.setString(idx++, sBean.getFeeIdx());
			pstmt.setString(idx++, sBean.getVanFeeIdx());
			pstmt.setString(idx++, sBean.getDepositIdx());
			pstmt.setString(idx++, sBean.getFileName());
			pstmt.setString(idx++, sBean.getSettleYn());
			pstmt.setLong(idx++, sBean.getIdx());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(sBean),this);
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
	
	public boolean updateByDeposit(SettleBean sBean){
		String query = "UPDATE TB_SETTLE SET DEPOSIT_IDX=? WHERE IDX = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, sBean.getDepositIdx());
			pstmt.setLong(idx++, sBean.getIdx());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(sBean),this);
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
	
	public boolean updateByFile(SettleBean sBean){
		String query = "UPDATE TB_SETTLE SET FILE_NAME=? WHERE IDX = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, sBean.getFileName());
			pstmt.setLong(idx++, sBean.getIdx());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(sBean),this);
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
	
	public boolean updateBySettleYn(String invoiceIdx){
		String query = "UPDATE TB_SETTLE SET SETTLE_YN='Y' WHERE INVOICE_IDX = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, invoiceIdx);
			
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
	
	public boolean insert(SettleBean sBean){
		String query = "INSERT INTO TB_SETTLE ( IDX, MERCHANT_ID, INVOICE_IDX, TRNSCTN_IDX, CHARGEBACK_IDX, REFUND_IDX, FEE_IDX, DEPOSIT_IDX, VANFEE_IDX,FILE_NAME, SETTLE_YN, REG_DATE ) "
			         + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,? , SYSDATE )";
 
				
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			sBean.setIdx(SequenceDAO.getSequenceLong("SEQ_SETTLE"));
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setLong(idx++, sBean.getIdx());
			pstmt.setString(idx++, sBean.getMerchantId());
			pstmt.setString(idx++, sBean.getInvoiceIdx());
			pstmt.setString(idx++, sBean.getTrnsctnIdx());
			pstmt.setString(idx++, sBean.getCbIdx());
			pstmt.setString(idx++, sBean.getRefundIdx());
			pstmt.setString(idx++, sBean.getFeeIdx());
			pstmt.setString(idx++, sBean.getDepositIdx());
			pstmt.setString(idx++, sBean.getVanFeeIdx());
			pstmt.setString(idx++, sBean.getFileName());
			pstmt.setString(idx++, sBean.getSettleYn());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(sBean),this);
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

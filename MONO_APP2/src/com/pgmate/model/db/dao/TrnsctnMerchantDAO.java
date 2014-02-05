/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.TrnsctnMerchantDAO.java
 * Date	        : Jan 21, 2009
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
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.CurrentTrnsctnBean;
import com.pgmate.model.db.TrnsctnBean;
import com.pgmate.payment.util.GSICrypt;

public class TrnsctnMerchantDAO extends DAO {
	
	public double sumAmount = 0;
	public String searchQuery = "";
	
	public TrnsctnMerchantDAO(){
		
	}
	
	public double getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(double sumAmount) {
		this.sumAmount = sumAmount;
	}
	
	public TrnsctnBean getByIdx(String idx){
		List<TrnsctnBean> list = get(" AND a.IDX = '"+idx +"'");
		if(list.size() == 0){
			return new TrnsctnBean();
		}else{
			return (TrnsctnBean)list.get(0);
		}
	}
	
	public TrnsctnBean getByTransactionId(String transactionId){
		List<TrnsctnBean> list = get(" AND a.TRANSACTION_ID ='"+transactionId+"'");
		if(list.size() == 0){
			return new TrnsctnBean();
		}else{
			return (TrnsctnBean)list.get(0);
		}
	}
	

	public List<TrnsctnBean> getView(TrnsctnBean trnsctnBean){
		
		StringBuffer sb = new StringBuffer();
		
		if(trnsctnBean.getIdx() != 0){
			return getView(" AND a.IDX =" + CommonUtil.toString(trnsctnBean.getIdx())+"'");
		}
		if(!trnsctnBean.getTransactionId().equals("")){
			sb.append(" AND a.TRANSACTION_ID = '"+ trnsctnBean.getTransactionId() +"'");
		}
		if(!trnsctnBean.getMerchantId().equals("")){
			sb.append(" AND a.MERCHANT_ID = '"+ trnsctnBean.getMerchantId() +"'");
		}
		if(trnsctnBean.getTrnReqDate() != null){
			sb.append(" AND TO_CHAR(a.TRN_REQ_DATE, 'yyyy-MM-dd') = '"+ CommonUtil.convertTimestampToString(trnsctnBean.getTrnReqDate(), "yyyy-MM-dd") + "'");
		}
		if(!trnsctnBean.getTrnStatus().equals("")){
			if(trnsctnBean.getTrnStatus().indexOf("'") >= 0){
				sb.append(" AND a.TRN_STATUS IN ("+ trnsctnBean.getTrnStatus() +")");
			}else{
				sb.append(" AND a.TRN_STATUS = '"+ trnsctnBean.getTrnStatus() +"'");
			}
		}
		if(!trnsctnBean.getCurType().equals("")){
			sb.append(" AND a.CUR_TYPE = '"+ trnsctnBean.getCurType() +"'");
		}
		if(!trnsctnBean.getPayEmail().equals("")){
			sb.append(" AND a.PAY_EMAIL = '"+ CommonUtil.toString(trnsctnBean.getPayEmail()) +"'");
		}
		if(!trnsctnBean.getPayName().equals("")){
			sb.append(" AND a.PAY_NAME = '"+ CommonUtil.toString(trnsctnBean.getPayName()) +"'");
		}
		if(trnsctnBean.getAmount() != 0){
			sb.append(" AND a.AMOUNT = '"+ CommonUtil.toString(trnsctnBean.getAmount()) +"'");
		}
		if(!trnsctnBean.getTemp1String().equals("")){
            sb.append(" AND b.CARD_NUM = '"+ trnsctnBean.getTemp1String() +"'");
		}
		if(!trnsctnBean.getTemp2String().equals("")){
            sb.append(" AND b.CARD_TYPE = '"+ CommonUtil.toString(trnsctnBean.getTemp2String()) +"'");
		}
		if(!trnsctnBean.getApprovalNo().equals("")){
			sb.append(" AND a.APPROVAL_NO = '"+ trnsctnBean.getApprovalNo() +"'");
		}
		if(trnsctnBean.getAuth().equals("Y")){
			sb.append(" AND a.AUTH = '"+ trnsctnBean.getAuth() +"'");
		}
		
		searchQuery = sb.toString();
		return getView(searchQuery);
		
	}
	
	public List<TrnsctnBean> get(TrnsctnBean trnsctnBean){
		StringBuffer sb = new StringBuffer();
		
		if(trnsctnBean.getIdx() != 0){
			return get(" AND a.IDX =" + CommonUtil.toString(trnsctnBean.getIdx())+"'");
		}
		if(!trnsctnBean.getTransactionId().equals("")){
			sb.append(" AND a.TRANSACTION_ID = '"+ trnsctnBean.getTransactionId() +"'");
		}
		if(!trnsctnBean.getVanTransactionId().equals("")){
			sb.append(" AND a.VAN_TRANSACTION_ID = '"+ trnsctnBean.getVanTransactionId() +"'");
		}
		if(!trnsctnBean.getMerchantId().equals("")){
			sb.append(" AND a.MERCHANT_ID = '"+ trnsctnBean.getMerchantId() +"'");
		}
		if(trnsctnBean.getTrnReqDate() != null){
			sb.append(" AND TO_CHAR(a.TRN_REQ_DATE, 'yyyy-MM-dd') = '"+ CommonUtil.convertTimestampToString(trnsctnBean.getTrnReqDate(), "yyyy-MM-dd") + "'");
		}
		if(!trnsctnBean.getTrnStatus().equals("")){
			if(trnsctnBean.getTrnStatus().indexOf("'") >= 0){
				sb.append(" AND a.TRN_STATUS IN ("+ trnsctnBean.getTrnStatus() +")");
			}else{
				sb.append(" AND a.TRN_STATUS = '"+ trnsctnBean.getTrnStatus() +"'");
			}
		}
		if(!trnsctnBean.getPayNo().equals("")){
			sb.append(" AND a.PAY_NO = '"+ trnsctnBean.getPayNo() +"'");
		}
		if(!trnsctnBean.getPayEmail().equals("")){
			sb.append(" AND a.PAY_EMAIL = '"+ CommonUtil.toString(trnsctnBean.getPayEmail()) +"'");
		}
		if(!trnsctnBean.getPayName().equals("")){
			sb.append(" AND a.PAY_NAME LIKE '%"+ trnsctnBean.getPayName() +"%'");
		}
		if(!trnsctnBean.getTemp2().equals("")){
			sb.append(" AND a.TEMP2 = '"+ trnsctnBean.getTemp2() +"'");
		}
		if(!trnsctnBean.getIpAddress().equals("")){
			sb.append(" AND a.IP_ADDRESS LIKE '%"+ trnsctnBean.getIpAddress() +"%'");
		}
		if(!trnsctnBean.getTemp1String().equals("")){
            sb.append(" AND b.CARD_NUM = '"+ trnsctnBean.getTemp1String() +"'");
		}
		if(!trnsctnBean.getTemp2String().equals("")){
            sb.append(" AND b.CARD_TYPE = '"+ CommonUtil.toString(trnsctnBean.getTemp2String()) +"'");
		}
		if(!trnsctnBean.getPayNo().equals("")){
			sb.append(" AND a.PAY_NO = '"+ CommonUtil.toString(trnsctnBean.getPayNo()) +"'");
		}
		if(!trnsctnBean.getProductName().equals("")){
			sb.append(" AND a.PRODUCT_NAME = '"+ trnsctnBean.getProductName() +"'");
		}
		if(!trnsctnBean.getApprovalNo().equals("")){
			sb.append(" AND a.APPROVAL_NO = '"+ trnsctnBean.getApprovalNo() +"'");
		}
		if(!trnsctnBean.getCurType().equals("")){
			sb.append(" AND a.CUR_TYPE = '"+ trnsctnBean.getCurType() +"'");
		}
		if(trnsctnBean.getAuth().equals("Y")){
			sb.append(" AND a.AUTH = '"+ trnsctnBean.getAuth() +"'");
		}
		if(!trnsctnBean.getVan().equals("")){
			sb.append(" AND a.VAN = '"+ trnsctnBean.getVan() +"'");
		}
		
		
		if(!trnsctnBean.getPublicAgentId().equals("")){
			sb.append(" AND A.MERCHANT_ID IN (SELECT MERCHANT_ID FROM TB_AGENT_MERCHANT WHERE AGENT_ID='"+trnsctnBean.getPublicAgentId()+"')");
		}
		if(!trnsctnBean.getPublicGroupId().equals("")){
			sb.append(" AND A.MERCHANT_ID IN (SELECT MERCHANT_ID FROM TB_GROUP_MERCHANT WHERE GROUP_ID='"+trnsctnBean.getPublicGroupId()+"')");
		}
		
		searchQuery = sb.toString();
		return get(searchQuery);
		
	}
	
	
	
	public List<TrnsctnBean> getInvoice(TrnsctnBean trnsctnBean){
		StringBuffer sb = new StringBuffer();
		
		if(trnsctnBean.getIdx() != 0){
			return get(" AND a.IDX =" + CommonUtil.toString(trnsctnBean.getIdx())+"'");
		}
		if(!trnsctnBean.getTransactionId().equals("")){
			sb.append(" AND a.TRANSACTION_ID = '"+ trnsctnBean.getTransactionId() +"'");
		}
		if(!trnsctnBean.getMerchantId().equals("")){
			sb.append(" AND a.MERCHANT_ID = '"+ trnsctnBean.getMerchantId() +"'");
		}
		if(trnsctnBean.getTrnReqDate() != null){
			sb.append(" AND TO_CHAR(a.TRN_REQ_DATE, 'yyyy-MM-dd') = '"+ CommonUtil.convertTimestampToString(trnsctnBean.getTrnReqDate(), "yyyy-MM-dd") + "'");
		}
		if(!trnsctnBean.getTrnStatus().equals("")){
			if(trnsctnBean.getTrnStatus().indexOf("'") >= 0){
				sb.append(" AND a.TRN_STATUS IN ("+ trnsctnBean.getTrnStatus() +")");
			}else{
				sb.append(" AND a.TRN_STATUS = '"+ trnsctnBean.getTrnStatus() +"'");
			}
		}
		if(!trnsctnBean.getPayNo().equals("")){
			sb.append(" AND a.PAY_NO = '"+ trnsctnBean.getPayNo() +"'");
		}
		if(!trnsctnBean.getPayEmail().equals("")){
			sb.append(" AND a.PAY_EMAIL = '"+ CommonUtil.toString(trnsctnBean.getPayEmail()) +"'");
		}
		if(!trnsctnBean.getTemp1String().equals("")){
            sb.append(" AND b.CARD_NUM = '"+ trnsctnBean.getTemp1String() +"'");
		}
		if(!trnsctnBean.getTemp2String().equals("")){
            sb.append(" AND b.CARD_TYPE = '"+ CommonUtil.toString(trnsctnBean.getTemp2String()) +"'");
		}
		if(!trnsctnBean.getPayNo().equals("")){
			sb.append(" AND a.PAY_NO = '"+ CommonUtil.toString(trnsctnBean.getPayNo()) +"'");
		}
		if(!trnsctnBean.getProductName().equals("")){
			sb.append(" AND a.PRODUCT_NAME = '"+ trnsctnBean.getProductName() +"'");
		}
		if(!trnsctnBean.getApprovalNo().equals("")){
			sb.append(" AND a.APPROVAL_NO = '"+ trnsctnBean.getApprovalNo() +"'");
		}
		
		if(!trnsctnBean.getCurType().equals("")){
			sb.append(" AND a.CUR_TYPE = '"+ trnsctnBean.getCurType() +"'");
		}
		searchQuery = sb.toString();
		return getInvoice(searchQuery);
		
	}
	
	public List<TrnsctnBean> getView(String subQuery){
		GSICrypt crypt = new GSICrypt();
		
		String query = "SELECT a.IDX, a.TRANSACTION_ID, a.MERCHANT_ID, a.TRN_REQ_DATE, a.TRN_STATUS,  a.CUR_TYPE, a.AMOUNT, a.PAY_EMAIL, a.PAY_NAME, b.CARD_NUM, b.CARD_TYPE, a.APPROVAL_NO ,a.RESULT_MSG "
			         + "FROM VW_TRNSCTN a , VW_TRNSCTN_SCR b "
			         + "WHERE  a.IDX IS NOT NULL AND a.TRANSACTION_ID = b.TRANSACTION_ID " + subQuery + " AND a.TRN_DATE BETWEEN ";
		
		if(super.regStartDate != null){
			query += "'"+CommonUtil.convertTimestampToString(regStartDate, "yyyyMMdd")+"'";
		}else{
			query +="TO_CHAR(ADD_MONTHS(SYSDATE,-12),'YYYYMMDD')";
		}
		if(super.regEndDate != null){
			query +=" AND '"+CommonUtil.convertTimestampToString(regEndDate, "yyyyMMdd")+"' ";
		}else{
			query +=" AND '"+CommonUtil.getCurrentDate("yyyyMMdd")+"' ";
		}
		
		if(!super.orderBy.equals("")){
			query += super.orderBy;
		}else{
			query += " ORDER BY a.IDX DESC";
		}
		
		query = super.toPaging(query);
		
		
		List<TrnsctnBean> list = new ArrayList<TrnsctnBean>();
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
				TrnsctnBean trnsctnBean = new TrnsctnBean();
				trnsctnBean.setIdx(rset.getLong("IDX"));
				trnsctnBean.setTransactionId(rset.getString("TRANSACTION_ID"));
				trnsctnBean.setMerchantId(rset.getString("MERCHANT_ID"));
				trnsctnBean.setTrnReqDate(rset.getTimestamp("TRN_REQ_DATE"));
				trnsctnBean.setTrnStatus(rset.getString("TRN_STATUS"));
				trnsctnBean.setCurType(rset.getString("CUR_TYPE"));
				trnsctnBean.setPayEmail(rset.getString("PAY_EMAIL"));
				trnsctnBean.setPayName(rset.getString("PAY_NAME"));
				trnsctnBean.setAmount(rset.getDouble("AMOUNT"));
				trnsctnBean.setTemp1String(crypt.decrypt(rset.getString("CARD_NUM")));
				trnsctnBean.setTemp2String(rset.getString("CARD_TYPE"));
				trnsctnBean.setApprovalNo(rset.getString("APPROVAL_NO"));
				trnsctnBean.setResultMsg(rset.getString("RESULT_MSG"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				sumAmount = sumAmount + trnsctnBean.getAmount();
				list.add(trnsctnBean); 
			
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		return list;

	}
	
	public double getSearchAmount(){
		
		String query = "SELECT SUM(a.AMOUNT) AS AMOUNT "
			         + "FROM VW_TRNSCTN a , VW_TRNSCTN_SCR b "
			         + "WHERE  a.IDX IS NOT NULL AND a.TRANSACTION_ID = b.TRANSACTION_ID " + searchQuery + " AND a.TRN_DATE BETWEEN ";
		
		if(super.regStartDate != null){
			query += "'"+CommonUtil.convertTimestampToString(regStartDate, "yyyyMMdd")+"'";
		}else{
			query +="TO_CHAR(ADD_MONTHS(SYSDATE,-12),'YYYYMMDD')";
		}
		if(super.regEndDate != null){
			query +=" AND '"+CommonUtil.convertTimestampToString(regEndDate, "yyyyMMdd")+"' ";
		}else{
			query +=" AND '"+CommonUtil.getCurrentDate("yyyyMMdd")+"' ";
		}
		
		double amount = 0;
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
				amount = rset.getDouble("AMOUNT"); 
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		return amount;

	}
	
	public List<TrnsctnBean> get(String subQuery){
		GSICrypt crypt = new GSICrypt();
		
		String query = "SELECT a.IDX, a.TRANSACTION_ID, a.MERCHANT_ID, a.MALL_ID, a.TRN_REQ_DATE, a.TRN_RES_DATE, a.TRN_STATUS,  a.CUR_TYPE, a.AMOUNT, a.PAY_NO, a.PAY_EMAIL, a.PAY_NAME, a.PRODUCT_NAME, b.CARD_NUM, b.CARD_TYPE, a.APPROVAL_NO,a.RESULT_MSG "
			         + " FROM VW_TRNSCTN a , VW_TRNSCTN_SCR b "
			         + " WHERE  a.IDX IS NOT NULL AND a.TRANSACTION_ID = b.TRANSACTION_ID " + subQuery + " AND a.TRN_DATE BETWEEN ";
		
		if(super.regStartDate != null){
			query += "'"+CommonUtil.convertTimestampToString(regStartDate, "yyyyMMdd")+"'";
		}else{
			query +="TO_CHAR(ADD_MONTHS(SYSDATE,-12),'YYYYMMDD')";
		}
		if(super.regEndDate != null){
			query +=" AND '"+CommonUtil.convertTimestampToString(regEndDate, "yyyyMMdd")+"' ";
		}else{
			query +=" AND '"+CommonUtil.getCurrentDate("yyyyMMdd")+"' ";
		}
		
		if(!super.orderBy.equals("")){
			query += super.orderBy;
		}else{
			query += " ORDER BY a.IDX DESC";
		}
		
		query = super.toPaging(query);
		
		List<TrnsctnBean> list = new ArrayList<TrnsctnBean>();
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
				TrnsctnBean trnsctnBean = new TrnsctnBean();
				trnsctnBean.setIdx(rset.getLong("IDX"));
				trnsctnBean.setTransactionId(rset.getString("TRANSACTION_ID"));
				trnsctnBean.setMerchantId(rset.getString("MERCHANT_ID"));
				trnsctnBean.setMallId(rset.getString("MALL_ID"));
				trnsctnBean.setTrnReqDate(rset.getTimestamp("TRN_REQ_DATE"));
				trnsctnBean.setTrnResDate(rset.getTimestamp("TRN_RES_DATE"));
				trnsctnBean.setTrnStatus(rset.getString("TRN_STATUS"));
				trnsctnBean.setCurType(rset.getString("CUR_TYPE"));
				trnsctnBean.setPayNo(rset.getString("PAY_NO"));
				trnsctnBean.setPayEmail(rset.getString("PAY_EMAIL"));
				trnsctnBean.setPayName(rset.getString("PAY_NAME"));
				trnsctnBean.setProductName(rset.getString("PRODUCT_NAME"));
				trnsctnBean.setAmount(rset.getDouble("AMOUNT"));
				trnsctnBean.setTemp1String(crypt.hideCardNumberDecrypt(rset.getString("CARD_NUM")));
				trnsctnBean.setTemp2String(rset.getString("CARD_TYPE"));
				trnsctnBean.setApprovalNo(rset.getString("APPROVAL_NO"));
				trnsctnBean.setResultMsg(rset.getString("RESULT_MSG"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				sumAmount = sumAmount + trnsctnBean.getAmount();
				list.add(trnsctnBean);  
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		return list;

	}
	
	public List<TrnsctnBean> getExport(TrnsctnBean trnsctnBean){
		
		StringBuffer sb = new StringBuffer();
		
		if(trnsctnBean.getIdx() != 0){
			return get(" AND a.IDX =" + CommonUtil.toString(trnsctnBean.getIdx())+"'");
		}
		if(!trnsctnBean.getTransactionId().equals("")){
			sb.append(" AND a.TRANSACTION_ID = '"+ trnsctnBean.getTransactionId() +"'");
		}
		if(!trnsctnBean.getVanTransactionId().equals("")){
			sb.append(" AND a.VAN_TRANSACTION_ID = '"+ trnsctnBean.getVanTransactionId() +"'");
		}
		if(!trnsctnBean.getMerchantId().equals("")){
			sb.append(" AND a.MERCHANT_ID = '"+ trnsctnBean.getMerchantId() +"'");
		}
		if(trnsctnBean.getTrnReqDate() != null){
			sb.append(" AND TO_CHAR(a.TRN_REQ_DATE, 'yyyy-MM-dd') = '"+ CommonUtil.convertTimestampToString(trnsctnBean.getTrnReqDate(), "yyyy-MM-dd") + "'");
		}
		if(!trnsctnBean.getTrnStatus().equals("")){
			if(trnsctnBean.getTrnStatus().indexOf("'") >= 0){
				sb.append(" AND a.TRN_STATUS IN ("+ trnsctnBean.getTrnStatus() +")");
			}else{
				sb.append(" AND a.TRN_STATUS = '"+ trnsctnBean.getTrnStatus() +"'");
			}
		}
		if(!trnsctnBean.getPayNo().equals("")){
			sb.append(" AND a.PAY_NO = '"+ trnsctnBean.getPayNo() +"'");
		}
		if(!trnsctnBean.getPayEmail().equals("")){
			sb.append(" AND a.PAY_EMAIL = '"+ CommonUtil.toString(trnsctnBean.getPayEmail()) +"'");
		}
		if(!trnsctnBean.getPayName().equals("")){
			sb.append(" AND a.PAY_NAME LIKE '%"+ trnsctnBean.getPayName() +"%'");
		}
		if(!trnsctnBean.getTemp2().equals("")){
			sb.append(" AND a.TEMP2 = '"+ trnsctnBean.getTemp2() +"'");
		}
		if(!trnsctnBean.getIpAddress().equals("")){
			sb.append(" AND a.IP_ADDRESS LIKE '%"+ trnsctnBean.getIpAddress() +"%'");
		}
		if(!trnsctnBean.getTemp1String().equals("")){
            sb.append(" AND b.CARD_NUM = '"+ trnsctnBean.getTemp1String() +"'");
		}
		if(!trnsctnBean.getTemp2String().equals("")){
            sb.append(" AND b.CARD_TYPE = '"+ CommonUtil.toString(trnsctnBean.getTemp2String()) +"'");
		}
		if(!trnsctnBean.getPayNo().equals("")){
			sb.append(" AND a.PAY_NO = '"+ CommonUtil.toString(trnsctnBean.getPayNo()) +"'");
		}
		if(!trnsctnBean.getProductName().equals("")){
			sb.append(" AND a.PRODUCT_NAME = '"+ trnsctnBean.getProductName() +"'");
		}
		if(!trnsctnBean.getApprovalNo().equals("")){
			sb.append(" AND a.APPROVAL_NO = '"+ trnsctnBean.getApprovalNo() +"'");
		}
		if(!trnsctnBean.getCurType().equals("")){
			sb.append(" AND a.CUR_TYPE = '"+ trnsctnBean.getCurType() +"'");
		}
		if(trnsctnBean.getAuth().equals("Y")){
			sb.append(" AND a.AUTH = '"+ trnsctnBean.getAuth() +"'");
		}
		if(!trnsctnBean.getVan().equals("")){
			sb.append(" AND a.VAN = '"+ trnsctnBean.getVan() +"'");
		}
		
		
		GSICrypt crypt = new GSICrypt();
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT a.IDX,a.TRANSACTION_ID,a.MERCHANT_ID,a.MALL_ID,a.PAY_NO,FC_CODE('TRN_STATUS',a.TRN_STATUS,'en') AS TRN_STATUS, a.CUR_TYPE, a.AMOUNT,b.CARD_NUM,b.CARD_TYPE, a.APPROVAL_NO ,a.PAY_USERID, ");
		query.append("a.PAY_NAME, a.PAY_EMAIL, a.PAY_TELNO,a.PRODUCT_NAME,a.IP_ADDRESS,FC_GEO(a.TRANSACTION_ID) AS IP_GEO,a.VAN,A.TEMP2 ,A.TEMP1,a.RESULT_CD,A.RESULT_MSG,  a.TRN_REQ_DATE, a.TRN_RES_DATE, a.TRN_DATE ");
		query.append("FROM VW_TRNSCTN a , VW_TRNSCTN_SCR b ");
		query.append("WHERE  a.TRANSACTION_ID = b.TRANSACTION_ID "+sb.toString()+" AND a.TRN_DATE BETWEEN ");
		
		
		if(super.regStartDate != null){
			query.append("'"+CommonUtil.convertTimestampToString(regStartDate, "yyyyMMdd")+"' ");
		}else{
			query.append("TO_CHAR(ADD_MONTHS(SYSDATE,-12),'YYYYMMDD')");
		}
		if(super.regEndDate != null){
			query.append(" AND '"+CommonUtil.convertTimestampToString(regEndDate, "yyyyMMdd")+"' ");
		}else{
			query.append(" AND '"+CommonUtil.getCurrentDate("yyyyMMdd")+"' ");
		}
		
		query.append(" ORDER BY a.IDX DESC");
		
	
		List<TrnsctnBean> list = new ArrayList<TrnsctnBean>();
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query.toString());
			rset 	= pstmt.executeQuery();
			
			while(rset.next()){
				TrnsctnBean trnBean = new TrnsctnBean();
				trnBean.setIdx(rset.getLong("IDX"));
				trnBean.setTransactionId(rset.getString("TRANSACTION_ID"));
				trnBean.setMerchantId(rset.getString("MERCHANT_ID"));
				trnBean.setMallId(CommonUtil.nToB(rset.getString("MALL_ID")));
				trnBean.setPayNo(CommonUtil.nToB(rset.getString("PAY_NO")));
				trnBean.setTrnStatus(rset.getString("TRN_STATUS"));
				trnBean.setCurType(rset.getString("CUR_TYPE"));
				trnBean.setAmount(rset.getDouble("AMOUNT"));
				trnBean.setTemp1String(crypt.hideCardNumberDecrypt(rset.getString("CARD_NUM")));
				trnBean.setTemp2String(rset.getString("CARD_TYPE"));
				trnBean.setApprovalNo(CommonUtil.nToB(rset.getString("APPROVAL_NO")));
				trnBean.setPayUserId(CommonUtil.nToB(rset.getString("PAY_USERID")));
				trnBean.setPayName(CommonUtil.nToB(rset.getString("PAY_NAME")));
				trnBean.setPayEmail(CommonUtil.nToB(rset.getString("PAY_EMAIL")));
				trnBean.setPayTelNo(CommonUtil.nToB(rset.getString("PAY_TELNO")));
				trnBean.setProductName(CommonUtil.nToB(rset.getString("PRODUCT_NAME")));
				trnBean.setIpAddress(CommonUtil.nToB(rset.getString("IP_ADDRESS")));
				trnBean.setTemp3String(CommonUtil.nToB(rset.getString("IP_GEO")));
				trnBean.setVan(CommonUtil.nToB(rset.getString("VAN")));
				trnBean.setTemp2(CommonUtil.nToB(rset.getString("TEMP2")));
				trnBean.setTemp1(CommonUtil.nToB(rset.getString("TEMP1")));
				String resultCd = CommonUtil.nToB(rset.getString("RESULT_CD"));
				if(resultCd.equals("0")){
					trnBean.setResultCd("Success");
				}else if(resultCd.equals("1")){
					trnBean.setResultCd("Fail");
				}else{
					trnBean.setResultCd("Error");
				}
				trnBean.setResultMsg(CommonUtil.nToB(rset.getString("RESULT_MSG")));
				
				
				trnBean.setTrnReqDate(rset.getTimestamp("TRN_REQ_DATE"));
				trnBean.setTrnResDate(rset.getTimestamp("TRN_RES_DATE"));
				trnBean.setTrnDate(CommonUtil.nToB(rset.getString("TRN_DATE")));
				
				list.add(trnBean);  
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		return list;

	}
	
	public List<TrnsctnBean> getInvoice(String subQuery){
		GSICrypt crypt = new GSICrypt();
		
		String query = "SELECT a.IDX, a.TRANSACTION_ID, a.MERCHANT_ID, a.TRN_REQ_DATE, a.TRN_STATUS,  a.CUR_TYPE, a.AMOUNT, a.PAY_NO, a.PAY_EMAIL, a.PAY_NAME, a.PRODUCT_NAME, b.CARD_NUM, b.CARD_TYPE, a.APPROVAL_NO "
			         + " FROM VW_TRNSCTN a , VW_TRNSCTN_SCR b "
			         + " WHERE  a.IDX IS NOT NULL AND a.TRN_STATUS IN ('07','08','09','10','18','19','20','21') "
			         + " AND a.TRANSACTION_ID = b.TRANSACTION_ID " + subQuery + " AND a.TRN_DATE BETWEEN ";
		
		if(super.regStartDate != null){
			query += "'"+CommonUtil.convertTimestampToString(regStartDate, "yyyyMMdd")+"'";
		}else{
			query +="TO_CHAR(SYSDATE-90,'YYYYMMDD')";
		}
		if(super.regEndDate != null){
			query +=" AND '"+CommonUtil.convertTimestampToString(regEndDate, "yyyyMMdd")+"' ";
		}else{
			query +=" AND '"+CommonUtil.getCurrentDate("yyyyMMdd")+"' ";
		} 
		if(!super.orderBy.equals("")){
			query += super.orderBy;
		}else{
			query += " ORDER BY a.IDX DESC";
		}
		
		query = super.toPaging(query);
		
		List<TrnsctnBean> list = new ArrayList<TrnsctnBean>();
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
				TrnsctnBean trnsctnBean = new TrnsctnBean();
				trnsctnBean.setIdx(rset.getLong("IDX"));
				trnsctnBean.setTransactionId(rset.getString("TRANSACTION_ID"));
				trnsctnBean.setMerchantId(rset.getString("MERCHANT_ID"));
				trnsctnBean.setTrnReqDate(rset.getTimestamp("TRN_REQ_DATE"));
				trnsctnBean.setTrnStatus(rset.getString("TRN_STATUS"));
				trnsctnBean.setCurType(rset.getString("CUR_TYPE"));
				trnsctnBean.setPayNo(rset.getString("PAY_NO"));
				trnsctnBean.setPayEmail(rset.getString("PAY_EMAIL"));
				trnsctnBean.setPayName(rset.getString("PAY_NAME"));
				trnsctnBean.setProductName(rset.getString("PRODUCT_NAME"));
				trnsctnBean.setAmount(rset.getDouble("AMOUNT"));
				trnsctnBean.setTemp1String(crypt.hideCardNumberDecrypt(rset.getString("CARD_NUM")));
				trnsctnBean.setTemp2String(rset.getString("CARD_TYPE"));
				trnsctnBean.setApprovalNo(rset.getString("APPROVAL_NO"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				sumAmount = sumAmount + trnsctnBean.getAmount();
				list.add(trnsctnBean);  
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		return list;

	}
	
	public CurrentTrnsctnBean getMonthlyTransaction(){
		
		String query = "SELECT COUNT(*) AS CNT, SUM(AMOUNT) AS SUM FROM VW_TRNSCTN WHERE TRN_STATUS IN ('02','07','08','09','11','12','13','10')"
					+"	AND TO_CHAR(TRN_REQ_DATE,'YYYYMM') = TO_CHAR(SYSDATE,'YYYYMM')";
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		CurrentTrnsctnBean currentTrnsctnBean = new CurrentTrnsctnBean();
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			rset 	= pstmt.executeQuery();
			
			while(rset.next()){
				 currentTrnsctnBean.setMonthCount(rset.getDouble("CNT"));
				 currentTrnsctnBean.setMonthSum(rset.getDouble("SUM"));
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		return currentTrnsctnBean;

	}
	
	public CurrentTrnsctnBean getMonthlyTransaction(String curType){
		
		String query = "SELECT COUNT(*) AS CNT, SUM(AMOUNT) AS SUM FROM VW_TRNSCTN WHERE TRN_STATUS IN ('02','07','08','09','11','12','13','10')"
					+"	AND TO_CHAR(TRN_REQ_DATE,'YYYYMM') = TO_CHAR(SYSDATE,'YYYYMM') ";
		if(!CommonUtil.isNullOrSpace(curType)){
			query = query+ " AND CUR_TYPE = '"+curType+"' ";
		}
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		CurrentTrnsctnBean currentTrnsctnBean = new CurrentTrnsctnBean();
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			rset 	= pstmt.executeQuery();
			
			while(rset.next()){
				 currentTrnsctnBean.setMonthCount(rset.getDouble("CNT"));
				 currentTrnsctnBean.setMonthSum(rset.getDouble("SUM"));
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		return currentTrnsctnBean;

	}
	
	public CurrentTrnsctnBean getDaylyTransaction(){
		
		String query = "SELECT COUNT(*) AS COUNT, SUM(AMOUNT) AS AMT FROM VW_TRNSCTN WHERE TRN_STATUS NOT IN ('00','01','03','06','18','22','23') AND TRN_REQ_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE)+ 0.99999421";

		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		CurrentTrnsctnBean currentTrnsctnBean = new CurrentTrnsctnBean();
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			rset 	= pstmt.executeQuery();
			
			while(rset.next()){
				 currentTrnsctnBean.setDailyCount(rset.getDouble("COUNT"));
				 currentTrnsctnBean.setDailySum(rset.getDouble("AMT"));
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		return currentTrnsctnBean;

	}
	
}

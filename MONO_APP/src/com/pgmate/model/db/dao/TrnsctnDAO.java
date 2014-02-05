/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.TrnsctnDAO.java
 * Date	        : Dec 23, 2008
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.pgmate.model.db.factory.DBFactory;
import com.pgmate.model.db.factory.DBUtil;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.BeanUtil;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.CurrentTrnsctnBean;
import com.pgmate.model.db.TrnsctnBean;

public class TrnsctnDAO extends DAO {

	public TrnsctnDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public TrnsctnBean getByIdx(String idx){
		List<TrnsctnBean> list = get(" AND IDX ="+idx);
		if(list.size() == 0){
			return new TrnsctnBean();
		}else{
			return (TrnsctnBean)list.get(0);
		}
	}
	
	
	public TrnsctnBean getByTransactionId(String transactionId){
		List<TrnsctnBean> list = get(" AND TRANSACTION_ID ='"+transactionId+"'");
		if(list.size() == 0){
			return new TrnsctnBean();
		}else{
			return (TrnsctnBean)list.get(0);
		}
	}
	
	public TrnsctnBean getByTransactionIdOrVanTransactionId(TrnsctnBean trnsctnBean){
		StringBuffer sb = new StringBuffer();
		
		if(!trnsctnBean.getTransactionId().equals("")){
			sb.append(" AND TRANSACTION_ID = '"+ trnsctnBean.getTransactionId() +"'");
		}
		if(!trnsctnBean.getVanTransactionId().equals("")){
			sb.append(" AND VAN_TRANSACTION_ID = '"+ trnsctnBean.getVanTransactionId() +"'");
		}
		
		List<TrnsctnBean> list = get(sb.toString());
		if(list.size() == 0){
			return new TrnsctnBean();
		}else{
			return (TrnsctnBean)list.get(0);
		}
	}
	
	public TrnsctnBean getMerchantTrnsctnById(String transactionId, String merchantId){
		List<TrnsctnBean> list = get(" AND TRANSACTION_ID ='"+transactionId+"' AND MERCHANT_ID ='"+merchantId+"' ");
		if(list.size() == 0){
			return new TrnsctnBean();
		}else{
			return (TrnsctnBean)list.get(0);
		}
	}
	
	
	public TrnsctnBean get3MonthAgoTrnsctnBean(String merchantId,String transactionId,String payNo){
		StringBuffer sb = new StringBuffer();
		
		sb.append(" AND TRANSACTION_ID = '"+ transactionId +"'");
		sb.append(" AND MERCHANT_ID = '"+ merchantId +"'");
		sb.append(" AND PAY_NO = '"+ payNo +"'");
		sb.append(" AND RESULT_CD='00' AND RESULT_MSG='0000' AND TRN_STATUS IN ('02','07','08','09','10','11','12','13','14','21','19')");
		sb.append(" AND TRN_REQ_DATE >= TO_DATE(ADD_MONTHS(SYSDATE,-3)) ");
		List<TrnsctnBean> list = get(sb.toString());
		if(list.size() ==0){
			return new TrnsctnBean();
		}else{
			return (TrnsctnBean)list.get(0);
		}
	}
	
	public List<TrnsctnBean> get(TrnsctnBean trnsctnBean){
		StringBuffer sb = new StringBuffer();
		
		if(trnsctnBean.getIdx() != 0){
			return get(" AND IDX =" + CommonUtil.toString(trnsctnBean.getIdx()));
		}
		if(!trnsctnBean.getTransactionId().equals("")){
			sb.append(" AND TRANSACTION_ID = '"+ trnsctnBean.getTransactionId() +"'");
		}
		if(!trnsctnBean.getMerchantId().equals("")){
			sb.append(" AND MERCHANT_ID = '"+ trnsctnBean.getMerchantId() +"'");
		}
		if(!trnsctnBean.getMallId().equals("")){
			sb.append(" AND MALL_ID = '"+ trnsctnBean.getMallId() +"'");
		}
		if(!trnsctnBean.getServiceType().equals("")){
			sb.append(" AND SERVICE_TYPE = '"+ trnsctnBean.getServiceType() +"'");
		}
		if(trnsctnBean.getTrnReqDate() != null){
			sb.append(" AND TO_CHAR(TRN_REQ_DATE, 'yyyy-MM-dd') = '"+ CommonUtil.convertTimestampToString(trnsctnBean.getTrnReqDate(), "yyyy-MM-dd") + "'");
		}
		if(trnsctnBean.getTrnResDate() != null){
			sb.append(" AND TO_CHAR(TRN_RES_DATE, 'yyyy-MM-dd') = '"+ CommonUtil.convertTimestampToString(trnsctnBean.getTrnResDate(), "yyyy-MM-dd") + "'");
		}
		if(!trnsctnBean.getTrnStatus().equals("")){
			if(trnsctnBean.getTrnStatus().indexOf("'") >0){
				sb.append(" AND TRN_STATUS IN ("+ trnsctnBean.getTrnStatus() +")");
			}else{
				sb.append(" AND TRN_STATUS '"+ trnsctnBean.getTrnStatus() +"'");
			}
		}
		if(!trnsctnBean.getResultCd().equals("")){
			sb.append(" AND RESULT_CD = '"+ trnsctnBean.getResultCd() +"'");
		}
		if(!trnsctnBean.getResultMsg().equals("")){
			sb.append(" AND RESULT_MSG = '"+ trnsctnBean.getResultMsg() +"'");
		}
		if(!trnsctnBean.getVanTransactionId().equals("")){
			sb.append(" AND VAN_TRANSACTION_ID = '"+ trnsctnBean.getVanTransactionId() +"'");
		}
		if(!trnsctnBean.getApprovalNo().equals("")){
			sb.append(" AND APPROVAL_NO = '"+ trnsctnBean.getApprovalNo() +"'");
		}
		if(!trnsctnBean.getPayNo().equals("")){
			sb.append(" AND PAY_NO = '"+ trnsctnBean.getPayNo() +"'");
		}
		if(!trnsctnBean.getPayUserId().equals("")){
			sb.append(" AND PAY_USERID = '"+ trnsctnBean.getPayUserId() +"'");
		}
		if(!trnsctnBean.getPayEmail().equals("")){
			sb.append(" AND PAY_EMAIL = '"+ trnsctnBean.getPayEmail() +"'");
		}
		if(!trnsctnBean.getPayName().equals("")){
			sb.append(" AND PAY_NAME = '"+ trnsctnBean.getPayName() +"'");
		}
		if(!trnsctnBean.getPayTelNo().equals("")){
			sb.append(" AND PAY_TELNO = '"+ trnsctnBean.getPayTelNo() +"'");
		}
		if(trnsctnBean.getAmount() != 0){
			sb.append(" AND AMOUNT = '"+ CommonUtil.toString(trnsctnBean.getAmount()) +"'");
		}
		if(!trnsctnBean.getProductType().equals("")){
			sb.append(" AND PRODUCT_TYPE = '"+ trnsctnBean.getProductType() +"'");
		}
		if(!trnsctnBean.getProductName().equals("")){
			sb.append(" AND PRODUCT_NAME = '"+ trnsctnBean.getProductName() +"'");
		}
		if(!trnsctnBean.getCurType().equals("")){
			sb.append(" AND CUR_TYPE = '"+ trnsctnBean.getCurType() +"'");
		}
		if(!trnsctnBean.getIpAddress().equals("")){
			sb.append(" AND IP_ADDRESS = '"+ trnsctnBean.getIpAddress()+"'");
		}
		if(!trnsctnBean.getVan().equals("")){
			sb.append(" AND VAN = '"+ trnsctnBean.getVan() +"'");
		}
		if(!trnsctnBean.getTemp1().equals("")){
			sb.append(" AND TEMP1 = '"+ trnsctnBean.getTemp1() +"'");
		}
		if(!trnsctnBean.getTemp2().equals("")){
			sb.append(" AND TEMP2 = '"+ trnsctnBean.getTemp2() +"'");
		}
		if(!trnsctnBean.getAuth().equals("")){
			sb.append(" AND AUTH = '"+ trnsctnBean.getAuth() +"'");
		}
		
		return get(sb.toString());
		
	}
	
	public List<TrnsctnBean> get(String subQuery){
		String query = "SELECT IDX, TRANSACTION_ID, MERCHANT_ID, MALL_ID, SERVICE_TYPE, TRN_REQ_DATE, TRN_RES_DATE, TRN_STATUS, RESULT_CD, RESULT_MSG, VAN_TRANSACTION_ID, APPROVAL_NO, PAY_NO, "
			         + "PAY_USERID, PAY_EMAIL, PAY_NAME, PAY_TELNO, AMOUNT, PRODUCT_TYPE, PRODUCT_NAME, CUR_TYPE, IP_ADDRESS, VAN, TEMP1, TEMP2,AUTH,TRN_DATE "
			         + "FROM VW_TRNSCTN WHERE IDX IS NOT NULL " + subQuery + " AND TRN_DATE BETWEEN '";
		
		if(super.regStartDate != null){
			query += CommonUtil.convertTimestampToString(regStartDate, "yyyyMMdd");
		}else{
			query +="20130101";
		}
		if(super.regEndDate != null){
			query +="' AND '"+CommonUtil.convertTimestampToString(regEndDate, "yyyyMMdd")+"' ";
		}else{
			query +="' AND '"+CommonUtil.getCurrentDate("yyyyMMdd")+"'";
		}		
		if(!super.orderBy.equals("")){
			query += " ORDER BY IDX DESC";
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
				trnsctnBean.setServiceType(rset.getString("SERVICE_TYPE"));
				trnsctnBean.setTrnReqDate(rset.getTimestamp("TRN_REQ_DATE"));
				trnsctnBean.setTrnResDate(rset.getTimestamp("TRN_RES_DATE"));
				trnsctnBean.setTrnStatus(CommonUtil.nToB(rset.getString("TRN_STATUS")));
				trnsctnBean.setResultCd(CommonUtil.nToB(rset.getString("RESULT_CD")));
				trnsctnBean.setResultMsg(CommonUtil.nToB(rset.getString("RESULT_MSG")));
				trnsctnBean.setVanTransactionId(CommonUtil.nToB(rset.getString("VAN_TRANSACTION_ID")));
				trnsctnBean.setApprovalNo(CommonUtil.nToB(rset.getString("APPROVAL_NO")));
				trnsctnBean.setPayNo(CommonUtil.nToB(rset.getString("PAY_NO")));
				trnsctnBean.setPayUserId(CommonUtil.nToB(rset.getString("PAY_USERID")));
				trnsctnBean.setPayEmail(CommonUtil.nToB(rset.getString("PAY_EMAIL")));
				trnsctnBean.setPayName(CommonUtil.nToB(rset.getString("PAY_NAME")));
				trnsctnBean.setPayTelNo(CommonUtil.nToB(rset.getString("PAY_TELNO")));
				trnsctnBean.setAmount(rset.getDouble("AMOUNT"));
				trnsctnBean.setProductType(CommonUtil.nToB(rset.getString("PRODUCT_TYPE")));
				trnsctnBean.setProductName(CommonUtil.nToB(rset.getString("PRODUCT_NAME")));
				trnsctnBean.setCurType(CommonUtil.nToB(rset.getString("CUR_TYPE")));
				trnsctnBean.setIpAddress(CommonUtil.nToB(rset.getString("IP_ADDRESS")));
				trnsctnBean.setVan(CommonUtil.nToB(rset.getString("VAN")));
				trnsctnBean.setTemp1(CommonUtil.nToB(rset.getString("TEMP1")));
				trnsctnBean.setTemp2(CommonUtil.nToB(rset.getString("TEMP2")));
				trnsctnBean.setAuth(CommonUtil.nToB(rset.getString("AUTH")));
				trnsctnBean.setTrnDate(CommonUtil.nToB(rset.getString("TRN_DATE")));
				super.totalCount = rset.getLong("TOTAL_COUNT");
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
	
	public synchronized boolean insert(TrnsctnBean trnsctnBean){
		
		String query = "INSERT INTO TB_TRNSCTN (IDX, TRANSACTION_ID, MERCHANT_ID, MALL_ID, SERVICE_TYPE, TRN_REQ_DATE, TRN_RES_DATE, TRN_STATUS, RESULT_CD, RESULT_MSG, VAN_TRANSACTION_ID, APPROVAL_NO, PAY_NO, PAY_USERID, PAY_EMAIL, PAY_NAME, PAY_TELNO, AMOUNT, PRODUCT_TYPE, PRODUCT_NAME, CUR_TYPE, IP_ADDRESS, VAN, TEMP1, TEMP2,AUTH) "
			         + "VALUES (?, ?, ?, ?, ?, SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		
		try{
			trnsctnBean.setIdx(SequenceDAO.getSequenceLong("SEQ_TRNSCTN"));
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setLong(idx++, trnsctnBean.getIdx());
			pstmt.setString(idx++, trnsctnBean.getTransactionId());
			pstmt.setString(idx++, trnsctnBean.getMerchantId());
			pstmt.setString(idx++, trnsctnBean.getMallId());
			pstmt.setString(idx++, trnsctnBean.getServiceType());
			pstmt.setTimestamp(idx++, trnsctnBean.getTrnResDate());
			pstmt.setString(idx++, trnsctnBean.getTrnStatus());
			pstmt.setString(idx++, trnsctnBean.getResultCd());
			pstmt.setString(idx++, trnsctnBean.getResultMsg());
			pstmt.setString(idx++, trnsctnBean.getVanTransactionId());
			pstmt.setString(idx++, trnsctnBean.getApprovalNo());
			pstmt.setString(idx++, trnsctnBean.getPayNo());
			pstmt.setString(idx++, trnsctnBean.getPayUserId());
			pstmt.setString(idx++, CommonUtil.cut(trnsctnBean.getPayEmail(),100));
			pstmt.setString(idx++, CommonUtil.cut(trnsctnBean.getPayName(),50));
			pstmt.setString(idx++, CommonUtil.cut(trnsctnBean.getPayTelNo(),20));
			pstmt.setDouble(idx++, trnsctnBean.getAmount());
			pstmt.setString(idx++, trnsctnBean.getProductType());
			pstmt.setString(idx++, CommonUtil.cut(trnsctnBean.getProductName(),50));
			pstmt.setString(idx++, trnsctnBean.getCurType());
			pstmt.setString(idx++, trnsctnBean.getIpAddress());
			pstmt.setString(idx++, trnsctnBean.getVan());
			pstmt.setString(idx++, CommonUtil.cut(trnsctnBean.getTemp1(),50));
			pstmt.setString(idx++, CommonUtil.cut(trnsctnBean.getTemp2(),50));
			pstmt.setString(idx++, trnsctnBean.getAuth());
			result = pstmt.executeUpdate();
			conn.commit();			
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(trnsctnBean),this);
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
	
	public synchronized boolean update(TrnsctnBean trnsctnBean){
		String query = "UPDATE TB_TRNSCTN SET SERVICE_TYPE=?, TRN_RES_DATE=SYSDATE, TRN_STATUS=?, RESULT_CD=?, RESULT_MSG=?, VAN_TRANSACTION_ID=?, APPROVAL_NO=?, "
					+"PAY_NO=?, PAY_USERID=?, PAY_EMAIL=?, PAY_NAME=?, PAY_TELNO=?, AMOUNT=?, PRODUCT_TYPE=?, PRODUCT_NAME=?, CUR_TYPE=?, IP_ADDRESS=?, VAN=?, TEMP1=?, TEMP2=?,AUTH=? "
					+"WHERE IDX = ?";

		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, trnsctnBean.getServiceType());
            pstmt.setString(idx++, trnsctnBean.getTrnStatus());
            pstmt.setString(idx++, trnsctnBean.getResultCd());
            pstmt.setString(idx++, trnsctnBean.getResultMsg());
            pstmt.setString(idx++, trnsctnBean.getVanTransactionId());
            pstmt.setString(idx++, trnsctnBean.getApprovalNo());
            pstmt.setString(idx++, trnsctnBean.getPayNo());
            pstmt.setString(idx++, trnsctnBean.getPayUserId());
            pstmt.setString(idx++, trnsctnBean.getPayEmail());
            pstmt.setString(idx++, trnsctnBean.getPayName());
            pstmt.setString(idx++, trnsctnBean.getPayTelNo());
            pstmt.setDouble(idx++, trnsctnBean.getAmount());
            pstmt.setString(idx++, trnsctnBean.getProductType());
            pstmt.setString(idx++, trnsctnBean.getProductName());
            pstmt.setString(idx++, trnsctnBean.getCurType());
            pstmt.setString(idx++, trnsctnBean.getIpAddress());
            pstmt.setString(idx++, trnsctnBean.getVan());
            pstmt.setString(idx++, trnsctnBean.getTemp1());
            pstmt.setString(idx++, trnsctnBean.getTemp2());
            pstmt.setString(idx++, trnsctnBean.getAuth());
			pstmt.setLong(idx++, trnsctnBean.getIdx());
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(trnsctnBean),this);
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
	
	public synchronized boolean updateTrnStatus(String transactionId,String trnStatus){     //취소대기상태 UPDATE
		String query = "UPDATE TB_TRNSCTN SET TRN_STATUS=? WHERE TRANSACTION_ID = ?";
		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, trnStatus);
			pstmt.setString(idx++, transactionId);
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
	
	/**
	 * 매입요청된 상태로 변경 
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public synchronized boolean updateTrnStatusToAcquire(){   
		String query = "UPDATE TB_TRNSCTN SET TRN_STATUS = '07' WHERE TRN_RES_DATE > SYSDATE-6  AND TO_CHAR(TRN_RES_DATE,'YYYYMMDD') < TO_CHAR(SYSDATE,'YYYYMMDD') AND TRN_STATUS = '02' ";
		String query2 = "UPDATE TB_TRNSCTN SET TRN_STATUS='09' WHERE TRANSACTION_ID IN (SELECT TRANSACTION_ID FROM  VW_TRNSCTN  WHERE TRN_RES_DATE > SYSDATE-8 AND TO_CHAR(TRN_RES_DATE,'YYYYMMDD') < TO_CHAR(SYSDATE-3,'YYYYMMDD')  AND TRN_STATUS = '07') ";
		 
	    int result = 0;
	    try{
	    	result = DBFactory.getInstance().statementExecuteUpdate(query);
	    	result = DBFactory.getInstance().statementExecuteUpdate(query2);
	    }catch(Exception e){
	    	Log.debug("log.sql","query=["+query+"]",this);
	    	Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
	    }
	    
	    if(result > -1){
	    	Log.debug("log.sql","TB_TRNSCTN TRN_STATUS UPDATE 02 --> 07,TRNSCTN_INFO UPDATE",this);
	    	return true;
	    }else{
	    	Log.debug("log.sql","TB_TRNSCTN TRN_STATUS UPDATE FAILURE ",this);
	    	return false;
	    }
	}
	
	public boolean updateDemoToFail(){
		String query = "UPDATE TB_TRNSCTN  SET TRN_STATUS='06'  WHERE TEMP2 IN ('DEMO','TESTCARD') AND TRN_STATUS='02'";
		 
	    int result = 0;
	    try{
	    	result = DBFactory.getInstance().statementExecuteUpdate(query);
	    }catch(Exception e){
	    	Log.debug("log.sql","query=["+query+"]",this);
	    	Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
	    }
	    
	    if(result > -1){
	    	Log.debug("log.sql","TB_TRNSCTN TRN_STATUS UPDATE DEMO,TESTCARD --> 06,TRNSCTN_INFO UPDATE",this);
	    	return true;
	    }else{
	    	return false;
	    }
	}
	
	
	public boolean updateSettleStatus(String settleId){
		String query = "UPDATE TB_TRNSCTN SET TRN_STATUS = '13' WHERE TRANSACTION_ID IN ( "
					+" SELECT TRN_ID FROM TB_BILL_TRN_ID WHERE BILL_ID = (SELECT TRN_BILL_ID FROM TB_MERCHANT_SETTLE WHERE SETTLE_ID ='"+settleId+"')) AND TRN_STATUS ='11'";
		 
	    int result = 0;
	    try{
	    	result = DBFactory.getInstance().statementExecuteUpdate(query);
	    }catch(Exception e){
	    	Log.debug("log.sql","query=["+query+"]",this);
	    	Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
	    }
	    
	    if(result > -1){
	    	Log.debug("log.sql","TB_TRNSCTN TRN_STATUS UPDATE 11 --> 13,정산완료로 업데이트 "+settleId,this);
	    	return true;
	    }else{
	    	return false;
	    }
	}
	
	
	public String getDailyTrnsctn(){
		if(CommonUtil.parseLong(CommonUtil.getCurrentDate("HH")) > 23){
			updateTestCardTransaction();
		}
		
		String query ="SELECT CUR_TYPE,SUM(AMOUNT) AS AMT , COUNT(IDX) AS CNT FROM VW_TRNSCTN WHERE " 
					+" TO_CHAR(TRN_REQ_DATE,'YYMMDD') = TO_CHAR(SYSDATE,'YYMMDD') AND RESULT_CD='00' AND RESULT_MSG='0000' AND TRN_STATUS='02' AND TEMP2 IS NULL GROUP BY CUR_TYPE";
		DBFactory db	= null;
		Connection conn			= null;
		Statement	stmt		= null;
		ResultSet rset			= null;
		StringBuffer sb = new StringBuffer();
		sb.append("DAY=["+CommonUtil.getCurrentDate("MM-dd-HH")+"]");
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			stmt	= conn.createStatement();
			rset	= stmt.executeQuery(query);
			
			while(rset.next()){
				sb.append(rset.getString("CUR_TYPE")+":SUM=["+CommonUtil.makeMoneyType(rset.getLong("AMT"),",")+"]CNT=["+rset.getInt("CNT")+"]");
			}
		}catch(Exception e){
	        Log.debug("log.day",CommonUtil.getExceptionMessage(e), this);
	    }finally{
	        db.close(conn,stmt,rset);
	    }
	    
	    return sb.toString();
	}
	
	public boolean updateTestCardTransaction(){
		
		boolean isSuccess = false;
		String query = "UPDATE TB_TRNSCTN SET TRN_STATUS='06' WHERE TEMP2='TESTCARD' AND TRN_STATUS IN ('07','02') AND TRN_REQ_DATE > SYSDATE -1";
		try{
			 if(DBFactory.getInstance().statementExecuteUpdate(query) > 0){
				 isSuccess = true;
				 Log.debug("log.day","UPDATE TESTCARD TRN_STATUS TO VOID",this);
			 }
		}catch(Exception e){
			Log.debug("log.day","UPDATE TRANSACTION="+CommonUtil.getExceptionMessage(e),this);
		}
		return isSuccess;
	}
	
	
	
	public CurrentTrnsctnBean getTrnsctnCount(String subQuery){
		String query = "SELECT A.COUNT AS DAY_CNT , A.SUM AS DAY_SUM, B.COUNT AS WEEK_CNT, B.SUM AS WEEK_SUM, C.COUNT AS MONTH_CNT, C.SUM AS MONTH_SUM "  
					 + "FROM (SELECT COUNT(*) AS COUNT, SUM(AMOUNT) AS SUM FROM VW_TRNSCTN WHERE TRN_STATUS IN ('02','07','08','09','11','12','13','10') AND TRN_REQ_DATE BETWEEN TRUNC(SYSDATE)  AND TRUNC(SYSDATE) + 0.99999421 AND MERCHANT_ID IN (" + subQuery + ")) A, "
					 + "(SELECT COUNT(*) AS COUNT, SUM(AMOUNT) AS SUM FROM VW_TRNSCTN WHERE TRN_STATUS IN ('02','07','08','09','11','12','13','10') AND TRN_REQ_DATE BETWEEN TRUNC(SYSDATE+1)-TO_CHAR(SYSDATE,'D') AND TRUNC(SYSDATE+1)-TO_CHAR(SYSDATE,'D')+6.99999421 AND MERCHANT_ID IN (" + subQuery + ")) B, "
					 + "(SELECT COUNT(*) AS COUNT, SUM(AMOUNT) AS SUM FROM VW_TRNSCTN WHERE TRN_STATUS IN ('02','07','08','09','11','12','13','10') AND TRN_REQ_DATE BETWEEN TRUNC(SYSDATE+1)-TO_CHAR(SYSDATE,'DD') AND TRUNC(LAST_DAY(SYSDATE))+0.99999421 AND MERCHANT_ID IN (" + subQuery + ")) C ";
		CurrentTrnsctnBean currentTrnsctnBean = new CurrentTrnsctnBean();
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
				currentTrnsctnBean.setDailyCount(rset.getDouble("DAY_CNT"));
				currentTrnsctnBean.setDailySum(rset.getDouble("DAY_SUM"));
				currentTrnsctnBean.setWeekCount(rset.getDouble("WEEK_CNT"));
				currentTrnsctnBean.setWeekSum(rset.getDouble("WEEK_SUM"));
				currentTrnsctnBean.setMonthCount(rset.getDouble("MONTH_CNT"));
				currentTrnsctnBean.setMonthSum(rset.getDouble("MONTH_SUM"));
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return currentTrnsctnBean;
	}
	
	
	
	
	public CurrentTrnsctnBean getYesterdayTrnsctnCount(String subQuery){
		String query = "SELECT A.COUNT AS DAY_CNT , A.SUM AS DAY_SUM, B.COUNT AS WEEK_CNT, B.SUM AS WEEK_SUM, C.COUNT AS MONTH_CNT, C.SUM AS MONTH_SUM "  
					 + "FROM (SELECT COUNT(*) AS COUNT, SUM(AMOUNT) AS SUM FROM VW_TRNSCTN WHERE TRN_STATUS NOT IN ('00','01','03','06','18','22','23') AND TRN_REQ_DATE BETWEEN TRUNC(SYSDATE-1) AND TRUNC(SYSDATE-1)+0.99999421 AND MERCHANT_ID IN (" + subQuery + ")) A, "
					 + "(SELECT COUNT(*) AS COUNT, SUM(AMOUNT) AS SUM FROM VW_TRNSCTN WHERE TRN_STATUS NOT IN ('00','01','03','06','18','22','23') AND TRN_REQ_DATE BETWEEN TRUNC(SYSDATE+1)-TO_CHAR(SYSDATE,'D') AND TRUNC(SYSDATE+1)-TO_CHAR(SYSDATE,'D')+6.99999421 AND MERCHANT_ID IN (" + subQuery + ")) B, "
					 + "(SELECT COUNT(*) AS COUNT, SUM(AMOUNT) AS SUM FROM VW_TRNSCTN WHERE TRN_STATUS NOT IN ('00','01','03','06','18','22','23') AND TRN_REQ_DATE BETWEEN TRUNC(SYSDATE+1)-TO_CHAR(SYSDATE,'DD') AND TRUNC(LAST_DAY(SYSDATE))+0.99999421 AND MERCHANT_ID IN (" + subQuery + ")) C ";
		CurrentTrnsctnBean currentTrnsctnBean = new CurrentTrnsctnBean();
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
				currentTrnsctnBean.setDailyCount(rset.getDouble("DAY_CNT"));
				currentTrnsctnBean.setDailySum(rset.getDouble("DAY_SUM"));
				currentTrnsctnBean.setWeekCount(rset.getDouble("WEEK_CNT"));
				currentTrnsctnBean.setWeekSum(rset.getDouble("WEEK_SUM"));
				currentTrnsctnBean.setMonthCount(rset.getDouble("MONTH_CNT"));
				currentTrnsctnBean.setMonthSum(rset.getDouble("MONTH_SUM"));
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return currentTrnsctnBean;
	}
	
	
	
	public List<TrnsctnBean> getDuplicatedPayNo(String merchantId, String payNo){
		List<TrnsctnBean> list = new ArrayList<TrnsctnBean>();
		String query = "SELECT IDX, TRANSACTION_ID, MERCHANT_ID, MALL_ID, SERVICE_TYPE, TRN_REQ_DATE, TRN_RES_DATE, TRN_STATUS, RESULT_CD, RESULT_MSG, VAN_TRANSACTION_ID, APPROVAL_NO, PAY_NO," 
					+ " PAY_USERID, PAY_EMAIL, PAY_NAME, PAY_TELNO, AMOUNT, PRODUCT_TYPE, PRODUCT_NAME, CUR_TYPE, IP_ADDRESS, VAN, TEMP1, TEMP2 ,AUTH,TRN_DATE FROM VW_TRNSCTN WHERE MERCHANT_ID =? AND PAY_NO =? AND TRN_DATE >= TO_CHAR(SYSDATE-1,'YYYYMMDD') AND TRN_STATUS IN ('02','07') ";
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			pstmt.setString(1,merchantId);
			pstmt.setString(2,payNo);
			rset 	= pstmt.executeQuery();
			
			while(rset.next()){
				TrnsctnBean trnsctnBean = new TrnsctnBean();
				trnsctnBean.setIdx(rset.getLong("IDX"));
				trnsctnBean.setTransactionId(rset.getString("TRANSACTION_ID"));
				trnsctnBean.setMerchantId(rset.getString("MERCHANT_ID"));
				trnsctnBean.setMallId(rset.getString("MALL_ID"));
				trnsctnBean.setServiceType(rset.getString("SERVICE_TYPE"));
				trnsctnBean.setTrnReqDate(rset.getTimestamp("TRN_REQ_DATE"));
				trnsctnBean.setTrnResDate(rset.getTimestamp("TRN_RES_DATE"));
				trnsctnBean.setTrnStatus(CommonUtil.nToB(rset.getString("TRN_STATUS")));
				trnsctnBean.setResultCd(CommonUtil.nToB(rset.getString("RESULT_CD")));
				trnsctnBean.setResultMsg(CommonUtil.nToB(rset.getString("RESULT_MSG")));
				trnsctnBean.setVanTransactionId(CommonUtil.nToB(rset.getString("VAN_TRANSACTION_ID")));
				trnsctnBean.setApprovalNo(CommonUtil.nToB(rset.getString("APPROVAL_NO")));
				trnsctnBean.setPayNo(CommonUtil.nToB(rset.getString("PAY_NO")));
				trnsctnBean.setPayUserId(CommonUtil.nToB(rset.getString("PAY_USERID")));
				trnsctnBean.setPayEmail(CommonUtil.nToB(rset.getString("PAY_EMAIL")));
				trnsctnBean.setPayName(CommonUtil.nToB(rset.getString("PAY_NAME")));
				trnsctnBean.setPayTelNo(CommonUtil.nToB(rset.getString("PAY_TELNO")));
				trnsctnBean.setAmount(rset.getDouble("AMOUNT"));
				trnsctnBean.setProductType(CommonUtil.nToB(rset.getString("PRODUCT_TYPE")));
				trnsctnBean.setProductName(CommonUtil.nToB(rset.getString("PRODUCT_NAME")));
				trnsctnBean.setCurType(CommonUtil.nToB(rset.getString("CUR_TYPE")));
				trnsctnBean.setIpAddress(CommonUtil.nToB(rset.getString("IP_ADDRESS")));
				trnsctnBean.setVan(CommonUtil.nToB(rset.getString("VAN")));
				trnsctnBean.setTemp1(CommonUtil.nToB(rset.getString("TEMP1")));
				trnsctnBean.setTemp2(CommonUtil.nToB(rset.getString("TEMP2")));
				trnsctnBean.setAuth(CommonUtil.nToB(rset.getString("AUTH")));
				trnsctnBean.setTrnDate(CommonUtil.nToB(rset.getString("TRN_DATE")));
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
	

	
	
	
	
	
	
	
	
}

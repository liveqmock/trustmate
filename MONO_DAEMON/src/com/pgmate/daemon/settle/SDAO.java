/* 
 * Project      : PGv2_SETTLE
 * File Name    : net.kttrust.settle2.SDAO.java
 * Date         : 2010. 3. 1.
 * Version      : 1.0
 * Author       : ginaida@trustmate.net
 * Comment      :  
 */

package com.pgmate.daemon.settle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import biz.trustnet.common.db.DBUtil;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.BeanUtil;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.MerchantDepositBean;
import com.pgmate.model.db.MerchantSettleBean;
import com.pgmate.model.db.factory.DBFactory;


public class SDAO {

	public SDAO(){
	}
	
	public boolean backup(){
		String query = "CREATE TABLE OLD_TRNSCTN_"+CommonUtil.getCurrentDate("yyMMdd")+" AS SELECT * FROM TB_TRNSCTN";
		
		int result = 0;
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
			Log.debug("log.sql",query,this);
			Log.debug("log.sql",e.getMessage(),this);		
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
	
	public boolean updateRefund(){
		String query = "UPDATE TB_TRNSCTN SET TRN_STATUS = DECODE(TRN_STATUS,'09','18','11','14') "
					+"	WHERE TRANSACTION_ID IN ( SELECT TRANSACTION_ID FROM TB_TRNSCTN_VOID WHERE TO_CHAR(REG_DATE,'YYYYMM') >= TO_CHAR(ADD_MONTHS(SYSDATE,-1),'YYYYMM') AND STATUS ='00'"
					+"	) AND TRN_STATUS NOT IN ('06','18','14') ";
		
		int result = 0;
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
			Log.debug("log.sql",query,this);
			Log.debug("log.sql",e.getMessage(),this);		
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
	
	public boolean insertBillTrn(MerchantSettleBean merchantSettleBean){
		String query = "INSERT INTO TB_BILL_TRN_ID ("
				+"	SELECT ? AS BILL_ID,A.MERCHANT_ID,A.TRANSACTION_ID,'T',CARD_TYPE ,AMOUNT,SYSDATE" 
				+"	FROM VW_TRNSCTN A ,VW_TRNSCTN_SCR B WHERE A.TRANSACTION_ID=B.TRANSACTION_ID" 
				+"	AND A.TRN_STATUS IN ('02','07','08','09') AND A.RESULT_CD='0'"
				+"	AND TO_CHAR(A.TRN_REQ_DATE,'YYYYMMDDHH24MISS') BETWEEN ? AND ?"
				+"	AND A.MERCHANT_ID =?" 
				+"	AND A.TRANSACTION_ID NOT IN (SELECT TRN_ID FROM TB_BILL_TRN_ID WHERE BILL_TYPE='T')"
				+"	)";
		
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, merchantSettleBean.getTrnBillId());
			pstmt.setString(idx++, merchantSettleBean.getStartDay()+"000000");
			pstmt.setString(idx++, merchantSettleBean.getEndDay()+"235959");
			pstmt.setString(idx++, merchantSettleBean.getMerchantId());
			result = pstmt.executeUpdate();
			conn.commit();			
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(merchantSettleBean),this);
			Log.debug("log.sql",e.getMessage(),this);		
		}finally{
			db.close(pstmt);
			db.close(conn);			
		}
		
		if(result > 0){
			calcBillTrnSum(merchantSettleBean,"NOW");
			return true;
		}else{
			return false;
		}
	}
	
	public boolean insertBillTrnPre(MerchantSettleBean merchantSettleBean){
		String query = "INSERT INTO TB_BILL_TRN_ID ("
				+"	SELECT ? AS BILL_ID,A.MERCHANT_ID,A.TRANSACTION_ID,'T',CARD_TYPE ,AMOUNT,SYSDATE" 
				+"	FROM VW_TRNSCTN A ,VW_TRNSCTN_SCR B WHERE A.TRANSACTION_ID=B.TRANSACTION_ID" 
				+"	AND A.TRN_STATUS IN ('02','07','08','09') AND A.RESULT_CD='0'"
				+"	AND TO_CHAR(A.TRN_REQ_DATE,'YYYYMMDDHH24MISS') BETWEEN ? AND ?"
				+"	AND A.MERCHANT_ID =?" 
				+"	AND A.TRANSACTION_ID NOT IN (SELECT TRN_ID FROM TB_BILL_TRN_ID WHERE BILL_TYPE='T')"
				+"	)";
		
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, merchantSettleBean.getPreTrnBillId());
			pstmt.setString(idx++, CommonUtil.getOpDate(GregorianCalendar.MONTH,-5,merchantSettleBean.getStartDay())+"000000");
			pstmt.setString(idx++, merchantSettleBean.getStartDay()+"000000");
			pstmt.setString(idx++, merchantSettleBean.getMerchantId());
			result = pstmt.executeUpdate();
			conn.commit();			
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(merchantSettleBean),this);
			Log.debug("log.sql",e.getMessage(),this);		
		}finally{
			db.close(pstmt);
			db.close(conn);			
		}
		
		if(result > 0){
			calcBillTrnSum(merchantSettleBean,"PRE");
			return true;
		}else{
			return false;
		}
	}
	
	public void calcBillTrnSum(MerchantSettleBean merchantSettleBean,String term){
		String query = "SELECT CARD_TYPE,COUNT(1) CNT,SUM(AMOUNT) AMT FROM TB_BILL_TRN_ID WHERE BILL_ID =? GROUP BY CARD_TYPE";
		
		DBFactory db 			= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			if(term.equals("NOW")){
				pstmt.setString(idx++, merchantSettleBean.getTrnBillId());
			}else{
				pstmt.setString(idx++, merchantSettleBean.getPreTrnBillId());
			}
			rset = pstmt.executeQuery();
			
			while(rset.next()){
				String cardType = rset.getString("CARD_TYPE"); 
				if(cardType.equals("VISA")||cardType.equals("MASTER")){
					if(term.equals("NOW")){
						merchantSettleBean.setTrnVmCnt(merchantSettleBean.getTrnVmCnt()+rset.getDouble("CNT"));
						merchantSettleBean.setTrnVmAmt(merchantSettleBean.getTrnVmAmt()+rset.getDouble("AMT"));
					}else{
						merchantSettleBean.setPreTrnVmCnt(merchantSettleBean.getPreTrnVmCnt()+rset.getDouble("CNT"));
						merchantSettleBean.setPreTrnVmAmt(merchantSettleBean.getPreTrnVmAmt()+rset.getDouble("AMT"));
					}
				}
				if(cardType.equals("AMEX")||cardType.equals("JCB")){
					if(term.equals("NOW")){
						merchantSettleBean.setTrnJaCnt(merchantSettleBean.getTrnJaCnt()+rset.getDouble("CNT"));
						merchantSettleBean.setTrnJaAmt(merchantSettleBean.getTrnJaAmt()+rset.getDouble("AMT"));
					}else{
						merchantSettleBean.setPreTrnJaCnt(merchantSettleBean.getPreTrnJaCnt()+rset.getDouble("CNT"));
						merchantSettleBean.setPreTrnJaAmt(merchantSettleBean.getPreTrnJaAmt()+rset.getDouble("AMT"));
					}
				}
			}
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(merchantSettleBean),this);
			Log.debug("log.sql",e.getMessage(),this);		
		}finally{
			db.close(conn, pstmt, rset);
		}
		
	}
	
	public boolean insertBillRefund(MerchantSettleBean merchantSettleBean){
		String query = "INSERT INTO TB_BILL_TRN_ID ("
				+"	SELECT ? AS BILL_ID,B.MERCHANT_ID,B.TRANSACTION_ID,'R',CARD_TYPE ,VOID_AMOUNT,SYSDATE  " 
				+"	FROM VW_TRNSCTN_VOID A ,VW_TRNSCTN B,VW_TRNSCTN_SCR C WHERE A.TRANSACTION_ID=B.TRANSACTION_ID " 
				+"	AND B.TRANSACTION_ID = C.TRANSACTION_ID  AND A.STATUS='00' AND TO_CHAR(A.REG_DATE,'YYYYMMDDHH24MISS') BETWEEN ? AND ?  "
				+"	AND A.ROOT_TRN_STATUS IN ('07','09','18')"
				+"	AND B.MERCHANT_ID =?" 
				+"	AND A.TRANSACTION_ID NOT IN (SELECT TRN_ID FROM TB_BILL_TRN_ID WHERE BILL_TYPE='R')"
				+"	)";
		
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, merchantSettleBean.getRefundBillId());
			pstmt.setString(idx++, merchantSettleBean.getStartDay()+"000000");
			pstmt.setString(idx++, merchantSettleBean.getEndDay()+"235959");
			pstmt.setString(idx++, merchantSettleBean.getMerchantId());
			result = pstmt.executeUpdate();
			conn.commit();			
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(merchantSettleBean),this);
			Log.debug("log.sql",e.getMessage(),this);		
		}finally{
			db.close(pstmt);
			db.close(conn);			
		}
		
		if(result > 0){
			calcBillTrnRefundSum(merchantSettleBean,"NOW");
			return true;
		}else{
			return false;
		}
	}
	
	public boolean insertBillRefundPre(MerchantSettleBean merchantSettleBean){
		String query = "INSERT INTO TB_BILL_TRN_ID ("
				+"	SELECT ? AS BILL_ID,B.MERCHANT_ID,B.TRANSACTION_ID,'R',CARD_TYPE ,VOID_AMOUNT,SYSDATE  " 
				+"	FROM VW_TRNSCTN_VOID A ,VW_TRNSCTN B,VW_TRNSCTN_SCR C WHERE A.TRANSACTION_ID=B.TRANSACTION_ID " 
				+"	AND B.TRANSACTION_ID = C.TRANSACTION_ID  AND A.STATUS='00' AND TO_CHAR(A.REG_DATE,'YYYYMMDDHH24MISS') BETWEEN ? AND ?  "
				+"	AND A.ROOT_TRN_STATUS IN ('11','12','13')"
				+"	AND B.MERCHANT_ID =?" 
				+"	AND A.TRANSACTION_ID NOT IN (SELECT TRN_ID FROM TB_BILL_TRN_ID WHERE BILL_TYPE='R')"
				+"	)";
		
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, merchantSettleBean.getPreRefundBillId());
			pstmt.setString(idx++, merchantSettleBean.getStartDay()+"000000");
			pstmt.setString(idx++, merchantSettleBean.getEndDay()+"235959");
			pstmt.setString(idx++, merchantSettleBean.getMerchantId());
			result = pstmt.executeUpdate();
			conn.commit();			
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(merchantSettleBean),this);
			Log.debug("log.sql",e.getMessage(),this);		
		}finally{
			db.close(pstmt);
			db.close(conn);			
		}
		
		if(result > 0){
			calcBillTrnRefundSum(merchantSettleBean,"PRE");
			return true;
		}else{
			return false;
		}
	}
	
	public void calcBillTrnRefundSum(MerchantSettleBean merchantSettleBean,String term){
		String query = "SELECT CARD_TYPE,COUNT(1) CNT,SUM(AMOUNT) AMT FROM TB_BILL_TRN_ID WHERE BILL_ID =? GROUP BY CARD_TYPE";
		
		DBFactory db 			= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			if(term.equals("NOW")){
				pstmt.setString(idx++, merchantSettleBean.getRefundBillId());
			}else{
				pstmt.setString(idx++, merchantSettleBean.getPreRefundBillId());
			}
			rset = pstmt.executeQuery();
			
			while(rset.next()){
				String cardType = rset.getString("CARD_TYPE"); 
				if(cardType.equals("VISA")||cardType.equals("MASTER")){
					if(term.equals("NOW")){
						merchantSettleBean.setRefundVmCnt(merchantSettleBean.getRefundVmCnt()+rset.getDouble("CNT"));
						merchantSettleBean.setRefundVmAmt(merchantSettleBean.getRefundVmAmt()+rset.getDouble("AMT"));
					}else{
						merchantSettleBean.setPreRefundVmCnt(merchantSettleBean.getPreRefundVmCnt()+rset.getDouble("CNT"));
						merchantSettleBean.setPreRefundVmAmt(merchantSettleBean.getPreRefundVmAmt()+rset.getDouble("AMT"));
					}
				}
				if(cardType.equals("AMEX")||cardType.equals("JCB")){
					if(term.equals("NOW")){
						merchantSettleBean.setRefundJaCnt(merchantSettleBean.getRefundJaCnt()+rset.getDouble("CNT"));
						merchantSettleBean.setRefundJaAmt(merchantSettleBean.getRefundJaAmt()+rset.getDouble("AMT"));
					}else{
						merchantSettleBean.setPreRefundJaCnt(merchantSettleBean.getPreRefundJaCnt()+rset.getDouble("CNT"));
						merchantSettleBean.setPreRefundJaAmt(merchantSettleBean.getPreRefundJaAmt()+rset.getDouble("AMT"));
					}
				}
			}
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(merchantSettleBean),this);
			Log.debug("log.sql",e.getMessage(),this);		
		}finally{
			db.close(conn, pstmt, rset);
		}
		
	}
	
	
	
	public boolean insertBillCb(MerchantSettleBean merchantSettleBean){
		String query = "INSERT INTO TB_BILL_TRN_ID ("
				+"	SELECT ? AS BILL_ID,B.MERCHANT_ID,B.TRANSACTION_ID,'C',CARD_TYPE ,AMOUNT,SYSDATE   " 
				+"	FROM VW_TRNSCTN_CB A ,VW_TRNSCTN B,VW_TRNSCTN_SCR C WHERE A.TRANSACTION_ID=B.TRANSACTION_ID  " 
				+"	AND B.TRANSACTION_ID = C.TRANSACTION_ID  AND TO_CHAR(A.REG_DATE,'YYYYMMDDHH24MISS') BETWEEN ? AND TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')  "
				+"	AND B.MERCHANT_ID =?" 
				+"	AND A.TRANSACTION_ID NOT IN (SELECT TRN_ID FROM TB_BILL_TRN_ID WHERE BILL_TYPE='C')"
				+"	)";
		
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, merchantSettleBean.getCbBillId());
			pstmt.setString(idx++, merchantSettleBean.getStartDay()+"000000");
			pstmt.setString(idx++, merchantSettleBean.getMerchantId());
			result = pstmt.executeUpdate();
			conn.commit();			
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(merchantSettleBean),this);
			Log.debug("log.sql",e.getMessage(),this);		
		}finally{
			db.close(pstmt);
			db.close(conn);			
		}
		
		if(result > 0){
			calcBillTrnCbSum(merchantSettleBean);
			updateBillCb(merchantSettleBean);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean updateBillCb(MerchantSettleBean merchantSettleBean){
		String query = "UPDATE TB_TRNSCTN_CB SET FLAG ='Y' WHERE TRANSACTION_ID IN ( "
				+"	SELECT TRN_ID FROM TB_BILL_TRN_ID WHERE BILL_TYPE ='C' AND MERCHANT_ID =? AND BILL_ID =?  )";
		
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, merchantSettleBean.getMerchantId());
			pstmt.setString(idx++, merchantSettleBean.getCbBillId());
			
			result = pstmt.executeUpdate();
			conn.commit();			
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(merchantSettleBean),this);
			Log.debug("log.sql",e.getMessage(),this);		
		}finally{
			db.close(pstmt);
			db.close(conn);			
		}
		
		if(result > 0){
			calcBillTrnCbSum(merchantSettleBean);
			return true;
		}else{
			return false;
		}
	}
	
	public void calcBillTrnCbSum(MerchantSettleBean merchantSettleBean){
		String query = "SELECT COUNT(1) CNT,SUM(AMOUNT) AMT FROM TB_BILL_TRN_ID WHERE BILL_ID =? ";
		
		DBFactory db 			= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, merchantSettleBean.getCbBillId());
			rset = pstmt.executeQuery();
			
			while(rset.next()){
				
				merchantSettleBean.setCbCnt(merchantSettleBean.getCbCnt()+rset.getDouble("CNT"));
				merchantSettleBean.setCbAmt(merchantSettleBean.getCbAmt()+rset.getDouble("AMT"));
				
			}
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(merchantSettleBean),this);
			Log.debug("log.sql",e.getMessage(),this);		
		}finally{
			db.close(conn, pstmt, rset);
		}
		
	}
	
	public boolean updateTrnId(MerchantSettleBean merchantSettleBean){
		String query = "UPDATE TB_TRNSCTN SET TRN_STATUS ='11' WHERE TRANSACTION_ID IN ("
				+"	SELECT TRN_ID FROM TB_BILL_TRN_ID WHERE MERCHANT_ID =? AND BILL_ID IN (?,?))" ;
		
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, merchantSettleBean.getMerchantId());
			pstmt.setString(idx++, merchantSettleBean.getTrnBillId());
			pstmt.setString(idx++, merchantSettleBean.getPreTrnBillId());
			result = pstmt.executeUpdate();
			conn.commit();			
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(merchantSettleBean),this);
			Log.debug("log.sql",e.getMessage(),this);		
		}finally{
			db.close(pstmt);
			db.close(conn);			
		}
		
		if(result > 0){
			calcBillTrnSum(merchantSettleBean,"NOW");
			return true;
		}else{
			return false;
		}
	}
	
	public List<MerchantDepositBean> getCurrentPay(String merchantId){
		
		String query = "SELECT IDX, MERCHANT_ID , RATE, LAST_AMT, CUR_AMT, PAY_AMT, TOT_AMT,PAY_MONTH,SETTLE_ID,PAY_SETTLE_ID , COMMENTS, REG_DATE "
			+" FROM TB_MERCHANT_DEPOSIT WHERE MERCHANT_ID=? AND PAY_MONTH=TO_CHAR(SYSDATE,'YYYYMM') AND PAY_SETTLE_ID IS NULL ORDER BY IDX ";
		
		
		
		List<MerchantDepositBean> list = new ArrayList<MerchantDepositBean>();
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			pstmt.setString(1, merchantId);
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
				merchantDepositBean.setPaySettleId(CommonUtil.nToB(rset.getString("PAY_SETTLE_ID")));
				merchantDepositBean.setComments(CommonUtil.nToB(rset.getString("COMMENTS")));
				merchantDepositBean.setRegDate(rset.getTimestamp("REG_DATE"));
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
	
	
	public boolean releaseDeposit(MerchantDepositBean merchantDepositBean){
		String query = "UPDATE TB_MERCHANT_DEPOSIT SET PAY_SETTLE_ID=?,COMMENTS =? WHERE IDX = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
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
	
	
	public boolean isCreateSettle(String merchantId,String endDate){
		
		
		String query = "SELECT COUNT(*) AS CNT FROM VW_TRNSCTN WHERE TRN_STATUS IN ('02','07','08','09') AND RESULT_CD='0' AND MERCHANT_ID =? AND TRN_DATE <= ? ";
		
		
		int result = 0;
		
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			pstmt.setString(1,merchantId);
			pstmt.setString(2, endDate);
			rset 	= pstmt.executeQuery();
			
			while(rset.next()){
				result = rset.getInt("CNT");
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		if(result > 0){
			return true;
		}else{
			return false;
		}
	}

	
}

package com.pgmate.model.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import biz.trustnet.common.db.DBUtil;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.GSIBean;
import com.pgmate.model.db.ReportMerchantPeriodBean;
import com.pgmate.model.db.ReportTrnsctnMonthlyBean;
import com.pgmate.model.db.factory.DBFactory;

/**
 * @author Administrator
 *
 */
public class TotalReportDAO extends DAO {

	public TotalReportDAO(){
	}
	
	public List<ReportMerchantPeriodBean> getByMerchantPeriod(String merchantId){
		String query = "SELECT * FROM TOTAL_BYMERCHANTPERIOD ";
		if(!merchantId.equals("")){
			query += " WHERE MERCHANT_ID='"+merchantId+"' ";
		}
		query += " ORDER BY MON DESC";		
		List<ReportMerchantPeriodBean> list = new ArrayList<ReportMerchantPeriodBean>();
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
				ReportMerchantPeriodBean mpBean = new ReportMerchantPeriodBean();
				
				mpBean.setMerchantId(rset.getString("MERCHANT_ID"));
				mpBean.setName(rset.getString("NAME"));
				mpBean.setActive(rset.getString("ACTIVE"));
				mpBean.setRegDate(rset.getTimestamp("REG_DATE"));
				mpBean.setMon(rset.getString("MON"));
				mpBean.setTotCnt(rset.getDouble("TOT_CNT"));
				mpBean.setTotAmt(rset.getDouble("TOT_AMT"));
				mpBean.setSucCnt(rset.getDouble("SUC_CNT"));
				mpBean.setSucAmt(rset.getDouble("SUC_AMT"));
				mpBean.setRefundCnt(rset.getDouble("REFUND_CNT"));
				mpBean.setRefundAmt(rset.getDouble("REFUND_AMT"));
				mpBean.setCbCnt(rset.getDouble("CB_CNT"));
				mpBean.setCbAmt(rset.getDouble("CB_AMT"));
				mpBean.setRefundRateByCnt(rset.getDouble("REFUNDRATEBYCNT"));
				mpBean.setRefundRateByAmt(rset.getDouble("REFUNDRATEBYAMT"));
				mpBean.setCbRateByCnt(rset.getDouble("CBRATEBYCNT"));
				mpBean.setCbRateByAmt(rset.getDouble("CBRATEBYAMT"));
				
				
				list.add(mpBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public List<ReportMerchantPeriodBean> getByGroupPeriod(String merchantId, String groupId){
		String query = "SELECT * FROM TOTAL_BYMERCHANTPERIOD ";
		if(!groupId.equals("")){
			if(!merchantId.equals("")){
				query += " WHERE MERCHANT_ID IN (SELECT MERCHANT_ID FROM TB_GROUP_MERCHANT WHERE GROUP_ID='"+groupId+"') AND MERCHANT_ID='"+merchantId+"' ";
			}else{
				query += " WHERE MERCHANT_ID IN (SELECT MERCHANT_ID FROM TB_GROUP_MERCHANT WHERE GROUP_ID='"+groupId+"') ";
			}
		}
		query += " ORDER BY MON DESC";		
		List<ReportMerchantPeriodBean> list = new ArrayList<ReportMerchantPeriodBean>();
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
				ReportMerchantPeriodBean mpBean = new ReportMerchantPeriodBean();
				
				mpBean.setMerchantId(rset.getString("MERCHANT_ID"));
				mpBean.setName(rset.getString("NAME"));
				mpBean.setActive(rset.getString("ACTIVE"));
				mpBean.setRegDate(rset.getTimestamp("REG_DATE"));
				mpBean.setMon(rset.getString("MON"));
				mpBean.setTotCnt(rset.getDouble("TOT_CNT"));
				mpBean.setTotAmt(rset.getDouble("TOT_AMT"));
				mpBean.setSucCnt(rset.getDouble("SUC_CNT"));
				mpBean.setSucAmt(rset.getDouble("SUC_AMT"));
				mpBean.setRefundCnt(rset.getDouble("REFUND_CNT"));
				mpBean.setRefundAmt(rset.getDouble("REFUND_AMT"));
				mpBean.setCbCnt(rset.getDouble("CB_CNT"));
				mpBean.setCbAmt(rset.getDouble("CB_AMT"));
				mpBean.setRefundRateByCnt(rset.getDouble("REFUNDRATEBYCNT"));
				mpBean.setRefundRateByAmt(rset.getDouble("REFUNDRATEBYAMT"));
				mpBean.setCbRateByCnt(rset.getDouble("CBRATEBYCNT"));
				mpBean.setCbRateByAmt(rset.getDouble("CBRATEBYAMT"));
				
				
				list.add(mpBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public List<ReportTrnsctnMonthlyBean> getByTrnsctnMonthly(String startMon,String endMon){
		String query = "SELECT * FROM TOTAL_TRNSCTN_MONTHLY ";
		if(!startMon.equals("")){
			query += " WHERE MON BETWEEN '"+startMon+"' AND '"+endMon+"'";
		}
		query += " ORDER BY MON DESC";		
		List<ReportTrnsctnMonthlyBean> list = new ArrayList<ReportTrnsctnMonthlyBean>();
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
				ReportTrnsctnMonthlyBean mpBean = new ReportTrnsctnMonthlyBean();
				
				mpBean.setMon(rset.getString("MON"));
				mpBean.setTotCnt(rset.getDouble("TOT_CNT"));
				mpBean.setTotAmt(rset.getDouble("TOT_AMT"));
				mpBean.setSucCnt(rset.getDouble("SUC_CNT"));
				mpBean.setSucAmt(rset.getDouble("SUC_AMT"));
				mpBean.setRefundCnt(rset.getDouble("REFUND_CNT"));
				mpBean.setRefundAmt(rset.getDouble("REFUND_AMT"));
				mpBean.setCbCnt(rset.getDouble("CB_CNT"));
				mpBean.setCbAmt(rset.getDouble("CB_AMT"));
				mpBean.setRefundRateByCnt(rset.getDouble("REFUNDRATEBYCNT"));
				mpBean.setRefundRateByAmt(rset.getDouble("REFUNDRATEBYAMT"));
				mpBean.setCbRateByCnt(rset.getDouble("CBRATEBYCNT"));
				mpBean.setCbRateByAmt(rset.getDouble("CBRATEBYAMT"));
				
				
				list.add(mpBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public List<GSIBean> getByApprovalRate(GSIBean gsiBean){
		String query = "";
		
		if(gsiBean.getTemp2String().equals("")){
			query = "SELECT TRN_DATE, VALID_CNT, VALID_AMT, DECLIEND_CNT, DECLIEND_AMT, TOT_CNT, TOT_AMT FROM VW_STATISTICS_APPROVAL_RATE "
				  + "WHERE TRN_DATE IS NOT NULL AND TRN_DATE BETWEEN ";
		}else{
			query = "SELECT TRN_DATE, MERCHANT_ID, VALID_CNT, VALID_AMT, DECLIEND_CNT, DECLIEND_AMT, TOT_CNT, TOT_AMT FROM TB_STATISTICS_APPROVAL_RATE "
				  + "WHERE TRN_DATE IS NOT NULL AND MERCHANT_ID='"+gsiBean.getTemp2String()+"' AND TRN_DATE BETWEEN ";
		}
		
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
		
		query += " ORDER BY TRN_DATE DESC";	
		
		query = super.toPaging(query);
		
		List<GSIBean> list = new ArrayList<GSIBean>();
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
				GSIBean gBean = new GSIBean();
				gBean.setTemp1String(rset.getString("TRN_DATE"));
				if(!gsiBean.getTemp2String().equals("")){
					gBean.setTemp2String(rset.getString("MERCHANT_ID"));
				}
				gBean.setTemp1Double(rset.getLong("VALID_CNT"));
				gBean.setTemp2Double(rset.getDouble("VALID_AMT"));
				gBean.setTemp3Double(rset.getLong("DECLIEND_CNT"));
				gBean.setTemp4Double(rset.getDouble("DECLIEND_AMT"));
				gBean.setTemp5Double(rset.getLong("TOT_CNT"));
				gBean.setTemp6Double(rset.getDouble("TOT_AMT"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(gBean);
				
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

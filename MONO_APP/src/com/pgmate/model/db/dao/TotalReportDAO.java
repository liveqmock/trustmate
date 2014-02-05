package com.pgmate.model.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

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
}

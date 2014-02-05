package com.pgmate.model.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.chart.BarChartBean;
import com.pgmate.model.chart.CalendarBean;
import com.pgmate.model.db.GSIBean;
import com.pgmate.model.db.factory.DBFactory;

public class ChartDAO extends DAO {
	
	private List<String> categoris = null;
	
	public ChartDAO(){
	}
	
	public List<String> getCategoris(){
		return categoris;
	}
	
	
	public List<BarChartBean> getByTrnsctnMonthly(String type){
		
		
		String query = "SELECT * FROM TOTAL_TRNSCTN_MONTHLY ORDER BY MON DESC";
		super.pageSize = 12;
		query = super.toPaging(query);
		
		categoris = new ArrayList<String>();
		List<BarChartBean> list = new ArrayList<BarChartBean>();
		
		
		BarChartBean totBean = new BarChartBean();
		if(type.equals("RATE")){
			totBean.setName("REFUND RATE BY COUNT");
		}else{
			totBean.setName("TOTAL");
		}
		double[] total = new double[12];
		
		BarChartBean validBean = new BarChartBean();
		if(type.equals("RATE")){
			validBean.setName("REFUND RATE BY AMOUNT");
		}else{
			validBean.setName("VALID");
		}
		
		double[] valid = new double[12];
		
		BarChartBean refundBean = new BarChartBean();
		if(type.equals("RATE")){
			refundBean.setName("C*B RATE BY COUNT");
		}else{
			refundBean.setName("REFUND");
		}
		double[] refund = new double[12];
		
		BarChartBean cbBean = new BarChartBean();
		if(type.equals("RATE")){
			cbBean.setName("C*B RATE BY AMOUNT");
		}else{
			cbBean.setName("C*B");
		}
		double[] cb = new double[12];
		
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			rset 	= pstmt.executeQuery();
			int idx = 0;
			while(rset.next()){
				categoris.add(rset.getString("MON"));
				
				if(type.equals("AMOUNT")){
					total[idx] = rset.getDouble("TOT_AMT");
					valid[idx] = rset.getDouble("SUC_AMT");
					refund[idx] = rset.getDouble("REFUND_AMT");
					cb[idx] = rset.getDouble("CB_AMT");
				}else if(type.equals("COUNT")){
					total[idx] = rset.getDouble("TOT_CNT");
					valid[idx] = rset.getDouble("SUC_CNT");
					refund[idx] = rset.getDouble("REFUND_CNT");
					cb[idx] = rset.getDouble("CB_CNT");
				}else if(type.equals("RATE")){
					total[idx] = rset.getDouble("REFUNDRATEBYCNT")*100;
					valid[idx] = rset.getDouble("REFUNDRATEBYAMT")*100;
					refund[idx] = rset.getDouble("CBRATEBYCNT")*100;
					cb[idx] = rset.getDouble("CBRATEBYAMT")*100;
				}
				idx++;
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		totBean.setData(total);
		validBean.setData(valid);
		refundBean.setData(refund);
		cbBean.setData(cb);
		
		list.add(totBean);
		list.add(validBean);
		list.add(refundBean);
		list.add(cbBean);
		
		return list;
	}
	
	public List<BarChartBean> getByTrnsctnMonthlyByMerchant(String type,String where){
		
		
		String query = "SELECT * FROM TOTAL_BYMERCHANTPERIOD WHERE 1=1 "+where+" ORDER BY MON DESC";
		super.pageSize = 12;
		query = super.toPaging(query);
		
		categoris = new ArrayList<String>();
		List<BarChartBean> list = new ArrayList<BarChartBean>();
		
		
		BarChartBean totBean = new BarChartBean();
		if(type.equals("RATE")){
			totBean.setName("REFUND RATE BY COUNT");
		}else{
			totBean.setName("TOTAL");
		}
		double[] total = new double[12];
		
		BarChartBean validBean = new BarChartBean();
		if(type.equals("RATE")){
			validBean.setName("REFUND RATE BY AMOUNT");
		}else{
			validBean.setName("VALID");
		}
		
		double[] valid = new double[12];
		
		BarChartBean refundBean = new BarChartBean();
		if(type.equals("RATE")){
			refundBean.setName("C*B RATE BY COUNT");
		}else{
			refundBean.setName("REFUND");
		}
		double[] refund = new double[12];
		
		BarChartBean cbBean = new BarChartBean();
		if(type.equals("RATE")){
			cbBean.setName("C*B RATE BY AMOUNT");
		}else{
			cbBean.setName("C*B");
		}
		double[] cb = new double[12];
		
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			rset 	= pstmt.executeQuery();
			int idx = 0;
			while(rset.next()){
				categoris.add(rset.getString("MON"));
				
				if(type.equals("AMOUNT")){
					total[idx] = rset.getDouble("TOT_AMT");
					valid[idx] = rset.getDouble("SUC_AMT");
					refund[idx] = rset.getDouble("REFUND_AMT");
					cb[idx] = rset.getDouble("CB_AMT");
				}else if(type.equals("COUNT")){
					total[idx] = rset.getDouble("TOT_CNT");
					valid[idx] = rset.getDouble("SUC_CNT");
					refund[idx] = rset.getDouble("REFUND_CNT");
					cb[idx] = rset.getDouble("CB_CNT");
				}else if(type.equals("RATE")){
					total[idx] = rset.getDouble("REFUNDRATEBYCNT")*100;
					valid[idx] = rset.getDouble("REFUNDRATEBYAMT")*100;
					refund[idx] = rset.getDouble("CBRATEBYCNT")*100;
					cb[idx] = rset.getDouble("CBRATEBYAMT")*100;
				}
				idx++;
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		totBean.setData(total);
		validBean.setData(valid);
		refundBean.setData(refund);
		cbBean.setData(cb);
		
		list.add(totBean);
		list.add(validBean);
		list.add(refundBean);
		list.add(cbBean);
		
		return list;
	}
	
	
	public List<BarChartBean> getByTrnsctnMonthlyByGroup(String type,String where){
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ");
		sb.append(" MON,    TOT_CNT,    TOT_AMT,    TOT_CNT-CB_CNT-REFUND_CNT AS SUC_CNT,    TOT_AMT-CB_AMT-REFUND_AMT AS SUC_AMT,");
		sb.append(" REFUND_CNT,    REFUND_AMT,   CB_CNT,    CB_AMT,    ROUND(REFUND_CNT/TOT_CNT,3) REFUNDRATEBYCNT ,    ROUND(REFUND_AMT/TOT_AMT,3) REFUNDRATEBYAMT,");
		sb.append("  ROUND(CB_CNT/TOT_CNT,3) CBRATEBYCNT ,ROUND(CB_AMT/TOT_AMT,3) CBRATEBYAMT ");
		
		sb.append(" FROM ");
		sb.append(" ( ");
		sb.append("SELECT  ");
		sb.append("TO_CHAR(TRN_REQ_DATE,'YYYYMM') MON , ");
		sb.append("COUNT(1) TOT_CNT, ");
		sb.append("SUM(AMOUNT) TOT_AMT, ");
		sb.append("NVL(SUM(DECODE(TRN_STATUS,'18',1,'14',1)),0) REFUND_CNT, ");
		sb.append("NVL(SUM(DECODE(TRN_STATUS,'18',amount,'14',amount)),0) REFUND_AMT, ");
		sb.append("NVL(SUM(DECODE(TRN_STATUS,'22',1,'23',1)),0) CB_CNT, ");
		sb.append("NVL(SUM(DECODE(TRN_STATUS,'22',amount,'23',amount)),0) CB_AMT ");
		sb.append("FROM VW_TRNSCTN ");
		sb.append("WHERE TRN_STATUS    IN ('02','07','08','09','11','12','13','10','22','23','18') ");
		sb.append("AND MERCHANT_ID IN ( SELECT MERCHANT_ID FROM TB_GROUP_MERCHANT WHERE GROUP_ID ='"+where+"') ");
		sb.append("GROUP BY  TO_CHAR(TRN_REQ_DATE,'YYYYMM')  ");
		sb.append("ORDER BY  TO_CHAR(TRN_REQ_DATE,'YYYYMM') DESC ");
		sb.append(")");
		
		String query = sb.toString();
		super.pageSize = 6;
		query = super.toPaging(query);
		
		categoris = new ArrayList<String>();
		List<BarChartBean> list = new ArrayList<BarChartBean>();
		
		
		BarChartBean totBean = new BarChartBean();
		if(type.equals("RATE")){
			totBean.setName("REFUND RATE BY COUNT");
		}else{
			totBean.setName("TOTAL");
		}
		double[] total = new double[6];
		
		BarChartBean validBean = new BarChartBean();
		if(type.equals("RATE")){
			validBean.setName("REFUND RATE BY AMOUNT");
		}else{
			validBean.setName("VALID");
		}
		
		double[] valid = new double[6];
		
		BarChartBean refundBean = new BarChartBean();
		if(type.equals("RATE")){
			refundBean.setName("C*B RATE BY COUNT");
		}else{
			refundBean.setName("REFUND");
		}
		double[] refund = new double[6];
		
		BarChartBean cbBean = new BarChartBean();
		if(type.equals("RATE")){
			cbBean.setName("C*B RATE BY AMOUNT");
		}else{
			cbBean.setName("C*B");
		}
		double[] cb = new double[6];
		
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			rset 	= pstmt.executeQuery();
			int idx = 0;
			while(rset.next()){
				categoris.add(rset.getString("MON"));
				
				if(type.equals("AMOUNT")){
					total[idx] = rset.getDouble("TOT_AMT");
					valid[idx] = rset.getDouble("SUC_AMT");
					refund[idx] = rset.getDouble("REFUND_AMT");
					cb[idx] = rset.getDouble("CB_AMT");
				}else if(type.equals("COUNT")){
					total[idx] = rset.getDouble("TOT_CNT");
					valid[idx] = rset.getDouble("SUC_CNT");
					refund[idx] = rset.getDouble("REFUND_CNT");
					cb[idx] = rset.getDouble("CB_CNT");
				}else if(type.equals("RATE")){
					total[idx] = rset.getDouble("REFUNDRATEBYCNT")*100;
					valid[idx] = rset.getDouble("REFUNDRATEBYAMT")*100;
					refund[idx] = rset.getDouble("CBRATEBYCNT")*100;
					cb[idx] = rset.getDouble("CBRATEBYAMT")*100;
				}
				idx++;
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		totBean.setData(total);
		validBean.setData(valid);
		refundBean.setData(refund);
		cbBean.setData(cb);
		
		list.add(totBean);
		list.add(validBean);
		list.add(refundBean);
		list.add(cbBean);
		
		return list;
	}
	
	public List<BarChartBean> getByTrnsctnMonthlyByAgent(String type,String where){
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ");
		sb.append(" MON,    TOT_CNT,    TOT_AMT,    TOT_CNT-CB_CNT-REFUND_CNT AS SUC_CNT,    TOT_AMT-CB_AMT-REFUND_AMT AS SUC_AMT,");
		sb.append(" REFUND_CNT,    REFUND_AMT,   CB_CNT,    CB_AMT,    ROUND(REFUND_CNT/TOT_CNT,3) REFUNDRATEBYCNT ,    ROUND(REFUND_AMT/TOT_AMT,3) REFUNDRATEBYAMT,");
		sb.append("  ROUND(CB_CNT/TOT_CNT,3) CBRATEBYCNT ,ROUND(CB_AMT/TOT_AMT,3) CBRATEBYAMT ");
		
		sb.append(" FROM ");
		sb.append(" ( ");
		sb.append("SELECT  ");
		sb.append("TO_CHAR(TRN_REQ_DATE,'YYYYMM') MON , ");
		sb.append("COUNT(1) TOT_CNT, ");
		sb.append("SUM(AMOUNT) TOT_AMT, ");
		sb.append("NVL(SUM(DECODE(TRN_STATUS,'18',1,'14',1)),0) REFUND_CNT, ");
		sb.append("NVL(SUM(DECODE(TRN_STATUS,'18',amount,'14',amount)),0) REFUND_AMT, ");
		sb.append("NVL(SUM(DECODE(TRN_STATUS,'22',1,'23',1)),0) CB_CNT, ");
		sb.append("NVL(SUM(DECODE(TRN_STATUS,'22',amount,'23',amount)),0) CB_AMT ");
		sb.append("FROM VW_TRNSCTN ");
		sb.append("WHERE TRN_STATUS    IN ('02','07','08','09','11','12','13','10','22','23','18') ");
		sb.append("AND MERCHANT_ID IN ( SELECT MERCHANT_ID FROM TB_AGENT_MERCHANT WHERE AGENT_ID ='"+where+"') ");
		sb.append("GROUP BY  TO_CHAR(TRN_REQ_DATE,'YYYYMM')  ");
		sb.append("ORDER BY  TO_CHAR(TRN_REQ_DATE,'YYYYMM') DESC ");
		sb.append(")");
		
		String query = sb.toString();
		super.pageSize = 6;
		query = super.toPaging(query);
		
		categoris = new ArrayList<String>();
		List<BarChartBean> list = new ArrayList<BarChartBean>();
		
		
		BarChartBean totBean = new BarChartBean();
		if(type.equals("RATE")){
			totBean.setName("REFUND RATE BY COUNT");
		}else{
			totBean.setName("TOTAL");
		}
		double[] total = new double[6];
		
		BarChartBean validBean = new BarChartBean();
		if(type.equals("RATE")){
			validBean.setName("REFUND RATE BY AMOUNT");
		}else{
			validBean.setName("VALID");
		}
		
		double[] valid = new double[6];
		
		BarChartBean refundBean = new BarChartBean();
		if(type.equals("RATE")){
			refundBean.setName("C*B RATE BY COUNT");
		}else{
			refundBean.setName("REFUND");
		}
		double[] refund = new double[6];
		
		BarChartBean cbBean = new BarChartBean();
		if(type.equals("RATE")){
			cbBean.setName("C*B RATE BY AMOUNT");
		}else{
			cbBean.setName("C*B");
		}
		double[] cb = new double[6];
		
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			rset 	= pstmt.executeQuery();
			int idx = 0;
			while(rset.next()){
				categoris.add(rset.getString("MON"));
				
				if(type.equals("AMOUNT")){
					total[idx] = rset.getDouble("TOT_AMT");
					valid[idx] = rset.getDouble("SUC_AMT");
					refund[idx] = rset.getDouble("REFUND_AMT");
					cb[idx] = rset.getDouble("CB_AMT");
				}else if(type.equals("COUNT")){
					total[idx] = rset.getDouble("TOT_CNT");
					valid[idx] = rset.getDouble("SUC_CNT");
					refund[idx] = rset.getDouble("REFUND_CNT");
					cb[idx] = rset.getDouble("CB_CNT");
				}else if(type.equals("RATE")){
					total[idx] = rset.getDouble("REFUNDRATEBYCNT")*100;
					valid[idx] = rset.getDouble("REFUNDRATEBYAMT")*100;
					refund[idx] = rset.getDouble("CBRATEBYCNT")*100;
					cb[idx] = rset.getDouble("CBRATEBYAMT")*100;
				}
				idx++;
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		totBean.setData(total);
		validBean.setData(valid);
		refundBean.setData(refund);
		cbBean.setData(cb);
		
		list.add(totBean);
		list.add(validBean);
		list.add(refundBean);
		list.add(cbBean);
		
		return list;
	}
	
	public List<GSIBean> getByMerchant(String id){
		String query = "";
		
		if(!id.equals("")){
			query = "SELECT MERCHANT_ID, NAME FROM TOTAL_BYMERCHANTPERIOD WHERE MON >= '201301' AND MERCHANT_ID IN (SELECT MERCHANT_ID FROM TB_GROUP_MERCHANT WHERE GROUP_ID='"+id+"') GROUP BY MERCHANT_ID, NAME ORDER BY NAME";
		}else{
			query = "SELECT MERCHANT_ID, NAME FROM TOTAL_BYMERCHANTPERIOD WHERE MON >= '201301' GROUP BY MERCHANT_ID, NAME ORDER BY NAME";
		}
		
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
			int idx = 0;
			while(rset.next()){
				GSIBean bean = new GSIBean();
				bean.setTemp1String(rset.getString("MERCHANT_ID"));
				bean.setTemp2String(rset.getString("NAME"));
				list.add(bean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		return list;
	}
	
	public List<BarChartBean> getByMonthlyByMerchantId(String merchantId,String type){
		
		
		String query = "SELECT * FROM TOTAL_BYMERCHANTPERIOD WHERE MERCHANT_ID ='"+merchantId+"' ORDER BY MON DESC";
		super.pageSize = 12;
		query = super.toPaging(query);
		
		categoris = new ArrayList<String>();
		List<BarChartBean> list = new ArrayList<BarChartBean>();
		
		
		BarChartBean totBean = new BarChartBean();
		if(type.equals("RATE")){
			totBean.setName("REFUND RATE BY COUNT");
		}else{
			totBean.setName("TOTAL");
		}
		double[] total = new double[12];
		
		BarChartBean validBean = new BarChartBean();
		if(type.equals("RATE")){
			validBean.setName("REFUND RATE BY AMOUNT");
		}else{
			validBean.setName("VALID");
		}
		
		double[] valid = new double[12];
		
		BarChartBean refundBean = new BarChartBean();
		if(type.equals("RATE")){
			refundBean.setName("C*B RATE BY COUNT");
		}else{
			refundBean.setName("REFUND");
		}
		double[] refund = new double[12];
		
		BarChartBean cbBean = new BarChartBean();
		if(type.equals("RATE")){
			cbBean.setName("C*B RATE BY AMOUNT");
		}else{
			cbBean.setName("C*B");
		}
		double[] cb = new double[12];
		
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			rset 	= pstmt.executeQuery();
			int idx = 0;
			while(rset.next()){
				categoris.add(rset.getString("MON"));
				
				if(type.equals("AMOUNT")){
					total[idx] = rset.getDouble("TOT_AMT");
					valid[idx] = rset.getDouble("SUC_AMT");
					refund[idx] = rset.getDouble("REFUND_AMT");
					cb[idx] = rset.getDouble("CB_AMT");
				}else if(type.equals("COUNT")){
					total[idx] = rset.getDouble("TOT_CNT");
					valid[idx] = rset.getDouble("SUC_CNT");
					refund[idx] = rset.getDouble("REFUND_CNT");
					cb[idx] = rset.getDouble("CB_CNT");
				}else if(type.equals("RATE")){
					total[idx] = rset.getDouble("REFUNDRATEBYCNT")*100;
					valid[idx] = rset.getDouble("REFUNDRATEBYAMT")*100;
					refund[idx] = rset.getDouble("CBRATEBYCNT")*100;
					cb[idx] = rset.getDouble("CBRATEBYAMT")*100;
				}
				idx++;
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		totBean.setData(total);
		validBean.setData(valid);
		refundBean.setData(refund);
		cbBean.setData(cb);
		
		list.add(totBean);
		list.add(validBean);
		list.add(refundBean);
		list.add(cbBean);
		
		return list;
	}
	
	public List<BarChartBean> getByTrnsctnMonthlyToday(String type){
		
		
		String query = "SELECT * FROM TOTAL_TRNSCTN_MONTHTODAY ORDER BY MON DESC";
		super.pageSize = 6;
		query = super.toPaging(query);
		
		categoris = new ArrayList<String>();
		List<BarChartBean> list = new ArrayList<BarChartBean>();
		
		
		BarChartBean totBean = new BarChartBean();
		totBean.setName("TOTAL");
		
		double[] total = new double[6];
		
		BarChartBean validBean = new BarChartBean();
		validBean.setName("VALID");

		double[] valid = new double[6];
		
		BarChartBean etcBean = new BarChartBean();
		etcBean.setName("INVALID");
		
		double[] etc = new double[6];
		

		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			rset 	= pstmt.executeQuery();
			int idx = 0;
			while(rset.next()){
				categoris.add(rset.getString("MON"));
				
				if(type.equals("AMOUNT")){
					total[idx] = rset.getDouble("TOT_AMT");
					valid[idx] = rset.getDouble("SUC_AMT");
					etc[idx] = rset.getDouble("ETC_AMT");
				}else if(type.equals("COUNT")){
					total[idx] = rset.getDouble("TOT_CNT");
					valid[idx] = rset.getDouble("SUC_CNT");
					etc[idx] = rset.getDouble("ETC_CNT");
				}
				idx++;
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		totBean.setData(total);
		validBean.setData(valid);
		etcBean.setData(etc);
		
		list.add(totBean);
		list.add(validBean);
		list.add(etcBean);
		
		
		return list;
	}
	
	
	
	public List<Object[]> getTodayChart(){
		String query = "SELECT FC_CODE('TRN_STATUS',TRN_STATUS,'en') AS STATUS ,SUM(AMOUNT) AS AMT FROM TB_TRNSCTN WHERE TRN_DATE =TO_CHAR(SYSDATE,'YYYYMMDD') GROUP BY TRN_STATUS";
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		ResultSet rset			= null;
		List<Object[]> list = new ArrayList<Object[]>();
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			rset 	= pstmt.executeQuery();
			
			while(rset.next()){
				Object[] obj = new Object[2];
				obj[0] = rset.getString("STATUS");
				obj[1] = rset.getDouble("AMT");
				list.add(obj);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		return list;
	}
	
	
	public List<CalendarBean> getBySettleCalendar(String month){
		String query = "SELECT TO_CHAR(SETTLE_DATE,'YYYYMMDD') AS SETTLE_DAY, START_DAY,END_DAY,SUM(TOT_AMT) TOT_AMT,SUM(TOT_FEE) TOT_FEE,SUM(TOT_DEPOSIT) TOT_DEPOSIT,SUM(TOT_SETTLE) TOT_SETTLE FROM TB_MERCHANT_SETTLE "
					+" WHERE TO_CHAR(SETTLE_DATE,'YYYY-MM') = '"+month+"'"
					+" GROUP BY TO_CHAR(SETTLE_DATE,'YYYYMMDD'),START_DAY,END_DAY ORDER BY SETTLE_DAY";
		
		List<CalendarBean> list = new ArrayList<CalendarBean>();
		
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
				CalendarBean bean1 = new CalendarBean();
				
				
				String date = rset.getString("SETTLE_DAY");
				bean1.setStart(CommonUtil.stringToTimestamp(date));
				bean1.setEnd(CommonUtil.stringToTimestamp(date));
				bean1.setBackgroundColor("#4b8df8");
				bean1.setTitle("AMT : "+CommonUtil.makeMoneyType(rset.getDouble("TOT_AMT"), ","));
				list.add(bean1);
				
				CalendarBean bean2 = new CalendarBean();
				bean2.setStart(CommonUtil.stringToTimestamp(date));
				bean2.setEnd(CommonUtil.stringToTimestamp(date));
				bean2.setBackgroundColor("#e02222");
				bean2.setTitle("FEE : "+CommonUtil.makeMoneyType(rset.getDouble("TOT_FEE"), ","));
				list.add(bean2);
				
				CalendarBean bean3 = new CalendarBean();
				bean3.setStart(CommonUtil.stringToTimestamp(date));
				bean3.setEnd(CommonUtil.stringToTimestamp(date));
				bean3.setBackgroundColor("#ffb848");
				bean3.setTitle("DEPOSIT : "+CommonUtil.makeMoneyType(rset.getDouble("TOT_DEPOSIT"), ","));
				list.add(bean3);
				
				CalendarBean bean4 = new CalendarBean();
				bean4.setStart(CommonUtil.stringToTimestamp(date));
				bean4.setEnd(CommonUtil.stringToTimestamp(date));
				bean4.setBackgroundColor("#35aa47");
				bean4.setTitle("SETTLE : "+CommonUtil.makeMoneyType(rset.getDouble("TOT_SETTLE"), ","));
				list.add(bean4);

				
				
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

package com.pgmate.model.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import biz.trustnet.common.db.DBUtil;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.BeanUtil;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.GSIBean;
import com.pgmate.model.db.MerchantSettleBean;
import com.pgmate.model.db.TrnsctnBean;
import com.pgmate.model.db.factory.DBFactory;
import com.pgmate.payment.util.GSICrypt;

/**
 * @author Administrator
 *
 */
public class MerchantSettleDAO extends DAO {

	private GSIBean gsiBean = new GSIBean();
	
	public MerchantSettleDAO(){
	}
	
	
	
	public MerchantSettleBean getBySettleId(String settleId){
		List<MerchantSettleBean> list = get(" AND SETTLE_ID='"+settleId+"' ");
		if(list.size() == 0){
			return new MerchantSettleBean();
		}else{
			return (MerchantSettleBean)list.get(0);
		}
	}
	
	public GSIBean getSumAmount(){
		return gsiBean;
	}
	
	
	
	public List<MerchantSettleBean> get(MerchantSettleBean merchantSettleBean){
		StringBuffer sb = new StringBuffer();
		if(!merchantSettleBean.getSettleId().equals("")){
			sb.append(" AND SETTLE_ID = '"+merchantSettleBean.getSettleId()+"' ");
		}
		if(!merchantSettleBean.getMerchantId().equals("")){
			sb.append(" AND MERCHANT_ID = '"+merchantSettleBean.getMerchantId()+"' ");
		}

		if(!merchantSettleBean.getPeriod().equals("")){
			sb.append(" AND PERIOD = '"+merchantSettleBean.getPeriod()+"' ");
		}
		if(!merchantSettleBean.getStatus().equals("")){
			if(merchantSettleBean.getStatus().indexOf("'") > -1){
				sb.append(" AND STATUS IN ("+merchantSettleBean.getStatus()+") ");
			}else{
				sb.append(" AND STATUS = '"+merchantSettleBean.getStatus()+"' ");
			}
		}
		if(merchantSettleBean.getPayDate() != null){
			sb.append(" AND TO_CHAR(PAY_DATE,'yyyyMMdd') = '"+CommonUtil.convertTimestampToString(merchantSettleBean.getPayDate(), "yyyyMMdd")+"' ");
		}
		if(merchantSettleBean.getSettleDate() != null){
			sb.append(" AND TO_CHAR(SETTLE_DATE,'yyyyMMdd') = '"+CommonUtil.convertTimestampToString(merchantSettleBean.getSettleDate(), "yyyyMMdd")+"' ");
		}
		if(!merchantSettleBean.getTemp2String().equals("")){
			sb.append(" AND STATUS != '"+merchantSettleBean.getTemp2String()+"' ");
		}
		if(!merchantSettleBean.getTemp3String().equals("")){
			sb.append(" AND SUBSTR(MERCHANT_ID,0,4) = '"+merchantSettleBean.getTemp3String().substring(0,4)+"' ");
		}
	
		
		return get(sb.toString());
	}
	
	public List<MerchantSettleBean> get(String subQuery){
		setAmount(subQuery);
		String query = "SELECT SETTLE_ID,MERCHANT_ID,START_DAY,END_DAY,PERIOD,SETTLE_CNT,STATUS,PAY_DATE, "
			+ "SETTLE_DATE,BANK_INFO,ACCOUNT,TOT_AMT,TOT_FEE,TOT_DEPOSIT,TOT_SETTLE,TRN_BILL_ID, "
			+ "TRN_VM_CNT,TRN_VM_AMT,TRN_VM_FEE,TRN_VM_RATE,TRN_JA_CNT,TRN_JA_AMT,TRN_JA_FEE,TRN_JA_RATE, "
			+ "PRE_TRN_BILL_ID,PRE_TRN_VM_CNT,PRE_TRN_VM_AMT,PRE_TRN_VM_FEE,PRE_TRN_VM_RATE,PRE_TRN_JA_CNT,PRE_TRN_JA_AMT,PRE_TRN_JA_FEE, "
			+ "PRE_TRN_JA_RATE,CB_BILL_ID,CB_CNT,CB_AMT,CB_FEE,CB_RATE,REFUND_BILL_ID,REFUND_VM_CNT, "
			+ "REFUND_VM_AMT,REFUND_VM_FEE,REFUND_VM_RATE,REFUND_JA_CNT,REFUND_JA_AMT,REFUND_JA_FEE,REFUND_JA_RATE,PRE_REFUND_BILL_ID, "
			+ "PRE_REFUND_VM_CNT,PRE_REFUND_VM_AMT,PRE_REFUND_VM_FEE,PRE_REFUND_VM_RATE,PRE_REFUND_JA_CNT,PRE_REFUND_JA_AMT,PRE_REFUND_JA_FEE,PRE_REFUND_JA_RATE, "
			+ "MANAGE_FEE,VAN_CNT,VAN_FEE,VAN_RATE,TRANSFE_FEE,VAT_AMT,DEPOST_IDX,ETC_NAME,ETC_AMT,REG_DATE " 
			+ "FROM TB_MERCHANT_SETTLE WHERE SETTLE_ID IS NOT NULL " + subQuery;
		
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
		List<MerchantSettleBean> list = new ArrayList<MerchantSettleBean>();
		
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
				MerchantSettleBean merchantSettleBean = new MerchantSettleBean();
				merchantSettleBean.setSettleId(rset.getString("SETTLE_ID"));
				merchantSettleBean.setMerchantId(rset.getString("MERCHANT_ID"));
				merchantSettleBean.setStartDay(rset.getString("START_DAY"));
				merchantSettleBean.setEndDay(rset.getString("END_DAY"));
				merchantSettleBean.setPeriod(rset.getString("PERIOD"));
				merchantSettleBean.setSettleCnt(rset.getString("SETTLE_CNT"));
				merchantSettleBean.setStatus(rset.getString("STATUS"));
				merchantSettleBean.setPayDate(rset.getTimestamp("PAY_DATE"));
				merchantSettleBean.setSettleDate(rset.getTimestamp("SETTLE_DATE"));
				merchantSettleBean.setBankInfo(rset.getString("BANK_INFO"));
				merchantSettleBean.setAccount(rset.getString("ACCOUNT"));
				merchantSettleBean.setTotAmt(rset.getDouble("TOT_AMT"));
				merchantSettleBean.setTotFee(rset.getDouble("TOT_FEE"));
				merchantSettleBean.setTotDeposit(rset.getDouble("TOT_DEPOSIT"));
				merchantSettleBean.setTotSettle(rset.getDouble("TOT_SETTLE"));
				merchantSettleBean.setTrnBillId(rset.getString("TRN_BILL_ID"));
				merchantSettleBean.setTrnVmCnt(rset.getDouble("TRN_VM_CNT"));
				merchantSettleBean.setTrnVmAmt(rset.getDouble("TRN_VM_AMT"));
				merchantSettleBean.setTrnVmFee(rset.getDouble("TRN_VM_FEE"));
				merchantSettleBean.setTrnVmRate(rset.getDouble("TRN_VM_RATE"));
				merchantSettleBean.setTrnJaCnt(rset.getDouble("TRN_JA_CNT"));
				merchantSettleBean.setTrnJaAmt(rset.getDouble("TRN_JA_AMT"));
				merchantSettleBean.setTrnJaFee(rset.getDouble("TRN_JA_FEE"));
				merchantSettleBean.setTrnJaRate(rset.getDouble("TRN_JA_RATE"));
				merchantSettleBean.setPreTrnBillId(rset.getString("PRE_TRN_BILL_ID"));
				merchantSettleBean.setPreTrnVmCnt(rset.getDouble("PRE_TRN_VM_CNT"));
				merchantSettleBean.setPreTrnVmAmt(rset.getDouble("PRE_TRN_VM_AMT"));
				merchantSettleBean.setPreTrnVmFee(rset.getDouble("PRE_TRN_VM_FEE"));
				merchantSettleBean.setPreTrnVmRate(rset.getDouble("PRE_TRN_VM_RATE"));
				merchantSettleBean.setPreTrnJaCnt(rset.getDouble("PRE_TRN_JA_CNT"));
				merchantSettleBean.setPreTrnJaAmt(rset.getDouble("PRE_TRN_JA_AMT"));
				merchantSettleBean.setPreTrnJaFee(rset.getDouble("PRE_TRN_JA_FEE"));
				merchantSettleBean.setPreTrnJaRate(rset.getDouble("PRE_TRN_JA_RATE"));
				merchantSettleBean.setCbBillId(rset.getString("CB_BILL_ID"));
				merchantSettleBean.setCbCnt(rset.getDouble("CB_CNT"));
				merchantSettleBean.setCbAmt(rset.getDouble("CB_AMT"));
				merchantSettleBean.setCbFee(rset.getDouble("CB_FEE"));
				merchantSettleBean.setCbRate(rset.getDouble("CB_RATE"));
				merchantSettleBean.setRefundBillId(rset.getString("REFUND_BILL_ID"));
				merchantSettleBean.setRefundVmCnt(rset.getDouble("REFUND_VM_CNT"));
				merchantSettleBean.setRefundVmAmt(rset.getDouble("REFUND_VM_AMT"));
				merchantSettleBean.setRefundVmFee(rset.getDouble("REFUND_VM_FEE"));
				merchantSettleBean.setRefundVmRate(rset.getDouble("REFUND_VM_RATE"));
				merchantSettleBean.setRefundJaCnt(rset.getDouble("REFUND_JA_CNT"));
				merchantSettleBean.setRefundJaAmt(rset.getDouble("REFUND_JA_AMT"));
				merchantSettleBean.setRefundJaFee(rset.getDouble("REFUND_JA_FEE"));
				merchantSettleBean.setRefundJaRate(rset.getDouble("REFUND_JA_RATE"));
				merchantSettleBean.setPreRefundBillId(rset.getString("PRE_REFUND_BILL_ID"));
				merchantSettleBean.setPreRefundVmCnt(rset.getDouble("PRE_REFUND_VM_CNT"));
				merchantSettleBean.setPreRefundVmAmt(rset.getDouble("PRE_REFUND_VM_AMT"));
				merchantSettleBean.setPreRefundVmFee(rset.getDouble("PRE_REFUND_VM_FEE"));
				merchantSettleBean.setPreRefundVmRate(rset.getDouble("PRE_REFUND_VM_RATE"));
				merchantSettleBean.setPreRefundJaCnt(rset.getDouble("PRE_REFUND_JA_CNT"));
				merchantSettleBean.setPreRefundJaAmt(rset.getDouble("PRE_REFUND_JA_AMT"));
				merchantSettleBean.setPreRefundJaFee(rset.getDouble("PRE_REFUND_JA_FEE"));
				merchantSettleBean.setPreRefundJaRate(rset.getDouble("PRE_REFUND_JA_RATE"));
				merchantSettleBean.setManageFee(rset.getDouble("MANAGE_FEE"));
				merchantSettleBean.setVanCnt(rset.getDouble("VAN_CNT"));
				merchantSettleBean.setVanFee(rset.getDouble("VAN_FEE"));
				merchantSettleBean.setVanRate(rset.getDouble("VAN_RATE"));
				merchantSettleBean.setTransfeFee(rset.getDouble("TRANSFE_FEE"));
				merchantSettleBean.setVatAmt(rset.getDouble("VAT_AMT"));
				merchantSettleBean.setDepositIdx(rset.getLong("DEPOST_IDX"));
				merchantSettleBean.setEtcName(rset.getString("ETC_NAME"));
				merchantSettleBean.setEtcAmt(rset.getDouble("ETC_AMT"));
				merchantSettleBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(merchantSettleBean);
			}
		}catch(SQLException e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn, pstmt, rset);
		}
		return list;
	}
	
	public List<TrnsctnBean> getTrnsctn(String idx){
		GSICrypt crypt = new GSICrypt();
		String query = "SELECT a.IDX, a.TRANSACTION_ID, a.MERCHANT_ID, a.MALL_ID, a.TRN_REQ_DATE, a.TRN_RES_DATE, a.TRN_STATUS,  a.CUR_TYPE, a.AMOUNT, a.PAY_NO, "
					 + " a.PAY_EMAIL, a.PAY_NAME, a.PAY_TELNO, a.PRODUCT_NAME, b.CARD_NUM, b.CARD_TYPE, a.APPROVAL_NO,a.RESULT_MSG "
			         + " FROM VW_TRNSCTN a , VW_TRNSCTN_SCR b "
			         + " WHERE  a.IDX IS NOT NULL AND a.TRANSACTION_ID = b.TRANSACTION_ID "
		         	 + " AND a.TRANSACTION_ID IN (SELECT TRN_ID FROM TB_BILL_TRN_ID WHERE BILL_ID = '"+idx+"' ) ";
		
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
				trnsctnBean.setPayTelNo(rset.getString("PAY_TELNO"));
				trnsctnBean.setPayEmail(rset.getString("PAY_EMAIL"));
				trnsctnBean.setPayName(rset.getString("PAY_NAME"));
				trnsctnBean.setProductName(rset.getString("PRODUCT_NAME"));
				trnsctnBean.setAmount(rset.getDouble("AMOUNT"));
				trnsctnBean.setTemp1String(crypt.hideCardNumberDecrypt(rset.getString("CARD_NUM")));
				trnsctnBean.setTemp2String(rset.getString("CARD_TYPE"));
				trnsctnBean.setApprovalNo(rset.getString("APPROVAL_NO"));
				trnsctnBean.setResultMsg(rset.getString("RESULT_MSG"));
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
	
	public void setAmount(String subQuery){
		
		String query = "SELECT SUM(TOT_AMT) AS AMT ,SUM(TOT_FEE) AS FEE,SUM(TOT_SETTLE) AS SETTLE " 
			+ "FROM TB_MERCHANT_SETTLE WHERE SETTLE_ID IS NOT NULL " + subQuery;
		
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
	
	public boolean insert(MerchantSettleBean merchantSettleBean){
		String query = "INSERT INTO TB_MERCHANT_SETTLE (SETTLE_ID,MERCHANT_ID,START_DAY,END_DAY,PERIOD,SETTLE_CNT,STATUS,PAY_DATE, "
			+ "SETTLE_DATE,BANK_INFO,ACCOUNT,TOT_AMT,TOT_FEE,TOT_DEPOSIT,TOT_SETTLE,TRN_BILL_ID, "
			+ "TRN_VM_CNT,TRN_VM_AMT,TRN_VM_FEE,TRN_VM_RATE,TRN_JA_CNT,TRN_JA_AMT,TRN_JA_FEE,TRN_JA_RATE, "
			+ "PRE_TRN_BILL_ID,PRE_TRN_VM_CNT,PRE_TRN_VM_AMT,PRE_TRN_VM_FEE,PRE_TRN_VM_RATE,PRE_TRN_JA_CNT,PRE_TRN_JA_AMT,PRE_TRN_JA_FEE, "
			+ "PRE_TRN_JA_RATE,CB_BILL_ID,CB_CNT,CB_AMT,CB_FEE,CB_RATE,REFUND_BILL_ID,REFUND_VM_CNT, "
			+ "REFUND_VM_AMT,REFUND_VM_FEE,REFUND_VM_RATE,REFUND_JA_CNT,REFUND_JA_AMT,REFUND_JA_FEE,REFUND_JA_RATE,PRE_REFUND_BILL_ID, "
			+ "PRE_REFUND_VM_CNT,PRE_REFUND_VM_AMT,PRE_REFUND_VM_FEE,PRE_REFUND_VM_RATE,PRE_REFUND_JA_CNT,PRE_REFUND_JA_AMT,PRE_REFUND_JA_FEE,PRE_REFUND_JA_RATE, "
			+ "MANAGE_FEE,VAN_CNT,VAN_FEE,VAN_RATE,TRANSFE_FEE,VAT_AMT,DEPOST_IDX,ETC_NAME,ETC_AMT,"
			+ "REG_DATE )"
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE)";
		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setString(idx++, merchantSettleBean.getSettleId());
			pstmt.setString(idx++, merchantSettleBean.getMerchantId());
			pstmt.setString(idx++, merchantSettleBean.getStartDay());
			pstmt.setString(idx++, merchantSettleBean.getEndDay());
			pstmt.setString(idx++, merchantSettleBean.getPeriod());
			pstmt.setString(idx++, merchantSettleBean.getSettleCnt());
			pstmt.setString(idx++, merchantSettleBean.getStatus());
			pstmt.setTimestamp(idx++, merchantSettleBean.getPayDate());
			pstmt.setTimestamp(idx++, merchantSettleBean.getSettleDate());
			pstmt.setString(idx++, merchantSettleBean.getBankInfo());
			pstmt.setString(idx++, merchantSettleBean.getAccount());
			pstmt.setDouble(idx++, merchantSettleBean.getTotAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getTotFee());
			pstmt.setDouble(idx++, merchantSettleBean.getTotDeposit());
			pstmt.setDouble(idx++, merchantSettleBean.getTotSettle());
			pstmt.setString(idx++, merchantSettleBean.getTrnBillId());
			pstmt.setDouble(idx++, merchantSettleBean.getTrnVmCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getTrnVmAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getTrnVmFee());
			pstmt.setDouble(idx++, merchantSettleBean.getTrnVmRate());
			pstmt.setDouble(idx++, merchantSettleBean.getTrnJaCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getTrnJaAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getTrnJaFee());
			pstmt.setDouble(idx++, merchantSettleBean.getTrnJaRate());
			pstmt.setString(idx++, merchantSettleBean.getPreTrnBillId());
			pstmt.setDouble(idx++, merchantSettleBean.getPreTrnVmCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getPreTrnVmAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getPreTrnVmFee());
			pstmt.setDouble(idx++, merchantSettleBean.getPreTrnVmRate());
			pstmt.setDouble(idx++, merchantSettleBean.getPreTrnJaCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getPreTrnJaAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getPreTrnJaFee());
			pstmt.setDouble(idx++, merchantSettleBean.getPreTrnJaRate());
			pstmt.setString(idx++, merchantSettleBean.getCbBillId());
			pstmt.setDouble(idx++, merchantSettleBean.getCbCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getCbAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getCbFee());
			pstmt.setDouble(idx++, merchantSettleBean.getCbRate());
			pstmt.setString(idx++, merchantSettleBean.getRefundBillId());
			pstmt.setDouble(idx++, merchantSettleBean.getRefundVmCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getRefundVmAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getRefundVmFee());
			pstmt.setDouble(idx++, merchantSettleBean.getRefundVmRate());
			pstmt.setDouble(idx++, merchantSettleBean.getRefundJaCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getRefundJaAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getRefundJaFee());
			pstmt.setDouble(idx++, merchantSettleBean.getRefundJaRate());
			pstmt.setString(idx++, merchantSettleBean.getPreRefundBillId());
			pstmt.setDouble(idx++, merchantSettleBean.getPreRefundVmCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getPreRefundVmAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getPreRefundVmFee());
			pstmt.setDouble(idx++, merchantSettleBean.getPreRefundVmRate());
			pstmt.setDouble(idx++, merchantSettleBean.getPreRefundJaCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getPreRefundJaAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getPreRefundJaFee());
			pstmt.setDouble(idx++, merchantSettleBean.getPreRefundJaRate());
			pstmt.setDouble(idx++, merchantSettleBean.getManageFee());
			pstmt.setDouble(idx++, merchantSettleBean.getVanCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getVanFee());
			pstmt.setDouble(idx++, merchantSettleBean.getVanRate());
			pstmt.setDouble(idx++, merchantSettleBean.getTransfeFee());
			pstmt.setDouble(idx++, merchantSettleBean.getVatAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getDepositIdx());
			pstmt.setString(idx++, merchantSettleBean.getEtcName());
			pstmt.setDouble(idx++, merchantSettleBean.getEtcAmt());
			result = pstmt.executeUpdate();
			conn.commit();
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(merchantSettleBean),this);
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
	
	public boolean update(MerchantSettleBean merchantSettleBean){
		String query = "UPDATE TB_MERCHANT_SETTLE SET MERCHANT_ID=?,START_DAY=?,END_DAY=?,PERIOD=?,SETTLE_CNT=?,STATUS=?,PAY_DATE=?, "
			+ "SETTLE_DATE=?,BANK_INFO=?,ACCOUNT=?,TOT_AMT=?,TOT_FEE=?,TOT_DEPOSIT=?,TOT_SETTLE=?,TRN_BILL_ID=?, "
			+ "TRN_VM_CNT=?,TRN_VM_AMT=?,TRN_VM_FEE=?,TRN_VM_RATE=?,TRN_JA_CNT=?,TRN_JA_AMT=?,TRN_JA_FEE=?,TRN_JA_RATE=?, "
			+ "PRE_TRN_BILL_ID=?,PRE_TRN_VM_CNT=?,PRE_TRN_VM_AMT=?,PRE_TRN_VM_FEE=?,PRE_TRN_VM_RATE=?,PRE_TRN_JA_CNT=?,PRE_TRN_JA_AMT=?,PRE_TRN_JA_FEE=?, "
			+ "PRE_TRN_JA_RATE=?,CB_BILL_ID=?,CB_CNT=?,CB_AMT=?,CB_FEE=?,CB_RATE=?,REFUND_BILL_ID=?,REFUND_VM_CNT=?, "
			+ "REFUND_VM_AMT=?,REFUND_VM_FEE=?,REFUND_VM_RATE=?,REFUND_JA_CNT=?,REFUND_JA_AMT=?,REFUND_JA_FEE=?,REFUND_JA_RATE=?,PRE_REFUND_BILL_ID=?, "
			+ "PRE_REFUND_VM_CNT=?,PRE_REFUND_VM_AMT=?,PRE_REFUND_VM_FEE=?,PRE_REFUND_VM_RATE=?,PRE_REFUND_JA_CNT=?,PRE_REFUND_JA_AMT=?,PRE_REFUND_JA_FEE=?,PRE_REFUND_JA_RATE=?, "
			+ "MANAGE_FEE=?,VAN_CNT=?,VAN_FEE=?,VAN_RATE=?,TRANSFE_FEE=?,VAT_AMT=?,DEPOST_IDX=?,ETC_NAME=?,ETC_AMT=? "
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
			
			pstmt.setString(idx++, merchantSettleBean.getMerchantId());
			pstmt.setString(idx++, merchantSettleBean.getStartDay());
			pstmt.setString(idx++, merchantSettleBean.getEndDay());
			pstmt.setString(idx++, merchantSettleBean.getPeriod());
			pstmt.setString(idx++, merchantSettleBean.getSettleCnt());
			pstmt.setString(idx++, merchantSettleBean.getStatus());
			pstmt.setTimestamp(idx++, merchantSettleBean.getPayDate());
			pstmt.setTimestamp(idx++, merchantSettleBean.getSettleDate());
			pstmt.setString(idx++, merchantSettleBean.getBankInfo());
			pstmt.setString(idx++, merchantSettleBean.getAccount());
			pstmt.setDouble(idx++, merchantSettleBean.getTotAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getTotFee());
			pstmt.setDouble(idx++, merchantSettleBean.getTotDeposit());
			pstmt.setDouble(idx++, merchantSettleBean.getTotSettle());
			pstmt.setString(idx++, merchantSettleBean.getTrnBillId());
			pstmt.setDouble(idx++, merchantSettleBean.getTrnVmCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getTrnVmAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getTrnVmFee());
			pstmt.setDouble(idx++, merchantSettleBean.getTrnVmRate());
			pstmt.setDouble(idx++, merchantSettleBean.getTrnJaCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getTrnJaAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getTrnJaFee());
			pstmt.setDouble(idx++, merchantSettleBean.getTrnJaRate());
			pstmt.setString(idx++, merchantSettleBean.getPreTrnBillId());
			pstmt.setDouble(idx++, merchantSettleBean.getPreTrnVmCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getPreTrnVmAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getPreTrnVmFee());
			pstmt.setDouble(idx++, merchantSettleBean.getPreTrnVmRate());
			pstmt.setDouble(idx++, merchantSettleBean.getPreTrnJaCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getPreTrnJaAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getPreTrnJaFee());
			pstmt.setDouble(idx++, merchantSettleBean.getPreTrnJaRate());
			pstmt.setString(idx++, merchantSettleBean.getCbBillId());
			pstmt.setDouble(idx++, merchantSettleBean.getCbCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getCbAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getCbFee());
			pstmt.setDouble(idx++, merchantSettleBean.getCbRate());
			pstmt.setString(idx++, merchantSettleBean.getRefundBillId());
			pstmt.setDouble(idx++, merchantSettleBean.getRefundVmCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getRefundVmAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getRefundVmFee());
			pstmt.setDouble(idx++, merchantSettleBean.getRefundVmRate());
			pstmt.setDouble(idx++, merchantSettleBean.getRefundJaCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getRefundJaAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getRefundJaFee());
			pstmt.setDouble(idx++, merchantSettleBean.getRefundJaRate());
			pstmt.setString(idx++, merchantSettleBean.getPreRefundBillId());
			pstmt.setDouble(idx++, merchantSettleBean.getPreRefundVmCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getPreRefundVmAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getPreRefundVmFee());
			pstmt.setDouble(idx++, merchantSettleBean.getPreRefundVmRate());
			pstmt.setDouble(idx++, merchantSettleBean.getPreRefundJaCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getPreRefundJaAmt());
			pstmt.setDouble(idx++, merchantSettleBean.getPreRefundJaFee());
			pstmt.setDouble(idx++, merchantSettleBean.getPreRefundJaRate());
			pstmt.setDouble(idx++, merchantSettleBean.getManageFee());
			pstmt.setDouble(idx++, merchantSettleBean.getVanCnt());
			pstmt.setDouble(idx++, merchantSettleBean.getVanFee());
			pstmt.setDouble(idx++, merchantSettleBean.getVanRate());
			pstmt.setDouble(idx++, merchantSettleBean.getTransfeFee());
			pstmt.setDouble(idx++, merchantSettleBean.getVatAmt());
			pstmt.setLong(idx++, merchantSettleBean.getDepositIdx());
			pstmt.setString(idx++, merchantSettleBean.getEtcName());
			pstmt.setDouble(idx++, merchantSettleBean.getEtcAmt());
			pstmt.setString(idx++, merchantSettleBean.getSettleId());
			
			result = pstmt.executeUpdate();
			conn.commit();
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(merchantSettleBean),this);
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
	
	
	public boolean updateByEtc(MerchantSettleBean merchantSettleBean){
		String query = "UPDATE TB_MERCHANT_SETTLE SET TOT_FEE = ?, TOT_SETTLE = ?, ETC_NAME = ?, ETC_AMT = ? WHERE SETTLE_ID = ? ";
		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setDouble(idx++, merchantSettleBean.getTotFee());
			pstmt.setDouble(idx++, merchantSettleBean.getTotSettle());
			pstmt.setString(idx++, merchantSettleBean.getEtcName());
			pstmt.setDouble(idx++, merchantSettleBean.getEtcAmt());
			pstmt.setString(idx++, merchantSettleBean.getSettleId());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(merchantSettleBean),this);
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
	
	public boolean updateBySettle(String settleId,String status){
		String query = "UPDATE TB_MERCHANT_SETTLE SET STATUS = ?,PAY_DATE=NULL WHERE SETTLE_ID = ? ";
		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, status);
			
			pstmt.setString(idx++, settleId);
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
	
	public boolean updateBySettleComplete(String settleId,String status){
		String query = "UPDATE TB_MERCHANT_SETTLE SET STATUS = ? ,PAY_DATE=SYSDATE WHERE SETTLE_ID = ? ";
		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, status);
			
			pstmt.setString(idx++, settleId);
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
	
	public boolean updateByPay(Timestamp payDate, String settleId){
		String query = "UPDATE TB_MERCHANT_SETTLE SET STATUS = 'Y', PAY_DATE = ?  WHERE SETTLE_ID = ? ";
		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setTimestamp(idx++, payDate);
			pstmt.setString(idx++, settleId);
			
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
	
	
	public List<String> getAbleSettleDate(){
		
		String query = "SELECT TO_CHAR(SETTLE_DATE,'YYYYMMDD') AS SETTLE_LIST FROM TB_MERCHANT_SETTLE GROUP BY TO_CHAR(SETTLE_DATE,'YYYYMMDD') ORDER BY TO_CHAR(SETTLE_DATE,'YYYYMMDD') DESC";
		
		
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

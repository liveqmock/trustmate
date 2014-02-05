/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.MerchantMngDAO.java
 * Date	        : Dec 23, 2008
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

import com.pgmate.model.db.MerchantMngBean;


public class MerchantMngDAO extends DAO {

	public MerchantMngDAO() {
	}
	
	
	
	public MerchantMngBean getById(String merchantId){
		List<MerchantMngBean> list = get(" AND MERCHANT_ID='"+merchantId+"'");
		if(list.size() == 0){
			return new MerchantMngBean();
		}else{
			return (MerchantMngBean)list.get(0);
		}	
	}
	
	public MerchantMngBean getByIdx(long idx){
		List<MerchantMngBean> list = get(" AND IDX="+CommonUtil.toString(idx));
		if(list.size() == 0){
			return new MerchantMngBean();
		}else{
			return (MerchantMngBean)list.get(0);
		}	
	}
	
	public List<MerchantMngBean> get(MerchantMngBean mmBean){
		StringBuffer sb = new StringBuffer();
		if(!mmBean.getMerchantId().equals("")){
			return get(" AND MERCHANT_ID='"+mmBean.getMerchantId()+"'");
		}
		
		if(!mmBean.getCurType().equals("")){
			sb.append(" AND CUR_TYPE='"+mmBean.getCurType()+"' ");
		}
		if(!mmBean.getVan().equals("")){
			sb.append(" AND VAN='"+mmBean.getVan()+"' ");
		}
		if(!mmBean.getVanId().equals("")){
			sb.append(" AND VAN_ID='"+mmBean.getVanId()+"' ");
		}
		
		return get(sb.toString());
	}
	
	public List<MerchantMngBean> get(String subQuery){
		
		String query = "SELECT IDX, MERCHANT_ID, CUR_TYPE, ONCE_LIMIT, DAY_LIMIT, MONTH_LIMIT, CARD_MONTH_LIMIT, TEMP_LIMIT, "
			+" DUPLICATION_COUNT, REPORT_EMAIL, DEMO, VAN, VAN_ID, VM_FEE, JCB_FEE, DEPOSIT, TEMP, COMMENT_IDX, REG_DATE,VAN_FEE,CB_FEE, AUTH, DM "
			+" FROM VW_MERCHANT_MNG WHERE IDX IS NOT NULL " + subQuery;
		
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
		
		List<MerchantMngBean> list = new ArrayList<MerchantMngBean>();
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
				MerchantMngBean mmBean = new MerchantMngBean();
				mmBean.setIdx(rset.getLong("IDX"));
				mmBean.setMerchantId(rset.getString("MERCHANT_ID"));
				mmBean.setCurType(CommonUtil.nToB(rset.getString("CUR_TYPE")));
				mmBean.setOnceLimit(rset.getDouble("ONCE_LIMIT"));
				mmBean.setDayLimit(rset.getDouble("DAY_LIMIT"));
				mmBean.setMonthLimit(rset.getDouble("MONTH_LIMIT"));
				mmBean.setCardMonthLimit(rset.getDouble("CARD_MONTH_LIMIT"));
				mmBean.setTempLimit(rset.getDouble("TEMP_LIMIT"));
				mmBean.setDuplicationCount(rset.getInt("DUPLICATION_COUNT"));
				mmBean.setReportEMail(CommonUtil.nToB(rset.getString("REPORT_EMAIL")));
				mmBean.setDemo(CommonUtil.nToB(rset.getString("DEMO")));
				mmBean.setVan(rset.getString("VAN"));
				mmBean.setVanId(rset.getString("VAN_ID"));
				mmBean.setVmFee(rset.getDouble("VM_FEE"));
				mmBean.setJcbFee(rset.getDouble("JCB_FEE"));
				mmBean.setDeposit(rset.getDouble("DEPOSIT"));
				mmBean.setTemp(CommonUtil.nToB(rset.getString("TEMP")));
				mmBean.setCommentIdx(rset.getLong("COMMENT_IDX"));
				mmBean.setRegDate(rset.getTimestamp("REG_DATE"));
				mmBean.setVanFee(rset.getDouble("VAN_FEE"));
				mmBean.setCbFee(rset.getDouble("CB_FEE"));
				mmBean.setAuth(rset.getString("AUTH"));
				mmBean.setDm(rset.getString("DM"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(mmBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	
	public boolean update(MerchantMngBean mmBean){
		String query = "UPDATE TB_MERCHANT_MNG SET CUR_TYPE = ?, ONCE_LIMIT = ?, DAY_LIMIT = ?, MONTH_LIMIT = ?, CARD_MONTH_LIMIT = ?, TEMP_LIMIT=?, "
			+" DUPLICATION_COUNT = ?, REPORT_EMAIL = ?, DEMO = ?, VAN = ?, VAN_ID = ?, VM_FEE = ?, JCB_FEE = ?, DEPOSIT = ?, CB_FEE=?, TEMP = ?, AUTH=?, DM=? WHERE IDX = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, CommonUtil.nToB(mmBean.getCurType()));
			pstmt.setDouble(idx++, mmBean.getOnceLimit());
			pstmt.setDouble(idx++, mmBean.getDayLimit());
			pstmt.setDouble(idx++, mmBean.getMonthLimit());
			pstmt.setDouble(idx++, mmBean.getCardMonthLimit());
			pstmt.setDouble(idx++, mmBean.getTempLimit());
			pstmt.setInt(idx++, mmBean.getDuplicationCount());
			pstmt.setString(idx++, CommonUtil.nToB(mmBean.getReportEMail()));
			pstmt.setString(idx++, CommonUtil.nToB(mmBean.getDemo()));
			pstmt.setString(idx++, CommonUtil.nToB(mmBean.getVan()));
			pstmt.setString(idx++, CommonUtil.nToB(mmBean.getVanId()));
			pstmt.setDouble(idx++, mmBean.getVmFee());
			pstmt.setDouble(idx++, mmBean.getJcbFee());
			pstmt.setDouble(idx++, mmBean.getDeposit());
			pstmt.setDouble(idx++, mmBean.getCbFee());
			pstmt.setString(idx++, CommonUtil.nToB(mmBean.getTemp()));
			pstmt.setString(idx++, mmBean.getAuth());
			pstmt.setString(idx++, mmBean.getDm());
			pstmt.setLong(idx++, mmBean.getIdx());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(mmBean),this);
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
	
	public boolean insert(MerchantMngBean mmBean){
		String query = "INSERT INTO TB_MERCHANT_MNG ( IDX, MERCHANT_ID, CUR_TYPE, ONCE_LIMIT, DAY_LIMIT, "
			+" MONTH_LIMIT, CARD_MONTH_LIMIT, TEMP_LIMIT, DUPLICATION_COUNT, REPORT_EMAIL, DEMO, VAN, VAN_ID, VM_FEE, JCB_FEE, DEPOSIT, TEMP, COMMENT_IDX, REG_DATE, AUTH, DM) "
			+" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?, ? )";
 
		 
				
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			mmBean.setIdx(SequenceDAO.getSequenceLong("SEQ_MERCHANT_MNG"));
			mmBean.setCommentIdx(SequenceDAO.getSequenceLong("SEQ_COMMENT_SERVICE"));
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setLong(idx++, mmBean.getIdx());
			pstmt.setString(idx++, mmBean.getMerchantId());
			pstmt.setString(idx++, mmBean.getCurType());
			pstmt.setDouble(idx++, mmBean.getOnceLimit());
			pstmt.setDouble(idx++, mmBean.getDayLimit());
			pstmt.setDouble(idx++, mmBean.getMonthLimit());
			pstmt.setDouble(idx++, mmBean.getCardMonthLimit());
			pstmt.setDouble(idx++, mmBean.getTempLimit());
			pstmt.setInt(idx++, mmBean.getDuplicationCount());
			pstmt.setString(idx++, CommonUtil.nToB(mmBean.getReportEMail()));
			pstmt.setString(idx++, CommonUtil.nToB(mmBean.getDemo()));
			pstmt.setString(idx++, CommonUtil.nToB(mmBean.getVan()));
			pstmt.setString(idx++, CommonUtil.nToB(mmBean.getVanId()));
			pstmt.setDouble(idx++, mmBean.getVmFee());
			pstmt.setDouble(idx++, mmBean.getJcbFee());
			pstmt.setDouble(idx++, mmBean.getDeposit());
			pstmt.setString(idx++, CommonUtil.nToB(mmBean.getTemp()));
			pstmt.setLong(idx++, mmBean.getCommentIdx());
			pstmt.setString(idx++, mmBean.getAuth());
			pstmt.setString(idx++, mmBean.getDm());
			
		
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(mmBean),this);
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

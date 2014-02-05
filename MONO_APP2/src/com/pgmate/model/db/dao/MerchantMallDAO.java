/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.MallDAO.java
 * Date	        : Feb 6, 2009
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

import com.pgmate.model.db.MerchantMallBean;

public class MerchantMallDAO extends DAO {
	
	public MerchantMallDAO(){
		
	}
	
	public MerchantMallBean getByIdx(long idx){
		List<MerchantMallBean> list = get(" AND IDX='"+CommonUtil.toString(idx)+"'");
		if(list.size() == 0){
			return new MerchantMallBean();
		}else{
			return (MerchantMallBean)list.get(0);
		}	
	}
	
	
	public List<MerchantMallBean> getByActive(String active){
		return get(" AND ACTIVE='"+active+"'");
	}
	
	public List<MerchantMallBean> getByMerchantId(String merchantId){
		return get(" AND MERCHANT_ID ='"+merchantId+"'");
	}
	
	public List<MerchantMallBean> get(MerchantMallBean mallBean){
		StringBuffer sb = new StringBuffer();
		
		if(mallBean.getIdx() != 0){
			return get(" AND IDX ="+CommonUtil.toString(mallBean.getIdx()));
		}else if(!mallBean.getMerchantId().equals("")){
			return get(" AND MERCHANT_ID ='"+mallBean.getMerchantId()+"' ");
		}else{
			if(!mallBean.getMallId().equals("")){
				sb.append( " AND MALL_ID ='"+mallBean.getMallId()+"' ");
			}
			if(!mallBean.getActive().equals("")){
				sb.append( " AND ACTIVE='"+mallBean.getActive()+"' ");
			}
			return get(sb.toString());
		}
		
	}
	
	public List<MerchantMallBean> get(String subQuery){
		
		String query = "SELECT IDX, MERCHANT_ID, MALL_ID, TEL, FAX, EMAIL, SITE, ACTIVE, REG_DATE FROM VW_MERCHANT_MALL "
			+" WHERE IDX IS NOT NULL " + subQuery;
		
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
		
		List<MerchantMallBean> list = new ArrayList<MerchantMallBean>();
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
				MerchantMallBean mallBean = new MerchantMallBean();
				mallBean.setIdx(rset.getLong("IDX"));
				mallBean.setMerchantId(rset.getString("MERCHANT_ID"));
				mallBean.setMallId(rset.getString("MALL_ID"));
				mallBean.setTel(rset.getString("TEL"));
				mallBean.setFax(rset.getString("FAX"));
				mallBean.setEmail(rset.getString("EMAIL"));
				mallBean.setSite(rset.getString("SITE"));
				mallBean.setActive(rset.getString("ACTIVE"));
				mallBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(mallBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		return list;
	}
	
	public boolean update(MerchantMallBean mallBean){
		String query = "UPDATE TB_MERCHANT_MALL SET TEL =?, FAX =?, EMAIL =?, SITE =?, ACTIVE=? "
					 + "WHERE MALL_ID = ?";

		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, mallBean.getTel());
			pstmt.setString(idx++, mallBean.getFax());
			pstmt.setString(idx++, mallBean.getEmail());
			pstmt.setString(idx++, mallBean.getSite());
			pstmt.setString(idx++, mallBean.getActive());
			pstmt.setString(idx++, mallBean.getMallId());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(mallBean),this);
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
	
	public boolean insert(MerchantMallBean mallBean){
		String query = "INSERT INTO TB_MERCHANT_MALL (IDX, MERCHANT_ID, MALL_ID, TEL, FAX, EMAIL, SITE, ACTIVE, REG_DATE) "
			         + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, SYSDATE )";
		 
				
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			mallBean.setIdx(SequenceDAO.getSequenceLong("SEQ_MERCHANT_MALL"));
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setLong(idx++, mallBean.getIdx());
			pstmt.setString(idx++, mallBean.getMerchantId());
			pstmt.setString(idx++, mallBean.getMallId());
			pstmt.setString(idx++, mallBean.getTel());
			pstmt.setString(idx++, mallBean.getFax());
			pstmt.setString(idx++, mallBean.getEmail());
			pstmt.setString(idx++, mallBean.getSite());
			pstmt.setString(idx++, mallBean.getActive());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(mallBean),this);
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

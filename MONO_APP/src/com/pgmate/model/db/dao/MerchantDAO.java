/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.MerchantDAO.java
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

import com.pgmate.model.db.MerchantBean;

public class MerchantDAO extends DAO {

	public MerchantDAO() {
	}
	
	public List<MerchantBean> getByServiceDate(){
		return get(" AND SERVICE_DATE IS NOT NULL ");
	}
	
	public List<MerchantBean> geByName(String merchantName){
		return get(" AND NAME='"+merchantName+"'");
	}
	
	public String toQueryStyle(List<MerchantBean> list){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i< list.size() ; i++){
			MerchantBean merchantBean = (MerchantBean)list.get(i);
			sb.append("'"+merchantBean.getMerchantId()+"'");
			if(list.size()-1 != i){
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	
	public MerchantBean getByIdx(long idx){
		List<MerchantBean> list = get(" AND IDX='"+CommonUtil.toString(idx)+"'");
		if(list.size() == 0){
			return new MerchantBean();
		}else{
			return (MerchantBean)list.get(0);
		}	
	}
	
	
	public List<MerchantBean> getByActive(String active){
		return get(" AND ACTIVE='"+active+"'");
	}
	
	public MerchantBean getById(String merchantId){
		List<MerchantBean> list = get(" AND MERCHANT_ID ='"+merchantId+"'");
		if(list.size() == 0){
			return new MerchantBean();
		}else{
			return (MerchantBean)list.get(0);
		}	
	}
	
	public List<MerchantBean> get(MerchantBean merchantBean){
		StringBuffer sb = new StringBuffer();
		
		if(merchantBean.getIdx() != 0){
			return get(" AND IDX ="+CommonUtil.toString(merchantBean.getIdx()));
		}else if(!merchantBean.getMerchantId().equals("")){
			return get(" AND MERCHANT_ID ='"+merchantBean.getMerchantId()+"' ");
		}else{
			if(!merchantBean.getName().equals("")){
				sb.append( " AND NAME LIKE '%"+merchantBean.getName()+"%' ");
			}
			if(!merchantBean.getActive().equals("")){
				sb.append( " AND ACTIVE='"+merchantBean.getActive()+"' ");
			}
			if(!merchantBean.getTemp3String().equals("")){
				sb.append(" AND MERCHANT_ID IN (SELECT MERCHANT_ID FROM TB_GROUP_MERCHANT WHERE GROUP_ID='"+merchantBean.getTemp3String()+"')");
			}
			
			
			
			
			return get(sb.toString());
		}
		
	}
	
	public List<MerchantBean> get(String subQuery){
		
		String query = "SELECT IDX, MERCHANT_ID, NAME, PASSWORD, PW_UPDATE, BIZ_NATURE, PRODUCT, HOMEPAGE, ADDR1, ADDR2, ZIPCODE, TELNO, FAX, EMAIL, "
			+" IDENTINO, CEO_NAME, CEO_ENG_NAME, CEO_ADDR1, CEO_ADDR2, CEO_ZIPCODE, CEO_TELNO, CEO_PHONENO, CEO_EMAIL, CEO_IDENTINO, SETTLE_PART_NAME, "
			+" SETTLE_PART_EMAIL, SETTLE_PART_TELNO, DEV_PART_NAME, DEV_PART_EMAIL, DEV_PART_TELNO, SETTLE_BANK, SETTLE_BANKCODE, SETTLE_ACCOUNT, "
			+" SERVICE_DATE, ACTIVE, COMMENT_IDX, REG_DATE FROM VW_MERCHANT "
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
		
		List<MerchantBean> list = new ArrayList<MerchantBean>();
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
				MerchantBean merchantBean = new MerchantBean();
				merchantBean.setIdx(rset.getLong("IDX"));
				merchantBean.setMerchantId(rset.getString("MERCHANT_ID"));
				merchantBean.setName(CommonUtil.nToB(rset.getString("NAME")));
				merchantBean.setPassword(rset.getString("PASSWORD"));
				merchantBean.setPwUpdate(rset.getString("PW_UPDATE"));
				merchantBean.setBizNature(CommonUtil.nToB(rset.getString("BIZ_NATURE")));
				merchantBean.setProduct(CommonUtil.nToB(rset.getString("PRODUCT")));
				merchantBean.setHomepage(CommonUtil.nToB(rset.getString("HOMEPAGE")));
				merchantBean.setAddr1(CommonUtil.nToB(rset.getString("ADDR1")));
				merchantBean.setAddr2(CommonUtil.nToB(rset.getString("ADDR2")));
				merchantBean.setZipCode(CommonUtil.nToB(rset.getString("ZIPCODE")));
				merchantBean.setTelNo(CommonUtil.nToB(rset.getString("TELNO")));
				merchantBean.setFax(CommonUtil.nToB(rset.getString("FAX")));
				merchantBean.setEmail(CommonUtil.nToB(rset.getString("EMAIL")));
				merchantBean.setIdentiNo(CommonUtil.nToB(rset.getString("IDENTINO")));
				merchantBean.setCeoName(CommonUtil.nToB(rset.getString("CEO_NAME")));
				merchantBean.setCeoEngName(CommonUtil.nToB(rset.getString("CEO_ENG_NAME")));
				merchantBean.setCeoAddr1(CommonUtil.nToB(rset.getString("CEO_ADDR1")));
				merchantBean.setCeoAddr2(CommonUtil.nToB(rset.getString("CEO_ADDR2")));
				merchantBean.setCeoZipCode(CommonUtil.nToB(rset.getString("CEO_ZIPCODE")));
				merchantBean.setCeoTelNo(CommonUtil.nToB(rset.getString("CEO_TELNO")));
				merchantBean.setCeoPhoneNo(CommonUtil.nToB(rset.getString("CEO_PHONENO")));
				merchantBean.setCeoEMail(CommonUtil.nToB(rset.getString("CEO_EMAIL")));
				merchantBean.setCeoIdentiNo(CommonUtil.nToB(rset.getString("CEO_IDENTINO")));
				merchantBean.setSettlePartName(CommonUtil.nToB(rset.getString("SETTLE_PART_NAME")));
				merchantBean.setSettlePartEMail(CommonUtil.nToB(rset.getString("SETTLE_PART_EMAIL")));
				merchantBean.setSettlePartTelNo(CommonUtil.nToB(rset.getString("SETTLE_PART_TELNO")));
				merchantBean.setDevPartName(CommonUtil.nToB(rset.getString("DEV_PART_NAME")));
				merchantBean.setDevPartEMail(CommonUtil.nToB(rset.getString("DEV_PART_EMAIL")));
				merchantBean.setDevPartTelNo(CommonUtil.nToB(rset.getString("DEV_PART_TELNO")));
				merchantBean.setSettleBank(CommonUtil.nToB(rset.getString("SETTLE_BANK")));
				merchantBean.setSettleBankCode(CommonUtil.nToB(rset.getString("SETTLE_BANKCODE")));
				merchantBean.setSettleAccount(CommonUtil.nToB(rset.getString("SETTLE_ACCOUNT")));
				merchantBean.setServiceDate(rset.getTimestamp("SERVICE_DATE"));
				merchantBean.setActive(CommonUtil.nToB(rset.getString("ACTIVE")));
				merchantBean.setCommentIdx(rset.getLong("COMMENT_IDX"));
				merchantBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(merchantBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public boolean update(MerchantBean merchantBean){
		String query = "UPDATE TB_MERCHANT SET NAME =?,PW_UPDATE =?,BIZ_NATURE=?,PRODUCT=?,HOMEPAGE=?,ADDR1 =?,ADDR2 = ?,ZIPCODE = ?,TELNO =?,FAX =?,"
			+"EMAIL =?,IDENTINO= ?,CEO_NAME= ?,CEO_ENG_NAME = ?,CEO_ADDR1 = ?,CEO_ADDR2 = ?,CEO_ZIPCODE = ?,CEO_TELNO = ?,CEO_PHONENO = ?,CEO_EMAIL = ?,"
			+"CEO_IDENTINO = ?,SETTLE_PART_NAME = ?,SETTLE_PART_EMAIL = ?,SETTLE_PART_TELNO = ?,DEV_PART_NAME  = ?,DEV_PART_EMAIL = ?,DEV_PART_TELNO = ?,"
			+"SETTLE_BANK =?,SETTLE_BANKCODE= ?,SETTLE_ACCOUNT = ?,SERVICE_DATE= ?,ACTIVE= ?,COMMENT_IDX= ? "
			+" WHERE MERCHANT_ID = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, merchantBean.getName());
			pstmt.setString(idx++, merchantBean.getPwUpdate());
			pstmt.setString(idx++, merchantBean.getBizNature());
			pstmt.setString(idx++, merchantBean.getProduct());
			pstmt.setString(idx++, merchantBean.getHomepage());
			pstmt.setString(idx++, merchantBean.getAddr1());
			pstmt.setString(idx++, merchantBean.getAddr2());
			pstmt.setString(idx++, merchantBean.getZipCode() );
			pstmt.setString(idx++, merchantBean.getTelNo());
			pstmt.setString(idx++, merchantBean.getFax());
			pstmt.setString(idx++, merchantBean.getEmail());
			pstmt.setString(idx++, merchantBean.getIdentiNo());
			pstmt.setString(idx++, merchantBean.getCeoName());
			pstmt.setString(idx++, merchantBean.getCeoEngName());
			pstmt.setString(idx++, merchantBean.getCeoAddr1());
			pstmt.setString(idx++, merchantBean.getCeoAddr2());
			pstmt.setString(idx++, merchantBean.getCeoZipCode());
			pstmt.setString(idx++, merchantBean.getCeoTelNo());
			pstmt.setString(idx++, merchantBean.getCeoPhoneNo());
			pstmt.setString(idx++, merchantBean.getCeoEMail());
			pstmt.setString(idx++, merchantBean.getCeoIdentiNo());
			pstmt.setString(idx++, merchantBean.getSettlePartName());
			pstmt.setString(idx++, merchantBean.getSettlePartEMail());
			pstmt.setString(idx++, merchantBean.getSettlePartTelNo());
			pstmt.setString(idx++, merchantBean.getDevPartName());
			pstmt.setString(idx++, merchantBean.getDevPartEMail());
			pstmt.setString(idx++, merchantBean.getDevPartTelNo());
			pstmt.setString(idx++, merchantBean.getSettleBank());
			pstmt.setString(idx++, merchantBean.getSettleBankCode());
			pstmt.setString(idx++, merchantBean.getSettleAccount());
			pstmt.setTimestamp(idx++, merchantBean.getServiceDate());
			pstmt.setString(idx++, merchantBean.getActive());
			pstmt.setLong(idx++, merchantBean.getCommentIdx());
			pstmt.setString(idx++, merchantBean.getMerchantId());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(merchantBean),this);
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
	
	public boolean pwupdate(MerchantBean merchantBean){
		String query = "UPDATE TB_MERCHANT SET PASSWORD =?,PW_UPDATE =? WHERE IDX = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, merchantBean.getPassword());
			pstmt.setString(idx++, merchantBean.getPwUpdate());
			pstmt.setLong(idx++, merchantBean.getIdx());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(merchantBean),this);
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
	
	public boolean insert(MerchantBean merchantBean){
		String query = "INSERT INTO TB_MERCHANT (IDX, MERCHANT_ID, NAME, PASSWORD, PW_UPDATE,BIZ_NATURE,PRODUCT,HOMEPAGE, ADDR1, ADDR2, "
			+"ZIPCODE, TELNO, FAX, EMAIL, IDENTINO, CEO_NAME, CEO_ENG_NAME, CEO_ADDR1, CEO_ADDR2, CEO_ZIPCODE, CEO_TELNO, CEO_PHONENO, CEO_EMAIL, "
			+"CEO_IDENTINO, SETTLE_PART_NAME, SETTLE_PART_EMAIL, SETTLE_PART_TELNO, DEV_PART_NAME, DEV_PART_EMAIL, DEV_PART_TELNO, SETTLE_BANK, "
			+"SETTLE_BANKCODE, SETTLE_ACCOUNT, SERVICE_DATE, ACTIVE, COMMENT_IDX, REG_DATE) "
			+"VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,SYSDATE )";
		 
				
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			merchantBean.setIdx(SequenceDAO.getSequenceLong("SEQ_MERCHANT"));
			merchantBean.setCommentIdx(SequenceDAO.getSequenceLong(conn,"SEQ_COMMENT_MEMBER"));
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setLong(idx++, merchantBean.getIdx());
			pstmt.setString(idx++, merchantBean.getMerchantId());
			pstmt.setString(idx++, merchantBean.getName());
			pstmt.setString(idx++, merchantBean.getPassword());
			pstmt.setString(idx++, merchantBean.getPwUpdate());
			pstmt.setString(idx++, merchantBean.getBizNature());
			pstmt.setString(idx++, merchantBean.getProduct());
			pstmt.setString(idx++, merchantBean.getHomepage());
			pstmt.setString(idx++, merchantBean.getAddr1());
			pstmt.setString(idx++, merchantBean.getAddr2());
			pstmt.setString(idx++, merchantBean.getZipCode() );
			pstmt.setString(idx++, merchantBean.getTelNo());
			pstmt.setString(idx++, merchantBean.getFax());
			pstmt.setString(idx++, merchantBean.getEmail());
			pstmt.setString(idx++, merchantBean.getIdentiNo());
			pstmt.setString(idx++, merchantBean.getCeoName());
			pstmt.setString(idx++, merchantBean.getCeoEngName());
			pstmt.setString(idx++, merchantBean.getCeoAddr1());
			pstmt.setString(idx++, merchantBean.getCeoAddr2());
			pstmt.setString(idx++, merchantBean.getCeoZipCode());
			pstmt.setString(idx++, merchantBean.getCeoTelNo());
			pstmt.setString(idx++, merchantBean.getCeoPhoneNo());
			pstmt.setString(idx++, merchantBean.getCeoEMail());
			pstmt.setString(idx++, merchantBean.getCeoIdentiNo());
			pstmt.setString(idx++, merchantBean.getSettlePartName());
			pstmt.setString(idx++, merchantBean.getSettlePartEMail());
			pstmt.setString(idx++, merchantBean.getSettlePartTelNo());
			pstmt.setString(idx++, merchantBean.getDevPartName());
			pstmt.setString(idx++, merchantBean.getDevPartEMail());
			pstmt.setString(idx++, merchantBean.getDevPartTelNo());
			pstmt.setString(idx++, merchantBean.getSettleBank());
			pstmt.setString(idx++, merchantBean.getSettleBankCode());
			pstmt.setString(idx++, merchantBean.getSettleAccount());
			pstmt.setTimestamp(idx++, merchantBean.getServiceDate());
			pstmt.setString(idx++, merchantBean.getActive());
			pstmt.setLong(idx++, merchantBean.getCommentIdx());
		
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(merchantBean),this);
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

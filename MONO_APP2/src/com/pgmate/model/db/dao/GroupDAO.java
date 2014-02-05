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

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.BeanUtil;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.GroupBean;
import com.pgmate.model.db.GroupMerchantBean;
import com.pgmate.model.db.MerchantBean;
import com.pgmate.model.db.factory.DBFactory;
import com.pgmate.model.db.factory.DBUtil;

public class GroupDAO extends DAO {

	public GroupDAO() {
	}
	
	public List<GroupBean> geByName(String groupName){
		return get(" AND NAME='"+groupName+"'");
	}
	
	
	public List<GroupBean> getByActive(String active){
		return get(" AND ACTIVE='"+active+"'");
	}
	
	public List<MerchantBean> getByMerchant(String groupId){
		MerchantDAO merchantDAO = new MerchantDAO();
		merchantDAO.pageSize = 10000;
		merchantDAO.orderBy = "ORDER BY MERCHANT_ID ASC";
		return merchantDAO.get(" AND MERCHANT_ID IN (SELECT MERCHANT_ID FROM TB_GROUP_MERCHANT WHERE GROUP_ID='"+groupId+"')");
	}
	
	public GroupBean getById(String groupId){
		List<GroupBean> list = get(" AND GROUP_ID ='"+groupId+"'");
		if(list.size() == 0){
			return new GroupBean();
		}else{
			return (GroupBean)list.get(0);
		}	
	}
	
	public GroupBean getByIdx(long idx){
		List<GroupBean> list = get(" AND IDX='"+CommonUtil.toString(idx)+"'");
		if(list.size() == 0){
			return new GroupBean();
		}else{
			return (GroupBean)list.get(0);
		}	
	}
	
	public List<GroupBean> get(GroupBean groupBean){
		StringBuffer sb = new StringBuffer();
		
		if(!groupBean.getGroupId().equals("")){
			return get(" AND GROUP_ID ='"+groupBean.getGroupId()+"' ");
		}else{
			if(!groupBean.getName().equals("")){
				sb.append( " AND NAME LIKE '%"+groupBean.getName()+"%' ");
			}
			if(!groupBean.getActive().equals("")){
				sb.append( " AND ACTIVE='"+groupBean.getActive()+"' ");
			}
			
			
			return get(sb.toString());
		}
		
	}
	
	public List<GroupBean> get(String subQuery){
		
		String query = "SELECT IDX, GROUP_ID, NAME, PASSWORD, PW_UPDATE, BIZ_NATURE, PRODUCT, HOMEPAGE, ADDR1, ADDR2, ZIPCODE, TELNO, FAX, EMAIL, "
			+" IDENTINO, CEO_NAME, CEO_ENG_NAME, CEO_ADDR1, CEO_ADDR2, CEO_ZIPCODE, CEO_TELNO, CEO_PHONENO, CEO_EMAIL, CEO_IDENTINO, SETTLE_PART_NAME, "
			+" SETTLE_PART_EMAIL, SETTLE_PART_TELNO, SETTLE_BANK, SETTLE_BANKCODE, SETTLE_ACCOUNT, ACTIVE, COMMENT_IDX, REG_DATE FROM TB_GROUP "
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
		
		List<GroupBean> list = new ArrayList<GroupBean>();
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
				GroupBean groupBean = new GroupBean();
				groupBean.setIdx(rset.getLong("IDX"));
				groupBean.setGroupId(rset.getString("GROUP_ID"));
				groupBean.setName(CommonUtil.nToB(rset.getString("NAME")));
				groupBean.setPassword(rset.getString("PASSWORD"));
				groupBean.setPwUpdate(rset.getString("PW_UPDATE"));
				groupBean.setBizNature(CommonUtil.nToB(rset.getString("BIZ_NATURE")));
				groupBean.setProduct(CommonUtil.nToB(rset.getString("PRODUCT")));
				groupBean.setHomepage(CommonUtil.nToB(rset.getString("HOMEPAGE")));
				groupBean.setAddr1(CommonUtil.nToB(rset.getString("ADDR1")));
				groupBean.setAddr2(CommonUtil.nToB(rset.getString("ADDR2")));
				groupBean.setZipCode(CommonUtil.nToB(rset.getString("ZIPCODE")));
				groupBean.setTelNo(CommonUtil.nToB(rset.getString("TELNO")));
				groupBean.setFax(CommonUtil.nToB(rset.getString("FAX")));
				groupBean.setEmail(CommonUtil.nToB(rset.getString("EMAIL")));
				groupBean.setIdentiNo(CommonUtil.nToB(rset.getString("IDENTINO")));
				groupBean.setCeoName(CommonUtil.nToB(rset.getString("CEO_NAME")));
				groupBean.setCeoEngName(CommonUtil.nToB(rset.getString("CEO_ENG_NAME")));
				groupBean.setCeoAddr1(CommonUtil.nToB(rset.getString("CEO_ADDR1")));
				groupBean.setCeoAddr2(CommonUtil.nToB(rset.getString("CEO_ADDR2")));
				groupBean.setCeoZipCode(CommonUtil.nToB(rset.getString("CEO_ZIPCODE")));
				groupBean.setCeoTelNo(CommonUtil.nToB(rset.getString("CEO_TELNO")));
				groupBean.setCeoPhoneNo(CommonUtil.nToB(rset.getString("CEO_PHONENO")));
				groupBean.setCeoEMail(CommonUtil.nToB(rset.getString("CEO_EMAIL")));
				groupBean.setCeoIdentiNo(CommonUtil.nToB(rset.getString("CEO_IDENTINO")));
				groupBean.setSettlePartName(CommonUtil.nToB(rset.getString("SETTLE_PART_NAME")));
				groupBean.setSettlePartEMail(CommonUtil.nToB(rset.getString("SETTLE_PART_EMAIL")));
				groupBean.setSettlePartTelNo(CommonUtil.nToB(rset.getString("SETTLE_PART_TELNO")));
				groupBean.setSettleBank(CommonUtil.nToB(rset.getString("SETTLE_BANK")));
				groupBean.setSettleBankCode(CommonUtil.nToB(rset.getString("SETTLE_BANKCODE")));
				groupBean.setSettleAccount(CommonUtil.nToB(rset.getString("SETTLE_ACCOUNT")));
				groupBean.setActive(CommonUtil.nToB(rset.getString("ACTIVE")));
				groupBean.setCommentIdx(rset.getLong("COMMENT_IDX"));
				groupBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(groupBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	public boolean update(GroupBean groupBean){
		String query = "UPDATE TB_GROUP SET NAME =?,PW_UPDATE =?,BIZ_NATURE=?,PRODUCT=?,HOMEPAGE=?,ADDR1 =?,ADDR2 = ?,ZIPCODE = ?,TELNO =?,FAX =?,"
			+"EMAIL =?,IDENTINO= ?,CEO_NAME= ?,CEO_ENG_NAME = ?,CEO_ADDR1 = ?,CEO_ADDR2 = ?,CEO_ZIPCODE = ?,CEO_TELNO = ?,CEO_PHONENO = ?,CEO_EMAIL = ?,"
			+"CEO_IDENTINO = ?,SETTLE_PART_NAME = ?,SETTLE_PART_EMAIL = ?,SETTLE_PART_TELNO = ?, "
			+"SETTLE_BANK =?,SETTLE_BANKCODE= ?,SETTLE_ACCOUNT = ?, ACTIVE= ?,COMMENT_IDX= ? "
			+" WHERE GROUP_ID = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, groupBean.getName());
			pstmt.setString(idx++, groupBean.getPwUpdate());
			pstmt.setString(idx++, groupBean.getBizNature());
			pstmt.setString(idx++, groupBean.getProduct());
			pstmt.setString(idx++, groupBean.getHomepage());
			pstmt.setString(idx++, groupBean.getAddr1());
			pstmt.setString(idx++, groupBean.getAddr2());
			pstmt.setString(idx++, groupBean.getZipCode() );
			pstmt.setString(idx++, groupBean.getTelNo());
			pstmt.setString(idx++, groupBean.getFax());
			pstmt.setString(idx++, groupBean.getEmail());
			pstmt.setString(idx++, groupBean.getIdentiNo());
			pstmt.setString(idx++, groupBean.getCeoName());
			pstmt.setString(idx++, groupBean.getCeoEngName());
			pstmt.setString(idx++, groupBean.getCeoAddr1());
			pstmt.setString(idx++, groupBean.getCeoAddr2());
			pstmt.setString(idx++, groupBean.getCeoZipCode());
			pstmt.setString(idx++, groupBean.getCeoTelNo());
			pstmt.setString(idx++, groupBean.getCeoPhoneNo());
			pstmt.setString(idx++, groupBean.getCeoEMail());
			pstmt.setString(idx++, groupBean.getCeoIdentiNo());
			pstmt.setString(idx++, groupBean.getSettlePartName());
			pstmt.setString(idx++, groupBean.getSettlePartEMail());
			pstmt.setString(idx++, groupBean.getSettlePartTelNo());
			pstmt.setString(idx++, groupBean.getSettleBank());
			pstmt.setString(idx++, groupBean.getSettleBankCode());
			pstmt.setString(idx++, groupBean.getSettleAccount());
			pstmt.setString(idx++, groupBean.getActive());
			pstmt.setLong(idx++, groupBean.getCommentIdx());
			pstmt.setString(idx++, groupBean.getGroupId());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(groupBean),this);
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
	
	public boolean pwupdate(GroupBean groupBean){
		String query = "UPDATE TB_GROUP SET PASSWORD =?   WHERE GROUP_ID = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, groupBean.getPassword());
			pstmt.setString(idx++, groupBean.getGroupId());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(groupBean),this);
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
	
	public boolean insert(GroupBean groupBean){
		String query = "INSERT INTO TB_GROUP (IDX, GROUP_ID, NAME, PASSWORD, PW_UPDATE,BIZ_NATURE,PRODUCT,HOMEPAGE, ADDR1, ADDR2, "
			+"ZIPCODE, TELNO, FAX, EMAIL, IDENTINO, CEO_NAME, CEO_ENG_NAME, CEO_ADDR1, CEO_ADDR2, CEO_ZIPCODE, CEO_TELNO, CEO_PHONENO, CEO_EMAIL, "
			+"CEO_IDENTINO, SETTLE_PART_NAME, SETTLE_PART_EMAIL, SETTLE_PART_TELNO, SETTLE_BANK, "
			+"SETTLE_BANKCODE, SETTLE_ACCOUNT, ACTIVE, COMMENT_IDX, REG_DATE) "
			+"VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? , SYSDATE )";
		 
				
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			groupBean.setIdx(SequenceDAO.getSequenceLong("SEQ_GROUP"));
			groupBean.setCommentIdx(SequenceDAO.getSequenceLong(conn,"SEQ_COMMENT_MEMBER"));
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setLong(idx++, groupBean.getIdx());
			pstmt.setString(idx++, groupBean.getGroupId());
			pstmt.setString(idx++, groupBean.getName());
			pstmt.setString(idx++, groupBean.getPassword());
			pstmt.setString(idx++, groupBean.getPwUpdate());
			pstmt.setString(idx++, groupBean.getBizNature());
			pstmt.setString(idx++, groupBean.getProduct());
			pstmt.setString(idx++, groupBean.getHomepage());
			pstmt.setString(idx++, groupBean.getAddr1());
			pstmt.setString(idx++, groupBean.getAddr2());
			pstmt.setString(idx++, groupBean.getZipCode() );
			pstmt.setString(idx++, groupBean.getTelNo());
			pstmt.setString(idx++, groupBean.getFax());
			pstmt.setString(idx++, groupBean.getEmail());
			pstmt.setString(idx++, groupBean.getIdentiNo());
			pstmt.setString(idx++, groupBean.getCeoName());
			pstmt.setString(idx++, groupBean.getCeoEngName());
			pstmt.setString(idx++, groupBean.getCeoAddr1());
			pstmt.setString(idx++, groupBean.getCeoAddr2());
			pstmt.setString(idx++, groupBean.getCeoZipCode());
			pstmt.setString(idx++, groupBean.getCeoTelNo());
			pstmt.setString(idx++, groupBean.getCeoPhoneNo());
			pstmt.setString(idx++, groupBean.getCeoEMail());
			pstmt.setString(idx++, groupBean.getCeoIdentiNo());
			pstmt.setString(idx++, groupBean.getSettlePartName());
			pstmt.setString(idx++, groupBean.getSettlePartEMail());
			pstmt.setString(idx++, groupBean.getSettlePartTelNo());
			pstmt.setString(idx++, groupBean.getSettleBank());
			pstmt.setString(idx++, groupBean.getSettleBankCode());
			pstmt.setString(idx++, groupBean.getSettleAccount());
			pstmt.setString(idx++, groupBean.getActive());
			pstmt.setLong(idx++, groupBean.getCommentIdx());
		
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(groupBean),this);
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
	
	public boolean checkMerchant(GroupMerchantBean gmBean){
		
		String query = "SELECT MERCHANT_ID FROM TB_GROUP_MERCHANT WHERE MERCHANT_ID='"+gmBean.getMerchantId()+"' ";
		String check = "";
		
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
				check = rset.getString("MERCHANT_ID");
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		if(!CommonUtil.isNullOrSpace(check)){
			return true;
		}else{
			return false;
		}
		
	}
	
	public boolean checkMerchant(String groupId, String merchantId){
		
		String query = "SELECT MERCHANT_ID FROM TB_GROUP_MERCHANT WHERE GROUP_ID='"+groupId+"' AND MERCHANT_ID='"+merchantId+"' ";
		String check = "";
		
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
				check = rset.getString("MERCHANT_ID");
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		if(!CommonUtil.isNullOrSpace(check)){
			return true;
		}else{
			return false;
		}
		
	}
	
	public boolean insertGroupMerchant(GroupMerchantBean gmBean){
		String query = "INSERT INTO TB_GROUP_MERCHANT (GROUP_ID, MERCHANT_ID, REG_ID, REG_DATE) VALUES (? ,? ,? ,SYSDATE )";
		 
				
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			
			pstmt.setString(idx++, gmBean.getGroupId());
			pstmt.setString(idx++, gmBean.getMerchantId());
			pstmt.setString(idx++, gmBean.getRegId());
			
		result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(gmBean),this);
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
	
	public boolean updateGroupMerchant(GroupMerchantBean gmBean){
		String query = "UPDATE TB_GROUP_MERCHANT SET GROUP_ID =? , REG_ID =?  WHERE MERCHANT_ID = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, gmBean.getGroupId());
			pstmt.setString(idx++, gmBean.getRegId());
			pstmt.setString(idx++, gmBean.getMerchantId());
			
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(gmBean),this);
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
	
	public GroupBean getByMerchantId(String merchantId){
		String query = "SELECT GROUP_ID, NAME FROM TB_GROUP WHERE GROUP_ID=(SELECT GROUP_ID FROM TB_GROUP_MERCHANT WHERE MERCHANT_ID='"+merchantId+"')";
		
		GroupBean gBean = new GroupBean();
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
				gBean.setGroupId(rset.getString("GROUP_ID"));
				gBean.setName(rset.getString("NAME"));
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return gBean;
	}
	
	public boolean deleteMerchantId(String merchantId){
		String query = "DELETE TB_GROUP_MERCHANT WHERE MERCHANT_ID = ?";


		
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, merchantId);
			
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
	
	public List<String> getMerchantList(String groupId,String merchantId){
		
		String query = "SELECT MERCHANT_ID FROM TB_GROUP_MERCHANT WHERE GROUP_ID='"+groupId+"' AND MERCHANT_ID LIKE '%"+merchantId+"%' ORDER BY MERCHANT_ID DESC";
			
		
		
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
				list.add(rset.getString("MERCHANT_ID"));
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

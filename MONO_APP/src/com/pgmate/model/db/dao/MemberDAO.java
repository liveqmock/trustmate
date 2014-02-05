/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.dao.MemberDAO.java
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

import com.pgmate.model.db.MemberBean;
import com.pgmate.model.db.factory.DBFactory;
import com.pgmate.model.db.factory.DBUtil;

public class MemberDAO extends DAO{

	
	public MemberDAO() {
	}
		
	public List<MemberBean> getByGrade(String memberGrade){
		return get(" AND MEMBER_GRADE ="+memberGrade);
	}
	
	public MemberBean getByIdx(String idx){
		List<MemberBean> list = get(" AND IDX ="+idx);
		if(list.size() == 0){
			return new MemberBean();
		}else{
			return (MemberBean)list.get(0);
		}
	}
	
	public List<MemberBean> getByActive(String active){
		return get(" AND ACTIVE ='"+active+"'");
	}
	
	public List<MemberBean> getByName(String memberName){
		return get(" AND NAME ='"+memberName+"'");
	}
	
	public List<MemberBean> getByNotice(String notice){
		return get(" AND NOTICE ='"+notice+"'");
	}
	
	public String[] getBySmsNotice(String notice){
		List<MemberBean> l = null;
		if(notice.equals("")){
			l = get(" AND ACTIVE ='1'");
		}else{
			l = get(" AND NOTICE ='"+notice+"' AND ACTIVE ='1'");
		}
		String[] recvTelNo = new String[l.size()];
		if(l.size() !=0){
			for(int i=0;i<l.size();i++){
				MemberBean memberBean = (MemberBean)l.get(i);
				recvTelNo[i] =  memberBean.getPhoneNo();
			}
		}
		return recvTelNo;
	}
	
	public MemberBean getById(String memberId){
		List<MemberBean> list = get(" AND MEMBER_ID ='"+memberId+"'");
		if(list.size() == 0){
			return new MemberBean();
		}else{
			return (MemberBean)list.get(0);
		}
	}
	
	public List<MemberBean> get(MemberBean memberBean){
		StringBuffer sb = new StringBuffer();
		
		if(memberBean.getIdx() != 0){
			return get(" AND IDX ="+CommonUtil.toString(memberBean.getIdx()));
		}else if(!memberBean.getMemberId().equals("")){
			return get(" AND MEMBER_ID ='"+memberBean.getMemberId()+"'");
		}else{
			if(!memberBean.getActive().equals("")){
				sb.append( "AND ACTIVE='"+memberBean.getActive()+"' ");
			}
			return get(sb.toString());
		}
		
	}
	
	public List<MemberBean> get(String subQuery){
		String query = "SELECT IDX ,MEMBER_ID, NAME, PASSWORD, OFFICE_EMAIL, PHONE_NO, MEMBER_GRADE, ACTIVE, NOTICE, "
			+" REG_DATE FROM VW_MEMBER WHERE IDX IS NOT NULL " + subQuery;
		
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
		
		List<MemberBean> list = new ArrayList<MemberBean>();
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
				MemberBean memberBean = new MemberBean();
				memberBean.setIdx(rset.getLong("IDX"));
				memberBean.setMemberId(rset.getString("MEMBER_ID"));
				memberBean.setName(rset.getString("NAME"));
				memberBean.setPassword(rset.getString("PASSWORD"));
				memberBean.setOfficeEmail(rset.getString("OFFICE_EMAIL"));
				memberBean.setPhoneNo(rset.getString("PHONE_NO"));
				memberBean.setMemberGrade(rset.getInt("MEMBER_GRADE"));
				memberBean.setActive(rset.getString("ACTIVE"));
				memberBean.setNotice(rset.getString("NOTICE"));
				memberBean.setRegDate(rset.getTimestamp("REG_DATE"));
				super.totalCount = rset.getLong("TOTAL_COUNT");
				list.add(memberBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		
		return list;
	}
	
	
	public boolean update(MemberBean memberBean){
		String query = "UPDATE TB_MEMBER SET NAME =?, PASSWORD =?, OFFICE_EMAIL =?, PHONE_NO =?, MEMBER_GRADE =?, ACTIVE =?  WHERE  IDX = ?";
			
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, memberBean.getName());
			pstmt.setString(idx++, memberBean.getPassword());
			pstmt.setString(idx++, memberBean.getOfficeEmail());
			pstmt.setString(idx++, memberBean.getPhoneNo());
			pstmt.setInt(idx++, memberBean.getMemberGrade());
			pstmt.setString(idx++, memberBean.getActive());
			pstmt.setLong(idx++, memberBean.getIdx());
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(memberBean),this);
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
	
	public boolean update2(MemberBean memberBean){
		String query = "UPDATE TB_MEMBER SET NAME =?, OFFICE_EMAIL =?, PHONE_NO =?, MEMBER_GRADE =?, ACTIVE =?, NOTICE =? WHERE  IDX = ?";
			
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, memberBean.getName());
			pstmt.setString(idx++, memberBean.getOfficeEmail());
			pstmt.setString(idx++, memberBean.getPhoneNo());
			pstmt.setInt(idx++, memberBean.getMemberGrade());
			pstmt.setString(idx++, memberBean.getActive());
			pstmt.setString(idx++, memberBean.getNotice());
			pstmt.setLong(idx++, memberBean.getIdx());
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(memberBean),this);
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
	
	public boolean pwupdate(MemberBean memberBean){
		String query = "UPDATE TB_MEMBER SET PASSWORD =? WHERE  IDX = ?";
			
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			pstmt.setString(idx++, memberBean.getPassword());
			pstmt.setLong(idx++, memberBean.getIdx());
			result = pstmt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(memberBean),this);
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
	
	public boolean insert(MemberBean memberBean){
		String query = "INSERT INTO TB_MEMBER ( IDX, MEMBER_ID, NAME, PASSWORD, OFFICE_EMAIL, PHONE_NO, MEMBER_GRADE, ACTIVE, NOTICE, REG_DATE)"
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE ) ";
				
		int result 	= 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		
		try{
			
			memberBean.setIdx(SequenceDAO.getSequenceLong("SEQ_MEMBER"));
			
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);
			
			int idx = 1;
			
			pstmt.setLong(idx++, memberBean.getIdx());
			pstmt.setString(idx++, memberBean.getMemberId());
			pstmt.setString(idx++, memberBean.getName());
			pstmt.setString(idx++, memberBean.getPassword());
			pstmt.setString(idx++, memberBean.getOfficeEmail());
			pstmt.setString(idx++, memberBean.getPhoneNo());
			pstmt.setInt(idx++, memberBean.getMemberGrade());
			pstmt.setString(idx++, memberBean.getActive());
			pstmt.setString(idx++, memberBean.getNotice());
			result = pstmt.executeUpdate();
			conn.commit();
			 
		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(memberBean),this);
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

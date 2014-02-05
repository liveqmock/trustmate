/*
 * Project Name : PG_APP
 * Project      : PW_APP
 * File Name    : com.pgmate.model.db.dao.TrnsctnRiskDAO.java
 * Date	        : 2010. 6. 1.
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

import com.pgmate.model.db.TrnsctnRiskBean;
import com.pgmate.model.db.factory.DBFactory;


public class TrnsctnRiskDAO extends DAO {

	public TrnsctnRiskDAO(){

	}

	public TrnsctnRiskBean getByIdx(Long idx){
		List<TrnsctnRiskBean> list = get(" AND IDX = "+CommonUtil.parseLong(idx)+" ");
		if(list.size() == 0){
			return new TrnsctnRiskBean();
		}else{
			return (TrnsctnRiskBean)list.get(0);
		}
	}

	public List<TrnsctnRiskBean>  getByUnit(String unit1,String unit2,String unit3){
		unit1 = CommonUtil.replace(unit1, "-","");
		unit2 = CommonUtil.replace(unit2, "-","");
		unit3 = CommonUtil.replace(unit3, "-","");
		return get(" AND ACTIVE ='Y' AND REPLACE(UNIT,'-','') IN ('"+unit1+"','"+unit2+"','"+unit3+"') ");
	}

	public List<TrnsctnRiskBean> get(TrnsctnRiskBean triskBean){
		StringBuffer sb = new StringBuffer();
		if(triskBean.getIdx() != 0){
			return get(" AND IDX = "+triskBean.getIdx()+" ");
		}else{
			if(!triskBean.getUnit().equals("")){
				sb.append(" AND REPLACE(UNIT,'-','') LIKE '%"+triskBean.getUnit()+"%' ");
			}
			if(!triskBean.getActive().equals("")){
				sb.append(" AND ACTIVE = '"+triskBean.getActive()+"' ");
			}
			if(!triskBean.getComments().equals("")){
				sb.append(" AND COMMENTS LIKE '%"+triskBean.getComments()+"%' ");
			}
		}

		return get(sb.toString());
	}

	public List<TrnsctnRiskBean> get(String subQuery){
		String query = "SELECT IDX, UNIT, ACTIVE, COMMENTS, REG_DATE "
			+ " FROM TB_TRNSCTN_RISK WHERE IDX IS NOT NULL " + subQuery;
		
		if(!super.orderBy.equals("")){
			query += " "+super.orderBy;
		}
		
		query = super.toPaging(query);

		List<TrnsctnRiskBean> list = new ArrayList<TrnsctnRiskBean>();

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
				TrnsctnRiskBean triskBean = new TrnsctnRiskBean();

				triskBean.setIdx(rset.getLong("IDX"));
				triskBean.setUnit(rset.getString("UNIT"));
				triskBean.setActive(rset.getString("ACTIVE"));
				triskBean.setComments(rset.getString("COMMENTS"));
				triskBean.setRegDate(rset.getTimestamp("REG_DATE"));
				list.add(triskBean);
			}
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn,pstmt,rset);
		}
		return list;
	}


	public boolean insert(TrnsctnRiskBean triskBean){

		String query =  "INSERT INTO TB_TRNSCTN_RISK(IDX, UNIT, ACTIVE, COMMENTS, REG_DATE) "
			 + "VALUES (SEQ_TRNSCTN_RISK.NEXTVAL, ?, ?, ?, SYSDATE) ";;
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;


		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);

			int idx = 1;
			pstmt.setString(idx++, triskBean.getUnit());
			pstmt.setString(idx++, triskBean.getActive());
			pstmt.setString(idx++, triskBean.getComments());

			result = pstmt.executeUpdate();
			conn.commit();

		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(triskBean),this);
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
	
	public boolean update(TrnsctnRiskBean triskBean){

		String query =  "UPDATE TB_TRNSCTN_RISK SET UNIT=?, ACTIVE=?, COMMENTS=? WHERE IDX = ? ";
		
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;


		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);

			int idx = 1;
			pstmt.setString(idx++, triskBean.getUnit());
			pstmt.setString(idx++, triskBean.getActive());
			pstmt.setString(idx++, triskBean.getComments());
			pstmt.setLong(idx++, triskBean.getIdx());

			result = pstmt.executeUpdate();
			conn.commit();

		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(triskBean),this);
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

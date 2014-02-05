package com.pgmate.model.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.BeanUtil;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.AlertBean;
import com.pgmate.model.db.factory.DBFactory;

public class AlertDAO extends DAO {
	
	public AlertDAO(){
	}
	
	public List<AlertBean> get(AlertBean alertBean){
		StringBuffer sb = new StringBuffer();
		
		if(!alertBean.getAlertType().equals("")){
			sb.append(" AND ALERT_TYPE = '"+alertBean.getAlertType()+"' ");
		}
		if(!alertBean.getTarget().equals("")){
			sb.append(" AND TARGET = '"+alertBean.getTarget()+"' ");
		}
		if(!alertBean.getRegId().equals("")){
			sb.append(" AND REG_ID = '"+alertBean.getRegId()+"' ");
		}

		return get(sb.toString());
	}
	
	public List<AlertBean> get(String subQuery){
		String query = "SELECT ALERT_TYPE, RESULT_MSG, TARGET, REG_ID, REG_DATE, FC_CODE('KAPPV_CODE',RESULT_MSG,'ko') AS MSG, MESSAGE "
			+ " FROM TB_ALERT WHERE ALERT_TYPE IS NOT NULL " + subQuery;
		
		if(!super.orderBy.equals("")){
			query += " "+super.orderBy;
		}
		
		query = super.toPaging(query);

		List<AlertBean> list = new ArrayList<AlertBean>();

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
				AlertBean triskBean = new AlertBean();

				triskBean.setAlertType(rset.getString("ALERT_TYPE"));
				triskBean.setResultMsg(rset.getString("RESULT_MSG"));
				triskBean.setTarget(rset.getString("TARGET"));
				triskBean.setRegId(rset.getString("REG_ID"));
				triskBean.setRegDate(rset.getTimestamp("REG_DATE"));
				triskBean.setMsg(rset.getString("MSG"));
				triskBean.setMessage(rset.getString("MESSAGE"));
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
	
	public boolean insert(AlertBean alertBean){
		
		String query = "INSERT INTO TB_ALERT(ALERT_TYPE, RESULT_MSG, TARGET, MESSAGE,  REG_ID, REG_DATE)";
		
		if(alertBean.getMessage().equals("")){
			query = query + "VALUES (?, ?, ?, FC_CODE('KAPPV_CODE',?,'ko'), ?, SYSDATE) ";
		}else{
			query = query + "VALUES (?, ?, ?, ?, ?, SYSDATE) ";
		}

		
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;
		

		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);

			int idx = 1;
			pstmt.setString(idx++, alertBean.getAlertType());
			pstmt.setString(idx++, alertBean.getResultMsg());
			pstmt.setString(idx++, alertBean.getTarget());
			if(alertBean.getMessage().equals("")){
				pstmt.setString(idx++, alertBean.getResultMsg());
			}else{
				pstmt.setString(idx++, alertBean.getMessage());
			}
			pstmt.setString(idx++, alertBean.getRegId());

			result = pstmt.executeUpdate();
			conn.commit();

		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(alertBean),this);
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
	
	public boolean delete(AlertBean alertBean){

		String query =  "DELETE FROM TB_ALERT WHERE ALERT_TYPE=?, RESULT_MSG=?, TARGET=?, REG_ID=? ";
		int result = 0;
		DBFactory db 	= null;
		PreparedStatement pstmt	= null;
		Connection conn			= null;


		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			pstmt	= conn.prepareStatement(query);

			int idx = 1;
			pstmt.setString(idx++, alertBean.getAlertType());
			pstmt.setString(idx++, alertBean.getResultMsg());
			pstmt.setString(idx++, alertBean.getTarget());
			pstmt.setString(idx++, alertBean.getRegId());

			result = pstmt.executeUpdate();
			conn.commit();

		}catch(Exception e){
			Log.debug("log.sql",BeanUtil.beanToString(alertBean),this);
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

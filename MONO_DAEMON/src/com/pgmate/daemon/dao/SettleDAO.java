/* 
 * Project      : MONO_DAEMON
 * File Name    : com.pgmate.daemon.dao.SettleDAO.java
 * Date         : Jun 4, 2009
 * Version      : 1.0
 * Author       : ginaida@trustmate.net
 * Comment      :  
 */

package com.pgmate.daemon.dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import biz.trustnet.common.db.DBConnectionManager;
import biz.trustnet.common.db.DBFactory;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.daemon.bean.SettleBean;

public class SettleDAO {

	public SettleDAO(){
		
	}
	
	public List<SettleBean> getPanworldSettle(){
		String query = "SELECT MERCHANT_ID,START_DAY,END_DAY,TOTAL_CNT,TOTAL_AMT,(TOTAL_CNT-REFUND_CNT-CB_CNT) AS VALID_CNT,(TOTAL_AMT-REFUND_AMT-CB_AMT) AS VALID_AMT,REFUND_CNT,REFUND_AMT,CB_CNT,CB_AMT FROM" 
					+"	("
					+"	SELECT T.MERCHANT_ID,TO_CHAR(ADD_MONTHS(SYSDATE,-2),'YYYY-MM')||'-01' AS START_DAY,TO_CHAR(LAST_DAY(ADD_MONTHS(SYSDATE,-2)),'YYYY-MM-DD') AS END_DAY ,SUM(DECODE(SETTLE_TYPE,'T',1,0)) TOTAL_CNT ,SUM(DECODE(SETTLE_TYPE,'T',T.AMOUNT,0)) TOTAL_AMT,"
					+"	SUM(DECODE(SETTLE_TYPE,'V',1,0)) REFUND_CNT,SUM(DECODE(SETTLE_TYPE,'V',T.AMOUNT,0)) REFUND_AMT,"
					+"	SUM(DECODE(SETTLE_TYPE,'C',1,0)) CB_CNT,SUM(DECODE(SETTLE_TYPE,'C',T.AMOUNT,0)) CB_AMT"
					+"	FROM TB_SETTLE_IDX S,TB_TRNSCTN T" 
					+"	WHERE S.TRANSACTION_ID = T.TRANSACTION_ID AND S.TRANSACTION_ID LIKE TO_CHAR(ADD_MONTHS(SYSDATE,-2),'YYMM')||'%' GROUP BY T.MERCHANT_ID	)";
		
		DBConnectionManager db 	= null;
		Statement stmt			= null;
		Connection conn			= null;
		ResultSet rset			= null;
		List<SettleBean> list = new ArrayList<SettleBean>();
		try{
			db 		= DBFactory.getInstance();
			conn	= db.getConnection();
			stmt	= conn.createStatement();
			rset	= stmt.executeQuery(query);
			
			while(rset.next()){
				SettleBean psBean = new SettleBean();
				psBean.setMerchantId(rset.getString("MERCHANT_ID"));
				psBean.setStartDay(rset.getString("START_DAY"));
				psBean.setEndDay(rset.getString("END_DAY"));
				psBean.setTotalCnt(rset.getDouble("TOTAL_CNT"));
				psBean.setTotalAmt(rset.getDouble("TOTAL_AMT"));
				psBean.setValidCnt(rset.getDouble("VALID_CNT"));
				psBean.setValidAmt(rset.getDouble("VALID_AMT"));
				psBean.setRefundCnt(rset.getDouble("REFUND_CNT"));
				psBean.setRefundAmt(rset.getDouble("REFUND_AMT"));
				psBean.setCbCnt(rset.getDouble("CB_CNT"));
				psBean.setCbAmt(rset.getDouble("CB_AMT"));
				list.add(psBean);
			}
			
		}catch(Exception e){
			Log.debug("log.sql","QUERY="+query,this);
			Log.debug("log.sql",CommonUtil.getExceptionMessage(e),this);
		}finally{
			db.close(conn, stmt, rset);
		}
		return list;
	}
}

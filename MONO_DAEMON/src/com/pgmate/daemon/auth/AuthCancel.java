package com.pgmate.daemon.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import biz.trustnet.common.comm.client.SocketManager;
import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.comm.HeaderBean;
import com.pgmate.model.comm.T002Bean;
import com.pgmate.model.db.TrnsctnBean;
import com.pgmate.model.db.factory.DBFactory;
import com.pgmate.payment.util.GSICipher;

/**
 * @author Administrator
 *
 */
public class AuthCancel {

	public AuthCancel(){
	}
	
	public void execute(){
		List<TrnsctnBean> list = getList();
		if(list.size() == 0){
			
		}else{
			for(int i=0 ; i < list.size(); i++){
				TrnsctnBean trnsctnBean = (TrnsctnBean)list.get(i);
				HeaderBean headerBean = new HeaderBean();
				headerBean.setSpecType("CFIX");
				headerBean.setTrnType("T002");
				headerBean.setMallId(trnsctnBean.getMallId());
				headerBean.setMerchantId(trnsctnBean.getMerchantId());
				headerBean.setServiceType(trnsctnBean.getServiceType());
				headerBean.setIpAddress("112.175.48.41");
				headerBean.setPayNo(trnsctnBean.getPayNo()+"1");
				
				
				T002Bean tBean = new T002Bean();
				tBean.setVoidType("1");
				tBean.setRTransactionId(trnsctnBean.getTransactionId());
				
				
				byte[] response = connect((headerBean.getTransaction(tBean.getTransaction())).getBytes());
				HeaderBean resBean = new HeaderBean(response);
				tBean = new T002Bean(CommonUtil.toString(response,200,response.length-200));
				
				if(resBean.getResultMsg().equals("0000")){
					Log.debug("log.day",(i+1)+" ["+tBean.getRTransactionId() +"] CANCEL SUCCEED ",this);
				}else{
					Log.debug("log.day",(i+1)+" ["+tBean.getRTransactionId() +"] CANCEL FAILURE "+headerBean.getResultCd(),this);
				}
			}
			
		}
		
		
	
		
	}
	
	public byte[] connect(byte[] request){
		GSICipher cipher	= new GSICipher();
		byte[] encrypt 		= cipher.encrypt(request);
		byte[] decrypt 		= null;
		SocketManager socket = new SocketManager();
		socket.setSocketInfo("localhost","20000");
		socket.setSocketTimeout(60000, 60000);
		//Log.debug("log.pay","WEB >> PWN  ["+CommonUtil.toString(encrypt)+"]",this);
		try{
			decrypt = socket.connect3(4, encrypt);
			//Log.debug("log.pay","WEB << PWN  ["+CommonUtil.toString(decrypt)+"]",this);
			decrypt = cipher.decrypt(decrypt);
		}catch(Exception e){
			Log.debug("log.pay","COMMUNICATION ERROR ="+e.getMessage(),this);
			HeaderBean headerBean = new HeaderBean(request);
			headerBean.setResultCd("1");
			headerBean.setResultMsg("X103");
			headerBean.setExtra(e.getMessage());
			decrypt = headerBean.getTransaction(CommonUtil.toString(request,200,request.length-200)).getBytes();
		}
		return decrypt;
	}
	
	
	
	public List<TrnsctnBean> getList(){
		String query = "SELECT TRANSACTION_ID, MERCHANT_ID  , MALL_ID , SERVICE_TYPE,PAY_NO FROM VW_AUTH "
			         + " WHERE  TRN_DATE >= TO_CHAR(SYSDATE-1,'YYYYMMDD') AND TRN_STATUS ='02' AND TRN_RES_DATE < SYSDATE - 1/10/24/6 ORDER BY TRANSACTION_ID ASC ";
		
		
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
				
				trnsctnBean.setTransactionId(rset.getString("TRANSACTION_ID"));
				trnsctnBean.setMerchantId(rset.getString("MERCHANT_ID"));
				trnsctnBean.setMallId(rset.getString("MALL_ID"));
				trnsctnBean.setServiceType(rset.getString("SERVICE_TYPE"));
				trnsctnBean.setPayNo(rset.getString("PAY_NO"));
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
}

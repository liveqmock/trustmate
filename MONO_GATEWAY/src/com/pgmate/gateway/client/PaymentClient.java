/* 
 * Project      : MONO_GATEWAY
 * File Name    : com.pgmate.gateway.client.PaymentClient.java
 * Date	        : Dec 22, 2008
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.gateway.client;

import biz.trustnet.common.comm.client.SocketManager;
import biz.trustnet.common.util.BeanUtil;
import biz.trustnet.common.util.CommonUtil;
import biz.trustnet.common.xml.XMLFactory;

import com.pgmate.gateway.server.conf.ServerConfigBean;
import com.pgmate.model.comm.HeaderBean;
import com.pgmate.model.comm.T001Bean;
import com.pgmate.model.comm.T002Bean;
import com.pgmate.model.comm.T003Bean;

public class PaymentClient {

	private ServerConfigBean configBean = null;
	
	public PaymentClient(){
		configBean = (ServerConfigBean)XMLFactory.getEntity("PWN_GATEWAY");
	}
	
	public void credit(){
		HeaderBean headerBean = getHeader("T001");
		T001Bean t001Bean = new T001Bean();
		t001Bean.setCardNumber("4444555566667777");
		t001Bean.setCardExpire("1109");
		t001Bean.setPayUserId("PAYUSERID");
		t001Bean.setPayName("PAYUSERNAME");
		t001Bean.setPayTelNo("PAYTELNUMBER");
		t001Bean.setPayEmail("PAYUSEREMAIL");
		t001Bean.setProductType("1");
		t001Bean.setProductName("PRODUCTNAME");
		t001Bean.setAmount("50000");
		t001Bean.setCurType("USD");
		byte[] response = connect((headerBean.getTransaction(t001Bean.getTransaction())).getBytes());
		headerBean = new HeaderBean(response);
		t001Bean = new T001Bean(CommonUtil.toString(response,200,response.length-200));
		System.out.println("HEADER ="+BeanUtil.beanToString(headerBean));
		System.out.println("T001 ="+BeanUtil.beanToString(t001Bean));
	}
	
	public void voidRequest(){
		HeaderBean headerBean = getHeader("T002");
		T002Bean t002Bean = new T002Bean();
		t002Bean.setVoidType("1");
		t002Bean.setApprovalDay("20081224");
		byte[] response = connect((headerBean.getTransaction(t002Bean.getTransaction())).getBytes());
		headerBean = new HeaderBean(response);
		t002Bean = new T002Bean(CommonUtil.toString(response,200,response.length-200));
		System.out.println("HEADER ="+BeanUtil.beanToString(headerBean));
		System.out.println("T002 ="+BeanUtil.beanToString(t002Bean));
	}
	
	public void searchRequest(){
		HeaderBean headerBean = getHeader("T003");
		T003Bean t003Bean = new T003Bean();
		t003Bean.setRPayNo("PANWORLD_"+CommonUtil.getCurrentDate("yyyyMMddHHmmssSSS"));
		byte[] response = connect((headerBean.getTransaction(t003Bean.getTransaction())).getBytes());
		headerBean = new HeaderBean(response);
		t003Bean = new T003Bean(CommonUtil.toString(response,200));
		System.out.println("HEADER ="+BeanUtil.beanToString(headerBean));
		System.out.println("T003 ="+BeanUtil.beanToString(t003Bean));
	}
	
	
	public HeaderBean getHeader(String trnType){
		HeaderBean headerBean = new HeaderBean();
		headerBean.setSpecType("CFIX");
		headerBean.setTrnType(trnType);
		headerBean.setMerchantId("unaico");
		headerBean.setMallId("unaico");
		headerBean.setServiceType("WEB");
		headerBean.setIpAddress("192.168.10.1");
		headerBean.setTrnDate(CommonUtil.getCurrentDate("yyyyMMddHHmmss"));
		headerBean.setPayNo("PANWORLD_"+CommonUtil.getCurrentDate("yyyyMMddHHmmssSSS"));
		return headerBean;
	}
	
	
	
	public byte[] connect(byte[] request){
		byte[] response = null;
		SocketManager socket = new SocketManager();
		socket.setSocketInfo("service.panworld-net.com","19999");
		System.out.println("C>>GATEWAY =["+CommonUtil.toString(request)+"]");
		try{
			response = (socket.connect3(4, request));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			System.out.println("C<<GATEWAY =["+CommonUtil.toString(response)+"]");
		}
		
		return response;
	}
	
	public static void main(String[] args){
		com.pgmate.gateway.client.PaymentClient p = new com.pgmate.gateway.client.PaymentClient();
		if(args[0].equals("CREDIT")){
			p.credit();
		}else if(args[0].equals("VOID")){
			p.voidRequest();
		}else if(args[0].equals("SEARCH")){
			p.searchRequest();
		}else{
			System.out.println("CREDIT,VOID,SEARCH");
		}
	}
	
}

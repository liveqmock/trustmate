/* 
 * Project Name : GSI_PROJECT
 * Project      : GSI_PAYMENT
 * File Name    : com.pgmate.payment.util.GalaxiaCode.java
 * Date	        : Jan 27, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.payment.util;

import biz.trustnet.common.log.Log;

public class GalaxiaCode {

	public String getCode(String code){
		
		String resCode = "";
		
		if(code == null || code.equals("")){
			resCode="VAN4";
		}else{
			code = code.replaceAll("-", "");
		}
		
		if(code.equals("0001")){
			resCode="8418";
		}else if(code.equals("0002")){
			resCode="2002";
		}else if(code.equals("0003")){
			resCode="P106";
		}else if(code.equals("0004")){
			resCode="8314";
		}else if(code.equals("0005")){
			resCode="8393";
		}else if(code.equals("0006")){
			resCode="8032";
		}else if(code.equals("0007") || code.equals("0008") ||code.equals("0030") ||code.equals("0044")){
			resCode="7979";
		}else if(code.equals("0009")){
			resCode="8120";
		}else if(code.equals("0010")){
			resCode="8380";
		}else if(code.equals("0011") || code.equals("0012")){
			resCode="8417";
		}else if(code.equals("0013")){
			resCode="8006";
		}else if(code.equals("0014")){
			resCode="P108";
		}else if(code.equals("0015")){
			resCode="8328";
		}else if(code.equals("0016")){
			resCode="8327";
		}else if(code.equals("0019")){
			resCode="6003";
		}else if(code.equals("0020")){
			resCode="VAN4";
		}else if(code.equals("0021") ){
			resCode="6007";
		}else if(code.equals("0022")){
			resCode="P10G";
		}else if(code.equals("0023")){
			resCode="12";
		}else if(code.equals("0024") ){
			resCode="7003";
		}else if(code.equals("0025") ){
			resCode="7001";
		}else if(code.equals("0026") ){
			resCode="8350";
		}else if(code.equals("0032")){
			resCode="8324";
		}else if(code.equals("0033") || code.equals("0042") || code.equals("0043")){
			resCode="8373";
		}else if(code.equals("0041") ){
			resCode="7001";
		}else if(code.equals("0046")){
			resCode="00ZM";
		}else if(code.equals("0047") ){
			resCode="P109";
		}else if(code.equals("0051")){
			resCode="00ZG";
		}else if(code.equals("0052")){
			resCode="0801";
		}else if(code.equals("0110")){
			resCode="P1D1";
		}else if(code.equals("9004")){
			resCode="7002";
		}else if(code.equals("7979") || code.equals("9091")){
			resCode="8418";
		}else if(code.equals("9092")){
			resCode="P002";
		}else if(code.equals("9093")){
			resCode="8326";
		}else if(code.equals("9094")){
			resCode="8328";
		}else{
			resCode=code;
		}
		
		Log.debug("log.day","Galaxia Code=["+code+"] GSI Code=["+resCode+"]" ,this);
		
		return resCode;
	}
}

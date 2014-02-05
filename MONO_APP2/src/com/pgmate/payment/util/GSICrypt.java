/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.crypt.GSICrypt.java
 * Date	        : Dec 23, 2008
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.payment.util;

import biz.trustnet.common.cipher.Base64;
import biz.trustnet.common.cipher.SymmetricCipher;
import biz.trustnet.common.util.CommonUtil;

public class GSICrypt {
	
	private final static String DEFAULT_KEY	=  "PANWORLDNET";
	private final static int KEY_SIZE		= 16;
	
	public GSICrypt(){
		
	}
	
	public String encrypt(String str){
		String key = CommonUtil.byteFiller(DEFAULT_KEY,KEY_SIZE);
		return CommonUtil.toString(Base64.base64Encode(SymmetricCipher.SEED_CBC_ENCRYPT(str.getBytes(),key.getBytes())));
	}
	
	public String decrypt(String str){
		String key = CommonUtil.byteFiller(DEFAULT_KEY,KEY_SIZE);
		return CommonUtil.toString(SymmetricCipher.SEED_CBC_DECRYPT(Base64.base64Decode(str.getBytes()),key.getBytes()));
	}
	
	public String hideCardNumberDecrypt(String str){
		String key = CommonUtil.byteFiller(DEFAULT_KEY,KEY_SIZE);
		str = CommonUtil.toString(SymmetricCipher.SEED_CBC_DECRYPT(Base64.base64Decode(str.getBytes()),key.getBytes()));
		if(str.length() == 16){
			return str.substring(0,4)+"XXXXXXXX"+str.substring(12,16);
		}else if(str.length() == 15){
			return str.substring(0,4)+"XXXXXXXX"+str.substring(11,15);
		}else if(str.length() == 14){
			return str.substring(0,4)+"XXXXXXXX"+str.substring(10,14);
		}else{
			return str;
		}
	}
}

/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.payment.util.GSICipher.java
 * Date	        : Jan 13, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.payment.util;

import biz.trustnet.common.cipher.Base64;
import biz.trustnet.common.cipher.MDEncoder;
import biz.trustnet.common.cipher.SymmetricCipher;
import biz.trustnet.common.util.CommonUtil;

public class GSICipher {

	public GSICipher(){
		
	}
	
	public byte[] encrypt(byte[] plain){
		byte[] text = CommonUtil.toString(plain,4,plain.length-4).getBytes();
		byte[] eText = Base64.base64Encode(SymmetricCipher.SEED_ECB_ENCRYPT(text));
		return CommonUtil.byteAppend(CommonUtil.zerofill(eText.length,4).getBytes(),eText);
	}
	
	public byte[] decrypt(byte[] eText){
		byte[] decrypt = CommonUtil.toString(eText,4,eText.length-4).getBytes();
		byte[] plain = SymmetricCipher.SEED_ECB_DECRYPT(Base64.base64Decode(decrypt));
		return CommonUtil.byteAppend(CommonUtil.zerofill(plain.length,4).getBytes(),plain);
	}
	
	public static void main(String[] args){
		com.pgmate.payment.util.GSICipher p = new com.pgmate.payment.util.GSICipher();
		System.out.println(new MDEncoder().encodeSHA1("25461"));
		System.out.println(new String(p.encrypt("25461".getBytes())));
	}
}

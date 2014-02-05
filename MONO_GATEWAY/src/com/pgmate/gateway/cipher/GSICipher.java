/* 
 * Project      : MONO_GATEWAY
 * File Name    : com.pgmate.gateway.cipher.GSICipher.java
 * Date	        : Dec 22, 2008
 * Version      : 1.0
 * Author       : ginaida@trustmate.net
 * Comment      :  
 */

package com.pgmate.gateway.cipher;

import biz.trustnet.common.cipher.Base64;
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
}

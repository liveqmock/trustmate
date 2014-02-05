package com.pgmate.payment.inicis;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;
import biz.trustnet.common.xml.XMLFactory;

import com.inicis.inipay4.INIpay;
import com.inicis.inipay4.util.INIdata;
import com.pgmate.model.comm.HeaderBean;
import com.pgmate.model.comm.T001Bean;
import com.pgmate.model.comm.T004Bean;
import com.pgmate.model.db.MerchantMngBean;

/**
 * @author Administrator
 *
 */
public class INICISCredit {

	
	
	public static INICISConfigBean configBean = null;
	
	private INIpay inipay = null;
	private INIdata data = null;
	
	public INICISCredit(){
		inipay = new INIpay();
		data = new INIdata();
		if(configBean == null){
			configBean = (INICISConfigBean)XMLFactory.getEntity("INICIS");
		}
	}
	
	public INIdata execute(HeaderBean headerBean,T001Bean tBean,MerchantMngBean merchantMngBean){
		
		try{
			
			data.setData("type", "formpay");                                  	// 결제 type
			data.setData("inipayHome", configBean.getHomeDirectory());        	// 이니페이가 설치된 절대경로
			data.setData("logMode", "INFO");                                 	// logMode
			data.setData("keyPW","1111");                                     	// 키패스워드
			data.setData("subPgip","112.175.48.41");                           	// Sub PG IP (고정)
			data.setData("mid", merchantMngBean.getVanId());  				    // 상점아이디
			data.setData("uid", merchantMngBean.getVanId() );         		  	// INIpay User ID
			data.setData("goodname", tBean.getProductName());      		       	// 상품명 (최대 40자)
			if(merchantMngBean.getCurType().equals("KRW")){
				data.setData("currency", "WON");						       	// 화폐단위
			}else {
				data.setData("currency", merchantMngBean.getCurType());
			}
			data.setData("merchantreserved3","below1000=1");   
			data.setData("price",CommonUtil.toString(CommonUtil.parseLong(tBean.getAmount())));  	// 가격
			
			if(tBean.getPayName().equals("")){
				data.setData("buyername", "AUTHENTICATION"); 		    		// 구매자 (최대 15자)
			}else{
				data.setData("buyername", tBean.getPayName());     					// 구매자 (최대 15자)
			}
			if(tBean.getPayTelNo().equals("")){
				data.setData("buyertel", "01071641016"); 	      				// 구매자이동전화
			}else{
				data.setData("buyertel", tBean.getPayTelNo());       				// 구매자이동전화
			}
			data.setData("buyeremail", "support@trustmate.net"); 	  				// 구매자이동전화
			
			
			data.setData("paymethod", "Card");     								// 지불방법
			if(tBean.getDomain().trim().equals("")){
				data.setData("url", "service.pgmate.com");              				// 홈페이지 주소(URL)
			}else{
				if(merchantMngBean.getMerchantId().equals("rablon")){
					data.setData("url", "service.pgmate.com");              				// 홈페이지 주소(URL)
				}else{
					data.setData("url", tBean.getDomain());              				// 홈페이지 주소(URL)
				}
			}
			data.setData("uip", headerBean.getIpAddress());                     // IP Addr

			data.setData("cardnumber", tBean.getCardNumber());
			data.setData("cardexpy",  tBean.getCardExpire().substring(0,2));
			data.setData("cardexpm", tBean.getCardExpire().substring(2,4));
			data.setData("cardquota", "00");
			data.setData("authentification", "11");								//본인인증여부 시행 00 시행안함 11
			data.setData("authField1", "");										//인증값1 주민번호
			data.setData("authField2", "");										//인증값2 카드비밀번호
			data.setData("authField3", "");										//인증값3
			data.setData("passwd", "");											//패스워드
			data.setData("quotaInterest", "0");									//무이자 여부 무이자-1 , 일반 -0
			Log.debug("log.day","INIPAY START ACTION",this);
			data = inipay.payRequest(data);
			Log.debug("log.day","INIPAY END ACTION",this);
			if ("00".equals(data.getData("ResultCode"))){
			    if (inipay.payAck()){
			        // DB 처리
			    }
			}else{
				
			}
			
			
			Log.debug("log.day","INI ResultCode="+data.getData("ResultCode"),this);				//승인 결과 코드 00:성공 01:실패
			Log.debug("log.day","INI ResultMsg="+data.getData("ResultMsg"),this);				//승인결과 메세지
			Log.debug("log.day","INI paymethod="+data.getData("paymethod"),this);				//결제 지불방법
			Log.debug("log.day","INI tid="+data.getData("tid"),this);							//결과 TID
			Log.debug("log.day","INI CardAuthCode="+data.getData("CardAuthCode"),this);			//카드 승인 번호
			Log.debug("log.day","INI CardResultQuota="+data.getData("CardResultQuota"),this);	//카드 할부 개월
			Log.debug("log.day","INI CardResultCode="+data.getData("CardResultCode"),this);		//신용카드 종류
			Log.debug("log.day","INI Detailcode="+data.getData("Detailcode"),this);				//발급사코드
			Log.debug("log.day","INI PGauthdate="+data.getData("PGauthdate"),this);				//PG승인 일자
			Log.debug("log.day","INI PGauthtime="+data.getData("PGauthtime"),this);				//PG승인 시간
			Log.debug("log.day","INI CardAuthCode="+data.getData("CardAuthCode"),this);			//신용카드 승인 번호
			Log.debug("log.day","INI PGAuthCode="+data.getData("PGAuthCode"),this);				//신용카드 승인 번호 
			
			
		}catch(Exception e){
			Log.debug("log.day",CommonUtil.getExceptionMessage(e),this);
			data.setData("ResultCode", "XX");
		}	
	    return data;
	}
	
	public INIdata execute(MerchantMngBean merchantMngBean,String vanTransactionId,String vanId){
		
		try{
			
			data.setData("type", "cancel");                                  	// 결제 type
			data.setData("inipayHome", configBean.getHomeDirectory());        	// 이니페이가 설치된 절대경로
			data.setData("logMode", "INFO");                                 	// logMode
			data.setData("keyPW","1111");                                     	// 키패스워드
			data.setData("subPgip","112.175.48.41");                           	// Sub PG IP (고정)
			if(vanId.equals(merchantMngBean.getVanId())){
				data.setData("mid", merchantMngBean.getVanId());	              	// 상점아이디
				data.setData("uid", merchantMngBean.getVanId());	  		 	  	// INIpay User ID
			}else{
				data.setData("mid", vanId);	              	// 상점아이디
				data.setData("uid", vanId);	  		 	  	// INIpay User ID
			}
			
			
			data.setData("tid", vanTransactionId);      		       			// INICIS 거래번호
			data.setData("cancelMsg","");
			Log.debug("log.day","INIPAY START ACTION",this);
			data = inipay.payRequest(data);
			Log.debug("log.day","INIPAY END ACTION",this);
			if ("00".equals(data.getData("ResultCode"))){
			    if (inipay.payAck()){
			        // DB 처리
			    }
			}else{
				
			}
			
			
			Log.debug("log.day","INI ResultCode="+data.getData("ResultCode"),this);				//승인 결과 코드 00:성공 01:실패
			Log.debug("log.day","INI ResultMsg="+data.getData("ResultMsg"),this);				//승인결과 메세지
			Log.debug("log.day","INI paymethod="+data.getData("paymethod"),this);				//결제 지불방법
			Log.debug("log.day","INI tid="+data.getData("tid"),this);							//결과 TID
			Log.debug("log.day","INI PGauthdate="+data.getData("PGcanceldate"),this);			//PG승인 일자
			Log.debug("log.day","INI PGauthtime="+data.getData("PGcanceltime"),this);			//PG승인 시간
			Log.debug("log.day","INI CardAuthCode="+data.getData("CardAuthCode"),this);			//신용카드 승인 번호
			Log.debug("log.day","INI PGAuthCode="+data.getData("PGAuthCode"),this);				//신용카드 승인 번호 
			
			
		}catch(Exception e){
			Log.debug("log.day",CommonUtil.getExceptionMessage(e),this);
			data.setData("ResultCode", "XX");
		}	
		return data;
	}
	
	public INIdata execute(HeaderBean headerBean,T004Bean tBean,MerchantMngBean merchantMngBean,long amount){
		
		try{
			
			data.setData("type", "formpay");                                  	// 결제 type
			data.setData("inipayHome", configBean.getHomeDirectory());        	// 이니페이가 설치된 절대경로
			data.setData("logMode", "INFO");                                 	// logMode
			data.setData("keyPW","1111");                                     	// 키패스워드
			data.setData("subPgip","112.175.48.41");                           	// Sub PG IP (고정)
			data.setData("mid", merchantMngBean.getVanId());  				    // 상점아이디
			data.setData("uid", merchantMngBean.getVanId() );         		  	// INIpay User ID
			data.setData("goodname", "AUTH"); 				     		       	// 상품명 (최대 40자)
			data.setData("currency","USD");
			data.setData("merchantreserved3","below1000=1");   
			data.setData("price",CommonUtil.toString(amount));  				// 가격
			if(tBean.getPayName().equals("")){
				data.setData("buyername", "AUTHENTICATION"); 		    		// 구매자 (최대 15자)
			}else{
				data.setData("buyername", tBean.getPayName());     			 	// 구매자 (최대 15자)
			}
			if(tBean.getPayTelNo().equals("")){
				data.setData("buyertel", "01071641016"); 	      				// 구매자이동전화
			}else{
				data.setData("buyertel", tBean.getPayTelNo());       				// 구매자이동전화
			}
			
			data.setData("buyeremail", "support@trustmate.net"); 				// 구매자이메일
			data.setData("paymethod", "Card");     								// 지불방법
			data.setData("url", "http://neograde.com");          					// 홈페이지 주소(URL)
			data.setData("uip", headerBean.getIpAddress());                     // IP Addr

			data.setData("cardnumber", tBean.getCardNumber());
			data.setData("cardexpy",  tBean.getCardExpire().substring(0,2));
			data.setData("cardexpm", tBean.getCardExpire().substring(2,4));
			data.setData("cardquota", "00");
			data.setData("authentification", "11");								//본인인증여부 시행 00 시행안함 11
			data.setData("authField1", "");										//인증값1 주민번호
			data.setData("authField2", "");										//인증값2 카드비밀번호
			data.setData("authField3", "");										//인증값3
			data.setData("passwd", "");											//패스워드
			data.setData("quotaInterest", "0");									//무이자 여부 무이자-1 , 일반 -0
			Log.debug("log.day","INIPAY START ACTION",this);
			data = inipay.payRequest(data);
			Log.debug("log.day","INIPAY END ACTION",this);
			if ("00".equals(data.getData("ResultCode"))){
			    if (inipay.payAck()){ 
			        // DB 처리
			    }
			}else{
				
			}
			
			
			Log.debug("log.day","INI ResultCode="+data.getData("ResultCode"),this);				//승인 결과 코드 00:성공 01:실패
			Log.debug("log.day","INI ResultMsg="+data.getData("ResultMsg"),this);				//승인결과 메세지
			Log.debug("log.day","INI paymethod="+data.getData("paymethod"),this);				//결제 지불방법
			Log.debug("log.day","INI tid="+data.getData("tid"),this);							//결과 TID
			Log.debug("log.day","INI CardAuthCode="+data.getData("CardAuthCode"),this);			//카드 승인 번호
			Log.debug("log.day","INI CardResultQuota="+data.getData("CardResultQuota"),this);	//카드 할부 개월
			Log.debug("log.day","INI CardResultCode="+data.getData("CardResultCode"),this);		//신용카드 종류
			Log.debug("log.day","INI Detailcode="+data.getData("Detailcode"),this);				//발급사코드
			Log.debug("log.day","INI PGauthdate="+data.getData("PGauthdate"),this);				//PG승인 일자
			Log.debug("log.day","INI PGauthtime="+data.getData("PGauthtime"),this);				//PG승인 시간
			Log.debug("log.day","INI CardAuthCode="+data.getData("CardAuthCode"),this);			//신용카드 승인 번호
			Log.debug("log.day","INI PGAuthCode="+data.getData("PGAuthCode"),this);				//신용카드 승인 번호 
			
			
		}catch(Exception e){
			Log.debug("log.day",CommonUtil.getExceptionMessage(e),this);
			data.setData("ResultCode", "XX");
		}	
	    return data;
	}
}

package com.pgmate.payment.kcp;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;
import biz.trustnet.common.util.Property;
import biz.trustnet.common.xml.XMLFactory;

import com.kcp.C_PP_CLI;
import com.pgmate.model.comm.HeaderBean;
import com.pgmate.model.comm.T001Bean;
import com.pgmate.model.db.MerchantMngBean;
import com.pgmate.model.db.TrnsctnDMBean;

/**
 * @author Administrator
 *
 */
public class KCPCredit {

	
	
	public static KCPConfigBean configBean = null;
	private static HashMap<String,Property> hashMap = new HashMap<String,Property>();

	private C_PP_CLI  kcp = null;
	private String tx_cd = "";

	
	public KCPCredit(){
		//KCP 인스턴스 생성 및 초기화 
		kcp = new C_PP_CLI();
		if(configBean == null){
			configBean = (KCPConfigBean)XMLFactory.getEntity("KCP");
		}
		kcp.mf_init(configBean.getHomeDirectory(), configBean.getUrl(),configBean.getPort(), CommonUtil.parseInt(configBean.getTxMode()));
		kcp.mf_init_set();
	}
	
	public Property getProp(String vanId){
		if(hashMap.containsKey(vanId)){
			return (Property)hashMap.get(vanId);
		}else{
			Property prop = new Property(configBean.getKeyDirectory()+File.separator+vanId+".properties");
			hashMap.put(vanId, prop);
			return prop;
		}
	}
	
	
	
	
	
	public C_PP_CLI executeDM(HeaderBean headerBean,T001Bean tBean,MerchantMngBean merchantMngBean,HashMap<String,String> hashMap){
		
		try{
			Log.debug("log.day","DM CERT",this);
			Property prop = getProp(merchantMngBean.getVanId());
			tx_cd = "00100720";
			
			int payx_data_set;
			int common_data_set;
			
			
			payx_data_set   = kcp.mf_add_set("payx_data");
            common_data_set = kcp.mf_add_set("common");
            
			kcp.mf_set_us(common_data_set, "amount",CommonUtil.toString(CommonUtil.parseLong(tBean.getAmount())) );	//금액
			if(merchantMngBean.getCurType().equals("WON")){						//원화
				kcp.mf_set_us( common_data_set,"currency", "410");
			}else if(merchantMngBean.getCurType().equals("USD")){				//USD
				kcp.mf_set_us( common_data_set,"currency", "840");
			}else if(merchantMngBean.getCurType().equals("EUR")){				//EUR
				kcp.mf_set_us( common_data_set,"currency", "978");
			}else if(merchantMngBean.getCurType().equals("JPY")){				//JPY
				kcp.mf_set_us( common_data_set,"currency", "392");
			}else if(merchantMngBean.getCurType().equals("CNY")){				//CNY
				kcp.mf_set_us( common_data_set,"currency", "156");
			}else{
				kcp.mf_set_us( common_data_set,"currency", "");
			}
			
	        kcp.mf_set_us( common_data_set, "cust_ip",headerBean.getIpAddress());//고객 아이피
            kcp.mf_add_rs( payx_data_set, common_data_set );

	        
            int cert_data_set;

            cert_data_set = kcp.mf_add_set( "cert" );
            kcp.mf_set_us( cert_data_set, "tx_type","07200100");	//거래번호
            kcp.mf_set_us(cert_data_set, "session_key", hashMap.get("ekey"));
            kcp.mf_set_us(cert_data_set, "session_hash", hashMap.get("ehash"));
            kcp.mf_set_us( cert_data_set, "card_no",      tBean.getCardNumber());		//카드번호
            kcp.mf_set_us( cert_data_set, "card_expiry",  tBean.getCardExpire());       //유효기간
            kcp.mf_add_rs( payx_data_set, cert_data_set );
            
            //	 주문 정보
            int ordr_data_set;

    		ordr_data_set = kcp.mf_add_set( "ordr_data" );
            kcp.mf_set_us( ordr_data_set, "ordr_idxx",headerBean.getTransactionId());	//거래번호 
            kcp.mf_set_us( cert_data_set, "good_name",tBean.getProductName());
            if(CommonUtil.isNullOrSpace(tBean.getForeName())){
            	kcp.mf_set_us( ordr_data_set, "buyr_name_first" , "John");
            }else{
            	kcp.mf_set_us( ordr_data_set, "buyr_name_first" , tBean.getForeName());
            }
            if(CommonUtil.isNullOrSpace(tBean.getSurName())){
            	kcp.mf_set_us( ordr_data_set, "buyr_name_last" , "Doe");
            }else{
            	kcp.mf_set_us( ordr_data_set, "buyr_name_last" , tBean.getSurName());
            }
            
            if(CommonUtil.isNullOrSpace(tBean.getAddr1())){
            	kcp.mf_set_us( ordr_data_set, "buyr_add1" , "1295 Charleston Road");
            }else{
            	kcp.mf_set_us( ordr_data_set, "buyr_add1" , tBean.getAddr1());
            }
            
            if(CommonUtil.isNullOrSpace(tBean.getAddr2())){
            	kcp.mf_set_us( ordr_data_set, "buyr_add2" , "addr2");
            }else{
            	kcp.mf_set_us( ordr_data_set, "buyr_add2" , tBean.getAddr2());
            }
            
            if(CommonUtil.isNullOrSpace(tBean.getCity())){
            	kcp.mf_set_us( ordr_data_set, "buyr_city" , "Mountain View");
            }else{
            	kcp.mf_set_us( ordr_data_set, "buyr_city" , tBean.getCity());
            }
            
            if(CommonUtil.isNullOrSpace(tBean.getState())){
            	kcp.mf_set_us( ordr_data_set, "buyr_state" , "CA");
            }else{
            	kcp.mf_set_us( ordr_data_set, "buyr_state" , tBean.getState());
            }
            
            if(CommonUtil.isNullOrSpace(tBean.getCountry())){
            	kcp.mf_set_us( ordr_data_set, "buyr_country" , "US");
            }else{
            	kcp.mf_set_us( ordr_data_set, "buyr_country" , tBean.getCountry());
            }
            
            if(CommonUtil.isNullOrSpace(tBean.getZip())){
            	kcp.mf_set_us( ordr_data_set, "buyr_zipx" , "94043");
            }else{
            	kcp.mf_set_us( ordr_data_set, "buyr_zipx" , tBean.getZip());
            }
            
            kcp.mf_set_us( ordr_data_set, "buyr_mail"  	  	, tBean.getPayEmail() 		);
            kcp.mf_set_us( ordr_data_set, "buyr_tel1" 	  	, tBean.getPayTelNo() 		);
            
            // 배송 정보 
            int rcvr_data_set;

    		rcvr_data_set = kcp.mf_add_set( "rcvr_data" );
    			
    		kcp.mf_set_us( rcvr_data_set, "rcvr_name_first" , tBean.getPayName() );
    		kcp.mf_set_us( rcvr_data_set, "rcvr_name_last"  , tBean.getPayName()  );
    		kcp.mf_set_us( rcvr_data_set, "rcvr_tel1"		, tBean.getPayTelNo()       );
    		kcp.mf_set_us( rcvr_data_set, "rcvr_add1"		, ""      );
    		kcp.mf_set_us( rcvr_data_set, "rcvr_add2"		, ""      );    
    		kcp.mf_set_us( rcvr_data_set, "rcvr_zipx"   	, ""       );							
    		kcp.mf_set_us( rcvr_data_set, "rcvr_city"		, ""       );
    		kcp.mf_set_us( rcvr_data_set, "rcvr_state"	    , ""      );
    		kcp.mf_set_us( rcvr_data_set, "rcvr_country"	, ""    );
            
         
			kcp.mf_do_tx( prop.getProperties("siteCd"),  prop.getProperties("siteKey"), tx_cd, headerBean.getIpAddress(), headerBean.getTransactionId(), configBean.getLogLevel(), "0" );
            
			Log.debug("log.day","Decision Manager Result",this);								//승인 결과 코드 0000
			Log.debug("log.day","KCP ResultCd="+kcp.m_res_cd,this);								//승인 결과 코드 0000
			Log.debug("log.day","KCP ResultMsg="+E2U(kcp.m_res_msg),this);						//승인결과 메세지
			Log.debug("log.day","KCP decision="+kcp.mf_get_res("decision"),this);				//DECISION 
			Log.debug("log.day","KCP res_host_cd="+kcp.mf_get_res("res_host_cd"),this);			//응답코드
			Log.debug("log.day","KCP res_host_msg="+kcp.mf_get_res("res_host_msg"),this);		//응답메세지
			Log.debug("log.day","KCP res_host_en_msg="+kcp.mf_get_res("res_host_en_msg"),this);	//응답영문메세지
			Log.debug("log.day","KCP cert_dm_no="+kcp.mf_get_res("cert_dm_no"),this);			//인증번호
			
			
		}catch(Exception e){
			Log.debug("log.day",CommonUtil.getExceptionMessage(e),this);
			kcp.m_res_cd = "9502";
			kcp.m_res_msg= "결제 연동 오류 "+e.getMessage();
		}	
	    return kcp;
	}
	
	
	public C_PP_CLI executeDMAction(HeaderBean headerBean,T001Bean tBean,MerchantMngBean merchantMngBean,String certDmNo,String actionCd,String actionDesc,String modId){
		
		try{
			Log.debug("log.day","DM CERT ACTION",this);
			Property prop = getProp(merchantMngBean.getVanId());
			tx_cd = "00100720";
			
			int payx_data_set;
			int common_data_set;
			
			
			payx_data_set   = kcp.mf_add_set("payx_data");
            common_data_set = kcp.mf_add_set("common");
            
            kcp.mf_set_us( common_data_set, "cust_ip",headerBean.getIpAddress());//고객 아이피
			kcp.mf_set_us(common_data_set, "amount","0");	//금액
			kcp.mf_add_rs( payx_data_set, common_data_set );
			
           

	        
            int cert_data_set;

            cert_data_set = kcp.mf_add_set( "cert" );
            kcp.mf_set_us( cert_data_set, "tx_type","07200200");		//거래번호
            kcp.mf_set_us(cert_data_set, "cert_dm_no", certDmNo);		//확인 번호
            kcp.mf_set_us(cert_data_set, "action_cd", actionCd);		//결정 ACCEPT,REJECT
            kcp.mf_set_us( cert_data_set, "action_desc", actionDesc);	//설명
            kcp.mf_set_us( cert_data_set, "mod_id",  modId);       		//편집자명
            kcp.mf_add_rs( payx_data_set, cert_data_set );
            
            Log.debug("log.day","Decision Manager Action Result",this);								//승인 결과 코드 0000
			Log.debug("log.day","KCP ResultCd="+kcp.m_res_cd,this);								//승인 결과 코드 0000
			Log.debug("log.day","KCP ResultMsg="+E2U(kcp.m_res_msg),this);					//승인결과 메세지
			Log.debug("log.day","KCP decision="+kcp.mf_get_res("decision"),this);				//DECISION 
			Log.debug("log.day","KCP res_host_cd="+kcp.mf_get_res("res_host_cd"),this);			//응답코드
			Log.debug("log.day","KCP res_host_msg="+kcp.mf_get_res("res_host_msg"),this);		//응답메세지
			Log.debug("log.day","KCP res_host_en_msg="+kcp.mf_get_res("res_host_en_msg"),this);	//응답영문메세지
			Log.debug("log.day","KCP cert_dm_no="+kcp.mf_get_res("cert_dm_no"),this);			//인증번호
			
			
		}catch(Exception e){
			Log.debug("log.day",CommonUtil.getExceptionMessage(e),this);
			kcp.m_res_cd = "9502";
			kcp.m_res_msg= "결제 연동 오류 "+e.getMessage();
		}	
	    return kcp;
	}
	
	public C_PP_CLI execute(HeaderBean headerBean,T001Bean tBean,MerchantMngBean merchantMngBean,TrnsctnDMBean trnsctnDMBean){
		
		try{
			
			Property prop = getProp(merchantMngBean.getVanId());
			tx_cd = "00100000";
			
			int payx_data_set;
			int common_data_set;
			
			
			payx_data_set   = kcp.mf_add_set("payx_data");
            common_data_set = kcp.mf_add_set("common");
            
			kcp.mf_set_us(common_data_set, "amount",CommonUtil.toString(CommonUtil.parseLong(tBean.getAmount())) );	//금액
			if(merchantMngBean.getCurType().equals("WON")){						//원화
				kcp.mf_set_us( common_data_set,"currency", "410");
			}else if(merchantMngBean.getCurType().equals("USD")){				//USD
				kcp.mf_set_us( common_data_set,"currency", "840");
			}else if(merchantMngBean.getCurType().equals("EUR")){				//EUR
				kcp.mf_set_us( common_data_set,"currency", "978");
			}else if(merchantMngBean.getCurType().equals("JPY")){				//JPY
				kcp.mf_set_us( common_data_set,"currency", "392");
			}else if(merchantMngBean.getCurType().equals("CNY")){				//CNY
				kcp.mf_set_us( common_data_set,"currency", "156");
			}else{
				kcp.mf_set_us( common_data_set,"currency", "");
			}
			
	        //kcp.mf_set_us( common_data_set, "soc_no",   soc_no  );			//주민 번호 등.
	            
	        kcp.mf_set_us( common_data_set, "cust_ip",headerBean.getIpAddress());//고객 아이피
	        kcp.mf_set_us( common_data_set, "escw_mod","N");					//ESCROW
            kcp.mf_add_rs( payx_data_set, common_data_set );

	        // 주문 정보
            int ordr_data_set;

            ordr_data_set = kcp.mf_add_set( "ordr_data" );

            kcp.mf_set_us( ordr_data_set, "ordr_idxx",headerBean.getTransactionId());	//거래번호 
            kcp.mf_set_us( ordr_data_set, "good_name",tBean.getProductName());
            kcp.mf_set_us( ordr_data_set, "good_mny", CommonUtil.toString(CommonUtil.parseLong(tBean.getAmount())) );
            kcp.mf_set_us( ordr_data_set, "buyr_name", tBean.getPayName() );
            if(tBean.getPayTelNo().equals("")){
				kcp.mf_set_us( ordr_data_set, "buyr_tel1", "01071641016");
			}else{
				kcp.mf_set_us( ordr_data_set, "buyr_tel1", tBean.getPayTelNo() );
			}
            kcp.mf_set_us( ordr_data_set, "buyr_tel2", "" );
            if(CommonUtil.isNullOrSpace(tBean.getPayEmail())){
            	kcp.mf_set_us( ordr_data_set, "buyr_mail", "support@trustmate.net" );
            }else{
            	kcp.mf_set_us( ordr_data_set, "buyr_mail", tBean.getPayEmail());
            }
            
            int card_data_set;

            card_data_set = kcp.mf_add_set( "card" );

            kcp.mf_set_us( card_data_set, "card_mny",     tBean.getAmount()   );        // 결제 금액             
            kcp.mf_set_us( card_data_set, "card_tx_type", "11111000" );
            kcp.mf_set_us( card_data_set, "quota",        "00" );						//할부기간
            kcp.mf_set_us( card_data_set, "card_no",      tBean.getCardNumber());		//카드번호
            kcp.mf_set_us( card_data_set, "card_expiry",  tBean.getCardExpire());       //유효기간
            if(trnsctnDMBean != null){
	            kcp.mf_set_us( card_data_set, "cert_dm_yn"	   , "Y"              ); 
	            kcp.mf_set_us( card_data_set, "cert_dm_no"	   , trnsctnDMBean.getDmNo()	  );  
	            kcp.mf_set_us( card_data_set, "cert_dm_decision" , trnsctnDMBean.getDecision() );
	            Log.debug("log.day","trnsctnDMBean check cert_dm_decision=["+trnsctnDMBean.getDecision()+"]",this);	
            }
            kcp.mf_add_rs( payx_data_set, card_data_set );
			
			
			kcp.mf_do_tx( prop.getProperties("siteCd"),  prop.getProperties("siteKey"), tx_cd, headerBean.getIpAddress(), headerBean.getTransactionId(), configBean.getLogLevel(), "0" );
            
			Log.debug("log.day","Payment Result",this);								//승인 결과 코드 0000
			Log.debug("log.day","KCP ResultCd="+kcp.m_res_cd,this);						//승인 결과 코드 00:성공 01:실패
			Log.debug("log.day","KCP ResultMsg="+E2U(kcp.m_res_msg),this);					//승인결과 메세지
			Log.debug("log.day","KCP tno="+kcp.mf_get_res("tno"),this);					//결제 결과 TID
			Log.debug("log.day","KCP 카드코드="+kcp.mf_get_res("card_cd"),this);			//카드코드
			Log.debug("log.day","KCP 카드종류="+kcp.mf_get_res("card_name"),this);		//카드종류
			Log.debug("log.day","KCP 승인시간="+kcp.mf_get_res("app_time"),this);		//승인시간
			Log.debug("log.day","KCP 승인번호="+kcp.mf_get_res("app_no"),this);			//승인번호 
			
			
		}catch(Exception e){
			Log.debug("log.day",CommonUtil.getExceptionMessage(e),this);
			kcp.m_res_cd = "9502";
			kcp.m_res_msg= "결제 연동 오류 "+e.getMessage();
		}	
	    return kcp;
	}
	
	public C_PP_CLI execute(MerchantMngBean merchantMngBean,String vanTransactionId,String vanId,String voidTransactionId){
		
		try{
			Property prop = getProp(vanId);
			tx_cd = "00200000";
			int     mod_data_set_no;
			mod_data_set_no = kcp.mf_add_set( "mod_data" );
			kcp.mf_set_us( mod_data_set_no, "tno",     vanTransactionId );        //거래번호             
	        kcp.mf_set_us( mod_data_set_no, "mod_type", "STSC" );
	        kcp.mf_set_us( mod_data_set_no, "mod_ip",   "112.175.48.41" );		  //취소 IP
	        kcp.mf_set_us( mod_data_set_no, "mod_desc", "고객 요청에 의한 취소");	  //카드번호
			
			
	        kcp.mf_do_tx( prop.getProperties("siteCd"),  prop.getProperties("siteKey"), tx_cd, "112.175.48.41", voidTransactionId, configBean.getLogLevel(), "0" );
            
	        Log.debug("log.day","Payment Refund Result",this);								//승인 결과 코드 0000
			Log.debug("log.day","KCP ResultCd="+kcp.m_res_cd,this);						//승인 결과 코드 00:성공 01:실패
			Log.debug("log.day","KCP ResultMsg="+E2U(kcp.m_res_msg),this);					//승인결과 메세지
			Log.debug("log.day","KCP tno="+kcp.mf_get_res("tno"),this);					//결제 결과 TID
			Log.debug("log.day","KCP 카드코드="+kcp.mf_get_res("card_cd"),this);			//카드코드
			Log.debug("log.day","KCP 카드종류="+kcp.mf_get_res("card_name"),this);		//카드종류
			Log.debug("log.day","KCP 승인시간="+kcp.mf_get_res("app_time"),this);		//승인시간
			Log.debug("log.day","KCP 승인번호="+kcp.mf_get_res("app_no"),this);			//승인번호 
		}catch(Exception e){
			Log.debug("log.day",CommonUtil.getExceptionMessage(e),this);
			kcp.m_res_cd = "9502";
			kcp.m_res_msg= "결제 연동 오류 "+e.getMessage();
		}	
		return kcp;
	}
	
	
	public String E2U(String s) {
		String s1 = null;
		try {
			s1 = new String(s.getBytes("EUC-KR"), "UTF-8");
		} catch (UnsupportedEncodingException unsupportedencodingexception) {} catch (NullPointerException nullpointerexception) {}
		return s1;
	}
	
	
}

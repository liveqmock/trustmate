package com.pgmate.payment.util;

import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

/**
 * @author Administrator
 *
 */
public class KcpDMUtil {

	private String url = "https://pay.kcp.co.kr/Pay/module/dm_hub/df_check.jsp?";
	private HashMap<String,String> hashMap = new HashMap<String,String>();
	
	
	public KcpDMUtil(){
	}
	
	
	public HashMap<String,String> getResult(){
		return hashMap;
	}
	
	
	public boolean getDM(String vanId,String transactionId){
		boolean dm =  false;

		String response = "";		
		Web w =new Web();
		String req = "site_cd="+vanId+"&ordr_idxx="+transactionId+"&rtn_url=0&rtn_method=NONE";
		w.setContentType("text/html");
		w.setFormAction(url+req,"GET");
		w.setInOut(true,true);
		try{
			
			Log.debug("log.day","DM REQ : "+req,this);
			response = w.connect(req);
			if(w.HTTP_CODE == 200){
				setMap(response);
				dm = true;
			}else{
				Log.debug("log.day","HTTP OCCURE ERROR CODE =["+w.HTTP_CODE +"] STATUS=["+w.HTTP_STATUS+w.HTTP_MSG.toString()+"]" ,this);
			}
			
		}catch(Exception e){
			Log.debug("log.day","DM HTTPS ERROR = "+e.getMessage(),this);
		}
	
		return dm;
	}
	
	
	public void setMap(String response){
		try{
			
			Document doc = Jsoup.parse(response);
			if(doc != null){
				setAttributeValue(doc,"ekey");
				setAttributeValue(doc,"ehash");
			}
		}catch(Exception e){
			Log.debug("log.day","JSEOUP PARSE ERROR = "+e.getMessage(),this);
		}
		
		
	}
	
	public void setAttributeValue(Document doc,String key){
		Element ele = doc.getElementById(key);
		if(ele != null){
			hashMap.put(key,trim(ele.attr("value")));
			Log.debug("log.day","DM KEY="+key+" VALUE="+(String)hashMap.get(key),this);
		}else{
			Log.debug("log.day","ELEMENT IS NULL = "+key,this);
		}
	}
	
	
	
	public static String trim(String s) {
	    int i = 0;
	    while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
	        i++;
	    }
	    return rtrim(s.substring(i));
	}

	public static String rtrim(String s) {
	    int i = s.length()-1;
	    while (i >= 0 && Character.isWhitespace(s.charAt(i))) {
	        i--;
	    }
	    return s.substring(0,i+1);
	}
	
	
	public static void main(String[] args){
		KcpDMUtil ex = new KcpDMUtil();
		ex.getDM("E5753",CommonUtil.zerofill(CommonUtil.generateRandomKey(),8));
	}
}

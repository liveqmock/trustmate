/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.web.util.ParamUtil.java
 * Date	        : Jan 15, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */


package com.pgmate.web.util;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;
import biz.trustnet.common.util.FastHashMap;
import biz.trustnet.common.util.URLEncDec;

import com.pgmate.model.db.dao.DAO;

public class ParamUtil {

	private FastHashMap fmap;
	private HttpServletRequest request = null;
		
	public ParamUtil(){
		fmap = new FastHashMap();
	}
	
	public ParamUtil(FastHashMap fmap){
		this.fmap = fmap;
	}
	
	public ParamUtil(HttpServletRequest request) {		
		this.request = request;
		fmap = new FastHashMap();
		Enumeration enums = request.getParameterNames();
		String name = null;
		Object value = null;
		while (enums.hasMoreElements()){
			name = (String)enums.nextElement();
			value = request.getParameter(name).trim();
			if (value == null) {
				fmap.put(name, "");
			} else {
				fmap.put(name, value);
			}
		}
		
		String pageUri = request.getRequestURI().trim()+"?request="+getString("request");
		setAttribute("pageUri", pageUri);
	}
	
	public ParamUtil(HttpServletRequest request,String charset) {
		this.request = request;
		fmap = new FastHashMap();
		Enumeration enums = request.getParameterNames();
		String name = null;
		Object value = null;
		while (enums.hasMoreElements()){
			name = (String)enums.nextElement();
			value = CommonUtil.paramEnc(request.getParameter(name),charset).trim();
			if (value == null) {
				fmap.put(name, "");
			} else {
				fmap.put(name, value);
			}
		}
	}
	
	public boolean isEqualLowerCase(String name,String value){
		if(getString(name).toLowerCase().equals(value)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isEqualUpperCase(String name,String value){
		if(getString(name).toUpperCase().equals(value)){
			return true;
		}else{
			return false;
		}
	}
	
	
	public boolean isEqual(String name,String value){
		if(getString(name).equals(value)){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	public String getHeaderValue(){
		Enumeration Enum = request.getHeaderNames();
		StringBuffer sb = new StringBuffer();
		String name = null;
		while (Enum.hasMoreElements()){
			name = (String)Enum.nextElement();
			if(request.getHeader(name) != null){
				sb.append("["+name+"="+request.getHeader(name)+"]");
			}
		}
		
		return sb.toString();
	}
	
	public String getString(String s) {
		try {
			if (fmap.get(s) == null) 
				return "";
			else
				return (String)fmap.get(s);
		} catch (Exception e) {
			return "";
		}
	}
	
	public String getString(String s, String r) {
		if(isNullOrSpace(s)) 
			return r;
		else 
			return getString(s);
	}
	
	public String getReplaceString(String s,String r){
		return getString(s,r);
	}
	
	

	public Object getObject(String s){
		if(isNullOrSpace(s)) {
			return new Object();
		} else {
			try {
				return fmap.get(s);
			} catch(Exception e) {
				return 0;
			}
		}
	}
	
	public int getInt(String s){
		if(isNullOrSpace(s)) {
			return 0;
		} else {
			try {
				return CommonUtil.parseInt(getString(s));
			} catch(Exception e) {
				return 0;
			}
		}
	}
	
	
	public long getLong(String s){
		if(isNullOrSpace(s)) {
			return 0;
		} else {
			try {
				return CommonUtil.parseLong(getString(s));
			} catch(Exception e) {
				return 0;
			}
		}
	}
	
	public double getDouble(String s){
		if(isNullOrSpace(s)) {
			return 0;
		} else {
			try {
				return CommonUtil.parseDouble(getString(s));
			} catch(Exception e) {
				return 0;
			}
		}
	}
	
	public void removeParam(String name){
		if(name == null || name.equals("")){
			return;
		}else{
			try{
			fmap.remove(name);
			}catch(Exception e){}
		}
	}
	
	public int getReplaceInt(String s,int r){
		if(isNullOrSpace(s)) 
			return r;
		else 
			return getInt(s);
	}
	
	public String[] getArray(String name) {
		if (isArray(name)) {
			return (String[])fmap.get(name);
		} else {
			return new String[] {getString(name)};
		}
	}
	
	public boolean isArray(String name) {
		Object obj = fmap.get(name);
		if (obj.getClass().isArray()) return true;
		else return false;
	}
	
	public boolean isNullOrSpace(String name) {
		if (fmap.get(name) == null || ((String)fmap.get(name)).trim().length() == 0)
		{
			return true;
		}
		return false;
	}
	
	public boolean isSpace(String name){
		if(getString(name).equals("")){
			return true;
		}else{
			return false;
		}
	}
	public String toString() {

		Iterator it = fmap.keySet().iterator();
		StringBuffer sb = new StringBuffer();
		sb.append("[Request Object]");
		String key = null;
		String[] rArray = null;
		while (it.hasNext())
		{
			key = (String)it.next();
			sb.append("[").append(key).append("=");
			rArray = getArray(key);
			for (int i = 0; i < rArray.length; i++)
			{
				sb.append(rArray[i]);
				if (i+1 != rArray.length)
				{
					sb.append(",");
				}
			}
			sb.append("]");
		}
		return sb.toString();
	}
	
	
	public void put(String key,String value){
		if(key != null && !key.equals(""))
			fmap.put(key,value);
		else
			return;
	}
	
	
	public void put(String key,Object value){
		if(key != null && !key.equals(""))
				fmap.put(key,value);
			else
				return;
		}
	public FastHashMap getMap(){
		return fmap;
	}
	
	
	public String getHIDDENTag() 
	{
		String name = null;
		String value = null;
		StringBuffer sb = new StringBuffer();
		Iterator it =  fmap.keySet().iterator();
		while (it.hasNext()) 
		{
			name = (String)it.next();
			value = getString(name);
			sb.append("<input type='hidden' name='"+name+"' value='"+value+"'>\n");
		}
		return sb.toString();
	}
	
	public String getQueryString(){
		Iterator it = fmap.keySet().iterator();
		StringBuffer sb = new StringBuffer();
		String key = null;
		while (it.hasNext())
		{
			key = (String)it.next();
			sb.append(key+"="+getString(key));
			sb.append("&");
		}
		return sb.toString();
	}
	
	public String getQueryString(String encodeFormat){
		Iterator it = fmap.keySet().iterator();
		StringBuffer sb = new StringBuffer();
		String key = null;
		while (it.hasNext())
		{
			key = (String)it.next();
			sb.append(key+"="+URLEncDec.encode(getString(key),encodeFormat));
			sb.append("&");
		}
		return sb.toString();
	}
	
	public void parseQueryString(String queryString){
		if(queryString.endsWith("&")){
			queryString = queryString.substring(0,queryString.length()-1);
		}
		queryString = queryString.replaceAll("&", " &");
		String[] resPair =  CommonUtil.adjustArray(CommonUtil.split(queryString,"&",true),2);
		for( int idx = 0 ; idx < resPair.length ; idx++) {
			String[] pair = CommonUtil.adjustArray(CommonUtil.split(resPair[idx],"=",true),2);
			fmap.put(URLEncDec.decode(pair[0]) , URLEncDec.decode(pair[1].trim()));
		}

	}
	
	
	
	public void toBean(Object bean){
		try{
			HashMap map = new HashMap();
			
		    Enumeration names = request.getParameterNames();
		    while (names.hasMoreElements()) {
		      String name = (String) names.nextElement();
		      map.put(name, request.getParameterValues(name));
		    }
		    BeanUtils.populate(bean, map);
		}catch(Exception e){
		}
	}
	
	public void setDate(DAO dao){
		if(!isNullOrSpace("startDate")){
			if(getString("startDate").length() == 8){
				dao.regStartDate = CommonUtil.stringToTimestamp(getString("startDate")+"000000");
				setAttribute("startDate",getString("startDate"));
			}
		}
		if(!isNullOrSpace("endDate")){
			if(getString("endDate").length() == 8){
				dao.regEndDate = CommonUtil.stringToTimestamp(getString("endDate")+"235959");
				setAttribute("endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
				setAttribute("endDate",getString("endDate"));
			}
		}else{
			setAttribute("endDate",CommonUtil.getCurrentDate("yyyyMMdd"));
		}
	}
	
	
	public String getHeader(String name){
		return request.getHeader(name);
	}
	
	public Object getSession(String name){
		return request.getSession().getAttribute(name);
	}
	
	public boolean isSession(String name){
		if(request.getSession().getAttribute(name) == null){
			return false;
		}else{
			return true;
		}
	}
	
	public void setSession(String name,Object obj){
		HttpSession session = request.getSession(true);
		session.setAttribute(name,obj);
	}
	
	
	public HttpServletRequest getServletRequest(){
		return request;
	}
	
	public void setAttribute(String name,Object obj){
		try{
			if(obj != null){
				request.setAttribute(name,obj);
			}
		}catch(Exception e){
			Log.debug("log.day","PARAM SET ATTRIBUTE ERROR ="+CommonUtil.getExceptionMessage(e),this);
		}
	}
	
	public Object getAttribute(String name){
		return request.getAttribute(name);
	}
	
	public void convertUrlParameter(String name){
		try{
			if(!isNullOrSpace(name)){
				String paramValue = getString(name);
				Log.debug("log.day","PARAM VALUE="+paramValue,this);
				if(paramValue.endsWith("php") || paramValue.endsWith("html") || paramValue.endsWith("jsp") || paramValue.endsWith("asp") || paramValue.endsWith("cgi") ){
					return;
				}
				if(paramValue.indexOf("%") > -1){
					paramValue = URLEncDec.decode(paramValue);
				}
				if(paramValue.endsWith("&")){
					paramValue = paramValue.substring(0,paramValue.length()-1);
				}
			
				if(paramValue.indexOf("?") > -1){
					removeParam(name);
					put(name,paramValue.substring(0,paramValue.indexOf("?")));
					paramValue = paramValue.substring(paramValue.indexOf("?")+1,paramValue.length());
					
					String[] value = CommonUtil.split(paramValue,"&", true);
					for(int i=0;i<value.length;i++){
						String[] pair = CommonUtil.split(value[i], "=",true);
						if(pair[0] != null && !pair[0].equals("")){
							if(pair[1] != null && !pair[1].equals("")){
								put(pair[0],pair[1]);
								Log.debug("log.day","PARAM ="+pair[0]+"="+pair[1],this);
							}
						}
					}
				}
			}
		}catch(Exception e){
			Log.debug("log.pay","DATA = "+getString(name),this);
			Log.debug("log.pay","Error = "+CommonUtil.getExceptionMessage(e),this);
		}
	}
	
	

}

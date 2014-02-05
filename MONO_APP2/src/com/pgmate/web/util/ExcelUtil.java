/* 
 * Project Name : PG_APP
 * Project      : PW_APP
 * File Name    : com.pgmate.web.util.ExcelUtil.java
 * Date	        : Mar 10, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExcelUtil {

	public ExcelUtil(){	
	}
	
	public HttpServletResponse toExcelResponse(HttpServletRequest request,HttpServletResponse response,String fileName){
		String agent=request.getHeader("USER-AGENT");
		String requestUrl = request.getRequestURL().toString().toLowerCase();
		response.reset();
		if (agent.indexOf("MSIE 6.0") > -1 || agent.indexOf("MSIE 5.5") > -1) {
			response.setHeader("Content-type", "application/octet-stream;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
			response.setHeader("Content-Transfer-ENCODING", "binary");
			if(!requestUrl.startsWith("https")){
				response.setHeader("Pragma", "no-cache");
			}
			response.setHeader("Cache-Control", "private");
			response.setHeader("Expires", "0");
		} else {
			//response.setHeader("Content-type", "file/unknown");
			response.setHeader("Content-type", "application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
			response.setHeader("Content-Description", "Servlet Generated Data");
			if(!requestUrl.startsWith("https")){
				response.setHeader("Pragma", "no-cache");
			}
			response.setHeader("Cache-Control", "private");
			response.setHeader("Expires", "0");
		}
		return response;
	}
}

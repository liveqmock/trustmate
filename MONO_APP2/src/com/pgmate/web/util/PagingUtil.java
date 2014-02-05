/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.web.util.PagingUtil.java
 * Date	        : Jan 23, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.web.util;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.dao.DAO;

public class PagingUtil {
	
	private ParamUtil param = null;
	private int unit		= 10;	//총 가로표기페이지
	private String url		= "";	//목적지 URL
	
	public PagingUtil(ParamUtil param,String url){
		this.param 	= param;
		this.url	= url;
		param.removeParam("curPage");
		param.removeParam("totalCount");
	}
	
	public void create(){
		
		DAO gsiDAO 	= (DAO)param.getAttribute("dao");
		String parameter= url+"?"+(param.getQueryString("utf-8")).replaceAll("'","%27"); 
		
		long curPage 	= gsiDAO.curPage;
		long pageSize	= gsiDAO.pageSize;
		long totalCount	= gsiDAO.totalCount;
		
		long totalPage = (totalCount-1)/pageSize + 1;
		
		int prev = (int)Math.floor((curPage-1) / unit) * unit;
		int next = prev + 11;
		 
		
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"hidden-print\">");
		sb.append("<ul class=\"pagination pagination-sm pull-right\">\n");
		if(prev > 0) {
			sb.append("								<li> <a href=\"#\" onclick=\"pagingSend('").append(parameter).append("curPage=").append(CommonUtil.toString(prev)).append("');\">").append("<i class=\"fa fa-angle-left\"></i></a></li>\n");
		}else{
			sb.append("								<li class=\"active\"><a href=\"#\"><i class=\"fa fa-angle-left\"></i></a></li>\n");
		}
		for (int i=1+prev; i<next && i<=totalPage; i++ ) {
			if (i==curPage) {
				sb.append("								<li class=\"active\"><a href=\"#\">"+i+"</a></li>\n");
			}else{
				sb.append("								<li><a href=\"#\" onclick=\"pagingSend('").append(parameter).append("curPage=").append(i).append("');\">"+i+"</a></li>\n");
			} 
		}
		
		if(totalPage >= next) {
			sb.append("								<li> <a href=\"#\" onclick=\"pagingSend('").append(parameter).append("curPage=").append(CommonUtil.toString(next)).append("');\">").append("<i class=\"fa fa-angle-right\"></i></a></li>\n");
		}else{
			sb.append("								<li class=\"active\"><a href=\"#\"><i class=\"fa fa-angle-right\"></i></a></li>\n");
		}
		sb.append("							</ul>");
		sb.append("</div>");
		param.setAttribute("paging",sb.toString());
	
		
		

	}
	
	

}

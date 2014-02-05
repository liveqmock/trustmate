/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.resource.GISResource.java
 * Date	        : Jan 15, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.resource;

public class GSIResource {
	
	public static final String BROWSER_INITIAL 		= "USER_BROWSER";
	public static final String DEVICE_INITIAL		= "USER_DEVICE";
	public static final String BROWSER_FIREFOX		= "firefox";
	public static final String BROWSER_SAFARI		= "safari";
	public static final String DEVICE_WEB			= "WEB";
	
	public static final String MEMBER_ROLE_MEMBER	= "0001";	//GSI 멤버
	public static final String MEMBER_ROLE_MERCHANT	= "0002";	//GSI 가맹점
	public static final String MEMBER_ROLE_GROUP	= "0003";	//GSI 그룹
	public static final String MEMBER_ROLE_AGENT	= "0004";	//GSI AGENT
	
	public static final String FILE_DIRECTORY		= "/upload";	//파일업로드 디렉토리
	public static final String TRNSCTN_SESSION 		= "TRNSCTN"; //가맹점 또는 그룹의 거래현황 SESSION KEY
	
	public static final String TRN_STATUS_SUCCESS   = "'02','04','05','07','08','09','10','11','12','13'";
	
}

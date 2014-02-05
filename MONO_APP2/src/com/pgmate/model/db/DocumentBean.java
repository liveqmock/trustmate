/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.DocumentBean.java
 * Date	        : Feb 24, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.db;

import org.springframework.web.multipart.MultipartFile;

public class DocumentBean {
	
	private MultipartFile file		= null;
	
	public DocumentBean(){
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}

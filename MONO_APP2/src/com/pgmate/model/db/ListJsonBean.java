package com.pgmate.model.db;

import java.util.List;

import com.pgmate.model.db.dao.DAO;

/**
 * @author Administrator
 *
 */
public class ListJsonBean {
	
	private String result		= "";
	private String message		= "";
	private List<GSIBean> list 	= null;
	private DAO dao				= null;
	private String paging		= "";
	
	
	public ListJsonBean(){
	}

	public List<GSIBean> getList() {
		return list;
	}

	public void setList(List<GSIBean> list) {
		this.list = list;
	}

	public DAO getDao() {
		return dao;
	}

	public void setDao(DAO dao) {
		this.dao = dao;
	}

	public String getPaging() {
		return paging;
	}

	public void setPaging(String paging) {
		this.paging = paging;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}

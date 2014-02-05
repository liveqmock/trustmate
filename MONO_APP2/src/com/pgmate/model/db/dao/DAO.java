/* 
 * Project      : PG_APP
 * File Name    : net.pgmate.model.db.dao.DAO.java
 * Date	        : Dec 23, 2008
 * Version      : 2.0
 * Author       : ginaida@ginaida.net
 * Comment      :  
 */

package com.pgmate.model.db.dao;

import java.sql.Timestamp;

public class DAO {

	public String orderBy 			= " ORDER BY REG_DATE DESC";
	public long curPage				= 1;
	public long pageSize			= 50;
	public long totalCount			= 0;
	public Timestamp regStartDate	= null;
	public Timestamp regEndDate		= null;
	
	public DAO(){	
	}
 
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}


	
	public long getCurPage() {
		return curPage;
	}

	public void setCurPage(long curPage) {
		if(curPage != 0)
			this.curPage = curPage;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		if(pageSize !=0)
			this.pageSize = pageSize;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public String getOrderBy() {
		return orderBy;
	}


	

	public Timestamp getRegStartDate() {
		return regStartDate;
	}

	public void setRegStartDate(Timestamp regStartDate) {
		this.regStartDate = regStartDate;
	}

	public Timestamp getRegEndDate() {
		return regEndDate;
	}

	public void setRegEndDate(Timestamp regEndDate) {
		this.regEndDate = regEndDate;
	}

	public String toPaging(String query){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * ");
		sb.append("FROM ");
		sb.append("    (SELECT T.* , (ROWNUM + RNUM - 1) AS TOTAL_COUNT ");
		sb.append("    FROM ");
		sb.append("        (SELECT TB.*, ROWNUM RNUM ");
		sb.append("        FROM ( ");
		sb.append(	             query);
		sb.append("             ) TB ");
		sb.append("        ORDER BY RNUM DESC) T ");
		sb.append("    ORDER BY RNUM ASC) ");
		sb.append("WHERE RNUM BETWEEN "+Long.toString(((pageSize*(curPage-1)+1)))+" AND "+Long.toString(pageSize*curPage) );
		return sb.toString();
	}	
}

package com.pgmate.model.chart;

import java.sql.Timestamp;

/**
 * @author Administrator
 *
 */
public class CalendarBean {

	private String title	= "";
	private Timestamp start = null;
	private Timestamp end	= null;
	private String backgroundColor = "";
	private String url		= "";
	
	public CalendarBean(){
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getStart() {
		return start;
	}

	public void setStart(Timestamp start) {
		this.start = start;
	}

	public Timestamp getEnd() {
		return end;
	}

	public void setEnd(Timestamp end) {
		this.end = end;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}

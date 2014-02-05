package com.pgmate.model.chart;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class PieChartBean {

	private String type	= "pie";
	private String name = "";
	private List<Object[]> data = null;
	
	public PieChartBean(){
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Object[]> getData() {
		return data;
	}

	public void setData(List<Object[]> data) {
		this.data = data;
	}


	
	
}

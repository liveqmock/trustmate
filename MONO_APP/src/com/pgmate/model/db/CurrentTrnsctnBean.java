/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.db.CurrentTrnsctnBean.java
 * Date	        : Jan 27, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.model.db;

public class CurrentTrnsctnBean implements java.io.Serializable{

	private double dailyCount 		= 0;
	private double dailySum			= 0;
	private double weekCount 		= 0;
	private double weekSum			= 0;
	private double monthCount 		= 0;
	private double monthSum			= 0;
	private double monthCancelCount = 0;
	
	public CurrentTrnsctnBean(){
	}

	public double getDailyCount() {
		return dailyCount;
	}

	public void setDailyCount(double dailyCount) {
		this.dailyCount = dailyCount;
	}

	public double getDailySum() {
		return dailySum;
	}

	public void setDailySum(double dailySum) {
		this.dailySum = dailySum;
	}

	public double getWeekCount() {
		return weekCount;
	}

	public void setWeekCount(double weekCount) {
		this.weekCount = weekCount;
	}

	public double getWeekSum() {
		return weekSum;
	}

	public void setWeekSum(double weekSum) {
		this.weekSum = weekSum;
	}

	public double getMonthCount() {
		return monthCount;
	}

	public void setMonthCount(double monthCount) {
		this.monthCount = monthCount;
	}

	public double getMonthSum() {
		return monthSum;
	}

	public void setMonthSum(double monthSum) {
		this.monthSum = monthSum;
	}

	public double getMonthCancelCount() {
		return monthCancelCount;
	}

	public void setMonthCancelCount(double monthCancelCount) {
		this.monthCancelCount = monthCancelCount;
	}
	
}

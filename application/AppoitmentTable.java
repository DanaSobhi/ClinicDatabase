package application;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;


public class AppoitmentTable  {
	
	private int appTableApID;

	private int appTablePatID;
	
	private int appTableNutriID;
	
	private Date appTableDate;
	
	private Time appTableTime;
	
	private Double appTableCost;
	
	private Date appTablePayDate;
	
	private String appTableBried;
	
	private int appTableStatus;
	



	



	public AppoitmentTable(int appTableApID, int appTablePatID, int appTableNutriID, Date appTableDate,
			Time appTableTime, double appTableCost, Date appTablePayDate, String appTableBried, int appTableStatus) {
		this.appTableApID = appTableApID;
		this.appTablePatID = appTablePatID;
		this.appTableNutriID = appTableNutriID;
		this.appTableDate = appTableDate;
		this.appTableTime = appTableTime;
		this.appTableCost = appTableCost;
		this.appTablePayDate = appTablePayDate;
		this.appTableBried = appTableBried;
		this.appTableStatus = appTableStatus;
	}



	public int getAppTableApID() {
		return appTableApID;
	}

	public void setAppTableApID(int appTableApID) {
		this.appTableApID = appTableApID;
	}

	public int getAppTablePatID() {
		return appTablePatID;
	}

	public void setAppTablePatID(int appTablePatID) {
		this.appTablePatID = appTablePatID;
	}

	public int getAppTableNutriID() {
		return appTableNutriID;
	}

	public void setAppTableNutriID(int appTableNutriID) {
		this.appTableNutriID = appTableNutriID;
	}

	public Date getAppTableDate() {
		return appTableDate;
	}

	public void setAppTableDate(Date appTableDate) {
		this.appTableDate = appTableDate;
	}

	public Time getAppTableTime() {
		return appTableTime;
	}

	public void setAppTableTime(Time appTableTime) {
		this.appTableTime = appTableTime;
	}

	public Double getAppTableCost() {
		return appTableCost;
	}

	public void setAppTableCost(Double appTableCost) {
		this.appTableCost = appTableCost;
	}

	public Date getAppTablePayDate() {
		return appTablePayDate;
	}

	public void setAppTablePayDate(Date appTablePayDate) {
		this.appTablePayDate = appTablePayDate;
	}

	public String getAppTableBried() {
		return appTableBried;
	}

	public void setAppTableBried(String appTableBried) {
		this.appTableBried = appTableBried;
	}

	public int getAppTableStatus() {
		return appTableStatus;
	}

	public void setAppTableStatus(int appTableStatus) {
		this.appTableStatus = appTableStatus;
	}


	
}

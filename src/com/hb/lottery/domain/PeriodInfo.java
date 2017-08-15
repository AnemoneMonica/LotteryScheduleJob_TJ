package com.hb.lottery.domain;

public class PeriodInfo {

	private String id; //流水号
	private String gameName; //彩种编号
	private String termCode; //期次
	private String dateTime; //yyyy-dd-mm 
	private String partner;//代理商
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getTermCode() {
		return termCode;
	}
	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	
	
}

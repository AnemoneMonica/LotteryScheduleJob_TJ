package com.hb.lottery.domain;

public class FilePar {

	private int fileParId;
	private String gameName;
	private String termCode;
	private String issueStartTime;
	private String issueEndTime;
	private String issueState;
	public int getFileParId() {
		return fileParId;
	}
	public void setFileParId(int fileParId) {
		this.fileParId = fileParId;
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
	public String getIssueStartTime() {
		return issueStartTime;
	}
	public void setIssueStartTime(String issueStartTime) {
		this.issueStartTime = issueStartTime;
	}
	public String getIssueEndTime() {
		return issueEndTime;
	}
	public void setIssueEndTime(String issueEndTime) {
		this.issueEndTime = issueEndTime;
	}
	public String getIssueState() {
		return issueState;
	}
	public void setIssueState(String issueState) {
		this.issueState = issueState;
	}
	
	
}

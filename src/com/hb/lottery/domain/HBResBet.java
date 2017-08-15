package com.hb.lottery.domain;

public class HBResBet {

	boolean flag = false;
	private String stationID;
	private String gameID;
	private String issue;
	private String tickSN;
	private String wagerTime;
	private String lastAmount;
	private String backCode;

	public void Analytical(String mess) {
		String[] fields = mess.split(";");
		if (fields.length != 0) {
			for (int i = 0; i < fields.length; i++) {
				System.out.println("fields[" + i + "]=" + fields[i]);
			}
			stationID = fields[0];
			gameID = fields[1];
			issue = fields[2];
			tickSN = fields[3];
			wagerTime = fields[4];
			lastAmount = fields[5];

			flag = true;
		} else {
			flag = false;
		}
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getGameID() {
		return gameID;
	}

	public void setGameID(String gameID) {
		this.gameID = gameID;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getLastAmount() {
		return lastAmount;
	}

	public void setLastAmount(String lastAmount) {
		this.lastAmount = lastAmount;
	}

	public String getStationID() {
		return stationID;
	}

	public void setStationID(String stationID) {
		this.stationID = stationID;
	}

	public String getTickSN() {
		return tickSN;
	}

	public void setTickSN(String tickSN) {
		this.tickSN = tickSN;
	}

	public String getWagerTime() {
		return wagerTime;
	}

	public void setWagerTime(String wagerTime) {
		this.wagerTime = wagerTime;
	}

	public String getBackCode() {
		return backCode;
	}

	public void setBackCode(String backCode) {
		this.backCode = backCode;
	}
}

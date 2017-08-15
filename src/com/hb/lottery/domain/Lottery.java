package com.hb.lottery.domain;

import java.util.List;

public class Lottery {

	private String salesMoney;
	private String winMoney;
	private List<LotteryRequestInfo>ticketInfolist;

	public String getSalesMoney() {
		return salesMoney;
	}

	public void setSalesMoney(String salesMoney) {
		this.salesMoney = salesMoney;
	}

	public String getWinMoney() {
		return winMoney;
	}

	public void setWinMoney(String winMoney) {
		this.winMoney = winMoney;
	}

	public List<LotteryRequestInfo> getTicketInfolist() {
		return ticketInfolist;
	}

	public void setTicketInfolist(List<LotteryRequestInfo> ticketInfolist) {
		this.ticketInfolist = ticketInfolist;
	}

	
	
}

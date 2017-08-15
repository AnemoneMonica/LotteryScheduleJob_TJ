package com.hb.lottery.domain;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class TicketNoticeBody {
	@XStreamAlias("ticketNotify")
	private List<TicketInfo> tickets = new ArrayList<TicketInfo>();

	public List<TicketInfo> getTickets() {
		return tickets;
	}

	public void setTickets(List<TicketInfo> tickets) {
		this.tickets = tickets;
	}

}

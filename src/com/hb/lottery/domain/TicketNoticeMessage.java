package com.hb.lottery.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("message")
public class TicketNoticeMessage extends Message {
	@XStreamAlias("body")
    private TicketNoticeBody body = new TicketNoticeBody();

	public TicketNoticeBody getBody() {
		return body;
	}

	public void setBody(TicketNoticeBody body) {
		this.body = body;
	}

}

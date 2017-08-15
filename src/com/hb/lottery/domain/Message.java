package com.hb.lottery.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
@XStreamAlias("message")
public class Message {

	@XStreamAsAttribute
	private String version;
	@XStreamAsAttribute
	private String id;
	@XStreamAlias("header")
	private Header header = new Header();
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}

	
}

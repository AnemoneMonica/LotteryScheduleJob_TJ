package com.hb.lottery.domain.tj;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("issueinfos")
public class Issueinfos {
	
	private  Issueinfo issueinfo;

	public Issueinfo getIssueinfo() {
		return issueinfo;
	}

	public void setIssueinfo(Issueinfo issueinfo) {
		this.issueinfo = issueinfo;
	}

	
	
}

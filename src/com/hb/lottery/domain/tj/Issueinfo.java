package com.hb.lottery.domain.tj;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("issueinfo")
public class Issueinfo {

	private String errorcode;
	private String errormsg;
	private String lotteryid;
	private String issueno;
	private String begintime;
	private String endtime;
	private String warestate;
	private String luckyno;
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	public String getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(String lotteryid) {
		this.lotteryid = lotteryid;
	}
	public String getIssueno() {
		return issueno;
	}
	public void setIssueno(String issueno) {
		this.issueno = issueno;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getWarestate() {
		return warestate;
	}
	public void setWarestate(String warestate) {
		this.warestate = warestate;
	}
	public String getLuckyno() {
		return luckyno;
	}
	public void setLuckyno(String luckyno) {
		this.luckyno = luckyno;
	}
	
}

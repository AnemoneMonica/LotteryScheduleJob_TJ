package com.hb.lottery.handler;

import java.util.HashMap;
import java.util.Map;

import com.hb.lottery.conf.Configuration;
import com.hb.lottery.conf.HttpSender;
import com.hb.lottery.conf.MD5Util;
import com.hb.lottery.domain.tj.Issueinfo;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class QueryWinData extends Thread{
	
	private String termCode;
	private String gameName;
	private int id;
	private final XStream lotteryRequestStream = new XStream(new DomDriver());

	public QueryWinData() {
		
	}
	
	@Override
	public void run()
	{
		handler(gameName,termCode);	
	}
	
	public void handler(String gameName,String termCode) {
		

	}

	

}

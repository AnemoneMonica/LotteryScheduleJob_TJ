package com.hb.lottery.handler;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.hb.lottery.conf.Configuration;
import com.hb.lottery.conf.DateUtils;
import com.hb.lottery.conf.HttpSender;
import com.hb.lottery.conf.MD5Util;
import com.hb.lottery.domain.FilePar;
import com.hb.lottery.domain.tj.Issueinfo;
import com.hb.lottery.domain.tj.Issueinfos;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import comm.hb.lottery.conn.QueryHelper;

//开奖号码查询
public class QueryWinCode extends Thread{

	private String termCode;
	private String gameName;
	private int id;
	private final XStream lotteryRequestStream = new XStream(new DomDriver());

//	public QueryWinCode(String gameName,String termCode) {
//		this.termCode = termCode;
//		this.gameName=gameName;
//	}
	public QueryWinCode() {
		
	}
	@Override
	public void run()
	{
		handler(gameName,termCode);	
	}
	
	public void handler(String gameName,String termCode) {
		String url = Configuration.getGlobalMsg("tj.url");
		Map<String, String> map = getRequestMsg();
		String resMsg = HttpSender.post(url, map);
		System.out.println(resMsg);
		lotteryRequestStream.processAnnotations(Issueinfo.class);
		Issueinfo body = null;
		try {
			body = (Issueinfo) lotteryRequestStream.fromXML(resMsg);
		} catch (Exception e) {
			System.out.println(e);
		}
		if (body != null && body.getErrorcode().equals("0")) {
			if (!body.getIssueno().equals(termCode)) {
				Insert(body.getLuckyno());
			}
		}

	}

	public Map<String, String> getRequestMsg() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("transcode", "13010");
		map.put("agentid", Configuration.getGlobalMsg("tj.agentid"));
		map.put("lotteryid", "112");
		map.put("issueno", "170815040");
		map.put("sign", getSign(map.get("lotteryid")+map.get("issueno")));

		return map;
	}

	public String getlotteryId(String gameName) {
		if (gameName.equals("TJK10")) {
			return "112";
		}

		return "";
	}

	public String getSign(String content) {
		String msg =  Configuration.getGlobalMsg("tj.agentid") + content
				+ Configuration.getGlobalMsg("tj.key");
		return MD5Util.getMD5(msg);
	}

	public void Insert(String bonusCode) {
		try {
			// 更新
			StringBuffer updateSql = new StringBuffer(
					"update file_par set BonusCode=?,IssueState=2 where FileParId=? and IssueState=1");
			QueryHelper.Update(updateSql.toString(), bonusCode, id);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void testQueryIssue() {
		String gameName="TJK10";
		String termCode="170815040";
		handler(gameName,termCode);
		//handler();
		
	}
}

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

//奖期查询
public class QueryIssue extends Thread{

	private String termCode;
	private String gameName;
	private final XStream lotteryRequestStream = new XStream(new DomDriver());

	public QueryIssue(String gameName,String termCode) {
		this.termCode = termCode;
		this.gameName=gameName;
	}
	public QueryIssue() {
		
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
		lotteryRequestStream.processAnnotations(Issueinfos.class);
		Issueinfos body = null;
		try {
			body = (Issueinfos) lotteryRequestStream.fromXML(resMsg);
		} catch (Exception e) {
			System.out.println(e);
		}
		if (body != null && body.getIssueinfo().getErrorcode().equals("0")) {
			if (!body.getIssueinfo().getIssueno().equals(termCode)) {
				Insert(body.getIssueinfo(),gameName);
			}
		}

	}

	public Map<String, String> getRequestMsg() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("transcode", "13007");
		map.put("agentid", Configuration.getGlobalMsg("tj.agentid"));
		map.put("lotteryid", "112");
		map.put("sign", getSign(map.get("lotteryid")));

		return map;
	}

	public String getlotteryId(String gameName) {
		if (gameName.equals("TJK10")) {
			return "112";
		}

		return "";
	}

	public String getSign(String lotteryid) {
		String msg = Configuration.getGlobalMsg("tj.agentid") + lotteryid
				+ Configuration.getGlobalMsg("tj.key");
		return MD5Util.getMD5(msg);
	}

	public void Insert(Issueinfo issue, String gameName) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select FileParId,GameName,TermCode,IssueState from file_par where GameName=\"");
			sql.append(gameName);
			sql.append("\" and TermCode=\"");
			sql.append(issue.getIssueno());
			sql.append("\"");
			System.out.println(sql.toString());
			FilePar filePar = QueryHelper.Query(sql.toString(), FilePar.class);
			if (filePar == null) {
				// 插入
				StringBuffer updateSql = new StringBuffer(
						"insert into file_par(GameName,TermCode,IssueStartTime,IssueEndTime,IssueState)value(?,?,?,?,?)");
				QueryHelper.Update(updateSql.toString(), gameName,
						issue.getIssueno(), DateUtils.formatDate(DateUtils.parseDate(issue.getBegintime()),"yyyy-MM-dd HH:mm:ss"),
						DateUtils.formatDate(DateUtils.parseDate(issue.getEndtime()),"yyyy-MM-dd HH:mm:ss"), "1");
			} else {
				if (filePar.getIssueState().equals("0")) {
					// 更新
					StringBuffer updateSql = new StringBuffer(
							"update file_par set IssueStartTime=?,IssueEndTime=?,IssueState=1 where FileParId=?");
					QueryHelper.Update(updateSql.toString(),
							DateUtils.formatDate(DateUtils.parseDate(issue.getBegintime()),"yyyy-MM-dd HH:mm:ss"), DateUtils.formatDate(DateUtils.parseDate(issue.getEndtime()),"yyyy-MM-dd HH:mm:ss"),
							filePar.getFileParId());
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

//	@Test
//	public void testQueryIssue() {
//		String gameName="TJK10";
//		String termCode="170813075";
//		handler(gameName,termCode);
//		//handler();
//		
//	}
	
	
	public static void main(String [] a) {
		
		String gameName="TJK10";
		String termCode="";
		new QueryIssue().handler(gameName,termCode);
		//handler();
		
	}
}

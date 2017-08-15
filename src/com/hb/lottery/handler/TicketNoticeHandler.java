package com.hb.lottery.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hb.lottery.conf.HttpSender;
import com.hb.lottery.conf.MD5Util;
import com.hb.lottery.conf.RedisUtil;
import com.hb.lottery.domain.TicketInfo;
import com.hb.lottery.domain.TicketNoticeMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class TicketNoticeHandler extends Thread {

	private String packageId; //包号
	private String ticketNum; //票号
	private String partner; //代理商号
	private final XStream ticketNoticeStream = new XStream(new DomDriver());
	private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public TicketNoticeHandler(String packageId,String ticketNum,String partner)
	{
		this.packageId=packageId;
		this.ticketNum=ticketNum;
		this.partner=partner;
	}
	@Override
	public void run()
	{
		Map<String,String>map=RedisUtil.getMap(packageId);
		
		//System.out.println(packageId+" : "+ticketNum+" : "+map.size());
		if(map.size()==Integer.parseInt(ticketNum))
		{
			Map<String,String>partnerMap=RedisUtil.getMap(partner);
			ticketNotice(map,partnerMap);
		}
	}
	public void ticketNotice(Map<String,String>map,Map<String,String>partnerMap)
	{
		List<TicketInfo>tickets=new ArrayList<TicketInfo>();
		TicketNoticeMessage ticketNoticeMessage = new TicketNoticeMessage();
		for(Map.Entry<String, String>entry:map.entrySet())
	{
		TicketInfo ticketInfo=new TicketInfo();
		String ticketId=entry.getKey();
		String[]ticket=entry.getValue().split("_");
		ticketInfo.setId(ticketId);
		ticketInfo.setStatus(ticket[0]);
		ticketInfo.setDealTime(ticket[1]);
		ticketInfo.setTicketSerialNo(ticket[2]);
		ticketInfo.setMessage("成功，系统处理正常");
		tickets.add(ticketInfo);
	}
	ticketNoticeMessage.getBody().setTickets(tickets);	
		
	  Date currentTime = new Date();
      String timeStamp = dateTimeFormat.format(currentTime);
      String body = ticketNoticeStream.toXML(ticketNoticeMessage.getBody()).replaceAll(">[\\n\\r\\s]*", ">");
      body = body.replaceAll("<>|<>", "");
      String id = packageId;
      String digest = MD5Util.getMD5(id+timeStamp+partnerMap.get("password")+body);
      ticketNoticeMessage.getHeader().setDigest(digest);
      ticketNoticeMessage.getHeader().setTimestamp(timeStamp);

      String MessageID = partner;
      ticketNoticeMessage.getHeader().setMessengerID(MessageID);
      ticketNoticeMessage.getHeader().setTransactionType("104");
      ticketNoticeMessage.getBody().setTickets(tickets);
      ticketNoticeMessage.setId(packageId);
      String version = "1.1";
      ticketNoticeMessage.setVersion(version);
      ticketNoticeStream.processAnnotations(TicketNoticeMessage.class);
      String xml1 = ticketNoticeStream.toXML(ticketNoticeMessage).replaceAll(">[\\n\\r\\s]*", ">");
      xml1 = xml1.replaceAll("</>|<issue/>", "");

//      String xml = "transType=104"
//              + "&transMessage=<?xml version="
//              + "\"1.0\""
//              + " encoding="
//              + "\"GBK\""
//              + "?>"
//              + xml1;
      String xml ="<?xml version="
              + "\"1.0\""
              + " encoding="
              + "\"GBK\""
              + "?>"
              + xml1;
      String url=partnerMap.get("noticeurl");
      RedisUtil.detTicketNoticeMap(id);
      send(xml,url);
	}
	
	public void send(String xml,String url)
	{
		try{ 
		Map<String,String>sendMap=new HashMap<String,String>();
	      sendMap.put("transType", "104");
	      sendMap.put("transMessage", xml);
	      System.out.println(xml);
		 HttpSender.post(url, sendMap);	
		}catch(Exception e)
		{
	
		}
	}
	
	
	public static void main(String[] args)
	{
//	{
//		Map<String,String>map=new HashMap<String,String>();
//		Map<String,String>partnerMap=new HashMap<String,String>();
//		map.put("1234567", "0000_2016-06-30 17:42:00_12345");
//		map.put("123456722", "0000_2016-06-30 17:42:00_1234511");
//		partnerMap.put("password", "123456");
		//new TicketNoticeHandler("111","1","ZYJYSH").ticketNotice(map,partnerMap);
	String xml="<?xml version=\"1.0\" encoding=\"GBK\"?><message version=\"1.1\" id=\"LPS20161012153939000654\"><header><messengerID>29002000191</messengerID><timestamp>20161012153650</timestamp><transactionType>104</transactionType><digest>6377fca9bdfb93aa29bdb5c2380e13a4</digest></header><body><ticketNotify><ticket id=\"HBP2016101215393900000085\" dealTime=\"2016-10-12 15:35:36\" status=\"0000\" message=\"成功，系统处理正常\" ticketSerialNo=\"3857\"/></ticketNotify></body></message>";
	 Map<String,String>sendMap=new HashMap<String,String>();
    sendMap.put("transType", "104");
    sendMap.put("transMessage", xml);
    System.out.println(xml);
    String url="http://60.216.3.34:22200/hubei";
    HttpSender.post(url, sendMap);
	}
}

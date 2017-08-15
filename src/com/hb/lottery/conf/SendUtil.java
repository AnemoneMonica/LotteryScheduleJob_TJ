package com.hb.lottery.conf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

public class SendUtil {

	Logger logger=Logger.getLogger(SendUtil.class);
	  public  String send(String sRecBuf, String host,
				int port) {
			Socket s = null;
			String sReturnBuf = "";

			try {
				//int port = Integer.parseInt(sport);
				s = new Socket(host, port);
				InputStream in = s.getInputStream();
				OutputStream out = s.getOutputStream();
	                logger.info("���ͺ�̨���ģ�[" + sRecBuf + "]" );
				//System.out.println("���ͺ�̨���ģ�[" + sRecBuf + "]");
				sRecBuf = sRecBuf + "\n\r";
				// дbuf
				out.write(sRecBuf.getBytes());
				// ��buf
				try
				{
					BufferedReader br = new BufferedReader(new InputStreamReader(in));
					//����ɹ���
					sReturnBuf = br.readLine();
					//sReturnBuf = sReturnBuf.substring(4,sReturnBuf.length());
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				logger.info("��̨���ر��ģ�[" + sReturnBuf + "]");
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			} finally {
				try {
					if (s != null)
						s.close();
				} catch (IOException e) {
					logger.error("Error:" + e.toString());
				}
			}
			return sReturnBuf;
		}
}

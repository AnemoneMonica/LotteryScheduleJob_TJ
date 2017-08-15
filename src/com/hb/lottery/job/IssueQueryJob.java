package com.hb.lottery.job;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hb.lottery.conf.DateUtils;
import com.hb.lottery.domain.FilePar;
import com.hb.lottery.handler.QueryIssue;

import comm.hb.lottery.conn.QueryHelper;

public class IssueQueryJob implements Job{
	Logger logger = Logger.getLogger(IssueQueryJob.class);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		press();
	}

	
	public void press()
	{
		try {
			String sql = "select FileParId,GameName,TermCode,IssueStartTime,IssueEndTime from FILE_PAR where IssueState=1";
			List<FilePar> fileParList=QueryHelper.QueryList(FilePar.class, sql.toString());
			if(!fileParList.isEmpty())
			{
				for(int i=0;i<fileParList.size();i++)
				{
					System.out.println(fileParList.get(i).getIssueEndTime());
					if(DateUtils.parseDate(fileParList.get(i).getIssueEndTime()).before(new Date()))
					{
						new QueryIssue(fileParList.get(i).getGameName(),fileParList.get(i).getTermCode()).start();
					}
				}
			}
		
		} catch (Exception e) {
			logger.error(e);
		}
	}
}

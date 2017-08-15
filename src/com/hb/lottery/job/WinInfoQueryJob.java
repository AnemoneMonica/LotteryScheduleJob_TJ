package com.hb.lottery.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class WinInfoQueryJob implements Job{
	
	Logger logger = Logger.getLogger(WinInfoQueryJob.class);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		press();
	}
	
	public void press()
	{
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new WinInfoQueryJob().press();
	}

}

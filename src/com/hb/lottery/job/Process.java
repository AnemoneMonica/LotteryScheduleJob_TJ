package com.hb.lottery.job;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class Process {
	public static void initSchedule(){
		try {
			System.out.println("¶¨Ê±Æ÷Æô¶¯");
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Process.initSchedule();
	}
}

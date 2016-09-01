package com.hundsun.hq.modify.factory;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.hundsun.hq.modify.tool.HqConfigTool;

public class HqJobFactory extends AbsScheduleFactory {

	public HqJobFactory() {
		super() ;
		try {
			hqScheduler = super.getDefaultScheduler() ;
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void loadJobClass() {
		List<Map<String, String>> jobs = HqConfigTool.parseModifyJobConfig() ;
		int jobLen = jobs.size() ;
		JobDetail hqJob = null ;
		CronTrigger cronTrigger = null ;
		for(int i = 0 ; i < jobLen ; i++){
			String jobName = jobs.get(i).get("name") ;
			String jobClass = jobs.get(i).get("class") ;
			String jobCronExpress = jobs.get(i).get("cronExpress") ;
			try {
				hqJob = new JobDetail(jobName,"hq", Class.forName(jobClass))  ;
				cronTrigger = new CronTrigger(jobName, "hq", jobCronExpress) ;
				//放入job队列
				hqScheduler.scheduleJob(hqJob, cronTrigger) ;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}
}

package com.hundsun.hq.modify.job;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 新增资源文件
 * @author hspcadmin
 *
 */
public class CopyFileJob implements Job{

	private static final String PREFIX = "/d:/" ;
	private static final String copyFromFile = "reff040824.txt" ;
	
	@Override
	public void execute(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMdd") ;
		String destFileName = copyFromFile.replace("0824", dateFormat.format(new Date())) ;
		Calendar yesterday = Calendar.getInstance() ;
		yesterday.add(Calendar.DAY_OF_YEAR, -1) ;
		String yestodayFileName = copyFromFile.replace("0824", dateFormat.format(yesterday.getTime())) ;
		
		BufferedReader reader = null ;
		BufferedWriter writer = null ;
		File fromFile = new File(PREFIX + copyFromFile) ;
		File destFile = new File(PREFIX + destFileName) ;
		File yestordayFile = new File(PREFIX + yestodayFileName) ;
		try {
			if(fromFile.exists() && fromFile.isFile()){
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(fromFile))) ;
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFile))) ;
				String line = null ;
				while((line = reader.readLine())!=null){
					writer.write(line + "\n");
				}
				
				writer.flush();
				
				reader.close();
				writer.close();
				System.out.println("新增reff04文件成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//删除昨天的文件
			if(yestordayFile.exists() && yestordayFile.isFile()){
				yestordayFile.delete() ;
			}
		}
	}

}

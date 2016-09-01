package com.hundsun.hq.modify.job;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hundsun.hq.modify.tool.HqTool;

public class ModifyTimeJob implements Job {
	private static final String modifyHqFilePath = "mktdt04.txt";
	private static List<String> hqList = new ArrayList<String>();

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		File modifyFile = new File(modifyHqFilePath);
		if (modifyFile.exists() && modifyFile.isFile()) {
			BufferedReader hqReader = null;
			BufferedWriter writer = null;
			String line = null;

			System.out.println("-----------------修改文件中日期：开始-----------------");
			try {

				hqReader = new BufferedReader(new InputStreamReader(
						new FileInputStream(modifyFile)));
				hqList.add(HqTool.replaceTime(hqReader.readLine()));
				while ((line = hqReader.readLine()) != null) {
					hqList.add(line);
				}
				writer = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(modifyFile, false)));
				StringBuffer writeBuffer = new StringBuffer();
				for (String temp : hqList) {
					writeBuffer.append(temp + "\n");
				}
				writer.write(writeBuffer.toString());
				writer.flush();
				System.out
						.println("-----------------修改文件中日期：结束-----------------");

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				hqList.clear();
				if (hqReader != null) {
					try {
						hqReader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}

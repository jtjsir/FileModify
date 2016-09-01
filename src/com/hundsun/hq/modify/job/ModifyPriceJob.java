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
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hundsun.hq.modify.tool.HqConfigTool;
import com.hundsun.hq.modify.tool.HqTool;

/**
 * @author jingsir
 **
 * 
 */
public class ModifyPriceJob implements Job {

	private static final String modifyHqFilePath = "mktdt04.txt";
	private static List<String> hqList = new ArrayList<String>();

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		Map<String, Map<String, String>> cofigMap=HqConfigTool.parseModifyConfig() ;
		File modifyFile = new File(modifyHqFilePath);
		if (modifyFile.exists() && modifyFile.isFile()) {
			System.out.println("-----------------正在修改文件中的价格-----------------");
			BufferedReader hqReader = null;
			BufferedWriter writer = null;
			String line = null;
			try {

				hqReader = new BufferedReader(new InputStreamReader(
						new FileInputStream(modifyFile)));
				while ((line = hqReader.readLine()) != null) {
					hqList.add(HqTool.replaceMarketInfo(cofigMap, line));

				}

				// 覆盖源内容
				writer = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(modifyFile, false)));
				StringBuffer writeBuffer = new StringBuffer();

				for (String temp : hqList) {
					writeBuffer.append(temp + "\n");

				}

				writer.write(writeBuffer.toString());
				writer.flush();
				System.out
						.println("-----------------修改文件中的价格结束-----------------");

			} catch (Exception e) {
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

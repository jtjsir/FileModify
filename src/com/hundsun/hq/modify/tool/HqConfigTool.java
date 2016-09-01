package com.hundsun.hq.modify.tool;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class HqConfigTool {
	private static final String modifyconfigPath = "com/hundsun/hq/config/modify-config.xml";
	
	private static final String modifyjobPath = "com/hundsun/hq/config/modify-job.xml" ;
	
	/**
	 * 解析modify-config.xml配置
	 * @return	map
	 */
	public static Map<String, Map<String, String>> parseModifyConfig() {
		String code = null;
		String index = null;
		String key = null;
		Map<String, Map<String, String>> cofigMap = new HashMap<String, Map<String, String>>();
		InputStream configStream = null;
		try {
			configStream = HqTool.class.getClassLoader().getResourceAsStream(
					modifyconfigPath);
		} catch (Exception e) {
			System.out.println("找不到modify-config.xml配置文件");
		}
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(configStream);
			Element root = document.getRootElement();
			Map<String, String> attrMap = null;
			for (@SuppressWarnings("rawtypes")
			Iterator iterator = root.elementIterator("modify"); iterator
					.hasNext();) {
				Element eFunNo = (Element) iterator.next();
				key = eFunNo.attributeValue("actionKey");

				attrMap = new HashMap<String, String>();
				code = eFunNo.attributeValue("code");
				index = eFunNo.getTextTrim();
				attrMap.put("code", code);
				attrMap.put("index", index);

				cofigMap.put(key, attrMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cofigMap;
	}

	/**
	 * 解析modify-job.xml配置
	 * @return
	 */
	public static List<Map<String, String>> parseModifyJobConfig(){
		InputStream jobConfigStream = null ;
		SAXReader jobReader = null ;
		Document jobDoc = null ;
		List<Map<String,String>> listJobs = new ArrayList<>() ;
		try {
			jobConfigStream = HqConfigTool.class.getClassLoader().getResourceAsStream(modifyjobPath) ;
			jobReader = new SAXReader() ;
			jobDoc = jobReader.read(jobConfigStream) ;
			Element root = jobDoc.getRootElement() ;
			Iterator<Element> jobElements = root.elementIterator("job") ;
			Element temp = null ;
			Map<String, String> jobMap = null ;
			while(jobElements.hasNext()){
				temp = jobElements.next() ;
				jobMap = new HashMap<>() ;
				jobMap.put("name", temp.attributeValue("name")) ;
				jobMap.put("class", temp.attributeValue("class")) ;
				jobMap.put("cronExpress", temp.attributeValue("cronExpress")) ;
				
				listJobs.add(jobMap) ;
			}
			
			if(jobConfigStream!=null){
				jobConfigStream.close();
			}
		} catch (Exception e) {
			System.out.println("读取modify-job.xml配置文件出错，确认该文件存在");
		}
		
		return listJobs ;
	}
}

package com.hundsun.hq.test;

import org.junit.Assert;
import org.junit.Test;

import com.hundsun.hq.modify.tool.HqConfigTool;
import com.hundsun.hq.modify.tool.HqTool;

public class HqToolTest {

	@Test
	public void testParse(){
		
		Assert.assertTrue(HqConfigTool.parseModifyConfig().get("MD405").get("index").equals("6,7,8")) ;
	}
	
	@Test
	public void test(){
		System.out.println(HqTool.replaceTime("HEADER|ITP1.00 |          |   37|        |XHKG01|20160824-11:30:23.000|0|3   "));
	}
}

package com.hundsun.hq.modify.tool;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * @author jingsir
 **
 *
 */
public class HqTool {

	private static boolean modifyFlag = false;
	private static int line_rows = 0;

	/**
	 * 目前先修改数字
	 *
	 * @param key
	 * @param code
	 *            代码
	 * @param index
	 *            下标
	 * @param line
	 *            内容
	 */
	public static String replaceMarketInfo(
			Map<String, Map<String, String>> cMap, String line) {
		line_rows++;
		String regx = "\\|";
		String[] hqArr = line.split(regx);
		Set<String> k = cMap.keySet();
		Iterator<String> it = k.iterator();
		while (it.hasNext()) {
			String key = it.next();
			Map<String, String> codeMap = cMap.get(key);
			String code = codeMap.get("code");
			// 格式化
			if ("MD404".equals(hqArr[0]) && key.equals("MD404")) {
				line = replaceRowLine(code, hqArr, cMap, line) ;
			} else if ("MD405".equals(hqArr[0]) && key.equals("MD405")) {
				line = replaceRowLine(code, hqArr, cMap, line) ;
				
			}
			else if ("MD401".equals(hqArr[0]) && key.equals("MD401")) {
				line = replaceRowLine(code, hqArr, cMap, line) ;
			}
		}

		// 重置
		if (line_rows == 22) {
			line_rows = 0;
			modifyFlag = !modifyFlag;
		}
		return line;
	}

	/**
	 * 修改每一行指定下标的数据
	 * 
	 * @param code
	 * @param hqArr
	 * @param cMap
	 * @param line
	 * @return
	 */
	private static String replaceRowLine(String code, String[] hqArr,
			Map<String, Map<String, String>> cMap, String line) {
		String old = null;

		if (code.equals(hqArr[1])) {
			String[] indexs = cMap.get(hqArr[0]).get("index").split(",");
			for (int i = 0; i < indexs.length; i++) {
				int ik = Integer.valueOf(indexs[i]);// 修改数字
				old = hqArr[ik];
				if ((ik == 4 || ik == 5 || ik == 6) && hqArr[0].equals("MD405")) {
					hqArr[ik] = caculatePrice(hqArr, ik);
				} else if ((ik == 6 || ik == 7 || ik == 8)
						&& hqArr[0].equals("MD404")) {
					hqArr[ik] = caculatePrice(hqArr, ik);
				} else if ((ik == 6 || ik == 7 || ik == 8 || ik == 9
						|| ik == 11 || ik == 13)
						&& hqArr[0].equals("MD401")) {
					hqArr[ik] = caculatePrice(hqArr, ik);
				}
				int offLen = old.length() - hqArr[ik].length();
				String addBlank = "";
				for (int j = 0; j < offLen; j++) {
					addBlank += " ";
				}
				line = line.replace(old, addBlank + hqArr[ik]);
			}

		}

		return line;
	}

	private static String caculatePrice(String[] hqArr, int ik) {
		String price;
		DecimalFormat format = new DecimalFormat("#.000");
		if (!modifyFlag) {
			price = String.valueOf(format.format(Float.parseFloat(hqArr[ik]
					.trim()) + 1.000F));
		} else {
			price = String.valueOf(format.format(Float.parseFloat(hqArr[ik]
					.trim()) - 1.000F));
		}

		return price;
	}

	public static String replaceTime(String line) {
		String regxString = "\\|";
		String[] hqLine = line.split(regxString);
		if ("HEADER".equals(hqLine[0])) {
			String date = hqLine[6];
			String regx = "-";
			String[] dateTime = date.split(regx);
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
			String aTime = sdf1.format(new Date());
			line = line.replace(dateTime[0], aTime);
		}
		System.out.println("------------修改后的时间为:-----------" + line);
		return line;
	}
}

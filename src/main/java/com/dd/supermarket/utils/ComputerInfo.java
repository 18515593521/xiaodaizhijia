package com.dd.supermarket.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年5月29日 下午9:28:36 <br/>
* 
* Copyright (C) 2014 ××××××××××. All Rights Reserved.                                                                                       
*                                                                                                                                           
* Filename:      ComputerInfo.java                                                                                                          
* Revision:      1.0                                                                                                                        
* Author:        <yao xiucai>                                                                                                               
* Created On:    2014年5月21日                                                                                                                 
* Modified by:                                                                                                                              
* Modified On:                                                                                                                              
*                                                                                                                                           
* Description:   <取网卡物理地址--1.在Windows,Linux系统下均可用；2.通过ipconifg,ifconfig获得计算机信息；3.再用模式匹配方式查找MAC地址，与操作系统的语言无关>                                
* Description:   <取计算机名--从环境变量中取>                                                                                                           
* 类说明：
*/
public class ComputerInfo {
	private static String macAddressStr = null;
	private static String computerName = System.getenv().get("COMPUTERNAME");

	private static final String[] windowsCommand = { "ipconfig", "/all" };
	private static final String[] linuxCommand = { "/sbin/ifconfig", "-a" };
	private static final Pattern macPattern = Pattern.compile(".*((:?[0-9a-f]{2}[-:]){5}[0-9a-f]{2}).*",
			Pattern.CASE_INSENSITIVE);

	private final static List<String> getMacAddressList() throws IOException {
		final ArrayList<String> macAddressList = new ArrayList<String>();
		final String os = System.getProperty("os.name");
		final String command[];

		if (os.startsWith("Windows")) {
			command = windowsCommand;
		} else if (os.startsWith("Linux")) {
			command = linuxCommand;
		} else {
			throw new IOException("Unknow operating system:" + os);
		}

		final Process process = Runtime.getRuntime().exec(command);

		BufferedReader bufReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		for (String line = null; (line = bufReader.readLine()) != null;) {
			Matcher matcher = macPattern.matcher(line);
			if (matcher.matches()) {
				macAddressList.add(matcher.group(1));
				// macAddressList.add(matcher.group(1).replaceAll("[-:]",
				// ""));//去掉MAC中的“-”
			}
		}

		process.destroy();
		bufReader.close();
		return macAddressList;
	}

	public static String getMacAddress() {
		if (macAddressStr == null || macAddressStr.equals("")) {
			StringBuffer sb = new StringBuffer(); // 存放多个网卡地址用，目前只取一个非0000000000E0隧道的值
			try {
				List<String> macList = getMacAddressList();
				for (Iterator<String> iter = macList.iterator(); iter.hasNext();) {
					String amac = iter.next();
					if (!amac.equals("0000000000E0")) {
						sb.append(amac);
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			macAddressStr = sb.toString();

		}

		return macAddressStr;
	}

	public static String getComputerName() {
		if (computerName == null || computerName.equals("")) {
			computerName = System.getenv().get("COMPUTERNAME");
		}

		return computerName;
	}

	private ComputerInfo() {

	}

	public static void main(String[] args) {
		System.out.println(ComputerInfo.getMacAddress());
		System.out.println(ComputerInfo.getComputerName());
	}
}
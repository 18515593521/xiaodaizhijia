package com.dd.supermarket.service.back;

import java.util.List;
import java.util.Map;

public interface IConsole {
	//统计总注册量
	public int register_total();
	//统计昨日总注册量
	public int yesterday_registernum();
	
	public int find_loginlog();
	
	//统计今日总注册量
	public int today_registernum();
	//统计昨日总活跃量
	public int find_yesterdayloginlog();
	//统计今日日总活跃量
	public int find_todayloginlog();
	
	//统计产品合计数
	public int find_comtotalnum();
			
	//统计在线产品合计数
	public int find_comnum();
	
	//统计统计昨日总结算量 ，金额
	public Map find_settlement();
	
	//统计统计昨日总结算量 ，金额
	public Map find_ydsettlement();
	
	//折线图统计注册,登录 量
	public List<Object> find_UserRegister();
	
	//折线图统计注册,登录 量
	public List<Object> find_UserSettlement();
	
	//统计未结算量 ，金额
	public Map find_Unsettled();

}

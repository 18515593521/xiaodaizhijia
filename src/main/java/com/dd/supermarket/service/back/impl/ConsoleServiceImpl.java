package com.dd.supermarket.service.back.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.dao.back.ConsoleDao;
import com.dd.supermarket.service.back.IConsole;

@Transactional
@Service("consoleServiceImpl")
public class ConsoleServiceImpl implements IConsole{
	

	@Resource(name="consoleDao")
	private ConsoleDao consoleDao;
	
	//统计总注册量
	public int register_total(){
		return consoleDao.register_total();
	}
	
	//统计昨日总注册量
	public int yesterday_registernum(){
		return consoleDao.yesterday_registernum();
	}
	
	public int find_loginlog(){
		return consoleDao.find_loginlog();
	}
	
	//统计昨日总注册量
	public int find_yesterdayloginlog(){
		return consoleDao.find_yesterdayloginlog();
	}
	
	//统计今日活跃量
	public int find_todayloginlog(){
		return consoleDao.find_todayloginlog();
	}
	
	//统计今日注册量
	public int today_registernum(){
		return consoleDao.today_registernum();
	}
	
	//统计产品合计数
	public int find_comtotalnum(){
		return consoleDao.find_comtotalnum();
	}
			
			
	//统计在线产品合计数
	public int find_comnum(){
		return consoleDao.find_comnum();
	}
	
	//统计总结算量 ，金额
	public Map find_settlement(){
		return consoleDao.find_settlement();
	}
		
		
	//统计统计昨日总结算量 ，金额
	public Map find_ydsettlement(){
		return consoleDao.find_ydsettlement();
	}
	
	//折线图统计注册,登录 量
	public List<Object> find_UserRegister(){
		return consoleDao.find_UserRegister();
	}
	
	//折线图统计注册,登录 量
	public List<Object> find_UserSettlement(){
		return consoleDao.find_UserSettlement();
	}
	
	//统计未结算量 ，金额
	public Map find_Unsettled(){
		return consoleDao.find_Unsettled();
	}

}

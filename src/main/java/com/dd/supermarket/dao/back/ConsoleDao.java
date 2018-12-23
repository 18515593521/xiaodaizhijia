package com.dd.supermarket.dao.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;

@Repository("consoleDao")
public class ConsoleDao {
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//统计总注册量
	public int register_total(){
		return (int) baseDao.findOne("BackConsole.register_total", null);
	}
	
	//统计昨日总注册量
	public int yesterday_registernum(){
		return (int) baseDao.findOne("BackConsole.yesterday_registernum", null);
	}
	
	//统计今日总注册量
	public int today_registernum(){
		return (int) baseDao.findOne("BackConsole.today_registernum", null);
	}
	
	//统计总活跃量
	public int find_loginlog(){
		return (int) baseDao.findOne("BackConsole.find_loginlog", null);
	}
	
	//统计昨日活跃量
	public int find_yesterdayloginlog(){
		return (int) baseDao.findOne("BackConsole.find_yesterdayloginlog", null);
	}
	//统计今日活跃量
	public int find_todayloginlog(){
		return (int) baseDao.findOne("BackConsole.find_todayloginlog", null);
	}
	
	//统计产品合计数
	public int find_comtotalnum(){
		return (int) baseDao.findOne("BackConsole.find_comtotalnum", null);
	}
		
	//统计在线产品合计数
	public int find_comnum(){
		return (int) baseDao.findOne("BackConsole.find_comnum", null);
	}
	
	//统计总结算量 ，金额
	public Map find_settlement(){
		return (Map) baseDao.findOne("BackConsole.find_settlement", null);
	}
	
	//统计未结算量 ，金额
	public Map find_Unsettled(){
		return (Map) baseDao.findOne("BackConsole.find_Unsettled", null);
	}
		
		
	//统计昨日总结算量 ，金额
	public Map find_ydsettlement(){
		return (Map) baseDao.findOne("BackConsole.find_ydsettlement", null);
	}
	
	//折线图统计注册,登录 量
	public List<Object> find_UserRegister(){
		return baseDao.findForList("BackConsole.find_UserRegister", null);
	}
	
	//折线图统计注册,登录 量
	public List<Object> find_UserSettlement(){
		return baseDao.findForList("BackConsole.find_UserSettlement", null);
	}
}

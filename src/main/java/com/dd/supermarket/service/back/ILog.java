package com.dd.supermarket.service.back;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.dd.supermarket.utils.PageData;

public interface ILog {
	//分页查询日志
	public List<PageData> find_log(PageData pd);
	
	//添加日志
	public void save_log(HttpSession session,String sys_log_content,int sign);
}

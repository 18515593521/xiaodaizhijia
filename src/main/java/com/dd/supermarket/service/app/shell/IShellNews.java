package com.dd.supermarket.service.app.shell;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IShellNews {
	//查询消息
	public List<Object> find_news(HttpServletRequest request, String user_id);
	
	public int find_newsCount(String user_id);
	
}

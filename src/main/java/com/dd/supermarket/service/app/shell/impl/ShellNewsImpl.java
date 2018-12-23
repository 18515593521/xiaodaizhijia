package com.dd.supermarket.service.app.shell.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.controller.app.utils.PathFactory;
import com.dd.supermarket.dao.app.shell.ShellNewsDao;
import com.dd.supermarket.service.app.shell.IShellNews;
import com.dd.supermarket.utils.http.GetServer;

@Transactional
@Service("shellNewsImpl")
public class ShellNewsImpl implements IShellNews {
	@Resource(name="shellNesDao")
	private ShellNewsDao shellNewsDao;
	
	//查询消息
	public List<Object> find_news(HttpServletRequest request, String user_id){
		List<Object> newslist = shellNewsDao.find_news(user_id);
		PathFactory pf = new PathFactory();
		String serverUrl = new GetServer().getServerUrl(request);	
		for (int i = 0; i < newslist.size(); i++) {
			Map<String, Object> map=(Map<String, Object>) newslist.get(i);
			newslist.set(i,pf.shelNewslFactory(serverUrl,map));
		}
		return newslist;
	}

	public int find_newsCount(String user_id) {
		return (Integer)shellNewsDao.find_newsCount(user_id);
	}
	
}

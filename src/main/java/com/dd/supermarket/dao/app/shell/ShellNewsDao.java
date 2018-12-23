package com.dd.supermarket.dao.app.shell;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;

@Repository("shellNesDao")
public class ShellNewsDao {
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//查询消息
	public List<Object> find_news(String user_id){
		return baseDao.findForList("shellNews.find_news", user_id);
	}

	public Object find_newsCount(String user_id) {
		return baseDao.findOne("shellNews.find_newsCount", user_id);
	}
}

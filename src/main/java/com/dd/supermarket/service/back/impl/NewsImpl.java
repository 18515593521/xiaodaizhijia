package com.dd.supermarket.service.back.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.dao.back.NewsDao;
import com.dd.supermarket.service.back.INews;
import com.dd.supermarket.utils.PageData;
@Transactional
@Service("backNewsImpl")
public class NewsImpl implements INews{
		
	@Resource(name="backNewsDao")
	private NewsDao newsdao;
	
	//分页查询日志
	public List<PageData> find_news(PageData pd){
		return newsdao.find_news(pd);
	}
	
	public void save_news(Map<String, Object> map){
		newsdao.save_news(map);
	}
}

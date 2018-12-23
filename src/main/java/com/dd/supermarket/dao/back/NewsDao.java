package com.dd.supermarket.dao.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;
import com.dd.supermarket.utils.PageData;
@Repository("backNewsDao")
public class NewsDao {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//分页查询日志
	public List<PageData> find_news(PageData pd){
		return (List<PageData>) baseDao.findForList("backNews.find_news", pd);		
	}
	
	public void save_news(Map<String, Object> map){
		baseDao.save("backNews.save_news", map);
	}
}

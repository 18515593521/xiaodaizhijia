package com.dd.supermarket.service.back;

import java.util.List;
import java.util.Map;

import com.dd.supermarket.utils.PageData;

public interface INews {
	//分页查询日志
	public List<PageData> find_news(PageData pd);
	public void save_news(Map<String, Object> map);
}

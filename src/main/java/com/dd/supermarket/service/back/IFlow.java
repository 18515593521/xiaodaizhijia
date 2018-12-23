package com.dd.supermarket.service.back;

import java.util.List;

import com.dd.supermarket.utils.PageData;

public interface IFlow {
	//分页查询
	public List<PageData> find_flow(PageData pd);
}

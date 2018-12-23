package com.dd.supermarket.service.back;

import java.util.List;

import com.dd.supermarket.utils.PageData;

public interface IFeedback {
	//分页查询日志
		public List<PageData> find_feedback(PageData pd);
}

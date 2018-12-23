package com.dd.supermarket.service.back;

import java.util.List;
import java.util.Map;

import com.dd.supermarket.utils.PageData;

public interface IUser {
	//分页查询用户
	public List<PageData> find_user(PageData pd);
	
	/**
	 * 获取用户手机号
	 */
	public List<Map> find_phone();
}

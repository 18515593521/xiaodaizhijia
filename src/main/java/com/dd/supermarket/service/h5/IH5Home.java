package com.dd.supermarket.service.h5;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface IH5Home {
	public List<Map<String, Object>> getHomeDataById(HttpServletRequest request,String lable_dic_id);
	
	//添加申请人信息
	public void save_h5apply(Map map);
}

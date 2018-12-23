package com.dd.supermarket.service.app.shell;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface IShellCommodity {
	//我要出
	public void save_commodity(Map map);
	
	//根据用户id查询手机号
	public Object find_phone(String user_id);
	
	//类型查询
	public List<Object> find_com(String ty_id);
	
	//查询首页轮播图片地址
	public  List<Map<String,Object>> find_banner(HttpServletRequest request);
	
	public void update(String com_id);
}

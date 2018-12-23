package com.dd.supermarket.service.back;

import java.util.List;
import java.util.Map;

public interface ILogin {
	//登录
	public Object find_admin(Map map);
	
	//查询管理员权限
	public Object find_adminPower(String sys_sr_id);
}

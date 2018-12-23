package com.dd.supermarket.service.back;

import java.util.List;
import java.util.Map;

public interface IAdmin {
	public List<Object> find_role();
	
	public void save_admin(Map map);
	
	public List<Object> find_admin(String sys_sa_name);
	public void del_admin(String sys_sa_id);
	
	//修改管理员
	public void update_admin(Map map);
}

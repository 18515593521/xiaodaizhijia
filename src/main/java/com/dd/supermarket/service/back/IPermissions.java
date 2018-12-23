package com.dd.supermarket.service.back;

import java.util.List;
import java.util.Map;

public interface IPermissions {
	//查询全部菜单
	public List<Object> find_allMenu();
	
	//新增角色
	public void save_role(Map<String, Object> map,List<Map> list);
	

	//查询角色权限
	public List<Object> find_allrRolePower();
	
	//删除角色
	public void del_rolePower(String sys_sr_id);
	
	
	public void update_role(String sys_sr_name,String sr_id,List<Map> list);
	
	//根据角色id查询权限
	public List<Object> find_powerByid(String sys_sr_id);
}

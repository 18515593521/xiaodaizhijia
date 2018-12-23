package com.dd.supermarket.service.back.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.dao.back.PermissionsDao;
import com.dd.supermarket.service.back.IPermissions;
import com.dd.supermarket.utils.UuidUtil;

@Transactional
@Service("backDictionarysImpl")
public class PermissionsImpl implements IPermissions{
	
	@Resource(name="backPermissionsDao")
	private PermissionsDao permissions;
	
	//查询全部菜单
	public List<Object> find_allMenu(){
		return permissions.find_allMenu();
	}

	//添加角色
	public void save_role(Map<String, Object> map,List<Map> list) {
		// TODO Auto-generated method stub
		
		permissions.save_role(map,list);
	}
	//查询角色权限
	public List<Object> find_allrRolePower(){
		return permissions.find_allrRolePower();
	}


	//删除角色
	public void del_rolePower(String sys_sr_id) {
		permissions.del_role(sys_sr_id);
	}
	
	//角色修改
	public void update_role(String sys_sr_name,String sr_id,List<Map> list){
		Map<String, Object> role = new HashMap<String, Object>();
		role.put("sys_sr_name", sys_sr_name);
		role.put("sys_sr_id", sr_id);
		permissions.update_role(role);
		permissions.del_power(sr_id);
		permissions.save_power(list);
	}
	
	//根据角色id查询权限
	public List<Object> find_powerByid(String sys_sr_id){
		return permissions.find_powerByid(sys_sr_id);
	}
	

}

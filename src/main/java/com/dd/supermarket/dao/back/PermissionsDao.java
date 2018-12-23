package com.dd.supermarket.dao.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;
//权限dao
@Repository("backPermissionsDao")
public class PermissionsDao {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//查询全部菜单
	public List<Object> find_allMenu(){
		return baseDao.findForList("BackSysMenu.find_allMenu",null);
	}
	
	//添加角色
	public void save_role(Map<String, Object> map,List<Map> list){
		 baseDao.save("BackSysMenu.save_role", map);
		 baseDao.batchSave("BackSysMenu.save_sys_power", list);
	}
	
	//查询角色权限
	public List<Object> find_allrRolePower(){
		return baseDao.findForList("BackSysMenu.find_allrRolePower", null);
	}
	
	//删除角色
	public void del_role(String sys_sr_id){
		baseDao.update("BackSysMenu.up_state", sys_sr_id);	
	}
	
	//删除角色权限关联表
	public void del_power(String sys_sr_id){
		baseDao.delete("BackSysMenu.del_power", sys_sr_id);
	}
	
	//修改角色
	public void update_role(Map<String, Object> map){
		baseDao.delete("BackSysMenu.update_role", map);
	}
	
	//添加角色权限关联表
	public void save_power(List<Map> list){
		 baseDao.batchSave("BackSysMenu.save_sys_power", list);
	}
	
	//根据角色id查询权限
	public List<Object> find_powerByid(String sys_sr_id){
		return baseDao.findForList("BackSysMenu.find_powerByid",sys_sr_id);
	}
	
}

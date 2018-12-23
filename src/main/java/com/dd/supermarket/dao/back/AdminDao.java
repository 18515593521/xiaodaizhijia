package com.dd.supermarket.dao.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;

@Repository("backAdminDao")
public class AdminDao {
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//查询全部角色
	public List<Object> find_role(){
		return baseDao.findForList("backAdministrators.find_role", null);
	}
	
	//添加管理员
	public void save_admin(Map map){
		baseDao.save("backAdministrators.save_admin", map);
	}
	
	//查询全部管理员
	public List<Object> find_admin(String sys_sa_name){
		return baseDao.findForList("backAdministrators.find_admin", sys_sa_name);
	}
	
	//删除管理员
	public void del_admin(String sys_sa_id){
		/*baseDao.delete("backAdministrators.del_admin", sys_sa_id);*/
		baseDao.update("backAdministrators.del_admin", sys_sa_id);
	}
	
	//修改管理员
	public void update_admin(Map map){
		baseDao.delete("backAdministrators.update_admin", map);
	}
}

package com.dd.supermarket.dao.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;

@Repository("backLoginDao")
public class LoginDao {
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//登录
	public Object find_admin(Map map){
		return baseDao.findOne("backAdministrators.select_admin", map);
	}
	
	//查询管理员权限
	public Object find_adminPower(String sys_sr_id){
		return baseDao.findOne("backAdministrators.find_loginpower", sys_sr_id);
	}
}

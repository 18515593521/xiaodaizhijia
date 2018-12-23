package com.dd.supermarket.service.back.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.dao.back.LoginDao;
import com.dd.supermarket.service.back.ILogin;

@Transactional
@Service("backLoginImpl")
public class LoginImpl implements ILogin{
	
	@Resource(name="backLoginDao")
	private LoginDao loginDao;
	
	//登录
	public Object find_admin(Map map){
		return loginDao.find_admin(map);
	}
	//查询管理员权限
	public Object find_adminPower(String sys_sr_id){
		return loginDao.find_adminPower(sys_sr_id);
	}
}

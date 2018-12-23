package com.dd.supermarket.service.back.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.dao.back.LoginLogDao;
import com.dd.supermarket.service.back.ILoginLog;
import com.dd.supermarket.utils.UuidUtil;


@Transactional
@Service("loginLogServiceImpl")
public class LoginLogServiceImpl implements ILoginLog{
	@Resource(name="loginLogDao")
	private LoginLogDao loginLogDao;
	
	public Object find_llogbyid(Map map){
		return loginLogDao.find_llogbyid(map);
	}
	
	public void add_loginlog(Map map){
		map.put("id", UuidUtil.get32UUID());
		loginLogDao.add_loginlog(map);
	}
	
	public void upd_time(Map map){
		loginLogDao.upd_time(map);
	}
}

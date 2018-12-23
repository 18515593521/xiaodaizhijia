package com.dd.supermarket.dao.back;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;

@Repository("loginLogDao")
public class LoginLogDao {
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	
	public Object find_llogbyid(Map map){
		return baseDao.findOne("backLoginLog.find_llogbyid", map);
	}
	
	public void add_loginlog(Map map){
		baseDao.save("backLoginLog.add_loginlog", map);
	}
	
	public void upd_time(Map map){
		baseDao.update("backLoginLog.upd_time", map);
	}
	
	
}

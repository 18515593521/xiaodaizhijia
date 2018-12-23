package com.dd.supermarket.dao.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;
import com.dd.supermarket.utils.PageData;

@Repository("backUserDao")
public class UserDao {
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//分页查询用户
	public List<PageData> find_user(PageData pd){
		return (List<PageData>) baseDao.findForList("BackUser.find_user", pd);		
	};
	
	/**
	 * 获取用户手机号
	 */
	public List<Map> find_phone(){
		return baseDao.findForList("BackUser.find_phone", null);
	}
	
}

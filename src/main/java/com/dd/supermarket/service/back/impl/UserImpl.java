package com.dd.supermarket.service.back.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.dao.back.UserDao;
import com.dd.supermarket.service.back.IUser;
import com.dd.supermarket.utils.PageData;
@Transactional
@Service("backUserImpl")
public class UserImpl implements IUser{
	@Resource(name="backUserDao")
	private UserDao userdao;
	//分页查询用户
	public List<PageData> find_user(PageData pd){
		return userdao.find_user(pd);
	}
	
	/**
	 * 获取用户手机号
	 */
	public List<Map> find_phone(){
		return userdao.find_phone();
	}
}

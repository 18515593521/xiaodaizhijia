package com.dd.supermarket.dao.wx;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;

@Repository("wxLoginDao")
public class WxLoginDao {
	@Resource(name="baseDao")
	private BaseDao baseDao;
	//根据微信openid查询
	public Object find_userinfo(String open_id){
		return baseDao.findOne("WxLogin.find_user_userinfos", open_id);
	}
}

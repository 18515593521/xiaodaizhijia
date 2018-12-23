package com.dd.supermarket.dao.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;

@Repository("backBusinessDao")
public class BusinessDao {
	@Resource(name="baseDao")
	private BaseDao baseDao;
	

	public List<Object> find_business(){
		return baseDao.findForList("backBusiness.find_business", null);
	}
	public void update_business(Map map){
		baseDao.update("backBusiness.update_business", map);
	}
}

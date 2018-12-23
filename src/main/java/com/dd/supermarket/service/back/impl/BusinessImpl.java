package com.dd.supermarket.service.back.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.dao.back.BusinessDao;
import com.dd.supermarket.service.back.IBusiness;
@Transactional
@Service("backBusinessImpl")
public class BusinessImpl implements IBusiness{
	@Resource(name="backBusinessDao")
	private BusinessDao businessDao;
	public List<Object> find_business(){
		return businessDao.find_business();
	}
	
	public void update_business(Map map){
		businessDao.update_business(map);
	}


}

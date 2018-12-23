package com.dd.supermarket.service.app.shell.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.dao.app.shell.ShellCommodityDao;
import com.dd.supermarket.dao.app.shell.ShellOrderDao;
import com.dd.supermarket.service.app.shell.IOrder;
import com.dd.supermarket.utils.UuidUtil;

@Transactional
@Service("shellOrderImpl")
public class ShellOrderImpl implements IOrder{
	@Resource(name="shellOrderDao")
	private ShellOrderDao shellOrderDao;
	
	//新增订单
	public void save_order(Map map){
		map.put("or_id",UuidUtil.get32UUID());
		map.put("active_time","5分钟内活跃");
		shellOrderDao.save_order(map);
	}
	
	//查询订单信息
	public List<Object> find_order(String user_id){
		return shellOrderDao.find_order(user_id);
	}
	
	//根据条件查询订单信息
	public List<Object> find_orderstate(Map map){
		return shellOrderDao.find_orderstate(map);
	}
}

package com.dd.supermarket.dao.app.shell;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;

@Repository("shellOrderDao")
public class ShellOrderDao {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//新增订单
	public void save_order(Map map){
		baseDao.save("shellOrder.save_order", map);
	}
	
	//查询订单信息
	public List<Object> find_order(String user_id){
		return baseDao.findForList("shellOrder.find_order", user_id);
	} 
	
	
	//根据条件查询订单信息
	public List<Object> find_orderstate(Map map){
		return baseDao.findForList("shellOrder.find_orderstate", map);
	} 
}

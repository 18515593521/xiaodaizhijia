package com.dd.supermarket.service.app.shell;

import java.util.List;
import java.util.Map;

public interface IOrder {
	//新增订单
	public void save_order(Map map);
	
	//查询订单信息
	public List<Object> find_order(String user_id);
	
	//根据条件查询订单信息
		public List<Object> find_orderstate(Map map);

}

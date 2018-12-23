package com.dd.supermarket.service.back;

import java.util.List;
import java.util.Map;

public interface IRecharge {

	//添加角色
	public void save_recharge(Map map);
	
	//查询充值记录
	public List<Object> find_recharge(String com_id);
	
	//根据id查询商品信息
		public List<Object> find_com(String com_id);
	
}

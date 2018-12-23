package com.dd.supermarket.service.back;

import java.util.List;
import java.util.Map;

public interface ISettlement {
	//添加结算明细
	public void save_settlement(Map map);
	
	//查询点击数量 和 产品单价
	public List<Object> find_clicknumber(Map map);
	
	//查询结算明细
	public List<Object> find_settlement(String com_id);
	
	//修改结算状态
	public void update_state(String cs_id);
}

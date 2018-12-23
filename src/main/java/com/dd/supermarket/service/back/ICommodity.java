package com.dd.supermarket.service.back;

import java.util.List;
import java.util.Map;

import com.dd.supermarket.utils.PageData;

public interface ICommodity {
	//分页查询商品
	public List<PageData> findByPage(PageData pd);
	//查询所有公司
	public List<Object> find_company();
	
	//根据id查询商品
	public List<Object> find_ByIdCommodity(String com_id);
	
	//添加商品信息
	public void save_commodity(Map map);
	
	//添加商品信息
	public void update_commodity(Map map);
	//上下架
	public void update_state(Map map);
	
	//根据公司id删除商品
	public void del_comm(String com_id);
	
	//查询所有产品id
	public List<Map> find_comId(Map map);
}

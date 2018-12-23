package com.dd.supermarket.service.back;

import java.util.List;
import java.util.Map;

import com.dd.supermarket.utils.PageData;

public interface IBankCard {
	//查询信用卡信息
	public List<PageData> find_bankCard(PageData pd);
	
	//上下架
	public void upd_state(Map map);

	//删除
	public void upd_isdisplay(String bc_id);
	
	//添加
	public void save_bankCard(Map map);
	
	//查询信用卡信息
	public Object findByIdCard(String bc_id);
	//修改
	public void upd_card(Map map);
}

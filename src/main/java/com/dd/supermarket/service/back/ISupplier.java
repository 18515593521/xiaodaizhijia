package com.dd.supermarket.service.back;

import java.util.List;
import java.util.Map;

public interface ISupplier {
	public void save_supplier(Map map);
	public List<Object> find_supplier(Map map);
	//删除供应商
	public void del_supplier(String ci_id);
	//根据ID查询供应商
	public List<Object> findByIdSupplier(String ci_id);
	
	//修改供应商
	public void update_supplier(Map map);
	
	// 查询角色对应对接人的管理员
		public List<Object> find_admin();
}

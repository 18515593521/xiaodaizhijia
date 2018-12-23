package com.dd.supermarket.service.back.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.dao.back.PermissionsDao;
import com.dd.supermarket.dao.back.SupplierDao;
import com.dd.supermarket.service.back.ISupplier;
import com.dd.supermarket.utils.UuidUtil;

@Transactional
@Service("backSupplierImpl")
public class SupplierImpl implements ISupplier{

	@Resource(name="backSupplierDao")
	private SupplierDao supplierdao;
	
	public void save_supplier(Map map) {
		// TODO Auto-generated method stub
		map.put("ci_id", UuidUtil.get32UUID());
		 supplierdao.save_supplier(map);;
	}
	
	public List<Object> find_supplier(Map map){
		return supplierdao.find_supplier(map);
	}
	
	//删除供应商
	public void del_supplier(String ci_id){
		supplierdao.del_supplier(ci_id);
	}
	
	//根据ID查询供应商
	public List<Object> findByIdSupplier(String ci_id){
		return supplierdao.findByIdSupplier(ci_id);
	}
	
	//修改供应商
	public void update_supplier(Map map){
		supplierdao.update_supplier(map);
	}
	
	// 查询角色对应对接人的管理员
	public List<Object> find_admin(){
		return supplierdao.find_admin();
	}

}

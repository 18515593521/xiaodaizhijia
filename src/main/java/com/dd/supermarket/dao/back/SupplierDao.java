package com.dd.supermarket.dao.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;
import com.dd.supermarket.utils.UuidUtil;

@Repository("backSupplierDao")
public class SupplierDao {
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//添加供应商
	public void save_supplier(Map map){
		baseDao.save("BackSupplier.save_company_informatio", map);
	}
	
	//查询供应商
	public List<Object> find_supplier(Map map){
		return baseDao.findForList("BackSupplier.find_company_informatio", map);
	}
	
	//删除供应商 更改状态即可
	public void del_supplier(String ci_id){
		/*baseDao.delete("BackSupplier.del_company_informatio", ci_id);*/
		baseDao.update("BackSupplier.del_company_informatio", ci_id);
	}
	
	//根据ID查询供应商
	public List<Object> findByIdSupplier(String id){
		return baseDao.findForList("BackSupplier.find_supplier", id);
	}
	
	//修改供应商
	public void update_supplier(Map map){
		 baseDao.update("BackSupplier.update_supplier", map);
	}
	
	// 查询角色对应对接人的管理员
	public List<Object> find_admin(){
		return baseDao.findForList("BackSupplier.find_admin", null);
	}
		
}

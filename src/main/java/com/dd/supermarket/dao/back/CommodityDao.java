package com.dd.supermarket.dao.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;
import com.dd.supermarket.utils.PageData;


@Repository("backCommodityDao")
public class CommodityDao {
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//分页查询商品
	public List<PageData> findByPage(PageData pd){
		return (List<PageData>) baseDao.findForList("backCommodity.find_commodity", pd);		
	};
	
	//查询所有公司
	public List<Object> find_company(){
		return baseDao.findForList("backCommodity.find_company", null);
	}
	
	//根据id查询商品
	public List<Object> find_ByIdCommodity(String com_id){
		return baseDao.findForList("backCommodity.find_ByIdCommodity", com_id);
	}
	
	//添加商品信息
	public void save_commodity(Map map){
		 baseDao.save("backCommodity.save_commodity", map);
	}
	
	//添加商品信息
	public void update_commodity(Map map){
		baseDao.update("backCommodity.update_commodity", map);
	}
	//上下架
	public void update_state(Map map){
		baseDao.update("backCommodity.update_state", map);
	}
	
	//根据公司id删除商品
	public void del_comm(String com_id){
		baseDao.delete("backCommodity.del_com", com_id);
	}
	
	//查询所有产品id
	public List<Map> find_comId(Map map){
		return baseDao.findForList("backCommodity.find_comId", map);
	}
	
	
}

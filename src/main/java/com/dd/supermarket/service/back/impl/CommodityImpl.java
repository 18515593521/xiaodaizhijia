package com.dd.supermarket.service.back.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dd.supermarket.dao.back.AdminDao;
import com.dd.supermarket.dao.back.CommodityDao;
import com.dd.supermarket.service.back.ICommodity;
import com.dd.supermarket.utils.PageData;
@Transactional
@Service("backCommodityImpl")
public class CommodityImpl implements ICommodity{
	
	@Resource(name="backCommodityDao")
	private CommodityDao commoditydao;
	
	//分页查询商品
	public List<PageData> findByPage(PageData pd){
		return  commoditydao.findByPage(pd);
	}
	
	//查询所有公司
	public List<Object> find_company(){
		return commoditydao.find_company();
	}
	
	
	
	//根据id查询商品
	public List<Object> find_ByIdCommodity(String com_id){
		return commoditydao.find_ByIdCommodity(com_id);
	}
	
	//添加商品信息
	public void save_commodity(Map map){
		commoditydao.save_commodity(map);
	}
	
	//添加商品信息
	public void update_commodity(Map map){
		commoditydao.update_commodity(map);
	}
	
	//上下架
	public void update_state(Map map){
		commoditydao.update_state(map);
	}
	//根据公司id删除商品
	public void del_comm(String com_id){
		commoditydao.del_comm(com_id);
	}
	
	//查询所有产品id
	public List<Map> find_comId(Map map){
		return commoditydao.find_comId(map);
	}
	
	
}

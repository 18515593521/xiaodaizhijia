package com.dd.supermarket.dao.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;

@Repository("backRechargeDao")
public class RechargeDao {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//添加充值记录
	public void save_recharge(Map map){
		baseDao.save("backRecharge.save_recharge", map);
	}
	
	//查询充值记录
	public List<Object> find_recharge(String com_id){
		return baseDao.findForList("backRecharge.find_recharge",com_id);
	}
	
	//根据id查询商品信息
	public List<Object> find_com(String com_id){
		return baseDao.findForList("backRecharge.find_com", com_id);
	}
	
}

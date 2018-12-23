package com.dd.supermarket.service.back.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.dao.back.RechargeDao;
import com.dd.supermarket.service.back.IRecharge;
import com.dd.supermarket.utils.UuidUtil;

@Transactional
@Service("backRechargeImpl")
public class RechargeImpl implements IRecharge{
	@Resource(name="backRechargeDao")
	private RechargeDao rechargedao;
	//添加角色
	public void save_recharge(Map map){
		map.put("cr_id", UuidUtil.get32UUID());
		rechargedao.save_recharge(map);
	}
	
	//查询充值记录
	public List<Object> find_recharge(String com_id){
		return rechargedao.find_recharge(com_id);
	}
	
	//根据id查询商品信息
	public List<Object> find_com(String com_id){
		return rechargedao.find_com(com_id);
	}
	
	
}

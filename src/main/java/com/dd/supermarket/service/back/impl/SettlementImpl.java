package com.dd.supermarket.service.back.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.dao.back.SettlementDao;
import com.dd.supermarket.service.back.ISettlement;
import com.dd.supermarket.utils.UuidUtil;

@Transactional
@Service("backSettlementImpl")
public class SettlementImpl implements ISettlement{
	
	@Resource(name="backSettlementDao")
	private SettlementDao settlementdao;
	//添加结算明细
	public void save_settlement(Map map){
		map.put("cs_id", UuidUtil.get32UUID());
		settlementdao.save_settlement(map);
	}
	
	//查询点击数量 和 产品单价
	public List<Object> find_clicknumber(Map map){
		return settlementdao.find_clicknumber(map);
		
	}
	
	//查询结算明细
	public List<Object> find_settlement(String com_id){
		return settlementdao.find_settlement(com_id);
	}
	
	//修改结算状态
	public void update_state(String cs_id){
		settlementdao.update_state(cs_id);
	}
}

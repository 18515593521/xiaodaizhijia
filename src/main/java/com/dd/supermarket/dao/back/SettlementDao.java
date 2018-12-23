package com.dd.supermarket.dao.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;
@Repository("backSettlementDao")
public class SettlementDao {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//添加结算明细
	public void save_settlement(Map map){
		baseDao.save("backSettlement.save_settlement", map);
	}
	
	
	//查询点击数量 和 产品单价
	public List<Object> find_clicknumber(Map map){
		return baseDao.findForList("backSettlement.find_clicknumber", map);
	}
	
	//查询结算明细
	public List<Object> find_settlement(String com_id){
		return baseDao.findForList("backSettlement.find_settlement", com_id);
		
	}
	
	//修改结算状态
	public void update_state(String cs_id){
		 baseDao.update("backSettlement.update_settlestate", cs_id);
	}
}

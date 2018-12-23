package com.dd.supermarket.dao.app.bank;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;

@Repository("appBankCardDao")
public class AppBankCardDao {
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	/**
	 * 查询银行卡信息
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> find_BankCard()throws Exception{
		return baseDao.findForList("appBankCard.find_BankCard", null);
	}
	
	//点击量加1
	public void update_click(String bc_id){
		baseDao.update("appBankCard.update_click", bc_id);
	}
}

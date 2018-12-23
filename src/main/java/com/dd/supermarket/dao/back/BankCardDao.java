package com.dd.supermarket.dao.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;
import com.dd.supermarket.utils.PageData;

@Repository("bankCardDao")
public class BankCardDao {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//查询信用卡信息
	public List<PageData> find_bankCard(PageData pd){
		return baseDao.findForList("backBankCard.find_bankCard", pd);
	}
	
	//上下架
	public void upd_state(Map map){
		baseDao.update("backBankCard.upd_state", map);
	}
	
	//删除
	public void upd_isdisplay(String bc_id){
		baseDao.delete("backBankCard.upd_isdisplay", bc_id);
	}
	
	//添加
	public void save_bankCard(Map map){
		baseDao.save("backBankCard.save_bankCard", map);
	}
	
	//查询信用卡信息
	public Object findByIdCard(String bc_id){
		return baseDao.findOne("backBankCard.findByIdCard", bc_id);
	}
	
	//修改
	public void upd_card(Map map){
		baseDao.update("backBankCard.upd_card", map);
	}
		
		
}

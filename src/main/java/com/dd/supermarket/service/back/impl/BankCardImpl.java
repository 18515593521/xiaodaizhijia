package com.dd.supermarket.service.back.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.dao.back.BankCardDao;
import com.dd.supermarket.service.back.IBankCard;
import com.dd.supermarket.utils.PageData;

@Transactional
@Service("bankCardImpl")
public class BankCardImpl implements IBankCard{
	

	@Resource(name="bankCardDao")
	private BankCardDao bankCardDao;
	
	//查询信用卡信息
	public List<PageData> find_bankCard(PageData pd){
		return  bankCardDao.find_bankCard(pd);
	}
	
	//上下架
	public void upd_state(Map map){
		bankCardDao.upd_state(map);
	}
	
	//删除
	public void upd_isdisplay(String bc_id){
		bankCardDao.upd_isdisplay(bc_id);
	}
	
	//添加
	public void save_bankCard(Map map){
		bankCardDao.save_bankCard(map);
	}
	
	//查询信用卡信息
	public Object findByIdCard(String bc_id){
		return bankCardDao.findByIdCard(bc_id);
	}
	
	//修改
	public void upd_card(Map map){
		bankCardDao.upd_card(map);
	}

}

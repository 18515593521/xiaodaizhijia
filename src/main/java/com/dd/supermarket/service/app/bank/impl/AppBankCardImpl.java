package com.dd.supermarket.service.app.bank.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.controller.app.utils.PathFactory;
import com.dd.supermarket.dao.app.bank.AppBankCardDao;
import com.dd.supermarket.service.app.bank.IAppBankCard;
import com.dd.supermarket.utils.http.GetServer;
@Transactional
@Service("appBankCardImpl")
public class AppBankCardImpl implements IAppBankCard{
	
	@Resource(name="appBankCardDao")
	private AppBankCardDao appBankCardDao;
	
	/**
	 * 获取APP首页banner图
	 */
	public List<Map<String, Object>> find_BankCard(HttpServletRequest request) {
		List<Map<String, Object>> findBank = null;
		try {
			findBank = appBankCardDao.find_BankCard();
		} catch (Exception e) {
			System.out.println("=========== APP查询银行卡时出错 ===========");
			e.printStackTrace();
		}
		if(null == findBank)return new ArrayList<Map<String, Object>>();
		
		String serverUrl = new GetServer().getServerUrl(request);
		PathFactory pf = new PathFactory();
		for (Map<String, Object> fr : findBank) {
			fr = pf.settlBankFactory(serverUrl, fr);
		}
		return findBank;
	}
	
	//点击量加1
	public void update_click(String bc_id){
		appBankCardDao.update_click(bc_id);
	}
}

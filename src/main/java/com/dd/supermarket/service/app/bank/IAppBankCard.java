package com.dd.supermarket.service.app.bank;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface IAppBankCard {

	/**
	 * 查询银行卡信息
	 * @param request
	 * @return
	 */
	public List<Map<String,Object>> find_BankCard(HttpServletRequest request);
	
	//点击量加1
	public void update_click(String bc_id);
	
}

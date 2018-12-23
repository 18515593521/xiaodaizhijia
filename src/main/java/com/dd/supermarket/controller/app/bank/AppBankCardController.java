package com.dd.supermarket.controller.app.bank;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dd.supermarket.pojo.CodeMsg;
import com.dd.supermarket.pojo.ResultData;
import com.dd.supermarket.service.app.bank.IAppBankCard;

@Controller
@RequestMapping("/app/bankCard")
public class AppBankCardController {
	
	@Resource(name="appBankCardImpl")
	private IAppBankCard ibankcard;
	
	/**
	 * 查询银行卡信息
	 * @param file
	 * @return
	 */
	@RequestMapping("find_news")
	@ResponseBody
	public ResultData find_BankCard(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("list",ibankcard.find_BankCard(request));		
		return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
	}
	
	/**
	 * 点击量加1
	 * @param file
	 * @return
	 */
	@RequestMapping("update_click")
	@ResponseBody
	public void update_click(String bc_id) {
		ibankcard.update_click(bc_id);
	}
}

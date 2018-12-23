package com.dd.supermarket.controller.app.shell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dd.supermarket.controller.BaseController;
import com.dd.supermarket.pojo.CodeMsg;
import com.dd.supermarket.pojo.ResultData;
import com.dd.supermarket.pojo.Shell;
import com.dd.supermarket.service.app.shell.IOrder;
import com.dd.supermarket.service.app.shell.IShellCommodity;

@Controller
@RequestMapping("/app/shellOrder")
public class ShellOrderContrller extends BaseController{
	
	@Resource(name="shellOrderImpl")
	private IOrder iorder;
	
	@Resource(name="shellCommodityImpl")
	private IShellCommodity ishellCommodity;
	
	//有意向
	@RequestMapping("save_order")
	@ResponseBody
	public Shell save_order(String user_id,String com_id){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_id", user_id);
		map.put("com_id", com_id);
		List<Object> list = iorder.find_orderstate(map);
		if(list.size()!=0){
			return new Shell(CodeMsg.CODE_FAIL, null);
		}
		Map<String,Object> orderMap = new HashMap<String,Object>();
		orderMap.put("user_id", user_id);
		orderMap.put("com_id", com_id);
		orderMap.put("state", 1);
		iorder.save_order(orderMap);
		ishellCommodity.update(com_id);
		return new Shell(CodeMsg.CODE_SUCCESS, null);
	}
	
	//查询订单信息
	@RequestMapping("find_order")
	@ResponseBody
	public Shell find_order(String user_id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("orderList", iorder.find_order(user_id));				
		return new Shell(CodeMsg.CODE_SUCCESS, resultMap);
	}
}

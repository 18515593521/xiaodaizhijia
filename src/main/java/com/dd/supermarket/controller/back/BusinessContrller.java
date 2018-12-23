package com.dd.supermarket.controller.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.BaseController;
import com.dd.supermarket.service.back.IBusiness;
@Controller
@RequestMapping("back/business")
public class BusinessContrller extends BaseController{
	@Resource(name="backBusinessImpl")
	private IBusiness ibusiness;
	/**
	 * 查询商家合作信息
	 * 
	 * @return
	 */
	@RequestMapping("find_business")
	public String find_business(Model model){
		List<Object> list = ibusiness.find_business();
		model.addAttribute("list", list);
		return "back/html/cooperate";
	}
	
	
	/**
	 * 商家合作添加备注
	 * 
	 * @return
	 */
	@RequestMapping("update_business")
	@ResponseBody
	public JSONObject update_business(){
		JSONObject json = new JSONObject();
		ibusiness.update_business(super.getPageData());
		json.put("result", 1);
		return json;
	}
}

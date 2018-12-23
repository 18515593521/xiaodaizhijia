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
import com.dd.supermarket.service.back.ICommodity;
import com.dd.supermarket.service.back.IConsole;

@Controller
@RequestMapping("back/console")
public class ConsoleContrller extends BaseController{
	@Resource(name="consoleServiceImpl")
	private IConsole iconsole;
	
	@RequestMapping("register")
	public String register_total(Model model){
		
		int num1 = iconsole.yesterday_registernum();
		int num2 = iconsole.register_total();
		int num3 = iconsole.today_registernum();
		
		int find_loginlog = iconsole.find_loginlog();
		int find_yesterdayloginlog = iconsole.find_yesterdayloginlog();
		int todayloginlog = iconsole.find_todayloginlog();
		
		int comnum = iconsole.find_comnum();
		int comtotalnum = iconsole.find_comtotalnum();
		
		Map<String, Object> map1 = iconsole.find_settlement();
		Map<String, Object> map2 = iconsole.find_ydsettlement();
		
		Map<String, Object> map3 = iconsole.find_Unsettled();
		
		model.addAttribute("num1", num1);
		model.addAttribute("num2", num2);
		model.addAttribute("num3", num3);
		model.addAttribute("find_loginlog", find_loginlog);
		model.addAttribute("find_yesterdayloginlog", find_yesterdayloginlog);
		model.addAttribute("todayloginlog", todayloginlog);
		model.addAttribute("comnum", comnum);
		model.addAttribute("comtotalnum", comtotalnum);
		
		model.addAttribute("map1", map1);
		model.addAttribute("map2", map2);
		model.addAttribute("map3", map3);
		return "back/html/index";
	}
	
	@RequestMapping("find_UserRegister")
	@ResponseBody
	public JSONObject find_UserRegister(){
		List<Object> list = iconsole.find_UserRegister();
		JSONObject json = new JSONObject();
		json.put("list", list);
		return json;
	}
	
	@RequestMapping("find_UserSettlement")
	@ResponseBody
	public JSONObject find_UserSettlement(){
		List<Object> list = iconsole.find_UserSettlement();
		JSONObject json = new JSONObject();
		json.put("list", list);
		return json;
	}
	
	
	
}

package com.dd.supermarket.controller.weChat.menu;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.BaseController;
import com.dd.supermarket.controller.weChat.util.MenuUtil;


@Controller
@RequestMapping("wechat/menu")
public class MenuController extends BaseController{
	/**
	 * 创建菜单
	 * */
	@RequestMapping("createMenu")
	@ResponseBody
	public String createMenu(){
		System.out.println("开始创建菜单！");
		int status = MenuUtil.createMenu(MenuUtil.getMenu());
		if(status==0){
			System.out.println("菜单创建成功！");
			return "SUCCESS";
		}else{
			System.out.println("菜单创建失败！");
			return "FAIL";
		}
	}
	/**
	 * 查询菜单
	 * @return JSONObject
	 */
	@RequestMapping("findMenu")
	@ResponseBody
	public JSONObject findMenu(){
		JSONObject json = new JSONObject();
		json = MenuUtil.find_wechatMenu();
		return json;
	}
	
}

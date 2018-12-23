package com.dd.supermarket.controller.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.BaseController;
import com.dd.supermarket.service.back.IAdmin;
import com.dd.supermarket.service.back.ILog;
import com.dd.supermarket.utils.PageData;
import com.dd.supermarket.utils.UuidUtil;

@Controller
@RequestMapping("back/admin")
public class AdminContrller extends BaseController{
	
	@Resource(name="backAdminImpl")
	private IAdmin iadmin;
	

	@Resource(name="backLogImpl")
	private ILog ilog;
	
	@RequestMapping("find_role")
	@ResponseBody
	public JSONObject find_role(){
		List<Object> listrole = iadmin.find_role();
		JSONObject json = new JSONObject();
		json.put("listrole", listrole);
		return json;
	}
	
	@RequestMapping("save_admin")
	@ResponseBody
	public JSONObject save_admin(String sys_sa_id) {
		JSONObject json = new JSONObject();
		if(sys_sa_id!=null && sys_sa_id!=""){
			iadmin.update_admin(super.getPageData());	
			json.put("result", 1);
		}else{
			iadmin.save_admin(super.getPageData());		
			json.put("result", 1);
		}
		return json;
	}
	
	@RequestMapping("save_admins")
	@ResponseBody
	public JSONObject save_admin(String loginName, String password) {
		PageData pd = super.getPageData();
		pd.put("sys_sa_loginname", loginName);
		pd.put("sys_sa_name", UuidUtil.get32UUID());
		pd.put("sys_sa_phone", "");
		pd.put("sys_sa_password", password);
		pd.put("sys_sr_id", "c40d457ad7004c79a5eed5416ac352f6");
		JSONObject json = new JSONObject();
		iadmin.save_admin(pd);		
		json.put("result", 1);
		return json;
	}
	
	//查询管理员
	@RequestMapping("find_admin")
	public String find_admin(Model model,String sys_sa_name) {
		List<Object> adminlist = iadmin.find_admin(sys_sa_name); 
		model.addAttribute("adminlist", adminlist);
		return "back/html/admin";
	}
	
	//删除管理员
	@RequestMapping("del_admin")
	@ResponseBody
	public JSONObject del_admin(String sys_sa_id) {
		JSONObject json = new JSONObject();
		iadmin.del_admin(sys_sa_id);		
		json.put("result", 1);
		return json;
	}
	
	//修改管理员
	@RequestMapping("update_admin")
	@ResponseBody
	public JSONObject update_admin(){
		JSONObject json = new JSONObject();
		iadmin.save_admin(super.getPageData());
		json.put("result", 1);
		return json;
	}
}

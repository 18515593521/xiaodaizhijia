package com.dd.supermarket.controller.back;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.dd.supermarket.service.back.ILog;
import com.dd.supermarket.service.back.IPermissions;
import com.dd.supermarket.service.back.impl.PermissionsImpl;
import com.dd.supermarket.utils.PageData;
import com.dd.supermarket.utils.UuidUtil;

@Controller
@RequestMapping("back/permissions")
public class PermissionsContrller extends BaseController{
	
	@Resource(name="backDictionarysImpl")
	private IPermissions permissions;
	
	@Resource(name="backLogImpl")
	private ILog ilog;
	
	//查询全部菜单
	@RequestMapping("find_allMenu")
	@ResponseBody
	public JSONObject find_allMenu() {
		List<Object> menu = permissions.find_allMenu();
		JSONObject json = new JSONObject();
		json.put("menu", menu);
		return json;
	}
	
	//添加角色和权限
	@RequestMapping("saveUpdate_role")
	@ResponseBody
	public String saveUpdate_role(String sys_sr_name,String strgetSelectValue,String sr_id) {
		if(sr_id==""||sr_id==null){
			Map<String, Object> maprole = new HashMap<String, Object>();
			maprole.put("sys_sr_name", sys_sr_name);
			maprole.put("sys_sr_id", UuidUtil.get32UUID());
			String roleId  = maprole.get("sys_sr_id").toString();
			List<Map> list = new ArrayList<Map>();
		    String a[] = strgetSelectValue.split(","); 
		    for (int i = 0; i < a.length; i++) {
		    	Map<String,String> map = new HashMap<String,String>();
		    	map.put("sys_sp_id", UuidUtil.get32UUID());
		    	map.put("sys_sr_id", roleId);
		    	map.put("sys_sm_id", a[i]);
		    	list.add(map);
				
			}
		   permissions.save_role(maprole,list);
		}else{
			List<Map> list = new ArrayList<Map>();
		    String a[] = strgetSelectValue.split(","); 
		    for (int i = 0; i < a.length; i++) {
		    	Map<String,String> map = new HashMap<String,String>();
		    	map.put("sys_sp_id", UuidUtil.get32UUID());
		    	map.put("sys_sr_id", sr_id);
		    	map.put("sys_sm_id", a[i]);
		    	list.add(map);
				
			}
			permissions.update_role(sys_sr_name, sr_id,list);
		}
		
		return "";	
	}
	
	//查询角色权限
	@RequestMapping("find_allrRolePower")
	public String find_allrRolePower(Model model){
		List<Object> listRole = permissions.find_allrRolePower();
		model.addAttribute("listRole", listRole);
		return "back/html/role";//自动拼接.html
	}
	
	//删除角色
	@RequestMapping("del_rolePower")
	@ResponseBody
	public JSONObject del_rolePower(String sys_sr_id){	
		JSONObject json = new JSONObject();
		permissions.del_rolePower(sys_sr_id);
		json.put("result", 1);
		return json;
	}
	
	//根据角色id查询权限
	@RequestMapping("find_powerByid")
	@ResponseBody
	public JSONObject find_powerByid(String sys_sr_id){
		JSONObject json = new JSONObject();
		List<Object> list = permissions.find_powerByid(sys_sr_id);
		json.put("list",list);
		return json;
	}
		
}

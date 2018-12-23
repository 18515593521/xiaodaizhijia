package com.dd.supermarket.controller.back;

import java.util.HashMap;
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
import com.dd.supermarket.service.back.ICommodity;
import com.dd.supermarket.service.back.ILog;
import com.dd.supermarket.service.back.ISupplier;

@Controller
@RequestMapping("back/supplier")
public class SupplierContrller extends BaseController{
	@Resource(name="backSupplierImpl")
	private ISupplier suplier;
	
	@Resource(name="backAdminImpl")
	private IAdmin iadmin;
	
	@Resource(name="backCommodityImpl")
	private ICommodity icommodity;
	
	@Resource(name="backLogImpl")
	private ILog ilog;
	
	@RequestMapping("save_supplier")
	@ResponseBody
	public JSONObject save_supplier(String ci_id) {
		HttpSession session = super.getRequest().getSession();
		JSONObject json = new JSONObject();
		if(ci_id!="" && ci_id!=null){
			ilog.save_log(session, "修改供应商",3);
			suplier.update_supplier(super.getPageData());
			json.put("result", 1);
		}else{
			ilog.save_log(session, "新增供应商",3);
			suplier.save_supplier(super.getPageData());
			json.put("result", 1);
		}
		
		return json;
	}
	
	//查询公司信息
	@RequestMapping("find_supplier")
	public String find_supplier(String ci_username,String ci_name,String state,Model model) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("ci_name", ci_name);
		map.put("ci_username", ci_username);
		map.put("sys_sa_id", state);
		List<Object> listrole = suplier.find_admin();
		List<Object> listsupplier = suplier.find_supplier(map);
		model.addAttribute("listsupplier", listsupplier);
		model.addAttribute("listrole", listrole);
		return "back/html/company";
	}
	
	//删除供应商
	@RequestMapping("del_supplier")
	@ResponseBody
	public JSONObject del_supplier(String ci_id){
		JSONObject json = new JSONObject();
		suplier.del_supplier(ci_id);
/*		icommodity.del_comm(ci_id);*/
		//添加日志
		HttpSession session = super.getRequest().getSession();
		ilog.save_log(session, "删除供应商",3);
		json.put("result", 1);
		return json;
	}
	
	//根据ID查询供应商
	@RequestMapping("findByIdSupplier")
	@ResponseBody
	public JSONObject findByIdSupplier(String ci_id){
		List<Object> list = suplier.findByIdSupplier(ci_id);
		JSONObject json = new JSONObject();
		json.put("list", list);
		return json;
	}
}

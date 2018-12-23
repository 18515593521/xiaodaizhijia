package com.dd.supermarket.controller.h5;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.BaseController;
import com.dd.supermarket.controller.weChat.util.ParamsED;
import com.dd.supermarket.pojo.ResultData;
import com.dd.supermarket.service.app.home.IAppHome;
import com.dd.supermarket.service.app.user.IAppUserinfo;
import com.dd.supermarket.service.h5.IH5Home;
import com.dd.supermarket.service.h5.impl.H5HomeImpl;
import com.dd.supermarket.utils.PageData;
import com.dd.supermarket.utils.secretly.DESUtil;


@Controller
@RequestMapping("/h5/home")
public class H5HomeController extends BaseController{
	@Resource(name="appHomeImpl")
	private IAppHome iah;
	
	@Resource(name="appUserinfoImpl")
	private IAppUserinfo iau;
	
	@Resource(name="h5homeImpl")
	private IH5Home ih5home;
	
	
	//根据产品标签（下款最快，利息超低，）查询信息
	@RequestMapping("getHomeDataById")
	@ResponseBody
	public JSONObject getHomeDataById(HttpServletRequest request,String lable_dic_id) {
		if(lable_dic_id==""){
			lable_dic_id=null;
		}
		JSONObject json = new JSONObject();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		json.put("resultMap", ih5home.getHomeDataById(request,lable_dic_id));
		return json;
	}
	
	//根据商品id查询详细信息
	@RequestMapping("find_commodityDetails")
	@ResponseBody
	public JSONObject find_commodityDetails(HttpServletRequest request,String com_id) {	
		JSONObject json = new JSONObject();
		Map<String, Object> resultMap = new HashMap<String, Object>();	
		json.put("resultMap", iah.find_commodityDetails(request,"",com_id));//商品详细信息
		iau.add_userFootprint("", com_id);	//添加用户足迹
		return json;
	}
	
	//添加申请人信息
	@RequestMapping("save_h5apply")
	@ResponseBody
	public JSONObject save_h5apply(String com_id,String user,String phone,String idNum) {
		JSONObject json = new JSONObject();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("com_id", com_id);
		map.put("user_name", user);
		map.put("user_phone", phone);
		map.put("ID_number", idNum);
		ih5home.save_h5apply(map);
		json.put("result", 1);
		return json;
	}
	
	@RequestMapping("encryption")
	@ResponseBody
	public JSONObject encryption(){
		JSONObject code = new JSONObject();
		PageData pd = this.getPageData();
		PageData result = new PageData();
		try {
			result.put("str", DESUtil.SECRET_KEY);//秘钥
			result.put("ciphertext", ParamsED.getParamsED().getSecret_key());//密文
			code.put("code", 1);
		} catch (Exception e) {
			e.printStackTrace();
			code.put("code", 0);
		}
		code.put("result", result);
		return code;
	}
	
}

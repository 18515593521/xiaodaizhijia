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
import com.dd.supermarket.service.back.IDictionary;
import com.dd.supermarket.utils.PageData;
import com.github.pagehelper.PageInfo;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月19日 上午13:56:05 <br/>
* 类说明：所有Controller均需要继承BaseController
*/
@Controller
@RequestMapping("back/diction")
public class DictionaryContrller extends BaseController{
	
	
	
	
	
	//http://127.0.0.1:8080/supermarket/test/world   				可以看到freemaker基本语法的展示效果（在TestController中  死值）
	//http://127.0.0.1:8080/supermarket/back/diction/find_diction	从数据库中读取数据展示
	
	
	
	
	
	
	
	@Resource(name="backDictionaryImpl")
	private IDictionary iDiction;
	
	@RequestMapping("save_diction")
	@ResponseBody
	public JSONObject save_diction() {
		JSONObject json = new JSONObject();
		iDiction.save_diction(super.getPageData());//super.getPageData()中包含了form表单或者ajax提交的参数。只要键值对名字与xml定义的一致，则可以直接调用该方法传参到xml中操作数据库
		
		json.put("result", 1);
		return json;
	}
	
	@RequestMapping("del_diction")
	@ResponseBody
	public JSONObject del_diction() {
		PageData pd = super.getPageData();//super.getPageData()中包含了form表单或者ajax提交的参数。 PageData继承自Map
		
		JSONObject json = new JSONObject();
		iDiction.del_diction(pd.getString("dic_id"));	//可通过getString("key")的方式获得提交的键值
		json.put("result", 1);
		return json;
	}
	
	@RequestMapping("update_diction")
	@ResponseBody
	public JSONObject update_diction() {
		JSONObject json = new JSONObject();
		iDiction.update_diction(super.getPageData());
		json.put("result", 1);
		return json;
	}

	@RequestMapping("find_diction")
	@ResponseBody
	public JSONObject find_diction(Model model,String dic_id) {
		
		List<Map<String,Object>> dictionList = iDiction.find_dictionByType("commodityCharacteris");
		model.addAttribute("dics", dictionList);
		System.out.println(dictionList);
		
		return new JSONObject();//自动拼接.html
	}
}

package com.dd.supermarket.controller.back;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.BaseController;
import com.dd.supermarket.service.back.ICollectionService;
import com.dd.supermarket.service.back.ICommodity;
import com.dd.supermarket.service.back.IUser;
import com.dd.supermarket.utils.PageData;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("back/user")
public class UserContrller extends BaseController{
	@Resource(name="backUserImpl")
	private IUser iuser;
	
	@Resource(name="channelServiceImpl")
	private ICollectionService collectionService;
	
	@ResponseBody
	@RequestMapping("find_user")
	public JSONObject find_user(){
		PageData pd = new PageData();
		pd = this.getPageData();
		String st = (String) pd.get("selectDate");
		String cn_title = (String) pd.get("cn_title");
		if (null != st && !"".equals(st)) {
			String[] str =st.split(" - ");
			for (int i = 0; i < str.length; i++) {
				if (i==0) {
					pd.put("startTime", str[i]+" 00:00:00");
				}else{
					pd.put("endtTime", str[i]+" 23:59:59");
				}
			}
		}
		this.getPager();
		List<PageData> userlist = iuser.find_user(pd);
		PageInfo pageInfo = this.getPageInfo(userlist);
		JSONObject resultJson = this.getResultJson(userlist, pageInfo);
		return resultJson;
	}
	
	@ResponseBody
	@RequestMapping("findCnName")
	public JSONObject findCnName(){
		JSONObject json = new JSONObject();
		List<Object> list = collectionService.findCnName();
		json.put("list", list);
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping("find_phone")
	public JSONObject find_phone(){
		JSONObject json = new JSONObject();
		/*String phones = iuser.find_phone();*/
		json.put("list", 1);
		return json;
	}
}

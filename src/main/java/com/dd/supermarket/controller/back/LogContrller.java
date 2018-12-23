package com.dd.supermarket.controller.back;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.BaseController;
import com.dd.supermarket.service.back.IDictionary;
import com.dd.supermarket.service.back.ILog;
import com.dd.supermarket.utils.PageData;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("back/log")
public class LogContrller extends BaseController{
	@Resource(name="backLogImpl")
	private ILog ilog;
	
	@ResponseBody
	@RequestMapping("find_log")
	public JSONObject find_log(){
		PageData pd = new PageData();
		pd = this.getPageData();
		String sys_sa_name = (String) pd.get("sys_sa_name");
		String create_time = (String) pd.get("create_time");
		String[] time = create_time.split(" - ");
		if(time.length > 0 && !time[0].trim().equals("")){
			String dt1=time[0];
			String dt2=time[1];
			pd.put("dt1", dt1);
			pd.put("dt2", dt2);
		}
		
		pd.put("sys_sa_name", sys_sa_name);
		
		this.getPager();
		List<PageData> list = ilog.find_log(pd);
		PageInfo pageInfo = this.getPageInfo(list);
		JSONObject resultJson = this.getResultJson(list, pageInfo);
		return resultJson;
	}
}

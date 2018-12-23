package com.dd.supermarket.controller.back;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.BaseController;
import com.dd.supermarket.service.back.IFlow;
import com.dd.supermarket.service.back.ILog;
import com.dd.supermarket.utils.PageData;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("back/flow")
public class FlowContrller extends BaseController{
	
	@Resource(name="backFlowImpl")
	private IFlow iflow;
	
	@ResponseBody
	@RequestMapping("find_flow")
	public JSONObject find_flow(){
		PageData pd = new PageData();
		pd = this.getPageData();
		String com_name = (String) pd.get("com_name");
		String ci_name = (String) pd.get("ci_name");
		String time = (String) pd.get("selectDate");
		pd.put("comname", com_name);
		pd.put("ciname", ci_name);
		pd.put("time", time);
		this.getPager();
		List<PageData> list = iflow.find_flow(pd);
		PageInfo pageInfo = this.getPageInfo(list);
		JSONObject resultJson = this.getResultJson(list, pageInfo);
		return resultJson;
	}
}

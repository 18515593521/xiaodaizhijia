package com.dd.supermarket.controller.back;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.BaseController;
import com.dd.supermarket.controller.app.utils.PathFactory;
import com.dd.supermarket.service.back.IFeedback;
import com.dd.supermarket.utils.PageData;
import com.dd.supermarket.utils.http.GetServer;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("back/feedback")
public class FeedbackContrller extends BaseController{
	@Resource(name="backFeedbackImpl")
	private IFeedback ifeedback;
	
	@ResponseBody
	@RequestMapping("find_feedback")
	public JSONObject find_feedback(){
		PageData pd = new PageData();
		pd = this.getPageData();
		String user_name = (String) pd.get("user_name");
		String user_phone = (String) pd.get("user_phone");
		
		pd.put("user_name", user_name);
		pd.put("user_phone", user_phone);
		this.getPager();
		List<PageData> list = ifeedback.find_feedback(pd);
		//意见反馈图片地址处理
		PathFactory pf = new PathFactory();
		
		HttpServletRequest request = super.getRequest();
		String serverUrl = new GetServer().getServerUrl(request);
		
		for (int i = 0; i < list.size(); i++) {
			PageData pdata=list.get(i);
			list.set(i, (PageData)pf.feedbackFactory(serverUrl,pdata));
		}
		
		PageInfo pageInfo = this.getPageInfo(list);
		JSONObject resultJson = this.getResultJson(list, pageInfo);
		return resultJson;
	}
}

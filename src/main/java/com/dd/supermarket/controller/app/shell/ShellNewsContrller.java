package com.dd.supermarket.controller.app.shell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dd.supermarket.controller.BaseController;
import com.dd.supermarket.pojo.CodeMsg;
import com.dd.supermarket.pojo.Shell;
import com.dd.supermarket.service.app.shell.IShellNews;

@Controller
@RequestMapping("/app/shellNews")
public class ShellNewsContrller extends BaseController{
	@Resource(name="shellNewsImpl")
	private IShellNews ishellNews;
	
	@RequestMapping("find_news")
	@ResponseBody
	//查询消息
	public Shell find_news(String user_id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Object> newslist = ishellNews.find_news(super.getRequest(),user_id);
		resultMap.put("comList", newslist);				
		return new Shell(CodeMsg.CODE_SUCCESS, resultMap);	
	}
}

package com.dd.supermarket.controller.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月22日 上午10:05:03 <br/>
* 类说明：
*/
@Controller
@RequestMapping("/app/userinfo")
@Api(value="用户Controller",tags = "用户信息类")
public class APPUserinfo {
	
	@ResponseBody
	@RequestMapping(value= "getJson",method=RequestMethod.POST)
    @ApiOperation(value = "获取用户信息", 
    notes = "根据id获取用户信息"
    )
	@ApiImplicitParams({
		@ApiImplicitParam(name="name",value="用户名",dataType="String"),
		@ApiImplicitParam(name="age",value="年龄",dataType="Integer")
	})
	public JSONObject getJson(){
		JSONObject json = new JSONObject();
		json.put("result", "hello");
		return json;
	}
	
	public void ss(){
		
	}
}

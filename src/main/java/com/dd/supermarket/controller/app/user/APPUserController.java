package com.dd.supermarket.controller.app.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.app.utils.ParameterED;
import com.dd.supermarket.pojo.CodeMsg;
import com.dd.supermarket.pojo.ResultData;
import com.dd.supermarket.service.app.home.IAppHome;
import com.dd.supermarket.service.app.shell.IShellNews;
import com.dd.supermarket.service.app.user.IAppUserinfo;
import com.dd.supermarket.utils.IsEmpty;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月28日 下午1:32:15 <br/>
* 类说明：		用户信息
*/
@Controller
@RequestMapping("/app/user")
@Api(value = "我的接口文档", tags = "我的接口文档")
public class APPUserController {

	@Resource(name="appUserinfoImpl")
	private IAppUserinfo iau;
	
	@Resource(name="appHomeImpl")
	private IAppHome iah;
	
	@Resource(name="shellNewsImpl")
	private IShellNews ishellNews;
	
	/**
	 * 用户足迹
	 * @param request
	 * @return
	 */
	@PostMapping(value = "getFootprint")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "data", 
					value = ""
							+ "密文{\"user_id\":\"0009cb29f63348c59a38f73c1a7d2671\","
							+ "			\"page\":0(首页为0),"
							+ "			\"pageSize\":7(数据行数,该键值不填写时,默认为999)}<br>", 
					paramType = "form", 
					required = true, 
					dataType = "String") 
	})
	@ResponseBody
	@ApiOperation(value = "浏览用户足迹",notes = ""
			+ "返回code码<br>"
			+ "-1：系统繁忙<br>"
			+ "0：请求成功<br>"
			+ "10000：参数不合法<br>"
			+ "10001：参数缺失<br>"
			+ "10002：密文无法解析<br>"
			+ "<br>"
			)
	public ResultData getFootprint(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String data = request.getParameter("data");
		if(IsEmpty.isEmpty(data))return new ResultData(CodeMsg.CODE_10001, resultMap);				//参数值传入缺失
		JSONObject dataJson = ParameterED.parameterDecryption(data);
		if(null==dataJson)return new ResultData(CodeMsg.CODE_10002, resultMap);						//密文无法解析
		//----------------------------------------------------------------------------------------------------------------------------------------
		if(!dataJson.containsKey("user_id"))return new ResultData(CodeMsg.CODE_10001, resultMap);		//参数缺失
		
		if(!dataJson.containsKey("page"))return new ResultData(CodeMsg.CODE_10001, resultMap);			//参数缺失
		int page = dataJson.getInteger("page");
		
		int pageSize = 999;//由于不考虑分页，默认设置为999条
		if(dataJson.containsKey("pageSize"))pageSize=dataJson.getInteger("pageSize");			//参数缺失
		
		String user_id = dataJson.getString("user_id");
		//----------------------------------------------------------------------------------------------------------------------------------------
		resultMap.put("footprintList", iau.find_userFootprintByUserId(request,user_id,page,pageSize));
		resultMap.put("footprintCount", iau.find_userFootprintCountByUserId(user_id));
		return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
	}
	
	
	/**
	 * 商务合作
	 * @param request
	 * @return
	 */
	@PostMapping(value = "cooperation")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "data", 
					value = ""
							+ "密文{\"user_id\":\"0009cb29f63348c59a38f73c1a7d2671\","
							+ "			\"email\":\"jyoudai@jyouhua.com\","
							+ "			\"phone\":\"18600001111\","
							+ "			\"companyName\":\"合作公司\","
							+ "			\"notes\":\"合作细则\"}<br>", 
					paramType = "form", 
					required = true, 
					dataType = "String") 
	})
	@ResponseBody
	@ApiOperation(value = "商务合作",notes = ""
			+ "返回code码<br>"
			+ "-1：系统繁忙<br>"
			+ "0：请求成功<br>"
			+ "10000：参数不合法<br>"
			+ "10001：参数缺失<br>"
			+ "10002：密文无法解析<br>"
			+ "<br>"
			)
	public ResultData cooperation(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String data = request.getParameter("data");
		if(IsEmpty.isEmpty(data))return new ResultData(CodeMsg.CODE_10001, resultMap);				//参数值传入缺失
		JSONObject dataJson = ParameterED.parameterDecryption(data);
		if(null==dataJson)return new ResultData(CodeMsg.CODE_10002, resultMap);						//密文无法解析
		//----------------------------------------------------------------------------------------------------------------------------------------
		if(!dataJson.containsKey("user_id"))return new ResultData(CodeMsg.CODE_10001, resultMap);		//参数缺失
		String user_id = dataJson.getString("user_id");
		
		if(!dataJson.containsKey("email"))return new ResultData(CodeMsg.CODE_10001, resultMap);			//参数缺失
		String email = dataJson.getString("email");
		
		if(!dataJson.containsKey("phone"))return new ResultData(CodeMsg.CODE_10001, resultMap);			//参数缺失
		String phone = dataJson.getString("phone");
		
		if(!dataJson.containsKey("companyName"))return new ResultData(CodeMsg.CODE_10001, resultMap);	//参数缺失
		String companyName = dataJson.getString("companyName");
		
		if(!dataJson.containsKey("notes"))return new ResultData(CodeMsg.CODE_10001, resultMap);			//参数缺失
		String notes = dataJson.getString("notes");
		//----------------------------------------------------------------------------------------------------------------------------------------
		
		String resultCode = "";
		resultCode = iau.save_cooperation(user_id,email,phone,companyName,notes);
		switch (resultCode) {
		case "-1":
			return new ResultData(CodeMsg.CODE_FAIL, resultMap);
		}
		return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
	}
	
	/**
	 * 关于我们
	 * @param request
	 * @return
	 */
	@PostMapping(value = "aboutUS")
/*	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "data", 
					value = ""
							+ "密文{\"user_id\":\"0009cb29f63348c59a38f73c1a7d2671\","
							+ "			\"page\":0(首页为0),"
							+ "			\"pageSize\":7(数据行数,该键值不填写时,默认为999)}<br>", 
					paramType = "form", 
					required = true, 
					dataType = "String") 
	})*/
	@ResponseBody
	@ApiOperation(value = "关于我们",notes = ""
			+ "返回code码<br>"
			+ "-1：系统繁忙<br>"
			+ "0：请求成功<br>"
			+ "<br>"
			)
	public ResultData aboutUS(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap = iau.getAboutUSData(request);
		return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
	}
	
	
	/**
	 * 问题反馈
	 * @param request
	 * @return
	 */
	@PostMapping(value = "problemFeedback")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "data", 
					value = "密文{\"user_id\":\"0009cb29f63348c59a38f73c1a7d2671\",\"content\":\"反馈内容\"}",  
					paramType = "form", 
					required = true, 
					dataType = "String") 
	})
	@ResponseBody
	@ApiOperation(value = "问题反馈",notes = ""
			+ "返回code码<br>"
			+ "-1：系统繁忙<br>"
			+ "0：请求成功<br>"
			+ "10000：参数不合法<br>"
			+ "10001：参数缺失<br>"
			+ "10002：密文无法解析<br>"
			+ "<br>"
			)
	public ResultData problemFeedback(HttpServletRequest request,
			@ApiParam(value = "反馈图片", required = false) MultipartFile file) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String data = request.getParameter("data");
		if(IsEmpty.isEmpty(data))return new ResultData(CodeMsg.CODE_10001, resultMap);					//参数值传入缺失
		JSONObject dataJson = ParameterED.parameterDecryption(data);
		if(null==dataJson)return new ResultData(CodeMsg.CODE_10002, resultMap);							//密文无法解析
		
		if(!dataJson.containsKey("user_id"))return new ResultData(CodeMsg.CODE_10001, resultMap);		//参数缺失
		String user_id = dataJson.getString("user_id");
		if(!dataJson.containsKey("content"))return new ResultData(CodeMsg.CODE_10001, resultMap);		//参数缺失
		String content = dataJson.getString("content");
		
		String logonCode = "";	//问题反馈结果
		logonCode = iau.add_userFeedback(request, user_id, content, file);
		switch (logonCode) {
		case "-1":
			return new ResultData(CodeMsg.CODE_FAIL, resultMap);
		}
		return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
	}
	
	/**
	 * 获取消息推送信息
	 * @param request
	 * @return
	 */
	@PostMapping(value = "getUserPush")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "data", 
					value = "密文{\"user_id\":\"0009cb29f63348c59a38f73c1a7d2671\",\"page\":0(首页为0),\"pageSize\":7(数据行数,该键值不填写时,默认为7)}<br>}",  
					paramType = "form", 
					required = true, 
					dataType = "String") 
	})
	@ResponseBody
	@ApiOperation(value = "用户消息推送",notes = ""
			+ "返回code码<br>"
			+ "-1：系统繁忙<br>"
			+ "0：请求成功<br>"
			+ "10000：参数不合法<br>"
			+ "10001：参数缺失<br>"
			+ "10002：密文无法解析<br>"
			+ "<br>"
			)
	public ResultData getUserPush(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String data = request.getParameter("data");
		if(IsEmpty.isEmpty(data))return new ResultData(CodeMsg.CODE_10001, resultMap);					//参数值传入缺失
		JSONObject dataJson = ParameterED.parameterDecryption(data);
		if(null==dataJson)return new ResultData(CodeMsg.CODE_10002, resultMap);							//密文无法解析
		
		if(!dataJson.containsKey("user_id"))return new ResultData(CodeMsg.CODE_10001, resultMap);		//参数缺失
		String user_id = dataJson.getString("user_id");
		
		if(!dataJson.containsKey("page"))return new ResultData(CodeMsg.CODE_10001, resultMap);			//参数缺失
		int page = dataJson.getInteger("page");
		
		int pageSize = 7;//默认数据大小为7
		if(dataJson.containsKey("pageSize"))pageSize=dataJson.getInteger("pageSize");			//参数缺失
		
		//0是false  1是true
		if(!dataJson.containsKey("shell"))return new ResultData(CodeMsg.CODE_10001, resultMap);			//参数缺失
		int shell = dataJson.getInteger("shell");
		
		//当前为1时,代表当前是壳，则返回壳所需消息列表=================================================================
		if(shell==1){
			resultMap.put("pushList", ishellNews.find_news(request, user_id));		//壳消息
			resultMap.put("newsCount", ishellNews.find_newsCount(user_id));			//壳消息总数
			return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
		}
		//===============================================================================================
		
		
		resultMap.put("pushList", iau.find_userPushByUserId(request, user_id, page, pageSize));		//消息
		resultMap.put("newsCount", iah.find_newsCount(user_id));			//消息总数
		return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
	}
	
	//检测app预申请量
		@PostMapping(value = "save_Apply")
		@ResponseBody
		public ResultData save_Apply(HttpServletRequest request) {
			String data = request.getParameter("data");
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if(IsEmpty.isEmpty(data))return new ResultData(CodeMsg.CODE_10001, resultMap);					//参数缺失
			JSONObject dataJson = ParameterED.parameterDecryption(data);
			if(null==dataJson)return new ResultData(CodeMsg.CODE_10002, resultMap);							//密文无法解析
			
			if(!dataJson.containsKey("user_id"))return new ResultData(CodeMsg.CODE_10001, resultMap);		//参数缺失
			String user_id = dataJson.getString("user_id");
			
			if(!dataJson.containsKey("com_id"))return new ResultData(CodeMsg.CODE_10001, resultMap);		//参数缺失
			String com_id = dataJson.getString("com_id");
			System.out.println(user_id);
			System.out.println(com_id);
			String code = iau.save_Apply(user_id, com_id);
			if("-1".equals(code)){
				return new ResultData(CodeMsg.CODE_FAIL, resultMap);
			}else{
				resultMap.put("aa_id", code);
			}
			return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
		}
	
}

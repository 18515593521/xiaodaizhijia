package com.dd.supermarket.controller.app.home;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.app.utils.ParameterED;
import com.dd.supermarket.pojo.CodeMsg;
import com.dd.supermarket.pojo.ResultData;
import com.dd.supermarket.service.app.home.IAppHome;
import com.dd.supermarket.service.app.user.IAppUserinfo;
import com.dd.supermarket.utils.IsEmpty;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月26日 下午2:58:45 <br/>
* 类说明：
*/
@Controller
@RequestMapping("/app/home")
@Api(value = "APP首页", tags = "首页所需数据")
public class APPHomeController {
	
	@Resource(name="appHomeImpl")
	private IAppHome iah;
	
	@Resource(name="appUserinfoImpl")
	private IAppUserinfo iau;
	

	@Resource(name="appUserinfoImpl")
	private IAppUserinfo iAppUserinfo;
	
	
	
	
	
	/**
	 * 首页获取数据接口
	 * @param request
	 * @return
	 */
	@PostMapping(value = "getHomeData")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "data", 
					value = ""
							+ "密文{\"user_id\":\"0009cb29f63348c59a38f73c1a7d2671\"}<br>", 
					paramType = "form", 
					required = true, 
					dataType = "String") 
	})
	@ResponseBody
	@ApiOperation(value = "首页获取数据接口文档",notes = ""
			+ "返回code码<br>"
			+ "-1：系统繁忙<br>"
			+ "0：请求成功<br>"
			+ "10000：参数不合法<br>"
			+ "10001：参数缺失<br>"
			+ "10002：密文无法解析<br>"
			+ "<br>"
			)
	public ResultData getHomeData(HttpServletRequest request) {
		String data = request.getParameter("data");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(IsEmpty.isEmpty(data))return new ResultData(CodeMsg.CODE_10001, resultMap);					//参数缺失
		JSONObject dataJson = ParameterED.parameterDecryption(data);
		if(null==dataJson)return new ResultData(CodeMsg.CODE_10002, resultMap);							//密文无法解析
		
//		if(!dataJson.containsKey("user_id"))return new ResultData(CodeMsg.CODE_10001, resultMap);		//参数缺失
//		String user_id = dataJson.getString("user_id");
		
		String user_id = "";
		String device_model = "";
		String appN_V = "";
		if(dataJson.containsKey("user_id"))user_id = dataJson.getString("user_id");	//获取用户手机型号
		if(dataJson.containsKey("appN_V"))appN_V = dataJson.getString("appN_V");	//获取app版本号
		if(dataJson.containsKey("device_model"))device_model = dataJson.getString("device_model");
		
		
		map.put("user_id", user_id);
		map.put("device_model", device_model);
		map.put("appN_V", appN_V);
		iAppUserinfo.up_appNV(map);   			//保存用户手机型号 app版本号
		
		String token = dataJson.getString("token");
		if(!"没有token".equals(token) ){
			map.put("device_token", token);
			iAppUserinfo.up_token(map);
		}
		//--------------------------------------------------------------------------------------------------------------------------
		resultMap.put("bannerList", iah.find_banner(request));				//轮播图
		resultMap.put("newsFlashList", iah.find_news_flashString());		//轮播消息
		resultMap.put("commodityList", iah.find_commodity(request, "default", 0, 100));//商品列表
		resultMap.put("readCount", iah.find_userReadCount(user_id));		//已读消息数
		resultMap.put("newsCount", iah.find_newsCount(user_id));			//消息总数
		
		return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
	}
	
	
	
	/**
	 * 获取商品列表
	 * @param request
	 * @return
	 */
	@PostMapping(value = "getCommodityList")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "data", 
					value = ""
							+ "密文{\"user_id\":\"0009cb29f63348c59a38f73c1a7d2671\","
							+ "			\"sort_rule\":\"默认：default,下款快：fastPayment,利息低：lowInterest,额度高：highAmount\","
							+ "			\"page\":0(首页为0),"
							+ "			\"pageSize\":7(数据行数,该键值不填写时,默认为999)}<br>", 
					paramType = "form", 
					required = true, 
					dataType = "String") 
	})
	@ResponseBody
	@ApiOperation(value = "获取商品列表",notes = ""
			+ "返回code码<br>"
			+ "-1：系统繁忙<br>"
			+ "0：请求成功<br>"
			+ "10000：参数不合法<br>"
			+ "10001：参数缺失<br>"
			+ "10002：密文无法解析<br>"
			+ "<br>"
			)
	public ResultData getCommodityList(HttpServletRequest request) {
		String data = request.getParameter("data");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(IsEmpty.isEmpty(data))return new ResultData(CodeMsg.CODE_10001, resultMap);					//参数缺失
		JSONObject dataJson = ParameterED.parameterDecryption(data);
		if(null==dataJson)return new ResultData(CodeMsg.CODE_10002, resultMap);							//密文无法解析
		
//		if(!dataJson.containsKey("user_id"))return new ResultData(CodeMsg.CODE_10001, resultMap);		//参数缺失
//		String user_id = dataJson.getString("user_id");
		
		if(!dataJson.containsKey("sort_rule"))return new ResultData(CodeMsg.CODE_10001, resultMap);		//参数缺失
		String sort_rule = dataJson.getString("sort_rule");
		
		if(!dataJson.containsKey("page"))return new ResultData(CodeMsg.CODE_10001, resultMap);			//参数缺失
		int page = dataJson.getInteger("page");
		
		int pageSize = 999;//由于不考虑分页，默认设置为999条
		if(dataJson.containsKey("pageSize"))pageSize=dataJson.getInteger("pageSize");			//参数缺失
		
		
		//--------------------------------------------------------------------------------------------------------------------------
		resultMap.put("commodityList", iah.find_commodity(request, sort_rule, page, pageSize));	//商品列表
		
		return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
	}
	
	/**
	 * 获取贷款分类商品
	 * @param request
	 * @return
	 */
	@PostMapping(value = "getCommodityCharacterisList")
	/*@ApiImplicitParams({
			@ApiImplicitParam(
					name = "data", 
					value = ""
							+ "密文{\"user_id\":\"0009cb29f63348c59a38f73c1a7d2671\","
							+ "			\"sort_rule\":\"默认：default,下款快：fastPayment,利息低：lowInterest,额度高：highAmount\","
							+ "			\"page\":0(首页为0),"
							+ "			\"pageSize\":7(数据行数,该键值不填写时,默认为999)}<br>", 
					paramType = "form", 
					required = true, 
					dataType = "String") 
	})*/
	@ResponseBody
	@ApiOperation(value = "获取贷款分类商品",notes = ""
			+ "返回code码<br>"
			+ "-1：系统繁忙<br>"
			+ "0：请求成功<br>"
			+ "<br>"
			)
	public ResultData getCommodityCharacterisList(HttpServletRequest request) {
//		String data = request.getParameter("data");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
//		if(IsEmpty.isEmpty(data))return new ResultData(CodeMsg.CODE_10001, resultMap);					//参数缺失
//		JSONObject dataJson = ParameterED.parameterDecryption(data);
//		if(null==dataJson)return new ResultData(CodeMsg.CODE_10002, resultMap);							//密文无法解析
//		
//		if(!dataJson.containsKey("sort_rule"))return new ResultData(CodeMsg.CODE_10001, resultMap);		//参数缺失
//		String sort_rule = dataJson.getString("sort_rule");
//		
//		if(!dataJson.containsKey("page"))return new ResultData(CodeMsg.CODE_10001, resultMap);			//参数缺失
//		int page = dataJson.getInteger("page");
//		
//		int pageSize = 999;//由于不考虑分页，默认设置为999条
//		if(dataJson.containsKey("pageSize"))pageSize=dataJson.getInteger("pageSize");			//参数缺失
		
		
		//--------------------------------------------------------------------------------------------------------------------------
		resultMap.put("CCList", iah.CommodityByCharacteris(request));	//商品列表
		
		return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
	}
	
	
	/**
	 * 商品详情
	 * @param request
	 * @return
	 */
	@PostMapping(value = "getCommodityDetails")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "data", 
					value = ""
							+ "密文{\"user_id\":\"0009cb29f63348c59a38f73c1a7d2671\",\"com_id\":\"(商品ID)0009cb29f63348c59a38f73c1a7d2671\"}<br>", 
					paramType = "form", 
					required = true, 
					dataType = "String") 
	})
	@ResponseBody
	@ApiOperation(value = "商品详情查看文档",notes = ""
			+ "返回code码<br>"
			+ "-1：系统繁忙<br>"
			+ "0：请求成功<br>"
			+ "10000：参数不合法<br>"
			+ "10001：参数缺失<br>"
			+ "10002：密文无法解析<br>"
			+ "<br>"
			)
	public ResultData getCommodityDetails(HttpServletRequest request) {
		String data = request.getParameter("data");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(IsEmpty.isEmpty(data))return new ResultData(CodeMsg.CODE_10001, resultMap);					//参数缺失
		JSONObject dataJson = ParameterED.parameterDecryption(data);
		if(null==dataJson)return new ResultData(CodeMsg.CODE_10002, resultMap);							//密文无法解析
		
		if(!dataJson.containsKey("user_id"))return new ResultData(CodeMsg.CODE_10001, resultMap);		//参数缺失
		String user_id = dataJson.getString("user_id");
		
		if(!dataJson.containsKey("com_id"))return new ResultData(CodeMsg.CODE_10001, resultMap);		//参数缺失
		String com_id = dataJson.getString("com_id");
		
		
		//--------------------------------------------------------------------------------------------------------------------------
		resultMap = iah.find_commodityDetails(request,user_id,com_id);			//商品详细信息
		iau.add_userFootprint(user_id, com_id);									//保存用户足迹
		return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
	}
}

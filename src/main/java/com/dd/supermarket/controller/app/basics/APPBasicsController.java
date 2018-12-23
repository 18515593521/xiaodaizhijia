package com.dd.supermarket.controller.app.basics;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.app.secretly.SecretKey;
import com.dd.supermarket.controller.app.utils.ParameterED;
import com.dd.supermarket.controller.app.utils.Print;
import com.dd.supermarket.pojo.CodeMsg;
import com.dd.supermarket.service.app.pool.IAppControl;
import com.dd.supermarket.utils.IsEmpty;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author 作者 ：   <br/>
 *         E-mail:   <br/>
 * @version 创建时间： 2018年6月22日 下午6:41:21 <br/>
 *          类说明：APP所需基础
 */
@Controller
@RequestMapping("/app/basics")
@Api(value = "APP所需基础数据", tags = "基础信息类")
public class APPBasicsController{
	
	
	@Resource(name="appControlImpl")
	private IAppControl iac;
	
	@PostMapping("pd")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "service", 
				value = "加密解密测试<br>"
				+ "pe/PE：加密<br>"
				+ "pd/PD：解密(必须有data这个数据)<br>", 
				paramType = "form", 
				required = true, 
				dataType = "String") })
	@ResponseBody
	@ApiOperation(value = "加密解密测试", notes = "加密解密测试<br>")
	public BaseData pd(HttpServletRequest request){
		Map<String,String[]> requestMap = request.getParameterMap();
		
		String service = requestMap.get("service")[0];
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject json = null;
		switch (service) {
		case "pe":
		case "PE":
			//加密
			System.out.println(requestMap);
			Map<String,Object> pdMap = new HashMap<String,Object>();
			for (Map.Entry<String, String[]> rm : requestMap.entrySet()) {
				pdMap.put(rm.getKey(), rm.getValue()[0]);
			}
			json = new JSONObject(pdMap);
			json.remove("service");
			String data = ParameterED.parameterEncryption(json);
			resultMap.put("data", data);
			break;
		case "pd":
		case "PD":
			//解密
			json = ParameterED.parameterDecryption(requestMap.get("data")[0]);
			resultMap = (Map<String,Object>)json;
			break;
		default:
			return new BaseData(CodeMsg.CODE_10000, null);//参数不合法
		}
		return new BaseData(CodeMsg.CODE_SUCCESS, resultMap);
	}
	
	/**
	 * APP基础信息接口
	 * @param file
	 * @return
	 */
	@PostMapping(value = "getBasics")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "service", 
					value = "获得密钥：(该参数不在此处说明)<br>"
					+ "获得开关：<br>"
					+ "(IOS：IOS_OFF_ON<br>"
					+ "Android：ANDROID_OFF_ON)<br>"
					+ "IOS开关加密钥:(该参数不在此处说明)<br>"
					+ "Android开关加密钥：(该参数不在此处说明)", 
					paramType = "form", 
					required = true, 
					dataType = "String") })
	@ResponseBody
	@ApiOperation(value = "获取APP运行所需基础数据", notes = "该接口为基础接口，传入不同service，得到不同的值。<br>")
	public BaseData getBasics(HttpServletRequest request) {
		String service = request.getParameter("service");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(IsEmpty.isEmpty(service))return new BaseData(CodeMsg.CODE_10001, resultMap);
		
		switch (service) {
		case "SECRET_INFORMATION":
			Print.println("获取密钥");
			resultMap = this.getSecretCiphertext();
			break;
		case "IOS_OFF_ON":
			Print.println("获取IOS开关");
			break;
		case "ANDROID_OFF_ON":
			Print.println("获取Android开关");
			break;
		//================= 开关加密钥 =================
		case "SI_IOS_OFF_ON":
			Print.println("获取密钥+IOS开关");
			resultMap = this.getSecretCiphertext();
			break;
		case "SI_ANDROID_OFF_ON":
			Print.println("获取密钥+Android开关");
			resultMap = this.getSecretCiphertext();
			break;
			
		default:
			return new BaseData(CodeMsg.CODE_10000, null);//参数不合法
		}
		return new BaseData(CodeMsg.CODE_SUCCESS, resultMap);
	}	
	//特殊返回类型，不加密
	class BaseData{
		private int code;
		private String msg;
		private Map<String,Object> data;
		
		public BaseData(CodeMsg codeMsg, Map<String,Object> resultMap) {
			this.code = codeMsg.getCode();
			this.msg = codeMsg.getMsg();
			this.data = resultMap;
		}
		public int getCode() {
			return this.code;
		}
		public String getMsg() {
			return this.msg;
		}
		public Map<String,Object> getData() {
			return this.data==null?new HashMap<String,Object>():this.data;
		}
		@Override
		public String toString() {
			return "Result [code=" + code + ", msg=" + msg + ", data=" + data + "]";
		}
	}
	/**
	 * 获取加密后的密钥(密文)
	 * @return
	 */
	private Map<String,Object> getSecretCiphertext(){
		String ciphertext = SecretKey.getSecretKeyClass().getCiphertext();//加密后的密钥(密文)
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("ciphertext", ciphertext);
		resultMap.put("iosVersion", iac.getIosVersion());				//获取IOS版本号
		resultMap.put("androidVersions", iac.getAndroidVersions());		//获取Android版本号
		return resultMap;
	}
	
	
	
	//密文模版
//	@PostMapping(value = "register")
//	@ApiImplicitParams({
//			@ApiImplicitParam(
//					name = "data", 
//					value = "密文", 
//					paramType = "form", 
//					required = true, 
//					dataType = "String") 
//	})
//	@ResponseBody
//	@ApiOperation(value = "注册", notes = "传入不同service得到不同结果集<br>")
//	public ResultData register(HttpServletRequest request) {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		String data = request.getParameter("data");
//		if(IsEmpty.isEmpty(data))return new ResultData(CodeMsg.CODE_10001, resultMap);
//		
//		return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
//	}

	
//	/**
//	 * 带文件上传例子
//	 * @param request
//	 * @param file
//	 * @return
//	 */
//	@PostMapping(value = "getBasics1")
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "data", value = "获得密钥：(该参数不在此处说明)", paramType = "form", required = true, dataType = "String") })
//	@ResponseBody
//	@ApiOperation(value = "获取APP运行所需基础数据", notes = "该接口为基础接口，传入不同参数，得到不同的值。<br>1")
//	public ResultData getBasics1(HttpServletRequest request,
//			@ApiParam(value = "上传文件", required = true) MultipartFile file) {
//		// JSONObject dataJson = ParameterED.parameterDecryption(data);
//		String data = request.getParameter("data");
//		System.out.println(file.toString());
//		System.out.println(data);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("key", "value");
//		return new ResultData(CodeMsg.CODE_SUCCESS, map);
//	}

}


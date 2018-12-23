package com.dd.supermarket.controller.app.login;

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
import com.dd.supermarket.controller.app.utils.Print;
import com.dd.supermarket.pojo.CodeMsg;
import com.dd.supermarket.pojo.ResultData;
import com.dd.supermarket.service.app.pool.IAppControl;
import com.dd.supermarket.service.app.user.IAppLogin;
import com.dd.supermarket.service.app.user.IAppUserinfo;
import com.dd.supermarket.service.back.ILoginLog;
import com.dd.supermarket.service.newMessage.ISendMessage;
import com.dd.supermarket.utils.IsEmpty;
import com.dd.supermarket.utils.secretly.SHAencrypt;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月23日 下午3:04:26 <br/>
* 类说明：登录、注册、修改密码
*/
@Controller
@RequestMapping("/app/login")
@Api(value = "登录、注册、修改密码", tags = "登录、注册、修改密码")
public class APPLoginController {
	
	@Resource(name="appLoginImpl")
	private IAppLogin appLoginImpl;
	
	@Resource(name="appControlImpl")
	private IAppControl iac;
	
	@Resource(name="loginLogServiceImpl")
	private ILoginLog iLoginLog;
	
	@Resource(name="appUserinfoImpl")
	private IAppUserinfo iAppUserinfo;
	
	
	/**
	 * 密码登录、验证码登录
	 * @param request
	 * @return
	 */
	@PostMapping(value = "logon")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "service", 
					value = ""
							+ "密码登录：PASSWORD <br>"
							+ "验证码登录：SMS_CODE(预留)<br>"
							+ "验证码登录：AUTHENTICATE_USER<br>", 
					paramType = "form", 
					required = true, 
					dataType = "String"),
			@ApiImplicitParam(
					name = "data", 
					value = ""
							+ "密码登录密文{\"phone\":\"18500001111\",\"password\":\"123456\"}<br>"
							+ "验证码登录密文{\"phone\":\"18500001111\",\"sms_code\":\"123456\"}<br>"
							+ "验证是否注册{\"phone\":\"18500001111\"}<br>", 
					paramType = "form", 
					required = true, 
					dataType = "String") 
	})
	@ResponseBody
	@ApiOperation(value = "密码登录、验证码登录",notes = ""
			+ "该接口为重置密码接口，传入不同service，得到不同的值。<br>"
			+ "返回code码<br>"
			+ "-1：系统繁忙<br>"
			+ "0：请求成功<br>"
			+ "10000：参数不合法<br>"
			+ "10001：参数缺失<br>"
			+ "10002：密文无法解析<br>"
			+ "<br>"
			+ "20001：验证码不正确<br>"
			+ "20002：该手机号已经注册过了(仅 AUTHENTICATE_USER 服务有该状态码)<br>"
			+ "20003：该手机号还未注册<br>"
			+ "20004：密码输入错误")
	public ResultData logon(HttpServletRequest request) {
		String service = request.getParameter("service");
		String data = request.getParameter("data");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(IsEmpty.isEmpty(service,data))return new ResultData(CodeMsg.CODE_10001, resultMap);		//参数缺失
		JSONObject dataJson = ParameterED.parameterDecryption(data);
		if(null==dataJson)return new ResultData(CodeMsg.CODE_10002, resultMap);						//密文无法解析
		
		if(!dataJson.containsKey("phone"))return new ResultData(CodeMsg.CODE_10001, resultMap);		//参数缺失
		String phone = dataJson.getString("phone");
		String token = dataJson.getString("token");
		System.out.println(token);
		String logonCode = "";	//登录结果code值
		Map<String, Object> map = new HashMap<String, Object>();
		resultMap.put("iosVersion", iac.getIosVersion());				//获取IOS版本号
		resultMap.put("androidVersions", iac.getAndroidVersions());		//获取Android版本号
		switch (service) {
		//--------------------------------------------------------------------------------------------------------------------------
		case "PASSWORD":
			if(!dataJson.containsKey("password"))return new ResultData(CodeMsg.CODE_10001, resultMap);	//参数缺失
			String password = dataJson.getString("password");
			
			logonCode = appLoginImpl.logonToPassword(phone, password);
			map.put("user_id", logonCode);
			switch (logonCode) { 
			case "-1":
				return new ResultData(CodeMsg.CODE_FAIL, resultMap);							//系统繁忙请稍后再试
			case "20003":
				return new ResultData(CodeMsg.CODE_20003, resultMap);							//该手机号还未注册！
			case "20004":
				return new ResultData(CodeMsg.CODE_20004, resultMap);							//密码输入错误！
			default:
				resultMap.put("user_id", logonCode);											//返回用户ID
				if(!"没有token".equals(token)){
					map.put("device_token", token);
					iAppUserinfo.up_token(map);
				}
				
				int num = (int) iLoginLog.find_llogbyid(map); 
				if(num==0){
					iLoginLog.add_loginlog(map);//记录用户登录时间
				}else{
					iLoginLog.upd_time(map);//修改用户登录时间
				}
				break;
			}
			break;
		//--------------------------------------------------------------------------------------------------------------------------
		case "SMS_CODE":
			if(!dataJson.containsKey("sms_code"))return new ResultData(CodeMsg.CODE_10001, resultMap);	//参数缺失
			String sms_code = dataJson.getString("sms_code");
			
			logonCode = appLoginImpl.logonToSMSCode(phone, sms_code);
			map.put("user_id", logonCode);
			switch (logonCode) {
			case "-1":
				return new ResultData(CodeMsg.CODE_FAIL, resultMap);							//系统繁忙请稍后再试
			case "20001":
				return new ResultData(CodeMsg.CODE_20001, resultMap);							//验证码输入错误
			case "20003":
				return new ResultData(CodeMsg.CODE_20003, resultMap);							//该手机号还未注册
			default:
				resultMap.put("user_id", logonCode);											//返回用户ID
				int num = (int) iLoginLog.find_llogbyid(map);
				if(num==0){
					iLoginLog.add_loginlog(map);
				}else{
					iLoginLog.upd_time(map);
				}
				break;
			}
			break;
		//--------------------------------------------------------------------------------------------------------------------------
		case "AUTHENTICATE_USER":
			logonCode = appLoginImpl.authenticateUserByPhone(phone);
			map.put("user_id", logonCode);
			switch (logonCode) {
			case "-1":
				return new ResultData(CodeMsg.CODE_FAIL, resultMap);							//系统繁忙请稍后再试
			case "20002":
				return new ResultData(CodeMsg.CODE_20002, resultMap);							//该手机号已经注册过了
			case "20003":
				return new ResultData(CodeMsg.CODE_20003, resultMap);							//该手机号还未注册
			default:
				resultMap.put("user_id", logonCode);											//返回用户ID
				int num = (int) iLoginLog.find_llogbyid(map);
				if(num==0){
					iLoginLog.add_loginlog(map);
				}else{
					iLoginLog.upd_time(map);
				}
				break;
			}
			break;
		default:
			return new ResultData(CodeMsg.CODE_10000, null);//参数不合法
		}
		return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
	}
	
	/**
	 * 修改密码（重置密码）
	 * @param request
	 * @return
	 */
	@PostMapping(value = "resetPassword")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "data", 
					value = "密文{\"phone\":\"18500001111\",\"password\":\"123456\",\"sms_code\":\"123456\"}",  
					paramType = "form", 
					required = true, 
					dataType = "String") 
	})
	@ResponseBody
	@ApiOperation(value = "修改密码(重置密码)",notes = ""
			+ "该接口为重置密码接口<br>"
			+ "返回code码<br>"
			+ "-1：系统繁忙<br>"
			+ "0：请求成功<br>"
			+ "10000：参数不合法<br>"
			+ "10001：参数缺失<br>"
			+ "10002：密文无法解析<br>"
			+ "<br>"
			+ "20001：验证码输入错误<br>"
			+ "20003：该手机号还未注册<br>"
			)
	public ResultData resetPassword(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String data = request.getParameter("data");
		if(IsEmpty.isEmpty(data))return new ResultData(CodeMsg.CODE_10001, resultMap);				//参数值传入缺失
		JSONObject dataJson = ParameterED.parameterDecryption(data);
		if(null==dataJson)return new ResultData(CodeMsg.CODE_10002, resultMap);						//密文无法解析
		
		if(!dataJson.containsKey("phone"))return new ResultData(CodeMsg.CODE_10001, resultMap);		//参数缺失
		if(!dataJson.containsKey("password"))return new ResultData(CodeMsg.CODE_10001, resultMap);	//参数缺失
		if(!dataJson.containsKey("sms_code"))return new ResultData(CodeMsg.CODE_10001, resultMap);	//参数缺失
		
		String phone = dataJson.getString("phone");
		String password = dataJson.getString("password");
		String sms_code = dataJson.getString("sms_code");
		
		String logonCode = appLoginImpl.resetPassword(phone, password, sms_code);			//修改密码结果code值
		switch (logonCode) {
		case "-1":
			return new ResultData(CodeMsg.CODE_FAIL, resultMap);							//系统繁忙请稍后再试
		case "20001":
			return new ResultData(CodeMsg.CODE_20001, resultMap);							//验证码输入错误！
		case "20003":
			return new ResultData(CodeMsg.CODE_20003, resultMap);							//该手机号还未注册！
		default:
			resultMap.put("user_id", logonCode);											//返回用户ID
			break;
		}
		
		return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
	}
	
	
	/**
	 * 用户注册
	 * @param request
	 * @return
	 */
	@PostMapping(value = "register")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "data", 
					value = "密文{\"phone\":\"18500001111\",\"password\":\"123456\",\"sms_code\":\"123456\"}", 
					paramType = "form", 
					required = true, 
					dataType = "String") 
	})
	@ResponseBody
	@ApiOperation(value = "用户注册", notes = ""
			+ "该接口为注册接口<br>"
			+ "返回code码<br>"
			+ "-1：系统繁忙<br>"
			+ "0：请求成功<br>"
			+ "10000：参数不合法<br>"
			+ "10001：参数缺失<br>"
			+ "10002：密文无法解析<br>"
			+ "<br>"
			+ "20001：验证码输入错误<br>"
			+ "20002：该手机号已经注册过了<br>"
			)
	public ResultData register(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		String data = request.getParameter("data");
		if(IsEmpty.isEmpty(data))return new ResultData(CodeMsg.CODE_10001, resultMap);				//参数值传入缺失
		JSONObject dataJson = ParameterED.parameterDecryption(data);
		if(null==dataJson)return new ResultData(CodeMsg.CODE_10002, resultMap);						//密文无法解析
		
		if(!dataJson.containsKey("phone"))return new ResultData(CodeMsg.CODE_10001, resultMap);		//参数缺失
		if(!dataJson.containsKey("password"))return new ResultData(CodeMsg.CODE_10001, resultMap);	//参数缺失
		if(!dataJson.containsKey("sms_code"))return new ResultData(CodeMsg.CODE_10001, resultMap);	//参数缺失
		
		String phone = dataJson.getString("phone");
		String password = dataJson.getString("password");
		String sms_code = dataJson.getString("sms_code");
		String user_source = dataJson.getString("user_source");
		String token = dataJson.getString("token");
//		resultMap.put("iosVersion", iac.getIosVersion());				//获取IOS版本号
		resultMap.put("androidVersions", iac.getAndroidVersions());		//获取Android版本号
		int num = appLoginImpl.find_namelist(phone);					//比对名单
		String logonCode = appLoginImpl.register(phone, password, sms_code,user_source);	//注册结果code值
		map.put("logonCode", logonCode);
		switch (logonCode) {
		case "-1":
			return new ResultData(CodeMsg.CODE_FAIL, resultMap);							//系统繁忙请稍后再试
		case "20001":
			return new ResultData(CodeMsg.CODE_20001, resultMap);							//验证码输入错误！
		case "20002":
			return new ResultData(CodeMsg.CODE_20002, resultMap);							//该手机号已经注册过了！
		default:
			if(!"没有token".equals(token)){													//报存用户token值
				map.put("device_token", token);
				iAppUserinfo.up_token(map);
			}
			resultMap.put("user_id", logonCode);											//返回用户ID
			if(num>0){
				String code = appLoginImpl.update_namelist(logonCode);							//修改结果code值
				if(code.equals("-1")){
					return new ResultData(CodeMsg.CODE_FAIL, resultMap);
				}
			}
			break;
		}
		return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
	}
	
	/**
	 * 用户注册
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "registerNoEncryption")
	@ResponseBody
	@ApiOperation(value = "用户注册", notes = ""
			+ "该接口为注册接口<br>"
			+ "返回code码<br>"
			+ "-1：系统繁忙<br>"
			+ "0：请求成功<br>"
			+ "10000：参数不合法<br>"
			+ "10001：参数缺失<br>"
			+ "10002：密文无法解析<br>"
			+ "<br>"
			+ "20001：验证码输入错误<br>"
			+ "20002：该手机号已经注册过了<br>"
			)
	public ResultData registerNoEncryption(HttpServletRequest request, 
			String phone, 
			String password, 
			String sms_code, 
			String user_source, 
			String token ) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		
//		resultMap.put("iosVersion", iac.getIosVersion());				//获取IOS版本号
		resultMap.put("androidVersions", iac.getAndroidVersions());		//获取Android版本号
		int num = appLoginImpl.find_namelist(phone);					//比对名单
		String logonCode = appLoginImpl.register(phone, password, sms_code,user_source);	//注册结果code值
		map.put("logonCode", logonCode);
		switch (logonCode) {
		case "-1":
			return new ResultData(CodeMsg.CODE_FAIL, resultMap);							//系统繁忙请稍后再试
		case "20001":
			return new ResultData(CodeMsg.CODE_20001, resultMap);							//验证码输入错误！
		case "20002":
			return new ResultData(CodeMsg.CODE_20002, resultMap);							//该手机号已经注册过了！
		default:
			if(!"没有token".equals(token)){													//报存用户token值
				map.put("device_token", token);
				iAppUserinfo.up_token(map);
			}
			resultMap.put("user_id", logonCode);											//返回用户ID
			if(num>0){
				String code = appLoginImpl.update_namelist(logonCode);							//修改结果code值
				if(code.equals("-1")){
					return new ResultData(CodeMsg.CODE_FAIL, resultMap);
				}
			}
			break;
		}
		return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
	}
	
}

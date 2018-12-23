package com.dd.supermarket.controller.app.newMessage;

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
import com.dd.supermarket.service.app.user.IAppUserinfo;
import com.dd.supermarket.service.newMessage.ISendMessage;
import com.dd.supermarket.utils.IsEmpty;
import com.dd.supermarket.utils.http.GETRequestIP;
import com.dd.supermarket.utils.verification.V_PhoneNumber;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月23日 下午5:24:32 <br/>
* 类说明： 短信验证码发送
*/
@Controller
@RequestMapping("/app/message")
@Api(value = "APP发送验证码", tags = "验证码类")
public class APPSendMessageContrller {
	
	@Resource(name="backSendMessageImpl")
	private ISendMessage ism;
	
	@Resource(name="appUserinfoImpl")
	private IAppUserinfo iau;
	/**
	 * 发送验证码
	 * @param file
	 * @return
	 */
	@PostMapping(value = "sendMessage")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "service", 
					value = ""
							+ "注册验证码：REGISTER<br>"
							+ "修改密码验证码：RESET_PASSWORD<br>"
							+ "验证码登录：CODE_PASSWORD", 
					paramType = "form", 
					required = true, 
					dataType = "String"),
			@ApiImplicitParam(
					name = "data", 
					value = "密文{\"phone\":\"18500001111\"}", 
					paramType = "form", 
					required = true, 
					dataType = "String") })
	@ResponseBody
	@ApiOperation(value = "发送短信验证码", notes = ""
			+ "该接口为验证码接口，传入不同service，得到不同的值。<br>"
			+ "返回code码<br>"
			+ "-1：系统繁忙<br>"
			+ "0：请求成功<br>"
			+ "10000：参数不合法<br>"
			+ "10001：参数缺失<br>"
			+ "10002：密文无法解析<br>"
			)
	public ResultData getBasics1(HttpServletRequest request) {
		String service = request.getParameter("service");
		String data = request.getParameter("data");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(IsEmpty.isEmpty(service,data))return new ResultData(CodeMsg.CODE_10001, resultMap);			//参数值传入缺失
		JSONObject dataJson = ParameterED.parameterDecryption(data);
		if(null==dataJson)return new ResultData(CodeMsg.CODE_10002, resultMap);							//密文无法解析
		
		
		if(!dataJson.containsKey("phone"))return new ResultData(CodeMsg.CODE_10001, resultMap);			//参数缺失
		String phone = dataJson.getString("phone");
		
		if(!V_PhoneNumber.isPhoneLegal(phone))return new ResultData(CodeMsg.CODE_20000, resultMap);		//手机号不正确
		
		Map<String,Object>  userinfo = null; 
		switch (service) {
		//--------------------------------------------------------------------------------------------------------------------------
		case "REGISTER":
			Print.println("发送注册验证码");
			
			//验证手机号是否注册==================================================
//			try {
//				userinfo = this.find_userinfoByPhone(phone);
//			} catch (Exception e) {
//				System.out.println("验证手机号是否注册时失败出错");
//				e.printStackTrace();
//				return new ResultData(CodeMsg.CODE_FAIL, resultMap);
//			}
//			if(null!=userinfo)return new ResultData(CodeMsg.CODE_20002, resultMap);						//该手机号已经注册过了！
			
			
			//发送验证码==================================================
			try {
				resultMap = this.sendRegisterMessage(request,phone,0);
			} catch (Exception e) {
				System.out.println("注册短信验证码在发送时失败出错");
				e.printStackTrace();
				return new ResultData(CodeMsg.CODE_FAIL, resultMap);
			}
			break;
		//--------------------------------------------------------------------------------------------------------------------------
		case "RESET_PASSWORD":
			Print.println("发送重置密码验证码");
//			//验证手机号是否注册==================================================
//			try {
//				userinfo = this.find_userinfoByPhone(phone);
//			} catch (Exception e) {
//				System.out.println("验证手机号是否注册时失败出错");
//				e.printStackTrace();
//				return new ResultData(CodeMsg.CODE_FAIL, resultMap);
//			}
//			if(null==userinfo)return new ResultData(CodeMsg.CODE_20003, resultMap);						//该手机号还未注册！
			
			//发送验证码==================================================
			try {
				resultMap = this.sendRegisterMessage(request,phone,5);
			} catch (Exception e) {
				System.out.println("注册短信验证码在发送时失败出错");
				e.printStackTrace();
				return new ResultData(CodeMsg.CODE_FAIL, resultMap);
			}
			break;
		//--------------------------------------------------------------------------------------------------------------------------
		case "CODE_PASSWORD":
			Print.println("发送验证码登录验证码");
//			//验证手机号是否注册==================================================
//			try {
//				userinfo = this.find_userinfoByPhone(phone);
//			} catch (Exception e) {
//				System.out.println("验证手机号是否注册时失败出错");
//				e.printStackTrace();
//				return new ResultData(CodeMsg.CODE_FAIL, resultMap);
//			}
//			if(null==userinfo)return new ResultData(CodeMsg.CODE_20003, resultMap);						//该手机号还未注册！
			
			//发送验证码==================================================
			try {
				resultMap = this.sendRegisterMessage(request,phone,10);
			} catch (Exception e) {
				System.out.println("登录验证码在发送时失败出错");
				e.printStackTrace();
				return new ResultData(CodeMsg.CODE_FAIL, resultMap);
			}
			break;
		//--------------------------------------------------------------------------------------------------------------------------
		default:
			return new ResultData(CodeMsg.CODE_10000, null);//参数不合法
		}
		return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
	}
	
	/**
	 * 发送验证码
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "sendMessageNoEncryption")
	@ResponseBody
	@ApiOperation(value = "发送短信验证码", notes = ""
			+ "该接口为验证码接口，传入不同service，得到不同的值。<br>"
			+ "返回code码<br>"
			+ "-1：系统繁忙<br>"
			+ "0：请求成功<br>"
			+ "10000：参数不合法<br>"
			+ "10001：参数缺失<br>"
			+ "10002：密文无法解析<br>"
			)
	public ResultData NoEncryption(HttpServletRequest request, 
			String phone, String service) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(null==service) return new ResultData(CodeMsg.CODE_10001, resultMap);	//参数缺失
		if(!V_PhoneNumber.isPhoneLegal(phone))return new ResultData(CodeMsg.CODE_20000, resultMap);		//手机号不正确
		
		switch (service) {
		//--------------------------------------------------------------------------------------------------------------------------
		case "REGISTER":
			//发送注册验证码==================================================
			try {
				resultMap = this.sendRegisterMessage(request,phone,0);
			} catch (Exception e) {
				System.out.println("注册短信验证码在发送时失败出错");
				e.printStackTrace();
				return new ResultData(CodeMsg.CODE_FAIL, resultMap);
			}
			break;
		//--------------------------------------------------------------------------------------------------------------------------
		case "RESET_PASSWORD":
			//发送重置密码验证码==================================================
			try {
				resultMap = this.sendRegisterMessage(request,phone,5);
			} catch (Exception e) {
				System.out.println("注册短信验证码在发送时失败出错");
				e.printStackTrace();
				return new ResultData(CodeMsg.CODE_FAIL, resultMap);
			}
			break;
		//--------------------------------------------------------------------------------------------------------------------------
		case "CODE_PASSWORD":
			//发送验证码登录验证码==================================================
			try {
				resultMap = this.sendRegisterMessage(request,phone,10);
			} catch (Exception e) {
				System.out.println("登录验证码在发送时失败出错");
				e.printStackTrace();
				return new ResultData(CodeMsg.CODE_FAIL, resultMap);
			}
			break;
		//--------------------------------------------------------------------------------------------------------------------------
		default:
			return new ResultData(CodeMsg.CODE_10000, null);//参数不合法
		}
		return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
	}
	
	/**
	 * 通过手机号查询查询手机号是否注册
	 * @param phone
	 * @return
	 * @throws Exception 
	 */
	private Map<String,Object> find_userinfoByPhone(String phone) throws Exception{
		return iau.find_userinfoByPhone(phone);
	}
	
	/**
	 * 发送验证码
	 * @param request
	 * @param phone
	 * @param type	验证码类型(0:注册验证码,5:重置密码验证码,10:登录验证码)
	 * @return
	 * @throws Exception
	 */
	private Map<String,Object> sendRegisterMessage(HttpServletRequest request,String phone,int type) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		int code = ism.sendCode(phone, type, GETRequestIP.getIp(request), "");
		map.put("phone", phone);
		if(code==-1){
			throw new RuntimeException("发送短信失败");
		}
		return map;
	}
	
}

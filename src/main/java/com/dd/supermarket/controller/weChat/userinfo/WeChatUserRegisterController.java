package com.dd.supermarket.controller.weChat.userinfo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
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
import com.dd.supermarket.service.newMessage.ISendMessage;
import com.dd.supermarket.service.weChat.IWeChatUserinfo;
import com.dd.supermarket.utils.IsEmpty;
import com.dd.supermarket.utils.http.GETRequestIP;
import com.dd.supermarket.utils.verification.V_PhoneNumber;

/**
 * 
 * 用户注册
 */
@Controller
@RequestMapping("weChatUserRegisterController")
public class WeChatUserRegisterController {
	@Resource(name="backSendMessageImpl")
	private ISendMessage ism;
	
	@Resource(name="appUserinfoImpl")
	private IAppUserinfo iau;
	
	@Resource(name="appLoginImpl")
	private IAppLogin appLoginImpl;
	
	@Resource(name="appControlImpl")
	private IAppControl iac;
	
	@Resource(name="weChatUserinfoImpl")
	private IWeChatUserinfo iwcu;
	
	/**
	 * 发送验证码
	 * @param file
	 * @return
	 */
	@RequestMapping("sendVerificationCode")
	@ResponseBody 
	public ResultData sendVerificationCode(HttpServletRequest request,String phone,String type) {
		System.out.println("发送验证码");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(phone==""||type==""){
			return new ResultData(CodeMsg.CODE_10001, resultMap);
		}
		if(!V_PhoneNumber.isPhoneLegal(phone)){
			return new ResultData(CodeMsg.CODE_20000, resultMap);		//手机号不正确
		}
		
		Map<String,Object>  userinfo = null; 
		switch (type) {
		//--------------------------------------------------------------------------------------------------------------------------
		case "REGISTER":
			Print.println("发送注册验证码");
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
//			
			try {
				resultMap = this.sendRegisterMessage(request,phone,5);
			} catch (Exception e) {
				System.out.println("注册短信验证码在发送时失败出错");
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

	private Map<String,Object> sendRegisterMessage(HttpServletRequest request,String phone,int type) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		int code = ism.sendCode(phone, type, GETRequestIP.getIp(request), "");
		map.put("phone", phone);
		if(code==-1){
			throw new RuntimeException("发送短信失败");
		}
		return map;
	}
	
	/**
	 * 注册
	 * @param file
	 * @return
	 */
	@RequestMapping("register")
	@ResponseBody 
	public ResultData register(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		String phone = request.getParameter("phone");
		String password = request.getParameter("pwd");
		String sms_code = request.getParameter("smsCode");
		String openid = request.getParameter("openid");
		if(phone==""||phone==null){
			return new ResultData(CodeMsg.CODE_10001, resultMap);
		}
		if(password==""||password==null){
			return new ResultData(CodeMsg.CODE_10001, resultMap);
		}
		if(sms_code==""||sms_code==null){
			return new ResultData(CodeMsg.CODE_10001, resultMap);
		}
		System.out.println("注册参数"+openid);
		String user_source = "share";    //渠道来源 （微信）
		String logonCode = appLoginImpl.register(phone, password, sms_code,user_source);	//修改密码结果code值
		switch (logonCode) {
		case "-1":
			return new ResultData(CodeMsg.CODE_FAIL, resultMap);							//系统繁忙请稍后再试
		case "20001":
			return new ResultData(CodeMsg.CODE_20001, resultMap);							//验证码输入错误！
		case "20002":
			return new ResultData(CodeMsg.CODE_20002, resultMap);							//该手机号已经注册过了！
		default:
			map.put("open_id", openid);
			map.put("user_id", logonCode);
			iwcu.updateOpenID(map);    														//根据用户id添加openid
			resultMap.put("user_id", logonCode);											//返回用户ID
			break;
		}
		return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
	}
	
	/**
	 * 修改密码
	 * @param file
	 * @return
	 */
	@RequestMapping("resetPassword")
	@ResponseBody 
	public ResultData resetPassword(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String phone = request.getParameter("phone");
		String password = request.getParameter("pwd");
		String sms_code = request.getParameter("smsCode");
		if(phone==""||phone==null){
			return new ResultData(CodeMsg.CODE_10001, resultMap);
		}
		if(password==""||password==null){
			return new ResultData(CodeMsg.CODE_10001, resultMap);
		}
		if(sms_code==""||sms_code==null){
			return new ResultData(CodeMsg.CODE_10001, resultMap);
		}
		String logonCode = appLoginImpl.resetPassword(phone, password, sms_code);			//修改密码结果code值
		switch (logonCode) {
		case "-1":
			return new ResultData(CodeMsg.CODE_FAIL, resultMap);							//系统繁忙请稍后再试
		case "20003":
			return new ResultData(CodeMsg.CODE_20003, resultMap);							//该手机号还未注册！
		case "20001":
			return new ResultData(CodeMsg.CODE_20001, resultMap);							//验证码输入错误！
		default:
			resultMap.put("user_id", logonCode);											//返回用户ID
			break;
		}
		
		return new ResultData(CodeMsg.CODE_SUCCESS, resultMap);
	}
}

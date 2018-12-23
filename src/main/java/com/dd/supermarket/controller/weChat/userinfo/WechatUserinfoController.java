/**
 * 
 */
package com.dd.supermarket.controller.weChat.userinfo;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.BaseController;
import com.dd.supermarket.controller.weChat.util.ParamsED;
import com.dd.supermarket.fixed.WeChat;
import com.dd.supermarket.pojo.CodeMsg;
import com.dd.supermarket.pojo.ResultData;
import com.dd.supermarket.service.weChat.IWeChatUserinfo;
import com.dd.supermarket.utils.http.HTTPRequest;
import com.dd.supermarket.utils.secretly.DESUtil;
import com.dd.supermarket.utils.secretly.SHAencrypt;
import com.dd.supermarket.utils.IsEmpty;
import com.dd.supermarket.utils.PageData;





/**
 * 
 * 获取用户信息
 */
@Controller
@RequestMapping("wechatUserinfo")
public class WechatUserinfoController extends  BaseController{
	
	@Resource(name="weChatUserinfoImpl")
	private IWeChatUserinfo iwcu;
	ParamsED PED = ParamsED.getParamsED();
	/**
	 * 通过code换取用户 openid 和 access_token
	 * @param code
	 * @return	JSON 
	 * 				{"access_token":"ACCESS_TOKEN", 
					 "expires_in":7200, 
					 "refresh_token":"REFRESH_TOKEN", 
					 "openid":"OPENID", 
					 "scope":"SCOPE"}
	 */
	@RequestMapping("getOpenIdByCode")
	@ResponseBody 
	public JSONObject getOpenIdByCode(String code){
		System.out.println("code"+code);
		JSONObject json = iwcu.getOpenIdByCode(code,WeChat.WX_APPID,WeChat.WX_APPSECRET);
		return json;
	}
	
	//查询用户是否绑定openid	
	@RequestMapping("findUserinfoByOpenID")
	@ResponseBody 
	public JSONObject findUserinfoByOpenID(String openid){
		JSONObject json = new JSONObject();
		try {
			json = iwcu.findUserinfoByOpenID(openid);	
		} catch (Exception e) {
			System.out.println("=============通过openid查询用户时出错==============");
			e.printStackTrace();
			return new JSONObject();
		}
		return json;
	}
	
	

	@ResponseBody
	@RequestMapping("loginByPhoneAndPwd")
	public ResultData loginByPhoneAndPwd(String data) {
		Map<String, Object> map = new HashMap<String,Object>();
		PageData pd = super.getPageData();
		pd.clear();

		if(null==data || "".equals(data)){
			CodeMsg msg = CodeMsg.CODE_10001;
			return new ResultData(msg, pd);
		} 
		
		JSONObject jsonO = null;
		try {
			jsonO = PED.decryptionToJSONObject(data, PED.getTrueParent_secret_key());
		} catch (Exception e) {
			CodeMsg msg = CodeMsg.CODE_10002;
			return new ResultData(msg, pd);
		}
		String phone = jsonO.getString("phone");
		String pwd = jsonO.getString("user_password");
		String openid = jsonO.getString("openid");
		String user_password="";
		try {
			user_password = SHAencrypt.encryptSHA(pwd);
		} catch (Exception e) {
			System.out.println("============= 注册时密码加密出错 =============");
			e.printStackTrace();
			CodeMsg msg = CodeMsg.CODE_FAIL;
			return new ResultData(msg, pd);	
		}
		
		if (IsEmpty.isEmpty(phone, user_password)) {
			CodeMsg msg = CodeMsg.CODE_10001;
			return new ResultData(msg, pd);
		}
		
		try {
			map = (Map<String, Object>) iwcu.findUserByPhone(phone);// 查询用户基本信息
		} catch (Exception e) {
			CodeMsg msg = CodeMsg.CODE_FAIL;
			 return new ResultData(msg, pd);
		}
		Object user = null;
		// ========================判断用户是否存在========================
		if (null == map || map.size() == 0) {
			CodeMsg msg = CodeMsg.CODE_20003;
			return new ResultData(msg, pd);	// 用户未注册
		}
		
		// ========================判断用户密码是否正确========================
		if (null != map) {
			if (!user_password.equals(map.get("user_password"))) {
				CodeMsg msg = CodeMsg.CODE_20004;
				return new ResultData(msg, pd);	//密码错误
			} 	
		}
		// ========================绑定用户微信openid========================
		map.put("open_id", openid);
		iwcu.updateOpenID(map);
		CodeMsg msg = CodeMsg.CODE_SUCCESS;
		return new ResultData(msg, pd);
	}
	
	@RequestMapping("encryption")
	@ResponseBody
	public JSONObject encryption(){
		JSONObject code = new JSONObject();
		PageData pd = this.getPageData();
		PageData result = new PageData();
		try {
			result.put("str", DESUtil.SECRET_KEY);//秘钥
			result.put("ciphertext", ParamsED.getParamsED().getSecret_key());//密文
			code.put("code", 1);
		} catch (Exception e) {
			e.printStackTrace();
			code.put("code", 0);
		}
		code.put("result", result);
		return code;
	}
	

}

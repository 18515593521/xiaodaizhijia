package com.dd.supermarket.service.weChat.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.weChat.util.WeChatHttpRequest;
import com.dd.supermarket.dao.BaseDao;
import com.dd.supermarket.fixed.WeChat;
import com.dd.supermarket.service.weChat.IWeChatUserinfo;

@Transactional
@Service("weChatUserinfoImpl")
public class WeChatUserinfoImpl implements IWeChatUserinfo{

	@Resource(name="baseDao")
	private BaseDao baseDao;
	/**
	 * 通过code换取用户 openid 和 access_token
	 * @param code
	 * @param appid				公众号appid
	 * @param secret			公众号的appsecret
	 * @return	JSON 
	 * 				{"access_token":"ACCESS_TOKEN", 
					 "expires_in":7200, 
					 "refresh_token":"REFRESH_TOKEN", 
					 "openid":"OPENID", 
					 "scope":"SCOPE"}
	 */
	@RequestMapping("getOpenIdByCode")
	@ResponseBody 
	public JSONObject getOpenIdByCode(String code,String appid,String secret){ 
		System.out.println("code >>> "+code);
		JSONObject json = new JSONObject();
		if(null==code||""==code)return json;
		String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret
				+"&code="+code+"&grant_type=authorization_code";
		System.out.println("path >>> "+path);
		return new WeChatHttpRequest().going(path, "POST", null);
		// {access_token: "9_RVWB3qcLzi---RuXouW3JyA2tLjK0k6ysmK5IHlwWmHVmPRS92_W-rqbxRZmiRFk0hCQRhovdiSqjwtOzYrecw", 
		//  refresh_token: "9_rqq0b-5MeK7_yaFis3nwNrSXWhcW7i2aPyfpErqSUROAAYvuYvZhfAyf2-fI6uckbo3-anFbSpwYq3SBUw3iRg", 
		//  openid: "ov70I0l4PPD9Gd2VKVeN1DYcpibc", scope: "snsapi_userinfo", expires_in: 7200}
	}
	
	/**
	 * 验证用户的access_token 	是否有效
	 * @param access_token 	网页授权接口调用凭证
	 * @param openid		用户的唯一标识
	 * @return				JSON  
	 * 							{"errcode":0, "errmsg":"ok"}
	 */
	public JSONObject verificationUserAccess_token(String access_token,String openid){
		String path = "https://api.weixin.qq.com/sns/auth?"+access_token+"=ACCESS_TOKEN&openid="+openid;
		return  new WeChatHttpRequest().going(path, "POST", null);
	}
	
	/**
	 * 刷新用户access_token
	 * @param appid				公众号appid
	 * @param refresh_token		使用code换取用户access_token时 
	 * 							其中的refresh_token 该值有效期为30天
	 * @return 					JSON
	 */
	public JSONObject refreshUserAccess_token(String appid, String refresh_token){
		String path = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+appid+"&grant_type=refresh_token&refresh_token="+refresh_token;
		return new WeChatHttpRequest().going(path, "POST", null);
	}
	
	/**
	 * 通过网页授权后获取用户用户个人信息资料
	 * @param access_token		网页授权接口调用凭证
	 * @param openid			用户的唯一标识
	 * @param lang				返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语	default:zh_CN	
	 * @return JSON
	 */
	public JSONObject getWechatUserinfo(String access_token, String openid, String lang){
		if(null==lang||lang=="")lang="zh_CN";
		String path = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang="+lang;
		return new WeChatHttpRequest().going(path, "POST", null);
	}
	
	/**
	 * 通过用户 openid 获取用户个人信息资料
	 * @param openid			用户的唯一标识
	 * @param lang				返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语	default:zh_CN	
	 * @return	JSON
	 */
	public JSONObject getWechatUserinfo(String openid, String lang){
		if(null==lang||lang=="")lang="zh_CN";
		String path = "https://api.weixin.qq.com/sns/userinfo?access_token="+WeChat.WX_APPSECRET+"&openid="+openid+"&lang="+lang;
		return new WeChatHttpRequest().going(path, "POST", null);
	}

	/**
	 * 通过openid 查询用户id
	 */
	public JSONObject findUserinfoByOpenID(String openid) throws Exception {
		Map<String,Object> map = new HashMap<String,Object> ();
		Object findOne =  baseDao.findOne("WxLogin.find_user_userinfos", openid);
		if(null==findOne)return new JSONObject();
		map = (Map<String,Object>)findOne;
		return new JSONObject(map);
	}
	
	/**
	 * 通过openid 查询用户id
	 */
	public Object findUserByPhone(String phone)throws Exception{
		Map<String,Object> map = new HashMap<String,Object> ();
		Object findOne =  baseDao.findOne("WxLogin.findUserByPhone",phone);
		
		return findOne;
	}
	
	

	public void saveWechatUserinfo(Map map) throws Exception {
		baseDao.save("weChatUserinfo.saveWechatUserinfo", map);
	}

	//绑定用户微信openid
	public void updateOpenID(Map map){
		baseDao.update("WxLogin.update_openID", map);
	}
}

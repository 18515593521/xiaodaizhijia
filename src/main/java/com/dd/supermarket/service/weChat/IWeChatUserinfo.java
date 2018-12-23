package com.dd.supermarket.service.weChat;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface IWeChatUserinfo {
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
	public JSONObject getOpenIdByCode(String code,String appid,String secret);
	
	/**
	 * 验证用户的access_token 	是否有效
	 * @param access_token 	网页授权接口调用凭证
	 * @param openid		用户的唯一标识
	 * @return				JSON  
	 * 							{"errcode":0, "errmsg":"ok"}
	 */
	public JSONObject verificationUserAccess_token(String access_token,String openid); 
	
	/**
	 * 刷新用户access_token
	 * @param appid				公众号appid
	 * @param refresh_token		使用code换取用户access_token时 
	 * 							其中的refresh_token 该值有效期为30天
	 * @return 					JSON
	 * 								{"access_token":"ACCESS_TOKEN", 
									 "expires_in":7200, 
									 "refresh_token":"REFRESH_TOKEN", 
									 "openid":"OPENID", 
									 "scope":"SCOPE"}
	 */
	public JSONObject refreshUserAccess_token(String appid, String refresh_token);
	
	/**
	 * 通过网页授权后获取用户用户个人信息资料
	 * @param access_token		网页授权接口调用凭证
	 * @param openid			用户的唯一标识
	 * @param lang				返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语	default:zh_CN	
	 * @return JSON
	 * 				{"openid":"OPENID", 
					 "nickname":"NICKNAME", 
					 "sex":"1", 
					 "province":"PROVINCE", 
					 "city":"CITY", 
					 "country":"COUNTRY",    
 					 "headimgurl":"http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",  
  					 "privilege":[ "PRIVILEGE1","PRIVILEGE2"], 
  					 "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"}
  				字段解释：
  					openid		用户的唯一标识
					nickname	用户昵称
					sex			用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
					province	用户个人资料填写的省份
					city		普通用户个人资料填写的城市
					country		国家，如中国为CN
					headimgurl	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
					privilege	用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
					unionid		只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	 */
	public JSONObject getWechatUserinfo(String access_token, String openid, String lang);
	
	/**
	 * 通过用户 openid 获取用户个人信息资料
	 * @param openid			用户的唯一标识
	 * @param lang				返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语	default:zh_CN	
	 * @return	JSON
	 * 				{"subscribe": 1, 
					  "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M", 
					  "nickname": "Band", 
					  "sex": 1, 
					  "language": "zh_CN", 
					  "city": "广州", 
					  "province": "广东", 
					  "country": "中国", 
					  "headimgurl":  "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
					  "subscribe_time": 1382694957,
					  "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
					  "remark": "",
					  "groupid": 0,
					  "tagid_list":[128,2]
					}
				字段解释：
					subscribe		用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
					openid			用户的标识，对当前公众号唯一
					nickname		用户的昵称
					sex				用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
					city			用户所在城市
					country			用户所在国家
					province		用户所在省份
					language		用户的语言，简体中文为zh_CN
					headimgurl		用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
					subscribe_time	用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
					unionid			只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
					remark			公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
					groupid			用户所在的分组ID（兼容旧的用户分组接口）
					tagid_list		用户被打上的标签ID列表
	 */
	public JSONObject getWechatUserinfo(String openid, String lang);
	
	/**
	 * 通过
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	public JSONObject findUserinfoByOpenID(String openid) throws Exception;
	
	//手机号查询用户 
	public Object findUserByPhone(String phone)throws Exception;
	
	//绑定用户微信openid
	public void updateOpenID(Map map);

}

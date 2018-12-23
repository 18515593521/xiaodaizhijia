package com.dd.supermarket.controller.weChat.util;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.weChat.accessToken.AccessToken;
import com.dd.supermarket.fixed.WeChat;



public class AccessTokenUtil {
	/**
	 * 获取accessToken
	 * @param appID		微信公众号凭证
	 * @param appScret	微信公众号凭证秘钥
	 * @return
	 */
//	public static AccessToken getAccessToken(String appID, String appScret) {
//		AccessToken token = new AccessToken();
//		// 访问微信服务器
//		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appID+"&secret="+appScret;
//		try {
//			URL getUrl=new URL(url);
//			HttpURLConnection http=(HttpURLConnection)getUrl.openConnection();
//			http.setDoOutput(true);
//			http.setDoInput(true);
//			http.setRequestMethod("GET"); 
//			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
//
//			http.connect();
//			InputStream is = http.getInputStream(); 
//			int size = is.available(); 
//			byte[] b = new byte[size];
//			is.read(b);
//
//			String message = new String(b, "UTF-8");
//			
//			JSONObject json = JSONObject.parseObject(message);
//			if(null==json.getString("access_token")){
//				System.out.println("未获得access_token.可能是您为配置您的白名单");
//				return null;
//			}
//			token.setAccess_token(json.getString("access_token"));
//			token.setExpires_in(new Integer(json.getString("expires_in")));
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return token;
//	}
	public static AccessToken getAccessToken(String appID, String appScret) {
		AccessToken token = new AccessToken();
		// 访问微信服务器
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appID+"&secret="+appScret;
		JSONObject json = new WeChatHttpRequest().going(url, "GET", null);
		if(null==json.getString("access_token")){
			System.out.println("未获得access_token.可能是您为配置您的白名单");
			return null;
		}
		token.setAccess_token(json.getString("access_token"));
		token.setExpires_in(new Integer(json.getString("expires_in")));
		return token;
	}
	public static void main(String[] args) {
		AccessToken token = new AccessToken();
		token = getAccessToken(WeChat.WX_APPID,WeChat.WX_APPSECRET);
		
		System.out.println(token.getAccess_token());
		System.out.println(token.getExpires_in());
	}
}

package com.dd.supermarket.controller.weChat.util;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.weChat.accessToken.TokenThread;
import com.dd.supermarket.controller.weChat.menu.entity.Button;
import com.dd.supermarket.controller.weChat.menu.entity.ComplexButton;
import com.dd.supermarket.controller.weChat.menu.entity.Menu;
import com.dd.supermarket.controller.weChat.menu.entity.ViewButton;
import com.dd.supermarket.fixed.WeChat;


public class MenuUtil {

	/**
	 * 	创建自定义菜单(每天限制1000次)
	 * 
	 */
	public static int createMenu(Menu menu){
		JSONObject json = (JSONObject) JSONObject.toJSON(menu);
		String path="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+TokenThread.access_token.getAccess_token();
		json = new WeChatHttpRequest().going(path, "POST", json);
		return Integer.parseInt(json.getString("errcode"));
	}
	

	
	/**
	 * 查询当前菜单
	 * @return
	 */
	public static JSONObject find_wechatMenu(){
		String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token="+TokenThread.access_token.getAccess_token();
		JSONObject json = new WeChatHttpRequest().going(url, "POST", null);
		return json;
	}
	
	
	public static void main(String[] args) {
//		redirect_uri = "127.0.0.1";
		System.out.println(formatUrl("http://www.51happypay.com/supermarket/wx/register.html"));
	}
	
	
	public static String formatUrl(String url){
		url = "https://open.weixin.qq.com/connect/oauth2/authorize"
				+ "?appid="+WeChat.WX_APPID+""
				+ "&redirect_uri="+getTranscoding(url)+""
				+ "&response_type=code"
				+ "&scope=snsapi_userinfo"
				+ "&state=1"
				+ "#wechat_redirect";
		return url;
	}
	
	/**
	 * 进行转码
	 * @param value
	 * @return
	 */
	 private static String getTranscoding(String value){
		try {
			value = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}
	static String redirect_uri = "www.jyoudai.com";
	
	/**
	 * 	封装菜单数据
	 * */
	public static Menu getMenu(){
//											redirect_uri="47.104.24.255";
											
		//基本信息	=========================================================================
		final String vip=formatUrl("http://www.jyoudai.com/active/html/register.html");
		final String lelaiqian=formatUrl("http://www.51happypay.com/supermarket/h5/market.html");
	
	
		
		ComplexButton cx_1 = new ComplexButton();
		cx_1.setName("认证");
		cx_1.setType("click");
		cx_1.setUrl(vip);
		
		ComplexButton cx_2 = new ComplexButton();
		cx_2.setName("乐来钱");
		cx_2.setType("click");
		cx_2.setUrl(lelaiqian);
		
		
		
	
		
		/*
		 * 创建菜单
		 */
		Menu menu=new Menu();
		menu.setButton(new ComplexButton[]{ cx_1, cx_2});
		
		return menu;
	}
}

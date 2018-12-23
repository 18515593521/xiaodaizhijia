package com.dd.supermarket.service.yunbei.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.fixed.ShellMessageConst;
import com.dd.supermarket.service.yunbei.IYBMessage;
import com.dd.supermarket.utils.http.HTTPRequest;
@Transactional
@Service("ybMessageImpl")
public class YBMessageImpl implements IYBMessage{
	
		//短信发送验证码
		public String send_message(String phone,int code){
			
			Map<String, Object> map = new HashMap<String,Object>();
			String url = "http://m.5c.com.cn/api/send/index.php";
			map.put("username", ShellMessageConst.USERNAME); 			//公司id
			map.put("password_md5", ShellMessageConst.PASSWORD_MD5);  		//发送用户帐号
			map.put("apikey", ShellMessageConst.APIKEY); 		//发送帐号密码
			map.put("content", ShellMessageConst.ENCODE+code);			//短信内容
			map.put("mobile", phone);			//被叫号码
			map.put("sendTime", "");			//定时发送时间
			map.put("extno", "");				//扩展子号
			map.put("type", 1);					//短信类型
			HTTPRequest http = new HTTPRequest();
			return http.postRequest(url, map).getResultData();
		}
}

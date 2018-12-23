/**
 * 
 */
package com.dd.supermarket.controller.weChat.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.alibaba.fastjson.JSONObject;


public class WeChatHttpRequest {

	/**
	 * 微信发送请求
	 * @param path requestPath
	 * @param method RequtstMethod：'POST'  or  'GET'.default: 'POST'
	 * @param json JSONObject
	 * @return
	 */
	public JSONObject going(String path,String method,JSONObject json){
		if(null==path||""==path)return JSONObject.parseObject("{\"error\":\"'path' is empty, or The length of 'path' is less than 0 \"}");
		if(null==method||""==method)method="POST";
		if(null==json)json = new JSONObject();
		try {
			URL url=new URL(path);
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			http.setDoOutput(true);
			http.setDoInput(true);
			http.setRequestMethod(method);
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			http.connect();
			OutputStream os = http.getOutputStream();
			os.write(json.toString().getBytes("UTF-8"));
			os.close();
			
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] bt = new byte[size];
			is.read(bt);
			String message=new String(bt,"UTF-8");
			json = JSONObject.parseObject(message);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
}

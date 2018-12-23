package com.dd.supermarket.utils.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.jdom.JDOMException;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.utils.factories.StringValidation;
import com.dd.supermarket.utils.factories.XMLToJson;

/**
 * 网络请求类
 * 
 * @author 作者 ： 刘哈哈 <br/>
 *         E-mail: 2593707755@qq.com <br/>
 * @version 创建时间： 2018年5月29日 下午1:05:00 <br/>
 *          类说明：
 */
public class HTTPRequest {
	private String resultData = "{\"error\":\"'url' is empty, or The length of 'url' is less than 0 \"}";
	
	public String getResultData() {
		return this.resultData;
	}

	/**
	 * 发送Post请求
	 * @param url Request address
	 * @param Request parameters
	 * @return HTTPRequest
	 */
	public HTTPRequest postRequest(String url, Map<String, Object> map) {
		if (null == url || "" == url) return this;
		url = url + this.formattingRequestParameter(map);
		this.resultData = this.sendRequest(url, "POST", null);
		return this;
	}

	/**
	 * 发送Get普通请求
	 * @param url Request address
	 * @param Request parameters
	 * @return HTTPRequest
	 */
	public HTTPRequest getRequest(String url, Map<String, Object> map) {
		if (null == url || "" == url) return this;
		url = url + this.formattingRequestParameter(map);
		this.resultData = this.sendRequest(url, "GET", null);
		return this;
	}

	/**
	 * 发送Post请求
	 * 
	 * @param url RequestUrl
	 * @param JSONObject 需要上传的JSON对象
	 * @return HTTPRequest
	 */
	public HTTPRequest postRequest(String url, JSONObject json) {
		if (null == url || "" == url) return this;
		this.resultData = this.sendRequest(url, "POST", json);
		return this;
	}

	/**
	 * 发送Get普通请求
	 * 
	 * @param url
	 *            RequestUrl
	 * @param JSONObject
	 *            需要上传的JSON对象
	 * @return
	 */
	public HTTPRequest getRequest(String url, JSONObject json) {
		if (null == url || "" == url) return this;
		this.resultData = this.sendRequest(url, "GET", json);
		return this;
	}

	/**
	 * 模拟通过formData提交表单
	 * 
	 * @param url
	 *            RequestUrl
	 * @param map
	 *            需要提交的参数
	 * @return JSONObject
	 */
	public String sendRequest(String url, Map<String, Object> map) {
		String message = "";
		StringBuffer buffer = new StringBuffer();
		
		try {
			URL URL = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) URL.openConnection();
			connection.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=----footfoodapplicationrequestnetwork");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			connection.setRequestProperty("Accept", "*/*");
			connection.setRequestProperty("Range", "bytes=" + "");
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			connection.setRequestMethod("POST");
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());

			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				if (null == key || "" == key)
					continue;
				buffer.append("------footfoodapplicationrequestnetwork\r\n");
				buffer.append("Content-Disposition: form-data; name=\"");
				buffer.append(key);
				buffer.append("\"\r\n\r\n");
				buffer.append(entry.getValue());
				buffer.append("\r\n");
			}

			buffer.append("------footfoodapplicationrequestnetwork--\r\n");
			out.writeBytes(buffer.toString());
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			message = br.readLine();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * 发送请求
	 * 
	 * @param url
	 * @param method
	 * @param json
	 * @return JSONObject
	 */
	private String sendRequest(String url, String method, JSONObject json) {
		String message = "";
		try {
			URL URL = new URL(url);
			HttpURLConnection http = (HttpURLConnection) URL.openConnection();
			http.setDoOutput(true);
			http.setDoInput(true);
			http.setRequestMethod(method);
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.connect();

			if (null != json) {
				OutputStream os = http.getOutputStream();
				os.write(json.toString().getBytes("UTF-8"));
				os.close();
			}

			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] bt = new byte[size];
			is.read(bt);
			message = new String(bt, "UTF-8");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * json转换
	 * @param value
	 * @return
	 */
	public JSONObject toJsonObject() {
		return this.toJsonObject(this.resultData);
	}
	
	/**
	 * json转换
	 * @param value
	 * @return
	 */
	public JSONObject toJsonObject(String value) {
		JSONObject json = new JSONObject();
		if (StringValidation.isJson(value)) {
			json = JSONObject.parseObject(value);
		} else {
				try {
					json = XMLToJson.xmlToJSON(value);
				} catch (JDOMException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return json;
	}

	/**
	 * 格式化请求所需参数
	 * 
	 * @param map
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String formattingRequestParameter(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		sb.append("?");
		for (Map.Entry<String, Object> i : map.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				break;
			}
		}
		return sb.toString().substring(0, sb.length() - 1);
	}
}

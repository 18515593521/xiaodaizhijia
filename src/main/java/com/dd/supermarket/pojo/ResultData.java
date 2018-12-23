package com.dd.supermarket.pojo;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.app.utils.ParameterED;

/**
* @author 作者 E-mail: 
* @version 创建时间：2018年5月27日 上午1:30:24
* 类说明：
*/
public class ResultData {
	private int code;
	private String msg;
	private String data;
	
	public ResultData(CodeMsg codeMsg, Map<String,Object> resultMap) {
		this.code = codeMsg.getCode();
		this.msg = codeMsg.getMsg();
		this.data = this.encryption(resultMap);
	}
	public int getCode() {
		return this.code;
	}
	public String getMsg() {
		return this.msg;
	}
	public String getData() {
		return this.data==null?"":this.data;
	}
	@Override
	public String toString() {
		return "Result [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
	
	
	/**
	 * map加密
	 * @param map
	 * @return
	 */
	private String encryption(Map<String,Object> map){
		JSONObject json = new JSONObject(map);
		return ParameterED.parameterEncryption(json);
	}
	public static void main(String[] args) {
		ResultData rd = new ResultData(CodeMsg.CODE_FAIL, null);
		System.out.println(rd.getCode());
	}
	
	
}

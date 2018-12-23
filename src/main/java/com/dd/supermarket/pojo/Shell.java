package com.dd.supermarket.pojo;

import java.util.HashMap;
import java.util.Map;

public class Shell {
	private int code;
	private String msg;
	private Map<String,Object> data;
	
	public Shell(CodeMsg codeMsg, Map<String,Object> resultMap) {
		this.code = codeMsg.getCode();
		this.msg = codeMsg.getMsg();
		this.data = resultMap;
	}
	public int getCode() {
		return this.code;
	}
	public String getMsg() {
		return this.msg;
	}
	public Map<String,Object> getData() {
		return this.data==null?new HashMap<String,Object>():this.data;
	}
	@Override
	public String toString() {
		return "Result [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
}

package com.dd.supermarket.pojo;
/**
* @author 作者 E-mail: 
* @version 创建时间：2018年5月27日 上午1:33:54
* 类说明：返回状态码管理
*/
public enum CodeMsg {
	
	CODE_FAIL	 	(-1,	"系统繁忙，请稍后再试！"), 
	CODE_SUCCESS	(0,		"请求成功！"),
 
	//接口造成的原因
	CODE_10000		(10000,	"您的参数不合法！"),
	CODE_10001		(10001,	"参数值传入缺失！"),
	CODE_10002		(10002,	"密文无法解析！"),
    
	//用户造成的原因
	CODE_20000		(20000,	"手机号不正确！"),
	CODE_20001		(20001,	"验证码不正确！"),
	CODE_20002		(20002,	"该手机号已经注册过了！"),
	CODE_20003		(20003,	"该手机号还未注册！"),
	CODE_20004		(20004,	"密码输入错误！"),
	
	
	
	
	;
    private String msg ;
    private int code ;
     
    private CodeMsg(int code,String msg){
        this.msg = msg ;
        this.code = code ;
    }

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	

}

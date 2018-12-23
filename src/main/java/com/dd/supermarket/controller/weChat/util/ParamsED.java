/**
 * 
 */
package com.dd.supermarket.controller.weChat.util;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.utils.secretly.DESUtil;


/**
 * @author 2593707755  
 * 参数加密解密
 *
 */
public class ParamsED {
	private ParamsED(){}
	
	/**
	 * 接口所需真实密文 
	 */
	private final String PARENT_SECRET_KEY = "DD+dq/KB=zfSX9f18So/7X+269=f1a0138Eoff7241cf4gRyU+ETgq/KBzLpf9f1a0138Eo7af+AXs1Nx1yLIMe5m38f7e2c57bff3b7da";
	
	/**
	 * 加密后密文拼接字符串
	 */
	private final String PREFIX = "+8dq/K!B=My";
	
	/**
	 * 获得加密后的接口所需密文
	 */
	private String secret_key = "";
	
	private static ParamsED ped = null;
	public static ParamsED getParamsED(){
		if(null==ped)ped = new ParamsED();
		return ped;
	}
	/**
	 * 获得接口需要的密钥加密后密文
	 * @return
	 */
	public String getSecret_key() {
		if(null==this.secret_key||""==this.secret_key)
			this.secret_key = this.getSecret_keyValue();
		return this.secret_key;
	}
	private String getSecret_keyValue() {
		String result = this.PREFIX;
		try {
			result += this.encryption(this.PARENT_SECRET_KEY, DESUtil.SECRET_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 获取接口所需真实密文
	 * @return
	 */
	public String getTrueParent_secret_key(){
		String result = this.getSecret_key();
		result = result.replace(this.PREFIX, "");
		try {
			result = this.decryption(result, DESUtil.SECRET_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * DES 加密<br/>
	 * @param plainData	原始字符串<br/>
	 * @param secretKey	加密密钥<br/>
	 * @return	密文<br/>
	 * @throws Exception<br/>
	 */
	public String encryption(String plainData, String secretKey) throws Exception{
		String result = "";
		result = DESUtil.encryption(plainData, secretKey);
		return result;
	}
	
	 /**
     * DES 解密<br/>
     * @param secretData	密文<br/>
     * @param secretKey		解密密钥<br/>
     * @return 原始字符串<br/>
     * @throws Exception <br/>
     */
    private String decryption(String secretData, String secretKey) throws Exception {
    	String result = "";
		result = DESUtil.decryption(secretData.replaceAll(" ", ""), secretKey);
		return result;
    }
	 /**
     * DES 解密<br/>
     * @param secretData	密文<br/>
     * @param secretKey		解密密钥<br/>
     * @return 原始字符串<br/>
     * @throws Exception <br/>
     */
    public JSONObject decryptionToJSONObject(String secretData, String secretKey) throws Exception {
		JSONObject json = JSONObject.parseObject(this.decryption(secretData.replaceAll(" ", ""), secretKey));
		if(null==json)throw new Exception("在解析完成后转换成JsonObject后发现该字符串为 null JsonObject"+secretData);
		return json;
    }
    
	
	
    public static void main(String[] args) throws Exception {
    	ParamsED ped = ParamsED.getParamsED();
    	String secretKey = "";
    	secretKey = ped.getTrueParent_secret_key();
    	
//    	String e =  ped.PARENT_SECRET_KEY;
//    	System.out.println(e);
//    	e = ped.getSecret_key();
//		System.out.println(e);
//		e = e.replace("+8dq/K!B=My", "");
//		System.out.println(e);
//		try {
//			e = ped.decryption(e, "8d5426f4f9f1a01ff7241c388f7e2c57bff3b7da");
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		System.out.println(e);
    	
    	
//    	String data = "DF2hUOaDwsuKTsihw4c8U+Rbx99yMEWnRMZvhEnukGS8xIMa7OZSeIAIksBPi60WCPdka5A2QH4=";
//    	System.out.println(ped.getTrueParent_secret_key());
//    	
//    	data = ped.decryption(data, secretKey);
//    	System.out.println(data);
//    	JSONObject jsonO = JSONObject.parseObject(data);
//    	System.out.println(jsonO);
    	
    	
    	String odata = "{\"uu_id\":\"5177977b841248fe9c7feeaf8dc209bf\",\"ld_id\":\"9732e2ede6574ed795ef7e5975a7605b\",\"bc_id\":\"c688028d0054462abc1b1a654b839a06\"}";
    	odata=ped.encryption(odata, secretKey);
    	//System.out.println("加密后  "+ odata);
//    	odata = "D2uDLQmGqw0c+PUfyLHo1B9EE8dW2UIXbniyFFvNuD48/ZAvseUZvQbQDlriLK5Fnd9dNMMK8n1IO1KllDD5DZtDuFIjZqNpHCNvIwUR8xvJxgYFUvDaQrw8+wwHCJDtaaaAc650GM6yFaQ+5beLrl+IQCtzzv5Z86WlsOk0qa8=";
    	odata = ped.decryption(odata, secretKey);
    	//System.out.println("解密后 "+ odata);
    	
    	
    	
	}
	
	
}

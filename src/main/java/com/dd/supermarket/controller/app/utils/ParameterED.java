package com.dd.supermarket.controller.app.utils;

import java.util.Optional;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.app.secretly.SecretKey;
import com.dd.supermarket.utils.secretly.DESUtil;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月22日 下午5:58:56 <br/>
* 类说明：参数加密与解密
*/
public class ParameterED {
	private static String secretKey = SecretKey.getSecretKeyClass().getSecret_key();
	
	/**
	 * 参数加密
	 * @param json
	 * @return
	 */
	public static String parameterEncryption(JSONObject json){
		String data = json.toJSONString();
		try {
			data = DESUtil.encryption(data, secretKey);
		} catch (Exception e) {
			System.out.println("参数在加密时出错");
		}
		return data;
	}
	
	/**
	 * 参数解密
	 * @param data
	 * @return
	 */
	public static JSONObject parameterDecryption(String data){
		if((Optional.ofNullable(data).map(String::length).orElse(-1)<=0))return null;
		
		JSONObject json = new JSONObject();
		try {
			data = DESUtil.decryption(data, secretKey);
		} catch (Exception e) {
			System.out.println(">>>> 密文在解析时出错！ <<<<");
			return null;
		}
		json = JSONObject.parseObject(data);
		return json;
	}
	
	
	public static void main(String[] args) {
		System.out.println("密钥  == "+secretKey);
		JSONObject json = new JSONObject();
//		json.put("phone", "18705171791");
//		json.put("password", "123456");
//		json.put("sms_code", 123456);
//		{\"user_id\":\"0009cb29f63348c59a38f73c1a7d2671\",\"content\":\"反馈内容\"}
		json.put("user_id", "2a22fbf5eb2d40e78c0e5048a4ea13ae");
//		json.put("com_id", "b291ff1d79b711e89ca300163e06af89");
		json.put("page", 0);
		json.put("pageSize", 7);
		String data = parameterEncryption(json);
		System.out.println("加密后结果集  == "+data);
		json = parameterDecryption("PikegoISvyNiq0fmM8BX/5KZNptV7ZdZGnXKuue6Cm0J5ke74PjrCV+RZY4+BMAm80xz3VrSh8f5rqbszhOXGJc5i8VfZKwk4rJyYYQHaobeLd1A3+UbiE6ABzHHNJ55Xp2UuJneOC7C919GBPJ40yvSQzWUCsi8I4Gqzg4uytHx0omQvWKwn9wBsjw6CRgPJQecOG5YhUH/s7lzHon21XYTjPiA7c9g+s0xutThMg20AyZk/jUTh8XDtuC52veJvm51CwOeYJjeRjKMnF1OetnnJvUzPgyQ0eW/gtR5DxkxPjZJawl3rVfeFAmwNCGA1MctqCAxVIB9LRuMy+2vomZLnjYYotBwRTsxfqIsR3xXglp1o4gEzaQ+JtyoHNh/y3SDGmySYYf6Zg7aD1y0wJQHlgYpdn9TRK6T49adgeKmrVdxF9LgfGZFmFdYdtBwvKcslfvPgWQY9BDZcD+MbKrgF0PqkXdF+PE2oBci0xbOXlzcDsmmTivSQzWUCsi8I4Gqzg4uytHCe036mZkJ5TG3rJorK5+UdTpWhMBOnH82NStmJ+ZEh35T04cQ93jr4yca6wlVoHtCOr/+ppdzG/lfzr66Xn9wlvduApmrn39Xglp1o4gEzfm7gWujL9TzC1uaaojjFGMJ5ke74PjrCV+RZY4+BMAm29glxJdAkap+jWEtMRFfRZc5i8VfZKwk4rJyYYQHaobeLd1A3+UbiE6ABzHHNJ55Xp2UuJneOC6J+68eRNblUHMJamoAJCttwNz9EbhgLloZZ/gpnq/qDFyGuxTi2PBKBTAuviP6PII8f57HiyR0TQ==");
		System.out.println("解密后字符串 == "+json.toJSONString());
	}
}

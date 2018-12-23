/**
 *	
 */
package com.dd.supermarket.utils.factories;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * @author 作者 ：   <br/>
 *         E-mail:   <br/>
 * @version 创建时间： 2018年7月18日上午9:57:06 <br/>
 *          类说明： 字符串验证
 */
public class StringValidation {

	/**
	 * 判断字符串是否是数字
	 */
	public static boolean isNumber(String value) {
		return isInteger(value) || isDouble(value);
	}

	/**
	 * 判断字符串是否是整数
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是浮点数
	 */
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains("."))
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断是否是json结构
	 */
	public static boolean isJson(String value) {
		try {
			JSONObject.parseObject(value);
		} catch (JSONException e) {
			return false;
		}
		return true;
	}

}

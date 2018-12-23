package com.dd.supermarket.utils;

import java.util.UUID;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年5月29日 上午12:40:08 <br/>
* 类说明：
*/
public class UuidUtil {
	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
	public static void main(String[] args) {
		System.out.println(get32UUID());
	}
}

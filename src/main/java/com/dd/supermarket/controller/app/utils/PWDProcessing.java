package com.dd.supermarket.controller.app.utils;

import com.dd.supermarket.utils.secretly.SHAencrypt;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月22日 下午2:50:21 <br/>
* 类说明：用户密码加密
*/
public class PWDProcessing {
	private static final SHAencrypt sha = new SHAencrypt();
	/**
	 * 用户密码加密
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public static String encryptionPWD(String pwd) throws Exception{
		return sha.encryptSHA(pwd);
	}
	
	public static void main(String[] args) throws Exception {
		String ss = "我爱中国123";
		System.out.println(encryptionPWD(ss));
	} 
}

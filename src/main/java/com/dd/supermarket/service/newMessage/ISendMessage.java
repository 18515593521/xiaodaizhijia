package com.dd.supermarket.service.newMessage;


/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月23日 下午4:23:17 <br/>
* 类说明：发送短信
*/
public interface ISendMessage {
	
	/**
	 * 发送验证码
	 * @param phone	手机号
	 * @param type	验证码类型(0:注册验证码,5:重置密码验证码)
	 * @param ip
	 * @param remarks
	 * @return
	 * @throws Exception
	 */
	public int sendCode(String phone,int type,String ip, String remarks);
	
	/**
	 * 根据手机号以及短信类型和验证码查询条短信
	 * @param phone	手机号
	 * @param content	短信内容（验证码）
	 * @param type	验证码类型(0:注册验证码,5:重置密码验证码)
	 * @return
	 * @throws Exception
	 */
	public boolean find_messageByPhoneAndTypeAndCode(String phone,String content,int type);
}

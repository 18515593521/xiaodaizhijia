package com.dd.supermarket.service.app.user;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月25日 下午7:21:59 <br/>
* 类说明：
*/
public interface IAppLogin {
	
	/**
	 * 用户注册
	 * @param phone
	 * @param password
	 * @throws Exception
	 */
	public String register(String phone,String password,String sms_code,String user_source);
	
	/**
	 * 修改密码/重置密码
	 * @param phone		手机号
	 * @param password	新密码
	 * @param password	验证码
	 * @return
	 * @throws Exception
	 */
	public String resetPassword(String phone,String password,String sms_code);
	
	/**
	 * 帐号密码登录
	 * @param phone
	 * @param password
	 * @return
	 */
	public String logonToPassword(String phone,String password);
	
	/**
	 * 通过验证码登录
	 * @param phone
	 * @param sms_code
	 * @return
	 */
	public String logonToSMSCode(String phone,String sms_code);
	
	/**
	 * 通过手机号验证用户是否注册
	 * @param phone
	 * @return
	 */
	public String authenticateUserByPhone(String phone);
	
	
	//用户注册手机号比对名单
	public int find_namelist(String phone);
	//修改名单状态
	public String update_namelist(String user_id);
}

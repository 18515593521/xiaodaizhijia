package com.dd.supermarket.service.app.user.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.dao.app.user.AppLoginDao;
import com.dd.supermarket.dao.app.user.AppUserinfoDao;
import com.dd.supermarket.pojo.CodeMsg;
import com.dd.supermarket.service.app.user.IAppLogin;
import com.dd.supermarket.service.app.user.IAppUserinfo;
import com.dd.supermarket.service.newMessage.ISendMessage;
import com.dd.supermarket.utils.UuidUtil;
import com.dd.supermarket.utils.secretly.SHAencrypt;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月25日 下午7:31:22 <br/>
* 类说明：
*/
@Transactional
@Service("appLoginImpl")
public class AppLoginImpl implements IAppLogin{
	
	@Resource(name="appLoginDao")
	private AppLoginDao appLoginDao;
	
	@Resource(name="appUserinfoDao")
	private AppUserinfoDao appUserinfoDao;
	
	@Resource(name="appUserinfoImpl")
	private IAppUserinfo appUserinfo;
	
	@Resource(name="backSendMessageImpl")
	private ISendMessage backSendMessageImpl;

	/**
	 * 注册
	 */
	public String register(String phone, String password,String sms_code,String user_source){
		try {
			password = SHAencrypt.encryptSHA(password);
		} catch (Exception e) {
			System.out.println("============= 注册时密码加密出错 =============");
			e.printStackTrace();
			return ""+CodeMsg.CODE_FAIL.getCode();	
		}
		
		//查询比对验证码	========================================
		boolean inCode = false;
		try {
			inCode = backSendMessageImpl.find_messageByPhoneAndTypeAndCode(phone, sms_code, 0);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("============= 查询注册验证码时出错 =============");
			return ""+CodeMsg.CODE_FAIL.getCode();									//系统繁忙请稍后再试
		}
		if(!inCode)return ""+CodeMsg.CODE_20001.getCode();							//验证码输入错误
		
		//查询用户信息	========================================
		Map<String,Object> userinfo = new HashMap<String,Object>();
		try {
			userinfo = (Map<String, Object>) appUserinfoDao.find_userinfoByPhone(phone);		//查询用户信息
		} catch (Exception e) {
			System.out.println("============= 注册用户查询用户时出错 =============");
			e.printStackTrace();
			return ""+CodeMsg.CODE_FAIL.getCode();											//系统繁忙请稍后再试
		}
		
		if(null!=userinfo)return ""+CodeMsg.CODE_20002.getCode();							//用户已经注册过了
		
		String user_id = UuidUtil.get32UUID();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_id", user_id);
		map.put("user_phone", phone);
		map.put("user_password", password);
		map.put("user_source", user_source);
		try {
			appLoginDao.save_userinfo(map);//注册
		} catch (Exception e) {
			System.out.println("============ 用户注册时出错 ============");
			e.printStackTrace();
			return ""+CodeMsg.CODE_FAIL.getCode();									//系统繁忙请稍后再试
		}
		return user_id;
	}

	/**
	 * 重置密码
	 */
	public String resetPassword(String phone, String password, String sms_code){
		try {
			password = SHAencrypt.encryptSHA(password);
		} catch (Exception e) {
			System.out.println("============= 修改用户密码 密码加密时出错 =============");
			e.printStackTrace();
			return ""+CodeMsg.CODE_FAIL.getCode();	
		}
		
		
		//查询比对验证码	========================================
		boolean inCode = false;
		try {
			inCode = backSendMessageImpl.find_messageByPhoneAndTypeAndCode(phone, sms_code, 5);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("============= 查询重置密码验证码时出错 =============");
			return ""+CodeMsg.CODE_FAIL.getCode();										//系统繁忙请稍后再试
		}
		if(!inCode)return ""+CodeMsg.CODE_20001.getCode();								//验证码输入错误
		
		
		//修改密码	========================================
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_phone", phone);
		map.put("user_password", password);
		
		try {
			appLoginDao.update_userinfoPassword_ByPhone(map);							//重置密码(修改密码)
		} catch (Exception e) {
			System.out.println("============= 修改用户密码时出错 =============");
			e.printStackTrace();
			return ""+CodeMsg.CODE_FAIL.getCode();										//系统繁忙请稍后再试
		}
		//查询用户信息	========================================
		try {
			map = (Map<String, Object>) appUserinfoDao.find_userinfoByPhone(phone);		//查询用户信息
		} catch (Exception e) {
			System.out.println("============= 修改用户密码查询用户时出错 =============");
			e.printStackTrace();
			return ""+CodeMsg.CODE_FAIL.getCode();										//系统繁忙请稍后再试
		}
		
		if(null==map)return ""+CodeMsg.CODE_20003.getCode();							//用户未注册
		if(!map.containsKey("user_id"))return ""+CodeMsg.CODE_20003.getCode();			//用户未注册
		return (String) map.get("user_id");
	}

	/**
	 * 密码登录
	 */
	public String logonToPassword(String phone,String password){
		Map<String,Object> userinfo = new HashMap<String,Object>();	
		
		try {
			userinfo = appUserinfo.find_userinfoByPhone(phone);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("============= 查询用户信息时出错=============");
			return ""+CodeMsg.CODE_FAIL.getCode();											//查询用户信息时出错
		}
		if(null==userinfo)return ""+CodeMsg.CODE_20003.getCode();							//该手机未注册
		if(!userinfo.containsKey("user_password"))return ""+CodeMsg.CODE_FAIL.getCode();	//系统繁忙
		
		try {
			password = SHAencrypt.encryptSHA(password);
		} catch (Exception e) {
			System.out.println("=========== 密码登录时密码加密失败 ==========");
			e.printStackTrace();
			return ""+CodeMsg.CODE_FAIL.getCode();											//系统繁忙
		}
		
		String user_password = (String) userinfo.get("user_password");
		if(!user_password.equals(password))return ""+CodeMsg.CODE_20004.getCode();			//系统繁忙
		
		return (String) userinfo.get("user_id");
	}

	/**
	 * 通过验证码登录
	 */
	public String logonToSMSCode(String phone, String sms_code) {
		Map<String,Object> userinfo = new HashMap<String,Object>();	
		
		//查询比对验证码	========================================
		boolean inCode = false;
		try {
			inCode = backSendMessageImpl.find_messageByPhoneAndTypeAndCode(phone, sms_code, 10);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("============= 查询注册验证码时出错 =============");
			return ""+CodeMsg.CODE_FAIL.getCode();											//系统繁忙请稍后再试
		}
		if(!inCode)return ""+CodeMsg.CODE_20001.getCode();									//验证码输入错误
		
		//查询用户信息 ========================================
		try {
			userinfo = appUserinfo.find_userinfoByPhone(phone);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("============= 查询用户信息时出错=============");
			return ""+CodeMsg.CODE_FAIL.getCode();											//系统繁忙请稍后再试
		}
		if(null==userinfo)return ""+CodeMsg.CODE_20003.getCode();							//该手机未注册
		if(!userinfo.containsKey("user_id"))return ""+CodeMsg.CODE_20003.getCode();			//该手机未注册
		
		return (String) userinfo.get("user_id");
	}

	/**
	 * 通过手机号验证手机是否已经注册
	 */
	public String authenticateUserByPhone(String phone) {
		//查询用户信息 ========================================
		Map<String,Object> userinfo = new HashMap<String,Object>();
		try {
			userinfo = appUserinfo.find_userinfoByPhone(phone);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("============= 查询用户信息时出错=============");
			return ""+CodeMsg.CODE_FAIL.getCode();											//系统繁忙请稍后再试
		}
		if(null!=userinfo) return ""+CodeMsg.CODE_20002.getCode();							//该手机号已经注册过了！
		else return ""+CodeMsg.CODE_20003.getCode();										//该手机未注册
	}
	
	//用户注册手机号比对名单
	public int find_namelist(String phone){
		return appLoginDao.find_namelist(phone);
	}
	
	//修改名单状态
	public String update_namelist(String user_id){
		
		try {
			appLoginDao.update_namelist(user_id);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("============= 查询用户信息时出错=============");
			return ""+CodeMsg.CODE_FAIL.getCode();											//系统繁忙请稍后再试
		}
		return ""+CodeMsg.CODE_SUCCESS.getCode();		
		
	}
	

}

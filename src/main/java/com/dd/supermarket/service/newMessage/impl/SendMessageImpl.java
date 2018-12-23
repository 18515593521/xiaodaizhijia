package com.dd.supermarket.service.newMessage.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.dao.back.newMessage.ShortMessageDao;
import com.dd.supermarket.service.newMessage.ISendMessage;
import com.dd.supermarket.service.yunbei.IYBMessage;
import com.dd.supermarket.utils.random.RandomAZor09;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月23日 下午4:31:36 <br/>
* 类说明：
*/
@Transactional
@Service("backSendMessageImpl")
public class SendMessageImpl implements ISendMessage {
	
	@Resource(name="backShortMessageDao")
	private ShortMessageDao smd;
	
	@Resource(name="ybMessageImpl")
	private IYBMessage ybmessage;

	public int sendCode(String phone,int type,String ip, String remarks){
		int code = RandomAZor09.getNum(100000, 999999);
		String response_value = ybmessage.send_message(phone, code);	//云贝短信发送
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sms_phone", phone);
		map.put("sms_content", code);
		map.put("sms_type", type);
		map.put("sms_ip", ip);
		map.put("response_value", response_value);
		map.put("remarks", remarks);
		smd.save_message(map);
		if(""==response_value)return -1;
		return code;
	}

	/**
	 * 验证短信验证码是否正确<br>
	 * @param phone		手机号<br>
	 * @param content	验证码<br>
	 * @param type		0:注册验证码<br>
	 * @return	正确：true, 错误：false<br>
	 * @throws Exception 
	 */
	public boolean find_messageByPhoneAndTypeAndCode(String phone,String content,int type){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sms_phone", phone);
		map.put("sms_content", content);
		map.put("sms_type", type);
		int i = (Integer)smd.find_messageByPhoneAndTypeAndCode(map);
		return i>0;
	}


}

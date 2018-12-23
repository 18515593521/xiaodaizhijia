package com.dd.supermarket.service.app.pool.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.service.app.pool.IAppControl;
import com.dd.supermarket.service.back.IDictionary;

/**
 * @author 	作者 ：	  <br/>
 *			E-mail:	  <br/>
 * @version 创建时间：	2018年7月16日下午3:18:33 <br/>
 * 类说明：	APP基本所需控制类
 */
@Transactional
@Service("appControlImpl")
public class AppControlImpl implements IAppControl{
	
	@Resource(name="backDictionaryImpl")
	private IDictionary idi;

	public String getIosVersion() {
		List<Map<String,Object>> iosShelves = idi.find_dictionByType("iosShelves");
		Map<String, Object> result = new HashMap<String,Object>();
		for (Map<String, Object> map : iosShelves) {
			if(null==map.get("dic_key"))continue;
			result.put((String) map.get("dic_key"),map.get("dic_value"));
		}
		return (String)result.get("iosVersion");
	}

	public Map<String, Object> getAndroidVersions() {
		List<Map<String,Object>> iosShelves = idi.find_dictionByType("androidShelves");
		Map<String, Object> result = new HashMap<String,Object>();
		String dic_key = "";
		for (Map<String, Object> map : iosShelves) {
			dic_key = (String) map.get("dic_key");
			if(null==dic_key||"".equals(dic_key))continue;
			result.put(dic_key,map.get("dic_value"));
		}
		return result;
	}

}

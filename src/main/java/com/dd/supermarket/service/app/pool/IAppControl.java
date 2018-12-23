package com.dd.supermarket.service.app.pool;

import java.util.Map;

/**
 * @author 	作者 ：	  <br/>
 *			E-mail:	  <br/>
 * @version 创建时间：	2018年7月16日下午3:16:09 <br/>
 * 类说明：		
 */
public interface IAppControl {
	
	public String getIosVersion();
	
	public Map<String,Object> getAndroidVersions();
}

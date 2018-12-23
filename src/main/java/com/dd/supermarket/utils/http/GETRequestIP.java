package com.dd.supermarket.utils.http;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.druid.util.StringUtils;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月23日 下午5:32:30 <br/>
* 类说明：
*/
public class GETRequestIP {
	
	/**
	 * 获取客户端IP
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		if(null==ip)ip=getIpAddress(request);
		return null!=ip?ip:request.getRemoteAddr();
	}
	
	private static String getIpAddress(HttpServletRequest request) {   
        String ip = request.getHeader("x-forwarded-for");   
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
          ip = request.getHeader("Proxy-Client-IP");   
        }   
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
          ip = request.getHeader("WL-Proxy-Client-IP");   
        }   
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
          ip = request.getHeader("HTTP_CLIENT_IP");   
        }   
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
          ip = request.getHeader("HTTP_X_FORWARDED_FOR");   
        }   
        return ip;   
      }  
}

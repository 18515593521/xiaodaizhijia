package com.dd.supermarket.utils.http;

import javax.servlet.http.HttpServletRequest;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年5月29日 上午12:50:41 <br/>
* 类说明：
*/
public class GetServer {
	/**
	 * 获取当前服务器ip地址以及端口号
	 * 
	 * @param request
	 * @return 服务器地址以及端口号，如：http://192.168.130.1:8080
	 */
	public String getServerUrl(HttpServletRequest request) {
		String http = request.getScheme();
		String sname = request.getServerName();
		int port = request.getServerPort();
		String projectName = request.getContextPath();
		String sp = http + "://" + sname + ":" + port + projectName+"/";
		
		return sp;
	}
	
	/**
	 * 获取服务器路径
	 * @param request
	 * @return
	 */
	public String getServerPath(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/");
	}
	
	
}

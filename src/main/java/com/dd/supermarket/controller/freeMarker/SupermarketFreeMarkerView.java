package com.dd.supermarket.controller.freeMarker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月24日 下午4:06:11 <br/>
* 类说明：
*/
public class SupermarketFreeMarkerView extends FreeMarkerView {
	private static final String CONTEXT_PATH = "basePath"; 
	@Override
	protected void exposeHelpers(Map<String, Object> model,HttpServletRequest request) throws Exception {
		model.put(CONTEXT_PATH, request.getContextPath()+"/static/");
		super.exposeHelpers(model, request);
	}
}

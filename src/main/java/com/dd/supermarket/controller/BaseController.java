package com.dd.supermarket.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.dd.supermarket.utils.PageData;
import com.dd.supermarket.utils.UuidUtil;

/**
* @author 作者 E-mail: 
* @version 创建时间：2018年5月27日 上午1:17:09
* 类说明：
*/
public class BaseController {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	int initEcho=0;
	int page=0;
	Integer sEcho=0;// 记录操作的次数 每次加1 
	Integer iDisplayStart=0;// 起始  
	Integer iDisplayLength=1;// 每页显示的size
	
	/** new PageData对象
	 * @return
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	/**得到ModelAndView
	 * @return
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/**得到request对象
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	/**得到32位的uuid
	 * @return
	 */
	public String get32UUID(){
		return UuidUtil.get32UUID();
	}
	
	/**得到分页列表的信息
	 * @return
	 */
	/*public Page getPage(){
		return new Page();
	}*/
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
	
	/**
	 * ajax返回
	 * @param code
	 * @param msg
	 * @param data
	 * @return
	 */
	public Map<String, Object> getResultMap(String code,String msg,Object data){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("code",code);
		map.put("msg",msg);
		map.put("data",data);
		return map;
	}
	
	
	/**
	 * 分页返回
	 * @param list
	 * @param p
	 * @return
	 */
	public JSONObject getResultJson(List list,PageInfo p){
		JSONArray jsonArr =JSONArray.parseArray(JSON.toJSONString(list));
		JSONObject json = new JSONObject();
		json.put("sEcho", this.getInitEcho());  
		json.put("iTotalRecords", p.getTotal());//数据总条数  
		json.put("iTotalDisplayRecords", p.getTotal());//显示的条数  
		json.put("aData", jsonArr);
		return json;
	}
	
	
	
	public PageInfo getPageInfo(List list){
        initEcho = sEcho + 1;  
		return new PageInfo(list);
	}

	
	public void getPager(){
		if (!StringUtils.isEmpty(this.getPageData().getString("sEcho"))) {
			this.setsEcho(Integer.valueOf(this.getPageData().getString("sEcho")));// 记录操作的次数 每次加1  
		}
		if (!StringUtils.isEmpty(this.getPageData().getString("iDisplayStart"))) {
			this.setiDisplayStart(Integer.valueOf(this.getPageData().getString("iDisplayStart")));
		}
		if (!StringUtils.isEmpty(this.getPageData().getString("iDisplayLength"))) {
			this.setiDisplayLength(Integer.valueOf(this.getPageData().getString("iDisplayLength")));
		}
		if (iDisplayStart/iDisplayLength<1) {
        	this.setPage(1);
		}else{
			if (iDisplayStart%iDisplayLength==0) {
				this.setPage(iDisplayStart/iDisplayLength+1);
			}else{
				this.setPage(iDisplayStart/iDisplayLength);
			}
		}
		PageHelper.startPage(page, iDisplayLength);
	}
	
	
	
	
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page=page;
	}

	public Integer getsEcho() {
		return sEcho;
	}

	public void setsEcho(Integer sEcho) {
			this.sEcho = sEcho;
		
	}

	public Integer getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(Integer iDisplayStart) {
			this.iDisplayStart = iDisplayStart;
	}

	public Integer getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(Integer iDisplayLength) {
			this.iDisplayLength = iDisplayLength;
	}

	public int getInitEcho() {
		return initEcho;
	}

	public void setInitEcho(int initEcho) {
		this.initEcho = initEcho;
	}
	
	
}

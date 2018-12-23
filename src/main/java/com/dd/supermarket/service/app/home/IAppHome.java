package com.dd.supermarket.service.app.home;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月26日 下午4:35:30 <br/>
* 类说明：
*/
public interface IAppHome {
	
	/**
	 * 查询APP首页所需banner图
	 * @param request
	 * @return
	 */
	public List<Map<String,Object>> find_banner(HttpServletRequest request);
	
	/**
	 * 查询借款快讯包含其它信息（弃用）
	 * @return
	 */
	public List<Map<String,Object>> find_news_flashMap();
	/**
	 * 查询借款快讯仅标题
	 * @return
	 */
	public List<String> find_news_flashString();
	
	/**
	 * 查询商品列表
	 * @param request
	 * @param sort_rule	排序规则
	 * @param page		页数 首页为0
	 * @param pageSize	查询大小
	 * @return
	 */
	public List<Map<String,Object>> find_commodity(HttpServletRequest request,String sort_rule,int page,int pageSize);
	
	/**
	 * 查询商品详细信息
	 * @param user_id
	 * @param com_id
	 * @return
	 */
	public Map<String,Object> find_commodityDetails(HttpServletRequest request,String user_id,String com_id);
	
	/**
	 * 查询用户消息已阅读数
	 * @param user_id
	 * @return
	 */
	public int find_userReadCount(String user_id);
	
	/**
	 * 查找消息总数
	 * @param uu_id
	 * @return
	 */
	public int find_newsCount(String user_id);
	
	/**
	 * 根据商品特色获取商品集合
	 * @param request
	 * @return
	 */
	public List<Map<String,Object>> CommodityByCharacteris(HttpServletRequest request);
}

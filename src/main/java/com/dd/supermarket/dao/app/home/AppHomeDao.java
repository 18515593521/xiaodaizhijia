package com.dd.supermarket.dao.app.home;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月26日 下午4:38:57 <br/>
* 类说明：
*/
@Repository("appHomeDao")
public class AppHomeDao {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	/**
	 * 查询APP首页banner图
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> find_banner()throws Exception{
		return baseDao.findForList("appHome.find_banner", null);
	}
	/**
	 * 查询APP跑马灯（资讯）
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> find_news_flash()throws Exception {
		return baseDao.findForList("appHome.find_news_flash", null);
	}
	/**
	 * 获取商品列表
	 * @param map page:页数 pageSize:页大小
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> find_commodityList(Map map)throws Exception {
		return baseDao.findForList("appHome.find_commodityList", map);
	}
	/**
	 * 获取商品详情
	 * @param com_id
	 * @return
	 * @throws Exception
	 */
	public Object find_commodityDetails(String com_id)throws Exception {
		return baseDao.findOne("appHome.find_commodityDetails", com_id);
	}
	/**
	 * 获取用户已读消息数 
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public Object find_userReadCount(String user_id)throws Exception  {
		return baseDao.findOne("appHome.find_userReadCount", user_id);
	}
	/**
	 * 获取用户未读消息数
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public Object find_newsCount(String user_id)throws Exception  {
		return baseDao.findOne("appHome.find_newsCount", user_id);
	}
	/**
	 * 通过商品分类ID查询商品列表
	 * @param dic_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> find_commodityListByDic_id(String dic_id)throws Exception{
		return baseDao.findForList("appHome.find_commodityListByDic_id", dic_id);
	}
	
	
}

package com.dd.supermarket.service.app.home.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.controller.app.utils.PathFactory;
import com.dd.supermarket.dao.app.home.AppHomeDao;
import com.dd.supermarket.fixed.AppConst;
import com.dd.supermarket.fixed.PathConst;
import com.dd.supermarket.service.app.home.IAppHome;
import com.dd.supermarket.service.back.IDictionary;
import com.dd.supermarket.utils.http.GetServer;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月26日 下午4:37:18 <br/>
* 类说明：
*/
@Transactional
@Service("appHomeImpl")
public class AppHomeImpl implements IAppHome {
	
	@Resource(name="appHomeDao")
	private AppHomeDao appHomeDao;
	
	@Resource(name="backDictionaryImpl")
	private IDictionary iDiction;

	/**
	 * 获取APP首页banner图
	 */
	public List<Map<String, Object>> find_banner(HttpServletRequest request) {
		List<Map<String, Object>> findResutl = null;
		try {
			findResutl = appHomeDao.find_banner();
		} catch (Exception e) {
			System.out.println("=========== APP查询首页banner图时出错 ===========");
			e.printStackTrace();
		}
		if(null == findResutl)return new ArrayList<Map<String, Object>>();
		
		String serverUrl = new GetServer().getServerUrl(request);
		PathFactory pf = new PathFactory();
		for (Map<String, Object> fr : findResutl) {
			fr = pf.bannersFactory(serverUrl, fr);
		}
		return findResutl;
	}
	


	/**
	 * 查询借款快讯包含其它信息（弃用）
	 */
	public List<Map<String, Object>> find_news_flashMap() {
		List<Map<String, Object>> findResutl = null;
		try {
			findResutl = appHomeDao.find_news_flash();
		} catch (Exception e) {
			System.out.println("=========== APP 查询借款快讯时出错 ===========");
			e.printStackTrace();
		}
		return null == findResutl?new ArrayList<Map<String, Object>>():findResutl;
	}
	
	/**
	 * 查询借款快讯仅标题
	 */
	public List<String> find_news_flashString() {
		List<String> result = new ArrayList<String>();
		
		List<Map<String, Object>> findResutl = this.find_news_flashMap();
		if(findResutl.isEmpty())return result;
		
		for (Map<String, Object> map : findResutl) {
			result.add((String)map.get("nf_title"));
		}
		
		return result;
	}

	/**
	 * 查询商品列表
	 */
	public List<Map<String, Object>> find_commodity(HttpServletRequest request,String sort_rule,int page, int pageSize) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sort_rule", sort_rule);
		map.put("page", page*pageSize);
		map.put("pageSize", pageSize);
		List<Map<String, Object>> findResutl = null;
		try {
			findResutl = appHomeDao.find_commodityList(map);
		} catch (Exception e) {
			System.out.println("=========== APP查询商品列表时出错 ===========");
			e.printStackTrace();
		}
		String serverUrl = new GetServer().getServerUrl(request);//获取服务器地址
		PathFactory pf = new PathFactory();
		for (Map<String, Object> fr : findResutl) {
			fr = pf.commodityFactory(serverUrl, fr);
			map = this.amountToInteger("loan_amount", map);
		}
		return null == findResutl?new ArrayList<Map<String, Object>>():findResutl;
	}
	
	/**
	 * 根据商品特色获取商品集合
	 */
	public List<Map<String, Object>> CommodityByCharacteris(HttpServletRequest request) {
		List<Map<String,Object>> dictionList = iDiction.find_dictionByType("commodityCharacteris");
		List<Map<String,Object>> commditList = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : dictionList) {
			if(map.get("dic_key").toString().isEmpty())continue;
			String dic_id		= 		(String)map.get("dic_id");
			String dic_key		= 		(String)map.get("dic_key");
			String dic_value	= 		(String)map.get("dic_value");
			
			map.clear();
			List<Map<String,Object>> resultList = null;
			resultList = this.find_commodityListByDic_id(new GetServer().getServerUrl(request), dic_id);
			if(null==resultList || resultList.size()<=0)continue;
			map.put("dic_key", dic_key);
			map.put("dic_value", dic_value);
			map.put("commodities", resultList);
			
			commditList.add(map);
		}
		return commditList;
	}
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}
	
	/**
	 * 处理商品金额，将分转换为元
	 * @param key	需要处理的键
	 * @param map	
	 * @return
	 */
	private Map<String,Object> amountToInteger(String key,Map<String,Object> map){
		if(!map.containsKey(key))return map;
		Integer value = Integer.parseInt((String)map.get(key));
		if(null==value)return map;
		map.put(key, value/100);
		return map;
	}
	
	/**
	 * 根据商品类型获取商品列表
	 * @param serverUrl
	 * @param dic_id
	 * @return
	 */
	private List<Map<String, Object>> find_commodityListByDic_id(String serverUrl,String dic_id){
		List<Map<String,Object>> commditList = null;
		try {
			commditList = appHomeDao.find_commodityListByDic_id(dic_id);
		} catch (Exception e) {
			System.out.println("=========== 根据商品特色获取商品集合时出错 ===========");
			e.printStackTrace();
		}
		if(null==commditList)return null;
		
		PathFactory pf = new PathFactory();
		for (Map<String, Object> fr : commditList) {
			fr = pf.commodityFactory(serverUrl, fr);
			fr = this.amountToInteger("loan_amount", fr);
		}
		return commditList;
	}


	/**
	 * 查询商品详情
	 */
	public Map<String, Object> find_commodityDetails(HttpServletRequest request,String user_id, String com_id) {
		Map<String, Object> data = null;
		try {
			data = (Map<String, Object>) appHomeDao.find_commodityDetails(com_id);
			data = null==data?new HashMap<String,Object>():new PathFactory().commodityFactory(new GetServer().getServerUrl(request), data);//处理图片路径
			data = this.amountToInteger("loan_amount", data);		//处理最高放款额
			data = this.amountToInteger("everyday_interest", data);	//处理每日利息
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 已阅读数
	 */
	public int find_userReadCount(String user_id) {
		Integer readCount = 0;
		if(null==user_id || user_id=="")return readCount;
		try {
			readCount = (Integer) appHomeDao.find_userReadCount(user_id);
			readCount = null==readCount?0:readCount;
		} catch (Exception e) {
			System.out.println("============= 查询消息已阅读数时出错 =============");
			e.printStackTrace();
			return readCount;
		}
		return readCount;
	}

	/**
	 * 消息总数
	 */
	public int find_newsCount(String user_id) {
		Integer unreadCount = 0;
		if(null==user_id || user_id=="")return unreadCount;
		try {
			unreadCount = (Integer)appHomeDao.find_newsCount(user_id);
			unreadCount = null==unreadCount?0:unreadCount;
		} catch (Exception e) {
			System.out.println("============= 查询消息未阅读数时出错 =============");
			e.printStackTrace();
			return unreadCount;
		}
		return unreadCount;
	}

}

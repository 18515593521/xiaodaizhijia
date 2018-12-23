package com.dd.supermarket.controller.app.utils;

import java.util.Map;

import com.dd.supermarket.fixed.PathConst;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月28日 下午4:27:04 <br/>
* 类说明：
*/
public class PathFactory {
	
	/**
	 * 处理首页滚动图的链接地址
	 * @param map
	 * @return
	 */
	public Map<String,Object> bannersFactory(String serverUrl,Map<String, Object> map){
		/*if(!map.containsKey("ban_picture"))return map;
		String ban_picture = (String) map.get("ban_picture");
		ban_picture = serverUrl+PathConst.HOME_BANNER_PIC_URL+ban_picture;
		map.put("ban_picture", ban_picture);
		return map;*/
		return picFactory(serverUrl+PathConst.HOME_BANNER_PIC_URL,"ban_picture", map);
	}

	/**
	 * 商品logo地址处理
	 * @param serverUrl
	 * @param map
	 * @return
	 */
	public Map<String, Object> commodityFactory(String serverUrl, Map<String, Object> map) {
		return picFactory(serverUrl+PathConst.COMMODITY_LOGO_PIC_URL,"com_logo", map);
	}
	
	/**
	 * 推送图片地址处理
	 * @param serverUrl
	 * @param map
	 * @return
	 */
	public Map<String, Object> pushFactory(String serverUrl, Map<String, Object> map) {
		return picFactory(serverUrl+PathConst.PUSH_PIC_URL,"up_picture", map);
	}
	
	/**
	 * 关于我们二维码图片地址处理
	 * @param serverUrl
	 * @param map
	 * @return
	 */
	public Map<String, Object> aboutUSFactory(String serverUrl, Map<String, Object> map) {
		return picFactory(serverUrl+PathConst.ABOUT_US_WECHAT_PIC_URL,"companyWeChatPicture", map);
	}
	
	/**
	 * 意见反馈图片地址处理
	 * @param serverUrl
	 * @param map
	 * @return
	 */
	public Map<String, Object> feedbackFactory(String serverUrl, Map<String, Object> map) {
		return picFactory(serverUrl+PathConst.FEEDBACK_PIC_URL,"uf_picture", map);
	}
	
	/**
	 * 结算明细图片地址处理
	 * @param serverUrl
	 * @param map
	 * @return
	 */
	public Map<String, Object>  settlementFactory(String serverUrl, Map<String, Object> map) {
		return picFactory(serverUrl+PathConst.COMMODITY_SETTLEMENT_PIC_URL,"cs_picture", map);
	}
	
	/**
	 * app银行卡图片地址处理
	 * @param serverUrl
	 * @param map
	 * @return
	 */
	public Map<String, Object>  settlBankFactory(String serverUrl, Map<String, Object> map) {
		return picFactory(serverUrl+PathConst.BACK_CARD_PIC_URL,"bc_img", map);
	}
	
	
	/**
	 * 壳消息图地址处理
	 * @param serverUrl
	 * @param map
	 * @return
	 */
	public Map<String, Object>  shelNewslFactory(String serverUrl, Map<String, Object> map) {
		return picFactory(serverUrl+PathConst.SHELL_NEWS_URL,"up_picture", map);
	}
	
	/**
	 * 壳商品图地址处理
	 * @param serverUrl
	 * @param map
	 * @return
	 */
	public Map<String, Object>  shellCommFactory(String serverUrl, Map<String, Object> map) {
		return picFactory(serverUrl+PathConst.SHELL_COMM_URL,"com_picture", map);
	}
	
	/**
	 * 壳首页轮播图片地址处理
	 * @param serverUrl
	 * @param map
	 * @return
	 */
	public Map<String, Object>  shellBannerPicture(String serverUrl, Map<String, Object> map) {
		return picFactory(serverUrl+PathConst.SHELL_BROADCAST_URL,"banner_picture", map);
	}

	private Map<String, Object> picFactory(String serverUrl,String key, Map<String, Object> map) {
		if(!map.containsKey(key))return map;
		String value = (String) map.get(key);
		if(value.isEmpty())return map;
		value = serverUrl+value;
		map.put(key, value);
		return map;
	}
}

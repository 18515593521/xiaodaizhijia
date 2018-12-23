package com.dd.supermarket.dao.back.newMessage;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月23日 下午4:35:18 <br/>
* 类说明：
*/
@Repository("backShortMessageDao")
public class ShortMessageDao {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	public void save_message(Map map){
		baseDao.save("backShortMessage.save_message",map);
	}
	
	/**
	 * 根据手机号以及短信类型查询一条短信
	 * @param map
	 * @return
	 */
	public Object find_messageByPhoneAndTypeAndCode(Map map){
		return baseDao.findOne("backShortMessage.find_messageByPhoneAndTypeAndCode",map);
	}
	
}

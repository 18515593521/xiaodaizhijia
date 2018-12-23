package com.dd.supermarket.dao.app.user;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月26日 上午12:15:29 <br/>
* 类说明：
*/
@Repository("appUserinfoDao")
public class AppUserinfoDao {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	/**
	 * 通过手机号查询用户信息
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public Object find_userinfoByPhone(String phone)throws Exception{
		return baseDao.findOne("appUserinfo.find_userinfoByPhone", phone);
	}

	/**
	 * 保存用户足迹
	 * @param map
	 * @throws Exception
	 */
	public void save_userFootprint(Map<String, String> map)throws Exception {
		baseDao.save("appUserinfo.save_userFootprint", map);
	}
	
	/**
	 * 查询用户足迹
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public List<Object> find_userFootprintByUserId(Map<String,Object> map)throws Exception{
		return baseDao.findForList("appUserinfo.find_userFootprintByUserId", map);
	}
	
	/**
	 * 查询用户足迹
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public Object find_userFootprintCountByUserId(String user_id)throws Exception{
		return baseDao.findOne("appUserinfo.find_userFootprintCountByUserId", user_id);
	}
	
	/**
	 * 保存用户意见反馈
	 * @param map
	 * @throws Exception
	 */
	public void save_userFeedback(Map<String,String> map)throws Exception{
		baseDao.save("appUserinfo.save_userFeedback", map);
	}
	
	/**
	 * 查询用户所有消息
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public List<Object> find_userPushByUserId(Map<String,Object> map)throws Exception{
		return baseDao.findForList("appUserinfo.find_userPushByUserId", map);
	}
	
	/**
	 * 修改用户已读信息数量
	 * @param map
	 * @throws Exception
	 */
	public void update_userinfosReadCountByUserId(Map<String,Object> map)throws Exception{
		baseDao.update("appUserinfo.update_userinfosReadCountByUserId", map);
	}

	/**
	 * 保存商务合作信息
	 * @param map
	 */
	public void save_cooperation(Map<String, Object> map)throws Exception {
		baseDao.save("appUserinfo.save_cooperation", map);
	}
	
	/**
	 * 保存预申请量
	 * @param map
	 * @throws Exception
	 */
	public void save_Apply(Map<String, String> map)throws Exception {
		baseDao.save("appUserinfo.save_Apply", map);
	}
	
	
	/**
	 * 保存用户token
	 * @param map
	 * @throws Exception
	 */
	public void up_token(Map map){
		baseDao.save("appUserinfo.up_token", map);
	}
	
	
	/**
	 * 保存用户手机型号 app版本号
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public void up_appNV(Map map){
		 baseDao.update("appUserinfo.up_appNV", map);
	}
	
	
}

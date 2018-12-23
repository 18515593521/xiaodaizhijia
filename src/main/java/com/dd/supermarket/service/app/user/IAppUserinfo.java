package com.dd.supermarket.service.app.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月26日 上午12:14:05 <br/>
* 类说明：
*/
public interface IAppUserinfo {

	/**
	 * 通过用户手机号查询用户
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> find_userinfoByPhone(String phone)throws Exception;
	
	/**
	 * 添加用户足迹
	 * @param user_id
	 * @param com_id
	 */
	public void add_userFootprint(String user_id,String com_id);
	
	/**
	 * 通过用户ID查询用户足迹
	 * @param user_id
	 * @return
	 */
	public List<Object> find_userFootprintByUserId(HttpServletRequest request,String user_id,int page,int pageSize);
	
	/**
	 * 查询用户足迹总数
	 * @param user_id
	 * @return
	 */
	public int find_userFootprintCountByUserId(String user_id);
	
	/**
	 * 保存意见反馈
	 * @param request
	 * @param user_id	用户ID
	 * @param content	问题内容
	 * @param file		问题图片
	 */
	public String add_userFeedback(HttpServletRequest request,String user_id,String content,MultipartFile file);
	
	/**
	 * 通过用户ID查询用户个人推送消息
	 * @param user_id
	 * @return
	 */
	public List<Object> find_userPushByUserId(HttpServletRequest request,String user_id,int page,int pageSize);

	/**
	 * 获取关于我们数据
	 * @return
	 */
	public Map<String, Object> getAboutUSData(HttpServletRequest request);
	
	/**
	 * 保存商务合作信息
	 * @param user_id
	 * @param email
	 * @param phone
	 * @param companyName
	 * @param notes
	 * @return
	 */
	public String save_cooperation(String user_id, String email, String phone, String companyName, String notes);
	
	public String save_Apply(String user_id,String com_id);
	
	/**
	 * 保存用户token
	 * @param map
	 * @throws Exception
	 */
	public void up_token(Map map);
	
	/**
	 * 保存用户手机型号 app版本号
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public void up_appNV(Map map);
}

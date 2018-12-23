package com.dd.supermarket.dao.app.user;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月25日 下午11:30:50 <br/>
* 类说明：
*/
@Repository("appLoginDao")
public class AppLoginDao {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	public void save_userinfo(Map map)throws Exception{
		baseDao.save("appLogin.save_userinfo", map);
	}
	
	public void update_userinfoPassword_ByPhone(Map map)throws Exception{
		baseDao.save("appLogin.update_userinfoPassword_ByPhone", map);
	}
	
	//用户注册手机号比对名单
	public int find_namelist(String phone){
		return (int) baseDao.findOne("appLogin.find_name_list",phone );
	}
	
	//修改名单状态
	public void update_namelist(String user_id)throws Exception{
		baseDao.update("appLogin.update_namelist", user_id);
	}
	
}

package com.dd.supermarket.dao.app.shell;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;

@Repository("shellCommodityDao")
public class ShellCommodityDao {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//我要出
	public void save_commodity(Map map){
		baseDao.save("shellCommodity.save_commodity", map);
	}
	
	//用户id查询手机号
	public Object find_phone(String user_id){
		return baseDao.findOne("shellCommodity.find_phone", user_id);
	}
	
	//类型查询
	public List<Object> find_com(String ty_id){
		return baseDao.findForList("shellCommodity.find_com", ty_id);
	}
	
	//查询首页轮播图片地址
	public  List<Map<String,Object>> find_banner(){
		return baseDao.findForList("shellCommodity.find_banner", null);
	}
	
	public void update(String com_id){
		baseDao.update("shellCommodity.update_comstate", com_id);
	}

}

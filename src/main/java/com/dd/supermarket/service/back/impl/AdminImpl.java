package com.dd.supermarket.service.back.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.dao.back.AdminDao;
import com.dd.supermarket.service.back.IAdmin;
import com.dd.supermarket.utils.UuidUtil;

@Transactional
@Service("backAdminImpl")
public class AdminImpl implements IAdmin{
	@Resource(name="backAdminDao")
	private AdminDao admindao;
	//查询全部角色
	public List<Object> find_role() {
		// TODO Auto-generated method stub
		return admindao.find_role();
	}
	//添加管理员
	public void save_admin(Map map) {
		// TODO Auto-generated method stub
		map.put("sys_sa_id", UuidUtil.get32UUID());
		admindao.save_admin(map);
	}
	//查询管理员
	public List<Object> find_admin(String sys_sa_name) {
		// TODO Auto-generated method stub
		return admindao.find_admin(sys_sa_name);
	}
	//删除管理员
	public void del_admin(String sys_sa_id) {
		// TODO Auto-generated method stub
		admindao.del_admin(sys_sa_id);
	}
	@Override
	public void update_admin(Map map) {
		// TODO Auto-generated method stub
		admindao.update_admin(map);
	}
	

}

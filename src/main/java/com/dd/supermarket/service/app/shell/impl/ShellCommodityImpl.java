package com.dd.supermarket.service.app.shell.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.controller.app.utils.PathFactory;
import com.dd.supermarket.dao.app.shell.ShellCommodityDao;
import com.dd.supermarket.service.app.shell.IShellCommodity;
import com.dd.supermarket.utils.UuidUtil;
import com.dd.supermarket.utils.http.GetServer;

@Transactional
@Service("shellCommodityImpl")
public class ShellCommodityImpl implements IShellCommodity{
	
	@Resource(name="shellCommodityDao")
	private ShellCommodityDao shellCommodityDao;
	
	//我要出
	public void save_commodity(Map comMap){
		comMap.put("state",0);
		comMap.put("lease_number",0);
		comMap.put("release_time","5分钟内发布");
		shellCommodityDao.save_commodity(comMap);
	}
	
	//根据用户id查询手机号
	public Object find_phone(String user_id){
		return shellCommodityDao.find_phone(user_id);
	}
	
	
	//类型查询
	public List<Object> find_com(String ty_id){
		return shellCommodityDao.find_com(ty_id);
	}
	
	
	/**
	 * 获取APP首页banner图
	 */
	public List<Map<String, Object>> find_banner(HttpServletRequest request) {
		List<Map<String, Object>> findResutl = null;
		try {
			findResutl = shellCommodityDao.find_banner();
		} catch (Exception e) {
			System.out.println("=========== APP查询首页banner图时出错 ===========");
			e.printStackTrace();
		}
		if(null == findResutl)return new ArrayList<Map<String, Object>>();
		
		String serverUrl = new GetServer().getServerUrl(request);
		PathFactory pf = new PathFactory();
		for (Map<String, Object> ban : findResutl) {
			ban = pf.shellBannerPicture(serverUrl, ban);
		}
		
		return findResutl;
	}
	
	public void update(String com_id){
		shellCommodityDao.update(com_id);
	}

}

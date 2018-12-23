package com.dd.supermarket.service.h5.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.controller.app.utils.PathFactory;
import com.dd.supermarket.dao.h5.H5HomeDao;
import com.dd.supermarket.service.h5.IH5Home;
import com.dd.supermarket.utils.UuidUtil;
import com.dd.supermarket.utils.http.GetServer;
@Transactional
@Service("h5homeImpl")
public class H5HomeImpl implements IH5Home{
	
	@Resource(name="h5homeDao")
	private H5HomeDao h5homeDao;
	
	public List<Map<String, Object>> getHomeDataById(HttpServletRequest request,String lable_dic_id){
		List<Map<String, Object>> findResutl =  h5homeDao.getHomeDataById(lable_dic_id);
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		String serverUrl = new GetServer().getServerUrl(request);//获取服务器地址
		PathFactory pf = new PathFactory();
		for (Map<String, Object> fr : findResutl) {
			fr = pf.commodityFactory(serverUrl, fr);
			map.add(fr);
		}
		return map;
	}
	
	//添加申请人信息
	public void save_h5apply(Map map){
		map.put("apply_id", UuidUtil.get32UUID());
		h5homeDao.save_h5apply(map);
	}
}

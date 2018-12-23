package com.dd.supermarket.service.back.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.dd.supermarket.dao.back.LogDao;
import com.dd.supermarket.service.back.ILog;
import com.dd.supermarket.utils.PageData;
import com.dd.supermarket.utils.UuidUtil;

@Transactional
@Service("backLogImpl")
public class LogImpl implements ILog{
	
	@Resource(name="backLogDao")
	private LogDao logdao;
	
	//分页查询日志
	public List<PageData> find_log(PageData pd){
		return logdao.find_log(pd);
	}
	
	//添加日志
	public void save_log(HttpSession session,String sys_log_content,int sign){
		Map<String, Object> map = new HashMap<String,Object>();
		
		Map<String, Object> usermap = (Map<String, Object>) session.getAttribute("user");
		String sys_sa_id="";
		if(usermap!=null){
			sys_sa_id = (String) usermap.get("sys_sa_id");
		}
		
		map.put("sys_log_content", sys_log_content);
		map.put("sign", sign);
		map.put("sys_sa_id", sys_sa_id);
		map.put("sys_sl_id",UuidUtil.get32UUID());
		logdao.save_log(map);
	}

	
}

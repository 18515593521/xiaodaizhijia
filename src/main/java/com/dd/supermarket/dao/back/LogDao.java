package com.dd.supermarket.dao.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;
import com.dd.supermarket.utils.PageData;
@Repository("backLogDao")
public class LogDao {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//分页查询日志
	public List<PageData> find_log(PageData pd){
		return (List<PageData>) baseDao.findForList("backLog.find_log", pd);		
	}
	
	//添加日志
	public void save_log(Map map){
		baseDao.save("backLog.save_log", map);
	}
}

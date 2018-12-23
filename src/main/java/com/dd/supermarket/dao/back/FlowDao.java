package com.dd.supermarket.dao.back;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;
import com.dd.supermarket.utils.PageData;

@Repository("bacKFlowDao")
public class FlowDao {
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//分页查询
	public List<PageData> find_flow(PageData pd){
		return (List<PageData>) baseDao.findForList("backFlow.find_flow", pd);		
	}
}

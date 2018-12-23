package com.dd.supermarket.service.back.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.dao.back.FlowDao;
import com.dd.supermarket.service.back.IFlow;
import com.dd.supermarket.utils.PageData;

@Transactional
@Service("backFlowImpl")
public class FlowImpl implements IFlow{
	
	@Resource(name="bacKFlowDao")
	private FlowDao flowdao;

	public List<PageData> find_flow(PageData pd) {
		// TODO Auto-generated method stub
		return flowdao.find_flow(pd);
	}
	
}

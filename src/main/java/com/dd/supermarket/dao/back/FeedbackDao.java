package com.dd.supermarket.dao.back;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;
import com.dd.supermarket.utils.PageData;

@Repository("bacKFeedbackDao")
public class FeedbackDao {
	@Resource(name="baseDao")
	private BaseDao baseDao;
	//分页查询日志
	public List<PageData> find_feedback(PageData pd){
		return (List<PageData>) baseDao.findForList("backfeedback.find_feedback", pd);		
	}
}

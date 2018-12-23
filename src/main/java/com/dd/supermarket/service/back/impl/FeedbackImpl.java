package com.dd.supermarket.service.back.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.dao.back.FeedbackDao;
import com.dd.supermarket.dao.back.LogDao;
import com.dd.supermarket.service.back.IFeedback;
import com.dd.supermarket.utils.PageData;
@Transactional
@Service("backFeedbackImpl")
public class FeedbackImpl implements IFeedback{
	
	@Resource(name="bacKFeedbackDao")
	private FeedbackDao feedbackDao;

	//分页查询日志
	public List<PageData> find_feedback(PageData pd){
		return feedbackDao.find_feedback(pd);
	}
}

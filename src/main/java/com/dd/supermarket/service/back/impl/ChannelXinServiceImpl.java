package com.dd.supermarket.service.back.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.dao.back.ChannelXinDao;
import com.dd.supermarket.service.back.IChannelXinService;
import com.dd.supermarket.utils.PageData;

@Transactional
@Service("channelXinServiceImpl")
public class ChannelXinServiceImpl implements IChannelXinService{
	
	@Resource(name="channelXinDao")
	private ChannelXinDao channelXinDao;
	
	public List<PageData> findChannelList(PageData pd) {
		return channelXinDao.findChannelList(pd);
	}
	
	
	public void addChannel(PageData pd) {
		channelXinDao.addChannel(pd);
	}
	
	public PageData singleChannel(PageData pd) {
		return (PageData) channelXinDao.singleChannel(pd);
	}
	
	public void update(PageData pd) {
		channelXinDao.update(pd);
	}
	
	
	public void delChannelPowerByCnid(PageData pd) {
		channelXinDao.delChannelPowerByCnid(pd);
	}

	public void updateChannel(PageData pd) {
		channelXinDao.updateChannel(pd);
	}
	
	public List<PageData> getChannel(PageData pd) {
		return channelXinDao.getChannel(pd);
	}
	
	public PageData find_naturalnum(PageData pd) {
		return channelXinDao.find_naturalnum(pd);
	}
	
	public List<PageData> channelByStateAndIsShow(PageData pd){
		return channelXinDao.channelByStateAndIsShow(pd);
	}
	
	//外部渠道列表数据统计
	public List<PageData> find_channelout(PageData pd) {
		return channelXinDao.find_channelout(pd);
	}
	
	//查询管理员渠道权限 
	public Object find_channelById(String adm_id){
		return channelXinDao.find_channelById(adm_id);
	}
	
	//查询管理员渠道权限 
	public List<PageData> find_userByChannel(PageData pd){
		return channelXinDao.find_userByChannel(pd);
	}
	
	
	public List<PageData> find_channelAll(){
		return channelXinDao.find_channelAll();
	}
	
	public PageData todayChannelAll(PageData pd){
		return channelXinDao.todayChannelAll(pd);
	}
	
	public PageData todayRegisterNum(PageData pd){
		return channelXinDao.todayRegisterNum(pd);
	}
	
	public PageData settlementNum(PageData pd){
		return channelXinDao.settlementNum(pd);
	}
	
	public PageData registerNum(PageData pd){
		return channelXinDao.registerNum(pd);
	}
}

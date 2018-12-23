package com.dd.supermarket.service.back;

import java.util.List;

import com.dd.supermarket.utils.PageData;


public interface IChannelXinService {
	public List<PageData> findChannelList(PageData pd);
	
	public void addChannel(PageData pd);

	public PageData singleChannel(PageData pd);

	public void update(PageData pd);

	public void delChannelPowerByCnid(PageData pd);
	public void updateChannel(PageData pd);
	
	public List<PageData> getChannel(PageData pd);
	
	public PageData find_naturalnum(PageData pd);
	
	public List<PageData> channelByStateAndIsShow(PageData pd);
	
	//外部渠道统计
	public List<PageData> find_channelout(PageData pd);
	
	//查询管理员渠道权限 
	public Object find_channelById(String adm_id);
	
	//查询管理员渠道权限 
	public List<PageData> find_userByChannel(PageData pd);
	
	
	public List<PageData> find_channelAll();
	
	public PageData todayChannelAll(PageData pd);
	
	public PageData todayRegisterNum(PageData pd);
	
	public PageData settlementNum(PageData pd);
	
	public PageData registerNum(PageData pd);
}

package com.dd.supermarket.service.back.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.dao.back.ChannelDao;
import com.dd.supermarket.service.back.ICollectionService;
import com.dd.supermarket.utils.PageData;


@Transactional
@Service("channelServiceImpl")
public class ChannelServiceImpl implements ICollectionService{
	
	@Resource(name="channelDao")
	private ChannelDao channelDao;
	
	public List<PageData> channelPowerList(PageData pd) {
		return channelDao.channelPowerList(pd);
	}
	
	public List<PageData> channelPowerBuId(PageData adm) {
		return channelDao.channelPowerBuId(adm);
	}
	
	
	public List<PageData> channelPowerAll() {
		return channelDao.channelPowerAll();
	}
	
	public List<PageData> channelPowerById(PageData pd) {
		return channelDao.channelPowerById(pd);
	}
	
	public void addChannelPower(PageData pd) {
		channelDao.addChannelPower(pd);
	}
	
	public void delChannelPower(PageData pd) {
		channelDao.delChannelPower(pd);
	}
	
	public List<Object> findCnName(){
		return channelDao.findCnName();
	}

}

package com.dd.supermarket.dao.back;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;
import com.dd.supermarket.utils.PageData;

@Repository("channelDao")
public class ChannelDao {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	
	public List<PageData> channelPowerList(PageData pd) {
		return baseDao.findForList("Channel.channelPowerList", pd);
	}
	
	public List<PageData> channelPowerBuId(PageData adm) {
		return baseDao.findForList("Channel.channelPowerBuId", adm);
	}
	
	
	
	public List<PageData> channelPowerAll() {
		return baseDao.findForList("Channel.channelPowerAll", null);
	}
	
	public List<PageData> channelPowerById(PageData pd) {
		return baseDao.findForList("Channel.channelPowerById", pd);
	}
	
	public void addChannelPower(PageData pd) {
		baseDao.update("Channel.addChannelPower", pd);
	}
	
	public void delChannelPower(PageData pd) {
		baseDao.delete("Channel.delChannelPower", pd);
	}
	
	public List<Object> findCnName() {
		return baseDao.findForList("Channel.findcn_name", null);
	}


}

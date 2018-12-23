package com.dd.supermarket.dao.back;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;
import com.dd.supermarket.utils.PageData;

@Repository("channelXinDao")
public class ChannelXinDao {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	
	public List<PageData> findChannelList(PageData pd){
		return baseDao.findForList("ChannelXin.findChannelList",pd);		
	};
	
	
	public void addChannel(PageData pd) {
		baseDao.save("ChannelXin.addChannel", pd);
	}
	
	public PageData singleChannel(PageData pd) {
		return (PageData) baseDao.findOne("ChannelXin.singleChannel", pd);
	}
	
	public void update(PageData pd) {
		baseDao.update("ChannelXin.update", pd);
	}
	

	public void delChannelPowerByCnid(PageData pd) {
		baseDao.delete("ChannelXin.delChannelPowerByCnid", pd);
	}
	
	public void updateChannel(PageData pd) {
		baseDao.update("ChannelXin.updateChannel", pd);
	}
	
	public List<PageData> getChannel(PageData pd) {
		return baseDao.findForList("ChannelXin.getChannel", pd);
	}
	
	public PageData find_naturalnum(PageData pd) {
		return (PageData) baseDao.findOne("ChannelXin.find_naturalnum", pd);
	}
	
	public List<PageData> channelByStateAndIsShow(PageData pd) {
		return baseDao.findForList("ChannelXin.channelByStateAndIsShow", pd);
	}
	
	
	//外部渠道统计
	public List<PageData> find_channelout(PageData pd) {
		return baseDao.findForList("ChannelXin.find_channelout", pd);
	}
	
	//查询管理员渠道权限 
	public Object find_channelById(String adm_id) {
		return baseDao.findOne("ChannelXin.find_channelById", adm_id);
	}
	
	//查询管理员渠道权限 
	public List<PageData> find_userByChannel(PageData pd) {
		return baseDao.findForList("ChannelXin.find_userByChannel", pd);
	}
	

	public List<PageData> find_channelAll() {
		return baseDao.findForList("ChannelXin.find_channelAll", null);
	}
	

	public PageData todayChannelAll(PageData pd) {
		return (PageData) baseDao.findOne("ChannelXin.todayChannelAll", pd);
	}
	
	public PageData todayRegisterNum(PageData pd) {
		return (PageData) baseDao.findOne("ChannelXin.todayRegisterNum", pd);
	}
	
	public PageData settlementNum(PageData pd) {
		return (PageData) baseDao.findOne("ChannelXin.settlementNum", pd);
	}
	
	public PageData registerNum(PageData pd) {
		return (PageData) baseDao.findOne("ChannelXin.registerNum", pd);
	}
	
	
}

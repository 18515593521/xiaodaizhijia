package com.dd.supermarket.service.back;

import java.util.List;

import com.dd.supermarket.utils.PageData;

public interface ICollectionService {
	
	public List<PageData> channelPowerList(PageData pd);
	
	public List<PageData> channelPowerBuId(PageData adm);
	
	public List<PageData> channelPowerAll();
	
	
	public List<PageData> channelPowerById(PageData pd);
		
	public void addChannelPower(PageData pd);
		
	
	public void delChannelPower(PageData pd);
	
	public List<Object> findCnName();

}

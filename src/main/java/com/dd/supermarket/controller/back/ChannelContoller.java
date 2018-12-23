package com.dd.supermarket.controller.back;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.BaseController;
import com.dd.supermarket.service.back.ICollectionService;
import com.dd.supermarket.utils.PageData;
import com.github.pagehelper.PageInfo;


@Controller
@RequestMapping("back/channel")
public class ChannelContoller extends BaseController{
	
	
	@Resource(name="channelServiceImpl")
	private ICollectionService collectionService;
	
	/**
	 * 查询用户渠道权限列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("channelPowerList")
	public JSONObject channelPowerList() {
		PageData pd = this.getPageData();
		this.getPager();
		List<PageData> oldList = collectionService.channelPowerList(pd);// 查询管理员
		PageInfo pageInfo = this.getPageInfo(oldList);
		List<PageData> newList = new ArrayList<PageData>();
		for (PageData adm : oldList) {
			List<PageData> channelPowers = collectionService.channelPowerBuId(adm);// 查询单个管理员的渠道权限
			adm.put("channels", this.listToStr(channelPowers));
			newList.add(adm);
		}
		JSONObject resultJson = this.getResultJson(newList, pageInfo);
		return resultJson;
	}
	
	/**
	 * 查询所有渠道权限
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("channelPowerAll")
	public JSONObject channelPowerAll() {
		PageData pd = this.getPageData();
		JSONObject json = new JSONObject();
		int code = 0;
		try {
			List<PageData> list = collectionService.channelPowerAll();// 查询管理员
			code = 1;
			json.put("result", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		json.put("code", code);
		return json;
	}
	
	
	/**
	 * 查询所有渠道权限
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("channelPowerById")
	public JSONObject channelPowerById() {
		PageData pd = this.getPageData();
		JSONObject json = new JSONObject();
		int code = 0;
		try {
			List<PageData> list = collectionService.channelPowerById(pd);// 查询管理员
			code = 1;
			json.put("result", this.listToStr1(list));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		json.put("code", code);
		return json;
	}
	
	
	/**
	 * 修改渠道权限
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updChannelPower")
	public JSONObject updChannelPower() {
		PageData pd = this.getPageData();
		JSONObject json = new JSONObject();
		int code = 0;
		String cn_ids = pd.getString("cn_ids");
	
		if (cn_ids != null || !"".equals(cn_ids.trim())){			
			collectionService.delChannelPower(pd);// 删除该管理员渠道权限
			String[] split = cn_ids.split(",");
			for (int i = 0; i < split.length; i++) {
				pd.put("cn_id", split[i]);
				try {
					collectionService.addChannelPower(pd);// 新增管理员渠道权限
					code = 1;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		json.put("code", code);
		return json;
	}
	
	
	/**
	 * 权限转为一个字符串
	 * 
	 * @return
	 */
	public String listToStr(List<PageData> list) {
		String str = "";
		for (int i = 0; i < list.size(); i++) {
			if ((list.size() - 1) == i) {
				str += list.get(i).getString("cn_name");
			} else {
				str += list.get(i).getString("cn_name") + ",";
			}
		}
		return str;
	}
	
	
	/**
	 * 权限转为一个字符串
	 * 
	 * @return
	 */
	public String listToStr1(List<PageData> list) {
		String str = "";
		for (int i = 0; i < list.size(); i++) {
			if ((list.size() - 1) == i) {
				str += list.get(i).getString("cn_id");
			} else {
				str += list.get(i).getString("cn_id") + ",";
			}
		}
		return str;
	}

	/**
	 * 权限转为一个字符串
	 * 
	 * @return
	 */
	public String listToStr2(List<PageData> list) {
		String str = "";
		for (int i = 0; i < list.size(); i++) {
			if ((list.size() - 1) == i) {
				str += "'" + list.get(i).getString("cn_name") + "'";
			} else {
				str += "'" + list.get(i).getString("cn_name") + "',";
			}
		}
		return str;
	}

}

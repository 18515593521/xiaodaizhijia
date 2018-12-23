package com.dd.supermarket.controller.back;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.BaseController;
import com.dd.supermarket.service.back.IChannelXinService;
import com.dd.supermarket.utils.PageData;
import com.github.pagehelper.PageInfo;



@Controller
@RequestMapping("back/channelxin")
public class ChannelXinContoller extends BaseController{
	
	@Resource(name="channelXinServiceImpl")
	private IChannelXinService ichannel;
	/**
	 * 展示渠道信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("list")
	public JSONObject list() {
		PageData pd = this.getPageData();
		this.getPager();
		List<PageData> list = ichannel.findChannelList(pd);
		PageInfo pageInfo = this.getPageInfo(list);
		JSONObject resultJson = this.getResultJson(list, pageInfo);
		return resultJson;
	}
	
	
	/**
	 * 新增渠道
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addChannel")
	public JSONObject addChannel(HttpSession session) {
		PageData pd = this.getPageData();
		JSONObject json = new JSONObject();
		int code = 0;
		try {
			Map<String, Object> map = (Map<String, Object>) session.getAttribute("user");
			if(map==null){
				json.put("code", 5);
				return json;
			}
			String sys_sa_name = (String) map.get("sys_sa_name");
			pd.put("create_by", sys_sa_name);
			ichannel.addChannel(pd);
			code = 1;
		} catch (Exception e) {
			e.printStackTrace();
			code = 0;
		}
		json.put("code", code);
		return json;
	}
	
	/**
	 * 查询单个渠道
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("single")
	public JSONObject single() {
		PageData pd = this.getPageData();
		JSONObject json = new JSONObject();
		int code = 0;
		try {
			pd = ichannel.singleChannel(pd);
			code = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		json.put("code", code);
		json.put("channel", pd);
		return json;
	}

	/**
	 * 修改渠道
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("update")
	public JSONObject update() {
		PageData pd = this.getPageData();
		JSONObject json = new JSONObject();
		int code = 0;
		try {
			ichannel.update(pd);
			code = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		json.put("code", code);
		return json;
	}

	/**
	 * 删除渠道
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delChannel")
	public JSONObject delChannel() {
		PageData pd = this.getPageData();
		JSONObject json = new JSONObject();
		int code = 0;
		try {
			ichannel.delChannelPowerByCnid(pd);
			ichannel.updateChannel(pd);
			code = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		json.put("code", code);
		return json;
	}
	
	/**
	 * 渠道统计/内部
	 * @return
	 */
	@RequestMapping("channelStatisticsXin")
	@ResponseBody
	public JSONObject channelStatisticsXin(){
		PageData pd = this.getPageData();
		String st = (String) pd.get("selectDate");
		String cn_title = (String) pd.get("cn_title");
		if (null != st && !"".equals(st)) {
			String[] str =st.split(" - ");
			for (int i = 0; i < str.length; i++) {
				if (i==0) {
					pd.put("startTime", str[i]+" 00:00:00");
				}else{
					pd.put("endtTime", str[i]+" 23:59:59");
				}
			}
		}
		pd.put("cn_name", cn_title);
		this.getPager();
		PageData channel =null;
		List<PageData> getChannelList= ichannel.getChannel(pd);
		PageData getChannelQt= ichannel.find_naturalnum(pd);
		getChannelQt.put("cn_title", "自然流量");
		getChannelQt.put("cn_name", "qt");
		getChannelList.add(0, getChannelQt);//放在第一个
		
		
		PageInfo pageInfo = this.getPageInfo(getChannelList);
		JSONObject resultJson = this.getResultJson(getChannelList, pageInfo);
		return resultJson;
	}
	
	
	
	/**
	 * 渠道统计/外部
	 * @return
	 */
	@RequestMapping("find_channelout")
	@ResponseBody
	public JSONObject find_channelout(){
		PageData pd = this.getPageData();
		String st = (String) pd.get("selectDate");
		String cn_title = (String) pd.get("cn_title");
		if (null != st && !"".equals(st)) {
			String[] str =st.split(" - ");
			for (int i = 0; i < str.length; i++) {
				if (i==0) {
					pd.put("startTime", str[i]+" 00:00:00");
				}else{
					pd.put("endtTime", str[i]+" 23:59:59");
				}
			}
		}
		pd.put("cn_name", cn_title);
		this.getPager();
		List<PageData> getChannelList= ichannel.find_channelout(pd);
		PageInfo pageInfo = this.getPageInfo(getChannelList);
		JSONObject resultJson = this.getResultJson(getChannelList, pageInfo);
		return resultJson;
	}
	
	/**
	 * 获取渠道列表
	 */
	@RequestMapping("channelListIn")
	@ResponseBody
	public PageData channelListIn() {
		PageData pd = this.getPageData();
		pd.put("is_show", 1);
		PageData json = new PageData();
		List<PageData> channelLists = ichannel.channelByStateAndIsShow(pd);
		json.put("channelList", channelLists);
		return json;
	}
	
	/**
	 * 获取渠道列表
	 */
	@RequestMapping("channelListOut")
	@ResponseBody
	public PageData channelListOut() {
		PageData pd = this.getPageData();
		pd.put("is_show", 0);
		pd.put("state", 0);
		PageData json = new PageData();
		List<PageData> channelLists = ichannel.channelByStateAndIsShow(pd);
		json.put("channelList", channelLists);
		return json;
	}
	
	
	/**
	 * 管理员渠道权限查看用户信息
	 */
	@ResponseBody
	@RequestMapping("find_channelById")
	public JSONObject find_channelById(HttpSession session){
		PageData pd = new PageData();
		pd = this.getPageData();
		String create_time = (String) pd.get("create_time");
		String[] time = create_time.split(" - ");
		if(time.length > 0 && !time[0].trim().equals("")){
			String dt1=time[0];
			String dt2=time[1];
			pd.put("dt1", dt1);
			pd.put("dt2", dt2);
		}
		
		Map usermap =  (Map) session.getAttribute("user");
		String adm_id=(String) usermap.get("sys_sa_id");
		//查询渠道权限
		String channel = ("'"+((String) ichannel.find_channelById(adm_id))+"'");
		channel = channel.replace(",", "','");
		pd.put("channel", channel);
		this.getPager();
		List<PageData> list = ichannel.find_userByChannel(pd);
		PageInfo pageInfo = this.getPageInfo(list);
		JSONObject resultJson = this.getResultJson(list, pageInfo);
		return resultJson;
	}
	
	
	/**
	 * 外部渠道 
	 * 今日注册量，今日结算量，今日结算总金额
	 */
	@RequestMapping("todayChannel")
	@ResponseBody
	public JSONObject todayChannel(){
		DecimalFormat df = new DecimalFormat("#.00");
		JSONObject json = new JSONObject();
		PageData pd = new PageData();
		pd = this.getPageData();
	
		List<PageData> channelAll = ichannel.find_channelAll();		
		//结算量 结算金额
		Long jsl = 0L;
		Long zje = 0L;
		for (int i = 0; i < channelAll.size(); i++) {
			pd.put("name", channelAll.get(i).get("cn_name"));
			PageData countJsl = ichannel.todayChannelAll(pd);
			jsl += (Long) countJsl.get("today_num");
			//今日结算总金额
			zje += ((Long) countJsl.get("today_num")*(int)channelAll.get(i).get("price"));
		}
		//注册量
		PageData channelZcl = ichannel.todayRegisterNum(pd);
		Long zcl = 0L;
		String jszje = "0";
		if((Long) channelZcl.get("zcl") != 0) {
			zcl = (Long) channelZcl.get("zcl");
			jszje = df.format(zje/100.0);
		}
				
		json.put("zcl", zcl);
		json.put("jsl", jsl);
		json.put("jszje", jszje);
		return json;
		
	}
	
	
	/**
	 * 外部渠道 
	 * 列表渠道注册总量，列表渠道总结算量，列表渠道结算总金额
	 */
	@RequestMapping("SettlementChannel")
	@ResponseBody
	public JSONObject SettlementChannel(String selectDate){
		DecimalFormat df = new DecimalFormat("#.00");
		JSONObject json = new JSONObject();
		PageData pd = new PageData();
		pd = this.getPageData();
		/*String create_time = (String) pd.get("selectDate");*/
		String[] time = selectDate.split(" - ");
		if(time.length > 0 && !time[0].trim().equals("")){
			String dt1=time[0];
			String dt2=time[1];
			pd.put("dt1", dt1);
			pd.put("dt2", dt2);
		}				
		List<PageData> channelAll = ichannel.find_channelAll();		
		//结算量 结算金额
		Long jsl = 0L;
		Long zje = 0L;
		for (int i = 0; i < channelAll.size(); i++) {
			pd.put("name", channelAll.get(i).get("cn_name"));
			PageData countJsl = ichannel.settlementNum(pd);
			jsl += (Long) countJsl.get("Settlement_num");
			//今日结算总金额
			zje += ((Long) countJsl.get("Settlement_num")*(int)channelAll.get(i).get("price"));
		}
		//注册量
		PageData channelZcl = ichannel.registerNum(pd);
		Long zcl = 0L;
		String jszje = "0";
		if((Long) channelZcl.get("register_name") != 0) {
			zcl = (Long) channelZcl.get("register_name");
			jszje = df.format(zje/100.0);
		}
				
		json.put("zcl", zcl);
		json.put("jsl", jsl);
		json.put("jszje", jszje);
		return json;
		
	}
	
}

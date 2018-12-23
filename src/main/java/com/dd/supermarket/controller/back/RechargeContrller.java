package com.dd.supermarket.controller.back;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.BaseController;
import com.dd.supermarket.controller.app.utils.PathFactory;
import com.dd.supermarket.fixed.PathConst;
import com.dd.supermarket.service.back.ICommodity;
import com.dd.supermarket.service.back.ILog;
import com.dd.supermarket.service.back.IRecharge;
import com.dd.supermarket.service.back.ISettlement;
import com.dd.supermarket.utils.PageData;
import com.dd.supermarket.utils.UuidUtil;
import com.dd.supermarket.utils.WhiteList;
import com.dd.supermarket.utils.file.FileUploader;
import com.dd.supermarket.utils.http.GetServer;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("back/pecharge")
public class RechargeContrller extends BaseController{
	@Resource(name="backRechargeImpl")
	private IRecharge irecharge;
	
	@Resource(name="backSettlementImpl")
	private ISettlement isettlement;
	
	@Resource(name="backLogImpl")
	private ILog ilog;
	
	@Resource(name="backCommodityImpl")
	private ICommodity icommodity;
	
	@RequestMapping(value="save_recharge",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject save_recharge(HttpSession session,HttpServletRequest request, MultipartFile file,@RequestParam String cr_money,@RequestParam String recharge_time,@RequestParam String com_id) {
		JSONObject json = new JSONObject();
		Map<String,Object> rechargeMap = new HashMap<String,Object>();
		rechargeMap.put("recharge_time", recharge_time);
		rechargeMap.put("cr_money", cr_money);
		rechargeMap.put("com_id", com_id);
		
		
		//获取操作人id
		Map map =  (Map) session.getAttribute("user");
		if(map==null){
			json.put("result", 5);
			return json;
		}
		String sys_sa_id=(String) map.get("sys_sa_id");
		
		rechargeMap.put("sys_sa_id", sys_sa_id);
		
		//保存充值截图并获取图片名称
		String uf_picture = "";
		FileUploader fu = new FileUploader();
		
		if(null!=file){
			String servicePath = fu.getservicePath(request);
			try {
				uf_picture = fu.uploade(file, servicePath+PathConst.COMMODITY_RECHARGE_PIC_URL);
				rechargeMap.put("cr_picture", uf_picture);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			rechargeMap.put("cr_picture", "");
		}
		
		
		irecharge.save_recharge(rechargeMap);
		//记录日志
		ilog.save_log(session, "新增充值记录",1);
		json.put("result", 1);
		return json;
	}
	
	//查询
	@RequestMapping("find_list")
	public String find_recharge(HttpSession session,Model model,String com_id,int settlement){
		String com_name = (String) session.getAttribute("com_name");
		String ci_name = (String) session.getAttribute("ci_name");
		Map<String, Object> mapcomid = new HashMap<String,Object>();
		mapcomid.put("com_name", com_name);
		mapcomid.put("ci_name", ci_name);
		List<Map> com_ids = icommodity.find_comId(mapcomid);
		Map<String, Object> mapid = WhiteList.pre_next_comDetail(com_id, com_ids);
		model.addAttribute("mapid", mapid);
		if(settlement==0){
			//查询充值记录
			List<Object> list = irecharge.find_recharge(com_id);		
			List<Object> rechargelist = new ArrayList<Object>();
			for (Object li : list) {
				Map<String, Object> map = (Map<String, Object>) li;
				double money = (Integer)(map.get("cr_money"))/100.0;
				map.put("cr_money", money);
				rechargelist.add(map);
			}
			model.addAttribute("com_id",com_id);
			model.addAttribute("rechargelist", rechargelist);
			
			//查询结算明细
			List<Object> settlist = isettlement.find_settlement(com_id);
			List<Object> settlementlist = new ArrayList<Object>();
			for (Object li : settlist) {
				Map<String, Object> map = (Map<String, Object>) li;
				double settlement_price = (Integer)(map.get("settlement_price"))/100.0;
				map.put("settlement_price", settlement_price);
				settlementlist.add(map);
			}
			PathFactory pf = new PathFactory();
			
			HttpServletRequest request = super.getRequest();
			String serverUrl = new GetServer().getServerUrl(request);
			
			for (int i = 0; i < settlementlist.size(); i++) {
			Map<String, Object> map	=(Map<String, Object>) settlementlist.get(i);
				settlementlist.set(i, pf.settlementFactory(serverUrl, map));
			}
			model.addAttribute("settlementlist", settlementlist);
			
			//根据id查询商品信息
			List<Object> comlist = irecharge.find_com(com_id);
			Map<String, Object> commap = new HashMap<String,Object>();
			for (Object li : comlist) {
				commap = (Map<String, Object>) li;
				double com_price = (Integer)(commap.get("com_price"))/100.0;
				double num = Integer.parseInt((commap.get("amount")).toString());
				double amount = (double)num/100;
				commap.put("com_price", com_price);
				commap.put("amount", amount);
			}
			model.addAttribute("commap", commap);
			
			return "back/html/products-details";
		}else{
				
			model.addAttribute("com_id",com_id);
			//查询结算明细
			List<Object> settlist = isettlement.find_settlement(com_id);
			List<Object> settlementlist = new ArrayList<Object>();
			for (Object li : settlist) {
				Map<String, Object> map = (Map<String, Object>) li;
				double settlement_price = (Integer)(map.get("settlement_price"))/100.0;
				map.put("settlement_price", settlement_price);
				settlementlist.add(map);
			}
			PathFactory pf = new PathFactory();
			
			HttpServletRequest request = super.getRequest();
			String serverUrl = new GetServer().getServerUrl(request);
			
			for (int i = 0; i < settlementlist.size(); i++) {
			Map<String, Object> map	=(Map<String, Object>) settlementlist.get(i);
				settlementlist.set(i, pf.settlementFactory(serverUrl, map));
			}
			model.addAttribute("settlementlist", settlementlist);
			
			//根据id查询商品信息
			List<Object> comlist = irecharge.find_com(com_id);
			Map<String, Object> commap = new HashMap<String,Object>();
			for (Object li : comlist) {
				commap = (Map<String, Object>) li;
				double com_price = (Integer)(commap.get("com_price"))/100.0;
				commap.put("com_price", com_price);
			}
			model.addAttribute("commap", commap);
			
			return "back/html/products_everydaydetails";
		}
		
	}
	
}

package com.dd.supermarket.controller.back;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.BaseController;
import com.dd.supermarket.fixed.PathConst;
import com.dd.supermarket.service.back.ILog;
import com.dd.supermarket.service.back.ISettlement;
import com.dd.supermarket.utils.file.FileUploader;
@Controller
@RequestMapping("back/settlement")
public class SettlementContrller extends BaseController{

	@Resource(name="backSettlementImpl")
	private ISettlement isettlement;
	
	@Resource(name="backLogImpl")
	private ILog ilog;
	
	//添加结算明细
	@RequestMapping(value="save_settlement",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject save_settlement(HttpSession session,HttpServletRequest request, MultipartFile file,
			@RequestParam int apply_number,@RequestParam String settlement_time,@RequestParam String com_id,@RequestParam(value="remarks",required=false) String remarks){
		JSONObject json = new JSONObject();
		
		//获取点击量 商品单价
		Map<String,Object> mapclick = new HashMap<String,Object>();
		Map<String,Object> mapnum = new HashMap<String,Object>();
		mapclick.put("com_id", com_id);
		mapclick.put("create_time", settlement_time);
		List<Object> list = isettlement.find_clicknumber(mapclick);
		for (Object li : list) {
			mapnum = (Map<String, Object>) li;
		}
		double click_number = (Long) mapnum.get("click_number");		
		double com_price = (Integer) mapnum.get("com_price");
		//结算金额
		double settlement_price = com_price*apply_number;
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("settlement_time", settlement_time);
	    map.put("apply_number", apply_number);
		map.put("com_id", com_id);
		
		map.put("settlement_price", settlement_price);
		map.put("click_number",click_number);
		if(remarks==null){
			map.put("remarks", "");
		}else{
			map.put("remarks", remarks);
		}
				
		//获取操作人id
		Map usermap =  (Map) session.getAttribute("user");
		if(usermap==null){
			json.put("result", 0);
			return json;
		}else{
			String sys_sa_id=(String) usermap.get("sys_sa_id");
			map.put("sys_sa_id", sys_sa_id);
		}
		
		
		//保存充值截图并获取图片名称
		String uf_picture = "";
		FileUploader fu = new FileUploader();
		
		if(null!=file){
			String servicePath = fu.getservicePath(request);
			try {
				uf_picture = fu.uploade(file, servicePath+PathConst.COMMODITY_SETTLEMENT_PIC_URL);
				map.put("cs_picture", uf_picture);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			map.put("cs_picture", "");
		}
		
		isettlement.save_settlement(map);
		//记录日志
		ilog.save_log(session, "新增结算明细",2);
		json.put("result", 1);
		return json;
	}
	
	//修改结算状态
	@RequestMapping("update_state")
	@ResponseBody
	public JSONObject update_state(String cs_id){
		JSONObject json = new JSONObject();
		isettlement.update_state(cs_id);
		HttpSession session = super.getRequest().getSession();
		ilog.save_log(session, "修改结算状态为已结算",2);
		json.put("result", 1);
		return json;
	}
	
	
	
	
	
}

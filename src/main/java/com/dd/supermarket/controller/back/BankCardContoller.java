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
import com.dd.supermarket.controller.app.utils.PathFactory;
import com.dd.supermarket.fixed.PathConst;
import com.dd.supermarket.service.back.IBankCard;
import com.dd.supermarket.utils.PageData;
import com.dd.supermarket.utils.UuidUtil;
import com.dd.supermarket.utils.file.FileUploader;
import com.dd.supermarket.utils.http.GetServer;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("back/card")
public class BankCardContoller extends BaseController{
	
	@Resource(name="bankCardImpl")
	private IBankCard ibankCard;
	
	
	@ResponseBody
	@RequestMapping("find_bankCard")
	public JSONObject find_bankCard(){
		PageData pd = new PageData();
		pd = this.getPageData();
		/*String user_name = (String) pd.get("user_name");
		String create_time = (String) pd.get("create_time");
		String[] time = create_time.split(" - ");
		if(time.length > 0 && !time[0].trim().equals("")){
			String dt1=time[0];
			String dt2=time[1];
			pd.put("dt1", dt1);
			pd.put("dt2", dt2);
		}
		
		pd.put("user_name", user_name);*/
		
		this.getPager();
		List<PageData> list = ibankCard.find_bankCard(pd);
		PathFactory pf = new PathFactory();
		
		HttpServletRequest request = super.getRequest(); 
		String serverUrl = new GetServer().getServerUrl(request);
		
		for (int i = 0; i < list.size(); i++) {
			PageData pdata=list.get(i);
			list.set(i, (PageData)pf.settlBankFactory(serverUrl,pdata));
		}
		PageInfo pageInfo = this.getPageInfo(list);
		JSONObject resultJson = this.getResultJson(list, pageInfo);
		
		return resultJson;
	}
	
	//上下架（0上架 1下架）
	@RequestMapping("upd_state")
	@ResponseBody
	public JSONObject upd_state(String id,String st){
		JSONObject json = new JSONObject();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("bc_id", id);

		if("upper".equals(st)){
			//改为下架状态
			map.put("state",1);
		}else{
			//改为上架状态
			map.put("state", 0);
		}
		ibankCard.upd_state(map);
		return json;
	}
	
	//删除产品
	@RequestMapping("upd_isdisplay")
	@ResponseBody
	public JSONObject upd_isdisplay(String bc_id){
		ibankCard.upd_isdisplay(bc_id);
		JSONObject json = new JSONObject();
		json.put("result", 1);
		return json;
	}
	
	
	//添加
	@RequestMapping(value="save_settlement",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject save_settlement(HttpSession session,HttpServletRequest request, MultipartFile file,
			@RequestParam String bc_name,@RequestParam String bc_url,@RequestParam String bc_describe){
		JSONObject json = new JSONObject();			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("bc_name", bc_name);
	    map.put("bc_url", bc_url);
		map.put("bc_describe", bc_describe);		
		map.put("bc_id", UuidUtil.get32UUID());

		
		//保存充值截图并获取图片名称
		String bc_img = "";
		FileUploader fu = new FileUploader();
		
		if(null!=file){
			String servicePath = fu.getservicePath(request);
			try {
				bc_img = fu.uploade(file, servicePath+PathConst.BACK_CARD_PIC_URL);
				map.put("bc_img", bc_img);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			map.put("bc_img", "");
		}
		
		ibankCard.save_bankCard(map);
		
		json.put("result", 1);
		return json;
	}
		
		
		//查询信用卡信息
		@RequestMapping("findByIdCard")
		@ResponseBody
		public JSONObject findByIdCard(String bc_id){
			JSONObject json = new JSONObject();		
			Map<String, Object> map = (Map<String, Object>) ibankCard.findByIdCard(bc_id);
			json.put("map", map);
			return json;
		}
		
		//修改
		@RequestMapping(value="upd_card",method=RequestMethod.POST)
		@ResponseBody
		public JSONObject upd_card(HttpSession session,HttpServletRequest request, MultipartFile file,
				@RequestParam String bc_name,@RequestParam String bc_url,@RequestParam String bc_describe,@RequestParam String bc_id){
			JSONObject json = new JSONObject();			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("bc_name", bc_name);
		    map.put("bc_url", bc_url);
			map.put("bc_describe", bc_describe);		
			map.put("bc_id", bc_id);

			
			//保存充值截图并获取图片名称
			String bc_img = "";
			FileUploader fu = new FileUploader();
			
			if(null!=file){
				String servicePath = fu.getservicePath(request);
				try {
					bc_img = fu.uploade(file, servicePath+PathConst.BACK_CARD_PIC_URL);
					map.put("bc_img", bc_img);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				map.put("bc_img", "");
			}
			
			ibankCard.upd_card(map);
			
			json.put("result", 1);
			return json;
		}
		
		
}

package com.dd.supermarket.controller.back;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
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
import com.dd.supermarket.service.back.IAdmin;
import com.dd.supermarket.service.back.ICommodity;
import com.dd.supermarket.service.back.IDictionary;
import com.dd.supermarket.service.back.ILog;
import com.dd.supermarket.utils.PageData;
import com.dd.supermarket.utils.UuidUtil;
import com.dd.supermarket.utils.file.FileUploader;
import com.dd.supermarket.utils.http.GetServer;
import com.github.pagehelper.PageInfo;



@Controller
@RequestMapping("back/commdity")
public class CommodityContrller extends BaseController{
	
	@Resource(name="backCommodityImpl")
	private ICommodity icommodity;
	
	@Resource(name="backDictionaryImpl")
	private IDictionary iDiction;
	
	@Resource(name="backLogImpl")
	private ILog ilog;
	
	//List<Map<String,Object>> dictionList = iDiction.find_dictionByType("commodityCharacteris");
	@ResponseBody
	@RequestMapping("getCommdityPaes")
	public JSONObject getCommdityPaes(HttpSession session){	
		PageData pd = new PageData();
		pd = this.getPageData();
		String com_name = (String) pd.get("com_name");
		String ci_name = (String) pd.get("ci_name");
		session.setAttribute("com_name", com_name);
		session.setAttribute("ci_name", ci_name);
		pd.put("com_name", com_name);
		pd.put("ci_name", ci_name);
		this.getPager();
		List<PageData> list = icommodity.findByPage(pd);
		PageInfo pageInfo = this.getPageInfo(list);
		JSONObject resultJson = this.getResultJson(list, pageInfo);
		return resultJson;
	}
	
	//查询所有公司 商品品类 
	@RequestMapping("find_company")
	@ResponseBody
	public JSONObject find_company(Model model,String com_id){
		List<Map<String,Object>> dictionList = iDiction.find_dictionByType("commodityCharacteris");
		//查询全部产品分类
		List<Map<String,Object>> comclassifyList = iDiction.find_dictionByType("commodityLabel");
		List<Object> listcompany = icommodity.find_company();
		JSONObject json = new JSONObject();
		json.put("dictionList", dictionList);
		json.put("listcompany", listcompany);
		json.put("comclassifyList", comclassifyList);
		return json;
		
	}
	
	//根据id查询产品信息
	@RequestMapping("find_ByIdCommodity")
	@ResponseBody
	public JSONObject find_ByIdCommodity(String com_id){
		List<Object> listcom = icommodity.find_ByIdCommodity(com_id);		
		JSONObject json = new JSONObject();
		PathFactory pf = new PathFactory();
		
		HttpServletRequest request = super.getRequest(); 
		String serverUrl = new GetServer().getServerUrl(request);
		
		for (int i = 0; i < listcom.size(); i++) {
			Map<String, Object> map =(Map<String, Object>) listcom.get(i);
			listcom.set(i,pf.commodityFactory(serverUrl,map));
		}
		json.put("listcom", listcom);
		return json;
	}
	
	//添加或修改产品信息
		@RequestMapping("commodity_saveupdate")
		public String commodity_saveupdate(HttpServletRequest request,MultipartFile file,String com_id){
			 			
			Map<String,String[]> requestMap = request.getParameterMap();
			
			Map<String,Object> comMap = new HashMap<String,Object>();
			for (Map.Entry<String, String[]> rm : requestMap.entrySet()) {
				comMap.put(rm.getKey(), rm.getValue()[0]);
			}
			
			 if (file.getSize()== 0) {
			        file=null; 
			        comMap.put("com_logo", "");
			 }
			String uf_picture = "";
			FileUploader fu = new FileUploader();
			
			if(null!=file){
				String servicePath = fu.getservicePath(request);
				try {
					uf_picture = fu.uploade(file, servicePath+PathConst.COMMODITY_LOGO_PIC_URL);
					comMap.put("com_logo", uf_picture);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			double com_price2 =Double.valueOf(comMap.get("com_price").toString());
			int com_price3 = (int) (com_price2*100);
			comMap.put("com_price", com_price3);
			//判断是新增还是修改
			if(!"share".equals(com_id)){
				icommodity.update_commodity(comMap);
				HttpSession session = super.getRequest().getSession();
				ilog.save_log(session, "修改产品",0);
			}else{
				comMap.put("com_id", UuidUtil.get32UUID());
				icommodity.save_commodity(comMap);
				HttpSession session = super.getRequest().getSession();
				ilog.save_log(session, "添加产品",0);
			}
			
			
			return "back/html/products";
		}
		
		//上下架（0上架 5下架）
		@RequestMapping("update_state")
		@ResponseBody
		public JSONObject update_state(String id,String st){
			JSONObject json = new JSONObject();
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("com_id", id);
		
		
			if("upper".equals(st)){
				//改为下架状态
				map.put("state",5);
			}else{
				//改为上架状态
				map.put("state", 0);
			}
			icommodity.update_state(map);
			HttpSession session = super.getRequest().getSession();
			ilog.save_log(session, "上架下架",0);
			return json;
		}
		
		//删除产品
		@RequestMapping("del_comm")
		@ResponseBody
		public JSONObject del_comm(String com_id){
			icommodity.del_comm(com_id);
			JSONObject json = new JSONObject();
			json.put("result", 1);
			return json;
		}
		
		
}

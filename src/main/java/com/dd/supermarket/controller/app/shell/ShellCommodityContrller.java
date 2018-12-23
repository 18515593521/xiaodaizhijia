package com.dd.supermarket.controller.app.shell;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dd.supermarket.controller.BaseController;
import com.dd.supermarket.controller.app.utils.PathFactory;
import com.dd.supermarket.fixed.PathConst;
import com.dd.supermarket.pojo.CodeMsg;
import com.dd.supermarket.pojo.ResultData;
import com.dd.supermarket.pojo.Shell;
import com.dd.supermarket.service.app.shell.IOrder;
import com.dd.supermarket.service.app.shell.IShellCommodity;
import com.dd.supermarket.utils.PageData;
import com.dd.supermarket.utils.UuidUtil;
import com.dd.supermarket.utils.file.FileUploader;
import com.dd.supermarket.utils.http.GetServer;

@Controller
@RequestMapping("/app/shellCommodity")
public class ShellCommodityContrller extends BaseController{
	
	@Resource(name="shellCommodityImpl")
	private IShellCommodity ishellCommodity;
	
	@Resource(name="shellOrderImpl")
	private IOrder iorder;
	
	
	//我要出
	@RequestMapping("find_business")
	@ResponseBody
	public Shell save_shellcommodity(HttpServletRequest request,@RequestParam(value="file" ,required =false) MultipartFile file,
			String com_name,String describes,String user_id,String ty_id){
		
		Map<String,Object> comMap = new HashMap<String,Object>();
		Map<String,Object> orderMap = new HashMap<String,Object>();
		orderMap.put("user_id", user_id);
			 
		String uf_picture = "";
		FileUploader fu = new FileUploader();
	
		if(null!=file){
			String servicePath = fu.getservicePath(request);
			try {
				uf_picture = fu.uploade(file, servicePath+PathConst.SHELL_COMM_URL);
				comMap.put("com_picture", uf_picture);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			comMap.put("com_picture", "");
		}
		comMap.put("com_name", com_name);
		comMap.put("describes", describes);
		comMap.put("ty_id", ty_id);
		
		//获取手机号
		String phone = (String) ishellCommodity.find_phone(user_id);
		
		comMap.put("phone", phone);
		comMap.put("com_id", UuidUtil.get32UUID());
		String com_id =(String) comMap.get("com_id");
		orderMap.put("com_id", com_id);
		orderMap.put("state", 0);
		ishellCommodity.save_commodity(comMap);	
		
		iorder.save_order(orderMap);
		return new Shell(CodeMsg.CODE_SUCCESS, null);
	}
	
	//类型查询
	@RequestMapping("find_com")
	@ResponseBody
	public Shell find_com(String ty_id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Object> comlist = ishellCommodity.find_com(ty_id);
		PathFactory pf = new PathFactory();
		
		HttpServletRequest request = super.getRequest();
		String serverUrl = new GetServer().getServerUrl(request);	
		for (int i = 0; i < comlist.size(); i++) {
			Map<String, Object> map=(Map<String, Object>) comlist.get(i);
			comlist.set(i,pf.shellCommFactory(serverUrl,map));
		}
		resultMap.put("comList", comlist);				
		return new Shell(CodeMsg.CODE_SUCCESS, resultMap);
	}
	
	//首页轮播图片
	@RequestMapping("find_banner")
	@ResponseBody
	public Shell find_banner(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("bannerList", ishellCommodity.find_banner(request));	
		return new Shell(CodeMsg.CODE_SUCCESS, resultMap);
	}
	
}

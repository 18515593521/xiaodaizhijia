package com.dd.supermarket.controller.back;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.dd.supermarket.controller.BaseController;
import com.dd.supermarket.controller.app.model.Push;
import com.dd.supermarket.controller.app.utils.PathFactory;
import com.dd.supermarket.fixed.PathConst;
import com.dd.supermarket.push.PushStart;
import com.dd.supermarket.service.back.INews;
import com.dd.supermarket.utils.PageData;
import com.dd.supermarket.utils.UuidUtil;
import com.dd.supermarket.utils.file.FileUploader;
import com.dd.supermarket.utils.http.GetServer;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("back/news")
public class NewsContrller extends BaseController{
	
	@Resource(name="backNewsImpl")
	private INews inews;
	
	@ResponseBody
	@RequestMapping("find_news")
	public JSONObject find_log(){
		PageData pd = new PageData();
		pd = this.getPageData();
		String user_name = (String) pd.get("user_name");
		String create_time = (String) pd.get("create_time");
		String[] time = create_time.split(" - ");
		if(time.length > 0 && !time[0].trim().equals("")){
			String dt1=time[0];
			String dt2=time[1];
			pd.put("dt1", dt1);
			pd.put("dt2", dt2);
		}
		
		pd.put("user_name", user_name);
		
		this.getPager();
		List<PageData> list = inews.find_news(pd);
		PathFactory pf = new PathFactory();
		
		HttpServletRequest request = super.getRequest(); 
		String serverUrl = new GetServer().getServerUrl(request);
		
		for (int i = 0; i < list.size(); i++) {
			PageData pdata=list.get(i);
			list.set(i, (PageData)pf.pushFactory(serverUrl,pdata));
		}
		PageInfo pageInfo = this.getPageInfo(list);
		JSONObject resultJson = this.getResultJson(list, pageInfo);
		
		return resultJson;
	}
	
	//添加消息
	@ResponseBody
	@RequestMapping(value="save_news",method=RequestMethod.POST)
	public JSONObject save_news(HttpServletRequest request, MultipartFile file,@RequestParam String up_title,
			@RequestParam String up_content,@RequestParam String up_url){
		JSONObject json = new JSONObject();
		PageData pd = new PageData();
		String uf_picture = "";
		FileUploader fu = new FileUploader();
		Map<String , Object> map = new HashMap<String,Object>();
				
		if(null!=file){
			String servicePath = fu.getservicePath(request);
			try {
				uf_picture = fu.uploade(file, servicePath+PathConst.PUSH_PIC_URL);
				map.put("up_picture", uf_picture);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			map.put("up_picture", "");
		}
		
		map.put("up_title",up_title );
		map.put("up_content",up_content );
		map.put("up_url", up_url);
		map.put("up_id", UuidUtil.get32UUID());
					
		try {
			inews.save_news(map);
			//this.push_all(map);	//消息推送
		} catch (Exception e) {
			System.out.println("添加失败！");
		}
		json.put("result", 1);
		return json;		
	}
	
	private void push_all(Map map) throws Exception{
		Push up = new Push();
		String message_context = (String) map.get("up_content");
		String message_title = (String) map.get("up_title");
		up.setContext(message_context);
		up.setTicker("");
		up.setTitle(message_title);
		
		PushStart ps = new PushStart();
		ps.push_AllUser(up);  //群推消息
		
	}
}

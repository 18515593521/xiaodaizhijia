package com.dd.supermarket.service.app.user.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dd.supermarket.controller.app.utils.PathFactory;
import com.dd.supermarket.dao.app.user.AppUserinfoDao;
import com.dd.supermarket.fixed.PathConst;
import com.dd.supermarket.pojo.CodeMsg;
import com.dd.supermarket.service.app.user.IAppUserinfo;
import com.dd.supermarket.service.back.IDictionary;
import com.dd.supermarket.utils.UuidUtil;
import com.dd.supermarket.utils.file.FileUploader;
import com.dd.supermarket.utils.http.GetServer;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月26日 上午12:15:01 <br/>
* 类说明：
*/
@Transactional
@Service("appUserinfoImpl")
public class AppUserinfoImpl implements IAppUserinfo {

	@Resource(name="appUserinfoDao")
	private AppUserinfoDao appUserinfoDao;
	
	@Resource(name="backDictionaryImpl")
	private IDictionary iDiction;
	
	public Map<String, Object> find_userinfoByPhone(String phone) throws Exception {
		return (Map<String, Object>) appUserinfoDao.find_userinfoByPhone(phone);
	}

	/**
	 * 保存用户足迹
	 */
	public void add_userFootprint(String user_id, String com_id){
		Map<String,String> map = new HashMap<String,String>();
		map.put("user_id", user_id);
		map.put("com_id", com_id);
		try {
			appUserinfoDao.save_userFootprint(map);
		} catch (Exception e) {
			System.out.println("============== 保存用户足迹时出错 ==============");
			e.printStackTrace();
		}
	}

	/**
	 * 查询用户足迹
	 */
	public List<Object> find_userFootprintByUserId(HttpServletRequest request,String user_id,int page,int pageSize) {
		List<Object> list = new ArrayList<Object>();
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_id", user_id);
		map.put("page", page*pageSize);
		map.put("pageSize", pageSize);
		try {
			list = appUserinfoDao.find_userFootprintByUserId(map);
			list = null==list?new ArrayList<Object>():list;
		} catch (Exception e) {
			System.out.println("APP查询用户足迹时出错");
			e.printStackTrace();
			return list;
		}
		String serverUrl = new GetServer().getServerUrl(request);
		PathFactory pf = new PathFactory();
		for (Object fr : list) {
			fr = pf.commodityFactory(serverUrl, (Map<String,Object>)fr);
		}
		return list;
	}
	
	/**
	 * 查询用户足迹总数
	 */
	public int find_userFootprintCountByUserId(String user_id) {
		int count = 0;
		try {
			Object obj = appUserinfoDao.find_userFootprintCountByUserId(user_id);
			if(null==obj)return count;
		} catch (Exception e) {
			System.out.println("================== 查询用户足迹总数时出错 ==================");
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 保存意见反馈
	 */
	public String add_userFeedback(HttpServletRequest request, String user_id, String content,
			MultipartFile file) {
		String uf_picture = "";
		FileUploader fu = new FileUploader();
		
		if(null!=file){
			String servicePath = fu.getservicePath(request);
			try {
				uf_picture = fu.uploade(file, servicePath+PathConst.FEEDBACK_PIC_URL);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("user_id", user_id);
		map.put("uf_content", content);
		map.put("uf_picture", uf_picture);
		map.put("remarks", "");
		try {
			appUserinfoDao.save_userFeedback(map);
		} catch (Exception e) {
			System.out.println("=========== 保存意见反馈时出错 ===========");
			e.printStackTrace();
			return ""+CodeMsg.CODE_FAIL.getCode();
		}
		return ""+CodeMsg.CODE_SUCCESS.getCode();
	}

	/*
	 * 查询用户个人推送消息
	 */
	public List<Object> find_userPushByUserId(HttpServletRequest request, String user_id, int page, int pageSize) {
		if(user_id==null||user_id.equals("")){
			 return new ArrayList<>();
		}
		List<Object> list = new ArrayList<Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_id", user_id);
		map.put("page", page*pageSize);
		map.put("pageSize", pageSize);
		//map.put("read_count", list.size());
		try {
			list = appUserinfoDao.find_userPushByUserId(map);
			list = null==list?new ArrayList<Object>():list;
		} catch (Exception e) {
			System.out.println("APP查询用户消息时出错");
			e.printStackTrace();
			return list;
		}
		try {
			appUserinfoDao.update_userinfosReadCountByUserId(map);
		} catch (Exception e) {
			System.out.println("修改用户已读消息数量时出错");
		}
		String serverUrl = new GetServer().getServerUrl(request);
		PathFactory pf = new PathFactory();
		for (Object fr : list) {
			fr = pf.pushFactory(serverUrl, (Map<String,Object>)fr);
		}
		return list;
	}

	/**
	 * 获取关于我们数据
	 */
	public Map<String, Object> getAboutUSData(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>> dictionList = iDiction.find_dictionByType("aboutUS");
		for (Map<String, Object> dl : dictionList) {
			map.put((String)dl.get("dic_key"), dl.get("dic_value"));
		}
		map = new PathFactory().aboutUSFactory(new GetServer().getServerUrl(request), map);
		return map;
	}

	/**
	 * 保存商务合作信息
	 */
	public String save_cooperation(String user_id, String email, String phone, String companyName, String notes) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("contact_email", email);
		map.put("contact_phone", phone);
		map.put("companyName", companyName);
		map.put("bc_content", notes);
		try {
			appUserinfoDao.save_cooperation(map);
		} catch (Exception e) {
			System.out.println("============ 保存商务合作信息失败 ============");
			e.printStackTrace();
			return ""+CodeMsg.CODE_FAIL.getCode();
		}
		return ""+CodeMsg.CODE_SUCCESS.getCode();
	}
	
	
	/**
	 * 保存预申请量
	 */
	public String save_Apply(String user_id, String com_id){
		Map<String,String> map = new HashMap<String,String>();
		String aa_id = UuidUtil.get32UUID();
		map.put("user_id", user_id);
		map.put("com_id", com_id);
		map.put("aa_id", aa_id);
		try {
			appUserinfoDao.save_Apply(map);
		} catch (Exception e) {
			System.out.println("============== 保存用户足迹时出错 ==============");
			e.printStackTrace();
			return ""+CodeMsg.CODE_FAIL.getCode();
		}
		return aa_id;
	}
	
	/**
	 * 保存用户token
	 * @param map
	 * @throws Exception
	 */
	public void up_token(Map map){
		appUserinfoDao.up_token(map);
	}

	/**
	 * 保存用户手机型号 app版本号
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public void up_appNV(Map map){
		appUserinfoDao.up_appNV(map);
	}

}

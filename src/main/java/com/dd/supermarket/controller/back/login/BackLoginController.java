package com.dd.supermarket.controller.back.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dd.supermarket.controller.BaseController;
import com.dd.supermarket.service.back.IAdmin;
import com.dd.supermarket.service.back.ILogin;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月24日 下午2:26:42 <br/>
* 类说明：
*/
@Controller
@RequestMapping("back/login")
public class BackLoginController extends BaseController{

	
	@Resource(name="backLoginImpl")
	private ILogin ilogin;
	
	@RequestMapping("login")
	public String login() {
		return "back/html/login";
	}
	
	@RequestMapping("find_admin")
	public String find_admin(Model model,String sys_sa_loginname,String sys_sa_password,HttpSession session) {
		String str = "";
	
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("sys_sa_loginname", sys_sa_loginname);
		map.put("sys_sa_password", sys_sa_password);
		Map<String, Object> mapadmin = (Map<String, Object>) ilogin.find_admin(map);
		if(null != mapadmin){
			session.setAttribute("user",mapadmin);
			String sys_sr_id = (String) mapadmin.get("sys_sr_id");
			Map<String, Object> mapadminPower = (Map<String, Object>) ilogin.find_adminPower(sys_sr_id);
			model.addAttribute("mapadminPower", mapadminPower);
			str="back/html/blank";	
		}else{
			str="back/html/login";
		}
		return str;
	}
	
	@RequestMapping("quit")
	@ResponseBody
	public String quit(HttpSession session){
		session.removeAttribute("user");
		return "/supermarket/back/html/login.html";
	}
}

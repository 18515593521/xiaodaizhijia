package com.dd.supermarket.controller.weChat.serverConfiguration;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dd.supermarket.controller.BaseController;
import com.dd.supermarket.controller.weChat.accessToken.TokenThread;
import com.dd.supermarket.controller.weChat.util.Print;
import com.dd.supermarket.controller.weChat.util.SignatureUtil;
import com.dd.supermarket.utils.IsEmpty;
import com.dd.supermarket.utils.PageData;

@RequestMapping("WeChat")
@Controller
public class WeChatController extends BaseController{

	
	@RequestMapping("wechatServlet")
	public void wechatServlet(HttpServletResponse response)throws ServletException, IOException {
		PageData pd = super.getPageData();
//		System.out.println(pd);
		wxVerification_servlet(response, pd);  //微信验证服务器
	}
	
	/**
	 * 刷新未刷新全局AccessToken
	 */
	@RequestMapping("refreshAccessToken")
	public void refreshAccessToken(){
		TokenThread.getAccessToken();
	}

	/**
	 * 微信验证服务器
	 * @param response
	 * @param pd
	 * @throws IOException
	 */
	private void wxVerification_servlet(HttpServletResponse response, PageData pd) throws IOException {
		// 微信加密签名  
        String signature = pd.getString("signature");  
        // 时间戳  
        String timestamp = pd.getString("timestamp");  
        // 随机数  
        String nonce = pd.getString("nonce");  
        // 随机字符串  
        String echostr = pd.getString("echostr");  
        
        if(IsEmpty.isEmpty(signature,timestamp,nonce,echostr))return;
        Print.println("signature >>>"+signature,"timestamp >>>"+timestamp ,"nonce >>>"+nonce,"echostr >>>"+echostr);
        
        PrintWriter out = response.getWriter();  
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
        if (SignatureUtil.checkSignature(signature, timestamp, nonce)) {  
            out.print(echostr);  
        }  
        
        out.close();  
        out = null;
	}
	
}
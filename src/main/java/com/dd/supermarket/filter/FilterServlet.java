package com.dd.supermarket.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FilterServlet implements Filter{
	public void destroy() {
		 
    }
 
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
 
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String servletPath=req.getServletPath();
       
        //判断用户是否登录
        HttpSession session = req.getSession();
        //没有登录，且访问页面不是登录也页面
        if(session.getAttribute("user") == null){
            //转发到登录页面 
            request.getRequestDispatcher("/supermarket/back/login/login").forward(req,res);
        }else{
            //放行
            chain.doFilter(req,res);
        }
      
    }
 
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
 
    }
}

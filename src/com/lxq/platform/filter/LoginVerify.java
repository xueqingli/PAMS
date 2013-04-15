package com.lxq.platform.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lxq.platform.servlet.Initialization;
import com.lxq.platform.userManage.pojo.User;

/**
 * 校验用户是否已经登录，没有登录则跳转到登录主页面
 * @author lixueqing
 *
 */
public class LoginVerify implements Filter{

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		
		//当前访问的url地址
		String url = request.getServletPath();
		
		/**
		 * url地址以.do,.action,或者.jsp结尾，
		 * 且不已index或login开头的url地址
		 * 则判断当前用户是否登录
		*/
		if ((url.endsWith(".do") || url.endsWith(".action") || url.endsWith(".jsp")) 
				&& url.indexOf("/index")< 0 && url.indexOf("/login") < 0) {
			
			HttpSession session = request.getSession();
			
			//从session中获取当前用户
			User user = (User) session.getAttribute("curUser");
			
			if(user == null){//如果用户不存在，则跳转到登录界面
				String path = (String)Initialization.context.getAttribute("path");
				response.sendRedirect(path+"/index.jsp");//返回登录页面
			}else{
				//否则继续过滤
				chain.doFilter(request, response);
			}
			
		}else{
			//否则继续过滤
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}

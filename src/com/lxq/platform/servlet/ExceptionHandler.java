package com.lxq.platform.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lxq.platform.util.Struts2Util;

/**
 * 异常处理
 * 
 * @author lixueqing
 * 
 */
public class ExceptionHandler extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected  void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException{
		
		String exceptionMessage = req.getParameter("exceptionMessage");
		
		Struts2Util.responseText("{'success':false,'msg':'"+exceptionMessage+"'}");
	}
}

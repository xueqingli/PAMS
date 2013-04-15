package com.lxq.platform.listener;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.lxq.platform.servlet.Initialization;
import com.lxq.platform.userManage.pojo.User;
import com.lxq.platform.util.DateUtil;

/**
 * session监听
 * 
 * @author lixueqing
 * 
 */
public class SessionListener implements HttpSessionListener,HttpSessionAttributeListener {
	
	private static Logger logger = Logger.getLogger(SessionListener.class);
	
	public void valueBound(HttpSessionBindingEvent event) {
	}

	public void valueUnbound(HttpSessionBindingEvent event) {
		
	}

	public void sessionCreated(HttpSessionEvent arg0) {
		
	}
 
	@SuppressWarnings("unchecked")
	public void sessionDestroyed(HttpSessionEvent arg0) {
		//获取当前session
		HttpSession session = arg0.getSession();
		
		User curUser = (User) session.getAttribute("curUser");
		String ipAddress = (String) session.getAttribute("ipAddress");
		
		if(curUser != null){
			logger.info(curUser.getUserId()+"-"+curUser.getUserName()+"    "+DateUtil.getNowTime()+"    "+ipAddress+"    logout");
		}
		
		//移除用户登录列表中登录用户的session
		List<HttpSession> onlineList = ((List<HttpSession>) Initialization.context.getAttribute("onlineList"));
		
		//用户列表中移除当前用户
		onlineList.remove(session);
	}

	public void attributeAdded(HttpSessionBindingEvent arg0) {
		
	}

	public void attributeRemoved(HttpSessionBindingEvent arg0) {
	
	}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		
	}

}

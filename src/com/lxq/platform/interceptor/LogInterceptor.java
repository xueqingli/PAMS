package com.lxq.platform.interceptor;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


/**
 * 异常拦截器
 * @author lixueqing@foxmail.com
 *
 */
public class LogInterceptor extends AbstractInterceptor {

	private static Logger logger = Logger.getLogger(LogInterceptor.class);
	private static final long serialVersionUID = -4627783886839367267L;

	public String intercept(ActionInvocation invocation) throws Exception {
	    try{ 
	    	invocation.invoke(); 
	    }catch(Exception e){
	    	
	    	logger.error("错误信息",e); //记录异常信息
	    	throw e;
	    }
	    return null;
	}
}
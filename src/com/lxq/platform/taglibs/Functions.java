package com.lxq.platform.taglibs;

import org.springframework.web.context.ContextLoader;

import com.lxq.platform.userManage.service.intef.IUserService;

public class Functions
{
    public static boolean hasRight(com.lxq.platform.userManage.pojo.User user , String operate, String className)
    {
		IUserService userService = (IUserService) ContextLoader.getCurrentWebApplicationContext().getBean("userService");
		
		return userService.hasRight(user, operate, className);
    }

    public static String getValue(String value)
    {
    	return value;
    }
}

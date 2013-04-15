package com.lxq.platform.aop;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

import com.lxq.platform.exception.PrivilegeException;
import com.lxq.platform.userManage.pojo.User;
import com.lxq.platform.userManage.service.intef.IUserService;

/**
 * 权限控制
 * @author lixueqing@foxmail.com
 * 
 */
public class PrivilegeControlAdvice implements MethodBeforeAdvice {

	private IUserService userService;

	/**
	 * 方法前执行
	 */
	public void before(Method method, Object[] args, Object target)
			throws Throwable {

		Object entity = args[0];
		User user = (User) args[1];

		//如果没有权限，则返回权限异常
		if (!userService.hasRight(user, method.getName(), entity.getClass()
				.getName())) {
			throw new PrivilegeException("没有操作权限");
		}
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
}
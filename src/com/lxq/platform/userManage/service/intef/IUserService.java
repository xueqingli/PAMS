package com.lxq.platform.userManage.service.intef;

import java.util.Set;

import com.lxq.platform.service.intef.IBaseService;
import com.lxq.platform.userManage.pojo.Privilege;
import com.lxq.platform.userManage.pojo.Role;
import com.lxq.platform.userManage.pojo.User;

public interface IUserService extends IBaseService {

	
	/**
	 * 校验用户是否存在
	 * @param user 被校验用户实体
	 * @return 存在返回true，不存在返回false
	 */
	public boolean hasUser(User user);

	/**
	 * 校验用户名和密码是否匹配
	 * @param user 用户实体
	 * @return 存在返回true，不存在返回false
	 */
	public boolean checkPassword(User user,String password);
	
	/**
	 * 根据用户编号获取用户实体
	 * @param userId 用户编号
	 */
	public User findUserByUserId(String userId);

	
	/**
	 * 用户登陆
	 * @param user 登陆用户
	 * @param loginTime 登陆时间
	 * @param ipAddress 登陆ip地址
	 */
	public void login(User user,String loginTime,String ipAddress);
	
	/**
	 * 获取指定用户的所有角色
	 * @param user 用户
	 * @return 角色集合
	 */
	public Set<Role> getRoles(User user);
	
	/**
	 * 获取指定用户的所有权限
	 * @param user 用户
	 * @return 权限集合
	 */
	public Set<Privilege> getPrivileges(User user);
	
	/**
	 * 校验用户对一个实体类的操作是否有权限
	 * @param operate 操作
	 * @param entityName 实体类名称
	 * @return true:有权限，false:无权限
	 */
	public boolean hasRight(User user , String operate , String className);
	
	/**
	 * 判断用户是否有指定角色
	 * @param user 用户
	 * @param role 角色
	 * @return
	 */
	public boolean hasRole(User user , Role role);
	
	/**
	 * 用户可见菜单
	 * @param user 用户
	 * @return
	 */
	public String getUserMenus(User user);
}

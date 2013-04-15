package com.lxq.platform.userManage.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.lxq.platform.service.impl.BaseServiceImpl;
import com.lxq.platform.systemManage.pojo.Menu;
import com.lxq.platform.userManage.pojo.Group;
import com.lxq.platform.userManage.pojo.Privilege;
import com.lxq.platform.userManage.pojo.Role;
import com.lxq.platform.userManage.pojo.User;
import com.lxq.platform.userManage.service.intef.IUserService;
import com.lxq.platform.util.MD5;

public class UserServiceImpl extends BaseServiceImpl implements IUserService {
	
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	/**
	 * 校验用户是否存在
	 * @param user 被校验用户实体
	 * @return 存在返回true，不存在返回false
	 */
	public boolean hasUser(User user){
		
		if(user != null ){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 校验用户名和密码是否匹配
	 * @param user 用户实体
	 * @return 匹配返回true，否则返回false
	 */
	public boolean checkPassword(User user,String password){
		
		String db_password = user.getPassword();
		
		if(MD5.encode(password).equals(db_password) ){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 根据用户编号获取用户实体
	 * @param userId 用户编号
	 */
	public User findUserByUserId(String userId){
		
		return (User)baseDAO.findUniqueByHQL("from User where userId='"+userId+"'");

	}
	
	/**
	 * 用户登陆
	 * @param user 登陆用户
	 * @param loginTime 登陆时间
	 * @param ipAddress 登陆ip地址
	 * @param isRecordloginLog 是否记录登陆日志
	 */
	public void login(User user,String loginTime,String ipAddress){
		
		user.setLoginNum(user.getLoginNum()+1);
		baseDAO.saveOrUpdate(user);
		
		logger.info(user.getUserId()+"-"+user.getUserName()+"    "+loginTime+"    "+ipAddress+"    login");
	}
	
	/**
	 * 获取指定用户的所有角色
	 * @param user 用户
	 * @return 角色集合
	 */
	public Set<Role> getRoles(User user){
		
		Set<Role> roles = user.getRoles();
		
		Set<Group> groups = user.getGroups();
		Iterator<Group> it = groups.iterator();
		while(it.hasNext()){
			Group group = it.next();
			Set<Role> g_roles = group.getRoles();
			roles.addAll(g_roles);
		}
		
		return roles;
	}
	
	/**
	 * 获取指定用户的所有权限
	 * @param user 用户
	 * @return 权限集合
	 */
	public Set<Privilege> getPrivileges(User user){
		Set<Role> roles = getRoles(user);
		
		Set<Privilege> privileges = new HashSet<Privilege>();
		
		Iterator<Role> it = roles.iterator();
		while(it.hasNext()){
			Role role = it.next();
			Set<Privilege> r_privileges = role.getPrivileges();
			if(r_privileges != null){
				privileges.addAll(r_privileges);
			}
		}
		
		return privileges;
	}
	
	/**
	 * 判断用户是否有指定角色
	 * @param user 用户
	 * @param role 角色
	 * @return
	 */
	public boolean hasRole(User user , Role role){
		
		User t_user = (User)baseDAO.findById(User.class, user.getUid());
		
		boolean hasRole = false;
		
		Set<Role> roles = getRoles(t_user);
		
		Iterator<Role> it = roles.iterator();
		while(it.hasNext()){
			Role t_role = it.next();
			
			if(t_role.getUid()==role.getUid()){
				return true;
			}
		}
		
		return hasRole;
	}
	
	/**
	 * 校验用户对一个实体类的操作是否有权限
	 * @param operate 操作
	 * @param entityName 实体类名称
	 * @return true:有权限，false:无权限
	 */
	public boolean hasRight(User user , String operate , String className){
		
		User t_user = (User)baseDAO.findById(User.class, user.getUid());
		
		boolean hasRight = false;
		
		Set<Privilege> privileges = getPrivileges(t_user);
		
		Iterator<Privilege> it = privileges.iterator();
		while(it.hasNext()){
			Privilege privilege = it.next();
			
			if((privilege.getOperate().equals(operate) || privilege.getOperate().equals("all")) && privilege.getClassName().equals(className)){
				return true;
			}
		}
		
		return hasRight;
	}
	
	/**
	 * 用户可见菜单
	 * @param user 用户
	 * @return 菜单集合的json对象
	 */
	public String getUserMenus(User user){
		
		Menu menu = (Menu)baseDAO.findUniqueByHQL("from Menu where id=1");
		
		JSONArray ja = getChildMenus(user,menu);
		
		return ja==null?"":ja.toString();
	}
	
	/**
	 * 获取菜单的json数组
	 * @param menu
	 * @return 菜单集合的json对象
	 */
	public JSONArray getChildMenus(User user,Menu menu){
		
		Set<Menu> childMenus = menu.getChildMenus();
		
		if(childMenus.size() == 0){
			return null;
		}
		
		JSONArray ja = new JSONArray();
		Iterator<Menu> it = childMenus.iterator();
		while(it.hasNext()){
			
			Menu childMenu = it.next();
			if(!childMenu.isValid()){
				continue;
			}
			
			boolean hasRole = false;
			Set<Role> roles = childMenu.getRoles();
			Iterator<Role> r_it = roles.iterator();
			while(r_it.hasNext()){
				Role role = r_it.next();
				hasRole = hasRole(user , role);
				if(hasRole){
					break;
				}
			}
			
			if(hasRole){
				JSONObject jb = new JSONObject();
				jb.put("id", childMenu.getUid());
				jb.put("text", childMenu.getText());
				jb.put("url", childMenu.getUrl());
				jb.put("showTree", childMenu.isShowTree());
				JSONArray t_ja = getChildMenus(user,childMenu);
				if(t_ja != null){
					jb.put("menu", t_ja);
				}
				ja.add(jb);
			}
		}
		return ja.size() == 0 ? null : ja;
	}
}

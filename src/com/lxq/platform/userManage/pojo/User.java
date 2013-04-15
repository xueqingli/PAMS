package com.lxq.platform.userManage.pojo;


import java.util.Set;

import com.lxq.platform.systemManage.pojo.CodeLibrary;
/**
 * 用户实体类
 * @author lixueqing
 *
 */
public class User {
	
	/**主键*/
	private int uid;
	
	/**用户编号*/
	private String userId;
	
	/**用户名称*/
	private String userName;
	
	/**用户密码*/
	private String password;
	
	/**所属用户部门*/
	private Department belongDept;
	
	/**所属用户组*/
	private Set<Group> groups;
	
	/**用户所有角色*/
	private Set<Role> roles;
	
	/**用户状态*/
	private CodeLibrary status;
	
	/**登陆次数*/
	private int loginNum;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Department getBelongDept() {
		return belongDept;
	}
	public void setBelongDept(Department belongDept) {
		this.belongDept = belongDept;
	}
	public Set<Group> getGroups() {
		return groups;
	}
	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getLoginNum() {
		return loginNum;
	}
	public void setLoginNum(int loginNum) {
		this.loginNum = loginNum;
	}
	public CodeLibrary getStatus() {
		return status;
	}
	public void setStatus(CodeLibrary status) {
		this.status = status;
	}
	
}
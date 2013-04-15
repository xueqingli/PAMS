package com.lxq.platform.userManage.pojo;

import java.util.Set;

import com.lxq.platform.systemManage.pojo.CodeLibrary;

/**
 * 用户组实体类
 * @author lixueiqng
 *
 */
public class Group {
	
	/**主键*/
	private int uid;

	/**用户组编号*/
	private String groupId;

	/**用户组名称*/
	private String groupName;
	
	/**用户组用户集*/
	private Set<User> users;
	
	/**用户组角色集*/
	private Set<Role> roles;
	
	/**用户组状态*/
	private CodeLibrary status;
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
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
	public CodeLibrary getStatus() {
		return status;
	}
	public void setStatus(CodeLibrary status) {
		this.status = status;
	}
	
}
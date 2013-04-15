package com.lxq.platform.userManage.pojo;

import java.util.Set;

import com.lxq.platform.systemManage.pojo.CodeLibrary;

/**
 * 用户实体类
 * @author lixueqing
 *
 */
public class Role {

	/**主键*/
	private int uid;
	
	/**角色编号*/
	private String roleId;

	/**角色名称*/
	private String roleName;
	
	/**权限集合*/
	private Set<Privilege> privileges;
	
	/**角色状态*/
	private CodeLibrary status;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Set<Privilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
	public CodeLibrary getStatus() {
		return status;
	}
	public void setStatus(CodeLibrary status) {
		this.status = status;
	}
	
}
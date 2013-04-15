package com.lxq.platform.systemManage.pojo;

import java.util.Set;

import com.lxq.platform.userManage.pojo.Role;

/**
 * Ext菜单实体类
 * @author lixueqing
 * 
 */
public class Menu {
	
	/**菜单主键*/
	private int uid;

	/**排序号*/
	private int orderNo;

	/**菜单名称*/
	private String text;
	
	/**菜单链接地址*/
	private String url;
	
	/**展示树形菜单*/
	private boolean showTree;
	
	/**父菜单*/
	private Menu parentMenu;
	
	/**子菜单*/
	private Set<Menu> childMenus;
	
	/**可见菜单的角色*/
	private Set<Role> roles;
	
	/**是否有效*/
	private boolean valid;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Menu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}
	public Set<Menu> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(Set<Menu> childMenus) {
		this.childMenus = childMenus;
	}

	public boolean isShowTree() {
		return showTree;
	}

	public void setShowTree(boolean showTree) {
		this.showTree = showTree;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
}
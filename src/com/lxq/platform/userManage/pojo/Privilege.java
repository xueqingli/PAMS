package com.lxq.platform.userManage.pojo;


/**
 * 权限实体类
 * @author lixueqing
 *
 */
public class Privilege {
	
	/**权限编号*/
	private int uid;

	/**权限名称*/
	private String name;
	
	/**操作名称*/
	private String operate;
	
	/**实体类名称*/
	private String className;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
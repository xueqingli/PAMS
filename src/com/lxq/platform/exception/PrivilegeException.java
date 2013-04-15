package com.lxq.platform.exception;

/**
 * 操作权限异常
 * @author Administrator
 *
 */
public class PrivilegeException extends Exception {
	private static final long serialVersionUID = -7405627348315348153L;

	public PrivilegeException() {
		super();
	}
	public PrivilegeException(String msg) {
		super(msg);
	}

	public PrivilegeException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PrivilegeException(Throwable cause) {
		super(cause);
	}
}
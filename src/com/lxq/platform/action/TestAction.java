package com.lxq.platform.action;

import java.sql.SQLException;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class TestAction extends ActionSupport{
	
	public String test() throws Exception{
		
		throw new SQLException();
		
	}
	
}

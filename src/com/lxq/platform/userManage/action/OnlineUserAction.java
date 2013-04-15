package com.lxq.platform.userManage.action;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.lxq.platform.action.BaseAction;
import com.lxq.platform.userManage.pojo.Department;
import com.lxq.platform.userManage.pojo.User;
import com.lxq.platform.util.Struts2Util;

@SuppressWarnings("serial")
public class OnlineUserAction extends BaseAction {
	
	private String userId;
	/**
	 * 在线用户列表
	 */
	@SuppressWarnings("unchecked")
	public void jsonPage() {

		ServletContext context = ServletActionContext.getServletContext();
		List<HttpSession> onlineList =  (List<HttpSession>) context.getAttribute("onlineList");//获取登录用户列表
		
		JSONArray topics = new JSONArray();
		for(int i = 0 ; i < onlineList.size() ; i ++){
			HttpSession session = onlineList.get(i);
			
			User user = (User) session.getAttribute("curUser");
			Department department = (Department) session.getAttribute("curDept");
			String loginTime = (String) session.getAttribute("loginTime");
			String ipAddress = (String) session.getAttribute("ipAddress");
			
			JSONObject topic = new JSONObject();
			
			topic.put("uid",user.getUid());
			topic.put("userId",user.getUserId());
			topic.put("userName",user.getUserName());
			topic.put("belongDept",department.getDeptName());
			topic.put("loginTime",loginTime);
			topic.put("ipAddress",ipAddress);
			
			topics.add(topic);
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("totalCount", onlineList.size());
		jsonObject.put("topics", topics);
		
		String str_json = jsonObject.toString();
		
		Struts2Util.responseText(str_json);
		
	}
	/**
	 * 强制退出
	 */
	@SuppressWarnings("unchecked")
	public void logout() {

		ServletContext context = ServletActionContext.getServletContext();
		List<HttpSession> onlineList =  (List<HttpSession>) context.getAttribute("onlineList");//获取登录用户列表
		
		for(int i = 0 ; i < onlineList.size() ; i ++){
			HttpSession session = onlineList.get(i);
			try{
				User user = (User) session.getAttribute("curUser");
				
				if(user.getUserId().equals(userId)){
					session.invalidate();
					onlineList.remove(session);//移除session
				}
			}catch(IllegalStateException e){
				onlineList.remove(session);//移除session
			}
		}
		Struts2Util.responseText("{'success':true,'msg':'已强制退出'}");
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	
}

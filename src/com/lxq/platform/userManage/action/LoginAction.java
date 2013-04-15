package com.lxq.platform.userManage.action;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.lxq.platform.userManage.pojo.User;
import com.lxq.platform.userManage.service.intef.IUserService;
import com.lxq.platform.util.DateUtil;
import com.lxq.platform.util.Struts2Util;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class LoginAction extends ActionSupport {

	/**用户编号*/
    private String userId;
    /**密码*/
    private String password;
    
    /*用户服务对象*/
    private IUserService userService;
	
    /**
     * 用户登陆
     * @return
     */
	@SuppressWarnings("unchecked")
	public String login() {
		
		boolean success = true;
		String msg = "登陆成功！";
		
		User user = userService.findUserByUserId(userId);
		
		if(!userService.hasUser(user)){//校验用户是否存在
			
			success = false;
			msg = "用户号不存在！";
		
		}else if(!user.getStatus().getValue().equals("1")){//校验用户是否失效
		
			success = false;
			msg = "用户已停用！";
		
		}else if(!userService.checkPassword(user,password)){////校验密码是否正确
		
			success = false;
			msg = "密码错误！";
		
		}else{//成功操作
			
			ServletContext context = ServletActionContext.getServletContext();
			List<HttpSession> onlineList = (List<HttpSession>) context.getAttribute("onlineList");//获取登录用户列表
			
//			for(int i = 0 ; i < onlineList.size() ; i ++){
//				HttpSession session = onlineList.get(i);
//				
//				try{
//					User t_user = (User)session.getAttribute("curUser");
//					if(t_user != null && t_user.getUid() == user.getUid()){
//						
//						//----一个用户登陆，会将已经登陆的相同用户，挤下线
//						session.invalidate();//session过期
//						
//						onlineList.remove(session);//移除session
//						
//						break;
//						
//					}
//				}catch(IllegalStateException e){
//					onlineList.remove(session);//移除session
//				}
//			}
			//将当前登录用户的session放到登录用户列表中
			onlineList.add(ServletActionContext.getRequest().getSession());
			
			String loginTime = DateUtil.getNowTime();//登陆时间
			String ipAddress = ServletActionContext.getRequest().getRemoteAddr();//登陆ip
			
			userService.login(user, loginTime, ipAddress);
			
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("userMenus", userService.getUserMenus(user));//将用户菜单放到session中
			session.put("loginTime", loginTime);//将登录时间放到session中
			session.put("ipAddress", ipAddress);//将登录时间放到session中
			session.put("curUser", user);//将当前用户放到session中
			session.put("curDept", user.getBelongDept());//将当前机构放到session中
		}
		
		String str_json = "{'success':"+success+",'msg':'"+msg+"'}";
		
		Struts2Util.responseText(str_json);
		return null;
	}

	/**
	 * 刷新用户缓存
	 * @return
	 */
	public void refreshUserCache(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		
		User curUser = (User)session.get("curUser");
		
		curUser = (User)userService.findById(User.class, curUser.getUid());
		
		session.put("userMenus", userService.getUserMenus(curUser));//将用户菜单放到session中
		session.put("curUser", curUser);//将当前机构放到session中
		session.put("curDept", curUser.getBelongDept());//将当前机构放到session中
	}
	
	/**
	 * 退出系统
	 * @return
	 */
	public String logout(){
		//session过期
		ServletActionContext.getRequest().getSession().invalidate();
		
		return SUCCESS;
	}
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

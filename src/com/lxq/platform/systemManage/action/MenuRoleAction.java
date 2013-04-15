package com.lxq.platform.systemManage.action;

import java.util.Iterator;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.lxq.platform.action.BaseAction;
import com.lxq.platform.systemManage.pojo.Menu;
import com.lxq.platform.userManage.pojo.Role;
import com.lxq.platform.userManage.pojo.User;
import com.lxq.platform.util.Struts2Util;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class MenuRoleAction extends BaseAction {
	
	/**
     * 获取json格式的分页数据
     * @return json格式的分页数据。
     *     例：{callback:"callback1001",totalCount:2,topics:[{"admin","系统管理员"},{"test","测试用户"}]}
     */
	public void jsonPage() {

		if(queryClause == null || queryClause.equals("")){
			Struts2Util.responseText("'totalCount':0,'topics':[]}");
		}else{
			Menu menu = (Menu) baseService.findUniqueByHQL("from Menu where "+queryClause);
			
			JSONArray topics = new JSONArray();
			
			JSONObject topic = new JSONObject();
			
			Set<Role> roles = menu.getRoles();
			Iterator<Role> it = roles.iterator();
			
			topic.put("menuId", menu.getUid());
			while(it.hasNext()){
				Role role = it.next();
				
				topic.put("roleUid", role.getUid());
				topic.put("roleId", role.getRoleId());
				topic.put("roleName", role.getRoleName());
				topic.put("status", role.getStatus().getText());
				
				topics.add(topic);
			}
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("totalCount", roles.size());
			jsonObject.put("topics", topics);
			
			String str_json = jsonObject.toString();
			
			Struts2Util.responseText(str_json);
		}
		
	}
	
	public void delete() throws Exception{
		
		User user = (User)(ActionContext.getContext().getSession().get("curUser"));
		
		if(!userService.hasRight(user, "delete", entityName)){
			String str_json = "{'success':false,'msg':'当前用户没有删除权限'}";
			Struts2Util.responseText(str_json);
			return;
		}
		
		JSONObject jb = JSONObject.fromObject(jsonObject);
		
		int menuId = jb.getInt("menuId");
		int roleUid = jb.getInt("roleUid");
		
		Menu menu = (Menu)baseService.findById(Menu.class, menuId);
		
		Set<Role> roles = menu.getRoles();
		
		Iterator<Role> it = roles.iterator();
		while(it.hasNext()){
			Role role = it.next();
			
			if(role.getUid() == roleUid){
				roles.remove(role);
				break;
			}
		}
		
		baseService.update(menu,(User)(ActionContext.getContext().getSession().get("curUser")),ServletActionContext.getRequest().getRemoteAddr(),"json:"+jsonObject);

		String str_json = "{'success':true,'msg':'删除成功'}";
		Struts2Util.responseText(str_json);
	}
	
	public void save() throws Exception {
		
		JSONObject jb = JSONObject.fromObject(jsonObject);
		
		int menuId = jb.getInt("menuId");
		int roleUid = jb.getInt("roleUid");
		
		Menu menu = (Menu)baseService.findById(Menu.class, menuId);
		
		Set<Role> roles = menu.getRoles();
		
		Iterator<Role> it = roles.iterator();
		
		Role role = null;
		
		while(it.hasNext()){
			Role t_role = it.next();
			
			if(t_role.getUid() == roleUid){
				role = t_role;
			}
		}
		
		if(role == null){
			role = new Role();
			role.setUid(roleUid);
			
			roles.add(role);
		}
		
		// 保存对象
		baseService.add(menu,(User)(ActionContext.getContext().getSession().get("curUser")),ServletActionContext.getRequest().getRemoteAddr(),"json:"+jsonObject);
		
		String str_json = "{'success':true,'msg':'保存成功'}";
		
		Struts2Util.responseText(str_json);
	}
}

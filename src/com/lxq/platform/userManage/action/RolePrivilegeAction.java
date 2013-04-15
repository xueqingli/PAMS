package com.lxq.platform.userManage.action;

import java.util.Iterator;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.lxq.platform.action.BaseAction;
import com.lxq.platform.userManage.pojo.Privilege;
import com.lxq.platform.userManage.pojo.Role;
import com.lxq.platform.userManage.pojo.User;
import com.lxq.platform.util.Struts2Util;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class RolePrivilegeAction extends BaseAction {
	
	/**
     * 获取json格式的分页数据
     * @return json格式的分页数据。
     *     例：{callback:"callback1001",totalCount:2,topics:[{"admin","系统管理员"},{"test","测试用户"}]}
     */
	public void jsonPage() {

		if(queryClause == null || queryClause.equals("")){
			Struts2Util.responseText("'totalCount':0,'topics':[]}");
		}else{
		
			Role role = (Role)baseService.findUniqueByHQL("from Role where "+queryClause);
	
			JSONArray topics = new JSONArray();
	
			JSONObject topic = new JSONObject();
			
			Set<Privilege> privileges = role.getPrivileges();
			Iterator<Privilege> it = privileges.iterator();
			
			topic.put("roleUid", role.getUid());
			while(it.hasNext()){
				Privilege privilege = it.next();
				
				topic.put("privilegeUid", privilege.getUid());
				topic.put("name", privilege.getName());
				topic.put("operate", privilege.getOperate());
				topic.put("className", privilege.getClassName());
				
				topics.add(topic);
			}
				
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("totalCount", privileges.size());
			jsonObject.put("topics", topics);
			
			String str_json = jsonObject.toString();
			
			Struts2Util.responseText(str_json);
	
		}
	}
	/**
	 * 加载表单数据
	 * @return json格式数据。
	 *     例：{success:true,info:["userId":"admin","userName":"系统管理员"]}
	 */
	public void find(){
		
		User user = (User)(ActionContext.getContext().getSession().get("curUser"));
		
		if(!userService.hasRight(user, "info", entityName)){
			String str_json = "{'success':false,'info': {'msg':'当前用户没有查看权限'}}";
			Struts2Util.responseText(str_json);
			return;
		}		
		JSONObject o_whereClause = JSONObject.fromObject(whereClause);
		
		int roleUid = o_whereClause.getInt("roleUid");
		int privilegeUid = o_whereClause.getInt("privilegeUid");
		
		JSONObject info = new JSONObject();
		
		info.put("roleUid", roleUid);
		info.put("privilegeUid", privilegeUid);
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(info);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("info",jsonArray );
		
		String str_json = jsonObject.toString();
		
		Struts2Util.responseText(str_json);
	}
	
	public void delete() throws Exception {
		
		User user = (User)(ActionContext.getContext().getSession().get("curUser"));
		
		if(!userService.hasRight(user, "delete", entityName)){
			String str_json = "{'success':false,'msg':'当前用户没有删除权限'}";
			Struts2Util.responseText(str_json);
			return;
		}		
		JSONObject o_whereClause = JSONObject.fromObject(jsonObject);
		
		int roleUid = o_whereClause.getInt("roleUid");
		int privilegeUid = o_whereClause.getInt("privilegeUid");
		
		Role role = (Role)baseService.findById(Role.class, roleUid);
		
		Set<Privilege> privileges = role.getPrivileges();
		
		Iterator<Privilege> it = privileges.iterator();
		while(it.hasNext()){
			Privilege privilege = it.next();
			
			if(privilege.getUid() == privilegeUid){
				privileges.remove(privilege);
				break;
			}
		}
		
		baseService.update(role,(User)(ActionContext.getContext().getSession().get("curUser")),ServletActionContext.getRequest().getRemoteAddr(),"json:"+jsonObject);
		
		String str_json = "{'success':true,'msg':'删除成功'}";
		Struts2Util.responseText(str_json);
	}
	
	public void save() throws Exception {
		
		JSONObject jb = JSONObject.fromObject(jsonObject);
		
		int roleUid = jb.getInt("roleUid");
		int privilegeUid = jb.getInt("privilegeUid");
		
		Role role = (Role)baseService.findById(Role.class, roleUid);
		
		Set<Privilege> privileges = role.getPrivileges();
		
		Iterator<Privilege> it = privileges.iterator();
		
		Privilege privilege = null;
		
		while(it.hasNext()){
			Privilege t_privilege = it.next();
			
			if(t_privilege.getUid() == privilegeUid){
				privilege = t_privilege;
			}
		}
		
		if(privilege == null){
			privilege = new Privilege();
			privilege.setUid(privilegeUid);
			
			privileges.add(privilege);
		}
		
		// 保存对象
		baseService.add(role,(User)(ActionContext.getContext().getSession().get("curUser")),ServletActionContext.getRequest().getRemoteAddr(),"json:"+jsonObject);
		
		String str_json = "{'success':true,'msg':'保存成功'}";
		
		Struts2Util.responseText(str_json);
	}
}

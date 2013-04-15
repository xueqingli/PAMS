package com.lxq.platform.userManage.action;

import java.util.Iterator;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.lxq.platform.action.BaseAction;
import com.lxq.platform.userManage.pojo.Group;
import com.lxq.platform.userManage.pojo.Role;
import com.lxq.platform.userManage.pojo.User;
import com.lxq.platform.util.Struts2Util;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class GroupRoleAction extends BaseAction {
	
	/**
     * 获取json格式的分页数据
     * @return json格式的分页数据。
     *     例：{callback:"callback1001",totalCount:2,topics:[{"admin","系统管理员"},{"test","测试用户"}]}
     */
	public void jsonPage() {

		if(queryClause == null || queryClause.equals("")){
			Struts2Util.responseText("'totalCount':0,'topics':[]}");
		}else{
		
			Group group = (Group) baseService.findUniqueByHQL("from Group where "+queryClause);
	
			JSONArray topics = new JSONArray();
			
			JSONObject topic = new JSONObject();
			
			Set<Role> roles = group.getRoles();
			Iterator<Role> it = roles.iterator();
			
			topic.put("groupUid", group.getUid());
			while(it.hasNext()){
				Role role = it.next();
				
				topic.put("roleUid", role.getUid());
				topic.put("roleId", role.getRoleId());
				topic.put("roleName", role.getRoleName());
				topic.put("valid", role.getStatus().getValue());
				
				topics.add(topic);
			}
				
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("totalCount", roles.size());
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
	public void find() {
		
		User user = (User)(ActionContext.getContext().getSession().get("curUser"));
		
		if(!userService.hasRight(user, "info", entityName)){
			String str_json = "{'success':false,'info': {'msg':'当前用户没有查看权限'}}";
			Struts2Util.responseText(str_json);
			return;
		}
		
		JSONObject o_whereClause = JSONObject.fromObject(whereClause);
		
		int groupUid = o_whereClause.getInt("groupUid");
		int roleUid = o_whereClause.getInt("roleUid");
		
		JSONObject info = new JSONObject();
		
		info.put("groupUid", groupUid);
		info.put("roleUid", roleUid);
		
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
		
		int groupUid = o_whereClause.getInt("groupUid");
		int roleUid = o_whereClause.getInt("roleUid");
		
		Group group = (Group)baseService.findById(Group.class, groupUid);
		
		Set<Role> roles = group.getRoles();
		
		Iterator<Role> it = roles.iterator();
		while(it.hasNext()){
			Role role = it.next();
			
			if(role.getUid() == roleUid){
				roles.remove(role);
				break;
			}
		}
		
		baseService.update(group,(User)(ActionContext.getContext().getSession().get("curUser")),ServletActionContext.getRequest().getRemoteAddr(),"json:"+jsonObject);
		
		String str_json = "{'success':true,'msg':'删除成功'}";
		Struts2Util.responseText(str_json);
	}
	
	public void save() throws Exception {
		
		JSONObject jb = JSONObject.fromObject(jsonObject);
		
		int groupUid = jb.getInt("groupUid");
		int roleUid = jb.getInt("roleUid");
		
		Group group = (Group)baseService.findById(Group.class, groupUid);
		
		Set<Role> roles = group.getRoles();
		
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
		baseService.add(group,(User)(ActionContext.getContext().getSession().get("curUser")),ServletActionContext.getRequest().getRemoteAddr(),"json:"+jsonObject);
		
		String str_json = "{'success':true,'msg':'保存成功'}";
		
		Struts2Util.responseText(str_json);
	}
}

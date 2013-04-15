package com.lxq.platform.userManage.action;

import java.util.Iterator;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.lxq.platform.action.BaseAction;
import com.lxq.platform.userManage.pojo.Group;
import com.lxq.platform.userManage.pojo.User;
import com.lxq.platform.util.Struts2Util;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class GroupUserAction extends BaseAction {
	
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
			
			Set<User> users = group.getUsers();
			Iterator<User> it = users.iterator();
			
			topic.put("groupUid", group.getUid());
			while(it.hasNext()){
				User user = it.next();
				
				topic.put("userUid", user.getUid());
				topic.put("userId", user.getUserId());
				topic.put("userName", user.getUserName());
				topic.put("valid", user.getStatus().getValue());
				
				topics.add(topic);
			}
				
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("totalCount", users.size());
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
		
		int groupUid = o_whereClause.getInt("groupUid");
		int userUid = o_whereClause.getInt("userUid");
		
		JSONObject info = new JSONObject();
		
		info.put("groupUid", groupUid);
		info.put("userUid", userUid);
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(info);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("info",jsonArray );
		
		String str_json = jsonObject.toString();
		
		Struts2Util.responseText(str_json);
	}
	
	public void delete() throws Exception{
		
		User curUser = (User)(ActionContext.getContext().getSession().get("curUser"));
		
		if(!userService.hasRight(curUser, "delete", entityName)){
			String str_json = "{'success':false,'msg':'当前用户没有删除权限'}";
			Struts2Util.responseText(str_json);
			return;
		}		
		JSONObject o_whereClause = JSONObject.fromObject(jsonObject);
		
		int groupUid = o_whereClause.getInt("groupUid");
		int userUid = o_whereClause.getInt("userUid");
		
		Group group = (Group)baseService.findById(Group.class, groupUid);
		
		Set<User> users = group.getUsers();
		
		Iterator<User> it = users.iterator();
		while(it.hasNext()){
			User user = it.next();
			
			if(user.getUid() == userUid){
				users.remove(user);
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
		int userUid = jb.getInt("userUid");
		
		Group group = (Group)baseService.findById(Group.class, groupUid);
		
		Set<User> users = group.getUsers();
		
		Iterator<User> it = users.iterator();
		
		User user = null;
		
		while(it.hasNext()){
			User t_user = it.next();
			
			if(t_user.getUid() == userUid){
				user = t_user;
			}
		}
		
		if(user == null){
			user = new User();
			user.setUid(userUid);
			
			users.add(user);
		}
		
		// 保存对象
		baseService.add(group,(User)(ActionContext.getContext().getSession().get("curUser")),ServletActionContext.getRequest().getRemoteAddr(),"json:"+jsonObject);
		
		String str_json = "{'success':true,'msg':'保存成功'}";
		
		Struts2Util.responseText(str_json);
	}
}

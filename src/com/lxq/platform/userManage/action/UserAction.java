package com.lxq.platform.userManage.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.lxq.platform.action.BaseAction;
import com.lxq.platform.userManage.pojo.User;
import com.lxq.platform.util.DataConvert;
import com.lxq.platform.util.MD5;
import com.lxq.platform.util.Struts2Util;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class UserAction extends BaseAction {
	
	/**
     * 获取json格式的分页数据
     * @return json格式的分页数据。
     *     例：{callback:"callback1001",totalCount:2,topics:[{"admin","系统管理员"},{"test","测试用户"}]}
     */
	public void jsonPage() {


		List<Map<String,String>> header = getHeader(fields);
		String queryString = getQueryString(header);
		
		if(queryClause == null || queryClause.equals("")){
			queryClause = "1=1";
		}

		if(nodeId == null || nodeId.equals("")){
			nodeId = "1";
		}

		String hql =" from "+entityName+" where belongDept.uid="+nodeId+" and "+queryClause;
		int totalCount = baseService.getCount("select count(*)"+hql);
		List<?> objects = baseService.findByHQL("select "+queryString+hql+" order by "+sort+" "+dir, start, limit);

		JSONArray topics = new JSONArray();

		for(int i = 0 ; i < objects.size() ; i++){
			Object[] object = (Object[]) objects.get(i);
			
			JSONObject topic = new JSONObject();
			
			for(int j = 0 ; j < header.size() ; j ++){
				topic.put(header.get(j).get("name"), DataConvert.toString(object[j]));
			}
			
			topics.add(topic);
			
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("totalCount", totalCount);
		jsonObject.put("topics", topics);
		
		String str_json = jsonObject.toString();
		
		Struts2Util.responseText(str_json);
	}
	
    /**
     * 修改密码
     * @throws Exception 
     */
	public void modifyPass() throws Exception {
		
		//获取当前登陆用户
		User curUser = (User) ActionContext.getContext().getSession().get("curUser");
	
		curUser = userService.findUserByUserId(curUser.getUserId());
		
		String old_md5_pass = curUser.getPassword();

		JSONObject json_pass = JSONObject.fromObject(jsonObject);
		
		String new_pass = json_pass.getString("newPass");
	
		if(new_pass == null || new_pass.length()==0){
			Struts2Util.responseText("{'success':false,'msg':'密码不能为空'}");
			return;
		}
		String new_md5_pass = MD5.encode(new_pass);
		curUser.setPassword(new_md5_pass);
		// 保存对象
		baseService.update(curUser,curUser,ServletActionContext.getRequest().getRemoteAddr(),"json:{'原密码'"+old_md5_pass+":'新密码':"+new_md5_pass+"}");
		Struts2Util.responseText("{'success':true,'msg':'密码修改成功'}");
	}

	/**
	 * 重置用户密码
	 * @throws Exception 
	 */
	public void resetPass() throws Exception {
		
		User curUser = (User)(ActionContext.getContext().getSession().get("curUser"));
		
		if(!userService.hasRight(curUser, "update", User.class.getName())){
			String str_json = "{'success':false,'msg': '当前用户没有重置密码权限'}";
			Struts2Util.responseText(str_json);
			return;
		}
		
		JSONObject json_pass = JSONObject.fromObject(jsonObject);
		
		String user_uid = json_pass.getString("uid");
		
		User user = (User) baseService.findById(User.class, Integer.parseInt(user_uid));
		
		String old_md5_pass = user.getPassword();
		String new_md5_pass = "ef73781effc5774100f87fe2f437a435";//原始密码1234abcd
		
		user.setPassword(new_md5_pass);
		
		// 保存对象
		baseService.update(user,(User)(ActionContext.getContext().getSession().get("curUser")),ServletActionContext.getRequest().getRemoteAddr(),"json:{'原密码'"+old_md5_pass+":'新密码':"+new_md5_pass+"}");
		
		Struts2Util.responseText("{'success':true,'msg':'密码重置成功'}");
	}
	
	/**
	 * 新增用户
	 * @return
	 * @throws Exception 
	 * @throws IOException response获取输出流失败
	 */
	public void save() throws Exception {
		User operateUser = (User)(ActionContext.getContext().getSession().get("curUser"));
		
		JSONObject jb = JSONObject.fromObject(jsonObject);
		
		String keyValue = (String)jb.get(keyName);

		if(keyValue == null){//实体不存在，则新增
			
			User user = (User) JSONObject.toBean(jb, Class.forName(entityName));
			
			user.setPassword("ef73781effc5774100f87fe2f437a435");

			// 保存对象
			baseService.add(user,operateUser,ServletActionContext.getRequest().getRemoteAddr(),"json:"+jsonObject);
			
		}
		
		String str_json = "{'success':true,'msg':'保存成功'}";
		
		Struts2Util.responseText(str_json);
	}
}

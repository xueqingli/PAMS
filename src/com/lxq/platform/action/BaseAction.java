package com.lxq.platform.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;

import com.lxq.platform.service.intef.IBaseService;
import com.lxq.platform.userManage.pojo.User;
import com.lxq.platform.userManage.service.intef.IUserService;
import com.lxq.platform.util.DataConvert;
import com.lxq.platform.util.DateUtil;
import com.lxq.platform.util.ExcelExport;
import com.lxq.platform.util.ReflectUtil;
import com.lxq.platform.util.Struts2Util;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BaseAction extends ActionSupport{
	
    /**排序方式*/
    protected String dir;
    /**最大记录数*/
    protected int limit;
    /**排序字段*/
    protected String sort;
    /**记录开始索引位置*/
    protected int start;
    /**查询实体类名称*/
    protected String entityName ;
    /**实体对象的唯一标示属性*/
    protected String keyName ;
    /**查询列表头*/
    protected String fields ;
    /**表单加载数据时的查询条件*/
    protected String whereClause;
    /**查询框提交的查询条件*/
    protected String queryClause;
    /**导出文件名*/
    protected String fileName; 
    /**json格式的实体对象*/
    protected String jsonObject; 
    /**业务处理*/
    protected IBaseService baseService;
    /**用户处理类*/
    protected IUserService userService;
    /**树形菜单节点编号*/
    protected String nodeId;
	
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
		
		String hql = " from "+entityName+" where "+queryClause+" ";
		
		int totalCount = baseService.getCount("select count(*)"+hql);
		
		List<?> objects = baseService.findByHQL("select "+queryString+hql+"order by "+sort+" "+dir, start, limit);

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
	 * 导出数据文件
	 * @return excel文件输出流
	 * @throws IOException reponse获取输出流
	 */
	public void export() throws IOException {
		
		List<Map<String,String>> header = getHeader(fields);
		String queryString = getQueryString(header);
		
 		List<?> objects = null;
		if(queryClause == null || queryClause.equals("")){
			queryClause = "1=1";
		}
		objects = baseService.findByHQL("select "+queryString+" from "+entityName+" where "+queryClause+" order by "+sort+" "+dir);
		
		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
		
		for(int i = 0 ; i < objects.size() ; i++){
			Object[] object = (Object[]) objects.get(i);
			Map<String,String> data_column = new HashMap<String,String>();
			
			for(int j = 0 ; j < header.size() ; j ++){

				data_column.put((String)header.get(j).get("name"), DataConvert.toString(object[j]));
			}
			
			data.add(data_column);
			
		}

		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("application/octet-stream;charset=UTF-8");
		
		response.reset(); //清空输出流
		fileName = new String(fileName.getBytes(), "ISO-8859-1")+DateUtil.getToday("yyyyMMdd");
		response.setHeader("Content-disposition", "attachment; filename="+fileName+".xls");  
		
		OutputStream out = response.getOutputStream();
		ExcelExport.export(header,data,out);
		out.flush();
		out.close();
		
	}
	
	/**
	 * 删除json格式的实体对象
	 * @throws Exception 
	 */
	public void delete() throws Exception{
		
		User user = (User)(ActionContext.getContext().getSession().get("curUser"));
		
		JSONObject jb = JSONObject.fromObject(jsonObject);
		
		int keyValue = jb.getInt(keyName);
		
		Object object = baseService.findById(Class.forName(entityName), keyValue);
		
		//删除用户
		baseService.delete(object,user,ServletActionContext.getRequest().getRemoteAddr(),"json:"+jsonObject);
		
		String str_json = "{'success':true,'msg':'删除成功'}";
		Struts2Util.responseText(str_json);
	}
	
	/**
	 * 加载表单数据
	 * @return json格式数据。
	 *     例：{success:true,info:["userId":"admin","userName":"系统管理员"]}
	 */
	public void find() {
		
		List<Map<String,String>> header = getHeader(fields);
		String queryString = getQueryString(header);
		
		whereClause = getWhereClause(whereClause);
		
		Object[] object = (Object[])baseService.findUniqueByHQL("select "+queryString+" from "+entityName+" where "+whereClause);
		
		JSONObject info = new JSONObject();
		for(int i = 0 ; i < header.size() ; i ++){
			Map<String,String> field = header.get(i);
			
			String name = field.get("name");
			info.put(name, object[i]);
		}
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(info);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("info",jsonArray );
		
		String str_json = jsonObject.toString();
		
		Struts2Util.responseText(str_json);
	}

	/**
	 * 保存json格式的实体对象
	 * @return
	 * @throws Exception 
	 * @throws IOException response获取输出流失败
	 */
	public void save() throws Exception {
		User user = (User)(ActionContext.getContext().getSession().get("curUser"));
		
		JSONObject jb = JSONObject.fromObject(jsonObject);
		
		Object entity = null;
		
		String keyValue = (String)jb.get(keyName);

		if(keyValue == null){//实体不存在，则新增
			
			entity = JSONObject.toBean(jb, Class.forName(entityName));

			// 保存对象
			baseService.add(entity,user,ServletActionContext.getRequest().getRemoteAddr(),"json:"+jsonObject);
		}
		
		String str_json = "{'success':true,'msg':'保存成功'}";
		
		Struts2Util.responseText(str_json);
	}
	
	/**
	 * 更新json格式的实体对象
	 * @return
	 * @throws Exception 
	 * @throws IOException response获取输出流失败
	 */
	public void update() throws Exception {
		User user = (User)(ActionContext.getContext().getSession().get("curUser"));
		
		JSONObject jb = JSONObject.fromObject(jsonObject);
		
		Object entity = null;
		
		String keyValue = (String)jb.get(keyName);

		if(keyValue != null){
			
			entity = baseService.findById(Class.forName(entityName), Integer.parseInt(keyValue));
			
			Iterator<?> keys = jb.keys();//获取json对象的keys
			
			StringBuffer message = new StringBuffer();
			
			while(keys.hasNext()){
				
				String key = (String)keys.next();//获取json对象属性名称
				
				Object oldObject = ReflectUtil.getPropertyValue(Class.forName(entityName),entity,key);
				
				Object value = jb.get(key);//获取json对象属性
				
				if(value.toString().startsWith("{")){//如果属性值是json对象格式，则解析成对象
					
					Class<?> fieldType = ReflectUtil.getPropertyType(Class.forName(entityName),key);
					value = JSONObject.toBean((JSONObject)value,fieldType);
					BeanUtils.setProperty(entity, key, value);
			
					try{//获取getUid方法返回值
						message.append(key+".uid:"+ReflectUtil.getPropertyValue(fieldType,oldObject,"uid").toString());
						message.append("->"+ReflectUtil.getPropertyValue(fieldType,value,"uid").toString()+",");
						
					}catch(NoSuchMethodException e){//获取getId方法返回值
						message.append(key+".id:"+ReflectUtil.getPropertyValue(fieldType,oldObject,"id").toString());
						message.append("->"+ReflectUtil.getPropertyValue(fieldType,value,"id").toString()+",");
					}
					
				}else{
					message.append(key+":"+oldObject==null?oldObject.toString():"");
					message.append("->"+value.toString()+",");
				}
				//替换属性
				BeanUtils.setProperty(entity, key, value);
			}
			// 更新对象
			baseService.update(entity,user,ServletActionContext.getRequest().getRemoteAddr(),"json:{"+message+"}");
		}
		
		String str_json = "{'success':true,'msg':'保存成功'}";
		
		Struts2Util.responseText(str_json);
	}
	
	/**
	 * 获取json格式表头信息，供导出数据和加载表单数据时，使用
	 * @param fields json格式的属性集合
	 * @return 表头信息。
	 *     例：{{"name":"userId","field":"userId","header":"用户编号"},{"name":"userName","field":"userName","header":"用户名称"}}
	 */
	public List<Map<String,String>> getHeader(String fields){
		
		JSONArray headers_Array = JSONArray.fromObject(fields);
		
		List<Map<String,String>> title = new ArrayList<Map<String,String>>();
		
		for(int i = 0 ; i < headers_Array.size() ; i ++){
			Map<String,String> header_column = new HashMap<String,String>();
			
			JSONObject m =  (JSONObject) headers_Array.get(i);
			
			String name = m.optString("name");
			String field = m.optString("field");
			String header = m.optString("header");
			
			header_column.put("name", name);
			header_column.put("field", field);
			header_column.put("header", header);
			
			title.add(header_column);
		}
		return title;
	}
	
	/**
	 * 组装hql查询条件
	 * @param header json格式的表头信息
	 * @return hql查询条件。例："userId,userName"
	 */
	public String getQueryString(List<Map<String,String>> header){
		String queryString = "";
		
		for(int i = 0 ; i < header.size() ; i ++){
			Map<String,String> m =  header.get(i);
			
			queryString = queryString+","+m.get("field");
		}
		queryString = queryString.substring(1);	
		
		return queryString;
	}

	
	/**
	 * 
	 * @param json_whereClause hql语句的where条件，例：{userId:'test',userName:'测试用户'}
	 * @return where条件，例："1=1 and userId='test' and userName='测试用户'"
	 */
	public String getWhereClause(String json_whereClause){
		String whereClause = "1=1";
		
		JSONObject o_whereClause = JSONObject.fromObject(json_whereClause);
		
		Iterator<?> it = o_whereClause.keys();
		while(it.hasNext()){
			String key = (String)it.next();
			String value = o_whereClause.getString(key);
			
			whereClause = whereClause + " and "+key+"="+value;
		}
		return whereClause;
	}
	
	public void setDir(String dir) {
		this.dir = dir;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public void setJsonObject(String jsonObject) {
		this.jsonObject = jsonObject;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public void setQueryClause(String queryClause) {
		this.queryClause = queryClause;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}

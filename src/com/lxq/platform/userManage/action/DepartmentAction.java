package com.lxq.platform.userManage.action;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.lxq.platform.action.BaseAction;
import com.lxq.platform.userManage.pojo.Department;
import com.lxq.platform.userManage.pojo.TreeNode;
import com.lxq.platform.util.Struts2Util;

@SuppressWarnings("serial")
public class DepartmentAction extends BaseAction {
	
	/**机构主键*/
	private String uid;
	
	/**机构树节点链接*/
	private String link;
	
	
	/**
     * 获取json格式的分页数据
     * @return json格式的分页数据。
     *     例：{callback:"callback1001",totalCount:2,topics:[{"admin","系统管理员"},{"test","测试用户"}]}
     */
	public void jsonPage() {

		if(queryClause == null || queryClause.equals("")){
			queryClause = "1=1";
		}
		
		if(nodeId == null || nodeId.equals("")){
			nodeId = "1";
		}

		String hql = " from Department where parentDept.uid="+nodeId+" and "+queryClause;
		int totalCount = baseService.getCount("select count(*) "+hql);
		List<?> objects = null;
		if(totalCount == 0){
			hql = " from Department where uid="+nodeId+" and "+queryClause+" ";
			totalCount = baseService.getCount("select count(*)"+hql);
			objects = baseService.findByHQL(hql+"order by "+sort+" "+dir, start, limit);
		}else{
			objects = baseService.findByHQL(hql+" order by "+sort+" "+dir, start, limit);
		}

		JSONArray topics = new JSONArray();

		for(int i = 0 ; i < objects.size() ; i++){
			Department dept = (Department) objects.get(i);
			
			JSONObject topic = new JSONObject();
			
			topic.put("uid", dept.getUid());
			topic.put("deptId", dept.getDeptId());
			topic.put("deptName", dept.getDeptName());
			topic.put("level", dept.getLevel().getText());
			topic.put("directLeader", dept.getDirectLeader()==null?"":dept.getDirectLeader().getUserName());
			topic.put("status", dept.getStatus().getText());
			
			topics.add(topic);
			
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("totalCount", totalCount);
		jsonObject.put("topics", topics);
		
		String str_json = jsonObject.toString();
		
		Struts2Util.responseText(str_json);
	}

	/**
	 * 根据机构主键获取机构名称
	 * @return
	 */
	public String getNodeText() {
		
		String nodeText = "";
		//如果父机构uid不存在，或者root，则查找根机构，否则查找下级机构
		if(uid != null && !uid.equals("")){
			Department dept = (Department) baseService.findById(Department.class, uid);
			nodeText = dept.getDeptName();
		}
		
		Struts2Util.responseText(nodeText);
		
		return null;
	}
	
	/**
	 * 获取父机构的子机构
	 * @return
	 */
	public void getChildNodes() {
		
		TreeNode[] array_childDepts = null;

		//如果父机构uid不存在，或者root，则查找根机构，否则查找下级机构
		if(uid == null || uid.equals("root")){
			List<?> l_depts = baseService.findByHQL("from Department where parentDept is null");
			
			array_childDepts = new TreeNode[l_depts.size()];
			
			for(int i = 0 ; i < l_depts.size() ; i ++){
				array_childDepts[i] = getTreeNode((Department)l_depts.get(i));
			}
			
			
		}else{
			
			Department dept = (Department) baseService.findById(Department.class, Integer.parseInt(uid));

			Set<Department> s_depts = dept.getChildDepts();
			
			array_childDepts = new TreeNode[s_depts.size()];
			
			Iterator<Department> it = s_depts.iterator();

			int i = 0;
			while(it.hasNext()){
				array_childDepts[i++] = getTreeNode(it.next());
			}
		}
		
		JsonConfig config = new JsonConfig();
		
		config.setExcludes(new String[]{"children"});
		
		JSONArray jsonArray = JSONArray.fromObject(array_childDepts,config);
		
		String str_json = jsonArray.toString();
		
		Struts2Util.responseText(str_json);
	}

	/**
	 * 获取所有机构
	 * @return
	 */
	public void getAllNodes() {
		TreeNode[] array_childDepts = null;
		
		List<?> l_depts = baseService.findByHQL("from Department where parentDept is null");
		
		array_childDepts = new TreeNode[l_depts.size()];
		
		for(int i = 0 ; i < l_depts.size() ; i ++){
			
			Department child = (Department)l_depts.get(i);
			
			array_childDepts[i] = getTreeNode(child);
			
			array_childDepts[i].setChildren(getChildNodes(child));
			
			
		}		
		
		JSONArray jsonArray = JSONArray.fromObject(array_childDepts);
		
		String str_json = jsonArray.toString();
		
		Struts2Util.responseText(str_json);
	}
	
	/**
	 * 递归获取父机构下所有的机构，包括子机构、子子机构、等
	 * @return ext树节点集合
	 */
	public TreeNode[] getChildNodes(Department parentDept){
		
		Set<Department> childDepts = parentDept.getChildDepts();
		
		if(childDepts.size() == 0){
			return null;
		}
		
		TreeNode[] array_childDepts = new TreeNode[childDepts.size()];
		Iterator<Department> it = childDepts.iterator();

		int i = 0;
		while(it.hasNext()){
			
			Department child = it.next();
			TreeNode treeNode = getTreeNode(child);
			
			if(child.getChildDepts().size() != 0){
				treeNode.setChildren(getChildNodes(child));
			}
			
			array_childDepts[i++] = treeNode;
		}
		
		return array_childDepts;
	}
	
	public TreeNode getTreeNode(Department dept){
		
		TreeNode treeNode = new TreeNode();
		
		treeNode.setId(String.valueOf(dept.getUid()));
		treeNode.setText(dept.getDeptName());
		treeNode.setCls(dept.getChildDepts().size() == 0 ? "file":"folder");
		treeNode.setLeaf(dept.getChildDepts().size() == 0 ? true:false);
		treeNode.setUrl(link);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("level", dept.getLevel().getValue());
		
		treeNode.setJsonObject(jsonObject.toString());
		
		return treeNode;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}

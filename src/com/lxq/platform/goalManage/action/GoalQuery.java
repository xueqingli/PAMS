package com.lxq.platform.goalManage.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lxq.platform.action.BaseAction;
import com.lxq.platform.userManage.pojo.TreeNode;
import com.lxq.platform.util.DataConvert;
import com.lxq.platform.util.Struts2Util;

@SuppressWarnings("serial")
public class GoalQuery extends BaseAction {
	
	/**目标类型 dept:机构目标,person:个人目标*/
	private String goalType;
	
	public void getAllNodes() {
		
		TreeNode[] treeNodes = new TreeNode[2];
		treeNodes[0] = new TreeNode();
		treeNodes[1] = new TreeNode();
		
		treeNodes[0].setId("item1");
		treeNodes[0].setText("部门目标查询");
		treeNodes[0].setCls("file");
		treeNodes[0].setLeaf(true);
		treeNodes[0].setUrl("/query/deptGoalQuery.jsp");
		
		treeNodes[0].setId("item2");
		treeNodes[1].setText("个人目标查询");
		treeNodes[1].setCls("file");
		treeNodes[1].setLeaf(true);
		treeNodes[1].setUrl("/query/personGoalQuery.jsp");
		
		JSONArray jsonArray = JSONArray.fromObject(treeNodes);
		
		String str_json = jsonArray.toString();
		
		Struts2Util.responseText(str_json);
	}
	
	public void jsonPage() {

		List<Map<String,String>> header = getHeader(fields);
		String queryString = getQueryString(header);
		
		if(queryClause == null || queryClause.equals("")){
			queryClause = "1=2";
		}
		String hql = " from PerformanceGoal where "+queryClause+" and goalType.value='"+goalType+"'";
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

	public String getGoalType() {
		return goalType;
	}

	public void setGoalType(String goalType) {
		this.goalType = goalType;
	}
}

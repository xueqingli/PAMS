package com.lxq.platform.goalManage.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lxq.platform.action.BaseAction;
import com.lxq.platform.userManage.pojo.User;
import com.lxq.platform.util.DataConvert;
import com.lxq.platform.util.Struts2Util;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class GoalApplierAction extends BaseAction {
	
	/**申请类型*/
	private String applyType;

	/**目标类型*/
	private String goalType;
	
	/**审批状态*/
	private String finishStatus;
	
	  /**
     * 获取json格式的分页数据
     * @return json格式的分页数据。
     *     例：{callback:"callback1001",totalCount:2,topics:[{"admin","系统管理员"},{"test","测试用户"}]}
     */
	public void jsonPage() {
		if(queryClause == null || queryClause.equals("")){
			queryClause = "1=1";
		}

		Map<String, Object> session = ActionContext.getContext().getSession();
		User curUser = (User) session.get("curUser");

		String hql = 
				" from ApprovePostil" +
				" where "+queryClause+//查询条件
				" and (goal.dateBatch.beginStatus=true or goal.dateBatch.endStatus=true)"+
				" and approvePerson.uid = "+curUser.getUid()+//批复人是当前用户
				(finishStatus.equals("yes")?"and approveTime is not null":"and approveTime is null")+//批复时间
				" and applyType.value = '"+applyType+"'"+//申请类型
				" and goal.goalType.value = '"+goalType+"'"+//目标类型
				" group by goal.dateBatch,goal.ownerPerson ";//按期次和执行人分组

		int totalCount = baseService.getCount("select count(*)"+hql);
		
		List<?> objects = baseService.findByHQL("select goal.dateBatch.uid,goal.dateBatch.dateBatch,"+
			" goal.ownerPerson.belongDept.uid,goal.ownerPerson.belongDept.deptName," +
			" goal.ownerPerson.uid,goal.ownerPerson.userName" +
			hql+
			" order by "+sort+" "+dir, start, limit);
		
		
		JSONArray topics = new JSONArray();

		for(int i = 0 ; i < objects.size() ; i++){
			Object[] object = (Object[]) objects.get(i);
			
			JSONObject topic = new JSONObject();
			
			topic.put("dateBatchUid", DataConvert.toString(object[0]));
			topic.put("dateBatch", DataConvert.toString(object[1]));
			topic.put("ownerDeptUid", DataConvert.toString(object[2]));
			topic.put("ownerDeptName", DataConvert.toString(object[3]));
			topic.put("ownerPersonUid", DataConvert.toString(object[4]));
			topic.put("ownerPersonName", DataConvert.toString(object[5]));

			topics.add(topic);
			
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("totalCount", totalCount);
		jsonObject.put("topics", topics);
		
		String str_json = jsonObject.toString();
		
		Struts2Util.responseText(str_json);
		
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getGoalType() {
		return goalType;
	}

	public void setGoalType(String goalType) {
		this.goalType = goalType;
	}

	public String getFinishStatus() {
		return finishStatus;
	}

	public void setFinishStatus(String finishStatus) {
		this.finishStatus = finishStatus;
	}

}

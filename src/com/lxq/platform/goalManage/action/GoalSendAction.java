package com.lxq.platform.goalManage.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.lxq.platform.action.BaseAction;
import com.lxq.platform.goalManage.pojo.PerformanceGoal;
import com.lxq.platform.goalManage.service.intef.IGoalService;
import com.lxq.platform.userManage.pojo.Department;
import com.lxq.platform.userManage.pojo.User;
import com.lxq.platform.util.DataConvert;
import com.lxq.platform.util.DateUtil;
import com.lxq.platform.util.Struts2Util;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class GoalSendAction extends BaseAction {
	
	/**目标服务类*/
	private IGoalService goalService;
	
	/**目标主键*/
	private String uid;
	
	/**目标意见主键*/
	private String opinionUid;
	
	/**申请类型*/
	private String applyType;
	
	/**目标类型*/
	private String goalType;
	
	/**提交动作*/
	private String action;
	
	/**意见*/
	private String opinion;
	
	/**制定申请状态*/
	private String beginStatus;
	
	/**自评申请状态*/
	private String endStatus;
	
	/**审批状态*/
	private String finishStatus;
	
	   /**
     * 获取json格式的分页数据
     * @return json格式的分页数据。
     *     例：{callback:"callback1001",totalCount:2,topics:[{"admin","系统管理员"},{"test","测试用户"}]}
     */
	public void jsonPage() {

		List<Map<String,String>> header = getHeader(fields);
		String queryString = getQueryString(header);
		
		int totalCount = 0;
		JSONArray topics = new JSONArray();
		List<?> objects = null;

		Map<String, Object> session = ActionContext.getContext().getSession();
		User curUser = (User) session.get("curUser");
		
		String hql = null;
		if(finishStatus.equals("no")){//待分发
			hql = 
				" from PerformanceGoal " +
				" where " +
				" createPerson.uid = '"+curUser.getUid()+"' " +//创建人是当前用户
				" and dateBatch.beginStatus=true "+//是期初目标
				" and dateBatch.endStatus=false "+//不是期末目标
				" and goalType.value='"+goalType+"' " +//目标类型
				" and beginStatus is null";//制定状态
		} else {//已分发
			hql = 
				" from PerformanceGoal " +
				" where " +
				" createPerson.uid = '"+curUser.getUid()+"' " +//创建人是当前用户
				" and dateBatch.beginStatus=true "+//是期初目标
				" and dateBatch.endStatus=false "+//不是期末目标
				" and goalType.value='"+goalType+"' " +//目标类型
				" and beginStatus.value='submit'";//制定状态
		}
		totalCount = baseService.getCount("select count(*)"+hql);
		
		objects = baseService.findByHQL("select "+queryString+
				hql+
				" order by "+sort+" "+dir, start, limit);
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
	 * 保存json格式的实体对象
	 * @return
	 * @throws Exception 
	 * @throws IOException response获取输出流失败
	 */
	public void save() throws Exception {
		User curUser = (User)(ActionContext.getContext().getSession().get("curUser"));
		Department curDept = (Department)(ActionContext.getContext().getSession().get("curDept"));
		
		JSONObject jb = JSONObject.fromObject(jsonObject);
		
		String keyValue = (String)jb.get(keyName);

		if(keyValue == null){//实体不存在，则新增
			
			PerformanceGoal goal = (PerformanceGoal) JSONObject.toBean(jb, Class.forName(entityName));
			User user = (User) baseService.findById(User.class,goal.getOwnerPerson().getUid() );

			goal.setCreateDept(curDept);
			goal.setCreatePerson(curUser);
			goal.setCreateTime(DateUtil.getNowTime());
			goal.setOwnerPerson(user);
			goal.setOwnerDept(user.getBelongDept());
			
			// 保存对象
			baseService.add(goal,curUser,ServletActionContext.getRequest().getRemoteAddr(),"json:"+jsonObject);
		}
		
		String str_json = "{'success':true,'msg':'保存成功'}";
		
		Struts2Util.responseText(str_json);
	}
	
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getGoalType() {
		return goalType;
	}

	public void setGoalType(String goalType) {
		this.goalType = goalType;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getOpinionUid() {
		return opinionUid;
	}

	public void setOpinionUid(String opinionUid) {
		this.opinionUid = opinionUid;
	}

	public String getBeginStatus() {
		return beginStatus;
	}

	public void setBeginStatus(String beginStatus) {
		this.beginStatus = beginStatus;
	}

	public String getEndStatus() {
		return endStatus;
	}

	public void setEndStatus(String endStatus) {
		this.endStatus = endStatus;
	}

	public IGoalService getGoalService() {
		return goalService;
	}

	public void setGoalService(IGoalService goalService) {
		this.goalService = goalService;
	}

	public String getFinishStatus() {
		return finishStatus;
	}

	public void setFinishStatus(String finishStatus) {
		this.finishStatus = finishStatus;
	}
	
}

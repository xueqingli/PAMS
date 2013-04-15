package com.lxq.platform.goalManage.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.lxq.platform.action.BaseAction;
import com.lxq.platform.goalManage.pojo.ApprovePostil;
import com.lxq.platform.goalManage.pojo.PerformanceGoal;
import com.lxq.platform.goalManage.service.intef.IGoalService;
import com.lxq.platform.systemManage.pojo.CodeLibrary;
import com.lxq.platform.userManage.pojo.Department;
import com.lxq.platform.userManage.pojo.TreeNode;
import com.lxq.platform.userManage.pojo.User;
import com.lxq.platform.util.DataConvert;
import com.lxq.platform.util.DateUtil;
import com.lxq.platform.util.Struts2Util;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class GoalApproveAction extends BaseAction {
	
	/**目标服务类*/
	private IGoalService goalService;
	
	/**目标主键*/
	private String uid;
	
	/**目标意见主键*/
	private String opinionUid;
	
	/**目标类型*/
	private String goalType;
	
	/**申请类型*/
	private String applyType;
	
	/**提交动作*/
	private String action;
	
	/**意见*/
	private String opinion;
	
	/**批次uid*/
	private String dateBatchUid;

	/**部门uid*/
	private String deptUid;

	/**用户uid*/
	private String userUid;
	
	/**审批状态*/
	private String finishStatus;
	
	/**
	 * 获取批复树
	 * @return
	 */
	public void getApproveNodes() {
		
		TreeNode[] treeNodes = new TreeNode[2];
		treeNodes[0] = new TreeNode();
		treeNodes[1] = new TreeNode();
		
		treeNodes[0].setId(goalType+"_item1");
		treeNodes[0].setText("待处理");
		treeNodes[0].setCls("folder");
		treeNodes[0].setLeaf(false);
		treeNodes[0].setUrl("");
		treeNodes[0].setId(goalType+"_item2");
		treeNodes[1].setText("已完成");
		treeNodes[1].setCls("folder");
		treeNodes[1].setLeaf(false);
		treeNodes[1].setUrl("");
		
		TreeNode[] childTreeNodes1 = new TreeNode[3]; 

		childTreeNodes1[0] = new TreeNode();
		childTreeNodes1[0].setId(goalType+"_non-finish_set-out");
		childTreeNodes1[0].setText("目标制定审核");
		childTreeNodes1[0].setCls("file");
		childTreeNodes1[0].setLeaf(true);
		childTreeNodes1[0].setUrl("/approve/setupGoalApprove.jsp");
		childTreeNodes1[0].setJsonObject("{finishStatus:\"no\",goalType:\""+goalType+"\"}");
		
		childTreeNodes1[1] = new TreeNode();
		childTreeNodes1[1].setId(goalType+"_non-finish_self-eva");
		childTreeNodes1[1].setText("目标自评审核");
		childTreeNodes1[1].setCls("file");
		childTreeNodes1[1].setLeaf(true);
		childTreeNodes1[1].setUrl("/approve/selfEvaGoalApprove.jsp");
		childTreeNodes1[1].setJsonObject("{finishStatus:\"no\",goalType:\""+goalType+"\"}");
		
		//待完成分发的任务
		childTreeNodes1[2] = new TreeNode();
		childTreeNodes1[2].setId(goalType+"_non-finish_send");
		childTreeNodes1[2].setText("待分发任务");
		childTreeNodes1[2].setCls("file");
		childTreeNodes1[2].setLeaf(true);
		childTreeNodes1[2].setUrl("/approve/sendGoal.jsp");
		childTreeNodes1[2].setJsonObject("{finishStatus:\"no\",goalType:\""+goalType+"\"}");
		
		treeNodes[0].setChildren(childTreeNodes1);
		
		TreeNode[] childTreeNodes2 = new TreeNode[3]; 
		
		childTreeNodes2[0] = new TreeNode();
		childTreeNodes2[0].setId(goalType+"_finish_set-out");
		childTreeNodes2[0].setText("目标制定审核");
		childTreeNodes2[0].setCls("file");
		childTreeNodes2[0].setLeaf(true);
		childTreeNodes2[0].setUrl("/approve/setupGoalApprove.jsp");
		childTreeNodes2[0].setJsonObject("{finishStatus:\"yes\",goalType:\""+goalType+"\"}");
		
		childTreeNodes2[1] = new TreeNode();
		childTreeNodes2[1].setId(goalType+"_finish_self-eva");
		childTreeNodes2[1].setText("目标自评审核");
		childTreeNodes2[1].setCls("file");
		childTreeNodes2[1].setLeaf(true);
		childTreeNodes2[1].setUrl("/approve/selfEvaGoalApprove.jsp");
		childTreeNodes2[1].setJsonObject("{finishStatus:\"yes\",goalType:\""+goalType+"\"}");
		
		//已完成分发的任务
		childTreeNodes2[2] = new TreeNode();
		childTreeNodes2[2].setId(goalType+"_finish_send");
		childTreeNodes2[2].setText("已分发任务");
		childTreeNodes2[2].setCls("file");
		childTreeNodes2[2].setLeaf(true);
		childTreeNodes2[2].setUrl("/approve/sendGoal.jsp");
		childTreeNodes2[2].setJsonObject("{finishStatus:\"yes\",goalType:\""+goalType+"\"}");
		
		treeNodes[1].setChildren(childTreeNodes2);

		JSONArray jsonArray = JSONArray.fromObject(treeNodes);
		
		String str_json = jsonArray.toString();
		
		Struts2Util.responseText(str_json);
	}
	
	   /**
  * 获取json格式的分页数据
  * @return json格式的分页数据。
  *     例：{callback:"callback1001",totalCount:2,topics:[{"admin","系统管理员"},{"test","测试用户"}]}
  */
	public void jsonPage() {

		if(queryClause == null){
			queryClause = "1=2";
		}
		
		List<Map<String,String>> header = getHeader(fields);
		String queryString = getQueryString(header);
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		User curUser = (User) session.get("curUser");
		
		JSONArray topics = new JSONArray();
		
		String hql = " from ApprovePostil " +
				" where "+queryClause+//查询条件
				" and (goal.dateBatch.beginStatus=true or goal.dateBatch.endStatus=true)"+
				(finishStatus.equals("yes")?"and approveTime is not null":"and approveTime is null")+//批复时间
				" and applyType.value = '"+applyType+"'"+//申请类型
				" and goal.goalType.value = '"+goalType+"'"+//目标类型
				" and approvePerson.uid = "+curUser.getUid();//批复人是当前用户
		
		int totalCount = baseService.getCount("select count(*) "+hql);
		
		List<?> objects = baseService.findByHQL("select "+queryString+
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
	 * 保存并提交
	 * @throws Exception
	 */
	public void signOpinion() throws Exception{
		
		PerformanceGoal goal = ((PerformanceGoal)baseService.findById(PerformanceGoal.class, Integer.parseInt(uid)));
		
		ApprovePostil approvePostil = (ApprovePostil) baseService.findById(ApprovePostil.class, Integer.parseInt(opinionUid));;
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		Department curDept = (Department) session.get("curDept");
		User curUser = (User) session.get("curUser");
		
		approvePostil.setAction(((CodeLibrary)baseService.findUniqueByHQL("from CodeLibrary where value='"+action+"' and codeCatalog.codeNo='Action'")));
		approvePostil.setApproveTime(DateUtil.getToday());
		approvePostil.setPostil(opinion);
		approvePostil.setScore(0);
		
		Struts2Util.responseText(goalService.signApproveOpinion(goal,approvePostil,curDept,curUser,applyType,action));
	}
	
	/**
	 * 进行任务分发
	 * @throws Exception 
	 */
	public void sendGoal() throws Exception {
		JSONObject json_gaol = JSONObject.fromObject(jsonObject);
		String goal_uid = json_gaol.getString("uid");
		PerformanceGoal performGoal = (PerformanceGoal) baseService.findById(PerformanceGoal.class, Integer.parseInt(goal_uid));
		
		performGoal.setBeginStatus((CodeLibrary) baseService.findUniqueByHQL("from CodeLibrary where codeCatalog.codeNo='ApplyStatus1' and value='submit'"));
		baseService.update(performGoal,(User)(ActionContext.getContext().getSession().get("curUser")),ServletActionContext.getRequest().getRemoteAddr(),"json:"+jsonObject);
		
		String str_json = "{'success':true,'msg':'分发任务成功'}";
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

	public String getOpinionUid() {
		return opinionUid;
	}

	public void setOpinionUid(String opinionUid) {
		this.opinionUid = opinionUid;
	}

	public IGoalService getGoalService() {
		return goalService;
	}

	public void setGoalService(IGoalService goalService) {
		this.goalService = goalService;
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

	public String getDateBatchUid() {
		return dateBatchUid;
	}

	public void setDateBatchUid(String dateBatchUid) {
		this.dateBatchUid = dateBatchUid;
	}

	public String getDeptUid() {
		return deptUid;
	}

	public void setDeptUid(String deptUid) {
		this.deptUid = deptUid;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public String getFinishStatus() {
		return finishStatus;
	}

	public void setFinishStatus(String finishStatus) {
		this.finishStatus = finishStatus;
	}
	
}

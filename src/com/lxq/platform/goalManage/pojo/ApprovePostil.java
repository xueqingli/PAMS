package com.lxq.platform.goalManage.pojo;

import com.lxq.platform.systemManage.pojo.CodeLibrary;
import com.lxq.platform.userManage.pojo.Department;
import com.lxq.platform.userManage.pojo.User;


/**
 * 审批意见实体类
 * @author michael
 *
 */
public class ApprovePostil {
	/**主键*/
	private int uid;
	/**阶段号*/
	private int phaseNo;
	/**所属目标*/
	private PerformanceGoal goal;
	/**申请类型，制定或者自评*/
	private CodeLibrary applyType;
	/**批复动作*/
	private CodeLibrary action;
	/**意见*/
	private String postil;
	/**得分*/
	private int score;
	/**审批机构*/
	private Department approveDept;
	/**审批人*/
	private User approvePerson;
	/**审批时间*/
	private String approveTime;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public PerformanceGoal getGoal() {
		return goal;
	}
	public void setGoal(PerformanceGoal goal) {
		this.goal = goal;
	}
	public String getPostil() {
		return postil;
	}
	public void setPostil(String postil) {
		this.postil = postil;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Department getApproveDept() {
		return approveDept;
	}
	public void setApproveDept(Department approveDept) {
		this.approveDept = approveDept;
	}
	public User getApprovePerson() {
		return approvePerson;
	}
	public void setApprovePerson(User approvePerson) {
		this.approvePerson = approvePerson;
	}
	public String getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}
	public CodeLibrary getApplyType() {
		return applyType;
	}
	public void setApplyType(CodeLibrary applyType) {
		this.applyType = applyType;
	}
	public CodeLibrary getAction() {
		return action;
	}
	public void setAction(CodeLibrary action) {
		this.action = action;
	}
	public int getPhaseNo() {
		return phaseNo;
	}
	public void setPhaseNo(int phaseNo) {
		this.phaseNo = phaseNo;
	}
	
}
package com.lxq.platform.goalManage.pojo;

import com.lxq.platform.systemManage.pojo.CodeLibrary;
import com.lxq.platform.userManage.pojo.Department;
import com.lxq.platform.userManage.pojo.User;

/**
 * 目标实体类
 * @author michael
 *
 */
public class PerformanceGoal {
	/**主键*/
	private int uid;
	/**所属期次*/
	private DateBatch dateBatch;
	/**目标内容*/
	private String content;
	/**完成状态*/
	private CodeLibrary finishStatus;
	/**目标类型*/
	private CodeLibrary goalType;
	/**满分*/
	private int fullScore;
	/**执行部门*/
	private Department ownerDept;
	/**执行人*/
	private User ownerPerson;
	/**协同部门*/
	private String coorDeptsJson;
	/**创建部门*/
	private Department createDept;
	/**创建人*/
	private User createPerson;
	/**创建时间*/
	private String createTime;
	/**最终审批人*/
	private User finalApprover;
	/**最终得分*/
	private int finalScore;
	/**期次初状态*/
	private CodeLibrary beginStatus;
	/**期次末状态*/
	private CodeLibrary endStatus;

	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public DateBatch getDateBatch() {
		return dateBatch;
	}
	public void setDateBatch(DateBatch dateBatch) {
		this.dateBatch = dateBatch;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public CodeLibrary getGoalType() {
		return goalType;
	}
	public void setGoalType(CodeLibrary goalType) {
		this.goalType = goalType;
	}
	public int getFullScore() {
		return fullScore;
	}
	public void setFullScore(int fullScore) {
		this.fullScore = fullScore;
	}
	public Department getOwnerDept() {
		return ownerDept;
	}
	public void setOwnerDept(Department ownerDept) {
		this.ownerDept = ownerDept;
	}
	public User getOwnerPerson() {
		return ownerPerson;
	}
	public void setOwnerPerson(User ownerPerson) {
		this.ownerPerson = ownerPerson;
	}
	public Department getCreateDept() {
		return createDept;
	}
	public void setCreateDept(Department createDept) {
		this.createDept = createDept;
	}
	
	public User getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(User createPerson) {
		this.createPerson = createPerson;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}
	public String getCoorDeptsJson() {
		return coorDeptsJson;
	}
	public void setCoorDeptsJson(String coorDeptsJson) {
		this.coorDeptsJson = coorDeptsJson;
	}
	public CodeLibrary getBeginStatus() {
		return beginStatus;
	}
	public void setBeginStatus(CodeLibrary beginStatus) {
		this.beginStatus = beginStatus;
	}
	public CodeLibrary getEndStatus() {
		return endStatus;
	}
	public void setEndStatus(CodeLibrary endStatus) {
		this.endStatus = endStatus;
	}
	public User getFinalApprover() {
		return finalApprover;
	}
	public void setFinalApprover(User finalApprover) {
		this.finalApprover = finalApprover;
	}
	public CodeLibrary getFinishStatus() {
		return finishStatus;
	}
	public void setFinishStatus(CodeLibrary finishStatus) {
		this.finishStatus = finishStatus;
	}
	
	
}
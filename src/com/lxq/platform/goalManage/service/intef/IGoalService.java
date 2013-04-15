package com.lxq.platform.goalManage.service.intef;

import com.lxq.platform.goalManage.pojo.ApprovePostil;
import com.lxq.platform.goalManage.pojo.PerformanceGoal;
import com.lxq.platform.service.intef.IBaseService;
import com.lxq.platform.userManage.pojo.Department;
import com.lxq.platform.userManage.pojo.User;

public interface IGoalService extends IBaseService {

	public User getFinalApprover(PerformanceGoal goal);
	
	public User getUserByRole (Department dept , String roleId);
	
	public String signApplyOpinion(PerformanceGoal goal,ApprovePostil approvePostil,Department curDept,User curUser,String applyType);
	
	public String signApproveOpinion(PerformanceGoal goal,ApprovePostil approvePostil,Department curDept,User curUser,String applyType,String action);
	
	public String delete(PerformanceGoal goal,int opinionUid);
	
	public String undo(String goalUid,String applyType);
}

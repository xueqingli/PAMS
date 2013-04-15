package com.lxq.platform.goalManage.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.lxq.platform.goalManage.pojo.ApprovePostil;
import com.lxq.platform.goalManage.pojo.PerformanceGoal;
import com.lxq.platform.goalManage.service.intef.IGoalService;
import com.lxq.platform.service.impl.BaseServiceImpl;
import com.lxq.platform.systemManage.pojo.CodeLibrary;
import com.lxq.platform.userManage.pojo.Department;
import com.lxq.platform.userManage.pojo.Role;
import com.lxq.platform.userManage.pojo.User;
import com.lxq.platform.util.DateUtil;
import com.lxq.platform.util.Struts2Util;

public class GoalServiceImpl extends BaseServiceImpl implements IGoalService {
	
	
	public User getFinalApprover(PerformanceGoal goal){
		
//		1	admin	系统管理员	5
//		2	100	局长	5
//		3	101	分管局长	5
//		4	200	部门主任	5
//		5	201	部门副主任	5
//		6	300	管理员	5
//		7	400	班组长	5
//		7	400	班组长	5

		Department dept = goal.getOwnerDept();
		CodeLibrary level = dept.getLevel();
		
		if(level.getValue().equals("1")){
			
			return getUserByRole(dept,"100");
			
		} else if(level.getValue().equals("2")){
			
			return dept.getDirectLeader();
		
		} else if(level.getValue().equals("3")){
		
			return getUserByRole(dept.getParentDept(),"200");
		
		}else{

			return getUserByRole(dept.getParentDept(),"300");
		}
	}
	
	public User getUserByRole (Department dept , String roleId){
		
		List<?> users = baseDAO.findByHQL("from User where belongDept.uid="+dept.getUid());
		
		for(int i = 0 ; i < users.size() ; i ++){
			User user = (User)users.get(i);
			Set<Role> roles = user.getRoles();
			
			Iterator<Role> it = roles.iterator();
			while(it.hasNext()){
				Role t_role = it.next();
				
				if(t_role.getRoleId().equals(roleId)){
					return user;
				}
			}
		}
		return null;
	}

	/**
	 * 保存并提交
	 * @throws Exception
	 */
	public String signApplyOpinion(PerformanceGoal goal,ApprovePostil approvePostil,Department curDept,User curUser,String applyType) {
		
		User finalApprover = this.getFinalApprover(goal);
		
		if(finalApprover == null){
			return "{'success':true,'msg':'终审人不存在'}";
		}
		
		goal.setFinalApprover(finalApprover);//设置终审人
		
		if (applyType.equals("setup")){
			goal.setBeginStatus((CodeLibrary) baseDAO.findUniqueByHQL("from CodeLibrary where codeCatalog.codeNo='ApplyStatus1' and value='approving'"));
		}else{
			goal.setEndStatus((CodeLibrary) baseDAO.findUniqueByHQL("from CodeLibrary where codeCatalog.codeNo='ApplyStatus2' and value='approving'"));
		}
		
		baseDAO.saveOrUpdate(approvePostil);// 保存或更新对象
		
		if (applyType.equals("selfEva") && goal.getCoorDeptsJson() != null && goal.getCoorDeptsJson().length()>0){//自评申请时如果有协同部门
			
			String[] corrDeptUid = goal.getCoorDeptsJson().split(",");
			for(int i = 0 ; i < corrDeptUid.length ; i ++){
				
				Department dept = (Department)baseDAO.findUniqueByHQL("from Department where uid="+corrDeptUid);

				User user = this.getUserByRole(dept, "200");
				
					if(user != null){
						ApprovePostil next_approvePostil = new ApprovePostil();
						next_approvePostil.setAction(approvePostil.getAction());
						next_approvePostil.setApplyType(approvePostil.getApplyType());
						next_approvePostil.setApproveDept(dept);
						next_approvePostil.setApprovePerson(user);
						next_approvePostil.setGoal(goal);
						next_approvePostil.setPhaseNo(2);
						next_approvePostil.setScore(0);
						baseDAO.save(next_approvePostil);
					}
			}
			
		} else{
			ApprovePostil next_approvePostil = new ApprovePostil();
			next_approvePostil.setAction(approvePostil.getAction());
			next_approvePostil.setApplyType(approvePostil.getApplyType());
			next_approvePostil.setApproveDept(finalApprover.getBelongDept());
			next_approvePostil.setApprovePerson(finalApprover);
			next_approvePostil.setGoal(goal);
			next_approvePostil.setPhaseNo(2);
			next_approvePostil.setScore(0);
			baseDAO.save(next_approvePostil);
			
		}
		baseDAO.update(goal);
		
		return "{'success':true,'msg':'提交成功'}";
	
	}
	
	/**
	 * 保存并提交
	 * @throws Exception
	 */
	public String signApproveOpinion(PerformanceGoal goal,ApprovePostil approvePostil,Department curDept,User curUser,String applyType,String action) {
		
		approvePostil.setApproveTime(DateUtil.getNowTime());
		if(action.equals("reject")){
			if(applyType.equals("setup")){
				goal.setBeginStatus((CodeLibrary) baseDAO.findUniqueByHQL("from CodeLibrary where codeCatalog.codeNo='ApplyStatus1' and value='reject'"));
			}else{
				goal.setEndStatus((CodeLibrary) baseDAO.findUniqueByHQL("from CodeLibrary where codeCatalog.codeNo='ApplyStatus2' and value='reject'"));
			}
			
			
		} else if(action.equals("goback")){
			if(applyType.equals("setup")){
				goal.setBeginStatus((CodeLibrary) baseDAO.findUniqueByHQL("from CodeLibrary where codeCatalog.codeNo='ApplyStatus1' and value='goback'"));
			}else{
				goal.setEndStatus((CodeLibrary) baseDAO.findUniqueByHQL("from CodeLibrary where codeCatalog.codeNo='ApplyStatus2' and value='goback'"));
			}
			
			ApprovePostil next_approvePostil = new ApprovePostil();
			next_approvePostil.setAction(approvePostil.getAction());
			next_approvePostil.setApplyType(approvePostil.getApplyType());
			next_approvePostil.setApproveDept(goal.getOwnerDept());
			next_approvePostil.setApprovePerson(goal.getOwnerPerson());
			next_approvePostil.setGoal(goal);
			next_approvePostil.setPhaseNo(approvePostil.getPhaseNo()+1);
			next_approvePostil.setScore(0);
			baseDAO.save(next_approvePostil);
		} else{
			
			User finalApprover = this.getFinalApprover(goal);
			if(curUser.getUid() == finalApprover.getUid()){//当前用户是终审人
				if(applyType.equals("setup")){//制定申请
					goal.setBeginStatus((CodeLibrary) baseDAO.findUniqueByHQL("from CodeLibrary where codeCatalog.codeNo='ApplyStatus1' and value='pass'"));
					goal.setEndStatus((CodeLibrary) baseDAO.findUniqueByHQL("from CodeLibrary where codeCatalog.codeNo='ApplyStatus2' and value='submit'"));
					if(goal.getCoorDeptsJson()!=null && goal.getCoorDeptsJson().length()>0){//如果有协同部门，则分发到协同部门
						
						String[] coorDepts = goal.getCoorDeptsJson().split(",");
						
						for(int i = 0 ; i < coorDepts.length ; i ++){
							
							Department ownerDept = (Department) baseDAO.findById(Department.class, coorDepts[i]);
							
							PerformanceGoal performGoal = new PerformanceGoal();
							
							performGoal.setBeginStatus((CodeLibrary) baseDAO.findUniqueByHQL("from CodeLibrary where codeCatalog.codeNo='ApplyStatus1' and value='submit'"));
							performGoal.setContent(goal.getContent());
							performGoal.setCoorDeptsJson(goal.getCoorDeptsJson());
							performGoal.setCreateDept(curDept);
							performGoal.setCreatePerson(curUser);
							performGoal.setCreateTime(DateUtil.getNowTime());
							performGoal.setDateBatch(goal.getDateBatch());
							performGoal.setFinalApprover(curUser);
							performGoal.setGoalType(goal.getGoalType());
							performGoal.setOwnerDept(ownerDept);
							performGoal.setOwnerPerson(getUserByRole (ownerDept , "200"));
							
							baseDAO.save(performGoal);
							
						}
						
					}
					
				}else{//自评申请
					goal.setEndStatus((CodeLibrary) baseDAO.findUniqueByHQL("from CodeLibrary where codeCatalog.codeNo='ApplyStatus2' and value='pass'"));
				}
			}else{
				
				int sum = baseDAO.getCount("select count(*) from ApprovePostil " +
						" where phaseNo="+approvePostil.getPhaseNo() + " " +
						" and approvePerson.uid !="+curUser.getUid()+
						" and applyType.value ='"+applyType+"'"+
						" and approveTime is null");
				
				if(sum<=0){
					
					ApprovePostil next_approvePostil = new ApprovePostil();
					next_approvePostil.setAction(approvePostil.getAction());
					next_approvePostil.setApplyType(approvePostil.getApplyType());
					next_approvePostil.setApproveDept(goal.getFinalApprover().getBelongDept());
					next_approvePostil.setApprovePerson(goal.getFinalApprover());
					next_approvePostil.setGoal(goal);
					next_approvePostil.setPhaseNo(approvePostil.getPhaseNo()+1);
					next_approvePostil.setScore(0);
					baseDAO.save(next_approvePostil);
				}
				
			}
			
		}		
		
		return "{'success':true,'msg':'提交成功'}";
		
	}
	
	/**
	 * 删除签署的意见和目标记录
	 * @throws Exception 
	 */
	public String delete(PerformanceGoal goal,int opinionUid){
		
		CodeLibrary beginStatus = goal.getBeginStatus();
		CodeLibrary endStatus = goal.getEndStatus();
		
		String beginStatusValue = beginStatus == null ? "":beginStatus.getValue();
		String endStatusValue = endStatus == null ? "":endStatus.getValue();

		//待申请阶段的制定申请，待申请阶段新增的目标申请，可以删除。
		if(beginStatusValue.equals("submit") || (endStatusValue.equals("submit") && beginStatusValue.equals("submit"))){
			ApprovePostil approvePostil = (ApprovePostil) baseDAO.findUniqueByHQL("from ApprovePostil where goal.uid="+opinionUid +" and approveTime is null");
			
			if(approvePostil != null){
				baseDAO.delete(approvePostil);
			}
			
			baseDAO.delete(goal);
			return "{'success':true,'msg':'提交成功'}";
			
		} else{
			return "{'success':false,'msg':'已提交的目标不能删除'}";
		}
	}
	
	/**
	 * 撤销目标提交
	 * @throws Exception 
	 */
	public String undo(String goalUid,String applyType) {
		
		PerformanceGoal goal = (PerformanceGoal) baseDAO.findUniqueByHQL("from PerformanceGoal where uid="+goalUid);
		int count = baseDAO.getCount("select count(*) from ApprovePostil " +
				"where goal.uid="+goalUid + " " +//当前目标
				"and applyType.value='"+applyType+"' " +//申请类型
				"and approveTime is not null " +//批复时间不为空
				"and  approvePerson.uid!="+Struts2Util.getCurUser().getUid() 
				);
		
		if(count > 0){
			return "{'success':false,'msg':'目标已审核，不能撤销'}";
		}else{
			List<?> approvePostils = baseDAO.findByHQL(("from ApprovePostil " +
					"where goal.uid="+goalUid + " " +//当前目标
					"and applyType.value='"+applyType+"' " +//申请类型
					"and  approvePerson.uid!="+Struts2Util.getCurUser().getUid() +
					"order by uid desc"));
			
			for(int i = 0 ; i < approvePostils.size() ; i ++){
				ApprovePostil approvePostil = (ApprovePostil) approvePostils.get(i);
				baseDAO.delete(approvePostil);
			}
			
			ApprovePostil approvePostil = (ApprovePostil) baseDAO.findUniqueByHQL("from ApprovePostil " +
					"where goal.uid="+goalUid + " " +//当前目标
					"and applyType.value='"+applyType+"' " +//申请类型
					"and  approvePerson.uid ="+Struts2Util.getCurUser().getUid()
			);
			
			approvePostil.setApproveTime(null);
			
			baseDAO.update(approvePostil);
			
			if(applyType.equals("setup")){
				goal.setBeginStatus((CodeLibrary) baseDAO.findUniqueByHQL("from CodeLibrary where codeCatalog.codeNo='ApplyStatus1' and value='submit'"));
			}else{
				goal.setEndStatus((CodeLibrary) baseDAO.findUniqueByHQL("from CodeLibrary where codeCatalog.codeNo='ApplyStatus2' and value='submit'"));
			}
			baseDAO.update(goal);
			
			return "{'success':true,'msg':'撤销成功'}";
		}
	}
	
	
}

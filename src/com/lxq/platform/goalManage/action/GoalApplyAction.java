package com.lxq.platform.goalManage.action;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;

import com.lxq.platform.action.BaseAction;
import com.lxq.platform.goalManage.pojo.ApprovePostil;
import com.lxq.platform.goalManage.pojo.DateBatch;
import com.lxq.platform.goalManage.pojo.PerformanceGoal;
import com.lxq.platform.goalManage.service.intef.IGoalService;
import com.lxq.platform.systemManage.pojo.CodeLibrary;
import com.lxq.platform.userManage.pojo.Department;
import com.lxq.platform.userManage.pojo.TreeNode;
import com.lxq.platform.userManage.pojo.User;
import com.lxq.platform.util.DataConvert;
import com.lxq.platform.util.DateUtil;
import com.lxq.platform.util.ReflectUtil;
import com.lxq.platform.util.Struts2Util;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class GoalApplyAction extends BaseAction {
	
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
	
	
	/**
	 * 获取申请树
	 * @return
	 */
	public void getApplyNodes() {
		
		TreeNode[] treeNodes = new TreeNode[2];
		treeNodes[0] = new TreeNode();
		treeNodes[1] = new TreeNode();
		
		treeNodes[0].setId(goalType+"_item1");
		treeNodes[0].setText("目标制定申请");
		treeNodes[0].setCls("folder");
		treeNodes[0].setLeaf(false);
		treeNodes[0].setUrl("");
		treeNodes[0].setId(goalType+"_item2");
		treeNodes[1].setText("目标自评申请");
		treeNodes[1].setCls("folder");
		treeNodes[1].setLeaf(false);
		treeNodes[1].setUrl("");
		
		List<?> codeLibrarys1 = baseService.findByHQL("select value,text from CodeLibrary where codeCatalog.codeNo='ApplyStatus1' order by uid");
		TreeNode[] childTreeNodes1 = new TreeNode[codeLibrarys1.size()];
		for(int i = 0 ; i < codeLibrarys1.size() ; i ++){
			
			Object[] codeLibrary =  (Object[]) codeLibrarys1.get(i);
			
			childTreeNodes1[i] = new TreeNode();
			
			childTreeNodes1[i].setId("set-out_"+String.valueOf(codeLibrary[0]));
			childTreeNodes1[i].setText(codeLibrary[1]+"");
			childTreeNodes1[i].setCls("file");
			childTreeNodes1[i].setLeaf(true);
			childTreeNodes1[i].setUrl("/apply/setupGoalApply.jsp");
			childTreeNodes1[i].setJsonObject("{beginStatus:\""+String.valueOf(codeLibrary[0])+"\",goalType:\""+goalType+"\",applyType:\"setup\"}");
		}
		
		List<?> codeLibrarys2 = baseService.findByHQL("select value,text from CodeLibrary where codeCatalog.codeNo='ApplyStatus2' order by uid");
		TreeNode[] childTreeNodes2 = new TreeNode[codeLibrarys2.size()];
		for(int i = 0 ; i < codeLibrarys2.size() ; i ++){
			
			Object[] codeLibrary =  (Object[]) codeLibrarys2.get(i);
			
			childTreeNodes2[i] = new TreeNode();
			
			childTreeNodes2[i].setId("self-eva_"+String.valueOf(codeLibrary[0]));
			childTreeNodes2[i].setText(codeLibrary[1]+"");
			childTreeNodes2[i].setCls("file");
			childTreeNodes2[i].setLeaf(true);
			childTreeNodes2[i].setUrl("/apply/selfEvaGoalApply.jsp");
			childTreeNodes2[i].setJsonObject("{endStatus:\""+String.valueOf(codeLibrary[0])+"\",goalType:\""+goalType+"\",applyType:\"selfEva\"}");
		}

		treeNodes[0].setChildren(childTreeNodes1);
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

		List<Map<String,String>> header = getHeader(fields);
		String queryString = getQueryString(header);
		
		int totalCount = 0;
		JSONArray topics = new JSONArray();
		List<?> objects = null;

		Map<String, Object> session = ActionContext.getContext().getSession();
		User curUser = (User) session.get("curUser");
		
		if(applyType.equals("setup")){//制定申请
			String hql = 
					" from PerformanceGoal " +
					" where " +
					" ownerPerson.uid = '"+curUser.getUid()+"' " +//执行人是当前用户
					" and dateBatch.beginStatus=true "+//是期初目标
					" and dateBatch.endStatus=false "+//不是期末目标
					" and goalType.value='"+goalType+"' " +//目标类型
					" and beginStatus.value = '"+beginStatus+"'";//制定状态

			totalCount = baseService.getCount("select count(*)"+hql);
			
			objects = baseService.findByHQL("select "+queryString+" from PerformanceGoal " +
					" where " +
					" ownerPerson.uid = '"+curUser.getUid()+"' " +//执行人是当前用户
					" and dateBatch.beginStatus=true "+//是期初目标
					" and dateBatch.endStatus=false "+//不是期末目标
					" and goalType.value='"+goalType+"' " +//目标类型
					" and beginStatus.value = '"+beginStatus+"'"+//制定状态
					" order by "+sort+" "+dir, start, limit);
			
		} else {//自评申请
			String hql = 
					" from PerformanceGoal " +
					" where " +
					" ownerPerson.uid = '"+curUser.getUid()+"' " +//执行人是当前用户
					" and dateBatch.beginStatus=false "+//不是期初目标
					" and dateBatch.endStatus=true "+//是期末目标
					" and goalType.value='"+goalType+"' " +//目标类型
					" and endStatus.value = '"+endStatus+"'";//制定状态
			
			totalCount = baseService.getCount("select count(*)"+hql);
			
			objects = baseService.findByHQL("select "+queryString+" from PerformanceGoal " +
					" where " +
					" ownerPerson.uid = '"+curUser.getUid()+"' " +//执行人是当前用户
					" and dateBatch.beginStatus=false "+//不是期初目标
					" and dateBatch.endStatus=true "+//是期末目标
					" and goalType.value='"+goalType+"' " +//目标类型
					" and endStatus.value = '"+endStatus+"'"+//制定状态
					" order by "+sort+" "+dir, start, limit);
		}
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

			goal.setCreateDept(curDept);
			goal.setCreatePerson(curUser);
			goal.setCreateTime(DateUtil.getNowTime());
			goal.setOwnerDept(curDept);
			goal.setOwnerPerson(curUser);
			goal.setFinishStatus((CodeLibrary) baseService.findUniqueByHQL("from CodeLibrary where codeCatalog.codeNo='FinishStatus' and value='no'"));
			
			// 保存对象
			baseService.add(goal,curUser,ServletActionContext.getRequest().getRemoteAddr(),"json:"+jsonObject);
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
		
		String keyValue = (String)jb.get(keyName);

		if(keyValue != null){
			
			PerformanceGoal entity = (PerformanceGoal) baseService.findById(Class.forName(entityName), Integer.parseInt(keyValue));
			
			CodeLibrary beginStatus = entity.getBeginStatus();
			CodeLibrary endStatus = entity.getEndStatus();
			
			String beginStatusValue = beginStatus == null ? "":beginStatus.getValue();
			String endStatusValue = endStatus == null ? "":endStatus.getValue();
			
			if(beginStatusValue.equals("submit") || beginStatusValue.equals("goback") || (endStatusValue.equals("submit") && beginStatusValue.equals("submit"))){
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
				Struts2Util.responseText("{'success':true,'msg':'保存成功'}");
			} else {
				Struts2Util.responseText("{'success':false,'msg':'已提交的目标不能更新'}");
			}
			
		}
		
	}
	
	
	/**
	 * 删除签署的意见和目标记录
	 * @throws Exception 
	 */
	public void delete() throws Exception{
		
		JSONObject jb = JSONObject.fromObject(jsonObject);
		
		int keyValue = jb.getInt(keyName);
		
		User user = (User)(ActionContext.getContext().getSession().get("curUser"));
		
		PerformanceGoal goal = (PerformanceGoal) baseService.findById(PerformanceGoal.class, keyValue);
		
		if(goal.getCreatePerson().getUid() == user.getUid()){//创建人是当前用户
			
			Struts2Util.responseText(goalService.delete(goal,keyValue));
			
		}else{
			Struts2Util.responseText("{'success':false,'msg':'上级分发的目标不能删除'}");
		}
	}
	
	/**
	 * 撤销目标提交
	 * @throws Exception 
	 */
	public void undo() throws Exception{
		
		Struts2Util.responseText(goalService.undo(uid, applyType));
	}
	
	public void getOpinions(){
		
		List<?> objects = baseService.findByHQL("select approveTime,approveDept.deptName,approvePerson.userName,action.text,postil " +
				"from ApprovePostil " +
				"where goal.uid = '"+uid+"' " +
				"and applyType.value='"+applyType+"' " +
				"and approveTime is not null "+
				"order by approveTime");

		JSONArray topics = new JSONArray();

		for(int i = 0 ; i < objects.size() ; i++){
			Object[] object = (Object[]) objects.get(i);
			
			JSONObject topic = new JSONObject();
			
			topic.put("approveTime", DataConvert.toString(object[0]));
			topic.put("approveDept", DataConvert.toString(object[1]));
			topic.put("approvePerson", DataConvert.toString(object[2]));
			topic.put("action", DataConvert.toString(object[3]));
			topic.put("opinion", DataConvert.toString(object[4]));
			
			topics.add(topic);
			
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("info",topics );
		
		String str_json = jsonObject.toString();
		Struts2Util.responseText(str_json);
		
	}
	
	/**
	 * 保存并提交
	 * @throws Exception
	 */
	public void signOpinion() throws Exception{
		
		PerformanceGoal goal = ((PerformanceGoal)baseService.findById(PerformanceGoal.class, Integer.parseInt(uid)));
		
		ApprovePostil approvePostil = null;
		
		if(opinionUid != null && opinionUid.length()>0 ){//查找当前阶段意见
			approvePostil = (ApprovePostil) baseService.findById(ApprovePostil.class, Integer.parseInt(opinionUid));
		}
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		Department curDept = (Department) session.get("curDept");
		User curUser = (User) session.get("curUser");
		
		if(approvePostil == null){//如果当前阶段意见不存在，则新增意见
			
			approvePostil = new ApprovePostil();
			
			approvePostil.setAction(((CodeLibrary)baseService.findUniqueByHQL("from CodeLibrary where value='"+action+"' and codeCatalog.codeNo='Action'")));
			approvePostil.setApplyType(((CodeLibrary)baseService.findUniqueByHQL("from CodeLibrary where value='"+applyType+"' and codeCatalog.codeNo='ApplyType'")));
			approvePostil.setApproveDept(curDept);
			approvePostil.setApprovePerson(curUser);
			approvePostil.setApproveTime(DateUtil.getToday());
			approvePostil.setGoal(goal);
			approvePostil.setPostil(opinion);
			approvePostil.setScore(0);
			approvePostil.setPhaseNo(1);
			
		} else{
			
			approvePostil.setAction(((CodeLibrary)baseService.findUniqueByHQL("from CodeLibrary where value='"+action+"' and codeCatalog.codeNo='Action'")));
			approvePostil.setApproveTime(DateUtil.getToday());
			approvePostil.setPostil(opinion);
			approvePostil.setScore(0);
		}
		
		Struts2Util.responseText(goalService.signApplyOpinion(goal,approvePostil,curDept,curUser,applyType));
	}
	
	public void findOpinion(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		User curUser = (User) session.get("curUser");
		
		ApprovePostil approvePostil = (ApprovePostil) baseService.findUniqueByHQL(
			" from ApprovePostil " +
			" where  goal.uid = "+uid +//当前目标
			" and applyType.value='"+applyType+"'" +//申请类型
			" and approvePerson.uid="+curUser.getUid()+//审批人是当前用户
			" and approveTime is null"//申请时间是null
		);
		
		JSONObject info = new JSONObject();
		if(approvePostil != null){
			info.put("opinionUid", approvePostil.getUid());
			info.put("action", approvePostil.getAction().getValue());
			info.put("opinion", approvePostil.getPostil());
		}else{
			info.put("action", "agree");
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
	 * 复制目标
	 * @throws Exception 
	 */
	public void copyGoal() throws Exception {
		JSONObject jb = JSONObject.fromObject(jsonObject);
		String uid = (String)jb.get("uid");
		String copyType = (String)jb.get("copyType");
		
		Department curDept = (Department)(ActionContext.getContext().getSession().get("curDept"));
		User curUser = (User)(ActionContext.getContext().getSession().get("curUser"));
		
		PerformanceGoal performGoal = (PerformanceGoal) baseService.findById(PerformanceGoal.class, Integer.parseInt(uid));
		
		PerformanceGoal newPerformGoal = new PerformanceGoal();
		
		newPerformGoal.setBeginStatus(copyType != null ? null :(CodeLibrary) baseService.findUniqueByHQL("from CodeLibrary where codeCatalog.codeNo='ApplyStatus1' and value='submit'"));
		newPerformGoal.setContent(performGoal.getContent());
		newPerformGoal.setCreateDept(curDept);
		newPerformGoal.setCreatePerson(curUser);
		newPerformGoal.setCreateTime(DateUtil.getNowTime());
		newPerformGoal.setDateBatch((DateBatch)baseService.findUniqueByHQL("from DateBatch where batchType.value='"+performGoal.getDateBatch().getBatchType().getValue()+"' and beginStatus=true"));
		newPerformGoal.setFinalApprover(performGoal.getFinalApprover());
		newPerformGoal.setFinishStatus((CodeLibrary) baseService.findUniqueByHQL("from CodeLibrary where codeCatalog.codeNo='FinishStatus' and value='no'"));
		newPerformGoal.setGoalType(performGoal.getGoalType());
		newPerformGoal.setOwnerDept(curDept);
		newPerformGoal.setOwnerPerson(curUser);
		
		baseService.add(newPerformGoal,curUser,ServletActionContext.getRequest().getRemoteAddr(),"json:");

		String str_json = "{'success':true,'msg':'目标复制成功'}";
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
	
	
}

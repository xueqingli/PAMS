package com.lxq.platform.goalManage.pojo;

import com.lxq.platform.systemManage.pojo.CodeLibrary;


/**
 * 期次实体类
 * @author michael
 *
 */
public class DateBatch {
	
	/**主键*/
	private int uid;
	/**期次类型*/
	private CodeLibrary batchType;
	/**期次*/
	private String dateBatch;
	/**期次初状态*/
	private boolean beginStatus;
	/**期次末状态*/
	private boolean endStatus;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public CodeLibrary getBatchType() {
		return batchType;
	}
	public void setBatchType(CodeLibrary batchType) {
		this.batchType = batchType;
	}
	public String getDateBatch() {
		return dateBatch;
	}
	public void setDateBatch(String dateBatch) {
		this.dateBatch = dateBatch;
	}
	public boolean isBeginStatus() {
		return beginStatus;
	}
	public void setBeginStatus(boolean beginStatus) {
		this.beginStatus = beginStatus;
	}
	public boolean isEndStatus() {
		return endStatus;
	}
	public void setEndStatus(boolean endStatus) {
		this.endStatus = endStatus;
	}

}
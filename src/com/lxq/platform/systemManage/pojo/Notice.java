package com.lxq.platform.systemManage.pojo;


/**
 * 系统通知
 * @author lixueqing
 * 
 */
public class Notice {
	
	/**菜单主键*/
	private int uid;

	/**标题*/
	private String title;

	/**附件路径*/
	private String filePath;
	
	/**开始日期*/
	private String startDate;
	
	/**开始日期*/
	private String endDate;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
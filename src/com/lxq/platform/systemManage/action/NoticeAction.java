package com.lxq.platform.systemManage.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.lxq.platform.action.BaseAction;
import com.lxq.platform.systemManage.pojo.Notice;
import com.lxq.platform.userManage.pojo.User;
import com.lxq.platform.util.DataConvert;
import com.lxq.platform.util.DateUtil;
import com.lxq.platform.util.Struts2Util;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class NoticeAction extends BaseAction {
	
	private int uid;
	private File file;
	private String contentType;
	private String fileName;
	private String saveDir;
	
	/**
     * 获取json格式数据
     */
	public void showNotice() {

		String today = DateUtil.getToday();
		List<?> objects = baseService.findByHQL("select title,uid,filePath from Notice where '"+today+"'>=startDate and '"+today+"'<=endDate");

		JSONArray topics = new JSONArray();

		for(int i = 0 ; i < objects.size() ; i++){
			Object[] object = (Object[]) objects.get(i);
			
			JSONObject topic = new JSONObject();
			
			topic.put("title", DataConvert.toString(object[0]));
			topic.put("uid", DataConvert.toString(object[1]));
			topic.put("filePath", DataConvert.toString(object[2]));
			
			topics.add(topic);
			
		}
		
		Struts2Util.responseText("var notices = "+topics.toString());
	}
	
	public void upload() throws Exception {
		
		User user = (User)(ActionContext.getContext().getSession().get("curUser"));
		
		Notice notice = (Notice) baseService.findById(Notice.class, uid);
		
		String filePath = "/WEB-INF/data/notice/"+fileName;
		
		//如果有附件，则先删除附件
		if(notice.getFilePath()!=null && notice.getFilePath().length()>0){
			Struts2Util.deleteFile(ServletActionContext.getServletContext().getRealPath("")+notice.getFilePath());
		}
		//保存附件
		Struts2Util.saveFile(file,ServletActionContext.getServletContext().getRealPath("")+filePath);
		
		//保存附件路径
		notice.setFilePath(filePath);
		baseService.add(notice,user,ServletActionContext.getRequest().getRemoteAddr(),"json:"+JSONObject.fromObject(notice));
		
		String str_json = "{'success':true}";
		Struts2Util.responseText(str_json);
		
	}
	
	public void delete() throws Exception{
		
		User user = (User)(ActionContext.getContext().getSession().get("curUser"));
		
		JSONObject jb = JSONObject.fromObject(jsonObject);
		
		int keyValue = jb.getInt(keyName);
		
		Notice notice = (Notice) baseService.findById(Notice.class, keyValue);
		
		baseService.delete(notice,user,ServletActionContext.getRequest().getRemoteAddr(),"json:"+jsonObject);
		
		Struts2Util.deleteFile(ServletActionContext.getServletContext().getRealPath("")+notice.getFilePath());
		
		String str_json = "{'success':true,'msg':'删除成功'}";
		Struts2Util.responseText(str_json);
	}

	public void downloadFile() throws IOException{
		Notice notice = (Notice) baseService.findById(Notice.class, uid);
		
		if(notice.getFilePath() != null && notice.getFilePath().length()>0){
			Struts2Util.downLoadFile(ServletActionContext.getServletContext().getRealPath("")+notice.getFilePath());
		}
		
	}
	
	
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public void setUploadContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setUpload(File file) {
		this.file = file;
	}

	public void setUploadFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSaveDir() {
		return saveDir;
	}

	public void setSaveDir(String saveDir) {
		this.saveDir = saveDir;
	}
	
}

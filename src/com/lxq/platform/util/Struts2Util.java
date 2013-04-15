package com.lxq.platform.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.lxq.platform.userManage.pojo.Department;
import com.lxq.platform.userManage.pojo.User;
import com.opensymphony.xwork2.ActionContext;


public class Struts2Util
{
	private static Logger logger = Logger.getLogger(Struts2Util.class);
	/**
	 * response返回数据
	 * @param text 输出的字符串
	 * @throws IOException
	 */
	public static void responseText(String text) 
	{
		//获得response
		HttpServletResponse response=ServletActionContext.getResponse();

		response.setContentType("text/html;charset=utf-8");
		
		//设置输出UTF-8编码
		response.setCharacterEncoding("UTF-8");
		
		//获得输出流
		PrintWriter pw;

		try {
			pw = response.getWriter();
			//将数据写入输出流
			pw.write(text); 
			
			//清楚数据流缓冲区
			pw.flush();  
			
			//关闭输出流
			pw.close();
		} catch (IOException e) {
			logger.error("系统错误",e);
		}

	}
	
	public static void saveFile(File file,String filePath) throws IOException{
		File saveFile = new File(filePath);

		if (!saveFile.getParentFile().exists()){
			saveFile.getParentFile().mkdirs();
		}
		if (saveFile.exists()){
			int dotIndex = filePath.lastIndexOf(".");
			
			if(dotIndex > 0){
				saveFile = new File(filePath.substring(0,dotIndex)+"_"+DateUtil.getNowTime("yyyyMMddHHmmssSSS")+filePath.substring(dotIndex));
			}else{
				saveFile = new File(filePath+"_"+DateUtil.getNowTime("yyyyMMddHHmmssSSS"));
			}
		}
		FileUtils.copyFile(file, saveFile);// 复制文件
	}
	
	public static void deleteFile(String filePath) throws IOException{
		File file = new File(filePath);
		
		if (file.exists()){
			file.delete();
		}
	}
	
	public static void downLoadFile(String filePath) throws IOException{
		
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("application/octet-stream;charset=UTF-8");
		
		response.reset(); //清空输出流
		String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
		
		fileName = new String(fileName.getBytes(), "ISO-8859-1");
		response.setHeader("Content-disposition", "attachment; filename="+fileName);  
		
		InputStream is = new FileInputStream(filePath); 
		OutputStream out = response.getOutputStream();
		
		byte[] b = new byte[1024];
		int len = 0;
		while((len = is.read(b)) > -1){
			out.write(b, 0, len);
		}
		
		out.flush();
		out.close();
	}

	public static User getCurUser(){
		return (User)(ActionContext.getContext().getSession().get("curUser"));
	}
	
	public static Department getCurDept(){
		return (Department)(ActionContext.getContext().getSession().get("curDept"));
	}
	
}

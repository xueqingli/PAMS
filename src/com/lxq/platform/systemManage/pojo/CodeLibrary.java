package com.lxq.platform.systemManage.pojo;

/**
 * 代码明细实体类
 * @author lixueqing
 *
 */
public class CodeLibrary {
	
	/**主键*/
	private int uid;
	
	/**代码值*/
	private String value;
	
	/**代码显示*/
	private String text;
	
	/**代码目录*/
	private CodeCatalog codeCatalog;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public CodeCatalog getCodeCatalog() {
		return codeCatalog;
	}
	public void setCodeCatalog(CodeCatalog codeCatalog) {
		this.codeCatalog = codeCatalog;
	}
	
	
}
package com.lxq.platform.systemManage.pojo;

import java.util.Set;

/**
 * 代码目录实体类
 * @author lixueqing
 *
 */
public class CodeCatalog {

	/**代码目录编号*/
	private int uid;

	/**代码目录编号*/
	private String codeNo;
	
	/**代码目录描述*/
	private String codeName;
	
	/**代码信息集合*/
	private Set<CodeLibrary> codeLibrarys;
	
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public Set<CodeLibrary> getCodeLibrarys() {
		return codeLibrarys;
	}

	public void setCodeLibrarys(Set<CodeLibrary> codeLibrarys) {
		this.codeLibrarys = codeLibrarys;
	}

}
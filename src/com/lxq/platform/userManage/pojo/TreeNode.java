package com.lxq.platform.userManage.pojo;


/**
 * Ext树节点实体类
 * @author lixueqing
 * 
 */
public class TreeNode {
	
	/**节点主键*/
	private String id;

	/**节点名称*/
	private String text;
	
	/**节点链接地址*/
	private String url;
	
	/**节点样式，folder:文件夹，file:文件*/
	private String cls;

	/**是否叶子节点*/
	private boolean leaf;
	
	/**子节点*/
	private TreeNode[] children;
	
	/**json对象字符串*/
	private String jsonObject;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public TreeNode[] getChildren() {
		return children;
	}

	public void setChildren(TreeNode[] children) {
		this.children = children;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(String jsonObject) {
		this.jsonObject = jsonObject;
	}

}
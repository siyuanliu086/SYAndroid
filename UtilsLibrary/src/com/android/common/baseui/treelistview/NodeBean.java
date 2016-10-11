package com.android.common.baseui.treelistview;

public class NodeBean {
	/**
	 * 节点Id
	 */
	private String id;
	/**
	 * 节点父id
	 */
	private String pId;
	/**
	 * 节点name
	 */
	private String name;
	/**
	 * 
	 */
	private String desc;
	/**
	 * 节点名字长度
	 */
	private long length;
	/**
	 * 用户登录名称
	 */
	private String loginName;
	
	public NodeBean(String id, String pId, String name, String loginName) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.loginName = loginName;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pId;
	}
	public void setPid(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
}

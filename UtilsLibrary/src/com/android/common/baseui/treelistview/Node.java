package com.android.common.baseui.treelistview;

import java.util.ArrayList;
import java.util.List;

public class Node {
	/**
	 * 节点id
	 */
	private String id;
	/**
	 * 父节点id
	 */
	private String pId;
	/**
	 * 是否展开
	 */
	private boolean isExpand = false;
	private boolean isChecked = false;//是否选中
	private boolean isHideChecked = false;//CheckBox是否隐藏
	/**
	 * 节点名字
	 */
	private String name;
	/**
	 * 登录名称
	 */
	private String loginName;
	/**
	 * 节点级别
	 */
	private int level;
	/**
	 * 节点展示图标
	 */
	private int icon;
	/**
	 * 节点所含的子节点
	 */
	private List<Node> childrenNodes = new ArrayList<Node>();
	/**
	 * 节点的父节点
	 */
	private Node parent;

	public Node() {
	}

	public Node(String id, String pId, String name, String loginName) {
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

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public boolean isExpand() {
		return isExpand;
	}

	/**
	 * 当父节点收起，其子节点也收起
	 * @param isExpand
	 */
	public void setExpand(boolean isExpand) {
		this.isExpand = isExpand;
		if (!isExpand) {

			for (Node node : childrenNodes) {
				node.setExpand(isExpand);
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public int getLevel() {
		return parent == null ? 0 : parent.getLevel() + 1;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public List<Node> getChildrenNodes() {
		return childrenNodes;
	}

	public void setChildrenNodes(List<Node> childrenNodes) {
		this.childrenNodes = childrenNodes;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	/**
	 * 判断是否是根节点
	 * 
	 * @return
	 */
	public boolean isRoot() {
		return parent == null;
	}

	/**
	 * 判断是否是叶子节点
	 * 
	 * @return
	 */
	public boolean isLeaf() {
		return childrenNodes.size() == 0;
	}
	

	/**
	 * 判断父节点是否展开
	 * 
	 * @return
	 */
	public boolean isParentExpand()
	{
		if (parent == null)
			return false;
		return parent.isExpand();
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public boolean isHideChecked() {
		if (isLeaf()) {
			return isHideChecked;
		}
		return true;
	}

	public void setHideChecked(boolean isHideChecked) {
		this.isHideChecked = isHideChecked;
	}
}

package com.zhangk.babysitter.viewmodel.manage;

import java.util.List;

import com.zhangk.babysitter.entity.County;

public class ManageCountyView {
	private long id;
	private String guid;
	private String text;
	private String state = "open";// open,closed
	private boolean checked = false;
	private List<ManageCountyView> children;
	private String shortName;
	private String iconCls = "";
	private long pId;
	private boolean open;

	public ManageCountyView() {
		// TODO Auto-generated constructor stub
	}

	public ManageCountyView(County county) {
		setId(county.getId());
		setGuid(county.getGuid());
		setText(county.getName());
		setPId(county.getParent() == null ? 0 : county.getParent().getId());
		setGuid(county.getGuid());
		setShortName(county.getShortName());
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<ManageCountyView> getChildren() {
		return children;
	}

	public void setChildren(List<ManageCountyView> children) {
		this.children = children;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public long getPId() {
		return pId;
	}

	public void setPId(long pId) {
		this.pId = pId;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

}

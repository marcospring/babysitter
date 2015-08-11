package com.zhangk.babysitter.viewmodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhangk.babysitter.entity.Menu;

public class MenuView {
	private long id;
	private String guid;
	private String text;
	private String name;
	private String state = "open";// open,closed
	private boolean checked = false;
	private Object attributes;
	private List<MenuView> children;
	private String url;
	private String iconCls = "";
	private long pId;
	private boolean open;

	public MenuView() {
		// TODO Auto-generated constructor stub
	}

	public MenuView(Menu menu) {
		setId(menu.getId());
		setText(menu.getTitle());
		setName(menu.getTitle());
		setAttributes(getAttr(menu.getUrl()));
		setPId(menu.getParent() == null ? 0 : menu.getParent().getId());
		setGuid(menu.getGuid());
		setUrl(menu.getUrl());
	}

	private Map<String, Object> getAttr(String url) {
		Map<String, Object> attr = new HashMap<String, Object>();
		attr.put("url", url);
		return attr;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Object getAttributes() {
		return attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}

	public List<MenuView> getChildren() {
		return children;
	}

	public void setChildren(List<MenuView> children) {
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

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

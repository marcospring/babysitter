package com.zhangk.babysitter.viewmodel;

import com.zhangk.babysitter.entity.BabysitterImage;
import com.zhangk.babysitter.utils.common.Constants;

public class BabysitterImageView {
	private String guid;
	private String name;
	private String url;

	public BabysitterImageView() {
	}

	public BabysitterImageView(BabysitterImage image) {
		setGuid(image.getGuid());
		setName(image.getName());
		setUrl(Constants.IMG_DOMAIN + image.getUrl());
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
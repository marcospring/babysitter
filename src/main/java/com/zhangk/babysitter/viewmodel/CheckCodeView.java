package com.zhangk.babysitter.viewmodel;

import com.zhangk.babysitter.entity.CheckCode;

public class CheckCodeView {

	private String guid;
	private String code;
	private int type;
	private String mobilePhone;

	public CheckCodeView() {
		// TODO Auto-generated constructor stub
	}

	public CheckCodeView(CheckCode code) {
		setGuid(code.getGuid());
		setCode(code.getCode());
		setType(code.getType());
		setMobilePhone(code.getMobilePhone());
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

}

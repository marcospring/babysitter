package com.zhangk.babysitter.viewmodel;

public class PreferentialVO {
	private String telephone;
	private long money;
	private String message;
	private String guid;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

}

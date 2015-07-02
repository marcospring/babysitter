package com.zhangk.babysitter.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseUser extends BaseEntity {

	Logger log = LoggerFactory.getLogger(BaseUser.class);
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String mobilePhone;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

}

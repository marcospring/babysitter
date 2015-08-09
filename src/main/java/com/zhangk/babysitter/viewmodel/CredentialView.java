package com.zhangk.babysitter.viewmodel;

import com.zhangk.babysitter.entity.BabysitterCredential;

public class CredentialView {
	private String guid;
	private String name;
	private int check;
	private int credentialType;

	public CredentialView() {
		// TODO Auto-generated constructor stub
	}

	public CredentialView(BabysitterCredential credential) {
		setName(credential.getCredential().getName());
		setGuid(credential.getGuid());
		setCheck(credential.getIscheck());
		setCredentialType(credential.getCredential().getCredentialType());
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

	public int getCheck() {
		return check;
	}

	public void setCheck(int check) {
		this.check = check;
	}

	public int getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(int credentialType) {
		this.credentialType = credentialType;
	}

}

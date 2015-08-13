package com.zhangk.babysitter.viewmodel;

import com.zhangk.babysitter.entity.Credential;

public class DicCredentialView {
	private String guid;
	private String name;
	private int credentialType;
	private int score;

	public DicCredentialView() {
		// TODO Auto-generated constructor stub
	}

	public DicCredentialView(Credential credential) {
		setGuid(credential.getGuid());
		setName(credential.getName());
		setCredentialType(credential.getCredentialType());
		setScore(credential.getScore());
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

	public int getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(int credentialType) {
		this.credentialType = credentialType;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
